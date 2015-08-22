package nitorac.multiforge.main;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import nitorac.multiforge.main.panels.PanelSettings;
import nitorac.multiforge.main.panels.PanelStats;
import nitorac.multiforge.main.panels.PanelStatsFragment;
import nitorac.multiforge.main.panels.PanelVersion;
import nitorac.multiforge.main.panels.PanelVersionBuildSelec;
import nitorac.multiforge.main.panels.PanelVersionBuildStats;
import nitorac.multiforge.main.panels.PanelVersionMcVersion;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Main {

	public static JFrame mainFrame;
	public static PanelConsole panelConsole = new PanelConsole();
	
	public static String startIndexUrl = "http://files.minecraftforge.net/maven/net/minecraftforge/forge/index.json";
	public static String baseIndexUrl = "http://files.minecraftforge.net/maven/net/minecraftforge/forge/index_%s.json";
	
	private static JSONArray mcversions;
	
	public static ArrayList<JSONObject> jsonForgeObjects = new ArrayList<JSONObject>();
	
	public static int MCVersion = 0;
	public static int BUILDVersion = 0;
	public static int FILEtype = 0;
	
	public static ImageIcon updateIcon;
	
	public static int internetMs = 0;
		
	public static ReverseComboBoxIndex reverteBuild;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					Main window = new Main();
					window.mainFrame.setVisible(true);			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		final ProgressDLDialog pd = new ProgressDLDialog(mainFrame, Util.getStr("connectionTitle"), Util.getStr("connectionMsg"), "");
		pd.setAlwaysOnTop(true);
		pd.setVisible(true);

		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0;i<i+1;i++){
					if(!pd.isVisible()){
						Main.setInternetMs(i);
						break;
					}
					pd.setProgress(String.valueOf(i) + " ms");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						Util.println(e);
					}
				}
			}
		}).start();
		
		if(Util.isInternetReachable("http://www.google.com")||Util.isInternetReachable("http://www.amazon.com")){
			pd.dispose();
			parsingInformations();
		}else{
			Util.println("No Internet");
			pd.dispose();
			JOptionPane.showMessageDialog(mainFrame,Util.getStr("noConnectionMsg"), Util.getStr("noConnectionTitle"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void setInternetMs(int ms){
		PanelStatsFragment.timeInternetJLabel.setText(String.format(Util.getStr("internetTime"), ms));
	}
	
	public static void parsingInformations(){
		final ProgressDLDialog pd = new ProgressDLDialog(Main.mainFrame, Util.getStr("recuperer"), Util.getStr("gettingForgeInfos"), Util.getStr("init"));
		pd.setVisible(true);
		pd.setAlwaysOnTop(true);
		
		JSONObject forge_file = Util.getForgeFile(startIndexUrl);
		mcversions = (JSONArray)forge_file.get("mcversions");
		
		pd.setProgress(Util.getStr("parsingForgeInfos"));
				
		for (int i = 0;i<mcversions.size();i++) {
			String ver = (String) mcversions.get(i);
            PanelVersionMcVersion.mcVerComboBox.addItem(ver);
            Util.println(String.format(Util.getStr("parsingMcVersions"), String.valueOf(i), ver));
            pd.setProgress(String.format(Util.getStr("creatingVersion"), String.valueOf(i)));
        }
		
		Util.println();
		pd.setProgress(Util.getStr("finishedCreation"));
		Util.println(Util.getStr("parsingFinished"));
		Util.println();
		
		pd.setProgress(Util.getStr("gettingJSONs"));
		for (int i = 0;i<mcversions.size();i++){
			pd.setProgress(String.format(Util.getStr("gettingJSONversions"), mcversions.get(i)));
			String url = String.format(baseIndexUrl, mcversions.get(i));
			Util.println("JSON : " + url + " ...");
			jsonForgeObjects.add(Util.getForgeFile(url));
			Util.println(String.format(Util.getStr("bufferingJSONs"), mcversions.get(i)));
			Util.println();
		}
		
		PanelStatsFragment.filesDL.setText(String.format(Util.getStr("jsonFiles"), mcversions.size()));
		
		pd.setProgress(Util.getStr("finalization"));
		
		PanelVersionMcVersion.mcVerComboBox.setEnabled(true);
				
		PanelVersionMcVersion.mcVerComboBox.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String mostRecentVersion = Util.getMostRecentVersionBuildForgeVersion((String)mcversions.get(PanelVersionMcVersion.mcVerComboBox.getSelectedIndex()));
		    	
		    	final BuildNum installedVer = new BuildNum(0, 0, 0, 0);
		    	if(!mostRecentVersion.isEmpty()){
			    	installedVer.parse(mostRecentVersion.split("Forge-")[1]);
		    	}
		    	final int sel = PanelVersionMcVersion.mcVerComboBox.getSelectedIndex();
		        Util.println(sel + "-->"+mcversions.get(sel));
		        PanelVersionBuildStats.lblLatestBuild.setText(String.format(Util.getStr("lblLatestBuild"), mcversions.get(sel)));
		        PanelVersionBuildStats.lblRecommendedBuild.setText(String.format(Util.getStr("lblReommendedBuild"), mcversions.get(sel)));
		        
		        PanelVersionBuildSelec.fileTypeComboBox.removeAllItems();
		        
		        for(ActionListener al : PanelVersionBuildSelec.buildVerComboBox.getActionListeners()) {
		        	PanelVersionBuildSelec.buildVerComboBox.removeActionListener(al);
		        }
		        
		        String LatestBuild = "";
		        String RecommendedBuild = "";
		        
		        try{
			        JSONObject md = (JSONObject)jsonForgeObjects.get(sel).get("md");
			        JSONArray versions = (JSONArray)md.get("versions");
			        JSONObject selVersion = (JSONObject)versions.get(versions.size()-1);
			        String selVersionString = (String)selVersion.get("version");
			        if(!selVersionString.isEmpty()){
			        	PanelVersionBuildStats.latestVersionBuild.setText("Forge-"+selVersionString);
				        LatestBuild = selVersionString;
			        }else{
			        	throw new Exception();
			        }
		        }catch(Exception e1){
		        	Util.println(e1);
		        	PanelVersionBuildStats.lblLatestBuild.setText(Util.getStr("cantgetLatestVersion"));
		        	PanelVersionBuildStats.latestVersionBuild.setText("");
		        }
		        
		        try{
		        	JSONObject md = (JSONObject)jsonForgeObjects.get(sel).get("md");
		        	JSONObject promos = (JSONObject)md.get("promos");
		        	JSONObject ver = (JSONObject)promos.get(mcversions.get(sel));
		        	String recommendedBuild = (String)ver.get("RECOMMENDED");
		        	if(!recommendedBuild.isEmpty()){
		        		PanelVersionBuildStats.recommendedVersionBuild.setText("Forge-"+recommendedBuild);
				        RecommendedBuild = recommendedBuild;
			        }else{
			        	throw new Exception();
			        }
		        }catch(Exception e2){
		        	PanelVersionBuildStats.lblRecommendedBuild.setText(Util.getStr("noRecommendedVersion"));
		        	PanelVersionBuildStats.recommendedVersionBuild.setText("");
		        }
		        
		        try{
		        	JSONObject md = (JSONObject)jsonForgeObjects.get(sel).get("md");
			        JSONArray versions = (JSONArray)md.get("versions");
			        Util.println();
			        Util.println(Util.getStr("readingVersion"));
			        PanelVersionBuildSelec.buildVerComboBox.removeAllItems();
			        
			        reverteBuild = new ReverseComboBoxIndex(versions.size());
			        
		        	for(int i = versions.size()-1;i>0;i--){
				        JSONObject selVersion = (JSONObject)versions.get(i);
				        String selVersionString = (String)selVersion.get("version");
				        if(selVersionString.equals(LatestBuild)){
				        	PanelVersionBuildSelec.buildVerComboBox.addItem("Forge-" + selVersionString + " " + Util.getStr("latestComboBox"));
				        }else if(selVersionString.equals(RecommendedBuild)){
				        	PanelVersionBuildSelec.buildVerComboBox.addItem("Forge-" + selVersionString + " " + Util.getStr("recommendedComboBox"));
				        }else{
				        	PanelVersionBuildSelec.buildVerComboBox.addItem("Forge-" + selVersionString);
				        }
		        	}
		        	Util.println(Util.getStr("readingFinished"));
		        }catch(Exception e3){
		        	Util.println(e3);
		        }
		        
		        PanelVersionBuildSelec.buildVerComboBox.setEnabled(true);
		        		        
		        PanelVersionBuildSelec.buildVerComboBox.addActionListener(new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				    	PanelVersionBuildSelec.fileTypeComboBox.removeAllItems();
				    	BuildNum latestVer = new BuildNum(0, 0, 0, 0);
				    	latestVer.parse(PanelVersionBuildSelec.buildVerComboBox.getSelectedItem().toString().split("Forge-")[1].split(" ")[0]);
				    			    	
				    	if(Util.compareTwoBuildNums(installedVer, latestVer).equals(latestVer)){
				    		PanelVersionBuildSelec.update.setIcon(updateIcon);
				    	}else{
				    		PanelVersionBuildSelec.update.setIcon(null);
				    	}
				        try{
				        	JSONObject md = (JSONObject)jsonForgeObjects.get(sel).get("md");
					        JSONArray versions = (JSONArray)md.get("versions");
					        JSONObject selVersion = (JSONObject)versions.get(reverteBuild.invertedItem(PanelVersionBuildSelec.buildVerComboBox.getSelectedIndex()));
					        JSONObject classifiers = (JSONObject)selVersion.get("classifiers");
					        
					        @SuppressWarnings("unchecked")
							Set<String> set = classifiers.keySet();
					        for(int i = 0;i<classifiers.size();i++){
					        	String obj = (String) set.toArray()[i];
					        	PanelVersionBuildSelec.fileTypeComboBox.addItem(obj.toString());
					        }
					        PanelVersionBuildSelec.fileTypeComboBox.setEnabled(true);
				        }catch(Exception e4){
				        	Util.println(e4);
				        }
				    }
		        });
		        
		        PanelVersionBuildSelec.buildVerComboBox.setSelectedIndex(0);
		    	BuildNum latestVer = new BuildNum(0, 0, 0, 0);
		    	latestVer.parse(PanelVersionBuildSelec.buildVerComboBox.getSelectedItem().toString().split("Forge-")[1].split(" ")[0]);
		    			    	
		    	if(Util.compareTwoBuildNums(installedVer, latestVer).equals(latestVer)){
		    		PanelVersionBuildSelec.update.setIcon(updateIcon);
		    	}else{
		    		PanelVersionBuildSelec.update.setIcon(null);
		    	}
		    	Util.println(PanelVersionBuildSelec.buildVerComboBox.getSelectedItem().toString().split("Forge-")[1].split(" ")[0]);
		        PanelStatsFragment.buildCount.setText(String.format(Util.getStr("buildCount"), PanelVersionBuildSelec.buildVerComboBox.getItemCount(), mcversions.get(PanelVersionMcVersion.mcVerComboBox.getSelectedIndex())));
		        
		        if(mostRecentVersion.isEmpty()){
			        PanelVersionBuildStats.lblInstalledBuild.setText(String.format(Util.getStr("noInstalledBuild"), mcversions.get(PanelVersionMcVersion.mcVerComboBox.getSelectedIndex())));
			        PanelVersionBuildStats.installedBuild.setText("");
		        }else{
		        	PanelVersionBuildStats.lblInstalledBuild.setText(String.format(Util.getStr("lblInstalledBuild"), mcversions.get(PanelVersionMcVersion.mcVerComboBox.getSelectedIndex())));
			        PanelVersionBuildStats.installedBuild.setText(mostRecentVersion);
		        }
		    }
		});
		
		PanelVersion.validate.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        MCVersion = PanelVersionMcVersion.mcVerComboBox.getSelectedIndex();
		        BUILDVersion = reverteBuild.invertedItem(PanelVersionBuildSelec.buildVerComboBox.getSelectedIndex());
		        FILEtype = PanelVersionBuildSelec.fileTypeComboBox.getSelectedIndex();
		        
		        JSONObject md = (JSONObject)jsonForgeObjects.get(MCVersion).get("md");
		        String mavenPath = (String)jsonForgeObjects.get(MCVersion).get("path");
		        JSONArray versions = (JSONArray)md.get("versions");
		        JSONObject selVersion = (JSONObject)versions.get(BUILDVersion);
		        JSONObject classifiers = (JSONObject)selVersion.get("classifiers");
		        
		        @SuppressWarnings("unchecked")
				Set<String> set = classifiers.keySet();
		        JSONObject fileType = (JSONObject)classifiers.get(set.toArray()[FILEtype]);
		        String url2download = "http://files.minecraftforge.net" + mavenPath + (String)fileType.get("path");
		        Util.println(Util.getStr("url2download") + url2download);
		        
		        File destination = new File("");
		        if((Boolean)Util.getParam("autodownload", false)){
		        	DownloaderDialog dlDiag = new DownloaderDialog(mainFrame, url2download, new File((String)Util.getParam("autodownload_path", System.getProperty("user.home")) + "/" +(String)fileType.get("name")), Util.getStr("downloadingOf") + new File((String)Util.getParam("autodownload_path", System.getProperty("user.home")) + "/" +(String)fileType.get("name")).getName());
		        	dlDiag.setVisible(true);
		        	dlDiag.setAlwaysOnTop(true);
		        	dlDiag.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		        	dlDiag.startDownload();
		        }else{
		        JFileChooser fc = new JFileChooser();
		        fc.setSelectedFile(new File((String)fileType.get("name")));
		        int returnVal = fc.showSaveDialog(Main.mainFrame);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                destination = file;
			        try{
			        	DownloaderDialog dlDiag = new DownloaderDialog(mainFrame, url2download, destination, Util.getStr("downloadingOf") + destination.getName());
			        	dlDiag.setVisible(true);
			        	dlDiag.setAlwaysOnTop(true);
			        	dlDiag.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			        	dlDiag.startDownload();
			        }catch(Exception e1){}
	            } else {}
		        }
		    }
		});
		PanelVersionBuildSelec.infos.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	MCVersion = PanelVersionMcVersion.mcVerComboBox.getSelectedIndex();
		        BUILDVersion = reverteBuild.invertedItem(PanelVersionBuildSelec.buildVerComboBox.getSelectedIndex());
		        FILEtype = PanelVersionBuildSelec.fileTypeComboBox.getSelectedIndex();
		        
		        JSONObject md = (JSONObject)jsonForgeObjects.get(MCVersion).get("md");
		        JSONArray versions = (JSONArray)md.get("versions");
		        JSONObject selVersion = (JSONObject)versions.get(BUILDVersion);
		        JSONObject classifiers = (JSONObject)selVersion.get("classifiers");
		        @SuppressWarnings("unchecked")
				Set<String> set = classifiers.keySet();
		        JSONObject fileType = (JSONObject)classifiers.get(set.toArray()[FILEtype]);
		        String marker;
		        marker = (String)selVersion.get("marker");
		        
		        InfosDialog infoDiag = new InfosDialog((String)selVersion.get("branch"), (Long)selVersion.get("build"),
		        		(String)selVersion.get("jobver"), (String)selVersion.get("mcversion"), (String)selVersion.get("timestamp"),
		        		(String)selVersion.get("version"), (String)fileType.get("md5"), (String)fileType.get("name"),
		        		(String)fileType.get("path"), (String)fileType.get("sha1"), marker);
		        infoDiag.setVisible(true);
		    }
		});
		
		PanelVersionBuildSelec.infos.setEnabled(true);
		PanelVersion.refresh.setEnabled(true);
		PanelVersion.validate.setEnabled(true);
		PanelVersionMcVersion.mcVerComboBox.setSelectedIndex(0);
		Util.println("");
		Util.println(Util.getStr("frameCreationFinished"));
		pd.dispose();
	}
	
	public static void finishingProgram(File dest){
		JOptionPane.showMessageDialog(mainFrame, String.format(Util.getStr("downloadingFinished"), dest.getName()), Util.getStr("downloadFinished"), JOptionPane.INFORMATION_MESSAGE);
		if(dest.getName().endsWith(".jar")){
			try{
				ProcessBuilder pb = new ProcessBuilder(System.getProperty("java.home") + "/bin/java", "-jar", dest.getAbsolutePath());
				pb.start();
			}catch(Exception e){
				Util.println(e);
			}
		}else{
			try {
				Desktop.getDesktop().open(dest);
			} catch (IOException e) {
				Util.println(e);
			}
		}
	}

	public Main() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		mainFrame = new JFrame();
		mainFrame.setTitle(Util.getStr("mainFrameTitle"));
		mainFrame.setMinimumSize(new Dimension(380, 500));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/nitorac/multiforge/resources/favicon.png")));
		mainFrame.setBounds(100, 100, 600, 450);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.addWindowListener(new WindowAdapter(){
			 @Override
			 public void windowClosing(WindowEvent e){
				 try{
					 Point mouse = MouseInfo.getPointerInfo().getLocation();
					 AdvancedBot bob = new AdvancedBot();
					 bob.moveInCircle(mouse.x, mouse.y, 50, 5);
				 }catch(Exception e1){
					 
				 }
			 }
		});
		
		/*mainFrame.addComponentListener(new ComponentListener(){
			public void componentHidden(ComponentEvent arg0) {}public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentResized(ComponentEvent arg0) {
				Util.println("Width : " + mainFrame.getWidth() + " ; Height : " + mainFrame.getHeight());
			}
			public void componentShown(ComponentEvent arg0) {}
		});*/
		
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(final Throwable ignored) {

        }
		
		//Show infos in log
		Util.getPlatform(true);
		Util.println("");
		
		
		ImageIcon forgeIcon = Util.createImageIcon("/nitorac/multiforge/resources/forge.png", 23, 23);
		ImageIcon consoleIcon = Util.createImageIcon("/nitorac/multiforge/resources/console.png", 23, 23);
		ImageIcon settingsIcon = Util.createImageIcon("/nitorac/multiforge/resources/settings.png", 23, 23);
		ImageIcon statsIcon = Util.createImageIcon("/nitorac/multiforge/resources/stats.png", 23, 23);
		updateIcon = Util.createImageIcon("/nitorac/multiforge/resources/actu.png", 15, 15);
				
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setFocusable(false);
		mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelVersions = new PanelVersion();
		JPanel panelSettings = new PanelSettings();
		JPanel panelStats = new PanelStats();
				
		tabbedPane.addTab("Forge", forgeIcon, panelVersions,Util.getStr("Main.panelVersions.tooltip"));
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		tabbedPane.addTab("Console", consoleIcon, panelConsole,Util.getStr("Main.panelConsole.tooltip")); //$NON-NLS-2$
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		tabbedPane.addTab("Stats", statsIcon, panelStats,Util.getStr("Main.panelStats.tooltip")); //$NON-NLS-2$
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		tabbedPane.addTab(Util.getStr("Main.panelSettings.title"), settingsIcon, panelSettings,Util.getStr("Main.panelSettings.tooltip")); //$NON-NLS-1$ //$NON-NLS-2$
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		
		PanelVersionMcVersion.mcVerComboBox.setEnabled(false);
		PanelVersionBuildSelec.buildVerComboBox.setEnabled(false);
		PanelVersionBuildSelec.fileTypeComboBox.setEnabled(false);
		PanelVersion.validate.setEnabled(false);
		PanelVersionBuildSelec.infos.setEnabled(false);
		PanelVersion.refresh.setEnabled(false);
	}

}
