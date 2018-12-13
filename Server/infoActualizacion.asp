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

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 then
	sql = ""
	sql = sql & "SELECT "
	sql = sql & "	 ID_TECNICO "
	sql = sql & "	,ID_NEGOCIO "
	sql = sql & "	,BD_AR.ID_CLIENTE "
	sql = sql & "	,ID_STATUS_AR "
	sql = sql & "	,IS_BOM "
	sql = sql & "	,ISNULL(ID_UNIDAD_ATENDIDA, -1) AS ID_UNIDAD_ATENDIDA "
	sql = sql & "	,IS_UNIDAD_ATENDIDA_REQUIRED "
	sql = sql & "FROM BD_AR "
	sql = sql & "INNER JOIN C_PRODUCTOS ON BD_AR.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO "
	sql = sql & "INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
	sql = sql & "WHERE ID_AR = " & idAR & "; "

	set rs = con.executeQuery(sql)

	if not rs.eof then
		idNegocio = rs.fields("ID_NEGOCIO")
		idTecnico = rs.fields("ID_TECNICO")
		idCliente = rs.fields("ID_CLIENTE")
		idStatusAR = rs.fields("ID_STATUS_AR")
		isBom = rs.fields("IS_BOM")
		idUnidadAtendida = rs.fields("ID_UNIDAD_ATENDIDA")
		isUnidadAtendidaRequired = rs.fields("IS_UNIDAD_ATENDIDA_REQUIRED")
		
		rs.close
		set rs = nothing
		
		sql = "SELECT NOMBRE + ' ' + PATERNO + ' ' + MATERNO AS DESC_TECNICO FROM C_USUARIOS WHERE ID_USUARIO = " & idTecnico
		set rs = con.executeQuery(sql)
		descTecnico = rs.fields("DESC_TECNICO")
		
		rs.close
		set rs = nothing
		
		sql = "SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = " & idNegocio
		set rs = con.executeQuery(sql)
		descNegocio = rs.fields("DESC_NEGOCIO")
		
		''A = ID_NEGOCIO
		''B = ID_CLIENTE
		''C = ID_STATUS_AR
		''D = IS_BOM
		''E = ID_UNIDAD_ATENDIDA
		''F = IS_UNIDAD_ATENDIDA_REQUIRED
		''G = DESC_TECNICO
		''H = DESC_NEGOCIO
		
		response.write("<r res=""OK"" desc=""Todo bien"" >")
		response.write("<data A=""" & idNegocio & """ B=""" & idCliente & """ C=""" & idStatusAR & """ ")
		response.write("D=""" & isBom & """ E=""" & idUnidadAtendida & """ F=""" & isUnidadAtendidaRequired & """ ")
		response.write("G=""" & descTecnico & """ H=""" & descNegocio & """ />")
		response.write("</r>")
	else
		response.write("<r res=""ERROR"" desc=""No saco los valores"" />")
	end if	
else
	response.write("<r res=""ERROR"" desc=""No se recibieron bien los parametros del url"" />")
end if

response.write("</d>")
con.endConnection
%>