package eisinterface;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
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
        this.default_pin = 0;
    }

    /**
     * @return returns a list with all discs with their pins, and the discs below them.
     */
    @AsPercept(name = "on", multiplePercepts = true, multipleArguments = true)
    public List<List<Integer>> onPin() {
        List<List<Integer>> list = new ArrayList<List<Integer>>();

        if (game.getPins() != null) {
            for (Drawable.Disc disc : game.getPins()) {
                if (disc != null) {
                    List<Integer> props = new ArrayList<Integer>();

                    // Add Disc information (name and position).
                    props.add(disc.number);
                    props.add(disc.pin);

                    if (disc.next != null) {
                        // Indicates the Disc below the current one.
                        props.add(disc.next.number);
                    } else {
                        // Add zero to indicate the next 'Disc' is the foundation.
                        // A disc with identifier 0 can _NOT_ exist.
                        props.add(0);
                    }

                    list.add(props);
                }
            }
        }

//        List<Integer> list2 = new ArrayList<Integer>();
//        list2.add(0); list2.add(0); list2.add(0);
//        list.add(list2);

        return list;
    }

    /**
     * Support function to move a disc.
     *
     * @param disc disc to be moved.
     * @param to   pin to move the disc to.
     */
    @AsAction(name = "move")
    public void moveDisc(int disc, int to) {
        game.moveDisc(disc, to);
    }
}