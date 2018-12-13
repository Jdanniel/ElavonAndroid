<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

set con = new cConnection
con.startConnection(DB_INI)

if request.QueryString("ia") <> "" then
	idAR = request.QueryString("ia")
else
	idAR = ""
end if

	sql = ""
	sql = sql & "SELECT "
	sql = sql & "	 BD_AR.ID_NEGOCIO "
	sql = sql & "	,BD_NEGOCIOS.DESC_NEGOCIO "
	sql = sql & "FROM BD_AR "
	sql = sql & "INNER JOIN BD_NEGOCIOS ON BD_AR.ID_NEGOCIO = BD_NEGOCIOS.ID_NEGOCIO "
	sql = sql & "WHERE BD_AR.ID_AR = " & idAR & "; "
	set rs = con.executeQuery(sql)
	idNegocio = rs.fields("ID_NEGOCIO")
	descNegocio = rs.fields("DESC_NEGOCIO")
							
rs.close
set rs = nothing
con.endConnection

'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''Unidades
set con = new cConnection
con.startConnection(DB_INI)
		   
	sql = "SELECT"
	sql = sql + " BD_UNIDADES.ID_UNIDAD"
	sql = sql + ",BD_UNIDADES.ID_MODELO"												
	sql = sql + ",C_MODELOS.DESC_MODELO"
	sql = sql + ",C_MARCAS.DESC_MARCA"
	sql = sql + ",BD_UNIDADES.NO_SERIE"	
	sql = sql + ",BD_UNIDADES.NO_INVENTARIO"
	sql = sql + ",BD_UNIDADES.NO_IMEI"
	sql = sql + ",BD_UNIDADES.POSICION_INVENTARIO"	
	sql = sql + ",BD_UNIDADES.ID_USUARIO_ALTA"				
	sql = sql + ",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"				
	sql = sql + ",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"	
	sql = sql + ",BD_UNIDADES.ID_STATUS_UNIDAD"		
	sql = sql + ",(SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_NEGOCIO"									
	sql = sql + ",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"		
	sql = sql + ",C_CLIENTES.DESC_CLIENTE"																											
	sql = sql + " FROM BD_UNIDADES"
	sql = sql + " INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
	sql = sql + " INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"	
	sql = sql + " INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"					
	sql = sql + " INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"
	sql = sql + " INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
	sql = sql + " WHERE BD_UNIDADES.ID_STATUS_UNIDAD IN (17)"
	sql = sql + " AND BD_UNIDADES.STATUS = 'ACTIVO'" 
	sql = sql + " AND BD_UNIDADES.ID_TIPO_RESPONSABLE = 4"
	sql = sql + " AND BD_UNIDADES.ID_RESPONSABLE = " & idNegocio
	sql = sql + " AND C_PRODUCTOS.ID_TIPO_PRODUCTO = 2"
	sql = sql + " ORDER BY BD_UNIDADES.ID_UNIDAD DESC"			
			
set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""ISO-8859-1"" ?>")
	response.write("<d>")			 	
	
	''A = NEGOCIO
	''B = CLIENTE
	''C = MARCA
	''D = MODELO 
	''E = NO. SERIE
	''F = NO. INVENTARIO
	''G = STATUS
		
	if not rs.eof then
	
		Do until rs.eof
			response.write("<o>")
			response.write("<A><![CDATA[" & idNegocio & "]]></A>")
			response.write("<B><![CDATA["&rs.fields("DESC_CLIENTE")&"]]></B>")
			response.write("<C><![CDATA[" & rs.fields("DESC_MARCA") & "]]></C>")
			response.write("<D><![CDATA[" & rs.fields("DESC_MODELO") & "]]></D>")
			response.write("<E><![CDATA[" & rs.fields("NO_SERIE") & "]]></E>")
			response.write("<F><![CDATA[" & rs.fields("NO_INVENTARIO") & "]]></F>")
			response.write("<G><![CDATA[" & rs.fields("ID_STATUS_UNIDAD") & "]]></G>")
			response.write("</o>")
		rs.MoveNext
		loop
	
	end if	
	response.write("</d>")			 		 
					   
rs.close
set rs = nothing
con.endConnection	
%>