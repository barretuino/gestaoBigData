package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientAccessCounts  extends HttpServlet{
	public void doGet (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		String countString = CookieUtilities.getCookieValue (request, "accessCount","1");
		int count = 1;
		try {
			count = Integer.parseInt(countString);
		} catch(NumberFormatException nfe) { }
		LongLivedCookie c =
			new LongLivedCookie("accessCount",
					String.valueOf(count+1));
		response.addCookie(c);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Servlet de Contagem de Acessos";
		String docType =
			"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
			"Transitional//EN\">\n";
		out.println(docType +
				"<HTML>\n" +
				"<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
				"<BODY BGCOLOR=\"#FDF5E6\">\n" +
				"<CENTER>\n" +
				"<H1>" + title + "</H1>\n" +
				"<H2>O numero de visitas é  " +
				count + " para este browser.</H2>\n" +
		"</CENTER></BODY></HTML>");
	}
	public void destroy() {
		super.destroy();
		log("Destruindo a servlet");
	}
	public void init() throws ServletException {
		super.init();
		log("Iniciando a servlet");
	}
}