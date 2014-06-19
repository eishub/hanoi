package eishanoi;

import eis.*;
import eis.exceptions.*;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Percept;
import hanoi.gui.Towers;
import org.omg.CORBA.Environment;

import java.util.LinkedList;

/**
 * Provides an interface to the Hanoi Tower game.
 *
 * @author Sander van den Oever
 */
public class HanoiEnvironmentInterface extends EIDefaultImpl {

	private Towers game = null;

	public HanoiEnvironmentInterface() {
		// Create a Game instance
		game = new Towers("Testing through EIS");
		try {
			this.addEntity("agent");
		} catch(EntityException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	/**
	 * Support function to add a disc.
	 *
	 * @param disc
	 * 		disc number to add.
	 * @param p
	 * 		pin to add the disc to.
	 */
	public void actionaddDisc(int disc, int p) {
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
	public void actionmoveDisc(int disc, int to) {
		game.moveDisc(disc, to);
		Percept percept = new Percept("move", new Identifier(""), new Identifier(""));
		try {
			notifyAgentsViaEntity(percept, "agent");
		} catch(EnvironmentInterfaceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets all percepts of an entity.
	 * <p/>
	 * This method must be overridden.
	 *
	 * @param entity
	 * 		is the entity whose percepts should be retrieved.
	 *
	 * @return a list of percepts.
	 */
	@Override
	protected LinkedList<Percept> getAllPerceptsFromEntity(String entity) throws PerceiveException, NoEnvironmentException {
		LinkedList<Percept> percepts = new LinkedList<Percept>();

		if (game == null) throw new NoEnvironmentException("Environment not started!");



		return percepts;
	}

	/**
	 * Returns true if the action is supported by the environment.
	 *
	 * @return true if the action is supported by the environment
	 */
	@Override
	protected boolean isSupportedByEnvironment(Action action) {
		return false;
	}

	/**
	 * Returns true if the action is supported by the type.
	 *
	 * @return Returns true if the action is supported by the type
	 */
	@Override
	protected boolean isSupportedByType(Action action, String type) {
		return false;
	}

	/**
	 * Returns true if the action is supported by the entity.
	 *
	 * @return true if action supported by entity.
	 */
	@Override
	protected boolean isSupportedByEntity(Action action, String entity) {
		return false;
	}

	/**
	 * @return Percept that is result of the action.
	 */
	@Override
	protected Percept performEntityAction(String entity, Action action) throws ActException {
		return null;
	}

	/**
	 * Returns the EIS-runtime-version that is compatible.
	 */
	@Override
	public String requiredVersion() {
		return null;
	}
}
