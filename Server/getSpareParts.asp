<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_SPARE_PART, DESC_SPARE_PART"
sql=sql+" FROM C_SPARE_PARTS"
sql=sql+" WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_SPARE_PART
'' B = DESC_SPARE_PART

if not rs.EOF then
	Do until rs.EOF
		response.write("<s A = """ & rs.fields("ID_SPARE_PART") & """ >")
		response.write("<![CDATA[" & rs.fields("DESC_SPARE_PART") & "]]>")
		response.write("</s>")
		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>