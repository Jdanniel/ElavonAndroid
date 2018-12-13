package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.SolicitudesBean;


import android.app.ProgressDialog;
import android.os.AsyncTask;

public class MultipleStatusConnTask extends AsyncTask<String, Void, Integer> {
	private ProgressDialog progressDialog;
	/*private RequestDetailActivity activity;
	
	private String 	connIdStatus;
	private String	connIdUser;
	private String 	connNewStatus;*/
	
	static ArrayList<SolicitudesBean>	solicitudesNuevasBean;
	static ArrayList<SolicitudesBean>	solicitudesAbiertasBean;
	NotificationsUpdateBean				notificationsUpdateBean;
	
	int 	success;
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		
		return success;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
	}
}