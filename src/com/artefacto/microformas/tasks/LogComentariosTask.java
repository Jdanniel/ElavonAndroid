package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.artefacto.microformas.RequestDetailActivity;
import com.artefacto.microformas.beans.LogComentariosBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class LogComentariosTask extends AsyncTask<String,Void, Integer>{
	private RequestDetailActivity activity;
	private ProgressDialog progressDialog;
	private String idAR;
	
	ArrayList<LogComentariosBean> logComentarioBeanArray;
	
	public LogComentariosTask(RequestDetailActivity activity, ProgressDialog progressDialog, String idAR) {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.idAR = idAR;
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		ArrayList<LogComentariosBean>	logComentarioBeanArray	= new ArrayList<LogComentariosBean>();

		logComentarioBeanArray = HTTPConnections.logComentarios(idAR);
	    
		setLogComentariosBeanArray(logComentarioBeanArray);
		return 0;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		logComentarioBeanArray = getLogComentariosBean();
		activity.verComentarios(logComentarioBeanArray);
	}
	
	public ArrayList<LogComentariosBean> getLogComentariosBean(){
		return logComentarioBeanArray;
	}
	
	public void setLogComentariosBeanArray(ArrayList<LogComentariosBean> logComentarioBeanArray){
		this.logComentarioBeanArray = logComentarioBeanArray;
	}
}