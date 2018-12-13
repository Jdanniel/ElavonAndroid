<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->

<%
response.Charset="iso-8859-1"
response.ContentType="text/html"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

''unidades del broder
sql = 	"SELECT top(25) * from BD_BITACORA_AR WHERE ID_AR=1181165 OR ID_AR=1181172 ORDER BY FEC_ALTA DESC"		
	
set rs = con.executeQuery(sql)	
if not rs.EOF then
	do until rs.EOF
		response.write("<ut ID_AR =""" 				& rs.fields("ID_AR") & """ ")
		response.write("ID_STATUS_AR_INI =""" 		& rs.fields("ID_STATUS_AR_INI") & """ ")
		response.write("ID_STATUS_AR_FIN ="""		& rs.fields("ID_STATUS_AR_FIN") & """ ")
		response.write("IS_PDA=""" 					& rs.fields("IS_PDA") & """ ")
		response.write("IS_CAMBIO_VALIDO=""" 		& rs.fields("IS_CAMBIO_VALIDO") & """ ")
		response.write("ID_USUARIO_ALTA =""" 		& rs.fields("ID_USUARIO_ALTA") & """ ")
		response.write("FEC_ALTA=""" 				& rs.fields("FEC_ALTA") & """ ")
					
		rs.MoveNext
	loop
				
	rs.close
	set rs = nothing
else
	response.write("<otror res=""OK"" val=""3"" desc=""El Tecnico no tiene unidades"" />")
end if
	
response.write("</d>")
response.end()

rs.close
set rs = Nothing
con.endConnection
%>