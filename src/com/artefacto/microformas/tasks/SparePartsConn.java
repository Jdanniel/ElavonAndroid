package com.artefacto.microformas.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.artefacto.microformas.RefaccionesActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.transactions.DataBaseTransactions;

public class SparePartsConn
{
	public SparePartsConn(String idRequest, String idUsuario, String idAlmacen,
			String refacciones, String idPrioridad, String notas,
			String tipoServicio, String idDireccion, String fecha)
	{
		this.idRequest = idRequest;
		this.idUser = idUsuario;
		this.idAlmacen = idAlmacen;
		this.refacciones = refacciones;
		this.idPrioridad = idPrioridad;
		this.notas = notas;
		this.tipoServicio = tipoServicio;
		this.idDireccion = idDireccion;
		this.fecha = fecha;
	}

	
	public ConnTaskResultBean sendSpareRequest()
	{
		ConnTaskResultBean taskResult = new ConnTaskResultBean();
		
		GenericResultBean result = HTTPConnections.sendSpareRequest(idRequest, idUser, idAlmacen, refacciones, idPrioridad, notas, tipoServicio, idDireccion, fecha);
		taskResult.setSuccess(0);
		
		if(result.getRes().equals("OK"))
		{
			DataBaseTransactions dataBaseTransaction = new DataBaseTransactions();
			NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(idUser);
			
			if(notificationsUpdateBean.getConnStatus() == 200)
			{
				SQLiteHelper sqliteHelper = (activity != null) ? new SQLiteHelper(activity, null) : new SQLiteHelper(MicroformasApp.getAppContext(), null);
				
				dataBaseTransaction.setNotificacionesNuevas	 (sqliteHelper, idUser,	notificationsUpdateBean);
				dataBaseTransaction.setNotificacionesAbiertas(sqliteHelper, idUser, notificationsUpdateBean);
				
				SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences("UserConfig", Context.MODE_PRIVATE);  
	    		Editor editor = sharedPreferences.edit();
	    		editor.putString(Constants.PREF_OPENED_MD5, notificationsUpdateBean.getAbiertasMD5());
    	    	editor.putString(Constants.PREF_OPENED_NUMBER, notificationsUpdateBean.getAbiertasNumber());
    	    	editor.putString(Constants.PREF_NEWS_MD5, notificationsUpdateBean.getNuevasMD5());
            	editor.putString(Constants.PREF_NEWS_NUMBER, notificationsUpdateBean.getNuevasNumber());
            	editor.commit();
            	
            	taskResult.setSuccess(1);
            	taskResult.setStatus(result.getDesc());
			}
			else
			{
				taskResult.setStatus(result.getDesc());
			}
		}
		else
		{
			taskResult.setStatus(result.getRes());
		}
		
		return taskResult;
	}
	
	private RefaccionesActivity activity;

	private String refacciones;
	private String idAlmacen;
	private String idRequest ="";
	private String idUser = "";
	private String idPrioridad = "";
	private String notas = "";
	private String tipoServicio = "";
	private String idDireccion = "";
	private String fecha = "";
	
	boolean busy;
}
