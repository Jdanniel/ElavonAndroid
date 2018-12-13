package com.artefacto.microformas.tasks;

import com.artefacto.microformas.SparePartActivity;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class EnviarSparePartTask extends AsyncTask<String,Void, Integer> {
	private SparePartActivity activity;
	private ProgressDialog progressDialog;
	
	String 	idAR;
	String 	idTecnico;
	String 	idSparePart;
	String 	idAlmacen;
	String 	idUrgencia;
	String 	notas;
	String 	tipoServicio;
	String 	idDireccion;
	String 	fechaCompromiso;
	String 	cantidad;
	GenericResultBean genericResultBean;
	
	public EnviarSparePartTask(	SparePartActivity activity, 
								ProgressDialog progressDialog, 
								String idAR,
								String idTecnico,
								String idSparePart,
								String idAlmacen,
								String idUrgencia,
								String notas,
								String tipoServicio,
								String idDireccion,
								String fechaCompromiso,
								String cantidad) {
		super();
		this.activity 			= activity;
		this.progressDialog 	= progressDialog;
		
		this.idAR 				= idAR;
		this.idTecnico 			= idTecnico;
		this.idSparePart 		= idSparePart;
		this.idAlmacen 			= idAlmacen;
		this.idUrgencia 		= idUrgencia;
		this.notas 				= notas;
		this.tipoServicio 		= tipoServicio;
		this.idDireccion 		= idDireccion;
		this.fechaCompromiso 	= fechaCompromiso;
		this.cantidad 			= cantidad;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params) {
		genericResultBean = HTTPConnections.sendPendienteSparePart(	idAR,
																	idTecnico,
																	idSparePart,
																	idAlmacen,
																	idUrgencia,
																	notas,
																	tipoServicio,
																	idDireccion,
																	fechaCompromiso,
																	cantidad);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		activity.nextScreen(genericResultBean);	
	}
}