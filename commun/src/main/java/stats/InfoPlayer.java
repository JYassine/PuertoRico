package stats;


import org.json.JSONException;
import org.json.JSONObject;

public class InfoPlayer {

    private String namePlayer;
    private String strategyPlayer;
    private String doublons;
    private boolean winner;
    private boolean gameEven;
    private int victoryPoint;

    public InfoPlayer(){
        winner=false;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public void setStrategyPlayer(String strategyPlayer) {
        this.strategyPlayer = strategyPlayer;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }


    public JSONObject convertToJson() throws JSONException {

        JSONObject infoPlayer = new JSONObject();
        infoPlayer.put("namePlayer", this.namePlayer);
        infoPlayer.put("strategyPlayer",this.strategyPlayer);
        infoPlayer.put("doublons", this.getDoublons());
        infoPlayer.put("winner",this.winner);
        infoPlayer.put("gameEven",this.gameEven);
        infoPlayer.put("victoryPoint",this.getVictoryPoint());

        return infoPlayer;
    }

    public String getDoublons() {
        return doublons;
    }

    public void setDoublons(String doublons) {
        this.doublons = doublons;
    }


    public boolean isWinner() {
        return winner;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public String getStrategyPlayer() {
        return strategyPlayer;
    }


    public boolean isGameEven() {
        return gameEven;
    }

    public void setGameEven(boolean gameEven) {
        this.gameEven = gameEven;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public void setVictoryPoint(int victoryPoint) {
        this.victoryPoint = victoryPoint;
    }
}
