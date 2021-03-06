package hanoi;

import java.util.ArrayList;
import java.util.List;

import eis.exceptions.ActException;
import hanoi.gui.Towers;

/**
 * Simple testing class.
 */
public class Hanoi {
	public static void main(final String... args) throws ActException {
		final Towers window = new Towers("The Hanoi Puzzle", null);
		window.setVisible(true);

		final List<Integer> list = new ArrayList<>(4);
		list.add(0);
		list.add(0);
		list.add(0);
		list.add(1);

		window.reset(list);

		window.printDiscs();
		window.moveDisc(3, 1);
		window.printDiscs();
		window.moveDisc(3, 2);
		window.printDiscs();
		window.moveDisc(2, 1);
		window.printDiscs();
		window.moveDisc(3, 1);
		window.printDiscs();
		window.moveDisc(4, 2);
		window.printDiscs();
	}
}