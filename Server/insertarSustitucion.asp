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
	
if request.QueryString("iue") <> "" then
	idUnidadEntrada = request.QueryString("iue")
else	
	idUnidadEntrada = -1
end if
	
if request.QueryString("ius") <> "" then
	idUnidadSalida = request.QueryString("ius")
else	
	idUnidadSalida = -1
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
	
if request.QueryString("noe") <> "" then
	noEquipo = request.QueryString("noe")
else
	noEquipo = ""
end if
				
response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 and idTecnico <> -1 and idUnidadEntrada <> -1 and idUnidadSalida <> -1 and edit <> -1 and idNegocio <> -1 and action <> "" and isDaniada <> -1 then
	sql = 	"SELECT ISNULL(COUNT(ID_SUSTITUCION),0) AS NO_SUSTITUCIONES FROM BD_SUSTITUCIONES WHERE ID_AR = " & idAR					
	set rs = con.executeQuery(sql)	
		
	if not rs.eof then
		noSustituciones	= rs.fields("NO_SUSTITUCIONES")
		
		rs.close
		set rs = nothing
		
		sql = "SELECT BD_AR.ID_TECNICO"
		sql=sql+ " ,C_CLIENTES.IS_SINGLE_MOV_INV" 	
		sql=sql+ " FROM BD_AR" 
		sql=sql+ " INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE" 	
		sql=sql+ " WHERE ID_AR = " & idAR 								
		set rs = con.executeQuery(sql)
		
		if not rs.eof then
			isSingleMovInv = rs.fields("IS_SINGLE_MOV_INV")
			
			rs.close
			set rs = nothing
			
			if edit = "1" then
				if isSingleMovInv = "1" then
					if noSustituciones = "0" then
						allowSustituir = "1"		
					else
						allowSustituir = "0"		
					end if
				else
					allowSustituir = "1"	
				end if
			else
				allowSustituir = "0"
			end if
			
			'if noSustituciones = "0" or action <> "SWAP" then
			if allowSustituir = 1 or action <> "SWAP" then
				sql = 	"EXEC SP_CONFIRM_SUSTITUCION " & idAR & "," & idNegocio & "," & idTecnico & "," & idUnidadEntrada & "," & idUnidadSalida & ",'" & action & "','" & noEquipo & "'," & idTecnico & "," & isDaniada
				con.executeQuery(sql)
				
				response.write("<r res=""OK"" desc=""Realizado exitosamente"" />")
			end if
		else
			response.write("<r res=""ERROR"" desc=""No salio lo del SingleMovInv"" />")
		end if	
	else
		response.write("<r res=""ERROR"" desc=""No salieron el no. de sustituciones"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""El Query String"" />")
end if

response.write("</d>")
con.endConnection
%>