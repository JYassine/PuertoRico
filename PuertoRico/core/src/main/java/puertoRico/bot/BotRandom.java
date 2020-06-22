package puertoRico.bot;
import puertoRico.player.Player;

public class  BotRandom extends Bot {

    public BotRandom(){
        super();
        this.name="botRandom";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Player getPlayer(Player p) {
        return null;
    }

    @Override
    public void setPlayer(Player p) {

    }
}
