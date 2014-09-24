package hanoi;

import hanoi.gui.Towers;

import java.util.ArrayList;

public class Hanoi {

	public static void main(String[] args) {
	    Towers window = new Towers("The Hanoi Puzzle", null);
        window.setVisible(true);

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(1);
        
        window.reset(list);
        
        window.moveDisc(3,1);
        window.moveDisc(3,2);
        window.moveDisc(2,1);
        window.moveDisc(3,1);

        window.reset(null);
	}
}