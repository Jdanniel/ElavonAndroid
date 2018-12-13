<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

Dim id
if request.QueryString("i") <> "" then
	id = request.QueryString("i")
else
	id = 0
end if


set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" BD_DIRECCIONES.ID_DIRECCION, "
sql=sql+" BD_DIRECCIONES.DIRECCION, "
sql=sql+" BD_DIRECCIONES.COLONIA, "
sql=sql+" BD_DIRECCIONES.POBLACION, "
sql=sql+" BD_DIRECCIONES.ESTADO "
sql=sql+" FROM BD_DIRECCIONES "
sql=sql+" INNER JOIN BD_DIRECCIONES_RELACIONES ON BD_DIRECCIONES.ID_DIRECCION = BD_DIRECCIONES_RELACIONES.ID_DIRECCION "
sql=sql+" WHERE BD_DIRECCIONES_RELACIONES.ID_RESPONSABLE = "&id& " AND BD_DIRECCIONES_RELACIONES.ID_TIPO_RESPONSABLE=2 "

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_DIRECCION
'' B = DIRECCION
'' C = COLONIA
'' D = POBLACION
'' E = ESTADO


if not rs.EOF then
	Do until rs.EOF
		response.write("<dir A = """ & rs.fields("ID_DIRECCION") & """  >")
		response.write("<B><![CDATA[" & rs.fields("DIRECCION") & "]]></B>")
		response.write("<C><![CDATA[" & rs.fields("COLONIA") & "]]></C>")
		response.write("<D><![CDATA[" & rs.fields("POBLACION") & "]]></D>")
		response.write("<E><![CDATA[" & rs.fields("ESTADO") & "]]></E>")
		response.write("</dir>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection
%>