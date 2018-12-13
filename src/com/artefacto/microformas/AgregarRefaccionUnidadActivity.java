package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.beans.InfoActualizacionBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.AgregarSKUTask;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

//--------------------------------------ATM--------------------------------------
public class AgregarRefaccionUnidadActivity extends AppCompatActivity
{
	ArrayList<String> idModeloList;
	ArrayList<String> descModeloString;
	ArrayList<String> idMarcaList;
	ArrayList<String> idSkuList;
	
	InfoActualizacionBean infoActualizacionBean;
	String idAR;
	String noAR;
	
	String idSKU = null;
	String idModelo;
	String idMarca;
	
	ListView skuListView;
	
	EditText skuEditText;
	EditText noSerieEditText;
	Button   agregarButton;
	
	CheckBox nuevaCheckBox;
	CheckBox daniadaCheckBox;
	
	CharSequence 	typedString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_refaccion_unidad);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.update_add_repair_unit_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.update_add_repair_unit_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_agregar_refaccion_unidad));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		skuEditText 	= (EditText)findViewById(R.id.addrefSkuEditText);
		skuListView		= (ListView)findViewById(R.id.addrefSKUListView);
		noSerieEditText = (EditText)findViewById(R.id.addrefNoSerieEditText);
		agregarButton 	= (Button)findViewById(R.id.addrefAgregarButton);
		
		nuevaCheckBox 	= (CheckBox)findViewById(R.id.addrefNuevaCheckBox);
		daniadaCheckBox = (CheckBox)findViewById(R.id.addrefDaniadaCheckBox); 
		
		Intent intent 			= getIntent();
		infoActualizacionBean	= (InfoActualizacionBean) intent.getSerializableExtra("bean");
		idAR					= intent.getStringExtra("id");
		noAR					= intent.getStringExtra("noar");

		idMarcaList			= new ArrayList<String>();
		idModeloList		= new ArrayList<String>();
		idSkuList			= new ArrayList<String>();
		descModeloString	= new ArrayList<String>();
		
		//Se inicializa el adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerow, descModeloString);
		skuListView.setAdapter(adapter);
	
		skuListView.setOnItemClickListener(skuListClickListener);
		
		agregarButton.setOnClickListener(agregarSKUOnClickListener);
		
		skuEditText.addTextChangedListener(new TextWatcher(){
		    public void afterTextChanged(Editable arg0) {
		    }

		    public void beforeTextChanged(CharSequence s, int start, int count,
		            int after) {
		    }

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
				typedString = s;
				if(typedString.length() > 2){
					//Manda a llamar el ArrayAdapter con el arreglo correspondiente
					setSKUList(typedString);
				}
				else{
					idMarcaList			= new ArrayList<String>();
					idModeloList		= new ArrayList<String>();
					idSkuList			= new ArrayList<String>();
					descModeloString	= new ArrayList<String>();
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerow, descModeloString);
					skuListView.setAdapter(adapter);
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
	
	public void setSKUList(CharSequence typedString){
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		ArrayAdapter<String> adapter;

		idMarcaList			= new ArrayList<String>();
		idModeloList		= new ArrayList<String>();
		idSkuList			= new ArrayList<String>();
		descModeloString	= new ArrayList<String>();
	       
		Cursor c = db.rawQuery("SELECT "	+ SQLiteHelper.MARCAS_ID_MARCA   	+ ","
				  							+ SQLiteHelper.MODELOS_ID_MODELO	+ ","
				  							+ SQLiteHelper.SKU_ID_SKU 			+ ","
				  							+ SQLiteHelper.MARCAS_DESC_MARCA 	+ ","
				  							+ SQLiteHelper.SKU_DESC 			+ ","
				  							+ SQLiteHelper.MODELOS_DESC_MODELO
				  			 + " FROM "	  	+ SQLiteHelper.SKU_DB_NAME
				  			 + " WHERE "    + SQLiteHelper.SKU_ID_SKU           + " like '%" + typedString + "%'"
				  			 + " OR "       + SQLiteHelper.SKU_DESC 			+ " like '%" + typedString + "%'"
				  			 + " LIMIT 20", null);

		idModeloList 		= new ArrayList<String>();
		descModeloString 	= new ArrayList<String>();
		idMarcaList 		= new ArrayList<String>();
		idSkuList			= new ArrayList<String>();

		try{
	       	if (c != null ) {
	       		if  (c.moveToFirst()) {
	       			do {
	       				idMarcaList.add(c.getString(0));
						idModeloList.add(c.getString(1));
						idSkuList.add(c.getString(2));
						descModeloString.add(c.getString(2) + " - " + c.getString(3) + " " + c.getString(4) + ": " + c.getString(5));
	       			}while (c.moveToNext());
	       		}
	       	}
	    }
	    catch(Exception e){
	    }
	    c.close();
	    db.close();
	        
	    adapter = new ArrayAdapter<String>(this, R.layout.simplerow, descModeloString);
		
		skuListView.setAdapter(adapter);
	}
	
	OnItemClickListener skuListClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			idSKU 		= idSkuList.get(arg2);
			idModelo 	= idModeloList.get(arg2);
			idMarca 	= idMarcaList.get(arg2);
			
			skuEditText.setText(descModeloString.get(arg2));
		}
	};

	OnClickListener agregarSKUOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			if(idSKU != null)
			{
                ProgressDialog progressDialog = new ProgressDialog(AgregarRefaccionUnidadActivity.this);
                progressDialog.setMessage("Agregando unidad, espere un momento.");
                progressDialog.setCancelable(false);

                SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);

                String nuevaChecked;
                String daniadaChecked;

                if (nuevaCheckBox.isChecked())
                    nuevaChecked = "1";
                else
                    nuevaChecked = "0";

                if (daniadaCheckBox.isChecked())
                    daniadaChecked = "1";
                else
                    daniadaChecked = "0";

                AgregarSKUTask agregarSKUTask = new AgregarSKUTask( AgregarRefaccionUnidadActivity.this,
                        progressDialog,
                        idAR, 														//idAR
                        idModelo,//idModeloList.get(skuSpinner.getSelectedItemPosition()), 	//Sku
                        idMarca,//idMarcaList.get(skuSpinner.getSelectedItemPosition()),		//idMarca
                        noSerieEditText.getText().toString(),						//noSerie
                        nuevaChecked, 												//nuevo
                        daniadaChecked, 											//daniado
                        sharedPreferences.getString(Constants.PREF_USER_ID, ""));	//idTecnico
                agregarSKUTask.execute();
			}
            else
            {
                Toast.makeText(AgregarRefaccionUnidadActivity.this, "Debes de seleccionar un SKU", Toast.LENGTH_SHORT).show();
            }
		}
	};
	
	public void nextScreen(GenericResultBean genericResultBean){
		if(genericResultBean.getRes().equals("OK")){
			if(genericResultBean.getVal().equals("1")){
                onBackPressed();
//				Intent intent = new Intent(this, ActualizacionActivity.class);
//				intent.putExtra("bean", infoActualizacionBean);
//				intent.putExtra("id", idAR);
//				intent.putExtra("noar", noAR);
//				startActivity(intent);
			}
		}
		else if(genericResultBean.getDesc() == null){
			Toast.makeText(getApplicationContext(), "Hubo un error en la solicitud. Intente más tarde", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show();
		}
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	    	Intent intent = new Intent(this, InventoryMovementsActivity.class);
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	    	intent.putExtra("bean", infoActualizacionBean);
//	    	intent.putExtra("noar", noAR);
//			intent.putExtra("id", idAR);
//			startActivity(intent);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}
}