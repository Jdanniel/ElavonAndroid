<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_MARCA, ID_PRODUCTO, DESCRIPCION"
sql=sql+" FROM C_MARCAS"
sql=sql+" WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_MARCA
'' B = ID_PRODUCTO
'' C = DESCRIPCION

if not rs.EOF then
	Do until rs.EOF
		response.write("<m A = """ & rs.fields("ID_MARCA") & """ B = """ & rs.fields("ID_PRODUCTO") & """ >")
		response.write("<![CDATA[" & rs.fields("DESCRIPCION") & "]]>")
		response.write("</m>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>