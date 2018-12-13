package com.artefacto.microformas;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.EnviarTerminalTask;
import com.artefacto.microformas.uicomponents.DateButton;

public class TerminalActivity extends AppCompatActivity
{ 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminal);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.terminal_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.terminal_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_terminal));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		layoutItems = (LinearLayout) this.findViewById(R.id.terminal_listitems);
		layoutIsumos = (LinearLayout) this.findViewById(R.id.terminal_insumo_listitems);
		
		direccionesSpinner 	= (Spinner)findViewById(R.id.terminalDireccionesSpinner);
		
		buttonAlmacen = (Button) this.findViewById(R.id.terminalAlmacenButton);
		buttonAlmacen.setOnClickListener(onStorageClicked);
		
		textAlmacen = (TextView) this.findViewById(R.id.refaccion_almacen_content);
		
		tipoServicioButton 	= (Button)findViewById(R.id.terminalTipoServicioButton);
        tipoServicioButton.setOnClickListener(tipoServicioOnClickListener);

		urgenciaButton = (Button)findViewById(R.id.terminalUrgenciaButton);
        urgenciaButton.setOnClickListener(urgenciaOnClickListener);

		enviarButton = (Button)findViewById(R.id.terminalEnviarButton);
        enviarButton.setOnClickListener(onChangePendingStatusClicked);
		
		notasEditText = (EditText)findViewById(R.id.terminalNotasEditText);
		
		fechaCompromisoDateButton 	= (DateButton)findViewById(R.id.terminalFechaCompromisoDateButton);
		
		// ADD TERMINAL
		Button buttonAddTerminal = (Button) this.findViewById(R.id.terminal_button_search);
		buttonAddTerminal.setOnClickListener(onSearchClicked);
		
		Button buttonAddInsumo 	= (Button) this.findViewById(R.id.terminal_insumo_button_search);
		buttonAddInsumo.setOnClickListener(onSearchInsumoClicked);
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		idAR = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
		idClient = sharedPreferences.getString(Constants.INSUMO_INSUMOS_ID_CLIENTE, idClient);
        idProduct = sharedPreferences.getString(Constants.INSUMO_INSUMOS_ID_PRODUCT, idProduct);

		SQLiteHelper sqliteHelper = new SQLiteHelper(getApplicationContext(), null);
	    SQLiteDatabase db = sqliteHelper.getReadableDB();

//        Cursor cursor = db.rawQuery("SELECT "  + SQLiteHelper.DIRECCIONES_ID_DIRECCION 	+ ",", null);

	    //Adquiere los almacenes
        Cursor cursor = db.rawQuery("SELECT "  + SQLiteHelper.DIRECCIONES_ID_DIRECCION 	+ ","
				   						  + SQLiteHelper.DIRECCIONES_DIRECCION 		+ ","
		    							  + SQLiteHelper.DIRECCIONES_COLONIA 		+ ","
		    							  + SQLiteHelper.DIRECCIONES_POBLACION		+ ","
		    							  + SQLiteHelper.DIRECCIONES_ESTADO
		    				   + " FROM " + SQLiteHelper.DIRECCIONES_DB_NAME + " ORDER BY " + sqliteHelper.DIRECCIONES_IS_DEFAULT + " ASC", null);
		    
		descDireccionList 	= new ArrayList<>();
		idDireccionList 	= new ArrayList<>();
		
		try{
		   	if (cursor != null ) {
		   		if  (cursor.moveToFirst()) {
		   			do {
		   				String address = cursor.getString(0);
		   				String value = cursor.getString(1) + ", " + cursor.getString(2) + ". " + cursor.getString(3) + ", " + cursor.getString(4);
		   				
		   				idDireccionList.add(address);
		   				descDireccionList.add(value);
		    		} while (cursor.moveToNext());
		       	}
		    }
		}
		catch(Exception ex)
        {
            Log.d("Microformas", ex.getMessage());
        }

        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TERMINAL_ALMACENES_ID, "");
        editor.putString(Constants.TERMINAL_ALMACENES_DESC, "(sin información)");
        editor.commit();

        textAlmacen.setText(sharedPreferences.getString(Constants.TERMINAL_ALMACENES_DESC, "(sin información)"));

        idDireccionList.add("-1");
		//descDireccionList.add("DIRECCION DEL NEGOCIO");
		
		cursor.close();
		db.close();

		ArrayAdapter<String> direccionesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descDireccionList);
        direccionesAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
		direccionesSpinner.setAdapter(direccionesAdapter);
		
		Calendar cal = Calendar.getInstance();
        String dateFormat = cal.get(Calendar.YEAR) + "/" +  cal.get(Calendar.MONTH) + "/"
				+  cal.get(Calendar.DAY_OF_MONTH);
		fechaCompromisoDateButton.setText(dateFormat);
        
        fechaCompromisoDateButton.onDateSet(dp, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        fechaCompromisoDateButton.setOnClickListener(dateOnClickListener);
        
        itemsCatalog = new ArrayList<>();
        itemsViews = new ArrayList<>();
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == Activity.RESULT_OK)
		{
			String brandConnectivityId = "";
			String brandConnectivityDesc= "";
			String modelSoftwareId = "";
			String modelSoftwareDesc = "";
            String requestType = "";

            String idInsumo = "";
            String nameInsumo = "";

            String countCantidad = "";

			if(requestCode == REQUEST_CODE_SEARCH_TERMINAL_UNIT)
			{
                requestType = data.getStringExtra(Constants.TERMINAL_REQUEST_TYPE);

                brandConnectivityId = data.getStringExtra("BRAND_CONNECTIVITY_ID");
                brandConnectivityDesc = data.getStringExtra("BRAND_CONNECTIVITY_DESC");

                modelSoftwareId = data.getStringExtra("MODEL_SOFTWARE_ID");
                modelSoftwareDesc = data.getStringExtra("MODEL_SOFTWARE_DESC");

		        countCantidad = data.getStringExtra("TERMINAL_CANTIDAD");
			}
			else if(requestCode == REQUEST_CODE_SEARCH_TERMINAL_INSUMO)
			{
				idInsumo= data.getStringExtra("INSUMO_INSUMO_ID");
				
				nameInsumo = data.getStringExtra("INSUMO_INSUMO");
		        countCantidad = data.getStringExtra("INSUMO_CANTIDAD");
			}

            if((brandConnectivityDesc != null && modelSoftwareDesc != null && countCantidad != null)
                    || (nameInsumo != null && countCantidad != null))
            {
                if((!brandConnectivityDesc.equals("") && !modelSoftwareDesc.equals("") && !countCantidad.equals(""))
                        || (!nameInsumo.equals("") && !countCantidad.equals("")))
                {
                    RelativeLayout item = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_description, null, false);
                    item.setTag(requestCode + "_" + itemsCatalog.size());

                    LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    relativeParams.setMargins(0, 0, 0, 10);

                    TextView textModelo = (TextView) item.findViewById(R.id.terminal_text_model_appplication_content);

                    if(requestCode == REQUEST_CODE_SEARCH_TERMINAL_UNIT) // TERMINAL
                    {
                        TextView textMarca = (TextView) item.findViewById(R.id.terminal_text_brand_connectivity_content);
                        textMarca.setText(brandConnectivityDesc);
                        textModelo.setText(modelSoftwareDesc);

                        if(requestType.equals(REQUEST_TYPE_CONNECTIVITY))
                        {// CONECTIVIDAD & SOFTWARE
                            TextView titleConnectivity = (TextView) item.findViewById(R.id.terminal_text_brand_connectivity);
                            titleConnectivity.setText(getString(R.string.terminal_text_connectivity));

                            TextView titleSoftware = (TextView) item.findViewById(R.id.terminal_text_model_appplication);
                            titleSoftware.setText(getString(R.string.terminal_text_software));
                        }
                    }
                    else
                    {// INSUMO
                        RelativeLayout layoutMarca = (RelativeLayout) item.findViewById(R.id.layout_terminal_marca);
                        layoutMarca.setVisibility(View.GONE);
                        textModelo.setText(nameInsumo);
                    }

                    TextView textCantidad = (TextView) item.findViewById(R.id.terminalCantidadTextViewContent);

                    item.setOnLongClickListener(onItemLongClick);
                    textCantidad.setText(countCantidad);

                    EntityCatalog tempCatalog = new EntityCatalog();
                    tempCatalog.setCount(Integer.parseInt(countCantidad));
                    if(requestCode == REQUEST_CODE_SEARCH_TERMINAL_UNIT ||
                            requestCode == REQUEST_CODE_SEARCH_TERMINAL_CONNECTIVITY_SOFTWARE)
                    {
                        tempCatalog.setBrandConnectivityId(Integer.parseInt(brandConnectivityId));
                        tempCatalog.setModelSoftwareId(Integer.parseInt(modelSoftwareId));
                        tempCatalog.setType(requestType);
                    }
                    else
                    {
                        tempCatalog.setModelSoftwareId(Integer.parseInt(idInsumo));
                        tempCatalog.setType(REQUEST_TYPE_SUPPLY);
                        tempCatalog.setCount(Integer.parseInt(countCantidad));
                    }

                    itemsCatalog.add(tempCatalog);
                    if(requestCode == REQUEST_CODE_SEARCH_TERMINAL_UNIT)
                    {
                        layoutItems.addView(item, relativeParams);
                    }
                    else
                    {
                        layoutIsumos.addView(item, relativeParams);
                    }

                    itemsViews.add(item);
                }
            }
		}

        if(requestCode == REQUEST_CODE_SEARCH_TERMINAL_STORAGE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                idAlmacen = data.getStringExtra("list_id");
                nameAlmacen = data.getStringExtra("list_desc");
                textAlmacen.setText(nameAlmacen);
            }
        }
	}
	
	public OnClickListener onSearchClicked = new OnClickListener()
	{
		public void onClick(View v)
		{	
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
//			editor.putString(Constants.TERMINAL_MARCAS_ID, "");
//			editor.putString(Constants.TERMINAL_MARCAS_DESC, "(sin información)");
//			editor.putString(Constants.TERMINAL_MODELOS_ID, "");
//			editor.putString(Constants.TERMINAL_MODELOS_DESC, "(sin información)");
            editor.putString(Constants.TERMINAL_ID_PRODUCT, idProduct);
            editor.putString(Constants.TERMINAL_ID_CLIENT, idClient);
            editor.putString(Constants.TERMINAL_REQUEST_TYPE, idProduct.trim().equals("1") ?
                REQUEST_TYPE_CONNECTIVITY : REQUEST_TYPE_UNIT);
			editor.commit();
			
			Intent intent = new Intent(TerminalActivity.this, SearchingTerminalActivity.class);
			startActivityForResult(intent, TerminalActivity.REQUEST_CODE_SEARCH_TERMINAL_UNIT);
		}
	};
	
	public OnClickListener onSearchInsumoClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
//			editor.putString(Constants.INSUMO_ALMACENES_ID, "");
//			editor.putString(Constants.INSUMO_ALMACENES_DESC, "(sin información)");
//			editor.putString(Constants.INSUMO_INSUMOS_ID, "");
//			editor.putString(Constants.INSUMO_INSUMOS_DESC, "(sin información)");
			editor.putString(Constants.INSUMO_INSUMOS_ID_CLIENTE, idClient);
            editor.putString(Constants.INSUMO_INSUMOS_ID_PRODUCT, idProduct);
			editor.commit();
			
			Intent intent = new Intent(TerminalActivity.this, SearchingInsumoActivity.class);
//			intent.putExtra(SearchingInsumoActivity.EXTRA_CODE_SEARCH, REQUEST_CODE_SEARCH_TERMINAL_INSUMO);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, REQUEST_CODE_SEARCH_TERMINAL_INSUMO);
		}
	};
	
	public OnClickListener dateOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			fechaCompromisoDateButton = (DateButton) findViewById(R.id.terminalFechaCompromisoDateButton);
			fechaCompromisoDateButton.onClick(v);
		}
	};
	
	public OnClickListener tipoServicioOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			if(tipoServicioButton.getText().toString().equals("LOCAL")){
				tipoServicioButton.setText("FORANEO");
			}
			else{
				tipoServicioButton.setText("LOCAL");
			}
		}
	};
	
	public OnClickListener urgenciaOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			if(urgenciaButton.getText().toString().equals("BAJA")){
				urgenciaButton.setText("MEDIA");
			}
			else if(urgenciaButton.getText().toString().equals("MEDIA")){
				urgenciaButton.setText("ALTA");
			}
			else{
				urgenciaButton.setText("BAJA");
			}
		}
	};
	
	public OnClickListener onChangePendingStatusClicked = new OnClickListener()
	{
		public void onClick(View v)
        {
            final String[] kitData = verifyKitRequired();
            if(kitData != null)
            {   // NEED KIT DATA
                if(kitData[0].equals("1"))
                {
                    new AlertDialog.Builder(TerminalActivity.this)
                        .setTitle("")
                        .setMessage("¿Desea agregar kit de insumos?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                changePendingStatus(kitData[0], kitData[1]);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                changePendingStatus("", "");
                            }
                        }).show();
                }
                else
                {
                    changePendingStatus("", "");
                }
            }
            else
            {
                changePendingStatus("", "");
            }
		}
	};
	
	public String setNumericMonth(String month)
	{
		if(month.equals("ene."))
			month = "01";
		else if(month.equals("feb."))
			month = "02";
		else if(month.equals("mar."))
			month = "03";
		else if(month.equals("abr."))
			month = "04";
		else if(month.equals("may."))
			month = "05";
		else if(month.equals("jun."))
			month = "06";
		else if(month.equals("jul."))
			month = "07";
		else if(month.equals("ago."))
			month = "08";
		else if(month.equals("sep."))
			month = "09";
		else if(month.equals("oct."))
			month = "10";
		else if(month.equals("nov."))
			month = "11";
		else
			month = "12";	
		
		return month;
	}
	
	public void nextScreen(GenericResultBean genericResultBean)
	{
		if(genericResultBean.getRes().equals("OK"))
		{
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString(Constants.TERMINAL_ALMACENES_ID, "");
			editor.putString(Constants.TERMINAL_ALMACENES_DESC, "");
			editor.putString(Constants.TERMINAL_MARCAS_ID, "");
			editor.putString(Constants.TERMINAL_MARCAS_DESC, "");
			editor.putString(Constants.TERMINAL_MODELOS_ID, "");
			editor.putString(Constants.TERMINAL_MODELOS_DESC, "");
			editor.commit();
			
			Intent service = new Intent(MicroformasApp.getAppContext(), GetUpdatesService.class);
			startService(service);
			
			Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, RequestListActivity.class);
			intent.putExtra("type", Constants.DATABASE_ABIERTAS);
			startActivity(intent);
		}
		else if(genericResultBean.getDesc() == null)
		{
			Toast.makeText(getApplicationContext(), "Hubo un error en la solicitud. Intente más tarde", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show();
		}
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	    	SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
//			Editor editor = sharedPreferences.edit();
//			editor.putString(Constants.TERMINAL_ALMACENES_ID, "");
//			editor.putString(Constants.TERMINAL_ALMACENES_DESC, "");
//			editor.putString(Constants.TERMINAL_MARCAS_ID, "");
//			editor.putString(Constants.TERMINAL_MARCAS_DESC, "");
//			editor.putString(Constants.TERMINAL_MODELOS_ID, "");
//			editor.putString(Constants.TERMINAL_MODELOS_DESC, "");
//			editor.commit();
//
//	    	Intent intent = new Intent(this, RequestDetailActivity.class);
//	    	intent.putExtra("type", Constants.DATABASE_ABIERTAS);
//	    	intent.putExtra("idRequest", idAR);
//			startActivity(intent);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}
	
	public OnLongClickListener onItemLongClick = new OnLongClickListener()
	{
		public boolean onLongClick(View v)
		{
			viewLongClicked = v;
			AlertDialog.Builder builder = new AlertDialog.Builder(TerminalActivity.this);
			builder.setMessage("Desea eliminar item?").setPositiveButton("OK", onDeleteDialogListener)
			    .setNegativeButton("Cancelar", onDeleteDialogListener).show();
			return false;
		}
	};
	
	public DialogInterface.OnClickListener onDeleteDialogListener = new DialogInterface.OnClickListener()
	{
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
	        {
	        	case DialogInterface.BUTTON_POSITIVE:
	        		String[] identifiers = ((String) viewLongClicked.getTag()).split("_");
	        		int mPos = Integer.parseInt(identifiers[1]);
	        		
	        		itemsViews.remove(mPos);
	        		((ViewManager) viewLongClicked.getParent()).removeView(viewLongClicked);
	        		itemsCatalog.remove(mPos);
	        		UpdateIDItems();
	            break;

	        	case DialogInterface.BUTTON_NEGATIVE:
	            break;
	        }
		}
	};
	
	public OnClickListener onStorageClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(TerminalActivity.this, TerminalListActivity.class);
			intent.putExtra("type", Constants.TERMINAL_ALMACENES);
			startActivityForResult(intent, REQUEST_CODE_SEARCH_TERMINAL_STORAGE);
        }
    };

    private String[] verifyKitRequired()
    {
        SQLiteHelper sqliteHelper = new SQLiteHelper(getApplicationContext(), null);
        SQLiteDatabase db = sqliteHelper.getReadableDB();

        String idService = "";

        Cursor cursor = db.rawQuery("SELECT " + SQLiteHelper.REQUESTS_ID_SERVICIO
            +  " FROM " + SQLiteHelper.REQUESTS_DB_NAME
            +  " WHERE " + SQLiteHelper.REQUESTS_ID_REQUEST +  " = " + idAR, null);

        try
        {
            if (cursor != null)
            {
                if  (cursor.moveToFirst())
                {
                    do
                    {
                        idService = cursor.getString(0);
                    }while (cursor.moveToNext());
                }
            }

            if(!idService.equals(""))
            {
                String isKitRequired = "";
                String kitSupply= "";

                cursor = db.rawQuery("SELECT " + SQLiteHelper.SERVICES_IS_KIT_REQ
                    + ", " + SQLiteHelper.SERVICES_KIT_SUPPLY
                    + " FROM " + SQLiteHelper.SERVICES_DB_NAME
                    + " WHERE " + SQLiteHelper.SERVICES_ID_SERVICIO +  " = " + idService, null);

                if (cursor != null)
                {
                    if  (cursor.moveToFirst())
                    {
                        do
                        {
                            isKitRequired = cursor.getString(0);
                            kitSupply = cursor.getString(1);
                        } while (cursor.moveToNext());
                    }
                }

                return new String[] { isKitRequired, kitSupply };
            }
            else
            {
                return null;
            }
        }
        catch(Exception ex)
        {
            Log.d("Microformas", ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            db.close();
            if(cursor != null)
            {
                cursor.close();
            }
        }

        return null;
    }

    private void changePendingStatus(String isKitInsumo, String idKitInsumo)
    {
        ProgressDialog progressDialog = new ProgressDialog(TerminalActivity.this);
        progressDialog.setMessage("Enviando status por Terminal");
        progressDialog.setCancelable(false);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);

        String day 		= fechaCompromisoDateButton.getText().toString().substring(0,2);
        String month 	= setNumericMonth(fechaCompromisoDateButton.getText().toString().substring(3, 7));
        String year		= fechaCompromisoDateButton.getText().toString().substring(8, 12);
        String notas	= notasEditText.getText().toString();
        String direccion = idDireccionList.get(direccionesSpinner.getSelectedItemPosition());

        String idPrioridad 		= "1";
        String idTipoServicio 	= "1";

        if(urgenciaButton.getText().equals("MEDIA"))
            idPrioridad = "2";
        else if(urgenciaButton.getText().equals("ALTA"))
            idPrioridad = "3";

        if(tipoServicioButton.getText().equals("FORANEO"))
            idPrioridad = "2";

        String isConnectivity = "0";

        ArrayList<EntityCatalog> itemsTerminals = new ArrayList<>();
        ArrayList<EntityCatalog> itemsInsumos = new ArrayList<>();
        for(int i = 0; i < itemsViews.size(); i++)
        {
            String[] identifiers = ((String) itemsViews.get(i).getTag()).split("_");
            int requestCode = Integer.parseInt(identifiers[0]);

            if(requestCode == REQUEST_CODE_SEARCH_TERMINAL_UNIT)
            {
                itemsTerminals.add(itemsCatalog.get(i));
                if(itemsCatalog.get(i).getType().equals(REQUEST_TYPE_CONNECTIVITY))
                {
                    isConnectivity = "1";
                }
            }
            else if(requestCode == REQUEST_CODE_SEARCH_TERMINAL_INSUMO)
            {
                itemsInsumos.add(itemsCatalog.get(i));
            }
        }

        EnviarTerminalTask enviarTerminalTask = new EnviarTerminalTask(	TerminalActivity.this,
            progressDialog, idAR, sharedPreferences.getString(Constants.PREF_USER_ID, ""),
            idAlmacen, itemsTerminals, itemsInsumos, idPrioridad, notas, idTipoServicio, direccion,
            year + "-" + day + "-" + month, isKitInsumo, idKitInsumo, isConnectivity);
        enviarTerminalTask.execute();
    }

	private void UpdateIDItems()
	{
		for(int i = 0; i < itemsViews.size(); i++)
		{
			String[] identifiers = ((String) itemsViews.get(i).getTag()).split("_");
			itemsViews.get(i).setTag(Integer.parseInt(identifiers[0]) + "_" + i);
		}
	}

	public static int REQUEST_CODE_SEARCH_TERMINAL_CONNECTIVITY_SOFTWARE = 470;
	public static int REQUEST_CODE_SEARCH_TERMINAL_INSUMO = 460;
	public static int REQUEST_CODE_SEARCH_TERMINAL_UNIT = 115;
	public static int REQUEST_CODE_SEARCH_TERMINAL_STORAGE = 110;

    public static String REQUEST_TYPE_SUPPLY = "INSUMO";
    public static String REQUEST_TYPE_UNIT = "BRAND_MODEL";
    public static String REQUEST_TYPE_CONNECTIVITY = "CONNECTIVITY_SOFTWARE";

	private ArrayList<String> descDireccionList;
	private ArrayList<String> idDireccionList;
	
	private LinearLayout layoutItems;
	private LinearLayout layoutIsumos;
	
	private DatePicker	dp;
	private Spinner 	direccionesSpinner;
	
	private Button tipoServicioButton;
	private Button urgenciaButton;
	private Button enviarButton;
	private Button buttonAlmacen;
	
	private TextView textAlmacen;
	
	private ArrayList<RelativeLayout> itemsViews;
	private ArrayList<EntityCatalog> itemsCatalog;
	
	private EditText 	notasEditText;
	
	private String idAR;
	private String idClient;
    private String idProduct;
	private String idAlmacen;
	private String nameAlmacen;
	
	private View viewLongClicked;
	
	private DateButton 	fechaCompromisoDateButton;
}