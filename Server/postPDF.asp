<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%


''Server.ScriptTimeout=3600
response.Charset="iso-8859-1"
response.ContentType="text/xml"



Dim idAR,idUser, fileName, status_ini, status_fin 

if request.QueryString("ia") <> "" then
	idAR = request.QueryString("ia")
else	
	idAR = -1
end if	

if request.QueryString("iu") <> "" then
	idUser = request.QueryString("iu")
else	
	idUser = -1
end if	

if request.QueryString("f") <> "" then
	fileName = request.QueryString("f")
else	
	fileName = ""
end if

status_fin = 2

set con = new cConnection
con.startConnection(DB_INI)

sql = 	"SELECT BD_AR.ID_STATUS_VALIDACION_PREFACTURACION FROM BD_AR WHERE BD_AR.ID_AR = " & idAR
set rs = con.executeQuery(sql)
if not rs.EOF then
	status_ini = rs.fields("ID_STATUS_VALIDACION_PREFACTURACION")
else
	status_ini = -1
end if

con.endConnection


response.write("<?xml version=""1.0"" encoding=""utf-8""?>")
response.write("<d>")

if idAR <> -1 and idUser <> -1 and fileName <> "" and status_ini <> -1 then 

	set con = new cConnection
	con.startConnection(DB_INI)

	sql = 	"UPDATE BD_AR SET"
	sql = sql + "  BD_AR.ID_STATUS_VALIDACION_PREFACTURACION = 2"
	sql = sql + " WHERE BD_AR.ID_AR = " & idAR
	con.executeQuery(sql)
	con.endConnection	
	
	set con = new cConnection
	con.startConnection(DB_INI)

	sql = 	"INSERT INTO BD_BITACORA_VALIDACION_PREFACTURACION"
	sql = sql + " (ID_AR ,ID_STATUS_INI, ID_STATUS_FIN, ID_USUARIO_ALTA, FEC_ALTA, DOCUMENTO) VALUES ("
	sql = sql + "" & idAr & ", " & status_ini & ", " & status_fin & ", " & idUser & ", GETDATE(), '" & fileName & "')"
	con.executeQuery(sql)
	con.endConnection	
	
	response.write("<r res=""OK"" desc=""Todo bien"" />")
	
else
	response.write("<r res=""ERROR"" desc=""Error al jalar los datos por get"" />")
	
end if	

response.write("</d>")


%>
