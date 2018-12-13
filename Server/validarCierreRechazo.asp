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



response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")


if idAR <> -1 then
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

	if rs.fields("IS_VALID_RECHAZO") = 1 then
		response.write("<r res=""OK"" val=""INSTALACION"" desc=""No es valido el cierre como rechazo"" />")
	elseif rs.fields("IS_VALID_RECHAZO") = 2 then
		response.write("<r res=""OK"" val=""SUSTITUCION"" desc=""No es valido el cierre como rechazo"" />")
	elseif rs.fields("IS_VALID_RECHAZO") = 3 then
		response.write("<r res=""OK"" val=""RETIRO"" desc=""No es valido el cierre como rechazo"" />")
	elseif rs.fields("IS_VALID_RECHAZO") = 4 then
		response.write("<r res=""OK"" val=""INSUMO"" desc=""No es valido el cierre como rechazo"" />")
	elseif rs.fields("IS_ABIERTA_FREEZE") = 1 then
		response.write("<r res=""OK"" val=""FREEZE"" desc=""No es valido el cierre como rechazo"" />")
	elseif rs.fields("IS_ABIERTA_FREEZE") <> 1 and rs.fields("IS_VALID_RECHAZO") = 0 then

		

		ID_USUARIO = rs.fields("ID_TECNICO")

		'sql = "EXEC SP_VERIFY_VISITA " & idAR & "," & ID_USUARIO
		'con.executeQuery(sql)

		'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

		sql = "SELECT dbo.FUNC_VALIDATE_RECHAZO(" & idAR & ") AS IS_VALID_RECHAZO"
		sql = sql + " ,BD_AR.ID_SERVICIO"
		sql = sql + " ,BD_AR.ID_CLIENTE"
		sql = sql + " ,BD_AR.ID_CAUSA"
		sql = sql + " ,BD_AR.ID_SOLUCION"
		sql = sql + " ,dbo.FUNC_GET_NO_EQUIPO(BD_AR.ID_AR) AS NO_EQUIPO"
		sql = sql + " ,BD_AR.DESCRIPCION_TRABAJO "
		sql = sql + " ,BD_AR.ATIENDE"
		sql = sql + " ,BD_AR.FOLIO_TAS "
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

		'' FIELD CLAVE_RECHAZO \( '.'   )
		sql = sql + " ,BD_AR.CLAVE_RECHAZO"

		sql = sql + " ,BD_AR.ID_MONEDA "
		sql = sql + " ,BD_AR.ID_TIPO_COBRO "
		sql = sql + " ,BD_AR.IS_COBRABLE "
		sql = sql + " ,BD_AR.HORAS_ATENCION"
		sql = sql + " ,C_MONEDAS.DESC_MONEDA"
		sql = sql + " ,dbo.FUNC_GET_PRECIO_EXITO(BD_AR.ID_AR) AS PRECIO_EXITO"
		sql = sql + " ,dbo.FUNC_GET_PRECIO_EXITO(BD_AR.ID_AR)*ISNULL(C_CLIENTES.POR_RECHAZO/100,0) AS PRECIO_RECHAZO"
		sql = sql + " ,CONVERT(VARCHAR,BD_AR.FEC_INICIO,103) AS FEC_INICIO"
		sql = sql + " ,CONVERT(VARCHAR,GETDATE(),103) AS FEC_ACTUAL"
		sql = sql + " ,DESC_TIPO_COBRO"
		sql = sql + " ,	DESC_TIPO_PRECIO"
		sql = sql + " FROM BD_AR"
		sql = sql + " INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
		sql = sql + " INNER JOIN C_MONEDAS ON BD_AR.ID_MONEDA = C_MONEDAS.ID_MONEDA"
		sql = sql + " INNER JOIN C_TIPO_PRECIO ON BD_AR.ID_TIPO_PRECIO = C_TIPO_PRECIO.ID_TIPO_PRECIO"
		sql = sql + " INNER JOIN BD_TIPO_SERVICIO_PRODUCTO ON BD_AR.ID_PRODUCTO = BD_TIPO_SERVICIO_PRODUCTO.ID_PRODUCTO AND BD_AR.ID_TIPO_SERVICIO = BD_TIPO_SERVICIO_PRODUCTO.ID_TIPO_SERVICIO"
		sql = sql + " INNER JOIN C_TIPO_COBRO ON BD_AR.ID_TIPO_COBRO = C_TIPO_COBRO.ID_TIPO_COBRO"
		sql = sql + " WHERE BD_AR.ID_AR = " & idAR
		set rs = con.executeQuery(sql)

		if not rs.eof then

			''FIELD CLAVE_RECHAZO \( '.'   )
			claveRechazo = rs.fields("CLAVE_RECHAZO")

			isValidRechazo = rs.fields("IS_VALID_RECHAZO")
			idServicio = rs.fields("ID_SERVICIO")
			idCliente = rs.fields("ID_CLIENTE")
			idCausa = rs.fields("ID_CAUSA")
			idSolucion = rs.fields("ID_SOLUCION")
			noEquipo = rs.fields("NO_EQUIPO")
			descripcionTrabajo = rs.fields("DESCRIPCION_TRABAJO")
			atiende = rs.fields("ATIENDE")
			folioTas = rs.fields("FOLIO_TAS")
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
			fecInicio = rs.fields("FEC_INICIO")

		end if

		rs.close
		set rs = nothing

		if isValidRechazo <> "1" then
			response.write("<r res=""OK"" val=""INVALIDO"" desc=""No es valido el cierre como rechazo"" />")
		else
			'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

			sql = "SELECT *"
			sql = sql + " FROM C_SERVICIOS"
			sql = sql + " WHERE C_SERVICIOS.ID_SERVICIO = " & idServicio
			set rs = con.executeQuery(sql)

			if not rs.eof then
				'' FIELD CLAVE_REACHAZO REQUIRED \( '.'   )
				isClaveRechazoRequired = rs.fields("needClaveRechazo")

				isCausaSolucionRequired = rs.fields("IS_CAUSA_SOLUCION_REQUIRED")
				isCausaRequired = rs.fields("IS_CAUSA_REQUIRED")
				isSolucionRequired = rs.fields("IS_SOLUCION_REQUIRED")
				isFolioServicioRechazoRequired = rs.fields("IS_FOLIO_SERVICIO_RECHAZO_REQUIRED")
				isOtorganteVoBoRechazoRequired = rs.fields("IS_OTORGANTE_VOBO_RECHAZO_REQUIRED")
				isDescripcionTrabajoRechazoRequired = rs.fields("IS_DESCRIPCION_TRABAJO_RECHAZO_REQUIRED")
				isIdCausaRechazoRequired = rs.fields("IS_ID_CAUSA_RECHAZO_REQUIRED")
				IsEspecificaTipoFalla = rs.fields("IS_ESPECIFICA_CAUSA_RECHAZO_REQUIRED")

				response.write("<r res=""OK"" val=""SI"" desc=""Todo bien"" />")

				response.write("<datar A=""" & idAR & """" )
				response.write(" B=""" & idServicio & """")
				response.write(" C=""" & idCliente & """")
				response.write(" D=""" & descMoneda & """")
				response.write(" E=""" & descTipoCobro & """")
				response.write(" F=""" & descTipoPrecio & """")
				response.write(" G=""" & descripcionTrabajo & """")
				response.write(" H=""" & horasAtencion & """")
				response.write(" I=""" & idTipoCobro & """")
				response.write(" J=""" & idTipoPrecio & """")
				response.write(" K=""" & isCobrable & """")
				response.write(" L=""" & precioRechazo & """")
				response.write(" M=""" & isCausaSolucionRequired & """")
				response.write(" N=""" & isCausaRequired & """")
				response.write(" O=""" & isSolucionRequired & """")
				response.write(" P=""" & isFolioServicioRechazoRequired & """")
				response.write(" Q=""" & isOtorganteVoBoRechazoRequired & """")
				response.write(" R=""" & isDescripcionTrabajoRechazoRequired & """")
				response.write(" S=""" & isIdTipoFallaEncontradaRequired & """")
				response.write(" T=""" & IsEspecificaTipoFalla & """")
				response.write(" U=""" & isIdCausaRechazoRequired & """")
				response.write(" V=""" & otorganteVoBo & """")
				response.write(" W=""" & fecInicio & """")
				'' ADD CLAVE RECHAZO \( '.'   )
				response.write(" X=""" & claveRechazo & """")
				response.write(" Y=""" & isClaveRechazoRequired & """")
				response.write(" />")



			end if
		end if
	end if
else
	response.write("<r res=""ERROR"" desc=""No se recibieron bien los parametros del url"" />")
end if

response.write("</d>")
'con.endConnection
%>