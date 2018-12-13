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
import com.artefacto.microformas.beans.InfoSustitucionBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.SustitucionConnTask;

public class InfoSustitucionSustitucionListAdapter  extends ArrayAdapter<InfoSustitucionBean.Sustitucion> {
	private ArrayList<InfoSustitucionBean.Sustitucion> items;

	public InfoSustitucionSustitucionListAdapter(Context context, int textViewResourceId, ArrayList<InfoSustitucionBean.Sustitucion> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listrow_sustitucion, null);
		}

		InfoSustitucionBean.Sustitucion sustitucion = items.get(position);

		if(sustitucion != null){
			TextView lblTecnico 			= (TextView)v.findViewById(R.id.lblTecnico);
			
			TextView lblNegocio 			= (TextView)v.findViewById(R.id.lblNegocio);
			TextView lblNoSerieEntrada		= (TextView)v.findViewById(R.id.lblNoSerieEntrada);
			TextView lblNoSerieSalida 		= (TextView)v.findViewById(R.id.lblNoSerieSalida);
			TextView lblCliente 			= (TextView)v.findViewById(R.id.lblCliente);
			TextView lblIdUnidadEntrada 	= (TextView)v.findViewById(R.id.lblUnidadEntrada);
			TextView lblIdUnidadSalida 		= (TextView)v.findViewById(R.id.lblUnidadSalida);
			TextView lblIdNegocio 			= (TextView)v.findViewById(R.id.lblIdNegocio);
			Button 	btnCancelarUnidad 		= (Button)v.findViewById(R.id.btnCancelarSustitucion);

			if(lblTecnico != null){
				lblTecnico.setText(sustitucion.getS_descTecnico());
			}
			if(lblNegocio != null){
				lblNegocio.setText(sustitucion.getS_descNegocio());
			}
			if(lblNoSerieEntrada != null){
				lblNoSerieEntrada.setText(sustitucion.getS_noSerieEntrada());
			}
			if(lblNoSerieSalida != null){
				lblNoSerieSalida.setText(sustitucion.getS_noSerieSalida());
			}
			if(lblCliente != null){
				lblCliente.setText(sustitucion.getS_descCliente());
			}
			if(lblIdUnidadEntrada != null){
				lblIdUnidadEntrada.setText(sustitucion.getS_idUnidadEntrada());
			}
			if(lblIdUnidadSalida != null){
				lblIdUnidadSalida.setText(sustitucion.getS_idUnidadSalida());
			}
			if(lblIdNegocio != null){
				lblCliente.setText(sustitucion.getS_idNegocio());
			}
			
			//Añadir clickListener al boton
			btnCancelarUnidad.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {		
					//Obtenemos el View de la fila de la lista
					View vp = (View)v.getParent();
					
					//Obtenemos los controles
					TextView lblIdUnidadEntrada = (TextView)vp.findViewById(R.id.lblUnidadEntrada);
					TextView lblIdUnidadSalida 	= (TextView)vp.findViewById(R.id.lblUnidadSalida);
				
					//Obtenemos datos necesarios
					SharedPreferences sp = vp.getContext().getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
					
					String idAR 			= sp.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
					String idTecnico		= sp.getString(Constants.PREF_USER_ID, "");
					String edit				= sp.getString(Constants.PREF_SUST_EDIT, "");
					String isDaniada		= sp.getString(Constants.PREF_SUST_IS_DANIADA, "");
					String noEquipo			= sp.getString(Constants.PREF_SUST_NO_EQUIPO, "");
					String idUnidadEntrada	= lblIdUnidadEntrada.getText().toString();
					String idUnidadSalida	= lblIdUnidadSalida.getText().toString();
					String idNegocio		= items.get(0).getS_idNegocio();
					String accion 			= "CANCEL_SWAP"; 
					
					//Hacemos la peticion de CANCEL_SWAP
					try{
						ProgressDialog pg = new ProgressDialog(vp.getContext());
						pg.setMessage("Registrando petición");
						pg.setCancelable(false);
						
						SustitucionConnTask iTask = new SustitucionConnTask((Activity)vp.getContext(), pg);
						iTask.execute(idAR, idTecnico, idUnidadEntrada,  idUnidadSalida, idNegocio, edit, isDaniada, accion, noEquipo);
					}catch(Exception e){
						Toast.makeText(vp.getContext(), "Error al cancelar la sustitución.", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
		}
		return v;
	}

	
}
