<%@ page contentType="text/html" language="java" import="java.sql.*"%>  

<!DOCTYPE HTML PUBLIC "--//W3C//DTD HTML 4.0 Transation//EN">
  
<head>  
   <title>Dele&ccedil&atildeo de Visitante</title>  
</head>  
  
<body>  
  
<%  
      Connection con;  
   Statement stm;  
     
   try {  
  
      Class.forName("com.mysql.jdbc.Driver");  
      con = DriverManager.getConnection("jdbc:mysql://localhost/posGraduacao","root","");  
      stm = con.createStatement();   
      stm.executeUpdate("DELETE FROM `visita` WHERE rg ='" + request.getParameter("rg") + "'");
      out.println("<H1><Font color=Green>Exclusão efetuada com sucesso</FONT></H1>");  
      out.print("<CENTER><a href=\"../frmCadVisitante.jsp\">Cadastrar Novo Visitante</a></CENTER>");
      out.println("<CENTER><a href=\"./servlet/coreservlets.listarBancoDados\">Consultar Visitantes</a></CENTER>");
           
   } catch (Exception e) {  
      out.println("<H1><Font color=Red>Não foi possível conectar ao banco" + e.getMessage() + "</FONT></H1>"); 
      out.println("<CENTER><a href=\"javascript:history.back(1)\">Voltar</a></CENTER>");
   }  
%>  			