package com.artefacto.microformas.tasks;

import com.artefacto.microformas.CierreActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.SendExitoResponseBean;
import com.artefacto.microformas.beans.ValidateClosureBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.transactions.DataBaseTransactions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class CierreExitoTask extends AsyncTask<ValidateClosureBean,Void,SendExitoResponseBean> {
	private CierreActivity activity;
	private ProgressDialog progressDialog;
	private ValidateClosureBean validateClosureBean;
	private String idAR; 
	
	public CierreExitoTask(ValidateClosureBean validateClosureBean) {
		this.validateClosureBean = validateClosureBean;
		
		progressDialog = null;
		activity = null;
	}

	public CierreExitoTask(CierreActivity activity, ProgressDialog progressDialog) {
		this.activity = activity;
		this.progressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected SendExitoResponseBean doInBackground(ValidateClosureBean... arg0) {
		validateClosureBean = activity.getValidateClosureBean();
		setIdAR(validateClosureBean.getIdAR());
		
		SendExitoResponseBean sendExitoResponseBean = HTTPConnections.sendCierreExito(validateClosureBean);
		return sendExitoResponseBean;
	}
	
	protected void onPostExecute(SendExitoResponseBean sendExitoResponseBean){
		if(sendExitoResponseBean.getRes().equals("OK"))
		{
			SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			String abiertasMD5 		= sharedPreferences.getString(Constants.PREF_OPENED_MD5, "");
			String cerradasMD5 		= sharedPreferences.getString(Constants.PREF_CLOSED_MD5, "");
			boolean saveAbiertas;
			boolean saveCerradas;
			
			NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(getIdAR());
			if(notificationsUpdateBean.getConnStatus() == 200){
				DataBaseTransactions dataBaseTransactions = new DataBaseTransactions();
				SQLiteHelper 		sqliteHelper 	= new SQLiteHelper(MicroformasApp.getAppContext(), null);
				
				if(!(notificationsUpdateBean.getAbiertasMD5().equals(abiertasMD5))){
					saveAbiertas = dataBaseTransactions.setNotificacionesAbiertas(sqliteHelper, sharedPreferences.getString(Constants.PREF_USER_ID, ""), notificationsUpdateBean);
					dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveAbiertas, Constants.DATABASE_ABIERTAS, false);
				}
				
				if(!(notificationsUpdateBean.getCerradasMD5().equals(cerradasMD5))){
					saveCerradas = dataBaseTransactions.setNotificacionesCerradas(sqliteHelper, sharedPreferences.getString(Constants.PREF_USER_ID, ""), notificationsUpdateBean);
					dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveCerradas, Constants.DATABASE_CERRADAS, false);
				}
			
				sqliteHelper.close();
				
				GetUpdatesService.updateIndex++;
				GetUpdatesService.UpdateMainActivity();
			}
		}
		progressDialog.dismiss();
		activity.sendExitoAnswer(sendExitoResponseBean);
	}
	
	public void setIdAR(String idAR){
		this.idAR = idAR;
	}
	
	public String getIdAR(){
		return idAR;
	}
}