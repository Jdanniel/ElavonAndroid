package com.artefacto.microformas.adapters;

import java.util.ArrayList;


import com.artefacto.microformas.ActualizacionActivity;
import com.artefacto.microformas.R;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.tasks.BorrarUnidadTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class UnidadesNegocioAdapter extends ArrayAdapter<UnitBean>{
	private ArrayList<UnitBean> 	items;
	private UnitBean unitBeanToSend;
	private ActualizacionActivity 	activity;
	
	public UnidadesNegocioAdapter(ActualizacionActivity activity, int textViewResourceId, ArrayList<UnitBean> items) {
		super(activity, textViewResourceId, items);
		this.items = items;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
 
		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.info_unidades_list, null);
		}

		UnitBean unitBean = items.get(position);
		unitBeanToSend = unitBean;
		
		if(unitBean != null){
			TextView 	idUnidadTextView 			= (TextView)v.findViewById(R.id.unidadIdUnidadTextView);
			TextView 	idUnidadTextViewContent 	= (TextView)v.findViewById(R.id.unidadIdUnidadTextViewContent);
			TextView 	negocioTextViewContent 		= (TextView)v.findViewById(R.id.unidadNegocioTextViewContent);
			TextView 	clienteTextViewContent 		= (TextView)v.findViewById(R.id.unidadClienteTextViewContent);
			TextView 	marcaTextViewContent 		= (TextView)v.findViewById(R.id.unidadMarcaTextViewContent);
			TextView 	modeloTextViewContent 		= (TextView)v.findViewById(R.id.unidadModeloTextViewContent);
			TextView 	noSerieTextViewContent 		= (TextView)v.findViewById(R.id.unidadNoSerieTextViewContent);
			TextView 	noInventarioTextViewContent = (TextView)v.findViewById(R.id.unidadNoInventarioTextViewContent);
			TextView 	statusTextViewContent 		= (TextView)v.findViewById(R.id.unidadStatusTextViewContent);
			TextView textConnectivity = (TextView) v.findViewById(R.id.addUnitShowConnectivityValue);
			TextView textSoftware = (TextView) v.findViewById(R.id.addUnitShowSoftwareValue);


			if(idUnidadTextViewContent != null){
				idUnidadTextViewContent.setText(unitBean.getId());
			}
			if(negocioTextViewContent != null){
				negocioTextViewContent.setText(unitBean.getDescBusiness());
			}
			if(clienteTextViewContent != null){
				clienteTextViewContent.setText(unitBean.getDescClient());
			}
			if(marcaTextViewContent != null){
				marcaTextViewContent.setText(unitBean.getDescBrand());
			}
			if(modeloTextViewContent != null){
				modeloTextViewContent.setText(unitBean.getDescModel());
			} 
			if(noSerieTextViewContent != null){
				noSerieTextViewContent.setText(unitBean.getNoSerie());
			}
			if(noInventarioTextViewContent != null){
				noInventarioTextViewContent.setText(unitBean.getNoIMEI());
			}
			if(statusTextViewContent != null){
				statusTextViewContent.setText(unitBean.getIdUnitStatus());
			}

			if(textConnectivity != null)
			{
				textConnectivity.setText(unitBean.getDescConnectivity());
			}

			if(textSoftware != null)
			{
				textSoftware.setText(unitBean.getDescSoftware());
			}
			
			Button borrarUnidadButton = (Button)v.findViewById(R.id.unidadBorrarUnidadButton);
			
			if(unitBean.getIdProduct() != null && unitBean.getIdProduct().equals("2") || unitBean.getIdProduct().equals("12")){
				idUnidadTextView.setText("ID Refacción: ");
				borrarUnidadButton.setText("Borrar Refacción");
			}
			 
			borrarUnidadButton.setOnClickListener(borrarUnidadOnClickListener);
		}
		return v;
	}
	
	OnClickListener borrarUnidadOnClickListener = new OnClickListener() {
		public void onClick(View arg0) {
			ProgressDialog progressDialog = new ProgressDialog(activity);
        	progressDialog.setMessage("Borrando unidad...");
			progressDialog.setCancelable(false);
			
        	BorrarUnidadTask borrarUnidadTask = new BorrarUnidadTask(activity, progressDialog, unitBeanToSend.getId(), unitBeanToSend.getIdBusiness());
        	borrarUnidadTask.execute(unitBeanToSend.getId(), unitBeanToSend.getDescBusiness());
		}
	};
}