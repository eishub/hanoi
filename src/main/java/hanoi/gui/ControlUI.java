package hanoi.gui;

import hanoi.Hanoi;
import hanoi.elements.Pin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlUI extends JFrame {

    private JButton btnLC = new JButton("Left to Center");
    private JButton btnLR = new JButton("Left to Right");
    private JButton btnCL = new JButton("Center to Left");
    private JButton btnCR = new JButton("Center to Right");
    private JButton btnRL = new JButton("Right to Left");
    private JButton btnRC = new JButton("Right to Center");

    private HanoiUI hanoi;

    public ControlUI(HanoiUI h) {
        super("Hanoi Controls");

        this.hanoi = h;

        setTitle("Hanoi Controls");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        btnLC.addActionListener(new ControlButtonListener());
        btnLR.addActionListener(new ControlButtonListener());
        btnCL.addActionListener(new ControlButtonListener());
        btnCR.addActionListener(new ControlButtonListener());
        btnRL.addActionListener(new ControlButtonListener());
        btnRC.addActionListener(new ControlButtonListener());

        // Add the buttons.
        panel.add(btnLC);
        panel.add(btnLR);
        panel.add(btnCL);
        panel.add(btnCR);
        panel.add(btnRL);
        panel.add(btnRC);

        add(panel);

        setSize(400, 105);
        setVisible(true);

        updateButtons();
    }

    /**
     * TODO: This can be done in a nicer way.
     */
    public void updateButtons() {
        Hanoi h = hanoi.getGame();
        btnLC.setEnabled(h.validMove(0, 1));
        btnLR.setEnabled(h.validMove(0, 2));
        btnCL.setEnabled(h.validMove(1, 0));
        btnCR.setEnabled(h.validMove(1, 2));
        btnRL.setEnabled(h.validMove(2, 0));
        btnRC.setEnabled(h.validMove(2, 1));
    }

    private class ControlButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Pin[] pins = hanoi.getGame().getPins();
            if (e.getSource() == btnLC) {
                hanoi.moveDisc(pins[0], pins[1]);
            } else if (e.getSource() == btnLR) {
                hanoi.moveDisc(pins[0], pins[2]);
            } else if (e.getSource() == btnCL) {
                hanoi.moveDisc(pins[1], pins[0]);
            } else if (e.getSource() == btnCR) {
                hanoi.moveDisc(pins[1], pins[2]);
            } else if (e.getSource() == btnRL) {
                hanoi.moveDisc(pins[2], pins[0]);
            } else if (e.getSource() == btnRC) {
                hanoi.moveDisc(pins[2], pins[1]);
            }
        }
    }
}