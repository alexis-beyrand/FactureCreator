package fr.wither.factures;

import static fr.wither.factures.commons.Common.FACT;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;

import com.itextpdf.io.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import fr.wither.factures.commons.Taux;
import fr.wither.factures.types.MyDouble;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = FXMLLoader.load(getClass().getResource("/fr/wither/factures/view/MainMenu.fxml"));
		//Création d'une Scene pour permettre l'affichage
		Scene scene = new Scene(root);
		
		//Ajout de l'icone
		primaryStage.getIcons().add(new Image("icon.png"));
		//Interdiction de redimentionner la fenêtre
		primaryStage.setResizable(false);
		//Ajout de la Scene au Stage, permettant ainsi l'affichage
		primaryStage.setScene(scene);
		//Instanciation du nom de la fenêtre en "Launcher Avalgan"
		primaryStage.setTitle("Generateur de factures");
		//Affichage de la fenêtre
		primaryStage.show();
		
	}
	
	public static void main(String args[]) throws java.io.IOException {
		launch(args);
	}
	
	
	public void createPDF(String dest) throws IOException {
		try {
			
			Paragraph adresse_gaec = new Paragraph("GAEC BEYRAND SABOURDY\n"+"Les Combes\n"+"87700 BEYNAC\n"+"Tél. 06 37 75 39 51\n"+"TVA fr02398956276");
			Paragraph adresse_client = new Paragraph("ACHATS PLEINEMAISON\n"+"SASU\n"+"18 AVENUE DE L'ABATTOIR\n"+"BP81\n"+"87000 LIMOGES");
			Paragraph facture = new Paragraph("FACTURE");
			Paragraph separator = new Paragraph("\n");
			Paragraph lieu = new Paragraph("BEYNAC Le " + FACT.getDate());
			
			Table vaches = new Table(new float[] {2,2,1,1,2});
			
			Table totaux = new Table(new float[] {3, 2, 1});
			
			Table retenues = new Table(new float[] {3, 2, 2, 1});
			
			
			double poidsTotal = FACT.getPoidsTotal();
			double montantTotal = FACT.getMontantTotal();
			double remise = montantTotal * Taux.REMISE;
			double tvaSurRemise = remise * Taux.TVA_SUR_REMISE;
			double tva10Pourcent = montantTotal * Taux.TVA_DIX_POURCENT;
			double normabev = FACT.getNbOfVaches() * Taux.NORMABEV;
			double tva20Pourcent = normabev * Taux.TVA_VINGT_POURCENT;
			double interbev = poidsTotal * Taux.INTERBEV;
			double fdsElevage = poidsTotal * Taux.FDS_ELEVAGE;
			
			double retenueTotale = remise + normabev + interbev + fdsElevage;
			double taxesTotales = tvaSurRemise + tva10Pourcent + tva20Pourcent;
			
			double totalTTC = montantTotal + retenueTotale + taxesTotales;
			
			vaches.setWidthPercent(100);
			totaux.setWidthPercent(100);
			retenues.setWidthPercent(100);
			
			vaches.addHeaderCell(new Cell().add("DESIGNATION"));
			vaches.addHeaderCell(new Cell().add("IDENTIFICATION"));
			vaches.addHeaderCell(new Cell().add("POIDS"));
			vaches.addHeaderCell(new Cell().add("PU"));
			vaches.addHeaderCell(new Cell().add("MONTANT"));
			
			retenues.addHeaderCell(new Cell().add("RETENUES").setBorder(Border.NO_BORDER));
			retenues.addHeaderCell(new Cell().add("BASE").setBorder(Border.NO_BORDER));
			retenues.addHeaderCell(new Cell().add("TAUX").setBorder(Border.NO_BORDER));
			retenues.addHeaderCell(new Cell().add("").setBorder(Border.NO_BORDER));
						
			
			totaux.addCell(new Cell().add("TOTAL MARCHANDISES").setBorder(Border.NO_BORDER));
			totaux.addCell(new Cell().add(formatDouble(poidsTotal)).setBorder(Border.NO_BORDER));
			totaux.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
			totaux.addCell(new Cell().add("TOTAL HT").setBorder(Border.NO_BORDER));
			totaux.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
			totaux.addCell(new Cell().add(formatDouble(montantTotal)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("REMISE").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(montantTotal)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatToPercent(Taux.REMISE)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(remise)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("TVA/REMISE").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(remise)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatToPercent(Taux.TVA_SUR_REMISE)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(tvaSurRemise)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("TVA 10%").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(montantTotal)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatToPercent(Taux.TVA_DIX_POURCENT)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(tva10Pourcent)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("NORMABEV").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(new Integer(FACT.getNbOfVaches()).toString()).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(Taux.NORMABEV)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(normabev)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("TVA 20%").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(normabev)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatToPercent(Taux.TVA_VINGT_POURCENT)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(tva20Pourcent)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("INTERBEV").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(poidsTotal)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(new MyDouble(Taux.INTERBEV).toString()).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(interbev)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("FDS ELEVAGE").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(poidsTotal)).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(new MyDouble(Taux.FDS_ELEVAGE).toString()).setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(fdsElevage)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("\n").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("\n").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("\n").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("\n").setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("retenues").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(retenueTotale)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("total TVA").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(taxesTotales)).setBorder(Border.NO_BORDER));
			
			retenues.addCell(new Cell().add("TOTAL TTC").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
			retenues.addCell(new Cell().add(formatDouble(totalTTC)).setBorder(Border.NO_BORDER));
			
			
			
			for(int i = 0; i < FACT.getVaches().length; i++) {
				vaches.addCell(new Cell().add(FACT.getVaches()[i].getDesignation()));
				vaches.addCell(new Cell().add(FACT.getVaches()[i].getId()));
				vaches.addCell(new Cell().add(formatDouble(FACT.getVaches()[i].getPoids())));
				vaches.addCell(new Cell().add(formatDouble(FACT.getVaches()[i].getPrixUnitaire())));
				vaches.addCell(new Cell().add(formatDouble(FACT.getVaches()[i].getMontant())));
			}
			
			
			
			lieu.setRelativePosition(350, 0, 0, 0); 
			adresse_gaec.setBold();
			adresse_gaec.setPaddingLeft(15.0f);
			facture.setPaddingLeft(15.0f);
			adresse_client.setBold();
			adresse_client.setRelativePosition(300, 0, 0, 0);
			vaches.setPaddingLeft(15.0f);
			
			
			FileOutputStream fos = new FileOutputStream(dest);
			PdfWriter writer = new PdfWriter(fos);
			
			PdfDocument pdf = new PdfDocument(writer);
			
			Document document = new Document(pdf);
			
			document.add(adresse_gaec);
			document.add(lieu);
			document.add(facture);
			document.add(adresse_client);
			document.add(separator);
			document.add(vaches);
			document.add(separator);
			document.add(totaux);
			document.add(separator);
			document.add(retenues);
			
			document.close();
			
			System.out.println("Génération réussie !");
		} catch (FileNotFoundException e) {
			System.out.println("Fichier non trouvé");
			e.printStackTrace();
		}
	}
	
	
	public String formatDouble(double dble) {
		double d = (double) Math.round(dble * 100) / 100;
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(2);
		String s = format.format(d);
		
		return s;
	}
	
	public String formatToPercent(double dble) {		
		double d = dble * 100;
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(1);
		String s = format.format(d);
		return s + "%";
	}


	
}
