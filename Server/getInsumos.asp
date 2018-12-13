<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_INSUMO, DESC_INSUMO, ID_CLIENTE, ID_TIPO_INSUMO"
sql=sql+" FROM C_INSUMOS"
sql=sql+" WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_INSUMO
'' B = ID_CLIENTE
'' C = ID_TIPO_INSUMO
'' D = DESC_INSUMO

if not rs.EOF then
	Do until rs.EOF
		response.write("<i A = """ & rs.fields("ID_INSUMO") & """ B=""" & rs.fields("ID_CLIENTE") & """ C=""" & rs.fields("ID_TIPO_INSUMO") & """ >")
		response.write("<![CDATA[" & rs.fields("DESC_INSUMO") & "]]>")
		response.write("</i>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>