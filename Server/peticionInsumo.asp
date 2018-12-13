<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

Dim idAR
Dim idTecnico
Dim idInsumo
Dim idAlmacen
Dim idUrgencia
Dim notas
Dim tipoServicio
Dim idDireccion
Dim fechaCompromiso
DIm cantidad
Dim idSolicitud
Dim idCliente
Dim otraDireccion
Dim conFailed
Dim failed
Dim maxTry

if request.QueryString("ia") <> "" then
	idAR = request.QueryString("ia")
else
	idAR = 0
end if

if request.QueryString("i") <> "" then
	idTecnico = cInt(request.QueryString("i"))
else
	idTecnico = 0
end if

if request.QueryString("in") <> "" then
	idInsumo = request.QueryString("in")
else
	idInsumo = 0
end if

if request.QueryString("a") <> "" then
	idAlmacen = cInt(request.QueryString("a"))
else
	idAlmacen = 0
end if

if request.QueryString("u") <> "" then
	idUrgencia = cInt(request.QueryString("u"))
else
	idUrgencia = 0
end if

if request.QueryString("n") <> "" then
	notas = request.QueryString("n")
else
	notas = ""
end if

if request.QueryString("ts") <> "" then
	tipoServicio = cInt(request.QueryString("ts"))
else
	tipoServicio = 0
end if

if request.QueryString("idir") <> "" then
	idDireccion = cInt(request.QueryString("idir"))
else
	idDireccion = 0
end if

if request.QueryString("fecha") <> "" then
	fechaCompromiso = "'"&request.QueryString("fecha")&"'"
else
	fechaCompromiso = "NULL"
end if

if request.QueryString("c") <> "" then
	cantidad = cInt(request.QueryString("c"))
else
	cantidad = 0
end if

conFailed = 1
maxTry = 0
failed = true

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if (idAR <> 0 and idTecnico <> 0 and idInsumo <> 0 and idAlmacen <> 0 and idUrgencia <> 0 and tipoServicio <> 0 and idDireccion <> 0 and fechaCompromiso <> "" and cantidad <>0) then
	set con = new cConnection
	
	On error resume next
		con.startConnection(DB_INI)
	if Err then
		conFailed = 0
	end if
	
	if conFailed <> 0 then
	
		sql= "SELECT"
		sql=sql+" ID_TECNICO, ID_STATUS_AR"
		sql=sql+" FROM dbo.BD_AR"
		sql=sql+" WHERE ID_AR="&idAR&" AND ID_TECNICO = "&idTecnico
		
		set rsValidacion = con.executeQuery(sql)
		
		if not rsValidacion.EOF then
			Do until rsValidacion.EOF
				if rsValidacion.fields("ID_STATUS_AR")<>"8" and rsValidacion.fields("ID_STATUS_AR")<>"2" then
					
					if idDireccion = -1 then
	
						sql= 	 "SELECT ISNULL(BD_NEGOCIOS.DIRECCION,'') + ',' + ISNULL(BD_NEGOCIOS.COLONIA,'') + ',' + ISNULL(BD_NEGOCIOS.POBLACION,'') + ',' + ISNULL(BD_NEGOCIOS.ESTADO,'') + ',C.P.:' + ISNULL(BD_NEGOCIOS.CP,'') AS DIRECCION_NEGOCIO"
						sql=sql& " FROM BD_AR"
						sql=sql& " INNER JOIN BD_NEGOCIOS ON BD_AR.ID_NEGOCIO = BD_NEGOCIOS.ID_NEGOCIO"	
						sql=sql& " WHERE BD_AR.ID_AR = " & idAR	
						set rs = con.executeQuery(sql)
						
						idDireccion = "NULL"
						
							if not rs.eof then
								otraDireccion = rs.fields("DIRECCION_NEGOCIO")
							end if	
						
						rs.close
						set rs = nothing
					
					else
						otraDireccion = ""	
					end if
			
					sql = "SP_FA_ADD_SOLICITUD_ALMACEN @ID_USUARIO_ALTA = " & idTecnico &", @ID_AR = " &idAR &", "
					sql = sql & " @ID_TIPO_SOLICITUD_ALMACEN = 6, @ID_ALMACEN = " & idAlmacen & ", @ID_TIPO_RESPONSABLE_DESTINO=2, "
					sql = sql & " @ID_RESPONSABLE_DESTINO = " & idTecnico & ", @ID_DIRECCION = " & idDireccion & ", "
					sql = sql & " @OTRA_DIRECCION='"&otraDireccion&"', "
					sql = sql & " @ID_URGENCIA_SOLICITUD_ALMACEN = " & idUrgencia & ", @FEC_COMPROMISO = " & fechaCompromiso & ", "
					sql = sql & " @ID_TIPO_SERVICIO	= " & tipoServicio & ", @IS_ENTREGAS_PARCIALES=0, @NOTAS = '" & notas & "'"
					on error resume next
					set rs = con.executeQuery(sql)
					//response.write(sql)
					
					if Err then
						maxTry = maxTry + 1
						if maxTry < 10 then
							failed = true
						else
							failed = false
						end if
					else
						failed = false
					end if
					
					if failed = false then
					
					
						''sql = "SELECT MAX(ID_SOLICITUD_ALMACEN) AS ID_SOLICITUD_ALMACEN FROM BD_SOLICITUDES_ALMACEN "
						''set rs = con.executeQuery(sql)
						idSolicitud = rs.fields("ID_SOLICITUD_ALMACEN")
						
						sql = "SELECT ID_CLIENTE FROM BD_AR WHERE ID_AR = " & idAR
						set rs = con.executeQuery(sql)
						idCliente = rs.fields("ID_CLIENTE")
						
						sql = "SP_FA_ADD_ITEMS_SOLICITUD_ALMACEN "
						sql = sql & " @ID_USUARIO_ALTA = " & idTecnico & " , "
						sql = sql & " @ID_TIPO_ITEM_SOLICITUD_ALMACEN=2, "
						sql = sql & " @ID_SOLICITUD_ALMACEN = " & idSolicitud & " , "
						sql = sql & " @ID_CLIENTE = " & idCliente & " , "
						sql = sql & " @ID_MARCA = NULL, "
						sql = sql & " @ID_MODELO = NULL, "
						sql = sql & " @ID_INSUMO = " & idInsumo & " , "
						sql = sql & " @ID_SPARE_PART=NULL, "
						sql = sql & " @CANTIDAD_ITEMS= " & cantidad & ", "
						sql = sql & " @NOTAS='',"
						sql = sql & " @ID_UNIDAD=NULL "
						
						con.executeQuery(sql)
						//response.write(sql)
						
						sql = "SP_UPDATE_STATUS_AR @ID_AR = "&idAR&" ,@ID_STATUS_AR = 11 , @ID_USUARIO = "&idTecnico
						con.executeQuery(sql)
					
						response.write("<r res=""OK"" desc=""EL cambio de status fue exitoso!""/>")
					else
						response.write("<r res=""ERROR"" desc=""Error de Sql: " & Err.description & """/>")
					end if
					
				end if
				rsValidacion.MoveNext
			Loop
		end if
		rsValidacion.close
		set rsValidacion = Nothing
	
			
	else
		response.write("<r res=""ERROR"" desc=""Error de Sql: " & Err.description & """/>")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error de Sql: " & Err.description & """/>")
end if

response.write("</d>")

%>