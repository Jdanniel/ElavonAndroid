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

Dim edit
Dim idTipoProducto

idTipoProducto = 2

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if idAR <> -1 then
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
		
		sql = "SELECT BD_AR.ID_NEGOCIO"
		sql=sql+ " ,BD_AR.ID_CLIENTE"
		sql=sql+ " ,C_CLIENTES.IS_ID_REQ"
		sql=sql+ " ,ISNULL(C_PRODUCTOS.IS_BOM, -1) AS IS_BOM"
		sql=sql+ " FROM BD_AR"
		sql=sql+ " INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
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
			
			'' Verificar Instalaciones Previas
			sql = "SELECT CASE WHEN (SELECT COUNT(*)"
			sql = sql + "			 FROM BD_INSTALACIONES"
			sql = sql + "			 WHERE " & idAR & " = BD_INSTALACIONES.ID_AR) = 0"
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
			
			''valores iniciales
			response.write("<r res=""OK"" desc=""Todo bien"" />")
			response.write("<data A=""" & idNegocio & """ B=""" & idCliente & """ C=""" & isIdReq & """ D=""" & isBom & """ ")
			response.write("E=""" & buttonDisplay & """ F=""" & cancelButtonDisplay & """ G=""" & edit & """ ")
			response.write("H=""" & idTipoProducto & """ />")
				
			''sacar los valores de las instalaciones confirmadas
			sql="SELECT"
			sql=sql+" BD_UNIDADES.ID_UNIDAD"
			sql=sql+",BD_UNIDADES.ID_MODELO"
			sql=sql+",C_MODELOS.DESC_MODELO"
			sql=sql+",C_MARCAS.DESC_MARCA"
			sql=sql+",BD_UNIDADES.NO_SERIE"
			sql=sql+",BD_UNIDADES.NO_INVENTARIO"
			sql=sql+",BD_UNIDADES.NO_IMEI"
			sql=sql+",BD_UNIDADES.NO_EQUIPO"
			sql=sql+",BD_UNIDADES.POSICION_INVENTARIO"
			sql=sql+",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"
			sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"
			sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"
			sql=sql+",(SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_NEGOCIO"
			sql=sql+",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"
			sql=sql+",C_CLIENTES.DESC_CLIENTE"
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
			sql=sql+" AND BD_UNIDADES.ID_UNIDAD IN (SELECT DISTINCT ID_UNIDAD FROM BD_INSTALACIONES WHERE ID_AR = " & idAR & ")"
			sql=sql+" AND C_PRODUCTOS.ID_TIPO_PRODUCTO = " & idTipoProducto
			sql=sql+" ORDER BY BD_UNIDADES.ID_UNIDAD DESC"
			
			set rs = con.executeQuery(sql)
			
			if not rs.EOF then
				do until rs.EOF
					response.write("<u A=""" & rs.fields("ID_UNIDAD") & """ B=""" & rs.fields("ID_MODELO") & """ ")
					response.write("C=""" & rs.fields("DESC_MODELO") & """ D=""" & rs.fields("DESC_MARCA") & """ ")
					response.write("E=""" & rs.fields("NO_SERIE") & """ F=""" & rs.fields("NO_INVENTARIO") & """ ")
					response.write("G=""" & rs.fields("NO_IMEI") & """ H=""" & rs.fields("NO_EQUIPO") & """ ")
					response.write("I=""" & rs.fields("POSICION_INVENTARIO") & """ J=""" & rs.fields("DESC_STATUS_UNIDAD") & """ ")
					response.write("K=""" & rs.fields("ID_STATUS_UNIDAD") & """ L=""" & rs.fields("DESC_NEGOCIO") & """ />")
					
					rs.MoveNext
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