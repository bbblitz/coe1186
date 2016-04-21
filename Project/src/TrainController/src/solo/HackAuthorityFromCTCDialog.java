import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HackAuthorityFromCTCDialog {
	
	private int authority = -1;

	public int showDialog() {
		JTextField authorityTextField = new JTextField();
        JLabel authorityLabel = new JLabel("ft");

		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				authority = Integer.parseInt(authorityTextField.getText());
				authority = Math.max(0, authority);
				JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
			}
		});

		JPanel buttons = new JPanel();
		buttons.add(okButton);
		buttons.add(cancelButton);

		JPanel content = new JPanel(new BorderLayout(8, 8));
		content.add(authorityTextField, BorderLayout.CENTER);
        content.add(authorityLabel, BorderLayout.EAST);
		content.add(buttons, BorderLayout.SOUTH);

		JDialog dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModal(true);
		dialog.setTitle("Hack authority");
        dialog.getContentPane().add(content);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

		return authority;
	}

}