<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_DESCRIPCION_TRABAJO,  "
sql=sql+" ID_ESPECIF_TIPO_FALLA ,  "
sql=sql+" DESC_TRABAJO ,  "
sql=sql+" STATUS  "
sql=sql+" FROM C_DESCRIPCION_TRABAJO WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A = ID_ALMACEN
''B = DESC_ALMACEN

if not rs.EOF then
	Do until rs.EOF
		response.write("<tra A= """ & rs.fields("ID_DESCRIPCION_TRABAJO") & """")
		response.write(" B= """ & rs.fields("ID_ESPECIF_TIPO_FALLA") & """")
		response.write(" C= """ & rs.fields("STATUS") & """>")
		response.write("<![CDATA["&rs.fields("DESC_TRABAJO")&"]]>")
		response.write("</tra>")
		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection


%>