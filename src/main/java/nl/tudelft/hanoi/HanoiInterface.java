package nl.tudelft.hanoi;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.*;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import hanoi.gui.Towers;

import java.util.List;
import java.util.Map;

/**
 * Provides an interface to the Hanoi Tower game.
 *
 * @author Sander van den Oever
 */
public class HanoiInterface extends AbstractEnvironment {

	public Towers game = null;

    /**
     * Constructor for the Hanoi Interface.
     */
	public HanoiInterface() throws ManagementException {
        // Create a Game instance
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

        // Try registering the Entity.
        try {
            registerEntity("agent", new Entity(game));
        } catch (EntityException e) {
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
        if (action.getName().equals("add")) { return true; }
        if (action.getName().equals("move")) { return true; }
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
    private int discs;

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