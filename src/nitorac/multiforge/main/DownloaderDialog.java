package nitorac.multiforge.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class DownloaderDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3677998512519176195L;
	
	
	private final JPanel contentPanel = new JPanel();
	
	private boolean stopDL = false;

	public DownloaderDialog(JFrame frame, final String adresse, final File dest, final String description) {
		super(frame, Util.getStr("download"),false);
		DownloaderDialogPanel.adresse = adresse;
		DownloaderDialogPanel.dest = dest;
		
		setBounds(100, 100, 400, 120);
		getContentPane().setLayout(new BorderLayout());
	    setDefaultLookAndFeelDecorated(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 5));
		contentPanel.add(new DownloaderDialogPanel());
		
		DownloaderDialogPanel.destLabel.setText(Util.getStr("downloadingOf") + dest.getName());
		
		DownloaderDialogPanel.bar.setPreferredSize(new Dimension(350, 20));
		DownloaderDialogPanel.bar.setForeground(SystemColor.textHighlight);
		DownloaderDialogPanel.bar.setStringPainted(true);
		DownloaderDialogPanel.bar.setString(Util.getStr("init"));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		this.pack();
	}
	
	public void cancelDownload(){
		stopDL = true;
		if(DownloaderDialogPanel.dest.exists()){
			DownloaderDialogPanel.dest.delete();
		}
	}
	
	public void startDownload()
	{
		DownloaderDialogPanel.bar.setIndeterminate(true);
	    new Thread()
	    {
	        @Override
	        public void run()
	        {
	            FileOutputStream fos = null;
	            BufferedReader reader = null;
	            
	            try
	            {
	                // Création de la connexion
	                URL url = new URL(DownloaderDialogPanel.adresse);
	                
	                URLConnection conn = url.openConnection();
	                Util.println(DownloaderDialogPanel.adresse);
	                
	                String FileType = conn.getContentType();
	                Util.println("FileType : " + FileType);
	                
	                int fileLength = conn.getContentLength();
	         
	                if (fileLength == -1){
	                	Util.println(new IOException("Fichier non valide."));
	                    throw new IOException("Fichier non valide.");
	                }else{
	                	DownloaderDialogPanel.bar.setMaximum(fileLength);
	                }
	                // Lecture de la réponse
	                
	                InputStream in = conn.getInputStream();
	                reader= new BufferedReader(new InputStreamReader(in));
	                
	                if (DownloaderDialogPanel.dest == null)
	                {
	                    String fileName = url.getFile();
	                    fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
	                    fos = new FileOutputStream(new File(fileName));
	                }
	                else
	                    fos  = new FileOutputStream(DownloaderDialogPanel.dest);
	                
	                byte[] buff = new byte[1024];

	                DownloaderDialogPanel.bar.setValue(0);
	                DownloaderDialogPanel.bar.setIndeterminate(false);
	                
	                int n;
	                while ((n=in.read(buff)) !=-1 && stopDL == false)
	                {
	                    fos.write(buff, 0, n);
	                    Float calcul = (float) 100*((float)DownloaderDialogPanel.bar.getValue()/(float)DownloaderDialogPanel.bar.getMaximum());
	                    DownloaderDialogPanel.bar.setString(calcul.intValue() + " %");
	                    DownloaderDialogPanel.bar.setValue(DownloaderDialogPanel.bar.getValue()+n);
	                    DownloaderDialogPanel.downloadStatus.setText(String.format(Util.getStr("downloadStatus"), ((float)DownloaderDialogPanel.bar.getValue()/1000000), ((float)DownloaderDialogPanel.bar.getMaximum()/1000000)));
	                }
	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	            }
	            finally
	            {
	                try
	                {
	                    fos.flush();
	                    fos.close();
	                }
	                catch (IOException e)
	                {
	                	Util.println(e);
	                    e.printStackTrace();
	                }
	                
	                try
	                {
	                    reader.close();
	                }
	                catch (Exception e)
	                {
	                	Util.println(e);
	                    e.printStackTrace();
	                }
	                DownloaderDialog.this.dispose();
	                Main.finishingProgram(DownloaderDialogPanel.dest);
	            }
	        }
	    }.start();
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
