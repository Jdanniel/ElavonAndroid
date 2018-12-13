package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.artefacto.microformas.InventoryMovementsActivity;
import com.artefacto.microformas.beans.InfoActualizacionBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class InfoActualizacionTask extends AsyncTask<String,Void, Integer>{
	private InventoryMovementsActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;

	InfoActualizacionBean infoActualizacionBean;
	public InfoActualizacionTask(InventoryMovementsActivity activity, ProgressDialog progressDialog,
			String idAR) {
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
		infoActualizacionBean = HTTPConnections.getInfoActualizacion(idAR);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		activity.setInfoActualizacion(infoActualizacionBean);	
	}
}