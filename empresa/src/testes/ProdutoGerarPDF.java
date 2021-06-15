package testes;

import javax.swing.JOptionPane;

import model.ProdutoDAO;

public class ProdutoGerarPDF {
	
	public static void main(String[] args) {
		ProdutoDAO dao = new ProdutoDAO();
		JOptionPane.showMessageDialog(null, dao.gerarPDF());
	}

}
