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
	
if request.QueryString("it") <> "" then
	idTecnico = request.QueryString("it")
else	
	idTecnico = -1
end if
	
if request.QueryString("in") <> "" then
	idInsumo = request.QueryString("in")
else
	idInsumo = -1
end if	
	
if request.QueryString("c") <> "" then
	cantidad = request.QueryString("c")
else	
	cantidad = -1
end if	

if request.QueryString("iai") <> "" then
	idARInsumo = request.QueryString("iai")
else	
	idARInsumo = -1
end if

if request.QueryString("a") <> "" then
	action = request.QueryString("a")
else
	action = -1
end if

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 and idTecnico <> -1 and idInsumo <> -1 and cantidad <> -1 and idARInsumo <> -1 and action <> -1 then
	if action = 1 then
		sql = 	"EXEC SP_INSERT_INSUMO " & idAR & "," & idInsumo & "," & cantidad & "," & idTecnico
		con.executeQuery(sql)
		
		response.write("<r res=""OK"" desc=""Todo bien"" />")
	elseif action = 2 then
		sql = 	"DELETE FROM BD_AR_INSUMO WHERE ID_AR_INSUMO = " & idARInsumo
		con.executeQuery(sql)
		
		response.write("<r res=""OK"" desc=""Todo bien"" />")
	else
		response.write("<r res=""ERROR"" desc=""Error en el tipo de acción"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error al jalar los datos por get"" />")
end if

response.write("</d>")
con.endConnection
%>