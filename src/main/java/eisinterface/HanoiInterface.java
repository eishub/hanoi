package eisinterface;

import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import hanoi.gui.Drawable;
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
    private Drawable gui = null;

    /**
     * Initializes and registers an Entity.
     *
     * @param parameters parameters for initialization.
     * @throws ManagementException
     */
    public void init(Map<String, Parameter> parameters) throws ManagementException {
        // Prepare the game.
        reset(parameters);

        // Setup the game. Add four discs.
        // TODO: Dynamically create world.
        game.addDisc(1, 0);
        game.addDisc(2, 0);
        game.addDisc(3, 0);
        game.addDisc(4, 0);

        // Try creating and registering an entity.
        try {
            registerEntity("entity", new Entity(game));
        } catch (EntityException e) {
            throw new ManagementException("Could not create an entity", e);
        }
    }

    /**
     * Creates a new model if not already there. If there is already a model, it
     * resets the model to the given new size. Resets the environment(-interface)
     * with a set of key-value-pairs.
     *
     * @param parameters
     * @throws ManagementException is thrown either when the initializing is not supported or
     *                             the parameters are wrong.
     */
    @Override
    public void reset(Map<String, Parameter> parameters)
            throws ManagementException {

        if (game != null) {
            // Dispose the current game
            gui.things = null; // TODO: necessary or not?
            game.dispose();
            game = null;
        }

        // Create a game instance
        game = new Towers("Testing through EIS");
        gui = game.getCanvas();
        game.setVisible(true);

        setState(EnvironmentState.PAUSED);
    }

    @Override
    public void kill() throws ManagementException {
        if (game != null) {
            gui.things = null;
            game.removeAll(); // TODO: does this clean up all gui components correctly?
            gui = null;
            game = null;
        }
        setState(EnvironmentState.KILLED);
    }

    /**
     * Returns true if the action is supported by the environment.
     *
     * @return true if the action is supported by the environment
     */
    @Override
    protected boolean isSupportedByEnvironment(Action action) {
        if (action.getName().equals("add") && action.getParameters().size() == 2) {
            return true;
        }
        if (action.getName().equals("move") && action.getParameters().size() == 2) {
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
