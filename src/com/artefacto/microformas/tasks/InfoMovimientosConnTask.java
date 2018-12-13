package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.InventoryMovementsActivity;
import com.artefacto.microformas.beans.InfoMovimientosBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class InfoMovimientosConnTask extends AsyncTask<String,Void, Integer>{
	private InventoryMovementsActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;

	InfoMovimientosBean infoMovimientosBean;
	public InfoMovimientosConnTask(InventoryMovementsActivity activity, ProgressDialog progressDialog,
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
		infoMovimientosBean = HTTPConnections.obtainInfoMovimientos(idAR);
		
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		Toast.makeText(activity, "Información obtenida.", Toast.LENGTH_SHORT).show();
		activity.setInfo(infoMovimientosBean);
		
		
	}

}