package application;

import java.io.IOException;
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

public class TimeSceneController {
	private Scene scene;
	private Stage stage;
	private Parent root;
	private TrainSystem model;
	private List<TrainService> trainServices;
	private Set<String> stations;
	private String stationChoice;
	private String[] hours = new String[24];
	private String[] minutes = new String[60];
	private String hourChoice;
	private String minuteChoice;
	private String timeChoice;
	private int hourNumChoice;
	private int minuteNumChoice;
	private int timeNumChoice;
	
	@FXML private Label timeWarning;
	@FXML private Label stationWarning;
	@FXML private ChoiceBox<String> hourChoiceBox;
	@FXML private ChoiceBox<String> minuteChoiceBox;
	@FXML private ChoiceBox<String> stationChoiceBox;
	@FXML private TableView<TrainService> tableView;
	@FXML private TableColumn<TrainService, String> trainLineColumn;
	@FXML private TableColumn<TrainService, String> trainIDColumn;
	@FXML private TableColumn<TrainService, Integer> startTimeColumn;
	@FXML private TableColumn<TrainService, Integer> arriveDestinationColumn;
	@FXML private TableColumn<TrainService, Integer> arriveStationTimeColumn;
	
	
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
		stationChoiceBox.getItems().addAll(stations);
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
		if(stationChoice == null) {
			stationWarning.setText("Please choose a station!");
		}
	}
	
	public void choiceBoxButtonPushed() {
		try {
			stationChoice= stationChoiceBox.getValue();
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
	
	public void displayInfo() {
		trainLineColumn.setCellValueFactory(new PropertyValueFactory<TrainService, String>("trainLineName"));
		trainIDColumn.setCellValueFactory(new PropertyValueFactory<TrainService, String>("trainID"));
		startTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainService, Integer>("startTime"));
		arriveDestinationColumn.setCellValueFactory(new PropertyValueFactory<TrainService, Integer>("arriveTime"));	
		arriveStationTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainService, Integer>("arriveTimeStation"));		
		tableView.setItems(getServices());
	}
	
	public ObservableList<TrainService> getServices() {
		ObservableList<TrainService> services = FXCollections.observableArrayList();
		setService();
		for(TrainService s: trainServices) {
			services.add(s);		
		}	
		return services;
	}
	
	public void setService() {
		trainServices = model.getTrainServiceByStation(stationChoice, timeNumChoice);
		for(TrainService s: trainServices) {
			s.setArriveTimeStation(stationChoice);
		}
	}
	
}
