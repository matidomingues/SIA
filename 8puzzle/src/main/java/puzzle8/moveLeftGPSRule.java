package puzzle8;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class moveLeftGPSRule implements GPSRule{

	public Integer getCost() {
		return 1;
	}

	public String getName() {
		return "LEFT";
	}

	public GPSState evalRule(GPSState state) throws NotAppliableException {
		// TODO Auto-generated method stub
		return null;
	}

}
