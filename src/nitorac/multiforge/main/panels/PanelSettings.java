package nitorac.multiforge.main.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import nitorac.multiforge.main.Util;

public class PanelSettings extends JPanel {

	private static final long serialVersionUID = 275350812526180715L;
	
	public static JLabel warningIcon = new JLabel();
	public static JLabel warningMsg = new JLabel();
	private static Font font = new Font("", 0, 9);
	
	public static JButton restart = new JButton();
	public static ImageIcon warningImageIcon = Util.createImageIcon("/nitorac/multiforge/resources/warning.png", 10, 10);

	public PanelSettings() {
        warningMsg.setFont(font);
		setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 5));
        
        final JPanel standardPanels = new JPanel(true);
        standardPanels.setLayout(new BoxLayout(standardPanels, 1));
        standardPanels.add(new PanelSettingsDL());
        standardPanels.add(new PanelSettingsCustomVersion());
        
        add(standardPanels, "Center");
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                PanelSettingsDL.pathChoosen.setFocusable(false);
                PanelSettingsDL.pathChoosen.setFocusable(true);
            }
        });
        
        final JPanel warningPanel = new JPanel(new FlowLayout());
        warningPanel.add(warningIcon);
        warningPanel.add(warningMsg);
        warningPanel.add(restart);
        restart.setVisible(false);
        add(warningPanel, "South");
	}
	
	public static void addWarningMessage(){
		warningIcon.setIcon(PanelSettings.warningImageIcon);
		warningMsg.setText(Util.getStr("rebootNeeded"));
		restart.setText(Util.getStr("restartButton"));
		restart.setVisible(true);
		
		restart.addActionListener(restartAction);
	}
	
	public static ActionListener restartAction = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
					Util.restartApp();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
	};
}
