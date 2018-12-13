package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import com.artefacto.microformas.EntityCatalog;
import com.artefacto.microformas.TerminalActivity;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class EnviarTerminalTask extends AsyncTask<String,Void, Integer>
{
	public EnviarTerminalTask(TerminalActivity activity, ProgressDialog progressDialog,
		String idAR, String idTecnico, String idAlmacen, ArrayList<EntityCatalog> terminals,
		ArrayList<EntityCatalog> insumos, String idUrgencia, String notas, String tipoServicio,
		String idDireccion, String fechaCompromiso, String isKitInsumo, String idKitInsumo,
        String isConnectivity)
	{
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		
		this.idAR = idAR;
		this.idTecnico = idTecnico;
		this.idAlmacen = idAlmacen;
		this.terminals = terminals;
		this.insumos = insumos;
		this.idUrgencia = idUrgencia;
		this.notas = notas;
		this.tipoServicio = tipoServicio;
		this.idDireccion = idDireccion;
		this.fechaCompromiso = fechaCompromiso;
        this.mIsKitInsumo = isKitInsumo;
		this.mIdKitInsumo = idKitInsumo;
        this.mIsConnectivity = isConnectivity;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params)
    {
		genericResultBean = HTTPConnections.sendPendienteTerminal(idAR, idTecnico, idAlmacen,
            terminals, insumos, idUrgencia, notas, tipoServicio, idDireccion, fechaCompromiso,
            mIsKitInsumo, mIdKitInsumo, mIsConnectivity);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode)
	{
		progressDialog.dismiss();
		activity.nextScreen(genericResultBean);	
	}
	
	private TerminalActivity activity;
	
	private ProgressDialog progressDialog;
	
	private ArrayList<EntityCatalog> terminals;
	private ArrayList<EntityCatalog> insumos;
	
	private String idAR;
	private String idAlmacen;
	private String idTecnico;
	private String idUrgencia;
	private String notas;
	private String tipoServicio;
	private String idDireccion;
	private String fechaCompromiso;
    private String mIsKitInsumo;
	private String mIdKitInsumo;
	private String mIsConnectivity;

	GenericResultBean genericResultBean;
}