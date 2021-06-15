<%@page import="java.io.File"%>
<%@page import="services.Diretorio"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="model.Produto"%>
<%@ page import="model.ProdutoDAO" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%
//inclusão de um novo produto (dados+imagem)
//alteração da foto (nome do arquivo+imagem)

String acao = "";
ProdutoDAO dao = new ProdutoDAO();
Produto p = new Produto();

//descompactar os dados (multipart)
FileItemFactory fif = new DiskFileItemFactory();
ServletFileUpload sfu = new ServletFileUpload(fif);

//copiar todos os itens do pacote numa lista
try{
	String path = getServletContext().getRealPath("/")+"fotos/";
	System.out.println(path); //caminho onde as imagens do produto serão armazenadas
	
	List<FileItem> itens = sfu.parseRequest(request);
	Iterator<FileItem> iterator = itens.iterator();
	while(iterator.hasNext()){ //percorre todos os itens da lista FIleItem
		FileItem item = (FileItem) iterator.next(); //pega o item atual
		if(item.getFieldName().equals("p_codigo")){
			p.setCodigo(Integer.parseInt(item.getString()));
		}
		else if(item.getFieldName().equals("p_acao")){
			acao = item.getString();
		}
		else if(item.getFieldName().equals("p_descricao")){
			p.setDescricao(item.getString());
		}
		else if(item.getFieldName().equals("p_preco")){
			p.setPreco(Double.parseDouble(item.getString()));
		}
		else if(item.getFieldName().equals("p_foto")){
			//caminho + o nome do arquivo
			String fileName = Diretorio.getFileNameFromPath(item.getName());
			p.setFoto(fileName);
			//gravar a imagem na pasta do servidor
			File file = new File(path+fileName);
			item.write(file); //grava o arquivo em disco
		}
	}
	if(acao.equals("editar")){ //alterar a imagem do produto
		session.setAttribute("men", dao.editar(p));
	}
	else{
		session.setAttribute("men", dao.incluir(p));
	}
	
}
catch(Exception e){
	System.out.println("Erro: " + e);
}


response.sendRedirect("../empresa/produtos");

%>
