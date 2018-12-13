<!--#include file="../connections/Session.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->
<!--#include file="../DAO/capitalizeString.asp"-->
<!--#include file="../DAO/displayAttach.asp"-->

<%

if request.Form("ID_AR") <> "" then
	idAR = request.Form("ID_AR")
elseif request.QueryString("ID_AR") <> "" then
	idAR = request.QueryString("ID_AR")
else
	idAR = ""
end if

set con = new cConnection
con.startConnection(DB_INI)

	sql = ""
	sql = sql & "SELECT "
	sql = sql & "	 BD_AR.ID_PRODUCTO "
	sql = sql & "	,ISNULL(ID_UNIDAD_ATENDIDA, -1) AS ID_UNIDAD_ATENDIDA "
	sql = sql & "	,BD_AR.ID_NEGOCIO "
	sql = sql & "	,BD_AR.ID_CLIENTE "
	sql = sql & "FROM BD_AR "
	sql = sql & "LEFT JOIN BD_NEGOCIOS ON BD_AR.ID_NEGOCIO = BD_NEGOCIOS.ID_NEGOCIO "
	sql = sql & "LEFT JOIN BD_UNIDADES ON BD_AR.ID_UNIDAD_ATENDIDA = BD_UNIDADES.ID_UNIDAD "
	sql = sql & "WHERE BD_AR.ID_AR = " & idAR & ";"
	
	set rs = con.executeQuery(sql)
	idProducto = rs.fields("ID_PRODUCTO")
	idNegocio = rs.fields("ID_NEGOCIO")
	idCliente = rs.fields("ID_CLIENTE")
	idUnidadAtendida = rs.fields("ID_UNIDAD_ATENDIDA")
							
rs.close
set rs = nothing
con.endConnection

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=windows-1252">
<TITLE></TITLE>
<link HREF="../CSS/styles.css" REL="stylesheet" TYPE="text/css">
<link href="../CSS/dhtmlxcombo.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../JS/dhtmlxcommon.js"></script>
<script type="text/javascript" src="../JS/dhtmlxcombo.js"></script>
<script type="text/javascript" src="../JS/scriptLibrary.js"></script>
<script type="text/javascript" src="./JS/AjaxGetInfoByNoSerie.js"></script>
<script>

function init(){

		//Inicializar Combo
	window.dhx_globalImgPath="../IMAGES/DHTMLXCOMBO/";
	z=dhtmlXComboFromSelect("ID_MODELO_MODULO");
	z.loadXML("./DAO/completeSku.asp?ID_AR=<%= idAR %>"); 
	z.enableFilteringMode(true,"./DAO/completeSku.asp?ID_AR=<%= idAR %>",false);
	z.setComboValue("")	;


	setRequired(["Sku"], true);
	setRequired(["NO_SERIE"], true);
	
	setOnlySerialNumbers(['NO_SERIE']);
	setOnlyCurrency(['COSTO']);
}

function submitForm(){

	if (REQ("insertRefaccionUnidad")){
	document.insertRefaccionUnidad.submit()
	}
	
}

function fnIsNueva(obj){
		if(obj.checked){
			setRequired(["COSTO"], true);
			setRequired(["ID_MONEDA"], true);
			findObj("trCosto").style.display = "";
			findObj("trMoneda").style.display = "";
		}
		else{
			setRequired(["COSTO"], false);
			setRequired(["ID_MONEDA"], false);
			findObj("COSTO").value = "";
			findObj("ID_MONEDA").value = "";
			findObj("trCosto").style.display = "none";
			findObj("trMoneda").style.display = "none";
		}
	}
</script>
</HEAD>

<BODY STYLE="margin-top:5px; padding-top:0px; margin-left:0px; padding-left:0px" onLoad="init();">  

<table align="center" style="width:98%;border-color:#CCCCCC;border-style:solid;border-width:1px; margin-bottom:5px;">
    <tr>
        <td style="text-align:left;border:inherit;">&nbsp;Negocios : <span style="color:#CC6666;font-size:11px;font-weight:bold">Agregar Nueva Refacción a Unidad del Negocio <%= descNegocio %></span></td>
        <td style="text-align:right;border:inherit;">&nbsp;</td>    
    </tr>
</table> 

<form name="insertRefaccionUnidad" id="insertRefaccionUnidad" method="post" action="./DAO/insertRefaccionUnidad.asp">
	<input type="hidden" name="ID_AR" id="ID_AR" value="<%= idAR %>">

<table width="98%" border=0 align="CENTER" cellspacing=2>
  <caption>
	Campos
  </caption>
  <thead>
    <th colspan="4">&nbsp;</th>
  </thead>
	<tr>
		<td class="tdMenuRight" id="tdNoSerieLabel" name="tdNoSerieLabel"><span class="redRequiredMark">*</span>&nbsp;No. Serie:</td>
		<td class="tdMenuLeft" id="tdNoSerieField" name="tdNoSerieField"><input type="text" name="NO_SERIE" id="NO_SERIE" class="formGeneralTextField" maxlength="50" messReq="No. Serie" style="width:250px;"></td>                       
	</tr>
  	<tr>
		<td class="tdMenuRight" width="15%"><span class="redRequiredMark">*</span>&nbsp;Sku:</td>
		<td class="tdMenuLeft" width="85%">
            <select name="Sku" id="ID_MODELO_MODULO" messReq="Sku" style="width:400px;"></select>
		</td>                       
	</tr>
	<tr>
    	<td class="tdMenuRight">Nueva:</td>
        <td class="tdMenuLeft"><input type="checkbox" id="IS_NUEVA" name="IS_NUEVA" onClick="fnIsNueva(this);" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            Da&ntilde;ada:<input type="checkbox" id="IS_DANIADA" name="IS_DANIADA" />
        </td>
  	</tr>
    <tr id="trCosto" style="display:none;">
		<td class="tdMenuRight" width="15%"><span class="redRequiredMark">*</span>&nbsp;Costo:</td>
		<td class="tdMenuLeft" width="85%">
            <input type="text" id="COSTO" name="COSTO" messReq="Costo" class="form16NoAlignedNumberField" />
		</td>                       
	</tr>
    <tr id="trMoneda" style="display:none;">
		<td class="tdMenuRight" width="15%"><span class="redRequiredMark">*</span>&nbsp;Moneda:</td>
		<td class="tdMenuLeft" width="85%">
        <%
            set con = new cConnection
            con.startConnection(DB_INI)
			
			sql=     ""
			sql=sql+ "SELECT "
			sql=sql+ "	 ID_MONEDA "
			sql=sql+ "	,DESC_MONEDA "
			sql=sql+ "FROM C_MONEDAS "
			sql=sql+ "WHERE "
			sql=sql+ "	STATUS = 'ACTIVO'; "
			
            set rs = con.executeQuery(sql)
            if not rs.eof then
		%>
        	<select name="ID_MONEDA" id="ID_MONEDA" messReq="Moneda" style="width:130px;">
            	<option value="">Seleccione...</option>
        <%
            
                do until rs.EOF
                
                    response.write("<option value='" & rs.fields("ID_MONEDA") & "'>" & rs.fields("DESC_MONEDA") & "</option>")
                
                rs.MoveNext
                loop
		%>
        	</select>
        <%
                
            end if
            
            rs.close
            set rs = nothing
            con.endConnection						
        %>
		</td>                       
	</tr>
</table>

<table width="98%" border=0 align="CENTER" cellspacing=2>
 	</tr>  
		<td class="tdFormCenter" width="90%"><input type="button" value="Aceptar" onClick="submitForm();" ></td>    
		<td style="text-align:right;cursor:pointer;" onClick="window.location='./infoRefaccionesUnidadNegocio.asp?ID_NEGOCIO=<%= idNegocio %>&ID_AR=<%= idAR %>'"><img src="../IMAGES/btBackMenu.png" border="0px">&nbsp;Regresar&nbsp;</td>
  	</tr>
</table> 
</form>

</BODY>
</HTML>