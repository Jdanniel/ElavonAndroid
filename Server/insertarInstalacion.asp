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
	
if request.QueryString("e") <> "" then
	edit = request.QueryString("e")
else
	edit = -1
end if	
	
if request.QueryString("in") <> "" then
	idNegocio = request.QueryString("in")
else	
	idNegocio = -1
end if	
	
if request.QueryString("it") <> "" then
	idTecnico = request.QueryString("it")
else	
	idTecnico = -1
end if	
	
if request.QueryString("iu") <> "" then
	idUnidad = request.QueryString("iu")
else	
	idUnidad = -1
end if	
	
if request.QueryString("a") <> "" then
	action = request.QueryString("a")
else	
	action = ""
end if	
	
if request.QueryString("noe") <> "" then
	noEquipo = request.QueryString("noe")
else	
	noEquipo = ""
end if

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 and idTecnico <> -1 and idUnidad <> -1 and edit <> -1 and idNegocio <> -1 and action <> "" and noEquipo <> "" then
	sql = "SELECT BD_AR.ID_TECNICO"
	sql=sql+ " ,C_CLIENTES.IS_SINGLE_MOV_INV"
	sql=sql+ " FROM BD_AR"
	sql=sql+ " INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
	sql=sql+ " WHERE ID_AR = " & idAR
	set rs = con.executeQuery(sql)

	if not rs.eof then
		isSingleMovInv = rs.fields("IS_SINGLE_MOV_INV")
	end if
	rs.close
	set rs = nothing
	
	'' Verificar Instalaciones Previas
	Dim allowInstall
	
	sql = "SELECT ISNULL(COUNT(ID_INSTALACION),0) AS NUM_INSTALACIONES"
	sql = sql + " FROM BD_INSTALACIONES"
	sql = sql + " WHERE BD_INSTALACIONES.ID_AR = " & idAR
	set rs = con.executeQuery(sql)
		
	if not rs.eof then
		numInstalaciones = rs.fields("NUM_INSTALACIONES")
	end if
		
	rs.close
	set rs = nothing
	
	if edit = "1" then
		if isSingleMovInv = "1" then
			if numInstalaciones = "0" then
				allowInstall = "1"		
			else
				allowInstall = "0"		
			end if
		else
			allowInstall = "1"	
		end if
	else
		allowInstall = "0"
	end if
	
	if allowInstall = "1" or action <> "INSTALL" then
		sql = 	"EXEC SP_CONFIRM_INSTALACION " & idAR & "," & idNegocio & "," & idTecnico & "," & idUnidad & ",'" & action & "','" & noEquipo & "'," & idTecnico						
		con.executeQuery(sql)
		
		response.write("<r res=""OK"" desc=""Todo pro"" />")
	else
		response.write("<r res=""ERROR"" desc=""Error de Parametros"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error al jalar los datos por get"" />")
end if

response.write("</d>")
con.endConnection
%>