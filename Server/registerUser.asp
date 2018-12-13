<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
''Server.ScriptTimeout=3600
response.Charset="iso-8859-1"
response.ContentType="text/xml"

Dim usuario, password, idusuario, passbd, ispda, status, c1, c2, c3

usuario  = request.QueryString("u")
password = request.QueryString("p")

response.write("<?xml version=""1.0"" encoding=""utf-8""?>")

set con = new cConnection
con.startConnection(DB_INI)

sql = "SELECT ID_USUARIO, IS_PDA, STATUS FROM C_USUARIOS WHERE C_USUARIOS.USERNAME='"&usuario&"'"
set rs = con.executeQuery(sql)

if not rs.EOF then
	idusuario = rs.fields(0)
	ispda = rs.fields(1)
	status = rs.fields(2)
	
	sql="SP_GET_PASSWORD @id_usuario= "&idusuario
	set rs = con.executeQuery(sql)

	passbd = rs.fields(0)
	
	if password = passbd then
		c1 = true
	else 
		c1 = false
	end if
	if ispda = 1 then
		c2 = true
	end if
	if status = "ACTIVO" then
		c3 = true
	end if
	response.write("<d>")
	''response.write("<data password=""" & password & """ passbd=""" & passbd & """ ispda=""" & ispda & """ status=""" & status & """ />")
	If password = passbd and ispda = 1 and status = "ACTIVO" Then
		response.write("<r res=""OK"" id="""&idusuario&""" />")
		response.write("<data c1="""&c1&""" c2="""&c2&""" c3="""&c3&"""/>")
	Else
		If password <> passbd Then
			response.write("<r res=""ERROR"" desc=""Error de autenticacion: Password incorrecto"" c1="""&c1&""" c2="""&password&""" c3="""&passbd&"""/>")
			response.write("<data pass="""&c1&""" ispda="""&c2&""" status="""&c3&"""/>")
		Else
			If ispda <> 1 Then
				response.write("<r res=""ERROR"" desc=""Error de autenticacion: El usuario no es de PDA"" c1="""&c1&""" c2="""&password&""" c3="""&passbd&"""/>")
				response.write("<data pass="""&c1&""" ispda="""&c2&""" status="""&c3&"""/>")
			Else
				response.write("<r res=""ERROR"" desc=""Error de autenticacion: El usuario ya no es activo"" c1="""&c1&""" c2="""&password&""" c3="""&passbd&"""/>")
				response.write("<data pass="""&c1&""" ispda="""&c2&""" status="""&c3&"""/>")
			End If
		End If
	End If
	response.write("</d>")
else
	response.write("<r res=""ERROR"" desc=""Error en la base de datos"" />")
end if

rs.close
set rs = nothing
con.endConnection
%>