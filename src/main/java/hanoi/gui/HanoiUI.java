package hanoi.gui;

import hanoi.Hanoi;
import hanoi.elements.Pin;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Simple GUI for the Hanoi game.
 *
 * @author Sander van den Oever
 * @version 0.1a
 */
public class HanoiUI extends JFrame {

	private static final long serialVersionUID = 1L;

	// Game itself and the controls
	private Hanoi hanoi;

	private HanoiPanel panel = null;

	private ControlUI controls = null;

	/**
	 * Constructor forwarder. Whenever the gui parameter isn't supplied, call
	 * the constructor correctly.
	 * @param hanoi the game board?
	 * @param pins the pins shown
	 */
	public HanoiUI(Hanoi hanoi, Pin[] pins) {
		init(hanoi, true);
	}

	/**
	 * Public constructor.
	 *
	 * Creates a game instance with given parameters.
	 * 
	 * @param hanoi
	 *            Game instance containing the state of the game / ability to
	 *            perform actions.
	 * @param gui
	 *            specifies whether a control panel (user controlled) should be
	 *            shown.
	 */
	public HanoiUI(Hanoi hanoi, boolean gui) {
		init(hanoi, gui);
	}

	private void init(Hanoi hanoi, boolean gui) {
		setTitle("Hanoi");
		setLayout(new BorderLayout());
		panel = new HanoiPanel(hanoi);
		add(panel, BorderLayout.CENTER);

		this.hanoi = hanoi;
		if (gui) {
			controls = new ControlUI(this);
		}

		// Set JFrame properties.
		setSize(panel.getWidth(), panel.getHeight());
		setLocation(100, 100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setVisible(true);

		if (gui) {
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.out
							.println("Controls are being closed because of the main window being closed!");
					if (controls != null) {
						controls.dispose();
						controls = null;
					}
				}
			});
		}
		updateGUI();
	}

	/**
	 * Returns the Game instance, used to access Game methods from the GUI.
	 *
	 * @return Hanoi Game instance
	 */
	public Hanoi getGame() {
		return this.hanoi;
	}

	/**
	 * Forwards a call to the move function to the move function in the Game
	 * itself.
	 *
	 * @param origin
	 *            Pin object to take a Disc from, this Pin can not be empty!
	 * @param destination
	 *            Pin to push the disc onto.
	 */
	public void moveDisc(Pin origin, Pin destination) {
		hanoi.moveDisc(origin, destination);
	}

	public void updateGUI() {
		if (controls != null) {
			controls.updateButtons();
		}
		repaint(0, 0, getWidth(), getHeight());
	}
}
