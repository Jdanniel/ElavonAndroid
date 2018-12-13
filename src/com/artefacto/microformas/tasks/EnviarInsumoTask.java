package com.artefacto.microformas.tasks;

import com.artefacto.microformas.EntityCatalog;
import com.artefacto.microformas.PendienteInsumoActivity;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class EnviarInsumoTask extends AsyncTask<String,Void, Integer> {
	private PendienteInsumoActivity activity;
	private ProgressDialog progressDialog;
	
	GenericResultBean genericResultBean;
	
	public EnviarInsumoTask(PendienteInsumoActivity activity, ProgressDialog progressDialog,
		String idAR, String idTecnico, String idAlmacen, EntityCatalog[] insumos, String idUrgencia,
		String notas, String tipoServicio, String idDireccion, String fechaCompromiso,
        String isKitInsumo, String idKitInsumo)
	{
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.insumos = insumos;
		this.idAlmacen = idAlmacen;
		this.idAR = idAR;
		this.idTecnico = idTecnico;
		this.idUrgencia = idUrgencia;
		this.notas = notas;
		this.tipoServicio = tipoServicio;
		this.idDireccion = idDireccion;
		this.fechaCompromiso = fechaCompromiso;
		this.mIsKitInsumo = isKitInsumo;
        this.mIdKitInsumo = idKitInsumo;
	}
	
	@Override
	protected void onPreExecute()
	{
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params)
	{
		genericResultBean = HTTPConnections.sendPendienteInsumo(idAR, idTecnico, idAlmacen, insumos,
            idUrgencia, notas, tipoServicio, idDireccion, fechaCompromiso, mIsKitInsumo, mIdKitInsumo);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode)
	{
		progressDialog.dismiss();
		activity.nextScreen(genericResultBean);	
	}
	
	private EntityCatalog[] insumos;
	
	private String idAR;
	private String idTecnico;
	private String idAlmacen;
	private String idUrgencia;
	private String notas;
	private String tipoServicio;
	private String idDireccion;
	private String fechaCompromiso;
	private String mIsKitInsumo;
	private String mIdKitInsumo;
}