package nitorac.multiforge.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONGetter {	
	public JSONObject returnObj;
	
	public String jsonReader(final String indexUrl){
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(new Callable<String>(){
			@Override
		    public String call() throws Exception {
		    	 String returnStr = "";
			        try {
			            URL url = new URL(indexUrl);
			            Util.println("Lecture du fichier : "+indexUrl);
			            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			            String str;
			            while ((str = in.readLine()) != null) {
			                returnStr += str;
			            }
			            in.close();
			        } catch (MalformedURLException e) {
			            Util.println(e);
			        } catch (IOException e) {
			        	Util.println(e);
			        }
			        return returnStr;
		    }
		});
		
		String str = "";
		try {
			str = future.get();
		} catch (InterruptedException e) {
			Util.println(e);
			JOptionPane.showMessageDialog(Main.mainFrame, "Execution interrompue", "Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (ExecutionException e) {
			Util.println(e);
			JOptionPane.showMessageDialog(Main.mainFrame, "Erreur d'execution", "Erreur", JOptionPane.ERROR_MESSAGE);
		}catch (Exception e){
			Util.println(e);
			JOptionPane.showMessageDialog(Main.mainFrame, "Erreur de récupération de valeur", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		executor.shutdown();
		return str;
	}
	
	public JSONObject jsonParser(String str){
		JSONParser jp = new JSONParser();
		try{
			return (JSONObject)jp.parse(str);
		}catch(Exception e){
			Util.println("Impossible de récupérer le fichier", e);
			return new JSONObject();
		}
	}
	
	
	public JSONObject jsonParser(FileReader in){
		JSONParser jp = new JSONParser();
		try{
			return (JSONObject)jp.parse(in);
		}catch(Exception e){
			Util.println("Impossible de récupérer le fichier", e);
			return new JSONObject();
		}
	}
}
