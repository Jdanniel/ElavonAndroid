package com.artefacto.microformas.tasks;

import com.artefacto.microformas.AgregarRefaccionUnidadActivity;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AgregarSKUTask extends AsyncTask<String,Void, Integer>{
	private ProgressDialog progressDialog;
	private AgregarRefaccionUnidadActivity activity;
	GenericResultBean genericResultBean;
	
	String sku 			= "";
	String idAR			= "";
	String idMarca 		= "";
	String noSerie 		= "";
	String nuevo		= "";
	String daniado		= "";
	String idTecnico	= "";

	public AgregarSKUTask(
			AgregarRefaccionUnidadActivity agregarRefaccionUnidadActivity,
			ProgressDialog progressDialog, String idAR, String sku, 
			String idMarca, String noSerie, String nuevo,
			String daniado, String idTecnico) {
		
		this.progressDialog = progressDialog;
		this.activity = agregarRefaccionUnidadActivity;
		
		this.sku 		= sku;
		this.idAR		= idAR;
		this.idMarca 	= idMarca;
		this.noSerie 	= noSerie;
		this.nuevo		= nuevo;
		this.daniado	= daniado;
		this.idTecnico	= idTecnico;
	}

	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params)
	{
		GenericResultBean genericResultBean = HTTPConnections.agregarRefaccionUnidadNegocio(idAR,
				noSerie, sku, idMarca, daniado, nuevo, idTecnico);
		setGenericResultBean(genericResultBean);
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();

		activity.nextScreen(genericResultBean);
	}
	
	public GenericResultBean getGenericResultBean(){
		return genericResultBean;
	}
	
	public void setGenericResultBean(GenericResultBean genericResultBean){
		this.genericResultBean = genericResultBean;
	}
}