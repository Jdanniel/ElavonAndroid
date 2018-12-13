<!--#include file="../../connections/Session.asp"-->
<!--#include file="../../connections/constants.asp"-->
<!--#include file="../../connections/cConnection.asp"-->
<!--#include file="../../DAO/capitalizeString.asp"-->
<!--#include file="../../DAO/displayAttach.asp"-->

<%

response.Charset="iso-8859-1"

if request.Form("ID_AR") <> "" then
	idAR = request.Form("ID_AR")
elseif request.QueryString("ID_AR") <> "" then
	idAR = request.QueryString("ID_AR")
else
	idAR = ""
end if

if request.Form("EDIT") <> "" then
	edit = request.Form("EDIT")
elseif request.QueryString("EDIT") <> "" then
	edit = request.QueryString("EDIT")
else
	edit = "0"
end if

if request.Form("SEARCH_TEXT") <> "" then
	searchText = request.Form("SEARCH_TEXT")
elseif	request.QueryString("SEARCH_TEXT") <> "" then
	searchText = request.QueryString("SEARCH_TEXT")
else	
	searchText = ""
end if

if request.Form("ID_TIPO_PRODUCTO") <> "" then
	idTipoProducto = request.Form("ID_TIPO_PRODUCTO")
elseif request.QueryString("ID_TIPO_PRODUCTO") <> "" then
	idTipoProducto = request.QueryString("ID_TIPO_PRODUCTO")
else
	idTipoProducto = "2"
end if

if idTipoProducto = "1" then
	checkedUnidad = "checked"
	checkedRefaccion = ""
else
	checkedUnidad = ""
	checkedRefaccion = "checked"
end if

set con = new cConnection
con.startConnection(DB_INI)

    sql = "SELECT BD_AR.ID_TECNICO"
	sql=sql+ " ,BD_AR.ID_NEGOCIO"
	sql=sql+ " ,BD_AR.ID_CLIENTE"
	sql=sql+ " ,C_CLIENTES.IS_ID_REQ" 
	sql=sql+ " ,C_CLIENTES.IS_SINGLE_MOV_INV"
	sql=sql+ " ,ISNULL(C_PRODUCTOS.IS_BOM, -1) AS IS_BOM" 
	sql=sql+ " FROM BD_AR" 
	sql=sql+ " INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
	sql=sql+ " LEFT JOIN BD_UNIDADES ON BD_AR.ID_UNIDAD_ATENDIDA = BD_UNIDADES.ID_UNIDAD "
	sql=sql+ " LEFT JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO "
	sql=sql+ " WHERE ID_AR = " & idAR 								
	'response.Write(sql)
	'response.End()
	set rs = con.executeQuery(sql)
	
	if not rs.eof then
	idTecnico = rs.fields("ID_TECNICO")
	idNegocio = rs.fields("ID_NEGOCIO")
	idCliente = rs.fields("ID_CLIENTE")	
	isIdReq = rs.fields("IS_ID_REQ")	
	isSingleMovInv = rs.fields("IS_SINGLE_MOV_INV")
	isBom = rs.fields("IS_BOM")
	end if	
							
rs.close
set rs = nothing
con.endConnection

set con = new cConnection
con.startConnection(DB_INI)

	sql = "SELECT NOMBRE + ' ' + PATERNO + ' ' + MATERNO AS DESC_TECNICO FROM C_USUARIOS WHERE ID_USUARIO = " & idTecnico									
	set rs = con.executeQuery(sql)
	descTecnico = rs.fields("DESC_TECNICO")
							
rs.close
set rs = nothing
con.endConnection

set con = new cConnection
con.startConnection(DB_INI)

	sql = "SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = " & idNegocio									
	set rs = con.executeQuery(sql)
	descNegocio = rs.fields("DESC_NEGOCIO")
							
rs.close
set rs = nothing
con.endConnection

'' Verificar Sustituciones Previas
set con = new cConnection
con.startConnection(DB_INI)

	sql = "SELECT CASE WHEN (SELECT COUNT(*)"
	sql = sql + "			 FROM BD_SUSTITUCIONES"
	sql = sql + "			 WHERE " & idAR & " = BD_SUSTITUCIONES.ID_AR) = 0"
	sql = sql + "			 AND " & edit & " = 1"
	sql = sql + "			 AND ( "
	sql = sql + "				SELECT IS_SINGLE_MOV_INV "
	sql = sql + "				FROM BD_AR "
	sql = sql + "				INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
	sql = sql + "				WHERE ID_AR = " & idAR & " "
	sql = sql + "			 ) = 1 "
	sql = sql + "	   THEN 'block' "
	sql = sql + " 	   WHEN ( "
	sql = sql + "				SELECT IS_SINGLE_MOV_INV "
	sql = sql + "				FROM BD_AR "
	sql = sql + "				INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE "
	sql = sql + "				WHERE ID_AR = " & idAR & " "
	sql = sql + "			 ) = 0 "
	sql = sql + "			 AND " & edit & " = 1"
	sql = sql + "	   THEN 'block' "
	sql = sql + "	   ELSE 'hidden'"
	sql = sql + " END AS BUTTON_DISPLAY"
	'response.Write(sql)
	'response.End()
	set rs = con.executeQuery(sql)
	buttonDisplay = rs.fields("BUTTON_DISPLAY")
	
rs.close
set rs = nothing
con.endConnection

if edit = "1" then
	cancelButtonDisplay = "block"
else
	cancelButtonDisplay = "hidden"
end if

if isBom = "-1" and idCliente <> 45 then
	idTipoProducto = "1"
'else
'	idTipoProducto = "2"
end if

%>	

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<TITLE></TITLE>
<LINK HREF="../../CSS/styles.css" REL="stylesheet" TYPE="text/css">
<link href="../../CSS/dhtmlwindow.css" rel="stylesheet"  type="text/css" />
<script type="text/javascript" src="../../JS/scriptLibrary.js"></script>
<script type="text/javascript" src="../../js/DHTMLwindowLevel2.js" ></script>
<script>

function openBitacoraUnidadDHTMLWindow(idUnidad){

	var DHTMLwin=dhtmlwindow.open("googlebox", "iframe", "../../DOCS/bitacoraUnidad.asp?ID_UNIDAD=" + idUnidad , "Bitácora Unidad No. " + idUnidad , "width=760px,height=300px,resize=0,scrolling=0,left=70,top=47")

}

function openAccesoriosUnidadDHTMLWindow(idUnidad){

	var DHTMLwin=dhtmlwindow.open("googlebox", "iframe", "../../ALMACEN/accesoriosUnidad.asp?ID_UNIDAD=" + idUnidad , "Accesorios Unidad No. " + idUnidad , "width=760px,height=300px,resize=0,scrolling=0,left=70,top=47")

}

function submitForm(){

var noEquipoEntrada = "";
var idUnidadEntrada = "";
var idUnidadSalida = "";
var idProductoUnidadEntrada = "";
var idProductoUnidadSalida = "";
var isDaniada = "";

	// idUnidadEntrada,noEquipoEntrada
	for (i=0;i<document.sustitucionForm.ID_UNIDAD_ENTRADA.length;i++){
		if (document.sustitucionForm.ID_UNIDAD_ENTRADA[i].checked==true){
			idUnidadEntrada = document.sustitucionForm.ID_UNIDAD_ENTRADA[i].value;
			noEquipoEntrada = document.sustitucionForm.NO_EQUIPO[i].value;
			idProductoUnidadEntrada = document.sustitucionForm.ID_PRODUCTO_ENTRADA[i].value;
			break;
		}
	}

	// idUnidadSalida	
	for (i=0;i<document.sustitucionForm.ID_UNIDAD_SALIDA.length;i++){
		if (document.sustitucionForm.ID_UNIDAD_SALIDA[i].checked==true){
			idUnidadSalida = document.sustitucionForm.ID_UNIDAD_SALIDA[i].value;
			idProductoUnidadSalida = document.sustitucionForm.ID_PRODUCTO_SALIDA[i].value;
			if(document.sustitucionForm.IS_DANIADA[i].checked)
				isDaniada = 1;
			else
				isDaniada = 0;
			break;
		}
	}	

	//alert(idProductoUnidadEntrada + " - " +idProductoUnidadSalida)
	//setValue("NO_EQUIPO1",noEquipoEntrada)	
	//setValue("ID_UNIDAD_ENTRADA1",idUnidadEntrada)
	//setValue("ID_UNIDAD_SALIDA1",idUnidadSalida)
	//setValue("IS_DANIADA1",isDaniada)
	
	document.getElementById("NO_EQUIPO1").value = noEquipoEntrada;
	document.getElementById("ID_UNIDAD_ENTRADA1").value = idUnidadEntrada;
	document.getElementById("ID_UNIDAD_SALIDA1").value = idUnidadSalida;
	document.getElementById("IS_DANIADA1").value = isDaniada;

	if (idProductoUnidadEntrada == idProductoUnidadSalida)
	{
		if( idUnidadEntrada != "" && idUnidadSalida != ""){
			
			if (<%= isIdReq %> == "1" && noEquipoEntrada == ""){
			
				alert("Es necesario ingresar No. Equipo de Unidad Instalada.");
				
			}
			else{
			
				document.sustitucionForm.submit();
			}
		
		}else{
		
			alert("Es necesario seleccionar Unidad Instalada y Unidad Retirada.");
		
		}
	}
	else
	{
		alert("Unidad Instalada no corresponde con Unidad Retirada.");
	}

}

</script>
</HEAD>

<body style="margin-top:3px; margin-left:0px; margin-bottom:0px; margin-right:0px">

<table align="center" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-bottom:5px;width:100%;">
    <tr>
        <td style="text-align:left;vertical-align:baseline;"><span style="color:#CC6666;font-size:11px;font-weight:bold;">Sustituciones Solicitud de Servicio No. <%= idAR %></span></td>
        <td style="text-align:right;"><label onClick="window.location.reload();" style='cursor:pointer;'>Actualizar&nbsp;</label></td>    
    </tr>
</table> 

<form id="" action="./sustitucion.asp" method="post">
	<input type="hidden" name="ID_AR" id="ID_AR" value="<%= idAR %>"/>
    <input type="hidden" name="EDIT" id="EDIT" value="<%= edit %>"/>
    <table width="100%" border=0 align="CENTER">
        <tr>
            <td class="tdMenuRight" width="*">
<%	if isBom = "1" then %>
                <input type="radio" name="ID_TIPO_PRODUCTO" id="ID_TIPO_PRODUCTO" value="1" <%= checkedUnidad %>/>Unidad
                <input type="radio" name="ID_TIPO_PRODUCTO" id="ID_TIPO_PRODUCTO" value="2" <%= checkedRefaccion %>/>Refacci&oacute;n
<%	else %>
			&nbsp;
<%	end if %>
            </td>
            <td class="tdMenuRight" width="20%">Busqueda de Unidades Técnico:</td>
            <td class="tdMenuLeft" width="10%">
                <input type="text" name="SEARCH_TEXT" id="SEARCH_TEXT" value="<%= searchText %>" class="form16NoAlignedNumberField" maxlength="50">      
            </td>  
            <td class="tdMenuLeft" width="5%"><input type="submit" value=" Buscar " onClick=""></td>                                 
        </tr>
    </table>
</form>

<%

if edit = "1" then

response.write("<table border='0' align='center' cellspacing='2' style='margin-top:5px;width:100%;'>" & vbCrLf)
response.write(vbTab & vbTab & "<form name='sustitucionForm' id='sustitucionForm' method='post' action='./DAO/insertSustitucion.asp'>" & vbCrLf) 
response.write(vbTab & vbTab & "<input type='hidden' name='ID_AR' id='ID_AR' value='" & idAR & "'>" & vbCrLf)	
response.write(vbTab & vbTab & "<input type='hidden' name='ID_NEGOCIO' id='ID_NEGOCIO' value='" & idNegocio & "'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='hidden' name='ID_TECNICO' id='ID_TECNICO' value='" & idTecnico & "'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='hidden' name='ACTION' id='ACTION' value='SWAP'>" & vbCrLf)	
response.write(vbTab & vbTab & "<input type='hidden' name='EDIT' id='EDIT' value='" & edit & "'>" & vbCrLf)					
	

response.write(vbTab & vbTab & "<input type='text' name='NO_EQUIPO' id='NO_EQUIPO' value='' style='display:none;'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='radio' name='ID_UNIDAD_ENTRADA' id='ID_UNIDAD_ENTRADA' value='' style='display:none;'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='radio' name='ID_UNIDAD_SALIDA' id='ID_UNIDAD_SALIDA' value='' style='display:none;'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='hidden' name='ID_PRODUCTO_ENTRADA' id='ID_PRODUCTO_ENTRADA' value='' style='display:none;'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='hidden' name='ID_PRODUCTO_SALIDA' id='ID_PRODUCTO_SALIDA' value='' style='display:none;'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='checkbox' id='IS_DANIADA' name='IS_DANIADA' style='display:none;'/></td>" & vbCrLf)

response.write(vbTab & vbTab & "<input type='hidden' name='NO_EQUIPO1' id='NO_EQUIPO1'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='hidden' name='ID_UNIDAD_ENTRADA1' id='ID_UNIDAD_ENTRADA1'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='hidden' name='ID_UNIDAD_SALIDA1' id='ID_UNIDAD_SALIDA1'>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='hidden' name='IS_DANIADA1' id='IS_DANIADA1'>" & vbCrLf)

'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''Unidades Técnico
set con = new cConnection
con.startConnection(DB_INI)
		   
	sql=""
	sql=sql+ "SELECT "
	sql=sql+ "	COUNT(*) AS IS_GRUPO "
	sql=sql+ "FROM BD_GRUPOS_CLIENTES "
	sql=sql+ "WHERE ID_CLIENTE = " & idCliente & "; "
	
set rs = con.executeQuery(sql)

if not rs.eof then
	isGrupo = rs.fields("IS_GRUPO")
else
	isGrupo = "0"
end if
rs.close
set rs = nothing
con.endConnection

set con = new cConnection
con.startConnection(DB_INI)
		   
	sql="SELECT"
	sql=sql+" BD_UNIDADES.ID_UNIDAD"
	sql=sql+",BD_UNIDADES.ID_PRODUCTO"
	sql=sql+",BD_UNIDADES.ID_MODELO"
	sql=sql+",C_MODELOS.DESC_MODELO"
	sql=sql+",C_MARCAS.DESC_MARCA"
	sql=sql+",BD_UNIDADES.NO_SERIE"	
	sql=sql+",BD_UNIDADES.NO_INVENTARIO"
	sql=sql+",BD_UNIDADES.NO_IMEI"
	sql=sql+",BD_UNIDADES.POSICION_INVENTARIO"				
	sql=sql+",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"				
	sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"	
	sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"		
	sql=sql+",(SELECT NOMBRE + ' ' + PATERNO + ' ' + MATERNO FROM C_USUARIOS WHERE ID_USUARIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_USUARIO"									
	sql=sql+",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"		
	sql=sql+",C_CLIENTES.DESC_CLIENTE"	
	sql=sql+",CASE WHEN ID_UNIDAD IN (SELECT ID_UNIDAD_SALIDA FROM BD_SUSTITUCIONES WHERE ID_AR = " & idAR & ") THEN 'disabled' ELSE '' END AS IS_DISABLED"																												
	sql=sql+" FROM BD_UNIDADES"
	sql=sql+" INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
	sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
	sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"	
	sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"
	sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"
	sql=sql+" LEFT JOIN BD_GRUPOS_CLIENTES ON C_CLIENTES.ID_CLIENTE = BD_GRUPOS_CLIENTES.ID_CLIENTE "
	sql=sql+" WHERE BD_UNIDADES.ID_STATUS_UNIDAD IN (15)"
	sql=sql+" AND BD_UNIDADES.STATUS = 'ACTIVO'" 
	sql=sql+" AND BD_UNIDADES.ID_TIPO_RESPONSABLE = 2"
	sql=sql+" AND BD_UNIDADES.ID_RESPONSABLE = " & idTecnico
	
	if isGrupo <> "0" then
		sql=sql+" AND BD_GRUPOS_CLIENTES.ID_GRUPO IN "
		sql=sql+" 								( "
		sql=sql+" 									SELECT ID_GRUPO "
		sql=sql+" 									FROM BD_GRUPOS_CLIENTES "
		sql=sql+" 									WHERE ID_CLIENTE = " & idCliente
		sql=sql+" 								) "
	else
		sql=sql+"AND BD_UNIDADES.ID_CLIENTE = " & idCliente
	end if
	
	sql=sql+" AND BD_UNIDADES.IS_RETIRO = 0"
	sql=sql+" AND C_PRODUCTOS.ID_TIPO_PRODUCTO = " & idTipoProducto
	sql=sql+" AND ( BD_UNIDADES.IS_DANIADA <> 1 OR BD_UNIDADES.IS_DANIADA IS NULL )"
	
	if searchText <> "" then
	sql=sql+" AND CONVERT(VARCHAR,ISNULL(BD_UNIDADES.NO_SERIE,'')) "
	sql=sql+"   + CONVERT(VARCHAR,ISNULL(BD_UNIDADES.NO_INVENTARIO,'')) "
	sql=sql+"   + CONVERT(VARCHAR,ISNULL(BD_UNIDADES.NO_IMEI,'')) "					
	sql=sql+"   + CONVERT(VARCHAR,ISNULL(BD_UNIDADES.NO_SIM,'')) "
	sql=sql+"   + CONVERT(VARCHAR,ISNULL(BD_UNIDADES.ID_UNIDAD,'')) "						
	sql=sql+"   + CONVERT(VARCHAR,ISNULL(C_MODELOS.DESC_MODELO,'')) "	
	sql=sql+"   + CONVERT(VARCHAR,ISNULL(C_MARCAS.DESC_MARCA,'')) "	
	sql=sql+"   + CONVERT(VARCHAR,ISNULL(C_CLIENTES.DESC_CLIENTE,'')) "	
	sql=sql+"   + CONVERT(VARCHAR,ISNULL(BD_UNIDADES.POSICION_INVENTARIO,'')) "	
	sql=sql+" LIKE '%" & searchText & "%'"		
	end if 
							
	sql=sql+" ORDER BY BD_UNIDADES.ID_UNIDAD DESC"			
			
set rs = con.executeQuery(sql)

response.write(vbTab & "<caption>Unidades Técnico " & descTecnico & "</caption>" & vbCrLf)

response.write(vbTab & "<thead>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='2%'>&nbsp;&nbsp;</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='2%'>&nbsp;&nbsp;</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='5%'>NO.</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='20%'>TÉCNICO</th>" & vbCrLf)  
response.write(vbTab & vbTab & "<th width='10%'>CLIENTE</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='11%'>MARCA</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='11%'>MODELO</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='11%'>NO. SERIE</th>" & vbCrLf) 
response.write(vbTab & vbTab & "<th width='10%'>STATUS</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='11%'>ID EQUIPO</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='7%'>&nbsp;</th>" & vbCrLf)
response.write(vbTab & "</thead>" & vbCrLf)

if not rs.eof then
	
	Do until rs.EOF

	response.write(vbTab & "<tr>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'><img src='../../IMAGES/btModelo.png' style='cursor:pointer;' title='Bitácora' onClick='openBitacoraUnidadDHTMLWindow(" & rs.fields("ID_UNIDAD") & ")'></td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'><img src='../../IMAGES/btBox.png' style='cursor:pointer;' title='Accesorios' onClick='openAccesoriosUnidadDHTMLWindow(" & rs.fields("ID_UNIDAD") & ")'></td>" & vbCrLf)	
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("ID_UNIDAD") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_USUARIO") & "</td>" & vbCrLf)		
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_CLIENTE") & "</td>" & vbCrLf)		
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_MARCA") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_MODELO") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("NO_SERIE") & "</td>" & vbCrLf) 	
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_STATUS_UNIDAD") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>")
	response.write(vbTab & vbTab & vbTab &"<input type='text' name='NO_EQUIPO' id='NO_EQUIPO' maxlength='20' class='form10NoAlignedNumberField'>" & vbCrLf)
	response.write(vbTab & vbTab & vbTab &"<input type='hidden' name='ID_PRODUCTO_ENTRADA' id='ID_PRODUCTO_ENTRADA' value='"& rs.fields("ID_PRODUCTO") &"'/>" & vbCrLf)
	response.write(vbTab & vbTab & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'><input type='radio' id='ID_UNIDAD_ENTRADA' name='ID_UNIDAD_ENTRADA' value='" & rs.fields("ID_UNIDAD") & "' " & rs.fields("IS_DISABLED") & "></td>" & vbCrLf)	
	response.write(vbTab & "</tr>" & vbCrLf)
	
	rs.MoveNext
	loop
		
end if

response.write("</table>" & vbCrLf)

rs.close
set rs = nothing
con.endConnection

'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''Unidades Instaladas
set con = new cConnection
con.startConnection(DB_INI)
		   
	sql="SELECT"
	sql=sql+" BD_UNIDADES.ID_UNIDAD"
	sql=sql+",BD_UNIDADES.ID_PRODUCTO"
	sql=sql+",BD_UNIDADES.ID_MODELO"												
	sql=sql+",C_MODELOS.DESC_MODELO"
	sql=sql+",C_MARCAS.DESC_MARCA"
	sql=sql+",BD_UNIDADES.NO_SERIE"	
	sql=sql+",BD_UNIDADES.NO_INVENTARIO"
	sql=sql+",BD_UNIDADES.NO_EQUIPO"	
	sql=sql+",BD_UNIDADES.NO_IMEI"
	sql=sql+",BD_UNIDADES.POSICION_INVENTARIO"				
	sql=sql+",CONVERT(VARCHAR,BD_UNIDADES.FEC_ALTA,103) AS FEC_ALTA"				
	sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"	
	sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"		
	sql=sql+",(SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE ID_NEGOCIO = BD_UNIDADES.ID_RESPONSABLE) AS DESC_NEGOCIO"									
	sql=sql+",ROW_NUMBER() OVER(ORDER BY BD_UNIDADES.ID_UNIDAD) AS NO_UNIDADES"		
	sql=sql+",C_CLIENTES.DESC_CLIENTE"	
	sql=sql+",CASE WHEN ID_UNIDAD IN (SELECT ID_UNIDAD_ENTRADA FROM BD_SUSTITUCIONES WHERE ID_AR = " & idAR & ") THEN 'disabled' ELSE '' END AS IS_DISABLED"																												
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
	sql=sql+" AND BD_UNIDADES.ID_CLIENTE = " & idCliente
	sql=sql+" AND C_PRODUCTOS.ID_TIPO_PRODUCTO = " & idTipoProducto						
	sql=sql+" ORDER BY BD_UNIDADES.ID_UNIDAD DESC"			
			
set rs = con.executeQuery(sql)

response.write("<table border='0' align='center' cellspacing='2' style='margin-top:5px;width:100%;'>" & vbCrLf)
response.write(vbTab & "<caption>Unidades Instaladas en Negocio " & descNegocio & "</caption>" & vbCrLf)

response.write(vbTab & "<thead>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='2%'>&nbsp;&nbsp;</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='2%'>&nbsp;&nbsp;</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='5%'>NO.</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='18%'>NEGOCIO</th>" & vbCrLf)  
response.write(vbTab & vbTab & "<th width='10%'>CLIENTE</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='10%'>MARCA</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='10%'>MODELO</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='10%'>NO. SERIE</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='10%'>ID EQUIPO</th>" & vbCrLf)  
response.write(vbTab & vbTab & "<th width='9%'>STATUS</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='10%'>DA&Ntilde;ADA</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='4%'>&nbsp;</th>" & vbCrLf)
response.write(vbTab & "</thead>" & vbCrLf)

if not rs.eof then
	
	Do until rs.EOF

	response.write(vbTab & "<tr>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'><img src='../../IMAGES/btModelo.png' style='cursor:pointer;' title='Bitácora' onClick='openBitacoraUnidadDHTMLWindow(" & rs.fields("ID_UNIDAD") & ")'></td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'><img src='../../IMAGES/btBox.png' style='cursor:pointer;' title='Accesorios' onClick='openAccesoriosUnidadDHTMLWindow(" & rs.fields("ID_UNIDAD") & ")'></td>" & vbCrLf)	
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("ID_UNIDAD") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_NEGOCIO") & "</td>" & vbCrLf)		
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_CLIENTE") & "</td>" & vbCrLf)		
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_MARCA") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_MODELO") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("NO_SERIE") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("NO_EQUIPO") & "</td>" & vbCrLf)  	
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_STATUS_UNIDAD") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & vbCrLf)
	response.write(vbTab & vbTab & vbTab &"<input type='checkbox' id='IS_DANIADA' name='IS_DANIADA' " & rs.fields("IS_DISABLED") & " checked title='Casilla para marcar unidad como da&ntilde;ada.'/>" & vbCrLf)
	response.write(vbTab & vbTab & vbTab &"<input type='hidden' id='ID_PRODUCTO_SALIDA' name='ID_PRODUCTO_SALIDA' value='"& rs.fields("ID_PRODUCTO") &"'/>" & vbCrLf)
	response.write(vbTab & vbTab & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'><input type='radio' id='ID_UNIDAD_SALIDA' name='ID_UNIDAD_SALIDA' value='" & rs.fields("ID_UNIDAD") & "' " & rs.fields("IS_DISABLED") & "></td>" & vbCrLf)
	response.write(vbTab & "</tr>" & vbCrLf)
	
	rs.MoveNext
	loop
		
end if

response.write("</table>" & vbCrLf)

rs.close
set rs = nothing
con.endConnection

response.write("<table border='0' align='center' cellspacing='2' style='margin-top:5px;width:100%;'>" & vbCrLf)
response.write(vbTab & "<tr>" & vbCrLf)
response.write(vbTab & vbTab & "<td class='tdFormCenter'><input type='button' value='Confirmar Sustitución' onClick='submitForm();' style='visibility:" & buttonDisplay& ";'></td>" & vbCrLf)
response.write(vbTab & "</tr>" & vbCrLf)	
response.write("</table>" & vbCrLf)
response.write(vbTab & vbTab & "<input type='hidden' name='ID_TIPO_PRODUCTO' id='ID_TIPO_PRODUCTO' value='"& idTipoProducto &"'>" & vbCrLf)
response.write(vbTab & vbTab & "</form>" & vbCrLf)

end if

'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''Sustituciones
set con = new cConnection
con.startConnection(DB_INI)
		   
	sql="SELECT"
	sql=sql+" BD_SUSTITUCIONES.ID_AR"
	sql=sql+",BD_SUSTITUCIONES.ID_NEGOCIO"
	sql=sql+",BD_SUSTITUCIONES.ID_TECNICO"	
	sql=sql+",BD_SUSTITUCIONES.ID_UNIDAD_ENTRADA"	
	sql=sql+",BD_SUSTITUCIONES.ID_UNIDAD_SALIDA"	
	sql=sql+",C_CLIENTES.DESC_CLIENTE"	
	sql=sql+",C_USUARIOS.NOMBRE + ' ' + C_USUARIOS.PATERNO + ' ' + C_USUARIOS.MATERNO AS DESC_TECNICO"
	sql=sql+",(SELECT DESC_NEGOCIO FROM BD_NEGOCIOS WHERE BD_NEGOCIOS.ID_NEGOCIO = BD_SUSTITUCIONES.ID_NEGOCIO) AS DESC_NEGOCIO"	
	sql=sql+",(SELECT NO_SERIE FROM BD_UNIDADES WHERE BD_UNIDADES.ID_UNIDAD = BD_SUSTITUCIONES.ID_UNIDAD_ENTRADA) AS NO_SERIE_ENTRADA"
	sql=sql+",(SELECT NO_SERIE FROM BD_UNIDADES WHERE BD_UNIDADES.ID_UNIDAD = BD_SUSTITUCIONES.ID_UNIDAD_SALIDA) AS NO_SERIE_SALIDA"	
	sql=sql+",(SELECT NOMBRE + ' ' + PATERNO + ' ' + MATERNO FROM C_USUARIOS WHERE ID_USUARIO = BD_SUSTITUCIONES.ID_TECNICO) AS DESC_TECNICO"																																														
	sql=sql+" FROM BD_SUSTITUCIONES"
	sql=sql+" INNER JOIN C_USUARIOS ON BD_SUSTITUCIONES.ID_TECNICO = C_USUARIOS.ID_USUARIO"	
	sql=sql+" INNER JOIN BD_AR ON BD_SUSTITUCIONES.ID_AR = BD_AR.ID_AR"
	sql=sql+" INNER JOIN C_CLIENTES ON BD_AR.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"					
	sql=sql+" WHERE BD_SUSTITUCIONES.ID_AR = " & idAR				
	sql=sql+" ORDER BY BD_SUSTITUCIONES.ID_SUSTITUCION"			
			
set rs = con.executeQuery(sql)

response.write("<table border='0' align='center' cellspacing='2' style='margin-top:5px;width:100%;'>" & vbCrLf)
response.write(vbTab & "<caption>Sustituciones Confirmadas</caption>" & vbCrLf)

response.write(vbTab & "<tr><thead>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='2%'>&nbsp;&nbsp;</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='20%'>TÉCNICO</th>" & vbCrLf)  
response.write(vbTab & vbTab & "<th width='11%'>NEGOCIO</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='10%'>CLIENTE</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='11%'>NO. SERIE ENTRADA</th>" & vbCrLf)
response.write(vbTab & vbTab & "<th width='11%'>NO. SERIE SALIDA</th>" & vbCrLf)  
response.write(vbTab & vbTab & "<th width='7%'>&nbsp;</th>" & vbCrLf)
response.write(vbTab & "</thead></tr>" & vbCrLf)

if not rs.eof then
	
	Do until rs.EOF

	response.write(vbTab & "<tr>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'><img src='../../IMAGES/bullet_off.gif'></td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_TECNICO") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_NEGOCIO") & "</td>" & vbCrLf)		
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("DESC_CLIENTE") & "</td>" & vbCrLf)		
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("NO_SERIE_ENTRADA") & "</td>" & vbCrLf)
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'>" & rs.fields("NO_SERIE_SALIDA") & "</td>" & vbCrLf)
	
	response.write(vbTab & vbTab & "<form method='post' action='./DAO/insertSustitucion.asp'>" & vbCrLf) 
	response.write(vbTab & vbTab & "<input type='hidden' name='ID_AR' id='ID_AR' value='" & rs.fields("ID_AR")  & "'>" & vbCrLf)	
	response.write(vbTab & vbTab & "<input type='hidden' name='ID_NEGOCIO' id='ID_NEGOCIO' value='" & rs.fields("ID_NEGOCIO")  & "'>" & vbCrLf)
	response.write(vbTab & vbTab & "<input type='hidden' name='ID_TECNICO' id='ID_TECNICO' value='" & rs.fields("ID_TECNICO")  & "'>" & vbCrLf)	
	response.write(vbTab & vbTab & "<input type='hidden' name='ID_UNIDAD_ENTRADA1' id='ID_UNIDAD_ENTRADA1' value='" & rs.fields("ID_UNIDAD_ENTRADA")  & "'>" & vbCrLf)
	response.write(vbTab & vbTab & "<input type='hidden' name='ID_UNIDAD_SALIDA1' id='ID_UNIDAD_SALIDA1' value='" & rs.fields("ID_UNIDAD_SALIDA")  & "'>" & vbCrLf)	
	response.write(vbTab & vbTab & "<input type='hidden' name='ACTION' id='ACTION' value='CANCEL_SWAP'>" & vbCrLf)	
	response.write(vbTab & vbTab & "<input type='hidden' name='EDIT' id='EDIT' value='" & edit & "'>" & vbCrLf)						
	response.write(vbTab & vbTab & "<td class='tdMenuCenter'><input type='button' value=' Cancelar Sustitución ' onClick='this.form.submit();' style='visibility:" & cancelButtonDisplay& ";'></td>" & vbCrLf)
	response.write(vbTab & vbTab & "<input type='hidden' name='ID_TIPO_PRODUCTO' id='ID_TIPO_PRODUCTO' value='"& idTipoProducto &"'>" & vbCrLf)	
	response.write(vbTab & vbTab & "</form>" & vbCrLf)
		
	response.write(vbTab & "</tr>" & vbCrLf)
	
	rs.MoveNext
	loop
		
end if

response.write("</table>" & vbCrLf)

rs.close
set rs = nothing
con.endConnection


%>

</BODY>
</HTML>