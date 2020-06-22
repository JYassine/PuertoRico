package puertoRico.boat;

import java.util.Comparator;

public class MaximumCapacityBoatComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Boat b1 = (Boat)o1;
        Boat b2 = (Boat)o2;
        return b2.getMaximumCapacity()-b1.getMaximumCapacity();
    }
}
