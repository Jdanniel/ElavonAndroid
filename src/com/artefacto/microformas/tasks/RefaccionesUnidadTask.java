package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.RefaccionesUnidadActivity;
import com.artefacto.microformas.beans.RefaccionesUnidadBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class RefaccionesUnidadTask extends AsyncTask<String,Void, Integer>{
	private RefaccionesUnidadActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;
	String searchText;
	String idTipoProducto;

	ArrayList<RefaccionesUnidadBean> refaccionesUnidadBeanArray;
	public RefaccionesUnidadTask(RefaccionesUnidadActivity activity, ProgressDialog progressDialog,
			String idAR, String searchText, String idTipoProducto) {
		super();
		this.activity 		= activity;
		this.progressDialog = progressDialog;
		this.idAR 			= idAR;
		this.searchText 	= searchText;
		this.idTipoProducto = idTipoProducto;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params) {
		refaccionesUnidadBeanArray = HTTPConnections.verInstalacionRefaccion(idAR, 
																			 "1", 
																			 searchText, 
																			 idTipoProducto);
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		if (refaccionesUnidadBeanArray.get(0).getConnStatus()==200){
			activity.setRefaccionesList(refaccionesUnidadBeanArray);
		}
		else{
			Toast.makeText(activity, "No se pudo instalar, intente más tarde", Toast.LENGTH_SHORT).show();
			activity.finish();
		}
	}
}