package fr.wither.factures.controller;

import static fr.wither.factures.commons.Common.BASE_FILE;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import fr.wither.factures.commons.Common;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuController {
	@FXML
	private Button btn_creerFact;
	@FXML
	private Button btn_openFact;

	// Event Listener on Button[#btn_creerFact].onAction
	@FXML
	public void openCreerFacture(ActionEvent event) {
		 Stage stage = (Stage) btn_creerFact.getScene().getWindow();
		 try {
			AnchorPane root = FXMLLoader.load(Common.main.getClass().getResource("/fr/wither/factures/view/NewFacture.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btn_openFact].onAction
	@FXML
	public void openFactureFile(ActionEvent event) {
		try {			
			Desktop.getDesktop().open(new File(BASE_FILE));
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur lors de l'ouverture :");
	        alert.setHeaderText(e.getMessage());
	 
	        VBox dialogPaneContent = new VBox();
	 
	        Label label = new Label("Tracage de l'erreur :");
	 
	        String stackTrace = e.getStackTrace().toString();
	        TextArea textArea = new TextArea();
	        textArea.setText(stackTrace);
	 
	        dialogPaneContent.getChildren().addAll(label, textArea);
	 
	        // Set content for Dialog Pane
	        alert.getDialogPane().setContent(dialogPaneContent);
	 
	        alert.showAndWait();
		}
	}
}
