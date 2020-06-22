package stats;
import java.text.DecimalFormat;
import java.util.*;

public class Statistics implements ActionStatisticHandler {

    private int nbMoveInGame;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private HashMap<Integer,ArrayList<InfoPlayer>> dataBaseStats;
    private String delimiter=", ";
    private int numberTotalGame;

    public int getNumberPlayer() {
        return numberPlayer;
    }

    private int numberPlayer;
    private Set<String> namePlayers;

    public Statistics(){
        this.nbMoveInGame=0;
        dataBaseStats = new HashMap<>();
        namePlayers = new HashSet<>();
        numberTotalGame = 0;
        numberPlayer=0;
    }

    public void setNumberPlayer(int numberPlayer) {
        this.numberPlayer = numberPlayer;
    }

    public int getNumberGame() {
        return numberTotalGame;
    }

    @Override
    public void calculateNbMoveInGame(int nbMove) {
        nbMoveInGame+=nbMove;
    }

    public void save(int numberGame,InfoPlayer infoPlayer){
        if(dataBaseStats.get(numberGame)==null){
            dataBaseStats.put(numberGame, new ArrayList<>());

        }

        boolean alreadyExist=false;
        for(String namePlayer : namePlayers){
            if(namePlayer.equals(infoPlayer.getNamePlayer())){
                alreadyExist=true;
            }
        }

        if (alreadyExist==false) {

            dataBaseStats.get(numberGame).add(infoPlayer);
        }



    }

    public void setNumberGame(int numberGame) {
        this.numberTotalGame=numberGame;
    }

    public HashMap<Integer,ArrayList<InfoPlayer>> getDataBaseStats() {
        return dataBaseStats;
    }

    public int getNbMoveInGame() {
        return nbMoveInGame;
    }

    public int calculateNumberWin(String namePlayer){
        int n=0;
        for(int i=0;i<dataBaseStats.size();i++) {
            if(dataBaseStats.get(i)!=null){
                for (InfoPlayer infoPlayer : dataBaseStats.get(i)) {
                    if (infoPlayer.getNamePlayer().equals(namePlayer) && infoPlayer.isWinner() && !infoPlayer.isGameEven()) {
                        n += 1;
                    }
                }
            }
        }
        return n;
    }

    public int calculateNumberLose(String namePlayer){
        int n=0;
        for(int i=0;i<dataBaseStats.size();i++){
            for(InfoPlayer infoPlayer : dataBaseStats.get(i)){
                if(infoPlayer.getNamePlayer().equals(namePlayer) && !infoPlayer.isWinner() && !infoPlayer.isGameEven()){
                    n+=1;
                }
            }
        }
        return n;
    }

    public String getStrategyPlayer(String namePlayer){
        for(InfoPlayer p : dataBaseStats.get(dataBaseStats.size()-1)){
            if(p.getNamePlayer().equals(namePlayer))
                return p.getStrategyPlayer();
        }
        return null;
    }

    public int calculateNumberGameEven(String namePlayer){
        int n=0;
        for(int i=0;i<dataBaseStats.size();i++){
            for(InfoPlayer infoPlayer : dataBaseStats.get(i)){
                if(infoPlayer.getNamePlayer().equals(namePlayer) && infoPlayer.isGameEven()){
                    n+=1;
                }
            }
        }

        return n;

    }

    public int calculatePointVictory(String namePlayer){
        int n=0;
        for(int i=0;i<dataBaseStats.size();i++){
            for(InfoPlayer infoPlayer : dataBaseStats.get(i)){
                if(infoPlayer.getNamePlayer().equals(namePlayer)){
                    n+=infoPlayer.getVictoryPoint();
                }
            }
        }

        return n;
    }



    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Le nombre total de coups joués est... "+ nbMoveInGame+"\n");
        stringBuffer.append("-------Stats of all players : --------- \n");
        for(String namePlayer : namePlayers){
            stringBuffer.append("----------------------"+"\n");
            stringBuffer.append(namePlayer +" : \n");
            stringBuffer.append("Pourcentage win : " + df2.format(((double)calculateNumberWin(namePlayer)/dataBaseStats.size())*100)+"%\n");
            stringBuffer.append("Pourcentage lose : " + df2.format(((double)calculateNumberLose(namePlayer)/dataBaseStats.size())*100)+"%\n");
            stringBuffer.append("Pourcentage even game : " +df2.format(((double)calculateNumberGameEven(namePlayer)/dataBaseStats.size())*100)+"%\n");
            stringBuffer.append("Bot : "+getStrategyPlayer(namePlayer)+"\n");
            stringBuffer.append("Average point victory : "+calculatePointVictory(namePlayer)/getDataBaseStats().size()+"\n");;
        }

        return stringBuffer.toString();
    }



    public Set<String> getNamePlayers() {
        return namePlayers;
    }

    public void setNamePlayers(Set<String> namePlayers) {
        this.namePlayers = namePlayers;
    }
}
