package nitorac.multiforge.main.panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import nitorac.multiforge.main.Util;

public class PanelVersionBuildStats extends JPanel {

	private static final long serialVersionUID = -5303660803874315676L;

	public static JLabel latestVersionBuild = new JLabel();
	public static JLabel lblLatestBuild = new JLabel();
	public static JLabel recommendedVersionBuild = new JLabel();
	public static JLabel lblRecommendedBuild = new JLabel();
	public static JLabel installedBuild = new JLabel();
	public static JLabel lblInstalledBuild = new JLabel();
	
	public PanelVersionBuildStats() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(Util.getStr("buildStatsTitle")));
        
        createPanel();
	}

	private void createPanel() {
		Font bold = new Font("", 1, 12);
		latestVersionBuild.setFont(bold);
		recommendedVersionBuild.setFont(bold);
		installedBuild.setFont(bold);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints = Util.defConstraints();
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = 0;
		add(lblLatestBuild, constraints);
		
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2 = Util.defConstraints();
        constraints2.gridy = 1;
        constraints2.anchor = GridBagConstraints.CENTER;
        constraints2.fill = 0;
		add(latestVersionBuild, constraints2);
		
		add(new JSeparator());
		
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3 = Util.defConstraints();
        constraints3.gridy = 3;
        constraints3.anchor = GridBagConstraints.CENTER;
        constraints3.fill = 0;
		add(lblRecommendedBuild, constraints3);
		
		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4 = Util.defConstraints();
        constraints4.gridy = 4;
        constraints4.anchor = GridBagConstraints.CENTER;
        constraints4.fill = 0;
		add(recommendedVersionBuild, constraints4);
		
		add(new JSeparator());
		
		GridBagConstraints constraints5 = new GridBagConstraints();
		constraints5 = Util.defConstraints();
        constraints5.gridy = 6;
        constraints5.anchor = GridBagConstraints.CENTER;
        constraints5.fill = 0;
		add(lblInstalledBuild, constraints5);
		
		GridBagConstraints constraints6 = new GridBagConstraints();
		constraints6 = Util.defConstraints();
        constraints6.gridy = 7;
        constraints6.anchor = GridBagConstraints.CENTER;
        constraints6.fill = 0;
		add(installedBuild, constraints6);
	}

}
