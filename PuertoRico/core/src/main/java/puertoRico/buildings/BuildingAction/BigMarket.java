package puertoRico.buildings.BuildingAction;

import puertoRico.type.TypeBuildingAction;

public class BigMarket extends BuildingAction{

    public BigMarket() {
        super();
        this.spaceBuilding=1;
        this.price=5;
        this.victoryPoints=8;
        this.idBuilding=2;
        this.nbAvailable=2;
        setTypeBuildingAction(TypeBuildingAction.BIGMARKET);
        setName(TypeBuildingAction.BIGMARKET.toString());

    }

}
