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

if request.QueryString("it") <> "" then
	idTecnico = request.QueryString("it")
else
	idTecnico = -1
end if

Dim edit
Dim idTipoProducto

idTipoProducto = 2

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 and idTecnico <> -1 then
	sql = "SELECT BD_AR.ID_SERVICIO, C_STATUS_AR.IS_MOV_INV_ALLOWED"
	sql = sql + " FROM BD_AR"
	sql = sql + " INNER JOIN C_STATUS_AR ON BD_AR.ID_STATUS_AR = C_STATUS_AR.ID_STATUS_AR"
	sql = sql + " WHERE ID_AR = " & idAR
	set rs = con.executeQuery(sql)
	
	if not rs.eof then
		idServicio = rs.fields("ID_SERVICIO")
		isMovInvAllowed = cInt(rs.fields("IS_MOV_INV_ALLOWED"))
		
		rs.close
		set rs = nothing
		
		if isMovInvAllowed = 1 then
			edit = 1
		else
			edit = 0
		end if
		
		sql = "SELECT BD_AR.ID_TECNICO"
		sql=sql+ " ,BD_AR.ID_NEGOCIO"
		sql=sql+ " ,BD_AR.ID_CLIENTE"
		sql=sql+ " ,C_CLIENTES.IS_ID_REQ" 
		sql=sql+ " ,C_CLIENTES.IS_SINGLE_MOV_INV"
		sql=sql+ " ,ISNULL(C_PRODUCTOS.IS_BOM, -1) AS IS_BOM" 
		sql=sql+ " FROM BD_AR" 
		sql=sql+ " INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
		sql=sql+ " LEFT JOIN BD_UNIDADES ON BD_AR.ID_UNIDAD_ATENDIDA = BD_UNIDADES.ID_UNIDAD "
		sql=sql+ " LEFT JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO "
		sql=sql+ " WHERE ID_AR = " & idAR

		set rs = con.executeQuery(sql)
	
		if not rs.eof then
			idNegocio = rs.fields("ID_NEGOCIO")
			idCliente = rs.fields("ID_CLIENTE")
			isIdReq = rs.fields("IS_ID_REQ")
			isBom = rs.fields("IS_BOM")
			
			rs.close
			set rs = nothing
			
			'' Verificar Sustituciones Previas
			sql = "SELECT CASE WHEN (SELECT COUNT(*)"
			sql = sql + "			 FROM BD_SUSTITUCIONES"
			sql = sql + "			 WHERE " & idAR & " = BD_SUSTITUCIONES.ID_AR) = 0"
			sql = sql + "			 AND " & edit & " = 1"
			sql = sql + "			 AND ( "
			sql = sql + "				SELECT IS_SINGLE_MOV_INV "
			sql = sql + "				FROM BD_AR "
			sql = sql + "				INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
			sql = sql + "				WHERE ID_AR = " & idAR & " "
			sql = sql + "			 ) = 1 "
			sql = sql + "	   THEN 1 "
			sql = sql + " 	   WHEN ( "
			sql = sql + "				SELECT IS_SINGLE_MOV_INV "
			sql = sql + "				FROM BD_AR "
			sql = sql + "				INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
			sql = sql + "				WHERE ID_AR = " & idAR & " "
			sql = sql + "			 ) = 0 "
			sql = sql + "			 AND " & edit & " = 1"
			sql = sql + "	   THEN 1 "
			sql = sql + "	   ELSE 0"
			sql = sql + " END AS BUTTON_DISPLAY"
			
			set rs = con.executeQuery(sql)
			buttonDisplay = rs.fields("BUTTON_DISPLAY")
			
			rs.close
			set rs = nothing

			if edit = "1" then
				cancelButtonDisplay = 1
			else
				cancelButtonDisplay = 0
			end if	

			if isBom = "-1" then
				idTipoProducto = "1"
			end if
			
			sql="SELECT"
			sql=sql+" BD_UNIDADES.ID_UNIDAD"
			sql=sql+",BD_UNIDADES.ID_PRODUCTO"
			sql=sql+",BD_UNIDADES.ID_MODELO"
			sql=sql+",C_MODELOS.DESC_MODELO"
			sql=sql+",C_MARCAS.DESC_MARCA"
			sql=sql+",BD_UNIDADES.NO_SERIE"
			sql=sql+",BD_UNIDADES.NO_INVENTARIO"
			sql=sql+",BD_UNIDADES.NO_EQUIPO"
			sql=sql+",BD_UNIDADES.NO_IMEI"
			sql=sql+",BD_UNIDADES.POSICION_INVENTARIO"
			sql=sql+",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"
			sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"
			sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"
			sql=sql+",(SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_NEGOCIO"
			sql=sql+",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"
			sql=sql+",C_CLIENTES.DESC_CLIENTE"
			sql=sql+",CASE WHEN ID_UNIDAD IN (SELECT ID_UNIDAD_ENTRADA FROM BD_SUSTITUCIONES WHERE ID_AR = " & idAR & ") THEN 1 ELSE 0 END AS IS_DISABLED"
			sql=sql+" FROM BD_UNIDADES"
			sql=sql+" INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
			sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
			sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"
			sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
			sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"
			sql=sql+" WHERE BD_UNIDADES.ID_STATUS_UNIDAD IN (17)"
			sql=sql+" AND BD_UNIDADES.STATUS = 'ACTIVO'"
			sql=sql+" AND BD_UNIDADES.ID_TIPO_RESPONSABLE = 4"
			sql=sql+" AND BD_UNIDADES.ID_RESPONSABLE = " & idNegocio
			sql=sql+" AND BD_UNIDADES.ID_CLIENTE = " & idCliente
			sql=sql+" AND C_PRODUCTOS.ID_TIPO_PRODUCTO = " & idTipoProducto
			sql=sql+" ORDER BY BD_UNIDADES.ID_UNIDAD DESC"
			
			set rs = con.executeQuery(sql)
			
			if not rs.EOF then
				''datos iniciales
				response.write("<r res=""OK"" val=""1"" desc=""Todo bien"" />")
				response.write("<data A=""" & idNegocio & """ B=""" & idCliente & """ C=""" & isIdReq & """ D=""" & isBom & """ ")
				response.write("E=""" & buttonDisplay & """ F=""" & cancelButtonDisplay & """ G=""" & edit & """ ")
				response.write("H=""" & idTipoProducto & """ />")
				
				do until rs.EOF
					response.write("<un I=""" & rs.fields("ID_UNIDAD") & """ J=""" & rs.fields("ID_MODELO") & """ ")
					response.write("K=""" & rs.fields("ID_PRODUCTO") & """ L=""" & rs.fields("NO_UNIDADES") & """ ")
					response.write("M=""" & rs.fields("DESC_CLIENTE") & """ N=""" & rs.fields("IS_DISABLED") & """ ")
					response.write("O=""" & Replace(rs.fields("DESC_MODELO"),"""", "'") & """ P=""" & rs.fields("DESC_MARCA") & """ ")
					response.write("Q=""" & rs.fields("NO_SERIE") & """ R=""" & rs.fields("NO_INVENTARIO") & """ ")
					response.write("S=""" & rs.fields("NO_IMEI") & """ T=""" & rs.fields("NO_EQUIPO") & """ ")
					response.write("U=""" & rs.fields("POSICION_INVENTARIO") & """ V=""" & rs.fields("DESC_STATUS_UNIDAD") & """ ")
					response.write("W=""" & rs.fields("ID_STATUS_UNIDAD") & """ X=""" & rs.fields("DESC_NEGOCIO") & """ />")
					
					rs.MoveNext
				loop
				
				rs.close
				set rs = nothing
			else
				response.write("<r res=""OK"" val=""2"" desc=""No hay instalaciones en el negocio"" />")
				response.write("<data A=""" & idNegocio & """ B=""" & idCliente & """ C=""" & isIdReq & """ D=""" & isBom & """ ")
				response.write("E=""" & buttonDisplay & """ F=""" & cancelButtonDisplay & """ G=""" & edit & """ ")
				response.write("H=""" & idTipoProducto & """ />")
			end if
			
			sql=""
			sql=sql+ "SELECT "
			sql=sql+ "	COUNT(*) AS IS_GRUPO "
			sql=sql+ "FROM BD_GRUPOS_CLIENTES "
			sql=sql+ "WHERE ID_CLIENTE = " & idCliente & "; "
	
			set rs = con.executeQuery(sql)

			if not rs.eof then
				isGrupo = rs.fields("IS_GRUPO")
			else
				isGrupo = "0"
			end if
			
			rs.close
			set rs = nothing	
				
			''unidades del broder
			sql="SELECT"
			sql=sql+" BD_UNIDADES.ID_UNIDAD"
			sql=sql+",BD_UNIDADES.ID_PRODUCTO"
			sql=sql+",BD_UNIDADES.ID_MODELO"
			sql=sql+",C_MODELOS.DESC_MODELO"
			sql=sql+",C_MARCAS.DESC_MARCA"
			sql=sql+",BD_UNIDADES.NO_SERIE"	
			sql=sql+",BD_UNIDADES.NO_INVENTARIO"
			sql=sql+",BD_UNIDADES.NO_IMEI"
			sql=sql+",BD_UNIDADES.POSICION_INVENTARIO"				
			sql=sql+",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"				
			sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"	
			sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"		
			sql=sql+",(SELECT NOMBRE + ' ' + PATERNO + ' ' + MATERNO FROM C_USUARIOS WHERE ID_USUARIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_USUARIO"									
			sql=sql+",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"		
			sql=sql+",C_CLIENTES.DESC_CLIENTE"	
			sql=sql+",CASE WHEN ID_UNIDAD IN (SELECT ID_UNIDAD_SALIDA FROM BD_SUSTITUCIONES WHERE ID_AR = " & idAR & ") THEN 1 ELSE 0 END AS IS_DISABLED"																												
			sql=sql+" FROM BD_UNIDADES"
			sql=sql+" INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
			sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
			sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"	
			sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
			sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"
			sql=sql+" LEFT JOIN BD_GRUPOS_CLIENTES ON C_CLIENTES.ID_CLIENTE = BD_GRUPOS_CLIENTES.ID_CLIENTE "
			sql=sql+" WHERE BD_UNIDADES.ID_STATUS_UNIDAD IN (15)"
			sql=sql+" AND BD_UNIDADES.STATUS = 'ACTIVO'" 
			sql=sql+" AND BD_UNIDADES.ID_TIPO_RESPONSABLE = 2"
			sql=sql+" AND BD_UNIDADES.ID_RESPONSABLE = " & idTecnico
			
			if isGrupo <> "0" then
				sql=sql+" AND BD_GRUPOS_CLIENTES.ID_GRUPO IN "
				sql=sql+" 								( "
				sql=sql+" 									SELECT ID_GRUPO "
				sql=sql+" 									FROM BD_GRUPOS_CLIENTES "
				sql=sql+" 									WHERE ID_CLIENTE = " & idCliente
				sql=sql+" 								) "
			else
				sql=sql+"AND BD_UNIDADES.ID_CLIENTE = " & idCliente
			end if
			
			sql=sql+" AND BD_UNIDADES.IS_RETIRO = 0"
			sql=sql+" AND C_PRODUCTOS.ID_TIPO_PRODUCTO = " & idTipoProducto
			sql=sql+" AND ( BD_UNIDADES.IS_DANIADA <> 1 OR BD_UNIDADES.IS_DANIADA IS NULL )"
	
			set rs = con.executeQuery(sql)
			
			if not rs.EOF then
				do until rs.EOF
                
					response.write("<ut I=""" & rs.fields("ID_UNIDAD") & """ J=""" & rs.fields("ID_MODELO") & """ ")
					response.write("K=""" & rs.fields("ID_PRODUCTO") & """ L=""" & rs.fields("NO_UNIDADES") & """ ")
					response.write("M=""" & rs.fields("DESC_CLIENTE") & """ N=""" & rs.fields("IS_DISABLED") & """ ")
					response.write("O=""" & Replace(rs.fields("DESC_MODELO"),"""", "'") & """ P=""" & rs.fields("DESC_MARCA") & """ ")
					response.write("Q=""" & rs.fields("NO_SERIE") & """ R=""" & rs.fields("NO_INVENTARIO") & """ ")
					response.write("S=""" & rs.fields("NO_IMEI") & """ W=""" & rs.fields("ID_STATUS_UNIDAD") & """ ")
					response.write("U=""" & rs.fields("POSICION_INVENTARIO") & """ V=""" & rs.fields("DESC_STATUS_UNIDAD") & """ />")
					
					rs.MoveNext
				loop
				
				rs.close
				set rs = nothing
			'else      <----Comentado para pruebas
			'	response.write("<otror res=""OK"" val=""3"" desc=""El Tecnico no tiene unidades"" />")
			end if
			
			sql="SELECT"
			sql=sql+" BD_SUSTITUCIONES.ID_AR"
			sql=sql+",BD_SUSTITUCIONES.ID_NEGOCIO"
			sql=sql+",BD_SUSTITUCIONES.ID_TECNICO"
			sql=sql+",BD_SUSTITUCIONES.ID_UNIDAD_ENTRADA"
			sql=sql+",BD_SUSTITUCIONES.ID_UNIDAD_SALIDA"
			sql=sql+",C_CLIENTES.DESC_CLIENTE"
			sql=sql+",C_USUARIOS.NOMBRE + ' ' + C_USUARIOS.PATERNO + ' ' + C_USUARIOS.MATERNO AS DESC_TECNICO"
			sql=sql+",(SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE BD_NEGOCIOS.ID_NEGOCIO = BD_SUSTITUCIONES.ID_NEGOCIO) AS DESC_NEGOCIO"
			sql=sql+",(SELECT NO_SERIE FROM BD_UNIDADES WHERE BD_UNIDADES.ID_UNIDAD = BD_SUSTITUCIONES.ID_UNIDAD_ENTRADA) AS NO_SERIE_ENTRADA"
			sql=sql+",(SELECT NO_SERIE FROM BD_UNIDADES WHERE BD_UNIDADES.ID_UNIDAD = BD_SUSTITUCIONES.ID_UNIDAD_SALIDA) AS NO_SERIE_SALIDA"
			sql=sql+",(SELECT NOMBRE + ' ' + PATERNO + ' ' + MATERNO FROM C_USUARIOS WHERE ID_USUARIO = BD_SUSTITUCIONES.ID_TECNICO) AS DESC_TECNICO"
			sql=sql+" FROM BD_SUSTITUCIONES"
			sql=sql+" INNER JOIN C_USUARIOS ON BD_SUSTITUCIONES.ID_TECNICO = C_USUARIOS.ID_USUARIO"
			sql=sql+" INNER JOIN BD_AR ON BD_SUSTITUCIONES.ID_AR = BD_AR.ID_AR"
			sql=sql+" INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
			sql=sql+" WHERE BD_SUSTITUCIONES.ID_AR = " & idAR
			sql=sql+" ORDER BY BD_SUSTITUCIONES.ID_SUSTITUCION"
			
			set rs = con.executeQuery(sql)
			
			if not rs.EOF then
				''sustituciones confirmadas
				
				do until rs.EOF
					response.write("<sus AU=""" & rs.fields("ID_AR") & """ AV=""" & rs.fields("ID_NEGOCIO") & """ ")
					response.write("AW=""" & rs.fields("ID_TECNICO") & """ AX=""" & rs.fields("ID_UNIDAD_ENTRADA") & """ ")
					response.write("AY=""" & rs.fields("ID_UNIDAD_SALIDA") & """ AZ=""" & rs.fields("DESC_TECNICO") & """ ")
					response.write("BA=""" & rs.fields("DESC_NEGOCIO") & """ BB=""" & rs.fields("NO_SERIE_ENTRADA") & """ ")
					response.write("BC=""" & rs.fields("NO_SERIE_SALIDA") & """ BD=""" & rs.fields("DESC_CLIENTE") & """ />")
					
					rs.moveNext
				loop
							
				rs.close
				set rs = nothing
			end if
		else
			response.write("<r res=""ERROR"" desc=""Error en el sql del isBom y demás"" />")
		end if
	else
		response.write("<r res=""ERROR"" desc=""Error en el sql del allowed"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error en el queryString"" />")
end if

response.write("</d>")
con.endConnection
%>