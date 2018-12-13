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
sql=sql+" BD_ENVIOS.ID_ENVIO, "
sql=sql+" count(BD_UNIDADES.ID_STATUS_UNIDAD) as num "
sql=sql+" FROM BD_ENVIOS "
sql=sql+" INNER JOIN BD_ENVIO_UNIDAD ON BD_ENVIOS.ID_ENVIO = BD_ENVIO_UNIDAD.ID_ENVIO "
sql=sql+" INNER JOIN BD_UNIDADES ON BD_ENVIO_UNIDAD.ID_UNIDAD = BD_UNIDADES.ID_UNIDAD "
sql=sql+" WHERE BD_ENVIOS.ID_STATUS_ENVIO=3 AND BD_ENVIOS.ID_TIPO_RESPONSABLE_ORIGEN=2 AND BD_ENVIOS.ID_RESPONSABLE_ORIGEN="&id
sql=sql+" AND ID_STATUS_UNIDAD=16 "
sql=sql+" GROUP BY BD_ENVIOS.ID_ENVIO,BD_ENVIOS.ID_TIPO_RESPONSABLE_ORIGEN,BD_ENVIOS.ID_RESPONSABLE_ORIGEN,BD_ENVIOS.ID_SERVICIO_MENSAJERIA,BD_ENVIOS.NO_GUIA,BD_UNIDADES.ID_STATUS_UNIDAD "


set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""utf-8""?>")
response.write("<d>")

''A = ID_ENVIO
''B = NUM
if not rs.eof then

	Do until rs.EOF
		response.write("<e A="""&rs.fields("ID_ENVIO")&"""  B="""&rs.fields("num")&""" />")

	rs.MoveNext
	loop

end if
response.write("</d>")



rs.close
set rs = nothing
con.endConnection

%>
