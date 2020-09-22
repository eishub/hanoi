package hanoi.gui;

import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import eis.exceptions.ActException;

public class Towers extends Frame {
	private static final long serialVersionUID = 1L;
	private Drawable canvas;

	public Towers(final String title, final List<Integer> list) {
		super(title);
		add(this.canvas = new Drawable());
		reset(list);
	}

	@Override
	public void paint(final Graphics g) {
		this.canvas.paint(g);
	}

	public void addDisc(final int disc, final int p) {
		this.canvas.addDisc(disc, p);
	}

	public void moveDisc(final int disc, final int to) throws ActException {
		if (validMove(disc, to)) {
			this.canvas.moveDisc(disc, to);
		} else {
			throw new ActException("Invalid move!");
		}

	}

	public void reset(List<Integer> list) {
		if (list == null) {
			list = new ArrayList<>(4);
			list.add(0);
			list.add(0);
			list.add(0);
			list.add(0);
		}

		if (this.canvas != null) {
			this.canvas.things = null;
			remove(this.canvas);
		}

		add(this.canvas = new Drawable());

		pack();

		// Draw the discs
		int disc = 1;
		for (final Integer x : list) {
			addDisc(disc, x);
			disc++;
		}
	}

	public Drawable.Disc[] getPins() {
		return this.canvas.pins;
	}

	public boolean discExists(final int disc) {
		if (getPins() != null) {
			final Drawable.Disc[] pins = getPins();
			for (Drawable.Disc d : pins) {
				while (d != null) {
					if (d.number == disc) {
						return true;
					}
					d = d.next;
				}
			}
		}
		return false;
	}

	public boolean validMove(final int d, final int to) {
		if (discExists(d)) {
			final Drawable.Disc disc = getDisc(d);
			final Drawable.Disc destination = getPins()[to];
			if (destination != null) {
				return (disc.size < destination.size);
			} else {
				return true;
			}
		}
		return false;
	}

	public Drawable.Disc getDisc(final int disc) {
		final Drawable.Disc[] pins = getPins();
		for (Drawable.Disc d : pins) {
			while (d != null) {
				if (d.number == disc) {
					return d;
				}
				d = d.next;
			}
		}
		return null;
	}

	public void printDiscs() {
		final Drawable.Disc[] pins = getPins();
		for (Drawable.Disc d : pins) {
			String res = "Pin[";
			while (d != null) {
				res += "(#" + d.number + " " + d.size + ")";
				d = d.next;
			}
			System.out.println(res + "]");
		}
	}
}