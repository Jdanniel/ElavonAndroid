<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%


''Server.ScriptTimeout=3600
response.Charset="iso-8859-1"
response.ContentType="text/xml"

Dim id, status
if request.QueryString("i") <> "" then
	id = request.QueryString("i")
else
	id = 0
end if



set con = new cConnection
con.startConnection(DB_INI)

sql="SELECT"
sql=sql+" BD_UNIDADES.ID_UNIDAD, "
sql=sql+" BD_UNIDADES.ID_STATUS_UNIDAD "
sql=sql+" FROM BD_UNIDADES "
sql=sql+" WHERE BD_UNIDADES.ID_TIPO_RESPONSABLE=2 AND BD_UNIDADES.ID_RESPONSABLE="&id&" AND ID_STATUS_UNIDAD=15 AND BD_UNIDADES.STATUS<>'BORRADO' "

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""utf-8""?>")
response.write("<d>")

'' A = ID_UNIDAD
'' B = ID_STATUS_UNIDAD

if not rs.eof then
	Do until rs.EOF
		response.write("<e A="""&rs.fields("ID_UNIDAD")&"""  B ="""&rs.fields("ID_STATUS_UNIDAD")&"""  />")
	rs.MoveNext
	loop
end if
response.write("</d>")

rs.close
set rs = nothing
con.endConnection
%>