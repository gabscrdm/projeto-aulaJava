package testes;

import model.Produto;
import model.ProdutoDAO;

public class ProdutoIncluirTeste {

	public static void main(String[] args) {
		
		Produto p = new Produto();
		p.setDescricao("Fini");
		p.setPreco(4.99);
		p.setFoto("");
		
		ProdutoDAO dao = new ProdutoDAO();
		System.out.println(dao.incluir(p));

	}

}
