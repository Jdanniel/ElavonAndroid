package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class UnidadInstalacionCatalogsActivity extends Activity {
	ArrayList<String> idUnidadInstalacion;
	ArrayList<String> descUnidadInstalacion;
	
	ListView unidadInstalacionListView;
	EditText unidadInstalacionCatalogEditText;
	CharSequence typedString;
	
	int idCliente = -1;
	int idTipoProducto = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unidad_instalacion_catalogs);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}
		
		Intent intent = getIntent();
		
		idUnidadInstalacion 	= new ArrayList<String>();
		descUnidadInstalacion = new ArrayList<String>();
		
		unidadInstalacionListView = (ListView)findViewById(R.id.unidadInstalacionesListView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerow, descUnidadInstalacion);
		unidadInstalacionListView.setAdapter(adapter);

		unidadInstalacionListView.setOnItemClickListener(unidadInstalacionListClickListener);
		
		Button sendUnidadInstalacionCatalogButton = (Button)findViewById(R.id.sendunidadInstalacionCatalogButton);
		sendUnidadInstalacionCatalogButton.setOnClickListener(sendUnidadInstalacionCatalogOnClickListener);
		
		unidadInstalacionCatalogEditText = (EditText)findViewById(R.id.unidadInstalacionCatalogEditText);
		unidadInstalacionCatalogEditText.addTextChangedListener(new TextWatcher(){
		    public void afterTextChanged(Editable arg0) {
		        // TODO Auto-generated method stub
		    }

		    public void beforeTextChanged(CharSequence s, int start, int count,
		            int after) {
		        // TODO Auto-generated method stub
		    }

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
				typedString = s;
				if(typedString.length() > 0){
					//Manda a llamar el ArrayAdapter con el arreglo correspondiente
					setunidadInstalacionList(typedString);
				}
				else{
					idUnidadInstalacion 		= new ArrayList<String>();
					descUnidadInstalacion 	= new ArrayList<String>();
					
					ArrayAdapter<String> adapter;
					adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerow, descUnidadInstalacion);
					unidadInstalacionListView.setAdapter(adapter);
				}
		    }

		});
		
		//Obtenemos Id_Cliente y Id_Tipo_Producto
		idCliente = intent.getIntExtra("Id_Cliente", -1);
		idTipoProducto = intent.getIntExtra("Id_Tipo_Producto", -1);
		
		if(idTipoProducto == 2 || idTipoProducto == 12){
			CharSequence title = "Buscar Refacción de Instalación";
			setTitle(title);
		}
	}
	
	public void setunidadInstalacionList(CharSequence typedString){
		SQLiteHelper 	 sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		ArrayAdapter<String> adapter;
		
		boolean isInGrupos = false;
		
		idUnidadInstalacion = new ArrayList<String>();
		descUnidadInstalacion = new ArrayList<String>();
		
		String query = "SELECT " 	+ SQLiteHelper.UNIDADES_ID_UNIDAD 		+ "," 
									+ SQLiteHelper.UNIDAD_NO_SERIE 			+ "," 
									+ SQLiteHelper.UNIDAD_DESC_MODELO 		+ "," 
									+ SQLiteHelper.UNIDAD_DESC_CLIENTE	 	+ "," 
									+ SQLiteHelper.UNIDAD_DESC_MODELO 		+ "," 
									+ SQLiteHelper.UNIDAD_IS_DANIADA	 	+ "," 
									+ SQLiteHelper.UNIDAD_IS_RETIRO 		+ "," 
									+ SQLiteHelper.UNIDAD_ID_TIPO_PRODUCTO 	+ "," 
									+ SQLiteHelper.CLIENTES_ID_CLIENTE
						+ " from " 	+ SQLiteHelper.UNIDAD_DB_NAME
						+ " where " 	+ SQLiteHelper.UNIDAD_NO_SERIE 		+ " like '%" + typedString + "%' OR "
										+ SQLiteHelper.UNIDAD_DESC_CLIENTE 	+ " like '%" + typedString + "%' OR "
										+ SQLiteHelper.UNIDAD_DESC_MARCA 	+ " like '%" + typedString + "%' OR "
										+ SQLiteHelper.UNIDAD_DESC_MODELO 	+ " like '%" + typedString + "%'";

        Cursor c = db.rawQuery(query, null);

		try{
        	if (c != null ) {
        		if  (c.moveToFirst()) {
        			do {
        				if(c.getInt(5) != 1 && c.getInt(6) == 0 && c.getInt(7) == idTipoProducto){
        					if(c.getInt(8) == idCliente || isInGrupos){
		        				idUnidadInstalacion.add(c.getString(0));
		        				descUnidadInstalacion.add(c.getString(1) + "/" + c.getString(2) + " " + c.getString(3));
	        				}
        					else{
        						Toast.makeText(getApplicationContext(), "No se encontraron refacciones", Toast.LENGTH_SHORT).show();
        					}
        				}
        			}while (c.moveToNext());
        		}
        	}
        }
        catch(Exception e){
        }
        c.close();
        db.close();
        
        adapter = new ArrayAdapter<String>(this, R.layout.simplerow, descUnidadInstalacion);
		unidadInstalacionListView.setAdapter(adapter);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	    	Intent intent = new Intent(this, InstalacionActivity.class);
	    	intent.putExtra("After_Search", true);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(intent);
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	OnClickListener sendUnidadInstalacionCatalogOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(), InstalacionActivity.class);
			intent.putExtra("After_Search", true);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 
         	SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
	        Editor editor = sharedPreferences.edit();
	        
        	//editor.putString(Constants.REFACCIONES_CANTIDAD_TEXT,  amountEditText.getText().toString());
        	editor.commit();
        
	        startActivity(intent);
		}
	};
	
	OnItemClickListener unidadInstalacionListClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
	        Editor editor = sharedPreferences.edit();
	        
			editor.putString(Constants.UNIDAD_INSTALACION_ID,		idUnidadInstalacion.get(arg2));
			editor.putString(Constants.UNIDAD_INSTALACION_TEXT, 	descUnidadInstalacion.get(arg2));
			unidadInstalacionCatalogEditText.setText(descUnidadInstalacion.get(arg2));
			
			editor.commit();
		}
	};
}