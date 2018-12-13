<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

Dim idTecnico
Dim idTipoDestino
Dim idRespDestino
Dim idMensajeria
Dim idUrgencia
Dim noGuia
Dim costo
Dim idEnvio
Dim unidades
Dim arregloUnidades, arregloFecha
Dim conFailed
Dim maxTry
Dim failed
Dim unitQuery
Dim fechaActual
Dim maxID
Dim statusInicial

if request.QueryString("i") <> "" then
	idTecnico = cInt(request.QueryString("i"))
else
	idTecnico = 0
end if

if request.QueryString("it") <> "" then
	idTipoDestino = cInt(request.QueryString("it"))
else
	idTipoDestino = 0
end if

if request.QueryString("ir") <> "" then
	idRespDestino = cInt(request.QueryString("ir"))
else
	idRespDestino = 0
end if

if request.QueryString("im") <> "" then
	idMensajeria = cInt(request.QueryString("im"))
else
	idMensajeria = 0
end if

if request.QueryString("ng") <> "" then
	noGuia = request.QueryString("ng")
else
	noGuia = ""
end if

if request.QueryString("c") <> "" then
	costo = cDbl(request.QueryString("c"))
else
	costo = 0.00
end if

if request.QueryString("iu") <> "" then
	idUrgencia = cInt(request.QueryString("iu"))
else
	idUrgencia = 0
end if

if request.QueryString("u") <> "" then
	unidades = request.QueryString("u")
else
	unidades = ""
end if

arregloUnidades = split(unidades, ",")
conFailed = 1
maxTry = 0
failed = true

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if (idTecnico <> 0 and idTipoDestino <> 0 and idRespDestino <> 0 and idMensajeria <> 0 and unidades <> "") then
	set con = new cConnection

	On error resume next
		con.startConnection(DB_INI)
	if Err then
		conFailed = 0
	end if

	if conFailed <> 0 then
	
		'validacion unidad con tecnico
		numUnidades = 0
		for i = 0 to UBound(arregloUnidades)
			sql="SELECT"
			sql=sql+" COUNT(BD_UNIDADES.ID_UNIDAD) as num "
			sql=sql+" FROM BD_UNIDADES "
			sql=sql+" WHERE BD_UNIDADES.ID_UNIDAD="& arregloUnidades(i) & " AND BD_UNIDADES.ID_TIPO_RESPONSABLE=2 AND BD_UNIDADES.ID_RESPONSABLE="&idTecnico&" AND ID_STATUS_UNIDAD=15 "
			set rs = con.executeQuery(sql)
			
			if rs.fields("num")=1 then
				numUnidades = numUnidades + 1
			end if
			
		next
		
		''response.write("<n>"&numUnidades&"</n>")
		
		if numUnidades > 0 Then

			maxQuery = "SELECT MAX(ID_ENVIO) AS MAXID FROM BD_ENVIOS"
			set rs = con.executeQuery(maxQuery)
	
			if not rs.EOF then
				maxID = rs.fields("MAXID")
				''response.write("<maxID>" & maxID & "</maxID>")
			end if
	
			do while failed <> false
	
				fecha = "SELECT CAST(GETDATE() AS SMALLDATETIME) AS FECHA"
				set rs = con.executeQuery(fecha)
	
				if not rs.EOF then
					fechaActual = rs.fields("FECHA")
				end if
	
				arregloFecha = split(fechaActual, "/")
				fechaActual = "" & arregloFecha(1) & "/" & arregloFecha(0) & "/" & arregloFecha(2) & ""
	
				rs.close
				set rs = nothing
	
				sql = "INSERT INTO BD_ENVIOS (ID_TIPO_RESPONSABLE_ORIGEN, ID_RESPONSABLE_ORIGEN, ID_TIPO_RESPONSABLE_DESTINO, "
				sql = sql + "ID_RESPONSABLE_DESTINO, ID_SERVICIO_MENSAJERIA, NO_GUIA, COSTO, ID_URGENCIA_ENVIO, "
				sql = sql + "FEC_ENVIO, FEC_RECEPCION, ID_STATUS_ENVIO, ID_USUARIO_ALTA, FEC_ALTA, STATUS) "
				sql = sql + "VALUES (2, " & idTecnico & ", " & idTipoDestino & ", " & idRespDestino & ", " & idMensajeria & ", "
				sql = sql + "'" & noGuia & "', " & costo & ", " & idUrgencia & ", '" & fechaActual & "', null, 3, " & idTecnico & ", "
				sql = sql + "'" & fechaActual & "', 'ACTIVO')"
	
				on error resume next
				''response.write("<query>" & sql & "</query>")
				con.executeQuery(sql)
	
				if Err then
					maxTry = maxTry + 1
					if maxTry < 10 then
						failed = true
					else
						failed = false
						unitQuery = false
					end if
				else
					failed = false
					unitQuery = true
				end if
			loop
	
			if unitQuery <> false then
	
				sqlQuery = "SELECT ID_ENVIO FROM BD_ENVIOS WHERE "
				sqlQuery = sqlQuery + "ID_TIPO_RESPONSABLE_ORIGEN = 2 AND ID_RESPONSABLE_ORIGEN = " & idTecnico & " "
				sqlQuery = sqlQuery + "AND ID_TIPO_RESPONSABLE_DESTINO = " & idTipoDestino & " "
				sqlQuery = sqlQuery + "AND ID_RESPONSABLE_DESTINO = " & idRespDestino & " "
				sqlQuery = sqlQuery + "AND ID_SERVICIO_MENSAJERIA = " & idMensajeria & " "
				sqlQuery = sqlQuery + "AND NO_GUIA = '" & noGuia & "' AND COSTO = " & costo & " "
				sqlQuery = sqlQuery + "AND ID_URGENCIA_ENVIO = " & idUrgencia & " AND DATEDIFF(second, FEC_ENVIO, '" & fechaActual & "') = 0 "
				sqlQuery = sqlQuery + "AND FEC_RECEPCION IS NULL AND ID_STATUS_ENVIO = 3 AND ID_USUARIO_ALTA = " & idTecnico & " "
				sqlQuery = sqlQuery + "AND DATEDIFF (second, FEC_ALTA, '" & fechaActual & "') = 0"
	
				''response.write("<queryID>" & sqlQuery & "</queryID>")
				set rs = con.executeQuery(sqlQuery)
	
				if not rs.EOF then
					idEnvio = rs.fields("ID_ENVIO")
					''response.write("<envioID>" & idEnvio & "</envioID>")
				end if
	
				rs.close
				set rs = nothing
	
				for i = 0 to UBound(arregloUnidades)
				
					sql="SELECT"
					sql=sql+" COUNT(BD_UNIDADES.ID_UNIDAD) as num "
					sql=sql+" FROM BD_UNIDADES "
					sql=sql+" WHERE BD_UNIDADES.ID_UNIDAD="& arregloUnidades(i) & " AND BD_UNIDADES.ID_TIPO_RESPONSABLE=2 AND BD_UNIDADES.ID_RESPONSABLE="&idTecnico&" AND ID_STATUS_UNIDAD=15 "
					set rs = con.executeQuery(sql)
					
					if rs.fields("num")=1 then
				
						sql2 = "INSERT INTO BD_ENVIO_UNIDAD (ID_UNIDAD, ID_ENVIO, FEC_ALTA, ID_USUARIO_ALTA, IS_RECIBIDA) "
						sql2 = sql2 + "VALUES (" & arregloUnidades(i) & ", " & idEnvio & ", '" & fechaActual & "', " & idTecnico & ", 0)"
		
						''response.write("<queryUnidades>" & sql2 & "</queryUnidades>")
						con.executeQuery(sql2)
						
						sqlRetrieve = "SELECT ID_STATUS_UNIDAD FROM BD_UNIDADES WHERE ID_UNIDAD = " & arregloUnidades(i) & ""
						''response.write("<retrieve>" & sqlRetrieve & "</retrieve>")
				
						set record = con.executeQuery(sqlRetrieve)
				
						if not record.EOF then
							statusInicial = record.fields("ID_STATUS_UNIDAD")
						end if
				
						record.close
						set record = nothing
							
						sqlU = "UPDATE BD_UNIDADES "
						sqlU = sqlU + "SET ID_STATUS_UNIDAD = 16, ID_RESPONSABLE = " & idMensajeria & " , ID_TIPO_RESPONSABLE = 3 "
						sqlU = sqlU + "WHERE ID_UNIDAD = " & arregloUnidades(i) & ""
						
						sqlStored = "SP_INSERT_BITACORA_UNIDAD @ID_UNIDAD = "& arregloUnidades(i) & ", @ID_STATUS_INI = " & statusInicial & ", "
						sqlStored = sqlStored + "@ID_STATUS_FIN = 16, @ID_TIPO_RESPONSABLE = 3, @ID_RESPONSABLE = " & idMensajeria & ", @ID_USUARIO = " & idTecnico & ""
						
						con.executeQuery(sqlU)
						''response.write("<stored>" & sqlStored & "</stored>")
						con.executeQuery(sqlStored)
					
					end if
					
				next
	
				response.write("<r res=""OK"" desc=""¡Envio realizado con exito!""/>")
			else
				response.write("<r res=""ERROR"" desc=""Error de Sql: " & Err.description & """/>")
			end if
		else
			response.write("<r res=""ERROR"" desc=""Unidad no pertenece a tecnico""/>")
		end if
	else
		response.write("<r res=""ERROR"" desc=""Error de conexión: " & Err.description & """/>")
		con.endConnection
	end if
else
	response.write("<r res=""ERROR"" desc=""Parámetros incorrectos""/>")
end if

response.write("</d>")

%>