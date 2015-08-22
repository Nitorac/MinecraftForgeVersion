package nitorac.multiforge.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class DownloaderDialogPanel extends JPanel {
	
	private static final long serialVersionUID = -4723281787508025746L;
	
	public static JLabel downloadStatus = new JLabel(Util.getStr("init"));
	public static JLabel destLabel = new JLabel();
	public static String adresse;
	public static File dest;
	public static JProgressBar bar = new JProgressBar();
	
	/**
	 * Create the panel.
	 */
	public DownloaderDialogPanel() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder());
        
        createPanel();
	}

	private void createPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints = Util.defConstraints();
        constraints.gridy = 0;
        constraints.fill = 2;
        constraints.weightx = 1.0D;
		add(bar, constraints);
		
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2 = Util.defConstraints();
        constraints2.gridy = 1;
        constraints2.anchor = GridBagConstraints.CENTER;
        constraints2.fill = 0;
		add(downloadStatus, constraints2);
		
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3 = Util.defConstraints();
        constraints3.gridy = 2;
        constraints3.anchor = GridBagConstraints.CENTER;
		add(destLabel, constraints3);
	}

}
