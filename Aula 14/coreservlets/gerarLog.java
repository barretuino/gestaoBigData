package coreservlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class gerarLog extends HttpServlet {
	public void destroy() {
		super.destroy();
		log("Destruindo a servlet");
	}
	public void init() throws ServletException {
		super.init();
		log("Iniciando a servlet");
	}
}