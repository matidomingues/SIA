package deeptrip.ai.states;

import deeptrip.game.Board;
import gps.api.GPSState;

public class DeeptripAIState implements GPSState{

    private final Board board;

    public DeeptripAIState(Board board) {
        this.board = board;
    }

    public boolean compare(GPSState gpsState) {
        if (!(gpsState instanceof DeeptripAIState)) {
            throw new IllegalArgumentException();
        }
        return board.equals(((DeeptripAIState) gpsState).getBoard());
    }

    public Board getBoard() {
        return board;
    }

}
