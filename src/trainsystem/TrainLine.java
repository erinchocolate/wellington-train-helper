package trainsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * TrainLine
 * Information about a Train Line.
 * Note, we treat the outbound train line as a different from the inbound line.
 * This means that the Johnsonville-Wellington line is a different train line from 
 * the Wellington-Johnsonville line.
 * Although they have the same stations, the stations will be in opposite orders.
 *
 * A TrainLine contains 
 * - the name of the TrainLine (originating station - terminal station, eg Wellington-Melling)
 * - The list of stations o n the line
 * - a list of TrainServices running on the line (eg the 10:00 am service from Upper-Hutt to Wellington)
 *   (in order of time - services earlier in the list are always earlier times (at any station) than later services  )
 */

public class TrainLine {
	private String name;
    private List<Station> stations = new ArrayList<Station>();  
    private List<String> stationsNames = new ArrayList<String>();  
    private List<TrainService> trainServices = new ArrayList<TrainService>();
	
	public TrainLine(String name){
        this.name = name;
    }
	
	public String getName(){
        return name;
    }
	
	/**
     * String contains name of the train line name plus number of stations and number of services
     */
    public String toString(){
        return (name+" ("+stations.size()+" stations, "+trainServices.size()+" services)");
    }
    
    /**
     * Add a TrainService to the set of TrainServices for this line
     */
    public void addTrainService(TrainService train){
        trainServices.add(train);
    }
    
    public List<TrainService> getTrainServices(){
        return Collections.unmodifiableList(trainServices); 
    }
    
    /**
     * Add a Station to the list of Stations on this line
     */
    public void addStation(Station station){
        stations.add(station);
        stationsNames.add(station.getName());
    }
    
    public List<Station> getStations(){
        return Collections.unmodifiableList(stations); 
    }
    
    public List<String> getStationsNames(){
    	return stationsNames;
    }
    
    
    
}
