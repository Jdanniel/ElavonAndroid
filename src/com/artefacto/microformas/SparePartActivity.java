package com.artefacto.microformas;

import java.util.ArrayList;
import java.util.Calendar;

import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.EnviarSparePartTask;
import com.artefacto.microformas.uicomponents.DateButton;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;

public class SparePartActivity extends Activity { 
	ArrayList<String> descDireccionList;
	ArrayList<String> idDireccionList;
	
	DatePicker	dp;
	Spinner 	direccionesSpinner;
	Button 		almacenButton;
	Button 		sparepartButton;
	Button 		tipoServicioButton;
	Button 		urgenciaButton;
	Button 		enviarButton;
	
	EditText notasEditText;
	EditText cantidadEditText;
	String idAR;
	
	DateButton 	fechaCompromisoDateButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spare_part);
		TextView almacenTextView 	= (TextView)findViewById(R.id.sparepartAlmacenTextViewContent);
		TextView sparepartTextView 	= (TextView)findViewById(R.id.sparepartSparePartTextViewContent);
		
		almacenButton 	= (Button)findViewById(R.id.sparepartAlmacenButton);
		sparepartButton = (Button)findViewById(R.id.sparepartSparePartButton);
		
		direccionesSpinner 	= (Spinner)findViewById(R.id.sparepartDireccionesSpinner);
		
		tipoServicioButton 	= (Button)findViewById(R.id.sparepartTipoServicioButton);
		urgenciaButton		= (Button)findViewById(R.id.sparepartUrgenciaButton);
		enviarButton		= (Button)findViewById(R.id.sparepartEnviarButton);
		
		notasEditText		= (EditText)findViewById(R.id.sparepartNotasEditText);
		cantidadEditText    = (EditText)findViewById(R.id.sparepartCantidadEditText);
		
		fechaCompromisoDateButton 	= (DateButton)findViewById(R.id.sparepartFechaCompromisoDateButton);
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		idAR = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
		
		almacenTextView.setText(sharedPreferences.getString(Constants.TERMINAL_ALMACENES_DESC, "(sin información)"));
		sparepartTextView.setText(sharedPreferences.getString(Constants.SPAREPART_SPAREPART_DESC, "(sin información)"));
		
		//TODO llenar el Spinner para Direcciones
		SQLiteHelper 	sqliteHelper	= new SQLiteHelper(getApplicationContext(), null);
	    SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();
	    
	    //Adquiere os almacenes
		Cursor c = db.rawQuery("SELECT "  + SQLiteHelper.DIRECCIONES_ID_DIRECCION 	+ ","
				   						  + SQLiteHelper.DIRECCIONES_DIRECCION 		+ ","
		    							  + SQLiteHelper.DIRECCIONES_COLONIA 		+ ","
		    							  + SQLiteHelper.DIRECCIONES_POBLACION		+ ","
		    							  + SQLiteHelper.DIRECCIONES_ESTADO
		    				   + " FROM " + SQLiteHelper.DIRECCIONES_DB_NAME, null);
		    
		descDireccionList 	= new ArrayList<String>();
		idDireccionList 	= new ArrayList<String>(); 
		
		try{
		   	if (c != null ) {
		   		if  (c.moveToFirst()) {
		   			do {
		   				idDireccionList.add(c.getString(0));
		   				descDireccionList.add(c.getString(1) + ", " + c.getString(2) + ". " + c.getString(3) + ", " + c.getString(4));
		    		}while (c.moveToNext());
		       	}
		    }
		}
		catch(Exception e){
		}
		
		idDireccionList.add("-1");
		descDireccionList.add("DIRECCION DEL NEGOCIO");
		
		c.close();
		db.close();
		
		ArrayAdapter<String> direccionesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descDireccionList);
		direccionesSpinner.setAdapter(direccionesAdapter);
		
		//TODO Lógica para Servicio //LOCAL FORANEO
		tipoServicioButton.setOnClickListener(tipoServicioOnClickListener);
		
		//TODO Lógica para Urgencia //BAJA MEDIA ALTA
		urgenciaButton.setOnClickListener(urgenciaOnClickListener);  
		
		enviarButton.setOnClickListener(enviarOnClickListener);
		
		almacenButton.setOnClickListener(almacenOnClickListener);
		sparepartButton.setOnClickListener(sparepartOnClickListener);
		
		Calendar cal = Calendar.getInstance();
        fechaCompromisoDateButton.setText( cal.get(Calendar.YEAR) 
				 							+ "/" 
				 							+  cal.get(Calendar.MONTH)
				 							+ "/" 
				 							+  cal.get(Calendar.DAY_OF_MONTH));
        
        fechaCompromisoDateButton.onDateSet(dp, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        fechaCompromisoDateButton.setOnClickListener(dateOnClickListener);
	}
	
	OnClickListener dateOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			fechaCompromisoDateButton = (DateButton)findViewById(R.id.terminalFechaCompromisoDateButton);
			fechaCompromisoDateButton.onClick(v);
		}
	};
	
	OnClickListener almacenOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			goToTerminalList(Constants.TERMINAL_ALMACENES);
		}
	};
	
	OnClickListener sparepartOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			goToTerminalList(Constants.SPAREPART_SPAREPARTS);
		}
	};
	
	public void goToTerminalList(String type){
		Intent intent = new Intent(this, SparePartListActivity.class);
		intent.putExtra("type", type);
		startActivity(intent);
	}
	
	OnClickListener tipoServicioOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			if(tipoServicioButton.getText().toString().equals("LOCAL")){
				tipoServicioButton.setText("FORANEO");
			}
			else{
				tipoServicioButton.setText("LOCAL");
			}
		}
	};
	
	OnClickListener urgenciaOnClickListener = new OnClickListener() {
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
	
	OnClickListener enviarOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			ProgressDialog progressDialog = new ProgressDialog(SparePartActivity.this);
	    	progressDialog.setMessage("Enviando estatus por Spare Part");
			progressDialog.setCancelable(false);
			
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			
			String day 		= fechaCompromisoDateButton.getText().toString().substring(0,2);
			String month 	= setNumericMonth(fechaCompromisoDateButton.getText().toString().substring(3, 7));
			String year		= fechaCompromisoDateButton.getText().toString().substring(8, 12);
			
			
			String idPrioridad 		= "1";
			String idTipoServicio 	= "1";
			
			if(urgenciaButton.getText().equals("MEDIA"))
				idPrioridad = "2";
			else if(urgenciaButton.getText().equals("ALTA"))
				idPrioridad = "3";
			
			if(tipoServicioButton.getText().equals("FORANEO"))
				idPrioridad = "2";
			
			EnviarSparePartTask enviarSparePartTask = new EnviarSparePartTask(  SparePartActivity.this, 
																				progressDialog, 
																				idAR, 																//idAR
																				sharedPreferences.getString(Constants.PREF_USER_ID, ""), 			//idTecnico
																				sharedPreferences.getString(Constants.SPAREPART_SPAREPART_ID, ""),	//idSparePart
																				sharedPreferences.getString(Constants.TERMINAL_ALMACENES_ID, ""), 	//idAlmacen
																				idPrioridad, 														//idUrgencia
																				notasEditText.getText().toString(),									//notas	
																				idTipoServicio,														//tipoServicio
																				idDireccionList.get(direccionesSpinner.getSelectedItemPosition()),	//direccion	
																				year + "-" + day + "-" + month,										//fechaCompromiso							//fechaCompromiso
																				cantidadEditText.getText().toString());								//cantidad	
																																		
			enviarSparePartTask.execute();
		}
	};
	
	public String setNumericMonth(String month){
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
	
	public void nextScreen(GenericResultBean genericResultBean){
		if(genericResultBean.getRes().equals("OK")){
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString(Constants.TERMINAL_ALMACENES_ID, "");
			editor.putString(Constants.TERMINAL_ALMACENES_DESC, "");
			editor.putString(Constants.SPAREPART_SPAREPART_ID, "");
			editor.putString(Constants.SPAREPART_SPAREPART_DESC, "");
			editor.commit();
				
			Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, RequestListActivity.class);
			intent.putExtra("type", Constants.DATABASE_ABIERTAS);
			startActivity(intent);
		}
		else if(genericResultBean.getDesc() == null){
			Toast.makeText(getApplicationContext(), "Hubo un error en la solicitud. Intente más tarde", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	    	SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString(Constants.TERMINAL_ALMACENES_ID, "");
			editor.putString(Constants.TERMINAL_ALMACENES_DESC, "");
			editor.putString(Constants.SPAREPART_SPAREPART_ID, "");
			editor.putString(Constants.SPAREPART_SPAREPART_DESC, "");
			editor.commit();
			
	    	Intent intent = new Intent(this, RequestDetailActivity.class);
	    	intent.putExtra("type", Constants.DATABASE_ABIERTAS);
	    	intent.putExtra("idRequest", idAR);
			startActivity(intent);
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
}