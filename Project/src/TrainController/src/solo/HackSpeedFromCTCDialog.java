import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HackSpeedFromCTCDialog {

    private int speed = -1;

    public int showDialog() {
        JTextField speedTextField = new JTextField();
        JLabel speedLabel = new JLabel("mph");

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = Integer.parseInt(speedTextField.getText());
                speed = Math.max(0, speed);
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
        content.add(speedTextField, BorderLayout.CENTER);
        content.add(speedLabel, BorderLayout.EAST);
        content.add(buttons, BorderLayout.SOUTH);

        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setTitle("Hack speed");
        dialog.getContentPane().add(content);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return speed;
    }

}