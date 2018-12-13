package com.artefacto.microformas.tasks;

import com.artefacto.microformas.AgregarUnidadNegocioActivity;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AgregarUnidadTask extends AsyncTask<String,Void, Integer>
{
	public AgregarUnidadTask(	AgregarUnidadNegocioActivity activity, 
								ProgressDialog progressDialog, 
								String idCliente,
								String idSerie,
								String noInventario,
								String idModelo,
								String noSIM,
								String idSolicitudRec,
								String isNueva,
								String idMarca,
								String noIMEI,
								String idTipoResponsable,
								String idResponsable,
								String idLlave,
								String idSoftware,
								String isRetiro,
								String posicionInventario,
								String idTecnico,
								String idStatusUnidad,
								String noEquipo,
								String idNegocio,
								String isDaniada,
								String idCarrier,
                                String idConnectivity)
    {
		super();
		this.activity 			= activity;
		this.progressDialog 	= progressDialog;
		
		this.idCliente 			= idCliente;
		this.idSerie 			= idSerie;
		this.noInventario 		= noInventario;
		this.idModelo 			= idModelo;
		this.noSIM 				= noSIM;
		this.idSolicitudRec 	= idSolicitudRec;
		this.isNueva 			= isNueva;
		this.idMarca 			= idMarca;
		this.noIMEI 			= noIMEI;
		this.idTipoResponsable 	= idTipoResponsable;
		this.idResponsable 		= idResponsable;
		this.idLlave 			= idLlave;
		this.idSoftware 		= idSoftware;
		this.isRetiro 			= isRetiro;
		this.posicionInventario = posicionInventario;
		this.idTecnico 			= idTecnico;
		this.idStatusUnidad 	= idStatusUnidad;
		this.noEquipo 			= noEquipo;
		this.idNegocio 			= idNegocio;
		this.isDaniada 			= isDaniada;
		this.idCarrier 			= idCarrier;
        this.mIdConnectivity = idConnectivity;
	}
	
	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params) {
		
		genericResultBean = HTTPConnections.agregarUnidad(	idCliente,
															idSerie,
															noInventario,
															idModelo,
															noSIM,
															idSolicitudRec,
															isNueva,
															idMarca,
															noIMEI,
															idTipoResponsable,
															idResponsable,
															idLlave,
															idSoftware,
															isRetiro,
															posicionInventario,
															idTecnico,
															idStatusUnidad,
															noEquipo,
															idNegocio,
															isDaniada,
															idCarrier,
                                                            mIdConnectivity);
		
		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode)
    {
		progressDialog.dismiss();
		activity.nextScreen(genericResultBean);	
	}

    private AgregarUnidadNegocioActivity activity;
    private ProgressDialog progressDialog;

    String 	idCliente;
    String 	idSerie;
    String 	noInventario;
    String 	idModelo;
    String 	noSIM;
    String 	idSolicitudRec;
    String 	isNueva;
    String 	idMarca;
    String 	noIMEI;
    String 	idTipoResponsable;
    String 	idResponsable;
    String 	idLlave;
    String 	idSoftware;
    String 	isRetiro;
    String 	posicionInventario;
    String 	idTecnico;
    String 	idStatusUnidad;
    String 	noEquipo;
    String 	idNegocio;
    String 	isDaniada;
    String 	idCarrier;
    String mIdConnectivity;
    GenericResultBean genericResultBean;
}