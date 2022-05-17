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

public class listarBancoDados extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException{

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			try{	
				//Registrando o driver:
				Class.forName("com.mysql.jdbc.Driver");		 
				//Estabelecendo a conexão através do ODBC criado no Painel de Controle:
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/posgraduacao", "root", "");
				//Criando um objeto Statement para enviar requisições SQL para o Banco de Dados 
				Statement stmt = con.createStatement();
				//Executando SQL:		
				stmt.execute("SELECT * FROM `visita`");

				//	Adquirindo através de um objeto ResulSet, os registros retornados pela SQL:			
				ResultSet rs = stmt.getResultSet();
			
				String title = "Relatório de Visitantes";
				String docType =
					"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
					"Transitional//EN\">\n";
				out.println(docType +
						"<HTML>\n" +
						"<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
						"<BODY BGCOLOR=\"#FDF5E6\">\n" +
						"<H1 ALIGN=\"CENTER\">" + title + "</H1>\n");				
				
				out.println("<H1><FONT COLOR='GREEN'>Relação de Visitantes Cadastrados</FONT></H1>");
				out.println("<table align = center width = 100% heigth = 100% border = 1>" +
						        "<TR><TD align = rigth bgcolor=\"#D3BDA\">Nome</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">RG</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Empresa</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Contato</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Ramal</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Departamento</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Prim. Visita?</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Data</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Hora</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Alteração</TD>" +
							    "<TD align = rigth bgcolor=\"#D3BDA\">Exclusão</TD>" +
							    "</TR>");
				while (rs.next()){
					out.println("<TR><TD>" + rs.getString("nome") + "</TD>" +
							        "<TD>" + rs.getString("rg") + "</TD>" +
							        "<TD>" + rs.getString("empresa") + "</TD>" +
							        "<TD>" + rs.getString("responsavel") + "</TD>" +
							        "<TD>" + rs.getString("ramal") + "</TD>" +
							        "<TD>" + rs.getString("departamento") + "</TD>" +
							        "<TD align=center>" + rs.getString("primVisita") + "</TD>" +
							        "<TD>" + rs.getString("dataChegada") + "</TD>" +
							        "<TD>" + rs.getString("horaChegada") + "</TD>" +
							        "<TD align=center><a href=../frmCadVisitante.jsp?parRg=" + rs.getString("rg") + 
							           " title='Alterar Visita'><img border=0 src=../alterar.gif width=25 height=25></a>" +
							        "<TD align=center><a href=../deletarVisitante.jsp?rg=" + rs.getString("rg") + 
							           " title='Deletar Visita'><img border=0 src=../delete.gif width=25 height=25></a>" +							        
							    	"</TR>");
				}
				out.println("</TABLE><BR><BR><BR><BR>"); 
				out.println("<CENTER><a href=\"../frmCadVisitante.jsp\">Cadastrar Novo Visitante</a></CENTER>");
				stmt.close();
				con.close();
			}catch(Exception erro){
				out.println("<H1><FONT COLOR='RED'>Falha na execução da pesquisa." + erro +"</FONT></H1>");				
				out.println("<CENTER><a href=\"javascript:history.back(1)\">Voltar</a></CENTER>");
			}			
			out.println("\n</BODY></HTML>");
		}
	}
