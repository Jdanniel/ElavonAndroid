package com.artefacto.microformas.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.artefacto.microformas.R;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.holders.UnidadViewHolder;

public class RecepcionUnidadesAdapter extends ArrayAdapter<UnitBean>{
	private LayoutInflater inflater;
	
	public RecepcionUnidadesAdapter(Context context, List<UnitBean> items) {
		super(context, R.layout.listrow_recepciones_unidades, R.id.recepciones2NoSerieCheckBox, items);
		
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		UnitBean unitBean = (UnitBean) this.getItem(position);
		
		TextView marcaTextViewContent;
		TextView modeloTextViewContent;
		CheckBox noSerieCheckBox;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listrow_recepciones_unidades, null);

			//Busca las child views
			marcaTextViewContent 	= (TextView)convertView.findViewById(R.id.recepciones2MarcaTextViewContent);
			modeloTextViewContent 	= (TextView)convertView.findViewById(R.id.recepciones2ModeloTextViewContent);
			noSerieCheckBox			= (CheckBox)convertView.findViewById(R.id.recepciones2NoSerieCheckBox);
			
			convertView.setTag(new UnidadViewHolder(marcaTextViewContent, modeloTextViewContent, noSerieCheckBox));
			//If CheckBox is toggled, update the planet it is tagged with.
			noSerieCheckBox.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) { 
					CheckBox cb = (CheckBox) v;
					UnitBean unitBean = (UnitBean) cb.getTag();
					unitBean.setChecked(cb.isChecked());
				}
			});
		}
		else{
			//Because we use a ViewHolder, we avoid havint to call findViewById().
			UnidadViewHolder viewHolder = (UnidadViewHolder) convertView.getTag();
			marcaTextViewContent 		= viewHolder.getMarcaTextViewContent();
			modeloTextViewContent 		= viewHolder.getModeloTextViewContent();
			noSerieCheckBox 			= viewHolder.getNoSerieCheckBox();
		}
		
		//Tag the CheckBox with the Planet it is displaying, so that we can access the planet in onClick() when the CheckBox is toggled.
		noSerieCheckBox.setTag(unitBean);
		//Display planet data
		//textView.setText(planet.getName());
		noSerieCheckBox.setChecked(unitBean.isChecked());
		
		if(!(unitBean.getDescBrand() == null || unitBean.getDescBrand().equals(""))){
			marcaTextViewContent.setText(unitBean.getDescBrand());
		}
		if(!(unitBean.getDescModel() == null || unitBean.getDescModel().equals(""))){
			modeloTextViewContent.setText(unitBean.getDescModel());
		}
		if(!(unitBean.getNoSerie() == null || unitBean.getNoSerie().equals(""))){
			noSerieCheckBox.setText(unitBean.getNoSerie());
		}
		noSerieCheckBox.setChecked(unitBean.isChecked());
				
		return convertView;
	}
}