<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%


''Server.ScriptTimeout=3600
response.Charset="iso-8859-1"
response.ContentType="text/xml"



Dim idAR 
idAR = request.QueryString("id")

set con = new cConnection
con.startConnection(DB_INI)

sql="SELECT IS_VALIDA_TIR FROM C_CLIENTES INNER JOIN BD_AR ON C_CLIENTES.ID_CLIENTE = BD_AR.ID_CLIENTE WHERE BD_AR.ID_AR = " & idAR


set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""utf-8""?>")
response.write("<d>")


if not rs.eof then

	Do until rs.EOF

		response.write("<e IS_VALIDA_TIR="""&rs.fields("IS_VALIDA_TIR")&""" />")

	rs.MoveNext
	loop

end if
response.write("</d>")



rs.close
set rs = nothing
con.endConnection

%>
