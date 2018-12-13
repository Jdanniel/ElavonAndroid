package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import com.artefacto.microformas.ActualizacionActivity;
import com.artefacto.microformas.beans.CarriersBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GetCarriersTask extends AsyncTask<String,Void, Integer>
{
	public GetCarriersTask(ActualizacionActivity activity, ProgressDialog progressDialog)
    {
		this.progressDialog = progressDialog;
		this.activity = activity;
	}

	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		
		int success = 0;

		carriersBeanArray = HTTPConnections.getCarriers();
		success = 0;
		
		setCarriersBeanArray(carriersBeanArray);
		return success;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();

    	activity.goToAgregarUnidad(carriersBeanArray);	
	}
	
	public ArrayList<CarriersBean> getCarriersBeanArray()
    {
		return carriersBeanArray;
	}
	
	public void setCarriersBeanArray(ArrayList<CarriersBean> carriersBeanArray)
    {
		this.carriersBeanArray = carriersBeanArray;
	}

    private ProgressDialog progressDialog;

    private ActualizacionActivity activity;

    private ArrayList<CarriersBean> carriersBeanArray;
}