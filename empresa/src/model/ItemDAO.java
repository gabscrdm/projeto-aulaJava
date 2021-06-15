package model;
import java.sql.SQLException;
import services.BD;

public class ItemDAO {
	private String sql;
	private BD bd;
	
	public ItemDAO() {
		bd = new BD();
	}
	
	public boolean incluir(Item item) {
		sql = "insert into itens_pedido values(?,?,?,?)";
		bd.getConnection();
		try {
			bd.st = bd.con.prepareStatement(sql); 
			bd.st.setInt(1, item.getNumeroPedido());
			bd.st.setInt(2, item.getCodigoProduto());
			bd.st.setInt(3, item.getQuantidade());
			bd.st.setDouble(4, item.getValor());
			bd.st.executeUpdate();
			return true;
		}
		catch(SQLException erro) {
			System.out.println(erro);
			
		}
		finally {
			bd.close();
		}
		return false;
	}
}
