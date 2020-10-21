package gamepackage;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class Background extends JLabel implements Runnable{
	
	private static final long serialVersionUID = 4910577473506497784L;
	protected Thread thread;
	protected File  scores = new File("Scores.txt");
	protected static int horizontalLoc=-1455;
	protected boolean pauseflag=false;
	protected boolean gameFinished=false;
	protected int flag=0;
	protected int turn;
	
	public Background(ImageIcon img,int width,int height) {
		super();
		this.setIcon(img);
		this.setSize(width, height);
	}
	
	@Override
	public void run() {
		background();	
		stop();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void background() {
		int horizontalLoc = -1455;
		try {
			while(getFlag()==0 && !getGameFinished()) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				while(!getPauseFlag()) {
					Thread.sleep(10);
						
					if(KeyInput.getLeftRightCheck()==1) {
						
						Thread.sleep(50);
						horizontalLoc-=2;
						setLocation(horizontalLoc,0);
					}
					if(KeyInput.getLeftRightCheck()==2) {
							
						Thread.sleep(50);
						horizontalLoc+=2;
						setLocation(horizontalLoc,0);
					}
						
						horizontalLoc+=turn;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getGameFinished() {
		return gameFinished;
	}
	
	public void setPauseflag(boolean pauseflag) {
		this.pauseflag = pauseflag;
	}

	public int getFlag() {
		return flag;
	}
	
	public boolean getPauseFlag() {
		return pauseflag;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
