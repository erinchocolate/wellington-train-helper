package trainsystem;

public class Trip {
	private TrainLine trainLine;  
	private Station startStation;
	private Station endStation;
	private String trainLineName;
	private int zones; 
	private int leaveTime;
	private int arriveTimeForStartStation;
	private int arriveTimeForEndStation;
	private int arriveTime;
	
	Trip(TrainLine trainLine, Station startStation, Station endStation){
		this.setTrainLine(trainLine);
		this.setStartStation(startStation);
		this.setEndStation(endStation);
		setTrainLineName();
	}
	
	public String toString() {
		return ("train line: " + trainLine.getName() + "\n"
				+"start station: " + startStation.getName() + "\n"
				+"destination: " + endStation.getName() + "\n"
				+"zones: " + zones + "\n"
				+"time to leave the first station: " + leaveTime + "\n"
				+"time to arrive at the destination: " + arriveTime + "\n");
	}
	
	public void setTrainLine(TrainLine trainLine) {
		this.trainLine = trainLine;
	}

	public TrainLine getTrainLine() {
		return trainLine;
	}
	
	public void setTrainLineName() {
		this.trainLineName = trainLine.getName();
	}
	
	public String getTrainLineName() {
		return trainLineName;
	}

	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}
	
	public Station getStartStation() {
		return startStation;
	}

	public void setEndStation(Station endStation) {
		this.endStation = endStation;
	}
	
	public Station getEndStation() {
		return endStation;
	}

	public void setZones(int zones) {
		this.zones = zones;
	}
	
	public int getZones() {
		return zones;
	}
	
	public void setLeaveTime(int leaveTime) {
		this.leaveTime = leaveTime;
	}

	public int getLeaveTime() {
		return leaveTime;
	}
	
	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	public int getArriveTime() {
		return arriveTime;
	}
	
	public void setArriveTimeForStartStation(int timeForStartStation) {
		this.arriveTimeForStartStation = timeForStartStation;
	}
	
	public int getArriveTimeForStartStation() {
		return arriveTimeForStartStation;
	}
	
	public void setArriveTimeForEndStation(int timeForEndStation) {
		this.arriveTimeForEndStation = timeForEndStation;
	}
	
	public int getArriveTimeForEndStation() {
		return arriveTimeForEndStation;
	}


}

