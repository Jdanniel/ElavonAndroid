<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_ALMACEN, DESC_ALMACEN"
sql=sql+" FROM C_ALMACENES"
sql=sql+" WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A = ID_ALMACEN
''B = DESC_ALMACEN

if not rs.EOF then
	Do until rs.EOF
		response.write("<a A=""" & rs.fields("ID_ALMACEN") & """>")
		response.write("<![CDATA["&rs.fields("DESC_ALMACEN")&"]]>")
		response.write("</a>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>
