package com.artefacto.microformas.tasks;

import com.artefacto.microformas.RefaccionesCatalogsActivity;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ModeloSKUTask extends AsyncTask<String,Void, Integer>{
	private ProgressDialog progressDialog;
	private RefaccionesCatalogsActivity activity;
	GenericResultBean genericResultBean;
	
	String sku 		= "";
	String idAR		= "";
	String idMarca 	= "";
	String noSerie 	= "";
	String nuevo	= "";
	String daniado	= "";
	String costo	= "";
	String idMoneda	= "";
	String idTecnico	= "";

	public ModeloSKUTask(String idAR,
			String sku, String idMarca, String noSerie,
			String nuevo, String daniado, String costo, String idMoneda, String idTecnico) {
		super();
		
		this.sku 		= sku;
		this.idAR		= idAR;
		this.idMarca 	= idMarca;
		this.noSerie 	= noSerie;
		this.nuevo		= nuevo;
		this.daniado	= daniado;
		this.costo		= costo;
		this.idMoneda	= idMoneda;
		this.idTecnico	= idTecnico;
	}

	public ModeloSKUTask(RefaccionesCatalogsActivity activity, ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
		this.activity = activity;
	}

	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		this.idAR		= params[0];
		this.sku 		= params[1];
		this.idMarca 	= params[2];
		this.noSerie 	= params[3];
		this.nuevo		= params[4];
		this.daniado	= params[5];
		this.costo		= params[6];
		this.idMoneda	= params[7];
		this.idTecnico	= params[8];
		
		int success = 0;

		GenericResultBean genericResultBean = HTTPConnections.agregarRefaccionUnidadNegocio(idAR, noSerie, sku, idMarca, daniado, nuevo, costo, idMoneda, idTecnico);
		success = 0;
		if(genericResultBean.getRes().equals("OK")){
        	success=1;
		}
		
		setGenericResultBean(genericResultBean);
		return success;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();

//    	activity.getApproval(getGenericResultBean());	
	}
	
	public GenericResultBean getGenericResultBean(){
		return genericResultBean;
	}
	
	public void setGenericResultBean(GenericResultBean genericResultBean){
		this.genericResultBean = genericResultBean;
	}
}