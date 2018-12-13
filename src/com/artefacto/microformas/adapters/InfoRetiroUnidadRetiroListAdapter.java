package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.R;
import com.artefacto.microformas.beans.InfoRetiroBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.RetiroConnTask;

public class InfoRetiroUnidadRetiroListAdapter extends ArrayAdapter<InfoRetiroBean.UnidadRetiro> {
	private ArrayList<InfoRetiroBean.UnidadRetiro> items;
	TextView lblIdUnidad;
	TextView lblNoEquipo;
	
	public InfoRetiroUnidadRetiroListAdapter(Context context, int textViewResourceId, ArrayList<InfoRetiroBean.UnidadRetiro> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listrow_retiro, null);
		}

		InfoRetiroBean.UnidadRetiro retiro = items.get(position);
		
		if(retiro != null){
			lblIdUnidad 				= (TextView)v.findViewById(R.id.listrowretiroUnidadTextViewContent);
			TextView lblCliente 		= (TextView)v.findViewById(R.id.listrowretiroClienteTextViewContent);
			TextView lblMarca			= (TextView)v.findViewById(R.id.listrowretiroMarcaTextViewContent);
			TextView lblModelo 			= (TextView)v.findViewById(R.id.listrowretiroModeloTextViewContent);
			TextView lblNoSerie 		= (TextView)v.findViewById(R.id.listrowretiroNoSerieTextViewContent);
			lblNoEquipo 				= (TextView)v.findViewById(R.id.listrowretiroNoEquipoTextViewContent);
			Button 	btnCancelarUnidad 	= (Button)v.findViewById(R.id.listrowretiroCancelarRetiro);

			TextView listrowretiroUnidadTextView = (TextView)v.findViewById(R.id.listrowretiroUnidadTextView);
			
			SharedPreferences sp = v.getContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
			
			if(lblIdUnidad != null){
				lblIdUnidad.setText(retiro.getIdUnidad());
			}
			if(lblCliente != null){
				lblCliente.setText(sp.getString(Constants.PREF_RET_ID_CLIENTE, ""));
			}
			if(lblMarca != null){
				lblMarca.setText(retiro.getDescMarca());
			}
			if(lblModelo != null){
				lblModelo.setText(retiro.getDescModelo());
			}
			if(lblNoSerie != null){
				lblNoSerie.setText(retiro.getNoSerie());
			}
			if(lblNoEquipo != null){
				lblNoEquipo.setText(retiro.getNoEquipo());
			}
			
			if(retiro.getIdTipoUnidad().equals("2")){
				listrowretiroUnidadTextView.setText("ID Refacción: ");
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
					String idUnidad		= lblIdUnidad.getText().toString();
					String idNegocio	= sp.getString(Constants.PREF_RET_ID_NEGOCIO, "");
					String edit			= sp.getString(Constants.PREF_RET_EDIT, "");
					String isDaniada	= sp.getString(Constants.PREF_RET_IS_DANIADA, "");
					String noEquipo		= lblNoEquipo.getText().toString();
					
					String accion 		= "CANCEL_RETIRO";
					
					//Hacemos la peticion de CANCEL_RETIRO
					try{
						ProgressDialog pg = new ProgressDialog(vp.getContext());
						pg.setMessage("Registrando petición");
						pg.setCancelable(false);
						
						RetiroConnTask iTask = new RetiroConnTask((Activity)vp.getContext(), pg);
                    	iTask.execute(idAR,idTecnico,idUnidad,idNegocio,edit,isDaniada,accion,noEquipo,"-1");
					}catch(Exception e){
						Toast.makeText(vp.getContext(), "Error al cancelar el retiro.", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
		}
		return v;
	}	
}