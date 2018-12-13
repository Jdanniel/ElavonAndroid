package com.artefacto.microformas.tasks;

import com.artefacto.microformas.connection.HTTPConnections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class SustitucionConnTask  extends AsyncTask<String,Void, Integer>{
	private Activity activity;
	private ProgressDialog progressDialog;
	
	private String  idAR, idTecnico, idUnidadEntrada, idUnidadSalida, idNegocio, edit, isDaniada, accion, noEquipo;


	private int success;
	private String status;
	
	public SustitucionConnTask(String idAR, String idTecnico,
			String idUnidadEntrada, String idUnidadSalida, String idNegocio,
			String edit, String isDaniada, String accion, String noEquipo) {
		super();
		this.idAR = idAR;
		this.idTecnico = idTecnico;
		this.idUnidadEntrada = idUnidadEntrada;
		this.idUnidadSalida = idUnidadSalida;
		this.idNegocio = idNegocio;
		this.edit = edit;
		this.isDaniada = isDaniada;
		this.accion = accion;
		this.noEquipo = noEquipo;
	}

	public SustitucionConnTask(Activity activity, ProgressDialog progressDialog) {
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
		this.idUnidadEntrada = params[2];
		this.idUnidadSalida = params[3];
		this.idNegocio = params[4];
		this.edit = params[5];
		this.isDaniada = params[6];
		this.accion = params[7];
		this.noEquipo = params[8];
		
		insertarSustitucion();
		
		return success;
	}
	
	public int insertarSustitucion(){
		String res = HTTPConnections.InsertarSustitucion(idAR, idTecnico, idUnidadEntrada, idUnidadSalida, idNegocio, edit, accion, noEquipo, isDaniada);
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
		
//		Intent intent = new Intent(activity, RequestDetailActivity.class);
//		intent.putExtra("idRequest", idAR);
//		intent.putExtra("type", Constants.DATABASE_ABIERTAS);
//		activity.startActivity(intent);
		activity.finish();
	}	
}