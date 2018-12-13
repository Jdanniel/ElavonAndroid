package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.artefacto.microformas.RequestDetailActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.SolicitudesBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.transactions.DataBaseTransactions;

public class ChangeStatusConn {
	private ProgressDialog progressDialog;
	private RequestDetailActivity activity;
	
	private String 	connIdRequest;
	private String	connIdUser;
	private String 	connNewStatus;
	private String  connComentario;
	
	
	static ArrayList<SolicitudesBean>	solicitudesNuevasBean;
	static ArrayList<SolicitudesBean>	solicitudesAbiertasBean;
	NotificationsUpdateBean				notificationsUpdateBean;
	
	boolean busy = false;
	
	public ChangeStatusConn(String idRequest, String idUser, int status, String comentario) {
		BasicNameValuePair basicId 		= new BasicNameValuePair("idRequest", idRequest);
		BasicNameValuePair basicIdUser 	= new BasicNameValuePair("idUser", idUser);
		BasicNameValuePair basicStatus 	= new BasicNameValuePair("status", status+"");
		
		connIdRequest 	= basicId.getValue().toString();
		connIdUser		= basicIdUser.getValue().toString();
		connNewStatus 	= basicStatus.getValue().toString();
		connComentario  = comentario;

		progressDialog = null;
		activity = null;
		
	}
	
	
	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}


	public void setProgressDialog(ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}


	public RequestDetailActivity getActivity() {
		return activity;
	}


	public void setActivity(RequestDetailActivity activity) {
		this.activity = activity;
	}


	public ConnTaskResultBean changeStatus(){
		ConnTaskResultBean ret = new ConnTaskResultBean();
		
		boolean isChanged = HTTPConnections.setStatus(connIdRequest, connIdUser, connNewStatus,connComentario);
		ret.setSuccess(0);
		if(isChanged){
			ret.setSuccess(1);
			//TODO Método para actualizar los datos de Nuevas y Abiertas
			DataBaseTransactions dataBaseTransaction = new DataBaseTransactions();
			
			notificationsUpdateBean = HTTPConnections.getUpdates(connIdUser);
			
			if(notificationsUpdateBean.getConnStatus() == 200){
				SQLiteHelper sqliteHelper;
				if (activity!=null){
					 		sqliteHelper 	= new SQLiteHelper(activity, null);
				}
				else{
					 		sqliteHelper 	= new SQLiteHelper(MicroformasApp.getAppContext(), null);
				}
				
				dataBaseTransaction.setNotificacionesNuevas	 (sqliteHelper, connIdUser,	notificationsUpdateBean);
				dataBaseTransaction.setNotificacionesAbiertas(sqliteHelper, connIdUser, notificationsUpdateBean); 
			}
			else{
				ret.setSuccess(0);
			}
		}
		
		return ret;
	}
	
	private void setPreferences(int type, ConnTaskResultBean ctrs){
		if(ctrs.getSuccess() == 1){
    		SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences("UserConfig", Context.MODE_PRIVATE);  
    		Editor editor = sharedPreferences.edit();
    		
    		if(type == 0){
    	    	editor.putString(Constants.PREF_OPENED_MD5, notificationsUpdateBean.getAbiertasMD5());
    	    	editor.putString(Constants.PREF_OPENED_NUMBER, notificationsUpdateBean.getAbiertasNumber());
    		}
    		
    		else if(type == 1){
    			
    		}
    		
    		else{
    	    	editor.putString(Constants.PREF_OPENED_MD5, notificationsUpdateBean.getAbiertasMD5());
    	    	editor.putString(Constants.PREF_OPENED_NUMBER, notificationsUpdateBean.getAbiertasNumber());
    		}
    		
    		editor.putString(Constants.PREF_NEWS_MD5, notificationsUpdateBean.getNuevasMD5());
        	editor.putString(Constants.PREF_NEWS_NUMBER, notificationsUpdateBean.getNuevasNumber());
        	editor.commit();
    	}
	}
	
	public void OnSend(ConnTaskResultBean ctrs){
		if(connNewStatus == Constants.REQUEST_APPROVED){
			if (progressDialog!=null)
				progressDialog.dismiss();
			setPreferences(0, ctrs);
			if (activity!=null)
				activity.sendStatusAnswer(ctrs.getSuccess(), 0, notificationsUpdateBean);
		}
		else if(connNewStatus == Constants.REQUEST_REFUSED){
			if (progressDialog!=null)
				progressDialog.dismiss();
			setPreferences(1, ctrs);
			if (activity!=null)
				activity.sendStatusAnswer(ctrs.getSuccess(), 1, notificationsUpdateBean);
		}
		
		else{
			if (progressDialog!=null)
				progressDialog.dismiss();
			setPreferences(2, ctrs);
			if (activity!=null)
				activity.sendStatusAnswer(ctrs.getSuccess(), 2, notificationsUpdateBean);
		}
	}
}
