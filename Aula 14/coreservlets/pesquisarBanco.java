package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class pesquisarBanco extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException{

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try{	
			//Registrando o driver:
			Class.forName("com.mysql.jdbc.Driver");		 
			//Estabelecendo a conexão através do ODBC criado no Painel de Controle:
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/posGraduacao", "root", "");
			//Criando um objeto Statement para enviar requisições SQL para o Banco de Dados 
			Statement stmt = con.createStatement();
			//Executando SQL:		
			stmt.execute("SELECT * FROM acesso " +
					"WHERE (usuario='"+ request.getParameter("nome") + "') AND" +
					"(senha='" + request.getParameter("senha") + "')");	

			//	Adquirindo através de um objeto ResulSet, os registros retornados pela SQL:			
			ResultSet rs = stmt.getResultSet();
		
			String title = "Conexão com Sistema";
			String docType =
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
				"Transitional//EN\">\n";
			out.println(docType +
					"<HTML>\n" +
					"<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
					"<BODY BGCOLOR=\"#FDF5E6\">\n" +
					"<H1 ALIGN=\"CENTER\">" + title + "</H1>\n");

			if (rs.next()) {
				out.println("<H1><FONT COLOR='GREEN'>Conexão realizada com sucesso.</FONT></H1>");
			}else{
				out.println("<H1><FONT COLOR='RED'>Usuario e Senha inválidos.</FONT></H1>");		
			}
			
			stmt.close();
			con.close();
		}catch(Exception erro){
			out.println("<H1>Erro com a conexão " + erro +"<H1>");	
		}
		out.println("<a href=\"javascript:history.back(1)\">Voltar</a>)");
		out.println("\n</BODY></HTML>");
	}
}
