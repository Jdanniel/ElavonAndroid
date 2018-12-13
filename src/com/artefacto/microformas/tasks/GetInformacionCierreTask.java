package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.artefacto.microformas.RequestDetailActivity;
import com.artefacto.microformas.connection.HTTPConnections;

public class GetInformacionCierreTask extends AsyncTask<String,Void, Integer>{
	private RequestDetailActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;

	InformacionCierreBean informacionCierreBean;
	public GetInformacionCierreTask(RequestDetailActivity activity, ProgressDialog progressDialog, String idAR) {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.idAR = idAR;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	

	@Override
	protected Integer doInBackground(String... params) {
		informacionCierreBean = HTTPConnections.getInformacionCierre(idAR);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		activity.setInformacionCierre(informacionCierreBean);
	}
}