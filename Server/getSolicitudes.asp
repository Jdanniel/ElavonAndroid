<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%


''Server.ScriptTimeout=3600
response.Charset="iso-8859-1"
response.ContentType="text/xml"

Dim id, query
if request.QueryString("i") <> "" then
	id = request.QueryString("i")
else
	id = 0
end if




if request.QueryString("s") <> "" then
	if request.QueryString("s")="n" then
		query = "C_STATUS_AR.IS_BB_NUEVAS = 1"
	end if
	'' 13 16 17 18 19 20 4 5
	if request.QueryString("s")="a" then
		query = "C_STATUS_AR.IS_BB_ABIERTAS = 1"
	end if
	if request.QueryString("s")="c" then
		query = "C_STATUS_AR.IS_BB_CERRADAS = 1"
	end if
	'' 11 15 12 22 24 21 25 23 26
	if request.QueryString("s")="p" then
		query = "C_STATUS_AR.IS_BB_PENDIENTES = 1"
	end if
else
	status = 0
end if


set con = new cConnection
con.startConnection(DB_INI)

sql="SELECT"
sql=sql+" BD_AR.ID_AR, BD_AR.ID_STATUS_AR"
sql=sql+" FROM BD_AR"
sql=sql+" INNER JOIN C_STATUS_AR ON BD_AR.ID_STATUS_AR = C_STATUS_AR.ID_STATUS_AR"
sql=sql+" WHERE BD_AR.ID_TECNICO = " & id & " AND " & query 


set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""utf-8""?>")
response.write("<d>")


if not rs.eof then

	Do until rs.EOF

		response.write("<e ID_AR="""&rs.fields("ID_AR")&""" ID_STATUS_AR="""&rs.fields("ID_STATUS_AR")&""" />")

	rs.MoveNext
	loop

end if
response.write("</d>")



rs.close
set rs = nothing
con.endConnection

%>
