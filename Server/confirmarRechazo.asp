<!--#include file="UTF8Functions.asp"-->
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

if request.QueryString("it") <> "" then
	idTecnico = request.QueryString("it")
else
	idTecnico = -1
end if


response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR =-1 and idTecnico =-1 then
	response.write("<r res=""ERROR"" desc=""No se recibieron los datos en el url"" />")
else

	set con = new cConnection
	con.startConnection(DB_INI)
	
		sql = "SELECT dbo.FUNC_VALIDATE_RECHAZO(" & idAR & ") AS IS_VALID_RECHAZO"
		sql = sql + " ,ID_SERVICIO"	
		sql = sql + " ,ID_CLIENTE"			
		sql = sql + " FROM BD_AR"
		sql = sql + " WHERE BD_AR.ID_AR = " & idAR							
		set rs = con.executeQuery(sql)
		
		if not rs.eof then
			isValidRechazo = rs.fields("IS_VALID_RECHAZO")
			idServicio = rs.fields("ID_SERVICIO")
			idCliente = rs.fields("ID_CLIENTE")				
		end if	
								
	rs.close
	set rs = nothing
	con.endConnection
	
	if isValidRechazo <> "1" then
	
		response.write("<r res=""OK"" val=""NO"" desc=""No es valido el rechazo"" />")
		response.write("</d>")
		response.end()
	
	end if
	
	set con = new cConnection
	con.startConnection(DB_INI)
	
	sql = "SELECT *"	
	sql = sql + " FROM C_SERVICIOS"
	sql = sql + " WHERE C_SERVICIOS.ID_SERVICIO = " & idServicio									
	set rs = con.executeQuery(sql)
	
	if not rs.eof then

		'' CLAVE_RECHAZO \( '.'   )
		isClaveRechazoRequired = rs.fields("needClaveRechazo")
	
		isCausaSolucionRequired = rs.fields("IS_CAUSA_SOLUCION_REQUIRED")
		isFolioServicioRechazoRequired = rs.fields("IS_FOLIO_SERVICIO_RECHAZO_REQUIRED")
		isOtorganteVoBoRechazoRequired = rs.fields("IS_OTORGANTE_VOBO_RECHAZO_REQUIRED")
		isDescripcionTrabajoRechazoRequired = rs.fields("IS_DESCRIPCION_TRABAJO_RECHAZO_REQUIRED")
		
		isIdCausaRechazoRequired = rs.fields("IS_ID_CAUSA_RECHAZO_REQUIRED")
		IsEspecificaisIdCausaRechazoEncontradaRequired = rs.fields("IS_ESPECIFICA_CAUSA_RECHAZO_REQUIRED")
				
	end if	
								
	rs.close
	set rs = nothing
	con.endConnection	
	
	set con = new cConnection
	con.startConnection(DB_INI)
		
		sql = "SELECT IS_VALIDA_TIR FROM C_CLIENTES INNER JOIN BD_AR ON C_CLIENTES.ID_CLIENTE = BD_AR.ID_CLIENTE WHERE BD_AR.ID_AR = " & idAR
		set rs = con.executeQuery(sql)
		if not rs.eof then
			isValidaTir = rs.fields("IS_VALIDA_TIR")
		end if
		
	rs.close
	set rs = nothing
	con.endConnection
	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	
	if isIdCausaRechazoRequired = "1" then 			

		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.ID_CAUSA_RECHAZO = " & request.QueryString("icr")	
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	
	if IsEspecificaisIdCausaRechazoEncontradaRequired  = "1" then 			

		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.ID_ESPECIFICA_CAUSA_RECHAZO = " & request.QueryString("ecr")	
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	'' START = COMMENTED FROM MIC3
	if isCausaSolucionRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.ID_CAUSA = " & request.QueryString("ic")	
				sql = sql + " ,BD_AR.ID_SOLUCION = " & request.QueryString("is")
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	'' END_START
	
	if isDescripcionTrabajoRechazoRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.DESCRIPCION_TRABAJO = '" & request.QueryString("dt") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if	
	
	if isFolioServicioRechazoRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.FOLIO_SERVICIO = '" & request.QueryString("fs") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isOtorganteVoBoRechazoRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.OTORGANTE_VOBO = '" & request.QueryString("ovr") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if

	'' VERIFIED IF CLAVE RECHAZO IS REQUIRED AND UPDATE TABLE  (updateNeedClaveRechazo.asp) \( '.'   )
	if isClaveRechazoRequired = "1" then 			
		set con = new cConnection
		con.startConnection(DB_INI)
		sql = 	"UPDATE BD_AR SET"
		sql = sql + " BD_AR.CLAVE_RECHAZO = '" & request.QueryString("crz") & "'"
		sql = sql + " WHERE BD_AR.ID_AR = " & idAR    		
		con.executeQuery(sql)
		con.endConnection
	end if
	
	set con = new cConnection
	con.startConnection(DB_INI)			
	
			sql = 	"UPDATE BD_AR SET"
			sql = sql + "  BD_AR.AUTORIZADOR_RECHAZO = '" & request.QueryString("au")	& "'"
			sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
			con.executeQuery(sql)	
						
	con.endConnection	
	
	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''	
		
	set con = new cConnection
	con.startConnection(DB_INI)			
	
			sql = 	"EXEC SP_CONFIRMAR_CIERRE " &  idAR & ",2,'" & request.QueryString("fc")& "','" & replace(request.QueryString("pr"),",","") & "'," & idTecnico 												
			con.executeQuery(sql)	
						
	con.endConnection
	
	if isValidaTir = "1" then
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + "  BD_AR.ID_STATUS_VALIDACION_PREFACTURACION = 1"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection	
		
		set con = new cConnection
		con.startConnection(DB_INI)

			sql = 	"SELECT BD_AR.ID_STATUS_VALIDACION_PREFACTURACION FROM BD_AR WHERE BD_AR.ID_AR = " & idAR
			set rs = con.executeQuery(sql)
			if not rs.EOF then
				status_ini = rs.fields("ID_STATUS_VALIDACION_PREFACTURACION")
			else
				status_ini = "NULL"
			end if

		con.endConnection
		
		set con = new cConnection
		con.startConnection(DB_INI)
			sql = 	"INSERT INTO BD_BITACORA_VALIDACION_PREFACTURACION"
			sql = sql + " (ID_AR ,ID_STATUS_INI, ID_STATUS_FIN, ID_USUARIO_ALTA, FEC_ALTA, DOCUMENTO) VALUES ("
			sql = sql + "" & idAr & ", " & status_ini & ", 1  , " & idTecnico & ", GETDATE(), '')"
		con.executeQuery(sql)
		con.endConnection	
	
	end if
	
	response.write("<r res=""OK"" val=""SI"" desc=""Todo bien, rechazo confirmado"" />")								

end if	

response.write("</d>")

%>
