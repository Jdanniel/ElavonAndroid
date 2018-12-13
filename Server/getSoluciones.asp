<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT "
sql=sql+"ID_SOLUCION, ID_CLIENTE, CLAVE, IS_EXITO, DESC_SOLUCION "
sql=sql+"FROM C_SOLUCIONES "
sql=sql+"WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_SOLUCION
'' B = ID_CLIENTE
'' C = CLAVE
'' D = IS_EXITO
'' E = DESC_SOLUCION

if not rs.EOF then
	Do until rs.EOF
		response.write("<sol A = """ & rs.fields("ID_SOLUCION") & """ B = """ & rs.fields("ID_CLIENTE") & """ ")
		response.write("C = """ & rs.fields("CLAVE") & """ D = """ & rs.fields("IS_EXITO") & """ >")
		response.write("<E><![CDATA[" & rs.fields("DESC_SOLUCION") & "]]></E>")
		response.write("</sol>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>