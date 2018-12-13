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
import com.artefacto.microformas.tasks.EnviarInsumoTask;
import com.artefacto.microformas.uicomponents.DateButton;

public class PendienteInsumoActivity extends AppCompatActivity
{ 
	ArrayList<String> descDireccionList;
	ArrayList<String> idDireccionList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pendiente_insumo);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.pending_supply_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.pending_supply_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_pendiente_insumo));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		layoutItems = (LinearLayout) this.findViewById(R.id.insumo_listitems);
		
		spinnerAddress 	= (Spinner) this.findViewById(R.id.insumo_spinner_direccion);
		
		buttonTypeSer = (Button) this.findViewById(R.id.insumo_button_type);
		buttonTypeSer.setOnClickListener(onTypeSerButtonClickedListener);
		
		buttonUrgencia = (Button) this.findViewById(R.id.insumo_button_urgencia);
		buttonUrgencia.setOnClickListener(onButtonUrgenciaClickedListener);  
		
		Button buttonAlmacen = (Button) this.findViewById(R.id.insumo_button_almacen);
		buttonAlmacen.setOnClickListener(onButtonAlmacenClickListener);
		
		Button buttonSearch = (Button) this.findViewById(R.id.insumo_button_search);
		buttonSearch.setOnClickListener(onButtonSearchClickedListener);
		
		Button buttonSend = (Button)findViewById(R.id.insumo_button_send);
		buttonSend.setOnClickListener(onChangePendingStatusClicked);
		
		textNotes = (EditText) this.findViewById(R.id.insumo_text_notas);
		
		buttonDate 	= (DateButton) this.findViewById(R.id.insumo_button_date);
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		idAR = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
        idClient = sharedPreferences.getString(Constants.INSUMO_INSUMOS_ID_CLIENTE, idClient);
        idProduct = sharedPreferences.getString(Constants.INSUMO_INSUMOS_ID_PRODUCT, idProduct);
		
		Editor editor = sharedPreferences.edit();
		editor.putString(Constants.INSUMO_ALMACENES_ID, "");
		editor.putString(Constants.INSUMO_ALMACENES_DESC, "(sin información)");
		editor.commit();
		
		textAlmacen = (TextView) this.findViewById(R.id.insumo_text_almacen);
		textAlmacen.setText(sharedPreferences.getString(Constants.INSUMO_ALMACENES_DESC, "(sin información)"));
		
		SQLiteHelper 	sqliteHelper	= new SQLiteHelper(getApplicationContext(), null);
	    SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();
	    
	    //Adquiere os almacenes
		Cursor cursor = db.rawQuery("SELECT "  + SQLiteHelper.DIRECCIONES_ID_DIRECCION 	+ ","
				   						  + SQLiteHelper.DIRECCIONES_DIRECCION 		+ ","
		    							  + SQLiteHelper.DIRECCIONES_COLONIA 		+ ","
		    							  + SQLiteHelper.DIRECCIONES_POBLACION		+ ","
		    							  + SQLiteHelper.DIRECCIONES_ESTADO
		    				   + " FROM " + SQLiteHelper.DIRECCIONES_DB_NAME, null);

		idDireccionList = new ArrayList<>();
		descDireccionList = new ArrayList<>();

		try{
		   	if (cursor != null )
            {
		   		if  (cursor.moveToFirst())
                {
		   			do
                    {
		   				idDireccionList.add(cursor.getString(0));
		   				descDireccionList.add(cursor.getString(1) + ", " + cursor.getString(2) + ". " + cursor.getString(3) + ", " + cursor.getString(4));
		    		} while (cursor.moveToNext());
		       	}
		    }
		}
		catch(Exception ex)
        {
            Log.d("Microformas", ex.getMessage());
            ex.printStackTrace();
        }
		finally
        {
            if(cursor != null)
            {
                cursor.close();
            }

            db.close();
        }

		idDireccionList.add("-1");
		descDireccionList.add("DIRECCION DEL NEGOCIO");

		ArrayAdapter<String> adapterAddress = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, descDireccionList);
		spinnerAddress.setAdapter(adapterAddress);

        Calendar cal = Calendar.getInstance();
        String currentDate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/"
            + cal.get(Calendar.DAY_OF_MONTH);
		buttonDate.setText(currentDate);
        buttonDate.onDateSet(datePicker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        buttonDate.setOnClickListener(dateOnClickListener);
        
        itemsInsumo = new ArrayList<>();
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
		if(requestCode == REQUEST_CODE_SEARCH)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				String idInsumo = data.getStringExtra("INSUMO_INSUMO_ID");
				
		        String nameInsumo = data.getStringExtra("INSUMO_INSUMO");
		        String countCantidad = data.getStringExtra("INSUMO_CANTIDAD");
		        
		        if(nameInsumo != null && countCantidad != null)
		        {
		        	if(!nameInsumo.equals("") && !countCantidad.equals(""))
		        	{
		        		RelativeLayout item = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_description, null, false);
		        		item.setTag(itemsInsumo.size());
		        		
		        		LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
		        		                LinearLayout.LayoutParams.MATCH_PARENT,
		        		                LinearLayout.LayoutParams.WRAP_CONTENT);
		        		relativeParams.setMargins(0, 0, 0, 10);
		        		
		        		RelativeLayout layoutMarca = (RelativeLayout) item.findViewById(R.id.layout_terminal_marca);
		        		layoutMarca.setVisibility(View.GONE);
		        		
		        		TextView textInsumo = (TextView) item.findViewById(R.id.terminal_text_model_appplication_content);
		        		TextView textCantidad = (TextView) item.findViewById(R.id.terminalCantidadTextViewContent);
		        		
		        		item.setOnLongClickListener(onItemLongClick);
		        		
		        		textInsumo.setText(nameInsumo);
		        		textCantidad.setText(countCantidad);
		        		
		        		EntityCatalog tempInsumo = new EntityCatalog();
		        		tempInsumo.setModelSoftwareId(Integer.parseInt(idInsumo));
						tempInsumo.setType("INSUMO");
		        		tempInsumo.setCount(Integer.parseInt(countCantidad));
		        		
		        		itemsInsumo.add(tempInsumo);
		        		layoutItems.addView(item, relativeParams);
		        		itemsViews.add(item);
		        	}
		        }
			}
		}

        if(requestCode == REQUEST_CODE_SEARCH_STORAGE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                idAlmacen = data.getStringExtra("list_id");
                nameAlmacen = data.getStringExtra("list_desc");
                textAlmacen.setText(nameAlmacen);
            }
        }
	}
	
	public OnLongClickListener onItemLongClick = new OnLongClickListener()
	{
		public boolean onLongClick(View v)
		{
			viewLongClicked = v;
			AlertDialog.Builder builder = new AlertDialog.Builder(PendienteInsumoActivity.this);
			builder.setMessage("Desea eliminar insumo?").setPositiveButton("OK", onDeleteDialogListener)
			    .setNegativeButton("Cancelar", onDeleteDialogListener).show();
			return false;
		}
	};
	
	public OnClickListener dateOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			buttonDate = (DateButton) findViewById(R.id.insumo_button_date);
			buttonDate.onClick(v);
		}
	};
	
	OnClickListener onTypeSerButtonClickedListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			buttonTypeSer.setText(buttonTypeSer.getText().toString().equals("LOCAL") ? "FORANEO" : "LOCAL");
		}
	};
	
	OnClickListener onButtonUrgenciaClickedListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			String tmpValue = buttonUrgencia.getText().toString();
			
			if(tmpValue.equals("BAJA"))
			{
				buttonUrgencia.setText("MEDIA");
			}
			else if(tmpValue.equals("MEDIA"))
			{
				buttonUrgencia.setText("ALTA");
			}
			else
			{
				buttonUrgencia.setText("BAJA");
			}
		}
	};
	
	OnClickListener onButtonSearchClickedListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString(Constants.INSUMO_INSUMOS_ID, "");
			editor.putString(Constants.INSUMO_INSUMOS_DESC, "(sin información)");
            editor.putString(Constants.INSUMO_INSUMOS_ID_CLIENTE, idClient);
			editor.commit();
			
			Intent intent = new Intent(PendienteInsumoActivity.this, SearchingInsumoActivity.class);
			startActivityForResult(intent, PendienteInsumoActivity.REQUEST_CODE_SEARCH);
		}
	};
	
	OnClickListener onChangePendingStatusClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
            final String[] kitData = verifyKitRequired();
            if(kitData != null)
            {   // NEED KIT DATA
                if(kitData[0].equals("1"))
                {
                    new AlertDialog.Builder(PendienteInsumoActivity.this)
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
	
	public DialogInterface.OnClickListener onDeleteDialogListener = new DialogInterface.OnClickListener()
	{
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
	        {
	        	case DialogInterface.BUTTON_POSITIVE:
	        		int mPos = (Integer) viewLongClicked.getTag();
	        		itemsViews.remove(mPos);
	        		((ViewManager) viewLongClicked.getParent()).removeView(viewLongClicked);
	        		itemsInsumo.remove(mPos);
	        		UpdateIDItems();
	            break;

	        	case DialogInterface.BUTTON_NEGATIVE:
	            break;
	        }
		}
	};
	
	OnClickListener onButtonAlmacenClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(PendienteInsumoActivity.this, PendienteInsumoListActivity.class);
			intent.putExtra("type", Constants.INSUMO_ALMACENES);
			startActivityForResult(intent, REQUEST_CODE_SEARCH_STORAGE);
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
			editor.putString(Constants.INSUMO_INSUMOS_ID, "");
			editor.putString(Constants.INSUMO_INSUMOS_DESC, "");
            editor.putString(Constants.INSUMO_INSUMOS_ID_PRODUCT, idProduct);
            editor.putString(Constants.INSUMO_INSUMOS_ID_CLIENTE, idClient);
			editor.commit();
			
			Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show();
			
			Intent service = new Intent(MicroformasApp.getAppContext(), GetUpdatesService.class);
			startService(service);
			
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
	
//	public boolean onKeyDown(int keyCode, KeyEvent event)
//	{
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	    	SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
//			Editor editor = sharedPreferences.edit();
//			editor.putString(Constants.TERMINAL_ALMACENES_ID, "");
//			editor.putString(Constants.TERMINAL_ALMACENES_DESC, "");
//			editor.putString(Constants.INSUMO_INSUMOS_ID, "");
//			editor.putString(Constants.INSUMO_INSUMOS_DESC, "");
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

    private void changePendingStatus(String isKitInsumo, String idKitInsumo)
    {
        ProgressDialog progressDialog = new ProgressDialog(PendienteInsumoActivity.this);
        progressDialog.setMessage("Enviando estatus por Insumo");
        progressDialog.setCancelable(false);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);

        String day 		= buttonDate.getText().toString().substring(0,2);
        String month 	= setNumericMonth(buttonDate.getText().toString().substring(3, 7));
        String year		= buttonDate.getText().toString().substring(8, 12);

        String idPrioridad 		= "1";
        String idTipoServicio 	= "1";

        if(buttonUrgencia.getText().equals("MEDIA"))
        {
            idPrioridad = "2";
        }
        else if(buttonUrgencia.getText().equals("ALTA"))
        {
            idPrioridad = "3";
        }

        if(buttonUrgencia.getText().equals("FORANEO"))
        {
            idPrioridad = "2";
        }

        EntityCatalog[] insumos = new EntityCatalog[itemsInsumo.size()];
        insumos = itemsInsumo.toArray(insumos);

        EnviarInsumoTask enviarInsumoTask = new EnviarInsumoTask(PendienteInsumoActivity.this,
                progressDialog,
                idAR, 																//idAR
                sharedPreferences.getString(Constants.PREF_USER_ID, ""), 			//idTecnico
                idAlmacen,
                insumos,
                idPrioridad, 														//idUrgencia
                textNotes.getText().toString(),									//notas
                idTipoServicio,														//tipoServicio
                idDireccionList.get(spinnerAddress.getSelectedItemPosition()),	//direccion
                year + "-" + day + "-" + month,
                isKitInsumo, idKitInsumo);									//fechaCompromiso

        enviarInsumoTask.execute();
    }

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
	
	private void UpdateIDItems()
	{
		for(int i = 0; i < itemsViews.size(); i++)
		{
			itemsViews.get(i).setTag(i);
		}
	}
	
	public static int REQUEST_CODE_SEARCH_STORAGE = 355;
	public static int REQUEST_CODE_SEARCH = 345;

	private LinearLayout layoutItems;
	
	private String idAR;
    private String idClient;
    private String idProduct;
	private String nameAlmacen;
	private String idAlmacen;

	private ArrayList<RelativeLayout> itemsViews;
	private ArrayList<EntityCatalog> itemsInsumo;
	
	private DatePicker	datePicker;
	
	private DateButton 	buttonDate;
	
	private View viewLongClicked;

	private TextView textAlmacen;
	
	private EditText textNotes;
	
	private Button buttonTypeSer;
	private Button buttonUrgencia;
	
	private Spinner spinnerAddress;
}