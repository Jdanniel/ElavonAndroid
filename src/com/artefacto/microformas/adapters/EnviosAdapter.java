package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.artefacto.microformas.R;
import com.artefacto.microformas.beans.ShipmentBean;

public class EnviosAdapter extends ArrayAdapter<ShipmentBean>{
	private ArrayList<ShipmentBean> items;

	public EnviosAdapter(Context context, int textViewResourceId, ArrayList<ShipmentBean> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.activity_envios_pendientes_list, null);
		}

		ShipmentBean shipmentBean = items.get(position);

		if(shipmentBean != null){
			TextView 	servicioMensajeriaTextView 	= (TextView)v.findViewById(R.id.pendServicioMensajeriaTextView);
			TextView 	tipoResponsableTextView 	= (TextView)v.findViewById(R.id.pendTipoResponsableTextView);
			TextView 	responsableTextView 		= (TextView)v.findViewById(R.id.pendResponsableTextView);
			TextView 	fechaEnvioTextView 			= (TextView)v.findViewById(R.id.pendFechaEnvioTextView);

			if(servicioMensajeriaTextView != null){
				servicioMensajeriaTextView.setText(shipmentBean.getMessagingServiceDesc());
			}
			if(tipoResponsableTextView != null){
				tipoResponsableTextView.setText(shipmentBean.getResponsibleTypeDesc());
			}
			if(responsableTextView != null){
				responsableTextView.setText(shipmentBean.getResponsible());
			}
			if(fechaEnvioTextView != null){
				fechaEnvioTextView.setText(shipmentBean.getDate());
			}
		}
		return v;
	}
}