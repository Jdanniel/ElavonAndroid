package com.artefacto.microformas;

import com.artefacto.microformas.adapters.PendingsAdapter;
import com.artefacto.microformas.async.AsyncQueue;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class PendingsListActivity extends ListActivity implements MFActivity{

	PendingsAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		UpdateGUI();
	}

	public void UpdateGUI() {
		runOnUiThread(new Runnable() {
    	    public void run() {	
    	    	if (adapter == null){
    	    		adapter = new PendingsAdapter(PendingsListActivity.this, R.layout.activity_pendings_list, AsyncQueue.getList());
    	    		setListAdapter(adapter);
    	    	}else{
    	    		adapter.reload(AsyncQueue.getList());
    	    	}
    	    	//setListAdapter(new PendingsAdapter(PendingsListActivity.this, R.layout.activity_pendings_list, AsyncQueue.getList()));
    	    	ListView listView = getListView();
    			listView.setTextFilterEnabled(true);
    			listView.setOnItemLongClickListener(new OnItemLongClickListener() {
    		        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
    		            DeleteItem(position);
		                return true;
		            }	
		        });
    	    }
		});
	}
	
	public void DeleteItem(final int position){
		AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Confirmación");
        dialog.setMessage("¿Seguro que deseas cancelar este envío?");
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
            	AsyncQueue.deleteItem(position);
            	UpdateGUI();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {

            }
        });
        dialog.show();
	}
}