<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

set con1 = new cConnection
con1.startConnection(DB_INI)

if request.QueryString("ic") <> "" then
	id_cliente = request.QueryString("ic")
else
	id_cliente = ""
end if

response.write("<d>")	

sql ="SELECT COUNT(*) AS COUNT " &_
		"FROM BD_MODELO_MODULO " &_ 
		 "INNER JOIN C_MARCAS MA_MODULO " &_
		 "	ON BD_MODELO_MODULO.ID_MARCA_MODULO = MA_MODULO.ID_MARCA " &_
		 "INNER JOIN C_MODELOS MO_MODULO " &_
		 "	ON BD_MODELO_MODULO.ID_MODELO_MODULO = MO_MODULO.ID_MODELO " &_
		 "INNER JOIN C_MODELOS MO_UNIDAD " &_
		 "	ON BD_MODELO_MODULO.ID_MODELO = MO_UNIDAD.ID_MODELO " &_
		 "INNER JOIN C_MARCAS MA_UNIDAD " &_
		 "	ON MO_UNIDAD.ID_MARCA = MA_UNIDAD.ID_MARCA " &_
		 "WHERE " &_
		 "	BD_MODELO_MODULO.ID_PRODUCTO_MODULO = 2" &_
		 "	AND MO_MODULO.STATUS = 'ACTIVO' "
		 

set rs1 = con1.executeQuery(sql)	
	
if not rs1.eof then
	
		Do until rs1.eof

		response.write("<D>" & rs1.fields("COUNT") & "</D>")
		rs1.MoveNext
		loop
	
	end if			 		 					   
rs1.close

response.write("</d>")	
set rs1 = nothing
con1.endConnection	

%>