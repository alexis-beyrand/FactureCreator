package fr.wither.factures.types;

public class Vache {
	private String designation; //Jeune bovin viande ou Vache de reforme ou Génisse viande
	private String id; //87033XXXXX
	private MyDouble poids;
	private MyDouble prixUnitaire;
	
	public Vache(String designation, String id, double poids, double prixUnitaire) {
		this.designation = designation;
		this.id = id;
		this.poids = new MyDouble(poids);
		this.prixUnitaire = new MyDouble(prixUnitaire);
	}

	public String getDesignation() {
		return this.designation;
	}

	public String getId() {
		return this.id;
	}

	public double getPoids() {
		return this.poids.getMyDouble();
	}

	public double getPrixUnitaire() {
		return this.prixUnitaire.getMyDouble();
	}

	public double getMontant() {
		return this.getPoids() * this.getPrixUnitaire();
	}
}
