package hanoi.elements;

/**
 * A disc can be placed on a pin.
 * Bigger discs cannot be placed on smaller discs.
 *
 * @author Sander van den Oever
 */
public class Disc {

    private int id;
    private int size;
    private Disc next;

    /**
     * Public constructor.
     *
     * @param id   id of the Disc
     * @param size size of the Disc (width)
     */
    public Disc(int id, int size, Pin pin) {
        this.id = id;
        this.size = size;
        this.next = null;
    }

    /**
     * Returns the top Disc seen from the current Disc, or returns the current
     * Disc if no Disc is on top of this Disc.
     *
     * @return Disc on top.
     */
    public Disc getTopDisc() {
        if (next == null)
            return this;
        else
            return next.getTopDisc();
    }

    /**
     * Get the Disc on top of the current Disc.
     *
     * @return Disc next Disc or null in case this is the Disc on top.
     */
    public Disc getNext() {
        return next;
    }

    /**
     * Change the reference to which Disc is on top of the current one.
     *
     * @param next Disc Disc to insert into the reference.
     */
    public void setNext(Disc next) {
        this.next = next;
    }

    /**
     * Returns the size of the current Disc.
     *
     * @return int size of the Disc.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the ID of the Disc.
     *
     * @return int ID of the Disc.
     */
    public int getID() {
        return this.id;
    }

}
