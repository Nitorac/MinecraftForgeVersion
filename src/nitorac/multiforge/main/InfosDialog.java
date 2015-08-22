package nitorac.multiforge.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class InfosDialog extends JDialog {

	private static final long serialVersionUID = 6792934226900257103L;
	
	private ArrayList<String> labelsValues = new ArrayList<String>();
	private String marker = null;
	private boolean hasMarker;
	
	public InfosDialog(String branch, Long build, String jobver, String mcversion, String timestamp, String version, String md5, String name, String path, String sha1, String marker) {
		super();
		this.labelsValues.add(Util.getStr("branch") + branch);
		this.labelsValues.add(Util.getStr("build") + String.valueOf(build));
		this.labelsValues.add(Util.getStr("jobver") + jobver);
		this.labelsValues.add(Util.getStr("mcversion") + mcversion);
		this.labelsValues.add(Util.getStr("timestamp") + timestamp);
		this.labelsValues.add(Util.getStr("version") + version);
		this.labelsValues.add(Util.getStr("md5") + md5);
		this.labelsValues.add(Util.getStr("name") + name);
		this.labelsValues.add(Util.getStr("path") + path);
		this.labelsValues.add(Util.getStr("sha1") + sha1);
		this.marker = marker;
		
        try{
        	marker.isEmpty();
        	hasMarker = true;
        }catch(Exception e1){
        	hasMarker = false;
        }
		
		setTitle(Util.getStr("propriete"));
		setBounds(150,150,2,2);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new GridLayout(20, 1, 0, 0));
		//setUndecorated(true);
	    setDefaultLookAndFeelDecorated(true);
	    
	    ArrayList<JPanel> jPanels = new ArrayList<JPanel>();
	    ArrayList<JLabel> jLabelsValues = new ArrayList<JLabel>();

	    //Creating JPanels with layout
	    for(int i = 0;i<labelsValues.size();i++){
	    	jPanels.add(new JPanel(new FlowLayout()));
	    }
	  
	    //Creating JLabels
	    for(int i = 0;i<labelsValues.size();i++){
	    	jLabelsValues.add(new JLabel(labelsValues.get(i)));
	    }
	    
	    //Adding labels to respective panels
	    for(int i = 0;i<labelsValues.size();i++){
	    	jPanels.get(i).add(jLabelsValues.get(i));
	    }
	    
		getContentPane().add(new JPanel());
		JLabel title = new JLabel(Util.getStr("global"));
		Font font = new Font("", 1, 12);
		title.setFont(font);
		
		JPanel jpane = new JPanel();
		jpane.setLayout(new FlowLayout());
		jpane.add(title);

		getContentPane().add(jpane);
		getContentPane().add(new JSeparator());
	    
	    //Adding panels to contentPane
	    for(int i = 0;i<labelsValues.size();i++){	    	
	    	if(i == 6){		    	
		    	if(hasMarker){
		    		JPanel markerPanel = new JPanel(new FlowLayout());
			    	markerPanel.add(new JLabel(Util.getStr("marker") + this.marker));
			    	getContentPane().add(markerPanel);
		    	}
		    	
	    		getContentPane().add(new JPanel());
	    		JLabel title2 = new JLabel(Util.getStr("relative2file"));
	    		title2.setFont(font);
	    		
	    		JPanel jpane2 = new JPanel();
	    		jpane2.setLayout(new FlowLayout());
	    		jpane2.add(title2);

	    		getContentPane().add(jpane2);
	    		getContentPane().add(new JSeparator());
	    	}
	    	getContentPane().add(jPanels.get(i));
	    }
	    this.pack();
	    setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
	}

}
