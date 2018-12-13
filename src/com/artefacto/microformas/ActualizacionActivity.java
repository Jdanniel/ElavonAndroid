package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.adapters.UnidadesNegocioAdapter;
import com.artefacto.microformas.beans.CarriersBean;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.beans.InfoActualizacionBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.GetCarriersTask;
import com.artefacto.microformas.tasks.InfoUnidadesTask;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActualizacionActivity extends AppCompatActivity
{
	String descNegocio;
	String idAR;
	String noAR;
	String idProducto;
	InfoActualizacionBean infoActualizacionBean;
	Button agregarUnidadButton;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizacion);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.update_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.update_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_actualizacion));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		infoActualizacionBean = (InfoActualizacionBean) intent.getSerializableExtra ("bean");
		idAR = intent.getStringExtra("id");
		noAR = intent.getStringExtra("noar");
		
		agregarUnidadButton = (Button)findViewById(R.id.actualAgregarUnidadesButton);
		
		//Cambia el texto del botón
		SQLiteHelper 	sqliteHelper	= new SQLiteHelper(getApplicationContext(), null);
	    SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();
	        
	    String[] campos = new String[] {SQLiteHelper.REQUESTS_ID_PRODUCTO};
	    String[] args 	= new String[] {idAR};
	        
	    //Adquiere la cantidad de registros que tiene un módulo
	    Cursor c = db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.REQUESTS_ID_REQUEST + "=?", args, null, null, null);
	    
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
		db.close();

		if((idProducto.equals("2") || idProducto.equals("12"))){
			agregarUnidadButton.setText("Agregar Refacción a Unidad");
			agregarUnidadButton.setOnClickListener(agregarRefaccionUnidadOnClickListener);
		}
		else{
			agregarUnidadButton.setOnClickListener(agregarUnidadOnClickListener);
		}
		
		setInfoActualizacionBean(infoActualizacionBean);
		
		TextView clienteTextViewContent = (TextView)findViewById(R.id.actualClienteTextViewContent);
		clienteTextViewContent.setText(infoActualizacionBean.getDescNegocio());

		descNegocio = infoActualizacionBean.getDescNegocio();
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
	
	OnClickListener agregarUnidadOnClickListener = new OnClickListener() {
		public void onClick(View arg0) {
			goToCarrierTask();
		}
	};
	OnClickListener agregarRefaccionUnidadOnClickListener = new OnClickListener() {
		public void onClick(View arg0) {
			goToAgregarRefaccion();
		}
	};
	
	public void fillList(ArrayList<UnitBean> unitBeanArray){
		if(unitBeanArray.size() > 0){
			if(unitBeanArray.get(0).getConnStatus() == 200){
				ListView unidadesListView = (ListView)findViewById(R.id.actualUnidadesListView);
				
				SQLiteHelper 	sqliteHelper	= new SQLiteHelper(getApplicationContext(), null);
			    SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();
				String idNegocio = "";
			        
			    String[] campos = new String[] {SQLiteHelper.REQUESTS_ID_NEGOCIO,
			    								SQLiteHelper.REQUESTS_ID_PRODUCTO};
			    String[] args 	= new String[] {idAR};
			        
			    //Adquiere la cantidad de registros que tiene un módulo
			    Cursor c = db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.REQUESTS_ID_REQUEST + "=?", args, null, null, null);
			      
			    ArrayList<String> idProducto = new ArrayList<String>();
			    try{
			      	if (c != null ) {
			       		if  (c.moveToFirst()) {
			       			do {
			       				idNegocio = c.getString(0);
			       				idProducto.add(c.getString(1));
			        		}while (c.moveToNext());
			        	}
			        }
			    }
			    catch(Exception e){
			    }
			    c.close();
				db.close();
				
				for(int i = 0; i < unitBeanArray.size(); i++){
					unitBeanArray.get(i).setIdBusiness(idNegocio);
					unitBeanArray.get(i).setDescBusiness(descNegocio);
					unitBeanArray.get(i).setIdProduct(idProducto.get(0));
				}

				UnidadesNegocioAdapter unidadesNegocioAdapter  = new UnidadesNegocioAdapter(this, R.layout.info_unidades_list, unitBeanArray);
				unidadesNegocioAdapter.notifyDataSetChanged();
				
				unidadesListView.setAdapter(unidadesNegocioAdapter);
			}
			else
				Toast.makeText(getApplicationContext(), "No se pudieron cargar las unidades.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void updateScreenFromDeleteData(GenericResultBean genericResultBean){
		Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show(); 
		if(genericResultBean.getVal() != null)
		{
			if(genericResultBean.getRes().equals("OK"))
            {
                finish();
				startActivity(getIntent());
			}
		}
	}
	
	public InfoActualizacionBean getInfoActualizacionBean(){
		return infoActualizacionBean;
	}
	
	public void setInfoActualizacionBean(InfoActualizacionBean infoActualizacionBean){
		this.infoActualizacionBean = infoActualizacionBean;
	}
	
	public void goToCarrierTask(){
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Adquiriendo Carriers... espere un momento");
		progressDialog.setCancelable(false);
		
		GetCarriersTask getSKUTask = new GetCarriersTask(this, progressDialog);
		getSKUTask.execute(""); 
	}
	
	public void goToAgregarRefaccion()
    {
		Intent intent 	= new Intent(this, AgregarRefaccionUnidadActivity.class);
		
		infoActualizacionBean 	= getInfoActualizacionBean();
		
		intent.putExtra("bean", infoActualizacionBean);
		intent.putExtra("id", idAR);
		intent.putExtra("noar", noAR);
		startActivity(intent);
	}
	
	public void goToAgregarUnidad(ArrayList<CarriersBean> carriersBeanArray)
    {
		Intent intent 	= new Intent(this, AgregarUnidadNegocioActivity.class);
		
		infoActualizacionBean 	= getInfoActualizacionBean();
		
		intent.putExtra("bean", 		infoActualizacionBean);
		intent.putExtra("id", 			idAR);
		intent.putExtra("noar", 		noAR);
		intent.putExtra("carriersbean", carriersBeanArray);
		startActivity(intent);
	}

    private void UpdateInfo()
    {
        //Se adquieren los datos de las unidades
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.uni_cargaunidades_message));
        progressDialog.setCancelable(false);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
        InfoUnidadesTask task = new InfoUnidadesTask(this, progressDialog, sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""), infoActualizacionBean.getIdNegocio());
        task.execute("");
    }

//	public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	    	Intent intent = new Intent(this, InventoryMovementsActivity.class);
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	    	intent.putExtra("noar", noAR);
//
//			startActivity(intent);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}
}