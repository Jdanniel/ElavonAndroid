package com.artefacto.microformas.services;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.InventoryFragment;
import com.artefacto.microformas.MainActivity;
import com.artefacto.microformas.R;
import com.artefacto.microformas.RequestDetailActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.receiver.UpdateScheduler;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.ValidateNeedNumberTask;
import com.artefacto.microformas.transactions.DataBaseTransactions;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GetUpdatesService extends IntentService
{
	@Override
	protected void onHandleIntent(Intent intent)
	{
		try
		{
			mMessage =  "Última actualización: ";
			SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			String id =   sharedPreferences.getString(Constants.PREF_USER_ID, "");

			String newsMD5 = sharedPreferences.getString(Constants.PREF_NEWS_MD5, "");
			String openendMD5 = sharedPreferences.getString(Constants.PREF_OPENED_MD5, "");
			String closedMD5 = sharedPreferences.getString(Constants.PREF_CLOSED_MD5, "");
			String pendingsMD5 = sharedPreferences.getString(Constants.PREF_PENDINGS_MD5, "");
			String unitsMD5 = sharedPreferences.getString(Constants.PREF_UNITS_MD5, "");
			String invSuppliesMD5 = sharedPreferences.getString(Constants.PREF_INV_SUPPLIES_MD5, "");
			String addressesMD5 = sharedPreferences.getString(Constants.PREF_ADDRESSES_MD5, "");
			String pendingsSentMD5 = sharedPreferences.getString(Constants.PREF_PENDINGS_SENT_MD5, "");
			String receptionsMD5= sharedPreferences.getString(Constants.PREF_RECEPTIONS_MD5, "");
			String statusDate = sharedPreferences.getString(Constants.PREF_STATUS_DATE, "");
			String productsDate = sharedPreferences.getString(Constants.PREF_PRODUCTS_DATE, "");
			String changesDate = sharedPreferences.getString(Constants.PREF_CHANGES_DATE, "");
			String expensesDate	= sharedPreferences.getString(Constants.PREF_EXPENSES_DATE, "");
			String sparePartsDate = sharedPreferences.getString(Constants.PREF_SPARE_PARTS_DATE, "");
			String inputsDate = sharedPreferences.getString(Constants.PREF_INPUTS_DATE, "");
			String storesDate = sharedPreferences.getString(Constants.PREF_STORES_DATE, "");
			String brandsDate = sharedPreferences.getString(Constants.PREF_BRANDS_DATE, "");
			String mSparePartsDate = sharedPreferences.getString(Constants.PREF_MSPARE_PARTS_DATE, "");
			String modelsDate = sharedPreferences.getString(Constants.PREF_MODELS_DATE, "");
			String servicesDate = sharedPreferences.getString(Constants.PREF_SERVICES_DATE, "");
			String causesDate = sharedPreferences.getString(Constants.PREF_CAUSES_DATE, "");
			String specCauRecDate = sharedPreferences.getString(Constants.PREF_SPEC_REJECTION_CAUSES_DATE, "");
			String solutionsDate = sharedPreferences.getString(Constants.PREF_SOLUTIONS_DATE, "");
			String groupsDate = sharedPreferences.getString(Constants.PREF_GROUPS_DATE, "");
			String groupsCliDate = sharedPreferences.getString(Constants.PREF_GROUPS_CLIENT_DATE, "");
			String codes0Date = sharedPreferences.getString(Constants.PREF_CODES_0_DATE, "");
			String codes1Date = sharedPreferences.getString(Constants.PREF_CODES_1_DATE, "");
			String codes2Date = sharedPreferences.getString(Constants.PREF_CODES_2_DATE, "");
			String clientsModDate = sharedPreferences.getString(Constants.PREF_CLIENTS_MOD_DATE, "");
			String servSolDate = sharedPreferences.getString(Constants.PREF_SERVSOL_DATE, "");
			String failTypeDate = sharedPreferences.getString(Constants.PREF_FAIL_TYPE_DATE, "");
			String specFailTypeDate	= sharedPreferences.getString(Constants.PREF_SPEC_FAIL_TYPE_DATE, "");
			String servCauDate = sharedPreferences.getString(Constants.PREF_SERVCAU_DATE, "");
			String engineersDate = sharedPreferences.getString(Constants.PREF_ENGINEERS_DATE, "");
			String dateClients = sharedPreferences.getString(Constants.PREF_DATE_CLIENTS, "");
			String dateConnectivity = sharedPreferences.getString(Constants.PREF_DATE_CONNECTIVITY, "");
			String dateSoftware = sharedPreferences.getString(Constants.PREF_DATE_SOFTWARE, "");
			String dateCausaRetiro = sharedPreferences.getString(Constants.PREF_DATE_CAUSA_RETIRO,"");
			String dateEtiquetas = sharedPreferences.getString(Constants.PREF_DATE_ETIQUETAS,"");
			String dateFailsFound = sharedPreferences.getString(Constants.PREF_DATE_FAILS_FOUND, "");
			String dateTicketQuality = sharedPreferences.getString(Constants.PREF_DATE_TICKET_QUALITY,"");
			String dateSiteCondition = sharedPreferences.getString(Constants.PREF_DATE_SITE_CONDITION,"");
			String dateClientTicketQuality = sharedPreferences.getString(Constants.PREF_DATE_CLIENT_TICKET_QUALITY,"");
			String dateClientSiteCondition = sharedPreferences.getString(Constants.PREF_DATE_CLIENT_SITE_CONDITION,"");

			NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(id);

			if(notificationsUpdateBean.getConnStatus() == 200)
			{   //Inicia solicitud por http y regreso del bean lleno
				isUpdating = true;
				updateIndex = 0;

				HTTPConnections.VerifyServerMode(MicroformasApp.getAppContext());
				String version = Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE) ?
						getResources().getString(R.string.version_value_prod) :
						getResources().getString(R.string.version_value_test);

				if(notificationsUpdateBean.getVersion().equals(version))
				{
					UpdateMainActivity();
					DataBaseTransactions dataBaseTransactions = new DataBaseTransactions();
					SQLiteHelper 		sqliteHelper 	= new SQLiteHelper(MicroformasApp.getAppContext(), null);

					if(!(notificationsUpdateBean.getNuevasMD5().equals(newsMD5)))
					{
						boolean saveNuevas = dataBaseTransactions.setNotificacionesNuevas(sqliteHelper, sharedPreferences.getString(Constants.PREF_USER_ID, ""), notificationsUpdateBean);
						dataBaseTransactions.setMD5 (MicroformasApp.getAppContext(), saveNuevas, Constants.DATABASE_NUEVAS, false);
						sendUpdateNotification(0);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getAbiertasMD5().equals(openendMD5)))
					{
						boolean saveAbiertas = dataBaseTransactions.setNotificacionesAbiertas(sqliteHelper, sharedPreferences.getString(Constants.PREF_USER_ID, ""), notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveAbiertas, Constants.DATABASE_ABIERTAS, false);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCerradasMD5().equals(closedMD5)))
					{
						boolean saveCerradas = dataBaseTransactions.setNotificacionesCerradas(sqliteHelper, sharedPreferences.getString(Constants.PREF_USER_ID, ""), notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveCerradas, Constants.DATABASE_CERRADAS, false);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getPendientesMD5().equals(pendingsMD5))){
						boolean savePendientes = dataBaseTransactions.setNotificacionesPendientes(sqliteHelper, sharedPreferences.getString(Constants.PREF_USER_ID, ""), notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), savePendientes, Constants.DATABASE_PENDIENTES, false);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getUnidadesMD5().equals(unitsMD5)))
					{
						boolean saveUnidades = dataBaseTransactions.setUnidadesCatalog(sharedPreferences.getString(Constants.PREF_USER_ID, ""), sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveUnidades, Constants.DATABASE_UNIDADES, false);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getInvSuppliesMD5().equals(invSuppliesMD5)))
					{
						boolean saveInvSupplies = dataBaseTransactions.setInvSupplies(sharedPreferences.getString(Constants.PREF_USER_ID, ""), sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveInvSupplies, Constants.DATABASE_INV_SUPPLIES, false);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getDireccionesMD5().equals(addressesMD5)))
					{
						boolean saveDirecciones = dataBaseTransactions.setDireccionesCatalog(sharedPreferences.getString(Constants.PREF_USER_ID, ""), sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveDirecciones, Constants.DATABASE_DIRECCIONES, false);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getEnviosPendMD5().equals(pendingsSentMD5)))
					{
						boolean saveEnviosPend = dataBaseTransactions.setProcessShipmentCatalog(sharedPreferences.getString(Constants.PREF_USER_ID, ""), sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveEnviosPend, Constants.DATABASE_ENVIOSPEND, false);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getRecepcionesMD5().equals(receptionsMD5)))
					{
						boolean saveRecepciones = dataBaseTransactions.setRecepcionesCatalog(sharedPreferences.getString(Constants.PREF_USER_ID, ""), sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveRecepciones, Constants.DATABASE_RECEPCIONES, false);
					}
					updateIndex++;
					UpdateMainActivity();
/*
					if((notificationsUpdateBean.getStatusDate().equals(statusDate)))
					{*/
						boolean saveStatus = dataBaseTransactions.setStatusCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveStatus, Constants.CATALOG_STATUS, true);
/*					}*/
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getProductosDate().equals(productsDate)))
					{
						boolean saveProductos = dataBaseTransactions.setProductosCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveProductos, Constants.CATALOG_PRODUCTOS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					//dataBaseTransactions.setClientesCambiosStatus(sqliteHelper);
					if(!(notificationsUpdateBean.getDateClients().equals(dateClients)))
					{
						boolean saveClients = dataBaseTransactions.setClientsCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveClients, Constants.CATALOG_CLIENTS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCambiosDate().equals(changesDate)))
					{
						dataBaseTransactions.setCambiosCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_CAMBIOS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getViaticosDate().equals(expensesDate)))
					{
						dataBaseTransactions.setViaticosCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_VIATICOS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getSparePartDate().equals(sparePartsDate)))
					{
						dataBaseTransactions.setSparePartsCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_SPARE_PARTS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getInsumosDate().equals(inputsDate)))
					{
						dataBaseTransactions.setInsumosCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_INSUMOS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getAlmacenesDate().equals(storesDate)))
					{
						dataBaseTransactions.setAlmacenesCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_ALMACENES, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getMarcasDate().equals(brandsDate)))
					{
						dataBaseTransactions.setMarcasCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_MARCAS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getMSparePartsDate().equals(mSparePartsDate)))
					{
						dataBaseTransactions.setMSparePartsCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_MSPARE_PARTS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getModelosDate().equals(modelsDate)))
					{
						dataBaseTransactions.setModelosCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_MODELOS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getServiciosDate().equals(servicesDate)))
					{
						dataBaseTransactions.setServiciosCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_SERVICIOS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCausasDate().equals(causesDate)))
					{
						dataBaseTransactions.setCausasCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_CAUSAS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getServiciosDate().equals(servicesDate)))
					{
						dataBaseTransactions.setCausasRechazoCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_CAUSASRECHAZO, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getEspecificaCausasRechazoDate().equals(specCauRecDate)))
					{
						dataBaseTransactions.setEspecificacionCausasRechazoCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_ESPECCAUREC, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getSolucionesDate().equals(solutionsDate)))
					{
						dataBaseTransactions.setSolucionesCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_SOLUCIONES, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getGruposDate().equals(groupsDate)))
					{
						dataBaseTransactions.setGruposCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_GRUPOS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getGruposClientesDate().equals(groupsCliDate)))
					{
						dataBaseTransactions.setGruposClientesCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_GRUPOSCLIENTES, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCodigos0Date().equals(codes0Date)))
					{
						dataBaseTransactions.setCodigosIntervencion0(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_CODIGOS0, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCodigos1Date().equals(codes1Date)))
					{
						dataBaseTransactions.setCodigosIntervencion1(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_CODIGOS1, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCodigos2Date().equals(codes2Date)))
					{
						dataBaseTransactions.setCodigosIntervencion2(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_CODIGOS2, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getClientesModDate().equals(clientsModDate)))
					{
						dataBaseTransactions.setClientesModelos(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_CLIENTESMOD, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getServSolDate().equals(servSolDate)))
					{
						dataBaseTransactions.setServiciosSoluciones(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_SERVSOL, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getTipoFallaDate().equals(failTypeDate)))
					{
						dataBaseTransactions.setTipoFalla(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_TIPOFALLA, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getEspTipoFallaDate().equals(specFailTypeDate)))
					{
						dataBaseTransactions.setEspecificaTipoFalla(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_ESPTIPOFALLA, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getServCauDate().equals(servCauDate)))
					{
						dataBaseTransactions.setServiciosCausas(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_SERVCAU, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getIngenierosDate().equals(engineersDate)))
					{
						dataBaseTransactions.setIngenieros(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), true, Constants.CATALOG_INGENIEROS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getDateConnectivity().equals(dateConnectivity)))
					{
						boolean saveClients = dataBaseTransactions.setConnectivityCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveClients, Constants.CATALOG_CONNECTIVITY, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getDateSoftware().equals(dateSoftware)))
					{
						boolean saveClients = dataBaseTransactions.setSoftwareCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveClients, Constants.CATALOG_SOFTWARE, true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getDateCausaRetiro().equals(dateCausaRetiro)))
					{
						boolean saveCausaRetiro = dataBaseTransactions.setCausaRetiro(sqliteHelper,notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(),saveCausaRetiro,Constants.CATALOG_CAUSAS_RETIRO,true);
					}
					updateIndex++;
					UpdateMainActivity();

					if (!(notificationsUpdateBean.getDateEtiquetas().equals(dateEtiquetas)))
					{
						boolean saveEtiquetas = dataBaseTransactions.setEtiquetas(sqliteHelper,notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(),saveEtiquetas,Constants.CATALOG_ETIQUETAS,true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getFailsFoundDate().equals(dateFailsFound)))
					{
						boolean saveFailsFound = dataBaseTransactions.setFailsFoundCatalog(sqliteHelper, notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveFailsFound, Constants.CATALOG_FAILS_FOUNDS, true);
					}
					updateIndex++;
					UpdateMainActivity();

					/*
					* CAMBIO 30/01/2018 JDOS*/
					if(!(notificationsUpdateBean.getCalidadBilleteDate().equals(dateTicketQuality))){
						boolean saveCalidadBillete = dataBaseTransactions.setCalidadBillete(sqliteHelper,notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveCalidadBillete,Constants.CATALOG_CALIDAD_BILLETE,true);
					}

					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCondicionSiteDate().equals(dateSiteCondition))){
						boolean savecondicionSite = dataBaseTransactions.setCondicionSite(sqliteHelper,notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), savecondicionSite,Constants.CATALOG_CONDICION_SITE,true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCcalidadBilleteDate().equals(dateClientTicketQuality))){
						boolean saveclienteCalidadBillete = dataBaseTransactions.setClienteCalidadBillete(sqliteHelper,notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveclienteCalidadBillete,Constants.CATALOG_C_CALIDAD_BILLETE,true);
					}
					updateIndex++;
					UpdateMainActivity();

					if(!(notificationsUpdateBean.getCcondicionSiteDate().equals(dateClientSiteCondition))){
						boolean savecondicionSite = dataBaseTransactions.setClienteCondicionSite(sqliteHelper,notificationsUpdateBean);
						dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), savecondicionSite,Constants.CATALOG_CALIDAD_BILLETE,true);
					}
					updateIndex++;
					UpdateMainActivity();

					dataBaseTransactions.setClientesCambiosStatus(sqliteHelper);
					UpdateMainActivity();

					ValidateNeedNumberTask task = new ValidateNeedNumberTask(Integer.parseInt(id), this);
					task.execute("");
					UpdateMainActivity();

				}
				else
				{
					Message myMessage = new Message();
					Bundle resBundle = new Bundle();
					resBundle.putString("status", "SUCCESS");
					myMessage.obj=resBundle;

					HandlerDownload handlerDownload = new HandlerDownload();
					handlerDownload.sendMessage(myMessage);
				}
			}else if(notificationsUpdateBean.getConnStatus() == 403)
			{
				mMessage = "Última actualización falló: ";
			}
			else if(notificationsUpdateBean.getConnStatus() == 404)
			{
				mMessage = "Última actualización falló: ";
			}
			else if(notificationsUpdateBean.getConnStatus() == 408)
			{
				mMessage = "Última actualización falló: ";
			}
			else if (notificationsUpdateBean.getConnStatus() == 100)
			{
				mMessage = "Última actualización falló: ";
			}
			else
			{
				mMessage = "Última actualización falló: ";
			}

			scheduleNextUpdate(Integer.valueOf(notificationsUpdateBean.getTime()));
			if (MicroformasApp.activity != null)
			{
				if (MicroformasApp.activity.getClass() == MainActivity.class)
				{
					MicroformasApp.activity.UpdateGUI();
				}
			}
		}
		catch(Exception ex)
		{   // Force logout user gets an exception
			Log.d("Microformas", "GetUpdateException= " + ex.getMessage());
		}
		finally
		{
			isUpdating = false;
		}
	}

	public GetUpdatesService(String name)
	{
		super("GetUpdatesService");
	}

	public GetUpdatesService()
	{
		super(null);
	}

	private void scheduleNextUpdate(int waitingTime)
	{
		Intent intent = new Intent(MicroformasApp.getAppContext(), UpdateScheduler.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(MicroformasApp.getAppContext(),
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// The update frequency should often be user configurable.  This is not.
		long currentTimeMillis = System.currentTimeMillis();
		long nextUpdateTimeMillis = currentTimeMillis + (waitingTime * 1000);
		Time nextUpdateTime = new Time();
		nextUpdateTime.set(nextUpdateTimeMillis);

		AlarmManager alarmManager = (AlarmManager) MicroformasApp.getAppContext().getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);

		Date currentDate = new Date();
		Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.PREF_CONFIG_USER,Context.MODE_PRIVATE );
		Editor edit = sharedPreferences.edit();
		edit.putString("UpdateMessage", mMessage + formatter.format(currentDate));
		edit.commit();
	}

	public static void UpdateMainActivity()
	{
		if (MicroformasApp.activity != null)
		{
			if (MicroformasApp.activity.getClass() == MainActivity.class)
			{
				MicroformasApp.activity.UpdateGUI();
			}
		}
	}

	public void sendUpdateNotification(int type)
	{
        /*

        NotificationCompat.Builder b=new NotificationCompat.Builder(this);

    b.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);

    if (e == null) {
      b.setContentTitle(getString(R.string.download_complete))
       .setContentText(getString(R.string.fun))
       .setSmallIcon(android.R.drawable.stat_sys_download_done)
       .setTicker(getString(R.string.download_complete));

      Intent outbound=new Intent(Intent.ACTION_VIEW);

      outbound.setDataAndType(Uri.fromFile(output), mimeType);

      b.setContentIntent(PendingIntent.getActivity(this, 0, outbound, 0));
    }
    else {
      b.setContentTitle(getString(R.string.exception))
       .setContentText(e.getMessage())
       .setSmallIcon(android.R.drawable.stat_notify_error)
       .setTicker(getString(R.string.exception));
    }

    NotificationManager mgr=
        (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

    mgr.notify(NOTIFY_ID, b.build());

        * */

		//NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		String message = (type == 0) ?
				"Se ha encontrado una nueva solicitud" : "Se ha encontrado un nuevo documento";

		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();

//		Notification notification = new Notification(icon, message, when);
//		notification.flags |= Notification.FLAG_AUTO_CANCEL;
//		notification.defaults |= Notification.DEFAULT_SOUND;
//		try
//        {
//			notification.defaults |= Notification.DEFAULT_VIBRATE;
//		}
//        catch(Exception ex)
//        {
//            Log.d("Microformas", ex.getMessage());
//        }

		CharSequence contentTitle = "Actualización MICROFORMAS";

		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();

		String[] campos = new String[] {SQLiteHelper.REQUESTS_ID_REQUEST};
		ArrayList<String> idRequest = new ArrayList<String>();

		String[] args;
		args = new String[] {String.valueOf(Constants.DATABASE_NUEVAS)};

		//Adquiere la cantidad de registros que tiene un módulo
		Cursor cursor = (type == 0) ?
				db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=?", args, null, null, null)
				: db.query(SQLiteHelper.DOCUMENTS_DB_NAME, campos, null, null, null, null, null);

		try
		{
			if (cursor != null)
			{
				if  (cursor.moveToFirst())
				{
					do
					{
						idRequest.add(cursor.getString(0));
					}while (cursor.moveToNext());
				}
			}
		}
		catch(Exception ex)
		{
			Log.d("Microformas", ex.getMessage());
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}

		db.close();

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
		notificationBuilder.setAutoCancel(true);
		notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
		notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
		notificationBuilder.setContentTitle(contentTitle);
		notificationBuilder.setContentText(message);

		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Intent notificationIntent = new Intent(this, RequestDetailActivity.class);

		if(type == 0)
		{
			if(idRequest.size() > 0) {
				notificationIntent.putExtra("idRequest", idRequest.get(0));
				notificationIntent.putExtra("type", Constants.DATABASE_ABIERTAS);
			}
		}
		else
		{
			notificationIntent = new Intent(this, RequestDetailActivity.class);
			notificationIntent.putExtra("idRequest", idRequest);
			notificationIntent.putExtra("type", Constants.DATABASE_DOCUMENTS);
		}
		if(idRequest.size()>0){
			notificationBuilder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0));
			notificationManager.notify(1, notificationBuilder.build());
		}
	}

	//Forzar Actualizacion de la app
	private static class HandlerDownload extends Handler
	{
		@Override
		public void handleMessage(Message msg) {
			HTTPConnections.VerifyServerMode(MicroformasApp.getAppContext());
			String url = Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE) ?
					"http://artefactoestudio.com/downloads/Microformas.apk"
					: "http://artefactoestudio.com/downloads/MicroformasTest.apk";

			Toast.makeText(MicroformasApp.getAppContext(),
					"Se ha encontrado una nueva versión. Favor de actualizar", Toast.LENGTH_LONG).show();

			goToURL(url);
		}

		private static  void goToURL(String url)
		{
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MicroformasApp.getAppContext().startActivity(i);
		}
	}

	/*
	AGREGAR VALIDACION PARA OBTENER SI EL USUARIO YA AGREGO SU TELEFONO
	SI NO LO HA HECHO ACTIVAR ALERTDIALOG
	 */

	public static boolean isUpdating = false;
	public static int updateIndex = 0;

	private Context mContext = MicroformasApp.getAppContext();
	private String mMessage;
	private int neddPhoneNumber;
}