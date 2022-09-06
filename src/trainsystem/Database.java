package trainsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Database
 * This class does two things: read data and create objects, including adding them into chosen
 * data structures and other objects, based on the data it reads from
 * Database object reads data from:
 * - stations.data
 * - train-lines.data 
 * - fares.data
 * - all train line station data
 * - all train line service data
 * Database object creates:
 * - Station object
 * - TrainLine object
 * - TrainService object
 * Database object adds:
 * - Station objects to TrainLine object and vice versa
 * - TrainService objects to TrainLine objects 
 * - Station objects to HashMap stationsMap(Key: station name; Value: Station object)
 * - TrainLine object to HashMap trainLinesMap(Key: train line name; Value: TrainLine object)
 * - Time to TrainService object
 * - Zone and fare to fareMap(Key: zone number; Value: fare)
 */

public class Database{
	private String stationFilePath = "./data/stations.data";
	private String trainLineFilePath = "./data/train-lines.data";
	private String faresFilePath = "./data/fares.data";
	/**
     * The file paths below are the same order as train lines in train-line.data
     */
	String[] trainLineStationFilePaths = {"./data/station/Johnsonville_Wellington-stations.data",
			"./data/station/Wellington_Johnsonville-stations.data",
			"./data/station/Melling_Wellington-stations.data",
			"./data/station/Wellington_Melling-stations.data",
			"./data/station/Waikanae_Wellington-stations.data",
			"./data/station/Wellington_Waikanae-stations.data",
			"./data/station/Masterton_Wellington-stations.data",
			"./data/station/Wellington_Masterton-stations.data",
			"./data/station/Upper-Hutt_Wellington-stations.data",
			"./data/station/Wellington_Upper-Hutt-stations.data",};
	String[] trainLineServiceFilePaths = {"./data/service/Johnsonville_Wellington-services.data",
			"./data/service/Wellington_Johnsonville-services.data",
			"./data/service/Melling_Wellington-services.data",
			"./data/service/Wellington_Melling-services.data",
			"./data/service/Waikanae_Wellington-services.data",
			"./data/service/Wellington_Waikanae-services.data",
			"./data/service/Masterton_Wellington-services.data",
			"./data/service/Wellington_Masterton-services.data",
			"./data/service/Upper-Hutt_Wellington-services.data",
			"./data/service/Wellington_Upper-Hutt-services.data",};
	
	private Station station;
	private TrainLine trainLine;
	private TrainService trainService;
	
	private HashMap<String, Station> stationsMap = new HashMap<String, Station>();
	private HashMap<String, TrainLine> trainLinesMap = new HashMap<String, TrainLine>();
	private HashMap<Integer, Double> faresMap = new HashMap<Integer, Double>();
	private List<TrainLine> trainLinesList = new ArrayList<TrainLine>();     
	private Scanner scanner;
	private File file;
	private int time;
	private boolean firstStop;
	
	Database(){
		loadStationData(); 
		loadTrainLineData();
		loadTrainLineStationData();
		loadTrainLineServiceData();
		loadFareData();
	}
	
	public void loadStationData() {
		file = new File(stationFilePath);
		try {
			scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String entry = scanner.nextLine().trim();
				Scanner entryScan = new Scanner(entry);
				while (entryScan.hasNext()) {
					String name = entryScan.next().trim();
					int zone = Integer. parseInt(entryScan.next().trim());
					double distance = Double.parseDouble(entryScan.next().trim());
					station = new Station(name, zone, distance);
					stationsMap.put(name, station);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTrainLineData() {
		file = new File(trainLineFilePath);
		try {
			scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String name = scanner.nextLine().trim();
				trainLine = new TrainLine(name);
				trainLinesMap.put(name, trainLine);
				trainLinesList.add(trainLine);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTrainLineStationData() {
		for(int i = 0; i < trainLineStationFilePaths.length; i++) {
			file = new File(trainLineStationFilePaths[i]);
			trainLine = trainLinesList.get(i);
			try {
				scanner = new Scanner(file);
				while (scanner.hasNext()) {
					String stationName = scanner.nextLine().trim();
					station = stationsMap.get(stationName);
					station.addTrainLine(trainLine);
					trainLine.addStation(station);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void loadTrainLineServiceData() {
		for(int i = 0; i < trainLineServiceFilePaths.length; i++) {
			file = new File(trainLineServiceFilePaths[i]);
			trainLine = trainLinesList.get(i);
			try {
				scanner = new Scanner(file); 
				while (scanner.hasNext()) {
					String entry = scanner.nextLine().trim();
					Scanner entryScan = new Scanner(entry);
					trainService = new TrainService(trainLine);
					trainLine.addTrainService(trainService);
					int counter = 0; //Use counter to check the first time of the service
					while (entryScan.hasNext()) {					
						if(counter == 0) {
							time = entryScan.nextInt();
							firstStop = true;
							counter ++;
							trainService.addTime(time, firstStop);
						}
						else {
							time = entryScan.nextInt();
							firstStop = false;
							counter ++;
							trainService.addTime(time, firstStop);
						}
					} 					
				}
				}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void loadFareData() {
		file = new File(faresFilePath);
		try {
			scanner = new Scanner(file);
			scanner.nextLine();
			while (scanner.hasNext()) {
				String entry = scanner.nextLine().trim();
				Scanner entryScan = new Scanner(entry);
				while (entryScan.hasNext()) {
					int zone = Integer. parseInt(entryScan.next().trim());
					double fare = Double.parseDouble(entryScan.next().trim());
					faresMap.put(zone, fare);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Station> getStationsMap() {
		return stationsMap;
	}
	
	public HashMap<String, TrainLine> getTrainLinesMap() {
		return trainLinesMap;
	}
	
	public HashMap<Integer, Double> getFaresMap(){
		return faresMap;
	}
}
