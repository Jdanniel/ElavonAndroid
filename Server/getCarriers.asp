<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql=sql+"SELECT "
sql=sql+"	 ID_CARRIER "
sql=sql+"	,DESC_CARRIER "
sql=sql+" FROM C_CARRIER "
sql=sql+" WHERE STATUS = 'ACTIVO' "

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A = ID_CARRIER
''B = DESC_CARRIER

if not rs.EOF then
	Do until rs.EOF
		response.write("<c A=""" & rs.fields("ID_CARRIER") & """>")
		response.write("<![CDATA["&rs.fields("DESC_CARRIER")&"]]>")
		response.write("</c>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection
%>
