
<% 
function CarInvalido(strString,strAlpha)
    	Dim itmCur, strCur
    	CarInvalido = "False"
'    	strAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    	For itmCur = 1 To Len(strString)
    		strCur = Mid(strString, itmCur, 1)
    		if Not Instr(1, strAlpha, strCur) > 0 Then
    			CarInvalido = "True"
    			Exit function
    		End if
    	Next
'IF InStr(alldata(6,x),"HH")
End function
%>