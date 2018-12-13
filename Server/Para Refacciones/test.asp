<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/html"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql = "SELECT " &_
			"					MO_MODULO.ID_MODELO " &_
			"				FROM BD_MODELO_MODULO " &_
			"				INNER JOIN C_PRODUCTOS P_MODULO " &_
			"					ON BD_MODELO_MODULO.ID_PRODUCTO_MODULO = P_MODULO.ID_PRODUCTO " &_
			"				INNER JOIN C_MARCAS MA_MODULO " &_
			"					ON BD_MODELO_MODULO.ID_MARCA_MODULO = MA_MODULO.ID_MARCA " &_
			"				INNER JOIN C_MODELOS MO_MODULO " &_
			"					ON BD_MODELO_MODULO.ID_MODELO_MODULO = MO_MODULO.ID_MODELO " &_
			"				INNER JOIN C_MODELOS MO_UNIDAD " &_
			"					ON BD_MODELO_MODULO.ID_MODELO = MO_UNIDAD.ID_MODELO " &_
			"				INNER JOIN C_MARCAS MA_UNIDAD " &_
			"					ON MO_UNIDAD.ID_MARCA = MA_UNIDAD.ID_MARCA " &_
			"				INNER JOIN C_PRODUCTOS P_UNIDAD " &_
			"					ON MA_UNIDAD.ID_PRODUCTO = P_UNIDAD.ID_PRODUCTO " &_
			"				WHERE " &_
			"					P_UNIDAD.ID_PRODUCTO =  2 " &_
			"					AND MO_MODULO.SKU IS NOT NULL " &_
			"					AND MO_UNIDAD.STATUS = 'ACTIVO' " &_
			"					AND MO_MODULO.STATUS = 'ACTIVO' " &_
			"					AND CAST(MO_MODULO.ID_MODELO AS VARCHAR) LIKE '%499%' " &_
			"				GROUP BY MO_MODULO.SKU, MO_MODULO.ID_MODELO "

set rs = con.executeQuery(sql)

if not rs.EOF then
	Do until rs.EOF
  response.write("<e A=""" & rs.fields("ID_MODELO") &_ 
				 """/>")
		rs.MoveNext
	Loop
end if

rs.close
set rs = Nothing


response.write("<br><br><br>")
con.endConnection

%>