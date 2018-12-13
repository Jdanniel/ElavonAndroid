package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.ViaticosConnTask;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViaticosActivity extends AppCompatActivity
{
	private ListView mainListView;
	ArrayList<String> idViatico;
	ArrayList<String> descViatico;
	ArrayList<String> costoViatico;
	Button		viaticosPriorityButton;
	EditText	placeEditText;
	EditText	observacionesEditText;
	int arg2;
	long arg3;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viaticos);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

		UpdateInfo();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		UpdateInfo();
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

	OnClickListener priorityOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(viaticosPriorityButton.getText().equals("BAJA")){
				viaticosPriorityButton.setText("MEDIA");
			}
			else if(viaticosPriorityButton.getText().equals("MEDIA")){
				viaticosPriorityButton.setText("ALTA");
			}
			else{
				viaticosPriorityButton.setText("BAJA");
			}
		}
	};

	protected OnClickListener addViaticosOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(getApplicationContext(), ViaticosListActivity.class);
			startActivity(intent);
		}
	};

	protected OnClickListener sendViaticosOnClickListener = new OnClickListener(){
		public void onClick(View v){
			//TODO cambiar por lógica de negocio. Estos son los datos a enviar
			SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			int idPrioridad = 0;

			if(viaticosPriorityButton.getText().equals("MEDIA"))
				idPrioridad = 1;
			else if(viaticosPriorityButton.getText().equals("ALTA"))
				idPrioridad = 2;
			
			/*Toast.makeText(getApplicationContext(), 
						     "idViatico = " + idViatico
						   + "\nCosto = "  + costoViatico
						   + "\nLugar = " + placeEditText.getText()
						   + "\nObservaciones = " + observacionesEditText.getText().toString()
						   + "Prioridad = " + idPrioridad, 
						   Toast.LENGTH_LONG).show();*/

			sendViatico(sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""),
					sharedPreferences.getString(Constants.PREF_USER_ID, ""),
					placeEditText.getText().toString(),
					observacionesEditText.getText().toString(),
					(idPrioridad+1)+"",
					idViatico,
					costoViatico);

			SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(getApplicationContext(), null);
			SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();
			sqliteHelper.deleteAllListaViaticos(db);
			db.close();
		}
	};

	public void sendViatico(final String idAr,final String idTecnico,final String lugar,final String observaciones, final String idUrgencia,
							final ArrayList<String> viaticos, final ArrayList<String> costos){
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle("Confirmación");
		dialog.setMessage("¿Seguro que deseas enviar esta solicitud?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int buttonId) {
				ProgressDialog progressDialog = new ProgressDialog(ViaticosActivity.this);
				progressDialog.setMessage("Enviando solicitud");
				progressDialog.setCancelable(false);
				ViaticosConnTask ctask = new ViaticosConnTask(ViaticosActivity.this, progressDialog);
				String viatico="";
				String costo="";
				if (viaticos.size()>0){
					int i=0;
					for (i=0;i<viaticos.size()-1;i++){
						viatico+=viaticos.get(i) + ",";
					}
					viatico+=viaticos.get(i);
				}
				if (costos.size()>0){
					int j=0;
					for (j=0;j<costos.size()-1;j++){
						costo+=costos.get(j) + ",";
					}
					costo+=costos.get(j);
				}

				ctask.execute(idAr,idTecnico,lugar,observaciones,idUrgencia,viatico,costo);
			}
		});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int buttonId) {

			}
		});
		dialog.show();
	}

	public int getArg2(){
		return arg2;
	}

	public void setArg2(int arg2){
		this.arg2 = arg2;
	}

	OnItemLongClickListener itemDelongListener = new OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {

			setArg2(arg2);
			AlertDialog.Builder builder = new AlertDialog.Builder(ViaticosActivity.this);
			builder.setMessage("¿Deseas eliminar este viático?").setPositiveButton("SÍ", dialogClickListener)
					.setNegativeButton("NO", dialogClickListener).setIcon(android.R.drawable.alert_dark_frame).show();
			return true;
		}
	};

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
				case DialogInterface.BUTTON_POSITIVE:
					int arg2 = getArg2();
					deleteViatico(arg2);
					startActivity(getIntent());
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					break;
			}
		}
	};

	public void deleteViatico (int arg2){
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();

		sqliteHelper.deleteListaViaticos(idViatico.get(arg2), costoViatico.get(arg2), db);
		db.close();
	}

//	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	    	Intent intent = new Intent(this, RequestDetailActivity.class);
//
//	    	SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
//	    	intent.putExtra("idRequest", sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
//	    	intent.putExtra("type", Constants.DATABASE_ABIERTAS);
//
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}

	private void UpdateInfo()
	{
		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.viaticos_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.viaticos_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_viaticos));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		idViatico = new ArrayList<>();
		descViatico = new ArrayList<>();
		costoViatico = new ArrayList<>();
		ArrayList<String> stringViatico = new ArrayList<String>();

		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();

		String[] campos = new String[] {SQLiteHelper.VIATICOS_ID_VIATICO,
				SQLiteHelper.LISTAVIATICOS_CONCEPTO,
				SQLiteHelper.LISTAVIATICOS_COSTO};


		Cursor c = db.query(SQLiteHelper.LISTAVIATICOS_DB_NAME , campos, null, null, null, null, null);

		try{
			if (c != null ) {
				if  (c.moveToFirst()) {
					do {
						idViatico.add(c.getString(0));
						descViatico.add(c.getString(1));
						costoViatico.add(c.getString(2));
					}while (c.moveToNext());
				}
			}
		}
		catch(Exception e){
		}
		c.close();
		db.close();

		try{
			if(!(idViatico.get(0) == null) || idViatico.get(0).equals("")){
				try{
					for(int i = 0; i < idViatico.size(); i++){
						stringViatico.add(descViatico.get(i) + ": $" + costoViatico.get(i));
					}

					setContentView(R.layout.activity_viaticos);
					ListView viaticosListView = (ListView)findViewById(R.id.viaticosListView);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
							R.layout.simplerow,
							stringViatico);
					viaticosListView.setAdapter(adapter);
				}
				catch(Exception e){}
			}
		}
		catch(Exception e){}


		if( idViatico.size() > 0){
			TextView	placeTextView 			= (TextView)findViewById(R.id.placeTextView);
			placeEditText						= (EditText)findViewById(R.id.placeEditText);
			TextView	observacionesTextView	= (TextView)findViewById(R.id.observacionesTextView);
			observacionesEditText				= (EditText)findViewById(R.id.observacionesEditText);
			TextView	prioridadTextView 		= (TextView)findViewById(R.id.prioridadTextView);
			viaticosPriorityButton				= (Button)findViewById(R.id.viaticosPriorityButton);
			Button 		sendViaticosButton 		= (Button)findViewById(R.id.sendViaticosButton);

			placeTextView.setVisibility(View.VISIBLE);
			placeEditText.setVisibility(View.VISIBLE);
			observacionesTextView.setVisibility(View.VISIBLE);
			observacionesEditText.setVisibility(View.VISIBLE);
			prioridadTextView.setVisibility(View.VISIBLE);
			viaticosPriorityButton.setVisibility(View.VISIBLE);
			sendViaticosButton.setVisibility(View.VISIBLE);

			viaticosPriorityButton.setText("BAJA");
			viaticosPriorityButton.setOnClickListener(priorityOnClickListener);

			sendViaticosButton.setOnClickListener(sendViaticosOnClickListener);
		}

		Button addViaticosButton = (Button) findViewById(R.id.addViaticosButton);
		addViaticosButton.setOnClickListener(addViaticosOnClickListener);

		mainListView = (ListView)findViewById(R.id.viaticosListView);
		mainListView.setOnItemLongClickListener(itemDelongListener);
	}
}
