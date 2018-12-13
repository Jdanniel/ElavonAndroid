<!--#include file="../../../connections/Session.asp"-->
<!--#include file="../../../connections/constants.asp"-->
<!--#include file="../../../connections/cConnection.asp"-->

<%

	if request.Form("ID_AR") <> "" then
		idAR = request.Form("ID_AR")
	elseif	request.QueryString("ID_AR") <> "" then
		idAR = request.QueryString("ID_AR")
	else	
		idAR = ""
	end if	
	
	if request.Form("EDIT") <> "" then
		edit = request.Form("EDIT")
	elseif request.QueryString("EDIT") <> "" then
		edit = request.QueryString("EDIT")
	else
		edit = ""
	end if		
	
	if request.Form("ID_NEGOCIO") <> "" then
		idNegocio = request.Form("ID_NEGOCIO")
	elseif	request.QueryString("ID_NEGOCIO") <> "" then
		idNegocio = request.QueryString("ID_NEGOCIO")
	else	
		idNegocio = ""
	end if	
	
	if request.Form("ID_TECNICO") <> "" then
		idTecnico = request.Form("ID_TECNICO")
	elseif	request.QueryString("ID_TECNICO") <> "" then
		idTecnico = request.QueryString("ID_TECNICO")
	else	
		idTecnico = ""
	end if	
	
	if request.Form("ID_UNIDAD_ENTRADA1") <> "" then
		idUnidadEntrada = request.Form("ID_UNIDAD_ENTRADA1")
	elseif	request.QueryString("ID_UNIDAD_ENTRADA1") <> "" then
		idUnidadEntrada = request.QueryString("ID_UNIDAD_ENTRADA1")
	else	
		idUnidadEntrada = ""
	end if	
	
	if request.Form("ID_UNIDAD_SALIDA1") <> "" then
		idUnidadSalida = request.Form("ID_UNIDAD_SALIDA1")
	elseif	request.QueryString("ID_UNIDAD_SALIDA1") <> "" then
		idUnidadSalida = request.QueryString("ID_UNIDAD_SALIDA1")
	else	
		idUnidadSalida = ""
	end if
	
	if request.Form("IS_DANIADA1") <> "" then
		isDaniada = request.Form("IS_DANIADA1")
	elseif	request.QueryString("IS_DANIADA1") <> "" then
		isDaniada = request.QueryString("IS_DANIADA1")
	else	
		isDaniada = "NULL"
	end if
	
	if request.Form("ACTION") <> "" then
		action = request.Form("ACTION")
	elseif	request.QueryString("ACTION") <> "" then
		action = request.QueryString("ACTION")
	else	
		action = ""
	end if		
	
	if request.Form("NO_EQUIPO1") <> "" then
		noEquipo = request.Form("NO_EQUIPO1")
	elseif	request.QueryString("NO_EQUIPO1") <> "" then
		noEquipo = request.QueryString("NO_EQUIPO1")
	else	
		noEquipo = ""
	end if
	
	if request.Form("ID_TIPO_PRODUCTO") <> "" then
		idTipoProducto = request.Form("ID_TIPO_PRODUCTO")
	elseif request.QueryString("ID_TIPO_PRODUCTO") <> "" then
		idTipoProducto = request.QueryString("ID_TIPO_PRODUCTO")
	else
		idTipoProducto = "1"
	end if
	
	set con = new cConnection
	con.startConnection(DB_INI)
					
		sql = 	"SELECT ISNULL(COUNT(ID_SUSTITUCION),0) AS NO_SUSTITUCIONES FROM BD_SUSTITUCIONES WHERE ID_AR = " & idAR					
		set rs = con.executeQuery(sql)	
		
		if not rs.eof then
			noSustituciones	= rs.fields("NO_SUSTITUCIONES")
		end if

	rs.close
	set rs = nothing
	con.endConnection	
	
	set con = new cConnection
	con.startConnection(DB_INI)
	
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
	
	set con = new cConnection
	con.startConnection(DB_INI)
					
			sql = 	"EXEC SP_CONFIRM_SUSTITUCION " & idAR & "," & idNegocio & "," & idTecnico & "," & idUnidadEntrada & "," & idUnidadSalida & ",'" & action & "','" & noEquipo & "'," & ID_USUARIO & "," & isDaniada
			con.executeQuery(sql)		
					
	con.endConnection
	
	end if				
					
	Response.Redirect("../sustitucion.asp?ID_AR=" & idAR & "&EDIT=" & edit & "&ID_TIPO_PRODUCTO=" & idTipoProducto)

	
%>
