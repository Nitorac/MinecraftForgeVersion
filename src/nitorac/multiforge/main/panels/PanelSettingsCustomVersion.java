package nitorac.multiforge.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nitorac.multiforge.main.Main;
import nitorac.multiforge.main.Util;

public class PanelSettingsCustomVersion extends JPanel {
	
	public static JTextField customVersionPath = new JTextField((String)Util.getParam("customversion_path", new File(Util.getMinecraftDirectory() + "/versions").getAbsolutePath()));
	public static JCheckBox changeVersionFolder = new JCheckBox(Util.getStr("customVersionCheckBoxTitle"));
	public static JButton browse = new JButton(Util.getStr("browse"));
 
	private static final long serialVersionUID = -1732272269370430931L;

	public PanelSettingsCustomVersion() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(Util.getStr("customVersionTitle")));
        createPanel();
        defineObjects();
	}

	private void createPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints = Util.defConstraints();
        constraints.gridy = 0;
        
        add(changeVersionFolder, constraints);
        
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1 = Util.defConstraints();
        constraints1.gridy = 1;
        constraints1.fill = 2;
        constraints1.weightx = 1.0D;
        add(customVersionPath, constraints1);
        
        GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2 = Util.defConstraints();
        constraints2.gridy = 1;
        add(browse, constraints2);
	}

	private void defineObjects() {
		changeVersionFolder.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	PanelSettings.addWarningMessage();
		    	if(changeVersionFolder.isSelected()){
		    		customVersionPath.setEnabled(true);
		    		browse.setEnabled(true);
		    		Util.putParam("customversion", true);
		    		if(!Util.isCorrectDirectory(customVersionPath.getText())){
		    			JOptionPane.showMessageDialog(Main.mainFrame, Util.getStr("wrongDirMsg"), Util.getStr("error"), JOptionPane.WARNING_MESSAGE);
		    			Util.removeParam("customversion_path");
		    			customVersionPath.setText((String)Util.getParam("customversion_path", new File(Util.getMinecraftDirectory() + "/versions").getAbsolutePath()));
		    		}else{
		    			Util.putParam("customversion_path", customVersionPath.getText());
		    		}
		    	}else{
		    		customVersionPath.setEnabled(false);
		    		browse.setEnabled(false);
		    		Util.putParam("customversion", false);
		    	}
		    }
		});

		customVersionPath.addFocusListener(new FocusListener() {
		      public void focusLost(FocusEvent e) {
		    	  if(!Util.isCorrectDirectory(customVersionPath.getText())){
		    			JOptionPane.showMessageDialog(Main.mainFrame, Util.getStr("wrongDirMsg"), Util.getStr("error"), JOptionPane.WARNING_MESSAGE);
		    			Util.removeParam("customversion_path");
		    			customVersionPath.setText((String)Util.getParam("customversion_path", new File(Util.getMinecraftDirectory() + "/versions").getAbsolutePath()));
		    		}else{
		    			Util.putParam("customversion_path", customVersionPath.getText());
		    		}
		      }
			public void focusGained(FocusEvent arg0) {PanelSettings.addWarningMessage();}
		});
		
		browse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelSettings.addWarningMessage();
				customVersionPath.setText(Util.showFolderChooser(Main.mainFrame, Util.getStr("folderChooserTitle")));
			}
		});
						
		if((Boolean)Util.getParam("customversion", false) == true){
			changeVersionFolder.setSelected(true);customVersionPath.setEnabled(true);browse.setEnabled(true);
		}else{changeVersionFolder.setSelected(false);customVersionPath.setEnabled(false);browse.setEnabled(false);}
	}
}
