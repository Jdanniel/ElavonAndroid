package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SparePartListActivity extends Activity {
	ArrayList<String> idList;
	ArrayList<String> descList;
	
	EditText editText;
	ListView listView;
	
	CharSequence typedString;
	String id;
	String desc;
	String type;
	String idAR;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spare_part_list);
		
		editText			= (EditText)findViewById(R.id.sparelistEditText);
		listView			= (ListView)findViewById(R.id.sparelistListView);
		Button enviarButton = (Button)findViewById(R.id.sparelistButton);
		
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		idAR = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
		
		if(type.equals(Constants.SPAREPART_SPAREPARTS)){
			setTitle("Spare Parts [Spare Part]");
			enviarButton.setText("AGREGAR SPARE PART");
		}
		
		editText.addTextChangedListener(new TextWatcher(){
		    public void afterTextChanged(Editable arg0) {
		    }

		    public void beforeTextChanged(CharSequence s, int start, int count,
		            int after) {
		    }

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
				typedString = s;
				if(typedString.length() > 2){
					//Manda a llamar el ArrayAdapter con el arreglo correspondiente
					setList(typedString);
				}
				else{
					idList		= new ArrayList<String>();
					descList	= new ArrayList<String>();
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerow, descList);
					listView.setAdapter(adapter);
				}
		    }
		});
		
		listView.setOnItemClickListener(listClickListener);
		
		enviarButton.setOnClickListener(enviarOnClickListener);
	}
	
	OnItemClickListener listClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			id		= idList.get(arg2);
			desc	= descList.get(arg2);
			
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			if(type.equals(Constants.TERMINAL_ALMACENES)){
				editor.putString(Constants.TERMINAL_ALMACENES_ID, id);
				editor.putString(Constants.TERMINAL_ALMACENES_DESC, desc);
			}
			else{
				editor.putString(Constants.SPAREPART_SPAREPART_ID, id);
				editor.putString(Constants.SPAREPART_SPAREPART_DESC, desc);
			}
			editor.commit();
			
			editText.setText(descList.get(arg2));
		}
	};
	
	public void setList(CharSequence typedString){
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		ArrayAdapter<String> adapter;

		idList		= new ArrayList<String>();
		descList	= new ArrayList<String>();
	       
		String itemId;
		String itemDesc; 
		String itemTable;
		
		if(type.equals(Constants.TERMINAL_ALMACENES)){
			itemId 		= SQLiteHelper.ALMACENES_ID_ALMACEN;
			itemDesc 	= SQLiteHelper.ALMACENES_DESC_ALMACEN;
			itemTable 	= SQLiteHelper.ALMACENES_DB_NAME;
		}
		else{
			itemId 		= SQLiteHelper.SPAREPARTS_ID_SPAREPART;
			itemDesc 	= SQLiteHelper.SPAREPARTS_DESC_SPAREPART;
			itemTable 	= SQLiteHelper.SPAREPARTS_DB_NAME;
		}
		
		Cursor c = db.rawQuery("SELECT "	+ itemId   	+ ","
				  							+ itemDesc
				  			 + " FROM "	  	+ itemTable
				  			 + " WHERE "    + itemDesc	+ " like '%" + typedString + "%'", null);

		try{
	       	if (c != null ) {
	       		if  (c.moveToFirst()) {
	       			do {
	       				idList.add(c.getString(0));
	       				descList.add(c.getString(1));
	       			}while (c.moveToNext());
	       		}
	       	}
	    }
	    catch(Exception e){
	    }
	    c.close();
	    db.close();
	        
	    adapter = new ArrayAdapter<String>(this, R.layout.simplerow, descList);
		listView.setAdapter(adapter);
	}
	
	OnClickListener enviarOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			if(id == null || id.equals("")){
				if(type.equals(Constants.TERMINAL_ALMACENES))
					Toast.makeText(getApplicationContext(), "Debes asignar un almacen válido", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Debes asignar un Spare Part válido", Toast.LENGTH_SHORT).show();
			}
			else{
				enviarDatosSparePart();
			}
		}
	};
	
	public void enviarDatosSparePart(){
		Intent intent = new Intent(this, SparePartActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	    	Intent intent = new Intent(this, SparePartActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
}