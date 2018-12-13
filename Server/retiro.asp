<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

if request.QueryString("ia") <> "" then
	idAR = request.QueryString("ia")
else
	idAR = ""
end if

if request.QueryString("ed") <> "" then
	edit = request.QueryString("ed")
else
	edit = ""
end if

if request.QueryString("ip") <> "" then
	idTipoProducto = request.QueryString("ip")
else
	idTipoProducto = "2"
end if

set con = new cConnection
con.startConnection(DB_INI)

    sql = "SELECT " &_
	      "	 BD_AR.ID_TECNICO " &_
		  "	,BD_AR.ID_NEGOCIO " &_
		  "	,BD_AR.ID_CLIENTE " &_
		  "	,ISNULL(C_PRODUCTOS.IS_BOM, -1) AS IS_BOM " &_
		  "FROM BD_AR " &_
		  " LEFT JOIN BD_UNIDADES ON BD_AR.ID_UNIDAD_ATENDIDA = BD_UNIDADES.ID_UNIDAD " &_
	      " LEFT JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO " &_
		  "WHERE ID_AR = " & idAR & "; "
	set rs = con.executeQuery(sql)
	
	if not rs.eof then
	idTecnico = rs.fields("ID_TECNICO")
	idNegocio = rs.fields("ID_NEGOCIO")
	idCliente = rs.fields("ID_CLIENTE")
	isBom = rs.fields("IS_BOM")
	end if	
							
rs.close
set rs = nothing
con.endConnection

set con = new cConnection
con.startConnection(DB_INI)

	sql = "SELECT NOMBRE + ' ' + PATERNO + ' ' + MATERNO AS DESC_TECNICO FROM C_USUARIOS WHERE ID_USUARIO = " & idTecnico									
	set rs = con.executeQuery(sql)
	descTecnico = rs.fields("DESC_TECNICO")
							
rs.close
set rs = nothing
con.endConnection

set con = new cConnection
con.startConnection(DB_INI)

	sql = "SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = " & idNegocio									
	set rs = con.executeQuery(sql)
	descNegocio = rs.fields("DESC_NEGOCIO")
							
rs.close
set rs = nothing
con.endConnection

response.write("<?xml version=""1.0"" encoding=""ISO-8859-1"" ?>")
response.write("<d>")
	
''Dependiendo el valor de Edit, es la información que se proporcionará
if edit = "1" then

set con = new cConnection
con.startConnection(DB_INI)
		   
	sql="SELECT"
	sql=sql+" BD_UNIDADES.ID_UNIDAD"
	sql=sql+",BD_UNIDADES.ID_MODELO"												
	sql=sql+",C_MODELOS.DESC_MODELO"
	sql=sql+",C_MARCAS.ID_MARCA"
	sql=sql+",C_MARCAS.DESC_MARCA"
	sql=sql+",BD_UNIDADES.NO_SERIE"	
	sql=sql+",BD_UNIDADES.NO_INVENTARIO"
	sql=sql+",BD_UNIDADES.NO_IMEI"
	sql=sql+",BD_UNIDADES.POSICION_INVENTARIO"				
	sql=sql+",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"				
	sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"	
	sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"		
	sql=sql+",(SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_NEGOCIO"										
	sql=sql+",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"		
	sql=sql+",C_CLIENTES.DESC_CLIENTE"																											
	sql=sql+" FROM BD_UNIDADES"
	sql=sql+" INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
	sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
	sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"	
	sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"					
	sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"											
	sql=sql+" WHERE BD_UNIDADES.ID_STATUS_UNIDAD IN (17)"
	sql=sql+" AND BD_UNIDADES.STATUS = 'ACTIVO'" 
	sql=sql+" AND BD_UNIDADES.ID_TIPO_RESPONSABLE = 4"
	sql=sql+" AND BD_UNIDADES.ID_RESPONSABLE = " & idNegocio
	sql=sql+" AND BD_UNIDADES.ID_CLIENTE = " & idCliente
	sql=sql+" AND C_PRODUCTOS.ID_TIPO_PRODUCTO = " & idTipoProducto
	sql=sql+" ORDER BY BD_UNIDADES.ID_UNIDAD DESC"			
			
set rs = con.executeQuery(sql)	

''Trae el negocio
if not rs.eof then
	Do until rs.EOF
		response.write("<o><A><![CDATA[" & rs.fields("ID_UNIDAD") 	   	& "]]></A>")
		response.write("<B><![CDATA[" & idNegocio 	   					& "]]></B>")
		response.write("<C><![CDATA[" & descNegocio 	   				& "]]></C>")
		response.write("<D><![CDATA[" & idCliente 	   					& "]]></D>")			
		response.write("<E><![CDATA[" & rs.fields("DESC_CLIENTE") 	    & "]]></E>")	
		response.write("<F><![CDATA[" & rs.fields("ID_MARCA")   	    & "]]></F>")			
		response.write("<G><![CDATA[" & rs.fields("DESC_MARCA")   	    & "]]></G>")
		response.write("<H><![CDATA[" & rs.fields("ID_MODELO")  	    & "]]></H>")
		response.write("<I><![CDATA[" & rs.fields("DESC_MODELO")  	    & "]]></I>")
		response.write("<J><![CDATA[" & rs.fields("NO_SERIE") 	 	    & "]]></J>")
		response.write("<K><![CDATA[" 	& rs.fields("NO_INVENTARIO") 	& "]]></K>")
		response.write("<L><![CDATA[" & rs.fields("DESC_STATUS_UNIDAD") & "]]></L></o>")
	rs.MoveNext
	loop
		
end if

rs.close
set rs = nothing
con.endConnection

end if

'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''Retiros
set con = new cConnection
con.startConnection(DB_INI)
		   
	sql="SELECT"
	sql=sql+" BD_UNIDADES.ID_UNIDAD"
	sql=sql+",BD_UNIDADES.ID_MODELO"	
	sql=sql+",C_MODELOS.DESC_MODELO"
	sql=sql+",C_MARCAS.ID_MARCA"
	sql=sql+",C_MARCAS.DESC_MARCA"
	sql=sql+",BD_UNIDADES.NO_SERIE"	
	sql=sql+",BD_UNIDADES.NO_INVENTARIO"
	sql=sql+",BD_UNIDADES.NO_IMEI"
	sql=sql+",BD_UNIDADES.POSICION_INVENTARIO"				
	sql=sql+",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"				
	sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"	
	sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"		
	sql=sql+",(SELECT NOMBRE + ' ' + PATERNO + ' ' + MATERNO FROM C_USUARIOS WHERE ID_USUARIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_USUARIO"								
	sql=sql+",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"		
	sql=sql+",C_CLIENTES.DESC_CLIENTE"																											
	sql=sql+" FROM BD_UNIDADES"
	sql=sql+" INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
	sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
	sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"	
	sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"					
	sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"											
	sql=sql+" WHERE BD_UNIDADES.ID_STATUS_UNIDAD IN (15)"
	sql=sql+" AND BD_UNIDADES.STATUS = 'ACTIVO'" 
	sql=sql+" AND BD_UNIDADES.ID_TIPO_RESPONSABLE = 2"
	sql=sql+" AND BD_UNIDADES.ID_RESPONSABLE = " & idTecnico	
	sql=sql+" AND BD_UNIDADES.ID_UNIDAD IN (SELECT DISTINCT ID_UNIDAD FROM BD_RETIROS WHERE ID_AR = " & idAR & ")"
	sql=sql+" AND C_PRODUCTOS.ID_TIPO_PRODUCTO = " & idTipoProducto
	sql=sql+" ORDER BY BD_UNIDADES.ID_UNIDAD DESC; "
			
set rs = con.executeQuery(sql)

''Trae el Usuario
if not rs.eof then
	Do until rs.EOF
		response.write("<o><A><![CDATA[" & rs.fields("ID_UNIDAD") 	   	& "]]></A>")
		response.write("<B><![CDATA[" & rs.fields("DESC_USUARIO")	   	& "]]></B>")
		response.write("<C><![CDATA[" & idCliente 	   					& "]]></C>")			
		response.write("<D><![CDATA[" & rs.fields("DESC_CLIENTE") 	    & "]]></D>")	
		response.write("<E><![CDATA[" & rs.fields("ID_MARCA")   	    & "]]></E>")			
		response.write("<F><![CDATA[" & rs.fields("DESC_MARCA")   	    & "]]></F>")
		response.write("<G><![CDATA[" & rs.fields("ID_MODELO")  	    & "]]></G>")
		response.write("<H><![CDATA[" & rs.fields("DESC_MODELO")  	    & "]]></H>")
		response.write("<I><![CDATA[" & rs.fields("NO_SERIE") 	 	    & "]]></I>")
		response.write("<J><![CDATA[" 	& rs.fields("NO_INVENTARIO") 	& "]]></J>")
		response.write("<K><![CDATA[" & rs.fields("DESC_STATUS_UNIDAD") & "]]></K></o>")
	rs.MoveNext
	loop
		
end if

response.write("</d>")

rs.close
set rs = nothing
con.endConnection
%>