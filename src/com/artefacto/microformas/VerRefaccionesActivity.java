package com.artefacto.microformas;


import java.util.ArrayList;

import com.artefacto.microformas.adapters.VerRefaccionesAdapter;
import com.artefacto.microformas.beans.RefaccionesUnidadBean;
import com.artefacto.microformas.constants.Constants;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;

public class VerRefaccionesActivity extends ListActivity {
	public String lastActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		lastActivity = intent.getStringExtra("activity");
		ArrayList<RefaccionesUnidadBean> refaccionesUnidadBeanArray = (ArrayList<RefaccionesUnidadBean>) intent.getSerializableExtra("bean");
		
		setListAdapter(new VerRefaccionesAdapter(VerRefaccionesActivity.this, R.layout.activity_ver_refacciones, refaccionesUnidadBeanArray));
		
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	    	Intent intent;
	    	if(lastActivity.equals(Constants.PREF_ACTUALIZACION_ACTIVITY)){
	    		intent = new Intent(this, ActualizacionActivity.class);
	    	}
	    	else if(lastActivity.equals(Constants.PREF_INSTALACION_ACTIVITY)){
	    		intent = new Intent(this, InstalacionActivity.class);
	    	}
	    	else{
	    		intent = new Intent(this, InstalacionActivity.class);
	    	}
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
}