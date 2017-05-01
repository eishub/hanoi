package hanoi.elements;

import hanoi.exceptions.EmptyPinException;

/**
 * Class to represent a Pin. A pin can be used to place discs on.
 *
 * @author Sander van den Oever
 */
public class Pin {

    public int id;
    public Disc discs;

    /**
     * Public constructor.
     *
     * @param id id of the pin.
     */
    public Pin(int id) {
        this.id = id;
        this.discs = null;
    }

    /**
     * Checks whether the Pin contains 1 or more discs.
     *
     * @return true if the Pin is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (discs == null);
    }

    /**
     * Returns the Disc on top of this Pin.
     *
     * @return Disc on top or null if no Disc.
     */
    public Disc getTopDisc() {
        if (isEmpty()) {
            return null;
        }
        return discs.getTopDisc();
    }

    /**
     * Returns the total amount of Discs on the current Pin.
     *
     * @return int amount of discs on pin.
     */
    public int discsOnPin() {
        Disc tmp = discs;
        int res = 0;

        while (tmp != null) {
            res++;
            tmp = tmp.getNext();
        }

        return res;
    }

    /**
     * Adds a Disc to the current Pin.
     *
     * @param d Disc to be added.
     */
    public void addDisc(Disc d) {
        if (discs != null) {
            Disc tmp = discs;
            while (tmp.getNext() != null) {
                tmp = tmp.getNext();
            }
            tmp.setNext(d);
        } else {
            discs = d;
        }
    }

    /**
     * @return disk on this pin
     * @throws EmptyPinException if pin is empty
     */
    public Disc popDisc() throws EmptyPinException {
        // Case of an empty Pin.
        if (isEmpty())
            throw new EmptyPinException("Cannot remove disc from an empty pin! Pin " + this.id + " is empty!");

        Disc tmp = discs;

        // There's only a single Disc on this pin. Return it.
        if (discs.getNext() == null) {
            discs = null;
            return tmp;
        }

        // There are at least two discs on this pin. Remove the last one from the list.
        while (tmp.getNext().getNext() != null) {
            tmp = tmp.getNext();
        }
        Disc res = tmp.getNext();
        tmp.setNext(null);
        return res;
    }

    /**
     * @return the level of the biggest disc on the current Pin.
     */
    public int maxLevel() {
        int res = Integer.MAX_VALUE;

        if (!isEmpty() && getTopDisc() != null) {
            res = getTopDisc().getSize() - 1;
        }

        return res;
    }
}
