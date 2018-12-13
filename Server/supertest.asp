<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

set con = new cConnection
con.startConnection(DB_INI)			
''BD_SOLICITUDES_ALMACEN"
''C_DESCRIPCION_TRABAJO
sql = 	"SELECT top(1) * from BD_BITACORA_VALIDACION_PREFACTURACION"																										
set rs = con.executeQuery(sql)
for each x in rs.fields
'  response.write(x.name)
'  response.write(" = ")
'  response.write(x.value) 
'  response.write("	")
	response.write(x.name & " ")
	response.write(" ")
next

'if not rs.EOF then
'	Do until rs.EOF
'		response.write("<a A=""" & rs & """>")
'		response.write("</a>")
'		rs.MoveNext
'	Loop
'end if
							
con.endConnection
response.write("</d>")
'con.endConnection
%>