<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

if request.QueryString("ia") <> "" then
	idAR = request.QueryString("ia")
else
	idAR = -1
end if

if request.QueryString("in") <> "" then
	idNegocio = request.QueryString("in")
else
	idNegocio = -1
end if

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''''''''''	id producto	''''''''''
	sql = 	"SELECT " &_
			 "	 ID_PRODUCTO " &_
			 "FROM BD_AR " &_
			 " WHERE ID_AR = " & idAR & "; "

	set rs = con.executeQuery(sql)	
	if not rs.eof then
		idTipoProducto = rs.fields("ID_PRODUCTO")
	end if
	rs.close
	
	if idTipoProducto = 2 or idTipoProducto = 12 then
		idTipoProducto = 2
	else
		idTipoProducto = 1
	end if

if idAR <> -1 and idNegocio <> -1 then
	sql="SELECT"
	sql=sql+" BD_UNIDADES.ID_UNIDAD"
	sql=sql+",BD_UNIDADES.ID_MODELO"												
	sql=sql+",C_MODELOS.DESC_MODELO"
	sql=sql+",C_MARCAS.DESC_MARCA"
	sql=sql+",BD_UNIDADES.NO_SERIE"	
	sql=sql+",BD_UNIDADES.NO_INVENTARIO"
	sql=sql+",BD_UNIDADES.NO_IMEI"
	sql=sql+",BD_UNIDADES.POSICION_INVENTARIO"	
	sql=sql+",BD_UNIDADES.ID_USUARIO_ALTA"				
	sql=sql+",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"				
	sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"	
	sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"		
	sql=sql+",(SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_NEGOCIO"									
	sql=sql+",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"		
	sql=sql+",C_CLIENTES.DESC_CLIENTE"																											
	sql=sql+" FROM BD_UNIDADES"
	sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
	sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"	
	sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"					
	sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"
	sql=sql+" INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
	sql=sql+" WHERE BD_UNIDADES.ID_STATUS_UNIDAD IN (17)"
	sql=sql+" AND BD_UNIDADES.STATUS = 'ACTIVO'" 
	sql=sql+" AND BD_UNIDADES.ID_TIPO_RESPONSABLE = 4"
	sql=sql+" AND BD_UNIDADES.ID_RESPONSABLE = " & idNegocio
	sql=sql+" AND C_PRODUCTOS.ID_TIPO_PRODUCTO = " & idTipoProducto
	sql=sql+" ORDER BY BD_UNIDADES.ID_UNIDAD DESC"			
			
	set rs = con.executeQuery(sql)
	
	'' A = ID_UNIDAD
	'' B = ID_MODELO
	'' C = DESC_MODELO
	'' D = DESC_MARCA
	'' E = NO_SERIE
	'' F = NO_INVENTARIO
	'' G = NO_IMEI
	'' H = POSICION_INVENTARIO
	'' I = ID_USUARIO_ALTA
	'' J = FEC_ALTA
	'' K = DESC_STATUS_UNIDAD
	'' L = ID_STATUS_UNIDAD
	'' M = DESC_NEGOCIO
	'' N = DESC_CLIENTE
	
	if not rs.eof then
		response.write("<r res=""OK"" desc=""Todo bien"" />")
		
		Do until rs.EOF
			response.write("<un>")
			response.write("<ID_UNIDAD><![CDATA[" & rs.fields("ID_UNIDAD") & "]]></ID_UNIDAD>")
			response.write("<ID_MODELO><![CDATA[" & rs.fields("ID_MODELO") & "]]></ID_MODELO>")
			response.write("<DESC_MODELO><![CDATA[" & rs.fields("DESC_MODELO") & "]]></DESC_MODELO>")
			response.write("<DESC_MARCA><![CDATA[" & rs.fields("DESC_MARCA") & "]]></DESC_MARCA>")
			response.write("<NO_SERIE><![CDATA[" & rs.fields("NO_SERIE") & "]]></NO_SERIE>")
			response.write("<NO_INVENTARIO><![CDATA[" & rs.fields("NO_INVENTARIO") & "]]></NO_INVENTARIO>")
			response.write("<NO_IMEI><![CDATA[" & rs.fields("NO_IMEI") & "]]></NO_IMEI>")
			response.write("<POSICION_INVENTARIO><![CDATA[" & rs.fields("POSICION_INVENTARIO") & "]]></POSICION_INVENTARIO>")
			response.write("<ID_USUARIO_ALTA><![CDATA[" & rs.fields("ID_USUARIO_ALTA") & "]]></ID_USUARIO_ALTA>")
			response.write("<FEC_ALTA><![CDATA[" & rs.fields("FEC_ALTA") & "]]></FEC_ALTA>")
			response.write("<DESC_STATUS_UNIDAD><![CDATA[" & rs.fields("DESC_STATUS_UNIDAD") & "]]></DESC_STATUS_UNIDAD>")
			response.write("<ID_STATUS_UNIDAD><![CDATA[" & rs.fields("ID_STATUS_UNIDAD") & "]]></ID_STATUS_UNIDAD>")
			response.write("<DESC_NEGOCIO><![CDATA[" & rs.fields("DESC_NEGOCIO") & "]]></DESC_NEGOCIO>")
			response.write("<DESC_CLIENTE><![CDATA[" & rs.fields("DESC_CLIENTE") & "]]></DESC_CLIENTE>")
			response.write("</un>")
			rs.MoveNext
		loop
	else
		response.write("<r res=""OK"" desc=""No hubo unidades en el negocio"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""No se recibieron bien los parametros del url"" />")
end if

response.write("</d>")
con.endConnection
%>