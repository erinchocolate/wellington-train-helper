package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import trainsystem.TrainSystem;

public class HomeSceneController {
	private Scene scene;
	private Stage stage;
	private Parent root;
	private TrainSystem model = new TrainSystem();
	
	public void enterMainScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
		root = loader.load();
		HomeSceneController s = loader.getController();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);	
		stage.setScene(scene);
		stage.show();
	}
	
	public void enterStationScene(ActionEvent e) throws IOException {
		String stationName = ((Button)e.getSource()).getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StationScene.fxml"));
		root = loader.load();
		StationSceneController s = loader.getController();
		s.setup(model, stationName);
		s.displayInfo();

		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);	
		stage.setScene(scene);
		stage.show();
	}
	
	public void enterTrainLineScene(ActionEvent e) throws IOException {
		String trainLineName = ((Button)e.getSource()).getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TrainLineScene.fxml"));
		root = loader.load();
		TrainLineSceneController s = loader.getController();
		s.setup(model, trainLineName);
		s.displayInfo();
		
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
}
