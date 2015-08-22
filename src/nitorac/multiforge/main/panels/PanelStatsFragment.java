package nitorac.multiforge.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nitorac.multiforge.main.Util;

public class PanelStatsFragment extends JPanel {

	private static final long serialVersionUID = 6687306562208591588L;
	
	public static JLabel timeInternetJLabel = new JLabel(Util.getStr("internetTime").replace("%d", "..."));
	public static JLabel filesDL = new JLabel(String.format(Util.getStr("jsonFiles").replace("%d", "...")));
	public static JLabel buildCount = new JLabel(Util.getStr("buildCount").replace("%d", "...").replace("%s", "..."));

	public PanelStatsFragment() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder());
        
        createPanel();
	}
	
	public void createPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints = Util.defConstraints();
		constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridy = 0;
        constraints.fill = 2;
        
        add(timeInternetJLabel, constraints);
        
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1 = Util.defConstraints();
		constraints1.anchor = GridBagConstraints.NORTH;
        constraints1.gridy = 1;
        constraints1.fill = 2;
        
        add(filesDL, constraints1);
        
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2 = Util.defConstraints();
		constraints2.anchor = GridBagConstraints.NORTH;
        constraints2.gridy = 2;
        constraints2.fill = 2;
        
        add(buildCount, constraints2);
	}

}
