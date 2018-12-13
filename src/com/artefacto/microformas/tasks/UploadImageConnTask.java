package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.PhotoTakingActivity;
import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.ConnTaskResultBean;

public class UploadImageConnTask extends AsyncTask<String,Void, Integer>{
	private ProgressDialog progressDialog;
	private PhotoTakingActivity activity;

	String path;
	String photoName;
	String numAr;
	String idTecnico;
	
	private ConnTaskResultBean ctrb;
	
	public UploadImageConnTask(	String path,
								String photoName,
								String numAr,
								String idTecnico)
	{
		super();
		this.path = path;
		this.photoName = photoName;
		this.numAr = numAr;
		this.idTecnico = idTecnico;
	}

	public UploadImageConnTask(PhotoTakingActivity activity,ProgressDialog progressDialog) {
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

		this.path = params[0];
		this.photoName = params[1];
		this.numAr = params[2];
		this.idTecnico = params[3];
		
		UploadImageConn uic = new UploadImageConn(path, photoName, numAr, idTecnico);
		ctrb = uic.uploadImage();
		
		if (ctrb.getSuccess() == 0){
			AsyncQueue.AddUploadImage(path, photoName, numAr, idTecnico);
		}
		
		return ctrb.getSuccess();
	}
	
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		Toast.makeText(activity, ctrb.getStatus(), Toast.LENGTH_SHORT).show();
		
		activity.finish();
	}
}
