package hanoi.eis;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.translation.Filter;
import hanoi.Hanoi;
import hanoi.elements.Disc;
import hanoi.elements.Pin;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class, creates a controllable entity to the game that can be controlled
 * by an agent-programming platform.
 *
 * @author Sander van den Oever
 */
public class Entity {

    private Hanoi controller;

    public Entity(Hanoi c) {
        this.controller = c;
    }

    /**
     * @return Returns a list of the current discs in the game and their levels.
     */
    @AsPercept(name = "disc", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ONCE)
    public List<List<Integer>> discs() {
        List<List<Integer>> result = new ArrayList<>();

        if (controller.getPins() != null) {
            for (Pin pin : controller.getPins()) {
                Disc disc = pin.discs;
                while (disc != null) {
                    List<Integer> entry = new ArrayList<>();
                    entry.add(disc.getID());
                    entry.add(disc.getSize());

                    result.add(entry);

                    disc = disc.getNext();
                }
            }
        }

        return result;
    }

    /**
     * @return returns a list with all discs with their pins, and the discs below them.
     */
    @AsPercept(name = "on", multiplePercepts = true, multipleArguments = true)
    public List<List<Integer>> onPin() {
        List<List<Integer>> list = new ArrayList<>();

        if (controller.getPins() != null) {
            for (Pin pin : controller.getPins()) {
                Disc disc = pin.discs;

                // Disc now is the first disc on the current pin
                while (disc != null) {
                    List<Integer> entry = new ArrayList<>();

                    entry.add(disc.getID());
                    entry.add(pin.id);

                    if (disc.getNext() != null) {
                        entry.add(disc.getNext().getID());
                    } else {
                        entry.add(0);
                    }

                    list.add(entry);

                    disc = disc.getNext();
                }
            }
        }

        return list;
    }

    /**
     * Support function to move a disc.
     *
     * @param from origin pin, can be either "left", "center" or "right"
     * @param to   pin to move the disc to, can be either "left", "center" or "right"
     *             <p/>
     */
    @AsAction(name = "move")
    public void moveDisc(int from, int to) {
        if (from >= 0 && from <= 2 && to >= 0 && to <= 2)
            controller.moveDisc(controller.getPins()[from], controller.getPins()[to]);
    }
}