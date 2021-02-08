package fr.wither.factures.controller;

import java.io.IOException;

import fr.wither.factures.commons.Common;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewFactureController {
	@FXML
	private DatePicker dp_date;
	@FXML
	private Button btn_addVaches;
	@FXML
	private Button btn_retour;

	@FXML
	public void openAddVache(ActionEvent event) {
		String s = dp_date.getValue().getDayOfMonth() + "/" + dp_date.getValue().getMonthValue() + "/" + dp_date.getValue().getYear();
		System.out.println("date : " + s); 
		Common.FACT.setDate(s);
		 Stage stage = (Stage) btn_addVaches.getScene().getWindow();
		 try {
			AnchorPane root = FXMLLoader.load(Common.main.getClass().getResource("/fr/wither/factures/view/NewVache.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btn_retour].onAction
	@FXML
	public void goBack(ActionEvent event) {
		 Stage stage = (Stage) btn_retour.getScene().getWindow();
		 Common.goBackToMain(stage);
	}
}
