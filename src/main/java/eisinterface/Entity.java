package eisinterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.exceptions.ActException;
import hanoi.gui.Drawable;
import hanoi.gui.Towers;

/**
 * Entity class, creates a controllable entity to the model that can be
 * controlled by an agent-programming platform.
 *
 * @author Sander van den Oever
 */
public class Entity {
	private final Towers model;

	public Entity(final Towers model) {
		this.model = model;
	}

	/**
	 * TODO: Send this percept only on initialization.
	 *
	 * @return Returns a list of the current discs in the model and their levels.
	 */
	@AsPercept(name = "disc", multiplePercepts = true, multipleArguments = true)
	public List<List<Integer>> discs() {
		final List<List<Integer>> discs = new LinkedList<>();

		if (this.model.getPins() != null) {
			for (Drawable.Disc disc : this.model.getPins()) {
				while (disc != null) {
					final List<Integer> entry = new ArrayList<>(2);
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
	 *
	 * @return returns a percept indicating the amount of pins.
	 */
	@AsPercept(name = "pins")
	public int pins() {
		return this.model.getPins().length;
	}

	/**
	 * @return returns a list with all discs with their pins, and the discs below
	 *         them.
	 */
	@AsPercept(name = "on", multiplePercepts = true, multipleArguments = true)
	public List<List<Integer>> onPin() {
		final List<List<Integer>> list = new LinkedList<>();

		if (this.model.getPins() != null) {
			for (Drawable.Disc disc : this.model.getPins()) {
				// Disc now is the first disc on the current pin
				while (disc != null) {
					final List<Integer> entry = new ArrayList<>(3);
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
	 *             TODO pins[to] possible out of bounds
	 */
	@AsAction(name = "move")
	public void moveDisc(final int disc, final int to) throws ActException {
		if (this.model.discExists(disc)) {
			this.model.moveDisc(disc, to);
		} else {
			throw new IllegalArgumentException("Disc does not exist.");
		}
	}
}