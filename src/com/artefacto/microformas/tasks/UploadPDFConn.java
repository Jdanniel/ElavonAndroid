package com.artefacto.microformas.tasks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.transactions.DataBaseTransactions;

public class UploadPDFConn {

	String path;
	String fileName;
	String idAr;
	
	
	public UploadPDFConn(	String path,
								String fileName,String idAr)
	{
		this.path = path;
		this.fileName = fileName;
		this.idAr = idAr;
	}


	public ConnTaskResultBean uploadPDF(){
		boolean res;
		ConnTaskResultBean ret = new ConnTaskResultBean();
		
		ret.setSuccess(0);
		
		if(!hasActiveInternetConnection()){
			ret.setStatus("No hay conexión a internet.");
			return ret;
		}
			
		res = HTTPConnections.UploadPDF(path, fileName, idAr);
		
		if(res){
			ret.setStatus("Envio correcto.");
			ret.setSuccess(1);
			
			SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences("UserConfig", Context.MODE_PRIVATE);
			DataBaseTransactions dataBaseTransaction = new DataBaseTransactions();
			String connIdUser = sharedPreferences.getString(Constants.PREF_USER_ID, "");
			NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(connIdUser);
			
			if(notificationsUpdateBean.getConnStatus() == 200){
				SQLiteHelper sqliteHelper 	= new SQLiteHelper(MicroformasApp.getAppContext(), null);
				dataBaseTransaction.setNotificacionesCerradas	 (sqliteHelper, connIdUser,	notificationsUpdateBean);
			}
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
