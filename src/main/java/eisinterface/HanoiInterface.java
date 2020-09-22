package eisinterface;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import hanoi.gui.Towers;

/**
 * Provides an interface to the Hanoi Tower model.
 *
 * @author Sander van den Oever
 */
@SuppressWarnings("serial")
public class HanoiInterface extends AbstractEnvironment {
	private Towers model = null;

	/**
	 * Initializes and registers an Entity.
	 *
	 * @param parameters parameters for initialization.
	 * @throws ManagementException
	 */
	@Override
	public void init(final Map<String, Parameter> parameters) throws ManagementException {
		// Prepare the model.
		reset(parameters);

		// Try creating and registering an entity.
		try {
			registerEntity("entity", "hanoicontroller", new Entity(this.model));
		} catch (final EntityException e) {
			throw new ManagementException("Could not create an entity", e);
		}
	}

	/**
	 * Creates a new model if not already there. If there is already a model, it
	 * resets the model to the given new size. Resets the environment(-interface)
	 * with a set of key-value-pairs.
	 *
	 * @param parameters
	 * @throws ManagementException is thrown either when the initializing is not
	 *                             supported or the parameters are wrong.
	 */
	@Override
	public void reset(final Map<String, Parameter> parameters) throws ManagementException {
		// Build the world based on the provided parameters.
		final List<Integer> start = new ArrayList<>();
		final Parameter p = parameters.get("discs");

		// Prepare model initialisation data.
		if (p != null) {
			if (p instanceof ParameterList) {
				final ParameterList list = (ParameterList) p;
				for (final Parameter x : list) {
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
		if (this.model == null) {
			this.model = new Towers("Hanoi Towers Game", start);

			this.model.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(final WindowEvent e) {
					try {
						HanoiInterface.this.model.dispose();
						kill();
					} catch (final ManagementException e1) {
						e1.printStackTrace();
					}
				}
			});

		} else {
			this.model.reset(start);
		}

		// Make the model-window visible.
		this.model.setVisible(true);

		setState(EnvironmentState.PAUSED);
	}

	@Override
	public void kill() throws ManagementException {
		if (this.model != null) {
			this.model.removeAll(); // TODO Not needed?
		}
		try {
			deleteEntity("entity");
		} catch (final Exception e) {
			e.printStackTrace();
		}
		if (!getState().equals(EnvironmentState.KILLED)) {
			setState(EnvironmentState.KILLED);
		}
	}

	/**
	 * Returns true if the action is supported by the environment.
	 *
	 * @return true if the action is supported by the environment
	 */
	@Override
	protected boolean isSupportedByEnvironment(final Action action) {
		if (action.getName().equals("add") && action.getParameters().size() == 2) {
			return true;
		} else if (action.getName().equals("move") && action.getParameters().size() == 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if the action is supported by the type.
	 *
	 * @return Returns true if the action is supported by the type
	 */
	@Override
	protected boolean isSupportedByType(final Action action, final String type) {
		return isSupportedByEnvironment(action);
	}
}
