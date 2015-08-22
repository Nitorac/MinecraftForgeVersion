package nitorac.multiforge.main;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class PanelConsole extends JScrollPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4911806048332561296L;
	
	public final JTextPane console = new JTextPane();
	private static final Font defaultFont = new Font("", 0, 12);
    
	public PanelConsole() {
		console.setFont(defaultFont);
        console.setEditable(false);
        console.setMargin(null);
        console.setBackground(Color.WHITE);
        console.setForeground(Color.BLACK);

        setViewportView(console);
	}

    public void print(final String line) {
        if(!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    print(line);
                }
            });
            return;
        }
        
        final Document document = console.getDocument();
        final JScrollBar scrollBar = getVerticalScrollBar();
        boolean shouldScroll = false;

        if(getViewport().getView() == console)
            shouldScroll = scrollBar.getValue() + scrollBar.getSize().getHeight() + defaultFont.getSize() * 6 > scrollBar.getMaximum();
        try {
            document.insertString(document.getLength(), line, null);
        }
        catch(final BadLocationException localBadLocationException) {
        }
        if(shouldScroll)
            scrollBar.setValue(102101);
    }
    
}
