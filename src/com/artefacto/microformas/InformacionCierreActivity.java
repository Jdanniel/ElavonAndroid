package com.artefacto.microformas;

import com.artefacto.microformas.tasks.InformacionCierreBean;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class InformacionCierreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informacion_cierre);
		
		Intent intent = getIntent();
		InformacionCierreBean informacionCierreBean = (InformacionCierreBean) intent.getSerializableExtra("bean");

		TextView noArTextView 	= (TextView)findViewById(R.id.infoNoArTextViewContent);
		TextView valTextView 	= (TextView)findViewById(R.id.infoValTextViewContent);
		
		noArTextView.setText("" + intent.getSerializableExtra("noar"));
		valTextView.setText(informacionCierreBean.getVal());
	}	
}