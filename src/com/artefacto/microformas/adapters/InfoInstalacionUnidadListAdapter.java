package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.InstalacionActivity;
import com.artefacto.microformas.R;
import com.artefacto.microformas.beans.InfoInstalacionBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.InstalacionConnTask;

public class InfoInstalacionUnidadListAdapter  extends ArrayAdapter<InfoInstalacionBean.Unidad> {
	private ArrayList<InfoInstalacionBean.Unidad> items;
	InstalacionActivity activity;
	TextView lblIdUnidad;
	TextView lblNoEquipo;
	
	public InfoInstalacionUnidadListAdapter(InstalacionActivity activity, int textViewResourceId, ArrayList<InfoInstalacionBean.Unidad> items) {
		super(activity, textViewResourceId, items);
		this.items = items;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listrow_unidad_instalacion, null);
		}

		InfoInstalacionBean.Unidad unidad = items.get(position);

		if(unidad != null){
			TextView    lblIdUnidadB			= (TextView)v.findViewById(R.id.listrowunidadIdUnidadTextView);
			lblIdUnidad 						= (TextView)v.findViewById(R.id.listrowunidadIdUnidadTextViewContent);
			TextView 	lblMarca 				= (TextView)v.findViewById(R.id.listrowunidadMarcaTextViewContent);
			TextView 	lblModelo 				= (TextView)v.findViewById(R.id.listrowunidadModeloTextViewContent);
			TextView 	lblNoSerie 				= (TextView)v.findViewById(R.id.listrowunidadNoSerieTextViewContent);
			lblNoEquipo 						= (TextView)v.findViewById(R.id.listrowunidadNoEquipoTextViewContent);
			Button 		btnCancelarUnidad 		= (Button)v.findViewById(R.id.listrowunidadCancelarUnidadButton);
			
			if(unidad.getIdProducto().equals("2") || unidad.getIdProducto().equals("12"))
				lblIdUnidad.setText("Id Refacción: ");
			
			if(lblIdUnidad != null){
				lblIdUnidad.setText(unidad.getIdUnidad());
			}
			if(lblMarca != null){
				lblMarca.setText(unidad.getDescMarca());
			}
			if(lblModelo != null){
				lblModelo.setText(unidad.getDescModelo());
			}
			if(lblNoSerie != null){
				lblNoSerie.setText(unidad.getNoSerie());
			}
			if(lblNoEquipo != null){
				lblNoEquipo.setText(unidad.getNoEquipo());
			}
			
			if(unidad.getIdProducto().equals("2") || unidad.getIdProducto().equals("12")){
				lblIdUnidadB.setText("Id Refacción: ");
			}
			
			//Añadir clickListener al boton
			btnCancelarUnidad.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {		
					//Obtenemos el View de la fila de la lista
					View vp = (View)v.getParent();

					//Obtenemos datos necesarios
					SharedPreferences sp = vp.getContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
					
					String idAR 		= sp.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
					String idTecnico	= sp.getString(Constants.PREF_USER_ID, "");
					String idNegocio	= sp.getString(Constants.PREF_INST_NEGOCIO, "");
					String edit			= sp.getString(Constants.PREF_INST_EDIT, "");
					String idUnidad		= lblIdUnidad.getText().toString();
					String noEquipo		= lblNoEquipo.getText().toString();
					String accion 		= "CANCEL_INSTALL";
					
					//Hacemos la peticion de CANCEL_INSTALL
					try{
						ProgressDialog pg = new ProgressDialog(vp.getContext());
						pg.setMessage("Registrando petición");
						pg.setCancelable(false);
						
						InstalacionConnTask iTask = new InstalacionConnTask((Activity)vp.getContext(), pg);
						iTask.execute(idAR, idTecnico, idUnidad, idNegocio, edit, accion, noEquipo);
					}catch(Exception e){
						Toast.makeText(vp.getContext(), "Error al cancelar la instalación.", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
		}
		return v;
	}	
}