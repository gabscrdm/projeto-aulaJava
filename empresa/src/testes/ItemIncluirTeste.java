package testes;

import model.Item;
import model.ItemDAO;

public class ItemIncluirTeste {

	public static void main(String[] args) {
		Item i = new Item(1, 2, 10, 2.34);
		ItemDAO dao = new ItemDAO();
		
		System.out.println(dao.incluir(i));
	}

}
