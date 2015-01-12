package hanoi.gui;

import hanoi.Hanoi;
import hanoi.elements.Disc;
import hanoi.elements.Pin;

import java.awt.Color;
import java.awt.Graphics;
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

	// Predefined values.
	final private int SPACER = 20;

	final private int PIN_WIDTH = 10;
	final private int PIN_BASE_HEIGHT = 10;

	final private int DISC_DELTA_WIDTH = 20;
	final private int DISC_HEIGHT = 10;

	// Values to be set later on.
	private int MAX_DISCS;

	private int PIN_BASE_WIDTH;
	private int PIN_HEIGHT;
	private int PIN_CENTER;

	// Positions of the Pins (left boundary).
	private int PIN_L_X;
	private int PIN_M_X;
	private int PIN_R_X;

	private int WINDOW_WIDTH;
	private int WINDOW_HEIGHT;

	// Game itself and the controls
	private Hanoi hanoi;
	ControlUI controls;

	/**
	 * Constructor forwarder. Whenever the gui parameter isn't supplied, call
	 * the constructor correctly.
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

		this.hanoi = hanoi;
		if (gui)
			controls = new ControlUI(this);

		// Set static information.
		MAX_DISCS = hanoi.MAX_CAPACITY + 2; // TODO: Find why this is needed.

		// Pins should be able to contain the biggest disc (and be a little
		// bigger in order to look nice).
		PIN_BASE_WIDTH = (MAX_DISCS * (DISC_DELTA_WIDTH + 1)) + SPACER;
		PIN_HEIGHT = PIN_BASE_HEIGHT + (MAX_DISCS * DISC_HEIGHT);
		PIN_CENTER = (PIN_BASE_WIDTH / 2) - (PIN_WIDTH / 2);

		// Width is equal to 3 pins surrounded by spacers.
		WINDOW_WIDTH = (4 * SPACER) + (3 * PIN_BASE_WIDTH);
		WINDOW_HEIGHT = (2 * SPACER) + PIN_HEIGHT + PIN_BASE_HEIGHT;

		// Calculate the position of each Pin.
		PIN_L_X = SPACER;
		PIN_M_X = PIN_L_X + SPACER + PIN_BASE_WIDTH;
		PIN_R_X = PIN_M_X + SPACER + PIN_BASE_WIDTH;

		// Set JFrame properties.
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocation(100, 100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setVisible(true);

		if (gui) {
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.out
							.println("Controls are being closed because of the main window being closed!");
					controls.dispose();
				}
			});
		}
	}

	@Override
	public void update(Graphics g) {
		super.update(g);

		drawPins(g);
		drawDiscs(g, hanoi.getPins());

		System.out.println("call to update");
	}

	/**
	 * Draw the GUI.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		drawPins(g);
		drawDiscs(g, hanoi.getPins());
		System.out.println("call to paint");
	}

	/**
	 * Draw the (orange) pins.
	 *
	 * @param g
	 *            Graphics object from the paint function.
	 */
	public void drawPins(Graphics g) {
		g.setColor(Color.orange);

		// Draw the bases of the Pins.
		g.fillRect(PIN_L_X, WINDOW_HEIGHT - (SPACER + PIN_BASE_HEIGHT),
				PIN_BASE_WIDTH, PIN_BASE_HEIGHT);
		g.fillRect(PIN_M_X, WINDOW_HEIGHT - (SPACER + PIN_BASE_HEIGHT),
				PIN_BASE_WIDTH, PIN_BASE_HEIGHT);
		g.fillRect(PIN_R_X, WINDOW_HEIGHT - (SPACER + PIN_BASE_HEIGHT),
				PIN_BASE_WIDTH, PIN_BASE_HEIGHT);

		// Draw the poles of the Pins.
		// TODO: Fix this dirty hack at the end? (+SPACER)
		g.fillRect(PIN_L_X + PIN_CENTER, WINDOW_HEIGHT
				- (SPACER + PIN_BASE_HEIGHT), PIN_WIDTH, -PIN_HEIGHT + SPACER);
		g.fillRect(PIN_M_X + PIN_CENTER, WINDOW_HEIGHT
				- (SPACER + PIN_BASE_HEIGHT), PIN_WIDTH, -PIN_HEIGHT + SPACER);
		g.fillRect(PIN_R_X + PIN_CENTER, WINDOW_HEIGHT
				- (SPACER + PIN_BASE_HEIGHT), PIN_WIDTH, -PIN_HEIGHT + SPACER);
	}

	/**
	 * Draws the discs.
	 *
	 * @param g
	 *            Graphics object form the paint function.
	 * @param pins
	 *            all Pins collected in an array.
	 */
	public void drawDiscs(Graphics g, Pin[] pins) {
		g.setColor(Color.black);

		for (Pin p : pins) {
			Disc tmp = p.discs;
			int count = 1; // Keep track of the amount of discs processed on
							// this pin.

			// Loop through all discs on this pin (LinkedList principle).
			while (tmp != null) {
				// Determine drawing positions.
				int w = 20 + tmp.getSize() * DISC_DELTA_WIDTH;
				int x = SPACER + (p.id * (PIN_BASE_WIDTH + SPACER))
						+ (PIN_CENTER - (w / 2) + (PIN_WIDTH / 2));
				int y = WINDOW_HEIGHT - SPACER - PIN_BASE_HEIGHT
						- (count * DISC_HEIGHT);

				// Draw the rectangle.
				g.fillRect(x, y, w, DISC_HEIGHT);

				count++;
				tmp = tmp.getNext();
			}
		}
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

	/**
	 * Returns the highest amount of discs that can be present.
	 *
	 * @return int max amount of discs.
	 */
	public int getMaxDiscs() {
		return MAX_DISCS;
	}

	public void updateGUI() {
		repaint();
		System.out.println("call to updateGUI");
		if (controls != null) {
			controls.updateButtons();
		}
	}
}
