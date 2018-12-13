<%

function validaDigitos(cadena,MIN, MAX)

	if cadena = "" then exit function
	
		cont = 0
		
		for n=1 to Len(cadena)
			if IsNumeric(Mid(cadena,n,1)) then
				cont = cont + 1
			end if
		next
		
		if cont > MIN-1 and cont < MAX+1 then
			validaDigitos = "OK"
		else
			validaDigitos = "ERROR"
		end if

End function
 

%>