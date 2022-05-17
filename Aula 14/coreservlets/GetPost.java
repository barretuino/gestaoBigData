package coreservlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class GetPost extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Metodo Get</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<FORM method=post>");
		out.println("<BR> Primeiro Nome: <INPUT type=text name=primeiroNome>");
		out.println("<BR> Ultimo Nome: <INPUT type=text name=ultimoNome>");
		out.println("<BR><INPUT type=submit value=ENVIAR!>");
		out.println("</FORM>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String Nome = request.getParameter("primeiroNome");

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Metodo Post</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<h1>Post recebido!</h1>");
		out.println("<BR> Primeiro Nome: " + Nome + "<BR>");
		Nome = request.getParameter("ultimoNome");
		out.println("<BR> Ultimo Nome: " + Nome);
		out.println("<HR>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}

}