package eisinterface;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.exceptions.ActException;
import hanoi.gui.Drawable;
import hanoi.gui.Towers;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class, creates a controllable entity to the game that can be controlled
 * by an agent-programming platform.
 *
 * @author Sander van den Oever
 */
public class Entity {

    private Towers game;
    private int default_pin;

    public Entity(Towers game) {
        this.game = game;
    }

    /**
     * TODO: Send this percept only on initialization.
     * @return Returns a list of the current discs in the game and their levels.
     */
    @AsPercept(name = "disc", multiplePercepts = true, multipleArguments = true)
    public List<List<Integer>> discs() {
        List<List<Integer>> discs = new ArrayList<List<Integer>>();

        if (game.getPins() != null) {
            for (Drawable.Disc disc : game.getPins()) {
                while (disc != null) {
                    List<Integer> entry = new ArrayList<Integer>();
                    entry.add(disc.number);
                    entry.add(disc.lvl);

                    discs.add(entry);

                    disc = disc.next;
                }
            }
        }

        return discs;
    }

    /**
     * TODO: Send only on initialization.
     * @return returns a percept indicating the amount of pins.
     */
    @AsPercept(name = "pins")
    public int pins() {
        return game.getPins().length;
    }

    /**
     * @return returns a list with all discs with their pins, and the discs below them.
     */
    @AsPercept(name = "on", multiplePercepts = true, multipleArguments = true)
    public List<List<Integer>> onPin() {
        List<List<Integer>> list = new ArrayList<List<Integer>>();

        if (game.getPins() != null) {
            for (Drawable.Disc disc : game.getPins()) {
                // Disc now is the first disc on the current pin
                while (disc != null) {
                    List<Integer> entry = new ArrayList<Integer>();

                    entry.add(disc.number);
                    entry.add(disc.pin);

                    if (disc.next != null) {
                        entry.add(disc.next.number);
                    } else {
                        entry.add(0);
                    }

                    list.add(entry);

                    disc = disc.next;
                }
            }
        }

        return list;
    }

    /**
     * Support function to move a disc.
     *
     * @param disc disc to be moved.
     * @param to   pin to move the disc to.
     *
     * TODO pins[to] possible out of bounds
     */
    @AsAction(name = "move")
    public void moveDisc(int disc, int to) {
        Drawable.Disc[] pins = game.getPins();
        if (to < pins.length && game.discExists(disc)) {
            // Passed security check, disc exists, now compare discs.
            if (pins[to] == null /*|| (game.getDisc(disc).lvl < pins[to].lvl)*/) {
                game.moveDisc(disc, to);
            }
        }
    }
}