package nitorac.multiforge.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nitorac.multiforge.main.Main;
import nitorac.multiforge.main.Util;

public class PanelSettingsDL extends JPanel {

	private static final long serialVersionUID = -4883401730661198167L;
	/**
	 * Create the panel.
	 */
	public static JCheckBox enableAuto = new JCheckBox(Util.getStr("enableAutoDownloadCheckBoxTxt"));
	public static JTextField pathChoosen = new JTextField((String)Util.getParam("autodownload_path", System.getProperty("user.home")));
	public static JButton showFolderChooser = new JButton(Util.getStr("browse"));
	
	public PanelSettingsDL() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(Util.getStr("autodownloadBorder")));
        createPanel();
        defineObjects();
	}
	
	public void createPanel(){
		GridBagConstraints constraints = new GridBagConstraints();
		constraints = Util.defConstraints();
        constraints.gridy = 0;
        
        add(enableAuto, constraints);
        
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1 = Util.defConstraints();
        constraints1.gridy = 1;
        constraints1.fill = 2;
        constraints1.weightx = 1.0D;
        add(pathChoosen, constraints1);
        
        GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2 = Util.defConstraints();
        constraints2.gridy = 1;
        
        add(showFolderChooser, constraints2);
	}
	
	public void defineObjects(){
		enableAuto.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(enableAuto.isSelected()){
		    		pathChoosen.setEnabled(true);
		    		showFolderChooser.setEnabled(true);
		    		Util.putParam("autodownload", true);
		    		if(!Util.isCorrectDirectory(pathChoosen.getText())){
		    			JOptionPane.showMessageDialog(Main.mainFrame, Util.getStr("wrongDirMsg"), Util.getStr("error"), JOptionPane.WARNING_MESSAGE);
		    			Util.removeParam("autodownload_path");
		    			pathChoosen.setText((String)Util.getParam("autodownload_path", System.getProperty("user.home")));
		    		}else{
		    			Util.putParam("autodownload_path", pathChoosen.getText());
		    		}
		    	}else{
		    		pathChoosen.setEnabled(false);
		    		showFolderChooser.setEnabled(false);
		    		Util.putParam("autodownload", false);
		    	}
		    }
		});

		pathChoosen.addFocusListener(new FocusListener() {
		      public void focusLost(FocusEvent e) {
		    	  if(!Util.isCorrectDirectory(pathChoosen.getText())){
		    			JOptionPane.showMessageDialog(Main.mainFrame, Util.getStr("wrongDirMsg"), Util.getStr("error"), JOptionPane.WARNING_MESSAGE);
		    			Util.removeParam("autodownload_path");
		    			pathChoosen.setText((String)Util.getParam("autodownload_path", System.getProperty("user.home")));
		    		}else{
		    			Util.putParam("autodownload_path", pathChoosen.getText());
		    		}
		      }
			public void focusGained(FocusEvent arg0) {}
		});
		
		showFolderChooser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pathChoosen.setText(Util.showFolderChooser(Main.mainFrame, Util.getStr("folderChooserTitle")));
			}
		});
				
		if((Boolean)Util.getParam("autodownload", false) == true){
			enableAuto.setSelected(true);pathChoosen.setEnabled(true);
		}else{enableAuto.setSelected(false);pathChoosen.setEnabled(false);}
	}

}
