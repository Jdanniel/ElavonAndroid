package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import com.artefacto.microformas.R;
import com.artefacto.microformas.beans.RefaccionesUnidadBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class VerRefaccionesAdapter extends ArrayAdapter<RefaccionesUnidadBean>{
	private ArrayList<RefaccionesUnidadBean> items;
	
	public VerRefaccionesAdapter(Context context, int textViewResourceId, ArrayList<RefaccionesUnidadBean> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.activity_ver_refacciones, null);
		}

		RefaccionesUnidadBean refaccionesUnidadBean = items.get(position);

		if(refaccionesUnidadBean != null){
			TextView 	idNegocioTextView 		= (TextView)v.findViewById(R.id.verrefNegocioTextViewContent);
			TextView 	descClienteTextView 	= (TextView)v.findViewById(R.id.verrefClienteTextViewContent);
			TextView 	descMarcaTextView 		= (TextView)v.findViewById(R.id.verrefMarcaTextViewContent);
			TextView 	descModeloTextView 		= (TextView)v.findViewById(R.id.verrefModeloTextViewContent);
			TextView 	noSerieTextView 		= (TextView)v.findViewById(R.id.verrefNoSerieTextViewContent);
			TextView	noInventarioTextView	= (TextView)v.findViewById(R.id.verrefNoInventarioTextViewContent);
			TextView 	idStatusUnidadTextView 	= (TextView)v.findViewById(R.id.verrefStatusTextViewContent);
			Button 		borrarRefaccionButton 		= (Button)v.findViewById(R.id.verrefBorrarRefaccionButton);

			if(idNegocioTextView != null){
				idNegocioTextView.setText(refaccionesUnidadBean.getIdNegocio());
			}
			if(descClienteTextView != null){
				descClienteTextView.setText(refaccionesUnidadBean.getDescCliente());
			}
			if(descMarcaTextView != null){
				descMarcaTextView.setText(refaccionesUnidadBean.getDescMarca());
			}
			if(descModeloTextView != null){
				descModeloTextView.setText(refaccionesUnidadBean.getDescModelo());
			}
			if(noSerieTextView != null){
				noSerieTextView.setText(refaccionesUnidadBean.getNoSerie());
			}
			if(noInventarioTextView != null){
				noInventarioTextView.setText(refaccionesUnidadBean.getNoInventario());
			}
			if(idStatusUnidadTextView != null){
				idStatusUnidadTextView.setText(refaccionesUnidadBean.getIdStatusUnidad());
			}
			
			borrarRefaccionButton.setOnClickListener(borrarRefaccionOnClickListener);
		}
		return v;
	}
	
	OnClickListener borrarRefaccionOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			//TODO poner lógica para borrar la refacción
			Toast.makeText(getContext(), "En construcción...", Toast.LENGTH_SHORT).show();
		}
	};
}
