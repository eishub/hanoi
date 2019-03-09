package hanoi.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Drawable extends Canvas {
	private static final long serialVersionUID = 1L;
	Towers window;

	/**
	 * Main window size.
	 */
	static int WIDTH = 300;
	static int HEIGHT = 200;

	/**
	 * The module width of the largest disc.
	 */
	static int LARGEST = 9;
	static int BORDER = 10;

	static int MODULE_WIDTH;
	static int MODULE_HEIGHT;
	static int VERTICAL_BASE;
	static int GAP;

	boolean resized = true;
	int dx = 0;
	int dy = 0;
	int[] center = new int[3];
	int PIN_HEIGHT;

	synchronized void setup() {
		Dimension d = getSize();
		if (d.width != WIDTH || d.height != HEIGHT) {
			this.dx = d.width - WIDTH;
			this.dy = d.height - HEIGHT;
			WIDTH = d.width;
			HEIGHT = d.height;
			this.resized = true;
		}

		MODULE_WIDTH = (WIDTH - 2 * BORDER) / (6 * (LARGEST + 1) + 6);
		MODULE_HEIGHT = (HEIGHT - 2 * BORDER) / (2 * LARGEST);
		VERTICAL_BASE = HEIGHT - (LARGEST + 4) * MODULE_HEIGHT;
		GAP = WIDTH - (6 * (LARGEST + 1) + 6) * MODULE_WIDTH;
		GAP /= 2;

		this.center[0] = offset(1) + GAP + MODULE_WIDTH;
		this.center[1] = this.center[0] + 2 * offset(1) + MODULE_WIDTH;
		this.center[2] = this.center[1] + 2 * offset(1) + MODULE_WIDTH;
		this.PIN_HEIGHT = (LARGEST + 2) * MODULE_HEIGHT;
	}

	/**
	 * All things paintable.
	 */
	Block[] things;

	/**
	 * Adding to the paintables.
	 */
	void addThing(Block p) {
		int size = (this.things == null) ? 0 : this.things.length;
		Block[] tmp = new Block[size + 1];
		for (int i = 0; i < size; i++) {
			tmp[i] = this.things[i];
		}
		tmp[size] = p;
		this.things = tmp;
		repaint(p.x, p.y, p.w, p.h);
	}

	/**
	 * The painting callback. Translates into similar painting callbacks to all
	 * things paintable.
	 */
	@Override
	public void paint(Graphics g) {
		if (this.things == null) {
			return;
		}
		for (Block thing : this.things) {
			thing.paint(g);
		}
		this.resized = false;
	}

	/**
	 * A filled rectangle .
	 */
	abstract class Block {

		int x;
		int y;
		int w;
		int h;
		Color color;
		boolean painted = false;

		Block(Color c) {
			this.color = c;
		}

		protected void paint(Graphics g) {
			synchronized (this) {
				g.setColor(this.color);
				g.fillRect(this.x, this.y, this.w, this.h);
				this.painted = true;
				notifyAll();
			}
		}

		void waitPainted() // Called within synchronized block
		{
			this.painted = false;
			while (!this.painted) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}

	}

	class Foundation extends Block {

		Foundation(Color c) {
			super(c);
			setup();
		}

		protected void setup() {
			this.x = GAP;
			this.y = VERTICAL_BASE + Drawable.this.PIN_HEIGHT;
			this.w = WIDTH - 2 * GAP;
			this.h = MODULE_HEIGHT;
		}

	}

	class Pin extends Block {
		int i = -1;

		Pin(int i, Color c) {
			super(c);
			this.i = i;
			setup();
		}

		protected void setup() {
			this.x = Drawable.this.center[this.i] - MODULE_WIDTH;
			this.y = VERTICAL_BASE;
			this.w = 2 * MODULE_WIDTH;
			this.h = Drawable.this.PIN_HEIGHT;
		}

	}

	public class Disc extends Block {

		int target;
		int target_x;
		int target_y;

		public int pin;
		public int number;
		public int lvl;
		public int size;
		public Disc next;

		synchronized void pickUp(int t) {
			this.target = t;
			this.target_x = xpos(this.target, this.number);
			Drawable.this.pins[this.pin] = this.next;
			this.pin = -1;
		}

		synchronized void putDown(int p) {
			this.lvl = level(p);
			this.next = Drawable.this.pins[p];
			Drawable.this.pins[p] = this;
			this.pin = p;
		}

		protected void setup() {
			this.target_x = xpos(this.target, this.number);
			if (this.pin != -1) {
				this.x = xpos(this.pin, this.number);
				this.y = VERTICAL_BASE + this.lvl * MODULE_HEIGHT;
			} else {
				// Use dx instead
				this.x *= WIDTH / (WIDTH - Drawable.this.dx);
				this.y *= HEIGHT / (HEIGHT - Drawable.this.dy);
			}
			this.w = offset(this.number) * 2;
			this.h = MODULE_HEIGHT;
		}

		Disc(int num, int p) {
			super(Color.black);
			this.number = num;
			this.pin = p;
			this.lvl = level(p);
			this.size = offset(this.number) * 2;
			this.next = Drawable.this.pins[p];
			Drawable.this.pins[p] = this;
			setup();
		}

		void move(int dx, int dy) {
			synchronized (this) {
				repaint(this.x, this.y, this.w, this.h);
				this.x += dx;
				this.y += dy;
				repaint(this.x, this.y, this.w, this.h);
				waitPainted();
			}
			try {
				Thread.sleep(10); // slow down
			} catch (InterruptedException e) {
			}
		}
	}

	Disc[] pins = new Disc[3];

	/**
	 * Returns the next free module level on a pin.
	 */
	int level(int p) {
		return (this.pins[p] == null) ? (LARGEST + 1) : this.pins[p].lvl - 1;
	}

	/**
	 * Returns a width pixel offset for a given disc.
	 */
	int offset(int num) {
		return MODULE_WIDTH * (LARGEST - num + 2);
	}

	/**
	 * Returns the home point x coordinate for a given disc on a given pin.
	 */
	int xpos(int p, int num) {
		return this.center[p] - offset(num);
	}

	/**
	 * Returns the home point y coordinate for a given disc on a given pin.
	 */
	int ypos(int p, int num) {
		return VERTICAL_BASE + level(p) * MODULE_HEIGHT;
	}

	public void addDisc(int disc, int p) {
		if (disc < 1) {
			return;
		}
		if (p < 0 || p > 2) {
			return;
		}
		addThing(new Disc(disc, p));
	}

	public void moveDisc(int disc, int to) {
		for (int from = 0; from < 3; from++) {
			if (to == from) {
				continue;
			}
			if (this.pins[from] == null) {
				continue;
			}
			Disc d = this.pins[from];
			if (d.number == disc) {
				new MovingDisc(from, to).action();
			}
		}
	}

	class MovingDisc {

		int from;
		int target;

		MovingDisc(int f, int to) {
			this.from = f;
			this.target = to;
		}

		protected void action() {
			System.out.println("moving from " + this.from + " to " + this.target);

			// Remove from pin
			Disc disc = Drawable.this.pins[this.from];
			synchronized (Drawable.this) {
				disc.pickUp(this.target);

				// Lift disc up above pins
				while (disc.y > VERTICAL_BASE - 2 * MODULE_HEIGHT) {
					disc.move(0, -MODULE_HEIGHT);
				}

				// Shift to target pin
				int dir = (this.from < this.target) ? MODULE_WIDTH : -MODULE_WIDTH;
				while (disc.x != disc.target_x) {
					disc.move(dir, 0);
				}

				// Sink down
				disc.target_y = ypos(this.target, disc.number);

				while (disc.y < disc.target_y) {
					disc.move(0, MODULE_HEIGHT);
				}

				disc.putDown(this.target);
				System.out.println("done moving from " + this.from + " to " + this.target);
			}
		}
	}

	Drawable(Towers w) {
		this.window = w;
		setSize(WIDTH, HEIGHT);
		setup();

		// set background white.
		setBackground(Color.white);

		// Foundation
		addThing(new Foundation(Color.orange));

		// Pins
		for (int i = 0; i < 3; i++) {
			addThing(new Pin(i, Color.orange));
		}
	}
}