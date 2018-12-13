<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

Dim id
if request.QueryString("i") <> "" then
	id = request.QueryString("i")
else
	id = 0
end if

sql= "SELECT "
sql=sql+"C_TIPO_USUARIO.DESC_TIPO_USUARIO, "
sql=sql+"C_USUARIOS.NOMBRE , C_USUARIOS.PATERNO , C_USUARIOS.MATERNO  ,"
sql=sql+"BD_COMENTARIO_AR.FEC_ALTA ,"
sql=sql+"BD_COMENTARIO_AR.DESC_COMENTARIO_AR "
sql=sql+"FROM BD_COMENTARIO_AR "
sql=sql+"INNER JOIN C_USUARIOS ON BD_COMENTARIO_AR.ID_USUARIO_ALTA = C_USUARIOS.ID_USUARIO "
sql=sql+"INNER JOIN C_TIPO_USUARIO ON C_USUARIOS.ID_TIPO_USUARIO  = C_TIPO_USUARIO.ID_TIPO_USUARIO "
sql=sql+"WHERE ID_AR  = " & id

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if not rs.EOF then
	Do until rs.EOF
		response.write("<c>")
		response.write("<A><![CDATA[" & rs.fields("DESC_TIPO_USUARIO") & "]]></A>")
		response.write("<B><![CDATA[" & rs.fields("NOMBRE") &" "&rs.fields("PATERNO")&" "&rs.fields("MATERNO") & "]]></B>")
		response.write("<C><![CDATA[" & rs.fields("FEC_ALTA") & "]]></C>")
		response.write("<D><![CDATA[" & rs.fields("DESC_COMENTARIO_AR") & "]]></D>")
		response.write("</c>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection
%>