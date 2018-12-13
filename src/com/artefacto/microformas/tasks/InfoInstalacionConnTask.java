package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.InstalacionActivity;
import com.artefacto.microformas.beans.InfoInstalacionBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class InfoInstalacionConnTask extends AsyncTask<String,Void, Integer>{
	private InstalacionActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;

	InfoInstalacionBean infoInstalacionBean;
	public InfoInstalacionConnTask(InstalacionActivity activity, ProgressDialog progressDialog,
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
		infoInstalacionBean = HTTPConnections.obtainInfoInstalacion(idAR);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		if (infoInstalacionBean.getConnStatus()==200){
			Toast.makeText(activity, "Información obtenida.", Toast.LENGTH_SHORT).show();
			activity.setInfo(infoInstalacionBean);
		}
		else{
			Toast.makeText(activity, "No se pudo cargar la información, intente más tarde", Toast.LENGTH_SHORT).show();
			activity.finish();
		}
	}
}