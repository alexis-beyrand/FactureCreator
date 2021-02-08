package fr.wither.factures.types;

import java.text.NumberFormat;

public class MyDouble {
	private double myDouble;
	
	public MyDouble(String myDouble) {
		String formated_e1 = myDouble.replaceAll(" ", "");
		String formated_e2 = formated_e1.replaceAll(",",".");
		try {
			this.myDouble = new Double(formated_e2);
		} catch (NumberFormatException e) {
			this.myDouble = 0.0;
		}
	}
	public MyDouble(double myDouble) {
		this.myDouble = myDouble;
	}
	
	
	public String toString() {
		NumberFormat format = NumberFormat.getInstance();
		return format.format(this.myDouble);
	}
	
	public double getMyDouble() {
		return myDouble;
	}
}
