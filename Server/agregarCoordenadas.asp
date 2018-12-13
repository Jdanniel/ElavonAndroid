<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

if request.QueryString("in") <> "" then
	idNegocio = request.QueryString("in")
else	
	idNegocio = -1
end if	
	
if request.QueryString("lat") <> "" then
	latitud = request.QueryString("lat")
else
	latitud = ""
end if	
	
if request.QueryString("lon") <> "" then
	longitud = request.QueryString("lon")
else	
	longitud = ""
end if

if request.QueryString("acc") <> "" then
	accuracy = request.QueryString("acc")
else	
	accuracy = ""
end if	
	
response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idNegocio <> -1 and latitud <> "" and longitud <> "" and accuracy <> "" then
	sql = "UPDATE BD_NEGOCIOS SET "
	sql = sql + "LATITUD = " & latitud & ", "
	sql = sql + "LONGITUD = " & longitud & ", "
	sql = sql + "GRADO_ERROR = " & accuracy & " "
	sql = sql + "WHERE ID_NEGOCIO = " & idNegocio & ""
	con.executeQuery(sql)

	response.write("<r res=""OK"" desc=""Todo bien"" />")
else
	response.write("<r res=""ERROR"" desc=""Error al jalar los datos por get"" />")
end if

response.write("</d>")
con.endConnection
%>