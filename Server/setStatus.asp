<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->



<%


''Server.ScriptTimeout=5600
response.Charset="iso-8859-1"
response.ContentType="text/xml"

Dim id
if request.QueryString("i") <> "" then
	id = request.QueryString("i")
else
	id = 0
end if


Dim status
if request.QueryString("s") <> "" then
	status = cInt(request.QueryString("s"))
else
	status = 0
end if


Dim usuario
if request.QueryString("u") <> "" then
	usuario = cInt(request.QueryString("u"))
else
	usuario = 0
end if




set con = new cConnection
con.startConnection(DB_INI)

if status>100 Then
	sql="SP_SQL_SEND_MAIL @From='soporte@einteligent.com',@To='nestgomez@gmail.com',@Subject='Error Status',@BodyType='TextBody',@Body=' i= " & id & " s = "& status &" u= "&usuario&" ',@attachment=NULL"
	con.executeUpdate(sql)
else
	sql= "SELECT"
	sql=sql+" ID_TECNICO, ID_STATUS_AR"
	sql=sql+" FROM dbo.BD_AR"
	sql=sql+" WHERE ID_AR="&id&" AND ID_TECNICO = "&usuario

	set rsValidacion = con.executeQuery(sql)

	if not rsValidacion.EOF then
		Do until rsValidacion.EOF
			if rsValidacion.fields("ID_STATUS_AR")<>"8" and rsValidacion.fields("ID_STATUS_AR")<>"2" then
				response.write( "<d r=""OK""/>" )
				sql="SP_UPDATE_STATUS_AR @ID_AR="&id&", @ID_STATUS_AR="&status&", @ID_USUARIO="&usuario
				con.executeUpdate(sql)
			end if
			rsValidacion.MoveNext
		Loop
	else
		response.write( "<d r=""ERROR""/>" )
	end if
	rsValidacion.close
	set rsValidacion = Nothing
	
End if

con.endConnection

%>