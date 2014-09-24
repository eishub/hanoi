package hanoi.gui;

import com.sun.javaws.exceptions.InvalidArgumentException;
import eis.exceptions.ActException;

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

        reset(list);
    }

    public void paint(Graphics g) {
        canvas.paint(g);
    }

    public void addDisc(int disc, int p) {
        canvas.addDisc(disc, p);
    }

    public void moveDisc(int disc, int to) throws ActException {
        if (validMove(disc, to)) {
            canvas.moveDisc(disc, to);
        } else throw new ActException("Invalid move!");

    }

    public void reset(List<Integer> list) {
        if (list == null) {
            list = new ArrayList<Integer>() {{
                add(0);
                add(0);
                add(0);
                add(0);
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
        return canvas.pins;
    }

    public boolean discExists(int disc) {
        if (getPins() != null) {
            Drawable.Disc[] pins = getPins();
            for (Drawable.Disc d : pins) {
                while (d != null) {
                    if (d.number == disc)
                        return true;
                    d = d.next;
                }
            }
        }
        return false;
    }

    public boolean validMove(int d, int to) {
        if (discExists(d)) {
            Drawable.Disc disc = getDisc(d);
            Drawable.Disc destination = getPins()[to];
            if (destination != null) {
                return (disc.size < destination.size);
            } else {
                return true;
            }
        }
        return false;
    }

    public Drawable.Disc getDisc(int disc) {
        Drawable.Disc[] pins = getPins();
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
        Drawable.Disc[] pins = getPins();
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