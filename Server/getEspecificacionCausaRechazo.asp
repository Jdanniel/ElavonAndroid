<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_ESPECIFICACION_CAUSA_RECHAZO,ID_CAUSA_RECHAZO_PARENT,DESC_ESPECIFICACION_CAUSA_RECHAZO"
sql=sql+" FROM BD_ESPECIFICACION_CAUSA_RECHAZO"
sql=sql+" WHERE STATUS='ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_ESPECIFICACION_CAUSA_RECHAZO
'' B = ID_CAUSA_RECHAZO_PARENT
'' C = DESC_ESPECIFICACION_CAUSA_RECHAZO

if not rs.EOF then
	Do until rs.EOF
		response.write("<ecr A=""" & rs.fields("ID_ESPECIFICACION_CAUSA_RECHAZO") & """ B="""&rs.fields("ID_CAUSA_RECHAZO_PARENT")&""">")
		response.write("<C><![CDATA["&rs.fields("DESC_ESPECIFICACION_CAUSA_RECHAZO")&"]]></C>")
		response.write("</ecr>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>
