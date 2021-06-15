package services;

import java.text.DecimalFormat;

public class Numero {

	public static String formatar(double numero, int quantCasas) {
		String mascara = "0.";
		for(int a = 0; a < quantCasas; a++) {
			mascara += "0";
		}
		DecimalFormat df = new DecimalFormat(mascara);
		return df.format(numero);
	}
}
