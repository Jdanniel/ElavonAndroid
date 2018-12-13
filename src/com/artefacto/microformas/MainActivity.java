package com.artefacto.microformas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.adapters.MainAdapter;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.UpdatePhoneNumberTask;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MFActivity
{

	int nuevasNumber;
	int abiertasNumber;
	int cerradasNumber;
	int pendientesNumber;
	int unidadesNumber;
	int bandejaNumber;
	int mActualizacionCount;
	int DocumentsNumber;
	
	String[] notificationsNuevas;
    String[] notificationsAbiertas;
    String[] notificationsCerradas;
    String[] notificationsPendientes;
    String[] notificationsUnidades;
    String[] notificationsBandeja;
    String[] notificationsActualizacion;
    String[] notificationsDocuments;
        
    String[] servicioNuevas;
    String[] servicioAbiertas;
    String[] servicioCerradas;	
    String[] servicioPendientes;
    String[] servicioUnidades;
    String[] servicioBandeja;
    String[] servicioActualizacion;
    String[] servicioDocuments;
        
    int[] nuevasLight;
    int[] abiertasLight;
    int[] cerradasLight;
    int[] pendientesLight;
	
    int documentText;
	String enteroDocumentos;
	
	public static int scrollOffset = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        documentText = R.string.documents_pendiente_text -1 ;
        UpdateGUI();

	    if (!MicroformasApp.firstUpdate) {
			//Se manda a llamar al servicio, debe activarse cada 10 minutos
			Intent service = new Intent(this, GetUpdatesService.class);
			startService(service);
			MicroformasApp.firstUpdate = true;
		}
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MicroformasApp.activity = this;
        UpdateGUI();
        if (MicroformasApp.needPhoneNumber == 0){
            getPhoneNumber();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MicroformasApp.activity = null;
    }
    
    public void UpdateGUI()
    {   //Crea la conexón a base de datos, para adquirir la cantidad de notificaciones y las 3
        //solicitudes principales en cada una
        SQLiteHelper 	sqliteHelper = new SQLiteHelper(MainActivity.this, null);
		SQLiteDatabase 	db = sqliteHelper.getWritableDB();
		String[] campos = new String[] {SQLiteHelper.CATALOGS_NUMBER};
		String[] args0 	= new String[] {String.valueOf(Constants.DATABASE_NUEVAS)};
		String[] args1 	= new String[] {String.valueOf(Constants.DATABASE_ABIERTAS)};
		//String[] args2 	= new String[] {String.valueOf(Constants.DATABASE_CERRADAS)};
		String[] args3 	= new String[] {String.valueOf(Constants.DATABASE_PENDIENTES)};
		
		nuevasNumber 		= 0;
		abiertasNumber 		= 0;
		cerradasNumber 		= 0;
		pendientesNumber	= 0;
		unidadesNumber		= 0;
		bandejaNumber		= 0;
		mActualizacionCount = 0;
		DocumentsNumber		= 0;
		 
		Cursor nuevasCursor  	= db.query(SQLiteHelper.CATALOGS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=?",  args0, null, null, null);
		Cursor abiertasCursor  	= db.query(SQLiteHelper.CATALOGS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=?",  args1, null, null, null);
		//Cursor cerradasCursor  	= db.query(SQLiteHelper.CATALOGS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=?",  args2, null, null, null);
		Cursor cerradasCursor  	= db.rawQuery("select count(*) from requests where id_catalog = 2 AND (v_prefacturacion<0 OR v_prefacturacion >4)",null);
		Cursor pendientesCursor	= db.query(SQLiteHelper.CATALOGS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=?",  args3, null, null, null);
		Cursor UnidadesCursor 	= db.rawQuery("Select Count(*) from " + SQLiteHelper.UNIDAD_DB_NAME, null);
		Cursor documentCursor	= db.rawQuery("select count(*) from requests where id_catalog = 2 AND v_prefacturacion>0 AND v_prefacturacion <5",null);

		ArrayList<String> pendings = AsyncQueue.getList();
		
		//Se van a asignar los valores para cantidad de solicitudes
		if (nuevasCursor.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do{
				if(nuevasCursor.getString(0).equals("") || nuevasCursor.getString(0) == null)
					nuevasNumber = 0;
				else
					nuevasNumber = Integer.valueOf(nuevasCursor.getString(0));
			}while(nuevasCursor.moveToNext());
		}
		nuevasCursor.close();
		 
		if (abiertasCursor.moveToFirst()) {
			do{
				if(abiertasCursor.getString(0).equals("") || abiertasCursor.getString(0) == null)
					abiertasNumber = 0;
				else
					abiertasNumber = Integer.valueOf(abiertasCursor.getString(0));
		    }while(abiertasCursor.moveToNext());
		}
		abiertasCursor.close();

		if (cerradasCursor.moveToFirst()) {
			do{
				if(cerradasCursor.getString(0).equals("") || cerradasCursor.getString(0) == null)
					cerradasNumber = 0;
				else
					cerradasNumber = Integer.valueOf(cerradasCursor.getString(0));
		    }while(cerradasCursor.moveToNext());
		}
		cerradasCursor.close();

		if (pendientesCursor.moveToFirst()) {
			do{
				if(pendientesCursor.getString(0).equals("") || pendientesCursor.getString(0) == null)
					pendientesNumber = 0;
				else
					pendientesNumber = Integer.valueOf(pendientesCursor.getString(0));
		    }while(pendientesCursor.moveToNext());
		}
		pendientesCursor.close();
		
		if (UnidadesCursor.moveToFirst()) {
			do{
				if(UnidadesCursor.getString(0).equals("") || UnidadesCursor.getString(0) == null)
					unidadesNumber = 0;
				else
					unidadesNumber = Integer.valueOf(UnidadesCursor.getString(0));
		    }while(UnidadesCursor.moveToNext());
		}
		UnidadesCursor.close();
		
		if (documentCursor.moveToFirst()) {
			do{
				if(documentCursor.getString(0).equals("") || documentCursor.getString(0) == null)
					DocumentsNumber = 0;
				else
					DocumentsNumber = Integer.valueOf(documentCursor.getString(0));

				//TODO guardar en hash en sharedreferences
				//Status de prefacturacion (buscar que hace)
		    }while(documentCursor.moveToNext());
		}
		documentCursor.close();
		
		bandejaNumber = pendings.size();
		
		notificationsNuevas 		= new String[2];
	    notificationsAbiertas 		= new String[2];
	    notificationsCerradas 		= new String[2];
	    notificationsPendientes 	= new String[2];
	    notificationsUnidades 		= new String[2];
	    notificationsBandeja 		= new String[2];
	    notificationsActualizacion  = new String[2];
	    notificationsDocuments		= new String[2];
	        
	    servicioNuevas	 			= new String[2];
	    servicioAbiertas 			= new String[2];
	    servicioCerradas 			= new String[2];
	    servicioPendientes	 		= new String[2];
	    servicioUnidades 			= new String[2];
	    servicioBandeja 			= new String[2];
	    servicioActualizacion		= new String[2];
	    servicioDocuments			= new String[2];
	        
	    nuevasLight					= new int[3];
	    abiertasLight				= new int[3];
	    cerradasLight				= new int[3];
	    pendientesLight				= new int[3];
	    int solicitudesLimit		= 0;
	    int horasGarantia			= 0;
	    int horasAtencion			= 0;

	        //Crea Cadena para nuevas
	    if(nuevasNumber <= 2){
	      	solicitudesLimit = nuevasNumber;
	    }
	    else{
			solicitudesLimit = 2;
		}

	    campos 				= new String[] {SQLiteHelper.REQUESTS_DESC_CLIENTE, 
	       									SQLiteHelper.REQUESTS_DESC_SERVICIO, 
	       									SQLiteHelper.REQUESTS_HORAS_GARANTIA,
	       									SQLiteHelper.REQUESTS_HORAS_ATENCION};
	    String[]	args 	= new String[] {String.valueOf(Constants.DATABASE_NUEVAS)};
		Cursor 		c		= db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=?",  args, null, null, null);
			
		int i = 0;

		//Se van a asignar los valores para cantidad de solicitudes
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do{
				if( i < solicitudesLimit){
					notificationsNuevas[i] = c.getString(0);
				 	servicioNuevas[i]      = c.getString(1);

				 	if(c.getString(2).equals("") || c.getString(2) == null) 
				 		horasGarantia = 0;
				 	else
				 		horasGarantia			= Integer.valueOf(c.getString(2));
					 	 
				 	if(c.getString(3).equals("") || c.getString(3) == null) 
				 		horasAtencion = 0;
				 	else
				 		horasAtencion			= Integer.valueOf(c.getString(3));
					 	 
				 	nuevasLight[i] =  horasGarantia - horasAtencion;
				 	i++;
				}
			}while(c.moveToNext());
		}
		c.close();
			 
		//Crea Cadena para abiertas
		if(abiertasNumber <= 2)
			solicitudesLimit = abiertasNumber;
		else
			solicitudesLimit = 2;
		        
		args 	= new String[] {String.valueOf(Constants.DATABASE_ABIERTAS)};
		c		= db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=?",  args, null, null, null);
			
		i = 0;
		//Se van a asignar los valores para cantidad de solicitudes
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do{
				if( i < solicitudesLimit){
					notificationsAbiertas[i] = c.getString(0);
				 	servicioAbiertas[i]      = c.getString(1);
					 	 
				 	if(c.getString(2).equals("") || c.getString(2) == null) 
				 		horasGarantia = 0;
				 	else
				 		horasGarantia			= Integer.valueOf(c.getString(2));
					 	 
				 	if(c.getString(3).equals("") || c.getString(3) == null) 
				 		horasAtencion = 0;
				 	else
				 		horasAtencion			= Integer.valueOf(c.getString(3));
					 	 
				 	abiertasLight[i] =  horasGarantia - horasAtencion;
				 	i++;
				}
			}while(c.moveToNext());
		}
		c.close();
			 
		//Crea Cadena para cerradas
		if(cerradasNumber <= 2)
			solicitudesLimit = cerradasNumber;
		else
			solicitudesLimit = 2;
		        
		args 	= new String[] {String.valueOf(Constants.DATABASE_CERRADAS)};
		c		= db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=? AND (v_prefacturacion<0 OR v_prefacturacion >4)",  args, null, null, null);

		i = 0;
		//Se van a asignar los valores para cantidad de solicitudes
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do{
				if( i < solicitudesLimit){
					notificationsCerradas[i] = c.getString(0);
				 	servicioCerradas[i]      = c.getString(1);
					     
				 	if(c.getString(2).equals("") || c.getString(2) == null) 
				 		horasGarantia = 0;
				 	else
				 		horasGarantia			= Integer.valueOf(c.getString(2));
				 	 
				 	if(c.getString(3).equals("") || c.getString(3) == null) 
				 		horasAtencion = 0;
				 	else
				 		horasAtencion			= Integer.valueOf(c.getString(3));
					 	 
				 	cerradasLight[i] =  horasGarantia - horasAtencion;
				 	i++;
				}
			}while(c.moveToNext());
		}
		c.close();
			 
		//Crea Cadena para pendientes
		if(pendientesNumber <= 2)
			solicitudesLimit = pendientesNumber;
		else
			solicitudesLimit = 2;

		args 	= new String[] {String.valueOf(Constants.DATABASE_PENDIENTES)};
		c		= db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=?",  args, null, null, null);

		i = 0;
		//Se van a asignar los valores para cantidad de solicitudes
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do{
				if( i < solicitudesLimit){
					notificationsPendientes[i] = c.getString(0);
				 	servicioPendientes[i]      = c.getString(1);
				     
				 	if(c.getString(2).equals("") || c.getString(2) == null) 
				 		horasGarantia = 0;
				 	else
				 		horasGarantia			= Integer.valueOf(c.getString(2));
				 	 
				 	if(c.getString(3).equals("") || c.getString(3) == null) 
				 		horasAtencion = 0;
				 	else
				 		horasAtencion			= Integer.valueOf(c.getString(3));
					 	 
				 	pendientesLight[i] =  horasGarantia - horasAtencion;
				 	i++;
				}
			}while(c.moveToNext());
		}
		c.close();
		
		//Crea Cadena para unidades
		if (unidadesNumber <= 2)
			solicitudesLimit = unidadesNumber;
		else
			solicitudesLimit = 2;
		
		campos 				= new String[] {SQLiteHelper.UNIDAD_DESC_CLIENTE,
											SQLiteHelper.UNIDAD_DESC_MARCA,
											SQLiteHelper.UNIDAD_DESC_MODELO };
		
		c		= db.query(SQLiteHelper.UNIDAD_DB_NAME, campos, null,  null, null, null, null);
		
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			for (int j=0;j<solicitudesLimit;j++){
				notificationsUnidades[j] = c.getString(0);
			 	servicioUnidades[j]      = c.getString(1) + " " + c.getString(2);
			 	c.moveToNext();
			}
		}
		c.close();
		//Crea Cadena para documentos
		if(DocumentsNumber <= 2)
			solicitudesLimit = DocumentsNumber;
		else
			solicitudesLimit = 2;
		campos 		= new String[] {SQLiteHelper.REQUESTS_DESC_CLIENTE, 
									SQLiteHelper.REQUESTS_PREFACTURACION};
		args 	= new String[] {String.valueOf(Constants.DATABASE_CERRADAS)};
		c		= db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.CATALOGS_ID_CATALOG + "=? AND v_prefacturacion>0 AND v_prefacturacion <5",  args, null, null, null);
		i = 0;
		if (c.moveToFirst())
        {   // Se van a asignar los valores para cantidad de solicitudes
            // Recorremos el cursor hasta que no haya más registros
			do
            {
				if( i < solicitudesLimit)
                {
					/****************************Inicio 02/08/2017**/
					switch (c.getString(1)){
						case "1":
							enteroDocumentos = "1";
							break;
						case "2":
							enteroDocumentos = "3";
							break;
						case "3":
							enteroDocumentos = "2";
							break;
					}
					/***********************************************/
					notificationsDocuments[i] = c.getString(0);
				 	servicioDocuments[i]      = getString(documentText + Integer.parseInt(enteroDocumentos));
				 	i++;
				}
			}while(c.moveToNext());
		}
		c.close();

		db.close();
		
		if (bandejaNumber <= 2){
			solicitudesLimit = bandejaNumber;
		}
		else{
			solicitudesLimit = 2;
		}
		for (int j=0;j<solicitudesLimit;j++){
			/*String[] frags = pendings.get(j).split(Constants.ASYNC_SEPARATOR_PARAMETER);
			*/
			String[] desc = AsyncQueue.getDescription(pendings.get(j));
			notificationsBandeja[j] = desc[0];
		 	servicioBandeja[j]      = desc[1];
		}

        mActualizacionCount = 0;
        if(GetUpdatesService.isUpdating)
        {
            int updateIndex = GetUpdatesService.updateIndex;
            int currentIndex = 0;
            for (int j = 0; j < 2; j++)
            {
                currentIndex = updateIndex + j;
                if(currentIndex >= UPDATE_STATUS.length)
                {
                    break;
                }

                notificationsActualizacion[j] = UPDATE_STATUS[currentIndex];
                servicioActualizacion[j] = (j == 0) ? "Actualizando..." : "Pendiente";
            }

            mActualizacionCount = UPDATE_STATUS.length - currentIndex;
        }
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                setGUIValues();
            }
        });
    }

    public void setGUIValues()
    {
        setContentView(R.layout.activity_main);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.main_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.main_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_main));
        setSupportActionBar(toolbarInventory);

        SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_MAIN_ACTIVITY);
        editor.commit();

        ArrayList<MainAdapter.Dyanamic> listItems = new ArrayList<MainAdapter.Dyanamic>();
        listItems.add(dynamicNews);
        listItems.add(dynamicOpened);
        listItems.add(dynamicDocs);
        listItems.add(dynamicClosed);
        listItems.add(dynamicPending);
        listItems.add(dynamicUnits);
        listItems.add(dynamicInbox);
        listItems.add(dynamicUpdate);

        dynamicNews.count = nuevasNumber;
        dynamicNews.notifications = notificationsNuevas;
        dynamicNews.services = servicioNuevas;
        dynamicNews.lights = nuevasLight;
        dynamicNews.litghsOn = true;
        dynamicNews.title = getString(R.string.main_news_text).toUpperCase();

        dynamicOpened.count = abiertasNumber;
        dynamicOpened.notifications = notificationsAbiertas;
        dynamicOpened.services = servicioAbiertas;
        dynamicOpened.lights = abiertasLight;
        dynamicOpened.litghsOn = true;
        dynamicOpened.title = getString(R.string.main_opened_text).toUpperCase();

        dynamicClosed.count = cerradasNumber;
        dynamicClosed.notifications = notificationsCerradas;
        dynamicClosed.services = servicioCerradas;
        dynamicClosed.lights = cerradasLight;
        dynamicClosed.litghsOn = true;
        dynamicClosed.title = getString(R.string.main_closed_text).toUpperCase();

        dynamicPending.count = pendientesNumber;
        dynamicPending.notifications = notificationsPendientes;
        dynamicPending.services = servicioPendientes;
        dynamicPending.lights = pendientesLight;
        dynamicPending.litghsOn = true;
        dynamicPending.title = getString(R.string.main_pending_text).toUpperCase();

        dynamicUnits.count = unidadesNumber;
        dynamicUnits.notifications = notificationsUnidades;
        dynamicUnits.services = servicioUnidades;
        dynamicUnits.lights = pendientesLight;
        dynamicUnits.litghsOn = false;
        dynamicUnits.title = getString(R.string.main_units_text).toUpperCase();

        dynamicInbox.count = bandejaNumber;
        dynamicInbox.notifications = notificationsBandeja;
        dynamicInbox.services = servicioBandeja;
        dynamicInbox.lights = pendientesLight;
        dynamicInbox.litghsOn = false;
        dynamicInbox.title = getString(R.string.main_inbox_text).toUpperCase();

        dynamicUpdate.count = mActualizacionCount;
        dynamicUpdate.notifications = notificationsActualizacion;
        dynamicUpdate.services = servicioActualizacion;
        dynamicUpdate.lights = pendientesLight;
        dynamicUpdate.litghsOn = false;
        dynamicUpdate.title = getString(R.string.main_update_text).toUpperCase();

        dynamicDocs.count = DocumentsNumber;
        dynamicDocs.notifications = notificationsDocuments;
        dynamicDocs.services = servicioDocuments;
        dynamicDocs.lights = pendientesLight;
        dynamicDocs.litghsOn = false;
        dynamicDocs.title = getString(R.string.main_docs_text).toUpperCase();

	    String str = sharedPreferences.getString("UpdateMessage", "");
	    TextView text = (TextView)findViewById(R.id.UpdateMessage);
		text.setText(str);

        GridView mGridView = (GridView) this.findViewById(R.id.main_grid);
        mGridView.setAdapter(new MainAdapter(MainActivity.this, listItems));
        mGridView.setOnItemClickListener(onMainItemClicked);
	}

    GridView.OnItemClickListener onMainItemClicked = new GridView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            switch(i)
            {
                case 0: // News
                    goToNextScreen(getString(R.string.main_news_text));
                    break;

                case 1: // Opened
                    goToNextScreen(getString(R.string.main_opened_text));
                    break;

                case 2: // Docs
                    goToNextScreen(getString(R.string.main_docs_text));
                    break;

                case 3: // Closed
                    goToNextScreen(getString(R.string.main_closed_text));
                    break;

                case 4: // Waiting
                    goToNextScreen(getString(R.string.main_pending_text));
                    break;

                case 5: // Units
                    goToNextScreen(getString(R.string.main_units_text));
                    break;

                case 6: // Inbox
                    goToNextScreen(getString(R.string.main_inbox_text));
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	menu.add(Menu.NONE, 1, Menu.NONE, R.string.menu_logout);
    	menu.add(Menu.NONE, 2, Menu.NONE, R.string.menu_update);
    	menu.add(Menu.NONE, 3, Menu.NONE, R.string.menu_version);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case 1: 
            	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            	builder.setTitle(R.string.menu_logout)
                   .setMessage(R.string.menu_logout_question)
                   .setPositiveButton(R.string.base_yes, closeClickListener)
                   .setNegativeButton(R.string.base_no, closeClickListener).show();
                return true;

            case 2: 
            	builder = new AlertDialog.Builder(this);
            	builder.setTitle(R.string.menu_update)
                   .setMessage(R.string.menu_update_question)
                   .setPositiveButton(R.string.base_yes, updateClickListener)
                   .setNegativeButton(R.string.base_no, updateClickListener).show();
                return true;

            case 3:
				HTTPConnections.VerifyServerMode(MainActivity.this);

            	builder = new AlertDialog.Builder(this);
                builder.setTitle(Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE) ?
                    R.string.version_title_prod : R.string.version_title_test)
                .setMessage(Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE) ?
                    R.string.version_value_prod : R.string.version_value_test)
                .setPositiveButton("OK", null);

            	AlertDialog dialog = builder.show();
            	TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
            	messageText.setGravity(Gravity.CENTER);
            	dialog.show();
            	return true;

            default:
                return super.onOptionsItemSelected(item);
    	}
    }
    
    DialogInterface.OnClickListener closeClickListener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    deleteAllData();
                    goToLoginActivity();
                    break;
            }
        }
    };
    
    public void deleteAllData()
    {   //Borramos el SharedPreferences
    	SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
    	Editor editor = sharedPreferences.edit();
    	editor.clear();
    	editor.commit();
    	
    	//Borramos la base de datos
		getApplicationContext().deleteDatabase(SQLiteHelper.DATABASE_NAME);
    }
    
    DialogInterface.OnClickListener updateClickListener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
            		Toast.makeText(MainActivity.this, "Actualizando...", Toast.LENGTH_SHORT).show();
	            	Intent service = new Intent(MicroformasApp.getAppContext(), GetUpdatesService.class);
	        		startService(service);
                break;
            }
        }
    };
    
    public void goToLoginActivity()
    {
    	Intent intent = new Intent(this, LoginActivity.class);
    	SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
    	Editor editor = sharedPreferences.edit();
    	editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_LOGIN_ACTIVITY);
    	editor.putString(Constants.PREF_USER_ID, "");
    	editor.commit();
    	startActivity(intent);
    }
	
	public void goToNextScreen(String screen)
    {
        Intent 	intent = null;

    	if(screen.equals(getString(R.string.main_news_text)))
        {
            intent	= new Intent(this, RequestListActivity.class);
            intent.putExtra("type", Constants.DATABASE_NUEVAS);
    	}
    	else if(screen.equals(getString(R.string.main_opened_text)))
        {
            intent	= new Intent(this, RequestListActivity.class);
    		intent.putExtra("type", Constants.DATABASE_ABIERTAS);
    	}
    	else if(screen.equals(getString(R.string.main_closed_text)))
        {
            intent	= new Intent(this, RequestListActivity.class);
    		intent.putExtra("type", Constants.DATABASE_CERRADAS);
    	}
    	else if(screen.equals(getString(R.string.main_pending_text)))
        {
            intent	= new Intent(this, RequestListActivity.class);
    		intent.putExtra("type", Constants.DATABASE_PENDIENTES);
    	}
    	else if(screen.equals(getString(R.string.main_docs_text)))
        {
            intent	= new Intent(this, RequestListActivity.class);
    		intent.putExtra("type", Constants.DATABASE_DOCUMENTS);
    	}
        else if(screen.equals(getString(R.string.main_units_text)))
        {
            intent = new Intent(MainActivity.this, InventoryActivity.class);
        }
        else if(screen.equals(getString(R.string.main_inbox_text)))
        {
            intent = new Intent(MainActivity.this, PendingsListActivity.class);
            startActivity(intent);
        }

        if(intent != null)
        {
            startActivity(intent);
        }
    }

	public boolean onKeyDown(int keyCode, KeyEvent event)
    {
	    if (keyCode == KeyEvent.KEYCODE_BACK )
        {
	        return false;
	    }

        return super.onKeyDown(keyCode, event);
	}

	private void getPhoneNumber(){
		SharedPreferences sharedPreferences = MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		final String id =   sharedPreferences.getString(Constants.PREF_USER_ID, "");
		final EditText taskEditText = new EditText(this);
		taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		taskEditText.setWidth(100);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Agregar Número de Teléfono");
		dialog.setMessage("Favor de agregar el número de teléfono");
		dialog.setView(taskEditText);
		dialog.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String PhoneNumber = String.valueOf(taskEditText.getText());
				UpdatePhoneNumberTask taskPhone = new UpdatePhoneNumberTask(MainActivity.this);
				taskPhone.execute(PhoneNumber,id);
				MicroformasApp.needPhoneNumber = 1;
			}
		});
		dialog.setNegativeButton("Luego", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.create();
		dialog.show();
	}

    private static String[] UPDATE_STATUS = new String[]
    {
        "Nuevas",
        "Abiertas",
        "Cerradas",
        "Pendientes",
        "Unidades",
		"Inv. Insumos",
        "Direcciones",
        "Envios",
        "Pendientes",
        "Recepciones",
        "Status",
        "Productos",
        "Clientes",
        "Cambios",
        "Viaticos",
        "Refacciones",
        "Insumos",
        "Almacenes",
        "Marcas",
        "Modelo Refacciones",
        "Modelos",
        "Servicios",
        "Causas",
        "Causa-Rechazo",
        "Especificación Causa-Rechazo",
        "Soluciones",
        "Grupos",
        "Grupos-Clientes",
        "Codigos 0",
        "Codigos 1",
        "Codigos 2",
        "Clientes-Modelos",
        "Servicios-Soluciones",
        "Tipo-Falla",
        "Especificación Tipo-Falla",
        "Servicio-Causa",
        "Ingenieros",
		"Conectividad",
		"Software",
		"Causas-Retiro",
		"Etiquetas",
		"Fallas Encontradas",
		"Calidad Billete",
		"Condicion Site",
		"Cliente Calidad Billete",
		"Cliente Condicion Site"
	};

    MainAdapter.Dyanamic dynamicNews = new MainAdapter.Dyanamic();
    MainAdapter.Dyanamic dynamicOpened = new MainAdapter.Dyanamic();
    MainAdapter.Dyanamic dynamicClosed = new MainAdapter.Dyanamic();
    MainAdapter.Dyanamic dynamicPending = new MainAdapter.Dyanamic();
    MainAdapter.Dyanamic dynamicUnits = new MainAdapter.Dyanamic();
    MainAdapter.Dyanamic dynamicInbox = new MainAdapter.Dyanamic();
    MainAdapter.Dyanamic dynamicUpdate = new MainAdapter.Dyanamic();
    MainAdapter.Dyanamic dynamicDocs = new MainAdapter.Dyanamic();
}