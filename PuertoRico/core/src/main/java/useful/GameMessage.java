package useful;


import puertoRico.buildings.BuildingAction.Building;
import puertoRico.characters.Character;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.type.TypeWare;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameMessage {


    private static Logger logger = Logger.getLogger(String.valueOf(GameMessage.class));
    private static Level level = Level.FINE;

    private GameMessage(){
    }

    // the usual print
    public static void show(String s){
        logger.fine(s);
    }
    // print used for debug ; informations of the game's state
    public static void showDebug(String s){
        logger.finest(s);
    }
    public static void showSudo(String s){ // a special "show" : this prints no matter what
        // display mode is set
        logger.warning(s);
    }

    public static void setLevel(Level level) {
        GameMessage.level = level;
    }

    public static Level getLevel() {
        return level;
    }

    public static void messageWhenGameStart(int nbPlayers){
        logger.log(level,"The game will begin with "+nbPlayers+" players...");
        logger.log(level,"The game started... !");
    }

    public static void messageWhenCharacterChoosen(Player p, Character c)
    {
        logger.log(level,"The player "+ColorText.ANSI_PURPLE+p.getName()+ColorText.ANSI_WHITE+" choose to play the "+ColorText.ANSI_RED+c.toString()+ColorText.ANSI_WHITE);
    }

    public static void messageWhenBonus(Player p, int bonus)
    {
        logger.log(level,"The player "+ColorText.ANSI_PURPLE+p.getName()+ColorText.ANSI_WHITE+" won "+ColorText.ANSI_YELLOW+bonus+" doublon "+ColorText.ANSI_WHITE+"for choosing this character !");
    }

    public static void messageWhenProduct(Player p, int n, TypeWare t){
        logger.log(level,"The player "+ColorText.ANSI_PURPLE+p.getName()+ColorText.ANSI_WHITE+" producted "+ColorText.ANSI_BLUE+n+" "+t.toString()+" barrels"+ColorText.ANSI_WHITE);
    }

    public static void messageWhenConstruct(Player p ,Building o)
    {
        logger.log(level,"The "+ColorText.ANSI_PURPLE+p.getName()+ColorText.ANSI_WHITE+" constructed "+ColorText.ANSI_BLUE+o.toString()+ColorText.ANSI_WHITE+" for "+ColorText.ANSI_YELLOW+o.getPrice()+" doublons "+ColorText.ANSI_WHITE+ColorText.ANSI_WHITE+" and won "+ColorText.ANSI_YELLOW+o.getVictoryPoints()+" VictoryPoints "+ColorText.ANSI_WHITE) ;
    }

    public static void messageWhenPlant(Player p , Plantation o)
    {
        logger.log(level,"The "+ColorText.ANSI_PURPLE+p.getName()+ColorText.ANSI_WHITE+" planted "+ColorText.ANSI_GREEN+o.toString()+ColorText.ANSI_WHITE);
    }

    public static void messageWhenSell(Player p, TypeWare t, int i)
    {
        logger.log(level,"The player "+ColorText.ANSI_PURPLE+p.getName()+ColorText.ANSI_WHITE+" choose to sell 1 ware of type "+ColorText.ANSI_YELLOW+t.toString()+ColorText.ANSI_WHITE);
        logger.log(level,"The player " + p.getName()+" won "+ColorText.ANSI_YELLOW+i+" doublon !"+ColorText.ANSI_WHITE);
    }

    public static void messageWhenLoadBoat(Player p,int numberWares, TypeWare typeWare, int numberBoat){
        logger.log(level,"The player "+p.getName()+" load "+ColorText.ANSI_YELLOW+numberWares+ColorText.ANSI_WHITE+ " "+ typeWare +" in the boat "+ ColorText.ANSI_RED+numberBoat+ColorText.ANSI_WHITE);

    }

    public static void messageWhenBoatSended(Player p, int numberBoat,int d){
        logger.log(level,"The player "+ColorText.ANSI_PURPLE+p.getName()+ColorText.ANSI_WHITE+" send the boat "+ ColorText.ANSI_RED+numberBoat+ColorText.ANSI_WHITE);
        if(d>0)
            logger.log(level,"The player "+p.getName()+ ColorText.ANSI_YELLOW+" gain "+d+" victory points !"+ColorText.ANSI_WHITE);
    }
    public static void messageWhenReceiveColonist(Player p , int c)
    {
        if(c>0)
            logger.log(level,"The player " + p.getName()+" received "+ColorText.ANSI_CYAN+c+" Colonist !"+ColorText.ANSI_WHITE);
    }

    public static void messageWhenColonistPut(Player p , ArrayList<String> building)
    {
        if(!building.isEmpty())
            logger.log(level,"The player " + p.getName()+" has put "+ColorText.ANSI_CYAN+"1 Colonist "+ColorText.ANSI_WHITE+" on "+building);
    }

    public static void messageWhenUniversityUsed(Player p,Building b)
    {
        logger.log(level,"The player " + p.getName()+" has put "+ColorText.ANSI_CYAN+"1 Colonist "+ColorText.ANSI_WHITE+" on "+b+" with his University");
    }

    public static void messageWhenHospiceUsed(Player p,Plantation plantation)
    {
        logger.log(level,"The player " + p.getName()+" has put "+ColorText.ANSI_CYAN+"1 Colonist "+ColorText.ANSI_WHITE+" on "+plantation+" with his Hospice");
    }

    public static void messageWhenCityFull(Player p){
        logger.log(level,"The player "+p.getName()+" has a completed his city, the game has ended");
    }

    public static void messageWhenColonistsDistributed(int nbColonist){
        logger.log(level,"All colonists have been handed out by the mayor the next boat will have :"+ColorText.ANSI_CYAN+nbColonist+" Colonist "+ColorText.ANSI_WHITE);
    }

    public static void messageWhenNoColonist()
    {
        logger.log(level,"There are no colonists left, the game has ended");
    }

    public static void messageWhenSellChamberEmptyed(){
        logger.log(level,"The sell chamber was full, wares have been sold and replaced in stock.");
    }

    public static void messageWhenGameEnd(Player p ,int n)
    {
        logger.log(level,"The game is finished ");
        logger.log(level,"***********************************");
        logger.log(level, p.getName() + " is the Winner with " + n + " Victory Points");
        logger.log(level,"***********************************");
    }


}
