package hanoi.gui;

import hanoi.Hanoi;
import hanoi.elements.Disc;
import hanoi.elements.Pin;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * @author W.Pasman 12jan15 rendering should be done inside a panel, not
 *         directly inside a JFrame, to avoid rendering issues as documented in
 *         #3256.
 *
 */
@SuppressWarnings("serial")
class HanoiPanel extends JPanel {

	// Predefined values.
	final private int SPACER = 20;

	final private int PIN_WIDTH = 10;
	final private int PIN_BASE_HEIGHT = 10;

	final private int DISC_DELTA_WIDTH = 20;
	final private int DISC_HEIGHT = 10;

	private int PIN_BASE_WIDTH;
	private int PIN_HEIGHT;
	private int PIN_CENTER;

	// Positions of the Pins (left boundary).
	private int PIN_L_X;
	private int PIN_M_X;
	private int PIN_R_X;

	private int WINDOW_WIDTH;
	private int WINDOW_HEIGHT;

	private Hanoi hanoi;

	public HanoiPanel(Hanoi h) {
		hanoi = h;

		// Set static information.
		int MAX_DISCS = hanoi.MAX_CAPACITY + 2; // TODO: Find why this is
												// needed.

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

	}

	/**
	 * Draw the GUI.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		drawPins(g);
		drawDiscs(g, hanoi.getPins());
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

	public int getWidth() {
		return WINDOW_WIDTH;
	}

	public int getHeight() {
		return WINDOW_HEIGHT;
	};

}