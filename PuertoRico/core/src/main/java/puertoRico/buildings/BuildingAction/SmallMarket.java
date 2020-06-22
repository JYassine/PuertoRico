package puertoRico.buildings.BuildingAction;
import puertoRico.type.TypeBuildingAction;

public class SmallMarket extends BuildingAction {

   public SmallMarket() {
      super();
      this.spaceBuilding=1;
      this.price=1;
      this.victoryPoints=3;
      this.idBuilding=1;
      this.nbAvailable=2;
      setTypeBuildingAction(TypeBuildingAction.SMALLMARKET);
      setName(TypeBuildingAction.SMALLMARKET.toString());

   }


}



