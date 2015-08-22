package nitorac.multiforge.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nitorac.multiforge.main.Util;

public class PanelVide extends JPanel {

	private static final long serialVersionUID = -1863518066300519361L;

	public PanelVide(int jLabelnum){
		setLayout(new GridBagLayout());
		for(int i = 0;i<jLabelnum;i++){
			GridBagConstraints constraints = new GridBagConstraints();
			constraints = Util.defConstraints();
			constraints.anchor = GridBagConstraints.NORTH;
	        constraints.gridy = i;
	        constraints.fill = 2;
	        
			add(new JLabel(" "), constraints);
		}
	}

}
