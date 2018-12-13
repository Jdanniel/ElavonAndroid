<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

set con = new cConnection
con.startConnection(DB_INI)

if request.QueryString("sk") <> "" then
	sku = request.QueryString("sk")
else
	sku = ""
end if

if request.QueryString("ia") <> "" then
	idAR = request.QueryString("ia")
else
	idAR = ""
end if

if request.QueryString("ns") <> "" then
	noSerie = request.QueryString("ns")
else
	noSerie = ""
end if

if request.QueryString("imar") <> "" then
	idMarca = request.QueryString("imar")
else
	idMarca = ""
end if

if request.QueryString("d") <> "" then
	isDaniada = request.QueryString("d")
else
	isDaniada = 0
end if

if request.QueryString("n") <> "" then
	isNueva = request.QueryString("n")
else
	isNueva = 0
end if

if request.QueryString("it") <> "" then
	idTecnico = request.QueryString("it")
else
	idTecnico = 0
end if

set con = new cConnection
con.startConnection(DB_INI)
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validación Número de Serie
sql = 	"SELECT CASE WHEN (SELECT ISNULL(COUNT(ID_UNIDAD),0)" &_
		 "                 FROM BD_UNIDADES" &_
		 "                 WHERE BD_UNIDADES.STATUS = 'ACTIVO'" &_						 
		 "                 AND NO_SERIE	= '" & noSerie & "') = 0" &_
		 "           THEN 'OK'" &_
		 "           ELSE 'ERROR'" &_
		 "       END AS VALIDACION_NO_SERIE"
set rs = con.executeQuery(sql)	
if not rs.eof then	
	dim validacionNoSerie
	validacionNoSerie = rs.fields("VALIDACION_NO_SERIE")	
end if
rs.close
   
response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d")

if validacionNoSerie = "OK" then
	sql = 	"SELECT ID_CLIENTE" &_
		 "   FROM BD_AR" &_
		 "   WHERE ID_AR = '" & idAR & "'"
		 
	set rs = con.executeQuery(sql)	
	if not rs.eof then	
		dim idCliente
		idCliente = rs.fields("ID_CLIENTE")	
	end if
	response.write("AR = " & sql)
	
	rs.close

	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validación Número de Inventario
	sql = 	"SELECT CASE WHEN (SELECT COUNT(ID_UNIDAD)" &_
			 "                 FROM BD_UNIDADES" &_
			 "                 WHERE ID_CLIENTE = " & idCliente &_
			 "                 AND BD_UNIDADES.STATUS = 'ACTIVO'" &_		 
			 "                 AND NO_INVENTARIO	= '" & Request.Form("NO_INVENTARIO") & "') = 0" &_
			 "                 OR '" & Request.Form("NO_INVENTARIO") & "' = ''" &_			 
			 "           THEN 'OK'" &_
			 "           ELSE 'ERROR'" &_
			 "       END AS VALIDACION_NO_INVENTARIO"
	set rs = con.executeQuery(sql)	
	if not rs.eof then
	
		dim validacionNoInventario
		validacionNoInventario = rs.fields("VALIDACION_NO_INVENTARIO")
	
	end if
	rs.close		
	
	if validacionNoInventario <> "OK" then
		response.write(" res=""ERROR"" desc=""El No. de Inventario ya ha sido ingresado al sistema.""/>")
		con.endConnection
		response.end()
	end if	
	
	'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validación SIM Ingresada
	sql = 	"SELECT CASE WHEN (SELECT IS_GPRS FROM C_MODELOS" &_ 
			"				   WHERE C_MODELOS.ID_MODELO = " & sku & ") = 1" &_
			"			 AND ('" & Request.Form("NO_SIM") & "' = '')" &_ 
			"            THEN 'ERROR'" &_
			"            ELSE 'OK'" &_
			"        END AS VALIDACION_SIM_INGRESADA"
	set rs = con.executeQuery(sql)	
	if not rs.eof then
	
		dim validacionSIMIngresada
		validacionSIMIngresada = rs.fields("VALIDACION_SIM_INGRESADA")
	
	end if
	rs.close		
	
	if validacionSIMIngresada <> "OK" then
		response.write(" res=""ERROR"" desc=""Es necesario proporcionar un numero de tarjeta SIM para ingresar este modelo.""/>")
		con.endConnection
		response.end()
	end if		
	'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validacion nomenclatura SIM
	if Request.Form("ID_CARRIER") <> "" then 
	
		Dim strAlpha 
		
		sql = 	"SELECT LETRA_MIN ,LETRA_MAX" &_ 
				"		,NUM_MIN ,NUM_MAX" &_
				"		,LONG_MIN ,LONG_MAX" &_
				"	FROM C_CARRIER " &_
				"	WHERE ID_CARRIER = " & Request.Form("ID_CARRIER")
		set rs = con.executeQuery(sql)	
		if not rs.eof then 
			Dim validaNomenclaturaSIM
			
			if cInt(rs.fields("NUM_MAX")) > 0 then
				if cInt(rs.fields("LETRA_MAX")) > 0 then
					strAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
				else
					strAlpha = "0123456789"
				end if
			end if
			
			if ValidaLongitud(Request.Form("NO_SIM"),rs.fields("LONG_MIN"),rs.fields("LONG_MAX")) <> "OK" then
				response.write(" res=""ERROR"" desc=""El numero de SIM ingresado tiene una longitud no permitida.""/>")
				con.endConnection
				response.end()
			else		
				if validaDigitos(Request.Form("NO_SIM"),rs.fields("NUM_MIN"),rs.fields("NUM_MAX")) <> "OK" then
					response.write(" res=""ERROR"" desc=""El numero DIGITOS ingresados es incorrecto.""/>")
					con.endConnection
					response.end()
				else	
					if validaLetras(Request.Form("NO_SIM"),rs.fields("LETRA_MIN"),rs.fields("LETRA_MAX")) <> "OK" then
						response.write(" res=""ERROR"" desc=""El numero LETRAS ingresados es incorrecto.""/>")
						con.endConnection
						response.end()
					else						
						if CarInvalido(Request.Form("NO_SIM"),strAlpha) then
							response.write(" res=""ERROR"" desc=""El numero de SIM ingresado contiene caracteres no validos.""/>")
							con.endConnection
							response.end()
						else
							validaNomenclaturaSIM = "OK"
						end if
					end if
				end if
			end if
		
		end if
		rs.close
	else
		validaNomenclaturaSIM = "OK"
	end if	
		
	'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validación SIM Duplicada
	sql = 	"SELECT CASE WHEN (SELECT COUNT(ID_SIM)" &_
			"				   FROM BD_SIMS" &_
			"				   WHERE BD_SIMS.NO_SIM = '" &	Request.Form("NO_SIM") & "') > 0" &_ 
			"            AND  (SELECT IS_GPRS FROM C_MODELOS" &_ 
			"				   WHERE C_MODELOS.ID_MODELO = " & sku & ") = 1" &_
			"            THEN 'ERROR'" &_
			"            ELSE 'OK'" &_
			"        END AS VALIDACION_SIM_INVENTARIO"
			
			'"				   WHERE BD_SIMS.ID_SIM = '" &	Request.Form("ID_SIM") & "') > 0"
	set rs = con.executeQuery(sql)	
	if not rs.eof then
	
		dim validacionSIMInventario
		validacionSIMInventario = rs.fields("VALIDACION_SIM_INVENTARIO")
	
	end if
	rs.close		
	
	if validacionSIMInventario <> "OK"  then
		response.write(" res=""ERROR"" desc=""El numero de tarjeta ya ha sido asignado a una Unidad.""/>")
		con.endConnection
		response.end()
	end if
	
	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
		
	
	sql = 	"SELECT ID_PRODUCTO FROM C_CLIENTES WHERE ID_CLIENTE = " & idCliente
	set rs = con.executeQuery(sql)	
	if not rs.eof then
	
		dim idProducto
		idProducto = rs.fields("ID_PRODUCTO")
	
	end if
	rs.close			
			''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	
	sql = 	"INSERT INTO BD_UNIDADES" &_
			" (ID_CLIENTE" &_
			" ,ID_SOLICITUD_RECOLECCION" &_
			" ,ID_PRODUCTO" &_	
			" ,IS_NUEVA" &_											
			" ,ID_MARCA" &_
			" ,ID_MODELO" &_
			" ,NO_SERIE" &_
			" ,NO_INVENTARIO" &_
			" ,ID_SIM" &_						
			" ,NO_IMEI" &_
			" ,NO_SIM" &_						
			" ,ID_TIPO_RESPONSABLE" &_						
			" ,ID_RESPONSABLE" &_
			" ,ID_LLAVE" &_
			" ,ID_SOFTWARE" &_
			" ,POSICION_INVENTARIO" &_											
			" ,IS_RETIRO" &_
			" ,IS_DANIADA" &_
			" ,COSTO" &_
			" ,ID_MONEDA" &_
			" ,ID_STATUS_UNIDAD" &_
			" ,IS_PROPIEDAD_CLIENTE " &_ 
			" ,STATUS" &_						
			" ,FEC_ALTA" &_
			" ,ID_USUARIO_ALTA)" &_
			" VALUES" &_
			" (" & idCliente &_
			" ," & Request.Form("ID_SOLICITUD_RECOLECCION") &_
			" ," & idProducto &_
			" ," & isNueva &_										
			" ," & Request.Form("ID_MARCA") &_
			" ," & sku &_	
			" ,NULLIF ('" & replace(Request.Form("NO_SERIE")," ","") & "','') " &_					
			" ,NULLIF ('" & replace(Request.Form("NO_INVENTARIO")," ","") & "','') " &_				
			" ,NULLIF ('" & Request.Form("ID_SIM") & "','') " &_																																				
			" ,NULLIF ('" & Request.Form("NO_IMEI") & "','') " &_
			" ,NULLIF ('" & Request.Form("NO_SIM") & "','') " &_						
			" ,NULLIF ('" & Request.Form("ID_TIPO_RESPONSABLE") & "','') " &_
			" ,NULLIF ('" & Request.Form("ID_RESPONSABLE") & "','') " &_
			" ,NULLIF ('" & Request.Form("ID_LLAVE") & "','') " &_
			" ,NULLIF ('" & Request.Form("ID_SOFTWARE") & "','') " &_
			" ,NULLIF ('" & Request.Form("POSICION_INVENTARIO") & "','') " &_	
			" ,NULLIF ('" & Request.Form("IS_RETIRO") & "','') " &_																
			" ," & isDaniada  &_
			" ," & costo & " " &_
			" ," & idMoneda & " " &_
			" ,NULLIF ('" & Request.Form("ID_STATUS_UNIDAD") & "','') " &_	
			" ," & isPropiedadCliente &_											
			" ,'ACTIVO'" &_						
			" ,GETDATE()" &_						
			" ," & ID_USUARIO &_	
			" )"													
	con.executeQuery(sql)
	
	
	'' Obtener ID de Unidad ingresada
	sql = 	"SELECT MAX(ID_UNIDAD) AS ID_UNIDAD FROM BD_UNIDADES WHERE NO_SERIE = '" & noSerie & "'"
	set rs = con.executeQuery(sql)	
	if not rs.eof then
	
		dim idUnidad
		idUnidad = rs.fields("ID_UNIDAD")
	
	end if
	rs.close	
	
	'' Insertar Accesorios
	sql = 	" INSERT INTO BD_UNIDAD_ACCESORIO" &_
			" SELECT BD_UNIDADES.ID_UNIDAD" &_
			" ,ID_ACCESORIO" &_
			" ,(SELECT COSTO FROM C_ACCESORIOS WHERE BD_MODELO_ACCESORIO.ID_ACCESORIO = C_ACCESORIOS.ID_ACCESORIO)" &_
			" ,NULL" &_										
			" ,1" &_
			" ," & idTecnico &_
			" ,GETDATE()" &_
			" ," & idTecnico &_
			" ,GETDATE()" &_
			" FROM BD_UNIDADES CROSS JOIN BD_MODELO_ACCESORIO " &_
			" WHERE BD_UNIDADES.ID_MODELO = BD_MODELO_ACCESORIO.ID_MODELO" &_
			" AND BD_UNIDADES.ID_UNIDAD = " & idUnidad	
	con.executeQuery(sql)									
	
	'' Ingresar Cambio de Status en Bitácora
	sql = 	"INSERT INTO BD_BITACORA_UNIDAD" &_
			"(ID_UNIDAD" &_
			",ID_STATUS_UNIDAD_INI" &_
			",ID_STATUS_UNIDAD_FIN" &_
			",ID_TIPO_RESPONSABLE" &_
			",ID_RESPONSABLE" &_						
			",ID_USUARIO_ALTA" &_
			",FEC_ALTA)" &_
			"VALUES" &_
			"(" & idUnidad &_
			",NULL" &_
			" ,NULLIF ('" & Request.Form("ID_STATUS_UNIDAD") & "','') " &_		
			" ,4" &_
			" ,NULLIF ('" & Request.Form("ID_NEGOCIO") & "','') " &_											
			"," & idTecnico &_
			",GETDATE())"
	con.executeQuery(sql)	
			
		
	if Request.Form("NO_SIM") <> "" then
		
		sql = 	"INSERT INTO BD_SIMS" &_
				"(ID_CLIENTE" &_
				",ID_SOLICITUD_RECOLECCION" &_
				",NO_SIM" &_
				",NO_IMEI" &_
				",ID_UNIDAD" &_
				",ID_USUARIO_ALTA" &_
				",FEC_ALTA" &_
				",STATUS)" &_
				"VALUES" &_
				" (" & idCliente &_
				" ," & Request.Form("ID_SOLICITUD_RECOLECCION") &_
				" ,NULLIF ('" & Request.Form("NO_SIM") & "','') " &_	
				" ,NULLIF ('" & Request.Form("NO_IMEI") & "','') " &_
				"," & idUnidad &_
				"," & idTecnico &_
				",GETDATE()" &_
				",'ACTIVO')"
						
		con.executeQuery(sql)
			
	end if	
	
else
	if request.Form("SOURCE") = "addUnidad.asp" or ID_TIPO_USUARIO = 1 then	
	'if 1 = 2 then

		'' Obtener Status Actual
		sql = 	"SELECT ID_STATUS_UNIDAD,ID_UNIDAD FROM BD_UNIDADES WHERE BD_UNIDADES.NO_SERIE = '" & Request.Form("NO_SERIE") & "'"
		set rs = con.executeQuery(sql)	
		if not rs.eof then
		
			dim idStatusUnidadActual
			idUnidad = rs.fields("ID_UNIDAD")				
			idStatusUnidadActual = rs.fields("ID_STATUS_UNIDAD")
		
		end if
		rs.close
		
		'' Verificar que no se encuentre la uniadad en algún envío
		sql = "SELECT " &_
			  "	CASE WHEN " &_
			  "				( " &_
			  "					SELECT COUNT(*) " &_
			  "					FROM BD_ENVIO_UNIDAD " &_
			  "					INNER JOIN BD_ENVIOS " &_
			  "						ON BD_ENVIO_UNIDAD.ID_ENVIO = BD_ENVIOS.ID_ENVIO " &_
			  "					WHERE ID_UNIDAD = " & idUnidad &_
			  "					AND ID_STATUS_ENVIO IN (1,2,3) " &_
			  "					AND IS_RECIBIDA = 0 " &_
			  "					AND STATUS <> 'BORRADO' " &_
			  "				) = 0 " &_
			  "	THEN 'OK' " &_
			  "	ELSE 'ERROR' END AS VALIDACION_ENVIO; "
			  
		set rs = con.executeQuery(sql)	
		if not rs.eof then
		
			dim validacionEnvio
			validacionEnvio = rs.fields("VALIDACION_ENVIO")
			'response.Write(validacionEnvio &" - "&idUnidad)
			'response.Write(getErrorPage("No se pueden realizar cambios sobre la unidad, ya que, se encuentra en un envío."))
			'response.End()
		
		end if
		rs.close			
			if validacionEnvio = "OK" then
		
			sql = ""
			sql=sql+ "UPDATE BD_UNIDADES SET "
			sql=sql+ "  ID_TIPO_RESPONSABLE = NULLIF ('" & Request.Form("ID_TIPO_RESPONSABLE") & "','') "
			sql=sql+ " ,ID_RESPONSABLE = NULLIF ('" & Request.Form("ID_RESPONSABLE") & "','') "
			sql=sql+ " ,NO_INVENTARIO = NULLIF ('" & Request.Form("NO_INVENTARIO") & "','') "
			sql=sql+ " ,POSICION_INVENTARIO = '" & Request.Form("POSICION_INVENTARIO") & "' "
			sql=sql+ " ,ID_MARCA = NULLIF ('" & idMarca & "','') "
			sql=sql+ " ,ID_MODELO = NULLIF ('" & sku & "','') "
			sql=sql+ " ,IS_RETIRO = " & Request.Form("IS_RETIRO") & " "
			sql=sql+ " ,IS_NUEVA = " & isNueva & " "
			sql=sql+ " ,IS_DANIADA = " & isDaniada & " "
			sql=sql+ " ,COSTO = " & costo & " "
			sql=sql+ " ,ID_MONEDA = " & idMoneda & " "
			sql=sql+ " ,IS_PROPIEDAD_CLIENTE = " & isPropiedadCliente & " "
			sql=sql+ " ,ID_STATUS_UNIDAD = " & Request.Form("ID_STATUS_UNIDAD") & " "
			sql=sql+ " WHERE BD_UNIDADES.NO_SERIE = '" & noSerie & "'; "
			
			con.executeQuery(sql)					
			
			'' Ingresar Cambio de Status en Bitácora
			sql = 	"INSERT INTO BD_BITACORA_UNIDAD" &_
					"(ID_UNIDAD" &_
					",ID_STATUS_UNIDAD_INI" &_
					",ID_STATUS_UNIDAD_FIN" &_
					",ID_TIPO_RESPONSABLE" &_
					",ID_RESPONSABLE" &_						
					",ID_USUARIO_ALTA" &_
					",FEC_ALTA)" &_
					"VALUES" &_
					"(" & idUnidad &_
					" ," & idStatusUnidadActual &_
					" ,NULLIF ('" & Request.Form("ID_STATUS_UNIDAD") & "','') " &_		
					" ,NULLIF ('" & Request.Form("ID_TIPO_RESPONSABLE") & "','') " &_
					" ,NULLIF ('" & Request.Form("ID_RESPONSABLE") & "','') " &_										
					"," & idTecnico &_
					",GETDATE())"
			con.executeQuery(sql)
			con.endConnection
			
		else
			response.write(" res=""ERROR"" desc=""No se pueden realizar cambios sobre la unidad, ya que, se encuentra en un envío.""/>")
			response.End()
			
		end if
			
	else
		response.write(" res=""ERROR"" desc=""No es posible agregar la unidad al negocio debido a que esta ya ha sido ingresada al sistema.""/>")
		response.End()
		
	end if				
end if			

response.write("/d>")
response.end()
con.close()
%>          