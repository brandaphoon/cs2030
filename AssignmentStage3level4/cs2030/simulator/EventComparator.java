package cs2030.simulator;

import java.util.Comparator;

/**
 * EventComparator class represents a comparator that compares events.
 */

public class EventComparator implements Comparator<Event> {

    /**
     * Compares two events according to the time of the event.
     * 
     * @param e1 The first event.
     * @param e2 The second event.
     */

    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getTime() > e2.getTime()) {
            return 1;
        } else {
            return -1;
        }

    }

}
