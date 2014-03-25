package deeptrip.ai.rules;

import deeptrip.ai.states.DeeptripAIState;
import deeptrip.stategies.ShiftRow;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotApplicableException;

public class DeeptripAIRule implements GPSRule {

    private final int row;
    private final int shift;

    public DeeptripAIRule(int row, int shift) {
        this.row = row;
        this.shift = shift;
    }


    public Integer getCost() {
        return 1;
    }

    public String getName() {
        return String.format("SHIFT ROW $d $d SPACES", row, shift);
    }

    public GPSState evalRule(GPSState gpsState) throws NotApplicableException {
        if (!(gpsState instanceof DeeptripAIState)) throw new NotApplicableException();

        try {
            return new DeeptripAIState(new ShiftRow(row, shift).execute(((DeeptripAIState) gpsState).getBoard()));
        } catch (IllegalArgumentException iae) {
            throw new NotApplicableException();
        }
    }
}
