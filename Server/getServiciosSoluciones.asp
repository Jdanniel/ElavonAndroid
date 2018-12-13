<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT "
sql=sql+"ID_SERVICIO_SOLUCION, ID_SERVICIO, ID_SOLUCION "
sql=sql+"FROM BD_SERVICIO_SOLUCION"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A = ID_SERVICIO_SOLUCION
''B = ID_SERVICIO
''C = ID_SOLUCION

if not rs.EOF then
	Do until rs.EOF
		response.write("<sersol A = """ & rs.fields("ID_SERVICIO_SOLUCION") & """ B = """ & rs.fields("ID_SERVICIO") & """ ")
		response.write("C = """ & rs.fields("ID_SOLUCION") & """ />")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>