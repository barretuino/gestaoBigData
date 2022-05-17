<%@ page contentType="text/html" language="java" import="java.sql.*"%>  

<!DOCTYPE HTML PUBLIC "--//W3C//DTD HTML 4.0 Transation//EN">
  
<head>  
   <title>Teste de Conex&atildeo</title>  
</head>  
  
<body>  
  
<%  
      Connection con;  
   Statement stm;  
     
   try {  
  
      Class.forName("com.mysql.jdbc.Driver");  
      con = DriverManager.getConnection("jdbc:mysql://localhost/posGraduacao","root","");    
      stm = con.createStatement();   
      out.println("Conexão efetuada com sucesso");  
           
   } catch (Exception e) {  
      out.println("Não foi possível conectar ao banco" + e.getMessage());  
   }  
%>  
  
</body>  
