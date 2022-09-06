package trainsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Station
 * Information about an individual station:
 * - The name
 * - The fare zone it is in (1 - 14)
 * - The distance from the hub station (Wellington)
 * - The set of TrainLines that go through that station.
 * The constructor just takes the name, zone and distance;
 * TrainLines must then be added to the station, one by one.
 */

public class Station {
	
	private String name;  
    private int zone; 			// fare zone     
    private double distance;	// distance from Wellington
    private Set<TrainLine> trainLines = new HashSet<TrainLine>(); 
    private List<String> trainLinesName = new ArrayList<String>(); 
    private List<String> allStations = new ArrayList<String>();
    
    public Station(String name, int zone, double dist){
        this.name = name;
        this.zone = zone;
        this.distance = dist;
    }

    public String getName(){
        return this.name;
    }

    public int getZone(){
        return this.zone;
    }
    
    public double getDistance(){
        return this.distance;
    }
    
    public String toString(){
        return name+" (fareZone "+zone+", "+trainLines.size()+" lines)";
    }
    
    /**
     * Add a TrainLine to the station
     */
    public void addTrainLine(TrainLine line){
        trainLines.add(line);
        trainLinesName.add(line.getName());
    }
    
    /**
     * Return an unmodifiable version of the set of train lines
     */
    public Set<TrainLine> getTrainLines(){
    	return Collections.unmodifiableSet(trainLines); 
    }
    
    public List<String> getTrainLinesName(){
    	return trainLinesName;
    }
    
    public List<String> getAllStationsOnTheSameLine(){
    	for (TrainLine t:trainLines) {
    		List<String> stations = t.getStationsNames();
    		for(String s: stations) {
    			if(!allStations.contains(s)) {
    				allStations.add(s);
    			}
    		}
    	}
    	return allStations;
    }
    
}
