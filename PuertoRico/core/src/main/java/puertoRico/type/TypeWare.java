package puertoRico.type;

public enum TypeWare {

    CORN("Corn",0,false),
    INDIGO("Indigo",1,true),
    SUGAR("Sugar",2,true),
    COFFEE("Coffee",3,true),
    TOBACCO("Tobacco",4,true);

    private String resources;
    private int value;
    private boolean requireBuildingForProduction;

    TypeWare(String resources, int value,boolean requireBuildingForProduction){
        this.resources=resources;
        this.value=value;
        this.requireBuildingForProduction=requireBuildingForProduction;
    }

    @Override
    public String toString() {
        return resources;
    }
    public int getValue(){return value;}
    public boolean requireBuilding(){return requireBuildingForProduction;}
}
