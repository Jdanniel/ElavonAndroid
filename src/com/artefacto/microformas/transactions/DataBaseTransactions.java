package com.artefacto.microformas.transactions;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.util.Log;

import com.artefacto.microformas.beans.CalidadBilleteBean;
import com.artefacto.microformas.beans.CausaRetiroBean;
import com.artefacto.microformas.beans.ClientBean;
import com.artefacto.microformas.beans.ClienteCalidadBilleteBean;
import com.artefacto.microformas.beans.ClienteCondicionSiteBean;
import com.artefacto.microformas.beans.CondicionSiteBean;
import com.artefacto.microformas.beans.ConnectivityBean;
import com.artefacto.microformas.beans.EtiquetasBean;
import com.artefacto.microformas.beans.FailsFoundBean;
import com.artefacto.microformas.beans.PackageShipmentBean;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.beans.SimBean;
import com.artefacto.microformas.beans.SoftwareBean;
import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.sqlite.SQLiteDatabase;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.AlmacenesBean;
import com.artefacto.microformas.beans.CambiosStatusBean;
import com.artefacto.microformas.beans.CausasBean;
import com.artefacto.microformas.beans.CausasRechazoBean;
import com.artefacto.microformas.beans.ClienteModelosBean;
import com.artefacto.microformas.beans.ClientesCambiosStatusBean;
import com.artefacto.microformas.beans.CodigosIntervencion0Bean;
import com.artefacto.microformas.beans.CodigosIntervencion1Bean;
import com.artefacto.microformas.beans.CodigosIntervencion2Bean;
import com.artefacto.microformas.beans.DireccionesBean;
import com.artefacto.microformas.beans.EspecificaCausasRechazoBean;
import com.artefacto.microformas.beans.EspecificacionTipoFallaBean;
import com.artefacto.microformas.beans.GruposBean;
import com.artefacto.microformas.beans.GruposClientesBean;
import com.artefacto.microformas.beans.IngenierosBean;
import com.artefacto.microformas.beans.InsumosBean;
import com.artefacto.microformas.beans.MarcasBean;
import com.artefacto.microformas.beans.MSparePartsBean;
import com.artefacto.microformas.beans.ModelosBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.ProductosStatusBean;
import com.artefacto.microformas.beans.RequestDetailBean;
import com.artefacto.microformas.beans.ServiciosBean;
import com.artefacto.microformas.beans.ServiciosCausasBean;
import com.artefacto.microformas.beans.ServiciosSolucionesBean;
import com.artefacto.microformas.beans.SolicitudesBean;
import com.artefacto.microformas.beans.SolucionesBean;
import com.artefacto.microformas.beans.SparePartsBean;
import com.artefacto.microformas.beans.StatusBean;
import com.artefacto.microformas.beans.TipoFallaBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.beans.UnidadesBean;
import com.artefacto.microformas.beans.ViaticosBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteHelper;

public class DataBaseTransactions {
	static ArrayList<RequestDetailBean> requestDetailBean = new ArrayList<RequestDetailBean>();
	
	static ArrayList<ClientesCambiosStatusBean>		clientesCambiosStatusBeanArray 		= new ArrayList<>();
	static ArrayList<StatusBean>					statusBeanArray 					= new ArrayList<>();
	static ArrayList<ProductosStatusBean>			productosStatusBeanArray 			= new ArrayList<>();
	static ArrayList<CambiosStatusBean>				cambiosStatusBeanArray 				= new ArrayList<>();
	static ArrayList<ViaticosBean>					viaticosBeanArray 					= new ArrayList<>();
	static ArrayList<SparePartsBean>				sparePartsBeanArray					= new ArrayList<>();
	static ArrayList<InsumosBean>					insumosBeanArray 					= new ArrayList<>();
	static ArrayList<AlmacenesBean>					almacenesBeanArray 					= new ArrayList<>();
	static ArrayList<MarcasBean>					marcasBeanArray 					= new ArrayList<>();
	static ArrayList<MSparePartsBean>				mSparePartsBeanArray 				= new ArrayList<>();
	static ArrayList<ModelosBean>					modelosBeanArray 					= new ArrayList<>();
	static ArrayList<CausaRetiroBean>				causaRetiroBeanArray				= new ArrayList<>(); //Campo agregado el 22/03/2017 JDOS
	static ArrayList<DireccionesBean>				direccionesBeanArray 				= new ArrayList<>();
	static ArrayList<ServiciosBean>					serviciosBeanArray 					= new ArrayList<>();
	static ArrayList<UnidadesBean>					unidadesBeanArray 					= new ArrayList<>();
	static ArrayList<CausasBean>					causasBeanArray 					= new ArrayList<>();
	static ArrayList<CausasRechazoBean>				causasRechazoBeanArray 				= new ArrayList<>();
	static ArrayList<EspecificaCausasRechazoBean>	especificaCausasRechazoBeanArray	= new ArrayList<>();
	static ArrayList<SolucionesBean>				solucionesBeanArray 				= new ArrayList<>();
	static ArrayList<GruposBean>					gruposBeanArray 					= new ArrayList<>();
	static ArrayList<GruposClientesBean>			gruposClientesBeanArray 			= new ArrayList<>();
	static ArrayList<CodigosIntervencion0Bean>		codigosIntervencion0BeanArray 		= new ArrayList<>();
	static ArrayList<CodigosIntervencion1Bean>		codigosIntervencion1BeanArray 		= new ArrayList<>();
	static ArrayList<CodigosIntervencion2Bean>		codigosIntervencion2BeanArray 		= new ArrayList<>();
	
	static ArrayList<ClienteModelosBean>			clientesModBeanArray 				= new ArrayList<>();
	static ArrayList<ServiciosSolucionesBean>		servSolBeanArray 					= new ArrayList<>();
	static ArrayList<TipoFallaBean>					tipoFallaBeanArray 					= new ArrayList<>();
	static ArrayList<EspecificacionTipoFallaBean>	espTipoFallaBeanArray 				= new ArrayList<>();
	static ArrayList<ServiciosCausasBean>			servCauBeanArray 					= new ArrayList<>();
	static ArrayList<IngenierosBean>				ingenierosBeanArray 				= new ArrayList<>();
	
	static ArrayList<UnidadesBean>					envioUnidadBeanArray 					= new ArrayList<>();
	static ArrayList<PackageShipmentBean> receptionShipmentBeas = new ArrayList<>();
	
	static ArrayList<SolicitudesBean>	solicitudesNuevasBean;
	static ArrayList<SolicitudesBean>	solicitudesAbiertasBean;
	static ArrayList<SolicitudesBean>	solicitudesCerradasBean;
	static ArrayList<SolicitudesBean>	solicitudesPendientesBean;
	
	public boolean setNotificacionesNuevas(SQLiteHelper sqliteHelper, String id,
        NotificationsUpdateBean notificationsUpdateBean)
    {
        SQLiteDatabase db = sqliteHelper.getWritableDB();
        boolean saveNuevas = false;

		solicitudesNuevasBean = HTTPConnections.getSolicitudes(id, Constants.DATABASE_NUEVAS);
		sqliteHelper.deleteRequests(String.valueOf(Constants.DATABASE_NUEVAS), db);
		for(int i = 0; i < solicitudesNuevasBean.size(); i++)
        {
			if(solicitudesNuevasBean.get(i).getIdAr() != null)
            {
                Object response = HTTPConnections.getRequestDetails(solicitudesNuevasBean.get(i).getIdAr(), Constants.DATABASE_NUEVAS);
                if(response instanceof RequestDetailBean)
                {
                    RequestDetailBean requestDetail = (RequestDetailBean) response;
                    requestDetailBean.add(requestDetail);
                    sqliteHelper.setRequests(requestDetail, db);

                    db.execSQL("update " + SQLiteHelper.CATALOGS_DB_NAME + " set "
                        + SQLiteHelper.CATALOGS_NUMBER + " = " +
                        " ( select count (*) from " + SQLiteHelper.REQUESTS_DB_NAME
                            + " where " + SQLiteHelper.CATALOGS_ID_CATALOG + " = " + Constants.DATABASE_NUEVAS
                        + " ) where " + SQLiteHelper.CATALOGS_ID_CATALOG + " = " + Constants.DATABASE_NUEVAS);
                }
                else
                {
                    ResultBean resultBean = (ResultBean) response;

                    resultBean.getMessage();
                }
				
				if (MicroformasApp.activity != null)
                {
					MicroformasApp.activity.UpdateGUI();
				}
			}
		}

        if(db != null)
        {
            sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_NUEVAS, db);
            saveNuevas = true;
            db.close();
        }

		return saveNuevas;
	}
	
	public boolean setNotificacionesAbiertas(SQLiteHelper sqliteHelper, String id,
		NotificationsUpdateBean notificationsUpdateBean)
	{
        SQLiteDatabase db = sqliteHelper.getWritableDB();
		boolean saveAbiertas = false;

        solicitudesAbiertasBean = HTTPConnections.getSolicitudes(id, Constants.DATABASE_ABIERTAS);
        sqliteHelper.deleteRequests(String.valueOf(Constants.DATABASE_ABIERTAS), db);
        for(int i = 0; i < solicitudesAbiertasBean.size(); i++)
        {
            if(solicitudesAbiertasBean.get(i).getIdAr() != null)
            {
                Object response = HTTPConnections.getRequestDetails(solicitudesAbiertasBean.get(i).getIdAr(), Constants.DATABASE_ABIERTAS);
                if(response instanceof RequestDetailBean)
                {

                    RequestDetailBean requestDetail = (RequestDetailBean) response;
                    requestDetailBean.add(requestDetail);
                    sqliteHelper.setRequests(requestDetail, db);

                    db.execSQL("update " + SQLiteHelper.CATALOGS_DB_NAME + " set "
                            + SQLiteHelper.CATALOGS_NUMBER + " = "
                            + " ( select count (*) from " + SQLiteHelper.REQUESTS_DB_NAME + " where "
                            + SQLiteHelper.CATALOGS_ID_CATALOG + " = " + Constants.DATABASE_ABIERTAS
                            + " ) where " + SQLiteHelper.CATALOGS_ID_CATALOG + " = " + Constants.DATABASE_ABIERTAS);
                }
                else
                {
                    ResultBean resultBean = (ResultBean) response;
                    Log.d("Microformas - Abiertas", resultBean.getMessage());
                    resultBean.getMessage();
                }

                if (MicroformasApp.activity != null)
                {
                    MicroformasApp.activity.UpdateGUI();
                }
            }
        }

        if(db != null)
        {
            sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_ABIERTAS, db);
            saveAbiertas = true;
            db.close();
        }

		return saveAbiertas;
	}
	
	public boolean setNotificacionesCerradas(SQLiteHelper sqliteHelper, String id,
        NotificationsUpdateBean notificationsUpdateBean)
    {
        SQLiteDatabase db = sqliteHelper.getWritableDB();
		boolean	saveCerradas	= false;
		solicitudesCerradasBean = HTTPConnections.getSolicitudes(id, Constants.DATABASE_CERRADAS);
		sqliteHelper.deleteRequests(String.valueOf(Constants.DATABASE_CERRADAS), db);
		for(int i = 0; i < solicitudesCerradasBean.size(); i++)
        {
			if(solicitudesCerradasBean.get(i).getIdAr() != null)
            {
				Object response = HTTPConnections.getRequestDetails(solicitudesCerradasBean.get(i).getIdAr(), Constants.DATABASE_CERRADAS);
                if(response instanceof RequestDetailBean)
                {
                    RequestDetailBean requestDetail = (RequestDetailBean) response;
                    requestDetailBean.add(requestDetail);
                    sqliteHelper.setRequests(requestDetail, db);
                    db.execSQL("update " + SQLiteHelper.CATALOGS_DB_NAME
                        + " set "   + SQLiteHelper.CATALOGS_NUMBER + " = "
                        + " ( select count (*) from " + SQLiteHelper. REQUESTS_DB_NAME
                            + " where " + SQLiteHelper.CATALOGS_ID_CATALOG + " = "
                        + Constants.DATABASE_CERRADAS
                        + " ) where " + SQLiteHelper.CATALOGS_ID_CATALOG + " = " + Constants.DATABASE_CERRADAS);
                    //Indica si hay documentos nuevos//Pregunatar a susi
                     /*int idPrefacturacion = Integer.valueOf(requestDetailBean.get(i).getvPreFacturacion());
                    if( idPrefacturacion > 0 && idPrefacturacion < 5)
                    {
                        long result = sqliteHelper.setDocumentsCatalog(requestDetailBean.get(i).getIdAr(), db);
                    }*/
                }
                else
                {
                    ResultBean resultBean = (ResultBean) response;
                    Log.d("Microformas - Cerradas", resultBean.getMessage());
                    resultBean.getMessage();
                }
				
				if (MicroformasApp.activity != null)
                {
					MicroformasApp.activity.UpdateGUI();
				}
			}
		}
		
		if(db!=null)
        {
			sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_CERRADAS, db);
			saveCerradas = true;
			db.close();
		}

		return saveCerradas;
	}
	
	public boolean setNotificacionesPendientes(SQLiteHelper sqliteHelper, String id,
        NotificationsUpdateBean notificationsUpdateBean)
    {
		SQLiteDatabase db = sqliteHelper.getWritableDB();
		boolean savePendientes = false;
				
		solicitudesPendientesBean = HTTPConnections.getSolicitudes(id, Constants.DATABASE_PENDIENTES);
		sqliteHelper.deleteRequests(String.valueOf(Constants.DATABASE_PENDIENTES), db);
		for(int i = 0; i < solicitudesPendientesBean.size(); i++)
        {
			if(solicitudesPendientesBean.get(i).getIdAr() != null)
            {
				Object response = HTTPConnections.getRequestDetails(solicitudesPendientesBean.get(i).getIdAr(), Constants.DATABASE_PENDIENTES);
                if(response instanceof RequestDetailBean)
                {
                    RequestDetailBean requestDetail = (RequestDetailBean) response;
                    requestDetailBean.add(requestDetail);
                    sqliteHelper.setRequests(requestDetail, db);
                    db.execSQL("update " + SQLiteHelper.CATALOGS_DB_NAME +
                        " set " + SQLiteHelper.CATALOGS_NUMBER + " = "
                        + " ( select count (*) from " + SQLiteHelper.REQUESTS_DB_NAME
                            + " where " + SQLiteHelper.CATALOGS_ID_CATALOG + " = " + Constants.DATABASE_PENDIENTES
                        + " ) where " + SQLiteHelper.CATALOGS_ID_CATALOG + " = " + Constants.DATABASE_PENDIENTES);
                }
                else
                {
                    ResultBean resultBean = (ResultBean) response;
                    Log.d("Microformas - Pendient", resultBean.getMessage());
                    resultBean.getMessage();
                }

				if (MicroformasApp.activity != null)
                {
					MicroformasApp.activity.UpdateGUI();
				}
			}
		}
		
		if(db != null){
			sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_PENDIENTES, db);
			savePendientes = true;
		}
		
		db.close();
		return savePendientes;
	}
	
	public boolean setUnidadesCatalog(String id, SQLiteHelper sqliteHelper,
        NotificationsUpdateBean notificationsUpdateBean)
    {
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveUnidades 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		unidadesBeanArray	= HTTPConnections.getUnidades(id);
		if(unidadesBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteUnidades(db);
			sqliteHelper.deleteUnidad(db);
		}
		Log.e("TotalUnidad", String.valueOf(unidadesBeanArray.size()));
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < unidadesBeanArray.size(); i++){
			if(unidadesBeanArray.get(i).getId() != null){
				sqliteHelper.setUnidades(unidadesBeanArray.get(i), db);
			}
		}
		
		if(db != null){
			sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_UNIDADES, db);
			
			//Arreglo Beans, almacenando las unidades
			for(int i = 0; i < unidadesBeanArray.size(); i++){
				UnitBean tempUnitBean = new UnitBean();
				if(unidadesBeanArray.get(i).getId() != null){
					tempUnitBean = HTTPConnections.getUnidad(unidadesBeanArray.get(i).getId());
						
					//Actualizamos el registro en la base de datos
					try{
						sqliteHelper.setUnidad(tempUnitBean, db);
					}
					catch(Exception e){
						sqliteHelper.setUnidad(tempUnitBean, db);
					}
				}
			}
			
			if(db != null){
				sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_NUEVAS, db);
			}
			
			saveUnidades = true;
		}
		
		db.close();
		return saveUnidades;	
	}

    public boolean setInvSupplies(String id, SQLiteHelper sqliteHelper,
        NotificationsUpdateBean notificationsUpdateBean)
    {
        boolean saveInvSupplies = false;
        Object result = HTTPConnections.getInvSupplies(id);
		SQLiteDatabase db = sqliteHelper.getWritableDB();
		sqliteHelper.deleteInvSupplies(db);
        if(result instanceof ArrayList<?>)
        {
            ArrayList<SupplyBean> suppliesBean = (ArrayList<SupplyBean>) result;

            sqliteHelper.deleteInvSupplies(db);
            for(int i = 0; i < suppliesBean.size(); i++)
            {
                sqliteHelper.setInvSupply(suppliesBean.get(i), db);
            }

            if(db != null)
            {
                sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_INV_SUPPLIES, db);
                saveInvSupplies = true;
                db.close();
            }
        }

        return saveInvSupplies;
    }
	
	public boolean setDireccionesCatalog(String id, SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveDirecciones = false;		
		
		//Arreglo Beans, almacenando las solicitudes
		direccionesBeanArray	= HTTPConnections.getDirecciones(id);
		if(direccionesBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteDirecciones(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < direccionesBeanArray.size(); i++){
			if(direccionesBeanArray.get(i).getId() != null){
				sqliteHelper.setDirecciones(direccionesBeanArray.get(i), db);
			}
		}
		
		if(db != null){
			sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_DIRECCIONES, db);
			saveDirecciones = true;
		}
		
		db.close();
		return saveDirecciones;	
	}
	
	public boolean setProcessShipmentCatalog(String id, SQLiteHelper sqliteHelper,
        NotificationsUpdateBean notificationsUpdateBean)
    {   //Inicializan las bases de datos
        SQLiteDatabase db = sqliteHelper.getWritableDB();
        boolean saveProcessShipment = false;

        //Arreglo Beans, almacenando las solicitudes
        Object result = HTTPConnections.getProcessShipment(id);
        if(result instanceof ArrayList<?>)
        {
            ArrayList<PackageShipmentBean> shipmentBeans = (ArrayList<PackageShipmentBean>) result;
            sqliteHelper.deleteProcessShipment(db);
            for(int i = 0; i < shipmentBeans.size(); i++)
            {
                sqliteHelper.setProcessShipment(shipmentBeans.get(i), db);
            }

            if(db != null)
            {
                sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_ENVIOSPEND, db);
                saveProcessShipment = true;

                db.close();
            }
        }

        return saveProcessShipment;
	}
	
	public boolean setRecepcionesCatalog(String id, SQLiteHelper sqliteHelper,
        NotificationsUpdateBean notificationsUpdateBean)
    {   //Inicializan las bases de datos
		SQLiteDatabase db = sqliteHelper.getWritableDB();
		boolean saveRecepciones = false;		
		
		//Arreglo Beans, almacenando las solicitudes
		Object result = HTTPConnections.getReceptions(id);
        if(result instanceof ArrayList<?>)
        {
            ArrayList<PackageShipmentBean> shipmentBeans = (ArrayList<PackageShipmentBean>) result;
            sqliteHelper.deleteRecepciones(db);
            for(int i = 0; i < shipmentBeans.size(); i++)
            {
                sqliteHelper.setRecepciones(shipmentBeans.get(i), db);
            }

            if(db != null)
            {
			    sqliteHelper.setCatalogs(notificationsUpdateBean, Constants.DATABASE_RECEPCIONES, db);
			    saveRecepciones = true;

                db.close();
		    }
        }

		return saveRecepciones;	
	}
	
	public boolean setStatusCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
	{
		//Inicializan las bases de datos
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		boolean saveStatus = false;		
		
		//Arreglo Beans, almacenando las solicitudes
		statusBeanArray 		= HTTPConnections.getStatusCatalog();
		if(statusBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteStatus(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < statusBeanArray.size(); i++){
			if(statusBeanArray.get(i).getId() != null){
				sqliteHelper.setStatus(statusBeanArray.get(i), db);
			}
		}
		
		if(db != null){
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_STATUS, db);
			saveStatus = true;
		}
		
		db.close();
		
		return saveStatus;
	}
	
	public boolean setProductosCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		boolean saveProductos = false;		
		
		//Arreglo Beans, almacenando las solicitudes
		productosStatusBeanArray 	= HTTPConnections.getProductosCatalog();
		if(productosStatusBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteProductosStatus(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < productosStatusBeanArray.size(); i++){
			if(productosStatusBeanArray.get(i).getIdProducto() != null){
				sqliteHelper.setProductosStatus(productosStatusBeanArray.get(i), db);
			}
		}
		
		if(db != null){
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_PRODUCTOS, db);
			saveProductos = true;
		}
		
		db.close();
		
		return saveProductos;
	}
	
	public void setClientesCambiosStatus(SQLiteHelper sqliteHelper){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		//Arreglo Beans, almacenando las solicitudes
		clientesCambiosStatusBeanArray 		= HTTPConnections.getClientesCambiosStatus();
		if(clientesCambiosStatusBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteClientesCambios(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < clientesCambiosStatusBeanArray.size(); i++){
			if(clientesCambiosStatusBeanArray.get(i).getIdCliente() != null){
				sqliteHelper.setClientesCambios(clientesCambiosStatusBeanArray.get(i), db);
			}
		}
		
		db.close();
	}
	
	//Esta clase depende de que estén llenos los campos por setClientesCambiosStatus
	public boolean setCambiosCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		boolean saveCambios = false;		
		
		//Arreglo Beans, almacenando las solicitudes
		sqliteHelper.deleteCambioStatus(db);
		
		//Se hará una lista de los clientes, y hará su consulta para cada uno
		String[] 			campos 			= new String[] {SQLiteHelper.CLIENTES_ID_CLIENTE};
        ArrayList<String>	clientesList 	= new ArrayList<String>();
		
        //Adquiere un arreglo con los distintos clientes a consultar
        Cursor c = db.query(SQLiteHelper.CLIENTES_DB_NAME, campos, null, null, null, null, null);	 
		 
        try{
        	if (c != null ) {
        		if  (c.moveToFirst()) {
        			do {
        				clientesList.add(c.getString(0));
        			}while (c.moveToNext());
        		}
        	}
        }
        catch(Exception e){}
        c.close();
        
		//para cada cliente, se hará la búsqueda de registros para guardarla en DB
        for(int i = 0; i < clientesList.size(); i++){
        	cambiosStatusBeanArray 	= HTTPConnections.getCambiosCatalog(clientesList.get(i));
        	
        	//Llena el bean con las solicitudes correspondientes
    		for(int j = 0; j < cambiosStatusBeanArray.size(); j++){
    			if(cambiosStatusBeanArray.get(j).getIdStatusIni() != null){
    				sqliteHelper.setCambioStatus(cambiosStatusBeanArray.get(j), clientesList.get(i), db);
    			}
    		}
    		
    		if(db != null){
    			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CAMBIOS, db);
    			saveCambios = true;
    		}
        }
		db.close();
		
		return saveCambios;
	}
	
	public boolean setViaticosCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		boolean saveViaticos = false;		
		
		//Arreglo Beans, almacenando las solicitudes
		viaticosBeanArray 		= HTTPConnections.getViaticos();
		if(viaticosBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteViaticos(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < viaticosBeanArray.size(); i++){
			if(viaticosBeanArray.get(i).getIdViatico() != null){
				sqliteHelper.setViaticos(viaticosBeanArray.get(i), db);
			}
		}
		
		if(db != null){
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_VIATICOS, db);
			saveViaticos = true;
		}
		
		db.close();
		
		return saveViaticos;
	}
	
	public boolean setSparePartsCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveSpareParts 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		sparePartsBeanArray	= HTTPConnections.getSpareParts();
		if(sparePartsBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteSpareParts(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < sparePartsBeanArray.size(); i++){
			if(sparePartsBeanArray.get(i).getId() != null){
				sqliteHelper.setSpareParts(sparePartsBeanArray.get(i), db);
			}
		}
		
		if(db != null){
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_SPARE_PARTS, db);
			saveSpareParts = true;
		}
		
		db.close();
		
		return saveSpareParts;
	}

	public boolean setFailsFoundCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
    {
        SQLiteDatabase db = sqliteHelper.getWritableDB();
        Object result = HTTPConnections.getFailsFound();
        if(result instanceof ArrayList<?>)
        {
            if(db != null)
            {
                sqliteHelper.deleteFailsFound(db);

                @SuppressWarnings("unchecked")
                ArrayList<FailsFoundBean> list = (ArrayList<FailsFoundBean>) result;
                for(int i = 0; i < list.size(); i++)
                {
                    sqliteHelper.setFailsFound(list.get(i), db);
                }

                sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_FAILS_FOUNDS, db);
                db.close();

                return true;
            }
            else
            {
                Log.d("Microformas", "DB for setFailsFoundCatalog is null.");
                return false;
            }
        }

        Log.d("Microformas", ((ResultBean) result).getMessage());
        return false;
    }

    public boolean setClientsCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
    {
        SQLiteDatabase db = sqliteHelper.getWritableDB();
        Object result = HTTPConnections.getClients();
        if(result instanceof ArrayList<?>)
        {
            if(db != null)
            {
                sqliteHelper.deleteClients(db);

                @SuppressWarnings("unchecked")
                ArrayList<ClientBean> list = (ArrayList<ClientBean>) result;
                for(int i = 0; i < list.size(); i++)
                {
                    sqliteHelper.setClient(list.get(i), db);
                }

                sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CLIENTS, db);
                db.close();

                return true;
            }
            else
            {
                Log.d("Microformas", "DB for setClientsCatalog is null.");
                return false;
            }
        }

        Log.d("Microformas", ((ResultBean) result).getMessage());
        return false;
    }

	public boolean setSoftwareCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
	{
		SQLiteDatabase db = sqliteHelper.getWritableDB();
		Object result = HTTPConnections.getSoftwares();
		if(result instanceof ArrayList<?>)
		{
			if(db != null)
			{
				sqliteHelper.deleteSoftware(db);

				@SuppressWarnings("unchecked")
				ArrayList<SoftwareBean> list = (ArrayList<SoftwareBean>) result;
				for(int i = 0; i < list.size(); i++)
				{
					sqliteHelper.setSoftware(list.get(i), db);
				}

				sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_SOFTWARE, db);
				db.close();

				return true;
			}
			else
			{
				Log.d("Microformas", "DB for setConnectivity is null.");
				return false;
			}
		}

		Log.d("Microformas", ((ResultBean) result).getMessage());
		return false;
	}

    public boolean setConnectivityCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
    {
        SQLiteDatabase db = sqliteHelper.getWritableDB();
        Object result = HTTPConnections.getConnectivities();
        if(result instanceof ArrayList<?>)
        {
            if(db != null)
            {
                sqliteHelper.deleteConnectivity(db);

                @SuppressWarnings("unchecked")
                ArrayList<ConnectivityBean> list = (ArrayList<ConnectivityBean>) result;
                for(int i = 0; i < list.size(); i++)
                {
                    sqliteHelper.setConnectivity(list.get(i), db);
                }

                sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CONNECTIVITY, db);
                db.close();

                return true;
            }
            else
            {
                Log.d("Microformas", "DB for setConnectivity is null.");
                return false;
            }
        }

        Log.d("Microformas", ((ResultBean) result).getMessage());
        return false;
    }
	
	public boolean setInsumosCatalog(SQLiteHelper sqliteHelper,
        NotificationsUpdateBean notificationsUpdateBean)
    {
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveInsumos 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		insumosBeanArray	= HTTPConnections.getInsumos();
		if(insumosBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteInsumos(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < insumosBeanArray.size(); i++){
			if(insumosBeanArray.get(i).getId() != null){
				sqliteHelper.setInsumos(insumosBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_INSUMOS, db);
			saveInsumos = true;
            db.close();
		}
		
		return saveInsumos;
	}
	
	public boolean setAlmacenesCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
    {
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveAlmacenes 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		almacenesBeanArray	= HTTPConnections.getAlmacenes();
		if(almacenesBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteAlmacenes(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < almacenesBeanArray.size(); i++){
			if(almacenesBeanArray.get(i).getId() != null){
				sqliteHelper.setAlmacenes(almacenesBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_ALMACENES, db);
			saveAlmacenes = true;
            db.close();
		}

		return saveAlmacenes;
	}
	
	public boolean setMarcasCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
    {
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveMarcas 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		marcasBeanArray	= HTTPConnections.getMarcas();
		if(marcasBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteMarcas(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < marcasBeanArray.size(); i++){
			if(marcasBeanArray.get(i).getId() != null){
				sqliteHelper.setMarcas(marcasBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_MARCAS, db);
			saveMarcas = true;
            db.close();
		}

		return saveMarcas;
	}
	
	public boolean setMSparePartsCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveMSpareParts 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		mSparePartsBeanArray	= HTTPConnections.getMSpareParts();
		if(mSparePartsBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteMSpareParts(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < mSparePartsBeanArray.size(); i++){
			if(mSparePartsBeanArray.get(i).getId() != null){
				sqliteHelper.setModeloSpareParts(mSparePartsBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_MSPARE_PARTS, db);
			saveMSpareParts = true;
            db.close();
		}

		return saveMSpareParts;
	}
	
	public boolean setModelosCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveModelos 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		modelosBeanArray	= HTTPConnections.getModelos();
		if(modelosBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteModelos(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < modelosBeanArray.size(); i++){
			if(modelosBeanArray.get(i).getId() != null){
				sqliteHelper.setModelos(modelosBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_MODELOS, db);
			saveModelos = true;
            db.close();
		}
		
		return saveModelos;
	}

	/*
	* Metodo para obtener la lista de catalogos de Causa de Retiro
	* */

	public boolean setCausaRetiro(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){

		SQLiteDatabase db = sqliteHelper.getWritableDB();
		Object result = HTTPConnections.getCausaRetiro();
		if(result instanceof ArrayList<?>)
		{
			if(db != null)
			{
				sqliteHelper.deleteCausaRetiro(db);

				@SuppressWarnings("unchecked")
				ArrayList<CausaRetiroBean> list = (ArrayList<CausaRetiroBean>) result;
				for(int i = 0; i < list.size(); i++)
				{
					sqliteHelper.setCausaRetiro(list.get(i), db);
				}

				sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CAUSAS_RETIRO, db);
				db.close();

				return true;
			}
			else
			{
				return false;
			}
		}
		return false;

	}

	/*
	* Metodo para obtener la lista de catalogos de Etiquetas 16082017
	* */

	public boolean setEtiquetas(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){

		SQLiteDatabase db = sqliteHelper.getWritableDB();
		Object result = HTTPConnections.getEtiquetas();

		if(result instanceof ArrayList<?>)
		{
			if(db != null)
			{
				sqliteHelper.deleteEtiquetas(db);

				@SuppressWarnings("unchecked")
				ArrayList<EtiquetasBean> list = (ArrayList<EtiquetasBean>) result;
				for(int i = 0; i < list.size(); i++)
				{
					sqliteHelper.setEtiquetas(list.get(i), db);
				}

				sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_ETIQUETAS, db);
				db.close();

				return true;
			}
			else
			{
				return false;
			}
		}

		Log.d("Microformas", ((ResultBean) result).getMessage());
		return false;

	}

	/*
	* CAMBIO 30/01/2018
	* */
	public boolean setCalidadBillete(SQLiteHelper sqLiteHelper, NotificationsUpdateBean notificationsUpdateBean){
		SQLiteDatabase db = sqLiteHelper.getWritableDB();
		Object result = HTTPConnections.getCalidadBillete();

		if(result instanceof ArrayList<?>){
			if(db != null){
				sqLiteHelper.deleteCalidadBillete(db);

				ArrayList<CalidadBilleteBean> list = (ArrayList<CalidadBilleteBean>) result;

				for(int i = 0; i < list.size(); i++){
					sqLiteHelper.setCalidadBillete(list.get(i),db);
				}

				sqLiteHelper.setExtraCatalogs(notificationsUpdateBean,Constants.CATALOG_CALIDAD_BILLETE,db);
				db.close();

				return true;
			}else{
				Log.d("Microformas", "DB for setCalidadBillete is null.");
				return false;
			}
		}
		return false;
	}

	public boolean setClienteCalidadBillete(SQLiteHelper sqLiteHelper, NotificationsUpdateBean notificationsUpdateBean){
		SQLiteDatabase db = sqLiteHelper.getWritableDB();
		Object result = HTTPConnections.getClienteCalidadBillete();

		if(result instanceof ArrayList<?>){
			if(db != null){
				sqLiteHelper.deleteClienteCalidadBillete(db);

				ArrayList<ClienteCalidadBilleteBean> list = (ArrayList<ClienteCalidadBilleteBean>) result;

				for(int i = 0; i < list.size(); i++){
					sqLiteHelper.setClienteCalidadBillete(list.get(i),db);
				}

				sqLiteHelper.setExtraCatalogs(notificationsUpdateBean,Constants.CATALOG_C_CALIDAD_BILLETE,db);
				db.close();

				return true;
			}else{
				Log.d("Microformas", "DB for setClienteCalidadBillete is null.");
				return false;
			}
		}
		return false;
	}

	public boolean setCondicionSite(SQLiteHelper sqLiteHelper, NotificationsUpdateBean notificationsUpdateBean){
		SQLiteDatabase db = sqLiteHelper.getWritableDB();
		Object result = HTTPConnections.getCondicionSite();

		if(result instanceof ArrayList<?>){
			if(db != null){
				sqLiteHelper.deleteCondicionSite(db);

				ArrayList<CondicionSiteBean> list = (ArrayList<CondicionSiteBean>) result;

				for(int i = 0; i < list.size(); i++){
						sqLiteHelper.setCondicionSite(list.get(i), db);
				}

				sqLiteHelper.setExtraCatalogs(notificationsUpdateBean,Constants.CATALOG_CONDICION_SITE,db);
				db.close();

				return true;
			}else{
				Log.d("Microformas", "DB for setCondicionSite is null.");
				return false;
			}
		}
		Log.d("Microformas", ((ResultBean) result).getMessage());
		return false;
	}

	public boolean setClienteCondicionSite(SQLiteHelper sqLiteHelper, NotificationsUpdateBean notificationsUpdateBean){
		SQLiteDatabase db = sqLiteHelper.getWritableDB();
		Object result = HTTPConnections.getClienteCondicionSite();

		if(result instanceof ArrayList<?>){
			if(db != null){
				sqLiteHelper.deleteClienteCondicionSite(db);

				ArrayList<ClienteCondicionSiteBean> list = (ArrayList<ClienteCondicionSiteBean>) result;

				for(int i = 0; i < list.size(); i++){
					sqLiteHelper.setClienteCondicionSite(list.get(i),db);
				}

				sqLiteHelper.setExtraCatalogs(notificationsUpdateBean,Constants.CATALOG_C_CONDICION_SITE,db);
				db.close();

				return true;
			}else{
				Log.d("Microformas", "DB for setClienteCondicionSite is null.");
				return false;
			}
		}
		Log.d("Microformasd", ((ResultBean) result).getMessage());
		return false;
	}

    @SuppressWarnings("unchecked")
	public boolean setServiciosCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
	{   // Inicializan las bases de datos
		SQLiteDatabase db = sqliteHelper.getWritableDB();
		boolean saveServicios = false;
		
		// Arreglo Beans, almacenando las solicitudes
		Object result = HTTPConnections.getServices();
        if(result instanceof ArrayList<?>)
        {// Llena el bean con las solicitudes correspondientes
            serviciosBeanArray = (ArrayList<ServiciosBean>) result;
            sqliteHelper.deleteServicios(db);

            for(int i = 0; i < serviciosBeanArray.size(); i++)
            {
                if(serviciosBeanArray.get(i).getId() != null)
                {
                    sqliteHelper.setServicios(serviciosBeanArray.get(i), db);
                }
            }
        }
        else
        {
            serviciosBeanArray = new ArrayList<>();
        }

		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_SERVICIOS, db);
			saveServicios = true;
            db.close();
		}

		return saveServicios;
	}
	
	public boolean setCausasCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 	= sqliteHelper.getWritableDB();
		boolean saveCausas 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		causasBeanArray	= HTTPConnections.getCausas();
		if(causasBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteCausas(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < causasBeanArray.size(); i++){
			if(causasBeanArray.get(i).getId() != null){
				sqliteHelper.setCausas(causasBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CAUSAS, db);
			saveCausas = true;
            db.close();
		}

		return saveCausas;
	}
	
	public boolean setCausasRechazoCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 	= sqliteHelper.getWritableDB();
		boolean saveCausasRechazo 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		causasRechazoBeanArray	= HTTPConnections.getCausasRechazo();
		if(causasRechazoBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteCausasRechazo(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < causasRechazoBeanArray.size(); i++){
			if(causasRechazoBeanArray.get(i).getId() != null){
				sqliteHelper.setCausasRechazo(causasRechazoBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CAUSASRECHAZO, db);
			saveCausasRechazo = true;
            db.close();
		}
		
		return saveCausasRechazo;
	}
	
	public boolean setEspecificacionCausasRechazoCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveEspCauRec	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		especificaCausasRechazoBeanArray	= HTTPConnections.getEspecificacionCausasRechazo();
		if(especificaCausasRechazoBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteEspecificacionCausasRechazo(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < especificaCausasRechazoBeanArray.size(); i++){
			if(especificaCausasRechazoBeanArray.get(i).getId() != null){
				sqliteHelper.setEspecificacionCausasRechazo(especificaCausasRechazoBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_ESPECCAUREC, db);
			saveEspCauRec = true;
            db.close();
		}

		return saveEspCauRec;
	}

    @SuppressWarnings("unchecked")
	public boolean setSolucionesCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean)
	{
		SQLiteDatabase db = sqliteHelper.getWritableDB();
		boolean saveSoluciones 	= false;		

		Object response = HTTPConnections.getSolutions();
        if(response instanceof ArrayList<?>)
        {
            solucionesBeanArray = (ArrayList<SolucionesBean>) response;
            sqliteHelper.deleteSoluciones(db);
            for(int i = 0; i < solucionesBeanArray.size(); i++)
            {
                sqliteHelper.setSoluciones(solucionesBeanArray.get(i), db);
            }
        }
        else
        {
            ResultBean resultBean = (ResultBean) response;
            Log.d("Microformas", resultBean.getMessage());
        }

		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_SOLUCIONES, db);
			saveSoluciones = true;
            db.close();
		}
		
		return saveSoluciones;
	}
	
	public boolean setGruposCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 	= sqliteHelper.getWritableDB();
		boolean saveGrupos 	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		gruposBeanArray	= HTTPConnections.getGrupos();
		if(gruposBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteGrupos(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < gruposBeanArray.size(); i++){
			if(gruposBeanArray.get(i).getId() != null){
				sqliteHelper.setGrupos(gruposBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_GRUPOS, db);
			saveGrupos = true;
            db.close();
		}
		
		return saveGrupos;
	}
	
	public boolean setGruposClientesCatalog(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveGruposCli	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		gruposClientesBeanArray	= HTTPConnections.getGruposClientes();
		if(gruposClientesBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteGruposClientes(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < gruposClientesBeanArray.size(); i++){
			if(gruposClientesBeanArray.get(i).getId() != null){
				sqliteHelper.setGruposClientes(gruposClientesBeanArray.get(i), db);
			}
		}
		
		if(db != null){
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_GRUPOSCLIENTES, db);
			saveGruposCli = true;
            db.close();
		}
		
		return saveGruposCli;
	}
	 
	public boolean setCodigosIntervencion0(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveCodigos0	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		codigosIntervencion0BeanArray	= HTTPConnections.getCodigosIntervencion0();
		if(codigosIntervencion0BeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteCodigosIntervencion0(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < codigosIntervencion0BeanArray.size(); i++){
			if(codigosIntervencion0BeanArray.get(i).getId() != null){
				sqliteHelper.setCodigosIntervencion0(codigosIntervencion0BeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CODIGOS0, db);
			saveCodigos0 = true;
            db.close();
		}
		
		return saveCodigos0;
	}
	
	public boolean setCodigosIntervencion1(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveCodigos1	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		codigosIntervencion1BeanArray	= HTTPConnections.getCodigosIntervencion1();
		if(codigosIntervencion1BeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteCodigosIntervencion1(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < codigosIntervencion1BeanArray.size(); i++){
			if(codigosIntervencion1BeanArray.get(i).getId() != null){
				sqliteHelper.setCodigosIntervencion1(codigosIntervencion1BeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CODIGOS1, db);
			saveCodigos1 = true;
            db.close();
		}
		
		return saveCodigos1;
	}
	
	public boolean setCodigosIntervencion2(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveCodigos2	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		codigosIntervencion2BeanArray	= HTTPConnections.getCodigosIntervencion2();
		if(codigosIntervencion2BeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteCodigosIntervencion2(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < codigosIntervencion2BeanArray.size(); i++){
			if(codigosIntervencion2BeanArray.get(i).getId() != null){
				sqliteHelper.setCodigosIntervencion2(codigosIntervencion2BeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CODIGOS2, db);
			saveCodigos2 = true;
            db.close();
		}
		
		return saveCodigos2;
	}
		
	public boolean setClientesModelos(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveClientesMod	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		clientesModBeanArray	= HTTPConnections.getClientesModelos();
		if(clientesModBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteCliMod(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < clientesModBeanArray.size(); i++){
			if(clientesModBeanArray.get(i).getId() != null){
				sqliteHelper.setCliMod(clientesModBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_CLIENTESMOD, db);
			saveClientesMod = true;
            db.close();
		}
		
		return saveClientesMod;
	}
	
	public boolean setServiciosSoluciones(SQLiteHelper sqliteHelper,
        NotificationsUpdateBean notificationsUpdateBean)
    {   //Inicializan las bases de datos
		SQLiteDatabase db = sqliteHelper.getWritableDB();
		boolean saveServSol = false;
		
		//Arreglo Beans, almacenando las solicitudes
		servSolBeanArray	= HTTPConnections.getServiciosSoluciones();
		if(servSolBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteServSol(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < servSolBeanArray.size(); i++){
			if(servSolBeanArray.get(i).getId() != null){
				sqliteHelper.setServSol(servSolBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_SERVSOL, db);
			saveServSol = true;
            db.close();
		}
		
		return saveServSol;
	}
	
	public boolean setTipoFalla(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveTipoFalla	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		tipoFallaBeanArray	= HTTPConnections.getTipoFalla();
		if(tipoFallaBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteTipoFalla(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < tipoFallaBeanArray.size(); i++){
			if(tipoFallaBeanArray.get(i).getId() != null){
				sqliteHelper.setTipoFalla(tipoFallaBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_TIPOFALLA, db);
			saveTipoFalla = true;
            db.close();
		}
		
		return saveTipoFalla;
	}
	
	public boolean setEspecificaTipoFalla(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveEspTipoFalla	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		espTipoFallaBeanArray	= HTTPConnections.getEspecificaTipoFalla();
		if(espTipoFallaBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteEspTiFa(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < espTipoFallaBeanArray.size(); i++){
			if(espTipoFallaBeanArray.get(i).getId() != null){
				sqliteHelper.setEspTiFa(espTipoFallaBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_ESPTIPOFALLA, db);
			saveEspTipoFalla = true;
            db.close();
		}
		
		return saveEspTipoFalla;
	}
	
	public boolean setServiciosCausas(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveServCau		= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		servCauBeanArray	= HTTPConnections.getServiciosCausas();
		if(servCauBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteServCau(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < servCauBeanArray.size(); i++){
			if(servCauBeanArray.get(i).getId() != null){
				sqliteHelper.setServCau(servCauBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_SERVCAU, db);
			saveServCau = true;
            db.close();
		}
		
		return saveServCau;
	}
	
	public boolean setIngenieros(SQLiteHelper sqliteHelper, NotificationsUpdateBean notificationsUpdateBean){
		//Inicializan las bases de datos
		SQLiteDatabase 	db 		= sqliteHelper.getWritableDB();
		boolean saveIngenieros	= false;		
		
		//Arreglo Beans, almacenando las solicitudes
		ingenierosBeanArray	= HTTPConnections.getIngenieros();
		if(ingenierosBeanArray.get(0).getConnStatus() == 200){
			sqliteHelper.deleteIngenieros(db);
		}
			
		//Llena el bean con las solicitudes correspondientes
		for(int i = 0; i < ingenierosBeanArray.size(); i++){
			if(ingenierosBeanArray.get(i).getId() != null){
				sqliteHelper.setIngenieros(ingenierosBeanArray.get(i), db);
			}
		}
		
		if(db != null)
        {
			sqliteHelper.setExtraCatalogs(notificationsUpdateBean, Constants.CATALOG_INGENIEROS, db);
			saveIngenieros = true;
            db.close();
		}
		
		return saveIngenieros;
	}

	public void setMD5(Context context, boolean saveCatalog, int catalog, boolean isExtra)
    {
		SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

        SQLiteHelper sqliteHelper = new SQLiteHelper(context, null);
        SQLiteDatabase db = sqliteHelper.getReadableDB();
        Cursor cursor;

        if(!isExtra)
        {
        	String query = "select "+SQLiteHelper.CATALOGS_NUMBER +" , "+SQLiteHelper.CATALOGS_MD5
                    + " from "+ SQLiteHelper.CATALOGS_DB_NAME
                    + " Where " + SQLiteHelper.CATALOGS_ID_CATALOG +" = " + catalog;
            cursor = db.rawQuery(query,null);
            if(cursor != null )
            {
            	if(cursor.moveToFirst())
                {
            		do
                    {
            			if(catalog == Constants.DATABASE_NUEVAS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_NEWS_NUMBER, cursor.getString(0));
            				editor.putString(Constants.PREF_NEWS_MD5, cursor.getString(1));
            				editor.commit();
            			}
            			else if(catalog == Constants.DATABASE_ABIERTAS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_OPENED_NUMBER,  cursor.getString(0));
            				editor.putString(Constants.PREF_OPENED_MD5,  cursor.getString(1));
            				editor.commit();
            			}
            			else if(catalog == Constants.DATABASE_CERRADAS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_CLOSED_NUMBER,  cursor.getString(0));
            				editor.putString(Constants.PREF_CLOSED_MD5,  cursor.getString(1));
            				editor.commit();
            			}
            			else if(catalog == Constants.DATABASE_PENDIENTES && saveCatalog)
                        {
            				editor.putString(Constants.PREF_PENDINGS_NUMBER, cursor.getString(0));
            				editor.putString(Constants.PREF_PENDINGS_MD5,  cursor.getString(1));
            				editor.commit();
            			}
            			else if(catalog == Constants.DATABASE_UNIDADES && saveCatalog)
                        {
            				editor.putString(Constants.PREF_UNITS_NUMBER, cursor.getString(0));
            				editor.putString(Constants.PREF_UNITS_MD5,  cursor.getString(1));
            				editor.commit();
            			}
                        else if(catalog == Constants.DATABASE_INV_SUPPLIES && saveCatalog)
                        {
                            editor.putString(Constants.PREF_INV_SUPPLIES_NUMBER, cursor.getString(0));
                            editor.putString(Constants.PREF_INV_SUPPLIES_MD5,  cursor.getString(1));
                            editor.commit();
                        }
            			else if(catalog == Constants.DATABASE_DIRECCIONES && saveCatalog)
                        {
            				editor.putString(Constants.PREF_ADDRESSES_NUMBER, cursor.getString(0));
            				editor.putString(Constants.PREF_ADDRESSES_MD5,  cursor.getString(1));
            				editor.commit();
            			}
            			else if(catalog == Constants.DATABASE_ENVIOSPEND && saveCatalog)
                        {
            				editor.putString(Constants.PREF_PENDINGS_SENT_NUMBER, cursor.getString(0));
            				editor.putString(Constants.PREF_PENDINGS_SENT_MD5,  cursor.getString(1));
            				editor.commit();
            			}
            			else if(catalog == Constants.DATABASE_RECEPCIONES && saveCatalog)
                        {
            				editor.putString(Constants.PREF_RECEPTIONS_NUMBER, cursor.getString(0));
            				editor.putString(Constants.PREF_RECEPTIONS_MD5,  cursor.getString(1));
            				editor.commit();
            			}
            		} while (cursor.moveToNext());
            	}
            }
        }
        else
        {
        	String query = "select "+SQLiteHelper.EXTRA_DATE +" from "+ SQLiteHelper.EXTRA_DB_NAME
					+ " Where " + SQLiteHelper.EXTRA_ID_EXTRA +" = " + catalog;
            cursor = db.rawQuery(query, null);
            if (cursor != null )
            {
            	if  (cursor.moveToFirst())
                {
            		do
                    {
            			if(catalog == Constants.CATALOG_STATUS && saveCatalog){
            				editor.putString(Constants.PREF_STATUS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_PRODUCTOS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_PRODUCTS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_VIATICOS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_EXPENSES_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_SPARE_PARTS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_SPARE_PARTS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_INSUMOS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_INPUTS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_ALMACENES && saveCatalog)
                        {
            				editor.putString(Constants.PREF_STORES_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_MARCAS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_BRANDS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_MSPARE_PARTS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_MSPARE_PARTS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_MODELOS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_MODELS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_SERVICIOS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_SERVICES_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_CAUSAS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_CAUSES_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_CAUSASRECHAZO && saveCatalog)
                        {
            				editor.putString(Constants.PREF_REJECTION_CAUSES_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_ESPECCAUREC && saveCatalog)
                        {
            				editor.putString(Constants.PREF_SPEC_REJECTION_CAUSES_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_SOLUCIONES && saveCatalog)
                        {
            				editor.putString(Constants.PREF_SOLUTIONS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_GRUPOS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_GROUPS_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_GRUPOSCLIENTES && saveCatalog){
            				editor.putString(Constants.PREF_GROUPS_CLIENT_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_CODIGOS0 && saveCatalog)
                        {
            				editor.putString(Constants.PREF_CODES_0_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_CODIGOS1 && saveCatalog)
                        {
            				editor.putString(Constants.PREF_CODES_1_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_CODIGOS2 && saveCatalog)
                        {
            				editor.putString(Constants.PREF_CODES_2_DATE, cursor.getString(0));
            				editor.commit();
            			}
                        else if(catalog == Constants.CATALOG_CLIENTS && saveCatalog)
                        {
                            editor.putString(Constants.PREF_DATE_CLIENTS, cursor.getString(0));
                            editor.commit();
                        }
            			else if(catalog == Constants.CATALOG_CLIENTESMOD && saveCatalog)
                        {
            				editor.putString(Constants.PREF_CLIENTS_MOD_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_SERVSOL && saveCatalog)
                        {
            				editor.putString(Constants.PREF_SERVSOL_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_TIPOFALLA && saveCatalog)
                        {
            				editor.putString(Constants.PREF_FAIL_TYPE_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_ESPTIPOFALLA && saveCatalog)
                        {
            				editor.putString(Constants.PREF_SPEC_FAIL_TYPE_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_SERVCAU && saveCatalog)
                        {
            				editor.putString(Constants.PREF_SERVCAU_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_CAMBIOS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_CHANGES_DATE, cursor.getString(0));
            				editor.commit();
            			}
            			else if(catalog == Constants.CATALOG_INGENIEROS && saveCatalog)
                        {
            				editor.putString(Constants.PREF_ENGINEERS_DATE, cursor.getString(0));
            				editor.commit();
            			}
                        else if(catalog == Constants.CATALOG_CONNECTIVITY && saveCatalog)
                        {
                            editor.putString(Constants.PREF_DATE_CONNECTIVITY, cursor.getString(0));
                            editor.commit();
                        }
                        else if(catalog == Constants.CATALOG_SOFTWARE && saveCatalog)
                        {
                            editor.putString(Constants.PREF_DATE_SOFTWARE, cursor.getString(0));
                            editor.commit();
                        }
						else if(catalog == Constants.CATALOG_CAUSAS_RETIRO && saveCatalog)
						{
							editor.putString(Constants.PREF_DATE_CAUSA_RETIRO, cursor.getString(0));
							editor.commit();
						}
						else if (catalog == Constants.CATALOG_ETIQUETAS && saveCatalog)
						{
							editor.putString(Constants.PREF_DATE_ETIQUETAS, cursor.getString(0));
							editor.commit();
						}
                        else if(catalog == Constants.CATALOG_FAILS_FOUNDS && saveCatalog)
                        {
                            editor.putString(Constants.PREF_DATE_FAILS_FOUND, cursor.getString(0));
                            editor.commit();
                        }
                        else if(catalog == Constants.CATALOG_CALIDAD_BILLETE && saveCatalog){
            				editor.putString(Constants.PREF_DATE_TICKET_QUALITY, cursor.getString(0));
            				editor.commit();
						}
						else if(catalog == Constants.CATALOG_CONDICION_SITE && saveCatalog){
							editor.putString(Constants.PREF_DATE_SITE_CONDITION, cursor.getString(0));
							editor.commit();
						}
						else if(catalog == Constants.CATALOG_C_CALIDAD_BILLETE && saveCatalog){
							editor.putString(Constants.PREF_DATE_CLIENT_TICKET_QUALITY, cursor.getString(0));
							editor.commit();
						}
						else if(catalog == Constants.CATALOG_C_CONDICION_SITE && saveCatalog){
							editor.putString(Constants.PREF_DATE_CLIENT_SITE_CONDITION, cursor.getString(0));
							editor.commit();
						}
            		} while (cursor.moveToNext());
            	}
            }
        }

        if(cursor != null)
        {
            cursor.close();
        }

        db.close();
	}
}