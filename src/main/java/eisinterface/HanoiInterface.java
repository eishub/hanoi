package eisinterface;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.*;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import hanoi.gui.Towers;

import java.util.Map;

/**
 * Provides an interface to the Hanoi Tower game.
 *
 * @author Sander van den Oever
 */
@SuppressWarnings("serial")
public class HanoiInterface extends AbstractEnvironment {

	private Towers game = null;

    /**
     * Constructor for the Hanoi Interface.
     */
	public HanoiInterface() throws ManagementException {
        // Create a game instance
        game = new Towers("Testing through EIS");
        game.setVisible(true);
    }

    /**
     * Initializes and registers an Entity.
     * @param parameters
     * @throws ManagementException
     */
    public void init(Map<String, Parameter> parameters) throws ManagementException {
        super.init(parameters);

        // Try creating and registering an entity called gripper.
        reset(parameters);
        try {
            registerEntity("gripper", new Entity(game));
        } catch (EntityException e) {
        	throw new ManagementException("Could not create a gripper", e);
        }
    }
    
	/**
	 * Creates a new model if not already there. If there is already a model, it
	 * resets the model to the given new size. Resets the environment(-interface)
	 * with a set of key-value-pairs.
	 * 
	 * @param parameters
	 * @throws ManagementException
	 *             is thrown either when the initializing is not supported or
	 *             the parameters are wrong.
	 */
	@Override
	public void reset(Map<String, Parameter> parameters)
			throws ManagementException {

		// TODO: must implement reset.
		// TODO: use Model-View-Controller setup, see blocksworld project.
		setState(EnvironmentState.PAUSED);
	}
	
	@Override
	public void kill() throws ManagementException {
		if (game != null) {
			game.removeAll(); // TODO: does this clean up all gui components correctly?
			game = null;
		}
		// TODO: model = null;
		setState(EnvironmentState.KILLED);
	}

	/**
	 * Returns true if the action is supported by the environment.
	 *
	 * @return true if the action is supported by the environment
	 */
	@Override
	protected boolean isSupportedByEnvironment(Action action) {
        if (action.getName().equals("add") && action.getParameters().size()==2) {
        	return true;
        }
        if (action.getName().equals("move") && action.getParameters().size()==2) {
        	return true;
        }
		return false;
	}

	/**
	 * Returns true if the action is supported by the type.
	 *
	 * @return Returns true if the action is supported by the type
	 */
	@Override
	protected boolean isSupportedByType(Action action, String type) {
        return isSupportedByEnvironment(action);
    }
}

class Entity {

    private Towers game;
    private int default_pin;
    private int discs; // TODO: not used?

    public Entity(Towers game) {
        this.game = game;
        this.default_pin = 0;
        this.discs = 0;
    }

    /**
     * Percept send when a Disc is added.
     * @return position of Disc
     */
    @AsPercept(name = "add", multiplePercepts = true, multipleArguments = false)
    public int add() {
        return default_pin;
    }

    /**
     * Percept send when a Disc is moved.
     * @return success.
     */
    @AsPercept(name = "move", multiplePercepts = true, multipleArguments = false)
    public String move() {
        return "success";
    }

    /**
     * Support function to add a disc.
     *
     * @param disc
     * 		disc number to add.
     * @param p
     * 		pin to add the disc to.
     */
    @AsAction(name = "add")
    public void addDisc(int disc, int p) throws ActException {
        game.addDisc(disc, p);
        discs++;
    }

    /**
     * Support function to move a disc.
     *
     * @param disc
     * 		disc to be moved.
     * @param to
     * 		pin to move the disc to.
     */
    @AsAction(name = "move")
    public void moveDisc(int disc, int to) {
        game.moveDisc(disc, to);
    }
}