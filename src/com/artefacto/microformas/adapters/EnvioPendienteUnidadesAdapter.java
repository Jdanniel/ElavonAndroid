package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.artefacto.microformas.R;
import com.artefacto.microformas.beans.UnitBean;

public class EnvioPendienteUnidadesAdapter extends ArrayAdapter<UnitBean>{
	private ArrayList<UnitBean> items;

	public EnvioPendienteUnidadesAdapter(Context context, int textViewResourceId, ArrayList<UnitBean> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listrow_enviospendientes_unidades, null);
		}

		UnitBean unitBean = items.get(position);

		if(unitBean != null){
			TextView 	marcaTextViewContent 	= (TextView)v.findViewById(R.id.pend2MarcaTextViewContent);
			TextView 	modeloTextViewContent 	= (TextView)v.findViewById(R.id.pend2ModeloTextViewContent);
			CheckBox 	noSerieTextViewContent	= (CheckBox)v.findViewById(R.id.pend2NoSerieCheckBox);
			
			if(!(unitBean.getDescBrand() == null || unitBean.getDescBrand().equals(""))){
				marcaTextViewContent.setText(unitBean.getDescBrand());
			}
			if(!(unitBean.getDescModel() == null || unitBean.getDescModel().equals(""))){
				modeloTextViewContent.setText(unitBean.getDescModel());
			}
			if(!(unitBean.getNoSerie() == null || unitBean.getNoSerie().equals(""))){
				noSerieTextViewContent.setText(unitBean.getNoSerie());
				System.out.println("noSerie = " + unitBean.getNoSerie());
			}
		}
		return v;
	}
}