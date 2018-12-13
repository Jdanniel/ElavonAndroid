package com.artefacto.microformas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import com.artefacto.microformas.beans.SendRechazoBean;
import com.artefacto.microformas.beans.SendRechazoResponseBean;
import com.artefacto.microformas.beans.ValidateRejectClosureBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.uicomponents.DateButton;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.tasks.CierreRechazoTask;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

public class CierreRechazoActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    private boolean isClosed;

	ValidateRejectClosureBean validateRejectClosureBean;
	Spinner 	causaSpinner; 
	Spinner 	solucionSpinner; 
	Spinner 	cauRecSpinner; 
	Spinner 	espCausaRechazoSpinner; 
	View		view;
	DateButton 	dateButton;
	String 		condicion;

	SendRechazoBean sendRechazoBean;
	
	ArrayList<String> cauRecList;
	ArrayList<String> causaList;
	ArrayList<String> solucionList;
	ArrayList<String> espCausaRechazoList;
	
	TextView causaRechazoTextView;
	
	DatePicker dp;
	Button	   timeButton;
	
	TextView descripcionTrabajoTextView;
	EditText descripcionTrabajoEditText;
	
	TextView folioServicioTextView;
	EditText folioServicioEditText;
	
	TextView autorizadorTextView;
	EditText autorizadorEditText;
	
	// FIELD FOR CLAVE_RECHAZO \( '.'   )
	TextView textViewClaveRechazo;
	EditText editTextClaveRechazo;
	
	TimePickerDialog timePickDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cierre_rechazo);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

        mToolbar = (Toolbar) this.findViewById(R.id.close_denied_toolbar);
        mToolbarTitle = (TextView) this.findViewById(R.id.close_denied_toolbar_title);
        setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText(getString(R.string.title_activity_cierre_rechazo));

		Intent intent = getIntent();
		validateRejectClosureBean = (ValidateRejectClosureBean) intent.getSerializableExtra("bean");
		setValidateRejectClosureBean(validateRejectClosureBean);
		
		validateRejectClosureBean.getIdAR();
		validateRejectClosureBean.getIdServicio();
		validateRejectClosureBean.getIdCliente();
		validateRejectClosureBean.getDescMoneda();
		validateRejectClosureBean.getDescTipoCobro();
		validateRejectClosureBean.getDescTipoPrecio();
		validateRejectClosureBean.getDescripcionTrabajo();
		validateRejectClosureBean.getHorasAtencion();
		validateRejectClosureBean.getIdTipoCobro();
		validateRejectClosureBean.getIdTipoPrecio();
		validateRejectClosureBean.getIsCobrable();
		validateRejectClosureBean.getPrecioRechazo();
		validateRejectClosureBean.getIsCausaSolucionRequired();
		validateRejectClosureBean.getIsCausaRequired();
		validateRejectClosureBean.getIsSolucionRequired();
		validateRejectClosureBean.getIsFolioServicioRechazoRequired(); 
		validateRejectClosureBean.getIsOtorganteVoBoRechazoRequired(); 
		validateRejectClosureBean.getIsDescripcionTrabajoRechazoRequired();
		validateRejectClosureBean.getIsIdTipoFallaEncontradaRequired();
		validateRejectClosureBean.getIsEspecificaTipoFalla();
		validateRejectClosureBean.getIsIdCausaRechazoRequired();
		validateRejectClosureBean.getOtorganteVoBo();
		// CLAVE RECHAZO \( '.'   )
		validateRejectClosureBean.getClaveRechazo();
		validateRejectClosureBean.getIsClaveRechazoRequired();
		
		TextView cauRecTextView				= (TextView)findViewById(R.id.rechazoCauRecTextView);
		cauRecSpinner 						= (Spinner) findViewById(R.id.rechazoCauRecSpinner);
		TextView causaTextView				= (TextView)findViewById(R.id.rechazoCausaTextView);
		causaSpinner 						= (Spinner) findViewById(R.id.rechazoCausaSpinner);
		TextView solucionTextView			= (TextView)findViewById(R.id.rechazoSolucionTextView);
		solucionSpinner 					= (Spinner) findViewById(R.id.rechazoSolucionSpinner);
		causaRechazoTextView		= (TextView)findViewById(R.id.rechazoSeleccionaCausaRechazoTextView);
		espCausaRechazoSpinner				= (Spinner) findViewById(R.id.rechazoCausaRechazoSpinner);
		
		descripcionTrabajoTextView			= (TextView)findViewById(R.id.rechazoDescripcionTrabajoTextView);
		descripcionTrabajoEditText			= (EditText)findViewById(R.id.rechazoDescripcionTrabajoEditText);
		
		folioServicioTextView				= (TextView)findViewById(R.id.rechazoFolioServicioTextView);
		folioServicioEditText				= (EditText)findViewById(R.id.rechazoFolioServicioEditText);
 		
		autorizadorTextView					= (TextView)findViewById(R.id.rechazoAutorizadorTextView);
		autorizadorEditText					= (EditText)findViewById(R.id.rechazoAutorizadorEditText);
		
		TextView folioServicioTextView		= (TextView)findViewById(R.id.rechazoFolioServicioTextView);					
		EditText otorganteVoBoEditText		= (EditText)findViewById(R.id.rechazoOtorganteVoBoEditText);
		
		TextView tipoCobroTextView			= (TextView)findViewById(R.id.rechazoTipoCobroTextView);
		TextView tipoPrecioTextView			= (TextView)findViewById(R.id.rechazoTipoPrecioTextView);
		TextView monedaTextView				= (TextView)findViewById(R.id.rechazoMonedaTextView);
		TextView horasAtencionTextView		= (TextView)findViewById(R.id.rechazoHorasAtencionTextView);
		
		CheckBox cobrable					= (CheckBox)findViewById(R.id.rechazoCobrableCheckBox);
		
		Button confirmarRechazoButton		= (Button)  findViewById(R.id.rechazoConfirmarRechazoButton);
		confirmarRechazoButton.setOnClickListener(confirmarRechazoOnClickListener);
		
		//OBTENER CLAVE RECHAZO FIELD  \( '.'   )
		textViewClaveRechazo = (TextView) this.findViewById(R.id.titleClaveRechazo);
		editTextClaveRechazo = (EditText) this.findViewById(R.id.claveRechazo);
		
		timeButton = (Button)findViewById(R.id.rechazoFechaCierreButton);
		
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		//CHECK VISIBILITY FOR CLAVE_RECHAZO FIELD \( '.'   )
		if(validateRejectClosureBean.getIsClaveRechazoRequired().trim().equals("1"))
        {
			textViewClaveRechazo.setVisibility(View.VISIBLE);
			editTextClaveRechazo.setVisibility(View.VISIBLE);
		}

		condicion = "0";
		if(validateRejectClosureBean.getIsIdCausaRechazoRequired().trim().equals("1"))
        {
			do
            {
				cauRecTextView.setVisibility(View.VISIBLE);
				cauRecSpinner.setVisibility(View.VISIBLE);
				cauRecList 		= new ArrayList<>();
				ArrayList<String> cauRecSpinnerArray =  new ArrayList<String>();
				
				Cursor cursor = db.rawQuery("SELECT "	+ SQLiteHelper.CAUREC_ID_CAUREC + ","
													+ SQLiteHelper.CAUREC_DESC_CAUREC  
									 + " FROM " 	+ SQLiteHelper.CAUREC_DB_NAME
									 + " WHERE "	+ SQLiteHelper.CLIENTES_ID_CLIENTE + " = " + validateRejectClosureBean.getIdCliente(), null);
				try
                {
				   	if (cursor != null )
                    {
				   		if  (cursor.moveToFirst())
                        {
				   			do
                            {
				   				cauRecList.add(cursor.getString(0));
				   				cauRecSpinnerArray.add(cursor.getString(1));
				   			} while (cursor.moveToNext());
				   		}
				   	}
				}
				catch(Exception ex)
                {
                    Log.d("Microformas", ex.getMessage());
                }
                finally
                {
                    if(cursor != null)
                    {
                        cursor.close();
                    }

                }

				ArrayAdapter<String> cauRecAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, cauRecSpinnerArray);
				cauRecSpinner.setAdapter(cauRecAdapter);
				
				condicion = cauRecList.get(cauRecSpinner.getSelectedItemPosition());
			} while(cauRecSpinner.getSelectedItemPosition() == -1);
		}
		
		System.out.println("idServicio = " 		+ validateRejectClosureBean.getIdServicio()
				+ "\nidCliente = " 		+ validateRejectClosureBean.getIdCliente());
		
		cauRecSpinner.setOnItemSelectedListener(tipoFallaOnItemSelectedListener);

		if(validateRejectClosureBean.getIsCausaSolucionRequired().trim().equals("1"))
        {
			if(validateRejectClosureBean.getIsCausaRequired().trim().equals("1"))
            {
				causaTextView.setVisibility(View.VISIBLE);
				causaSpinner.setVisibility(View.VISIBLE);
				causaList 		= new ArrayList<>();
				ArrayList<String> causaSpinnerArray =  new ArrayList<>();
				
				Cursor cursor = db.rawQuery("SELECT c."	+ SQLiteHelper.CAUSAS_ID_CAUSA 			+ ","
													+ SQLiteHelper.CAUSAS_DESC_CAUSA 
									+ " FROM " 		+ SQLiteHelper.CAUSAS_DB_NAME 			+ " c "
									+ " LEFT JOIN " + SQLiteHelper.SERVCAU_DB_NAME 			+ " s "
									+ " ON s." 		+ SQLiteHelper.CAUSAS_ID_CAUSA 			+ " = c." 		+ SQLiteHelper.CAUSAS_ID_CAUSA
									+ " WHERE " 	+ SQLiteHelper.SERVICES_ID_SERVICIO + " = " 		+ validateRejectClosureBean.getIdServicio()
									+ " AND "		+ SQLiteHelper.CLIENTES_ID_CLIENTE 		+ " = " 		+ validateRejectClosureBean.getIdCliente(), null);
				
				try
                {
				   	if (cursor != null )
                    {
				   		if  (cursor.moveToFirst())
                        {
				   			do
                            {
				   				causaList.add(cursor.getString(0));
				   				causaSpinnerArray.add(cursor.getString(1));
				   			} while (cursor.moveToNext());
				   		}
				   	}
				}
				catch(Exception ex)
                {
                    Log.d("Microformas", ex.getMessage());
				}
                finally
                {
                    if(cursor != null)
                    {
                        cursor.close();
                    }
                }

				ArrayAdapter<String> causasAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, causaSpinnerArray);
				causaSpinner.setAdapter(causasAdapter);
			}
			
			if(validateRejectClosureBean.getIsSolucionRequired().trim().equals("1"))
            {
				solucionTextView.setVisibility(View.VISIBLE);
				solucionSpinner.setVisibility(View.VISIBLE);
				solucionList 		= new ArrayList<>();
				ArrayList<String> solucionSpinnerArray =  new ArrayList<>();

				Cursor cursor = db.rawQuery("SELECT c."	+ SQLiteHelper.SOLUCIONES_ID_SOLUCION 	+ ","
													+ SQLiteHelper.SOLUCIONES_DESC_SOLUCION 
							 + " FROM " 			+ SQLiteHelper.SOLUCIONES_DB_NAME 		+ " c "
							 + " LEFT JOIN " 		+ SQLiteHelper.SERVSOL_DB_NAME 			+ " s "
							 + " ON s." 			+ SQLiteHelper.SOLUCIONES_ID_SOLUCION 	+ " = c." 	+ SQLiteHelper.SOLUCIONES_ID_SOLUCION
							 + " WHERE " 			+ SQLiteHelper.SERVICES_ID_SERVICIO + " = " 	+ validateRejectClosureBean.getIdServicio()
							 + " AND "				+ SQLiteHelper.CLIENTES_ID_CLIENTE 		+ " = " 	+ validateRejectClosureBean.getIdCliente(), null);
				try
                {
				   	if (cursor != null )
                    {
				   		if  (cursor.moveToFirst())
                        {
				   			do
                            {
				   				solucionList.add(cursor.getString(0));
				   				solucionSpinnerArray.add(cursor.getString(1));
				   			} while (cursor.moveToNext());
				   		}
				   	}
				}
				catch(Exception ex)
                {
                    Log.d("Microfmras", ex.getMessage());
				}
                finally
                {
                    if(cursor != null)
                    {
                        cursor.close();
                    }
                }

				ArrayAdapter<String> solucionesAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, solucionSpinnerArray);
				solucionSpinner.setAdapter(solucionesAdapter);
				
			}
		}

		if(validateRejectClosureBean.getIsEspecificaTipoFalla().trim().equals("1"))
        {
			causaRechazoTextView.setVisibility(View.VISIBLE);
			espCausaRechazoSpinner.setVisibility(View.VISIBLE);
			espCausaRechazoList 	= new ArrayList<>();
			ArrayList<String> espCausaRechazoSpinnerArray 	=  new ArrayList<String>();
					
			Cursor cursor = db.rawQuery("SELECT "	+ SQLiteHelper.ESPCAUREC_ID_ESPCAUREC 		+ ","
												+ SQLiteHelper.ESPCAUREC_DESC_ESPCAUREC 
								 + " FROM " 	+ SQLiteHelper.ESPCAUREC_DB_NAME
								 + " WHERE " 	+ SQLiteHelper.ESPCAUREC_ID_CAURECPARENT 	+ " = " + condicion, null);
			try
            {
			   	if (cursor != null )
                {
			   		if  (cursor.moveToFirst())
                    {
			   			do
                        {
			   				espCausaRechazoList.add(cursor.getString(0));
			   				espCausaRechazoSpinnerArray.add(cursor.getString(1));
			   			}while (cursor.moveToNext());
			   		}
			   	}
			}
			catch(Exception ex)
            {
                Log.d("Microformas", ex.getMessage());
			}
            finally
            {
                cursor.close();
            }

			db.close();
			
			ArrayAdapter<String>  espCausaRechazoAdapter = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, espCausaRechazoSpinnerArray);
			espCausaRechazoSpinner.setAdapter(espCausaRechazoAdapter);
		}
		
		descripcionTrabajoEditText.setText(validateRejectClosureBean.getDescripcionTrabajo());
		
		if(validateRejectClosureBean.getIsFolioServicioRechazoRequired().trim().equals("1"))
        {
			folioServicioTextView.setVisibility(View.VISIBLE);
			folioServicioEditText.setVisibility(View.VISIBLE);
		}

		if(validateRejectClosureBean.getIsOtorganteVoBoRechazoRequired().trim().equals(1))
        {
			otorganteVoBoEditText.setVisibility(View.VISIBLE);
			otorganteVoBoEditText.setText(validateRejectClosureBean.getOtorganteVoBo());
		}
		
		tipoCobroTextView.setText(getString(R.string.rechazo_tipocobro_text) 			+ " " + validateRejectClosureBean.getDescTipoCobro());
		tipoPrecioTextView.setText(getString(R.string.rechazo_tipoprecio_text) 			+ " " + validateRejectClosureBean.getDescTipoPrecio());
		monedaTextView.setText(getString(R.string.rechazo_moneda_text)	 				+ " " + validateRejectClosureBean.getDescMoneda());
		horasAtencionTextView.setText(getString(R.string.rechazo_horasatencion_text) 	+ " " + validateRejectClosureBean.getHorasAtencion());
		 
		if(validateRejectClosureBean.getIsCobrable().trim().equals("1"));
			cobrable.setChecked(true);
			   
		DateButton dateButton = (DateButton)findViewById(R.id.cierreRechazoDateButton);
		
		Calendar c = Calendar.getInstance();
		
		//TODO Cambiar por la fecha de ahorita
		dateButton.onDateSet(dp, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		dateButton.setOnClickListener(dateOnClickListener);
		view = this.findViewById(android.R.id.content).getRootView();
		
		Calendar cal = Calendar.getInstance();
		String currentHour;
		String currentMinute;
		if(cal.get(Calendar.HOUR_OF_DAY) > 9)
			currentHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		else
			currentHour = "0" + String.valueOf(cal.get(Calendar.HOUR_OF_DAY));	
		
		if(cal.get(Calendar.MINUTE) > 9)
			currentMinute =  String.valueOf(cal.get(Calendar.MINUTE));
		else
			currentMinute =  "0" + String.valueOf(cal.get(Calendar.MINUTE));
		
		timeButton.setText(currentHour + ":" + currentMinute);
		timeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String time = timeButton.getText().toString();

                if (time != null && !time.equals("")) {
                    StringTokenizer st = new StringTokenizer(time, ":");
                    String timeHour = st.nextToken();
                    String timeMinute = st.nextToken();

                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new TimePickHandler(), Integer.parseInt(timeHour),
                            Integer.parseInt(timeMinute), true);
                } else {
                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new TimePickHandler(), 10, 45, true);
                }

                
                
                timePickDialog.show();
            }
        });;
        
		db.close();

        isClosed = false;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }

        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(isClosed)
        {
            Intent intentBackTo = new Intent(CierreRechazoActivity.this, RequestDetailActivity.class);
            setResult(Activity.RESULT_OK, intentBackTo);
        }
        else
        {
            setResult(Activity.RESULT_CANCELED);
        }

        super.onBackPressed();
    }

	OnClickListener dateOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			showDateDialog();
		}
	};
	
	public void showDateDialog(){
		dateButton = (DateButton)findViewById(R.id.cierreRechazoDateButton);
		dateButton.onClick(view);
	}
	
	private class TimePickHandler implements OnTimeSetListener {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String hours ="00";
			String minutes = "00";
			
			if(hourOfDay < 10)
				hours = "0" + hourOfDay;
			else
				hours = String.valueOf(hourOfDay);
			
			if(minute < 10)
				minutes =  "0" + minute;
			else
				minutes = String.valueOf(minute);
			
			timeButton.setText(hours + ":" + minutes);
	        timePickDialog.hide();
	    }
	}

	OnClickListener confirmarRechazoOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			sendRechazo();
		}
	};
	
//	public void goToList(){
//		Intent intent = new Intent(this, RequestListActivity.class);
//		intent.putExtra("type", Constants.DATABASE_ABIERTAS);
//		startActivity(intent);
//	}
	
	public void sendRechazo() {
		ProgressDialog progressDialog = new ProgressDialog(CierreRechazoActivity.this);
    	progressDialog.setMessage("Enviando solicitud de rechazo");
		progressDialog.setCancelable(false);
		boolean proceed = true;
		ArrayList<String> neededFields = new ArrayList<String>();
		DateButton dateButton = (DateButton)findViewById(R.id.cierreRechazoDateButton);
		
		validateRejectClosureBean = getValidateRejectClosureBean();
		sendRechazoBean 		  = new SendRechazoBean();
	
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		sendRechazoBean.setIdAR(validateRejectClosureBean.getIdAR());

		String day 	 = dateButton.getText().toString().substring(0,2);
		String month = setNumericMonth(dateButton.getText().toString().substring(3, 7));
		String year	 = dateButton.getText().toString().substring(8, 12);
		
		String hour 	= timeButton.getText().toString().substring(0, 2);
		String minute 	= timeButton.getText().toString().substring(3, 5);
		
		sendRechazoBean.setFecCierre(year + "-" + day + "-" + month + " " + hour + ":" + minute + ":00" );
		
		try{
			sendRechazoBean.setIsIdCausaRechazoRequired(Integer.valueOf(validateRejectClosureBean.getIsIdCausaRechazoRequired()));
		}catch(Exception e){
			sendRechazoBean.setIsIdCausaRechazoRequired(0);
		}
		try{
			sendRechazoBean.setIdCausaRechazo(cauRecList.get(cauRecSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			sendRechazoBean.setIdCausaRechazo("");
		}
		try{
			sendRechazoBean.setEspecificaCausaRechazoRequired(Integer.valueOf(validateRejectClosureBean.getIsEspecificaTipoFalla()));
		}catch(Exception e){
			sendRechazoBean.setEspecificaCausaRechazoRequired(0);
		}
		try{
			sendRechazoBean.setIdEspecificaCausaRechazo(espCausaRechazoList.get(espCausaRechazoSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			sendRechazoBean.setIdEspecificaCausaRechazo("");
		}
		try{
			sendRechazoBean.setIsCausaSolucionRequired(Integer.valueOf(validateRejectClosureBean.getIsSolucionRequired()));
		}catch(Exception e){
			sendRechazoBean.setIsCausaSolucionRequired(0);
		}
		try{
			sendRechazoBean.setIdCausa(causaList.get(causaSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			sendRechazoBean.setIdCausa("0");
		}
		try{
			sendRechazoBean.setIdSolucion(solucionList.get(solucionSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			sendRechazoBean.setIdSolucion("0");
		}
		try{
			sendRechazoBean.setIsDescripcionTrabajoRechazoRequired(Integer.valueOf(validateRejectClosureBean.getIsDescripcionTrabajoRechazoRequired())); 
		}catch(Exception e){
			sendRechazoBean.setIsDescripcionTrabajoRechazoRequired(0);
		}
		
		if(sendRechazoBean.getIsDescripcionTrabajoRechazoRequired() == 1){
			if(descripcionTrabajoEditText.getText().toString().equals("") || descripcionTrabajoEditText.getText().toString() == null){
				proceed = false;
				descripcionTrabajoTextView.setTextColor(Color.RED);
					
				neededFields.add("Descripción Trabajo");
			}
			else{
				sendRechazoBean.setDescriptionTrabajo(descripcionTrabajoEditText.getText().toString());
				descripcionTrabajoTextView.setTextColor(Color.BLACK);
			}
		}

		try{
			sendRechazoBean.setIsFolioServicioRechazoRequired(Integer.valueOf(validateRejectClosureBean.getIsFolioServicioRechazoRequired())); 
		}catch(Exception e){
			sendRechazoBean.setIsFolioServicioRechazoRequired(0);
		}
		
		// SET VALUES FOR CLAVE_RECHAZO IN BEAN \( '.'   )
		try {
			sendRechazoBean.setIsClaveRechazoRequired(Integer.valueOf(validateRejectClosureBean.getIsClaveRechazoRequired())); 
		}catch(Exception e){
			sendRechazoBean.setIsFolioServicioRechazoRequired(0);
		}
		
		
		if(sendRechazoBean.getIsFolioServicioRechazoRequired() == 1){
			if(folioServicioEditText.getText().toString().equals("") || folioServicioEditText.getText().toString() == null){
				proceed = false;
				folioServicioTextView.setTextColor(Color.RED);
					
				neededFields.add("Folio Servicio");
			}
			else{
				sendRechazoBean.setFolioServicioRechazo(folioServicioEditText.getText().toString());
				folioServicioTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(autorizadorEditText.getText().toString().equals("") || autorizadorEditText.getText().toString() == null){
			proceed = false;
			autorizadorTextView.setTextColor(Color.RED);
				
			neededFields.add("Autorizador");
		}
		else{
			sendRechazoBean.setAutorizador(autorizadorEditText.getText().toString());
			autorizadorTextView.setTextColor(Color.BLACK);
		}
		
		sendRechazoBean.setIsOtorganteVoBoRechazoRequired(Integer.valueOf(validateRejectClosureBean.getIsOtorganteVoBoRechazoRequired()));
		sendRechazoBean.setOtorganteVoBoRechazo(validateRejectClosureBean.getOtorganteVoBo());
		sendRechazoBean.setIdTecnico(sharedPreferences.getString(Constants.PREF_USER_ID, ""));
		sendRechazoBean.setPrecioRechazo(validateRejectClosureBean.getPrecioRechazo());
		
		//Hace la comparación de la fecha
		String cierreFormatString = "EEE MMM dd HH:mm:ss zzz yyyy";
		SimpleDateFormat df = new SimpleDateFormat(cierreFormatString);

		Date cierreDate = new Date();
		Date actualDate = new Date();
		Date inicioDate = new Date();
				
		//Primero, cambiamos la fecha de formato de FechaInicio
		String inicioDay 	= validateRejectClosureBean.getFecInicio().substring(0, 2);
		String inicioMonth 	= validateRejectClosureBean.getFecInicio().substring(3, 5);
		String inicioYear 	= validateRejectClosureBean.getFecInicio().substring(6, 10);
				
		SimpleDateFormat cierreFormat 	= new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
		SimpleDateFormat inicioFormat 	= new SimpleDateFormat("MM/dd/yyyy");
				
		try {
			inicioDate = inicioFormat.parse(inicioMonth + "/" + inicioDay + "/" + inicioYear + " 00:00:00 AM");
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//TODO convertir FecInicio a Date(que sea igual al cierre)
		try{
			Calendar c = Calendar.getInstance();
			String formattedDate = df.format(c.getTime());
			actualDate = df.parse(formattedDate);
			cierreDate = cierreFormat.parse(sendRechazoBean.getFecCierre());
		}
		catch(Exception e){}

		if(espCausaRechazoSpinner.getSelectedItemPosition() == 0) {
			proceed = false;
			neededFields.add("Especifique la Causa del Rechazo");
			causaRechazoTextView.setTextColor(Color.RED);
		} else {
			causaRechazoTextView.setTextColor(Color.BLACK);
		}
		
		if(cierreDate.before(actualDate) || cierreDate.equals(actualDate)){
					
			if(cierreDate.before(inicioDate)){
				//No cumple la condición
				proceed = false;
				neededFields.add("Fecha Cierre (debe ser mayor a la Fecha de Inicio)");
			}
			else{
				//Cumple todas las condiciones
			}
		}
		else{
			//No cumple la condición
			proceed = false;
			neededFields.add("Fecha Cierre (debe ser menor a la Fecha Actual)");
		}
		
		//VERIFIED IF CLAVE RECHAZO IS REQUIRED AND ADD FIELD TO BEAN \( '.'   )
		//VALIDATE CLAVE RACHAZO AS ONLY NUMBERS AND LETTERS \( '.'   )
		if(sendRechazoBean.getIsClaveRechazoRequired() == 1) {
			if(editTextClaveRechazo.getText().toString().equals("") || editTextClaveRechazo.getText().toString() == null) {
				proceed = false;
				textViewClaveRechazo.setTextColor(Color.RED);
					
				neededFields.add("Clave Rechazo");
			} else{
				sendRechazoBean.setClaveRechazo(editTextClaveRechazo.getText().toString());
				textViewClaveRechazo.setTextColor(Color.BLACK);
			}
		}
		
		if(proceed){
			CierreRechazoTask ctask = new CierreRechazoTask(CierreRechazoActivity.this, progressDialog);
	    	setSendRechazoBean(sendRechazoBean);
	    	ctask.execute(sendRechazoBean);
		}
		
		else{
			String text = "";
			for(int i = 0; i < neededFields.size(); i++){
				if(i > 0)
					text += ", ";
				
				text += neededFields.get(i);
			}
			text += ".";
			
			Toast.makeText(getApplicationContext(), "Falta rellenar campos: " + text, Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setValidateRejectClosureBean(ValidateRejectClosureBean validateRejectClosureBean){
		this.validateRejectClosureBean = validateRejectClosureBean;
	}
	
	public ValidateRejectClosureBean getValidateRejectClosureBean(){
		return validateRejectClosureBean;
	}
	
	public void sendRechazoAnswer(SendRechazoResponseBean sendRechazoResponseBean){
		try{
			if(sendRechazoResponseBean.getVal().equals("SI"))
            {
				final SendRechazoResponseBean response = sendRechazoResponseBean;
				final CierreRechazoActivity cierreRechazo = this;
				this.runOnUiThread(new Runnable()
                {
					public void run()
                    {
						String description = response.getDesc();
						Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
                        isClosed = true;
                        onBackPressed();
					}
				});
			}
			else
            {
				Toast.makeText(getApplicationContext(), sendRechazoResponseBean.getDesc(), Toast.LENGTH_SHORT).show();
			}
		}
        catch(Exception e)
        {
			Toast.makeText(getApplicationContext(), "Se encontró un error al cerrar la solicitud, inténtelo más tarde", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setSendRechazoBean(SendRechazoBean sendRechazoBean){
		this.sendRechazoBean = sendRechazoBean;
	}
	public SendRechazoBean getSendRechazoBean(){
		return sendRechazoBean;
	}
	
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
	
	OnItemSelectedListener tipoFallaOnItemSelectedListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			changeEspCauRecSpinner();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	};
	
	public void changeEspCauRecSpinner(){
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(getApplicationContext(), null);
        SQLiteDatabase 	dbRequest 		= sqliteHelper.getReadableDB();
        
        System.out.println("condicion = " + condicion);
        String condicion = cauRecList.get(cauRecSpinner.getSelectedItemPosition());
        
		ArrayList<String> espCausaRechazoSpinnerArray =  new ArrayList<String>();
		Cursor c = dbRequest.rawQuery("SELECT "	+ SQLiteHelper.ESPCAUREC_ID_ESPCAUREC 		+ "," 
												+ SQLiteHelper.ESPCAUREC_DESC_ESPCAUREC 
									+ " FROM " 	+ SQLiteHelper.ESPCAUREC_DB_NAME
									+ " WHERE " + SQLiteHelper.ESPCAUREC_ID_CAURECPARENT 	+ " = " + condicion, null);
        
		espCausaRechazoList = new ArrayList<String>();
		espCausaRechazoList.add("-1");
		espCausaRechazoSpinnerArray.add("...");
		
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do{
				espCausaRechazoList.add(c.getString(0));
				espCausaRechazoSpinnerArray.add(c.getString(1));
			}while(c.moveToNext());
		}
		c.close();
		dbRequest.close();
		
		ArrayAdapter<String>  espCausaRechazoAdapter = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, espCausaRechazoSpinnerArray);
		espCausaRechazoSpinner.setAdapter(espCausaRechazoAdapter);
	}
}