<!DOCTYPE HTML PUBLIC "--//W3C//DTD HTML 4.0 Transation//EN">
<HTML>
<%-- Exemplo com forward - redirecionamento de p�gina --%>
<HEAD><TITLE>Ciclo de Vida de uma p�gina JSP</TITLE></HEAD>
<BODY BGCOLOR="#FDF5E6">
<%!
    String mensagem="";

    //Executado na primeira vez que � carregado - Inicio
    public void jspInit(){
         mensagem = "Neste momento a p�gina JSP foi carregada!";
         System.out.println("Criada a p�gina JSP");
    }

    //Executado quando deixa a mem�ria - Fim
    public void jspDestroy(){
         mensagem = "Esta mensagem n�o ser� vista!";
         System.out.println("Destruida a p�gina JSP");		
    }

%>
<% out.println(mensagem); %><BR><BR>

<% <jsp:forward page="Hello.jsp">
   </jsp:forward>  
%>
    
</BODY>
</HTML>