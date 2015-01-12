package hanoi.gui;

import hanoi.Hanoi;
import hanoi.elements.Pin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Control UI, used to provide the user the possibility to manually control the
 * environment.
 *
 * @author Sander van den Oever
 */
public class ControlUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnLC = new JButton("Left to Center");
	private JButton btnLR = new JButton("Left to Right");
	private JButton btnCL = new JButton("Center to Left");
	private JButton btnCR = new JButton("Center to Right");
	private JButton btnRL = new JButton("Right to Left");
	private JButton btnRC = new JButton("Right to Center");

	private HanoiUI ui;

	public ControlUI(HanoiUI h) {
		super("Hanoi Controls");

		this.ui = h;

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
		Hanoi game = ui.getGame();
		btnLC.setEnabled(game.validMove(0, 1));
		btnLR.setEnabled(game.validMove(0, 2));
		btnCL.setEnabled(game.validMove(1, 0));
		btnCR.setEnabled(game.validMove(1, 2));
		btnRL.setEnabled(game.validMove(2, 0));
		btnRC.setEnabled(game.validMove(2, 1));
		// repaint();
	}

	private class ControlButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Pin[] pins = ui.getGame().getPins();
			if (e.getSource() == btnLC) {
				ui.moveDisc(pins[0], pins[1]);
			} else if (e.getSource() == btnLR) {
				ui.moveDisc(pins[0], pins[2]);
			} else if (e.getSource() == btnCL) {
				ui.moveDisc(pins[1], pins[0]);
			} else if (e.getSource() == btnCR) {
				ui.moveDisc(pins[1], pins[2]);
			} else if (e.getSource() == btnRL) {
				ui.moveDisc(pins[2], pins[0]);
			} else if (e.getSource() == btnRC) {
				ui.moveDisc(pins[2], pins[1]);
			}
		}
	}
}