package com.artefacto.microformas.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.artefacto.microformas.ViaticosActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.transactions.DataBaseTransactions;

public class ViaticosConn {
	private ViaticosActivity activity;

	String idRequest ="";
	String idUser = "";
	String lugar = "";
	String observaciones = "";
	String idPrioridad = "";
	String viaticos = "";
	String costos = "";
	
	
	public ViaticosConn(String idRequest, String idUser, String lugar,
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

	public ConnTaskResultBean sendViaticos(){
		ConnTaskResultBean ret = new ConnTaskResultBean();
		//Validar conexion a internet
		ValidateConnTask validateConnTask = new ValidateConnTask();
		if(validateConnTask.Conexion(MicroformasApp.getAppContext())){
			lugar = lugar.replace("&","").replace("'","");
			observaciones = observaciones.replace("&","").replace("'","");
			String res = HTTPConnections.sendViaticos(idRequest, idUser, lugar, observaciones, idPrioridad, viaticos, costos);
			ret.setSuccess(0);
			if(res.equals("OK")){//Si envia los viaticos; error en actualizacion
				//Toast.makeText(MicroformasApp.getAppContext(), "Cambio de status ok", Toast.LENGTH_SHORT).show();
				DataBaseTransactions dataBaseTransaction = new DataBaseTransactions();

				NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(idUser);

				if(notificationsUpdateBean.getConnStatus() == 200){
					SQLiteHelper sqliteHelper;
					if (activity!=null){
						sqliteHelper 	= new SQLiteHelper(activity, null);
					}
					else{
						sqliteHelper 	= new SQLiteHelper(MicroformasApp.getAppContext(), null);
					}

					dataBaseTransaction.setNotificacionesNuevas	 (sqliteHelper, idUser,	notificationsUpdateBean);
					dataBaseTransaction.setNotificacionesAbiertas(sqliteHelper, idUser, notificationsUpdateBean);
					SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences("UserConfig", Context.MODE_PRIVATE);
					Editor editor = sharedPreferences.edit();
					editor.putString(Constants.PREF_OPENED_MD5, notificationsUpdateBean.getAbiertasMD5());
					editor.putString(Constants.PREF_OPENED_NUMBER, notificationsUpdateBean.getAbiertasNumber());
					editor.putString(Constants.PREF_NEWS_MD5, notificationsUpdateBean.getNuevasMD5());
					editor.putString(Constants.PREF_NEWS_NUMBER, notificationsUpdateBean.getNuevasNumber());
					editor.commit();
					ret.setSuccess(1);
					ret.setStatus("Enviado con éxito");
				}else{
					ret.setSuccess(2);
					ret.setStatus("Envío exitoso, actualización fallida");
				}
			}else{
				ret.setStatus(res);
			}
		}else{
			ret.setSuccess(1);
			ret.setStatus("Por el momento no cuentas con una conexion establa intentalo mas tarde.");
		}

		return ret;
	}

}
