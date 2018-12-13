<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT "
sql=sql+"ID_GRUPO_CLIENTE, ID_GRUPO, ID_CLIENTE "
sql=sql+"FROM BD_GRUPOS_CLIENTES"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_GRUPO_CLIENTE
'' B = ID_GRUPO
'' C = ID_CLIENTE

if not rs.EOF then
	Do until rs.EOF
		response.write("<gc A = """ & rs.fields("ID_GRUPO_CLIENTE") & """ B = """ & rs.fields("ID_GRUPO") & """ ")
		response.write("C = """ & rs.fields("ID_CLIENTE") & """ />")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>