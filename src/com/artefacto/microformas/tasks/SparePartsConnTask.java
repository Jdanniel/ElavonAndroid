package com.artefacto.microformas.tasks;

import com.artefacto.microformas.EntityCatalog;
import com.artefacto.microformas.RefaccionesActivity;
import com.artefacto.microformas.RequestListActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.widget.Toast;

public class SparePartsConnTask extends AsyncTask<String, Void, Integer>
{
	public SparePartsConnTask(RefaccionesActivity activity, ProgressDialog progressDialog,
			String idRequest, String idUsuario, String idAlmacen,
			EntityCatalog[] refacciones, String idPrioridad, String notas,
			String tipoServicio, String idDireccion, String fecha)
	{
		this.progressDialog = progressDialog;
		this.activity = activity;
		
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

	@Override
	protected void onPreExecute()
	{
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params)
	{
		if(refacciones == null)
		{
			strRefacciones = "";
		}
		else
		{
			for(int i = 0; i < refacciones.length; i++)
			{
				strRefacciones += refacciones[i].toString() + ((i == refacciones.length - 1) ? "" : "#");
			}
		}
		
		SparePartsConn spareParts = new SparePartsConn(idRequest, idUser, idAlmacen, strRefacciones, idPrioridad, notas, tipoServicio, idDireccion, fecha);
		ctrb = spareParts.sendSpareRequest();
		
		if (ctrb.getSuccess() == 0)
		{
			AsyncQueue.AddSendSpare(idRequest, idUser, idAlmacen, strRefacciones, idPrioridad, notas, tipoServicio, idDireccion, fecha);
		}
		
		return ctrb.getSuccess();
	}
	
	@Override
	protected void onPostExecute(Integer headerCode)
	{
		progressDialog.dismiss();

		SharedPreferences sharedPreferences	= activity.getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		Editor editor = sharedPreferences.edit();
		editor.putString(Constants.REFACCIONES_ALMACEN_ID, "");
		editor.putString(Constants.REFACCIONES_MARCA_ID, "");
		editor.putString(Constants.REFACCIONES_MODELO_ID, "");
		editor.putString(Constants.REFACCIONES_ALMACEN_TEXT, "");
		editor.putString(Constants.REFACCIONES_MARCA_TEXT, "");
		editor.putString(Constants.REFACCIONES_MODELO_TEXT, "");
		editor.putString(Constants.REFACCIONES_CANTIDAD_TEXT, "");
		editor.commit();
		
		Intent service = new Intent(MicroformasApp.getAppContext(), GetUpdatesService.class);
		activity.startService(service);
		
		Toast.makeText(activity, ctrb.getStatus(), Toast.LENGTH_SHORT).show();
		
    	Intent intent = new Intent(activity, RequestListActivity.class);
		intent.putExtra("type", Constants.DATABASE_ABIERTAS);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
	}
	
	private ProgressDialog progressDialog;
	
	private RefaccionesActivity activity;

	private EntityCatalog[] refacciones;
	
	private String idRequest ="";
	private String idAlmacen;
	private String idUser = "";
	private String idPrioridad = "";
	private String notas = "";
	private String tipoServicio = "";
	private String idDireccion = "";
	private String fecha = "";
	private String strRefacciones = "";
	
	boolean busy;
	
	ConnTaskResultBean ctrb;
}