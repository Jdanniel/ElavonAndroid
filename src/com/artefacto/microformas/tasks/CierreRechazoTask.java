package com.artefacto.microformas.tasks;

import com.artefacto.microformas.CierreRechazoActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.SendRechazoBean;
import com.artefacto.microformas.beans.SendRechazoResponseBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.transactions.DataBaseTransactions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.Runnable;

public class CierreRechazoTask extends AsyncTask<SendRechazoBean, Void, Void> /*AsyncTask<SendRechazoBean, Void, SendRechazoResponseBean>*/ {
	private ProgressDialog 			progressDialog;
	private CierreRechazoActivity 	activity;
	private SendRechazoBean			sendRechazoBean;
	private SendRechazoResponseBean responseBean;
	
	public CierreRechazoTask(SendRechazoBean sendRechazoBean) {
		this.sendRechazoBean = sendRechazoBean;
		
		progressDialog = null;
		activity = null;
		
	}
	
	public CierreRechazoTask(CierreRechazoActivity activity, ProgressDialog progressDialog) {
		this.activity 		= activity;
		this.progressDialog = progressDialog;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Void /*SendRechazoResponseBean*/ doInBackground(SendRechazoBean... arg0) {
		sendRechazoBean = activity.getSendRechazoBean();
		SendRechazoResponseBean sendRechazoResponseBean = HTTPConnections.sendCierreRechazo(sendRechazoBean);
		this.updateRechazoResponseBean(sendRechazoResponseBean);
		
		//onPostExecute
		/*if(sendRechazoResponseBean.getRes().equals("OK")){
			SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			String abiertasMD5 		= sharedPreferences.getString(Constants.PREF_OPENED_MD5, "");
			String cerradasMD5 		= sharedPreferences.getString(Constants.PREF_CLOSED_MD5, "");
			boolean saveAbiertas;
			boolean saveCerradas;
			Log.d("ID_TECNICO", sendRechazoBean.getIdTecnico());
			NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(sendRechazoBean.getIdTecnico());
			
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
		progressDialog.dismiss();
		activity.sendRechazoAnswer(sendRechazoResponseBean);*/
		
		
		return null;// sendRechazoResponseBean;
	}
	
	//<----- ERROR DE ACTUALIZACION ----->
	protected void onPostExecute(SendRechazoResponseBean sendRechazoResponseBean){
		/*if(sendRechazoResponseBean.getRes().equals("OK")){
			SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			String abiertasMD5 		= sharedPreferences.getString(Constants.PREF_OPENED_MD5, "");
			String cerradasMD5 		= sharedPreferences.getString(Constants.PREF_CLOSED_MD5, "");
			boolean saveAbiertas;
			boolean saveCerradas;
			Log.d("ID_TECNICO", sendRechazoBean.getIdTecnico());
			NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(sendRechazoBean.getIdTecnico());
			
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
		progressDialog.dismiss();
		activity.sendRechazoAnswer(sendRechazoResponseBean);*/
	}
	
	protected void updateRechazoResponseBean(SendRechazoResponseBean sendRechazoResponseBean) {
		final SendRechazoResponseBean response = sendRechazoResponseBean;
		Runnable runnable = new Runnable() {
            public void run() {
            	if(response.getRes().equals("OK")){
        			SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
        			String abiertasMD5 		= sharedPreferences.getString(Constants.PREF_OPENED_MD5, "");
        			String cerradasMD5 		= sharedPreferences.getString(Constants.PREF_CLOSED_MD5, "");
        			boolean saveAbiertas;
        			boolean saveCerradas;
        			Log.d("ID_TECNICO", sendRechazoBean.getIdTecnico());
        			NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(sendRechazoBean.getIdTecnico());
        			
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
            	
        		progressDialog.dismiss();
        		activity.sendRechazoAnswer(response);
            }
        };
        
        Thread tr = new Thread(runnable);
        tr.start();
	}
}