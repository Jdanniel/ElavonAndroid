<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

if request.QueryString("iu") <> "" then
	idUnidad = request.QueryString("iu")
else
	idUnidad = ""
end if

if request.QueryString("in") <> "" then
	idNegocio = request.QueryString("in")
else
	idNegocio = -1
end if

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idUnidad <> -1 and idNegocio <> -1 then
	''''''''''''''''''''''''''''''''''''' Validación Sustitución
	sql = "SELECT ISNULL(COUNT(ID_SUSTITUCION),0) AS VALIDACION_SUSTITUCION" &_
		  " FROM BD_SUSTITUCIONES" &_ 
		  " WHERE ID_UNIDAD_ENTRADA = " & idUnidad &_
		  " OR ID_UNIDAD_SALIDA = " & idUnidad
	set rs = con.executeQuery(sql)
	
	if not rs.eof then	
		validacionSustitucion = rs.fields("VALIDACION_SUSTITUCION")
	else		
		validacionSustitucion = "0"
	end if				
	
	rs.close
	set rs = nothing
	
	if 	validacionSustitucion <> "0" then
		response.write("<r res=""OK"" desc=""Unidad usada en sustitución"" val=""2"" />")
	else
		''''''''''''''''''''''''''''''''''''' Eliminar Unidad	
		sql = "EXEC SP_BORRADO_FISICO_UNIDAD " & idUnidad
		set rs = con.executeQuery(sql)
	
		if not rs.eof then	
			response.write("<r res=""OK"" desc=""Todo bien"" val=""1"" />")
		else		
			response.write("<r res=""ERROR"" desc=""Error de validacion en el borrado"" />")
		end if
	end if
else
	response.write("<r res=""ERROR"" desc=""No jalo bien los valores del get"" />")
end if

response.write("</d>")
con.endConnection
%>