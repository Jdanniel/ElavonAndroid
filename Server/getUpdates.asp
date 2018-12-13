<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->
<!--#include file="md5.asp"-->

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

dim ultimaHoraAct
dim status


sql="select CONVERT(VARCHAR(20), MAX( FEC_ALTA_HORAS_ATENCION), 100) from BD_AR"
set rs = con.executeQuery(sql)

ultimaHoraAct = rs.fields(0)
''ultimaHoraAct="2009-02-19 11:00:00"

sql = "SELECT STATUS FROM C_USUARIOS WHERE ID_USUARIO = " & id
set rs = con.executeQuery(sql)

status = rs.fields(0)

'' ARs nuevas, abiertas,cerradas, pendientes
sql = "SELECT BD_AR.ID_AR, BD_AR.ID_STATUS_AR, C_STATUS_AR.IS_BB_NUEVAS, C_STATUS_AR.IS_BB_CERRADAS, "
sql = sql + "C_STATUS_AR.IS_BB_ABIERTAS, C_STATUS_AR.IS_BB_PENDIENTES , BD_AR.ID_STATUS_VALIDACION_PREFACTURACION FROM BD_AR "
sql = sql + "INNER JOIN C_STATUS_AR ON BD_AR.ID_STATUS_AR = C_STATUS_AR.ID_STATUS_AR "
sql = sql + "WHERE BD_AR.ID_TECNICO = " & id & " ORDER BY BD_AR.ID_AR "


set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d h=""" & ultimaHoraAct & """ v=""1.0.21"" t=""600"" s=""" & status & """>")



Dim objMD5
Dim num_c,num_a,num_n,num_p,num_u,num_ep,num_er
Dim str_n,str_a,str_c,str_p,str_er,str_ep,str_u
Dim fec_al,fec_ic,fec_s,fec_ps,fec_i,fec_m,fec_mod,fec_v,fec_sp,fec_mm,fec_ms,fec_cm,fec_ser,fec_ca,fec_sol,fec_ser_ca,fec_ser_sol,fec_cre,fec_gr,fec_gc
Dim fec_ll,fec_sw,fec_mll,fec_msw,fec_seq,fec_csw
num_c = 0
num_a = 0
num_n = 0
num_p = 0
num_u = 0
num_ep = 0
num_er = 0
num_dir = 0
str_n = ""
str_a = ""
str_c = ""
str_p = ""
str_u = ""
str_ep = ""
str_er = ""
str_dir = ""
fec_al = ""
fec_ic = ""
fec_s = ""
fec_ps = ""
fec_i = ""
fec_m = ""
fec_mod = ""
fec_v = ""
fec_sp = ""
fec_mm = ""
fec_ms = ""
fec_cm = ""
fec_ser = ""
fec_ca = ""
fec_sol = ""
fec_ser_sol = ""
fec_ser_ca = ""
fec_cre = ""
fec_gr = ""
fec_gc = ""
fec_ll = ""
fec_sw = ""
fec_mll = ""
fec_msw = ""

if not rs.eof then
	Do until rs.EOF
		if rs.fields("IS_BB_NUEVAS") = 1 then
			num_n = num_n + 1
			str_n = str_n & rs.fields("ID_AR") & rs.fields("ID_STATUS_AR")
		elseif rs.fields("IS_BB_CERRADAS") = 1 then
			num_c = num_c + 1
			str_c = str_c & rs.fields("ID_AR") & rs.fields("ID_STATUS_AR") & rs.fields("ID_STATUS_VALIDACION_PREFACTURACION")
		elseif rs.fields("IS_BB_ABIERTAS") = 1 then
			num_a = num_a + 1
			str_a = str_a & rs.fields("ID_AR") & rs.fields("ID_STATUS_AR")
		elseif rs.fields("IS_BB_PENDIENTES") = 1 then
			num_p = num_p + 1
			str_p = str_p & rs.fields("ID_AR") & rs.fields("ID_STATUS_AR")
		end if

		rs.MoveNext
	Loop
end if

response.write("<n l="""&num_n&""" md5="""& md5(str_n) &"""/>")
response.write("<a l="""&num_a&""" md5="""& md5(str_a) &"""/>")
response.write("<c l="""&num_c&""" md5="""& md5(str_c) &"""/>")
response.write("<p l="""&num_p&""" md5="""& md5(str_p) &"""/>")


'' Unidades
sql="SELECT"
sql=sql+" BD_UNIDADES.ID_UNIDAD,"
sql=sql+" BD_UNIDADES.ID_STATUS_UNIDAD"
sql=sql+" FROM BD_UNIDADES"
sql=sql+" WHERE BD_UNIDADES.ID_TIPO_RESPONSABLE=2 AND BD_UNIDADES.ID_RESPONSABLE="&id& " AND ID_STATUS_UNIDAD=15 AND BD_UNIDADES.STATUS<>'BORRADO' "


set rs = con.executeQuery(sql)


if not rs.eof then

	Do until rs.EOF

		num_u = num_u + 1
		str_u = str_u & rs.fields("ID_UNIDAD")&rs.fields("ID_STATUS_UNIDAD")

	rs.MoveNext
	loop
end if
response.write("<u l="""&num_u&""" md5="""& md5(str_u) &"""/>")


'' Envios Pendientes
sql="SELECT"
sql=sql+" BD_ENVIOS.ID_ENVIO,count(BD_UNIDADES.ID_STATUS_UNIDAD) as num  "
sql=sql+" FROM BD_ENVIOS "
sql=sql+" INNER JOIN BD_ENVIO_UNIDAD ON BD_ENVIOS.ID_ENVIO = BD_ENVIO_UNIDAD.ID_ENVIO "
sql=sql+" INNER JOIN BD_UNIDADES ON BD_ENVIO_UNIDAD.ID_UNIDAD = BD_UNIDADES.ID_UNIDAD "
sql=sql+" WHERE BD_ENVIOS.ID_STATUS_ENVIO=3 AND BD_ENVIOS.ID_TIPO_RESPONSABLE_ORIGEN=2 AND BD_ENVIOS.ID_RESPONSABLE_ORIGEN="&id
sql=sql+" AND ID_STATUS_UNIDAD=16 "
sql=sql+" GROUP BY BD_ENVIOS.ID_ENVIO,BD_UNIDADES.ID_STATUS_UNIDAD "

set rs = con.executeQuery(sql)

if not rs.eof then

	Do until rs.EOF

		num_ep = num_ep + 1
		str_ep = str_ep & rs.fields("ID_ENVIO")& rs.fields("num")

	rs.MoveNext
	loop
end if
response.write("<ep l="""&num_ep&""" md5="""& md5(str_ep) &"""/>")


'' Envios Recibidos
sql="SELECT"
sql=sql+" BD_ENVIOS.ID_ENVIO,count(BD_UNIDADES.ID_STATUS_UNIDAD) as num  "
sql=sql+" FROM BD_ENVIOS "
sql=sql+" INNER JOIN BD_ENVIO_UNIDAD ON BD_ENVIOS.ID_ENVIO = BD_ENVIO_UNIDAD.ID_ENVIO "
sql=sql+" INNER JOIN BD_UNIDADES ON BD_ENVIO_UNIDAD.ID_UNIDAD = BD_UNIDADES.ID_UNIDAD "
sql=sql+" WHERE BD_ENVIOS.ID_STATUS_ENVIO=3 AND BD_ENVIOS.ID_TIPO_RESPONSABLE_DESTINO=2 AND BD_ENVIOS.ID_RESPONSABLE_DESTINO="&id
sql=sql+" AND ID_STATUS_UNIDAD=16 "
sql=sql+" GROUP BY BD_ENVIOS.ID_ENVIO,BD_UNIDADES.ID_STATUS_UNIDAD "
set rs = con.executeQuery(sql)


if not rs.eof then

	Do until rs.EOF

		num_er = num_er + 1
		str_er = str_er & rs.fields("ID_ENVIO") & rs.fields("num")

	rs.MoveNext
	loop
end if
response.write("<er l="""&num_er&""" md5="""& md5(str_er) &"""/>")

''direcciones
sql="SELECT BD_DIRECCIONES.ID_DIRECCION,BD_DIRECCIONES.DIRECCION,BD_DIRECCIONES.COLONIA,BD_DIRECCIONES.POBLACION,BD_DIRECCIONES.ESTADO "
sql=sql+" FROM BD_DIRECCIONES  "
sql=sql+" INNER JOIN BD_DIRECCIONES_RELACIONES ON BD_DIRECCIONES.ID_DIRECCION = BD_DIRECCIONES_RELACIONES.ID_DIRECCION "
sql=sql+" WHERE BD_DIRECCIONES_RELACIONES.ID_RESPONSABLE = "&id&" AND BD_DIRECCIONES_RELACIONES.ID_TIPO_RESPONSABLE=2 "
set rs = con.executeQuery(sql)

if not rs.eof then

	Do until rs.EOF

		num_dir = num_dir + 1
		str_dir = str_dir & rs.fields("DIRECCION") & rs.fields("COLONIA")& rs.fields("POBLACION")& rs.fields("ESTADO")

	rs.MoveNext
	loop
end if
response.write("<dir l="""&num_dir&""" md5="""& md5(str_dir) &"""/>")




''almacenes
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 1"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_al = rs.fields(0)
end if

response.write("<al d=""" & fec_al & """/>")


''ingenieros de campo
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 2"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ic = rs.fields(0)
end if

response.write("<ic d=""" & fec_ic & """/>")


''status
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 6"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_s = rs.fields(0)
end if

response.write("<s d=""" & fec_s & """/>")


''productos status
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 7"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ps = rs.fields(0)
end if

response.write("<ps d=""" & fec_ps & """/>")


''insumos
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 3"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_i = rs.fields(0)
end if

response.write("<i d=""" & fec_i & """/>")


''marcas
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 4"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_m = rs.fields(0)
end if

response.write("<m d=""" & fec_m & """/>")


''modelos
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 5"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_mod = rs.fields(0)
end if

response.write("<mod d=""" & fec_mod & """/>")


''viaticos
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 11"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_v = rs.fields(0)
end if

response.write("<v d=""" & fec_v & """/>")


''spareParts
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 10"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_sp = rs.fields(0)
end if

response.write("<sp d=""" & fec_sp & """/>")


''modeloModulos
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 8"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_mm = rs.fields(0)
end if

response.write("<mm d=""" & fec_mm & """/>")


''modeloSparePart
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 9"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ms = rs.fields(0)
end if

response.write("<ms d=""" & fec_ms & """/>")


''clienteModelos
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 12"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_cm = rs.fields(0)
end if

response.write("<cm d=""" & fec_cm & """/>")


''servicios
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 13"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ser = rs.fields(0)
end if

response.write("<ser d=""" & fec_ser & """/>")


''causas
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 14"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ca = rs.fields(0)
end if

response.write("<ca d=""" & fec_ca & """/>")


''soluciones
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 15"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_sol = rs.fields(0)
end if

response.write("<sol d=""" & fec_sol & """/>")


''servicios causas
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 17"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ser_ca = rs.fields(0)
end if

response.write("<serca d=""" & fec_ser_ca & """/>")


''servicios soluciones
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 16"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ser_sol = rs.fields(0)
end if

response.write("<sersol d=""" & fec_ser_sol & """/>")


''causas rechazo
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100),TABLA_UPDATE FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 18"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_cre = rs.fields(0)
end if

response.write("<cre d=""" & fec_cre & """ />")


''grupos
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 19"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_gr = rs.fields(0)
end if

response.write("<gr d=""" & fec_gr & """/>")


''grupos clientes
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 20"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_gc = rs.fields(0)
end if

response.write("<gc d=""" & fec_gc & """/>")


''llaves
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 25 OR ID_UPDATE_TABLAS_BB = 21"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ll = rs.fields(0)
end if

response.write("<ll d=""" & fec_ll & """/>")


''softwares
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 26 OR ID_UPDATE_TABLAS_BB = 22"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_sw = rs.fields(0)
end if

response.write("<sw d=""" & fec_sw & """/>")


''modelo llaves
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 27 OR ID_UPDATE_TABLAS_BB = 23"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_mll = rs.fields(0)
end if

response.write("<mll d=""" & fec_mll & """/>")


''modelo softwares
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE ID_UPDATE_TABLAS_BB = 28 OR ID_UPDATE_TABLAS_BB = 24"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_msw = rs.fields(0)
end if

response.write("<msw d=""" & fec_msw & """/>")


''statusAREQ
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'C_STATUS_AR_EQ'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_seq = rs.fields(0)
end if

response.write("<seq d=""" & fec_seq & """/>")


''cambios wincor
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'BD_CAMBIO_STATUS_AR'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_csar = rs.fields(0)
end if

response.write("<csar d=""" & fec_csar & """/>")


''especificacion causa rechazo
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'BD_ESPECIFICACION_CAUSA_RECHAZO'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_ecr = rs.fields(0)
end if
response.write("<ecr d=""" & fec_ecr & """/>")


''tipo de fallas
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'C_TIPO_FALLA'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_tf = rs.fields(0)
end if
response.write("<tf d=""" & fec_tf & """/>")


''especificacion tipo de fallas
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'BD_ESPECIFICACION_TIPO_FALLA'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_etf = rs.fields(0)
end if
response.write("<etf d=""" & fec_etf & """/>")

''falla especifica tipo falla
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'BD_FALLA_ESPECIFICA_TIPO_FALLA'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_fetf = rs.fields(0)
end if
response.write("<fetf d=""" & fec_fetf & """/>")

''codigos de intervencion nivel0
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'C_CODIGOS_INTERVENCION_NIVEL0'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_cin0 = rs.fields(0)
end if
response.write("<cin0 d=""" & fec_cin0 & """/>")

''codigos de intervencion nivel1
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'C_CODIGOS_INTERVENCION_NIVEL1'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_cin1 = rs.fields(0)
end if
response.write("<cin1 d=""" & fec_cin1 & """/>")


''codigos de intervencion nivel2
sql="SELECT CONVERT(VARCHAR(20), FEC_UPDATE, 100) FROM C_UPDATE_TABLAS_BB"
sql=sql+" WHERE TABLA_UPDATE = 'C_CODIGOS_INTERVENCION_NIVEL2'"

set rs = con.executeQuery(sql)

if not rs.eof then
	fec_cin2 = rs.fields(0)
end if
response.write("<cin2 d=""" & fec_cin2 & """/>")

''cerrar tag
response.write("</d>")

sql="UPDATE C_USUARIOS SET FEC_ACCESO_PDA = GETDATE() WHERE ID_USUARIO=" & id
con.executeUpdate(sql)
rs.close
set rs = nothing
con.endConnection

%>
