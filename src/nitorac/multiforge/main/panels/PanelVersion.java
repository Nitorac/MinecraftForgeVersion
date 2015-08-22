package nitorac.multiforge.main.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import nitorac.multiforge.main.Util;

public class PanelVersion extends JPanel {

	private static final long serialVersionUID = -5848564443710314547L;
	public static JButton validate = new JButton(Util.getStr("Main.validate.text"));
	public static JButton refresh = new JButton(Util.createImageIcon("/nitorac/multiforge/resources/refresh.png", 16, 16));

	public PanelVersion() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 5));
        
        final JPanel standardPanels = new JPanel(true);
        PanelVersionMcVersion panelVersionMcVersion = new PanelVersionMcVersion();
        PanelVersionBuildStats panelVersionBuildStats = new PanelVersionBuildStats();
        PanelVersionBuildSelec panelVersionBuildSelec = new PanelVersionBuildSelec();
        standardPanels.setLayout(new BoxLayout(standardPanels, BoxLayout.PAGE_AXIS));
        standardPanels.add(panelVersionMcVersion);
        standardPanels.add(panelVersionBuildStats);
        standardPanels.add(panelVersionBuildSelec);
        
        final JPanel buttonPannel = new JPanel();
        buttonPannel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints = Util.defConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        buttonPannel.add(validate, constraints);
        
        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1 = Util.defConstraints();
        constraints1.gridy = 0;
        constraints1.gridx = 2;
        buttonPannel.add(refresh, constraints1);
        
        add(buttonPannel, "South");
        add(standardPanels, "Center");
        defineObjects();
	}

	private void defineObjects() {
		refresh.setToolTipText(Util.getStr("refresh"));
		
		refresh.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Util.restartApp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
