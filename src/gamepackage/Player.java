package gamepackage;

import java.awt.Color;


import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Player extends JLabel{

	private static final long serialVersionUID = -9163231759831275570L;
	private int image_index=7;
	private final String[] images = {"imgs/motor_left_7.png","imgs/motor_left_6.png","imgs/motor_left_5.png","imgs/motor_left_4.png","imgs/motor_left_3.png","imgs/motor_left_2.png","imgs/motor_left_1.png","imgs/motor_center.png","imgs/motor_right_1.png","imgs/motor_right_2.png","imgs/motor_right_3.png","imgs/motor_right_4.png","imgs/motor_right_5.png","imgs/motor_right_6.png","imgs/motor_right_7.png"};
	private final String[] crashimages= {"imgs/motor_left_fall_11.png","imgs/motor_left_fall_10.png","imgs/motor_left_fall_9.png","imgs/motor_left_fall_8.png","imgs/motor_left_fall_7.png","imgs/motor_left_fall_6.png","imgs/motor_left_fall_5.png","imgs/motor_left_fall_4.png","imgs/motor_left_fall_3.png","imgs/motor_left_fall_2.png","imgs/motor_left_fall_1.png","imgs/motor_right_fall_1.png","imgs/motor_right_fall_2.png","imgs/motor_right_fall_3.png","imgs/motor_right_fall_4.png","imgs/motor_right_fall_5.png","imgs/motor_right_fall_6.png","imgs/motor_right_fall_7.png","imgs/motor_right_fall_8.png","imgs/motor_right_fall_9.png","imgs/motor_right_fall_10.png","imgs/motor_right_fall_11.png"};
	private ImageIcon Player_Image = new ImageIcon(images[image_index]);
	private float Speed=1;
	private int Time=30;
	private double Distance=0;
	
	private JLabel DistanceLabel = new JLabel("Traveled Distance " +Distance + " M");
	private JLabel SpeedLabel = new JLabel("Speed " + Speed + " KM/H");
	private JLabel TimeLabel = new JLabel("Time "+ Time);
	
	public Player() {
		super();
		this.setIcon(Player_Image);
		this.setSize(200, 200);
		
		DistanceLabel.setForeground(Color.green);
		DistanceLabel.setFont(new Font("Times Header", Font.BOLD, 16));
		
		SpeedLabel.setForeground(Color.green);
		SpeedLabel.setFont(new Font("Times Header", Font.BOLD, 16));
		
		TimeLabel.setForeground(Color.green);
		TimeLabel.setFont(new Font("Times Header", Font.BOLD, 16));
		
		
		this.setVerticalAlignment(SwingConstants.BOTTOM);
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}
	

	public void speed(Background road) {
		Thread sp = new Thread() {
		@Override
		public void run() {
		while(road.getFlag()==0 && !road.getGameFinished()) {
			float volume = 0.45f;
			AudioInputStream audioInputStream = null;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(new File("imgs/sound.wav").getAbsoluteFile());
			} catch (UnsupportedAudioFileException | IOException e2) {
				e2.printStackTrace();
			} 
			Clip clip = null;
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			} 
				          
			try {
				clip.open(audioInputStream);
			} catch (LineUnavailableException | IOException e1) {
				e1.printStackTrace();
			} 
				          
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
				        
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e3) {
				e3.printStackTrace();
			}
			while(!road.getPauseFlag()) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				volume= (float) (0.45f + (Speed/300f)/2);
				gain = (range * volume) + gainControl.getMinimum();
				if(gain>6)
					gain = 6;
				gainControl.setValue(gain);
				if(KeyInput.getUpDownCheck()==1 && Speed<300) {
					Speed+=8;
					volume+=0.010;
				}
				if(KeyInput.getUpDownCheck()==-1 && Speed > 8) {
					Speed-=8;
				}
				if(KeyInput.getUpDownCheck()==2 && Speed > 2) {
					Speed-=2;
				}
				if(volume<0.07)
					volume=(float) 0.07;
				getSpeedLabel().setText("Speed " + Speed + " KM/H");
			}
			clip.close();
			}			
		}
	};
	sp.start();
	}
	
	public void animation(Background road) {
		Thread anim = new Thread() {
			@Override
			public void run() {	
				try {
					while(road.getFlag()==0 && !road.getGameFinished()) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						while(!road.getPauseFlag()) {
							while(image_index<images.length && image_index>=0 ) {
								if(KeyInput.getLeftRightCheck()!=0) {
									Thread.sleep(50);
							
									Player_Image= new ImageIcon(images[image_index]);
									setIcon(Player_Image);
							
									if(KeyInput.getLeftRightCheck()==1) 
										image_index++;
									else if(KeyInput.getLeftRightCheck()==2) 
										image_index--;
								
								}
								else {
									Thread.sleep(50);
								
									Player_Image= new ImageIcon(images[image_index]);
									setIcon(Player_Image);

									if(image_index < 7)
										image_index++;
									else if(image_index > 7)
										image_index--;
								}
							}
						
							if(image_index>14)
								image_index=12;
							else if(image_index<0)
								image_index=2;
						
						}
					}
					if(road.getFlag()==1) {
						for(int i=11;i<22;i++) {
							Player_Image= new ImageIcon(crashimages[i]);
							setIcon(Player_Image);
							Thread.sleep(100);
						}
					}
					if(road.getFlag()==-1) {
						for(int i=10;i>-1;i--) {
							Player_Image= new ImageIcon(crashimages[i]);
							setIcon(Player_Image);
							Thread.sleep(100);
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		anim.start();
	}
	
	public void time(Background road, Game game) {
		Thread tm = new Thread() {
		@Override
		public void run() {
		while(road.getFlag()==0 && !road.getGameFinished()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
				while(!road.getPauseFlag()) {
				try {
					
					Thread.sleep(1000);
					Time--;
					Distance+=Speed;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(Time<0) {
					road.setPauseflag(true);
					road.setFlag(2);
					Speed=1;
					Time=60;
					Distance=0;
					image_index=7;
			
					Player_Image = new ImageIcon(images[image_index]);
							
					SpeedLabel = new JLabel("Speed " + Speed + " KM/H");
					DistanceLabel = new JLabel("Traveled " + Distance+ " M");
					TimeLabel = new JLabel("Time "+ Time);
					game.start();
				}
				TimeLabel.setText("Time " + Time);
				DistanceLabel.setText("Traveled " + Distance/1000 + " KM");		
			}
		}
		}
	};
	tm.start();
	}

	public JLabel getTimeLabel() {
		return TimeLabel;
	}

	public JLabel getDistanceLabel() {
		return DistanceLabel;
	}
	
	public JLabel getSpeedLabel() {
		return SpeedLabel;
	}
	
	public float getSpeed() {
		return Speed;
	}
	
	public double getDistance() {
		return Distance;
	}
	
	public int getTime() {
		return Time;
	}
	
	public void setTime(int Time) {
		this.Time=Time;
	}
	
	public void setSpeed(float Speed) {
		this.Speed=Speed;
	}

}
