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
	
	if request.Form("ID_UNIDAD") <> "" then
		idUnidad = request.Form("ID_UNIDAD")
	elseif	request.QueryString("ID_UNIDAD") <> "" then
		idUnidad = request.QueryString("ID_UNIDAD")
	else	
		idUnidad = ""
	end if	
	
	if request.Form("ACTION") <> "" then
		action = request.Form("ACTION")
	elseif	request.QueryString("ACTION") <> "" then
		action = request.QueryString("ACTION")
	else	
		action = ""
	end if
	
	if request.Form("IS_DANIADA") <> "" then
		if request.Form("IS_DANIADA") = "on" then
			isDaniada = 1
		else
			isDaniada = 0
		end if
	elseif	request.QueryString("IS_DANIADA") <> "" then
		if request.QueryString("IS_DANIADA") = "on" then
			isDaniada = 1
		else
			isDaniada = 0
		end if
	else	
		isDaniada = 0
	end if
	
	if request.Form("ID_TIPO_PRODUCTO") <> "" then
		idTipoProducto = request.Form("ID_TIPO_PRODUCTO")
	elseif request.QueryString("ID_TIPO_PRODUCTO") <> "" then
		idTipoProducto = request.QueryString("ID_TIPO_PRODUCTO")
	else
		idTipoProducto = "1"
	end if

	'''''''''' Verificar cuántos retiros se pueden realizar
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
	
	'''''''''' Verificar Retiros Previos
	Dim allowRetiro	
	set con = new cConnection
	con.startConnection(DB_INI)
	
		sql = "SELECT ISNULL(COUNT(ID_RETIRO),0) AS NUM_RETIROS"
		sql = sql + " FROM BD_RETIROS"
		sql = sql + " WHERE BD_RETIROS.ID_AR = " & idAR		
		set rs = con.executeQuery(sql)
		
		if not rs.eof then
			numRetiros = rs.fields("NUM_RETIROS")
		end if
		
	rs.close
	set rs = nothing
	con.endConnection
	
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
	
		set con = new cConnection
		con.startConnection(DB_INI)
						
				sql = 	"EXEC SP_CONFIRM_RETIRO " & idAR & "," & idNegocio & "," & idTecnico & "," & idUnidad & ",'" & action & "'," & ID_USUARIO & "," & isDaniada
				con.executeQuery(sql)		
						
		con.endConnection
		
	elseif action = "CANCEL_RETIRO" then
	
		set con = new cConnection
		con.startConnection(DB_INI)
						
				sql = 	"EXEC SP_CONFIRM_RETIRO " & idAR & "," & idNegocio & "," & idTecnico & "," & idUnidad & ",'" & action & "'," & ID_USUARIO & "," & isDaniada
				con.executeQuery(sql)		
						
		con.endConnection

	end if		
					
	Response.Redirect("../retiro.asp?ID_AR=" & idAR & "&EDIT=" & edit & "&ID_TIPO_PRODUCTO=" & idTipoProducto)

	
%>
