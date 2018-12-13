<%

function validaLetras(cadena,MIN, MAX)

	if cadena = "" then exit function
	
		cont = 0
		
		for n=1 to Len(cadena)
			if Asc(UCase(Mid(cadena,n,1))) >= Asc("A") and  Asc(UCase(Mid(cadena,n,1))) <= Asc("Z") then
				cont = cont + 1
			end if
		next

		if cont > MIN-1 and cont < MAX+1 then
			validaLetras = "OK"
		else
			validaLetras = "ERROR"
		end if

End function
 

%>