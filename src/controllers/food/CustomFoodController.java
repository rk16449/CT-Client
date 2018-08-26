package controllers.food;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import model.Food;
import model.Helper;

public class CustomFoodController extends BaseFoodController implements Initializable {

	@FXML
	private Spinner<Double> spinnerQuantity;
	@FXML
	private CheckBox checkboxToday;
	@FXML
	private Button buttonCreate;
	
	// The food we will return back to DietTabController
	private Food returnFoodData;

	public void initialize(URL arg0, ResourceBundle arg1) {
		// Setup Spinner min/max values
		spinnerQuantity.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100));
	}

	@FXML
	protected void handleCreate(ActionEvent event) throws IOException {
		// Make sure we have valid TextFields
		if (super.valid()) {
			
			// Validate checkbox values
			if (checkboxToday.isSelected()) {
				if (!(spinnerQuantity.getValue() >= 1 && spinnerQuantity.getValue() <= 100)) {
					return;
				}
			}
			
			// Parse variables to save into new Food
			String name = tfName.getText();
			double[] amt = {
					Double.parseDouble(tfCarbohydrates.getText()), 
					Double.parseDouble(tfFats.getText()),
					Double.parseDouble(tfProteins.getText()), 
					Double.parseDouble(tfAmount.getText()),
					spinnerQuantity.getValue()
			};
			
			
			returnFoodData = new Food(name, amt);
			returnFoodData.setCustom(true);

			// Make sure to save everything
			System.out.println("We have valid data to work with");

			// Close this window and continue (DietTabController)
			buttonCreate.getScene().getWindow().hide();

		} else {
			System.out.println("We don't have valid data to create food");
		}
	}
	
	public Food getFood() {
		return this.returnFoodData;
	}

	@FXML
	protected void handleCheckBoxToday(ActionEvent event) throws IOException {
		System.out.println("Checkbox pressed");
		spinnerQuantity.setDisable(!spinnerQuantity.isDisable());
	}

	public boolean addToTable() {
		return checkboxToday.isSelected();
	}
}