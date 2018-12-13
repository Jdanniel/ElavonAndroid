<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

if request.QueryString("ia") <> "" then
	idAR = request.QueryString("ia")
else	
	idAR = -1
end if

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 then
	sql="SELECT "
	sql=sql+" BD_AR.NO_AR "
	sql=sql+",dbo.FUNC_GET_STRING_CIERRE(BD_AR.ID_AR) AS CIERRE "
	sql=sql+" FROM BD_AR "
	sql=sql+" INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
	sql=sql+" WHERE BD_AR.ID_AR = " & idAR & " "
	sql=sql+" ORDER BY BD_AR.ID_AR; "
	set rs = con.executeQuery(sql)
	
	if not rs.EOF then
		response.write("<r res=""OK"" desc=""Todo bien"" val=""" & rs.fields("CIERRE") & """ />")
	else
		response.write("<r res=""ERROR"" desc=""Error en el query para sacar la cadena"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""El Query String"" />")
end if

response.write("</d>")
con.endConnection
%>