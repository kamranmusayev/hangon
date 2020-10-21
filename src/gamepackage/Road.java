package gamepackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.ImageIcon;

public class Road extends Background{
	
	private static final long serialVersionUID = 4768491496639015177L;
	protected static long points=0;
	private Game game;
	private Player player;
	public Road(ImageIcon img, int width, int height,Player player, Game game) {
		super(img, width, height);
		this.player=player;
		this.game=game;
	}
	
	@Override
	public void run() {
		road(player,game);
		stop();
	}
	
	public void road(Player player, Game game) {
		points=0;
		int leftsize=0,pointsize=0, nextpointsize=0, rightsize=0, finishsize=0,straightsize=0;
		while(flag==0 && !gameFinished) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			while(!pauseflag) {
				try {
					setLocation(horizontalLoc,193);
					Thread.sleep((long) (500/ player.getSpeed()));

					if(player.getDistance()>5 && leftsize<82) {
						turn=-2;
						Thread.sleep((long) (330-player.getSpeed()));
						ImageIcon img= new ImageIcon("imgs/leftTurn("+leftsize%82+").png");
						setIcon(img);
						horizontalLoc-=10;
						leftsize++;
					}
							
					else if(player.getDistance()>15 && pointsize<5) {
						turn=0;
						Thread.sleep((long) (330-player.getSpeed()));
						ImageIcon img= new ImageIcon("imgs/roadpoint("+pointsize%5+").png");
						setIcon(img);
								
						pointsize++;
						if(pointsize==5) {
							player.setTime(player.getTime() + 60);
						}
							
					}
					else if(player.getDistance()>30 && nextpointsize<5) {
						turn=0;
						Thread.sleep((long) (330-player.getSpeed()));
						ImageIcon img= new ImageIcon("imgs/roadpoint("+nextpointsize%5+").png");
						setIcon(img);
						nextpointsize++;
						if(nextpointsize==5) {
							player.setTime(player.getTime() + 60);
						}	
					}		
					else if(player.getDistance()>20 && rightsize<82) {
						turn=2;
						Thread.sleep((long) (330-player.getSpeed()));
						ImageIcon img= new ImageIcon("imgs/rightTurn("+rightsize%82+").png");
						setIcon(img);
						horizontalLoc+=10;
						rightsize++;
					}
					else if(player.getDistance()>35 && finishsize<5) {
						turn=0;
						Thread.sleep((long) (340-player.getSpeed()));
						ImageIcon img= new ImageIcon("imgs/roadfinish("+finishsize%5+").png");
						setIcon(img);
						horizontalLoc+=10;
						finishsize++;
					}
					else {
						turn=0;
						ImageIcon img= new ImageIcon("imgs/road("+straightsize%5+").png");
						setIcon(img);
					}
								
					if(finishsize==5) {
						Random randomGenerator = new Random();
						points=(long) (player.getDistance()+(player.getSpeed()*(randomGenerator.nextInt(25)+1)));
						FileWriter fr = null;
						try {
							fr = new FileWriter(scores, true);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						BufferedWriter br = new BufferedWriter(fr);
						PrintWriter pr = new PrintWriter(br);
										
						pr.println(Window.getLoggedinUser() + ": " + Road.points);
						pr.close();
						try {
							br.close();
							fr.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
										
						gameFinished=true;
						pauseflag=true;
								
					}
					straightsize++;
					
					if(KeyInput.getLeftRightCheck()==1) 
						horizontalLoc-=20;
					else if(KeyInput.getLeftRightCheck()==2)	
						horizontalLoc+=20;
					
					if(horizontalLoc < -1930 ) {	
						setFlag(1);		
						setPauseflag(true);
						Thread.sleep(3000);
						horizontalLoc=-1455;
						game.start();
								
					}
					else if(horizontalLoc > -1030 ) {	
						setFlag(-1);
						setPauseflag(true);
						Thread.sleep(3000);
						horizontalLoc=-1455;
						game.start();
								
					}		
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
	}	
}
