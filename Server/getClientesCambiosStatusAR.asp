<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" DISTINCT ID_CLIENTE"
sql=sql+" FROM BD_CAMBIO_STATUS_AR"
sql=sql+" WHERE STATUS = 'ACTIVO' ORDER BY ID_CLIENTE"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''e=ID_CLIENTE

if not rs.EOF then
	Do until rs.EOF
		response.write("<e>" & rs.fields("ID_CLIENTE") &_ 
					   "</e>")
		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>