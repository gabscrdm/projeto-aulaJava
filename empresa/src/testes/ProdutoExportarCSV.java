package testes;

import model.ProdutoDAO;

public class ProdutoExportarCSV {

	public static void main(String[] args) {
		
		ProdutoDAO dao = new ProdutoDAO();
		System.out.println(dao.exportarCSV());

	}

}
