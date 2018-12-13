<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" ID_PRODUCTO, ID_STATUS_AR"
sql=sql+" FROM BD_PRODUCTO_STATUS_AR"
sql=sql+" WHERE STATUS_BB='ACTIVO'"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

''A=ID_PRODUCTO
''B=ID_STATUS_AR

if not rs.EOF then
	Do until rs.EOF
		response.write("<e A = """  & rs.fields("ID_PRODUCTO")  &_ 
					   """ B = """ & rs.fields("ID_STATUS_AR") &_ 
					   """ />")
		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>
