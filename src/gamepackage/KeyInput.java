package gamepackage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	private static int LeftRightCheck;
	private static int UpDownCheck;
	public KeyInput() {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A)
			LeftRightCheck=2;
		else if(key == KeyEvent.VK_D)
			LeftRightCheck=1;
		else if(key == KeyEvent.VK_W)
			UpDownCheck=1;
		else if(key == KeyEvent.VK_S)
			UpDownCheck=-1;			
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_D){
			LeftRightCheck=0;
		}
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_S){
			UpDownCheck=2;
		}
		
	}
	
	public static int getLeftRightCheck() {
		return LeftRightCheck;
	}
	
	public static int getUpDownCheck() {
		return UpDownCheck;
	}

}
