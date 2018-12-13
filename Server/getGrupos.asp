<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT "
sql=sql+"ID_GRUPO, DESC_GRUPO "
sql=sql+"FROM C_GRUPOS "
sql=sql+"WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

'' A = ID_GRUPO
'' B = DESC_GRUPO

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if not rs.EOF then
	Do until rs.EOF
		response.write("<gr A = """ & rs.fields("ID_GRUPO") & """ >")
		response.write("<B><![CDATA[" & rs.fields("DESC_GRUPO") & "]]></B>")
		response.write("</gr>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>