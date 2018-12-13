<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" C_TIPO_FALLA.ID_TIPO_FALLA, DESC_TIPO_FALLA, ID_SERVICIO, BD_SERVICIO_TIPO_FALLA.ID_CLIENTE"
sql=sql+" FROM C_TIPO_FALLA"
sql=sql+" INNER JOIN BD_SERVICIO_TIPO_FALLA"
sql=sql+" ON C_TIPO_FALLA.ID_TIPO_FALLA=BD_SERVICIO_TIPO_FALLA.ID_TIPO_FALLA"
sql=sql+" WHERE STATUS='ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A = ID_TIPO_FALLA
''B = ID_SERVICIO
''C = DESC_TIPO_FALLA

if not rs.EOF then
	Do until rs.EOF
		response.write("<tf A=""" & rs.fields("ID_TIPO_FALLA") & """ B="""&rs.fields("ID_SERVICIO")&""" C="""&rs.fields("ID_CLIENTE")&""">")
		response.write("<C><![CDATA["&rs.fields("DESC_TIPO_FALLA")&"]]></C>")
		response.write("</tf>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>
