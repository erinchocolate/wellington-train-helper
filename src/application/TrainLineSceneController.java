package application;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import trainsystem.TrainLine;
import trainsystem.TrainService;
import trainsystem.TrainSystem;

public class TrainLineSceneController {
	private Scene scene;
	private Stage stage;
	private Parent root;
	private TrainSystem model;
	private TrainLine trainLine;
	private String trainLineName;
	private List<String> stations;
	private List<TrainService> trainServices;
	
	@FXML private Label title;
	@FXML private ListView<String> listView;
	@FXML private TableView<TrainService> tableView;
	@FXML private TableColumn<TrainService, String> serviceColumn;
	@FXML private TableColumn<TrainService, Integer> startTimeColumn;
	@FXML private TableColumn<TrainService, Integer> arriveTimeColumn;
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
	public void setup(TrainSystem model, String trainLineName) {
		setModel(model);
		setName(trainLineName);
		setTrainLine();
		setTrainServices();
		setStations();
	}
	
	public void setModel(TrainSystem model) {
		this.model = model;
	}
	
	public void setName(String trainLineName) {
		this.trainLineName = trainLineName;
	}
	
	public void setTrainLine() {
		trainLine = model.getTrainLineByName(trainLineName);
	}
	
	public void setStations() {
		stations = trainLine.getStationsNames();
	}
	
	public void setTrainServices() {
		trainServices = trainLine.getTrainServices();
	}
	
	public void displayInfo() {
		title.setText(trainLineName);
		listView.getItems().addAll(stations);	
		serviceColumn.setCellValueFactory(new PropertyValueFactory<TrainService, String>("trainID"));
		startTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainService, Integer>("startTime"));
		arriveTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainService, Integer>("arriveTime"));		
		tableView.setItems(getTrainService());
	}
	
	public ObservableList<TrainService> getTrainService(){
		ObservableList<TrainService> trainService = FXCollections.observableArrayList();
		for (TrainService s: trainServices) {
			trainService.add(s);
		}
		return trainService;
	}
}
