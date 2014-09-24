package eisinterface;

import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.RelationException;
import eis.iilang.*;
import hanoi.gui.Drawable;
import hanoi.gui.Towers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides an interface to the Hanoi Tower model.
 *
 * @author Sander van den Oever
 */
@SuppressWarnings("serial")
public class HanoiInterface extends AbstractEnvironment {


    private Towers model = null;
    private Drawable gui = null;

    /**
     * Initializes and registers an Entity.
     *
     * @param parameters parameters for initialization.
     * @throws ManagementException
     */
    public void init(Map<String, Parameter> parameters) throws ManagementException {
        // Prepare the model.
        reset(parameters);

        // Try creating and registering an entity.
        try {
            registerEntity("entity", new Entity(model));
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

        // Build the world based on the provided parameters.
        List<Integer> start = new ArrayList<Integer>();
        Parameter p = parameters.get("discs");

        // Prepare model initialisation data.
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
            start.add(0); // All discs positioned on the first tower (0).
        }

        // Instantiate the model.
        if (model == null) {
            model = new Towers("Hanoi Towers Game", start);

            model.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    try {
                        model.dispose();
                        kill();
                    } catch (ManagementException e1) {
                        e1.printStackTrace();
                    }
                }
            });;
        } else {
            model.reset(start);
        }

        // Make the model-window visible.
        model.setVisible(true);

        setState(EnvironmentState.PAUSED);
    }

    @Override
    public void kill() throws ManagementException {
        if (model != null) {
            model.removeAll(); // TODO Not needed? Dispose / GC takes care of it?
            gui = null;
        }
        try {
            deleteEntity("entity");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!getState().equals(EnvironmentState.KILLED))
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
