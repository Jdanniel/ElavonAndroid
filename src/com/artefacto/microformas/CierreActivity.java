package com.artefacto.microformas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import com.artefacto.microformas.beans.DescripcionTrabajoBean;
import com.artefacto.microformas.beans.SendExitoResponseBean;
import com.artefacto.microformas.beans.ValidateClosureBean;
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
import com.artefacto.microformas.tasks.CierreExitoTask;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CierreActivity extends AppCompatActivity
{
	ValidateClosureBean validateClosureBean;
	TimePickerDialog 	timePickDialog;
	DatePicker			dp;
	String				idServicio;
	String				fechaAtencion;

    private boolean isClosed;
    private String mDescFailFoundSelected;

	ArrayList<String> causaList;
	ArrayList<String> solucionList;
	ArrayList<String> codigos0List;
	ArrayList<String> codigos0ClaveList;
	ArrayList<String> codigos1List;
	ArrayList<String> codigos1ClaveList;
	ArrayList<String> codigos2List;
	ArrayList<String> codigos2ClaveList;
	ArrayList<String> modelosList;
	ArrayList<String> simList;
	ArrayList<String> soporteList;
	ArrayList<String> boletinList;
	ArrayList<String> tipoFallaList;
	ArrayList<String> espTipoFallaList;
	ArrayList<String> listFailsFoundId;
	ArrayList<String> listFailsFoundDesc;
	ArrayList<DescripcionTrabajoBean> descripcionTrabajoBeanArray;
	ArrayList<String> listCalidadBillete;
	ArrayList<String> listCondicionSite;

	View		view;
	TextView 	causaTextView ;
	Spinner 	causaSpinner;
	TextView	solucionTextView;
	Spinner 	solucionSpinner;
	
	TextView 	descripcionTextView;
	Spinner		descripcionSpinner;
	EditText 	descripcionEditText;
	
	TextView 	atiendeTextView;
	EditText 	atiendeEditText;
	
	TextView 	fechaCierreTextView; 
	DateButton 	fechaCierreDateButton;
	
	TextView 	tasTextView;
	EditText 	tasEditText;
	
	TextView 	codigoIntervencionTextView;
	Spinner  	codigoIntervencion0Spinner;
	Spinner  	codigoIntervencion1Spinner;
	Spinner  	codigoIntervencion2Spinner;
	
	TextView 	noEquipoTextView;
	EditText 	noEquipoEditText;
	
	TextView 	otorganteFolioTASTextView;
	EditText 	otorganteFolioTASEditText;
	
	TextView 	noSerieTextView;
	EditText 	noSerieEditText;
	
	TextView 	noInventarioTextView;
	EditText 	noInventarioEditText;
	
	TextView 	modeloTextView;
	Spinner  	modeloSpinner;
	
	TextView 	fechaLlegadaTextView;
	DateButton 	fechaLlegadaDateButton;
	
	TextView 	fechaLlegadaTercerosTextView;
	DateButton 	fechaLlegadaTercerosDateButton;
	
	TextView 	folioServicioTextView;
	EditText 	folioServicioEditText;
	
	TextView 	fechaInicioIngenierosTextView;
	DateButton 	fechaInicioIngenierosDateButton;
	
	TextView 	fechaFinIngenierosTextView;
	DateButton 	fechaFinIngenierosDateButton;
	
	TextView 	otorganteVoBoTextView;
	EditText 	otorganteVoBoEditText;
	
	TextView 	otorganteVoBoTercerosTextView;
	EditText 	otorganteVoBoTercerosEditText;
	
	TextView intensidadSenalTextView;
	Button 	 intensidadSenalButton;
	
	TextView simReemplazadaTextView;
	Spinner  simReemplazadaSpinner;
	
	TextView digitoVerTextView;
	EditText digitoVerEditText;
	
	TextView tipoFallaEncontradaTextView;
	Spinner  tipoFallaSpinner;
	
	TextView especificaTipoFallaTextView;
	Spinner  especificaTipoFallaSpinner;
	
	TextView fallaEncontradaTextView;
	Spinner  mSpinnerFailsFound;
    EditText fallaEncontradaEditText;
	
	TextView voBoClienteTextView;
	EditText voBoClienteEditText;
	
	TextView voltajeNeutroTextView;
	EditText voltajeNeutroEditText;
	TextView voltajeTierraTextView;
	EditText voltajeTierraEditText;
	TextView voltajeTierraNeutroTextView;
	EditText voltajeTierraNeutroEditText;
	
	TextView folioValidacionTextView;
	EditText folioValidacionEditText;
	
	TextView folioTIRTextView;
	EditText folioTIREditText;
	
	TextView motivoCobroTextView;
	EditText motivoCobroEditText;
	
	TextView soporteClienteTextView;
	Spinner	 soporteClienteSpinner;
	
	TextView boletinTextView;
	Spinner  boletinSpinner;
	
	TextView otorganteSoporteTextView;
	EditText otorganteSoporteEditText;
	
	TextView tipoCobroTextView;
	
	TextView tipoPrecioTextView;

	TextView monedaTextView;
	
	TextView horasAtencionTextView;
	
	TextView horarioUptimeTextView;
	EditText horarioUptimeEditText;
	
	TextView horarioAccesoTextView;
	EditText horarioAccesoEditText;
	
	TextView enContratoTextView;
	EditText enContratoEditText;
	
	TextView afectacionUptimeTextView;
	EditText afectacionUptimeEditText;
	
	CheckBox cobrableCheckBox;
	
	TextView precioServicioTextView;
	
	TextView cadenaCierreTextView;
	EditText cadenaCierreEditText;
	
	TextView instalacionLlavesTextView;
	EditText instalacionLlavesEditText;
	
	TextView tipoLlaveTextView;
	EditText tipoLlaveEditText;
	
	TextView statusInstalacionTextView;
	EditText statusInstalacionEditText;
	
	TextView personaLlavesATextView;
	EditText personaLlavesAEditText;
	
	TextView personaLlavesBTextView;
	EditText personaLlavesBEditText;
	
	TextView tipoPasswordTextView;
	EditText tipoPasswordEditText;
	
	TextView tipoTecladoTextView;
	EditText tipoTecladoEditText;
	
	TextView versionTecladoTextView;
	EditText versionTecladoEditText;
	
	TextView procesadorTextView;
	EditText procesadorEditText;
	
	TextView velocidadProcesadorTextView;
	EditText velocidadProcesadorEditText;
	
	TextView memoriaTextView;
	EditText memoriaEditText;
	
	TextView discoDuroTextView;
	EditText discoDuroEditText;
	
	TextView monitorTextView;
	EditText monitorEditText;
	
	TextView lectorTarjetaTextView;
	EditText lectorTarjetaEditText;
	
	TextView aplicacionTextView;
	EditText aplicacionEditText;
	
	TextView versionTextView;
	EditText versionEditText;
	
	TextView cajaTextView;
	EditText cajaEditText;

	TextView textViewSim;
	EditText editTextSim;

	TextView calidadBilleteTextView;
	Spinner calidadBilleteSpinner;

	TextView condicionBilleteTextView;
	Spinner condicionBilleteSpinner;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;

	String idCliente;
	Button timeButton;
	Button llegadaTimeButton;
	Button llegadaTercerosTimeButton;
	Button iniIngenierosTimeButton;
	Button finIngenierosTimeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cierre);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

        mToolbar = (Toolbar) this.findViewById(R.id.close_success_toolbar);
        mToolbarTitle = (TextView) this.findViewById(R.id.close_success_toolbar_title);
        setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText(getString(R.string.title_activity_cierre));

        isClosed = false;
        mDescFailFoundSelected = "";
		
		causaTextView 		= (TextView)findViewById(R.id.cierresCausaTextView);
		solucionTextView 	= (TextView)findViewById(R.id.cierresSolucionextView);
		solucionSpinner 	= (Spinner)findViewById(R.id.cierresSolucionSpinner);
		causaSpinner 		= (Spinner)findViewById(R.id.cierresCausaSpinner);
		
		descripcionTextView = (TextView)findViewById(R.id.cierresDescripcionTextView);
		descripcionSpinner	= (Spinner)findViewById(R.id.cierresDescripcionSpinner);
		descripcionEditText = (EditText)findViewById(R.id.cierresDescripcionEditText);
		
		atiendeTextView 	= (TextView)findViewById(R.id.cierresAtiendeTextView);
		atiendeEditText 	= (EditText)findViewById(R.id.cierresAtiendeEditText);
		
		fechaCierreTextView 	= (TextView)findViewById(R.id.cierresFechaCierreTextView);
		fechaCierreDateButton 	= (DateButton)findViewById(R.id.cierresFechaCierreDateButton);
		timeButton 				= (Button)findViewById(R.id.cierresTimeButton);
		
		codigoIntervencionTextView 	= (TextView)findViewById(R.id.cierresCodigoIntervencionTextView);
		codigoIntervencion0Spinner	= (Spinner)findViewById(R.id.cierresCodigos0Spinner);
		codigoIntervencion1Spinner	= (Spinner)findViewById(R.id.cierresCodigos1Spinner);
		codigoIntervencion2Spinner	= (Spinner)findViewById(R.id.cierresCodigos2Spinner);
		
		tasTextView = (TextView)findViewById(R.id.cierresFolioTASTextView);
		tasEditText = (EditText)findViewById(R.id.cierresFolioTASEditText);
		
		otorganteFolioTASTextView = (TextView)findViewById(R.id.cierresOtorganteFolioTASTextView);
		otorganteFolioTASEditText = (EditText)findViewById(R.id.cierresOtorganteFolioTasEditText);
		
		noEquipoTextView = (TextView)findViewById(R.id.cierresNoEquipoTextView);
		noEquipoEditText = (EditText)findViewById(R.id.cierresNoEquipoEditText);
		
		noSerieTextView = (TextView)findViewById(R.id.cierresNoSerieTextView);
		noSerieEditText = (EditText)findViewById(R.id.cierresNoSerieEditText);
		
		noInventarioTextView = (TextView)findViewById(R.id.cierresNoInventarioTextView);
		noInventarioEditText = (EditText)findViewById(R.id.cierresNoInventarioEditText);
		
		modeloTextView 	= (TextView)findViewById(R.id.cierresModeloTextView);
		modeloSpinner 	= (Spinner)findViewById(R.id.cierresModeloSpinner);
		
		fechaLlegadaTextView 	= (TextView)findViewById(R.id.cierresFechaLlegadaTextView);
		fechaLlegadaDateButton 	= (DateButton)findViewById(R.id.cierresFechaLlegadaDateButton);
		llegadaTimeButton  		= (Button)findViewById(R.id.cierresFechaLlegadaTimeButton);
		
		fechaLlegadaTercerosTextView 	= (TextView)findViewById(R.id.cierresFechaLlegadaTercerosTextView);
		fechaLlegadaTercerosDateButton 	= (DateButton)findViewById(R.id.cierresFechaLlegadaTercerosDateButton);
		llegadaTercerosTimeButton  		= (Button)findViewById(R.id.cierresFechaLlegadaTercerosTimeButton);
		
		folioServicioTextView = (TextView)findViewById(R.id.cierresFolioServicioTextView);
		folioServicioEditText = (EditText)findViewById(R.id.cierresFolioServicioEditText);
		
		fechaInicioIngenierosTextView 		= (TextView)findViewById(R.id.cierresFechaInicioIngenierosTextView);
		fechaInicioIngenierosDateButton 	= (DateButton)findViewById(R.id.cierresFechaInicioIngenierosDateButton);
		iniIngenierosTimeButton				= (Button)findViewById(R.id.cierresFechaInicioIngenierosTimeButton);
		
		fechaFinIngenierosTextView 		= (TextView)findViewById(R.id.cierresFechaFinIngenierosTextView);
		fechaFinIngenierosDateButton 	= (DateButton)findViewById(R.id.cierresFechaFinIngenierosDateButton);
		finIngenierosTimeButton  		= (Button)findViewById(R.id.cierresFechaFinIngenierosTimeButton);
		
		otorganteVoBoTextView = (TextView)findViewById(R.id.cierresOtorganteVoBoTextView);
		otorganteVoBoEditText = (EditText)findViewById(R.id.cierresOtorganteVoBoEditText);
		
		otorganteVoBoTercerosTextView = (TextView)findViewById(R.id.cierresOtorganteVoBoTercerosTextView);
		otorganteVoBoTercerosEditText = (EditText)findViewById(R.id.cierresOtorganteVoBoTercerosEditText);
		
		intensidadSenalTextView = (TextView)findViewById(R.id.cierresIntensidadSenalTextView);
		intensidadSenalButton 	= (Button)findViewById(R.id.cierresIntensidadSenalButton);
		
		intensidadSenalTextView = (TextView)findViewById(R.id.cierresIntensidadSenalTextView);
		intensidadSenalButton 	= (Button)findViewById(R.id.cierresIntensidadSenalButton);
		
		simReemplazadaTextView 	= (TextView)findViewById(R.id.cierresSimReemplazadaTextView);
		simReemplazadaSpinner 	= (Spinner)findViewById(R.id.cierresSimReemplazadaSpinner);
		
		digitoVerTextView = (TextView)findViewById(R.id.cierresDigitoVerificadorTextView);
		digitoVerEditText = (EditText)findViewById(R.id.cierresDigitoVerificadorEditText);
		
		tipoFallaEncontradaTextView = (TextView)findViewById(R.id.cierresTipoFallaEncontradaTextView);
		tipoFallaSpinner = (Spinner)findViewById(R.id.cierresTipoFallaSpinner);
		
		especificaTipoFallaTextView = (TextView)findViewById(R.id.cierresEspecificaTipoFallaTextView);
		especificaTipoFallaSpinner = (Spinner)findViewById(R.id.cierresEspecificaTipoFallaSpinner);
		
		fallaEncontradaTextView = (TextView)findViewById(R.id.cierresFallaEncontradaTextView);
        mSpinnerFailsFound = (Spinner) this.findViewById(R.id.close_success_spinner_fails_found);
        mSpinnerFailsFound.setOnItemSelectedListener(onFailFoundItemSelected);
        fallaEncontradaEditText = (EditText) this.findViewById(R.id.cierresFallaEncontradaEditText);

		
		voBoClienteTextView = (TextView)findViewById(R.id.cierresVoBoClienteTextView);
		voBoClienteEditText = (EditText)findViewById(R.id.cierresVoBoClienteEditText);
		
		voltajeNeutroTextView 		= (TextView)findViewById(R.id.cierresVoltajeNeutroTextView);
		voltajeNeutroEditText 		= (EditText)findViewById(R.id.cierresVoltajeNeutroEditText);
		voltajeTierraTextView 		= (TextView)findViewById(R.id.cierresVoltajeTierraTextView);
		voltajeTierraEditText 		= (EditText)findViewById(R.id.cierresVoltajeTierraEditText);
		voltajeTierraNeutroTextView = (TextView)findViewById(R.id.cierresVoltajeTierraNeutroTextView);
		voltajeTierraNeutroEditText = (EditText)findViewById(R.id.cierresVoltajeTierraNeutroEditText);
		
		folioValidacionTextView = (TextView)findViewById(R.id.cierresFolioValidacionTextView);
		folioValidacionEditText = (EditText)findViewById(R.id.cierresFolioValidacionEditText);
		
		folioTIRTextView = (TextView)findViewById(R.id.cierresFolioTIRTextView);
		folioTIREditText = (EditText)findViewById(R.id.cierresFolioTIREditText);
		
		folioTIRTextView = (TextView)findViewById(R.id.cierresFolioTIRTextView);
		folioTIREditText = (EditText)findViewById(R.id.cierresFolioTIREditText);
		
		motivoCobroTextView = (TextView)findViewById(R.id.cierresMotivoCobroTextView);
		motivoCobroEditText = (EditText)findViewById(R.id.cierresMotivoCobroEditText);
		
		soporteClienteTextView = (TextView)findViewById(R.id.cierresSoporteClienteTextView);
		soporteClienteSpinner = (Spinner)findViewById(R.id.cierresSoporteClienteSpinner);
		
		boletinTextView = (TextView)findViewById(R.id.cierresBoletinTextView);
		boletinSpinner = (Spinner)findViewById(R.id.cierresBoletinSpinner);
		
		otorganteSoporteTextView = (TextView)findViewById(R.id.cierresOtorganteSoporteTextView);
		otorganteSoporteEditText = (EditText)findViewById(R.id.cierresOtorganteSoporteEditText);
		
		tipoCobroTextView = (TextView)findViewById(R.id.cierresTipoCobroTextView);
		
		tipoPrecioTextView = (TextView)findViewById(R.id.cierresTipoPrecioTextView);

		monedaTextView = (TextView)findViewById(R.id.cierresMonedaTextView);
		
		horasAtencionTextView = (TextView)findViewById(R.id.cierresHorasAtencionTextView);
		
		horarioUptimeTextView = (TextView)findViewById(R.id.cierresHorarioUptimeTextView);
		horarioUptimeEditText = (EditText)findViewById(R.id.cierresHorarioUptimeEditText);
		
		horarioAccesoTextView = (TextView)findViewById(R.id.cierresHorarioAccesoTextView);
		horarioAccesoEditText = (EditText)findViewById(R.id.cierresHorarioAccesoEditText);
		
		enContratoTextView = (TextView)findViewById(R.id.cierresEnContratoTextView);
		enContratoEditText = (EditText)findViewById(R.id.cierresEnContratoEditText);
		
		afectacionUptimeTextView = (TextView)findViewById(R.id.cierresAfectacionUptimeTextView);
		afectacionUptimeEditText = (EditText)findViewById(R.id.cierresAfectacionUptimeEditText);
		
		cobrableCheckBox = (CheckBox)findViewById(R.id.cierresCobrableCheckBox);
		
		precioServicioTextView = (TextView)findViewById(R.id.cierresPrecioServicioTextView);
		
		cadenaCierreTextView = (TextView)findViewById(R.id.cierresCadenaCierreTextView);
		cadenaCierreEditText = (EditText)findViewById(R.id.cierresCadenaCierreEditText);
		
		instalacionLlavesTextView = (TextView)findViewById(R.id.cierresInstalacionLlavesTextView);
		instalacionLlavesEditText = (EditText)findViewById(R.id.cierresInstalacionLlavesEditText);
		
		tipoLlaveTextView = (TextView)findViewById(R.id.cierresTipoLlavesCargadosTextView);
		tipoLlaveEditText = (EditText)findViewById(R.id.cierresTipoLlavesCargadosEditText);
		
		statusInstalacionTextView = (TextView)findViewById(R.id.cierresStatusInstalacionTextView);
		statusInstalacionEditText = (EditText)findViewById(R.id.cierresStatusInstalacionEditText);
		
		personaLlavesATextView = (TextView)findViewById(R.id.cierresNombrePersonaATextView);
		personaLlavesAEditText = (EditText)findViewById(R.id.cierresNombrePersonaAEditText);
		
		personaLlavesBTextView = (TextView)findViewById(R.id.cierresNombrePersonaBTextView);
		personaLlavesBEditText = (EditText)findViewById(R.id.cierresNombrePersonaBEditText);
		
		tipoPasswordTextView = (TextView)findViewById(R.id.cierresTipoPasswordTextView);
		tipoPasswordEditText = (EditText)findViewById(R.id.cierresTipoPasswordEditText);
		
		tipoTecladoTextView = (TextView)findViewById(R.id.cierresTipoTecladoTextView);
		tipoTecladoEditText = (EditText)findViewById(R.id.cierresTipoTecladoEditText);
		
		versionTecladoTextView = (TextView)findViewById(R.id.cierresVersionTecladoEPPTextView);
		versionTecladoEditText = (EditText)findViewById(R.id.cierresVersionTecladoEppEditText);
		
		procesadorTextView = (TextView)findViewById(R.id.cierresProcesadorTextView);
		procesadorEditText = (EditText)findViewById(R.id.cierresProcesadorEditText);
		
		velocidadProcesadorTextView = (TextView)findViewById(R.id.cierresVelocidadProcesadorTextView);
		velocidadProcesadorEditText = (EditText)findViewById(R.id.cierresVelocidadProcesadorEditText);
		
		memoriaTextView = (TextView)findViewById(R.id.cierresMemoriaTextView);
		memoriaEditText = (EditText)findViewById(R.id.cierresMemoriaEditText);
		
		discoDuroTextView = (TextView)findViewById(R.id.cierresCapacidadDiscoDuroTextView);
		discoDuroEditText = (EditText)findViewById(R.id.cierresCapacidadDiscoDuroEditText);
		
		monitorTextView = (TextView)findViewById(R.id.cierresMonitorTextView);
		monitorEditText = (EditText)findViewById(R.id.cierresMonitorEditText);
		
		lectorTarjetaTextView = (TextView)findViewById(R.id.cierresLectorTarjetaTextView);
		lectorTarjetaEditText = (EditText)findViewById(R.id.cierresLectorTarjetaEditText);
		
		aplicacionTextView = (TextView)findViewById(R.id.cierresAplicacionTextView);
		aplicacionEditText = (EditText)findViewById(R.id.cierresAplicacionEditText);
		
		versionTextView = (TextView)findViewById(R.id.cierresVersionTextView);
		versionEditText = (EditText)findViewById(R.id.cierresVersionEditText);
		
		cajaTextView = (TextView)findViewById(R.id.cierresCajaTextView);
		cajaEditText = (EditText)findViewById(R.id.cierresCajaEditText);

		textViewSim = (TextView) this.findViewById(R.id.titleNoSim);
		editTextSim = (EditText) this.findViewById(R.id.noSim);

		//SE AGREGAN NUEVOS SPINNERS 31/01/2018

		calidadBilleteTextView = (TextView) findViewById(R.id.calidadBilleteTextView);
		calidadBilleteSpinner = (Spinner) findViewById(R.id.calidadBilleteSpinner);

		condicionBilleteTextView = (TextView) findViewById(R.id.condicionSiteTextView);
		condicionBilleteSpinner = (Spinner) findViewById(R.id.condicionSiteSpinner);
		
		Button confirmExitoButton = (Button)findViewById(R.id.cierresConfirmarExitoButton);
		confirmExitoButton.setOnClickListener(confirmarExitoOnClickListener);
		
		Intent intent = getIntent();
		validateClosureBean = (ValidateClosureBean) intent.getSerializableExtra("bean");

		descripcionTrabajoBeanArray = (ArrayList<DescripcionTrabajoBean>)intent.getSerializableExtra("descbean");
		setValidateClosureBean(validateClosureBean);
		
		especificaTipoFallaSpinner.setOnItemSelectedListener(tipoFallaDescripcionOnItemSelectedListener);
		
		idCliente = validateClosureBean.getIdCliente();
		
		causaList 			= new ArrayList<>();
		solucionList 		= new ArrayList<>();
		codigos0List 		= new ArrayList<>();
		codigos0ClaveList 	= new ArrayList<>();
		codigos1List 		= new ArrayList<>();
		codigos1ClaveList 	= new ArrayList<>();
		codigos2List 		= new ArrayList<>();
		codigos2ClaveList 	= new ArrayList<>();
		modelosList 		= new ArrayList<>();
		simList 			= new ArrayList<>();
		soporteList 		= new ArrayList<>();
		boletinList 		= new ArrayList<>();
		tipoFallaList 		= new ArrayList<>();
		espTipoFallaList 	= new ArrayList<>();
        listFailsFoundId 	= new ArrayList<>();
        listFailsFoundDesc 	= new ArrayList<>();
		listCalidadBillete 	= new ArrayList<>();
		listCondicionSite   = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        String strDate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/"
            + cal.get(Calendar.DAY_OF_MONTH);
        fechaCierreDateButton.setText(strDate);

        SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	dbRequest 		= sqliteHelper.getWritableDB();
        String[]		campos			= new String[]{SQLiteHelper.REQUESTS_ID_SERVICIO,SQLiteHelper.REQUESTS_FEC_ATENCION};
        String[]		args			= new String[]{validateClosureBean.getIdAR()};
        
        Cursor cursor = dbRequest.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.REQUESTS_ID_REQUEST + "=?",
            args, null, null, null);
        
        if (cursor.moveToFirst())
        {//Recorremos el cursor hasta que no haya más registros
			do
            {
				idServicio = cursor.getString(0);
				fechaAtencion = cursor.getString(1);

			} while(cursor.moveToNext());
		}
        cursor.close();

        //SE VALIDAR BANDERA CALIDAD DE BILLETE 31/01/2018
        if(validateClosureBean.getIsCalidadBilleteRequired().equals("1")){
        	calidadBilleteTextView.setVisibility(View.VISIBLE);
        	calidadBilleteSpinner.setVisibility(View.VISIBLE);

        	ArrayList<String> calidadSpinnerArray = new ArrayList<>();

        	cursor = dbRequest.rawQuery("SELECT C." + SQLiteHelper.CALIDAD_BILLETE_ID + ", C." + SQLiteHelper.CALIDAD_BILLETE_DESC
					+ " FROM " + SQLiteHelper.CALIDAD_BILLETE + " C "
					+ "INNER JOIN " + SQLiteHelper.CLIENTE_CALIDAD_BILLETE
					+ " X" + " ON C." + SQLiteHelper.CALIDAD_BILLETE_ID + " = "
					+ " X." + SQLiteHelper.CLIENTE_CALIDAD_BILLETE_ID_CALIDAD
					+ " WHERE " + SQLiteHelper.CLIENTE_CALIDAD_BILLETE_ID_CLIENTE
					+ " = " +validateClosureBean.getIdCliente(),null);

        	if(cursor.moveToFirst()){
        		do {
					listCalidadBillete.add(cursor.getString(0));
        			calidadSpinnerArray.add(cursor.getString(1));
				}while (cursor.moveToNext());
			}
			cursor.close();

        	ArrayAdapter<String> calidadAdaper = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,calidadSpinnerArray);
        	calidadBilleteSpinner.setAdapter(calidadAdaper);
		}

		if(validateClosureBean.getIsCondicionSiteRequired().equals("1")){
			condicionBilleteTextView.setVisibility(View.VISIBLE);
			condicionBilleteSpinner.setVisibility(View.VISIBLE);

			ArrayList<String> condicionSpinnerArray = new ArrayList<>();

			cursor = dbRequest.rawQuery("SELECT C." + SQLiteHelper.CONDICIONES_SITE_ID + ", C." + SQLiteHelper.CONDICIONES_SITE_DESC
					+ " FROM " + SQLiteHelper.CONDICIONES_SITE + " C "
					+ "INNER JOIN " + SQLiteHelper.CLIENTE_CONDICIONES_SITE
					+ " X" + " ON C." + SQLiteHelper.CONDICIONES_SITE_ID + " = "
					+ " X." + SQLiteHelper.CLIENTE_CONDICIONES_SITE_ID_CONDICION
					+ " WHERE " + SQLiteHelper.CLIENTE_CONDICIONES_SITE_ID_CLIENTE
					+ " = " +validateClosureBean.getIdCliente(),null);

			if(cursor.moveToFirst()){
				do {
					listCondicionSite.add(cursor.getString(0));
					condicionSpinnerArray.add(cursor.getString(1));
				}while (cursor.moveToNext());
			}
			cursor.close();

			ArrayAdapter<String> condicionAdaper = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,condicionSpinnerArray);
			condicionBilleteSpinner.setAdapter(condicionAdaper);
		}

        if(validateClosureBean.getIsSIMRequired().equals("1"))
        {
        	textViewSim.setVisibility(View.VISIBLE);
        	editTextSim.setVisibility(View.VISIBLE);
        }
        
		if(validateClosureBean.getIsCausaSolucionRequired().equals("1"))
        {
			causaTextView.setVisibility(View.VISIBLE);
			solucionTextView.setVisibility(View.VISIBLE);
			causaSpinner.setVisibility(View.VISIBLE);
			solucionSpinner.setVisibility(View.VISIBLE);
			
			//Se busca la descripción de la causa
	        ArrayList<String> causaSpinnerArray =  new ArrayList<>();
	        
	        cursor = dbRequest.rawQuery("SELECT c."	+ SQLiteHelper.CAUSAS_ID_CAUSA + ","
                + SQLiteHelper.CAUSAS_DESC_CAUSA + " FROM " + SQLiteHelper.CAUSAS_DB_NAME + " c "
                + " LEFT JOIN "	+ SQLiteHelper.SERVCAU_DB_NAME + " s " + " ON s."
                + SQLiteHelper.CAUSAS_ID_CAUSA + " = c." + SQLiteHelper.CAUSAS_ID_CAUSA
                + " WHERE " + SQLiteHelper.SERVICES_ID_SERVICIO + " = "	+ validateClosureBean.getIdServicio()
                + " AND " + SQLiteHelper.CLIENTES_ID_CLIENTE + " = " + validateClosureBean.getIdCliente(), null);

	        if (cursor.moveToFirst())
            {//Recorremos el cursor hasta que no haya más registros
				do
                {
					causaList.add(cursor.getString(0));
	   				causaSpinnerArray.add(cursor.getString(1));
				} while(cursor.moveToNext());
			}
			cursor.close();
			  
			ArrayAdapter<String> causaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, causaSpinnerArray);
			causaSpinner.setAdapter(causaAdapter);
			
			if(!validateClosureBean.getIdCausa().equals(""))
            {
				causaSpinner.setSelection(Integer.valueOf(causaList.indexOf(validateClosureBean.getIdCausa())));
			}
			
			//Se busca la descripción de la solución
		    ArrayList<String> solucionSpinnerArray =  new ArrayList<>();

		    cursor = dbRequest.rawQuery("SELECT c." + SQLiteHelper.SOLUCIONES_ID_SOLUCION + ","
                + SQLiteHelper.SOLUCIONES_DESC_SOLUCION + " FROM " + SQLiteHelper.SOLUCIONES_DB_NAME + " c "
                + " LEFT JOIN " + SQLiteHelper.SERVSOL_DB_NAME + " s " + " ON s."
                + SQLiteHelper.SOLUCIONES_ID_SOLUCION + " = c." + SQLiteHelper.SOLUCIONES_ID_SOLUCION
                + " WHERE " + SQLiteHelper.SERVICES_ID_SERVICIO + " = " + validateClosureBean.getIdServicio()
                + " AND " + SQLiteHelper.CLIENTES_ID_CLIENTE + " = " + validateClosureBean.getIdCliente()
                + " AND " + SQLiteHelper.SOLUCIONES_IS_EXITO + " = 1", null);
		        
		    if (cursor.moveToFirst())
            {//Recorremos el cursor hasta que no haya más registros
				do
                {
					solucionList.add(cursor.getString(0));
	   				solucionSpinnerArray.add(cursor.getString(1));
				} while(cursor.moveToNext());
			}
			cursor.close();
			
			ArrayAdapter<String> solucionAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, solucionSpinnerArray);
			solucionSpinner.setAdapter(solucionAdapter);
			if(!validateClosureBean.getIdSolucion().equals(""))
            {
				solucionSpinner.setSelection(Integer.valueOf(solucionList.indexOf(validateClosureBean.getIdSolucion())));
			}
		}
		
		if(validateClosureBean.getIsDescTrabajoCatalogRequired().equals("1"))
        {//Para evitar problemas si los dos tienen 0 o tienen 1, se toma prioridad si catalogo = 1
            validateClosureBean.setIsDescTrabajoRequired("0");
		}
		else
        {
			validateClosureBean.setIsDescTrabajoRequired("1");
			validateClosureBean.setIsDescTrabajoCatalogRequired("0");
		}
		
		ArrayList<String> descriptionList = new ArrayList<>();
  		for(int i = 0; i < descripcionTrabajoBeanArray.size(); i++){
  			descriptionList.add(descripcionTrabajoBeanArray.get(i).getDesc());
  		}
  		
  		ArrayAdapter<String> descriptionAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, descriptionList);
  		descripcionSpinner.setAdapter(descriptionAdapter);
		
  		if(validateClosureBean.getIsDescTrabajoRequired().equals("1"))
        {
        	descripcionEditText.setVisibility(View.VISIBLE);
      		descripcionSpinner.setVisibility(View.GONE);
      	}
      	else
        {
      		if(validateClosureBean.getIsDescTrabajoCatalogRequired().equals("1"))
            {
          		descripcionEditText.setVisibility(View.GONE);
          		descripcionSpinner.setVisibility(View.VISIBLE);
      		}
      		else
            {
            	descripcionEditText.setVisibility(View.VISIBLE);
          		descripcionSpinner.setVisibility(View.GONE);
      		}
      	}
		
		if(validateClosureBean.getDesc() != null)
        {
            if(!validateClosureBean.getDesc().equals("") )
            {
                descripcionEditText.setText(validateClosureBean.getDescripcionTrabajo());
            }
		}

		if(validateClosureBean.getAtiende() != null)
        {
            if(!validateClosureBean.getAtiende().equals(""))
            {
                atiendeEditText.setText(validateClosureBean.getAtiende());
            }
		}

		if(validateClosureBean.getIsCodigoIntervencionRequired().equals("1"))
		{// Valida los Códigos de Intervención
			String strLength = validateClosureBean.getLengthCodigoIntervencion();
			int lengthCodigo = Integer.parseInt(strLength);
			
			codigoIntervencionTextView.setVisibility(View.VISIBLE);
			codigoIntervencion0Spinner.setVisibility(lengthCodigo >= 1 ? View.VISIBLE : View.GONE);
			codigoIntervencion1Spinner.setVisibility(lengthCodigo >= 2 ? View.VISIBLE : View.GONE);
			codigoIntervencion2Spinner.setVisibility(lengthCodigo >= 3 ? View.VISIBLE : View.GONE);
			
			
			ArrayList<String> codigos0SpinnerArray =  new ArrayList<>();
			if(codigoIntervencion0Spinner.getVisibility() == View.VISIBLE)
			{//Se llena el spinner de codigo 0
				campos = new String[]
                {
                    SQLiteHelper.CODIGOS0_ID_CODIGO,
                    SQLiteHelper.CODIGOS0_CLAVE_CODIGO,
                    SQLiteHelper.CODIGOS0_DESC_CODIGO
    			};

				args = new String[]{validateClosureBean.getIdCliente()};
				cursor = dbRequest.query(SQLiteHelper.CODIGOS0_DB_NAME, campos, SQLiteHelper.CLIENTES_ID_CLIENTE + "=?",
                    args, null, null, null);
				
				codigos0List = new ArrayList<>();
				if (cursor.moveToFirst())
                {//Recorremos el cursor hasta que no haya más registros
					do
                    {
						codigos0List.add(cursor.getString(0));
						codigos0ClaveList.add(cursor.getString(1));
                        codigos0SpinnerArray.add(cursor.getString(1) + " - " + cursor.getString(2));
					} while(cursor.moveToNext());
				}
				cursor.close();
					
				ArrayAdapter<String> codigos0Adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, codigos0SpinnerArray);
				codigoIntervencion0Spinner.setAdapter(codigos0Adapter);
			}
			    

			if(codigoIntervencion1Spinner.getVisibility() == View.VISIBLE)
			{// Se llena el spinner de codigo 1
				changeCodigo1Spinner();
			}

			if(codigoIntervencion2Spinner.getVisibility() == View.VISIBLE)
			{// Se llena el spinner de codigo 2
				changeCodigo2Spinner();
			}
			
			if(validateClosureBean.getCodigoIntervencion() != null)
			{
                if(!validateClosureBean.getCodigoIntervencion().equals(""))
                {
                    String[] codigos = validateClosureBean.getCodigoIntervencion().split("");

                    int i = 0;
                    int codigoPosition = 0;

                    if(codigoIntervencion1Spinner.getVisibility() == View.VISIBLE)
                    {
                        for(i = 0; i < codigos0SpinnerArray.size(); i++){
                            if(codigos0SpinnerArray.get(i).startsWith(codigos[0])){
                                codigoPosition = i;
                                break;
                            }
                        }
                        codigoIntervencion0Spinner.setSelection(codigoPosition);
                    }

                    if(codigoIntervencion1Spinner.getVisibility() == View.VISIBLE)
                    {
                        codigoPosition = 0;

                        for(i = 0; i < codigos0SpinnerArray.size(); i++){
                            if(codigos0SpinnerArray.get(i).startsWith(codigos[0])){
                                codigoPosition = i;
                                break;
                            }
                        }
                        codigoIntervencion1Spinner.setSelection(codigoPosition);
                    }

                    if(codigoIntervencion2Spinner.getVisibility() == View.VISIBLE)
                    {
                        codigoPosition = 0;

                        for(i = 0; i < codigos0SpinnerArray.size(); i++){
                            if(codigos0SpinnerArray.get(i).startsWith(codigos[0])){
                                codigoPosition = i;
                                break;
                            }
                        }
                        codigoIntervencion2Spinner.setSelection(codigoPosition);
                    }
                }
			}
		}

		if(validateClosureBean.getIsTASRequired().equals("1"))
        {//Valida que se use Folio TAS
			tasTextView.setVisibility(View.VISIBLE);
			tasEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getFolioTas() != null)
            {
                if(!validateClosureBean.getFolioTas().equals(""))
                {
                    tasEditText.setText(validateClosureBean.getFolioTas());
                }
			}
		}

		if(validateClosureBean.getIsNoEquipoREquired().equals("1"))
        {//Valida el No. Equipo
			noEquipoTextView.setVisibility(View.VISIBLE);
			noEquipoEditText.setVisibility(View.VISIBLE);

            if(validateClosureBean.getNoEquipo() != null)
            {
                if(!validateClosureBean.getNoEquipo().equals(""))
                {
                    noEquipoEditText.setText(validateClosureBean.getNoEquipo());
                }
            }
		}
		
		if(validateClosureBean.getIsOtorganteTASRequired().equals("1"))
        {
			otorganteFolioTASTextView.setVisibility(View.VISIBLE);
			otorganteFolioTASEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getOtorganteTAS() != null)
            {
                if(!validateClosureBean.getOtorganteTAS().equals(""))
                {
                    otorganteFolioTASEditText.setText(validateClosureBean.getOtorganteTAS());
                }
            }
		}

		if(validateClosureBean.getIsNoSerieRequired().equals("1"))
        {
			noSerieTextView.setVisibility(View.VISIBLE);
			noSerieEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getNoSerieFalla() != null)
            {
                if(!validateClosureBean.getNoSerieFalla().equals(""))
                {
                    noSerieEditText.setText(validateClosureBean.getNoSerieFalla());
                }
			}
		}

		if(validateClosureBean.getIsNoInventarioRequired().equals("1"))
        {
			noInventarioTextView.setVisibility(View.VISIBLE);
			noInventarioEditText.setVisibility(View.VISIBLE);

            if(validateClosureBean.getNoInventarioFalla() != null)
            {
                if(validateClosureBean.getNoInventarioFalla().equals(""))
                {
                    noInventarioEditText.setText(validateClosureBean.getNoInventarioFalla());
                }
            }
		}
		
		if(validateClosureBean.getIsIdModeloRequired().equals("1") && validateClosureBean.getIsSustitucion().equals("0"))
        {
			modeloTextView.setVisibility(View.VISIBLE);
			modeloSpinner.setVisibility(View.VISIBLE);
			
			String idProducto = "1";
			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			cursor = dbRequest.rawQuery("SELECT " 	+ SQLiteHelper.REQUESTS_ID_PRODUCTO
                +  " FROM " + SQLiteHelper.REQUESTS_DB_NAME + " WHERE "
                + SQLiteHelper.REQUESTS_ID_REQUEST +  " = " + sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""), null);
			
			try
            {
				if (cursor != null )
                {
					if  (cursor.moveToFirst())
                    {
						do
                        {
							idProducto = cursor.getString(0);
						} while(cursor.moveToNext());
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

			//Se Insertan datos de Modelo
			campos = new String[]{	SQLiteHelper.MODELOS_ID_MODELO,
	        						SQLiteHelper.MODELOS_DESC_MODELO,};
	        
			ArrayList<String> modelosSpinnerArray =  new ArrayList<>();
			cursor = dbRequest.rawQuery("SELECT MO." 	+ SQLiteHelper.MODELOS_ID_MODELO 	+ ", MO."
  											+ SQLiteHelper.MODELOS_DESC_MODELO 
  						  + " FROM " 		+ SQLiteHelper.MODELOS_DB_NAME 		+ " MO " 
  						  + " INNER JOIN " 	+ SQLiteHelper.MARCAS_DB_NAME 		+ " MA ON MO."  + SQLiteHelper.MARCAS_ID_MARCA
  																				+ " = MA."		+ SQLiteHelper.MARCAS_ID_MARCA
  						  + " WHERE MA." 	+ SQLiteHelper.PRODUCTO_ID_PRODUCTO + " = "			+ idProducto
					      + " AND ID_MODELO IN (SELECT "+ SQLiteHelper.MODELOS_ID_MODELO
                          + " FROM "+ SQLiteHelper.CLIMOD_DB_NAME +" WHERE "
                          + SQLiteHelper.CLIENTES_ID_CLIENTE +"="+ validateClosureBean.getIdCliente()+ ")", null);
	        
			modelosList = new ArrayList<>();
			if (cursor.moveToFirst())
            {   //Recorremos el cursor hasta que no haya más registros
				do
                {
					modelosList.add(cursor.getString(0));
					modelosSpinnerArray.add(cursor.getString(1));
				} while(cursor.moveToNext());
			}
			cursor.close();
			
			ArrayAdapter<String> modelosAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, modelosSpinnerArray);
			modeloSpinner.setAdapter(modelosAdapter);
			
			if(validateClosureBean.getIdModeloFalla() != null)
            {
                if(!validateClosureBean.getIdModeloFalla().equals(""))
                {
                    modeloSpinner.setSelection(Integer.valueOf(modelosList.indexOf(validateClosureBean.getIdModeloFalla())));
                }
			}
		}
		String monthRedux = "";
		
		if(validateClosureBean.getIsFecLlegadaRequired().equals("1"))
        {
			fechaLlegadaTextView.setVisibility(View.VISIBLE);
			fechaLlegadaDateButton.setVisibility(View.VISIBLE);
			llegadaTimeButton.setVisibility(View.VISIBLE);

			if(validateClosureBean.getFecLlegada() == null || validateClosureBean.getFecLlegada().equals(""))
			{
                strDate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/"
                    +  cal.get(Calendar.DAY_OF_MONTH);
				fechaLlegadaDateButton.setText(strDate);
			}
			else
			{
				String 	 date 	= validateClosureBean.getFecLlegada();
				String[] fecha 	= date.split("/");
				String[] hour 	= fecha[2].substring(5).split(":");
				
				if(Integer.valueOf(hour[0])< 10)
					hour[0] = "0" + hour[0];
				
				if(Integer.valueOf(hour[1])< 10)
					hour[0] = "0" + hour[1];

				switch(Integer.valueOf(fecha[1]))
                {
	    			case 1: monthRedux = "ene.";
	    					break;
	    			case 2: monthRedux = "feb.";
				 			break;
	    			case 3: monthRedux = "mar.";
				 			break;
	    			case 4: monthRedux = "abr.";
				 			break;
	    			case 5: monthRedux = "may.";
				 			break;
	    			case 6: monthRedux = "jun.";
				 			break;
	    			case 7: monthRedux = "jul.";
				 			break;
	    			case 8: monthRedux = "ago.";
				 			break;
	    			case 9: monthRedux = "sep.";
				 			break;
	    			case 10: monthRedux = "oct.";
				 			break;
	    			case 11: monthRedux = "nov.";
				 			break;
	    			case 12: monthRedux = "dic.";
				 			break;
				}

                strDate = Integer.valueOf(fecha[0]) + "/" + monthRedux + "/"
                    + Integer.valueOf(fecha[2].substring(0, 4));
				fechaLlegadaDateButton.setText(strDate);
			}
		}
		
		if(validateClosureBean.getIsFecLlegadaTercerosRequired().equals("1"))
        {
			fechaLlegadaTercerosTextView.setVisibility(View.VISIBLE);
			fechaLlegadaTercerosDateButton.setVisibility(View.VISIBLE);
			llegadaTercerosTimeButton.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getFecLlegadaTerceros() == null
                || validateClosureBean.getFecLlegadaTerceros().equals(""))
            {
                strDate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/"
                    + cal.get(Calendar.DAY_OF_MONTH);
				fechaLlegadaTercerosDateButton.setText(strDate);
			}
			else
            {
				String date = validateClosureBean.getFecLlegadaTerceros();
				String[] fecha = date.split("/");
				
				switch(Integer.valueOf(fecha[1])){
    				case 1: monthRedux = "ene.";
    						break;
    				case 2: monthRedux = "feb.";
			 				break;
    				case 3: monthRedux = "mar.";
			 				break;
    				case 4: monthRedux = "abr.";
			 				break;
    				case 5: monthRedux = "may.";
			 				break;
    				case 6: monthRedux = "jun.";
			 				break;
    				case 7: monthRedux = "jul.";
			 				break;
    				case 8: monthRedux = "ago.";
			 				break;
    				case 9: monthRedux = "sep.";
			 				break;
    				case 10: monthRedux = "oct.";
			 				break;
    				case 11: monthRedux = "nov.";
			 				break;
    				case 12: monthRedux = "dic.";
			 				break;
				}

                strDate = Integer.valueOf(fecha[0]) + "/" + monthRedux + "/"
                    + Integer.valueOf(fecha[2].substring(0, 4));
				fechaLlegadaTercerosDateButton.setText(strDate);
			}
		}
		
		if(validateClosureBean.getIsFolioServicioRequired().equals("1"))
        {
			folioServicioTextView.setVisibility(View.VISIBLE);
			folioServicioEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getFolioServicio() != null)
            {
                if(!validateClosureBean.getFolioServicio().equals(""))
                {
                    folioServicioEditText.setText(validateClosureBean.getFolioServicio());
                }
			}
		}
		
		if(validateClosureBean.getIsFecIniIngenieroRequired().equals("1"))
        {
			fechaInicioIngenierosTextView.setVisibility(View.VISIBLE);
			fechaInicioIngenierosDateButton.setVisibility(View.VISIBLE);
			iniIngenierosTimeButton.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getFecIniIngeniero() == null
                || validateClosureBean.getFecIniIngeniero().equals(""))
            {
                strDate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/"
                    + cal.get(Calendar.DAY_OF_MONTH);
				fechaInicioIngenierosDateButton.setText(strDate);
			}
			else
            {
				String date = validateClosureBean.getFecIniIngeniero();
				String[] fecha = date.split("/");
				
				switch(Integer.valueOf(fecha[1])){
					case 1: monthRedux = "ene.";
							break;
					case 2: monthRedux = "feb.";
		 					break;
					case 3: monthRedux = "mar.";
		 					break;
					case 4: monthRedux = "abr.";
		 					break;
					case 5: monthRedux = "may.";
		 					break;
					case 6: monthRedux = "jun.";
		 					break;
					case 7: monthRedux = "jul.";
		 					break;
					case 8: monthRedux = "ago.";
		 					break;
					case 9: monthRedux = "sep.";
		 					break;
					case 10: monthRedux = "oct.";
		 					break;
					case 11: monthRedux = "nov.";
		 					break;
					case 12: monthRedux = "dic.";
		 					break;
				}

                strDate = Integer.valueOf(fecha[0]) + "/" + monthRedux + "/"
                    + Integer.valueOf(fecha[2].substring(0, 4));
				fechaInicioIngenierosDateButton.setText(strDate);
			}
		}
		
		if(validateClosureBean.getIsFecFinIngenieroRequired().equals("1"))
        {
			fechaFinIngenierosTextView.setVisibility(View.VISIBLE);
			fechaFinIngenierosDateButton.setVisibility(View.VISIBLE);
			finIngenierosTimeButton.setVisibility(View.VISIBLE);

            strDate = cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/"
                + cal.get(Calendar.DAY_OF_MONTH);

			if(validateClosureBean.getFecFinIngeniero() == null
                || validateClosureBean.getFecFinIngeniero().equals(""))
            {
				fechaFinIngenierosDateButton.setText(strDate);
			}
			else
            {
				String date = validateClosureBean.getFecFinIngeniero();
				String[] fecha = date.split("/");
				
				switch(Integer.valueOf(fecha[1])){
					case 1: monthRedux = "ene.";
							break;
					case 2: monthRedux = "feb.";
	 						break;
					case 3: monthRedux = "mar.";
	 						break;
					case 4: monthRedux = "abr.";
	 						break;
					case 5: monthRedux = "may.";
	 						break;
					case 6: monthRedux = "jun.";
	 						break;
					case 7: monthRedux = "jul.";
	 						break;
					case 8: monthRedux = "ago.";
	 						break;
					case 9: monthRedux = "sep.";
	 						break;
					case 10: monthRedux = "oct.";
	 						break;
					case 11: monthRedux = "nov.";
	 						break;
					case 12: monthRedux = "dic.";
	 						break;
				}

                strDate = Integer.valueOf(fecha[0]) + "/" + monthRedux + "/"
                    + Integer.valueOf(fecha[2].substring(0, 4));
				fechaFinIngenierosDateButton.setText(strDate);
			}
		}
		
		if(validateClosureBean.getIsOtorganteVoBoRequired().equals("1"))
        {
			otorganteVoBoTextView.setVisibility(View.VISIBLE);
			otorganteVoBoEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getOtorganteVoBo() != null)
            {
                if(!validateClosureBean.getOtorganteVoBo().equals(""))
                {
                    otorganteVoBoEditText.setText(validateClosureBean.getOtorganteVoBo());
                }
			}
		}
		
		if(validateClosureBean.getIsOtorganteVoBoTercerosRequired().equals("1"))
        {
			otorganteVoBoTercerosTextView.setVisibility(View.VISIBLE);
			otorganteVoBoTercerosEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getOtorganteVoBoTerceros() != null)
            {
                if(!validateClosureBean.getOtorganteVoBoTerceros().equals(""))
                {
                    otorganteVoBoTercerosEditText.setText(validateClosureBean.getOtorganteVoBoTerceros());
                }
			}
		}
		
		if(validateClosureBean.getIsIntensidadSenialRequired().equals("1"))
        {
			intensidadSenalTextView.setVisibility(View.VISIBLE);
			intensidadSenalButton.setVisibility(View.VISIBLE);
			
			intensidadSenalButton.setText("Baja");
			
			if(validateClosureBean.getIntensidadSenial() != null)
            {
                if(!validateClosureBean.getIntensidadSenial().equals(""))
                {
                    intensidadSenalButton.setText(validateClosureBean.getIntensidadSenial());
                }
			}
		}
		
		if(validateClosureBean.getIsIsSIMRemplazadaRequired().equals("1"))
        {
			simReemplazadaTextView.setVisibility(View.VISIBLE);
			simReemplazadaSpinner.setVisibility(View.VISIBLE);
			
			ArrayList<String> simSpinnerArray =  new ArrayList<String>();

			simList.add("0");
			simList.add("1");
			simSpinnerArray.add("No");
			simSpinnerArray.add("Si");

			ArrayAdapter<String> simAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, simSpinnerArray);
			simReemplazadaSpinner.setAdapter(simAdapter);
		}
		
		if(validateClosureBean.getIsDigitoVerificadorRequired().equals("1"))
        {
			digitoVerTextView.setVisibility(View.VISIBLE);
			digitoVerEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getDigitoVerificador() != null)
            {
                if(!validateClosureBean.getDigitoVerificador().equals(""))
                {
                    digitoVerEditText.setText(validateClosureBean.getDigitoVerificador());
                }
			}
		}
		
		if(validateClosureBean.getIsIdTipoFallaEncontradaRequired().equals("1"))
        {
			tipoFallaEncontradaTextView.setVisibility(View.VISIBLE);
			tipoFallaSpinner.setVisibility(View.VISIBLE);
			
			campos = new String[] {	SQLiteHelper.TIPOFALLA_ID_TIPOFALLA,
	        						SQLiteHelper.TIPOFALLA_DESC_TIPOFALLA};
			String[] valores = new String[]{idServicio};
	        
			ArrayList<String> tipoFallaSpinnerArray =  new ArrayList<>();
			cursor = dbRequest.query(SQLiteHelper.TIPOFALLA_DB_NAME, campos, SQLiteHelper.REQUESTS_ID_SERVICIO + "=?",
                valores, null, null, null);
			
			tipoFallaList.add("-1");
			tipoFallaSpinnerArray.add("...");
			
			if (cursor.moveToFirst())
            {// Recorremos el cursor hasta que no haya más registros
				do
                {
					tipoFallaList.add(cursor.getString(0));
					tipoFallaSpinnerArray.add(cursor.getString(1));
				} while(cursor.moveToNext());
			}
			cursor.close();
			
			ArrayAdapter<String> tipoFallaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tipoFallaSpinnerArray);
			tipoFallaSpinner.setAdapter(tipoFallaAdapter);	
			tipoFallaSpinner.setOnItemSelectedListener(tipoFallaOnItemSelectedListener);

			if(validateClosureBean.getIdTipoFallaEncontrada() != null)
            {
                if(!validateClosureBean.getIdTipoFallaEncontrada().equals(""))
                {
                    tipoFallaSpinner.setSelection(Integer.valueOf(tipoFallaList.indexOf(validateClosureBean.getIdTipoFallaEncontrada())));
                }
			}
			
			if(validateClosureBean.getIsEspecificaTipoFalla().equals("1"))
            {
				especificaTipoFallaTextView.setVisibility(View.VISIBLE);
				especificaTipoFallaSpinner.setVisibility(View.VISIBLE);

				String condicion;
				if(tipoFallaSpinner.getSelectedItemPosition() == -1)
                {
					do
                    {
						campos = new String[] {	SQLiteHelper.TIPOFALLA_ID_TIPOFALLA,
        										SQLiteHelper.TIPOFALLA_DESC_TIPOFALLA};
						valores = new String[]{idServicio};
						
						cursor = dbRequest.query(SQLiteHelper.TIPOFALLA_DB_NAME, campos,
                            SQLiteHelper.REQUESTS_ID_SERVICIO + "=?", valores, null, null, null);
			
						tipoFallaList 		  = new ArrayList<>();
						tipoFallaSpinnerArray = new ArrayList<>();
						
						tipoFallaList.add("-1");
						tipoFallaSpinnerArray.add("...");
						if (cursor.moveToFirst()) {
							do{
								tipoFallaList.add(cursor.getString(0));
								tipoFallaSpinnerArray.add(cursor.getString(1));
							}while(cursor.moveToNext());
						}
						cursor.close();
			
						tipoFallaAdapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_item, tipoFallaSpinnerArray);
						tipoFallaSpinner.setAdapter(tipoFallaAdapter);	
						tipoFallaSpinner.setOnItemSelectedListener(tipoFallaOnItemSelectedListener);
						
						condicion = tipoFallaList.get(tipoFallaSpinner.getSelectedItemPosition());
					} while(tipoFallaSpinner.getSelectedItemPosition() == -1);
				}
				else
                {
					condicion = tipoFallaList.get(tipoFallaSpinner.getSelectedItemPosition());
				}
				
				ArrayList<String> espTipoFallaSpinnerArray =  new ArrayList<>();
				cursor = dbRequest.rawQuery("SELECT " + SQLiteHelper.ESPTIFA_ID_ESPTIFA + ","
		        									+ SQLiteHelper.ESPTIFA_DESC_ESPTIFA
										+ " FROM " 	+ SQLiteHelper.ESPTIFA_DB_NAME
									    + " WHERE " + SQLiteHelper.ESPTIFA_ID_FALLA_PARENT + "=" + condicion,
									    null);
		        
				espTipoFallaList = new ArrayList<>();
				espTipoFallaList.add("-1");
				espTipoFallaSpinnerArray.add("...");
				
				if (cursor.moveToFirst())
                {// Recorremos el cursor hasta que no haya más registros
					do
                    {
						espTipoFallaList.add(cursor.getString(0));
						espTipoFallaSpinnerArray.add(cursor.getString(1));
					} while(cursor.moveToNext());
				}
				cursor.close();
				
				ArrayAdapter<String> espTipoFallaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, espTipoFallaSpinnerArray);
				especificaTipoFallaSpinner.setAdapter(espTipoFallaAdapter);	
				
				if(validateClosureBean.getIdEspecifiqueTipoFallo() != null)
                {
                    if(!validateClosureBean.getIdEspecifiqueTipoFallo().equals(""))
                    {
                        especificaTipoFallaSpinner.setSelection(Integer.valueOf(espTipoFallaList.indexOf(validateClosureBean.getIdEspecifiqueTipoFallo())));
                    }
				}
			}
		}

        if(validateClosureBean.getIsFallaEncontradaRequiredText().equals("1"))
        {
            fallaEncontradaTextView.setVisibility(View.VISIBLE);
            fallaEncontradaEditText.setVisibility(View.VISIBLE);

            mDescFailFoundSelected = "";
        }
        else if(validateClosureBean.getIsFallaEncontradaRequired().equals("1"))
        {
            fallaEncontradaTextView.setVisibility(View.VISIBLE);
            mSpinnerFailsFound.setVisibility(View.VISIBLE);

            String[] fields = new String[] {SQLiteHelper.FAILS_FOUND_ID,
                    SQLiteHelper.FAILS_FOUND_DESC};
            String[] values = new String[]{ validateClosureBean.getIdFail() };

            cursor = dbRequest.query(SQLiteHelper.FAILS_FOUND_DB_NAME, fields,
                    SQLiteHelper.FAILS_FOUND_ID_FATHER + "=?", values, null, null, null);

            listFailsFoundId = new ArrayList<>();
            listFailsFoundDesc = new ArrayList<>();

            mDescFailFoundSelected = "";
            listFailsFoundId.add("-1");
            listFailsFoundDesc.add("...");
            if (cursor.moveToFirst())
            {
                do
                {
                    listFailsFoundId.add(cursor.getString(0));
                    listFailsFoundDesc.add(cursor.getString(1));
                } while(cursor.moveToNext());
            }
            cursor.close();

            ArrayAdapter<String> failsFoundAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, listFailsFoundDesc);
            mSpinnerFailsFound.setAdapter(failsFoundAdapter);
            mSpinnerFailsFound.setOnItemSelectedListener(onFailFoundItemSelected);

			if(validateClosureBean.getFallaEncontrada() != null)
            {
                if(!validateClosureBean.getFallaEncontrada().equals(""))
                {
                    digitoVerEditText.setText(validateClosureBean.getFallaEncontrada());
                }
			}
		}
		
		if(validateClosureBean.getIsOtorganteVoBoClienteRequired().equals("1"))
        {
			voBoClienteTextView.setVisibility(View.VISIBLE);
			voBoClienteEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getOtorganteVoBoCliente() != null)
            {
                if(!validateClosureBean.getOtorganteVoBoCliente().equals(""))
                {
                    digitoVerEditText.setText(validateClosureBean.getOtorganteVoBoCliente());
                }
			}
		}
		
		if(validateClosureBean.getIsLecturaVoltajeRequired().equals("1"))
        {
			voltajeNeutroTextView.setVisibility(View.VISIBLE);
			voltajeNeutroEditText.setVisibility(View.VISIBLE);
			voltajeTierraTextView.setVisibility(View.VISIBLE);
			voltajeTierraEditText.setVisibility(View.VISIBLE);
			voltajeTierraNeutroTextView.setVisibility(View.VISIBLE);
			voltajeTierraNeutroEditText.setVisibility(View.VISIBLE);

			if(validateClosureBean.getVoltajeNeutro() != null)
            {
                if(!validateClosureBean.getVoltajeNeutro().equals(""))
                {
                    voltajeNeutroEditText.setText(validateClosureBean.getVoltajeNeutro());
                }
			}

			if(validateClosureBean.getVoltajeTierra() != null)
            {
                if(!validateClosureBean.getVoltajeTierra().equals(""))
                {
                    voltajeTierraEditText.setText(validateClosureBean.getVoltajeTierra());
                }
			}

			if(validateClosureBean.getVoltajeTierraNeutro() != null)
            {
                if(!validateClosureBean.getVoltajeTierraNeutro().equals(""))
                {
                    voltajeTierraNeutroEditText.setText(validateClosureBean.getVoltajeTierraNeutro());
                }
			}
		}
		
		if(validateClosureBean.getIsFolioValidacionRequired().equals("1"))
        {
			folioValidacionTextView.setVisibility(View.VISIBLE);
			folioValidacionEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getFolioValidacion() != null)
            {
                if(!validateClosureBean.getFolioValidacion().equals(""))
                {
                    folioValidacionEditText.setText(validateClosureBean.getFolioValidacion());
                }
			}
		}
		
		if(validateClosureBean.getIsFolioTIRRequired().equals("1"))
        {
			folioTIRTextView.setVisibility(View.VISIBLE);
			folioTIREditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getFolioTIR() != null)
            {
                if(!validateClosureBean.getFolioTIR().equals(""))
                {
                    folioTIREditText.setText(validateClosureBean.getFolioTIR());
                }
			}
		}
		
		if(validateClosureBean.getIsMotivoCobroRequired().equals("1"))
        {
			motivoCobroTextView.setVisibility(View.VISIBLE);
			motivoCobroEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getMotivoCobro() != null)
            {
                if(!validateClosureBean.getMotivoCobro().equals(""))
                {
                    motivoCobroEditText.setText(validateClosureBean.getMotivoCobro());
                }
			}
		}

		if(validateClosureBean.getIsSoporteCliente().equals("1"))
        {
			soporteClienteTextView.setVisibility(View.VISIBLE);
			soporteClienteSpinner.setVisibility(View.VISIBLE);
			
			ArrayList<String> soporteSpinnerArray =  new ArrayList<>();

			soporteList.add("0");
			soporteList.add("1");
			soporteSpinnerArray.add("No");
			soporteSpinnerArray.add("Si");

			ArrayAdapter<String> soporteAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, soporteSpinnerArray);
			soporteClienteSpinner.setAdapter(soporteAdapter);
		}
		
		if(validateClosureBean.getIsboletin().equals("1"))
        {
			boletinTextView.setVisibility(View.VISIBLE);
			boletinSpinner.setVisibility(View.VISIBLE);
			
			ArrayList<String> boletinSpinnerArray =  new ArrayList<>();

			boletinList.add("0");
			boletinList.add("1");
			boletinSpinnerArray.add("No");
			boletinSpinnerArray.add("Si");

			ArrayAdapter<String> boletinAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, boletinSpinnerArray);
			boletinSpinner.setAdapter(boletinAdapter);
		}
		
		if(validateClosureBean.getIsOtorganteSoporteClienteRequired().equals("1"))
        {
			otorganteSoporteTextView.setVisibility(View.VISIBLE);
			otorganteSoporteEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getOtorganteSoporteCliente() != null)
            {
                if(!validateClosureBean.getOtorganteSoporteCliente().equals(""))
                {
                    otorganteSoporteEditText.setText(validateClosureBean.getOtorganteSoporteCliente());
                }
			}
		}
		
		tipoCobroTextView.setText(getString(R.string.cierre_tipocobro_text) 		+ " " + validateClosureBean.getDescTipoCobro());
		tipoPrecioTextView.setText(getString(R.string.cierre_tipoprecio_text) 		+ " " + validateClosureBean.getDescTipoPrecio());
		monedaTextView.setText(getString(R.string.cierre_moneda_text) 				+ " " + validateClosureBean.getDescMoneda());
		horasAtencionTextView.setText(getString(R.string.cierre_horasatencion_text) + " " + validateClosureBean.getHorasAtencion());
		
		if(validateClosureBean.getIsUptime().equals("1"))
        {
			horarioUptimeTextView.setVisibility(View.VISIBLE);
			horarioUptimeEditText.setVisibility(View.VISIBLE);
			
			horarioAccesoTextView.setVisibility(View.VISIBLE);
			horarioAccesoEditText.setVisibility(View.VISIBLE);
			
			enContratoTextView.setVisibility(View.VISIBLE);
			enContratoEditText.setVisibility(View.VISIBLE);
			
			afectacionUptimeTextView.setVisibility(View.VISIBLE);
			afectacionUptimeEditText.setVisibility(View.VISIBLE);
			
			horarioUptimeEditText.setText(validateClosureBean.getDescHorarioUptime());
			horarioAccesoEditText.setText(validateClosureBean.getDescHorarioAcceso());
			
			
			if(validateClosureBean.getIsContract().equals("0"))
				enContratoEditText.setText("No");
			else
				enContratoEditText.setText("Si");
			
			afectacionUptimeEditText.setText(validateClosureBean.getMinsDowntime());
		}
		
		if(validateClosureBean.getIsCobrable().equals("1"))
        {
			cobrableCheckBox.setChecked(true);
		}
		
		precioServicioTextView.setText(getString(R.string.cierre_precioservicio_text) 	+ " " + validateClosureBean.getPrecioExito());
		
		if(validateClosureBean.getIsCadenaCierreEscritaRequired().equals("1")){
			cadenaCierreTextView.setVisibility(View.VISIBLE);
			cadenaCierreEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getCadenaCierreEscrita() != null)
            {
                if(!validateClosureBean.getCadenaCierreEscrita().equals(""))
                {
                    cadenaCierreEditText.setText(validateClosureBean.getCadenaCierreEscrita());
                }
			}
		}
		
		if(validateClosureBean.getIsInstalacionLlavesRequired().equals("1"))
        {
			instalacionLlavesTextView.setVisibility(View.VISIBLE);
			instalacionLlavesEditText.setVisibility(View.VISIBLE);
			String metiqueta = getEtiqueta(1);
			instalacionLlavesTextView.setText(metiqueta+"1");
			if(validateClosureBean.getTipoLlaves() != null)
            {
                if(!validateClosureBean.getTipoLlaves().equals(""))
                {
                    instalacionLlavesEditText.setText(validateClosureBean.getInstalacionLlaves());
                }
			}
		}
		
		if(validateClosureBean.getIsTipoLlaveRequired().equals("1"))
        {
			tipoLlaveTextView.setVisibility(View.VISIBLE);
			tipoLlaveEditText.setVisibility(View.VISIBLE);
			String etiqueta = getEtiqueta(2);
			tipoLlaveTextView.setText(etiqueta);
			if(validateClosureBean.getTipoLlaves() != null)
            {
                if(!validateClosureBean.getTipoLlaves().equals(""))
                {
                    tipoLlaveEditText.setText(validateClosureBean.getTipoLlaves());
                }
			}
		}
		
		if(validateClosureBean.getIsStatusInstalacionLlavesRequired().equals("1"))
        {
			instalacionLlavesTextView.setVisibility(View.VISIBLE);
			instalacionLlavesEditText.setVisibility(View.VISIBLE);
			String etiqueta = getEtiqueta(3);
			instalacionLlavesTextView.setText(etiqueta);
			if(validateClosureBean.getStatusInstalacionLlaves() != null)
            {
                if(!validateClosureBean.getStatusInstalacionLlaves().equals(""))
                {
                    instalacionLlavesEditText.setText(validateClosureBean.getStatusInstalacionLlaves());
                }
			}
		}

		if(validateClosureBean.getIsNombrePersonaLlavesARequired().equals("1"))
        {
			personaLlavesATextView.setVisibility(View.VISIBLE);
			personaLlavesAEditText.setVisibility(View.VISIBLE);
			String etiqueta = getEtiqueta(4);
			personaLlavesATextView.setText(etiqueta);
			if(validateClosureBean.getNombrePersonalLlavesA() != null)
            {
                if(!validateClosureBean.getNombrePersonalLlavesA().equals(""))
                {
                    personaLlavesAEditText.setText(validateClosureBean.getNombrePersonalLlavesA());
                }
			}
		}
		
		if(validateClosureBean.getIsNombrePersonaLlavesBRequired().equals("1"))
        {
			personaLlavesBTextView.setVisibility(View.VISIBLE);
			personaLlavesBEditText.setVisibility(View.VISIBLE);
			String etiqueta = getEtiqueta(5);
			personaLlavesBTextView.setText(etiqueta);
            if(validateClosureBean.getNombrePersonalLlavesB() != null)
            {
                if(!validateClosureBean.getNombrePersonalLlavesB().equals(""))
                {
                    personaLlavesBEditText.setText(validateClosureBean.getNombrePersonalLlavesB());
                }
            }
		}
		
		if(validateClosureBean.getIsTipoPwRequired().equals("1"))
        {
			tipoPasswordTextView.setVisibility(View.VISIBLE);
			tipoPasswordEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getTipoPw() != null)
            {
                if(!validateClosureBean.getTipoPw().equals(""))
                {
                    tipoPasswordEditText.setText(validateClosureBean.getTipoPw());
                }
			}
		}
		
		if(validateClosureBean.getIsTipoTecladoRequired().equals("1"))
        {
			tipoTecladoTextView.setVisibility(View.VISIBLE);
			tipoTecladoEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getTisTipoTeclado() != null)
            {
                if(!validateClosureBean.getTisTipoTeclado().equals(""))
                {
                    tipoTecladoEditText.setText(validateClosureBean.getTisTipoTeclado());
                }
			}
		}
		
		if(validateClosureBean.getIsVersionTecladoEPPRequired().equals("1"))
        {
			versionTecladoTextView.setVisibility(View.VISIBLE);
			versionTecladoEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getVersionTecladoEPP() != null)
            {
                if(!validateClosureBean.getVersionTecladoEPP().equals(""))
                {
                    versionTecladoEditText.setText(validateClosureBean.getVersionTecladoEPP());
                }
			}
		}
		
		if(validateClosureBean.getIsProcesadorRequired().equals("1"))
        {
			procesadorTextView.setVisibility(View.VISIBLE);
			procesadorEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getProcesador() != null)
            {
                if(!validateClosureBean.getProcesador().equals(""))
                {
                    procesadorEditText.setText(validateClosureBean.getProcesador());
                }
			}
		}
		
		if(validateClosureBean.getIsVelocidadProcesadorRequired().equals("1"))
        {
			velocidadProcesadorTextView.setVisibility(View.VISIBLE);
			velocidadProcesadorEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getVelocidadProcesador() != null)
            {
                if(!validateClosureBean.getVelocidadProcesador().equals(""))
                {
                    velocidadProcesadorEditText.setText(validateClosureBean.getVelocidadProcesador());
                }
			}
		}
		
		if(validateClosureBean.getIsMemoriaRequired().equals("1"))
        {
			memoriaTextView.setVisibility(View.VISIBLE);
			memoriaEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getMemoria() != null)
            {
                if(!validateClosureBean.getMemoria().equals(""))
                {
                    memoriaEditText.setText(validateClosureBean.getMemoria());
                }
			}
		}

		if(validateClosureBean.getCapacidadDiscoDuro().equals("1"))
        {
			discoDuroTextView.setVisibility(View.VISIBLE);
			discoDuroEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getCapacidadDiscoDuro() != null)
            {
                if(!validateClosureBean.getCapacidadDiscoDuro().equals(""))
                {
                    discoDuroEditText.setText(validateClosureBean.getCapacidadDiscoDuro());
                }
			}
		}

		if(validateClosureBean.getIsMonitorRequired().equals("1"))
        {
			monitorTextView.setVisibility(View.VISIBLE);
			monitorEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getMonitor() != null)
            {
                if(!validateClosureBean.getMonitor().equals(""))
                {
                    monitorEditText.setText(validateClosureBean.getMonitor());
                }
			}
		}

		if(validateClosureBean.getIsLectorTarjetaRequired().equals("1"))
        {
			lectorTarjetaTextView.setVisibility(View.VISIBLE);
			lectorTarjetaEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getLectorTarjeta() != null)
            {
                if(!validateClosureBean.getLectorTarjeta().equals(""))
                {
                    lectorTarjetaEditText.setText(validateClosureBean.getLectorTarjeta());
                }
			}
		}
		
		if(validateClosureBean.getIsAplicacionRequired().equals("1"))
        {
			aplicacionTextView.setVisibility(View.VISIBLE);
			aplicacionEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getAplicacion_santander() != null)
            {
                if(!validateClosureBean.getAplicacion_santander().equals(""))
                {
                    aplicacionEditText.setText(validateClosureBean.getAplicacion_santander());
                }
			}
		}

		if(validateClosureBean.getIsVersionRequired().equals("1"))
        {
			versionTextView.setVisibility(View.VISIBLE);
			versionEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getVersion_santander() != null)
            {
                if(!validateClosureBean.getVersion_santander().equals(""))
                {
                    versionEditText.setText(validateClosureBean.getVersion_santander());
                }
			}
		}

		if(validateClosureBean.getIsCajaRequired().equals("1"))
        {
			cajaTextView.setVisibility(View.VISIBLE);
			cajaEditText.setVisibility(View.VISIBLE);
			
			if(validateClosureBean.getCaja() != null)
            {
                if(!validateClosureBean.getCaja().equals(""))
                {
                    cajaEditText.setText(validateClosureBean.getCaja());
                }
			}
		}
		
		codigoIntervencion0Spinner.setOnItemSelectedListener(codigo0OnItemSelectedListener);
		codigoIntervencion1Spinner.setOnItemSelectedListener(codigo1OnItemSelectedListener);

		dbRequest.close();
		
		view = this.findViewById(android.R.id.content).getRootView();
		cal = Calendar.getInstance();

		fechaCierreDateButton.onDateSet(dp, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		fechaCierreDateButton.setOnClickListener(dateOnClickListener);
		
		String currentHour;
		String currentMinute;
		
		if(cal.get(Calendar.HOUR_OF_DAY) > 9){
			currentHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		}
		else{
			currentHour = "0" + String.valueOf(cal.get(Calendar.HOUR_OF_DAY));	
		}
		
		if(cal.get(Calendar.MINUTE) > 9){
			currentMinute =  String.valueOf(cal.get(Calendar.MINUTE));
		}
		else{
			currentMinute =  "0" + String.valueOf(cal.get(Calendar.MINUTE));
		}
		timeButton.setText(currentHour + ":" + currentMinute);
		timeButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String time = timeButton.getText().toString();

                if (!time.equals(""))
                {
                    StringTokenizer st = new StringTokenizer(time, ":");
                    String timeHour = st.nextToken();
                    String timeMinute = st.nextToken();

                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new TimePickHandler(), Integer.parseInt(timeHour),
                            Integer.parseInt(timeMinute), true);
                }
                else
                {
                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new TimePickHandler(), 10, 45, true);
                }

                timePickDialog.show();
            }
        });;
        
        if(!validateClosureBean.getFecLlegada().equals("")){
        	String[] fecha 	= validateClosureBean.getFecLlegada().split("/");
        	String[] hour = fecha[2].substring(5).split(":");
        	if(Integer.valueOf(hour[0])< 10)
				hour[0] = "0" + hour[0];
			
			if(Integer.valueOf(hour[1])< 10)
				hour[1] = "0" + hour[1];
			
        	if(hour[2].substring(3).trim().equals("PM"))
				llegadaTimeButton.setText((Integer.valueOf(hour[0]) + 12) + ":" + Integer.valueOf(hour[1]));
			else
				llegadaTimeButton.setText((Integer.valueOf(hour[0])) + ":" + Integer.valueOf(hour[1]));
        }
        else
        	llegadaTimeButton.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        
        llegadaTimeButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String time = llegadaTimeButton.getText().toString();
                if (!time.equals(""))
                {
                    StringTokenizer st = new StringTokenizer(time, ":");
                    String timeHour = st.nextToken();
                    String timeMinute = st.nextToken();

                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new LlegadaTimePickHandler(), Integer.parseInt(timeHour),
                            Integer.parseInt(timeMinute), true);
                }
                else
                {
                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new LlegadaTimePickHandler(), 10, 45, true);
                }

                timePickDialog.show();
            }
        });;
        
        if(!validateClosureBean.getFecLlegadaTerceros().equals("")){
        	String[] fecha 	= validateClosureBean.getFecLlegadaTerceros().split("/");
        	String[] hour = fecha[2].substring(5).split(":");
        	if(Integer.valueOf(hour[0])< 10)
				hour[0] = "0" + hour[0];
			
			if(Integer.valueOf(hour[1])< 10)
				hour[1] = "0" + hour[1];
        	
        	if(hour[2].substring(3).trim().equals("PM"))
				llegadaTercerosTimeButton.setText((Integer.valueOf(hour[0]) + 12) + ":" + Integer.valueOf(hour[1]));
			else
				llegadaTercerosTimeButton.setText((Integer.valueOf(hour[0])) + ":" + Integer.valueOf(hour[1]));
        }
        else
        	llegadaTercerosTimeButton.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        
        llegadaTercerosTimeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String time = llegadaTercerosTimeButton.getText().toString();

                if (time != null && !time.equals("")) {
                    StringTokenizer st = new StringTokenizer(time, ":");
                    String timeHour = st.nextToken();
                    String timeMinute = st.nextToken();

                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new LlegadaTercerosTimePickHandler(), Integer.parseInt(timeHour),
                            Integer.parseInt(timeMinute), true);
                } else {
                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new LlegadaTercerosTimePickHandler(), 10, 45, true);
                }

                timePickDialog.show();
            }
        });;
        
        if(!validateClosureBean.getFecIniIngeniero().equals("")){
        	String[] fecha 	= validateClosureBean.getFecIniIngeniero().split("/");
        	String[] hour = fecha[2].substring(5).split(":");
        	if(Integer.valueOf(hour[0])< 10)
				hour[0] = "0" + hour[0];
			
			if(Integer.valueOf(hour[1])< 10)
				hour[1] = "0" + hour[1];
			
        	if(hour[2].substring(3).trim().equals("PM"))
				iniIngenierosTimeButton.setText((Integer.valueOf(hour[0]) + 12) + ":" + Integer.valueOf(hour[1]));
			else
				iniIngenierosTimeButton.setText((Integer.valueOf(hour[0])) + ":" + Integer.valueOf(hour[1]));
        }
        else
        	iniIngenierosTimeButton.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        
        iniIngenierosTimeButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String time = iniIngenierosTimeButton.getText().toString();
                if (!time.equals(""))
                {
                    StringTokenizer st = new StringTokenizer(time, ":");
                    String timeHour = st.nextToken();
                    String timeMinute = st.nextToken();

                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new IniIngenierosTimePickHandler(), Integer.parseInt(timeHour),
                            Integer.parseInt(timeMinute), true);
                }
                else
                {
                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new IniIngenierosTimePickHandler(), 10, 45, true);
                }

                timePickDialog.show();
            }
        });;
        
        if(!validateClosureBean.getFecFinIngeniero().equals(""))
        {
        	String[] fecha 	= validateClosureBean.getFecFinIngeniero().split("/");
        	String[] hour = fecha[2].substring(5).split(":");
        	if(Integer.valueOf(hour[0])< 10)
				hour[0] = "0" + hour[0];
			
			if(Integer.valueOf(hour[1])< 10)
				hour[1] = "0" + hour[1];
			
        	if(hour[2].substring(3).trim().equals("PM"))
				finIngenierosTimeButton.setText((Integer.valueOf(hour[0]) + 12) + ":" + Integer.valueOf(hour[1]));
			else
				finIngenierosTimeButton.setText((Integer.valueOf(hour[0])) + ":" + Integer.valueOf(hour[1]));
        }
        else
        	finIngenierosTimeButton.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        
        finIngenierosTimeButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String time = finIngenierosTimeButton.getText().toString();
                if (!time.equals(""))
                {
                    StringTokenizer st = new StringTokenizer(time, ":");
                    String timeHour = st.nextToken();
                    String timeMinute = st.nextToken();

                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new FinIngenierosTimePickHandler(), Integer.parseInt(timeHour),
                            Integer.parseInt(timeMinute), true);
                }
                else
                {
                    timePickDialog = new TimePickerDialog(v.getContext(),
                            new FinIngenierosTimePickHandler(), 10, 45, true);
                }

                timePickDialog.show();
            }
        });;
        
        fechaLlegadaDateButton.setOnClickListener(llegadasDateOnClickListener);
        if(validateClosureBean.getFecLlegada().equals(""))
        {
        	fechaLlegadaDateButton.onDateSet(dp, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }
        else
        {
        	String[] date = validateClosureBean.getFecLlegada().split("/");
        	fechaLlegadaDateButton.onDateSet(dp, Integer.valueOf(date[2].substring(0,4)), Integer.valueOf(date[1]) - 1, Integer.valueOf(date[0]));
        }
        
        fechaLlegadaTercerosDateButton.setOnClickListener(llegadasTercerosDateOnClickListener);
        if(validateClosureBean.getFecLlegadaTerceros().equals(""))
        {
        	fechaLlegadaTercerosDateButton.onDateSet(dp, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }
        else{
        	String[] date = validateClosureBean.getFecLlegadaTerceros().split("/");
        	fechaLlegadaTercerosDateButton.onDateSet(dp, Integer.valueOf(date[2].substring(0,4)), Integer.valueOf(date[1]) - 1, Integer.valueOf(date[0]));
        }
        
        fechaInicioIngenierosDateButton.setOnClickListener(iniIngenierosDateOnClickListener);
        if(validateClosureBean.getFecIniIngeniero().equals("")){
        	fechaInicioIngenierosDateButton.onDateSet(dp, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }
        else{
        	String[] date = validateClosureBean.getFecIniIngeniero().split("/");
        	fechaInicioIngenierosDateButton.onDateSet(dp, Integer.valueOf(date[2].substring(0,4)), Integer.valueOf(date[1]) - 1, Integer.valueOf(date[0]));
        }
        
        fechaFinIngenierosDateButton.setOnClickListener(finIngenierosDateOnClickListener);
        if(validateClosureBean.getFecFinIngeniero().equals("")){
        	fechaFinIngenierosDateButton.onDateSet(dp, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }
        else{
        	String[] date = validateClosureBean.getFecFinIngeniero().split("/");
        	fechaFinIngenierosDateButton.onDateSet(dp, Integer.valueOf(date[2].substring(0,4)), Integer.valueOf(date[1]) - 1, Integer.valueOf(date[0]));
        }
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
            Intent intentBackTo = new Intent(CierreActivity.this, RequestDetailActivity.class);
            setResult(Activity.RESULT_OK, intentBackTo);
        }
        else
        {
            setResult(Activity.RESULT_CANCELED);
        }

        super.onBackPressed();
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
	
	private class LlegadaTimePickHandler implements OnTimeSetListener {
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
			
			llegadaTimeButton.setText(hours + ":" + minutes);
	        timePickDialog.hide();
	    }
	}
	
	private class LlegadaTercerosTimePickHandler implements OnTimeSetListener {
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
			
			llegadaTercerosTimeButton.setText(hours + ":" + minutes);
	        timePickDialog.hide();
	    }
	}
	
	private class IniIngenierosTimePickHandler implements OnTimeSetListener {
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
			
			iniIngenierosTimeButton.setText(hours + ":" + minutes);
	        timePickDialog.hide();
	    }
	}
	
	private class FinIngenierosTimePickHandler implements OnTimeSetListener {
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
			
			finIngenierosTimeButton.setText(hours + ":" + minutes);
	        timePickDialog.hide();
	    }
	}
	
	OnClickListener dateOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			showDateDialog(0);
		}
	};
	
	OnClickListener llegadasDateOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			showDateDialog(1);
		}
	};
	
	OnClickListener llegadasTercerosDateOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			showDateDialog(2);
		}
	};
	
	OnClickListener iniIngenierosDateOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			showDateDialog(3);
		}
	};
	
	OnClickListener finIngenierosDateOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			showDateDialog(4);
		}
	};
	
	public void showDateDialog(int type){
		switch(type){
			case 0: fechaCierreDateButton = (DateButton)findViewById(R.id.cierresFechaCierreDateButton);
					fechaCierreDateButton.onClick(view);
					break;
					
			case 1: fechaLlegadaDateButton = (DateButton)findViewById(R.id.cierresFechaLlegadaDateButton);
					fechaLlegadaDateButton.onClick(view);
					break;
			
			case 2: fechaLlegadaTercerosDateButton = (DateButton)findViewById(R.id.cierresFechaLlegadaTercerosDateButton);
					fechaLlegadaTercerosDateButton.onClick(view);
					break;
			
			case 3: fechaInicioIngenierosDateButton = (DateButton)findViewById(R.id.cierresFechaInicioIngenierosDateButton);
					fechaInicioIngenierosDateButton.onClick(view);
					break;
			
			case 4: fechaFinIngenierosDateButton = (DateButton)findViewById(R.id.cierresFechaFinIngenierosDateButton);
					fechaFinIngenierosDateButton.onClick(view);
					break;
		}
		
	}
	
	private OnClickListener confirmarExitoOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			sendExito();
		}
	};
	
//	public void goToList(){
//		Intent intent = new Intent(this, RequestListActivity.class);
//		intent.putExtra("type", Constants.DATABASE_ABIERTAS);
//		startActivity(intent);
//	}
	
	public void sendExito(){
		ProgressDialog progressDialog = new ProgressDialog(CierreActivity.this);
    	progressDialog.setMessage("Enviando solicitud de exito");
		progressDialog.setCancelable(false);
		boolean proceed = true;
		ArrayList<String> neededFields = new ArrayList<String>();
		
		validateClosureBean = getValidateClosureBean();
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		try{
			validateClosureBean.setIdCausa(causaList.get(causaSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			validateClosureBean.setIdCausa("");
		}
		
		try{
			validateClosureBean.setIdSolucion(solucionList.get(solucionSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			validateClosureBean.setIdSolucion("");
		}
		
		if(descripcionEditText.getText().toString() == null || descripcionEditText.getText().toString().equals("")){
			if(validateClosureBean.getIsDescTrabajoCatalogRequired().equals("1")){
			}
			else{
				proceed = false;
				descripcionTextView.setTextColor(Color.RED);
				
				neededFields.add("Descripción del trabajo");
			}
		}
		else{
			validateClosureBean.setDescripcionTrabajo(descripcionEditText.getText().toString());
			descripcionTextView.setTextColor(Color.BLACK);
		}
		
		if(atiendeEditText.getText().toString() == null || atiendeEditText.getText().toString().equals("")){
			proceed = false;
			atiendeTextView.setTextColor(Color.RED);
			
			neededFields.add("Atiende");
		}
		else{
			validateClosureBean.setAtiende(atiendeEditText.getText().toString());
			atiendeTextView.setTextColor(Color.BLACK);
		}
		
		String day 		= fechaCierreDateButton.getText().toString().substring(0,2);
		String month 	= setNumericMonth(fechaCierreDateButton.getText().toString().substring(3, 7));
		String year		= fechaCierreDateButton.getText().toString().substring(8, 12);
		String hour 	= timeButton.getText().toString().substring(0, 2);
		String minute 	= timeButton.getText().toString().substring(3, 5);
		
		validateClosureBean.setFecCierre(year + "-" + day + "-" + month + " " + hour + ":" + minute + ":00" );
		
		if(validateClosureBean.getIsTASRequired().equals("1")){
			if(tasEditText.getText().toString().equals("") || tasEditText.getText().toString() == null){
				proceed = false;
				tasTextView.setTextColor(Color.RED);
				
				neededFields.add("Folio TAS");
			}
			else{
				validateClosureBean.setFolioTas(tasEditText.getText().toString());
				tasTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsCodigoIntervencionRequired().equals("1"))
		{
			String itemsSelected = "";
			if(codigoIntervencion0Spinner.getVisibility() == View.VISIBLE)
			{
				itemsSelected += codigos0ClaveList.get(codigoIntervencion0Spinner.getSelectedItemPosition());
			}
			
			if(codigoIntervencion1Spinner.getVisibility() == View.VISIBLE)
			{
				itemsSelected += codigos1ClaveList.get(codigoIntervencion1Spinner.getSelectedItemPosition());
			}
			
			if(codigoIntervencion2Spinner.getVisibility() == View.VISIBLE)
			{
				itemsSelected += codigos2ClaveList.get(codigoIntervencion2Spinner.getSelectedItemPosition());
			}
			
			validateClosureBean.setCodigoIntervencion(itemsSelected);
		}
		
		if(validateClosureBean.getIsOtorganteTASRequired().equals("1")){
			if(otorganteFolioTASEditText.getText().toString().equals("") || otorganteFolioTASEditText.getText().toString() == null){
				proceed = false;
				otorganteFolioTASTextView.setTextColor(Color.RED);
				
				neededFields.add("Otorgante folio TAS");
			}
			else{
				validateClosureBean.setOtorganteTAS(otorganteFolioTASEditText.getText().toString());
				otorganteFolioTASTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsNoEquipoREquired().equals("1")){
			if(noEquipoEditText.getText().toString().equals("") || noEquipoEditText.getText().toString() == null){
				proceed = false;
				noEquipoTextView.setTextColor(Color.RED);
				
				neededFields.add("No. Equipo");

			}
			else{
				validateClosureBean.setNoEquipo(noEquipoEditText.getText().toString());
				noEquipoTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsNoSerieRequired().equals("1")){
			if(noSerieEditText.getText().toString().equals("") || noSerieEditText.getText().toString() == null){
				proceed = false;
				noSerieTextView.setTextColor(Color.RED);
				
				neededFields.add("No. Serie");
			}
			else{
				validateClosureBean.setNoSerieFalla(noSerieEditText.getText().toString());
				noSerieTextView.setTextColor(Color.BLACK);
			}
		}
		

		if(validateClosureBean.getIsNoInventarioRequired().equals("1")){
			if(noInventarioEditText.getText().toString().equals("") || noInventarioEditText.getText().toString() == null){
				proceed = false;
				noInventarioTextView.setTextColor(Color.RED);
				
				neededFields.add("No. Inventario");
			}
			else{
				validateClosureBean.setNoInventarioFalla(noInventarioEditText.getText().toString());
				noInventarioTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsIdModeloRequired().equals("1")){
			try{
				validateClosureBean.setIdModeloFalla(modelosList.get(modeloSpinner.getSelectedItemPosition()));
			}catch(Exception e){

			}
		}

		if(validateClosureBean.getIsCalidadBilleteRequired().equals("1")){
			validateClosureBean.setIdCalidadBillete(listCalidadBillete.get(calidadBilleteSpinner.getSelectedItemPosition()));
		}

		if(validateClosureBean.getIsCondicionSiteRequired().equals("1")){
			validateClosureBean.setIdCondicionSite(listCondicionSite.get(condicionBilleteSpinner.getSelectedItemPosition()));
		}

		if(validateClosureBean.getIsFecLlegadaRequired().equals("1")){
			try{
				day 	= fechaLlegadaDateButton.getText().toString().substring(0,2);
				month 	= setNumericMonth(fechaLlegadaDateButton.getText().toString().substring(3, 7));
				year	= fechaLlegadaDateButton.getText().toString().substring(8, 12);
				hour 	= llegadaTimeButton.getText().toString().substring(0, 2);
				minute 	= llegadaTimeButton.getText().toString().substring(3, 5);

				validateClosureBean.setFecLlegada(year + "-" + day + "-" + month + " " + hour + ":" + minute + ":00" );
			}catch(Exception e){
				validateClosureBean.setFecLlegada("");
			}
		}
		
		if(validateClosureBean.getIsFecLlegadaTercerosRequired().equals("1")){
			try{
				day 	= fechaLlegadaTercerosDateButton.getText().toString().substring(0,2);
				month 	= setNumericMonth(fechaLlegadaTercerosDateButton.getText().toString().substring(3, 7));
				year	= fechaLlegadaTercerosDateButton.getText().toString().substring(8, 12);
				hour 	= llegadaTercerosTimeButton.getText().toString().substring(0, 2);
				minute 	= llegadaTercerosTimeButton.getText().toString().substring(3, 5);
				
				validateClosureBean.setFecLlegadaTerceros(year + "-" + day + "-" + month + " " + hour + ":" + minute + ":00" );
			}catch(Exception e){
				validateClosureBean.setFecLlegadaTerceros("");
			}
		}
		
		if(validateClosureBean.getIsFolioServicioRequired().equals("1")){
			if(folioServicioEditText.getText().toString().equals("") || folioServicioEditText.getText().toString() == null){
				proceed = false;
				folioServicioTextView.setTextColor(Color.RED);
				
				neededFields.add("Folio Servicio");
			}
			else{
				validateClosureBean.setFolioServicio(folioServicioEditText.getText().toString());
				folioServicioTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsFecIniIngenieroRequired().equals("1")){
			try{
				day 	= fechaInicioIngenierosDateButton.getText().toString().substring(0,2);
				month 	= setNumericMonth(fechaInicioIngenierosDateButton.getText().toString().substring(3, 7));
				year	= fechaInicioIngenierosDateButton.getText().toString().substring(8, 12);
				hour 	= iniIngenierosTimeButton.getText().toString().substring(0, 2);
				minute 	= iniIngenierosTimeButton.getText().toString().substring(3, 5);

				validateClosureBean.setFecIniIngeniero(year + "-" + day + "-" + month + " " + hour + ":" + minute + ":00" );
			}catch(Exception e){
				validateClosureBean.setFecIniIngeniero("");
			}
		}
		
		if(validateClosureBean.getIsFecIniIngenieroRequired().equals("1")){
			try{
				day 	= fechaFinIngenierosDateButton.getText().toString().substring(0,2);
				month 	= setNumericMonth(fechaFinIngenierosDateButton.getText().toString().substring(3, 7));
				year	= fechaFinIngenierosDateButton.getText().toString().substring(8, 12);
				hour 	= finIngenierosTimeButton.getText().toString().substring(0, 2);
				minute 	= finIngenierosTimeButton.getText().toString().substring(3, 5);
				
				validateClosureBean.setFecFinIngeniero(year + "-" + day + "-" + month + " " + hour + ":" + minute + ":00" );
			}catch(Exception e){
				validateClosureBean.setFecFinIngeniero("");
			}
		}
		
		if(validateClosureBean.getIsOtorganteVoBoRequired().equals("1")){
			if(otorganteVoBoEditText.getText().toString().equals("") || otorganteVoBoEditText.getText().toString() == null){
				proceed = false;
				otorganteVoBoTextView.setTextColor(Color.RED);
				
				neededFields.add("Otorgante VoBo");
			}
			else{
				validateClosureBean.setOtorganteVoBo(otorganteVoBoEditText.getText().toString());
				otorganteVoBoTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsOtorganteVoBoTercerosRequired().equals("1")){
			if(otorganteVoBoTercerosEditText.getText().toString().equals("") || otorganteVoBoTercerosEditText.getText().toString() == null){
				proceed = false;
				otorganteVoBoTercerosTextView.setTextColor(Color.RED);
				
				neededFields.add("Otorgante VoBo Terceros");
			}
			else{
				validateClosureBean.setOtorganteVoBoTerceros(otorganteVoBoTercerosEditText.getText().toString());
				otorganteVoBoTercerosTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsIntensidadSenialRequired().equals("1")){
			validateClosureBean.setIntensidadSenial(intensidadSenalButton.getText().toString());
		}

		try{
			validateClosureBean.setIdSIMRemplazada(simList.get(simReemplazadaSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			validateClosureBean.setIdSIMRemplazada("0");
		}
		
		if(validateClosureBean.getIsDigitoVerificadorRequired().equals("1")){
			if(digitoVerEditText.getText().toString().equals("") || digitoVerEditText.getText().toString() == null){
				proceed = false;
				digitoVerTextView.setTextColor(Color.RED);
					
				neededFields.add("Digito Verificador");
			}
			else{
				validateClosureBean.setDigitoVerificador(digitoVerEditText.getText().toString());
				digitoVerTextView.setTextColor(Color.BLACK);
			}
		}

		if(validateClosureBean.getIsIdTipoFallaEncontradaRequired().equals("1")){
			
			if(tipoFallaSpinner.getSelectedItemPosition() <= 0) {
				proceed = false;
				tipoFallaEncontradaTextView.setTextColor(Color.RED);
				
				neededFields.add("Tipo Falla Encontrada");
			} else {
				try{
					validateClosureBean.setIdTipoFallaEncontrada(tipoFallaList.get(tipoFallaSpinner.getSelectedItemPosition()));
				} catch(Exception e) {
					validateClosureBean.setIdTipoFallaEncontrada("");
				}
				tipoFallaEncontradaTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsEspecificaTipoFalla().equals("1")) {
			if(especificaTipoFallaSpinner.getSelectedItemPosition() <= 0){
				proceed = false;
				especificaTipoFallaTextView.setTextColor(Color.RED);
				neededFields.add("Especifica Tipo de Falla Encontrada");
				
			} else {
				try{
					validateClosureBean.setIdEspecifiqueTipoFallo(espTipoFallaList.get(especificaTipoFallaSpinner.getSelectedItemPosition()));
				} catch(Exception e) {
					validateClosureBean.setIdEspecifiqueTipoFallo("");
				}
				especificaTipoFallaTextView.setTextColor(Color.BLACK);
			}
		}

        if(validateClosureBean.getIsFallaEncontradaRequiredText().equals("1") ||
                validateClosureBean.getIsFallaEncontradaRequired().equals("1"))
        {
            if(validateClosureBean.getIsFallaEncontradaRequiredText().equals("1"))
            {
                mDescFailFoundSelected = fallaEncontradaEditText.getText().toString();
            }

			if(mDescFailFoundSelected.equals(""))
            {
				proceed = false;
				fallaEncontradaTextView.setTextColor(Color.RED);
				neededFields.add("Falla Encontrada");
			}
			else
            {// check this -.-
				validateClosureBean.setFallaEncontrada(mDescFailFoundSelected);
				fallaEncontradaTextView.setTextColor(Color.BLACK);
			}
		}

		if(validateClosureBean.getIsOtorganteVoBoClienteRequired().equals("1")){
			if(voBoClienteEditText.getText().toString().equals("") || voBoClienteEditText.getText().toString() == null){
				proceed = false;
				voBoClienteTextView.setTextColor(Color.RED);
					
				neededFields.add("Otorgante VoBo Cliente");
			}
			else{
				validateClosureBean.setOtorganteVoBoCliente(voBoClienteEditText.getText().toString());
				voBoClienteTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsLecturaVoltajeRequired().equals("1")){
			if(voltajeNeutroEditText.getText().toString().equals("") || voltajeNeutroEditText.getText().toString() == null){
				proceed = false;
				voltajeNeutroTextView.setTextColor(Color.RED);
					
				neededFields.add("Voltaje Neutro");
			}
			else{
				validateClosureBean.setVoltajeNeutro(voltajeNeutroEditText.getText().toString());
				voltajeNeutroTextView.setTextColor(Color.BLACK);
			}
			
			if(voltajeTierraEditText.getText().toString().equals("") || voltajeTierraEditText.getText().toString() == null){
				proceed = false;
				voltajeTierraTextView.setTextColor(Color.RED);
					
				neededFields.add("Voltaje Tierra");
			}
			else{
				validateClosureBean.setVoltajeTierra(voltajeTierraEditText.getText().toString());
				voltajeTierraTextView.setTextColor(Color.BLACK);
			}
			
			if(voltajeTierraNeutroEditText.getText().toString().equals("") || voltajeTierraNeutroEditText.getText().toString() == null){
				proceed = false;
				voltajeTierraNeutroTextView.setTextColor(Color.RED);
					
				neededFields.add("Voltaje Tierra Neutro");
			}
			else{
				validateClosureBean.setVoltajeTierraNeutro(voltajeTierraNeutroEditText.getText().toString());
				voltajeTierraNeutroTextView.setTextColor(Color.BLACK);
			}
		}

		if(validateClosureBean.getIsFolioValidacionRequired().equals("1")){
			if(folioValidacionEditText.getText().toString().equals("") || folioValidacionEditText.getText().toString() == null){
				proceed = false;
				folioValidacionTextView.setTextColor(Color.RED);
					
				neededFields.add("Folio Validacion");
			}
			else{
				validateClosureBean.setFolioValidacion(folioValidacionEditText.getText().toString());
				folioValidacionTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsFolioTIRRequired().equals("1")){
			if(folioTIREditText.getText().toString().equals("") || folioTIREditText.getText().toString() == null){
				proceed = false;
				folioTIRTextView.setTextColor(Color.RED);
					
				neededFields.add("Folio TIR");
			}
			else{
				validateClosureBean.setFolioTIR(folioTIREditText.getText().toString());
				folioTIRTextView.setTextColor(Color.BLACK);
			}
		}

		if(validateClosureBean.getIsMotivoCobroRequired().equals("1")){
			if(motivoCobroEditText.getText().toString().equals("") || motivoCobroEditText.getText().toString() == null){
				proceed = false;
				motivoCobroTextView.setTextColor(Color.RED);
					
				neededFields.add("Motivo Cobro");
			}
			else{
				validateClosureBean.setMotivoCobro(motivoCobroEditText.getText().toString());
				motivoCobroTextView.setTextColor(Color.BLACK);
			}
		}

		try{
			validateClosureBean.setIsSoporteCliente(soporteList.get(soporteClienteSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			validateClosureBean.setIsSoporteCliente("0");
		}
		
		try{
			validateClosureBean.setIsboletin(boletinList.get(boletinSpinner.getSelectedItemPosition()));
		}catch(Exception e){
			validateClosureBean.setIsboletin("0");
		}
		
		if(validateClosureBean.getIsOtorganteSoporteClienteRequired().equals("1")){
			if(otorganteSoporteEditText.getText().toString().equals("") || otorganteSoporteEditText.getText().toString() == null){
				proceed = false;
				otorganteSoporteTextView.setTextColor(Color.RED);
					
				neededFields.add("Otorgante Soporte Cliente");
			}
			else{
				validateClosureBean.setOtorganteSoporteCliente(otorganteSoporteEditText.getText().toString());
				otorganteSoporteTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(cobrableCheckBox.isChecked())
			validateClosureBean.setIsCobrable("1");
		else
			validateClosureBean.setIsCobrable("0");
		
		if(validateClosureBean.getIsInstalacionLlavesRequired().equals("1")){
			if(instalacionLlavesEditText.getText().toString().equals("") || instalacionLlavesEditText.getText().toString() == null){
				proceed = false;
				instalacionLlavesTextView.setTextColor(Color.RED);
					
				neededFields.add("Instalación de Llaves");
			}
			else{
				validateClosureBean.setInstalacionLlaves(instalacionLlavesEditText.getText().toString());
				instalacionLlavesTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsTipoLlaveRequired().equals("1")){
			if(tipoLlaveEditText.getText().toString().equals("") || tipoLlaveEditText.getText().toString() == null){
				proceed = false;
				tipoLlaveTextView.setTextColor(Color.RED);
					
				neededFields.add("Tipo de Llave");
			}
			else{
				validateClosureBean.setTipoLlaves(tipoLlaveEditText.getText().toString());
				tipoLlaveTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsStatusInstalacionLlavesRequired().equals("1")){
			if(statusInstalacionEditText.getText().toString().equals("") || statusInstalacionEditText.getText().toString() == null){
				proceed = false;
				statusInstalacionTextView.setTextColor(Color.RED);
					
				neededFields.add("Status de Instalación");
			}
			else{
				validateClosureBean.setStatusInstalacionLlaves(statusInstalacionEditText.getText().toString());
				statusInstalacionTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsNombrePersonaLlavesARequired().equals("1")){
			if(personaLlavesAEditText.getText().toString().equals("") || personaLlavesAEditText.getText().toString() == null){
				proceed = false;
				personaLlavesATextView.setTextColor(Color.RED);
					
				neededFields.add("Nombre Persona Llaves A");
			}
			else{
				validateClosureBean.setNombrePersonalLlavesA(personaLlavesAEditText.getText().toString());
				personaLlavesATextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsNombrePersonaLlavesBRequired().equals("1")){
			if(personaLlavesBEditText.getText().toString().equals("") || personaLlavesBEditText.getText().toString() == null){
				proceed = false;
				personaLlavesBTextView.setTextColor(Color.RED);
					
				neededFields.add("Nombre Persona Llaves B");
			}
			else{
				validateClosureBean.setNombrePersonalLlavesB(personaLlavesBEditText.getText().toString());
				personaLlavesBTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsTipoPwRequired().equals("1")){
			if(tipoPasswordEditText.getText().toString().equals("") || tipoPasswordEditText.getText().toString() == null){
				proceed = false;
				tipoPasswordTextView.setTextColor(Color.RED);
					
				neededFields.add("Tipo Password");
			}
			else{
				validateClosureBean.setTipoPw(tipoPasswordEditText.getText().toString());
				tipoPasswordTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsTipoTecladoRequired().equals("1")){
			if(tipoTecladoEditText.getText().toString().equals("") || tipoTecladoEditText.getText().toString() == null){
				proceed = false;
				tipoTecladoTextView.setTextColor(Color.RED);
					
				neededFields.add("Tipo Teclado");
			}
			else{
				validateClosureBean.setTisTipoTeclado(tipoTecladoEditText.getText().toString());
				tipoTecladoTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsVersionTecladoEPPRequired().equals("1")){
			if(versionTecladoEditText.getText().toString().equals("") || versionTecladoEditText.getText().toString() == null){
				proceed = false;
				versionTecladoTextView.setTextColor(Color.RED);
					
				neededFields.add("Versión Teclado EPP");
			}
			else{
				validateClosureBean.setVersionTecladoEPP(versionTecladoEditText.getText().toString());
				versionTecladoTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsProcesadorRequired().equals("1")){
			if(procesadorEditText.getText().toString().equals("") || procesadorEditText.getText().toString() == null){
				proceed = false;
				procesadorTextView.setTextColor(Color.RED);
					
				neededFields.add("Procesador");
			}
			else{
				validateClosureBean.setProcesador(procesadorEditText.getText().toString());
				procesadorTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsVelocidadProcesadorRequired().equals("1")){
			if(velocidadProcesadorEditText.getText().toString().equals("") || velocidadProcesadorEditText.getText().toString() == null){
				proceed = false;
				velocidadProcesadorTextView.setTextColor(Color.RED);
					
				neededFields.add("Velocidad Procesador");
			}
			else{
				validateClosureBean.setVelocidadProcesador(velocidadProcesadorEditText.getText().toString());
				velocidadProcesadorTextView.setTextColor(Color.BLACK);
			}
		}

		if(validateClosureBean.getIsMemoriaRequired().equals("1")){
			if(memoriaEditText.getText().toString().equals("") || memoriaEditText.getText().toString() == null){
				proceed = false;
				memoriaTextView.setTextColor(Color.RED);
					
				neededFields.add("Memoria");
			}
			else{
				validateClosureBean.setMemoria(memoriaEditText.getText().toString());
				memoriaTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsCapacidadDiscoDuroRequired().equals("1")){
			if(discoDuroEditText.getText().toString().equals("") || discoDuroEditText.getText().toString() == null){
				proceed = false;
				discoDuroTextView.setTextColor(Color.RED);
					
				neededFields.add("Capacidad Disco Duro");
			}
			else{
				validateClosureBean.setCapacidadDiscoDuro(discoDuroEditText.getText().toString());
				discoDuroTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsMonitorRequired().equals("1")){
			if(monitorEditText.getText().toString().equals("") || monitorEditText.getText().toString() == null){
				proceed = false;
				monitorTextView.setTextColor(Color.RED);
					
				neededFields.add("Monitor");
			}
			else{
				validateClosureBean.setMonitor(monitorEditText.getText().toString());
				monitorTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsLectorTarjetaRequired().equals("1")){
			if(lectorTarjetaEditText.getText().toString().equals("") || lectorTarjetaEditText.getText().toString() == null){
				proceed = false;
				lectorTarjetaTextView.setTextColor(Color.RED);
					
				neededFields.add("Lector Tarjeta");
			}
			else{
				validateClosureBean.setLectorTarjeta(lectorTarjetaEditText.getText().toString());
				lectorTarjetaTextView.setTextColor(Color.BLACK);
			}
		}

		if(validateClosureBean.getIsAplicacionRequired().equals("1")){
			if(aplicacionEditText.getText().toString().equals("") || aplicacionEditText.getText().toString() == null){
				proceed = false;
				aplicacionTextView.setTextColor(Color.RED);
					
				neededFields.add("Aplicación");
			}
			else{
				validateClosureBean.setAplicacion_santander(aplicacionEditText.getText().toString());
				aplicacionTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsVersionRequired().equals("1")){
			if(versionEditText.getText().toString().equals("") || versionEditText.getText().toString() == null){
				proceed = false;
				versionTextView.setTextColor(Color.RED);
					
				neededFields.add("Versión");
			}
			else{
				validateClosureBean.setVersion_santander(versionEditText.getText().toString());
				versionTextView.setTextColor(Color.BLACK);
			}
		}
		
		if(validateClosureBean.getIsCajaRequired().equals("1")){
			if(cajaEditText.getText().toString().equals("") || cajaEditText.getText().toString() == null){
				proceed = false;
				cajaTextView.setTextColor(Color.RED);
					
				neededFields.add("Caja");
			}
			else{
				validateClosureBean.setCaja(cajaEditText.getText().toString());
				cajaTextView.setTextColor(Color.BLACK);
			}
		}

		validateClosureBean.setIdTecnico(sharedPreferences.getString(Constants.PREF_USER_ID, ""));

		//Hace la comparación de la fecha
		String cierreFormatString = "EEE MMM dd HH:mm:ss zzz yyyy";
		SimpleDateFormat df = new SimpleDateFormat(cierreFormatString);

		Date cierreDate = new Date();
		Date actualDate = new Date();
		Date inicioDate = new Date();
		Date AtencionDate = new Date();
		
		//Primero, cambiamos la fecha de formato de FechaInicio
		String inicioDay = validateClosureBean.getFecInicio().substring(0, 2);
		String inicioMonth = validateClosureBean.getFecInicio().substring(3, 5);
		String inicioYear = validateClosureBean.getFecInicio().substring(6, 10);
		
		String actualDay = validateClosureBean.getFecActual().substring(0, 2);
		String actualMonth = validateClosureBean.getFecActual().substring(3, 5);
		String actualYear = validateClosureBean.getFecActual().substring(6, 10);

		String atencionDay = fechaAtencion.substring(0, 2);
		String atencionMonth = fechaAtencion.substring(3, 5);
		String atencionYear = fechaAtencion.substring(6, 10);

		//FECHA DE ATENCION
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		SimpleDateFormat cierreFormat = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
		SimpleDateFormat inicioFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat atencionFormat = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
		
		try {
			inicioDate = dateFormat.parse(inicioMonth + "/" + inicioDay + "/" + inicioYear + " 00:00:00 AM");
			actualDate = dateFormat.parse(actualDay + "/" + actualMonth + "/" + actualYear + " 00:00:00 AM");


		} catch (java.text.ParseException e1) {
			e1.printStackTrace();
		}

		try{
			Calendar c = Calendar.getInstance();
			String formattedDate = df.format(c.getTime());
			
			actualDate = df.parse(formattedDate);
			cierreDate = cierreFormat.parse(validateClosureBean.getFecCierre());
			inicioDate = inicioFormat.parse(validateClosureBean.getFecInicio());
			AtencionDate = atencionFormat.parse(fechaAtencion);

		}
		catch(Exception e){}
		
		if(validateClosureBean.getIsEspecificaTipoFalla().equals("1")) 
		{
			if(tipoFallaSpinner.getSelectedItemPosition() <= 0) {
				proceed = false;
				neededFields.add("Especifica Tipo de Falla Encontrada");
				tipoFallaEncontradaTextView.setTextColor(Color.RED);
			} else {
				tipoFallaEncontradaTextView.setTextColor(Color.BLACK);
			}
		}
		//JDOS 03/03/2017
		if(idCliente.equals("39")){
			if(cierreDate.before(AtencionDate)){
				Log.e("Fecha",fechaAtencion.toString());
				proceed = false;
				neededFields.add("Fecha Cierre (no puede se menor a la Fecha de Cita)");
			}

			//Se inicia cambio 15/01/2018
			//1. Obtener Cliente
			//2. Validar Cliente sea 39
			//3. No permitir que entren cifras menores a 5 digitos
			//4. Solo permitir la entrada de digitos
			if(isNumeric(folioValidacionEditText.getText().toString())){
				if(folioValidacionEditText.getText().toString().length() < 5){
					proceed = false;
					neededFields.add("El campo folio de validacion debe contener 5 o mas digitos.");
				}
			}else{
				proceed = false;
				neededFields.add("El campo de folio de validacion debe contener solo digitos.");
			}

		}
		//


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
		
		if(validateClosureBean.getIsDescTrabajoCatalogRequired().equals("1")){
			validateClosureBean.setIdDescripcionTrabajo(descripcionTrabajoBeanArray.get(descripcionSpinner.getSelectedItemPosition()).getId());
		}
		
		//VERIFIED IF NO SIM IS REQUIRED AND ADD FIELD TO OBJECT \( '.'   )
		//VALIDATE SIM AS ONLY NUMBERS \( '.'   )
		if(validateClosureBean.getIsSIMRequired().equals("1")) {
			if(editTextSim.getText().toString().equals("") || editTextSim.getText().toString() == null){
				proceed = false;
				textViewSim.setTextColor(Color.RED);
				neededFields.add("No. SIM");
			}
			else{
				validateClosureBean.setSIM(editTextSim.getText().toString());
				textViewSim.setTextColor(Color.BLACK);
			}
		}
		
		if(proceed)
		{
			CierreExitoTask ctask = new CierreExitoTask(CierreActivity.this, progressDialog);
	    	setValidateClosureBean(validateClosureBean);
	    	
	    	ctask.execute(validateClosureBean);
		}
		else
		{
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
	
	//Actualiza los datos cuando se cambia el spinner de Codigo 0
	OnItemSelectedListener codigo0OnItemSelectedListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			changeCodigo1Spinner();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	};
	
	//Actualiza los datos cuando se cambia el spinner de Codigo 1
	OnItemSelectedListener codigo1OnItemSelectedListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			changeCodigo2Spinner();
		}
		
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	};
	

	public void changeCodigo1Spinner()
    {   //Crea los datos del spinner Codigo 1 y sus respectivos lists
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(getApplicationContext(), null);
        SQLiteDatabase 	dbRequest 		= sqliteHelper.getReadableDB();
		
        codigos1List 		= new ArrayList<>();
        codigos1ClaveList 	= new ArrayList<>();

        ArrayList<String> codigos1SpinnerArray =  new ArrayList<>();

		Log.e("Select codigos:",codigos0List.get(codigoIntervencion0Spinner.getSelectedItemPosition()));

        Cursor c = dbRequest.rawQuery("SELECT " + SQLiteHelper.CODIGOS1_ID_CODIGO 		+ ","
        										+ SQLiteHelper.CODIGOS1_CLAVE_CODIGO 	+ ","
        										+ SQLiteHelper.CODIGOS1_DESC_CODIGO
        							+ " FROM "	+ SQLiteHelper.CODIGOS1_DB_NAME
        							+ " WHERE " + SQLiteHelper.CLIENTES_ID_CLIENTE 		+ "=" + idCliente
        							+ " AND "   + SQLiteHelper.CODIGOS1_ID_PARENT_0		+ "=" + codigos0List.get(codigoIntervencion0Spinner.getSelectedItemPosition()), null);



      
        codigos1List = new ArrayList<String>();
        if (c.moveToFirst())
        {//Recorremos el cursor hasta que no haya más registros
        	do{
        		codigos1List.add(c.getString(0));
        		codigos1ClaveList.add(c.getString(1));
        		codigos1SpinnerArray.add(c.getString(1) + " - " + c.getString(2));
        	}while(c.moveToNext());
        }
        c.close();
        
        if(codigos1List.size() == 0){
        	c = dbRequest.rawQuery("SELECT " 	+ SQLiteHelper.CODIGOS1_ID_CODIGO 		+ ","
												+ SQLiteHelper.CODIGOS1_CLAVE_CODIGO 	+ ","
												+ SQLiteHelper.CODIGOS1_DESC_CODIGO
								 + " FROM "	+ SQLiteHelper.CODIGOS1_DB_NAME
								 + " WHERE " + SQLiteHelper.CLIENTES_ID_CLIENTE 		+ "=" + idCliente, null);
        	
        	codigos1List = new ArrayList<String>();
            if (c.moveToFirst()) {
            	//Recorremos el cursor hasta que no haya más registros
            	do{
            		codigos1List.add(c.getString(0));
            		codigos1ClaveList.add(c.getString(1));
            		codigos1SpinnerArray.add(c.getString(1) + " - " + c.getString(2));
            	}while(c.moveToNext());
            }
            c.close();
        }
        
        dbRequest.close();

        ArrayAdapter<String> codigos1Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, codigos1SpinnerArray);
        codigoIntervencion1Spinner.setAdapter(codigos1Adapter);
	}
	
	//Crea l os datos del spinner Codigo 1 y sus respectivos lists
	public void changeCodigo2Spinner(){
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(getApplicationContext(), null);
        SQLiteDatabase 	dbRequest 		= sqliteHelper.getReadableDB();
        
        codigos2List 		= new ArrayList<String>();
        codigos2ClaveList 	= new ArrayList<String>();

		ArrayList<String> codigos2SpinnerArray =  new ArrayList<String>();

		Cursor c = dbRequest.rawQuery("SELECT " + SQLiteHelper.CODIGOS2_ID_CODIGO 		+ ","
												+ SQLiteHelper.CODIGOS2_CLAVE_CODIGO 	+ ","
												+ SQLiteHelper.CODIGOS2_DESC_CODIGO
									+ " FROM "	+ SQLiteHelper.CODIGOS2_DB_NAME
									+ " WHERE " + SQLiteHelper.CLIENTES_ID_CLIENTE 		+ "=" + idCliente
									+ " AND "   + SQLiteHelper.CODIGOS2_ID_PARENT_1		+ "=" + codigos1List.get(codigoIntervencion1Spinner.getSelectedItemPosition()), null);

		codigos2List = new ArrayList<String>();
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do{
				codigos2List.add(c.getString(0));
				codigos2ClaveList.add(c.getString(1));
				codigos2SpinnerArray.add(c.getString(1) + " - " + c.getString(2));
			}while(c.moveToNext());
		}
		c.close();
		
		if(codigos2List.size() == 0){
        	c = dbRequest.rawQuery("SELECT " 	+ SQLiteHelper.CODIGOS2_ID_CODIGO 		+ ","
												+ SQLiteHelper.CODIGOS2_CLAVE_CODIGO 	+ ","
												+ SQLiteHelper.CODIGOS2_DESC_CODIGO
								 + " FROM "	+ SQLiteHelper.CODIGOS2_DB_NAME
								 + " WHERE " + SQLiteHelper.CLIENTES_ID_CLIENTE 		+ "=" + idCliente, null);
        	
        	codigos2List = new ArrayList<String>();
            if (c.moveToFirst()) {
            	//Recorremos el cursor hasta que no haya más registros
            	do{
            		codigos2List.add(c.getString(0));
            		codigos2ClaveList.add(c.getString(1));
            		codigos2SpinnerArray.add(c.getString(1) + " - " + c.getString(2));
            	}while(c.moveToNext());
            }
            c.close();
        }
		
		dbRequest.close();

		ArrayAdapter<String> codigos2Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, codigos2SpinnerArray);
		codigoIntervencion2Spinner.setAdapter(codigos2Adapter);		
	}
	
	OnItemSelectedListener tipoFallaOnItemSelectedListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			changeEspTipoFallaSpinner();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	};

	/*
		INICIA CAMBIO 16/08/2017
	 */
	public String getEtiqueta(Integer idetiqueta){
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(getApplicationContext(), null);
		SQLiteDatabase 	dbRequest 		= sqliteHelper.getReadableDB();

		String etiqueta = null;
		Cursor c = dbRequest.rawQuery(	"SELECT " + SQLiteHelper.ETIQUETAS_DESC
										+ " FROM " +SQLiteHelper.ETIQUETAS_BD_NAME
										+ "	WHERE " + SQLiteHelper.ETIQUETAS_ID_CLIENTE + "=" + idCliente
										+ "	AND " + SQLiteHelper.ETIQUETAS_ID + "=" + idetiqueta,null
		);
		if (c.moveToFirst()){
			do {
				etiqueta = c.getString(0);
			}while (c.moveToNext());
		}
		c.close();
		dbRequest.close();

		return etiqueta;
	}
	
	public void changeEspTipoFallaSpinner(){
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(getApplicationContext(), null);
        SQLiteDatabase 	dbRequest 		= sqliteHelper.getReadableDB();
        
		ArrayList<String> espTipoFallaSpinnerArray =  new ArrayList<String>();
		Cursor c = dbRequest.rawQuery(	"SELECT " 	+ SQLiteHelper.ESPTIFA_ID_ESPTIFA + ","
        											+ SQLiteHelper.ESPTIFA_DESC_ESPTIFA
        							  + " FROM " 	+ SQLiteHelper.ESPTIFA_DB_NAME
        							  + " WHERE " 	+ SQLiteHelper.ESPTIFA_ID_FALLA_PARENT + "=" + tipoFallaList.get(tipoFallaSpinner.getSelectedItemPosition()),
        							  null);
        
		espTipoFallaList = new ArrayList<String>();
		espTipoFallaList.add("-1");
		espTipoFallaSpinnerArray.add("...");
		
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do{
				espTipoFallaList.add(c.getString(0));
				espTipoFallaSpinnerArray.add(c.getString(1));
			}while(c.moveToNext());
		}
		c.close();
		dbRequest.close();
		
		ArrayAdapter<String> espTipoFallaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, espTipoFallaSpinnerArray);
		especificaTipoFallaSpinner.setAdapter(espTipoFallaAdapter);	
		
		if(tipoFallaList.get(tipoFallaSpinner.getSelectedItemPosition()).equals("-1")){
	        descripcionEditText.setVisibility(View.VISIBLE);
	      	descripcionSpinner.setVisibility(View.GONE);
		}
		else{
			if(espTipoFallaList.get(especificaTipoFallaSpinner.getSelectedItemPosition()).equals("-1")){
		        descripcionEditText.setVisibility(View.VISIBLE);
		      	descripcionSpinner.setVisibility(View.GONE);
			}
			else{
				if(validateClosureBean.getIsDescTrabajoCatalogRequired().equals("1")){
			        descripcionEditText.setVisibility(View.GONE);
			      	descripcionSpinner.setVisibility(View.VISIBLE);
				}
				else{
			        descripcionEditText.setVisibility(View.VISIBLE);
			      	descripcionSpinner.setVisibility(View.GONE);
				}
			}
		}
	}
	
	public void sendExitoAnswer(SendExitoResponseBean sendExitoResponseBean)
    {
		try
        {
			if(sendExitoResponseBean.getVal().equals("SI"))
            {
				Toast.makeText(getApplicationContext(), sendExitoResponseBean.getDesc(), Toast.LENGTH_SHORT).show();
                isClosed = true;
				onBackPressed();
			}
			else
            {
				Toast.makeText(getApplicationContext(), sendExitoResponseBean.getDesc(), Toast.LENGTH_SHORT).show();
			}
		}
        catch(Exception e)
        {
			Toast.makeText(getApplicationContext(), "Se encontró un error al cerrar la solicitud, inténtelo más tarde", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setValidateClosureBean(ValidateClosureBean validateClosureBean){
		this.validateClosureBean = validateClosureBean;
	}
	public ValidateClosureBean getValidateClosureBean(){
		return validateClosureBean;
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
	
	OnItemSelectedListener tipoFallaDescripcionOnItemSelectedListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if(tipoFallaList.get(tipoFallaSpinner.getSelectedItemPosition()).equals("-1")){
		        descripcionEditText.setVisibility(View.VISIBLE);
		      	descripcionSpinner.setVisibility(View.GONE);
			}
			else{
				if(espTipoFallaList.get(especificaTipoFallaSpinner.getSelectedItemPosition()).equals("-1")){
			        descripcionEditText.setVisibility(View.VISIBLE);
			      	descripcionSpinner.setVisibility(View.GONE);
				}
				else{
					if(validateClosureBean.getIsDescTrabajoCatalogRequired().equals("1")){
						descripcionEditText.setVisibility(View.GONE);
				      	descripcionSpinner.setVisibility(View.VISIBLE);
					}
					else{
						descripcionEditText.setVisibility(View.VISIBLE);
				      	descripcionSpinner.setVisibility(View.GONE);
					}
				}
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	};


    public OnItemSelectedListener onFailFoundItemSelected = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            mDescFailFoundSelected = (position != 0) ? listFailsFoundDesc.get(position) : "";
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    public boolean isNumeric(String text){
    	if (text != null || !text.equals("")){
    		char[] characters = text.toCharArray();
    		for(int i = 0; i < text.length(); i++){
    			if (characters[i] < 48 || characters[i] > 57)
    				return false;
			}
		}
		return true;
	}
}