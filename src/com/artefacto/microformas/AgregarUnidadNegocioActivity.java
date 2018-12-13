package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.beans.CarriersBean;
import com.artefacto.microformas.beans.ConnectivityBean;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.beans.InfoActualizacionBean;
import com.artefacto.microformas.beans.SoftwareBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.tasks.AgregarUnidadTask;
import com.artefacto.microformas.tasks.GetUpdateConnectivity;
import com.artefacto.microformas.tasks.GetUpdateSoftware;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AgregarUnidadNegocioActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_unidad_negocio);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.update_add_unit_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.update_add_unit_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_agregar_refaccion_unidad));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textConnectivity = (TextView) findViewById(R.id.addUnitModConnectivityText);
        textConnectivity.setVisibility(View.GONE);

        textSoftware = (TextView) findViewById(R.id.addUnitModSoftwareText);
        textSoftware.setVisibility(View.GONE);

        spinnerConnectivity = (Spinner) findViewById(R.id.addUnitModConnectivitySpinner);
        spinnerConnectivity.setVisibility(View.GONE);
        spinnerConnectivity.setOnItemSelectedListener(onItemConnectivitySelectedListener);

        spinnerSoftware = (Spinner) findViewById(R.id.addUnitModSoftwareSpinner);
        spinnerSoftware.setVisibility(View.GONE);
		spinnerSoftware.setOnItemSelectedListener(onItemSoftwareSelectedListener);

		carrierTextView = (TextView)findViewById(R.id.addUniCarrierTextView);
		
		noSerieEditText			= (EditText)findViewById(R.id.addUniNoSerieEditText);
		noInventarioEditText	= (EditText)findViewById(R.id.addUniNoInventarioEditText);
		idEquipoEditText 		= (EditText)findViewById(R.id.addUniIdEquipoEditText);
		noSimTextView			= (TextView)findViewById(R.id.addUniNoSimTextView);
		noSimEditText			= (EditText)findViewById(R.id.addUniNoSimEditText);
		noImeiTextView			= (TextView)findViewById(R.id.addUniNoImeiTextView);
		noImeiEditText			= (EditText)findViewById(R.id.addUniNoImeiEditText);
		
		spinnerModelos = (Spinner)findViewById(R.id.addUniModeloSpinner);
		spinnerCarrier = (Spinner)findViewById(R.id.addUniCarrierSpinner);

		Button agregarButton 	= (Button)findViewById(R.id.addUniAgregarButton);
		
		Intent intent 			= getIntent();
		infoActualizacionBean	= (InfoActualizacionBean) intent.getSerializableExtra("bean");
		idAR					= intent.getStringExtra("id");
		noAR					= intent.getStringExtra("noar");
		carriersBean			= (ArrayList<CarriersBean>)intent.getSerializableExtra("carriersbean");
		
		SQLiteHelper 	sqliteHelper	= new SQLiteHelper(getApplicationContext(), null);
	    SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();
	    
	    //Adquiere la cantidad de registros que tiene un modelo
        String tmpSQL = "SELECT "  + SQLiteHelper.MARCAS_DB_NAME + "."  + SQLiteHelper.MARCAS_ID_MARCA   + ","
                + SQLiteHelper.MODELOS_DB_NAME + "." + SQLiteHelper.MODELOS_ID_MODELO     + ","
                + SQLiteHelper.MARCAS_DESC_MARCA + ","
                + SQLiteHelper.MODELOS_DESC_MODELO + ","
                + SQLiteHelper.MODELOS_ID_GPRS
                + " FROM " + SQLiteHelper.MODELOS_DB_NAME
                + " INNER JOIN " + SQLiteHelper.CLIMOD_DB_NAME
                + "  ON "        + SQLiteHelper.CLIMOD_DB_NAME + "."  + SQLiteHelper.MODELOS_ID_MODELO
                +  "  = "        + SQLiteHelper.MODELOS_DB_NAME + "." + SQLiteHelper.MODELOS_ID_MODELO
                + " INNER JOIN " + SQLiteHelper.MARCAS_DB_NAME
                + "  ON " 		+ SQLiteHelper.MARCAS_DB_NAME + "."  + SQLiteHelper.MARCAS_ID_MARCA
                +  "  = "  		+ SQLiteHelper.MODELOS_DB_NAME + "." + SQLiteHelper.MARCAS_ID_MARCA
                + " WHERE " 	  + SQLiteHelper.CLIMOD_DB_NAME + "."    + SQLiteHelper.CLIENTES_ID_CLIENTE + " = "
                + "(SELECT " + SQLiteHelper.CLIENTES_ID_CLIENTE
                + " FROM "   + SQLiteHelper.REQUESTS_DB_NAME
                + " WHERE "  + SQLiteHelper.REQUESTS_ID_REQUEST + " = " + idAR + ")"
                + " ORDER BY " + SQLiteHelper.MARCAS_DB_NAME + "." + SQLiteHelper.MARCAS_ID_MARCA;
	    Cursor c = db.rawQuery(tmpSQL
					  , null);
		    
		idModeloList 		= new ArrayList<>();
		descModeloString 	= new ArrayList<>();
		idMarcaList 		= new ArrayList<>();
		isGPRSList 			= new ArrayList<>();
		
		try{
		   	if (c != null ) {
		   		if  (c.moveToFirst()) {
		   			do {
		   				idMarcaList.add(c.getString(0));
		   				idModeloList.add(c.getString(1));
		   				if(c.getString(3) == null || c.getString(3).equals(""))
		   					descModeloString.add(c.getString(3));
		   				else
		   					descModeloString.add(c.getString(2) + " - " + c.getString(3));
		   				isGPRSList.add(c.getString(4));
		    		}while (c.moveToNext());
		       	}
		    }
		}
		catch(Exception ex)
		{
			Log.d("Microformas=", ex.getMessage());
		}
		finally
        {
            if(c != null)
            {
                c.close();
            }
        }

		
		ArrayAdapter<String> modelosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descModeloString);
		spinnerModelos.setAdapter(modelosAdapter);
		spinnerModelos.setOnItemSelectedListener(modelosOnItemSelectedListener);
		
		ArrayList<String> descCarrierString = new ArrayList<String>();
		idCarrierList 						= new ArrayList<String>();
		descCarrierString 					= new ArrayList<String>();
		
		for(int i = 0; i < carriersBean.size(); i++){
			idCarrierList.add(carriersBean.get(i).getId());
			descCarrierString.add(carriersBean.get(i).getDescCarrier());
		}
		
		ArrayAdapter<String> carrierAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descCarrierString);
		spinnerCarrier.setAdapter(carrierAdapter);
			
		agregarButton.setOnClickListener(agregarOnClickListener);
		db.close();
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

    private OnItemSelectedListener onItemConnectivitySelectedListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            textSoftware.setVisibility(View.GONE);
            spinnerSoftware.setVisibility(View.GONE);

			changeVisibility(mListConnectivity.get(position).isGPRS());

			ProgressDialog progressDialog = new ProgressDialog(AgregarUnidadNegocioActivity.this);
            progressDialog.setMessage("Descargando software.");
            progressDialog.setCancelable(false);

            GetUpdateSoftware getSoftwareTask = new GetUpdateSoftware(AgregarUnidadNegocioActivity.this,
                    progressDialog);
            getSoftwareTask.execute(String.valueOf(mListConnectivity.get(spinnerConnectivity.getSelectedItemPosition()).getId()),
                    infoActualizacionBean.getIdCliente());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

	private OnItemSelectedListener onItemSoftwareSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	OnItemSelectedListener modelosOnItemSelectedListener = new OnItemSelectedListener()
    {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
        {
            textConnectivity.setVisibility(View.GONE);
            spinnerConnectivity.setVisibility(View.GONE);

            ProgressDialog progressDialog = new ProgressDialog(AgregarUnidadNegocioActivity.this);
            progressDialog.setMessage("Descargando conectividad.");
            progressDialog.setCancelable(false);

            GetUpdateConnectivity getConnTask = new GetUpdateConnectivity(AgregarUnidadNegocioActivity.this,
                    progressDialog);
            getConnTask.execute(idModeloList.get(spinnerModelos.getSelectedItemPosition()),
                    infoActualizacionBean.getIdCliente());

//            changeVisibility(isGPRSList.get(spinnerModelos.getSelectedItemPosition()));
		}

		public void onNothingSelected(AdapterView<?> arg0) {}
	};
	
	OnClickListener agregarOnClickListener = new OnClickListener()
    {
		public void onClick(View v)
        {
            ProgressDialog progressDialog = new ProgressDialog(AgregarUnidadNegocioActivity.this);
	    	progressDialog.setMessage("Agregando unidad, espere un momento.");
			progressDialog.setCancelable(false);

            int pos = spinnerSoftware.getSelectedItemPosition();
            String idSoftware = "";
            if(pos >= 0)
            {
                idSoftware = String.valueOf(mListSoftware.get(pos).getId());
            }

            pos = spinnerConnectivity.getSelectedItemPosition();
            String idConnectivity = "";
            if(pos >= 0)
            {
                idConnectivity = String.valueOf(mListConnectivity.get(pos).getId());
            }
            if (!infoActualizacionBean.getIdCliente().equals("106")) {
				if (mListConnectivity.get(pos).isGPRS()) {//validacion IsGPRS
					if (noSimEditText.getText().toString().trim().equals("")) {
						Toast.makeText(getApplicationContext(), "No. SIM no puede ser vacío.", Toast.LENGTH_SHORT).show();
						return;
					}

					if (spinnerCarrier.getSelectedItemPosition() < 0) {
						Toast.makeText(getApplicationContext(), "Selecciona un Carrier para continuar.", Toast.LENGTH_SHORT).show();
						return;
					}
				}
			}
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			
			AgregarUnidadTask agregarUnidadTask = new AgregarUnidadTask(AgregarUnidadNegocioActivity.this, 
																		progressDialog, 
																		infoActualizacionBean.getIdCliente(), 						//IDCLIENTE
																		noSerieEditText.getText().toString(), 						//IDSERIE
																		noInventarioEditText.getText().toString(), 					//NOINVENTARIO
																		idModeloList.get(spinnerModelos.getSelectedItemPosition()),	//IDMODELO
																		noSimEditText.getText().toString(), 						//noSIM								//noSIM
																		"",															//idSolicitudRec
																		"0", 														//isNueva
																		idMarcaList.get(spinnerModelos.getSelectedItemPosition()), 	//idMarca
																		noImeiEditText.getText().toString(), 						//noIMEI
																		"4", 														//idTipoResponsable
																		infoActualizacionBean.getIdNegocio(), 						//idResponsable
																		"-1", 														//idLlave    (SIN USAR)
																		idSoftware, 														//idSoftware (SIN USAR)
																		"0", 														//isRetiro   (SIN USAR)
																		"", 														//posicionInventario
																		sharedPreferences.getString(Constants.PREF_USER_ID, ""), 	//idTecnico
																		"17", 														//idStatusUnidad
																		idEquipoEditText.getText().toString(), 						//noEquipo
																		infoActualizacionBean.getIdNegocio(), 						//idNegocio
																		"",//isDaniada
																		idCarrierList.get(spinnerCarrier.getSelectedItemPosition()),
                                                                        idConnectivity);
	    	agregarUnidadTask.execute(); //isDaniada
		}
	};

    public void InitSpinnerConnectivity(ArrayList<ConnectivityBean> list)
    {
        ArrayList<String> listShow = new ArrayList<>();
        mListConnectivity = list;
        if(mListConnectivity != null)
        {
            if(mListConnectivity.size() > 0)
            {
                textConnectivity.setVisibility(View.VISIBLE);
                spinnerConnectivity.setVisibility(View.VISIBLE);

                for(int i = 0; i < mListConnectivity.size(); i++)
                {
                    listShow.add(mListConnectivity.get(i).getDescription());
                }

                ArrayAdapter<String> carrierAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, listShow);
                spinnerConnectivity.setAdapter(carrierAdapter);

                int selectedPos = spinnerConnectivity.getSelectedItemPosition();
                changeVisibility(mListConnectivity.get(selectedPos).isGPRS());
            }
        }
    }

	public void InitSpinnerSoftware(ArrayList<SoftwareBean> list)
	{
		ArrayList<String> listShow = new ArrayList<>();
        mListSoftware = list;
		if(mListSoftware != null)
		{
			if(mListSoftware.size() > 0)
			{
				textSoftware.setVisibility(View.VISIBLE);
				spinnerSoftware.setVisibility(View.VISIBLE);

				for(int i = 0; i < mListSoftware.size(); i++)
				{
					listShow.add(mListSoftware.get(i).getDescription());
				}

				ArrayAdapter<String> carrierAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, listShow);
				spinnerSoftware.setAdapter(carrierAdapter);
			}
		}
	}

	public void nextScreen(GenericResultBean genericResultBean)
    {
		if(genericResultBean.getRes() != null)
        {
			if(!genericResultBean.getRes().equals("OK"))
            {
                Toast.makeText(getApplicationContext(), genericResultBean.getDesc(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Unidad agregada", Toast.LENGTH_SHORT).show();
                finish();
            }
		}
	}
	
	public void changeVisibility(boolean isGPRS)
    {
		if(isGPRS)
        {
			noSimTextView.setVisibility(View.VISIBLE);
			noSimEditText.setVisibility(View.VISIBLE);
			noImeiTextView.setVisibility(View.VISIBLE);
			noImeiEditText.setVisibility(View.VISIBLE);
			carrierTextView.setVisibility(View.VISIBLE);
			spinnerCarrier.setVisibility(View.VISIBLE);
		}
		else
        {
			carrierTextView.setVisibility(View.GONE);
			spinnerCarrier.setVisibility(View.GONE);
			noSimTextView.setVisibility(View.GONE);
			noSimEditText.setVisibility(View.GONE);
			noImeiTextView.setVisibility(View.GONE);
			noImeiEditText.setVisibility(View.GONE);
		}
	}

    private ArrayList<String> idModeloList;
    private ArrayList<String> idMarcaList;
    private ArrayList<String> descModeloString;
    private ArrayList<String> isGPRSList;
    private ArrayList<String> idCarrierList;
    private ArrayList<CarriersBean>	carriersBean;
    private ArrayList<ConnectivityBean> mListConnectivity;
    private ArrayList<SoftwareBean> mListSoftware;

	private Spinner spinnerModelos;
	private Spinner spinnerCarrier;
    private Spinner spinnerConnectivity;
    private Spinner spinnerSoftware;

	InfoActualizacionBean 	infoActualizacionBean;
	String 					idAR;
	String					noAR;

    private TextView textConnectivity;
    private TextView textSoftware;
	private TextView carrierTextView;
	private TextView noSimTextView;
	private TextView noImeiTextView;

	EditText noSerieEditText;
	EditText noInventarioEditText;
	EditText idEquipoEditText;
	EditText noSimEditText;
	EditText noImeiEditText;
}