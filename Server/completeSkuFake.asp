<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

set con1 = new cConnection
con1.startConnection(DB_INI)

set rs1 = nothing
con1.endConnection

set con = new cConnection
con.startConnection(DB_INI)
	
	sql ="SELECT TOP(20) " &_
		 "	 MO_MODULO.SKU " &_ 
		 "	,MO_MODULO.ID_MODELO " &_
		 "	,MO_MODULO.ID_MARCA " &_
		 "	,MA_MODULO.DESC_MARCA " &_
		 "	,MA_MODULO.DESCRIPCION " &_
		 "	,MO_MODULO.DESC_MODELO " &_
		 "FROM BD_MODELO_MODULO " &_ 
		 "INNER JOIN C_PRODUCTOS P_MODULO " &_
		 "	ON BD_MODELO_MODULO.ID_PRODUCTO_MODULO = P_MODULO.ID_PRODUCTO " &_
		 "INNER JOIN C_MARCAS MA_MODULO " &_
		 "	ON BD_MODELO_MODULO.ID_MARCA_MODULO = MA_MODULO.ID_MARCA " &_
		 "INNER JOIN C_MODELOS MO_MODULO " &_
		 "	ON BD_MODELO_MODULO.ID_MODELO_MODULO = MO_MODULO.ID_MODELO " &_
		 "INNER JOIN C_MODELOS MO_UNIDAD " &_
		 "	ON BD_MODELO_MODULO.ID_MODELO = MO_UNIDAD.ID_MODELO " &_
		 "INNER JOIN C_MARCAS MA_UNIDAD " &_
		 "	ON MO_UNIDAD.ID_MARCA = MA_UNIDAD.ID_MARCA " &_
		 "INNER JOIN C_PRODUCTOS P_UNIDAD " &_
		 "	ON MA_UNIDAD.ID_PRODUCTO = P_UNIDAD.ID_PRODUCTO " &_
		 "WHERE " &_
		 "	P_UNIDAD.ID_PRODUCTO = 2" &_
		 "	AND MO_MODULO.SKU IS NOT NULL " &_
		 "	AND MO_UNIDAD.STATUS = 'ACTIVO' " &_
		 "	AND MO_MODULO.STATUS = 'ACTIVO' " &_
		 "	AND MO_MODULO.SKU LIKE '%"	& replace(Request.QueryString("mask")," ","") & "%' " &_
		 "GROUP BY MO_MODULO.SKU, MO_MODULO.ID_MODELO, MO_MODULO.ID_MARCA , MA_MODULO.DESC_MARCA, MA_MODULO.DESCRIPCION, MO_MODULO.DESC_MODELO " &_
		 "ORDER BY MO_MODULO.SKU, MO_MODULO.ID_MODELO ASC; "
		 
	set rs = con.executeQuery(sql)	
	
	response.write("<?xml version=""1.0"" encoding=""ISO-8859-1"" ?>")
	response.write("<d>")			 	

	''A = ID_MODELO
	''B = ID_MARCA
	''C = SKU
	''D = DESC_MARCA
	''E = DESCRIPCION 
	''F = DESC_MODELO
		
	if not rs.eof then
	
		Do until rs.eof
			response.write("<o A = """ & rs.fields("ID_MODELO") & """ B = """ & rs.fields("ID_MARCA") & """>")
			response.write("<C><![CDATA["&rs.fields("SKU")&"]]></C>")
			response.write("<D><![CDATA["&rs.fields("DESC_MARCA")&"]]></D>")
			response.write("<E><![CDATA["&rs.fields("DESCRIPCION")&"]]></E>")
			response.write("<F><![CDATA["&rs.fields("DESC_MODELO")&"]]></F>")
			response.write("</o>")
		rs.MoveNext
		loop
	
	end if	
	
	response.write("</d>")			 		 
					   
rs.close
set rs = nothing
con.endConnection	

%>