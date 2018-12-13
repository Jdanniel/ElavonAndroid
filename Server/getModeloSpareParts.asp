<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID, ID_MODELO, ID_SPARE_PART"
sql=sql+" FROM BD_MODELO_SPARE_PART"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'' A = ID
'' B = ID_MODELO
'' C = ID_SPARE_PART

if not rs.EOF then
	Do until rs.EOF
		response.write("<ms A = """ & rs.fields("ID") & """ B = """ & rs.fields("ID_MODELO") & _
					   """ C = """ & rs.fields("ID_SPARE_PART") & """ />")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>
