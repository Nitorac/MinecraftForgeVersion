package nitorac.multiforge.main.panels;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nitorac.multiforge.main.Util;

public class PanelVersionBuildSelec extends JPanel {

	private static final long serialVersionUID = -243600249066525758L;
	
	public static JComboBox buildVerComboBox = new JComboBox();
	public static JComboBox fileTypeComboBox = new JComboBox();
	public static JButton infos = new JButton(Util.createImageIcon("/nitorac/multiforge/resources/infos.png", 15, 15));
	
	public static JLabel lblBuildSel = new JLabel(Util.getStr("Main.lblBuildSel.text")); //$NON-NLS-1$
	public static JLabel lblFileType = new JLabel(Util.getStr("Main.lblFileType.text")); //$NON-NLS-1$
	public static JLabel update = new JLabel();

	public PanelVersionBuildSelec() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(Util.getStr("buildSelecTitle")));
        
        createPanel();
	}

	private void createPanel() {
		update.setToolTipText(Util.getStr("updateToolTip"));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints = Util.defConstraints();
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = 0;
        add(lblBuildSel, constraints);
        
        JPanel refreshAndVerComboBox = new JPanel(new FlowLayout());
        refreshAndVerComboBox.add(buildVerComboBox);
        refreshAndVerComboBox.add(update);
        
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2 = Util.defConstraints();
        constraints2.gridy = 1;
        constraints2.anchor = GridBagConstraints.CENTER;
        constraints2.fill = 0;
        add(refreshAndVerComboBox, constraints2);
        
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3 = Util.defConstraints();
        constraints3.gridy = 2;
        constraints3.anchor = GridBagConstraints.CENTER;
        constraints3.fill = 0;
        add(lblFileType, constraints3);
        
        JPanel selecFileType = new JPanel(new FlowLayout());
        selecFileType.add(fileTypeComboBox);
        selecFileType.add(infos);
        
		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4 = Util.defConstraints();
        constraints4.gridy = 3;
        constraints4.anchor = GridBagConstraints.CENTER;
        add(selecFileType, constraints4);
	}

}
