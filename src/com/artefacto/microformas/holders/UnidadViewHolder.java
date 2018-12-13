package com.artefacto.microformas.holders;

import android.widget.CheckBox;
import android.widget.TextView;

public class UnidadViewHolder {
	private TextView marcaTextViewContent;
	private TextView modeloTextViewContent;
	private CheckBox noSerieCheckBox;
	
	public UnidadViewHolder(){}
	
	public UnidadViewHolder(TextView marcaTextViewContent, TextView modeloTextViewContent, CheckBox noSerieCheckBox){
		this.marcaTextViewContent 	= marcaTextViewContent;
		this.modeloTextViewContent 	= modeloTextViewContent;
		this.noSerieCheckBox 		= noSerieCheckBox;
	}
	
	public TextView getMarcaTextViewContent() {
		return marcaTextViewContent;
	}
	public void setMarcaTextViewContent(TextView marcaTextViewContent) {
		this.marcaTextViewContent = marcaTextViewContent;
	}
	public TextView getModeloTextViewContent() {
		return modeloTextViewContent;
	}
	public void setModeloTextViewContent(TextView modeloTextViewContent) {
		this.modeloTextViewContent = modeloTextViewContent;
	}
	public CheckBox getNoSerieCheckBox() {
		return noSerieCheckBox;
	}
	public void setNoSerieCheckBox(CheckBox noSerieCheckBox) {
		this.noSerieCheckBox = noSerieCheckBox;
	}
}
