package com.artefacto.microformas;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.beans.InfoActualizacionBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

public class RefaccionesCatalogsActivity extends AppCompatActivity
{	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refacciones_catalogs);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.repairs_catalog_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.repairs_catalog_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_refacciones_catalogs));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
//		lastActivity = intent.getStringExtra("activity");
		this.type = intent.getIntExtra("type", 0);
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		idAr = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
		
		int stringId;
		if(type == Constants.REFACCIONES_TIPO_STORAGE)
		{
			stringId = R.string.actualizacion_almacenes;
			setTitle("Refacción [Almacén]");
		}
		else if(type == Constants.REFACCIONES_TIPO_BRAND)
		{
			stringId = R.string.actualizacion_marcas;
			setTitle("Refacción [Marca]");
		}
		else
		{
			stringId = R.string.actualizacion_modelos;
			setTitle("Refacción [Modelo]");
		}

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}
		
		refaccionListview = (ListView) this.findViewById(R.id.refaccion_listview);
		refaccionListview.setOnItemClickListener(onItemClickListener);
		
		Button buttonAdd = (Button) this.findViewById(R.id.refaccion_button_add);
		buttonAdd.setOnClickListener(onAddClickListener);
		
		textCatalog = (EditText) this.findViewById(R.id.refaccion_text_catalog);
		textCatalog.addTextChangedListener(new TextWatcher()
		{
		    public void afterTextChanged(Editable arg0) {}

		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		    public void onTextChanged(CharSequence s, int start, int before, int count)
		    {
				typedString = s;
				if(typedString.length() > 2)
				{	//Manda a llamar el ArrayAdapter con el arreglo correspondiente
					setRefaccionList(typedString, type);
				}
				else{
					listId = new ArrayList<String>();
					listDesc = new ArrayList<String>();
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerow, listDesc);
					refaccionListview.setAdapter(adapter);
				}
		    }
		});
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

	public void setRefaccionList(CharSequence typedString, int type)
	{
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		String idProducto = "1";
		Cursor c = db.rawQuery("SELECT " 	+ SQLiteHelper.REQUESTS_ID_PRODUCTO
							+  " FROM " 	+ SQLiteHelper.REQUESTS_DB_NAME 
							+  " WHERE "	+ SQLiteHelper.REQUESTS_ID_REQUEST +  " = " + idAr, null);
		
		try{
			if (c != null ) {
				if  (c.moveToFirst()) {
					do {
						idProducto = c.getString(0);
					}while (c.moveToNext());
				}
			}
		}
		catch(Exception e){
		}
		c.close();
		
		listId		= new ArrayList<String>();
		listDesc	= new ArrayList<String>();
		
		String itemId;
		String itemDesc; 
		String itemTable;
		
		if(this.type == Constants.REFACCIONES_TIPO_STORAGE)
		{ 
			itemId		= SQLiteHelper.ALMACENES_ID_ALMACEN;
			itemDesc	= SQLiteHelper.ALMACENES_DESC_ALMACEN;
			itemTable	= SQLiteHelper.ALMACENES_DB_NAME;
		}
		else if(this.type == Constants.REFACCIONES_TIPO_BRAND)
		{
			itemId		= SQLiteHelper.MARCAS_ID_MARCA;
			itemDesc	= SQLiteHelper.MARCAS_DESC_MARCA;
			itemTable	= SQLiteHelper.MARCAS_DB_NAME;
		}
		else
		{
			itemId		= SQLiteHelper.MODELOS_ID_MODELO;
			itemDesc	= SQLiteHelper.MODELOS_DESC_MODELO;
			itemTable	= SQLiteHelper.MODELOS_DB_NAME;
		}
		
		String query = "SELECT " + itemId + "," + itemDesc + " FROM " + itemTable;
		query = query + " WHERE "    + itemDesc	+ " like '%" + typedString + "%'";
		
		if(type == Constants.REFACCIONES_TIPO_BRAND)
		{
			query = query + " AND "	+ SQLiteHelper.PRODUCTO_ID_PRODUCTO + " = "		 + idProducto;
		}
		else if(type == Constants.REFACCIONES_TIPO_MODEL)
		{
			query = "SELECT MO." 	+ SQLiteHelper.MODELOS_ID_MODELO 	+ ", MO." 
									+ SQLiteHelper.MODELOS_DESC_MODELO 
	              + " FROM " 		+ SQLiteHelper.MODELOS_DB_NAME 		+ " MO " 
	              + " INNER JOIN " 	+ SQLiteHelper.MARCAS_DB_NAME 		+ " MA ON MO."  + SQLiteHelper.MARCAS_ID_MARCA
															            + " = MA."		+ SQLiteHelper.MARCAS_ID_MARCA
	              + " WHERE MA." 	+ SQLiteHelper.PRODUCTO_ID_PRODUCTO + " = "			+ idProducto
	              + " AND MO." 		+ SQLiteHelper.MODELOS_DESC_MODELO 	+ " LIKE '%" + typedString + "%'"
	              + " OR MO." 		+ SQLiteHelper.MODELOS_SKU 			+ " LIKE '%" + typedString + "%'";
		}
		
		c = db.rawQuery(query, null);

		try{
	       	if (c != null ) {
	       		if  (c.moveToFirst()) {
	       			do {
	       				listId.add(c.getString(0));
	       				listDesc.add(c.getString(1));
	       			}while (c.moveToNext());
	       		}
	       	}
	    }
	    catch(Exception e) {}
		
		db.close();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simplerow, listDesc);
		refaccionListview.setAdapter(adapter);
	}
	
	OnClickListener onAddClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{	
			if(id == null || id.equals(""))
			{
				if(type == Constants.REFACCIONES_TIPO_STORAGE)
					Toast.makeText(getApplicationContext(), "Debes asignar un almacen válido", Toast.LENGTH_SHORT).show();
				else if(type == Constants.REFACCIONES_TIPO_BRAND)
					Toast.makeText(getApplicationContext(), "Debes asignar una marca válida", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Debes asignar un modelo válido", Toast.LENGTH_SHORT).show();
			}
			else
			{
				onBackPressed();
			}
		}
	};
	
	OnItemClickListener onItemClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			id		= listId.get(arg2);
			desc	= listDesc.get(arg2);
			
			SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
	        Editor editor = sharedPreferences.edit();
	        
			if(type == Constants.REFACCIONES_TIPO_STORAGE)
			{
				editor.putString(Constants.REFACCIONES_ALMACEN_ID, id);
				editor.putString(Constants.REFACCIONES_ALMACEN_TEXT, desc);
				textCatalog.setText(desc);
			}
			else if(type == Constants.REFACCIONES_TIPO_BRAND)
			{
				editor.putString(Constants.REFACCIONES_MARCA_ID, id);
				editor.putString(Constants.REFACCIONES_MARCA_TEXT, desc);
				textCatalog.setText(desc);
			}
			else
			{
				editor.putString(Constants.REFACCIONES_MODELO_ID, id);
				editor.putString(Constants.REFACCIONES_MODELO_TEXT, desc);
				textCatalog.setText(desc);
			}
			
			editor.commit();
		}
	};
	
	private ArrayList<String> listId;
	private ArrayList<String> listDesc;
	
	InfoActualizacionBean infoActualizacionBean;
	
	String costo;
	String idMoneda;
	
	private ListView refaccionListview;
	private EditText textCatalog;
	
	private String idAr;
	private String id;
	private String desc;
	
	private int	type;
//	String 			lastActivity;
	CharSequence 	typedString;
}