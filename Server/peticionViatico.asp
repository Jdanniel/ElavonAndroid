<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

Dim idAR
Dim idTecnico
Dim viaticos
Dim costos
Dim lugar
Dim idUrgencia
Dim observaciones
Dim arregloViaticos
Dim arregloCostos
Dim conFailed
Dim failed
Dim maxTry

if request.QueryString("ia") <> "" then
	idAR = request.QueryString("ia")
else
	idAR = 0
end if

if request.QueryString("i") <> "" then
	idTecnico = cInt(request.QueryString("i"))
else
	idTecnico = 0
end if

if request.QueryString("l") <> "" then
	lugar = request.QueryString("l")
else
	lugar = ""
end if

if request.QueryString("o") <> "" then
	observaciones = request.QueryString("o")
else
	observaciones = ""
end if

if request.QueryString("u") <> "" then
	idUrgencia = cInt(request.QueryString("u"))
else
	idUrgencia = 0
end if

if request.QueryString("v") <> "" then
	viaticos = request.QueryString("v")
else
	viaticos = ""
end if

if request.QueryString("c") <> "" then
	costos = request.QueryString("c")
else
	costos = ""
end if

conFailed = 1
maxTry = 0
failed = true

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")
if (idAR <> 0 and idTecnico <> 0 and idUrgencia <> 0 and viaticos <> "" and costos <> "") then
	arregloViaticos = split(viaticos, ",")
	arregloCostos = split(costos, ",")

	set con = new cConnection
	
	On error resume next
		con.startConnection(DB_INI)
	if Err then
		conFailed = 0
	end if
	
	if conFailed <> 0 then
	
		sql= "SELECT"
		sql=sql+" ID_TECNICO, ID_STATUS_AR"
		sql=sql+" FROM dbo.BD_AR"
		sql=sql+" WHERE ID_AR="&idAR&" AND ID_TECNICO = "&idTecnico
		
		set rsValidacion = con.executeQuery(sql)
		
		Dim miBandera
		miBandera = 0
		
		if not rsValidacion.EOF then
			Do until rsValidacion.EOF
				if rsValidacion.fields("ID_STATUS_AR")<>"8" and rsValidacion.fields("ID_STATUS_AR")<>"2" then
					
					sql = "EXEC SP_SOLICITUD_VIATICOS " & idAR & ", '" & lugar & "', '" & observaciones & "', " & idTecnico
		
					on error resume next
					con.executeQuery(sql)
					
					if UBound(arregloViaticos) > 0 then
						for i = 0 to UBound(arregloViaticos)
							sql = "EXEC SP_SOLICITUD_VIATICOS_SOLICITUD_CONCEPTOS " & idAR & ", " & arregloViaticos(i) & ", " & arregloCostos(i) & ", " & idTecnico
							response.write("#" & i & ": " & sql & "		")
							con.executeQuery(sql)
						next
					else
						sql = "EXEC SP_SOLICITUD_VIATICOS_SOLICITUD_CONCEPTOS " & idAR & ", " & viaticos & ", " & costos & ", " & idTecnico
						con.executeQuery(sql)
					end if
			
					if Err then
						maxTry = maxTry + 1
						if maxTry < 10 then
							failed = true
						else
							failed = false
						end if
					else
						failed = false
					end if
					
					if failed = false then
						response.write("<r res=""OK"" />")
					else
						response.write("<r res=""ERROR"" desc=""Error de Sql 1: " & Err.description & """/>")
					end if

				end if
				rsValidacion.MoveNext
			Loop
		end if
		rsValidacion.close
		set rsValidacion = Nothing
		
	else
		response.write("<r res=""ERROR"" desc=""Error de Sql 2: " & Err.description & """/>")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error de Sql 3: " & Err.description & """/>")
end if

response.write("</d>")

%>