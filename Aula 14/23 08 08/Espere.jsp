<!DOCTYPE HTML PUBLIC "--//W3C//DTD HTML 4.0 Transation//EN">
<HTML>
<%-- Exemplo com forward - redirecionamento de página --%>
<HEAD><TITLE>Ciclo de Vida de uma página JSP</TITLE></HEAD>
<BODY BGCOLOR="#FDF5E6">
<%!
    String mensagem="";

    //Executado na primeira vez que é carregado - Inicio
    public void jspInit(){
         mensagem = "Neste momento a página JSP foi carregada!";
         System.out.println("Criada a página JSP");
    }

    //Executado quando deixa a memória - Fim
    public void jspDestroy(){
         mensagem = "Esta mensagem não será vista!";
         System.out.println("Destruida a página JSP");		
    }

%>
<% out.println(mensagem); %><BR><BR>

<% <jsp:forward page="Hello.jsp">
   </jsp:forward>  
%>
    
</BODY>
</HTML>