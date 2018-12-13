package com.artefacto.microformas.tasks;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.ConnTaskResultBean;

public class UploadPDFConnTask extends AsyncTask<String,Void, Integer>{
	private ProgressDialog progressDialog;
	private Activity activity;

	String path;
	String fileName;
	String idAr;
	
	private ConnTaskResultBean ctrb;
	
	public UploadPDFConnTask(	String path,
								String fileName, String idAr)
	{
		super();
		this.path = path;
		this.fileName = fileName;
		this.idAr = idAr;
		this.progressDialog = null;
		this.activity = null;
	}

	public UploadPDFConnTask(Activity activity,ProgressDialog progressDialog) {
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
		this.fileName = params[1];
		this.idAr = params[2];
		
		UploadPDFConn upc = new UploadPDFConn(path, fileName,idAr);

		ctrb = upc.uploadPDF();
		int exito = ctrb.getSuccess();

		//Log.e("Success",""+exito);

		if (ctrb.getSuccess() == 0){
			//Log.e("Success",""+exito);
			AsyncQueue.AddUploadPDF(path, fileName,idAr);
		}else{
			File file = new File(path);
			file.delete();
		}
		//Log.e("Success",""+exito);
		return ctrb.getSuccess();
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){

		try{
			progressDialog.dismiss();
			Toast.makeText(activity, ctrb.getStatus(), Toast.LENGTH_SHORT).show();
			activity.finish();
		}catch(Exception e){
			Log.e("PD",e.toString());
		}

	}
}