<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_MODELO, DESC_MODELO, ID_MARCA, IS_GPRS, SKU"
sql=sql+" FROM C_MODELOS"
sql=sql+" WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID_MODELO
'' B = ID_MARCA
'' C = IS_GPRS
'' D = DESC_MODELO
'' E = SKU

if not rs.EOF then
	Do until rs.EOF
		response.write("<mod A = """ & rs.fields("ID_MODELO") & """ B = """ & rs.fields("ID_MARCA") & """ C=""" & rs.fields("IS_GPRS") & """ >")
		response.write("<D><![CDATA[" & rs.fields("DESC_MODELO") & "]]></D>")
		response.write("<E><![CDATA[" & rs.fields("SKU") & "]]></E>")
		response.write("</mod>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>
