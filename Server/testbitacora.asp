<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%


''Server.ScriptTimeout=3600
response.Charset="iso-8859-1"
response.ContentType="text/xml"

response.write("<?xml version=""1.0"" encoding=""utf-8""?>")
response.write("<d>")

set con = new cConnection
con.startConnection(DB_INI)

sql = 	"SELECT * FROM BD_BITACORA_VALIDACION_PREFACTURACION"
set rs = con.executeQuery(sql)

if not rs.EOF then
	Do until rs.EOF
		response.write("<r x=""" & rs.fields("ID_AR") & " , " & rs.fields("ID_STATUS_INI") & " , " & rs.fields("ID_STATUS_FIN") & " , " & rs.fields("ID_USUARIO_ALTA") & " , " & rs.fields("FEC_ALTA") & " , " & rs.fields("DOCUMENTO") & """/>")
		rs.MoveNext
	Loop
end if

con.endConnection

response.write("</d>")


%>
