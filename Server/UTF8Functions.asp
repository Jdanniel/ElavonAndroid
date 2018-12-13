<%
Public Function decodeUTF8(encoded)
	dim i
	dim c
	dim n

	i = 1

	do while i <= len(encoded)
		c = asc(mid(encoded,i,1))
		
		if c and &H80 then
			n = 1
			
			do while i + n < len(encoded)
				if (asc(mid(encoded,i+n,1)) and &HC0) <> &H80 then
					exit do
				end if
				
				n = n + 1
			loop
			
			if n = 2 and ((c and &HE0) = &HC0) then
				c = asc(mid(encoded,i+1,1)) + &H40 * (c and &H01)
			else
				c = 191
			end if
				
			encoded = left(encoded,i-1) + chr(c) + mid(encoded,i+n)
		end if
		
		i = i + 1
	loop

	decodeUTF8 = encoded
End Function
%>