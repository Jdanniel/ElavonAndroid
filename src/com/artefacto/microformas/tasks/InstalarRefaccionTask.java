package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.RefaccionesUnidadActivity;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.beans.RefaccionesUnidadBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class InstalarRefaccionTask extends AsyncTask<String,Void, Integer>{
	private RefaccionesUnidadActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;
	String idTecnico;
	RefaccionesUnidadBean refaccionesUnidadBean;
	GenericResultBean	  genericResultBean;
	String noEquipo;

	public InstalarRefaccionTask(	RefaccionesUnidadActivity activity, 
									ProgressDialog progressDialog,
									String idAR,
									String idTecnico,
									RefaccionesUnidadBean refaccionesUnidadBean,
									String noEquipo) {
		super();
		this.activity 				= activity;
		this.progressDialog 		= progressDialog;
		this.idAR 					= idAR;
		this.idTecnico 				= idTecnico;
		this.refaccionesUnidadBean 	= refaccionesUnidadBean;
		this.noEquipo 				= noEquipo;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params) {
		genericResultBean = HTTPConnections.instalarRefaccion(	idAR, 
																idTecnico,
																refaccionesUnidadBean,
																noEquipo);
		setGenericResultBean(genericResultBean);
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		
		genericResultBean = getGenericResultBean();	

		if (refaccionesUnidadBean.getConnStatus()==200){
			activity.checkInstalacion(genericResultBean);
		}
		else{
			Toast.makeText(activity, "No se pudo cargar la información, intente más tarde", Toast.LENGTH_SHORT).show();
			activity.finish();
		}
	}
	
	public void setGenericResultBean(GenericResultBean genericResultBean){
		this.genericResultBean = genericResultBean;
	}
	
	public GenericResultBean getGenericResultBean(){
		return genericResultBean;
	}
}