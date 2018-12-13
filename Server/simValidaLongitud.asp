<%
Function ValidaLongitud(Cadena, Min, Max)

If Cadena = "" Then Exit Function

	If Len(Cadena) < Min Then
		ValidaLongitud = "Error... Faltan Caracteres"
		Exit Function
	End If
	
	If Len(Cadena) > Max Then
		ValidaLongitud = "Error... Excede Caracteres"
		Exit Function
	End If
	
	
		ValidaLongitud = "OK"
	
End Function
	

%>