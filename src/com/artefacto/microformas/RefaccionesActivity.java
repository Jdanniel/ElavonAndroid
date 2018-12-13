package com.artefacto.microformas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.SparePartsConnTask;

public class RefaccionesActivity extends AppCompatActivity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refacciones);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.repairs_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.repairs_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_refacciones));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		layoutItems = (LinearLayout) this.findViewById(R.id.refacciones_listitems);
		
		Button buttonAlmacen = (Button) this.findViewById(R.id.refaccion_button_almacen);
		buttonAlmacen.setOnClickListener(almacenOnClickListener);
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		Editor editor = sharedPreferences.edit();
		editor.putString(Constants.REFACCIONES_ALMACEN_ID, "");
		editor.putString(Constants.REFACCIONES_ALMACEN_TEXT, "(sin información)");
		editor.commit();
		
		textAlmacen = (TextView) this.findViewById(R.id.refaccion_almacen_content);
		textAlmacen.setText(sharedPreferences.getString(Constants.REFACCIONES_ALMACEN_TEXT, "(sin información)"));
		
		buttonAddRefaccion = (Button) this.findViewById(R.id.refacciones_button_search);
		buttonAddRefaccion.setOnClickListener(onAddRefaccionClicked);
		
		buttonService = (Button) this.findViewById(R.id.serviceTypeButton);
		buttonService.setText("Local");
		buttonService.setOnClickListener(onServiceTypeClicked);
		
		buttonPriority = (Button) this.findViewById(R.id.priorityButton);
		buttonPriority.setText("BAJA");
		buttonPriority.setOnClickListener(onPriorityClicked);
		
		sendSparePartsButton = (Button)findViewById(R.id.sendSparePartsButton);
		textNotes = (EditText)findViewById(R.id.notasEditText);

		//TODO Llena el AddressSpinner
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		direccionIDList = new ArrayList<String>();
		ArrayList<String> addressSpinnerArray =  new ArrayList<String>();
		
		Cursor c = db.rawQuery("SELECT "	+ SQLiteHelper.DIRECCIONES_ID_DIRECCION + "," 
											+ SQLiteHelper.DIRECCIONES_DIRECCION + "," 
											+ SQLiteHelper.DIRECCIONES_COLONIA 
							 + " FROM " 	+ SQLiteHelper.DIRECCIONES_DB_NAME, null);
		try{
	       	if (c != null ) {
	       		if  (c.moveToFirst()) {
	       			do {
	       				direccionIDList.add(c.getString(0));
	       				addressSpinnerArray.add(c.getString(1) + " ," + c.getString(2));
	       			}while (c.moveToNext());
	       		}
	       	}
	    }
	    catch(Exception e){
	    }
	    c.close();
	    db.close();

	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, addressSpinnerArray);
	    addressSpinnerArray.add("Dirección del Negocio");
	    direccionIDList.add("-1");
	    
	    spinnerAddress	= (Spinner)findViewById(R.id.addressSpinner);
	    spinnerAddress.setAdapter(adapter);
	    
		sendSparePartsButton.setOnClickListener(sendSparePartOnClickListener);
		
		itemsRefaccion = new ArrayList<EntityCatalog>();
		itemsViews = new ArrayList<RelativeLayout>();
	}
	
	@Override
	protected void onResume()
	{	// TODO Auto-generated method stub
		super.onResume();
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		idAlmacen = sharedPreferences.getString(Constants.REFACCIONES_ALMACEN_ID, "");
		nameAlmacen = sharedPreferences.getString(Constants.REFACCIONES_ALMACEN_TEXT, "(sin información)");
		textAlmacen.setText(nameAlmacen);
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
	{	// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQUEST_CODE_SEARCH)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				String idMarca = data.getStringExtra("REFACCION_MARCA_ID");
				String idModelo = data.getStringExtra("REFACCION_MODELO_ID");
				
		        String nameMarca = data.getStringExtra("REFACCION_MARCA");
		        String nameModelo = data.getStringExtra("REFACCION_MODELO");
		        String countCantidad = data.getStringExtra("REFACCION_CANTIDAD");
		        
		        if(nameMarca != null && nameModelo != null && countCantidad != null)
		        {
		        	if(!nameMarca.equals("") && !nameModelo.equals("") && !countCantidad.equals(""))
		        	{
		        		RelativeLayout item = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_description, null, false);
		        		item.setTag(itemsRefaccion.size());
		        		
		        		LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
		        		                LinearLayout.LayoutParams.MATCH_PARENT,
		        		                LinearLayout.LayoutParams.WRAP_CONTENT);
		        		relativeParams.setMargins(0, 0, 0, 10);
		        		
		        		TextView textMarca = (TextView) item.findViewById(R.id.terminal_text_brand_connectivity_content);
		        		TextView textModelo = (TextView) item.findViewById(R.id.terminal_text_model_appplication_content);
		        		TextView textCantidad = (TextView) item.findViewById(R.id.terminalCantidadTextViewContent);
		        		
		        		item.setOnLongClickListener(onItemLongClick);
		        		
		        		textMarca.setText(nameMarca);
		        		textModelo.setText(nameModelo);
		        		textCantidad.setText(countCantidad);
		        		
		        		EntityCatalog tempRefaccion = new EntityCatalog();
		        		tempRefaccion.setBrandConnectivityId(Integer.parseInt(idMarca));
		        		tempRefaccion.setModelSoftwareId(Integer.parseInt(idModelo));
						tempRefaccion.setType("REFACCION");
		        		tempRefaccion.setCount(Integer.parseInt(countCantidad));
		        		
		        		itemsRefaccion.add(tempRefaccion);
		        		layoutItems.addView(item, relativeParams);
		        		itemsViews.add(item);
		        	}
		        }
			}
		}
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event)
//	{
//	    if (keyCode == KeyEvent.KEYCODE_BACK)
//	    {
//	    	Intent intent = new Intent(this, MainActivity.class);
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}
	
	public OnLongClickListener onItemLongClick = new OnLongClickListener()
	{
		public boolean onLongClick(View v)
		{	// TODO Auto-generated method stub
			viewLongClicked = v;
			AlertDialog.Builder builder = new AlertDialog.Builder(RefaccionesActivity.this);
			builder.setMessage("Desea eliminar refacción?").setPositiveButton("OK", onDeleteDialogListener)
			    .setNegativeButton("Cancelar", onDeleteDialogListener).show();
			return false;
		}
	};
	
	public DialogInterface.OnClickListener onDeleteDialogListener = new DialogInterface.OnClickListener()
	{
		public void onClick(DialogInterface dialog, int which)
		{	// TODO Auto-generated method stub
			switch (which)
	        {
	        	case DialogInterface.BUTTON_POSITIVE:
	        		int mPos = (Integer) viewLongClicked.getTag();
	        		itemsViews.remove(mPos);
	        		((ViewManager) viewLongClicked.getParent()).removeView(viewLongClicked);
	        		itemsRefaccion.remove(mPos);
	        		UpdateIDItems();
	            break;

	        	case DialogInterface.BUTTON_NEGATIVE:
	            break;
	        }
		}
	};
	
	public OnClickListener almacenOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(getApplicationContext(), RefaccionesCatalogsActivity.class);
	    	intent.putExtra("type", Constants.REFACCIONES_TIPO_STORAGE);
	    	startActivity(intent);
	    }
	};
	
	OnClickListener onAddRefaccionClicked = new OnClickListener()
	{
		public void onClick(View v)
		{	// TODO Auto-generated method stub
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString(Constants.REFACCIONES_MARCA_ID, "");
			editor.putString(Constants.REFACCIONES_MARCA_TEXT, "(sin información)");
			editor.putString(Constants.REFACCIONES_MODELO_ID, "");
			editor.putString(Constants.REFACCIONES_MODELO_TEXT, "(sin información)");
			editor.putString(Constants.REFACCIONES_CANTIDAD_TEXT, "");
			editor.commit();
			
			Intent intent = new Intent(RefaccionesActivity.this, SearchingRefaccionActivity.class);
			startActivityForResult(intent, REQUEST_CODE_SEARCH);
		}
	};
	
	OnClickListener onServiceTypeClicked = new OnClickListener()
	{
		public void onClick(View v)
		{	// TODO Auto-generated method stub
			Button clicked = (Button) v;
			clicked.setText(clicked.getText().equals("Local") ? "Foráneo" : "Local");
		}
	};
	
	OnClickListener onPriorityClicked = new OnClickListener()
	{
		public void onClick(View v)
		{	// TODO Auto-generated method stub
			Button clicked = (Button) v;
			
			if(clicked.getText().equals("BAJA"))
			{
				clicked.setText("MEDIA");
			}
			else if(clicked.getText().equals("MEDIA"))
			{
				clicked.setText("ALTA");
			}
			else
			{
				clicked.setText("BAJA");
			}
		}
	};

	OnClickListener sendSparePartOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Date date = new Date();
			String dateFormatted = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(date);
			
			int idPrioridad = 0;
			int idTipoServicio = 0;
			
			if(buttonPriority.getText().equals("MEDIA"))
			{
				idPrioridad = 1;
			}
			else if(buttonPriority.getText().equals("ALTA"))
			{
				idPrioridad = 2;
			}
			
			if(buttonService.getText().equals("Foráneo")) 
				idTipoServicio = 1;
			
        	ProgressDialog progressDialog = new ProgressDialog(RefaccionesActivity.this);
	    	progressDialog.setMessage("Enviando status por Refacción");
			progressDialog.setCancelable(false);
			
			EntityCatalog[] refacciones = new EntityCatalog[itemsRefaccion.size()];
			refacciones = itemsRefaccion.toArray(refacciones);
			
			SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
	    	SparePartsConnTask ctask = new SparePartsConnTask(RefaccionesActivity.this, progressDialog,
	    			sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""),
	    			sharedPreferences.getString(Constants.PREF_USER_ID, ""),
	    			idAlmacen,
	    			refacciones,
	    			(idPrioridad + 1) + "",
	    			textNotes.getText().toString(),
	    			(idTipoServicio + 1) + "",
	    			direccionIDList.get(spinnerAddress.getSelectedItemPosition()),
	    			dateFormatted);
	    	ctask.execute();
		}
	};
	
	private void UpdateIDItems()
	{
		for(int i = 0; i < itemsViews.size(); i++)
		{
			itemsViews.get(i).setTag(i);
		}
	}
	
	public static int REQUEST_CODE_SEARCH = 230;
	
	private ArrayList<RelativeLayout> itemsViews;
	private ArrayList<EntityCatalog> itemsRefaccion;
	
	private Spinner spinnerAddress;
	
	private LinearLayout layoutItems;
	
	private EditText textNotes;
	
	private View viewLongClicked;
	
	private TextView textAlmacen;
	
	private String idAlmacen;
	private String nameAlmacen;
	
	private Button buttonAddRefaccion;
	private Button buttonService;
	private Button buttonPriority;
	private Button sendSparePartsButton;
	
	private ArrayList<String> direccionIDList;
}