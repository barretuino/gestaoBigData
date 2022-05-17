<!DOCTYPE HTML PUBLIC "--//W3C//DTD HTML 4.0 Transation//EN">
<HTML>
<%-- Comentário em jsp aqui: exemplo de declaração de variável em jsp --%>
<HEAD><TITLE>Bem Vindo a T&eacutecnologia JSP</TITLE></HEAD>
<BODY BGCOLOR="#FDF5E6">
<%!
    String mensagem = "Bom Dia!";
%>
<H1>Duas vers&otildees diferentes na hora de imprimir algo:</H1><BR>

<H2>M&eacutetodo um:</H2><BR>
<% out.println(mensagem); %><BR><BR>

<H2>M&eacutetodo dois:</H2><BR>
<%= mensagem %><BR>

<%
    System.out.println("Tudo foi executado com sucesso!");
%>

</BODY>
</HTML>