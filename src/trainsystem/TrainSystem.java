package trainsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TrainSystem {
	private Database database;
	private Station station;
	private TrainLine trainLine;
	private Trip trip;
	private HashMap<String, Station> stationByName;
	private HashMap<String, TrainLine> trainLineByName;
	private List<TrainService> trainService = new ArrayList();
	private Set<TrainLine> trainLineByStation;
	
	public TrainSystem(){
		database = new Database();
		stationByName = database.getStationsMap();
		trainLineByName = database.getTrainLinesMap();
	}
	
	public Station getStationByName(String stationName) {
		return stationByName.get(stationName);
	}
	
	public TrainLine getTrainLineByName(String trainLineName) {
		return trainLineByName.get(trainLineName);
	}
	
	public HashMap<String, Station> getStationsMap(){
		return stationByName;
	}
	
	public HashMap<String, TrainLine> getTrainLinesMap(){
		return trainLineByName;
	}
	
	/** 
	 * List all the stations in the region
	 */
	public Set<String> getStationsName(){
		return stationByName.keySet();
	}
	
	/** 
	 * List all the train lines in the region
	 */
	public Set<String> getTrainLinesName(){
		return trainLineByName.keySet();
	}
	
	/** 
	 * List the train lines that go through a given station
	 */
	public Set<TrainLine> getTrainLineByStation(String stationName) {
		station = stationByName.get(stationName);
		return station.getTrainLines();
	}
	
	/** 
	 * List the stations along a given train line
	 */	
	public List<Station> getStationByTrainLine(String trainLineName) {
		trainLine = trainLineByName.get(trainLineName);
		return trainLine.getStations();
	}
	
	/** 
	 * Print the name of a train line that goes from a station to a destination station
	 */
	public String getTrainLineByStation(String startName, String endName) {
		Station startStation = stationByName.get(startName);
		Station endStation = stationByName.get(endName);
		trainLineByStation = startStation.getTrainLines();
		for(TrainLine t:trainLineByStation) {
			if(t.getStations().contains(endStation)){
				int indexOfStartStation = t.getStations().indexOf(startStation);
				int indexOfEndStation = t.getStations().indexOf(endStation);
				//Make sure the train line goes in the correct direction
				if(indexOfEndStation > indexOfStartStation) {
					return t.getName();
				}	
			}
		}
		/**
		 * If there is no Train Line connection between the two Train Stations directly, 
		 * then give an appropriate error message to the user
		 */
		return "Sorry, there is no train line connection between " + startName + " and "
				+ endName + " directly.";
	}

	/** 
	 * Find the next train service for each line at a station 
	 * immediately after a user-specified time 
	 */
	public List<TrainService> getTrainServiceByStation(String stationName, Integer time) {
		station = stationByName.get(stationName);
		trainLineByStation = station.getTrainLines();
		for(TrainLine trainLine:trainLineByStation) {
			List<TrainService> trainServiceByTrainLine = trainLine.getTrainServices();
			for(TrainService service: trainServiceByTrainLine) {
				service.setArriveTimeStation(stationName);
				if(service.getArriveTimeStation()>time) {
					trainService.add(service);
					break;
				}
			}
		}
		return trainService;
	}
	
	/**
	 * Find the trip between two given stations (on the same line), 
	 * immediately after a user-specified time
	 */
	public Trip getTripByStation(String startName, String endName, Integer time) {
		Station startStation = stationByName.get(startName);
		Station endStation = stationByName.get(endName);	
		Integer zones = 0;
		
		//Get train line that passes the start station
		trainLineByStation = startStation.getTrainLines();
		for(TrainLine trainLine:trainLineByStation) {
			//Get the train line that passes the end station
			if(trainLine.getStations().contains(endStation)){
				int indexOfStartStation = trainLine.getStations().indexOf(startStation);
				int indexOfEndStation = trainLine.getStations().indexOf(endStation);
				//Get the train line that passes the start and end station in the right direction
				if(indexOfEndStation > indexOfStartStation) {
					TrainLine trainLineForTrip = trainLine;
					//Get the train service from the chosen train line
					List<TrainService> trainServiceByTrainLine = trainLineForTrip.getTrainServices();
					for(TrainService service: trainServiceByTrainLine) {
						//Get the service that arrives to the start station after user-specific time
						service.setArriveTimeStation(startName);
						Integer arriveTimeForStartStation = service.getArriveTimeStation();
						if(arriveTimeForStartStation>time) {
							TrainService serviceForTrip = service;
							Integer leaveTime = service.getStartTime();
							Integer arriveTimeForDestination = service.getArriveTime();	
							service.setArriveTimeStation(endName);
							Integer arriveTimeForEndStation = service.getArriveTimeStation();
							
							Integer startStationZone = startStation.getZone();
							Integer endStationZone = endStation.getZone();
							
							if(endStationZone <= startStationZone) {
								zones = startStationZone - endStationZone + 1;
							}
							else {
								zones = endStationZone - startStationZone + 1;
							}
							trip = new Trip(trainLineForTrip, startStation, endStation);
							trip.setZones(zones);
							trip.setArriveTime(arriveTimeForDestination);
							trip.setLeaveTime(leaveTime);
							trip.setArriveTimeForStartStation(arriveTimeForStartStation);
							trip.setArriveTimeForEndStation(arriveTimeForEndStation);
							break;
						}	
					}
				}
			}				
		}
		return trip;
	}
	/**
	 * Find the trip that arrives destination earliest between two given stations (on the different line), 
	 * immediately after a user-specified time
	 */
	public List<Trip> getTripByStationFromExchange(String startName, String endName, Integer time) {
		Station startStation = stationByName.get(startName);
		Station endStation = stationByName.get(endName);
		Integer currentArriveTime = 0;
		Integer maxArriveTime = 0;
		Integer timeArriveExchange = 0;
		Trip firstTrip = null;
		Trip secondTrip = null;
		List<Trip> trips = new ArrayList<Trip>();
		
		//Get all stations that the start station can go to
		List<String> s1 = startStation.getAllStationsOnTheSameLine();
		//Get all stations that the end station can go to
		List<String> s2 = endStation.getAllStationsOnTheSameLine();
		//Get exchange station names
		List<String> exchange = new ArrayList<String>(s1);
		exchange.retainAll(s2);
		
		//Find the fastest trip between start station and exchange station
		for(String exchangeName: exchange) {
			Trip trip = getTripByStation(startName, exchangeName, time);
			currentArriveTime = trip.getArriveTimeForEndStation();
			if (currentArriveTime > maxArriveTime) {
				firstTrip = trip;
				maxArriveTime = currentArriveTime;
			}
		}

		trips.add(firstTrip);
		
		//Find the fastest trip between exchange station and end station
		for(String exchangeName: exchange) {
			Trip trip = getTripByStation(exchangeName, endName, maxArriveTime);
			maxArriveTime = 0;
			currentArriveTime = trip.getArriveTimeForEndStation();		
			if (currentArriveTime > maxArriveTime) {
				secondTrip = trip;
				maxArriveTime = currentArriveTime;
			}
		}
		trips.add(secondTrip);
		
		return trips;
	}
}
