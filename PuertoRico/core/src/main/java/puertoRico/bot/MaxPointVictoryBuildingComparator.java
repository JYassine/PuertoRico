package puertoRico.bot;

import puertoRico.buildings.BuildingAction.Building;

import java.util.Comparator;

public class MaxPointVictoryBuildingComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Building b1 = (Building) o1;
        Building b2 = (Building)o2;
        return b2.getVictoryPoints()-b1.getVictoryPoints();
    }
}
