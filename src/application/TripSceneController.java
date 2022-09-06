package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import trainsystem.TrainService;
import trainsystem.TrainSystem;
import trainsystem.Trip;

public class TripSceneController {
	private Scene scene;
	private Stage stage;
	private Parent root;
	private TrainSystem model;
	private Set<String> stations;
	private String[] hours = new String[24];
	private String[] minutes = new String[60];
	private String startChoice;
	private String endChoice;
	private String hourChoice;
	private String minuteChoice;
	private String timeChoice;
	private int hourNumChoice;
	private int minuteNumChoice;
	private int timeNumChoice;
	private Trip trip;
	private List<Trip> trips = new ArrayList<Trip>();
	
	@FXML private Label timeWarning;
	@FXML private Label stationWarning;
	@FXML private ChoiceBox<String> startStationChoiceBox;
	@FXML private ChoiceBox<String> endStationChoiceBox;
	@FXML private ChoiceBox<String> hourChoiceBox;
	@FXML private ChoiceBox<String> minuteChoiceBox;
	@FXML private TableView<Trip> tableView;
	@FXML private TableColumn<Trip, String> trainLineColumn;
	@FXML private TableColumn<Trip, Integer> startTimeColumn;
	@FXML private TableColumn<Trip, Integer> arriveFirstStationColumn;
	@FXML private TableColumn<Trip, Integer> arriveDestinationColumn;
	
	public void enterMainScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
		root = loader.load();
		HomeSceneController s = loader.getController();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);	
		stage.setScene(scene);
		stage.show();
	}
	
	public void enterTripScene(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TripScene.fxml"));
		root = loader.load();
		TripSceneController s = loader.getController();
		s.setup(model);
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);	
		stage.setScene(scene);
		stage.show();
	}
	
	public void enterTimeScene(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TimeScene.fxml"));
		root = loader.load();
		TimeSceneController s = loader.getController();
		s.setup(model);
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);	
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void setup(TrainSystem model) {
		setModel(model);
		setStation();
		setHour();
		setMinute();
		startStationChoiceBox.getItems().addAll(stations);
		endStationChoiceBox.getItems().addAll(stations);
		hourChoiceBox.getItems().addAll(hours);
		minuteChoiceBox.getItems().addAll(minutes);
	}

	public void setModel(TrainSystem model) {
		this.model = model;
	}
	
	public void setStation() {
		stations = model.getStationsName();
	}
	
	public void setHour() {
		for(int i = 1; i < 25; i ++) {
			hours[i-1] = String.valueOf(i);
		}
	}
	
	public void setMinute() {	
		for(int i = 0; i < 60; i ++) {
			minutes[i] = String.valueOf(i);
		}
	}
	
	public void validateTimeChoice() {
		if(hourNumChoice ==24 && minuteNumChoice > 0) {
			timeWarning.setText("Please choose a valid time!");
		}
	}
	
	public void validateStationChoice() {
		if(startChoice == null || endChoice == null) {
			stationWarning.setText("Please choose your start and destination station!");
		}
	}
	
	public Trip getDirectTrip() {
		trip = model.getTripByStation(startChoice, startChoice, timeNumChoice);
		return trip;
	}
	
	public List<Trip> getExchangeTrip(){
		trips = model.getTripByStationFromExchange(startChoice, endChoice, timeNumChoice);
		return trips;
	}
	
	public ObservableList<Trip> getTrips(){
		ObservableList<Trip> tripDisplay = FXCollections.observableArrayList();
		if(getDirectTrip()==null) {
			trips = model.getTripByStationFromExchange(startChoice, endChoice, timeNumChoice);
			for(Trip t: trips) {
				tripDisplay.add(t);
			}
		}
		else {
			tripDisplay.add(getDirectTrip());
		}		
		return tripDisplay;
	}
	
	public void displayInfo() {
		trainLineColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("trainLineName"));
		startTimeColumn.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("leaveTime"));
		arriveFirstStationColumn.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("arriveTimeForStartStation"));	
		arriveDestinationColumn.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("arriveTimeForEndStation"));		
		tableView.setItems(getTrips());
	}
	
	public void choiceBoxButtonPushed() {
		try {
			startChoice= startStationChoiceBox.getValue();
			endChoice= endStationChoiceBox.getValue();
			hourChoice = hourChoiceBox.getValue();
			hourNumChoice =  Integer.parseInt(hourChoice);
			minuteChoice = minuteChoiceBox.getValue();
			minuteNumChoice = Integer.parseInt(minuteChoice);
			if (minuteNumChoice < 10) {
				timeChoice = hourChoice + "0" + minuteChoice;
			}
			else {
				timeChoice = hourChoice + minuteChoice;
			}
			timeNumChoice = Integer.parseInt(timeChoice);
			validateTimeChoice();
			validateStationChoice();
			displayInfo();
		}
		catch(Exception e){
			timeWarning.setText("Please choose a valid time and station!");
		}
	}
}
