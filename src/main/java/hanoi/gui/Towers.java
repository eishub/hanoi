package hanoi.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main window.
 */
public class Towers extends Frame {

    public static void main(String[] arg) {
        new Towers("Testing", null).setVisible(true);
    }

    Drawable canvas;

    /**
     * General constructor for the Towers game.
     *
     * @param title title of the frame
     * @param list  list of startpositions for the discs. Discs will be numbered automatically.
     */
    public Towers(String title, List<Integer> list) {
        super(title);
//		add(canvas = new Drawable(this));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        reset(list);
        pack();
    }

    /**
     * Resets the game to a given start situation.
     *
     * @param list list of startpositions for the discs. Discs will be numbered automatically.
     */
    public void reset(List<Integer> list) {
        if (list == null) {
            reset(new ArrayList<Integer>() {{
                add(0);
                add(0);
                add(0);
                add(0);
            }});
            return;
        }

        if (canvas != null) {
            canvas.things = null;
            remove(canvas);
        }
        add(canvas = new Drawable(this));

        pack();

        // Draw the first discs.
        int disc = 1; // Name of the first disc.
        for (Integer x : list) {
            addDisc(disc, x.intValue());
            disc++;
        }
    }

    public void paint(Graphics g) {
        canvas.paint(g);
    }

    /**
     * Function to add a disc to the game.
     *
     * @param disc disc number / identifier.
     * @param p    position of the disc to be drawn.
     */
    public void addDisc(int disc, int p) {
        canvas.addDisc(disc, p);
    }

    /**
     * Move the given disc to a given destination if it's on top of the origin tower.
     *
     * @param disc disc identifier
     * @param to   destination tower
     */
    public void moveDisc(int disc, int to) {
        canvas.moveDisc(disc, to);
    }

    /**
     * Make the canvas accessible from the outside.
     *
     * @return the current canvas object.
     */
    public Drawable getCanvas() {
        return canvas;
    }

    /**
     * Returns the pins as they are.
     *
     * @return Array containing all the information about the pins.
     */
    public Drawable.Disc[] getPins() {
        return canvas.pins;
    }

    /**
     * Returns true if the disc exists, and false otherwise.
     *
     * @param disc disc to be checked for existence.
     * @return boolean result
     */
    public boolean discExists(int disc) {
        try {
            getDisc(disc);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the Disc object associated with the given identifier.
     *
     * @param disc identifier of the disc to be retrieved.
     * @return the Disc object
     * @throws IndexOutOfBoundsException if the disc does not exist.
     */
    public Drawable.Disc getDisc(int disc) throws IndexOutOfBoundsException {
        Drawable.Disc[] pins = getPins();
        for (Drawable.Disc d : pins) {
            if (d.number == disc)
                return d;
        }
        throw new IndexOutOfBoundsException("Disc not found");
    }
}