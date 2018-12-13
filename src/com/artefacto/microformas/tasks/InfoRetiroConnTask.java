package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.RetiroActivity;
import com.artefacto.microformas.beans.InfoRetiroBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class InfoRetiroConnTask extends AsyncTask<String,Void, Integer>{
	private RetiroActivity activity;
	private ProgressDialog progressDialog;

	String idAR;
	int idTecnico;
	
	String message;
	int status = 1;
	
	InfoRetiroBean info;
	
	public InfoRetiroConnTask(RetiroActivity activity, ProgressDialog progressDialog,
			String idAR, int idTecnico) {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.idAR = idAR;
		this.idTecnico = idTecnico;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	

	@Override
	protected Integer doInBackground(String... params) {
		info = HTTPConnections.obtainInfoRetiro(idAR,idTecnico);
		status = info.getStatusRet();
		switch(status){
			case 1: message = "Puede Continuar";
					break;
			case 2: message = "No hay unidades en el negocio";
					break;
			default: message = "No se pudo cargar la información, intente más tarde";
					break;
		}
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
		if (status == 1){
			activity.setInfo(info);
		}
		else{
			activity.finish();
		}
		
	}
}
