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

if request.QueryString("c") <> "" then
	costo = request.QueryString("c")
else
	costo = 0
end if

if request.QueryString("im") <> "" then
	idMoneda = request.QueryString("im")
else
	idMoneda = 0
end if

if request.QueryString("it") <> "" then
	idTecnico = request.QueryString("it")
else
	idTecnico = 0
end if

   
response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d")

sql = 	"SELECT ID_PRODUCTO" &_
		" FROM BD_AR" &_
		" WHERE ID_AR = " & idAR						 

	set rs = con.executeQuery(sql)	
	
	if not rs.eof then
		idProducto = rs.fields("ID_PRODUCTO")
	else
		idProducto = "0"
	end if
	
	rs.close
	set rs = nothing
	con.endConnection

	set con = new cConnection
	con.startConnection(DB_INI)

	''''''''''	Validar modelo de Refacción	''''''''''

	sql = 	"IF EXISTS " &_
			"			( " &_
			"				SELECT " &_
			"					MO_MODULO.ID_MODELO " &_
			"				FROM BD_MODELO_MODULO " &_
			"				INNER JOIN C_PRODUCTOS P_MODULO " &_
			"					ON BD_MODELO_MODULO.ID_PRODUCTO_MODULO = P_MODULO.ID_PRODUCTO " &_
			"				INNER JOIN C_MARCAS MA_MODULO " &_
			"					ON BD_MODELO_MODULO.ID_MARCA_MODULO = MA_MODULO.ID_MARCA " &_
			"				INNER JOIN C_MODELOS MO_MODULO " &_
			"					ON BD_MODELO_MODULO.ID_MODELO_MODULO = MO_MODULO.ID_MODELO " &_
			"				INNER JOIN C_MODELOS MO_UNIDAD " &_
			"					ON BD_MODELO_MODULO.ID_MODELO = MO_UNIDAD.ID_MODELO " &_
			"				INNER JOIN C_MARCAS MA_UNIDAD " &_
			"					ON MO_UNIDAD.ID_MARCA = MA_UNIDAD.ID_MARCA " &_
			"				INNER JOIN C_PRODUCTOS P_UNIDAD " &_
			"					ON MA_UNIDAD.ID_PRODUCTO = P_UNIDAD.ID_PRODUCTO " &_
			"				WHERE " &_
			"					P_UNIDAD.ID_PRODUCTO = " & idProducto &_
			"					AND MO_MODULO.SKU IS NOT NULL " &_
			"					AND MO_UNIDAD.STATUS = 'ACTIVO' " &_
			"					AND MO_MODULO.STATUS = 'ACTIVO' " &_
			"					AND CAST(MO_MODULO.ID_MODELO AS VARCHAR) = '" & sku & "' " &_
			"				GROUP BY MO_MODULO.SKU, MO_MODULO.ID_MODELO " &_
			"			) " &_
			"	BEGIN " &_
			"		SELECT 'MODELO CORRECTO' AS IS_MODELO_VALIDO; " &_
			"	END " &_
			"ELSE " &_
			"	BEGIN " &_
			"		SELECT 'MODELO INCORRECTO' AS IS_MODELO_VALIDO; " &_
			"	END "

	set rs = con.executeQuery(sql)	
	if not rs.eof then
		isModeloValido = rs.fields("IS_MODELO_VALIDO")
	end if
	rs.close
	
	if isModeloValido = "MODELO INCORRECTO" then
		response.write(" res=""ERROR"" desc=""El modelo no existe, verifique""/>")
		response.End()
	end if
	
	''''''''''	Información recuperada de modelo	''''''''''
	sql = 	"SELECT " &_
			 "	 C_MARCAS.ID_PRODUCTO " &_
			 "	,C_MARCAS.ID_MARCA " &_
			 "	,C_MODELOS.ID_MODELO " &_
			 "FROM BD_MODELO_MODULO " &_
			 "INNER JOIN C_MODELOS ON BD_MODELO_MODULO.ID_MODELO_MODULO = C_MODELOS.ID_MODELO " &_
			 "INNER JOIN C_MARCAS ON C_MODELOS.ID_MARCA = C_MARCAS.ID_MARCA " &_
			 "WHERE BD_MODELO_MODULO.ID_MODELO_MODULO = " & sku & " " &_
			 "GROUP BY C_MARCAS.ID_PRODUCTO ,C_MARCAS.ID_MARCA ,C_MODELOS.ID_MODELO"

	set rs = con.executeQuery(sql)	
	if not rs.eof then
		idProducto = rs.fields("ID_PRODUCTO")
		idMarca    = rs.fields("ID_MARCA")
		idModelo   = rs.fields("ID_MODELO")
	end if
	rs.close
	
	''''''''''	Información recuperada de AR	''''''''''
	sql = 	"SELECT " &_
			 "	 ID_CLIENTE " &_
			 "	,ID_NEGOCIO " &_
			 "	,ISNULL (ID_UNIDAD_ATENDIDA,0) AS ID_UNIDAD_ATENDIDA " &_
			 "FROM BD_AR " &_
			 "WHERE BD_AR.ID_AR = " & idAR & "; "

	set rs = con.executeQuery(sql)	
	if not rs.eof then
		idCliente = rs.fields("ID_CLIENTE")
		idNegocio = rs.fields("ID_NEGOCIO")
		idUnidadAtendida = rs.fields("ID_UNIDAD_ATENDIDA")
	end if
	rs.close
	
	''''''''''	Validación Número de Serie	''''''''''
	sql = 	 "SELECT CASE WHEN (SELECT ISNULL(COUNT(ID_UNIDAD),0)" &_
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
	else
		response.write(" res=""ERROR"" desc=""Número de Serie duplicado""/>")
	end if
	rs.close
	
	if validacionNoSerie = "OK" then
		''''''''''	Insertar en Tabla de Unidades	''''''''''
		sql = 	"INSERT INTO BD_UNIDADES" &_
				" (ID_PARENT" &_
				" ,ID_CLIENTE" &_
				" ,ID_PRODUCTO" &_
				" ,ID_MARCA" &_
				" ,ID_MODELO" &_
				" ,NO_SERIE" &_
				" ,ID_TIPO_RESPONSABLE" &_
				" ,ID_RESPONSABLE" &_
				" ,IS_RETIRO" &_
				" ,IS_NUEVA" &_
				" ,IS_DANIADA" &_
				" ,COSTO" &_
				" ,ID_MONEDA" &_
				" ,ID_STATUS_UNIDAD" &_
				" ,STATUS" &_
				" ,FEC_ALTA" &_
				" ,ID_USUARIO_ALTA)" &_
				" VALUES" &_
				" (" & idUnidadAtendida &_
				" ," & idCliente &_
				" ," & idProducto &_
				" ," & idMarca &_
				" ," & idModelo &_
				" ,NULLIF ('" & noSerie & "','') " &_
				" ,4 " &_
				" ," & idNegocio &_
				" ,0 " &_
				" ," & isNueva &_
				" ,NULLIF (" & isDaniada & ",'') " &_
				" ," & costo & " " &_
				" ," & idMoneda & " " &_
				" ,17 " &_
				" ,'ACTIVO'" &_
				" ,GETDATE()" &_
				" ," & idTecnico &_
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
				" ,NULL" &_
				" ,NULLIF ('" & Request.Form("ID_STATUS_UNIDAD") & "','') " &_		
				" ,4" &_
				" ,NULLIF ('" & Request.Form("ID_NEGOCIO") & "','') " &_											
				" ," & idTecnico &_
				" ,GETDATE())"
		con.executeQuery(sql)
		
		response.write(" res=""OK"" desc=""Todo bien"" val=""1"" />")
	else
		response.write(" res=""ERROR"" desc=""No es posible agregar la refaccion al negocio, debido a que esta ya ha sido ingresada al sistema""/>")
	end if	
%>          