package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.artefacto.microformas.ActualizacionActivity;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class BorrarUnidadTask extends AsyncTask<String,Void, Integer>{
	private ActualizacionActivity activity;
	private ProgressDialog progressDialog;
	
	String 		idUnidad;
	String 		idNegocio;
	UnitBean unitBean;
	GenericResultBean 	genericResultBean;
	
	public BorrarUnidadTask(ActualizacionActivity activity, ProgressDialog progressDialog, String idUnidad, String idNegocio) {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.idUnidad = idUnidad;
		this.idNegocio = idNegocio;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params) {
		genericResultBean = HTTPConnections.borrarUnidad(idUnidad, idNegocio);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		activity.updateScreenFromDeleteData(genericResultBean);	
	}
}