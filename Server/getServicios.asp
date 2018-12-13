<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT "
sql=sql+"ID_SERVICIO, ID_CLIENTE, ID_TIPO_SERVICIO, ID_MONEDA, ID_TIPO_PRECIO, IS_INSUMOS_REQUIRED, IS_CAUSA_SOLUCION_REQUIRED, "
sql=sql+"IS_CAUSA_REQUIRED, IS_SOLUCION_REQUIRED, IS_TAS_REQUIRED, IS_OTORGANTE_TAS_REQUIRED, IS_NO_EQUIPO_REQUIRED, IS_NO_SERIE_REQUIRED, "
sql=sql+"IS_NO_INVENTARIO_REQUIRED, IS_ID_MODELO_REQUIRED, IS_FEC_LLEGADA_REQUIRED, IS_FEC_LLEGADA_TERCEROS_REQUIRED, "
sql=sql+"IS_FOLIO_SERVICIO_REQUIRED, IS_FEC_INI_INGENIERO_REQUIRED, IS_FEC_FIN_INGENIERO_REQUIRED, IS_OTORGANTE_VOBO_REQUIRED, "
sql=sql+"IS_OTORGANTE_VOBO_TERCEROS_REQUIRED, IS_INTENSIDAD_SENIAL_REQUIRED, IS_IS_SIM_REMPLAZADA_REQUIRED, IS_FOLIO_SERVICIO_RECHAZO_REQUIRED, "
sql=sql+"IS_OTORGANTE_VOBO_RECHAZO_REQUIRED, IS_FALLA_ENCONTRADA_REQUIRED, IS_OTORGANTE_VOBO_CLIENTE_REQUIRED, IS_MOTIVO_COBRO_REQUIRED, "
sql=sql+"IS_IS_SOPORTE_CLIENTE_REQUIRED, IS_OTORGANTE_SOPORTE_CLIENTE_REQUIRED, IS_IS_BOLETIN_REQUIRED, IS_CADENA_CIERRE_ESCRITA_REQUIRED, "
sql=sql+"IS_DOWNTIME, DESC_SERVICIO, IS_CIERRE_PDA, IS_APLICACION_REQUIRED, IS_VERSION_REQUIRED, IS_CAJA_REQUIRED "
sql=sql+"FROM C_SERVICIOS "
sql=sql+"WHERE STATUS = 'ACTIVO'"

set rs = con.executeQuery(sql)


'' A = ID_SERVICIO
'' B = ID_CLIENTE
'' C = ID_TIPO_SERVICIO
'' D = ID_MONEDA
'' E = ID_TIPO_PRECIO
'' F = IS_INSUMOS_REQUIRED
'' G = IS_CAUSA_SOLUCION_REQUIRED
'' H = IS_CAUSA_REQUIRED
'' I = IS_SOLUCION_REQUIRED
'' J = IS_TAS_REQUIRED
'' K = IS_OTORGANTE_TAS_REQUIRED
'' L = IS_NO_EQUIPO_REQUIRED
'' M = IS_NO_SERIE_REQUIRED
'' N = IS_NO_INVENTARIO_REQUIRED
'' O = IS_ID_MODELO_REQUIRED
'' P = IS_FEC_LLEGADA_REQUIRED
'' Q = IS_FEC_LLEGADA_TERCEROS_REQUIRED
'' R = IS_FOLIO_SERVICIO_REQUIRED
'' S = IS_FEC_INI_INGENIERO_REQUIRED
'' T = IS_FEC_FIN_INGENIERO_REQUIRED
'' U = IS_OTORGANTE_VOBO_REQUIRED
'' V = IS_OTORGANTE_VOBO_TERCEROS_REQUIRED
'' W = IS_INTENSIDAD_SENIAL_REQUIRED
'' X = IS_IS_SIM_REMPLAZADA_REQUIRED
'' Y = IS_FOLIO_SERVICIO_RECHAZO_REQUIRED
'' Z = IS_OTORGANTE_VOBO_RECHAZO_REQUIRED
'' AA = IS_FALLA_ENCONTRADA_REQUIRED
'' AB = IS_OTORGANTE_VOBO_CLIENTE_REQUIRED
'' AC = IS_MOTIVO_COBRO_REQUIRED
'' AD = IS_IS_SOPORTE_CLIENTE_REQUIRED
'' AE = IS_OTORGANTE_SOPORTE_CLIENTE_REQUIRED
'' AF = IS_IS_BOLETIN_REQUIRED
'' AG = IS_CADENA_CIERRE_ESCRITA_REQUIRED
'' AH = IS_DOWNTIME
'' AI = IS_CIERRE_PDA
'' AJ = IS_APLICACION_REQUIRED
'' AK = IS_VERSION_REQUIRED
'' AL = IS_CAJA_REQUIRED
'' AM = DESC_SERVICIO

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if not rs.EOF then
	Do until rs.EOF
		response.write("<ser A= """ & rs.fields("ID_SERVICIO") & """ B= """ & rs.fields("ID_CLIENTE") & """ ")
		response.write("C= """ & rs.fields("ID_TIPO_SERVICIO") & """ D= """ & rs.fields("ID_MONEDA") & """ ")
		response.write("E = """ & rs.fields("ID_TIPO_PRECIO") & """ F= """ & rs.fields("IS_INSUMOS_REQUIRED") & """ ")
		response.write("G = """ & rs.fields("IS_CAUSA_SOLUCION_REQUIRED") & """ H= """ & rs.fields("IS_CAUSA_REQUIRED") & """ ")
		response.write("I = """ & rs.fields("IS_SOLUCION_REQUIRED") & """ J= """ & rs.fields("IS_TAS_REQUIRED") & """ ")
		response.write("K = """ & rs.fields("IS_OTORGANTE_TAS_REQUIRED") & """ L= """ & rs.fields("IS_NO_EQUIPO_REQUIRED") & """ ")
		response.write("M = """ & rs.fields("IS_NO_SERIE_REQUIRED") & """ N= """ & rs.fields("IS_NO_INVENTARIO_REQUIRED") & """ ")
		response.write("O = """ & rs.fields("IS_ID_MODELO_REQUIRED") & """ P= """ & rs.fields("IS_FEC_LLEGADA_REQUIRED") & """ ")
		response.write("Q = """ & rs.fields("IS_FEC_LLEGADA_TERCEROS_REQUIRED") & """ R= """ & rs.fields("IS_FOLIO_SERVICIO_REQUIRED") & """ ")
		response.write("S = """ & rs.fields("IS_FEC_INI_INGENIERO_REQUIRED") & """ T= """ & rs.fields("IS_FEC_FIN_INGENIERO_REQUIRED") & """ ")
		response.write("U = """ & rs.fields("IS_OTORGANTE_VOBO_REQUIRED") & """ V= """ & rs.fields("IS_OTORGANTE_VOBO_TERCEROS_REQUIRED") & """ ")
		response.write("W = """ & rs.fields("IS_INTENSIDAD_SENIAL_REQUIRED") & """ X = """ & rs.fields("IS_IS_SIM_REMPLAZADA_REQUIRED") & """ ")
		response.write("Y = """ & rs.fields("IS_FOLIO_SERVICIO_RECHAZO_REQUIRED") & """ Z = """ & rs.fields("IS_OTORGANTE_VOBO_RECHAZO_REQUIRED") & """ ")
		response.write("AA = """ & rs.fields("IS_FALLA_ENCONTRADA_REQUIRED") & """ AB= """ & rs.fields("IS_OTORGANTE_VOBO_CLIENTE_REQUIRED") & """ ")
		response.write("AC = """ & rs.fields("IS_MOTIVO_COBRO_REQUIRED") & """ AD = """ & rs.fields("IS_IS_SOPORTE_CLIENTE_REQUIRED") & """ ")
		response.write("AE = """ & rs.fields("IS_OTORGANTE_SOPORTE_CLIENTE_REQUIRED") & """ AF = """ & rs.fields("IS_IS_BOLETIN_REQUIRED") & """ ")
		response.write("AG = """ & rs.fields("IS_CADENA_CIERRE_ESCRITA_REQUIRED") & """ AH = """ & rs.fields("IS_DOWNTIME") & """ ")
		response.write("AI=""" & rs.fields("IS_CIERRE_PDA") & """ ")
		
		miVar = "" & rs.fields("IS_APLICACION_REQUIRED")
		if miVar="" then
			response.write("AJ=""0"" ")
		else
			response.write("AJ=""" & rs.fields("IS_APLICACION_REQUIRED") & """ ")
		end if
		
		miVar = "" & rs.fields("IS_VERSION_REQUIRED")
		if miVar="" then
			response.write("AK=""0"" ")
		else
			response.write("AK=""" & rs.fields("IS_VERSION_REQUIRED") & """ ")
		end if
		miVar = "" & rs.fields("IS_CAJA_REQUIRED")
		if miVar="" then	 
			response.write("AL=""0"" >")
		else
			response.write("AL=""" & rs.fields("IS_CAJA_REQUIRED") & """ >")
		end if
		response.write("<![CDATA[" & rs.fields("DESC_SERVICIO") & "]]>")
		response.write("</ser>")

		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>
