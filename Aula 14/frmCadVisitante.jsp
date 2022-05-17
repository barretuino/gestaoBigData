<!DOCTYPE HTML PUBLIC "--//W3C//DTD HTML 4.0 Transation//EN">

<html>

<head>
   <title>Cadastro de Visitantes</title>
   <link href="media/css/forms.css" rel="stylesheet" type="text/css" />
</head>

<body BGCOLOR="#FDF5E6">
<FORM ACTION="/servlet/coreservlets.incluirBancoDados" METHOD=POST>
   <table width="700" border="0" cellspacing="1" cellpadding="0" align="center" class="default">
      <tr>
         <td align="middle" colspan="2" class="header"><b>Controle de Visitantes</b></td>
      </tr>
      <tr>
         <td align="middle" colspan="2" class="titulo"><b>Dados do Visitante</b></td>
      </tr>
      <tr>
         <td width="20%" align="right" class="labels">Nome:</td>
         <td class="fields"><input name="nome" size="50" ></td>
      </tr>
      <tr>
         <td align="right" class="labels">R.G.:</td>
         <td class="fields"><input name="rg" value='<% if (request.getParameter("parRg") != null) out.println(request.getParameter("parRg")); %>'></td>
      </tr>
      <tr>
         <td align="right" class="labels">Empresa:</td>
         <td class="fields"><input name="empresa" size="50" ></td>
      </tr>
      <tr>
         <td align="middle" colspan="2" class="titulo">Responsável para recepcioná-lo na empresa</td>
      </tr>
      <tr>
         <td align="right" class="labels">Responsável / Ramal:</td>
         <td class="fields">
            <input name="responsavel" size="35" >
            <input name="ramal" size="10" >
         </td>
      </tr>
      <tr>
         <td align="right" class="labels">Área:</td>
         <td class="fields"><input name="area" size="35" ></td>
      </tr>
      <tr>
         <td align="right" class="labels">1ª Visita:</td>
         <td class="fields">
            <input type="radio" name="primeiraVisita" value="S" checked> Sim
            <input type="radio" name="primeiraVisita" value="N" 
     > Não
         </td>
      </tr>
      <tr>
         <td align="right" class="labels">Chegada Prevista:</td>
         <td class="fields">
            <input name="data" size="10" >
            <input name="hora" size="10" >
         </td>
      </tr></TR>
      <tr>
      <td align="middle" colspan="2" class="titulo">
      <P>   </P></td></tr>
      <tr>   
		

         <td colspan="2" height="40" align="middle" valign="bottom">
         <INPUT type=submit value=Cadastrar>&nbsp; 
         <INPUT type=reset value=Limpar> 
         </td>
      </tr>
		</table>
</FORM>
</body>