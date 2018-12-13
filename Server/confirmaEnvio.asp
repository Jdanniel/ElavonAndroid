<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

Dim conFailed
Dim idEnvio
Dim idTecnico
Dim unidades
Dim arregloUnidades
Dim stringUnidades
Dim idStatus
Dim idUnidad
Dim statusInicial
Dim fechaActual
Dim isRecibida,c1, c2, p1

if request.QueryString("i") <> "" then
	idEnvio = request.QueryString("i")
else
	idEnvio = 0
end if

if request.QueryString("t") <> "" then
	idTecnico = cInt(request.QueryString("t"))
else
	idTecnico = 0
end if

if request.QueryString("u") <> "" then
	unidades = (request.QueryString("u"))
else
	unidades = 0
end if

arregloUnidades = split(unidades, ",")

conFailed = 1
checked = true
stringUnidades = ""

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

On error resume next
	set con = new cConnection
	con.startConnection(DB_INI)
if Err then
	conFailed = 0
end if

if conFailed <> 0 then

	sql="SELECT DISTINCT"
	sql=sql+" BD_ENVIOS.ID_TIPO_RESPONSABLE_DESTINO, BD_ENVIOS.ID_RESPONSABLE_DESTINO"
	sql=sql+" FROM BD_UNIDADES"
	sql=sql+" INNER JOIN BD_ENVIO_UNIDAD ON BD_UNIDADES.ID_UNIDAD = BD_ENVIO_UNIDAD.ID_UNIDAD"
	sql=sql+" INNER JOIN BD_ENVIOS ON BD_ENVIOS.ID_ENVIO = BD_ENVIO_UNIDAD.ID_ENVIO"
	sql=sql+" WHERE BD_ENVIOS.ID_ENVIO ="&idEnvio
	''response.write("<data info3="""&sql&""" />")
	set rsValidacion = con.executeQuery(sql)
	
	if rsValidacion.fields("ID_TIPO_RESPONSABLE_DESTINO")="2" then
	c1 = true
	else
	c1 =false
	end if
	
	if rsValidacion.fields("ID_RESPONSABLE_DESTINO")="""&idTecnico&""" then
	c2 = true
	else
	c2 =false
	end if
	
	p1 = 1 + idTecnico -1
	
	if not rsValidacion.EOF then
		Do until rsValidacion.EOF
			if rsValidacion.fields("ID_TIPO_RESPONSABLE_DESTINO")="2" And rsValidacion.fields("ID_RESPONSABLE_DESTINO")=p1 then
				for i = 0 to Ubound(arregloUnidades)
					response.write("<u id=""" & arregloUnidades(i) & """/>")
			
					sqlRetrieve = "SELECT ID_STATUS_UNIDAD FROM BD_UNIDADES WHERE ID_UNIDAD = " & arregloUnidades(i) & ""
					response.write("<retrieve>" & sqlRetrieve & "</retrieve>")
			
					set record = con.executeQuery(sqlRetrieve)
					''Me regresa 15
			
					if not record.EOF then
						statusInicial = record.fields("ID_STATUS_UNIDAD")
					end if
			
					record.close
					set record = nothing
			
					sql = "UPDATE BD_UNIDADES "
					sql = sql + "SET ID_STATUS_UNIDAD = 15, ID_RESPONSABLE = " & idTecnico & " , ID_TIPO_RESPONSABLE = 2 "
					sql = sql + "WHERE ID_UNIDAD = " & arregloUnidades(i) & ""
			
					con.executeQuery(sql)
					
					fecha = "SELECT CAST(GETDATE() AS SMALLDATETIME) AS FECHA"
					set rsFecha = con.executeQuery(fecha)
			
					if not rsFecha.EOF then
						fechaActual = rsFecha.fields("FECHA")
					end if
			
					arregloFecha = split(fechaActual, "/")
					fechaActual = "" & arregloFecha(1) & "/" & arregloFecha(0) & "/" & arregloFecha(2) & ""
			
					rsFecha.close
					set rsFecha = nothing
					
					sqlNuevo = "UPDATE BD_ENVIO_UNIDAD "
					sqlNuevo = sqlNuevo + "SET IS_RECIBIDA = 1, FEC_RECIBIDA = '" & fechaActual & "', ID_USUARIO_RECEPCION = " & idTecnico & " "
					sqlNuevo = sqlNuevo + "WHERE ID_UNIDAD = " & arregloUnidades(i) & " AND ID_ENVIO = " & idEnvio & ""
					
					con.executeQuery(sqlNuevo)
					
					sqlStored = "SP_INSERT_BITACORA_UNIDAD @ID_UNIDAD = "& arregloUnidades(i) & ", @ID_STATUS_INI = " & statusInicial & ", "
					sqlStored = sqlStored + "@ID_STATUS_FIN = 15, @ID_TIPO_RESPONSABLE = 2, @ID_RESPONSABLE = " & idTecnico & ", @ID_USUARIO = " & idTecnico & ""
			
					response.write("<stored>" & sqlStored & "</stored>")
					con.executeQuery(sqlStored)
				next
				
				''response.write("<data password=""Termina primer for"" />")
			
				if Err then
					response.write("<r res=""ERROR"" desc=""Error de SQL: " & Err.Description & """/>")
					cheked = false
				end if
			
				''--------------cambio
				if checked <> false then
					sqlID = "SELECT ID_UNIDAD FROM BD_ENVIO_UNIDAD WHERE ID_ENVIO = " & idEnvio & ""
			
					set rs = con.executeQuery(sqlID)
					response.write("<records>" & rs.RecordCount & "</records>")
			
						if not rs.EOF then
							''recorre unidades del envio
							for x = 0 to rs.RecordCount - 1
								sqlUnidades = "SELECT IS_RECIBIDA FROM BD_ENVIO_UNIDAD  WHERE ID_UNIDAD = " & rs.fields("ID_UNIDAD") & " AND ID_ENVIO = " & idEnvio & ""
								response.write("<queryStatus>" & sqlUnidades & "</queryStatus>")
								set rs2 = con.executeQuery(sqlUnidades)
				
								response.write("<eof>" & rs.EOF & "</eof>")
								if not rs2.EOF then
									isRecibida = rs2.fields("IS_RECIBIDA")
									idUnidad = rs.fields("ID_UNIDAD")
									response.write("<units>" & idUnidad & "</units>")
									response.write("<unitsRS>" & rs.fields("ID_UNIDAD") & "</unitsRS>")
									response.write("<isrecibida>" & isRecibida & "</isrecibida>")
									response.write("<hiRS>" & rs2.fields("ID_STATUS_UNIDAD") & "</hiRS>")
								end if
				
								if isRecibida <> 1 then
									response.write("<rrrr res=""OK"" desc=""Cambio String unif""/>")
									stringUnidades = stringUnidades + "" & idUnidad & ""
								end if
				
								rs.MoveNext
							next
							
							''response.write("<data password=""Termina segundo for"" />")
							
							'' si todas estan en IS_RECIBIDA se cambia el status del envio
							if stringUnidades = "" then
								sqlUpdate = "UPDATE BD_ENVIOS SET ID_STATUS_ENVIO = 4 WHERE ID_ENVIO = " & idEnvio & ""
				
								con.executeQuery(sqlUpdate)
								response.write("<queryUpdate>" & sqlUpdate & "</queryUpdate>")
								response.write("<r res=""OK"" desc=""Estatus Cambiado""/>")
							else
								response.write("<r res=""OK"" desc=""Estatus NO Cambiado""/>")
							end if
						end if
						else
						response.write("<data info=""no"" info2=""ma""  />")
				end if
			else
				response.write("<data info=""Tipo de técnico o técnico incorrecto"" />")
			end if
			
		rsValidacion.MoveNext
		Loop
	end if
	
	rsValidacion.close
	set rsValidacion = Nothing	
else
	response.write("<r res=""ERROR"" desc=""Error de Conexión: " & Err.Description & """/>")
end if

response.write("</d>")

%>