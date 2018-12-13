package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.artefacto.microformas.RequestDetailActivity;
import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.SolicitudesBean;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class ChangeStatusConnTask extends AsyncTask<String, Void, Integer> {
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
	
	ChangeStatusConn csc;
	
	ConnTaskResultBean ctrb;
	
	public ChangeStatusConnTask(String idRequest, String idUser, int status, String causaRechazo) {
		BasicNameValuePair basicId 			= new BasicNameValuePair("idRequest", idRequest);
		BasicNameValuePair basicIdUser 		= new BasicNameValuePair("idUser", idUser);
		BasicNameValuePair basicStatus 		= new BasicNameValuePair("status", status+"");
		BasicNameValuePair basiccomentario  = new BasicNameValuePair("comentario", causaRechazo);
		
		connIdRequest 	= basicId.getValue().toString();
		connIdUser		= basicIdUser.getValue().toString();
		connNewStatus 	= basicStatus.getValue().toString();
		connComentario  = basiccomentario.getValue().toString();
		progressDialog = null;
		activity = null;
		
		
	}
	
	public ChangeStatusConnTask(RequestDetailActivity activity, ProgressDialog progressDialog) {
		this.activity 		= activity;
		this.progressDialog = progressDialog;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		if (AsyncQueue.isChangeStatusPending(params[0])){
			busy = true;
			return 0;
		}
		
		BasicNameValuePair basicId 		= new BasicNameValuePair("idRequest", params[0]);
		BasicNameValuePair basicIdUser 	= new BasicNameValuePair("idUser", params[1]);
		BasicNameValuePair basicStatus 	= new BasicNameValuePair("status", params[2]);
		BasicNameValuePair basiccomentario  = new BasicNameValuePair("comentario", params[3]);

		connIdRequest 	= basicId.getValue().toString();
		connIdUser		= basicIdUser.getValue().toString();
		connNewStatus 	= basicStatus.getValue().toString();
		connComentario  = basiccomentario.getValue().toString();

		csc = new ChangeStatusConn(connIdRequest, connIdUser, Integer.parseInt(connNewStatus), connComentario);
		csc.setActivity(activity);
		csc.setProgressDialog(progressDialog);
		
		ctrb = csc.changeStatus();
		
		if (ctrb.getSuccess() == 0){
			AsyncQueue.AddChangeStatus(params[0],params[1], params[2]);
		}
		
		return ctrb.getSuccess();
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		if (busy){
			progressDialog.dismiss();
			
			activity.sendStatusAnswer(ctrb.getSuccess(),3,notificationsUpdateBean);
		}
		else{
			csc.OnSend(ctrb);
		}
	}
}