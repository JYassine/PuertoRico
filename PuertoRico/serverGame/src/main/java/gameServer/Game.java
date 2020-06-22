package gameServer;
import puertoRico.bot.Bot;
import puertoRico.bot.BotRandom;
import puertoRico.buildings.BuildingAction.*;
import puertoRico.player.Player;
import useful.GameMessage;
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
import java.util.logging.Logger;

/**
 * Classe puertoRico.Game gére le lancement de la partie et les tours de jeux
 */
public class Game{


    private int nbPlayers;
    private Player winner;
    private ArrayList<Player> players;
    private int currentPlayer;
    private ArrayList<Character> characters ;
    private ArrayList<Character> usedCharacters ;
    private Character currentCharacter;
    private Mayor m;
    private int doublonPerPlayer=0;
    private ArrayList<Building> buildings;
    private ArrayList<Plantation> plantations;
    private Stock stock;

    private ControllerStats controllerStats;
    private ConnexionStats connexionStats;
    private ArrayList<Boat> boats;
    private static Logger logger = Logger.getLogger(String.valueOf(Game.class));
    private Level level;
    private boolean activeConnexion;
    private final int maximumBoat=3;

    public Game()  {
        ArrayList<Bot> bots = new ArrayList<>();
        for(int i=0;i<3;i++){
            bots.add(new BotRandom());
        }
        setupGame(3,bots);
    }

    public void setupGame(int nbPlayers, ArrayList<Bot> bots) {
        this.nbPlayers=nbPlayers;
        currentPlayer=0;
        initBoats();
        stock=new Stock(nbPlayers);
        players= new ArrayList<>();
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
        switch (nbPlayers){
            case 4:
                doublonPerPlayer=3;
                break;
            case 5:
                doublonPerPlayer=4;
                break;
            default:
                doublonPerPlayer=2;
                break;
        }

        for(int i=0;i<bots.size();i++){
            players.add(new Player("Joueur"+i,doublonPerPlayer));
            players.get(i).setBot(bots.get(i));
            bots.get(i).setPlayer(players.get(i));
        }

    }

    public void setupGame(int nbPlayers) {
        this.nbPlayers=nbPlayers;
        currentPlayer=0;
        initBoats();
        stock=new Stock(nbPlayers);
        buildings = new ArrayList<>();
        characters = new ArrayList<>();
        plantations = new ArrayList<>();
        usedCharacters = new ArrayList<>();
        players = new ArrayList<>();
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
        switch (nbPlayers){
            case 4:
                doublonPerPlayer=3;
                break;
            case 5:
                doublonPerPlayer=4;
                break;
            default:
                doublonPerPlayer=2;
                break;
        }

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



    public void startGame(){
        if(activeConnexion==false){
            GameMessage.setLevel(level.INFO);
        }
        GameMessage.messageWhenGameStart(nbPlayers);
        this.controllerStats.sendNumberPlayer(nbPlayers);
        int i=0;
        while(!checkEnd()){
            logger.log(level,"************ Tour: " + (i + 1) + " ************");
            if(this.playTurn()) {
                endGame();
                break;
            }
            logger.log(level,"***********************************");
            logger.log(level,"***********************************");
            i++;
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
    public boolean playTurn()
    {
        for(int i=0;i<nbPlayers;i++)
        {
            if(playSmallTurn())
                return true;
        }
        currentPlayer++;
        currentPlayer=currentPlayer%nbPlayers;
        for (Character c: characters) {
            c.bonusIncrement();
        }
        characters.addAll(usedCharacters);
        usedCharacters.clear();
        checkEnd();
        return false;
    }

    public ArrayList<Character> getUsedCharacters() {
        return usedCharacters;
    }

    public boolean playSmallTurn() {
        currentCharacter = players.get(currentPlayer).getBot().chooseCharacter(characters);
        characters.remove(currentCharacter);
        usedCharacters.add(currentCharacter);
        logger.log(level,"--------------------------------");
        GameMessage.messageWhenCharacterChoosen(players.get(currentPlayer),currentCharacter);
        logger.log(level,"--------------------------------");
        players.get(currentPlayer).playRole(currentCharacter);
        controllerStats.sendNbMove();
        for (int i = 1; i < nbPlayers; i++) {
            controllerStats.sendNbMove();
            players.get((i+currentPlayer)%nbPlayers).playRole(currentCharacter);
        }

        currentPlayer++;
        currentPlayer=currentPlayer%nbPlayers;
        if(checkEnd()){
            return true;
        }
        return false;
    }


    public Character getCurrentCharacter() {
        return currentCharacter;
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Character> getCharacterList()
    {
        return this.characters;
    }

    public ArrayList<Building> getBuildingList()
    {
        return this.buildings;
    }

    public boolean checkEnd()
    {
        for (Player p: players) {
            if(p.hasFullCity()) {
                GameMessage.messageWhenCityFull(p);
                return true;
            }
        }
        if(m.isEnd()) {
            GameMessage.messageWhenNoColonist();
            return true;
        }
        return false;
    }



    public void endGame() {
        Player winner =  players.get(0);

        for(int i = 0;i < nbPlayers;i++) {
            if( players.get(i).getInventory().getDoublons() > winner.getInventory().getDoublons())
                winner =  players.get(i);
        }
        this.winner=winner;


        GameMessage.messageWhenGameEnd(winner,winner.getInventory().getVictoryPoints());
        for(Player p : players){
            checkGameEven(p);
            if(winner.getName().equals(p.getName())){
                winner.setWinner(true);
                controllerStats.sendInfoPlayer(winner);
            }else{
                winner.setWinner(false);
                controllerStats.sendInfoPlayer(p);
            }
        }

        if(activeConnexion){
            controllerStats.sendGameFinished();
        }else{
            System.exit(0);
        }

    }

    private boolean checkGameEven(Player p){
        boolean gameEven=false;
        for(Player player : players){
            if(!p.getName().equals(player.getName()) && p.getInventory().getVictoryPoints()==player.getInventory().getVictoryPoints()){
                p.setGameEven(true);
                player.setGameEven(true);
                gameEven=true;
            }
        }
        return gameEven;
    }

    public Player getWinner() {
        return winner;
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
