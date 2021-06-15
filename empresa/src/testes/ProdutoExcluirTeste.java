package testes;

import model.ProdutoDAO;

public class ProdutoExcluirTeste {

	public static void main(String[] args) {

		ProdutoDAO dao = new ProdutoDAO();
		System.out.println(dao.ativar(5,1));
		System.out.println(dao.ativar(6,1));
		System.out.println(dao.ativar(7,1));
		System.out.println(dao.ativar(8,1));
		

	}

}
