package testes;

import model.Produto;

public class ProdutoTestar {

	public static void main(String[] args) {
		
		Produto p1 = new Produto();
		p1.setCodigo(1);
		p1.setDescricao("Detergente");
		p1.setPreco(3.20);
		p1.setAtivo(true);
		p1.setFoto("detergente.jpg");
		
		System.out.println(p1.toString());
		
		
		Produto p2 = new Produto(2, "Sabao em Pó", 8.35, true, "sabaopo.jpg");
		
		System.out.println(p2);
	}

}
