<!--#include file="../connections/Session.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->
<!--#include file="../DAO/capitalizeString.asp"-->
<!--#include file="../DAO/displayAttach.asp"-->

<%

response.Charset="iso-8859-1"

if request.Form("ID_UNIDAD") <> "" then
	idUnidad = request.Form("ID_UNIDAD")
elseif request.QueryString("ID_UNIDAD") <> "" then
	idUnidad = request.QueryString("ID_UNIDAD")
else
	idUnidad = ""
end if	

set con = new cConnection
con.startConnection(DB_INI)

	sql="SELECT"
	sql=sql+" BD_UNIDADES.ID_UNIDAD"
	sql=sql+",BD_UNIDADES.ID_MODELO"												
	sql=sql+",C_MODELOS.DESC_MODELO"
	sql=sql+",C_MODELOS.COSTO"	
	sql=sql+",C_MARCAS.DESC_MARCA"
	sql=sql+",BD_UNIDADES.NO_SERIE"	
	sql=sql+",BD_UNIDADES.NO_INVENTARIO"
	sql=sql+",BD_UNIDADES.NO_IMEI"
	sql=sql+",BD_UNIDADES.NO_SIM"
	sql=sql+",BD_UNIDADES.NO_EQUIPO"			
	sql=sql+",BD_UNIDADES.FEC_ALTA"	
	sql=sql+",BD_UNIDADES.IS_NUEVA"		
	sql=sql+",BD_UNIDADES.IS_RETIRO"
	sql=sql+",BD_UNIDADES.IS_DANIADA"					
	sql=sql+",C_STATUS_UNIDAD.DESC_STATUS_UNIDAD"	
	sql=sql+",BD_UNIDADES.ID_STATUS_UNIDAD"					
	sql=sql+",C_CLIENTES.DESC_CLIENTE"	
	sql=sql+",C_LLAVES.DESC_LLAVE"	
	sql=sql+",C_SOFTWARE.DESC_SOFTWARE"			
	sql=sql+",dbo.FUNC_GET_RESPONSABLE(ID_UNIDAD) AS DESC_RESPONSABLE"
	sql=sql+",ISNULL(BD_MODELO_SKU.SKU, ISNULL(C_MODELOS.SKU, '')) AS SKU"
	sql=sql+" FROM BD_UNIDADES"
	sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
	'sql=sql+" INNER JOIN BD_CLIENTE_MODELO ON BD_UNIDADES.ID_MODELO = BD_CLIENTE_MODELO.ID_MODELO AND BD_UNIDADES.ID_CLIENTE = BD_CLIENTE_MODELO.ID_CLIENTE "
	sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"	
	sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"					
	sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"
	sql=sql+" LEFT JOIN BD_MODELO_SKU ON BD_UNIDADES.ID_MODELO = BD_MODELO_SKU.ID_MODELO AND BD_UNIDADES.ID_CLIENTE = BD_MODELO_SKU.ID_CLIENTE "	
	sql=sql+" LEFT OUTER JOIN C_LLAVES ON BD_UNIDADES.ID_LLAVE = C_LLAVES.ID_LLAVE"	
	sql=sql+" LEFT OUTER JOIN C_SOFTWARE ON BD_UNIDADES.ID_SOFTWARE = C_SOFTWARE.ID_SOFTWARE"												
	sql=sql+" WHERE BD_UNIDADES.STATUS = 'ACTIVO'" 
	sql=sql+" AND BD_UNIDADES.ID_UNIDAD = " & idUnidad						
	sql=sql+" ORDER BY BD_UNIDADES.ID_UNIDAD DESC"	
	set rs = con.executeQuery(sql)

if not rs.eof then	

	idModelo = rs.fields("ID_MODELO")
	descModelo = rs.fields("DESC_MODELO")
	idMarca = rs.fields("ID_MODELO")
	descMarca = rs.fields("DESC_MARCA")	
	noSerie = rs.fields("NO_SERIE")
	noInventario = rs.fields("NO_INVENTARIO")
	noIMEI = rs.fields("NO_IMEI")
	noSIM = rs.fields("NO_SIM")	
	noEquipo = rs.fields("NO_EQUIPO")		
	costo = rs.fields("COSTO")	
	fecAlta = rs.fields("FEC_ALTA")	
	descCliente = rs.fields("DESC_CLIENTE")	
	descResponsable = rs.fields("DESC_RESPONSABLE")	
	descStatusUnidad = rs.fields("DESC_STATUS_UNIDAD")	
	descLlave = rs.fields("DESC_LLAVE")	
	descSoftware = rs.fields("DESC_SOFTWARE")
	sku = rs.fields("SKU")
	
	if rs.fields("IS_NUEVA") = "1" then
		isNueva = "SI"
	else
		isNueva = "NO"
	end if
	
	if rs.fields("IS_RETIRO") = "1" then
		isRetiro = "SI"
	else
		isRetiro = "NO"
	end if
	
	if rs.fields("IS_DANIADA") = "1" then
		isDaniada = "SI"
	else
		isDaniada = "NO"
	end if	

end if

rs.close
set rs = nothing
con.endConnection
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<TITLE></TITLE>
<LINK HREF="../CSS/styles.css" REL="stylesheet" TYPE="text/css">
<link href="../CSS/dhtmlwindow.css" rel="stylesheet"  type="text/css" />
<script type="text/javascript" src="../js/DHTMLwindow.js" ></script>
<script type="text/javascript" src="./js/ajaxIsDaniada.js" ></script>
<script type="text/javascript" src="./js/ajaxUpdateCliente.js" ></script>
<script>

function openUpdateInfoUnidadDHTMLWindow(idUnidad){
	
	//alert("Hola mundo!!!");

	var DHTMLwin=dhtmlwindow.open("googlebox", "iframe", "updateInfoUnidad.asp?ID_UNIDAD=" + idUnidad, "Modificar Información Unidad No. " + idUnidad , "width=450px,height=280px,resize=0,scrolling=0,left=150,top=47")

}

function hola(idUnidad, noSerie){
	
	alert("Hola mundo!!!");

}

</script>
</HEAD>

<body style="margin-top:5px; margin-left:0px; margin-bottom:0px; margin-right:0px">

<table align="center" style="width:98%;border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px; margin-bottom:5px;">
    <tr>
        <td style="text-align:left;vertical-align:baseline;border:inherit;"><span style="color:#CC6666;font-size:11px;font-weight:bold;">Información Unidad No. <%= idUnidad %></span></td>
        <td style="text-align:right;cursor:pointer;border:inherit;"><img src="../IMAGES/logoMicroformas2.jpg" border="0"></td>    
    </tr>
</table> 

<table width="98%" border=0 align="CENTER" cellspacing=2>
  <caption>&nbsp;
	
  </caption>
  <thead>
    <th colspan="6">&nbsp;</th>
  </thead>

  <tr>
      <td class="tdMenuRight">Fecha de Alta:</td>
      <td class="tdMenuLeft"><%= fecAlta %></td>      
      <td class="tdMenuRight">&nbsp;</td>  
      <td class="tdMenuLeft">&nbsp;</td>   
  </tr>
  <tr>
      <td class="tdMenuRight" width="15%">No. Unidad:</td>
      <td class="tdMenuLeft" width="25%"><%= idUnidad %></td>      
      <td class="tdMenuRight" width="15%">Marca:</td>  
      <td class="tdMenuLeft" width="30%"><%= descMarca %></td>      
      <td class="tdMenuCenter" rowspan="9">    
        <%
        IFRAME_DESC_ATTACH = "IMAGEN_MODELO"
        IFRAME_TABLA = "C_MODELOS"
        IFRAME_COLUMNA = "ID_ATTACH_IMAGEN_MODELO"
        IFRAME_INDICE = "ID_MODELO"
        IFRAME_VALOR_INDICE = idModelo	
		READONLY = "1"				
        %>  
        <iframe contentDocument id="iFrameNivel1" style="border:solid;border-color:#CCCCCC;border-width:1px;" frameborder="0" width="270" height="250" scrolling="NO" src="../UPLOADER/UploadImage.asp?DESC_ATTACH=<%=IFRAME_DESC_ATTACH %>&TABLA=<%= IFRAME_TABLA %>&COLUMNA=<%= IFRAME_COLUMNA %>&INDICE=<%= IFRAME_INDICE %>&VALOR_INDICE=<%= IFRAME_VALOR_INDICE %>&ID_TIPO_ATTACH=<%= IFRAME_ID_TIPO_ATTACH %>&READONLY=<%= READONLY %>"></iframe>
      </td>          
  </tr>
  <tr>
      <td class="tdMenuRight">Modelo:</td>
      <td class="tdMenuLeft"><%= descModelo %></td>      
      <td class="tdMenuRight">SKU:&nbsp;</td>  
      <td class="tdMenuLeft"><%= sku %></td>   
  </tr>  
  <tr>  
      <td class="tdMenuRight">Costo:</td>
	  <td class="tdMenuLeft">$&nbsp;<%= formatNumber(costo) %></td>
      <td class="tdMenuRight">No. Serie:</td>
	  <td class="tdMenuLeft"><%= noSerie %></td>      
  </tr>   
  <tr>  
      <td class="tdMenuRight">No. Inventario:</td>
	  <td class="tdMenuLeft"><%= noInventario %></td> 
      <td class="tdMenuRight">No. IMEI:</td>
	  <td class="tdMenuLeft"><%= noIMEI %></td>      
  </tr>    
  <tr>  
      <td class="tdMenuRight">Cliente:</td>
	  <td class="tdMenuLeft"><%= descCliente %></td> 
      <td class="tdMenuRight">No. SIM:</td>
	  <td class="tdMenuLeft"><%= noSIM %></td>           
  </tr>   
  <tr>  
      <td class="tdMenuRight">Status:</td>
	  <td class="tdMenuLeft"><%= descStatusUnidad %></td>
      <td class="tdMenuRight">Unidad Nueva:</td>
	  <td class="tdMenuLeft"><%= isNueva %></td>      
  </tr>     
  <tr>  
      <td class="tdMenuRight">Responsable:</td>
	  <td class="tdMenuLeft" colspan="3"><%= descResponsable %></td>     
  </tr> 
  <tr>  
      <td class="tdMenuRight">No Equipo:</td>
	  <td class="tdMenuLeft"><%= noEquipo %></td>
      <td class="tdMenuRight">Llave:</td>
	  <td class="tdMenuLeft"><%= descLlave %></td>   
  </tr>  
  <tr>  
      <td class="tdMenuRight">Software:</td>
	  <td class="tdMenuLeft"><%= descSoftware %></td>
      <td class="tdMenuRight">Retiro:</td>
	  <td class="tdMenuLeft"><%= isRetiro %></td>     
  </tr>
  <tr>  
      <td class="tdMenuRight">Da&ntilde;ada:</td>
	  <td class="tdMenuLeft"><%= isDaniada %></td>
      <td class="tdMenuRight">&nbsp;</td>
	  <td class="tdMenuLeft">&nbsp;</td>     
  </tr>
</table>
	
<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-bottom:5px;margin-top:20px;">
    <tr>
        <td style="text-align:left;vertical-align:baseline;"><span style="color:#CC6666;font-size:11px;font-weight:bold;">Bitácora Unidad No. <%= idUnidad %></span></td>
        <td style="text-align:right;cursor:pointer;">&nbsp;</td>       
    </tr>
</table> 

<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-top:10px;">
	<caption>&nbsp;</caption>
    <tr>
        <td style="text-align:center;" width="20%">Status Inicial</td> 
        <td style="text-align:center;" width="20%">Status Final</td>  
        <td style="text-align:center;" width="20%">Hora y Fecha</td>  
        <td style="text-align:center;" width="20%">Usuario</td>  
        <td style="text-align:center;" width="20%">Responsable</td>                                 
    </tr>
   
	<%
	set con = new cConnection
	con.startConnection(DB_INI)
	sql="SELECT"
	sql=sql+" (SELECT DESC_STATUS_UNIDAD FROM C_STATUS_UNIDAD WHERE C_STATUS_UNIDAD.ID_STATUS_UNIDAD = BD_BITACORA_UNIDAD.ID_STATUS_UNIDAD_INI) AS DESC_STATUS_INI"
	sql=sql+",(SELECT DESC_STATUS_UNIDAD FROM C_STATUS_UNIDAD WHERE C_STATUS_UNIDAD.ID_STATUS_UNIDAD = BD_BITACORA_UNIDAD.ID_STATUS_UNIDAD_FIN) AS DESC_STATUS_FIN"
	sql=sql+",BD_BITACORA_UNIDAD.FEC_ALTA"
	sql=sql+",C_USUARIOS.NOMBRE + ' ' + C_USUARIOS.PATERNO + ' ' + C_USUARIOS.MATERNO AS NOMBRE_USUARIO"
	sql=sql+",dbo.FUNC_GET_RESPONSABLE_BITACORA_UNIDAD(ID_BITACORA_UNIDAD) AS DESC_RESPONSABLE"
	sql=sql+" FROM BD_BITACORA_UNIDAD" 
	sql=sql+" INNER JOIN C_USUARIOS ON BD_BITACORA_UNIDAD.ID_USUARIO_ALTA = C_USUARIOS.ID_USUARIO"
	sql=sql+" WHERE ID_UNIDAD = " & idUnidad
	sql=sql+" ORDER BY BD_BITACORA_UNIDAD.FEC_ALTA ASC "
	set rs = con.executeQuery(sql)

    set rs = con.executeQuery(sql)
    if not rs.eof then
    
		Do until rs.eof
		
				response.write(vbTab & "<tr>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("DESC_STATUS_INI") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("DESC_STATUS_FIN") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("FEC_ALTA") & "</td>" & vbCrLf)				
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("NOMBRE_USUARIO") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("DESC_RESPONSABLE") & "</td>" & vbCrLf)		
						
				response.write(vbTab & "</tr>" & vbCrLf)						
		
		rs.MoveNext
		loop
                    
    end if
    rs.close
    set rs = nothing
    con.endConnection
    %>    
</table> 

<%

set con = new cConnection
con.startConnection(DB_INI)
sql="SELECT ID_SOLICITUD_RECOLECCION FROM BD_UNIDADES WHERE ID_UNIDAD = " & idUnidad
set rs = con.executeQuery(sql)
if not rs.eof then	

	Dim idSolicitudRecoleccion
	idSolicitudRecoleccion = rs.fields("ID_SOLICITUD_RECOLECCION")

end if
rs.close
set rs = nothing
con.endConnection	

if idSolicitudRecoleccion <> "" then

%>

<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-bottom:5px;margin-top:20px;">
    <tr>
        <td style="text-align:left;vertical-align:baseline;"><span style="color:#CC6666;font-size:11px;font-weight:bold;">Bitácora Solicitud de Recolección No. <%= idSolicitudRecoleccion %></span></td>
        <td style="text-align:right;cursor:pointer;">&nbsp;</td>    
    </tr>
</table> 

<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-top:10px;">
	<caption>&nbsp;</caption>
    <tr>
        <td style="text-align:center;">Status Inicial</td> 
        <td style="text-align:center;">Status Final</td>  
        <td style="text-align:center;">Hora y Fecha</td>  
        <td style="text-align:center;">Usuario</td>                           
    </tr>
   
	<%
	set con = new cConnection
	con.startConnection(DB_INI)
	sql="SELECT"
	sql=sql+" (SELECT DESC_STATUS_SOLICITUD_RECOLECCION FROM C_STATUS_SOLICITUD_RECOLECCION WHERE C_STATUS_SOLICITUD_RECOLECCION.ID_STATUS_SOLICITUD_RECOLECCION = BD_BITACORA_SOLICITUD_RECOLECCION.ID_STATUS_SOLICITUD_RECOLECCION_INI) AS DESC_STATUS_INI"
	sql=sql+",(SELECT DESC_STATUS_SOLICITUD_RECOLECCION FROM C_STATUS_SOLICITUD_RECOLECCION WHERE C_STATUS_SOLICITUD_RECOLECCION.ID_STATUS_SOLICITUD_RECOLECCION = BD_BITACORA_SOLICITUD_RECOLECCION.ID_STATUS_SOLICITUD_RECOLECCION_FIN) AS DESC_STATUS_FIN"
	sql=sql+",BD_BITACORA_SOLICITUD_RECOLECCION.FEC_ALTA"
	sql=sql+",C_USUARIOS.NOMBRE + ' ' + C_USUARIOS.PATERNO + ' ' + C_USUARIOS.MATERNO AS NOMBRE_USUARIO"
	sql=sql+" FROM BD_BITACORA_SOLICITUD_RECOLECCION" 
	sql=sql+" INNER JOIN C_USUARIOS ON BD_BITACORA_SOLICITUD_RECOLECCION.ID_USUARIO_ALTA = C_USUARIOS.ID_USUARIO"
	sql=sql+" WHERE ID_SOLICITUD_RECOLECCION = " & idSolicitudRecoleccion			
	set rs = con.executeQuery(sql)

    set rs = con.executeQuery(sql)
    if not rs.eof then
    
		Do until rs.eof
		
				response.write(vbTab & "<tr>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("DESC_STATUS_INI") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("DESC_STATUS_FIN") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("FEC_ALTA") & "</td>" & vbCrLf)				
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("NOMBRE_USUARIO") & "</td>" & vbCrLf)		
				response.write(vbTab & "</tr>" & vbCrLf)						
		
		rs.MoveNext
		loop
                    
    end if
    rs.close
    set rs = nothing
    con.endConnection
    %>    
</table> 

<% end if %>


<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-bottom:5px;margin-top:20px;">
    <tr>
        <td style="text-align:left;vertical-align:baseline;"><span style="color:#CC6666;font-size:11px;font-weight:bold;">Reparaciones Unidad No. <%= idUnidad %></span></td>
        <td style="text-align:right;cursor:pointer;">&nbsp;</td>    
    </tr>
</table> 

<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-top:10px;">
	<caption>&nbsp;</caption>
    <tr>
        <td style="text-align:center;">Reparación</td> 
        <td style="text-align:center;">Técnico Asignado</td>  
        <td style="text-align:center;">Costo</td> 
        <td style="text-align:center;">Status</td>         
        <td style="text-align:center;">Fecha de Alta</td>
        <td style="text-align:center;">Fecha de Reparación</td>                                    
    </tr>
   
	<%
	set con = new cConnection
	con.startConnection(DB_INI)
	sql="SELECT"
	sql=sql+" BD_REPARACION_UNIDAD.COSTO"	
	sql=sql+",BD_REPARACION_UNIDAD.FEC_ALTA"
	sql=sql+",BD_REPARACION_UNIDAD.FEC_REPARACION"											
	sql=sql+",C_STATUS_REPARACION.DESC_STATUS_REPARACION"	
	sql=sql+",C_TECNICOS.NOMBRE + ' ' + C_TECNICOS.PATERNO  + ' ' + C_TECNICOS.MATERNO AS DESC_TECNICO"	
	sql=sql+",C_REPARACIONES.DESC_REPARACION"																																							
	sql=sql+" FROM BD_REPARACION_UNIDAD"
	sql=sql+" INNER JOIN C_TECNICOS ON BD_REPARACION_UNIDAD.ID_TECNICO = C_TECNICOS.ID_TECNICO"
	sql=sql+" INNER JOIN C_REPARACIONES ON BD_REPARACION_UNIDAD.ID_REPARACION = C_REPARACIONES.ID_REPARACION"
	sql=sql+" INNER JOIN C_STATUS_REPARACION ON BD_REPARACION_UNIDAD.ID_STATUS_REPARACION = C_STATUS_REPARACION.ID_STATUS_REPARACION"			 
	sql=sql+" WHERE BD_REPARACION_UNIDAD.ID_UNIDAD = " & idUnidad			
	set rs = con.executeQuery(sql)

    set rs = con.executeQuery(sql)
    if not rs.eof then
    
		Do until rs.eof
		
				if rs.fields("COSTO") <> "" then
					costo = "$&nbsp;" & formatNumber(rs.fields("COSTO"))
				else
					costo = ""
				end if				
		
				response.write(vbTab & "<tr>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("DESC_REPARACION") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("DESC_TECNICO") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & costo & "</td>" & vbCrLf)				
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("DESC_STATUS_REPARACION") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("FEC_ALTA") & "</td>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' >" & rs.fields("FEC_REPARACION") & "</td>" & vbCrLf)										
				response.write(vbTab & "</tr>" & vbCrLf)						
		
		rs.MoveNext
		loop
		
	else
	
				response.write(vbTab & "<tr>" & vbCrLf)
				response.write(vbTab & vbTab & "<td class='tdMenuCenter' colspan='6'>No existen reparaciónes reportadas para esta Unidad.</td>" & vbCrLf)
				response.write(vbTab & "</tr>" & vbCrLf)	
                    
    end if
    rs.close
    set rs = nothing
    con.endConnection
    %>    
</table>

<!--INICIO Envíos de una Unidad-->
<%
	set con = new cConnection
	con.startConnection(DB_INI)
	sql= "SELECT "
	sql=sql+ "BD_ENVIO_UNIDAD.ID_ENVIO"
	sql=sql+ ",dbo.FUNC_GET_RESPONSABLE_ENVIO_ORIGEN(BD_ENVIO_UNIDAD.ID_ENVIO) AS DESC_RESPONSABLE_ORIGEN"
	sql=sql+ ",dbo.FUNC_GET_RESPONSABLE_ENVIO_DESTINO(BD_ENVIO_UNIDAD.ID_ENVIO) AS DESC_RESPONSABLE_DESTINO"
	sql=sql+ ",ID_SERVICIO_MENSAJERIA"
	sql=sql+ ",CASE WHEN ID_SERVICIO_MENSAJERIA = 0	THEN 'Entrega Directa'"
	sql=sql+ "										ELSE"
	sql=sql+ "										("
	sql=sql+ "											SELECT DESC_SERVICIO_MENSAJERIA"
	sql=sql+ "											FROM C_SERVICIO_MENSAJERIA"
	sql=sql+ "											WHERE BD_ENVIOS.ID_SERVICIO_MENSAJERIA = C_SERVICIO_MENSAJERIA.ID_SERVICIO_MENSAJERIA"
	sql=sql+ "										) END AS DESC_SERVICIO_MENSAJERIA"
	sql=sql+ "	,(SELECT ISNULL(PAGINA_WEB,'')"
	sql=sql+ "	FROM C_SERVICIO_MENSAJERIA"
	sql=sql+ "	WHERE BD_ENVIOS.ID_SERVICIO_MENSAJERIA = C_SERVICIO_MENSAJERIA.ID_SERVICIO_MENSAJERIA) AS PAGINA_WEB_SERVICIO_MENSAJERIA"
	sql=sql+ "	,NO_GUIA"
	sql=sql+ "	,ISNULL(COSTO, 0) AS COSTO"
	sql=sql+ "	,C_URGENCIA_ENVIO.DESC_URGENCIA_ENVIO"
	sql=sql+ "	,CONVERT(VARCHAR,BD_ENVIOS.FEC_ENVIO,103) AS FEC_ENVIO"
	sql=sql+ "	,CONVERT(VARCHAR,BD_ENVIOS.FEC_RECEPCION,103) AS FEC_RECEPCION"
	'sql=sql+ "	,DESC_STATUS_ENVIO "
	'sql=sql+ "	,CASE WHEN BD_ENVIOS.ID_STATUS_ENVIO = 3 AND IS_RECIBIDA = 1 THEN 'Recibido' ELSE DESC_STATUS_ENVIO END AS DESC_STATUS_ENVIO "
	sql=sql+ "	,CASE "
	sql=sql+ "		WHEN BD_ENVIOS.ID_STATUS_ENVIO = 3 AND IS_RECIBIDA = 1 THEN 'Recibido' "
	sql=sql+ "		WHEN BD_UNIDADES.ID_STATUS_UNIDAD = 18 THEN 'Robada' "
	sql=sql+ "		ELSE DESC_STATUS_ENVIO "
	sql=sql+ "	END AS DESC_STATUS_ENVIO "	
	sql=sql+ "FROM BD_ENVIO_UNIDAD "
	sql=sql+ "INNER JOIN BD_ENVIOS ON BD_ENVIOS.ID_ENVIO = BD_ENVIO_UNIDAD.ID_ENVIO "
	sql=sql+ "INNER JOIN C_STATUS_ENVIO ON BD_ENVIOS.ID_STATUS_ENVIO = C_STATUS_ENVIO.ID_STATUS_ENVIO "
	sql=sql+ "INNER JOIN C_URGENCIA_ENVIO ON BD_ENVIOS.ID_URGENCIA_ENVIO = C_URGENCIA_ENVIO.ID_URGENCIA_ENVIO "
	sql=sql+ "INNER JOIN BD_UNIDADES ON BD_ENVIO_UNIDAD.ID_UNIDAD = BD_UNIDADES.ID_UNIDAD "
	sql=sql+ "WHERE BD_ENVIOS.STATUS <> 'BORRADO'"
	sql=sql+ "		AND BD_ENVIO_UNIDAD.ID_UNIDAD = " & idUnidad & " "
	sql=sql+ "ORDER BY BD_ENVIO_UNIDAD.ID_ENVIO DESC"
	
	set rs = con.executeQuery(sql)
%>

<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-bottom:5px;margin-top:20px;">
    <tr>
        <td style="text-align:left;vertical-align:baseline;"><span style="color:#CC6666;font-size:11px;font-weight:bold;">Envios de la Unidad No. <%= idUnidad %></span></td>
        <td style="text-align:right;cursor:pointer;">&nbsp;</td>    
    </tr>
</table>

<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-top:10px;">
	<caption>&nbsp;</caption>
    <tr>
        <td style="text-align:center;" width="5%">Id</td>
        <!--td style="text-align:center;">Imágen</td-->
        <td style="text-align:center;" width="17%">Origen</td>
        <td style="text-align:center;" width="17%">Destino</td>
        <td style="text-align:center;" width="10%">Tipo de env&iacute;o</td>
        <td style="text-align:center;" width="5%">No. Gu&iacute;a</td>
        <td style="text-align:center;" width="10%">Costo</td>
        <td style="text-align:center;" width="8%">Urgencia</td>
        <td style="text-align:center;" width="10%">Env&iacute;o</td>
        <td style="text-align:center;" width="10%">Recepci&oacute;n</td>
        <td style="text-align:center;" width="8%">Status</td>
    </tr>

<%
if not rs.EOF then

	do until rs.EOF	
		
		dim servicioMensajeria, queryStringServicioMensajeria
		if rs.fields("PAGINA_WEB_SERVICIO_MENSAJERIA") = "" then
			servicioMensajeria = rs.fields("DESC_SERVICIO_MENSAJERIA")
			queryStringServicioMensajeria = ""		
		else
		
			if rs.fields("ID_SERVICIO_MENSAJERIA") = "2" then
				queryStringServicioMensajeria = "/publish/mx/es/eshipping/RastreoyLocalizacion2.high.html?pageToInclude=RESULTS&AWB=" & rs.fields("NO_GUIA") & "&type=fasttrack&awb_hidden=" & rs.fields("NO_GUIA")
			else
				queryStringServicioMensajeria = ""	
			end if
		
			servicioMensajeria = "<a href='" & rs.fields("PAGINA_WEB_SERVICIO_MENSAJERIA") & queryStringServicioMensajeria & "' target='_blank'>" & rs.fields("DESC_SERVICIO_MENSAJERIA") & "</a>"
		
		end if	
%>

    <tr>
        <td class="tdMenuCenter" width="5%"><%= rs.fields("ID_ENVIO") %></td>
        <!--td> &nbsp; </td-->
        <td class="tdMenuCenter" width="17%"><%= rs.fields("DESC_RESPONSABLE_ORIGEN") %></td>
        <td class="tdMenuCenter" width="17%"><%= rs.fields("DESC_RESPONSABLE_DESTINO") %></td>
        <td class="tdMenuCenter" width="10%"><%= servicioMensajeria %></td>
        <td class="tdMenuCenter" width="5%"><%= rs.fields("NO_GUIA") %></td>
        <td class="tdMenuCenter" width="10%">$&nbsp;<%= formatNumber(rs.fields("COSTO")) %></td>
        <td class="tdMenuCenter" width="8%"><%= rs.fields("DESC_URGENCIA_ENVIO") %></td>
        <!--td class="tdMenuCenter">&nbsp;</td-->
        <td class="tdMenuCenter" width="10%"><%= rs.fields("FEC_ENVIO") %></td>
        <td class="tdMenuCenter" width="10%"><%= rs.fields("FEC_RECEPCION") %></td>
        <td class="tdMenuCenter" width="8%"><%= rs.fields("DESC_STATUS_ENVIO") %></td>
    </tr>
<% 
	rs.MoveNext
	loop
	'response.Write("</table>")
else
	
	response.Write("<tr><td class='tdMenuCenter' colspan='8'>No existen env&iacute;os para esta Unidad.</td></tr>")
	
end if	

	rs.close
	set rs= nothing
	con.endConnection
%>
</table>
<br>
<!--FIN Envíos de una Unidad-->


<!--INICIO Reingresos de una Unidad-->
<%
	set con = new cConnection
	con.startConnection(DB_INI)
	
	sql= "SELECT "
	sql=sql+ " FEC_REINGRESO, "
	sql=sql+ " ID_SOLICITUD_RECOLECCION_ANTERIOR, "
	sql=sql+ " DESC_STATUS_UNIDAD AS STATUS_UNIDAD_ANTERIOR, "
	sql=sql+ " C_TIPO_RESPONSABLE.DESC_TIPO_RESPONSABLE AS TIPO_RESPONSABLE, "
	sql=sql+ " CASE WHEN ID_TIPO_RESPONSABLE_REINGRESO = 1 "
	sql=sql+ " 	THEN "
	sql=sql+ "		C_ALMACENES.DESC_ALMACEN "
	sql=sql+ " 	ELSE "
	sql=sql+ " 	CASE WHEN ID_TIPO_RESPONSABLE_REINGRESO = 2 "
	sql=sql+ " 		THEN "
	sql=sql+ " 	A.NOMBRE + ' ' + A.PATERNO + ' ' + A.MATERNO "
	sql=sql+ " 		END "
	sql=sql+ " END AS RESPONSABLE, "
	sql=sql+ " 	B.NOMBRE + ' ' + B.PATERNO + ' ' + B.MATERNO AS USUARIO_ALTA"
	sql=sql+ " FROM BD_REINGRESO_UNIDAD "
	sql=sql+ " INNER JOIN C_STATUS_UNIDAD ON C_STATUS_UNIDAD.ID_STATUS_UNIDAD = BD_REINGRESO_UNIDAD.ID_STATUS_UNIDAD_ANTERIOR "
	sql=sql+ " INNER JOIN C_TIPO_RESPONSABLE ON C_TIPO_RESPONSABLE.ID_TIPO_RESPONSABLE = BD_REINGRESO_UNIDAD.ID_TIPO_RESPONSABLE_REINGRESO "
	sql=sql+ " INNER JOIN C_ALMACENES ON C_ALMACENES.ID_ALMACEN = BD_REINGRESO_UNIDAD.ID_RESPONSABLE_REINGRESO "
	sql=sql+ " INNER JOIN C_USUARIOS A ON A.ID_USUARIO = BD_REINGRESO_UNIDAD.ID_RESPONSABLE_REINGRESO "
	sql=sql+ " INNER JOIN C_USUARIOS B ON B.ID_USUARIO = BD_REINGRESO_UNIDAD.ID_USUARIO_REINGRESO "
	sql=sql+ " WHERE ID_UNIDAD = " & idUnidad
	sql=sql+ " ORDER BY FEC_REINGRESO ASC "
	
	set rs = con.executeQuery(sql)
%>

<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-bottom:5px;margin-top:20px;">
    <tr>
        <td style="text-align:left;vertical-align:baseline;"><span style="color:#CC6666;font-size:11px;font-weight:bold;">Reingresos de la Unidad No. <%= idUnidad %></span></td>
        <td style="text-align:right;cursor:pointer;">&nbsp;</td>    
    </tr>
</table>

<table align="center" style="width:98%" style="border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-top:10px;">
	<caption>&nbsp;</caption>
    <tr>
        <td style="text-align:center;" width="15%">Fecha Reingreso</td>
        <td style="text-align:center;" width="15%">Solicitud Recolecci&oacute;n Anterior</td>
        <td style="text-align:center;" width="25%">Status Unidad Anterior</td>
        <td style="text-align:center;" width="25%">Responsable</td>
        <td style="text-align:center;" width="20%">Usuario Alta</td>
    </tr>

<%
if not rs.EOF then

	do until rs.EOF	
%>

    <tr>
        <td class="tdMenuCenter"><%= rs.fields("FEC_REINGRESO") %></td>
        <td class="tdMenuCenter"><%= rs.fields("ID_SOLICITUD_RECOLECCION_ANTERIOR") %></td>
        <td class="tdMenuCenter"><%= rs.fields("STATUS_UNIDAD_ANTERIOR") %></td>
        <td class="tdMenuCenter"><%= rs.fields("TIPO_RESPONSABLE") %>: <%= rs.fields("RESPONSABLE") %></td>
        <td class="tdMenuCenter"><%= rs.fields("USUARIO_ALTA") %></td>
    </tr>
<% 
	rs.MoveNext
	loop
	'response.Write("</table>")
else
	
	response.Write("<tr><td class='tdMenuCenter' colspan='5'>No existen reingresos para esta Unidad.</td></tr>")
	
end if	

	rs.close
	set rs= nothing
	con.endConnection
%>
</table>
<br>
<!--FIN Reingresos de una Unidad-->


<% if ID_TIPO_USUARIO = "1" then %>
<table align="center" style="width:98%;border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-bottom:5px;">
	<caption>
    Otras Acciones
    </caption>
    <tr>
		<td class="tdMenuLeft" style="cursor:pointer;" onClick="if(confirm('¿Confirmar que desea marcar la unidad como robada?')) window.location='./DAO/updateRobada.asp?ID_UNIDAD=<%= idUnidad %>'">&nbsp;&nbsp;<img src="../images/bullet_off.gif" border="0">&nbsp;Marcar Unidad Como Robada.</td>   
    </tr>
     <tr>
		<td class="tdMenuLeft" style="cursor:pointer;" onClick="openUpdateInfoUnidadDHTMLWindow(<%=idUnidad%>);">&nbsp;&nbsp;<img src="../images/bullet_off.gif" border="0">&nbsp;Modificar Información Unidad</td>
    </tr>
</table>   
<% end if %>

<table align="center" style="width:98%;border-top-color:#CCCCCC;border-top-style:solid;border-top-width:1px;margin-top:20px;">
    <tr>
        <td class="tdMenuCenter"><%= nombreCompania %></td>
    </tr>
</table> 

<br />

</BODY>
</HTML>