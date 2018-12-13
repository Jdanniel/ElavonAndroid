package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import com.artefacto.microformas.R;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.RequestListBean;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<RequestListBean>
{
	private ArrayList<RequestListBean> items;
	int documentText;
	int enteroDocumento;
	
	public CustomAdapter(Context context, int textViewResourceId, ArrayList<RequestListBean> items) {
		super(context, textViewResourceId, items);
		this.items = items;

        documentText = R.string.documents_pendiente_text -1 ;
	}

	public void reload(ArrayList <RequestListBean> list){
		this.items.clear();
		this.items.addAll(list);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.activity_cerradas, null);
		}

		RequestListBean requestListBean = items.get(position);

		if(requestListBean != null){
			TextView 	idRequestTextView 		= v.findViewById(R.id.idRequestTextViewList);
			TextView 	descClienteTextView 	= v.findViewById(R.id.descClienteTextViewList);
			TextView 	descServicioTextView 	= v.findViewById(R.id.descServicioTextViewList);
			TextView 	fechaAltaTextView 		= v.findViewById(R.id.fechaAltaTextViewList);
			TextView 	fechaGarantiaTextView 	= v.findViewById(R.id.fechaGarantiaTextViewList);
			TextView 	fechaAtencionTextView	= v.findViewById(R.id.fechaAtencionTextViewList);
			TextView	negocioTextView			= v.findViewById(R.id.negocioViewList);
			TextView 	statusTextView			= v.findViewById(R.id.statusdocumentTextViewList);
			TextView 	comentarioTextView		= v.findViewById(R.id.comentDocumentoTextViewList);
			ImageView 	lightIcon 				= v.findViewById(R.id.lightIcon);

			if(idRequestTextView != null){
				idRequestTextView.setText(requestListBean.getNoAr());
			}
			if(descClienteTextView != null){
				descClienteTextView.setText(requestListBean.getDescCliente());
			}
			if(negocioTextView != null){
				negocioTextView.setText(requestListBean.getDescNegocio());
			}
			if(descServicioTextView != null){
				descServicioTextView.setText(requestListBean.getDescServicio());
			}
			if(fechaAltaTextView != null){
				fechaAltaTextView.setText("Alta : " + requestListBean.getFechaAlta());
			}
			if(fechaGarantiaTextView != null){
				fechaGarantiaTextView.setText("Garantia : " + requestListBean.getFechaGarantia());
			}
			if(fechaAtencionTextView != null){
				fechaAtencionTextView.setText("Atención : " + requestListBean.getFechaAtencion());
			}
			int prefacturacion = Integer.parseInt(requestListBean.getPrefacturacion());

			switch (prefacturacion){
                case 1:
                    enteroDocumento = 1;
                    break;
				case 2:
					enteroDocumento = 3;
					break;
				case 3:
					enteroDocumento = 2;
					break;
			}

			if(prefacturacion > 0 && prefacturacion < 5){
				statusTextView.setText("Status Documento : " + MicroformasApp.getAppContext().getString((documentText + enteroDocumento)));
				statusTextView.setVisibility(View.VISIBLE);
				/*if(requestListBean.getStatusDocumento().equals("NUMERO CUALQUIERA")){
					comentarioTextView.setVisibility(View.VISIBLE);
					comentarioTextView.setText("Comentario: " + requestListBean.getComentario());
				}*/
				
			}
			if(lightIcon != null){
				lightIcon.setImageBitmap(requestListBean.getImage());
			}
		}
		return v;
	}
}