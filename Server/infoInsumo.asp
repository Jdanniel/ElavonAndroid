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
	sql = "SELECT ID_CLIENTE FROM BD_AR WHERE ID_AR = " & idAR 								
	set rs = con.executeQuery(sql)
	
	if not rs.eof then
		idCliente = rs.fields("ID_CLIENTE")
			
		rs.close
		set rs = nothing
			
		sql = "SELECT ISNULL(COUNT(ID_AR_INSUMO),0) AS NO_INSUMOS_ROLLO" &_ 
		      " FROM BD_AR_INSUMO" &_
			  " INNER JOIN C_INSUMOS ON BD_AR_INSUMO.ID_INSUMO = C_INSUMOS.ID_INSUMO" &_
			  " WHERE ID_AR = " & idAR &_
			  " AND C_INSUMOS.ID_TIPO_INSUMO = 1" 								
		set rs = con.executeQuery(sql)
	
		if not rs.eof then
			noInsumosRollo = rs.fields("NO_INSUMOS_ROLLO")
			
			rs.close
			set rs = nothing
			
			sql="SELECT"
			sql=sql+" BD_AR_INSUMO.ID_AR_INSUMO"	
			sql=sql+",C_INSUMOS.DESC_INSUMO"	
			sql=sql+",C_USUARIOS.NOMBRE + ' ' + C_USUARIOS.PATERNO + ' ' + C_USUARIOS.MATERNO AS DESC_USUARIO"
			sql=sql+",BD_AR_INSUMO.FEC_ALTA"
			sql=sql+",BD_AR_INSUMO.CANTIDAD"	
			sql=sql+",BD_AR_INSUMO.COSTO_UNITARIO"	
			sql=sql+",BD_AR_INSUMO.COSTO_TOTAL"			
			sql=sql+" FROM BD_AR_INSUMO" 
			sql=sql+" INNER JOIN C_INSUMOS ON BD_AR_INSUMO.ID_INSUMO = C_INSUMOS.ID_INSUMO"	
			sql=sql+" INNER JOIN C_USUARIOS ON BD_AR_INSUMO.ID_USUARIO_ALTA = C_USUARIOS.ID_USUARIO"	
			sql=sql+" WHERE BD_AR_INSUMO.ID_AR = " & idAR		
			set rs = con.executeQuery(sql)
			
			if not rs.EOF then
				response.write("<r res=""OK"" val=""1"" NO_INSUMOS_ROLLO=""" & noInsumosRollo & """ ID_CLIENTE=""" & idCliente & """ desc=""Todo bien"" />")
				
				do until rs.EOF
					response.write("<ins ID_AR_INSUMO=""" & rs.fields("ID_AR_INSUMO") & """ DESC_INSUMO=""" & rs.fields("DESC_INSUMO") & """ ")
					response.write("DESC_USUARIO=""" & rs.fields("DESC_USUARIO") & """ FEC_ALTA=""" & rs.fields("FEC_ALTA") & """ ")
					response.write("CANTIDAD=""" & rs.fields("CANTIDAD") & """ COSTO_UNITARIO=""" & rs.fields("COSTO_UNITARIO") & """ ")
					response.write("COSTO_TOTAL=""" & rs.fields("COSTO_TOTAL") & """ />")
					
					rs.MoveNext
				loop
			else
				response.write("<r res=""OK"" val=""2"" NO_INSUMOS_ROLLO=""" & noInsumosRollo & """ ID_CLIENTE=""" & idCliente & """ desc=""Todo bien pero sin insumos anteriores"" />")
			end if
		else
			response.write("<r res=""ERROR"" desc=""Error en el sql de los noInsumosRollo"" />")
		end if
	else
		response.write("<r res=""ERROR"" desc=""Error en el sql del idCliente"" />")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error en el queryString"" />")
end if


response.write("</d>")
con.endConnection
%>