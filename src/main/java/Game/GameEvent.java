package Game;

import Model.Game;
import Model.Map;

import java.util.EventObject;

public class GameEvent extends EventObject {

    private Map Game;
    private int numberOfPlayers;

    public GameEvent(Object source) {
        super(source);
    }

    public GameEvent(Object source, int numberOfPlayers) {
        super(source);
        this.numberOfPlayers = numberOfPlayers;
    }
    public GameEvent(Object source, Map game, int numberOfPlayers) {
        super(source);
        this.numberOfPlayers = numberOfPlayers;
        this.Game= game;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public Map getGameMap() {
        return Game;
    }

}
