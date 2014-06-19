package hanoi;

import hanoi.gui.Towers;

public class Hanoi {

	public static void main(String[] args) {
	    Towers window = new Towers("The Hanoi Puzzle");
        window.setVisible(true);
        
        window.addDisc(1,0);
        window.addDisc(2,0);
        window.addDisc(3,0);
        window.addDisc(4,2);
        
        window.moveDisc(3,1);
        window.moveDisc(3,2);
        window.moveDisc(2,1);
        window.moveDisc(3,1);
	}
}