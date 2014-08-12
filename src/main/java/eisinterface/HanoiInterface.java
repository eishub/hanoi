package eisinterface;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.ActException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import hanoi.gui.Towers;
import hanoi.gui.Drawable;

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


    private Towers game = null;
    private Drawable gui = null;

    /**
     * Constructor for the Hanoi Interface.
     */
    public HanoiInterface() throws ManagementException {
    }

    /**
     * Initializes and registers an Entity.
     *
     * @param parameters
     * @throws ManagementException
     */
    public void init(Map<String, Parameter> parameters) throws ManagementException {
        // Setup the game. Add four discs.
        // TODO: Dynamically create world.
        game.addDisc(1, 0);
        game.addDisc(2, 0);
        game.addDisc(3, 0);
        game.addDisc(4, 0);

        reset(parameters);

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
            gui = null; game = null;
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

class Entity {

    private Towers game;
    private int default_pin;

    public Entity(Towers game) {
        this.game = game;
        this.default_pin = 0;
    }

    /**
     * Percept send when a Disc is added.
     *
     * @return position of Disc
     */
    @AsPercept(name = "add", multiplePercepts = true, multipleArguments = false)
    public int add() {
        return default_pin;
    }

    /**
     * @return
     */
    @AsPercept(name = "on", multiplePercepts = true, multipleArguments = true)
    public List<List<Integer>> onPin() {
        List<List<Integer>> list = new ArrayList<List<Integer>>();

        for (Drawable.Disc disc : game.getPins()) {
            List<Integer> props = new ArrayList<Integer>();

            props.add(disc.number);
            props.add(disc.pin);

            if (disc.next != null) {
                props.add(disc.next.number);
                // TODO: If there's no disc, maybe insert a 0 instead to keep the percept on\3?
            }

            list.add(props);
        }

        return list;
    }



    /**
     * Support function to add a disc.
     *
     * @param disc disc number to add.
     * @param p    pin to add the disc to.
     */
    @AsAction(name = "add")
    public void addDisc(int disc, int p) throws ActException {
        game.addDisc(disc, p);
    }

    /**
     * Support function to move a disc.
     *
     * @param disc disc to be moved.
     * @param to   pin to move the disc to.
     */
    @AsAction(name = "move")
    public void moveDisc(int disc, int to) {
        game.moveDisc(disc, to);
    }
}
