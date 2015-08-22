package nitorac.multiforge.main;

import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.beans.Beans;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Util {
	
	private static final String BUNDLE_NAME = "nitorac.multiforge.resources.i18n.lang"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = loadBundle();
	private static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	public static enum OS {
        WINDOWS, MACOS, SOLARIS, LINUX, UNKNOWN;
    }

    public static OS getPlatform(boolean print) {
        final String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("win")){
        	// 38 chars   
        	if(print){Util.println("*************Windows OS***************");}
            return OS.WINDOWS;
        }
        if(osName.contains("mac")){
        	if(print){Util.println("***************Mac OS*****************");}
            return OS.MACOS;
        }
        if(osName.contains("linux")){
        	if(print){Util.println("**************Linux OS****************");}
            return OS.LINUX;
        }
        if(osName.contains("unix")){
        	if(print){Util.println("**************Linux OS****************");}
            return OS.LINUX;
        }
        	if(print){Util.println("*************Unknown OS***************");}
        return OS.UNKNOWN;
    }

    public static File getWorkingDirectory() {
        final String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch(getPlatform(false)) {
        case LINUX:
            workingDirectory = new File(userHome);
            break;
        case WINDOWS:
            final String applicationData = System.getenv("APPDATA");
            final String folder = applicationData != null ? applicationData : userHome;

            workingDirectory = new File(folder);
            break;
        case MACOS:
            workingDirectory = new File(userHome, "Library/Application Support/");
            break;
        default:
            workingDirectory = new File(userHome);
        }

        return workingDirectory;
    }
    
    public static File getMinecraftDirectory(){
    	File minecraftDir;
    	switch(getPlatform(false)) {
        case LINUX:
        	minecraftDir = new File(getWorkingDirectory().getAbsolutePath() + "/.minecraft");
            break;
        case WINDOWS:
        	minecraftDir = new File(getWorkingDirectory().getAbsolutePath() + "/.minecraft");
            break;
        case MACOS:
        	minecraftDir = new File(getWorkingDirectory().getAbsolutePath() + "/minecraft");
            break;
        default:
        	minecraftDir = new File(getWorkingDirectory().getAbsolutePath() + "/.minecraft");
        }
    	return minecraftDir;
    }
    
    public static String getMostRecentVersionBuildForgeVersion(String mcversion){
    	File versionsDir = new File(getMinecraftDirectory().getAbsolutePath() + "/versions");
    	Util.println(versionsDir.getAbsolutePath());    	

    	String[] names = versionsDir.list();
    	ArrayList<String> dirNames = new ArrayList<String>();
    	
    	for(String name : names){
    	    if (new File(versionsDir + "/" + name).isDirectory()){
    	    	dirNames.add(name);
    	    }
    	}
        
    	ArrayList<String> dirNamesVersions = new ArrayList<String>();
    	for(int i = 0;i<dirNames.size();i++){
    		String dirName = dirNames.get(i);
    		String[] splitted = dirName.split("-Forge");
    		
    		if(!splitted[0].equals(null) && splitted[0].equals(mcversion) && dirName.contains(splitted[0] + "-Forge")){
    			dirNamesVersions.add(dirName);
    		}
    	}
    	
    	if(dirNamesVersions.size() == 0){
    		return "";
    	}
    	
    	ArrayList<BuildNum> buildNums = new ArrayList<BuildNum>();
    	
    	for(int i = 0;i<dirNamesVersions.size();i++){
    		try {
	    		String buildSupposed = dirNamesVersions.get(i).split("-Forge")[1];
	    		String jobver = buildSupposed.split("\\.")[0] + "." + buildSupposed.split("\\.")[1] + "." + buildSupposed.split("\\.")[2];
	    		String buildNum = String.valueOf(buildSupposed.split("\\.")[3].subSequence(0, 4));
	    		
	    		try{
	    			int num1 = Integer.parseInt(jobver.split("\\.")[0]);
	    			int num2 = Integer.parseInt(jobver.split("\\.")[1]);
	    			int num3 = Integer.parseInt(jobver.split("\\.")[2]);
	    			int num4 = Integer.parseInt(buildNum);
	    			
	    			buildNums.add(new BuildNum(num1, num2, num3, num4));
	    		}catch(Exception e){
	    			Util.println("It's not a correct build", e);
	    		}
    		}catch(Exception e){
    			Util.println("It's not a correct build", e);
    		}
    	}
    	    
    	return "Forge-" + getNewestVersion(buildNums).toString();
    }
    
    public static BuildNum getNewestVersion(ArrayList<BuildNum> nums){
    	BuildNum newerBuild = nums.get(0);
    	
    	for(int i = 0;i<nums.size();i++){
    		newerBuild = compareTwoBuildNums(newerBuild, nums.get(i));
    	}
    	
    	Util.println(newerBuild.toString());
    	return newerBuild;
    }
    
    public static BuildNum compareTwoBuildNums(BuildNum bn1, BuildNum bn2){
    	if(bn1.getNum1() < bn2.getNum1()){
    		return bn2;
    	}else if(bn1.getNum1() == bn2.getNum1()){
    		
        	if(bn1.getNum2() < bn2.getNum2()){
        		return bn2;
        	}else if(bn1.getNum2() == bn2.getNum2()){
        		
            	if(bn1.getNum3() < bn2.getNum3()){
            		return bn2;
            	}else if(bn1.getNum3() == bn2.getNum3()){
            		
                	if(bn1.getNum4() < bn2.getNum4()){
                		return bn2;
                	}else if(bn1.getNum4() == bn2.getNum4()){
                		return bn1;
                	}else if(bn1.getNum4() > bn2.getNum4()){
                		return bn1;
                	}
                	return null;
            		
            	}else if(bn1.getNum3() > bn2.getNum3()){
            		return bn1;
            	}
            	return null;
        		
        	}else if(bn1.getNum2() > bn2.getNum2()){
        		return bn1;
        	}
        	return null;
        	
    	}else if(bn1.getNum1() > bn2.getNum1()){
    		return bn1;
    	}
    	return null;
    }
    
    public static File getSettingsFile(){
    	File file = new File(getWorkingDirectory().getAbsolutePath() + "/MinecraftForgeVersions.settings");
    	if(!file.exists()){
    		try {
				file.createNewFile();
			} catch (IOException e) {
				Util.println(e);
			}
    	}
    	return file;
    }
	
	public static ImageIcon createImageIcon(String path, int width, int lenght) {
	        java.net.URL imgURL = Main.class.getResource(path);
	        if (imgURL != null) {
	        	ImageIcon imageIcon = new ImageIcon(imgURL);
	        	Image image = imageIcon.getImage(); 
	        	Image newimg = image.getScaledInstance(width, lenght,  Image.SCALE_SMOOTH);  
	        	Util.println(Util.getStr("iconLoaded") + path);
	        	return new ImageIcon(newimg);
	        } else {
	            Util.println("Imposs: " + path, new IOException());
	            return null;
	        }
	}
    
    @SuppressWarnings("unchecked")
	public static void putParam(String key, Object obj){    	
        	try {
				JSONObject jsonObj = (JSONObject)new JSONParser().parse(new FileReader(getSettingsFile()));
				jsonObj.put(key, obj);
				
				FileWriter file = new FileWriter(getSettingsFile());
				file.write(jsonObj.toJSONString());
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(key, obj);
				
				try {
					FileWriter file = new FileWriter(getSettingsFile());
					file.write(jsonObj.toJSONString());
					file.flush();
					file.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
    }
    
    public static void removeParam(String key){
    	try {
			Object obj = new JSONParser().parse(new FileReader(getSettingsFile()));
			JSONObject jsonObject = (JSONObject) obj;
			try{
				jsonObject.remove(key);
				FileWriter file = new FileWriter(getSettingsFile());
				file.write(jsonObject.toJSONString());
				file.flush();
				file.close();
			}catch(Exception e){}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ParseException e) {
			Util.println(getStr("settingsFileCreation"));
		}
    }
    
    public static Object getParam(String key, Object defaut){
		try {
			Object obj = new JSONParser().parse(new FileReader(getSettingsFile()));
			JSONObject jsonObject = (JSONObject) obj;
			try{
				if(!jsonObject.get(key).equals(null)){
					return jsonObject.get(key);
				}
		}catch(Exception e){}
		} catch (FileNotFoundException e) {} catch (IOException e) {
		} catch (ParseException e) {
			Util.println(getStr("settingsFileCreation"));
		}
		
		putParam(key, defaut);
		return getParam(key, defaut);
    }
    
    public static String getStr(String key) {
		try {
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
    
	public static boolean isCorrectDirectory(String path){
		if(new File(path).exists()){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static String showFolderChooser(JFrame frame, String title){
		if(getPlatform(false) == OS.MACOS){
			System.setProperty("apple.awt.fileDialogForDirectories", "true");
			FileDialog chooser = new FileDialog(frame, title); 
		    chooser.setDirectory(new java.io.File(".").getAbsolutePath());
		    chooser.setVisible(true);

		    return chooser.getFile();
		}else{
			JFileChooser chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle(title);
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    //
		    // disable the "All files" option.
		    //
		    chooser.setAcceptAllFileFilterUsed(false);
		    //    
		    if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { 
			      return chooser.getSelectedFile().getAbsolutePath();
		      }else {
		    	  return "";
		      }
		}
	}
	
	public static GridBagConstraints defConstraints(){
		final GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = 17;
        return constraints;
	}

	public static void println() {
	    System.out.println();
	    Main.panelConsole.print("\n");
	}

	public static void println(final String line) {
	    System.out.println(line);
	    Main.panelConsole.print(line + "\n");
	}

	public static void println(final String line, final Throwable throwable) {
	    println(line);
	    Util.println(throwable);
	}

	public static void println(final Throwable throwable) {
	    StringWriter writer = null;
	    PrintWriter printWriter = null;
	    String result = throwable.toString();
	    try {
	        writer = new StringWriter();
	        printWriter = new PrintWriter(writer);
	        throwable.printStackTrace(printWriter);
	        result = writer.toString();
	    }
	    finally {
	        try {
	            if(writer != null)
	                writer.close();
	            if(printWriter != null)
	                printWriter.close();
	        }
	        catch(final IOException localIOException1) {
	        }
	
	    }
	    println(result);
	}

	public static boolean isInternetReachable(String site) {
	
				URL url;
				HttpURLConnection con;
				try {
					url = new URL(site);
					con = (HttpURLConnection) url.openConnection();
					con.connect();
				} catch (MalformedURLException e) {
					return false;
				} catch(IOException e){
					return false;
				}
				
				try{
					con.getResponseCode();
					con.disconnect();
					return true;
				}catch(Exception e){
					return false;
				}
				
	}

	public static JSONObject getForgeFile(String url){
		JSONGetter jg = new JSONGetter();
		return jg.jsonParser(jg.jsonReader(url));
	}

	public static void restartApp() throws URISyntaxException, IOException{
		StringBuilder cmd = new StringBuilder();
	    cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
	    
	    for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
	        cmd.append(jvmArg + " ");
	    }
	    
	    cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
	    cmd.append(Main.class.getName()).append(" ");
	    Runtime.getRuntime().exec(cmd.toString());
	    System.exit(0);
	}
}
