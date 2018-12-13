<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->
<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

set con = new cConnection
con.startConnection(DB_INI)

	if request.QueryString("ia") <> "" then
		idAR = request.QueryString("ia")
	else
		idAR = ""
	end if
	
	if request.QueryString("ed") <> "" then
		edit = request.QueryString("ed")
	else
		edit = ""
	end if
	
	if request.QueryString("in") <> "" then
		idNegocio = request.QueryString("in")
	else
		idNegocio = ""
	end if
	
	if request.QueryString("it") <> "" then
		idTecnico = request.QueryString("it")
	else
		idNegocio = ""
	end if
	
	if request.QueryString("iu") <> "" then
		idUnidad = request.QueryString("iu")
	else
		idUnidad = ""
	end if

	if request.QueryString("ne") <> "" then
		noEquipo = request.QueryString("ne")
	else
		noEquipo = ""
	end if
	
	if request.QueryString("ip") <> "" then
		idTipoProducto = request.QueryString("ip")
	else
		idTipoProducto = ""
	end if
	
	'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
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
	con.endConnection	
	
	'' Verificar Instalaciones Previas
	Dim allowInstall	
	set con = new cConnection
	con.startConnection(DB_INI)
	
		sql = "SELECT ISNULL(COUNT(ID_INSTALACION),0) AS NUM_INSTALACIONES"
		sql = sql + " FROM BD_INSTALACIONES"
		sql = sql + " WHERE BD_INSTALACIONES.ID_AR = " & idAR
		set rs = con.executeQuery(sql)
		
		if not rs.eof then
			numInstalaciones = rs.fields("NUM_INSTALACIONES")
		end if
		
	rs.close
	set rs = nothing
	con.endConnection
	
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
	
	action="INSTALL"
	
	response.write("<?xml version=""1.0"" encoding=""ISO-8859-1"" ?>")
	response.write("<d")	
	
	if allowInstall = "1" then
						
		set con = new cConnection 
		con.startConnection(DB_INI)
						
			sql = 	"EXEC SP_CONFIRM_INSTALACION " & idAR & "," & idNegocio & "," & idTecnico & "," & idUnidad & ",'" & action & "','" & noEquipo & "'," & idTecnico						
			con.executeQuery(sql)		
			response.write(" res=""OK"" desc=""Todo bien"" val=""1"" >")
	else
		response.write(" res=""ERROR"" desc=""No se autorizó la instalación"">")
		response.end()
	end if	
	
	response.write("</d>")			 		 
					   
con.endConnection	
%>