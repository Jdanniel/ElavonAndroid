<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_ESPECIF_TIPO_FALLA,ID_FALLA_PARENT,DESC_ESPECIFICACION_FALLA"
sql=sql+" FROM BD_ESPECIFICACION_TIPO_FALLA"
sql=sql+" WHERE STATUS='ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A = ID_ESPECIF_TIPO_FALLA
''B = ID_FALLA_PARENT
''C = DESC_ESPECIFICACION_FALLA

if not rs.EOF then
	Do until rs.EOF
		response.write("<etf A=""" & rs.fields("ID_ESPECIF_TIPO_FALLA") & """ B="""&rs.fields("ID_FALLA_PARENT")&""">")
		response.write("<C><![CDATA["&rs.fields("DESC_ESPECIFICACION_FALLA")&"]]></C>")
		response.write("</etf>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>