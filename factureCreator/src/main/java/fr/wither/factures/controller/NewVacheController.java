package fr.wither.factures.controller;

import java.io.IOException;

import fr.wither.factures.commons.Common;
import fr.wither.factures.types.MyDouble;
import fr.wither.factures.types.Vache;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewVacheController {
	@FXML
	private Button btn_retour;
	@FXML
	private ComboBox<String> cbb_designation;
	@FXML
	private TextField lbl_id;
	@FXML
	private TextField lbl_poids;
	@FXML
	private TextField lbl_pu;
	@FXML
	private TableView<Vache> tbv_listeVache;
	@FXML
	private TableColumn<Vache, String> col_designation;
	@FXML
	private TableColumn<Vache, String> col_id;
	@FXML
	private TableColumn<Vache, MyDouble> col_poids;
	@FXML
	private TableColumn<Vache, MyDouble> col_pu;
	@FXML
	private Button btn_addVache;
	@FXML
	private Button btn_done;
	@FXML
	private Button btn_undo;
	
	private ObservableList<Vache> list = FXCollections.observableArrayList();
	private int compteur = -1;
	@FXML
	public void initialize() {
		cbb_designation.getItems().add(0, "--==Selectionner une designation==--");
		cbb_designation.getItems().add(1, "Jeune bovin viande");
		cbb_designation.getItems().add(2, "Vache de réforme");
		cbb_designation.getItems().add(2, "Génisse viande");
		
		col_designation.setCellValueFactory(new PropertyValueFactory<Vache,String>("designation"));
		col_id.setCellValueFactory(new PropertyValueFactory<Vache,String>("id"));
		col_poids.setCellValueFactory(new PropertyValueFactory<Vache, MyDouble>("poids"));
		col_pu.setCellValueFactory(new PropertyValueFactory<Vache, MyDouble>("prixUnitaire"));
		
		clearFields();
	}

	// Event Listener on Button[#btn_retour].onAction
	@FXML
	public void goBack(ActionEvent event) {
		Stage stage = (Stage)btn_retour.getScene().getWindow();
		Common.goBackToMain(stage);
	}
	// Event Listener on Button[#btn_addVache].onAction
	@FXML
	public void addAndReset(ActionEvent event) {
		
		Vache tempVache = new Vache(cbb_designation.getValue(), lbl_id.getText(), new MyDouble(lbl_poids.getText()).getMyDouble(), new MyDouble(lbl_pu.getText()).getMyDouble());
		list.add(tempVache);
		compteur += 1;
		clearFields();
	}
	// Event Listener on Button[#btn_done].onAction
	@FXML
	public void addAndDone(ActionEvent event) {
		Vache tempVache = new Vache(cbb_designation.getValue(), lbl_id.getText(), new MyDouble(lbl_poids.getText()).getMyDouble(), new MyDouble(lbl_pu.getText()).getMyDouble());
		list.add(tempVache);
		clearFields();
		Vache[] vaches = new Vache[list.size()];
		for(int i=0; i < list.size(); i++) {
			vaches[i] = list.get(i);
		}
		Common.FACT.setVaches(vaches);
		Stage stage = (Stage) btn_addVache.getScene().getWindow();
		 try {
			AnchorPane root = FXMLLoader.load(Common.main.getClass().getResource("/fr/wither/factures/view/Confirm.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btn_undo].onAction
	@FXML
	public void undo(ActionEvent event) {
		if(compteur >= -1) {
			clearFields();
			list.remove(compteur);
			tbv_listeVache.refresh();
			compteur --;
		}
	}

	@FXML
	private void clearFields() {
		cbb_designation.getSelectionModel().select(0);
		lbl_id.setText("87033");
		lbl_poids.setText("");
		lbl_pu.setText("");
		tbv_listeVache.setItems(list);
		tbv_listeVache.refresh();
	}
}
