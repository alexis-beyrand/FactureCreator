package fr.wither.factures.commons;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import fr.wither.factures.Main;
import fr.wither.factures.types.Facture;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Common {

	public static Main main = new Main();
	public static Facture FACT = new Facture();
	public static final String BASE_FILE = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/factures/gaec/";
	
	public static final void createFile() {
		String DEST = BASE_FILE + "Facture_" + FACT.getDate().replaceAll("/", "-") + ".pdf";
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		new Main().createPDF(DEST);
	}
	
	
	public static final void goBackToMain(Stage stage) {
		
		try {
			AnchorPane root = FXMLLoader.load(main.getClass().getResource("/fr/wither/factures/view/MainMenu.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
