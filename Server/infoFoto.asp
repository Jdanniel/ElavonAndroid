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
	
if request.QueryString("sfn") <> "" then
	systemFileName = request.QueryString("sfn")
else
	systemFileName = ""
end if

Dim idTipoArchivo
idTipoArchivo = 6

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

sql= "SELECT"
sql=sql+" ID_TECNICO, ID_STATUS_AR"
sql=sql+" FROM dbo.BD_AR"
sql=sql+" WHERE ID_AR="&idAR&" AND ID_TECNICO = "&idTecnico

set rsValidacion = con.executeQuery(sql)

Dim miBandera
miBandera = "0"

if not rsValidacion.EOF then
	Do until rsValidacion.EOF
		if rsValidacion.fields("ID_STATUS_AR")<>"8" and rsValidacion.fields("ID_STATUS_AR")<>"2" then
			miBandera ="1"
		end if
		rsValidacion.MoveNext
	Loop
end if
rsValidacion.close
set rsValidacion = Nothing

if idAR <> -1 and idTecnico <> -1 and idTipoArchivo <> -1 and systemFileName <> "" and miBandera = "1" then
	sql = "INSERT INTO BD_ATTACH (ID_TIPO_ARCHIVO, DESC_ATTACH, SYSTEM_FILENAME, USER_FILENAME, FEC_ALTA, ID_USUARIO_ALTA) VALUES ("
	sql = sql + "" & idTipoArchivo & ", '" & idAR & "', '" & systemFileName & "', '" & idAR & "', GETDATE(), " & idTecnico & ")"
	con.executeQuery(sql)
	
	sql = "SELECT ID_ATTACH FROM BD_ATTACH WHERE ID_USUARIO_ALTA = " & idTecnico & " AND SYSTEM_FILENAME = '" & systemFileName & "' AND DESC_ATTACH = '" & idAR & "'"
	set rs = con.executeQuery(sql)
	
	if not rs.EOF then
		sql = "INSERT INTO BD_FOTO_AR (ID_ATTACH, ID_AR) VALUES(" & rs.fields("ID_ATTACH") & ", " & idAR & ")"
		con.executeQuery(sql)
		
		response.write("<r res=""OK"" desc=""Todo bien"" />")
	else
		response.write("<r res=""ERROR"" desc=""Error en el query para sacar el ID_ATTACH"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error al jalar los datos por get"" />")
end if

response.write("</d>")
con.endConnection
%>