package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.artefacto.microformas.ActualizacionActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.SKUBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

public class InfoUnidadesTask extends AsyncTask<String,Void, Integer>{
	private ActualizacionActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;
	String idNegocio;
	ArrayList<UnitBean> unitBeanArray;
	
	public InfoUnidadesTask(ActualizacionActivity activity, ProgressDialog progressDialog,
			String idAR, String idNegocio) {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.idAR = idAR;
		this.idNegocio = idNegocio;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params) {
		unitBeanArray = HTTPConnections.getInfoUnidadesNegocioActualizacion(idAR, idNegocio);
		setUnitBeanArray(unitBeanArray);
		
		ArrayList<SKUBean> skuBeanArray = HTTPConnections.getSKU(idAR);
		SQLiteHelper 	sqliteHelper = new SQLiteHelper(MicroformasApp.getAppContext(), null);
		SQLiteDatabase	db 			= sqliteHelper.getWritableDB();
		
		sqliteHelper.deleteAllSKU(db);
		
		for(int i = 0; i < skuBeanArray.size(); i++){
			sqliteHelper.setSKU(skuBeanArray.get(i), db);
		}
		
		db.close();
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		unitBeanArray = getUnitBeanArray();
		
		if(unitBeanArray.get(0).getId() == null)
			unitBeanArray = new ArrayList<UnitBean>();
		
		activity.fillList(unitBeanArray);
	}
	
	public ArrayList<UnitBean> getUnitBeanArray(){
		return unitBeanArray;
	}
	
	public void setUnitBeanArray(ArrayList<UnitBean> unitBeanArray){
		this.unitBeanArray = unitBeanArray;
	}
}