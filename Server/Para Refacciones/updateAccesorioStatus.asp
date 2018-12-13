<!--#include file="../../connections/Session.asp"-->
<!--#include file="../../connections/constants.asp"-->
<!--#include file="../../connections/cConnection.asp"-->
<!--#include file="../../DAO/sendSQLEmail.asp"-->

	<%
	
		if request.Form("ID_UNIDAD") <> "" then
			idUnidad = request.Form("ID_UNIDAD")
		elseif request.QueryString("ID_UNIDAD") <> "" then
			idUnidad = request.QueryString("ID_UNIDAD")
		else
			idUnidad = ""
		end if	
		
		if request.Form("ID_UNIDAD_ACCESORIO") <> "" then
			idUnidadAccesorio = request.Form("ID_UNIDAD_ACCESORIO")
		elseif request.QueryString("ID_UNIDAD_ACCESORIO") <> "" then
			idUnidadAccesorio = request.QueryString("ID_UNIDAD_ACCESORIO")
		else
			idUnidadAccesorio = ""
		end if		
		
		if request.Form("ID_STATUS_ACCESORIO") <> "" then
			idStatusAccesorio = request.Form("ID_STATUS_ACCESORIO")
		elseif request.QueryString("ID_STATUS_ACCESORIO") <> "" then
			idStatusAccesorio = request.QueryString("ID_STATUS_ACCESORIO")
		else
			idStatusAccesorio = ""
		end if
		
		if request.Form("NO_SERIE") <> "" then
			noSerie = request.Form("NO_SERIE")
		elseif request.QueryString("NO_SERIE") <> "" then
			noSerie = request.QueryString("NO_SERIE")
		else
			noSerie = ""
		end if				
		
		if request.Form("EDIT") <> "" then
			edit = request.Form("EDIT")
		elseif request.QueryString("EDIT") <> "" then
			edit = request.QueryString("EDIT")
		else
			edit = ""
		end if					
		
		set con = new cConnection
		con.startConnection(DB_INI)
						
				sql = 	"UPDATE BD_UNIDAD_ACCESORIO SET" &_
						" ID_STATUS_ACCESORIO = " & idStatusAccesorio &_
						" ,NO_SERIE = '" & noSerie & "'" &_								
						" ,FEC_STATUS = GETDATE()" &_
						" ,ID_USUARIO_STATUS = " & ID_USUARIO &_																
						" WHERE ID_UNIDAD_ACCESORIO = " & idUnidadAccesorio												
		
		con.executeQuery(sql)
		con.endConnection	
								
 		Response.Redirect("../accesoriosUnidad.asp?ID_UNIDAD=" & idUnidad & "&EDIT=" & edit)

		
    %>