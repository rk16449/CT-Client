package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Exercise;
import model.Food;

public class ExercisesTabController implements Initializable {

	@FXML
	TableView<Exercise> tvExercises;
	
	@FXML
	private TableColumn<Exercise, String> tcExercise, tcSets, tcReps, tcWeight, tcCaloriesBurned;
	
	@FXML
	Button btnAddExercise, btnCustom, btnEdit, btnDelete;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setupTable();
		
	}
	
	private void setupTable() {
		
		tcExercise.setCellValueFactory(e -> e.getValue().getStrExercise());
	}
	
	@FXML
	protected void handleAddExercise(ActionEvent event) throws IOException {
		
	}
	
	@FXML
	protected void handleDelete(ActionEvent event) throws IOException {
		
	}
	
	@FXML
	protected void handleCustom(ActionEvent event) throws IOException {
		
	}
	
	@FXML
	protected void handleEdit(ActionEvent event) throws IOException {
		
	}
}