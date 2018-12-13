package com.artefacto.microformas.tasks;

import com.artefacto.microformas.CommentActivity;
import com.artefacto.microformas.RequestDetailActivity;
import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.constants.Constants;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class SendCommentConnTask extends AsyncTask<String, Void, Integer> {

	ConnTaskResultBean ctrb;
	
	public SendCommentConnTask(String idRequest, String idUser, String comment, int type) {
		this.idRequest = idRequest;
		this.idUser = idUser;
		this.comment = comment;
		this.type = type;
	}
	public SendCommentConnTask( CommentActivity activity, ProgressDialog progressDialog, int type) {
		this.progressDialog = progressDialog;
		this.activity = activity;
		this.type = type;
	}

	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		idRequest = params[0];
		idUser = params[1];
		comment = params[2];
		
		SendCommentConn scc = new SendCommentConn(idRequest, idUser, comment);
		
		ctrb = scc.sendComment();
		
		if (ctrb.getSuccess() == 0){
			AsyncQueue.AddSendComment(params[0],params[1], params[2]);
		}
		
		return ctrb.getSuccess();
	}
	
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();


    	Intent intent = new Intent(activity, RequestDetailActivity.class);
    	
		SharedPreferences sharedPreferences	= activity.getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		intent.putExtra("idRequest", sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
		intent.putExtra("type", type);
		    	
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Toast.makeText(activity, ctrb.getStatus(), Toast.LENGTH_SHORT).show();
		activity.startActivity(intent);
		
	}
	
	private ProgressDialog progressDialog;
	private CommentActivity activity;
	
	private String idRequest;
	private String idUser;
	private String comment;
	
	boolean busy;
	
	private int type;
}
