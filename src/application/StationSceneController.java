package application;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import trainsystem.Station;
import trainsystem.TrainSystem;

public class StationSceneController{
	private Scene scene;
	private Stage stage;
	private Parent root;
	private TrainSystem model;
	private Station station;
	private String stationName;
	private List<String> trainLinesName;
	
	@FXML private Label title;
	@FXML private Label zone;
	@FXML private Label distance;
	@FXML private ListView<String> listView;
	
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
	
	public void setup(TrainSystem model, String stationName) {
		setModel(model);
		setName(stationName);
		setStation();
		setTrainLine();
	}
	
	public void setModel(TrainSystem model) {
		this.model = model;
	}
	
	public void setName(String stationName) {
		this.stationName = stationName;
	}
	
	public void setStation() {
		station = model.getStationByName(stationName);
	}
	
	public void setTrainLine() {
		trainLinesName = station.getTrainLinesName();	
	}
	
	public void displayInfo() {
		title.setText(stationName);
		zone.setText(String.valueOf(station.getZone()));
		distance.setText(Double.toString(station.getDistance()));
		listView.getItems().addAll(trainLinesName);	
	}
	
}
