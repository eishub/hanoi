package hanoi.eis;

import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.iilang.*;
import hanoi.Hanoi;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides an interface to the Hanoi Tower game.
 *
 * @author Sander van den Oever
 */
@SuppressWarnings("serial")
public class HanoiInterface extends AbstractEnvironment {

    // Game instance, with possibility to perform actions on the game.
    private Hanoi controller = null;

    /**
     * Initializes and registers an Entity.
     *
     * @param parameters parameters for initialization.
     * @throws ManagementException
     */
    public void init(Map<String, Parameter> parameters) throws ManagementException {
        // Prepare the game.
        reset(parameters);

        // Try creating and registering an entity.
        try {
            registerEntity("entity", new Entity(controller));
        } catch (EntityException e) {
            throw new ManagementException("Could not create gripper entity", e);
        }
    }

    /**
     * Creates a new model if not already there. If there is already a model, it
     * resets the model to the given new size. Resets the environment(-interface)
     * with a set of key-value-pairs.
     *
     * @param parameters List of Numbers containing data to setup the game.
     * @throws ManagementException is thrown either when the initializing is not supported or
     *                             the parameters are wrong.
     */
    @Override
    public void reset(Map<String, Parameter> parameters)
            throws ManagementException {

        // Build the world based on the provided parameters.
        List<Integer> start = new ArrayList<>();
        Parameter p = parameters.get("discs");

        // Prepare game initialization data.
        if (p != null) {
            if (p instanceof ParameterList) {
                ParameterList list = (ParameterList) p;
                for (Parameter x : list) {
                    // Check for valid parameters and add them to the list.
                    if (!(x instanceof Numeral)) {
                        throw new IllegalArgumentException("Numbers expected but found " + x);
                    }
                    start.add(((Numeral) x).getValue().intValue());
                }
            } else {
                throw new IllegalArgumentException("Expected a list of positions or nothing but found " + p);
            }
        } else {
            // Use default setup with 4 discs.
            start.add(0);
            start.add(0);
            start.add(0);
            start.add(0); // All discs positioned on the first pin (0).
        }

        // Instantiate the game.
        if (controller == null) {
            controller = new Hanoi(start);
            controller.getUI().addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    try {
                        deleteEntity("entity");
                        kill();
                    } catch (Exception ex) {
                        // TODO
                    }
                }
            });
        } else {
            controller.reset(start);
        }

        setState(EnvironmentState.PAUSED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void kill() throws ManagementException {
        if (controller != null) {
            controller.exitGame();
            controller = null;
        }
        setState(EnvironmentState.KILLED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isSupportedByEnvironment(Action action) {
        return action.getName().equals("move") && action.getParameters().size() == 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isSupportedByType(Action action, String type) {
        return isSupportedByEnvironment(action);
    }
}