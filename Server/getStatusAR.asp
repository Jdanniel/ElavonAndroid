<!--#include file="../connections/constants.asp"-->
<!--#include file="../connections/cConnection.asp"-->


<%
response.Charset="iso-8859-1"
response.ContentType="text/xml"
''Server.ScriptTimeout=3600

set con = new cConnection
con.startConnection(DB_INI)

sql= "SELECT"
sql=sql+" DISTINCT A.ID_STATUS_AR, DESC_STATUS_AR, IS_BB_NUEVAS, IS_BB_CERRADAS, IS_BB_PENDIENTES, IS_BB_ABIERTAS,"
sql=sql+" IS_SOLICITUD_ALMACEN, IS_SOLICITUD_VIATICOS, STATUS, ORDEN"
sql=sql+" FROM C_STATUS_AR A"
sql=sql+" INNER JOIN BD_STATUS_TIPO_USUARIO S ON S.ID_STATUS_AR = S.ID_STATUS_AR"
sql=sql+" WHERE ID_TIPO_USUARIO = 8"

set rs = con.executeQuery(sql)

response.write("<?xml version=""1.0"" encoding=""iso-8859-1""?>")
response.write("<d>")

'A=ID_STATUS_AR
'B=IS_BB_NUEVAS
'C=IS_BB_CERRADAS
'D=IS_BB_PENDIENTES
'E=IS_BB_ABIERTAS
'F=IS_SOLICITUD_ALMACEN
'G=IS_SOLICITUD_VIATICOS
'H=STATUS
'I=ORDEN
'H=DESC_STATUS_AR

if not rs.EOF then
	Do until rs.EOF
		response.write("<sa A = """ & rs.fields("ID_STATUS_AR") &_ 
					    """ B = """ & rs.fields("IS_BB_NUEVAS") &_ 
						""" C = """ & rs.fields("IS_BB_CERRADAS") &_ 
						""" D = """ & rs.fields("IS_BB_PENDIENTES") &_ 
						""" E = """ & rs.fields("IS_BB_ABIERTAS") &_ 
						""" F = """ & rs.fields("IS_SOLICITUD_ALMACEN") &_ 
						""" G = """ & rs.fields("IS_SOLICITUD_VIATICOS") &_ 
						""" H = """ & rs.fields("STATUS") &_ 
						""" I = """ & rs.fields("ORDEN") &_ 
						""">")
		response.write("<J><![CDATA["&rs.fields("DESC_STATUS_AR")&"]]></J>")
		response.write("</sa>")
		rs.MoveNext
	Loop
end if

response.write("</d>")

rs.close
set rs = Nothing
con.endConnection

%>