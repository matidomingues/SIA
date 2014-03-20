package deeptrip.ai.states;

import deeptrip.game.Board;
import gps.api.GPSState;

public class DowntripAIState implements GPSState{

    private final Board board;

    public DowntripAIState(Board board) {
        this.board = board;
    }

    @Override
    public boolean compare(GPSState gpsState) {
        if (!(gpsState instanceof DowntripAIState)) {
            throw new IllegalArgumentException();
        }
        return board.equals(((DowntripAIState) gpsState).getBoard());
    }

    public Board getBoard() {
        return board;
    }
}
