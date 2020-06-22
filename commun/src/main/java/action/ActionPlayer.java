package action;

public enum ActionPlayer {

    INIT("init"),
    READY("ready"),
    PLAY_TURN("playTurn"),
    TURN_FINISHED("turnFinished"),
    TURN_GOVERNOR_FINISHED("turnGovernorFinished"),
    CURRENT_PLAYER_TURN("currentPlayerTurn"),
    IS_GAME_FINISHED("gameFinished"),
    WINNER("winner"),
    DISCONNECT_GAME("disconnect_game");

    private String nameAction;

    ActionPlayer(String nameAction){
        this.nameAction=nameAction;
    }

    public String getNameAction() {
        return nameAction;
    }
}
