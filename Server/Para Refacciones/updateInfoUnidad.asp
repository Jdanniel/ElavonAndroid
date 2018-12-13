<!--#include file="../connections/Session.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

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
	sql=sql+",CASE WHEN C_PRODUCTOS.ID_TIPO_PRODUCTO = 2 THEN "
	sql=sql+"	( "
	sql=sql+"		SELECT C_PRODUCTOS.ID_PRODUCTO "
	sql=sql+"		FROM C_PRODUCTOS "
	sql=sql+"		INNER JOIN C_MARCAS "
	sql=sql+"			ON C_PRODUCTOS.ID_PRODUCTO = C_MARCAS.ID_PRODUCTO "
	sql=sql+"		INNER JOIN C_MODELOS "
	sql=sql+"			ON C_MARCAS.ID_MARCA = C_MODELOS.ID_MARCA "
	sql=sql+"		INNER JOIN BD_MODELO_MODULO "
	sql=sql+"			ON BD_MODELO_MODULO.ID_MODELO = C_MODELOS.ID_MODELO "
	sql=sql+"		WHERE ID_MODELO_MODULO IN "
	sql=sql+"									( "
	sql=sql+"										SELECT C_MODELOS.ID_MODELO "
	sql=sql+"										FROM C_PRODUCTOS "
	sql=sql+"										INNER JOIN C_MARCAS "
	sql=sql+"											ON C_PRODUCTOS.ID_PRODUCTO = C_MARCAS.ID_PRODUCTO "
	sql=sql+"										INNER JOIN C_MODELOS "
	sql=sql+"											ON C_MARCAS.ID_MARCA = C_MODELOS.ID_MARCA "
	sql=sql+"										INNER JOIN BD_MODELO_MODULO "
	sql=sql+"											ON BD_MODELO_MODULO.ID_MODELO_MODULO = C_MODELOS.ID_MODELO "
	sql=sql+"										WHERE ID_MODELO_MODULO = BD_UNIDADES.ID_MODELO"
	sql=sql+"									) "
	sql=sql+"		GROUP BY C_PRODUCTOS.ID_PRODUCTO "
	sql=sql+"	) "
	sql=sql+"ELSE "
	sql=sql+"	BD_UNIDADES.ID_PRODUCTO "
	sql=sql+"END AS ID_PRODUCTO"
	sql=sql+",C_MODELOS.DESC_MODELO"
	sql=sql+",C_MODELOS.COSTO"	
	sql=sql+",C_MODELOS.IS_GPRS"		
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
	sql=sql+" FROM BD_UNIDADES"
	sql=sql+" INNER JOIN C_MODELOS ON BD_UNIDADES.ID_MODELO = C_MODELOS.ID_MODELO"
	sql=sql+" INNER JOIN C_MARCAS ON BD_UNIDADES.ID_MARCA = C_MARCAS.ID_MARCA"	
	sql=sql+" INNER JOIN C_CLIENTES ON BD_UNIDADES.ID_CLIENTE = C_CLIENTES.ID_CLIENTE"					
	sql=sql+" INNER JOIN C_STATUS_UNIDAD ON BD_UNIDADES.ID_STATUS_UNIDAD = C_STATUS_UNIDAD.ID_STATUS_UNIDAD"
	sql=sql+" INNER JOIN C_PRODUCTOS ON BD_UNIDADES.ID_PRODUCTO = C_PRODUCTOS.ID_PRODUCTO"
	sql=sql+" LEFT OUTER JOIN C_LLAVES ON BD_UNIDADES.ID_LLAVE = C_LLAVES.ID_LLAVE"	
	sql=sql+" LEFT OUTER JOIN C_SOFTWARE ON BD_UNIDADES.ID_SOFTWARE = C_SOFTWARE.ID_SOFTWARE"												
	sql=sql+" WHERE BD_UNIDADES.STATUS = 'ACTIVO'" 
	sql=sql+" AND BD_UNIDADES.ID_UNIDAD = " & idUnidad						
	set rs = con.executeQuery(sql)

if not rs.eof then	

	idModelo = rs.fields("ID_MODELO")
	descModelo = rs.fields("DESC_MODELO")
	idMarca = rs.fields("ID_MODELO")
	descMarca = rs.fields("DESC_MARCA")	
	noSerie = rs.fields("NO_SERIE")
	noInventario = rs.fields("NO_INVENTARIO")
	isGPRS = rs.fields("IS_GPRS")	
	idProducto = rs.fields("ID_PRODUCTO")		
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
	
	if rs.fields("IS_NUEVA") = "1" then
		isNueva = "SI"
		isNuevaChecked = "checked"		
	else
		isNueva = "NO"
		isNuevaChecked = ""		
	end if
	
	if rs.fields("IS_RETIRO") = "1" then
		isRetiro = "SI"
	else
		isRetiro = "NO"
	end if
	
	if rs.fields("IS_DANIADA") = "1" then
		isDaniada = "SI"
		isDaniadaChecked = "checked"
	else
		isDaniada = "NO"
		isDaniadaChecked = ""
	end if		
	
	if isGPRS = "1" then
	
		tdNoSIMVisibility = "visible"
		tdNoIMEIVisibility = "visible"	
		noSIMRequired = "true"	
	
	else
	
		tdNoSIMVisibility = "hidden"
		tdNoIMEIVisibility = "hidden"
		noSIMRequired = "false"			
	
	end if

end if


%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<TITLE></TITLE>
<LINK HREF="../CSS/styles.css" REL="stylesheet" TYPE="text/css">
<script type="text/javascript" src="../JS/scriptLibrary.js"></script>
<script>

function init(){

	setRequired(["NO_SERIE"], true);	
	setRequired(["NO_SIM"], <%= noSIMRequired %>);	
	
	setOnlySerialNumbers(['NO_SERIE']);	
	setOnlySerialNumbers(['NO_INVENTARIO']);
	setOnlySerialNumbers(['NO_SIM']);	
	setOnlySerialNumbers(['NO_IMEI']);		

}

function submitForm(){

	if (REQ("updateUnidad")){
		if (document.all.IS_DANIADA.checked && document.all.IS_NUEVA.value == "1"){
			alert("Si la unidad es nueva, no se puede marcar como dañada.");
		}
		else
		{
			document.updateUnidad.submit();
		}
	}
	
}

</script>
</HEAD>

<body style="margin-top:5px; margin-left:0px; margin-bottom:0px; margin-right:0px" onLoad="init();">

<form name="updateUnidad" id="updateUnidad" method="post" action="./DAO/updateInfoUnidad.asp">
<input type="hidden" name="ID_UNIDAD" id="ID_UNIDAD" value="<%= idUnidad %>">
<table align="center" style="width:98%;border-bottom-color:#CCCCCC;border-bottom-style:solid;border-bottom-width:1px;margin-bottom:5px;">
  <tr>
      <td class="tdMenuRight" style="width:100px;"><span class="redRequiredMark">*</span>&nbsp;No. Serie:</td>
      <td class="tdMenuLeft"><input type="text" name="NO_SERIE" id="NO_SERIE" class="form16NoAlignedNumberField" maxlength="50" messReq="No. Serie" value="<%= noSerie %>"></td>                       
  </tr> 
  <tr>
      <td class="tdMenuRight">No. Inventario:</td>
      <td class="tdMenuLeft"><input type="text" name="NO_INVENTARIO" id="NO_INVENTARIO" class="form16NoAlignedNumberField" maxlength="50" messReq="No. Inventario" value="<%= noInventario %>"></td>                       
  </tr>  
  <tr>
      <td class="tdMenuRight" style="visibility:<%= tdNoSIMVisibility %>;"><span class="redRequiredMark">*</span>&nbsp;No. SIM:</td>
      <td class="tdMenuLeft" style="visibility:<%= tdNoSIMVisibility %>;"><input type="text" name="NO_SIM" id="NO_SIM" class="form16NoAlignedNumberField" maxlength="50" messReq="No SIM" value="<%= noSIM %>"></td>                       
  </tr>    
  <tr>
      <td class="tdMenuRight" style="visibility:<%= tdNoIMEIVisibility %>;">No. IMEI:</td>
      <td class="tdMenuLeft" style="visibility:<%= tdNoIMEIVisibility %>;"><input type="text" name="NO_IMEI" id="NO_IMEI" class="form16NoAlignedNumberField" maxlength="50" messReq="No IMEI" value="<%= noIMEI %>"></td>                       
  </tr> 
  <tr>
      <td class="tdMenuRight">Unidad Nueva:</td>
      <td class="tdMenuLeft"><input type="checkbox" id="IS_NUEVA" name="IS_NUEVA" <%= isNuevaChecked%> /></td>                       
  </tr>   
  <tr>
      <td class="tdMenuRight">Unidad Dañada:</td>
      <td class="tdMenuLeft"><input type="checkbox" id="IS_DANIADA" name="IS_DANIADA" <%= isDaniadaChecked%> /></td>                       
  </tr> 
  <tr>
      <td class="tdMenuRight">Cliente:</td>   
      <td class="tdMenuLeft">       
          <select id="ID_CLIENTE" name="ID_CLIENTE">  
            <%
                set con = new cConnection
                con.startConnection(DB_INI)
                sql= "SELECT "
                sql=sql+ " ID_CLIENTE, "
                sql=sql+ " DESC_CLIENTE, " 
                sql=sql+ " CASE WHEN DESC_CLIENTE = '" & descCliente & "' THEN 'selected' ELSE '' END AS SELECTED_CLIENTE"
                sql=sql+ " FROM C_CLIENTES "
                sql=sql+ " WHERE C_CLIENTES.ID_PRODUCTO = " & idProducto
                sql=sql+ " AND C_CLIENTES.ID_CLIENTE <> 13"
                set rs = con.executeQuery(sql)

                if not rs.EOF then
                    do until rs.EOF

                		response.write("<option value=" & rs.fields("ID_CLIENTE") & " " & rs.fields("SELECTED_CLIENTE") & ">" & rs.fields("DESC_CLIENTE") & "</option>" & vbCrLf)

                    rs.moveNext
                    loop
                end if
				
				rs.close
				set rs= nothing
				con.endConnection				
            %>   
            </select>  
      </td>
  </tr> 
  <tr>
      <td class="tdMenuCenter" colspan="2">&nbsp;</td>                   
  </tr>    
  <tr>
      <td class="tdMenuCenter" colspan="2"><input type="button" value="Aceptar" onClick="submitForm();"></td>                   
  </tr>                 
</table>
</form>

<table style="width:98%;border-top-color:#CCCCCC;border-top-style:solid;border-top-width:1px;margin-top:20px;">
    <tr>
        <td style="text-align:center;vertical-align:baseline;"><%= nombreCompania %></td>
    </tr>
</table> 

</BODY>
</HTML>