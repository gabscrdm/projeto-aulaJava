package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BD {

	//3 objetos diferentes

	//conecta
	public Connection con = null; 		//realiza a conex�o, usando o driver
	public PreparedStatement st = null; //sabe executar instru��es em SQL
	public ResultSet rs = null; 		//recebe o resultado de uma consulta em SQL
	// para MySQL8 --> com.mysql.cj.jdbc.Driver
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String DATABASENAME = "empresa";
	private final String URL = "jdbc:mysql://localhost:3306/"+DATABASENAME+"?useTimezone=true&serverTimezone=UTC";
	private final String LOGIN = "root";
	private final String PASSWORD = "cardim95";


	public static void main(String[] args) {
		BD bd = new BD();
		bd.getConnection();
		//executo a opera��o desejada
		bd.close();
	}

	/**
	 * Realiza a conex�o ao banco de dados por meio do driver.
	 * @return - true em caso de sucesso, ou false caso contr�rio.
	 */
	public boolean getConnection() {
		try {
			Class.forName(DRIVER);
			//System.out.println("Driver carregado com sucesso.");
			con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			System.out.println("Conectou...");
			return true;
		}
		catch(ClassNotFoundException erro) {
			System.out.println("Driver n�o encontrado!!");
		}
		catch(SQLException erro) {
			System.out.println("Falha na conex�o ao Banco de Dados!!" + erro.toString());
		}
		return false;
	}

	/**
	 * Encerra a conex�o ao banco de dados.
	 */
	public void close() {
		try {
			rs.close();
		} 
		catch (Exception e) {}

		try {
			st.close();
		} 
		catch (Exception e) {}

		try {
			con.close();
			System.out.println("\nDesconectou...");
		} 
		catch (Exception e) {}
	}
}