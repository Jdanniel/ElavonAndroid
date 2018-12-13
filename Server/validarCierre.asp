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

if request.QueryString("t") <> "" then
	tipo = request.QueryString("t")
else
	tipo = -1
end if



response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 and tipo <> -1 then
	sql="SELECT"
	sql=sql+" BD_AR.ID_AR, BD_AR.ID_TECNICO"
	sql=sql+" ,dbo.FUNC_VALIDATE_CIERRE_BB(BD_AR.ID_AR) AS IS_VALID_CIERRE"
	sql=sql+" ,dbo.FUNC_VALIDATE_RECHAZO_BB(BD_AR.ID_AR) AS IS_VALID_RECHAZO"
	sql=sql+" ,CASE WHEN (C_STATUS_AR.IS_ABIERTA_FREEZE = 1 AND C_STATUS_AR.IS_SOLICITUD_ALMACEN = 0 AND C_STATUS_AR.IS_SOLICITUD_VIATICOS = 0)"
	sql=sql+"       THEN 1"
	sql=sql+" 	    WHEN (C_STATUS_AR.IS_ABIERTA_FREEZE = 1 AND C_STATUS_AR.IS_SOLICITUD_ALMACEN = 1)"
	sql=sql+" 	    THEN CASE WHEN (SELECT COUNT(ID_AR)"
	sql=sql+" 						FROM BD_SOLICITUDES_ALMACEN"
	sql=sql+" 						INNER JOIN C_STATUS_SOLICITUD_ALMACEN ON BD_SOLICITUDES_ALMACEN.ID_STATUS_SOLICITUD_ALMACEN = C_STATUS_SOLICITUD_ALMACEN.ID_STATUS_SOLICITUD_ALMACEN"
	sql=sql+" 						WHERE C_STATUS_SOLICITUD_ALMACEN.IS_CONFIRMAR_ENTREGA = 0"
	sql=sql+" 						AND C_STATUS_SOLICITUD_ALMACEN.IS_CANCELADO = 0"
	sql=sql+" 						AND BD_SOLICITUDES_ALMACEN.ID_AR = BD_AR.ID_AR) > 0"
	sql=sql+" 				  THEN 1"
	sql=sql+" 			 	  ELSE 0"
	sql=sql+" 			 END"
	sql=sql+" 	    WHEN (C_STATUS_AR.IS_ABIERTA_FREEZE = 1 AND C_STATUS_AR.IS_SOLICITUD_VIATICOS = 1)"
	sql=sql+" 	    THEN CASE WHEN (SELECT COUNT(ID_AR)"
	sql=sql+" 						FROM BD_SOLICITUDES_VIATICOS"
	sql=sql+" 						INNER JOIN C_STATUS_SOLICITUD_VIATICOS ON BD_SOLICITUDES_VIATICOS.ID_STATUS_SOLICITUD_VIATICOS = C_STATUS_SOLICITUD_VIATICOS.ID_STATUS_SOLICITUD_VIATICOS"
	sql=sql+" 						WHERE C_STATUS_SOLICITUD_VIATICOS.IS_HABILITA_AR = 0"
	sql=sql+" 						AND BD_SOLICITUDES_VIATICOS.ID_AR = BD_AR.ID_AR) > 0"
	sql=sql+" 				  THEN 1"
	sql=sql+" 			 	  ELSE 0"
	sql=sql+" 			 END"
	sql=sql+" 		ELSE 0"
	sql=sql+"  END AS IS_ABIERTA_FREEZE"
	sql=sql+" FROM BD_AR"
	sql=sql+" INNER JOIN C_STATUS_AR ON BD_AR.ID_STATUS_AR = C_STATUS_AR.ID_STATUS_AR"
	sql=sql+" WHERE BD_AR.STATUS = 'PROCESADO' AND BD_AR.ID_AR = " & idAR

	set rs = con.executeQuery(sql)

	if rs.fields("IS_VALID_CIERRE") = 1 then
		response.write("<r res=""OK"" val=""INSTALACION"" desc=""No es valido el cierre como exito"" />")
	elseif rs.fields("IS_VALID_CIERRE") = 2 then
		response.write("<r res=""OK"" val=""SUSTITUCION"" desc=""No es valido el cierre como exito"" />")
	elseif rs.fields("IS_VALID_CIERRE") = 3 then
		response.write("<r res=""OK"" val=""RETIRO"" desc=""No es valido el cierre como exito"" />")
	elseif rs.fields("IS_VALID_CIERRE") = 4 then
		response.write("<r res=""OK"" val=""INSUMO"" desc=""No es valido el cierre como exito"" />")
	elseif rs.fields("IS_ABIERTA_FREEZE") = 1 then
		response.write("<r res=""OK"" val=""FREEZE"" desc=""No es valido el cierre como exito"" />")
	elseif rs.fields("IS_ABIERTA_FREEZE") <> 1 and rs.fields("IS_VALID_CIERRE") = 0 then
		
		ID_USUARIO = rs.fields("ID_TECNICO")
		
		'sql = "EXEC SP_VERIFY_VISITA " & idAR & "," & ID_USUARIO
		'con.executeQuery(sql)

		'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

		sql = "SELECT dbo.FUNC_VALIDATE_CIERRE(" & idAR & ") AS IS_VALID_CIERRE"
		sql = sql + " ,BD_AR.ID_SERVICIO"
		sql = sql + " ,BD_AR.ID_CLIENTE"
		sql = sql + " ,(select is_wincor from c_clientes where id_cliente = BD_AR.ID_CLIENTE) as isWincor"
		sql = sql + " ,BD_AR.ID_CAUSA"
		sql = sql + " ,BD_AR.ID_SOLUCION"
		sql = sql + " ,dbo.FUNC_GET_NO_EQUIPO(BD_AR.ID_AR) AS NO_EQUIPO"
		sql = sql + " ,BD_AR.DESCRIPCION_TRABAJO "
		sql = sql + " ,BD_AR.ATIENDE"
		sql = sql + " ,BD_AR.FOLIO_TAS "
		sql = sql + " ,BD_AR.CODIGO_INTERVENCION "
		sql = sql + " ,BD_AR.NO_SERIE_FALLA "
		sql = sql + " ,BD_AR.NO_INVENTARIO_FALLA"
		sql = sql + " ,BD_AR.ID_MODELO_FALLA "
		sql = sql + " ,BD_AR.IS_ACTUALIZACION "
		sql = sql + " ,BD_AR.IS_INSTALACION "
		sql = sql + " ,BD_AR.IS_SUSTITUCION"
		sql = sql + " ,BD_AR.IS_RETIRO "
		sql = sql + " ,BD_AR.ID_PROYECTO "
		sql = sql + " ,BD_AR.ID_MODELO_REQ "
		sql = sql + " ,BD_AR.ID_PRODUCTO "
		sql = sql + " ,BD_AR.ID_TIPO_SERVICIO "
		sql = sql + " ,BD_AR.ID_TIPO_PRECIO "
		sql = sql + " ,BD_AR.ID_MONEDA "
		sql = sql + " ,BD_AR.ID_TIPO_COBRO "
		sql = sql + " ,BD_AR.IS_COBRABLE "
		sql = sql + " ,BD_AR.HORAS_ATENCION"
		sql = sql + " ,BD_AR.CAJA"

		'' FIELD SIM \( '.'   )
		sql = sql + " ,BD_AR.NO_SIM"

		sql = sql + " ,C_MONEDAS.DESC_MONEDA"
		sql = sql + " ,ISNULL(dbo.FUNC_GET_PRECIO_EXITO(BD_AR.ID_AR), 0) AS PRECIO_EXITO"
		sql = sql + " ,dbo.FUNC_GET_PRECIO_EXITO(BD_AR.ID_AR)*ISNULL(C_CLIENTES.POR_RECHAZO,0) AS PRECIO_RECHAZO"
		sql = sql + " ,CONVERT(VARCHAR,BD_AR.FEC_INICIO,103) AS FEC_INICIO"
		sql = sql + " ,CONVERT(VARCHAR,GETDATE(),103) AS FEC_ACTUAL"
		sql = sql + " ,DESC_TIPO_COBRO"
		sql = sql + " ,DESC_TIPO_PRECIO"
		sql = sql + " ,BD_AR.FEC_LLEGADA"
		sql = sql + " ,BD_AR.FEC_LLEGADA_TERCEROS"
		sql = sql + " ,BD_AR.FOLIO_SERVICIO"
		sql = sql + " ,BD_AR.FEC_INI_INGENIERO"
		sql = sql + " ,BD_AR.FEC_FIN_INGENIERO"
		sql = sql + " ,BD_AR.OTORGANTE_VOBO"
		sql = sql + " ,BD_AR.OTORGANTE_VOBO_TERCEROS"
		sql = sql + " ,BD_AR.INTENSIDAD_SENIAL"
		sql = sql + " ,BD_AR.IS_SIM_REMPLAZADA"
		sql = sql + " ,BD_AR.DIGITO_VERIFICADOR"
		sql = sql + " ,ISNULL(BD_AR.ID_TIPO_FALLA_ENCONTRADA, -1) AS ID_TIPO_FALLA_ENCONTRADA "
		sql = sql + " ,BD_AR.FALLA_ENCONTRADA"
		sql = sql + " ,BD_AR.OTORGANTE_VOBO_CLIENTE"
		sql = sql + " ,BD_AR.MOTIVO_COBRO"
		sql = sql + " ,BD_AR.IS_SOPORTE_CLIENTE"
		sql = sql + " ,BD_AR.IS_BOLETIN"
		sql = sql + " ,BD_AR.OTORGANTE_SOPORTE_CLIENTE"
		sql = sql + " ,BD_AR.CADENA_CIERRE_ESCRITA"
		sql = sql + " ,C_CLIENTES.IS_UPTIME"
		sql = sql + " ,dbo.FUNC_GET_MINS_DOWNTIME(BD_AR.ID_AR) AS MINS_DOWNTIME"
		sql = sql + " ,A.DESC_HORARIO AS DESC_HORARIO_UPTIME"
		sql = sql + " ,B.DESC_HORARIO AS DESC_HORARIO_ACCESO"
		sql = sql + " ,dbo.SP_GET_IS_CONTRACT(BD_NEGOCIOS.ID_NEGOCIO,MONTH(GETDATE()),YEAR(GETDATE())) AS IS_CONTRACT"
		sql = sql + " ,ISNULL(C_CLIENTES.DOC_TIR, '') AS DOC_TIR"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.INSTALACION_LLAVES, '') AS INSTALACION_LLAVES"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.TIPO_LLAVE, '') AS TIPO_LLAVE"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.STATUS_INSTALACION_LLAVES, '') AS STATUS_INSTALACION_LLAVES"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.NOMBRE_PERSONA_LLAVES_A, '') AS NOMBRE_PERSONA_LLAVES_A"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.NOMBRE_PERSONA_LLAVES_B, '') AS NOMBRE_PERSONA_LLAVES_B"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.TIPO_PW, '') AS TIPO_PW"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.TIPO_TECLADO, '') AS TIPO_TECLADO"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.VERSION_TECLADO_EPP, '') AS VERSION_TECLADO_EPP"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.PROCESADOR, '') AS PROCESADOR"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.VELOCIDAD_PROCESADOR, '') AS VELOCIDAD_PROCESADOR"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.MEMORIA, '') AS MEMORIA"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.CAPACIDAD_DISCO_DURO, '') AS CAPACIDAD_DISCO_DURO"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.MONITOR, '') AS MONITOR"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.LECTOR_TARJETA, '') AS LECTOR_TARJETA"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.APLICACION, '') AS APLICACION"
		sql = sql + " ,ISNULL(BD_INFORMACION_UNIDAD_AR.VERSION, '') AS VERSION"
		sql = sql + " ,ISNULL(BD_AR.VOLTAJE_NEUTRO, '') AS VOLTAJE_NEUTRO"
		sql = sql + " ,ISNULL(BD_AR.VOLTAJE_TIERRA, '') AS VOLTAJE_TIERRA"
		sql = sql + " ,ISNULL(BD_AR.VOLTAJE_TIERRA_NEUTRO, '') AS VOLTAJE_TIERRA_NEUTRO"
		sql = sql + " ,ISNULL(BD_AR.ID_ESPECIF_TIPO_FALLA, '') AS ID_ESPECIF_TIPO_FALLA"
		sql = sql + " ,ISNULL(BD_AR.FOLIO_VALIDACION, '') AS FOLIO_VALIDACION "
		sql = sql + " ,ISNULL(BD_AR.FOLIO_TIR, '') AS FOLIO_TIR "
		sql = sql + " FROM BD_AR"
		sql = sql + " INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
		sql = sql + " INNER JOIN C_MONEDAS ON BD_AR.ID_MONEDA = C_MONEDAS.ID_MONEDA"
		sql = sql + " INNER JOIN C_TIPO_PRECIO ON BD_AR.ID_TIPO_PRECIO = C_TIPO_PRECIO.ID_TIPO_PRECIO"
		sql = sql + " INNER JOIN BD_TIPO_SERVICIO_PRODUCTO ON BD_AR.ID_PRODUCTO = BD_TIPO_SERVICIO_PRODUCTO.ID_PRODUCTO AND BD_AR.ID_TIPO_SERVICIO = BD_TIPO_SERVICIO_PRODUCTO.ID_TIPO_SERVICIO"
		sql = sql + " INNER JOIN C_TIPO_COBRO ON BD_AR.ID_TIPO_COBRO = C_TIPO_COBRO.ID_TIPO_COBRO"
		sql = sql + " INNER JOIN BD_NEGOCIOS ON BD_AR.ID_NEGOCIO = BD_NEGOCIOS.ID_NEGOCIO"
		sql = sql + " LEFT JOIN C_HORARIOS A ON BD_NEGOCIOS.ID_HORARIO_UPTIME = A.ID_HORARIO"
		sql = sql + " LEFT JOIN C_HORARIOS B ON BD_NEGOCIOS.ID_HORARIO_ACCESO = B.ID_HORARIO"
		sql = sql + " LEFT JOIN BD_INFORMACION_UNIDAD_AR ON BD_AR.ID_AR = BD_INFORMACION_UNIDAD_AR.ID_AR"
		sql = sql + " WHERE BD_AR.ID_AR = " & idAR & "; "
		set rs = con.executeQuery(sql)


		if not rs.eof then
			''FIELD SIM \( '.'   )
			noSIM = rs.fields("NO_SIM")

			isValidCierre = rs.fields("IS_VALID_CIERRE")
			idServicio = rs.fields("ID_SERVICIO")
			idCliente = rs.fields("ID_CLIENTE")
			isWincor = cInt(rs.fields("isWincor"))
			idCausa = rs.fields("ID_CAUSA")
			idSolucion = rs.fields("ID_SOLUCION")
			noEquipo = rs.fields("NO_EQUIPO")
			descripcionTrabajo = rs.fields("DESCRIPCION_TRABAJO")
			atiende = rs.fields("ATIENDE")
			folioTas = rs.fields("FOLIO_TAS")
			codigoIntervencion = rs.fields("CODIGO_INTERVENCION")
			noSerieFalla = rs.fields("NO_SERIE_FALLA")
			noInventarioFalla = rs.fields("NO_INVENTARIO_FALLA")
			idModeloFalla = rs.fields("ID_MODELO_FALLA")
			isActualizacion = rs.fields("IS_ACTUALIZACION")
			isInstalacion = rs.fields("IS_INSTALACION")
			isSustitucion = rs.fields("IS_SUSTITUCION")
			isRetiro = rs.fields("IS_RETIRO")
			fecInicio = rs.fields("FEC_INICIO")
			fecActual = rs.fields("FEC_ACTUAL")
			idProyecto = rs.fields("ID_PROYECTO")
			idModeloReq = rs.fields("ID_MODELO_REQ")
			idProducto = rs.fields("ID_PRODUCTO")
			idTipoServicio = rs.fields("ID_TIPO_SERVICIO")
			idTipoPrecio = rs.fields("ID_TIPO_PRECIO")
			idMoneda = rs.fields("ID_MONEDA")
			idTipoCobro = rs.fields("ID_TIPO_COBRO")
			isCobrable = rs.fields("IS_COBRABLE")
			descMoneda = rs.fields("DESC_MONEDA")
			horasAtencion = rs.fields("HORAS_ATENCION")
			precioExito = rs.fields("PRECIO_EXITO")
			precioRechazo = rs.fields("PRECIO_RECHAZO")
			descTipoCobro = rs.fields("DESC_TIPO_COBRO")
			descTipoPrecio = rs.fields("DESC_TIPO_PRECIO")
			fecLlegada = rs.fields("FEC_LLEGADA")
			fecLlegadaTerceros = rs.fields("FEC_LLEGADA_TERCEROS")
			folioServicio = rs.fields("FOLIO_SERVICIO")
			fecIniIngeniero = rs.fields("FEC_INI_INGENIERO")
			fecFinIngeniero = rs.fields("FEC_FIN_INGENIERO")
			otorganteVoBo = rs.fields("OTORGANTE_VOBO")
			otorganteVoBoTerceros = rs.fields("OTORGANTE_VOBO_TERCEROS")
			intensidadSenial = rs.fields("INTENSIDAD_SENIAL")
			idSIMRemplazada = rs.fields("IS_SIM_REMPLAZADA")
	'''' +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ MODIFICACION DIGITO_VERIFICADOR ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			digitoVerificador = rs.fields("DIGITO_VERIFICADOR")
			idTipoFallaEncontrada = rs.fields("ID_TIPO_FALLA_ENCONTRADA")
			fallaEncontrada = rs.fields("FALLA_ENCONTRADA")
			otorganteVoBoCliente = rs.fields("OTORGANTE_VOBO_CLIENTE")
			motivoCobro = rs.fields("MOTIVO_COBRO")
			isSoporteCliente = rs.fields("IS_SOPORTE_CLIENTE")
			isBoletin = rs.fields("IS_BOLETIN")
			otorganteSoporteCliente = rs.fields("OTORGANTE_SOPORTE_CLIENTE")
			cadenaCierreEscrita = rs.fields("CADENA_CIERRE_ESCRITA")
			isUptime = rs.fields("IS_UPTIME")
			minsDowntime = rs.fields("MINS_DOWNTIME")
			descHorarioUptime = rs.fields("DESC_HORARIO_UPTIME")
			descHorarioAcceso = rs.fields("DESC_HORARIO_ACCESO")
			isContract = rs.fields("IS_CONTRACT")
			docTIR = rs.fields("DOC_TIR")

			instalacionLlaves = rs.fields("INSTALACION_LLAVES")
	'''' +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ MODIFICACION TIPO_LLAVE ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			tipoLlave = rs.fields("TIPO_LLAVE")
			statusInstalacionLlaves = rs.fields("STATUS_INSTALACION_LLAVES")
			nombrePersonaLlavesA = rs.fields("NOMBRE_PERSONA_LLAVES_A")
			nombrePersonaLlavesB = rs.fields("NOMBRE_PERSONA_LLAVES_B")
			tipoPw = rs.fields("TIPO_PW")
			tisTipoTeclado = rs.fields("TIPO_TECLADO")
			versionTecladoEPP = rs.fields("VERSION_TECLADO_EPP")
			procesador = rs.fields("PROCESADOR")
			velocidadProcesador = rs.fields("VELOCIDAD_PROCESADOR")
			memoria = rs.fields("MEMORIA")
			capacidadDiscoDuro = rs.fields("CAPACIDAD_DISCO_DURO")
			monitor = rs.fields("MONITOR")
			lectorTarjeta = rs.fields("LECTOR_TARJETA")
			aplicacion_santander = rs.fields("APLICACION")
			version_santander = rs.fields("VERSION")
			caja = rs.fields("CAJA")
			voltajeNeutro = rs.fields("VOLTAJE_NEUTRO")
			voltajeTierra = rs.fields("VOLTAJE_TIERRA")
			voltajeTierraNeutro = rs.fields("VOLTAJE_TIERRA_NEUTRO")
			idEspecifiqueTipoFalla = rs.fields("ID_ESPECIF_TIPO_FALLA")
			folioValidacion = rs.fields("FOLIO_VALIDACION")
			folioTIR = rs.fields("FOLIO_TIR")

		end if

		rs.close
		set rs = nothing

		if isValidCierre <> "1" then
			response.write("<r res=""OK"" val=""INVALIDO"" desc=""No es valido el cierre como exito"" />")
		else
			'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

			sql = "SELECT *"
			sql = sql + " FROM C_SERVICIOS"
			sql = sql + " WHERE C_SERVICIOS.ID_SERVICIO = " & idServicio
			set rs = con.executeQuery(sql)

			if not rs.eof then
				'' FIELD SIM REQUIRED \( '.'   )
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
				lengthCodigoIntervencion = rs.fields("LENGTH_CODIGO_INTERVENCION")
		'''' +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ MODIFICACION DIGITO_VERIFICADOR ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				isDigitoVerificadorRequired = rs.fields("IS_DV_REQUIRED")
				isIdTipoFallaEncontradaRequired = rs.fields("IS_ID_TIPO_FALLA_ENCONTRADA_REQUIRED")

				isInstalacionLlavesRequired = rs.fields("IS_INSTALACION_LLAVES_REQUIRED")
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

				isMultipleTask = rs.fields("IS_MULTIPLE_TASK")

				IsLecturaVoltajeRequired = rs.fields("IS_LECTURA_VOLTAJE_REQUIRED")
				IsEspecificaTipoFalla = rs.fields("IS_ESPECIFICA_TIPO_FALLA")
				IsFolioValidacionRequired = rs.fields("IS_FOLIO_VALIDACION_REQUIRED")
				IsFolioTIRRequired = rs.fields("IS_FOLIO_TIR_REQUIRED")
				
				isDescTrabajoRequired = rs.fields("IS_DESC_TRABAJO_REQUIRED")
				isDescTrabajoCatalogRequired = rs.fields("IS_DESC_TRABAJO_CATALOGO_REQUIRED")

				response.write("<r res=""OK"" val=""SI"" desc=""Todo bien"" />")

				response.write("<datae A=""" & idAR & """" )
				response.write(" B=""" & idServicio & """")
				response.write(" C=""" & idCliente & """")
				response.write(" D=""" & isWincor & """")
				response.write(" E=""" & idCausa & """")
				response.write(" F=""" & idSolucion & """")
				response.write(" G=""" & noEquipo & """")
				response.write(" H=""" & descripcionTrabajo & """")
				response.write(" I=""" & atiende & """")
				response.write(" J=""" & folioTas & """")
				response.write(" K=""" & codigoIntervencion & """")
				response.write(" L=""" & noSerieFalla & """")
				response.write(" M=""" & noInventarioFalla & """")
				response.write(" N=""" & idModeloFalla & """")
				response.write(" O=""" & isActualizacion & """")
				response.write(" P=""" & isInstalacion & """")
				response.write(" Q=""" & isSustitucion & """")
				response.write(" R=""" & isRetiro & """")
				response.write(" S=""" & fecInicio & """")
				response.write(" T=""" & fecActual & """")
				response.write(" U=""" & idProyecto & """")
				response.write(" V=""" & idModeloReq & """")
				response.write(" W=""" & idProducto & """")
				response.write(" X=""" & idTipoServicio & """")
				response.write(" Y=""" & idTipoPrecio & """")
				response.write(" Z=""" & idMoneda & """")
				response.write(" AA=""" & idTipoCobro & """")
				response.write(" AB=""" & isCobrable & """")
				response.write(" AC=""" & descMoneda & """")
				response.write(" AD=""" & horasAtencion & """")
				response.write(" AE=""" & precioExito & """")
				response.write(" AF=""" & precioRechazo & """")
				response.write(" AG=""" & descTipoCobro & """")
				response.write(" AH=""" & descTipoPrecio & """")
				response.write(" AI=""" & fecLlegada & """")
				response.write(" AJ=""" & fecLlegadaTerceros & """")
				response.write(" AK=""" & folioServicio & """")
				response.write(" AL=""" & fecIniIngeniero & """")
				response.write(" AM=""" & fecFinIngeniero & """")
				response.write(" AN=""" & otorganteVoBo & """")
				response.write(" AO=""" & otorganteVoBoTerceros & """")
				response.write(" AP=""" & intensidadSenial & """")
				response.write(" AQ=""" & idSIMRemplazada & """")
				response.write(" AR=""" & digitoVerificador & """")
				response.write(" AS=""" & idTipoFallaEncontrada & """")
				response.write(" AT=""" & fallaEncontrada & """")
				response.write(" AU=""" & otorganteVoBoCliente & """")
				response.write(" AV=""" & motivoCobro & """")
				response.write(" AW=""" & isSoporteCliente & """")
				response.write(" AX=""" & isBoletin & """")
				response.write(" AY=""" & otorganteSoporteCliente & """")
				response.write(" AZ=""" & cadenaCierreEscrita & """")
				response.write(" BA=""" & isUptime & """")
				response.write(" BB=""" & minsDowntime & """")
				response.write(" BC=""" & descHorarioUptime & """")
				response.write(" BD=""" & descHorarioAcceso & """")
				response.write(" BE=""" & isContract & """")
				response.write(" BF=""" & docTIR & """")
				response.write(" BG=""" & instalacionLlaves & """")
				response.write(" BH=""" & tipoLlave & """")
				response.write(" BI=""" & statusInstalacionLlaves & """")
				response.write(" BJ=""" & nombrePersonaLlavesA & """")
				response.write(" BK=""" & nombrePersonaLlavesB & """")
				response.write(" BL=""" & tipoPw & """")
				response.write(" BM=""" & tisTipoTeclado & """")
				response.write(" BN=""" & versionTecladoEPP & """")
				response.write(" BO=""" & procesador & """")
				response.write(" BP=""" & velocidadProcesador & """")
				response.write(" BQ=""" & memoria & """")
				response.write(" BR=""" & capacidadDiscoDuro & """")
				response.write(" BS=""" & monitor & """")
				response.write(" BT=""" & lectorTarjeta & """")
				response.write(" BU=""" & aplicacion_santander & """")
				response.write(" BV=""" & version_santander & """")
				response.write(" BW=""" & caja & """")
				response.write(" BX=""" & voltajeNeutro & """")
				response.write(" BY=""" & voltajeTierra & """")
				response.write(" BZ=""" & voltajeTierraNeutro & """")
				response.write(" CA=""" & idEspecifiqueTipoFalla & """")
				response.write(" CB=""" & folioValidacion & """")
				response.write(" CC=""" & folioTIR & """")

				response.write(" CD=""" & isCausaSolucionRequired & """")
				response.write(" CE=""" & isTASRequired & """")
				response.write(" CF=""" & isOtorganteTASRequired & """")
				response.write(" CG=""" & isNoEquipoRequired & """")
				response.write(" CH=""" & isNoSerieRequired & """")
				response.write(" CI=""" & isNoInventarioRequired & """")
				response.write(" CJ=""" & isIdModeloRequired & """")
				response.write(" CK=""" & isFecLlegadaRequired & """")
				response.write(" CL=""" & isFecLlegadaTercerosRequired & """")
				response.write(" CM=""" & isFolioServicioRequired & """")
				response.write(" CN=""" & isFecIniIngenieroRequired & """")
				response.write(" CO=""" & isFecFinIngenieroRequired & """")
				response.write(" CP=""" & isOtorganteVoBoRequired & """")
				response.write(" CQ=""" & isOtorganteVoBoTercerosRequired & """")
				response.write(" CR=""" & isIntensidadSenialRequired & """")
				response.write(" CS=""" & isIsSIMRemplazadaRequired & """")
				response.write(" CT=""" & isFallaEncontradaRequired & """")
				response.write(" CU=""" & isOtorganteVoBoClienteRequired & """")
				response.write(" CV=""" & isMotivoCobroRequired & """")
				response.write(" CW=""" & isIsSoporteClienteRequired & """")
				response.write(" CX=""" & isIsBoletinRequired & """")
				response.write(" CY=""" & isOtorganteSoporteClienteRequired & """")
				response.write(" CZ=""" & isCadenaCierreEscritaRequired & """")
				response.write(" DA=""" & isCodigoIntervencionRequired & """")
				response.write(" DB=""" & lengthCodigoIntervencion & """")
				response.write(" DC=""" & isDigitoVerificadorRequired & """")
				response.write(" DD=""" & isIdTipoFallaEncontradaRequired & """")
				response.write(" DE=""" & isInstalacionLlavesRequired & """")
				response.write(" DF=""" & isTipoLlaveRequired & """")
				response.write(" DG=""" & isStatusInstalacionLlavesRequired & """")
				response.write(" DH=""" & isNombrePersonaLlavesARequired & """")
				response.write(" DI=""" & isNombrePersonaLlavesBRequired & """")
				response.write(" DJ=""" & isTipoPwRequired & """")
				response.write(" DK=""" & isTipoTecladoRequired & """")
				response.write(" DL=""" & isVersionTecladoEPPRequired & """")
				response.write(" DM=""" & isProcesadorRequired & """")
				response.write(" DN=""" & isVelocidadProcesadorRequired & """")
				response.write(" DO=""" & isMemoriaRequired & """")
				response.write(" DP=""" & isCapacidadDiscoDuroRequired & """")
				response.write(" DQ=""" & isMonitorRequired & """")
				response.write(" DR=""" & isLectorTarjetaRequired & """")
				response.write(" DS=""" & isAplicacionRequired & """")
				response.write(" DT=""" & isVersionRequired & """")
				response.write(" DU=""" & isCajaRequired & """")
				response.write(" DV=""" & isMultipleTask & """")
				response.write(" DW=""" & IsLecturaVoltajeRequired & """")
				response.write(" DX=""" & IsEspecificaTipoFalla & """")
				response.write(" DY=""" & IsFolioValidacionRequired & """")
				response.write(" DZ=""" & IsFolioTIRRequired & """")
				response.write(" EA=""" & isDescTrabajoRequired & """")
				response.write(" EB=""" & isDescTrabajoCatalogRequired & """")
				'' ADD SIM <-_( '.'   )
				response.write(" EC=""" & noSIM & """")
				response.write(" ED=""" & isSIMRequired & """")
				response.write(" />")



			end if
		end if
	end if
else
	response.write("<r res=""ERROR"" desc=""No se recibieron bien los parametros del url"" />")
end if

response.write("</d>")
con.endConnection
%>