package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.GeoLocalizacionActivity;
import com.artefacto.microformas.beans.AgregarCoordenadasBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class AgregarCoordenadasConnTask extends AsyncTask<String,Void, Integer>{
	private GeoLocalizacionActivity activity;
	private ProgressDialog progressDialog;
	
	String idNegocio;
	double latitud;
	double longitud;
	double accuracy;

	AgregarCoordenadasBean agregarCoordenadasBean;
	public AgregarCoordenadasConnTask(GeoLocalizacionActivity activity, ProgressDialog progressDialog,
			String idNegocio, double latitud, double longitud, double accuracy) {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.idNegocio = idNegocio;
		this.latitud = latitud;
		this.longitud = longitud;
		this.accuracy = accuracy;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params) {
		agregarCoordenadasBean = HTTPConnections.agregarCoordenadas(idNegocio, latitud, longitud, accuracy);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		
		if(agregarCoordenadasBean.getRes().equalsIgnoreCase("OK")){
			Toast.makeText(activity, "Coordenadas enviadas exitosamente.", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(activity, "Error: " + agregarCoordenadasBean.getDesc(), Toast.LENGTH_SHORT).show();
		}
		
		activity.finish();
	}
}