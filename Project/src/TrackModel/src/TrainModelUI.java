import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;

public class TrainModelUI {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainModelUI window = new TrainModelUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrainModelUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 487, 446);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPleaseSelectThe = new JLabel("Please Select the Track");
		lblPleaseSelectThe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseSelectThe.setBounds(31, 31, 141, 33);
		frame.getContentPane().add(lblPleaseSelectThe);
		
		JButton btnNewButton = new JButton("Load Green Line");
		btnNewButton.setBounds(84, 64, 119, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnLoadRedLine = new JButton("Load Red Line");
		btnLoadRedLine.setBounds(82, 95, 121, 23);
		frame.getContentPane().add(btnLoadRedLine);
		
		JLabel lblFailState = new JLabel("Fail State");
		lblFailState.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFailState.setBounds(31, 155, 77, 14);
		frame.getContentPane().add(lblFailState);
		
		JLabel lblEnterTheBlock = new JLabel("Enter the Block ID");
		lblEnterTheBlock.setBounds(60, 188, 95, 14);
		frame.getContentPane().add(lblEnterTheBlock);
		
		textField = new JTextField();
		textField.setBounds(165, 185, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnCreate = new JButton("Create Broken Rail");
		btnCreate.setBounds(116, 222, 135, 23);
		frame.getContentPane().add(btnCreate);
		
		JButton btnNewButton_1 = new JButton("Create Track Circuit Failure");
		btnNewButton_1.setBounds(84, 254, 167, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Create Power Failure");
		btnNewButton_2.setBounds(114, 284, 137, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblPleaseEnterTrack = new JLabel("Please Enter Track Temperature");
		lblPleaseEnterTrack.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseEnterTrack.setBounds(220, 40, 186, 14);
		frame.getContentPane().add(lblPleaseEnterTrack);
	}
}
