<!--#include file="../../connections/Session.asp"-->
<!--#include file="../../connections/constants.asp"-->
<!--#include file="../../connections/cConnection.asp"-->
<!--#include file="../../DAO/sendSQLEmail.asp"-->

	<%
	
		idUnidad = request.QueryString("ID_UNIDAD")			
		
		''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' Cambiar Status de la Unidad
		
			set con = new cConnection
			con.startConnection(DB_INI)		
	
			dim statusActual,idTipoResponsable,idResponsable
			sql = 	"SELECT ID_STATUS_UNIDAD,ID_TIPO_RESPONSABLE,ID_RESPONSABLE FROM BD_UNIDADES WHERE ID_UNIDAD = " & idUnidad
			set rs = con.executeQuery(sql)	
			if not rs.eof then
			
				statusActual = rs.fields("ID_STATUS_UNIDAD")
				idTipoResponsable = rs.fields("ID_TIPO_RESPONSABLE")
				idResponsable = rs.fields("ID_RESPONSABLE")								
			
			end if
			rs.close
			set rs = nothing	

					
			sql = 	"UPDATE BD_UNIDADES SET" &_
					" BD_UNIDADES.ID_STATUS_UNIDAD = " & 18 &_					
					" WHERE BD_UNIDADES.ID_UNIDAD = " & idUnidad
			con.executeQuery(sql)						
					
			'' Ingresar Cambio de Status en Bitácora
			sql = 	"INSERT INTO BD_BITACORA_UNIDAD" &_
					"(ID_UNIDAD" &_
					",ID_STATUS_UNIDAD_INI" &_
					",ID_STATUS_UNIDAD_FIN" &_
					",ID_TIPO_RESPONSABLE" &_
					",ID_RESPONSABLE" &_						
					",ID_USUARIO_ALTA" &_
					",FEC_ALTA)" &_
					"VALUES" &_
					"(" & idUnidad &_
					"," & statusActual &_
					",18" &_
					"," & idTipoResponsable &_
					"," & idResponsable &_												
					"," & ID_USUARIO &_
					",GETDATE())"
			con.executeQuery(sql)			

	con.endConnection		
	response.redirect("../bitacoraUnidad.asp?ID_UNIDAD=" & idUnidad)
		
    %>