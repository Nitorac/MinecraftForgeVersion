package nitorac.multiforge.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ProgressDLDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6834101335781264529L;
	
	private final JPanel contentPanel = new JPanel();
	private final JLabel description = new JLabel();
	private final JLabel progress = new JLabel();
	/**
	 * Create the dialog.
	 */
	public ProgressDLDialog(JFrame frm, String title, String desc, String baseProg) {
		super(frm, title, false);
		description.setText(desc);
		progress.setFont(new Font("", Font.PLAIN, 9));
		progress.setText(baseProg);
		setBounds(200, 200, 345, 110);
		getContentPane().setLayout(new BorderLayout());
		GridLayout gl_contentPanel = new GridLayout();
		gl_contentPanel.setColumns(1);
		gl_contentPanel.setRows(2);
		contentPanel.setLayout(gl_contentPanel);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		//setUndecorated(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
	    removeCloseButton(this);
	    URL url = Main.class.getResource("/nitorac/multiforge/resources/ajax-loader.gif");
	    ImageIcon imageIcon = new ImageIcon(url);
	    JLabel labelLoading = new JLabel(imageIcon);
	    
	    JPanel staticProgress = new JPanel();
	    staticProgress.setLayout(new FlowLayout());
	    
	    JPanel dynamicProgress = new JPanel();
	    dynamicProgress.setLayout(new FlowLayout());
	    
	    staticProgress.add(labelLoading);
	    staticProgress.add(description);
	    
	    dynamicProgress.add(progress);
	    
	    contentPanel.add(staticProgress);
	    contentPanel.add(dynamicProgress);
	    this.pack();
	    this.setMinimumSize(new Dimension(getWidth(), getHeight()));
	}
	
	public void setDesc(String desc){
		this.description.setText(desc);
	}
	
	public void setProgress(String progress){
		this.progress.setText(progress);
	}
	
	public void removeCloseButton(Component comp) {
	    if (comp instanceof JMenu) {
	      Component[] children = ((JMenu) comp).getMenuComponents();
	      for (int i = 0; i < children.length; ++i)
	        removeCloseButton(children[i]);
	    }
	    else if (comp instanceof AbstractButton) {
	      Action action = ((AbstractButton) comp).getAction();
	      String cmd = (action == null) ? "" : action.toString();
	      if (cmd.contains("CloseAction")) {
	        comp.getParent().remove(comp);
	      }
	    }
	    else if (comp instanceof Container) {
	      Component[] children = ((Container) comp).getComponents();
	      for (int i = 0; i < children.length; ++i)
	        removeCloseButton(children[i]);
	    }
	  }

}
