package puertoRico.characters;

import puertoRico.player.Player;

public interface Action {

     /**
      * This function permit character to interact with the player when an action is made by the player (or robot of the player)
      * @param p
      */
     void activate(Player p);
}
