import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class TrainModelProtoType{
	private JFrame frame = new JFrame();
	private JPanel[] panel = new JPanel[6];
	private JLabel[] label = new JLabel[11];
	private JButton go = new JButton("Go");
	private JButton reset = new JButton("Reset");
	private JTextField text = new JTextField("Max 120");
	private final int WIDTH = 800;
	private final int HEIGHT =600;
	private double startTime = System.currentTimeMillis() / 1000.0;
	private double currentTime = (System.currentTimeMillis() / 1000.0) - startTime;
	private double position;
	private double velocity;
	private double acceleration;
	private String pos;
	private String vel;
	private String acc;
	private double force;
	private double power;
	private double mass = 37103.86;
	private int start = 0;
	
	public TrainModelProtoType(){
		
		//formatting GUI
		
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(new GridLayout(3,2));
		
		force = 0;
		position = 0;
		velocity = 0;
		acceleration = 0;
		pos = new Double(position).toString();
		vel = new Double(velocity).toString();
		acc = new Double(acceleration).toString();
		
		label[0] = new JLabel("Position:       ");
		label[1] = new JLabel(pos);
		label[3] = new JLabel("Velocity:       ");
		label[4] = new JLabel(vel);
		label[6] = new JLabel("Acceleration:   ");
		label[7] = new JLabel(acc);
		label[9] = new JLabel("Power Command:  ");
		label[2] = new JLabel("m");
		label[5] = new JLabel("m/s");
		label[8] = new JLabel("m/s^2");
		label[10] = new JLabel("kW");
		
		for(int i=0; i<6; i++){
			panel[i] = new JPanel();
			
			frame.add(panel[i]);
		}
		
		panel[0].add(label[0]);
		panel[0].add(label[1]);
		panel[0].add(label[2]);
		panel[2].add(label[3]);
		panel[2].add(label[4]);
		panel[2].add(label[5]);
		panel[4].add(label[6]);
		panel[4].add(label[7]);
		panel[4].add(label[8]);
		
		panel[1].add(label[9]);
		panel[1].add(text);
		panel[1].add(label[10]);
		panel[3].add(go);
		panel[5].add(reset);
		
		//ActionListener for Timer 
		
		int delay = 50; //how often to update (ms), 1000 updates once per second (best possible), 2000 every 2 seconds, etc.
		ActionListener taskPerformer = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				
				
				//What you want to update on the interval goes in here
				
				//if loop prevents variables updating before train started
				if(start != 0){
					currentTime = (System.currentTimeMillis() / 1000.0) - startTime;
					
					//math to move train
					
					force = power * 1000.0 / velocity;
					acceleration = force / mass;
					
					double vConstant = velocity - (acceleration * (currentTime-1));
					velocity = (acceleration * currentTime) + vConstant;
					
					double xConstant = position - (velocity * (currentTime-1));
					position = (velocity * currentTime) + xConstant;
					
					acc = new Double(acceleration).toString();
					vel = new Double(velocity).toString();
					pos = new Double(position).toString();
					
					label[7].setText(acc);
					label[4].setText(vel);
					label[1].setText(pos);	
				}
			}
		};
		new Timer(delay, taskPerformer).start();
		
		
		frame.setVisible(true);
		
		ActionListener buttonListener = new ButtonListener();
		go.addActionListener(buttonListener);
		reset.addActionListener(buttonListener);
	}
	
	
	//ActionListener for Go and Reset 
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			JButton source = (JButton) e.getSource();
			
			if(source.equals(go)){
				
				//gets power from text box
				
				String pow = text.getText();
				power = Double.parseDouble(pow);
				if(power > 120){
					power = 120;
				}
				
				if(acceleration == 0 && position == 0){
					velocity = 1;  //must have starting velocity due to way equations set up...needs fixed to start from 0m/s
				}
				startTime = System.currentTimeMillis() / 1000.0;
				
				//set start to 1 so that labels begin updating on Timer
				
				start = 1;
				
			} else if(source.equals(reset)){
				
				//reset labels back to initial state
				
				acceleration = 0;
				velocity = 0;
				position = 0;
				start = 0;
				
				acc = new Double(acceleration).toString();
				vel = new Double(velocity).toString();
				pos = new Double(position).toString();
				
				label[7].setText(acc);
				label[4].setText(vel);
				label[1].setText(pos);
			}
			
		}
	}
	
	public static void main(String[] args){
		new TrainModelProtoType();
	}
}