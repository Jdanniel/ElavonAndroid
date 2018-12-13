package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.adapters.LogComentariosAdapter;
import com.artefacto.microformas.beans.LogComentariosBean;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;

public class LogComentarioActivity extends ListActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
        ArrayList<LogComentariosBean> logComentarioBeanArray = (ArrayList<LogComentariosBean>) intent.getSerializableExtra("bean");
		
        LogComentariosAdapter adapter = new LogComentariosAdapter(LogComentarioActivity.this, R.layout.activity_log_comentario, logComentarioBeanArray);
        setListAdapter(adapter);
	}
}