package hanoi.gui;

import java.awt.*;
import java.awt.event.*;

/**
 * This is the main window.
 */
public class Towers extends Frame {

	public static void main (String[] arg) {
		new Towers("Testing").setVisible(true);
	}

	Drawable canvas;

	public Towers (String title) {
		super(title);
		add(canvas = new Drawable(this));

		addWindowListener(new WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				System.exit(0);
			}
		});

		pack();
	}

	public void paint (Graphics g) {
		canvas.paint(g);
	}

	public void addDisc (int disc, int p) {
		canvas.addDisc(disc, p);
	}

	public void moveDisc (int disc, int to) {
		canvas.moveDisc(disc, to);
	}

    public Drawable getCanvas() {
        return canvas;
    }

    public Drawable.Disc[] getPins() {
        return canvas.pins;
    }
}