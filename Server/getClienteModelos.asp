<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_CLIENTE_MODELO, ID_MODELO, ID_CLIENTE"
sql=sql+" FROM BD_CLIENTE_MODELO"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A = ID_CLIENTE_MODELO
''B = ID_CLIENTE
''C = ID_MODELO

if not rs.EOF then
	Do until rs.EOF
		response.write("<cm A = """ & rs.fields("ID_CLIENTE_MODELO") & """ B = """ & rs.fields("ID_CLIENTE") & _
					   """ C = """ & rs.fields("ID_MODELO") & """ />")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>