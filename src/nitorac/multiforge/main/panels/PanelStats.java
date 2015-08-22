package nitorac.multiforge.main.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelStats extends JPanel {

	private static final long serialVersionUID = 275350812526180815L;

	public PanelStats() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 5));
        
        final JPanel standardPanels = new JPanel(true);
        PanelStatsFragment panelStatsFragment = new PanelStatsFragment();
        standardPanels.add(panelStatsFragment);
        standardPanels.add(new PanelVide(4));
        
        add(standardPanels, "Center");
	}
}
