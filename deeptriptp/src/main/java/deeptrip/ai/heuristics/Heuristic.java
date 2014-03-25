package deeptrip.ai.heuristics;

import gps.api.GPSState;


/**
 * Interfaz de una heuristica
 * 
 *
 */
public interface Heuristic {


	public Integer getHValue(GPSState stateFrom, GPSState goalState);
}
