<!--#include file="../connections/Session.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%

if request.Form("ID_UNIDAD") <> "" then
	idUnidad = request.Form("ID_UNIDAD")
elseif request.QueryString("ID_UNIDAD") <> "" then
	idUnidad = request.QueryString("ID_UNIDAD")
else
	idUnidad = ""
end if	

if request.Form("EDIT") <> "" then
	edit = request.Form("EDIT")
elseif request.QueryString("EDIT") <> "" then
	edit = request.QueryString("EDIT")
else
	edit = ""
end if	

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=windows-1252">
<TITLE></TITLE>
<LINK HREF="../CSS/styles.css" REL="stylesheet" TYPE="text/css">
<script type="text/javascript" src="../JS/scriptLibrary.js"></script>
</HEAD>


<body style="margin-top:5px; margin-left:0px; margin-bottom:0px; margin-right:0px">

<table width="100%" border=0 align="CENTER" cellspacing=2>
  <caption>&nbsp;
	
  </caption>
  <thead>
	<th width="2%">&nbsp;&nbsp;</th>  
	<th width="21%" style="text-align:left">&nbsp;&nbsp;ACCESORIO</th>  
	<th width="8%">COSTO</th>      
	<th width="15%">ULTIMA ACTUALIZACIÓN</th>            
	<th width="15%">USUARIO</th>
	<th width="10%">STATUS</th>    
	<th width="15%">NO. SERIE</th>           
  </thead>    
  <%

set con = new cConnection
con.startConnection(DB_INI)
sql="SELECT "
sql=sql+"  BD_UNIDAD_ACCESORIO.ID_UNIDAD_ACCESORIO"
sql=sql+" ,BD_UNIDAD_ACCESORIO.ID_ACCESORIO"
sql=sql+" ,BD_UNIDAD_ACCESORIO.ID_UNIDAD"
sql=sql+" ,BD_UNIDAD_ACCESORIO.COSTO"
sql=sql+" ,DESC_ACCESORIO"
sql=sql+" ,BD_UNIDAD_ACCESORIO.ID_STATUS_ACCESORIO"
sql=sql+" ,BD_UNIDAD_ACCESORIO.FEC_STATUS"
sql=sql+" ,C_USUARIOS.NOMBRE + ' ' + C_USUARIOS.PATERNO + ' ' + C_USUARIOS.MATERNO AS NOMBRE_USUARIO"
sql=sql+" ,C_STATUS_ACCESORIO.DESC_STATUS_ACCESORIO"
sql=sql+" ,BD_UNIDAD_ACCESORIO.NO_SERIE"
sql=sql+" FROM BD_UNIDAD_ACCESORIO"
sql=sql+" INNER JOIN C_STATUS_ACCESORIO ON BD_UNIDAD_ACCESORIO.ID_STATUS_ACCESORIO = C_STATUS_ACCESORIO.ID_STATUS_ACCESORIO"
sql=sql+" INNER JOIN C_ACCESORIOS ON BD_UNIDAD_ACCESORIO.ID_ACCESORIO = C_ACCESORIOS.ID_ACCESORIO" 
sql=sql+" INNER JOIN C_USUARIOS ON BD_UNIDAD_ACCESORIO.ID_USUARIO_STATUS = C_USUARIOS.ID_USUARIO" 
sql=sql+" WHERE BD_UNIDAD_ACCESORIO.ID_UNIDAD = " & idUnidad
set rs = con.executeQuery(sql)
if not rs.eof then

counter = 1
do until rs.EOF	
%>

  <tr>
  	  <form method="post" action="./DAO/updateAccesorioStatus.asp">
      <input type="hidden" id="ID_UNIDAD" name="ID_UNIDAD" value="<%= rs.fields("ID_UNIDAD") %>" >  
      <input type="hidden" id="EDIT" name="EDIT" value="<%= edit %>" >           
      <input type="hidden" id="ID_UNIDAD_ACCESORIO" name="ID_UNIDAD_ACCESORIO" value="<%= rs.fields("ID_UNIDAD_ACCESORIO") %>" >
      <td class="tdMenuCenter">  
      		<img src="../IMAGES/btBox.png">
      </td>   
  
      <td class="tdMenuLeft">
      	&nbsp;&nbsp;<%= rs.fields("DESC_ACCESORIO") %>
      </td>   
      
      <td class="tdMenuCenter">
      	$&nbsp;<%= rs.fields("COSTO") %>
      </td>            
      
      <td class="tdMenuCenter">
      	&nbsp;&nbsp;<%= rs.fields("FEC_STATUS") %>
      </td>        
      
      <td class="tdMenuCenter">
      	<%= rs.fields("NOMBRE_USUARIO") %>
      </td>      

      <td class="tdMenuCenter">
    
        <%
		
			if edit = "1" then
			
			     response.write("<select name='ID_STATUS_ACCESORIO' id='ID_STATUS_ACCESORIO' title='Cambiar Status del Accesorio' style='width:95%;' onChange='this.form.submit();'>" & vbCrLf) 

			
				set con1 = new cConnection
				con1.startConnection(DB_INI)
				sql1=" SELECT" &_
					 "  ID_STATUS_ACCESORIO" &_
					 " ,DESC_STATUS_ACCESORIO" &_
					 " ,CASE WHEN ID_STATUS_ACCESORIO = '" & rs.fields("ID_STATUS_ACCESORIO") & "' THEN 'selected' else '' END AS SELECTED" &_
					 " FROM C_STATUS_ACCESORIO WHERE STATUS = 'ACTIVO'"
				set rs1 = con1.executeQuery(sql1)
				if not rs1.eof then
				
					do until rs1.EOF
					
						response.write("<option value=" & rs1.fields("ID_STATUS_ACCESORIO") & "  " & rs1.fields("SELECTED") & ">" & rs1.fields("DESC_STATUS_ACCESORIO") & "</option>" & vbCrLf)
					
					rs1.MoveNext
					loop	
					
				end if
				
				rs1.close
				set rs1 = nothing
				con1.endConnection
				
				response.write("</select>" & vbCrLf)					
			
			else	
			
				response.write(rs.fields("DESC_STATUS_ACCESORIO"))	
				
			end if							
        %>               
      </td>
      <td class="tdMenuCenter">
      	<%	
				if edit = "1" then			
					response.write("<input type='text' name='NO_SERIE' id='NO_SERIE' value='" & rs.fields("NO_SERIE") & "' maxlength=50 onChange='this.form.submit();'>")
				else
					response.write(rs.fields("NO_SERIE"))
				end if
		 %>
      </td>       
      </form>
  </tr>
  <%
counter = counter + 1
rs.MoveNext
loop

end if
rs.close
set rs = nothing
con.endConnection
%>

</table>

</BODY>
</HTML>