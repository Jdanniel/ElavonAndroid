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


set con = new cConnection
con.startConnection(DB_INI)

sql="SELECT"
sql=sql+" BD_UNIDADES.ID_UNIDAD, "
sql=sql+" C_CLIENTES.DESC_CLIENTE, "
sql=sql+" BD_UNIDADES.ID_CLIENTE, "
sql=sql+" BD_UNIDADES.ID_SOLICITUD_RECOLECCION, "
sql=sql+" BD_UNIDADES.ID_PRODUCTO, "
sql=sql+" BD_UNIDADES.ID_USUARIO_ALTA, "
sql=sql+" C_PRODUCTOS.ID_TIPO_PRODUCTO, "
sql=sql+" BD_UNIDADES.IS_NUEVA, "
sql=sql+" BD_UNIDADES.IS_DANIADA, "
sql=sql+" C_MARCAS.DESC_MARCA, "
sql=sql+" C_MODELOS.DESC_MODELO, "
sql=sql+" BD_UNIDADES.NO_SERIE, "
sql=sql+" BD_UNIDADES.NO_INVENTARIO, "
sql=sql+" BD_UNIDADES.NO_IMEI, "
sql=sql+" BD_UNIDADES.NO_SIM, "
sql=sql+" BD_UNIDADES.NO_EQUIPO, "
sql=sql+" BD_UNIDADES.ID_LLAVE, "
sql=sql+" BD_UNIDADES.ID_SOFTWARE, "
sql=sql+" BD_UNIDADES.POSICION_INVENTARIO, "
sql=sql+" BD_UNIDADES.IS_RETIRO, "
sql=sql+" BD_UNIDADES.ID_STATUS_UNIDAD, "
sql=sql+" C_STATUS_UNIDAD.DESC_STATUS_UNIDAD, "
sql=sql+" BD_UNIDADES.STATUS, "
sql=sql+" BD_UNIDADES.FEC_ALTA, "
sql=sql+" (SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_NEGOCIO "		

sql=sql+" FROM BD_UNIDADES "
sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA "
sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO "
sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD "
sql=sql+" INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
sql=sql+" WHERE BD_UNIDADES.ID_UNIDAD="&id

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")
if not rs.eof then

	Dim llave, software
	llave = ""
	software = ""

	if NOT isNull (rs.fields("ID_LLAVE")) then
		sql = "SELECT DESC_LLAVE FROM C_LLAVES WHERE ID_LLAVE=" &rs.fields("ID_LLAVE")
		set rs2 = con.executeQuery(sql)
		
		if rs2.BOF and RS.EOF then
			llave = rs2.fields("DESC_LLAVE")
		else
			llave = ""
		end if
	end if

	if NOT isNULL( rs.fields("ID_SOFTWARE")) then
			sql = "SELECT DESC_SOFTWARE FROM C_SOFTWARE WHERE ID_SOFTWARE=" &rs.fields("ID_SOFTWARE")
			set rs2 = con.executeQuery(sql)
			
			if rs2.BOF and RS.EOF then
				software = rs2.fields("DESC_SOFTWARE")
			else
				software = ""
			end if		
	end if

	'' A = ID_UNIDAD
	'' B = ID_SOLICITUD_RECOLECCION
	'' C = ID_PRODUCTO
	'' D = IS_NUEVA
	'' E = IS_DANIADA
	'' F = IS_RETIRO
	'' G = ID_CLIENTE
	'' H = ID_TIPO_PRODUCTO
	'' I = ID_STATUS_UNIDAD
	'' J = ID_USUARIO_ALTA
	'' K = DESC_CLIENTE
	'' L = DESC_MARCA
	'' M = DESC_MODELO
	'' N = NO_SERIE
	'' O = NO_INVENTARIO
	'' P = NO_IMEI
	'' Q = NO_SIM
	'' R = NO_EQUIPO
	'' S = DESC_LLAVE
	'' T = DESC_SOFTWARE
	'' U = POSICION_INVENTARIO
	'' V = DESC_STATUS_UNIDAD
	'' W = STATUS
	'' X = FEC_ALTA

	response.write "<e"
	response.write " A="""&rs.fields("ID_UNIDAD")&""""
	response.write " B="""&rs.fields("ID_SOLICITUD_RECOLECCION")&""""
	response.write " C="""&rs.fields("ID_PRODUCTO")&""""
	response.write " D="""&rs.fields("IS_NUEVA")&""""
	response.write " E="""&rs.fields("IS_DANIADA")&""""
	response.write " F="""&rs.fields("IS_RETIRO")&""""
	response.write " G="""&rs.fields("ID_CLIENTE")&""""
	response.write " H="""&rs.fields("ID_TIPO_PRODUCTO")&""""
	response.write " I="""&rs.fields("ID_STATUS_UNIDAD")&""""
	response.write " J="""&rs.fields("ID_USUARIO_ALTA")&""""
	response.write ">"

	response.write("<K><![CDATA["&rs.fields("DESC_CLIENTE")&"]]> </K>")
	response.write("<L><![CDATA["&rs.fields("DESC_MARCA")&"]]> </L>")
	response.write("<M><![CDATA["&rs.fields("DESC_MODELO")&"]]> </M>")
	response.write("<N><![CDATA["&rs.fields("NO_SERIE")&"]]> </N>")
	response.write("<O><![CDATA["&rs.fields("NO_INVENTARIO")&"]]> </O>")
	response.write("<P><![CDATA["&rs.fields("NO_IMEI")&"]]> </P>")
	response.write("<Q><![CDATA["&rs.fields("NO_SIM")&"]]> </Q>")
	response.write("<R><![CDATA["&rs.fields("NO_EQUIPO")&"]]> </R>")
	response.write("<S><![CDATA["&llave&"]]> </S>")
	response.write("<T><![CDATA["&software&"]]> </T>")
	response.write("<U><![CDATA["&rs.fields("POSICION_INVENTARIO")&"]]> </U>")
	response.write("<V><![CDATA["&rs.fields("DESC_STATUS_UNIDAD")&"]]> </V>")
	response.write("<W><![CDATA["&rs.fields("STATUS")&"]]> </W>")
	response.write("<X><![CDATA["&rs.fields("FEC_ALTA")&"]]> </X>")
	response.write("</e>")

end if


sql="SELECT"
sql=sql+" C_ACCESORIOS.DESC_ACCESORIO, "
sql=sql+" C_ACCESORIOS.DESCRIPCION, "
sql=sql+" C_ACCESORIOS.STATUS "

sql=sql+" FROM BD_UNIDAD_ACCESORIO "

sql=sql+" INNER JOIN C_ACCESORIOS ON BD_UNIDAD_ACCESORIO.ID_ACCESORIO = C_ACCESORIOS.ID_ACCESORIO "
sql=sql+" WHERE BD_UNIDAD_ACCESORIO.ID_UNIDAD="&id

set rs = con.executeQuery(sql)
response.write("<as>")

'' Y  = DESC_ACCESORIO
'' Z  = DESCRIPCION
'' AA = STATUS

if not rs.eof then

	Do until rs.EOF

		response.write("<a>")
		response.write("<Y><![CDATA["&rs.fields("DESC_ACCESORIO")&"]]> </Y>")
		response.write("<Z><![CDATA["&rs.fields("DESCRIPCION")&"]]> </Z>")
		response.write("<AA><![CDATA["&rs.fields("STATUS")&"]]> </AA>")
		response.write("</a>")

	rs.MoveNext
	loop

end if
response.write("</as>")

response.write("</d>")

rs.close
set rs = nothing
con.endConnection
%>