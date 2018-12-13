<!--#include file = "../connections/constants.asp"-->
<!--#include file = "../connections/cConnection.asp"-->
<%
response.Charset = "iso-8859-1"
response.ContentType = "text/html"%>
<html><head><title>SQL TEST - PRODUCCION</title></head><body>
<%
''Server.ScriptTimeout = 3600

set con = new cConnection
con.startConnection(DB_INI)

'' === GET_UPDATES ===
''sql = "SELECT BD_AR.ID_AR, BD_AR.ID_STATUS_AR, C_STATUS_AR.IS_BB_NUEVAS, C_STATUS_AR.IS_BB_CERRADAS, "
''sql = sql + "C_STATUS_AR.IS_BB_ABIERTAS, C_STATUS_AR.IS_BB_PENDIENTES , BD_AR.ID_STATUS_VALIDACION_PREFACTURACION FROM BD_AR "
''sql = sql + "INNER JOIN C_STATUS_AR ON BD_AR.ID_STATUS_AR = C_STATUS_AR.ID_STATUS_AR "
''sql = sql + "WHERE BD_AR.ID_TECNICO = 190"

'' ==== GET_SOLICITUDES ===
''sql = "SELECT"
''sql = sql + " BD_AR.ID_AR, BD_AR.ID_STATUS_AR"
''sql = sql + " FROM BD_AR"
''sql = sql + " INNER JOIN C_STATUS_AR ON BD_AR.ID_STATUS_AR  =  C_STATUS_AR.ID_STATUS_AR"
''sql = sql  + " WHERE BD_AR.ID_TECNICO = 190 AND C_STATUS_AR.IS_BB_NUEVAS = 1"

'' === GET _SOLICITUD ===
'' sql = "SELECT"
'' sql = sql + " BD_AR.ID_AR, "
'' sql = sql + " BD_AR.NO_AR, "
'' sql = sql + " BD_AR.SEGMENTO, "
'' sql = sql + " C_SEGMENTOS.IS_KEY_ACCOUNT, "
'' sql = sql + " BD_AR.HORAS_GARANTIA, "
'' sql = sql + " BD_AR.HORAS_ATENCION, "
'' sql = sql + " BD_AR.ID_CLIENTE, "
'' sql = sql + " C_CLIENTES.DESC_CLIENTE, "
'' sql = sql + " C_CLIENTES.IS_WINCOR, "
'' sql = sql + " BD_AR.NO_AFILIACION, "
'' sql = sql + " BD_AR.FEC_ALTA, "
'' sql = sql + " BD_AR.FEC_GARANTIA, "
'' sql = sql + " BD_AR.ID_SERVICIO, "
'' sql = sql + " C_SERVICIOS.DESC_SERVICIO, "
'' sql = sql + " BD_AR.SINTOMA, "
'' sql = sql + " BD_AR.CONCEPTO, "
'' sql = sql + " BD_AR.DESC_CORTA, "
'' sql = sql + " BD_AR.BITACORA, "
'' sql = sql + " BD_AR.NOTAS_REMEDY, "
'' sql = sql + " BD_AR.DESC_EQUIPO, "
'' sql = sql + " BD_AR.EQUIPO, "
'' sql = sql + " BD_AR.NO_SERIE, "
'' sql = sql + " BD_AR.DIRECCION, "
'' sql = sql + " BD_AR.COLONIA, "
'' sql = sql + " BD_AR.POBLACION, "
'' sql = sql + " BD_AR.ESTADO, "
'' sql = sql + " BD_AR.CP, "
'' sql = sql + " BD_AR.DESC_NEGOCIO, "
'' sql = sql + " BD_AR.ID_NEGOCIO, "
'' sql = sql + " BD_AR.TELEFONO, "
'' sql = sql + " BD_AR.ID_STATUS_AR, "
'' sql = sql + " BD_AR.ID_PRODUCTO, "
'' sql = sql + " BD_AR.ID_UNIDAD_ATENDIDA, "
'' sql = sql + " BD_AR.FEC_ATENCION, "
'' sql = sql + " BD_AR.CAJA, "
'' sql = sql + " BD_AR.FEC_CIERRE,"
'' sql = sql + " BD_AR.ID_STATUS_VALIDACION_PREFACTURACION,"
'' sql = sql + " BD_BITACORA_VALIDACION_PREFACTURACION.FEC_ALTA,"
'' sql = sql + " BD_BITACORA_VALIDACION_PREFACTURACION.COMENTARIO"

'' sql = sql + " FROM BD_AR "
'' sql = sql + " INNER JOIN C_SEGMENTOS ON BD_AR.ID_SEGMENTO  =  C_SEGMENTOS.ID_SEGMENTO "
'' sql = sql + " INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE  =  C_CLIENTES.ID_CLIENTE "
'' sql = sql + " INNER JOIN C_SERVICIOS ON BD_AR.ID_SERVICIO  =  C_SERVICIOS.ID_SERVICIO"
'' sql = sql + " INNER JOIN C_STATUS_AR ON C_STATUS_AR.ID_STATUS_AR  =  BD_AR.ID_STATUS_AR"
'' sql = sql + " LEFT JOIN BD_BITACORA_VALIDACION_PREFACTURACION ON BD_AR.ID_AR  =  BD_BITACORA_VALIDACION_PREFACTURACION.ID_AR"
'' sql = sql + " WHERE BD_AR.ID_AR = 220103"
'' sql = sql + " ORDER BY BD_BITACORA_VALIDACION_PREFACTURACION.FEC_ALTA DESC"

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

	sql = ""
	sql= sql + "SELECT "
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

	set rs  =  con.executeQuery(sql)


'' === UNIDADES ===
''sql = "SELECT ID_UNIDAD "
''sql = sql + "FROM BD_UNIDADES WHERE BD_UNIDADES.STATUS = 'ACTIVO'"

'' === STATUS ===
''sql = 	"SELECT CASE WHEN (SELECT ISNULL(COUNT(ID_UNIDAD),0)" &_
''			 "                 FROM BD_UNIDADES" &_
''			 "                 WHERE BD_UNIDADES.STATUS = 'ACTIVO'" &_						 
''			 "                 AND NO_SERIE	= '" & replace(noSerie," ","") & "') = 0" &_
''			 "           THEN 'OK'" &_
''			 "           ELSE 'ERROR'" &_
''			 "       END AS VALIDACION_NO_SERIE"

%>
<h1>GET_SOLICITUD</h1>
<%if not rs.EOF then
	do until rs.EOF
		''num_n = 0
		''str_n = ""
		''if not rs.eof then
		''	Do until rs.EOF
		''		if rs.fields("IS_BB_NUEVAS") = 1 then
		''			num_n = num_n + 1
		''			str_n = str_n & rs.fields("ID_AR") & rs.fields("ID_STATUS_AR")
		''		end if

		''		rs.MoveNext
		''	Loop
		''end if

			isIdModeloRequired = rs.fields("IS_ID_MODELO_REQUIRED")	
			%><p><b>IS_MODELO_REQUIRED: </b> <% response.write(isIdModeloRequired) %></p><%
			%><p><b>IS_SUSTITUCION: </b> <% response.write(isSustitucion) %></p><%

			if isIdModeloRequired = "1" and isSustitucion = "0" then 			
				
				modelo =  request.QueryString("im")
				%><p><b>id_modelo_falla: </b> <% response.write(modelo) %></p><%
				if modelo <> "" then %>

					<p><b>ID_MODELO_FALLA: </b> <% response.write(modelo) %></p>
					
				<%end if
				
			end if

		%>

 		<!-- UNIDADES -->
 		<!--<p><b>ID. AR: </b> <% response.write(rs.fields("id_ar")) %></p>
 		<p><b>No. AR: </b> <% response.write(rs.fields("no_ar")) %></p>
 		<p><b>Fecha Alta: </b> <% response.write(rs.fields("fec_alta")) %></p>-->
	<%
	rs.MoveNext
	loop
else%>
	<p><b>No se encontraron resultados</b></p>
<%end if

response.end()

rs.close()
set rs = Nothing
con.endConnection

end if
%>
</body></html>