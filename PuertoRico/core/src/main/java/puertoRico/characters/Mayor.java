package puertoRico.characters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import useful.GameMessage;
import puertoRico.player.Player;
import puertoRico.stock.Stock;


@JsonIgnoreProperties({"colonists"})
public class Mayor extends Character {

    public int getNbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getNextShip() {
        return nextShip;
    }

    public void setNextShip(int nextShip) {
        this.nextShip = nextShip;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    private int nbPlayers;
    private int turn;
    private int colonistShip;
    private int nextShip;
    private boolean end;
    public Mayor(){

    }
    public Mayor(int nbPlayers, Stock stock){
        super("Mayor",stock);
        this.nbPlayers=nbPlayers;
        this.colonistShip=nbPlayers;
        this.nextShip=0;
        this.turn=0;
        this.end=false;
    }

    public int getColonists() {
        return getStock().getColonists();
    }

    public int getColonistShip() {
        return colonistShip;
    }

    public void setColonistShip(int colonistShip) {
        this.colonistShip = colonistShip;
    }

    @Override
    public void activate(Player p)
    {
        giveBonus(p);
        if(colonistShip>0)
        {
            int nbColonists =(this.colonistShip/(nbPlayers-turn))+this.colonistShip%(nbPlayers-turn);
            p.setColonist(nbColonists+p.getColonist());
            GameMessage.messageWhenReceiveColonist(p,nbColonists);
            this.colonistShip-=nbColonists;
            GameMessage.messageWhenColonistPut(p,p.putColonist());
            this.nextShip+=p.getNbUnactiveBuildings();
            turn++;
            if(colonistShip==0){
                if(nextShip<nbPlayers) {
                    nextShip = nbPlayers;
                }
                this.colonistShip=this.getStock().removeColonist(nextShip);
                if(colonistShip<nextShip)
                    this.end=true;
                GameMessage.messageWhenColonistsDistributed(nextShip);
                this.nextShip=0;
                this.turn=0;
            }
        }
        else if(turn==0){
            GameMessage.messageWhenNoColonist();
            turn++;
        }
        else if(turn==nbPlayers-1)
            turn=0;
        else
            turn++;
    }

    public boolean isEnd(){ return end; }

}
