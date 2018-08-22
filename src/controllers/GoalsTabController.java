package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import main.Helper;
import model.Activity;
import model.Goal;
import model.Person;

public class GoalsTabController implements Initializable {

	@FXML
	TextField tfCurrentGoal, tfTDEE, tfBMR, tfCaloricReqs;

	@FXML
	Button btnMaintainWeight, btnGainWeight, btnLoseWeight;

	@FXML
	ChoiceBox<Activity> cbActivityLevel;

	private Goal currentGoal;
	private Activity currentActivity;
	private ArrayList<Activity> activities = new ArrayList<Activity>();
	private ArrayList<Goal> goals = new ArrayList<Goal>();

	private void calculateBMR() {
		Person p = Person.getInstance();

		if (p.getGender().equals("Male")) {
			// Formula - BMR = 66 + (13.75 x weight in kg) + (5 x height in cm) � (6.8 x age
			// in yrs)

			// Check what units we are in and convert to Metric
			if (Person.getInstance().getUnits().equals("Imperial")) {
				// Use the converted BMR
				Person.getInstance().setBMR(66 + (13.75 * (p.getWeight() / 2.20462) + (5 * (p.getHeight() / 0.0328084)) - (6.8 * p.getAge())));
			} else {
				Person.getInstance().setBMR(66 + (13.75 * p.getWeight()) + (5 * p.getHeight()) - (6.8 * p.getAge()));
			}

		} else if (p.getGender().equals("Female")) {
			// Formula - BMR = 655 + (9.6 x weight in kg) + (1.8 x height in cm) � (4.7 x
			// age in Yrs)

			if (Person.getInstance().getUnits().equals("Imperial")) {
				// Use converted BMR to metric
				Person.getInstance().setBMR(665 + (9.6 * (p.getWeight() / 2.20462) + (1.8 * (p.getHeight() / 0.0328084)) - (4.7 * p.getAge())));
			} else {
				Person.getInstance().setBMR(655 + (9.6 * p.getWeight()) + (1.8 * p.getHeight()) - (4.7 * p.getAge()));
			}

		}
	}

	private void calculateTDEE() {
		Person.getInstance().setTDEE(Person.getInstance().getBMR() * currentActivity.getActivityLevel());
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		setupActivityLevels();
		setupGoals();

		calculateValues();
	}

	private Activity getActivityLevel(Activity ac) {

		for (int i = 0; i < activities.size(); i++) {

			System.out.println(activities.get(i).getName());
			// Basically checks the activity stored on person with all the activities
			// and returns the one exactly the same as the person one in here

			if (activities.get(i).getActivityLevel() == ac.getActivityLevel()) {
				System.out.println("ACTIVITY FOUND!!");
				return activities.get(i);
			}
		}

		return null;
	}

	private void setupActivityLevels() {

		// Set value of the currentActivity
		currentActivity = Person.getInstance().getActivityLevel();

		Activity sedentary = new Activity("Sedentary", 1.2);
		Activity lightlyActive = new Activity("Lightly Active", 1.375);
		Activity moderateActive = new Activity("Moderately Active", 1.55);
		Activity veryActive = new Activity("Very Active", 1.725);
		Activity extremelyActive = new Activity("Extremely Active", 1.9);

		activities.addAll(Arrays.asList(sedentary, lightlyActive, moderateActive, veryActive, extremelyActive));

		// Fill choicebox with Activities
		cbActivityLevel.setItems(FXCollections.observableArrayList(activities));

		System.out.println("Setting activity level here -----");
		cbActivityLevel.setValue(getActivityLevel(Person.getInstance().getActivityLevel()));

		// Add event listener
		ChangeListener<Activity> changeListener = new ChangeListener<Activity>() {
			@Override
			public void changed(ObservableValue<? extends Activity> observable, //
					Activity oldValue, Activity newValue) {
				if (newValue != null) {
					System.out.println("Activity: " + newValue);
					currentActivity = newValue;

					// Save it into person
					Person.getInstance().setActivityLevel(currentActivity);

					// Update values
					calculateValues();
				}
			}
		};
		// Selected Item Changed.
		cbActivityLevel.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	private void setupGoals() {
		// Setup currentGoal from Person
		currentGoal = Person.getInstance().getCurrentGoal();

		// Create the types of goals
		Goal loseWeight = new Goal("Lose Weight", 0.8);
		Goal maintainWeight = new Goal("Maintain Weight", 1.0);
		Goal gainWeight = new Goal("Gain Weight", 1.2);

		goals.addAll(Arrays.asList(loseWeight, maintainWeight, gainWeight));

		tfCurrentGoal.setText(currentGoal.getName());
	}

	private Goal getGoal(String name) {
		for (int i = 0; i < goals.size(); i++) {
			if (goals.get(i).getName().equals(name)) {
				return goals.get(i);
			}
		}
		return null;
	}

	private void updateGoal(String name) {
		currentGoal = getGoal(name);
		tfCurrentGoal.setText(currentGoal.getName());

		calculateValues();
	}

	private void calculateValues() {
		calculateBMR();
		tfBMR.setText(Double.toString(Helper.round(Person.getInstance().getBMR(), 2)));

		calculateTDEE();
		tfTDEE.setText(Double.toString(Helper.round(Person.getInstance().getTDEE(), 2)));

		// This should be done internally inside Person
		Person.getInstance().setGoalCalories(Person.getInstance().getTDEE() * currentGoal.getMultiplier());

		tfCaloricReqs.setText(Double.toString(Helper.round(Person.getInstance().getGoalCalories(), 2)));
	}

	@FXML
	protected void handleLoseWeight(ActionEvent event) throws IOException {
		updateGoal("Lose Weight");
	}

	@FXML
	protected void handleGainWeight(ActionEvent event) throws IOException {
		updateGoal("Gain Weight");
	}

	@FXML
	protected void handleMaintainWeight(ActionEvent event) throws IOException {
		updateGoal("Maintain Weight");
	}

	public void update() {
		// Updates everything on this tab page
		calculateValues();
	}
}