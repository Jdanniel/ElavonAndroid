<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_USUARIO , NOMBRE, PATERNO, MATERNO"
sql=sql+" FROM C_USUARIOS WHERE ID_TIPO_USUARIO = 8 AND STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A=ID_USUARIO
'' B=NOMBRE_COMPLETO

if not rs.EOF then

	Do until rs.EOF
		completo = rs.fields("NOMBRE")&" "&rs.fields("PATERNO")&" "&rs.fields("MATERNO")
		response.write("<ic A=""" & rs.fields("ID_USUARIO") & """>")
		response.write("<![CDATA["&completo&"]]>")
		response.write("</ic>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>