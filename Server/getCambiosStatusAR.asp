<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

Dim id
if request.QueryString("i") <> "" then
	id = request.QueryString("i")
else
	id = 0
end if

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_STATUS_AR_INI, ID_STATUS_AR_FIN "
sql=sql+" FROM BD_CAMBIO_STATUS_AR"
sql=sql+" WHERE STATUS = 'ACTIVO' AND ID_CLIENTE = " & id

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A=ID_STATUS_AR_INI
''B=ID_STATUS_AR_FIN

if not rs.EOF then
	Do until rs.EOF
		response.write("<e A=""" & rs.fields("ID_STATUS_AR_INI") &_
					   """ B=""" & rs.fields("ID_STATUS_AR_FIN")&_
					   """/>")
		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>