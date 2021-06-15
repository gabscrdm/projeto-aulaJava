package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Cliente;
import model.Mensagem;

/**
 * Servlet implementation class ServletCliente
 */
@WebServlet("/cliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = br.readLine();
		System.out.println(json);
		
		//mapeamento json --> cliente
		ObjectMapper mapper = new ObjectMapper();
		Cliente cliente = mapper.readValue(json, Cliente.class);
		System.out.println(cliente);
		
		//gravado no banco de dados
		String resp = "Dados armazenados com sucesso";
		Mensagem m = new Mensagem(resp, "ServletCliente");
		response.setContentType("application/json"); //o retorno será em JSON
		mapper.writeValue(response.getOutputStream(), m); //objeto de mensagem
		
	}

}
