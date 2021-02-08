package fr.wither.factures.controller;

import java.io.IOException;

import fr.wither.factures.commons.Common;
import fr.wither.factures.types.Facture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ConfirmController {
	@FXML
	private Button btn_confirm;
	@FXML
	private Button btn_undo;

	// Event Listener on Button[#btn_confirm].onAction
	@FXML
	public void confirm(ActionEvent event) {
		Common.createFile();
		//reseting the facture
		Common.FACT = new Facture();
		Stage stage = (Stage) btn_confirm.getScene().getWindow();
		 try {
			AnchorPane root = FXMLLoader.load(Common.main.getClass().getResource("/fr/wither/factures/view/MainMenu.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btn_undo].onAction
	@FXML
	public void undo(ActionEvent event) {
		Common.FACT = new Facture();
		Stage stage = (Stage) btn_confirm.getScene().getWindow();
		 Common.goBackToMain(stage);
	}
}
