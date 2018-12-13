package com.artefacto.microformas.tasks;

import com.artefacto.microformas.RequestDetailActivity;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class RetiroConnTask extends AsyncTask<String,Void, Integer>{
	private Activity activity;
	private ProgressDialog progressDialog;
	
	private String idAR, idTecnico, idUnidad, idNegocio, edit, isDaniada, accion, noEquipo, idCausaRetiro;
	
	private int success;
	private String status;
	
	public RetiroConnTask(String idAR, String idTecnico, String idUnidad,
			String idNegocio, String edit, String isDaniada, String accion,
			String noEquipo, String CausaR) {
		super();
		this.idAR = idAR;
		this.idTecnico = idTecnico;
		this.idUnidad = idUnidad;
		this.idNegocio = idNegocio;
		this.edit = edit;
		this.isDaniada = isDaniada;
		this.accion = accion;
		this.noEquipo = noEquipo;
		this.idCausaRetiro = CausaR;
	}

	public RetiroConnTask(Activity activity, ProgressDialog progressDialog) {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
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
		this.isDaniada = params[5];
		this.accion = params[6];
		this.noEquipo = params[7];
		//this.idCausaRetiro = params[8];
		
		insertarRetiro();
		
		return success;
	}
	
	public int insertarRetiro(){
		String res = HTTPConnections.InsertarRetiro(idAR, idTecnico, idUnidad, idNegocio, edit, accion, noEquipo, isDaniada);
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
		
		Intent intent = new Intent(activity, RequestDetailActivity.class);
		intent.putExtra("idRequest", idAR);
		intent.putExtra("type", Constants.DATABASE_ABIERTAS);
		activity.startActivity(intent);
		activity.finish();
	}
}
