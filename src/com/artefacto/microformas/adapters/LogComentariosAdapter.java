package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.artefacto.microformas.R;
import com.artefacto.microformas.beans.LogComentariosBean;

public class LogComentariosAdapter extends ArrayAdapter<LogComentariosBean> {
	private ArrayList<LogComentariosBean> items;

	public LogComentariosAdapter(Context context, int textViewResourceId, ArrayList<LogComentariosBean> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.activity_log_comentario, null);
		}

		LogComentariosBean logComentariosBean = items.get(position);

		if(logComentariosBean != null){
			TextView 	usuarioTextView 		= (TextView)v.findViewById(R.id.logcomUsuarioTextViewContent);
			TextView 	fechaTextView 			= (TextView)v.findViewById(R.id.logcomFechaTextViewContent);
			TextView 	comentarioTextView 		= (TextView)v.findViewById(R.id.logcomComentarioTextViewContent);

			if(usuarioTextView != null){
				if(!(logComentariosBean.getNombre() == null))
					usuarioTextView.setText(logComentariosBean.getNombre());
			}
			if(fechaTextView != null){
				if(!(logComentariosBean.getFecAlta() == null))
					fechaTextView.setText(logComentariosBean.getFecAlta());
			}
			if(comentarioTextView != null){
					if(!(logComentariosBean.getDescComentario() == null))
				comentarioTextView.setText(logComentariosBean.getDescComentario());
			}
		}
		return v;
	}
}