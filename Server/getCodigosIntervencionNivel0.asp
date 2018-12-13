<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT "
sql=sql+"ID_CODIGO_NIVEL0, CLAVE_CODIGO, DESC_CODIGO, ID_CLIENTE, REPORTA_INSTALACION "
sql=sql+"FROM C_CODIGOS_INTERVENCION_NIVEL0 "
sql=sql+"WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A = ID_CODIGO_NIVEL0
''B = CLAVE_CODIGO
''C = ID_CLIENTE
''D = REPORTA_INSTALACION
''E = DESC_CODIGO

if not rs.EOF then
	Do until rs.EOF
		response.write("<ci0 A = """ & rs.fields("ID_CODIGO_NIVEL0") & """ B = """ &rs.fields("CLAVE_CODIGO") & """ C="""&rs.fields("ID_CLIENTE")&""" D="""&rs.fields("REPORTA_INSTALACION")&""" >")
		response.write("<E><![CDATA[" & rs.fields("DESC_CODIGO") & "]]></E>")
		response.write("</ci0>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>