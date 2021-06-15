package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import services.BD;
import services.Numero;

public class ProdutoDAO {

	private BD bd;
	private Produto produto;
	private String men, sql;
	
	public ProdutoDAO() {
		bd = new BD();
	}
	
	/**
	 * Retorna uma lista de produtos a partir de uma instrução em SQL
	 * @param sql - a instrução a ser executada
	 * @param descricao - um parâmetro usado como filtro na descrição do produto
	 * @return - a lista contendo todos os produtos
	 */
	public List<Produto> get(String sql, String descricao){
		List<Produto> lista = new ArrayList<Produto>();
		//preencher a lista a partir do BD
		bd.getConnection();
		try {
			bd.st = bd.con.prepareStatement(sql); //select * from produtos where descricao like ? 
			bd.st.setString(1, descricao + "%");
			bd.rs = bd.st.executeQuery();
			while(bd.rs.next()) { //enquanto existir próximo (todos os registros)
				//adicionar os produtos a lista
				produto = new Produto();
				produto.setCodigo(bd.rs.getInt(1));
				produto.setDescricao(bd.rs.getString(2));
				produto.setPreco(bd.rs.getDouble(3));
				produto.setAtivo(bd.rs.getBoolean(4));
				produto.setFoto(bd.rs.getString(5));
				if(bd.rs.getString(5) == null) produto.setFoto("");
				lista.add(produto);
			}
		}
		catch(SQLException erro) {
			System.out.println(erro.toString());
			lista = null;
		}
		finally {
			bd.close();
		}
		
		return lista;
	}
	
	/**
	 * Método para alterar o status de um produto (ativo ou não)
	 * @param codigo - código do produto a alterar
	 * @param ativo - 0 para inativar, 1 para ativar
	 * @return - uma mensagem informando o resultado da operação
	 */
	public String ativar(int codigo, int ativo) {
		sql = "update produtos set ativo = ? where codigo = ?";
		bd.getConnection();
		try {
			bd.st = bd.con.prepareStatement(sql); 
			bd.st.setInt(1, ativo == 0 ? 0:1);
			bd.st.setInt(2, codigo);
			int n = bd.st.executeUpdate();
			if(n == 1) {
				men = "Status do produto alterado com sucesso!";
			}
			else {
				men = "Produto não localizado!";
			}
		}
		catch(SQLException erro) {
			System.out.println(erro.toString());
		}
		finally {
			bd.close();
		}
		return men;
	}
	
	/**
	 * Método para alterar um produto existente
	 * @param p - produto a ser alterado
	 * @return - retorna uma mensagem se o produto foi alterado com sucesso ou não
	 */
	public String editar(Produto p) {
		sql = "update produtos set descricao=?,preco_unitario=?,foto=? where codigo=?";
		if(bd.getConnection()) {
			try {
				bd.st = bd.con.prepareStatement(sql); 
				bd.st.setString(1, p.getDescricao());
				bd.st.setDouble(2, p.getPreco());
				bd.st.setString(3, p.getFoto());
				bd.st.setInt(4, p.getCodigo());
				if (p.getFoto() == null) bd.st.setString(3, "");
				bd.st.executeUpdate();
				men = "Produto alterado com sucesso!";
			}
			catch(SQLException erro) {
				men = "Falha na alteração do produto! "+ erro.toString();
			}
			finally {
				bd.close();
			}
		}
		else {
			men = "Falha na conexão ao Banco de Dados";
		}
		return men;
	}
	
	/**
	 * Método para incluir um novo produto 
	 * @param p - produto a ser incluido
	 * @return - retorna uma mensagem se o produto foi incluido com sucesso ou nao
	 */
	public String incluir(Produto p) {
		sql = "insert into produtos(descricao,preco_unitario,ativo,foto) values(?,?,?,?)";
		if(bd.getConnection()) {
			try {
				bd.st = bd.con.prepareStatement(sql); 
				bd.st.setString(1, p.getDescricao());
				bd.st.setDouble(2, p.getPreco());
				bd.st.setBoolean(3, true);
				bd.st.setString(4, p.getFoto());
				bd.st.executeUpdate();
				men = "Produto registrado com sucesso!";
			}
			catch(SQLException erro) {
				men = "Falha na inclusão do produto! "+erro.toString();
			}
			finally {
				bd.close();
			}
		}
		else {
			men = "Falha na conexão ao Banco de Dados!";
		}
		return men;
	}
	
	public String excluir(int codigo) {
		sql = "update produtos set ativo=0 where codigo=?";
		if(bd.getConnection()) {
			try {
				bd.st = bd.con.prepareStatement(sql); 
				bd.st.setInt(1, codigo);
				int n = bd.st.executeUpdate();
				if(n == 1) {
					men = "Produto removido com sucesso!";
				}
				else {
					men = "Produto não encontrado!";
				}
			}
			catch(SQLException erro) {
				men = "Falha na remoção do produto!";
				System.out.println(erro.toString());
			}
			finally {
				bd.close();
			}
		}
		else {
			men = "Falha na conexão ao Banco de Dados";
		}
		return men;
	}
	
	public String exportarCSV() {
		bd.getConnection();
		try {
			PrintWriter pw = new PrintWriter("d:/temp/produtos.csv");
			pw.println("codigo; descricao; preco_unitario; ativo; foto");
			sql = "select * from produtos";
			bd.st = bd.con.prepareStatement(sql); 
			bd.rs = bd.st.executeQuery();
			while(bd.rs.next()) { 
				pw.print(bd.rs.getInt(1)+"; ");
				pw.print(bd.rs.getString(2)+"; ");
				pw.print(bd.rs.getDouble(3)+"; ");
				pw.print(bd.rs.getInt(4)+"; ");
				pw.print(bd.rs.getString(5)+"\n");
			}
			pw.close();
			return "Arquivo gerado com sucesso!";
		}
		catch(SQLException erro) {
			System.out.println(erro);
		}
		catch(IOException erro) {
			System.out.println(erro);
		}
		finally {
			bd.close();
		}
		return "Falha na geração do arquivo!";
	}
	
	public String importarCSV() {
		bd.getConnection();
		try {
			sql = "insert into produtos_bckp values (?,?,?,?,?)";
			bd.st = bd.con.prepareStatement(sql); 
			BufferedReader br = new BufferedReader(new FileReader("d:/temp/produtos.csv"));
			String linha = "";
			br.readLine(); //pula a linha de cabeçalho
			while((linha = br.readLine()) != null) { //enquanto existiterm linhas no arquivo .csv
				//inserir dados no banco 
				String[] dados = linha.split(";"); //dados[0] --> código...
				bd.st.setString(1, dados[0]);
				bd.st.setString(2, dados[1]);
				bd.st.setString(3, dados[2]);
				bd.st.setString(4, dados[3]);
				bd.st.setString(5, dados[4]);
				bd.st.executeUpdate();
			}
			br.close();
			return "Dados armazenados com sucesso!";
		}
		catch(SQLException erro) {
			System.out.println(erro);
		}
		catch(IOException erro) {
			System.out.println(erro);
		}
		finally {
			bd.close();
		}
		return "Falha na gravação dos dados!";
	}
	
	public String gerarPDF() {
		try {
			String file = "d:/temp/produtos.pdf";
			PdfWriter writer = new PdfWriter(file);
			PdfDocument pdfDoc = new PdfDocument(writer);
			pdfDoc.addNewPage();
			Document doc = new Document(pdfDoc);
			
			String img = "d:/temp/itext.png";
			ImageData data = ImageDataFactory.create(img);
			Image imagem = new Image(data);
			imagem.setHorizontalAlignment(HorizontalAlignment.CENTER);
			doc.add(imagem);
			
			PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
			Text titulo = new Text("Relatório de Produtos");
			titulo.setFont(font);
			titulo.setFontSize(24);
			titulo.setFontColor(Color.BLUE);
			
			Paragraph p = new Paragraph();
			p.setTextAlignment(TextAlignment.CENTER);
			p.add(titulo);
			doc.add(p);
			
			float[] pontos = {50f, 300f, 100f};
			Table t = new Table(pontos);
			Cell cCodigo = new Cell().add("Código").setBackgroundColor(Color.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER).setBold();
			Cell cDescricao = new Cell().add("Descrição").setBackgroundColor(Color.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER).setBold();
			Cell cPreco = new Cell().add("Preço").setBackgroundColor(Color.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER).setBold();	
			t.addCell(cCodigo); 
			t.addCell(cDescricao); 
			t.addCell(cPreco);
			
			List<Produto> lista = get("select * from produtos where descricao like ?","");
			for(Produto prod: lista) {
				//System.out.println(prod);
				t.addCell(new Cell().add(""+prod.getCodigo()));
				t.addCell(new Cell().add(prod.getDescricao()));
				t.addCell(new Cell().add("R$ "+Numero.formatar(prod.getPreco(), 2)).setTextAlignment(TextAlignment.RIGHT));
			}
			t.setHorizontalAlignment(HorizontalAlignment.CENTER);
			doc.add(t);
			doc.close();
			return "Arquivo gerado com sucesso!!";
		}
		catch(IOException erro) {
			return "Falha na geração do arquivo! " + erro;
		}
		
	}
}
