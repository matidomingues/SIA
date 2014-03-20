package deeptrip.ai.rules;

import deeptrip.ai.states.DowntripAIState;
import deeptrip.stategies.ShiftRow;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class DowntripAIRule implements GPSRule {

    private final int row;
    private final int shift;

    public DowntripAIRule(int row, int shift) {
        this.row = row;
        this.shift = shift;
    }


    public Integer getCost() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName() {
        return String.format("SHIFT ROW $d $d SPACES", row, shift);
    }

    public GPSState evalRule(GPSState gpsState) throws NotAppliableException {
        if (!(gpsState instanceof DowntripAIRule)) throw new NotAppliableException();

        try {
            return new DowntripAIState(new ShiftRow(row, shift).execute(((DowntripAIState)gpsState).getBoard()));
        } catch (IllegalArgumentException iae) {
            throw new NotAppliableException();
        }
    }
}
