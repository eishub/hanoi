package hanoi.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Towers extends Frame {

    Drawable canvas;

    public Towers(String title, List<Integer> list) {
        super(title);
        add(canvas = new Drawable(this));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        pack();
    }

    public void paint(Graphics g) {
        canvas.paint(g);
    }

    public void addDisc(int disc, int p) {
        canvas.addDisc(disc, p);
    }

    public void moveDisc(int disc, int to) {
        canvas.moveDisc(disc, to);
    }

    // TODO
    public void reset(List<Integer> list) {
        if (list == null) {
            list = new ArrayList<Integer>() {{
                add(0); add(0); add(0); add(0);
            }};
        }

        if (canvas != null) {
            canvas.things = null;
            remove(canvas);
        }

        add(canvas = new Drawable(this));

        pack();

        // Draw the discs
        int disc = 1;
        for (Integer x : list) {
            addDisc(disc, x.intValue());
            disc++;
        }
    }

    public Drawable.Disc[] getPins() {
        return null;
    }

    public boolean discExists(int disc) {
        return true;
    }
}