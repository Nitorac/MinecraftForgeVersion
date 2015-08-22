package nitorac.multiforge.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nitorac.multiforge.main.Util;

public class PanelVersionMcVersion extends JPanel {

	private static final long serialVersionUID = -7576079560781619650L;
	
	public static JLabel lblSelectVer = new JLabel(Util.getStr("selectForgeVersion"));
	public static JComboBox mcVerComboBox = new JComboBox();


	public PanelVersionMcVersion() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(Util.getStr("mcVersionTitle")));
                
        createPanel();
	}
	
	
	public void createPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints = Util.defConstraints();
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = 0;
        
        add(lblSelectVer, constraints);
        
        GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2 = Util.defConstraints();
        constraints2.gridy = 1;
        constraints2.anchor = GridBagConstraints.CENTER;
        constraints2.weightx = 0.5D;
        
        add(mcVerComboBox, constraints2);
        
	}

}
