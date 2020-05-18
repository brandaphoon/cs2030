package cs2030.simulator;

import java.util.Comparator;

public class ServedComparator implements Comparator<ServedEvent> {

    @Override
    public int compare(ServedEvent e1, ServedEvent e2) {
        if (e1.getDoneTime() > e2.getDoneTime()) {
            return 1;
        } else {
            return -1;
        } 
        
    }

}
