package trainsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TrainService
 * A particular train service running on a train line.
 * A train service is specified by a list of times that train leaves
 *  each station along the train line.
 *  (it the train does not stop at a station, then the corresponding time is -1)
 * A TrainService object contains
 *  - The TrainLine that the service runs on
 *  - a ID of the train (the name of the line concatenated with the starting time of the train)
 *  - a list of times (integers representing 24-hour time, eg 1425 for 2:45pm), one for
 *     each station on the train line. A time is -1 if the train does not stop at the station.
 * The getStart() method will return the first real time in the list of times
 */

public class TrainService {
	private TrainLine trainLine;  
	private Integer startTime;
	private Integer arriveTime;
	private Integer arriveTimeStation;
	private String trainLineName;
    private String trainID; // train line name + starting time of the train
    private List<Integer> times = new ArrayList<Integer>();
    private List<String> stationsNames = new ArrayList<String>();  
    
    public TrainService(TrainLine trainLine){
        setTrainLine(trainLine);
        setStations();
        setTrainLineName();
    }
    
    public void setTrainLine(TrainLine trainLine) {
    	this.trainLine = trainLine;
    }
    
    public TrainLine getTrainLine(){
        return trainLine;
    }
    
    public void setTrainLineName() {
    	trainLineName= trainLine.getName();
    }
    
    public String getTrainLineName() {
    	return trainLineName;
    }
    
    public void setStations() {
    	stationsNames = trainLine.getStationsNames();
    }
    
    public List<String> getStations(){
    	return stationsNames;
    }
    
    public String getTrainID(){
        return trainID;
    }
    
    public List<Integer> getTimes(){
        return Collections.unmodifiableList(times);  
    }
  
    public void setArriveTimeStation(String station) {
    	int indexOfStation = stationsNames.indexOf(station);
    	arriveTimeStation = times.get(indexOfStation);
    }
    
    public int getArriveTimeStation() {
    	return arriveTimeStation;
    }
    
    /**
     * Add the next time to the TrainService
     */	
    public void addTime(int time, boolean firstStop){
        times.add(time);
        if (trainID==null && time != -1){
            if (firstStop) {
                trainID = trainLine.getName()+"-"+time;
            }
            else {
                time += 10000;
                trainID = trainLine.getName()+"-"+time;
            }           
        }
    }
    
    /**
     * Return the start time of this Train Service
     *  -1 if no start times
     */
    public int getStartTime(){
        for (int time : times){
            if (time!=-1){
            	return time;}
        }
        return -1;
    }
    
    /**
     * Return the arrive time of this Train Service
     */
    public int getArriveTime() {
    	arriveTime = times.get(times.size()-1);
    	return arriveTime;
    }
    
    /**
     * ID plus number of stops
     */
    public String toString(){
        if (trainID==null){return trainLine.getName()+"-unknownStart";}
        int count = 0;
        for (int time : times) {if (time!=-1) count++;}
        return trainID+" ("+count+" stops)";
    }
}
