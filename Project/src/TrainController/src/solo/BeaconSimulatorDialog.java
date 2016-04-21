import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BeaconSimulatorDialog {

    private Beacon beacon;

    public Beacon showDialog() {
        JLabel stationLabel = new JLabel("Station name: ");
        JTextField stationTextField = new JTextField("", 20);

        JLabel distanceLabel = new JLabel("Distance: ");
        JTextField distanceTextField = new JTextField("", 15);
        JLabel distanceUnitsLabel = new JLabel("ft");

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stationName = stationTextField.getText();
                int distanceFeet = Integer.parseInt(distanceTextField.getText());
                int distance = (int)((double)distanceFeet * 0.3048);
                beacon = new Beacon(stationName, distance);
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



        JPanel stationNamePanel = new JPanel();
        stationNamePanel.add(stationLabel);
        stationNamePanel.add(stationTextField);

        JPanel distancePanel = new JPanel();
        distancePanel.add(distanceLabel);
        distancePanel.add(distanceTextField);
        distancePanel.add(distanceUnitsLabel);

        JPanel buttons = new JPanel();
        buttons.add(okButton);
        buttons.add(cancelButton);

        JPanel contentPanel = new JPanel(new BorderLayout(8, 8));
        contentPanel.add(stationNamePanel, BorderLayout.NORTH);
        contentPanel.add(distancePanel, BorderLayout.CENTER);
        contentPanel.add(buttons, BorderLayout.SOUTH);

        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setTitle("Simulate a beacon");
        dialog.getContentPane().add(contentPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return beacon;
    }


}
