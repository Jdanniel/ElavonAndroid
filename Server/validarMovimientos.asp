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
	sql = "SELECT BD_AR.ID_SERVICIO,BD_AR.ID_STATUS_AR,IS_MOV_INV_ALLOWED,IS_CERRADA,C_PRODUCTOS.IS_INSUMOS,C_PRODUCTOS.IS_SPARE_PARTS"
	sql = sql + " FROM BD_AR"
	sql = sql + " INNER JOIN C_STATUS_AR ON BD_AR.ID_STATUS_AR = C_STATUS_AR.ID_STATUS_AR" 
	sql = sql + " INNER JOIN C_PRODUCTOS ON BD_AR.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO" 	
	sql = sql + " WHERE ID_AR = " & idAR 								
	set rs = con.executeQuery(sql)
	
	if not rs.eof then
		idServicio = rs.fields("ID_SERVICIO")
		isInsumos = cInt(rs.fields("IS_INSUMOS"))

		sql = "SELECT *"
		sql = sql + " FROM C_MOV_INV"
		sql = sql + " WHERE C_MOV_INV.STATUS = 'ACTIVO'"
		sql = sql + " AND C_MOV_INV.ID_MOV_INV IN (SELECT ID_MOV_INV FROM BD_SERVICIO_MOV_INV WHERE ID_SERVICIO = " & idServicio & " AND STATUS = 'ACTIVO')"														
		set rs = con.executeQuery(sql)
		
		if not rs.EOF then
			response.write("<r V=""OK"" D=""Todo bien"" />")
			Do until rs.eof
				response.write("<T V=""" & rs.fields("HREF") & """ />")

				rs.MoveNext
			loop
			
			if isInsumos = 1 then
				response.write("<T V=""insumos.asp"" />")
			end if
		else
			response.write("<r V=""OK"" D=""No hubo ninguno extra"" />")
			
			if isInsumos = 1 then
				response.write("<T V=""insumos.asp"" />")
			end if
		end if
	else
		response.write("<r V=""ERROR"" D=""No saco el idServicio etc"" />")
	end if	
else
	response.write("<r V=""ERROR"" D=""No se recibieron bien los parametros del url"" />")
end if

response.write("</d>")
con.endConnection
%>