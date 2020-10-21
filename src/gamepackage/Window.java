package gamepackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Window extends Canvas implements ActionListener{

	private static final long serialVersionUID = -8567949487202082846L;
	private Background road;
	private int width,height;
	private Game game;
	private JFrame frame,mainframe;
	private JLabel LoginLabel;
	private KeyInput keyinput;
	private int count=0;
	private File users = new File("Users.txt");
	private String username = "",user_password = "";
	private boolean loggedin = false;
	private static String loggedinUser ="";
	
	@SuppressWarnings("deprecation")
	public Window(int width, int height, String title, Game game) {
		this.game=game;
		this.width=width;
		this.height=height;
		keyinput= new KeyInput();
		frame = new JFrame(title);
		JMenuBar menuBar = new JMenuBar();
		LoginLabel = new JLabel("Not Logged In    Login to Play");
		JLayeredPane pane = new JLayeredPane();
		frame.setPreferredSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setJMenuBar(menuBar);
		JMenu main = new JMenu("Game");
		menuBar.add(main);
		JMenuItem start = new JMenuItem("Start");
		main.add(start);
		start.setActionCommand("Start");
		start.addActionListener(this);
		JMenuItem pause = new JMenuItem("Pause/Unpause");
		main.add(pause);
		pause.setActionCommand("Pause");
		pause.addActionListener(this);
		JMenuItem restart = new JMenuItem("Restart");
		main.add(restart);
		restart.setActionCommand("Restart");
		restart.addActionListener(this);
		JMenu second = new JMenu("User");
		menuBar.add(second);
		JMenuItem register = new JMenuItem("Register");
		second.add(register);
		register.setActionCommand("Register");
		register.addActionListener(this);
		JMenuItem login = new JMenuItem("Login");
		second.add(login);
		login.setActionCommand("Login");
		login.addActionListener(this);
		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("Quit");
		quit.addActionListener(this);
		menuBar.add(quit);
		
		JLabel hangon = new JLabel(new ImageIcon("imgs/hangon.png"));
		hangon.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		
		LoginLabel.setForeground(Color.green);
		LoginLabel.setFont(new Font("Times Header", Font.BOLD, 15));
		LoginLabel.setBounds(10, 10, 300, 20);
		
		pane.add(LoginLabel, new Integer(2));
		pane.add(hangon, new Integer(1));
		pane.setBounds(0,0,900,507);
		
		frame.add(pane);
		frame.add(game);
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Start")){
			if(loginstatus()) {
				frame.dispose();
				game.start();
			}		
		}	
		else if(command.equals("Restart")){
			mainframe.dispose();
			game.start();
		}
		else if(command.equals("Pause")){
			count++;
			if(count%2==1) {
				road.setPauseflag(true);
			}
			else {
				road.setPauseflag(false);
			}
		}
		else if(command.equals("Register")){
			FileWriter fp = null;
			try {
				fp = new FileWriter(users, true);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			BufferedWriter br = new BufferedWriter(fp);
			PrintWriter pr = new PrintWriter(br);
			
			JLabel label_login = new JLabel("Username:");
			JTextField login = new JTextField();
			 
			JLabel label_password = new JLabel("Password:");
			JPasswordField password = new JPasswordField();
			 
			Object[] array = { label_login,  login, label_password, password };
			 
			int reg = JOptionPane.showConfirmDialog(null, array, "Register", 
			        JOptionPane.OK_CANCEL_OPTION,
			        JOptionPane.PLAIN_MESSAGE);
			if (reg == JOptionPane.OK_OPTION) {
			    pr.println(login.getText().trim() + "/" + new String(password.getPassword())+"/");
			}
			pr.close();
			try {
				br.close();
				fp.close();
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		else if(command.equals("Login")){
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(users));
			} 
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 

			JLabel label_login = new JLabel("Username:");
			JTextField login = new JTextField();
			
			JLabel label_password = new JLabel("Password:");
			JPasswordField password = new JPasswordField();
			 
			Object[] array = { label_login,  login, label_password, password };
			 
			int reg = JOptionPane.showConfirmDialog(null, array, "Login", 
			        JOptionPane.OK_CANCEL_OPTION,
			        JOptionPane.PLAIN_MESSAGE);
			
			if (reg == JOptionPane.OK_OPTION) {
			   username=new String(login.getText().trim());
			   user_password=new String(password.getPassword());
			}
			  
			String string; 
			try {
				while ((string = br.readLine()) != null) {
				   count =string.indexOf('/');
				   int j =string.lastIndexOf('/'); 
				   if(username.equals(string.substring(0, count))) {
				    	if(user_password.equals(string.substring(count+1, j))) {
				    		LoginLabel.setText("Welcome " + username);
				    		loggedin=true;
				    		loggedinUser=username;
				    	}
				   }
				}
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		else if(command.equals("Quit")){
			System.exit(0);
		}		
	}
	
	public void setgame(String title) {
		if(mainframe!=null)
			mainframe.dispose();
		mainframe = new JFrame(title);
		JMenuBar menuBar = new JMenuBar();
		mainframe.setResizable(false);
		mainframe.setSize(width, height + menuBar.getHeight());
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setLocationRelativeTo(null);
		mainframe.setLayout(null);
		
		JMenu first = new JMenu("Game");
		menuBar.add(first);
		
		JMenuItem start = new JMenuItem("Start");
		first.add(start);
		
		start.setActionCommand("Start");
		start.addActionListener(this);
		
		JMenuItem pause = new JMenuItem("Pause/Unpause");
		first.add(pause);
		pause.setActionCommand("Pause");
		pause.addActionListener(this);
		
		JMenuItem restart = new JMenuItem("Restart");
		first.add(restart);
		restart.setActionCommand("Restart");
		restart.addActionListener(this);
				
		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("Quit");
		quit.addActionListener(this);
		menuBar.add(quit);
		mainframe.setJMenuBar(menuBar);
	}
	
	public boolean loginstatus() {
		return loggedin;
	}
	
	@SuppressWarnings("deprecation")
	public void start(Background road, Background bg, Player player) {
		this.road=road;
		JLayeredPane pane = new JLayeredPane();
		pane.add(player,new Integer(4));
		pane.add(road, new Integer(3));
		pane.add(bg,new Integer(1));
		pane.add(player.getSpeedLabel(), new Integer(2));
		pane.add(player.getDistanceLabel(), new Integer(2));
		pane.add(player.getTimeLabel(), new Integer(2));
		pane.setBounds(0, 0, 3824, 506);
		mainframe.addKeyListener(keyinput);
		mainframe.add(pane);
		mainframe.setVisible(true);
	}
	
	public KeyInput getKeyInput() {
		return keyinput;
	}
	
	public static String getLoggedinUser() {
		return loggedinUser;
	}

}
