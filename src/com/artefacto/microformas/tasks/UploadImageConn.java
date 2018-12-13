package com.artefacto.microformas.tasks;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.beans.UploadImageBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class UploadImageConn {

	String path;
	String photoName;
	String numAr;
	String idTecnico;
	
	
	public UploadImageConn(	String path,
								String photoName,
								String numAr,
								String idTecnico)
	{
		this.path = path;
		this.photoName = photoName;
		this.numAr = numAr;
		this.idTecnico = idTecnico;
	}


	public ConnTaskResultBean uploadImage(){
		UploadImageBean res = new UploadImageBean();
		ConnTaskResultBean ret = new ConnTaskResultBean();
		
		ret.setSuccess(0);
		
		if(!hasActiveInternetConnection()){
			ret.setStatus("No hay conexión a internet.");
			return ret;
		}
			 
		res = HTTPConnections.uploadImage(path, photoName, numAr, idTecnico);
		
		if(res.isSuccelfulInfo()){
			ret.setStatus("Envio correcto.");
			ret.setSuccess(1);
		}else if(res.isSuccesfulSend()){
			ret.setStatus("La imagen se ha enviado satisfactoriamente, pero no se ha podido actualizar la base de datos.");
		}
		else{
			ret.setStatus("Error al enviar.");
		}
		
		return ret;
	}
	
	public static boolean hasActiveInternetConnection() {
	        try {
	            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
	            urlc.setRequestProperty("User-Agent", "Test");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(1500); 
	            urlc.connect();
	            return (urlc.getResponseCode() == 200);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	    return false;
	}
}