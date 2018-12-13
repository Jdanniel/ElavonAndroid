<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->
<!--#include file="simValidaLongitud.asp" -->
<!--#include file="simValidaDigitos.asp" -->
<!--#include file="simValidaLetras.asp" -->
<!--#include file="simValidaCarInvalido.asp" -->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

if request.QueryString("ic") <> "" then
	idCliente = request.QueryString("ic")
else
	idCliente = -1
end if

if request.QueryString("nose") <> "" then
	noSerie = request.QueryString("nose")
else
	noSerie = ""
end if

if request.QueryString("noi") <> "" then
	noInventario = request.QueryString("noi")
else
	noInventario = ""
end if

if request.QueryString("imo") <> "" then
	idModelo = request.QueryString("imo")
else
	idModelo = -1
end if

if request.QueryString("nosim") <> "" then
	noSIM = request.QueryString("nosim")
else
	noSIM = ""
end if

if request.QueryString("idsr") <> "" then
	idSolicitudRecoleccion = request.QueryString("idsr")
else
	idSolicitudRecoleccion = -1
end if

if request.QueryString("in") <> "" then
	isNueva = request.QueryString("in")
else
	isNueva = -1
end if

if request.QueryString("ima") <> "" then
	idMarca = request.QueryString("ima")
else
	idMarca = -1
end if

if request.QueryString("noimei") <> "" then
	noIMEI = request.QueryString("noimei")
else
	noIMEI = ""
end if

if request.QueryString("idtr") <> "" then
	idTipoResponsable = request.QueryString("idtr")
else
	idTipoResponsable = -1
end if

if request.QueryString("ir") <> "" then
	idResponsable = request.QueryString("ir")
else
	idResponsable = -1
end if

if request.QueryString("il") <> "" then
	idLlave = request.QueryString("il")
else
	idLlave = ""
end if

if request.QueryString("is") <> "" then
	idSoftware = request.QueryString("is")
else
	idSoftware = ""
end if

if request.QueryString("isr") <> "" then
	isRetiro = request.QueryString("isr")
else
	isRetiro = -1
end if

if request.QueryString("pi") <> "" then
	posicionInventario = request.QueryString("pi")
else
	posicionInventario = ""
end if

if request.QueryString("it") <> "" then
	idTecnico = request.QueryString("it")
else
	idTecnico = -1
end if

if request.QueryString("isu") <> "" then
	idStatusUnidad = request.QueryString("isu")
else
	idStatusUnidad = -1
end if

if request.QueryString("noe") <> "" then
	noEquipo = request.QueryString("noe")
else
	noEquipo = ""
end if

if request.QueryString("ineg") <> "" then
	idNegocio = request.QueryString("ineg")
else
	idNegocio = -1
end if

if request.QueryString("icar") <> "" then
	idCarrier = request.QueryString("icar")
else
	idCarrier = -1
end if

Dim isDaniada
isDaniada = 0

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idCliente <> -1 and noSerie <> -1 and noInventario <> -1 and idModelo <> -1 and noSIM <> -1 and idSolicitudRecoleccion <> -1 and _
	isNueva <> -1 and idMarca <> -1 and noIMEI <> -1 and idTipoResponsable <> -1 and idResponsable <> -1 and idLlave <> -1 and _
	idSoftware <> -1 and isRetiro <> -1 and posicionInventario <> -1 and idTecnico <> -1 and idStatusUnidad <> -1 and noEquipo <> -1 and _
	idNegocio <> -1 then
	
	if idCarrier <> "" then 
	
		Dim strAlpha 
		
		sql = 	"SELECT LETRA_MIN ,LETRA_MAX" &_ 
				"		,NUM_MIN ,NUM_MAX" &_
				"		,LONG_MIN ,LONG_MAX" &_
				"	FROM C_CARRIER " &_
				"	WHERE ID_CARRIER = " & idCarrier
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
			
			if noSIM <> "" then
				if ValidaLongitud(noSIM, rs.fields("LONG_MIN"),rs.fields("LONG_MAX")) <> "OK" then
					response.write("<r res=""ERROR"" desc=""El numero de SIM ingresado tiene una longitud no permitida.""/></d>")
					con.endConnection
					response.end()
				else		
					if validaDigitos(noSIM,rs.fields("NUM_MIN"),rs.fields("NUM_MAX")) <> "OK" then
						response.write("<r res=""ERROR"" desc=""El numero DIGITOS ingresados es incorrecto.""/></d>")
						con.endConnection
						response.end()
					else	
						if validaLetras(noSIM,rs.fields("LETRA_MIN"),rs.fields("LETRA_MAX")) <> "OK" then
							response.write("<r res=""ERROR"" desc=""El numero LETRAS ingresados es incorrecto.""/></d>")
							con.endConnection
							response.end()
						else						
							if CarInvalido(noSIM,strAlpha) then
								response.write("<r res=""ERROR"" desc=""El numero de SIM ingresado contiene caracteres no validos.""/></d>")
								con.endConnection
								response.end()
							else
								validaNomenclaturaSIM = "OK"
							end if
						end if
					end if
				end if
			end if
		
		end if
		rs.close
	else
		validaNomenclaturaSIM = "OK"
	end if	
	
	''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validación Número de Serie
	sql = 	"SELECT CASE WHEN (SELECT ISNULL(COUNT(ID_UNIDAD),0)" &_
			 "                 FROM BD_UNIDADES" &_
			 "                 WHERE BD_UNIDADES.STATUS = 'ACTIVO'" &_						 
			 "                 AND NO_SERIE	= '" & replace(noSerie," ","") & "') = 0" &_
			 "           THEN 'OK'" &_
			 "           ELSE 'ERROR'" &_
			 "       END AS VALIDACION_NO_SERIE"

	set rs = con.executeQuery(sql)	
	
	if not rs.eof then
		dim validacionNoSerie
		validacionNoSerie = rs.fields("VALIDACION_NO_SERIE")
		
		rs.close
		set rs = nothing
		
		if validacionNoSerie = "OK" then
			''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validación Número de Inventario
			sql = 	"SELECT CASE WHEN (SELECT COUNT(ID_UNIDAD)" &_
					 "                 FROM BD_UNIDADES" &_
					 "                 WHERE ID_CLIENTE = " & idCliente &_
					 "                 AND BD_UNIDADES.STATUS = 'ACTIVO'" &_		 
					 "                 AND NO_INVENTARIO	= '" & noInventario & "') = 0" &_
					 "                 OR '" & noInventario & "' = ''" &_			 
					 "           THEN 'OK'" &_
					 "           ELSE 'ERROR'" &_
					 "       END AS VALIDACION_NO_INVENTARIO"
			
			set rs = con.executeQuery(sql)	
			
			if not rs.eof then
				dim validacionNoInventario
				validacionNoInventario = rs.fields("VALIDACION_NO_INVENTARIO")
				
				rs.close
				set rs = nothing
				
				if validacionNoInventario = "OK" then
					'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validación SIM Ingresada
					sql = 	"SELECT CASE WHEN (SELECT IS_GPRS FROM C_MODELOS" &_ 
							"				   WHERE C_MODELOS.ID_MODELO = " & idModelo & ") = 1" &_
							"			 AND ('" & noSIM & "' = '')" &_ 
							"            THEN 'ERROR'" &_
							"            ELSE 'OK'" &_
							"        END AS VALIDACION_SIM_INGRESADA"
					
					set rs = con.executeQuery(sql)	
					
					if not rs.eof then
						dim validacionSIMIngresada
						validacionSIMIngresada = rs.fields("VALIDACION_SIM_INGRESADA")
						
						rs.close
						set rs = nothing
						
						if validacionSIMIngresada = "OK" then
							'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Validación SIM Duplicada
							sql = 	"SELECT CASE WHEN (SELECT COUNT(ID_SIM)" &_
									"				   FROM BD_SIMS" &_
									"				   WHERE BD_SIMS.NO_SIM = '" & noSIM & "') > 0" &_ 
									"            AND  (SELECT IS_GPRS FROM C_MODELOS" &_ 
									"				   WHERE C_MODELOS.ID_MODELO = " & idModelo & ") = 1" &_
									"            THEN 'ERROR'" &_
									"            ELSE 'OK'" &_
									"        END AS VALIDACION_SIM_INVENTARIO"
							set rs = con.executeQuery(sql)	
							
							if not rs.eof then
								dim validacionSIMInventario
								validacionSIMInventario = rs.fields("VALIDACION_SIM_INVENTARIO")
								
								rs.close
								set rs = nothing
								
								if validacionSIMInventario = "OK" then
									sql = 	"SELECT ID_PRODUCTO FROM C_CLIENTES WHERE ID_CLIENTE = " & idCliente
									set rs = con.executeQuery(sql)	
									
									if not rs.eof then
										dim idProducto
										idProducto = rs.fields("ID_PRODUCTO")
										
										rs.close
										set rs = nothing
										
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
												" ,ID_STATUS_UNIDAD" &_
												" ,STATUS" &_						
												" ,FEC_ALTA" &_
												" ,ID_USUARIO_ALTA)" &_
												" VALUES" &_
												" (" & idCliente &_
												" ," & idSolicitudRecoleccion &_
												" ," & idProducto &_
												" ," & isNueva &_										
												" ," & idMarca &_
												" ," & idModelo &_	
												" ,NULLIF ('" & replace(noSerie," ","") & "','') " &_					
												" ,NULLIF ('" & replace(noInventario," ","") & "','') " &_				
												" ,NULLIF ('" & idSIM & "','') " &_																																				
												" ,NULLIF ('" & noIMEI & "','') " &_
												" ,NULLIF ('" & noSIM & "','') " &_						
												" ,NULLIF ('" & idTipoResponsable & "','') " &_
												" ,NULLIF ('" & idResponsable & "','') " &_
												" ,NULLIF ('" & idLlave & "','') " &_
												" ,NULLIF ('" & idSoftware & "','') " &_
												" ,NULLIF ('" & posicionInventario & "','') " &_	
												" ,NULLIF ('" & isRetiro & "','') " &_																
												" ," & isDaniada  &_
												" ,NULLIF ('" & idStatusUnidad & "','') " &_												
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
										
											rs.close
											set rs = nothing
																		
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
													" ,NULLIF ('" & idStatusUnidad & "','') " &_		
													" ,4" &_
													" ,NULLIF ('" & idNegocio & "','') " &_											
													"," & idTecnico &_
													",GETDATE())"
											con.executeQuery(sql)	
												
											if noSIM <> "" then
												sql = 	"INSERT INTO BD_SIMS" &_
														"(ID_CLIENTE" &_
														",ID_SOLICITUD_RECOLECCION" &_
														",NO_SIM" &_
														",NO_IMEI" &_
														",ID_UNIDAD" &_
														",ID_USUARIO_ALTA" &_
														",FEC_ALTA" &_
														",STATUS" &_
														",ID_STATUS_SIM)" &_
														"VALUES" &_
														" (" & idCliente &_
														" ," & idSolicitudRecoleccion &_
														" ,NULLIF ('" & noSIM & "','') " &_	
														" ,NULLIF ('" & noIMEI & "','') " &_
														"," & idUnidad &_
														"," & idTecnico &_
														",GETDATE()" &_
														",'ACTIVO'" &_
														",2)"	
												con.executeQuery(sql)
											end if
												
											response.write("<r res=""OK"" desc=""Se ha agregado exitosamente"" val=""1"" /></d>")
										else
											response.write("<r res=""ERROR"" desc=""Error en  el query del id Unidad"" /></d>")
										end if
									else
										response.write("<r res=""ERROR"" desc=""Error en el query del id producto"" /></d>")
									end if
								else
									response.write("<r res=""ERROR"" desc=""SIM duplicada"" val=""4"" /></d>")
								end if
							else
								response.write("<r res=""ERROR"" desc=""Error en el query de la validacion de SIM duplicada"" /></d>")
							end if	
						else
							response.write("<r res=""ERROR"" desc=""Falta ingresar el SIM para este modelo"" val=""3"" /></d>")
						end if	
					else
						response.write("<r res=""ERROR"" desc=""Error en el query de la validacion SIM"" /></d>")
					end if
				else
					response.write("<r res=""ERROR"" desc=""No de inventario ya ingresado"" val=""2"" /></d>")
				end if	
			else
				response.write("<r res=""ERROR"" desc=""Error en el query de la validacion del No. Inventario"" /></d>")
			end if
		else
			response.write("<r res=""ERROR"" desc=""Unidad duplicada"" val=""-1"" /></d>")
		end if
	else
		response.write("<r res=""ERROR"" desc=""Error en el query de la validacion del No. Serie"" /></d>")
	end if
else
	response.write("<r res=""ERROR"" desc=""No jalo bien los valores del get"" /></d>")
end if

con.endConnection
%>