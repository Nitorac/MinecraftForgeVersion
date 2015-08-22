package nitorac.multiforge.main;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_NUMPAD0;

public class AdvancedBot extends Robot{

	public AdvancedBot() throws AWTException {
		super();
	}

	public void smoothMouseMove(int sx, int sy, int ex, int ey, int speed) throws Exception{
		for(int i = 0;i < 100;i++){
			int mov_x = ((ex * i)/100) + (sx * (100-i)/100);
			int mov_y = ((ey * i)/100) + (sy * (100-i)/100);
			mouseMove(mov_x, mov_y);
			delay(speed);
		}
	}
	
	public void moveInCircle(int sx, int sy, int rayon, int speed){
		Point center = new Point(sx + rayon, sy);
		Point left = new Point(sx, center.y);

		for(int i = 0; i<i+1; i++){
			double x_member = (left.x - center.x)*(left.x - center.x);
			
			double rayon2 = rayon*rayon;
			
			int calculated_y = (int)-(Math.sqrt(rayon2 - x_member)) + center.y;
			
			if(i!=0 && calculated_y == center.y){
				break;
			}
			
			left.setLocation(left.x + 1, calculated_y);
			
			mouseMove(left.x, left.y);
			
			delay(speed);
		}
		
		System.out.println("CHANGEMENT");
		
		for(int i = left.x; i>(center.x - rayon)-1; i--){
			double x_member = (left.x - center.x)*(left.x - center.x);
			
			double rayon2 = rayon*rayon;
			
			int calculated_y = (int)Math.sqrt(rayon2 - x_member) + center.y;
			
			left.setLocation(left.x - 1, calculated_y);
			
			mouseMove(left.x, left.y);
			
			delay(speed);
		}
	}
	
	   public void type(String cs){
	        for(int i=0;i<cs.length();i++){
	            type(cs.charAt(i));
	        }
	    }

	    public void type(char c){
	        keyPress(VK_ALT);
	        keyPress(VK_NUMPAD0);
	        keyRelease(VK_NUMPAD0);
	        String altCode=Integer.toString(c);
	        for(int i=0;i<altCode.length();i++){
	            c = (char)(altCode.charAt(i)+'0');
	            delay(20);
	            keyPress(c);
	            delay(20);
	            keyRelease(c);
	        }
	        keyRelease(VK_ALT);
	    }
}
