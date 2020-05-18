package cs2030.simulator;

import java.util.Comparator;

/**
 * ServedComparator class represents a comparator that compares ServedEvents.
 */

public class ServedComparator implements Comparator<ServedEvent> {

    /**
     * Compares two ServedEvent based on the serving completion time.
     * 
     * @param e1 The first ServedEvent.
     * @param e2 The second ServedEvent.
     */
    
    @Override
    public int compare(ServedEvent e1, ServedEvent e2) {
        if (e1.getDoneTime() > e2.getDoneTime()) {
            return 1;
        } else {
            return -1;
        }

    }

}
