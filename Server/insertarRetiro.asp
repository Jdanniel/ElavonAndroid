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
	
if request.QueryString("isd") <> "" then
	isDaniada = request.QueryString("isd")
else	
	isDaniada = -1
end if

if isDaniada = -2 then
	isDaniada = 0
end if

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 and idTecnico <> -1 and idUnidad <> -1 and edit <> -1 and idNegocio <> -1 and action <> "" and isDaniada <> -1 then
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
	Dim allowRetiro
	
	sql = "SELECT ISNULL(COUNT(ID_RETIRO),0) AS NUM_RETIROS"
	sql = sql + " FROM BD_RETIROS"
	sql = sql + " WHERE BD_RETIROS.ID_AR = " & idAR
	set rs = con.executeQuery(sql)
		
	if not rs.eof then
		numInstalaciones = rs.fields("NUM_RETIROS")
	end if
		
	rs.close
	set rs = nothing
	
	if edit = "1" then
		if isSingleMovInv = "1" then
			if numRetiros = "0" then
				allowRetiro = "1"		
			else
				allowRetiro = "0"		
			end if
		else
			allowRetiro = "1"	
		end if
	else
		allowRetiro = "0"
	end if
	
	if allowRetiro = "1" or action <> "CANCEL_RETIRO" then
		sql = 	"EXEC SP_CONFIRM_RETIRO " & idAR & "," & idNegocio & "," & idTecnico & "," & idUnidad & ",'" & action & "'," & idTecnico & "," & isDaniada
		con.executeQuery(sql)	
		
		response.write("<r res=""OK"" desc=""Todo pro"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error al jalar los datos por get"" />")
end if

response.write("</d>")
con.endConnection
%>