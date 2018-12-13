package com.artefacto.microformas.tasks;

import com.artefacto.microformas.SustitucionesActivity;
import com.artefacto.microformas.beans.InfoSustitucionBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class InfoSustConnTask extends AsyncTask<String,Void, Integer>{
	private SustitucionesActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;
	int idTecnico;

	String message;
	int status;
	InfoSustitucionBean infoSustitucionBean;
	public InfoSustConnTask(SustitucionesActivity activity, ProgressDialog progressDialog,
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
		infoSustitucionBean = HTTPConnections.obtainInfoSustitucion(idAR,idTecnico);
		status = infoSustitucionBean.getStatusSust();
		switch(status){
			case 1: message = "Puede Continuar";
					break;
			case 2: message = "No hay unidades en el negocio";
					break;
			case 3: message = "El tecnico no tiene unidades";
					break;
			case 4: message = "Ni el tecnico ni el negocio tienen unidades";
					break;
			default : message = "No se pudo cargar la información, intente más tarde";
					break;
		}
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
		if (status == 1){
			activity.setInfo(infoSustitucionBean);
		}
		else{
			activity.finish();
		}	
	}
}