package nl.tudelft.hanoi;

import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.*;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Percept;
import hanoi.gui.Towers;

/**
 * Provides an interface to the Hanoi Tower game.
 *
 * @author Sander van den Oever
 */
public class HanoiInterface extends AbstractEnvironment {

	private Towers game = null;

    /**
     * Constructor for the Hanoi Interface.
     */
	public HanoiInterface() {
		// Create a Game instance
		game = new Towers("Testing through EIS");
		try {
			this.addEntity("agent");
		} catch(EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Support function to add a disc.
	 *
	 * @param disc
	 * 		disc number to add.
	 * @param p
	 * 		pin to add the disc to.
	 */
	public void actionAddDisc(int disc, int p) {
		game.addDisc(disc, p);
		Percept percept = new Percept("add", new Identifier(""), new Identifier(""));
		try {
			notifyAgentsViaEntity(percept, "agent");
		} catch(EnvironmentInterfaceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Support function to move a disc.
	 *
	 * @param disc
	 * 		disc to be moved.
	 * @param to
	 * 		pin to move the disc to.
	 */
	public void actionMoveDisc(int disc, int to) {
		game.moveDisc(disc, to);
		Percept percept = new Percept("move", new Identifier(""), new Identifier(""));
		try {
			notifyAgentsViaEntity(percept, "agent");
		} catch(EnvironmentInterfaceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns true if the action is supported by the environment.
	 *
	 * @return true if the action is supported by the environment
	 */
	@Override
	protected boolean isSupportedByEnvironment(Action action) {
		return true;
	}

	/**
	 * Returns true if the action is supported by the type.
	 *
	 * @return Returns true if the action is supported by the type
	 */
	@Override
	protected boolean isSupportedByType(Action action, String type) {
        return true;
    }
}
