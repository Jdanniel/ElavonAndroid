package com.artefacto.microformas;

import java.io.Serializable;
import java.util.ArrayList;

import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.beans.RefaccionesUnidadBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.InstalarRefaccionTask;
import com.artefacto.microformas.tasks.RefaccionesUnidadTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
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

public class RefaccionesUnidadActivity extends Activity implements Serializable{
	String 			lastActivity;
	CharSequence 	typedString;
	int				selectedItem;
	
	TextView clienteTextView;
	TextView marcaTextView;
	TextView modeloTextView;
	TextView noSerieTextView;
	TextView statusTextView;
	EditText idEquipoEditText;
	
	EditText 	catalogEditText;
	ListView 	catalogListView;
	Button 		buscarButton;
	Button 		aceptarButton;
	
	ArrayList<RefaccionesUnidadBean> refaccionesUnidadBeanArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refacciones_unidad);
		
		Intent intent = getIntent();
		lastActivity = intent.getStringExtra("activity");
		setLastActivity(lastActivity);
		
		catalogEditText = (EditText)findViewById(R.id.refuniCatalogEditText);
		catalogListView = (ListView)findViewById(R.id.refuniCatalogListView);
		buscarButton	= (Button)findViewById(R.id.refuniBuscarButton);
		aceptarButton	= (Button)findViewById(R.id.refuniAgregarRefaccionButton);
		
		clienteTextView		= (TextView)findViewById(R.id.refuniClienteTextViewContent);
		marcaTextView		= (TextView)findViewById(R.id.refuniMarcaTextViewContent);
		modeloTextView		= (TextView)findViewById(R.id.refuniModeloTextViewContent);
		noSerieTextView	 	= (TextView)findViewById(R.id.refuniNoSerieTextViewContent);
		statusTextView	 	= (TextView)findViewById(R.id.refuniStatusTextViewContent);
		idEquipoEditText	= (EditText)findViewById(R.id.refuniEquipoEditText);

		catalogListView.setOnItemClickListener(catalogListClickListener);
		
		buscarButton.setOnClickListener(buscarOnClickListener);
		aceptarButton.setOnClickListener(aceptarOnClickListener);
	}
	
	public void setRefaccionesList(CharSequence typedString){
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Buscando refacciones...");
		progressDialog.setCancelable(false);
		
		RefaccionesUnidadTask task = new RefaccionesUnidadTask( this, 
																progressDialog, 
																sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""), 
																typedString.toString(), 
																"2");
		task.execute("");
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if(getLastActivity().equals(Constants.PREF_INSTALACION_ACTIVITY)){
			if (keyCode == KeyEvent.KEYCODE_BACK ) {
			   	Intent intent = new Intent(this, InstalacionActivity.class);
			   	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			    return true;
			}
		}
		else{

		}
	    return super.onKeyDown(keyCode, event);
	}
	
	OnItemClickListener catalogListClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			clienteTextView.setText(refaccionesUnidadBeanArray.get(arg2).getDescCliente());
			marcaTextView.setText(refaccionesUnidadBeanArray.get(arg2).getDescMarca());
			modeloTextView.setText(refaccionesUnidadBeanArray.get(arg2).getDescModelo());
			noSerieTextView.setText(refaccionesUnidadBeanArray.get(arg2).getNoSerie());
			statusTextView.setText(refaccionesUnidadBeanArray.get(arg2).getDescStatusUnidad());
			
			catalogEditText.setText(refaccionesUnidadBeanArray.get(arg2).getIdUnidad());
			selectedItem = arg2;
		}
	};
	
	OnClickListener buscarOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			setRefaccionesList(catalogEditText.getText().toString());
		}
	};
	
	OnClickListener aceptarOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			instalarUnidad();
		}
	};
	
	public void setRefaccionesList(ArrayList<RefaccionesUnidadBean> refaccionesUnidadBeanArray){
		//TODO llenar los arraylist
		this.refaccionesUnidadBeanArray = refaccionesUnidadBeanArray;
		ArrayList<String> idSku = new ArrayList<String>();
		
		for(int i = 0; i < refaccionesUnidadBeanArray.size(); i++)
			idSku.add(refaccionesUnidadBeanArray.get(i).getIdUnidad());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simplerow, idSku);
		catalogListView.setAdapter(adapter);
	}
	
	public void instalarUnidad(){
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.instalacion_info));
		progressDialog.setCancelable(false);
 		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		InstalarRefaccionTask task = new InstalarRefaccionTask(	this, 
																progressDialog, 
																sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""),
																sharedPreferences.getString(Constants.PREF_USER_ID, ""),
																refaccionesUnidadBeanArray.get(selectedItem),
																idEquipoEditText.getText().toString());
		task.execute("");
	}
	
	public void checkInstalacion(GenericResultBean genericResultBean){
		if(genericResultBean.getRes().equals("OK")){
			Intent intent = new Intent(this, InstalacionActivity.class);
			startActivity(intent);
		}
		else{
			Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show();
		}
	}
	public void setLastActivity(String lastActivity){
		this.lastActivity = lastActivity;
	}
	
	public String getLastActivity(){
		return lastActivity;
	}
}