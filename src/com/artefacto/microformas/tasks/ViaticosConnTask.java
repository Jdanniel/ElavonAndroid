package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import com.artefacto.microformas.RequestListActivity;
import com.artefacto.microformas.ViaticosActivity;
import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.constants.Constants;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class ViaticosConnTask extends AsyncTask<String,Void, Integer>{
	private ProgressDialog progressDialog;
	private ViaticosActivity activity;

	String idRequest ="";
	String idUser = "";
	String lugar = "";
	String observaciones = "";
	String idPrioridad = "";
	String viaticos = "";
	String costos = "";
	
	ConnTaskResultBean ctrb;
	
	public ViaticosConnTask(String idRequest, String idUser, String lugar,
			String observaciones, String idPrioridad, String viaticos,
			String costos) {
		super();
		this.idRequest = idRequest;
		this.idUser = idUser;
		this.lugar = lugar;
		this.observaciones = observaciones;
		this.idPrioridad = idPrioridad;
		this.viaticos = viaticos;
		this.costos = costos;
	}

	public ViaticosConnTask(ViaticosActivity activity,ProgressDialog progressDialog) {
		super();
		this.progressDialog = progressDialog;
		this.activity = activity;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {

		this.idRequest = params[0];
		this.idUser = params[1];
		this.lugar = params[2];
		this.observaciones = params[3];
		this.idPrioridad = params[4];
		this.viaticos = params[5];
		this.costos = params[6];
		
		ViaticosConn  vc = new ViaticosConn(idRequest, idUser, lugar, observaciones, idPrioridad, viaticos, costos);
		ctrb = vc.sendViaticos();

		if (ctrb.getSuccess() == 0) {
			AsyncQueue.AddSendViaticos(idRequest, idUser, lugar, observaciones, idPrioridad, viaticos, costos);
			ArrayList<String> data = AsyncQueue.getList();
			/*for(int index = 0; index < data.size(); index++) {
				String temp = data.get(index);
			}*/
		}
		
		return ctrb.getSuccess();
	}
	
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		Toast.makeText(activity, ctrb.getStatus(), Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(activity, RequestListActivity.class);
		intent.putExtra("type", Constants.DATABASE_ABIERTAS);
		activity.startActivity(intent);
	}
}
