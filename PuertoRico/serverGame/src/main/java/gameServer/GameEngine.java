package gameServer;
import config.ConfigServer;
import puertoRico.buildings.BuildingAction.*;
import puertoRico.boat.Boat;
import puertoRico.characters.*;
import puertoRico.characters.Character;
import puertoRico.plantations.CoffeePlantation;
import puertoRico.plantations.CornPlantation;
import puertoRico.plantations.IndigoPlantation;
import puertoRico.plantations.TobaccoPlantation;
import puertoRico.plantations.Plantation;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.buildings.BuildingIndustrial.TobaccoStorage;
import puertoRico.buildings.BuildingIndustrial.CoffeeRoaster;
import puertoRico.stock.Stock;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Classe puertoRico.Game gére le lancement de la partie et les tours de jeux
 */
public class GameEngine{


    private int nbPlayers;
    private int currentPlayer;
    private ArrayList<Character> characters ;
    private ArrayList<Character> usedCharacters ;
    private Mayor m;
    private ArrayList<Building> buildings;
    private ArrayList<Plantation> plantations;
    private Stock stock;
    private ControllerStats controllerStats;
    private ConnexionStats connexionStats;
    private ArrayList<Boat> boats;
    private Level level;
    private boolean activeConnexion;
    private final int maximumBoat=3;

    public GameEngine(int nbPlayers)  {
        this.controllerStats = new ControllerStats();
        this.connexionStats=new ConnexionStats(ConfigServer.hostAddress,ConfigServer.port,controllerStats);
        this.connexionStats.connectingToServer();
        this.nbPlayers=nbPlayers;
        currentPlayer=0;
        initBoats();
        stock=new Stock(nbPlayers);
        buildings = new ArrayList<>();
        characters = new ArrayList<>();
        plantations = new ArrayList<>();
        usedCharacters = new ArrayList<>();
        buildings.add(new SmallMarket());
        buildings.add(new SmallIndigoPlant());
        buildings.add(new CoffeeRoaster());
        buildings.add(new TobaccoStorage());
        buildings.add(new Office());
        buildings.add(new University());
        buildings.add(new BigMarket());
        buildings.add(new Hospice());
        plantations.add(new IndigoPlantation());
        plantations.add(new CornPlantation());
        plantations.add(new CoffeePlantation());
        plantations.add(new TobaccoPlantation());
        characters.add(new Builder(buildings,stock));
        characters.add(new Trader(stock,nbPlayers));
        characters.add(new Farmer(plantations,stock));
        characters.add(new Craftman(stock));
        characters.add(new Captain(boats,stock));
        m=new Mayor(nbPlayers,stock);
        characters.add(m);
        currentPlayer=0;
        level=Level.FINE;

    }


    public ControllerStats getControllerStats() {
        return controllerStats;
    }

    public void connectToServerStats(){
        this.connexionStats.connectingToServer();
    }

    private void initBoats(){
        boats= new ArrayList<>();
        int maximumCapacity=6;
        for (int i =0;i<maximumBoat;i++){
            Boat b = new Boat(i);
            b.setMaximumCapacity(maximumCapacity);
            boats.add(b);
            maximumCapacity-=1;
        }
    }


    public Stock getStock() {
        return stock;
    }

    public int getCurrentPlayer(){return currentPlayer;}

    public void updateCurrentPlayer(int nbPlayers){
        currentPlayer++;
        currentPlayer=currentPlayer%nbPlayers;
    }

    public ArrayList<Character> getUsedCharacters() {
        return usedCharacters;
    }


    public void updateCharacterList(ArrayList<Character> characters,Character character){
        for(int i=0;i<characters.size();i++){
            if(characters.get(i).equals(character)){
                if(character.equals(m)){
                    this.m = (Mayor) character;
                }
                characters.remove(i);
                characters.add(character);
                break;
            }
        }
    }


    public ArrayList<Character> getCharacterList()
    {
        return this.characters;
    }

    public ArrayList<Building> getBuildingList()
    {
        return this.buildings;
    }

    public boolean checkEnd(){
        if(m.isEnd()) {
            return true;
        }
        return false;

    }


    public void setControllerStats(ControllerStats controllerStats) {
        this.controllerStats = controllerStats;
    }

    public void setConnexionStats(ConnexionStats connexionStats) {
        this.connexionStats = connexionStats;
    }

    public boolean isActiveConnexion() {
        return activeConnexion;
    }

    public void setActiveConnexion(boolean activeConnexion) {
        this.activeConnexion = activeConnexion;
    }


    public int getNbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }
}
