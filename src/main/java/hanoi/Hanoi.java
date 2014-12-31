package hanoi;

import hanoi.elements.Disc;
import hanoi.elements.Pin;
import hanoi.exceptions.EmptyPinException;
import hanoi.gui.HanoiUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Hanoi implementation.
 *
 * @author Sander van den Oever
 */
public class Hanoi {

    // Information about the pins.
    protected Pin[] pins = new Pin[3];

    // Visual representation of the Game.
    protected HanoiUI ui;

    // Information for the game to function properly.
    public int MAX_CAPACITY = 5;
    private int DISCS = 1;

    /**
     * Public constructor (when list is provided).
     *
     * Creates an object and instantiates it using the provided list.
     * @param list list with parameters to set up the game.
     */
    public Hanoi(List<Integer> list) {
        reset(list);
    }

    /**
     * Public constructor.
     *
     * Creates an object and instantiates it using the default values.
     */
    public Hanoi() {
        // TODO control window optional
        // TODO documentation
        // TODO documentation EI
        // TODO agent
        reset(null);
    }

    /**
     * Resets the game to the provided setup. If no list is supplied, default values will be used.
     * @param list list with parameters to set up the game.
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

        // Create three pins
        pins[0] = new Pin(0);
        pins[1] = new Pin(1);
        pins[2] = new Pin(2);

        // TODO Garbage collector will remove old junk.
        ui = new HanoiUI(this, pins);

        // TODO proper error
        for (Integer x : list) {
            if (x >= 0 && x <= 2)
                addDisc(pins[x]);
            else
                System.err.println("[ERROR] [RESET] Error in parameters for setting up the game. -> " + x);
        }
        
        ui.updateGUI();
    }

    /**
     * Main function. Starts the UI.
     *
     * @param args arguments to be supplied to Hanoi.
     */
    public static void main(String[] args) {
        new Hanoi(null);
    }

    /**
     * Move a disc from one pin to another.
     *
     * @param origin      Pin to get the disc from.
     * @param destination Pin to move the disc to.
     * TODO: This can be done in a cleaner way
     */
    public void moveDisc(Pin origin, Pin destination) {
        // Check for existing disc that can be moved.
        if (origin.discsOnPin() >= 1) {
            if (origin.getTopDisc().getSize() <= destination.maxLevel()) {
                // Move
                try {
                    Disc toBeMoved = origin.popDisc();
                    destination.addDisc(toBeMoved);
                } catch (EmptyPinException e) {
                    System.out.println("Pin was empty even after the check?!");
                }
            } else {
                System.out.println("Disc cannot be moved. Invalid move!");
            }
        } else {
            System.out.println("Failed to move! The originating pin is empty!");
        }

        // Update the UI
        getUI().updateGUI();
        getUI().repaint();
    }

    /**
     * Add a new disc to the game.
     *
     * @param pin Pin to place the new disc on.
     */
    public void addDisc(Pin pin) {
        if (DISCS < MAX_CAPACITY) {
            Disc d = new Disc(DISCS, MAX_CAPACITY - DISCS, pin);
            DISCS++;
            pin.addDisc(d);
            ui.updateGUI();
        }
        // TODO: Throw error.
    }

    /**
     * Prints all possible moves at the current state of the game.
     */
    public void possibleMoves() {
        int[] top = {0, 0, 0};
        for (Pin p : pins) {
            if (!p.isEmpty())
                top[p.id] = p.getTopDisc().getSize();
        }

        System.out.println(
                (top[0] < top[1] ? "[L -> C]" : "[      ]") + " " +
                        (top[0] < top[2] ? "[L -> R]" : "[      ]") + " " +
                        (top[1] < top[0] ? "[C -> L]" : "[      ]") + " " +
                        (top[1] < top[1] ? "[C -> R]" : "[      ]") + " " +
                        (top[2] < top[0] ? "[R -> L]" : "[      ]") + " " +
                        (top[2] < top[1] ? "[R -> C]" : "[      ]")

        );
    }

    /**
     * Determines whether a move can be made in the current situation.
     *
     * @param p1 int identifying the origin pin.
     * @param p2 int identifying the destination pin.
     * @return boolean true if valid move, false otherwise.
     */
    public boolean validMove(int p1, int p2) {
        if (p1 >= 0 && p1 <= 2 && p2 >= 0 && p2 <= 2) {
            if (!pins[p1].isEmpty()) {
                if (pins[p2].isEmpty()) {
                    return true;
                } else {
                    return pins[p1].getTopDisc().getSize() < pins[p2].getTopDisc().getSize();
                }
            }
        }
        return false;
    }

    /**
     * Returns the array containing all Pins.
     *
     * @return Pin[] Array of Pins.
     */
    public Pin[] getPins() {
        return pins;
    }

    /**
     * Closes the ui and exits the game.
     */
    public void exitGame() {
        ui.dispose();
        pins = null;
    }

    /**
     * Returns the UI object.
     * @return HanoiUI UI object.
     */
    public HanoiUI getUI() {
        return ui;
    }
}
