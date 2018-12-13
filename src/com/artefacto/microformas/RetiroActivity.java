package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.adapters.InfoRetiroUnidadRetiroListAdapter;
import com.artefacto.microformas.beans.InfoRetiroBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.InfoRetiroConnTask;
import com.artefacto.microformas.tasks.RetiroConnTask;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RetiroActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retiro);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.retiro_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.retiro_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_retiro));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		ProgressDialog pg = new ProgressDialog(this);
		pg.setMessage(getString(R.string.sustitucion_espererecoleccion_message));
		pg.setCancelable(false);
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
   	 	mIdAR = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
   	 	mIdTecnico = sharedPreferences.getString(Constants.PREF_USER_ID,"");
   	 	
		InfoRetiroConnTask task = new InfoRetiroConnTask(this,pg, mIdAR,Integer.parseInt(mIdTecnico));
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

	public void setInfo(InfoRetiroBean info)
    {
		//mUnidadesNegocio = info.getUnidadesNegocio();
		mUnidadesNegocioRG = (RadioGroup)findViewById(R.id.retiroUnidadesNegocioRadioGroup);
		mBean = info;
		
		if(mBean.getIdTipoProducto().equals("2") || mBean.getIdTipoProducto().equals("12")){
   	 		TextView unidadesNegocioTextView = (TextView)findViewById(R.id.UnidadesNegocioLabel);
   	 		unidadesNegocioTextView.setText("Refacciones del Negocio");
   	 	}
		
		//Guardamos datos en el SharedPreferences
		if(mSharedPref == null){
			mSharedPref = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		}
		
		Editor editor = mSharedPref.edit();
		editor.putString(Constants.PREF_RET_ID_CLIENTE, mBean.getIdCliente());
		editor.putString(Constants.PREF_RET_ID_NEGOCIO, mBean.getIdNegocio());
		editor.putString(Constants.PREF_RET_EDIT, mBean.getEdit());
		editor.putString(Constants.PREF_RET_IS_DANIADA, "-2");
		
		editor.commit();
		
		mUnidadesNegocio = mBean.getUnidadesNegocio();
		for (InfoRetiroBean.Unidad unidad : mUnidadesNegocio){
			RadioButton rb = new RadioButton(this);
			
			if(mBean.getIdTipoProducto().equals("2") || mBean.getIdTipoProducto().equals("12")){
				rb.setText(getString(R.string.sustitucion_idrefaccion_text) + unidad.getIdUnidad() + "\n" +
						   //getString(R.string.sustitucion_cliente_text) + unidad. + "\n" +
						   getString(R.string.sustitucion_marca_text) + unidad.getDescMarca() + "\n" +
						   getString(R.string.sustitucion_modelo_text) + unidad.getDescModelo() + "\n" +
						   getString(R.string.sustitucion_noserie_text) + unidad.getNoSerie() + "\n" +
						   "No. Equipo: " + unidad.getNoEquipo() + "\n"
						   );
			}
			else{
				rb.setText( getString(R.string.sustitucion_idunidad_text) + unidad.getIdUnidad() + "\n" +
						   	//getString(R.string.sustitucion_cliente_text) + unidad. + "\n" +
						   	getString(R.string.sustitucion_marca_text) + unidad.getDescMarca() + "\n" +
						   	getString(R.string.sustitucion_modelo_text) + unidad.getDescModelo() + "\n" +
							getString(R.string.sustitucion_noserie_text) + unidad.getNoSerie() + "\n" +
							"No. Equipo: " + unidad.getNoEquipo() + "\n"
							);
			}
			mUnidadesNegocioRG.addView(rb);
		}
		
		Button button = (Button)findViewById(R.id.confirmarRetiro);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int radioButtonID = mUnidadesNegocioRG.getCheckedRadioButtonId();
	           	View radioButton = mUnidadesNegocioRG.findViewById(radioButtonID);
	           	mSelectedUN = mUnidadesNegocioRG.indexOfChild(radioButton);
				
	           	if (mSelectedUN < 0){
	           		Toast.makeText(RetiroActivity.this, getString(R.string.sustitucion_unidadsalida_error), Toast.LENGTH_SHORT).show();
	           		return;
	           	}

				String idCliente = mBean.getIdCliente();

				if (idCliente.equals("106")){
					String idUnidad = mUnidadesNegocio.get(mSelectedUN).getIdUnidad();
					String idNegocio = mBean.getIdNegocio();
					String edit = mBean.getEdit();
					String isD = "1";
					String accion="RETIRO";
					String noEquipo = mUnidadesNegocio.get(mSelectedUN).getNoEquipo();

					goToCausasRetiro(mIdAR, mIdTecnico,idUnidad,idNegocio,edit,isD,accion,noEquipo,idCliente);
				}else{
					AlertDialog dialog = new AlertDialog.Builder(RetiroActivity.this).create();
					dialog.setTitle("Confirmación");
					dialog.setMessage("¿Seguro que deseas enviar este retiro?");
					dialog.setCancelable(false);
					dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int buttonId) {
							ProgressDialog progressDialog = new ProgressDialog(RetiroActivity.this);
							progressDialog.setMessage("Enviando Retiro");
							progressDialog.setCancelable(false);
							RetiroConnTask ctask = new RetiroConnTask(RetiroActivity.this, progressDialog);
							String idUnidad = mUnidadesNegocio.get(mSelectedUN).getIdUnidad();
							String idNegocio = mBean.getIdNegocio();
							String edit = mBean.getEdit();
							String isD = "1";
							String accion="RETIRO";
							String noEquipo = mUnidadesNegocio.get(mSelectedUN).getNoEquipo();

							ctask.execute(mIdAR, mIdTecnico,idUnidad,idNegocio,edit,isD,accion,noEquipo);
						}
					});
					dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int buttonId) {

						}
					});
					dialog.show();
				}
			}
			
		});
		
		//Añadimos los retiros
				ArrayList<InfoRetiroBean.UnidadRetiro> retiros = mBean.getUnidadesRetiro();
				TextView lblUnidadesRetiro = (TextView)findViewById(R.id.unidadesRetiroTextView);
				ListView lvwUnidadesRetiro = (ListView)findViewById(R.id.lvwUnidadesRetiro);
				
				if(retiros != null && retiros.size() > 0){
					//Volvemos visibles los controles
					lblUnidadesRetiro.setVisibility(View.VISIBLE);
					lvwUnidadesRetiro.setVisibility(View.VISIBLE);
					
					if(info.getIdTipoProducto().equals("2") || mBean.getIdTipoProducto().equals("12")){
						lblUnidadesRetiro.setText("Refacciones de Retiro");
					}
					
					for(int i = 0; i < retiros.size(); i++){
						retiros.get(i).setIdTipoUnidad(mBean.getIdTipoProducto());
					}
					
					//Añadimos el adaptador 
					lvwUnidadesRetiro.setAdapter(new InfoRetiroUnidadRetiroListAdapter(this, R.layout.listrow_retiro, retiros));
				
					
					//Hacemos el ListView de la altura necesaria
					ListAdapter listAdapter = lvwUnidadesRetiro.getAdapter(); 
					
			        int totalHeight = 0;
			        for (int i = 0; i < listAdapter.getCount(); i++) {
			            View listItem = listAdapter.getView(i, null, lvwUnidadesRetiro);
			            listItem.measure(0, 0);
			            totalHeight += listItem.getMeasuredHeight() + 20;
			        }

			        ViewGroup.LayoutParams params = lvwUnidadesRetiro.getLayoutParams();
			        params.height = totalHeight + (lvwUnidadesRetiro.getDividerHeight() * (listAdapter.getCount() - 1));
			        lvwUnidadesRetiro.setLayoutParams(params);
			        lvwUnidadesRetiro.requestLayout();
				}	
	}

	private void goToCausasRetiro(String idar, String idtecnico,String idUnidad,String idNegocio, String edit, String isD, String accion, String noEquipo, String idCliente){
		Intent intent 	= new Intent(this.getApplicationContext(), CausaRetiroActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("ID_AR",idar);
		intent.putExtra("ID_TECNICO",idtecnico);
		intent.putExtra("ID_NEGOCIO",idNegocio);
		intent.putExtra("EDIT",edit);
		intent.putExtra("IS_DANIADA",isD);
		intent.putExtra("ACCION",accion);
		intent.putExtra("NO_EQUIPO",noEquipo);
		intent.putExtra("ID_UNIDAD",idUnidad);
		intent.putExtra("ID_CLIENTE",idCliente);
		this.getApplicationContext().startActivity(intent);
	}

    private ArrayList<InfoRetiroBean.Unidad> mUnidadesNegocio;

    private RadioGroup mUnidadesNegocioRG;

    private int mSelectedUN;

    private String mIdAR;
    private String mIdTecnico;

    private InfoRetiroBean mBean;
    private SharedPreferences mSharedPref;
}