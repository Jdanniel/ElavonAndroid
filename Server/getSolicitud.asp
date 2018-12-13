<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
''Server.ScriptTimeout=3600
response.Charset="iso-8859-1"
response.ContentType="text/xml"

Dim id
if request.QueryString("i") <> "" then
	id = request.QueryString("i")
else
	id = 0
end if

Dim idModelo


set con = new cConnection
con.startConnection(DB_INI)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")


		sql="SELECT"
		sql=sql+" BD_AR.ID_AR, "
		sql=sql+" BD_AR.NO_AR, "
		sql=sql+" BD_AR.SEGMENTO, "
		sql=sql+" C_SEGMENTOS.IS_KEY_ACCOUNT, "
		sql=sql+" BD_AR.HORAS_GARANTIA, "
		sql=sql+" BD_AR.HORAS_ATENCION, "
		sql=sql+" BD_AR.ID_CLIENTE, "
		sql=sql+" C_CLIENTES.DESC_CLIENTE, "
		sql=sql+" C_CLIENTES.IS_WINCOR, "
		sql=sql+" BD_AR.NO_AFILIACION, "
		sql=sql+" BD_AR.FEC_ALTA AS FECALTA, "
		sql=sql+" BD_AR.FEC_GARANTIA, "
		sql=sql+" BD_AR.ID_SERVICIO, "
		sql=sql+" C_SERVICIOS.DESC_SERVICIO, "
		sql=sql+" BD_AR.SINTOMA, "
		sql=sql+" BD_AR.CONCEPTO, "
		sql=sql+" BD_AR.DESC_CORTA, "
		sql=sql+" BD_AR.BITACORA, "
		sql=sql+" BD_AR.NOTAS_REMEDY, "
		sql=sql+" BD_AR.DESC_EQUIPO, "
		sql=sql+" BD_AR.EQUIPO, "
		sql=sql+" BD_AR.NO_SERIE, "
		sql=sql+" BD_AR.DIRECCION, "
		sql=sql+" BD_AR.COLONIA, "
		sql=sql+" BD_AR.POBLACION, "
		sql=sql+" BD_AR.ESTADO, "
		sql=sql+" BD_AR.CP, "
		sql=sql+" BD_AR.DESC_NEGOCIO, "
		sql=sql+" BD_AR.ID_NEGOCIO, "
		sql=sql+" BD_AR.TELEFONO, "
		sql=sql+" BD_AR.ID_STATUS_AR, "
		sql=sql+" BD_AR.ID_PRODUCTO, "
		sql=sql+" BD_AR.ID_UNIDAD_ATENDIDA, "
		sql=sql+" BD_AR.FEC_ATENCION, "
		sql=sql+" BD_AR.CAJA, "
		sql=sql+" BD_AR.FEC_CIERRE,"
		sql=sql+" BD_AR.ID_STATUS_VALIDACION_PREFACTURACION,"
        sql=sql+" BD_BITACORA_VALIDACION_PREFACTURACION.FEC_ALTA,"
		sql=sql+" BD_BITACORA_VALIDACION_PREFACTURACION.COMENTARIO,"
		''NEW FIELDS (SIM AND CLAVE_RECHAZO) \( '.'   )
		sql=sql+" BD_AR.NO_SIM,"
		sql=sql+" BD_AR.CLAVE_RECHAZO"

		sql=sql+" FROM BD_AR "
		sql=sql+" INNER JOIN C_SEGMENTOS ON BD_AR.ID_SEGMENTO = C_SEGMENTOS.ID_SEGMENTO "
		sql=sql+" INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
		sql=sql+" INNER JOIN C_SERVICIOS ON BD_AR.ID_SERVICIO = C_SERVICIOS.ID_SERVICIO"
		sql=sql+" INNER JOIN C_STATUS_AR ON C_STATUS_AR.ID_STATUS_AR = BD_AR.ID_STATUS_AR"
		sql=sql+" LEFT JOIN BD_BITACORA_VALIDACION_PREFACTURACION ON BD_AR.ID_AR = BD_BITACORA_VALIDACION_PREFACTURACION.ID_AR"
		sql=sql+" WHERE BD_AR.ID_AR="&id
        sql=sql+" ORDER BY BD_BITACORA_VALIDACION_PREFACTURACION.FEC_ALTA DESC"

		set rs = con.executeQuery(sql)
	

	idModelo = -1			
	Dim fec_garantia,fec_alta,fec_atencion

	if not rs.eof then
		if not isnull(rs.fields("ID_UNIDAD_ATENDIDA")) then
			sqlModelo = "SELECT ID_MODELO FROM BD_UNIDADES WHERE ID_UNIDAD = " & rs.fields("ID_UNIDAD_ATENDIDA")

			set rsModelo = con.executeQuery(sqlModelo)
			idModelo = rsModelo.fields("ID_MODELO")
		end if


		if LEN(rs.fields("FECALTA"))<11 Then
			fec_alta = rs.fields("FECALTA") & " 12:00:00 AM"
		Else
			fec_alta = rs.fields("FECALTA")
		End If

		if LEN(rs.fields("FEC_ATENCION"))<11 Then
			fec_atencion = rs.fields("FEC_ATENCION") & " 12:00:00 AM"
		Else
			fec_atencion = rs.fields("FEC_ATENCION")
		End If

		if LEN(rs.fields("FEC_GARANTIA"))<11 Then
			fec_garantia = rs.fields("FEC_GARANTIA") & " 12:00:00 AM"
		Else
			fec_garantia = rs.fields("FEC_GARANTIA")
		End If


		response.write "<e"
		response.write " ID_AR="""&rs.fields("ID_AR")&""""
		response.write " IS_KEY_ACCOUNT="""&rs.fields("IS_KEY_ACCOUNT")&""""
		response.write " HORAS_GARANTIA="""&rs.fields("HORAS_GARANTIA")&""""
		response.write " HORAS_ATENCION="""&rs.fields("HORAS_ATENCION")&""""
		response.write " FEC_ALTA="""&fec_alta&""""
		response.write " FEC_ATENCION="""&fec_atencion&""""
		response.write " FEC_GARANTIA="""&fec_garantia&""""
		response.write " ID_STATUS_AR="""&rs.fields("ID_STATUS_AR")&""""
		response.write " ID_PRODUCTO="""&rs.fields("ID_PRODUCTO")&""""
		response.write " ID_UNIDAD_ATENDIDA="""&rs.fields("ID_UNIDAD_ATENDIDA")&""""
		response.write " ID_MODELO="""&idModelo&""""
		response.write " ID_CLIENTE="""&rs.fields("ID_CLIENTE")&""""
		response.write " ID_SERVICIO="""&rs.fields("ID_SERVICIO")&""""
		response.write " ID_NEGOCIO="""&rs.fields("ID_NEGOCIO")&""""
		response.write " FEC_CIERRE="""&rs.fields("FEC_CIERRE")&""""
		response.write " vPREFACTURACION="""&rs.fields("ID_STATUS_VALIDACION_PREFACTURACION")&""""
		response.write " vCOMENTARIO="""&rs.fields("COMENTARIO")&""""
		''NEW FIELDS (SIM AND CLAVE_RECHAZO) \( '.'   )
		response.write " NO_SIM="""&rs.fields("NO_SIM")&""""
		response.write " CLAVE_RECHAZO="""&rs.fields("CLAVE_RECHAZO")&""""
		response.write ">"


	response.write("<NO_AR><![CDATA["&rs.fields("NO_AR")&"]]> </NO_AR>")
	response.write("<DESC_CLIENTE><![CDATA["&rs.fields("DESC_CLIENTE")&"]]> </DESC_CLIENTE>")
	response.write("<NO_AFILIACION><![CDATA["&rs.fields("NO_AFILIACION")&"]]> </NO_AFILIACION>")
	response.write("<DESC_SERVICIO><![CDATA["&rs.fields("DESC_SERVICIO")&"]]> </DESC_SERVICIO>")
	response.write("<SINTOMA><![CDATA["&rs.fields("SINTOMA")&"]]> </SINTOMA>")
	response.write("<CONCEPTO><![CDATA["&rs.fields("CONCEPTO")&"]]> </CONCEPTO>")
	response.write("<DESC_CORTA><![CDATA["&rs.fields("DESC_CORTA")&"]]> </DESC_CORTA>")
	response.write("<BITACORA><![CDATA["&rs.fields("BITACORA")&"]]> </BITACORA>")
	response.write("<NOTAS_REMEDY><![CDATA["&rs.fields("NOTAS_REMEDY")&"]]> </NOTAS_REMEDY>")
	response.write("<DESC_EQUIPO><![CDATA["&rs.fields("DESC_EQUIPO")&"]]> </DESC_EQUIPO>")
	response.write("<EQUIPO><![CDATA["&rs.fields("EQUIPO")&"]]> </EQUIPO>")
	response.write("<NO_SERIE><![CDATA["&rs.fields("NO_SERIE")&"]]> </NO_SERIE>")
	response.write("<DIRECCION><![CDATA["&rs.fields("DIRECCION")&"]]> </DIRECCION>")
	response.write("<COLONIA><![CDATA["&rs.fields("COLONIA")&"]]> </COLONIA>")
	response.write("<POBLACION><![CDATA["&rs.fields("POBLACION")&"]]> </POBLACION>")
	response.write("<ESTADO><![CDATA["&rs.fields("ESTADO")&"]]> </ESTADO>")
	response.write("<CP><![CDATA["&rs.fields("CP")&"]]> </CP>")
	response.write("<DESC_NEGOCIO><![CDATA["&rs.fields("DESC_NEGOCIO")&"]]></DESC_NEGOCIO>")
	response.write("<TELEFONO><![CDATA["&rs.fields("TELEFONO")&"]]> </TELEFONO>")
	response.write("<CAJA><![CDATA["&rs.fields("CAJA")&"]]> </CAJA>")
	response.write("</e>")
end if
	
response.write("</d>")

rs.close
set rs = nothing
con.endConnection
%>