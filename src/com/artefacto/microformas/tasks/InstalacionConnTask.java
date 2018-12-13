package com.artefacto.microformas.tasks;


import com.artefacto.microformas.InstalacionActivity;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class InstalacionConnTask extends AsyncTask<String,Void, Integer>{

	private Activity activity;
	private ProgressDialog progressDialog;

	String idAR = "";
	String idTecnico = "";
	String idUnidad = "";
	String idNegocio = "";
	String edit = "";
	String accion = "";
	String noEquipo = "";

	private int success;
	private String status;
	
	public InstalacionConnTask(Activity activity, ProgressDialog progressDialog) {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
	}

	public InstalacionConnTask(String idAR, String idTecnico, String idUnidad,
			String idNegocio, String edit, String accion, String noEquipo) {
		super();
		this.idAR = idAR;
		this.idTecnico = idTecnico;
		this.idUnidad = idUnidad;
		this.idNegocio = idNegocio;
		this.edit = edit;
		this.accion = accion;
		this.noEquipo = noEquipo;
	}

	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		this.idAR = params[0];
		this.idTecnico = params[1];
		this.idUnidad = params[2];
		this.idNegocio = params[3];
		this.edit = params[4];
		this.accion = params[5];
		this.noEquipo = params[6];
		
		
		insertarInstalacion();
		
		
		return success;
	}

	public Integer insertarInstalacion(){
		String res = HTTPConnections.InsertarInstalacion(idAR, idTecnico, idUnidad, idNegocio, edit, accion, noEquipo);
		success = 0;
		if(res.equals("OK")){
        	success=1;
			status = "Enviado con éxito";
		}else{
			status = res;
		}
		return success;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(activity, InstalacionActivity.class);
		intent.putExtra("Get_Info", true);
		activity.startActivity(intent);
		activity.finish();
		
	}
	
}
