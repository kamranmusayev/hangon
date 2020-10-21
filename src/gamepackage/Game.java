package gamepackage;

import java.awt.Canvas;
import javax.swing.ImageIcon;

public class Game extends Canvas{
	
	public static final int WIDTH=900, HEIGHT = WIDTH/16*9;
	private static final long serialVersionUID = 5374908400966050628L;
	private String title;
	private Window maingame;
	private Player player;
	private Road road;
	private Background bg;
	public Game(String title) {
		this.title = title;
		maingame = new Window(WIDTH,HEIGHT,title,this);
	}
	
	public void start() {
		maingame.setgame(title);
		player = new Player();
		player.getSpeedLabel().setBounds(700, 0, 180, 24);
		player.getTimeLabel().setBounds(400,0,140,24);
		player.setLocation((WIDTH-player.getWidth())/2,(HEIGHT-player.getHeight())-15);
		player.getDistanceLabel().setBounds(0, 0, 250, 24);
		
		road = new Road(new ImageIcon("imgs/road(0).png"),3824,314,player,this);
		road.setLocation(-1455,193);

		bg = new Background(new ImageIcon("imgs/background.png"),3824,506);
		bg.setLocation(-1800,0);
		
		road.start();
		player.animation(road);
		player.speed(road);
		player.time(road, this);
		bg.start();
		maingame.start(road,bg,player);
	}
	
	
	
	
	public static void main (String[] args) {
		new Game("Hang-On");
	}

	
}
