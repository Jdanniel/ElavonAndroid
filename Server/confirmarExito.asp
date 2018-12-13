<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if request.QueryString("id") = "" or request.QueryString("s")="" then
	response.write("<r res=""ERROR"" desc=""No se recibieron los datos en el url"" />")
else
	idAR = request.QueryString("id")
	idServicio = request.QueryString("s")
	
	if request.QueryString("vn") <> "" then
		sql= " UPDATE BD_AR SET VOLTAJE_NEUTRO='"&request.QueryString("vn")&"' WHERE ID_AR="&idAR
		con.executeQuery(sql)
	end if
	if request.QueryString("fv") <> "" then
		sql= " UPDATE BD_AR SET FOLIO_VALIDACION='"&request.QueryString("fv")&"' WHERE ID_AR="&idAR
		con.executeQuery(sql)
	end if
	if request.QueryString("ft") <> "" then
		sql= " UPDATE BD_AR SET FOLIO_TIR='"&request.QueryString("ft")&"' WHERE ID_AR="&idAR
		con.executeQuery(sql)
	end if
	if request.QueryString("vt") <> "" then
		sql= " UPDATE BD_AR SET VOLTAJE_TIERRA='"&request.QueryString("vt")&"' WHERE ID_AR="&idAR
		con.executeQuery(sql)
	end if
	if request.QueryString("vtn") <> "" then
		sql= " UPDATE BD_AR SET VOLTAJE_TIERRA_NEUTRO='"&request.QueryString("vtn")&"' WHERE ID_AR="&idAR
		con.executeQuery(sql)
	end if
	
	if request.QueryString("etf") <> "" then
	
		if not isNull(request.QueryString("etf")) then
		
			sql=" UPDATE BD_AR SET ID_ESPECIF_TIPO_FALLA="&request.QueryString("etf")&" WHERE ID_AR="&idAR
			con.executeQuery(sql)
		end if
	
	end if
	
		sql = "SELECT dbo.FUNC_VALIDATE_CIERRE_BB(" & idAR & ") AS IS_VALID_CIERRE"
		sql = sql + " ,ID_SERVICIO"	
		sql = sql + " ,IS_ACTUALIZACION "	
		sql = sql + " ,IS_INSTALACION "	
		sql = sql + " ,IS_SUSTITUCION"
		sql = sql + " ,IS_RETIRO "			
		sql = sql + " FROM BD_AR"
		sql = sql + " WHERE BD_AR.ID_AR = " & idAR									
		set rs = con.executeQuery(sql)
		
		if not rs.eof then
			isValidCierre = rs.fields("IS_VALID_CIERRE")
			idServicio = rs.fields("ID_SERVICIO")	
			isActualizacion = rs.fields("IS_ACTUALIZACION")		
			isInstalacion = rs.fields("IS_INSTALACION")		
			isSustitucion = rs.fields("IS_SUSTITUCION")
			isRetiro = rs.fields("IS_RETIRO")				
		end if	
								
	rs.close
	set rs = nothing
	con.endConnection
	
	if isValidCierre <> "0" then
	
		response.write("<r res=""OK"" val=""NO"" desc=""No es valido el cierre"" />")
		response.write("</d>")
		response.end()
	
	end if
	
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
	
	
	set con = new cConnection
	con.startConnection(DB_INI)
	
		sql=""
		sql=sql+ "SELECT "
		sql=sql+ "	 *"
		sql=sql+ "	,CASE "
		sql=sql+ "		WHEN "
		sql=sql+ "			( "
		sql=sql+ "				IS_INSTALACION_LLAVES_REQUIRED = 1 "
		sql=sql+ "				OR IS_TIPO_LLAVE_REQUIRED = 1 "
		sql=sql+ "				OR IS_STATUS_INSTALACION_LLAVES_REQUIRED = 1 "
		sql=sql+ "				OR IS_NOMBRE_PERSONA_LLAVES_A_REQUIRED = 1 "
		sql=sql+ "				OR IS_NOMBRE_PERSONA_LLAVES_B_REQUIRED = 1 "
		sql=sql+ "				OR IS_TIPO_PW_REQUIRED = 1 "
		sql=sql+ "				OR IS_TIPO_TECLADO_REQUIRED = 1 "
		sql=sql+ "				OR IS_VERSION_TECLADO_EPP_REQUIRED = 1 "
		sql=sql+ "				OR IS_PROCESADOR_REQUIRED = 1 "
		sql=sql+ "				OR IS_VELOCIDAD_PROCESADOR_REQUIRED = 1 "
		sql=sql+ "				OR IS_MEMORIA_REQUIRED = 1 "
		sql=sql+ "				OR IS_CAPACIDAD_DISCO_DURO_REQUIRED = 1 "
		sql=sql+ "				OR IS_MONITOR_REQUIRED = 1 "
		sql=sql+ "				OR IS_LECTOR_TARJETA_REQUIRED = 1 "
		sql=sql+ "				OR IS_APLICACION_REQUIRED = 1 "
		sql=sql+ "				OR IS_VERSION_REQUIRED = 1 "
		sql=sql+ "				OR IS_CAJA_REQUIRED = 1 "
		sql=sql+ "			) "
		sql=sql+ "		AND "
		sql=sql+ "			( "
		sql=sql+ "				SELECT COUNT(*) "
		sql=sql+ "				FROM BD_INFORMACION_UNIDAD_AR "
		sql=sql+ "				WHERE ID_AR = " & idAR & " "
		sql=sql+ "			) = 0 "
		sql=sql+ "		THEN 'INSERTAR' "
		sql=sql+ "		ELSE '' "
		sql=sql+ "	END AS VALIDA_INSERTAR "
		sql=sql+ " FROM C_SERVICIOS"
		sql=sql+ " WHERE C_SERVICIOS.ID_SERVICIO = " & idServicio									
		set rs = con.executeQuery(sql)
		
		if not rs.eof then

			'' SIM \( '.'   )
			isSIMRequired = rs.fields("needNoSim")
		
			isCausaSolucionRequired = rs.fields("IS_CAUSA_SOLUCION_REQUIRED")
			isTASRequired = rs.fields("IS_TAS_REQUIRED")
			isOtorganteTASRequired = rs.fields("IS_OTORGANTE_TAS_REQUIRED")				
			isNoEquipoRequired = rs.fields("IS_NO_EQUIPO_REQUIRED")	
			isNoSerieRequired = rs.fields("IS_NO_SERIE_REQUIRED")	
			isNoInventarioRequired = rs.fields("IS_NO_INVENTARIO_REQUIRED")
			isIdModeloRequired = rs.fields("IS_ID_MODELO_REQUIRED")	
			
			isFecLlegadaRequired = rs.fields("IS_FEC_LLEGADA_REQUIRED")
			isFecLlegadaTercerosRequired = rs.fields("IS_FEC_LLEGADA_TERCEROS_REQUIRED")	
			isFolioServicioRequired = rs.fields("IS_FOLIO_SERVICIO_REQUIRED")
			isFecIniIngenieroRequired = rs.fields("IS_FEC_INI_INGENIERO_REQUIRED")		
			isFecFinIngenieroRequired = rs.fields("IS_FEC_FIN_INGENIERO_REQUIRED")		
			isOtorganteVoBoRequired = rs.fields("IS_OTORGANTE_VOBO_REQUIRED")	
			isOtorganteVoBoTercerosRequired = rs.fields("IS_OTORGANTE_VOBO_TERCEROS_REQUIRED")	
			isIntensidadSenialRequired = rs.fields("IS_INTENSIDAD_SENIAL_REQUIRED")		
			isIsSIMRemplazadaRequired = rs.fields("IS_IS_SIM_REMPLAZADA_REQUIRED")	
			
			isFallaEncontradaRequired = rs.fields("IS_FALLA_ENCONTRADA_REQUIRED")
			isOtorganteVoBoClienteRequired = rs.fields("IS_OTORGANTE_VOBO_CLIENTE_REQUIRED")
			isMotivoCobroRequired = rs.fields("IS_MOTIVO_COBRO_REQUIRED")
			isIsSoporteClienteRequired = rs.fields("IS_IS_SOPORTE_CLIENTE_REQUIRED")
			isIsBoletinRequired = rs.fields("IS_IS_BOLETIN_REQUIRED")	
			isOtorganteSoporteClienteRequired = rs.fields("IS_OTORGANTE_SOPORTE_CLIENTE_REQUIRED")
			isCadenaCierreEscritaRequired = rs.fields("IS_CADENA_CIERRE_ESCRITA_REQUIRED")
			
			isCodigoIntervencionRequired = rs.fields("IS_CODIGO_INTERVENCION_REQUIRED")
	'''''++++++++++++++++++++++MODOFICACION IS_DV_REQUIRED+++++++++++++++++++++++++++++++++++++++++++++++++++
			isDigitoVerificadorRequired = rs.fields("IS_DV_REQUIRED")
			isIdTipoFallaEncontradaRequired = rs.fields("IS_ID_TIPO_FALLA_ENCONTRADA_REQUIRED")
			
			isInstalacionLlavesRequired = rs.fields("IS_INSTALACION_LLAVES_REQUIRED")
	'''''++++++++++++++++++++++MODOFICACION TIPO_LLAVE_REQUIRED+++++++++++++++++++++++++++++++++++++++++++++++++++
			isTipoLlaveRequired = rs.fields("IS_TIPO_LLAVE_REQUIRED")
			isStatusInstalacionLlavesRequired = rs.fields("IS_STATUS_INSTALACION_LLAVES_REQUIRED")
			isNombrePersonaLlavesARequired = rs.fields("IS_NOMBRE_PERSONA_LLAVES_A_REQUIRED")
			isNombrePersonaLlavesBRequired = rs.fields("IS_NOMBRE_PERSONA_LLAVES_B_REQUIRED")
			isTipoPwRequired = rs.fields("IS_TIPO_PW_REQUIRED")
			isTipoTecladoRequired = rs.fields("IS_TIPO_TECLADO_REQUIRED")
			isVersionTecladoEPPRequired = rs.fields("IS_VERSION_TECLADO_EPP_REQUIRED")
			isProcesadorRequired = rs.fields("IS_PROCESADOR_REQUIRED")
			isVelocidadProcesadorRequired = rs.fields("IS_VELOCIDAD_PROCESADOR_REQUIRED")
			isMemoriaRequired = rs.fields("IS_MEMORIA_REQUIRED")
			isCapacidadDiscoDuroRequired = rs.fields("IS_CAPACIDAD_DISCO_DURO_REQUIRED")
			isMonitorRequired = rs.fields("IS_MONITOR_REQUIRED")
			isLectorTarjetaRequired = rs.fields("IS_LECTOR_TARJETA_REQUIRED")
			isAplicacionRequired = rs.fields("IS_APLICACION_REQUIRED")
			isVersionRequired = rs.fields("IS_VERSION_REQUIRED")
			isCajaRequired = rs.fields("IS_CAJA_REQUIRED")
			validaInsertar = rs.fields("VALIDA_INSERTAR")
						
		end if	
								
	rs.close
	set rs = nothing
	con.endConnection


	'''''''''' 	
	if validaInsertar = "INSERTAR" then
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = ""
				sql = sql + "INSERT INTO "
				sql = sql + "BD_INFORMACION_UNIDAD_AR (ID_AR) "	
				sql = sql + "VALUES( " & idAR & " ); "
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	
	if isCausaSolucionRequired = "1" then 			
	
		if request.QueryString("ca") <> "" then
			set con = new cConnection
			con.startConnection(DB_INI)
			
					sql = 	"UPDATE BD_AR SET"
					sql = sql + " BD_AR.ID_CAUSA = " & request.QueryString("ca")
					sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
					con.executeQuery(sql)
			con.endConnection
			
		end if
		if request.QueryString("so") <> "" then

			set con = new cConnection
			con.startConnection(DB_INI)
			
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.ID_SOLUCION = " & request.QueryString("so")	
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)
			con.endConnection
				
		end if
		
	end if	
	
	if isTASRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.FOLIO_TAS = '" & request.QueryString("ftas") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if	
	
	if isOtorganteTASRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.OTORGANTE_TAS = '" & request.QueryString("ot") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if		
	
	if isNoEquipoRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.NO_EQUIPO = NULLIF('" & request.QueryString("ne") & "','')"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
			
	if isNoSerieRequired = "1" and isSustitucion = "0" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.NO_SERIE_FALLA = '" & request.QueryString("ns") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isNoInventarioRequired = "1" and isSustitucion = "0" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.NO_INVENTARIO_FALLA = '" & request.QueryString("ni") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isIdModeloRequired = "1" and isSustitucion = "0" then 			
	
		modelo  = request.QueryString("im")
		if modelo <> "" then

			set con = new cConnection
			con.startConnection(DB_INI)			
			
			sql = 	"UPDATE BD_AR SET"
			sql = sql + " BD_AR.ID_MODELO_FALLA = " & modelo
			sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
			con.executeQuery(sql)
								
			con.endConnection
			
		end if
		
	end if
	
	if isFecLlegadaRequired = "1" then
		
		Dim horaCompletaLlegada
		horaCompletaLlegada = request.QueryString("fll")
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.FEC_LLEGADA = '" & horaCompletaLlegada & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if		
	
	if isFecLlegadaTercerosRequired = "1" then 			
	
		Dim horaCompletaLlegadaTerceros
		horaCompletaLlegadaTerceros = request.QueryString("fllt")
		
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.FEC_LLEGADA_TERCEROS = '" & horaCompletaLlegadaTerceros & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if	
	
	if isFolioServicioRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.FOLIO_SERVICIO = '" & request.QueryString("fs") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if	
	
	if isFecIniIngenieroRequired = "1" then 			
	
		Dim horaCompletaInicioIngeniero
		horaCompletaInicioIngeniero = request.QueryString("fiin")
		
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.FEC_INI_INGENIERO = '" & horaCompletaInicioIngeniero & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if		
	
	if isFecFinIngenieroRequired = "1" then 			
	
		Dim horaCompletaFinIngeniero
		horaCompletaFinIngeniero = request.QueryString("ffin")
		
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.FEC_FIN_INGENIERO = '" & horaCompletaFinIngeniero & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if		
	
	if isOtorganteVoBoRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.OTORGANTE_VOBO = '" & request.QueryString("ov") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if	
	
	if isOtorganteVoBoTercerosRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.OTORGANTE_VOBO_TERCEROS = '" & request.QueryString("ovt") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if	
	
	if isIntensidadSenialRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.INTENSIDAD_SENIAL = '" & request.QueryString("in") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isIsSIMRemplazadaRequired = "1" then 			
		
		if request.QueryString("sim") <> "" then
			set con = new cConnection
			con.startConnection(DB_INI)			
			
					sql = 	"UPDATE BD_AR SET"
					sql = sql + " BD_AR.IS_SIM_REMPLAZADA = " & request.QueryString("sim")
					sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
					con.executeQuery(sql)	
								
			con.endConnection		
		end if
				
	end if	
	
	if isFallaEncontradaRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.FALLA_ENCONTRADA = '" & request.QueryString("fe") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isOtorganteVoBoClienteRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.OTORGANTE_VOBO_CLIENTE = '" & request.QueryString("ovc") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if		
	
	if isMotivoCobroRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.MOTIVO_COBRO = '" & request.QueryString("mc") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isIsSoporteClienteRequired = "1" then 			

		if request.Form("IS_SOPORTE_CLIENTE") <> "" then
		
			set con = new cConnection
			con.startConnection(DB_INI)			
			
					sql = 	"UPDATE BD_AR SET"
					sql = sql + " BD_AR.IS_SOPORTE_CLIENTE = " & request.QueryString("sc")
					sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
					con.executeQuery(sql)	
								
			con.endConnection
		
		end if
		
	end if
	
	if isIsBoletinRequired = "1" then 			
	
		if request.QueryString("b") <> "" then
			set con = new cConnection
			con.startConnection(DB_INI)			
			
					sql = 	"UPDATE BD_AR SET"
					sql = sql + " BD_AR.IS_BOLETIN = " & request.QueryString("b")
					sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
					con.executeQuery(sql)	
								
			con.endConnection
		end if
		
	end if
	
	if isOtorganteSoporteClienteRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.OTORGANTE_SOPORTE_CLIENTE = '" & request.QueryString("osc") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if

	if isCadenaCierreEscritaRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.CADENA_CIERRE_ESCRITA = '" & request.QueryString("cc") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isDigitoVerificadorRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.DIGITO_VERIFICADOR = '" & request.QueryString("dv") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if	
	
	if isIdTipoFallaEncontradaRequired = "1" then 			
	
		if request.QueryString("itfe") <> "" then
			set con = new cConnection
			con.startConnection(DB_INI)			
			
					sql = 	"UPDATE BD_AR SET"
					sql = sql + " BD_AR.ID_TIPO_FALLA_ENCONTRADA = '" & request.QueryString("itfe") & "'"
					sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
					con.executeQuery(sql)	
								
			con.endConnection
		end if
		
	end if																					

	if isCodigoIntervencionRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.CODIGO_INTERVENCION = '" & ucase(request.QueryString("ci")) & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if

	'''''''''' Información Unidad - Inicio
	
	if isInstalacionLlavesRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.INSTALACION_LLAVES = '" & request.QueryString("ill") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isTipoLlaveRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.TIPO_LLAVE = '" & request.QueryString("tll") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	
	if isStatusInstalacionLlavesRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.STATUS_INSTALACION_LLAVES = '" & request.QueryString("stll") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isNombrePersonaLlavesARequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.NOMBRE_PERSONA_LLAVES_A = '" & request.QueryString("nplla") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isNombrePersonaLlavesBRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.NOMBRE_PERSONA_LLAVES_B = '" & request.QueryString("npllb") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isTipoPwRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.TIPO_PW = '" & request.QueryString("tpw") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isTipoTecladoRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.TIPO_TECLADO = '" & request.QueryString("tt") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isVersionTecladoEPPRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.VERSION_TECLADO_EPP = '" & request.QueryString("vtepp") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isProcesadorRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.PROCESADOR = '" & request.QueryString("pr") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isVelocidadProcesadorRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.VELOCIDAD_PROCESADOR = '" & request.QueryString("vp") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isMemoriaRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.MEMORIA = '" & request.QueryString("me") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isCapacidadDiscoDuroRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.CAPACIDAD_DISCO_DURO = '" & request.QueryString("cdd") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isMonitorRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.MONITOR = '" & request.QueryString("mon") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isLectorTarjetaRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.LECTOR_TARJETA = '" & request.QueryString("lt") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isAplicacionRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.APLICACION = '" & request.QueryString("app") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isVersionRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_INFORMACION_UNIDAD_AR SET"
				sql = sql + " BD_INFORMACION_UNIDAD_AR.VERSION = '" & request.QueryString("ver") & "'"
				sql = sql + " WHERE BD_INFORMACION_UNIDAD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if
	
	if isCajaRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)			
		
				sql = 	"UPDATE BD_AR SET"
				sql = sql + " BD_AR.CAJA = '" & request.QueryString("caj") & "'"
				sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
				con.executeQuery(sql)	
							
		con.endConnection		
		
	end if

	''VERIFIED IF NO SIM IS REQUIRED AND UPDATE TABLE (updateNeedNoSimRequired.asp) \( '.'   )

	if isSIMRequired = "1" then 			
	
		set con = new cConnection
		con.startConnection(DB_INI)
		sql = "UPDATE BD_AR SET"
		sql = sql + " BD_AR.NO_SIM = '" & request.QueryString("simm") & "'"
		sql = sql + " WHERE BD_AR.ID_AR = " & idAR
		con.executeQuery(sql)
		con.endConnection	
		
	end if

	
	'''''''''' Información Unidad - Fin
	
	set con = new cConnection
	con.startConnection(DB_INI)			
	
	if request.QueryString("desc") <> "" then
		sql = 	"UPDATE BD_AR SET"
		sql = sql + "  BD_AR.DESCRIPCION_TRABAJO = '" & request.QueryString("desc")	 & "'"
		sql = sql + " ,BD_AR.ATIENDE = '" & request.QueryString("at")	 & "'"			
		sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
		con.executeQuery(sql)	
		con.endConnection		
	else
		sql = 	"UPDATE BD_AR SET"
		sql = sql + "  BD_AR.ID_DESCRIPCION_TRABAJO  = '" & request.QueryString("desct")	 & "'"
		sql = sql + " ,BD_AR.ATIENDE = '" & request.QueryString("at")	 & "'"			
		sql = sql + " WHERE BD_AR.ID_AR = " & idAR																			
		con.executeQuery(sql)	
		con.endConnection
	end if
	
			
	
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
			sql = sql + "" & idAr & ", " & status_ini & ", 1  , " & request.QueryString("it") & ", GETDATE(), '')"
		con.executeQuery(sql)
		con.endConnection	
	
	end if
	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''	
	isUpdateStatusAR = "1"
	if isUpdateStatusAR = "1" then
		set con = new cConnection
		con.startConnection(DB_INI)
		
		Dim horaTotalCierre
		horaTotalCierre = request.QueryString("fecc") 
		
				sql = 	"EXEC SP_CONFIRMAR_CIERRE " &  idAR & ",1,'"  & horaTotalCierre  & "','" & replace(request.QueryString("pre"),",","") &"'," & request.QueryString("it")
				con.executeQuery(sql)
				response.write("<r res=""OK"" val=""SI"" desc=""Todo bien, exito confirmado"" />")	
							
		con.endConnection
	end if
	
end if

response.write("</d>")
'con.endConnection
%>