<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

Dim id, status
if request.QueryString("i") <> "" then
	id = request.QueryString("i")
else
	id = 0
end if



set con = new cConnection
con.startConnection(DB_INI)

sql="SELECT"
sql=sql+" BD_ENVIOS.ID_ENVIO, "
sql=sql+" BD_ENVIOS.ID_TIPO_RESPONSABLE_DESTINO, "
sql=sql+" C_TIPO_RESPONSABLE.DESC_TIPO_RESPONSABLE, "
sql=sql+" BD_ENVIOS.ID_RESPONSABLE_DESTINO, "
sql=sql+" BD_ENVIOS.ID_SERVICIO_MENSAJERIA, "
sql=sql+" C_SERVICIO_MENSAJERIA.DESC_SERVICIO_MENSAJERIA, "
sql=sql+" BD_ENVIOS.NO_GUIA, "
sql=sql+" BD_ENVIOS.FEC_ENVIO "
sql=sql+" FROM BD_ENVIOS "
sql=sql+" INNER JOIN C_TIPO_RESPONSABLE ON BD_ENVIOS.ID_TIPO_RESPONSABLE_DESTINO = C_TIPO_RESPONSABLE.ID_TIPO_RESPONSABLE "
sql=sql+" INNER JOIN C_SERVICIO_MENSAJERIA ON BD_ENVIOS.ID_SERVICIO_MENSAJERIA = C_SERVICIO_MENSAJERIA.ID_SERVICIO_MENSAJERIA "
sql=sql+" WHERE BD_ENVIOS.ID_ENVIO="&id



set rs = con.executeQuery(sql)
Dim origen

if rs.fields("ID_TIPO_RESPONSABLE_DESTINO")=1 then
	sql="SELECT"
	sql=sql+" C_ALMACENES.DESC_ALMACEN "
	sql=sql+" FROM C_ALMACENES "
	sql=sql+" WHERE C_ALMACENES.ID_ALMACEN="&rs.fields("ID_RESPONSABLE_DESTINO")
	set rs2 = con.executeQuery(sql)
	origen = rs2.fields("DESC_ALMACEN")
end if

if rs.fields("ID_TIPO_RESPONSABLE_DESTINO")=2 then
	sql="SELECT"
	sql=sql+" C_USUARIOS.NOMBRE, "
	sql=sql+" C_USUARIOS.PATERNO, "
	sql=sql+" C_USUARIOS.MATERNO "
	sql=sql+" FROM C_USUARIOS "
	sql=sql+" WHERE C_USUARIOS.ID_USUARIO="&rs.fields("ID_RESPONSABLE_DESTINO")
	set rs2 = con.executeQuery(sql)
	origen = rs2.fields("NOMBRE")&" "&rs2.fields("PATERNO")&" "&rs2.fields("MATERNO")
end if

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if not rs.eof then

	Do until rs.EOF

		''AA = ID_ENVIO
		''AB = DESC_TIPO_RESPONSABLE
		''AC = RESPONSABLE
		''AD = DESC_SERVICIO_MENSAJERIA
		''AE = NO_GUIA
		''AF = FEC_ENVIO
	
		response.write("<e AA="""&rs.fields("ID_ENVIO")&""">")
		response.write("<AB><![CDATA["&rs.fields("DESC_TIPO_RESPONSABLE")&"]]></AB>")
		response.write("<AC><![CDATA["&origen&"]]></AC>")
		response.write("<AD><![CDATA["&rs.fields("DESC_SERVICIO_MENSAJERIA")&"]]></AD>")
		response.write("<AE><![CDATA["&rs.fields("NO_GUIA")&"]]></AE>")
		response.write("<AF><![CDATA["&rs.fields("FEC_ENVIO")&"]]></AF>")

		if NOT isNull(rs.fields("ID_ENVIO")) then
			sql2 = "SELECT ID_UNIDAD FROM BD_ENVIO_UNIDAD"
			sql2 = sql2+" WHERE ID_ENVIO = " &rs.fields("ID_ENVIO")
			set rs3 = con.executeQuery(sql2)

			if not rs3.eof then
				response.write("<u>")

				do until rs3.eof
					sql3="SELECT"
					sql3=sql3+" BD_UNIDADES.ID_UNIDAD, "
					sql3=sql3+" C_CLIENTES.DESC_CLIENTE, "
					sql3=sql3+" BD_UNIDADES.ID_SOLICITUD_RECOLECCION, "
					sql3=sql3+" BD_UNIDADES.ID_PRODUCTO, "
					sql3=sql3+" BD_UNIDADES.IS_NUEVA, "
					sql3=sql3+" C_MARCAS.DESC_MARCA, "
					sql3=sql3+" C_MODELOS.DESC_MODELO, "
					sql3=sql3+" BD_UNIDADES.NO_SERIE, "
					sql3=sql3+" BD_UNIDADES.NO_INVENTARIO, "
					sql3=sql3+" BD_UNIDADES.NO_IMEI, "
					sql3=sql3+" BD_UNIDADES.NO_SIM, "
					sql3=sql3+" BD_UNIDADES.NO_EQUIPO, "
					sql3=sql3+" BD_UNIDADES.ID_LLAVE, "
					sql3=sql3+" BD_UNIDADES.ID_SOFTWARE, "
					sql3=sql3+" BD_UNIDADES.POSICION_INVENTARIO, "
					sql3=sql3+" BD_UNIDADES.IS_RETIRO, "
					sql3=sql3+" BD_UNIDADES.ID_STATUS_UNIDAD, "
					sql3=sql3+" C_STATUS_UNIDAD.DESC_STATUS_UNIDAD, "
					sql3=sql3+" BD_UNIDADES.STATUS, "
					sql3=sql3+" BD_UNIDADES.FEC_ALTA "


					sql3=sql3+" FROM BD_UNIDADES "
					sql3=sql3+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
					sql3=sql3+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA "
					sql3=sql3+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO "
					sql3=sql3+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD "
					sql3=sql3+" WHERE BD_UNIDADES.ID_UNIDAD="&rs3.fields("ID_UNIDAD")
					sql3=sql3+" AND BD_UNIDADES.ID_STATUS_UNIDAD=16 "

					set rs4 = con.executeQuery(sql3)

					if NOT rs4.EOF then
						Dim llave, software
						llave = ""
						software = ""

						if NOT isNull (rs4.fields("ID_LLAVE")) then
							sqlExtra = "SELECT DESC_LLAVE FROM C_LLAVES WHERE ID_LLAVE=" &rs4.fields("ID_LLAVE")
							set rs5 = con.executeQuery(sqlExtra)
							llave = rs5.fields("DESC_LLAVE")
						end if

						if NOT isNULL( rs4.fields("ID_SOFTWARE")) then
							sqlExtra = "SELECT DESC_SOFTWARE FROM C_SOFTWARE WHERE ID_SOFTWARE=" &rs4.fields("ID_SOFTWARE")
							set rs5 = con.executeQuery(sqlExtra)
							software = rs5.fields("DESC_SOFTWARE")
						end if
						
						''A = ID_UNIDAD
						''B = ID_SOLICITUD_RECOLECCION
						''C = ID_PRODUCTO
						''D = IS_NUEVA
						''E = IS_RETIRO
						''F = ID_STATUS_UNIDAD
						''G = DESC_CLIENTE
						''H = DESC_MARCA
						''I = DESC_MODELO
						''J = NO_SERIE
						''K = NO_INVENTARIO
						''L = NO_IMEI
						''M = NO_SIM
						''N = NO_EQUIPO
						''O = DESC_LLAVE
						''P = DESC_SOFTWARE
						''Q = POSICION_INVENTARIO
						''R = DESC_STATUS_UNIDAD
						''S = STATUS
						''T = FEC_ALTA
						
						response.write "<u"
						response.write " A="""&rs4.fields("ID_UNIDAD")&""""
						response.write " B="""&rs4.fields("ID_SOLICITUD_RECOLECCION")&""""
						response.write " C="""&rs4.fields("ID_PRODUCTO")&""""
						response.write " D="""&rs4.fields("IS_NUEVA")&""""
						response.write " E="""&rs4.fields("IS_RETIRO")&""""
						response.write " F="""&rs4.fields("ID_STATUS_UNIDAD")&""""
						response.write ">"
						
						response.write("<G><![CDATA["&rs4.fields("DESC_CLIENTE")&"]]> </G>")
						response.write("<H><![CDATA["&rs4.fields("DESC_MARCA")&"]]> </H>")
						response.write("<I><![CDATA["&rs4.fields("DESC_MODELO")&"]]> </I>")
						response.write("<J><![CDATA["&rs4.fields("NO_SERIE")&"]]> </J>")
						response.write("<K><![CDATA["&rs4.fields("NO_INVENTARIO")&"]]> </K>")
						response.write("<L><![CDATA["&rs4.fields("NO_IMEI")&"]]> </L>")
						response.write("<M><![CDATA["&rs4.fields("NO_SIM")&"]]> </M>")
						response.write("<N><![CDATA["&rs4.fields("NO_EQUIPO")&"]]> </N>")
						response.write("<O><![CDATA["&llave&"]]> </O>")
						response.write("<P><![CDATA["&software&"]]> </P>")
						response.write("<Q><![CDATA["&rs4.fields("POSICION_INVENTARIO")&"]]> </Q>")
						response.write("<R><![CDATA["&rs4.fields("DESC_STATUS_UNIDAD")&"]]> </R>")
						response.write("<S><![CDATA["&rs4.fields("STATUS")&"]]> </S>")
						response.write("<T><![CDATA["&rs4.fields("FEC_ALTA")&"]]> </T>")
						response.write("</u>")
					end if

					rs4.close
					set rs4 = nothing

					rs3.moveNext
				loop

				response.write "</u>"
			end if

			rs3.close
			set rs3 = nothing
		end if

		response.write("</e>")
		rs.MoveNext
	loop
end if

response.write("</d>")

rs.close
set rs = nothing
con.endConnection

%>
