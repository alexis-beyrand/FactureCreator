package fr.wither.factures.types;

import java.util.ArrayList;

public class Facture {
	private String date;
	private ArrayList<Vache> vaches; //designation + id + poids + PU
	
	public Facture(String date, Vache vaches[]) {
		this.vaches = new ArrayList<Vache>();
		this.date = date;
		for(int i = 0; i < vaches.length; i++) {
			this.vaches.add(vaches[i]);
		}
		
	}

	
	public Facture() {
		this.date = "VIDE";
		this.vaches = new ArrayList<Vache>();
	}


	public String getDate() {
		return date;
	}

	public Vache[] getVaches() {
		Vache tablVaches[] = new Vache[this.vaches.size()];
		
		for(int i = 0; i < this.vaches.size(); i++) {
			tablVaches[i] = this.vaches.get(i);
		}
		return tablVaches;
	}
	
	public void setVaches(Vache[] vaches) {
		for(int i = 0; i < vaches.length; i++) {
			this.vaches.add(vaches[i]);
		}		
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public int getNbOfVaches() {
		return this.vaches.size();
	}
	public double getPoidsTotal() {
		double poids = 0;
		for(int i=0; i < this.getNbOfVaches(); i++) {
			poids += this.vaches.get(i).getPoids();
		}
		return poids;
	}
	
	public double getMontantTotal() {
		double montant = 0;
		
		for(int i = 0; i < this.getNbOfVaches(); i++) {
			montant += (double) Math.round(this.vaches.get(i).getMontant() * 100) / 100;
		}
		
		return montant;
	}

}
