<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT "
sql=sql+"ID_CAUSA, ID_CLIENTE, DESC_CAUSA, CLAVE "
sql=sql+"FROM C_CAUSAS "
sql=sql+"WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_CAUSA
'' B = ID_CLIENTE
'' C = CLAVE
'' D = DESC_CAUSA

if not rs.EOF then
	Do until rs.EOF
		response.write("<ca A = """ & rs.fields("ID_CAUSA") & """ B = """ & rs.fields("ID_CLIENTE") & """ ")
		response.write("C = """ & rs.fields("CLAVE") & """ >")
		response.write("<D><![CDATA[" & rs.fields("DESC_CAUSA") & "]]></D>")
		response.write("</ca>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>