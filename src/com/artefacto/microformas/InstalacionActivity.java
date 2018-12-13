package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.adapters.InfoInstalacionUnidadListAdapter;
import com.artefacto.microformas.beans.InfoInstalacionBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.InfoInstalacionConnTask;
import com.artefacto.microformas.tasks.InstalacionConnTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InstalacionActivity extends Activity {

	private SharedPreferences sp;
	String idAR;
	String idTecnico;
	String idUnidad;
	String idNegocio;
	String edit;
	InfoInstalacionBean infoBean;
	boolean afterSearch;
	boolean getInfo;
	boolean infoObtained;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instalacion);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}
		
		Intent intent = this.getIntent();
		
		afterSearch = intent.getBooleanExtra("After_Search", false);
		getInfo = intent.getBooleanExtra("Get_Info", false);
		
				
		if(sp == null){
			sp = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		}
		
		idAR = sp.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
		idTecnico = sp.getString(Constants.PREF_USER_ID, "");
		infoObtained = sp.getBoolean(Constants.PREF_INST_INFO_OBTAINED, false);
		
		if(getInfo || !infoObtained){
			ProgressDialog pg = new ProgressDialog(this);
			pg.setMessage(getString(R.string.instalacion_info));
			pg.setCancelable(false);
			
			
			//Quitamos el id de instalacion anterior
			if(getInfo){
				Editor editor = sp.edit();
				
				editor.remove(Constants.UNIDAD_INSTALACION_ID);
				
				editor.commit();
			}
			
			InfoInstalacionConnTask task = new InfoInstalacionConnTask(this,pg,idAR);
			task.execute(""); 
		}
		else if(infoObtained){
			InfoInstalacionBean bean = new InfoInstalacionBean();
			bean.setIdCliente(sp.getString(Constants.PREF_INST_CLIENTE, ""));
			bean.setIdNegocio(sp.getString(Constants.PREF_INST_NEGOCIO, ""));
			bean.setIdTipoProducto(sp.getString(Constants.PREF_INST_TIPO_PRODUCTO, ""));
			bean.setEdit(sp.getString(Constants.PREF_INST_EDIT, ""));
			bean.setIdIsReq(sp.getString(Constants.PREF_INST_IS_REQ, ""));
			
			String unidadesNegocioString = sp.getString(Constants.PREF_INST_UNIDADES_INSTALADAS, "");
			
			
			
			//Parseamos unidades de negocio
			String unidadesNegocio[] = unidadesNegocioString.split(";");
			String atributos[];
			ArrayList<InfoInstalacionBean.Unidad> unidadesNegocioList = new ArrayList<InfoInstalacionBean.Unidad>();
			InfoInstalacionBean.Unidad unidadTemp;
			
			for(String unidad : unidadesNegocio){
				if(unidad.length() > 0){
					atributos = unidad.split(",");
					
					unidadTemp = new InfoInstalacionBean.Unidad();
					
					unidadTemp.setDescMarca(atributos[0]);
					unidadTemp.setDescModelo(atributos[1]);
					unidadTemp.setDescNegocio(atributos[2]);
					unidadTemp.setDescStatusUnidad(atributos[3]);
					unidadTemp.setIdModelo(atributos[4]);
					unidadTemp.setIdStatusUnidad(atributos[5]);
					unidadTemp.setIdUnidad(atributos[6]);
					unidadTemp.setNoEquipo(atributos[7]);
					unidadTemp.setNoIMEI(atributos[8]);
					unidadTemp.setNoInventario(atributos[9]);
					unidadTemp.setNoSerie(atributos[10]);
					unidadTemp.setPosicionInventario(atributos[11]);
					
					unidadesNegocioList.add(unidadTemp);
					
				}
			}
			bean.setUnidadesNegocio(unidadesNegocioList);	
			
			setInfo(bean);
		}
	}

	public void confirmarButtonClick(View v){
		try{
			if(sp == null){
				sp = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			}
			
			
			TextView lblIdUnidad 	= (TextView)findViewById(R.id.idUnidadTextViewContent);
			
			idTecnico = sp.getString(Constants.PREF_USER_ID, "");
			idUnidad = lblIdUnidad.getText().toString();
			idNegocio = infoBean.getIdNegocio();
			String accion = "INSTALL";
			
			EditText noEquipoEditText = (EditText)findViewById(R.id.noEquipoTextViewContent);
			String noEquipo = noEquipoEditText.getText().toString();
			
			if (Integer.parseInt(infoBean.getIdIsReq()) == 1) {
				if (noEquipo.equals("")) {
					Toast.makeText(this, R.string.instalacion_escribirnoequipo_error, Toast.LENGTH_LONG).show();
					return;
				}
			}
			
			ProgressDialog pg = new ProgressDialog(this);
			pg.setMessage("Registrando petición");
			pg.setCancelable(false);
			
			InstalacionConnTask iTask = new InstalacionConnTask(this, pg);
			iTask.execute(idAR, idTecnico, idUnidad, idNegocio, edit, accion, noEquipo);
			
			//Set info to sp
			if(sp == null){
				sp = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			}
			
			Editor editor = sp.edit();
		
			editor.putBoolean(Constants.PREF_INST_INFO_OBTAINED, false);
			
			editor.commit();
			
		}catch(Exception e){
			Toast.makeText(this, "Error en la instalación.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void buscarUnidadButtonClick(View v){
		Intent buscar = new Intent(this, UnidadInstalacionCatalogsActivity.class);
		
		buscar.putExtra("Id_Cliente", Integer.parseInt(infoBean.getIdCliente()));
		buscar.putExtra("Id_Tipo_Producto", Integer.parseInt(infoBean.getIdTipoProducto()));
		startActivity(buscar);
		
	}
	
	public void setInfo(InfoInstalacionBean bean){
		infoBean = bean;
		
		EditText noEquipoEditText = (EditText)findViewById(R.id.noEquipoTextViewContent);
		TextView lblNoEquipo = (TextView)findViewById(R.id.noEquipoTextView);
		
		TextView lblIdUnidad = (TextView)findViewById(R.id.idUnidadTextView);
		TextView lblDescCliente = (TextView)findViewById(R.id.clienteTextView);
		TextView lblDescMarca = (TextView)findViewById(R.id.marcaTextView);
		TextView lblDescModelo = (TextView)findViewById(R.id.modeloTextView);
		TextView lblDescNoSerie = (TextView)findViewById(R.id.noSerieTextView);
		TextView lblDescStatusUnidad = (TextView)findViewById(R.id.statusUnidadTextView);
		TextView lblIdUnidadContent = (TextView)findViewById(R.id.idUnidadTextViewContent);
		TextView lblDescClienteContent = (TextView)findViewById(R.id.clienteTextViewContent);
		TextView lblDescMarcaContent = (TextView)findViewById(R.id.marcaTextViewContent);
		TextView lblDescModeloContent = (TextView)findViewById(R.id.modeloTextViewContent);
		TextView lblDescNoSerieContent = (TextView)findViewById(R.id.noSerieTextViewContent);
		TextView lblDescStatusUnidadContent = (TextView)findViewById(R.id.statusUnidadTextViewContent);
		TextView lblUnidadesInstaladas = (TextView)findViewById(R.id.unidadesInstaladasTextView);
		
		Button btnBuscarUnidad = (Button)findViewById(R.id.buscarUnidadButton);
		Button btnConfirmar = (Button)findViewById(R.id.confirmarButton);
		
		//Se cambian los label en caso de ser tipo refacción
		if(bean.getIdTipoProducto().equals("2") || bean.getIdTipoProducto().equals("12")){
			TextView lblIdUnidadB 	= (TextView)findViewById(R.id.idUnidadTextView);
			TextView lblstatusUnidad= (TextView)findViewById(R.id.statusUnidadTextView);
			
			btnBuscarUnidad.setText("Buscar Refacción");
			lblIdUnidadB.setText("Id Refacción:");
			lblstatusUnidad.setText("Status Refacción");
		}
		
		ListView lvwUnidadesInstaladas = (ListView)findViewById(R.id.lvwUnidadesInstaladas);
		
		ArrayList<InfoInstalacionBean.Unidad> unidadesNegocio = null;
	
		
		edit = bean.getEdit();
		
		if (edit.equals("1")) {
			SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
			SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
			
			String query = "SELECT " 	+ SQLiteHelper.UNIDADES_ID_UNIDAD 		+ "," 
										+ SQLiteHelper.UNIDAD_NO_SERIE 			+ "," 
										+ SQLiteHelper.UNIDAD_DESC_MODELO 		+ "," 
										+ SQLiteHelper.UNIDAD_DESC_CLIENTE	 	+ ","
										+ SQLiteHelper.UNIDAD_DESC_MARCA	 	+ "," 
										+ SQLiteHelper.UNIDAD_STATUS
							+ " from " 	+ SQLiteHelper.UNIDAD_DB_NAME
							+ " where " 	+ SQLiteHelper.UNIDADES_ID_UNIDAD + " = " + sp.getString(Constants.UNIDAD_INSTALACION_ID, "-1");
			
			Cursor c = db.rawQuery(query, null);
			try{
	        	if (c != null ) {
	        		if  (c.moveToFirst()) {
	        			lblIdUnidadContent.setText(c.getString(0));
	        			lblDescNoSerieContent.setText(c.getString(1));
	        			lblDescModeloContent.setText(c.getString(2));
	        			lblDescClienteContent.setText(c.getString(3));
	        			lblDescMarcaContent.setText(c.getString(4));
	        			lblDescStatusUnidadContent.setText(c.getString(5));
	        			
	        			//Set all fields to visible
	        			lblIdUnidad.setVisibility(View.VISIBLE);
	        			lblDescNoSerie.setVisibility(View.VISIBLE);
	        			lblDescModelo.setVisibility(View.VISIBLE);
	        			lblDescCliente.setVisibility(View.VISIBLE);
	        			lblDescMarca.setVisibility(View.VISIBLE);
	        			lblDescStatusUnidad.setVisibility(View.VISIBLE);
	        			lblIdUnidadContent.setVisibility(View.VISIBLE);
	        			lblDescNoSerieContent.setVisibility(View.VISIBLE);
	        			lblDescModeloContent.setVisibility(View.VISIBLE);
	        			lblDescClienteContent.setVisibility(View.VISIBLE);
	        			lblDescMarcaContent.setVisibility(View.VISIBLE);
	        			lblDescStatusUnidadContent.setVisibility(View.VISIBLE);
	        			
	        			lblNoEquipo.setVisibility(View.VISIBLE);
	        			noEquipoEditText.setVisibility(View.VISIBLE);
	        			
	        			//Make the EditText noEquipo enabled
	        			noEquipoEditText.setEnabled(true);
	        			 
	        			btnBuscarUnidad.setText(R.string.instalacion_cambiarunidad_text);
	        			btnConfirmar.setVisibility(View.VISIBLE);
	        			
	        			if(infoBean.getIdTipoProducto().equals("2") || bean.getIdTipoProducto().equals("12")){
	        				btnBuscarUnidad.setText("Cambiar Refacción");
	        			}
	        		}
	        	}
	        }
	        catch(Exception e){
	        	return;
	        }
			
			//Añadimos las unidades de negocio
			unidadesNegocio = infoBean.getUnidadesNegocio();
			
			//Pone el idProducto
			
			
			for(int i = 0; i < unidadesNegocio.size(); i++){
				unidadesNegocio.get(i).setIdProducto(infoBean.getIdTipoProducto());
			}
			
			if(unidadesNegocio != null && unidadesNegocio.size() > 0){
				//Volvemos visibles los controles
				lblUnidadesInstaladas.setVisibility(View.VISIBLE);
				lvwUnidadesInstaladas.setVisibility(View.VISIBLE);
				
				//Añadimos el adaptador
				lvwUnidadesInstaladas.setAdapter(new InfoInstalacionUnidadListAdapter(this, R.layout.listrow_unidad_instalacion, unidadesNegocio));
				
				//Hacemos el ListView de la altura necesaria
				ListAdapter listAdapter = lvwUnidadesInstaladas.getAdapter(); 
				
		        int totalHeight = 0;
		        for (int i = 0; i < listAdapter.getCount(); i++) {
		            View listItem = listAdapter.getView(i, null, lvwUnidadesInstaladas);
		            listItem.measure(0, 0);
		            totalHeight += listItem.getMeasuredHeight() + 20;
		        }

		        ViewGroup.LayoutParams params = lvwUnidadesInstaladas.getLayoutParams();
		        params.height = totalHeight + (lvwUnidadesInstaladas.getDividerHeight() * (listAdapter.getCount() - 1));
		        lvwUnidadesInstaladas.setLayoutParams(params);
		        lvwUnidadesInstaladas.requestLayout();
			}
	        c.close();
	        db.close();
		}
		
		//Set info to sp
		if(sp == null){
			sp = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		}
		
		//generamos el String de unidades de negocio
		String unidadesNegocioString = "";
		InfoInstalacionBean.Unidad unidadTemp;
		
		if(unidadesNegocio != null && unidadesNegocio.size() > 0){
			for(int i = 0; i < unidadesNegocio.size(); i++){
				unidadTemp = unidadesNegocio.get(i);
				
				unidadesNegocioString += unidadTemp.getDescMarca()			+ "," +
										unidadTemp.getDescModelo()			+ "," +
										unidadTemp.getDescNegocio()			+ "," +
										unidadTemp.getDescStatusUnidad()	+ "," +
										unidadTemp.getIdModelo()			+ "," +
										unidadTemp.getIdStatusUnidad()		+ "," +
										unidadTemp.getIdUnidad()			+ "," +
										unidadTemp.getNoEquipo()			+ "," +
										unidadTemp.getNoIMEI() 				+ "," +
										unidadTemp.getNoInventario() 		+ "," +
										unidadTemp.getNoSerie() 			+ "," +
										unidadTemp.getPosicionInventario()	+ " ;";			
			}
		}
		
		Editor editor = sp.edit();
		editor.putBoolean(Constants.PREF_INST_INFO_OBTAINED, true);
		editor.putString(Constants.PREF_INST_CLIENTE, infoBean.getIdCliente());
		editor.putString(Constants.PREF_INST_NEGOCIO, infoBean.getIdNegocio());
		editor.putString(Constants.PREF_INST_TIPO_PRODUCTO, infoBean.getIdTipoProducto());
		editor.putString(Constants.PREF_INST_EDIT, infoBean.getEdit());
		editor.putString(Constants.PREF_INST_IS_REQ, infoBean.getIdIsReq());
		editor.putString(Constants.PREF_INST_UNIDADES_INSTALADAS, unidadesNegocioString);
		
		editor.commit();
	}
}