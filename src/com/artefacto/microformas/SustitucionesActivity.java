package com.artefacto.microformas;


import java.util.ArrayList;

import com.artefacto.microformas.adapters.InfoSustitucionSustitucionListAdapter;
import com.artefacto.microformas.beans.InfoSustitucionBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.InfoSustConnTask;
import com.artefacto.microformas.tasks.SustitucionConnTask;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SustitucionesActivity extends AppCompatActivity
{

	InfoSustitucionBean bean;
	ArrayList<InfoSustitucionBean.Unidad> unidadesTecnico;
	ArrayList<InfoSustitucionBean.Unidad> unidadesNegocio;
	
	RadioGroup unidadesTecnicoRG,unidadesNegocioRG;
	
	String idAR ,noEquipo,idTecnico;
	
	int selectedUT,selectedUN;
	
	SharedPreferences sp;
	
	TextView equipoText;
	EditText equipo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sustituciones);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.substitutions_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.substitutions_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_substitutions));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		ProgressDialog pg = new ProgressDialog(this);
		pg.setMessage(getString(R.string.sustitucion_espererecoleccion_message));
		pg.setCancelable(false);

   	 	SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
   	 	idAR = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
   	 	idTecnico = sharedPreferences.getString(Constants.PREF_USER_ID,""); 
		
   	 	equipoText 	= (TextView)findViewById(R.id.sustitucionesNoEquipoTextView);
   	 	equipo 		= (EditText)findViewById(R.id.sustitucionesNoEquipoEditText);
   	 	
		InfoSustConnTask task = new InfoSustConnTask(this,pg,idAR,Integer.parseInt(idTecnico));
		task.execute("");
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

	public void setInfo(InfoSustitucionBean info){
		this.bean = info;
		
		if(bean.getData_idTipoProducto().equals("2") || bean.getData_idTipoProducto().equals("12")){
			equipoText.setVisibility(View.GONE);
			equipo.setVisibility(View.GONE);
			
			TextView unidadesTecnicoLabel = (TextView)findViewById(R.id.UnidadesTecnicoLabel);
			unidadesTecnicoLabel.setText("Refacciones del Técnico");
			
			TextView unidadesNegocioLabel = (TextView)findViewById(R.id.UnidadesNegocioLabel);
			unidadesNegocioLabel.setText("Refacciones del Negocio");
		}
		
		//Guardamos datos en el SharedPreferences
		if(sp == null){
			sp = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		}
		
		Editor editor = sp.edit();
		editor.putString(Constants.PREF_SUST_EDIT, bean.getData_edit());
		editor.putString(Constants.PREF_SUST_IS_DANIADA, "-2");
		editor.putString(Constants.PREF_SUST_NO_EQUIPO, "");
		
		editor.commit();
		
		unidadesTecnico = info.getUnidadesTecnico();
		unidadesTecnicoRG = (RadioGroup)findViewById(R.id.sustitucionesUnidadesTecnicoRadioGroup);
		
		for (InfoSustitucionBean.Unidad unidad : unidadesTecnico){
			RadioButton rb = new RadioButton(this);
			
			if(unidad.getIdProducto().equals("2") || unidad.getIdProducto().equals("12")){
				rb.setText(getString(R.string.sustitucion_idrefaccion_text) + unidad.getIdUnidad() + "\n" +
						   getString(R.string.sustitucion_cliente_text) + unidad.getDescCliente() + "\n" +
						   getString(R.string.sustitucion_marca_text) + unidad.getDescMarca() + "\n" +
						   getString(R.string.sustitucion_modelo_text) + unidad.getDescModelo() + "\n" +
						   getString(R.string.sustitucion_noserie_text) + unidad.getNoSerie() + "\n" +
						   getString(R.string.sustitucion_status_text) + unidad.getDescStatusUnidad() + "\n" 
						   );
			}
			else{
				rb.setText(getString(R.string.sustitucion_idunidad_text) + unidad.getIdUnidad() + "\n" +
						   getString(R.string.sustitucion_cliente_text) + unidad.getDescCliente() + "\n" +
						   getString(R.string.sustitucion_marca_text) + unidad.getDescMarca() + "\n" +
						   getString(R.string.sustitucion_modelo_text) + unidad.getDescModelo() + "\n" +
						   getString(R.string.sustitucion_noserie_text) + unidad.getNoSerie() + "\n" +
						   getString(R.string.sustitucion_status_text) + unidad.getDescStatusUnidad() + "\n" 
						   );
			}
			rb.setText(getString(R.string.sustitucion_idunidad_text) + unidad.getIdUnidad() + "\n" +
					   getString(R.string.sustitucion_cliente_text) + unidad.getDescCliente() + "\n" +
					   getString(R.string.sustitucion_marca_text) + unidad.getDescMarca() + "\n" +
					   getString(R.string.sustitucion_modelo_text) + unidad.getDescModelo() + "\n" +
					   getString(R.string.sustitucion_noserie_text) + unidad.getNoSerie() + "\n" +
					   getString(R.string.sustitucion_status_text) + unidad.getDescStatusUnidad() + "\n" 
					   );
			unidadesTecnicoRG.addView(rb);
		}
		
		unidadesNegocio = info.getUnidadesNegocio();
		unidadesNegocioRG = (RadioGroup)findViewById(R.id.sustitucionesUnidadesNegocioRadioGroup);
		for (InfoSustitucionBean.Unidad unidad : unidadesNegocio){
			RadioButton rb = new RadioButton(this);
			rb.setText(getString(R.string.sustitucion_idunidad_text) + unidad.getIdUnidad() + "\n" +
					   getString(R.string.sustitucion_cliente_text) + unidad.getDescCliente() + "\n" +
					   getString(R.string.sustitucion_marca_text) + unidad.getDescMarca() + "\n" +
					   getString(R.string.sustitucion_modelo_text) + unidad.getDescModelo() + "\n" +
					   getString(R.string.sustitucion_noserie_text) + unidad.getNoSerie() + "\n" +
					   getString(R.string.sustitucion_status_text) + unidad.getDescStatusUnidad() + "\n" +
					   getString(R.string.sustitucion_unidaddanada_text) + "\n" 
					   );
			unidadesNegocioRG.addView(rb);
		}
		Button button = (Button)findViewById(R.id.confirmarSustitucion);
		button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 int radioButtonID = unidadesTecnicoRG.getCheckedRadioButtonId();
            	 View radioButton = unidadesTecnicoRG.findViewById(radioButtonID);
            	 selectedUT = unidadesTecnicoRG.indexOfChild(radioButton);
            	 
            	 radioButtonID = unidadesNegocioRG.getCheckedRadioButtonId();
            	 radioButton = unidadesNegocioRG.findViewById(radioButtonID);
            	 selectedUN = unidadesNegocioRG.indexOfChild(radioButton);
            	 
            	 if (selectedUT < 0){
            		 Toast.makeText(SustitucionesActivity.this, getString(R.string.sustitucion_unidadentrada_error), Toast.LENGTH_SHORT).show();
            		 return;
            	 }
            	 if (selectedUN < 0){
            		 Toast.makeText(SustitucionesActivity.this, getString(R.string.sustitucion_unidadsalida_error), Toast.LENGTH_SHORT).show();
            		 return;
            	 }

            	 noEquipo = equipo.getText().toString();
            	 
            	 if ((noEquipo == null || noEquipo.equals("")) && !(bean.getData_idTipoProducto().equals("2") || bean.getData_idTipoProducto().equals("12"))){
            		 Toast.makeText(SustitucionesActivity.this, getString(R.string.sustitucion_numeroequipo_error), Toast.LENGTH_SHORT).show();
            		 return;
            	 }
            	  
            	 AlertDialog dialog = new AlertDialog.Builder(SustitucionesActivity.this).create();
                 dialog.setTitle("Confirmación");
                 dialog.setMessage("¿Seguro que deseas enviar esta sustitucion?");
                 dialog.setCancelable(false);
                 dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int buttonId) {
                     	ProgressDialog progressDialog = new ProgressDialog(SustitucionesActivity.this);
                     	progressDialog.setMessage("Enviando Sustitucion");
             			progressDialog.setCancelable(false);
             			SustitucionConnTask ctask = new SustitucionConnTask(SustitucionesActivity.this, progressDialog);
                     	String idUnidadEntrada = unidadesTecnico.get(selectedUT).getIdUnidad();
                     	String idUnidadSalida = unidadesNegocio.get(selectedUN).getIdUnidad();
                     	String idNegocio = bean.getData_idNegocio();
                     	String edit = bean.getData_edit();
                     	String accion = "SWAP";
                     	String isDaniada = "1";
                     	
                     	ctask.execute(idAR,idTecnico,idUnidadEntrada,idUnidadSalida,idNegocio,edit,isDaniada,accion,noEquipo);
                     }
                 });
                 dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int buttonId) {

                     }
                 });
                 dialog.show();
             }
         });
		
		//Añadimos las Sustituciones
		ArrayList<InfoSustitucionBean.Sustitucion> sustituciones = bean.getSustituciones();
		TextView lblSustitucionesConfirmadas = (TextView)findViewById(R.id.sustitucionesConfirmadasTextView);
		ListView lvwSustitucionesConfirmadas = (ListView)findViewById(R.id.lvwSustitucionesConfirmadas);
		
		
		if(sustituciones != null && sustituciones.size() > 0){
			//Volvemos visibles los controles
			lblSustitucionesConfirmadas.setVisibility(View.VISIBLE);
			lvwSustitucionesConfirmadas.setVisibility(View.VISIBLE);
			
			//Añadimos el adaptador
			
			for(int i = 0; i < sustituciones.size(); i++){
				sustituciones.get(i).setS_idTipoProducto(bean.getData_idTipoProducto());
			}
				
			lvwSustitucionesConfirmadas.setAdapter(new InfoSustitucionSustitucionListAdapter(this, R.layout.listrow_sustitucion,sustituciones));
			
			//Hacemos el ListView de la altura necesaria
			ListAdapter listAdapter = lvwSustitucionesConfirmadas.getAdapter(); 
			
	        int totalHeight = 0;
	        for (int i = 0; i < listAdapter.getCount(); i++) {
	            View listItem = listAdapter.getView(i, null, lvwSustitucionesConfirmadas);
	            listItem.measure(0, 0);
	            totalHeight += listItem.getMeasuredHeight() + 20;
	        }

	        ViewGroup.LayoutParams params = lvwSustitucionesConfirmadas.getLayoutParams();
	        params.height = totalHeight + (lvwSustitucionesConfirmadas.getDividerHeight() * (listAdapter.getCount() - 1));
	        lvwSustitucionesConfirmadas.setLayoutParams(params);
	        lvwSustitucionesConfirmadas.requestLayout();
			
		}	
	}	
}