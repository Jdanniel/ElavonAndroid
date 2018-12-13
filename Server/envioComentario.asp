<!--#include file="UTF8Functions.asp"-->
<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"

Dim idAR
Dim idTecnico
Dim notas
Dim fechaActual
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

if request.QueryString("n") <> "" then
	notas = request.QueryString("n")
else
	notas = ""
end if

conFailed = 1
maxTry = 0
failed = true

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

if (idAR <> 0 and idTecnico <> 0) then
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
		
		
		
		if not rsValidacion.EOF then
			Do until rsValidacion.EOF
				if rsValidacion.fields("ID_STATUS_AR")<>"8" and rsValidacion.fields("ID_STATUS_AR")<>"2" then
					fecha = "SELECT CAST(GETDATE() AS SMALLDATETIME) AS FECHA"
					set rs = con.executeQuery(fecha)
			
					if not rs.EOF then
						fechaActual = rs.fields("FECHA")
					end if
			
					arregloFecha = split(fechaActual, "/")
					fechaActual = "" & arregloFecha(1) & "/" & arregloFecha(0) & "/" & arregloFecha(2) & ""
						
					sql = "INSERT INTO BD_COMENTARIO_AR (ID_AR, DESC_COMENTARIO_AR, ID_USUARIO_ALTA, FEC_ALTA) "
					sql = sql + "VALUES (" & idAR & ", '" & decodeUTF8(notas) & "', " & idTecnico & ", '" & fechaActual & "')"
					
					
					sql2 = "DECLARE @id int "
					sql2 = sql2 + "select top(1)@id=id_outbox from wincor_outbox where helpdesk in( "
					sql2 = sql2 + "select no_ar from bd_ar  "
					sql2 = sql2 + " inner join c_clientes on c_clientes.id_cliente = bd_ar.id_cliente "
					sql2 = sql2 + " inner join bd_cargas on bd_cargas.id_carga = bd_ar.id_carga "
					sql2 = sql2 + " inner join c_usuarios on bd_cargas.id_usuario_alta = c_usuarios.id_usuario "
					sql2 = sql2 + "where is_wincor = 1  "
					sql2 = sql2 + " and nombre = 'Interfaz Wincor' "
					sql2 = sql2 + " and id_ar = " & idAR
					sql2 = sql2 + ") "
					sql2 = sql2 + " if @id is not null "
					sql2 = sql2 + "begin "
					sql2 = sql2 + "declare @fec smalldatetime "
					sql2 = sql2 + "set @fec = getdate() "
					sql2 = sql2 + "exec SP_GENERA_STATUS_WINCOR @id, 'Comment',0,@fec,0,'" & notas & "'"
					sql2 = sql2 + "end "
					
					on error resume next
					con.executeQuery(sql)
					con.ExecuteQuery(sql2)
			
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
						response.write("<r res=""ERROR"" desc=""Error de Sql: " & Err.description & """/>")
					end if
				end if
				rsValidacion.MoveNext
			Loop
		end if
		rsValidacion.close
		set rsValidacion = Nothing
		
	else
		response.write("<r res=""ERROR"" desc=""Error de Sql: " & Err.description & """/>")
	end if
else
	response.write("<r res=""ERROR"" desc=""Error de Sql: " & Err.description & """/>")
end if

response.write("</d>")

%>