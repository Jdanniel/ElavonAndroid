package com.artefacto.microformas;

import java.util.ArrayList;
import java.util.List;

import com.artefacto.microformas.sqlite.SQLiteHelper;

import android.os.Bundle;
import android.content.Context;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViaticosListActivity extends AppCompatActivity
{
	 EditText 			costoEditText;
	 Spinner 			items;
	 List<String> 		spinnerArray;
	 ArrayList<String> 	itemSelected;
	 Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viaticos_list);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.viaticos_list_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.viaticos_list_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_viaticos_list));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		context = this.getApplicationContext();
		
		spinnerArray =  new ArrayList<String>();

        UpdateInfo();
	}

    @Override
    protected void onResume()
    {
        super.onResume();
        UpdateInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    
    OnClickListener addButtonOnClickListener = new OnClickListener(){
		public void onClick(View arg0) {
			//TODO Pasar valores de id viáticos y costo.
			//Validar que viatico sea mayor a 0
			/*ProgressDialog progressDialog = new ProgressDialog(ViaticosListActivity.this);
			progressDialog.setMessage("Agregando Viático");
			progressDialog.setCancelable(false);
			progressDialog.show();*/
			
			int cost = (int) Double.parseDouble(costoEditText.getText().toString());
			if(cost != 0) {
//				Intent intent = new Intent(getApplicationContext(), ViaticosActivity.class);
				insertViatico(itemSelected.get(items.getSelectedItemPosition()), spinnerArray.get(items.getSelectedItemPosition()), costoEditText.getText().toString());
//				startActivity(intent);
                onBackPressed();
			} else {
				Toast.makeText(context, "Costo debe ser mayor a 0", Toast.LENGTH_SHORT).show();
				//progressDialog.dismiss();
			}
		}
    };
    
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	    	Intent intent = new Intent(this, ViaticosActivity.class);
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}
    
    public void insertViatico(String id, String viatico, String costo)
    {
    	SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
        SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();
        
        sqliteHelper.setListaViaticos(id, viatico, costo, db);
        db.close();
    }

    private void UpdateInfo()
    {
        //Adquiere la lista de Viáticos
        SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
        SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();

        String[] campos = new String[] {SQLiteHelper.VIATICOS_ID_VIATICO,
                SQLiteHelper.VIATICOS_DESC_VIATICO};
        itemSelected = new ArrayList<String>();


        Cursor c = db.query(SQLiteHelper.VIATICOS_DB_NAME, campos, null, null, null, null, null);

        try{
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        itemSelected.add(c.getString(0));
                        spinnerArray.add(c.getString(1));
                    }while (c.moveToNext());
                }
            }
        }
        catch(Exception e){
        }
        c.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        items = (Spinner) findViewById(R.id.viaticosSpinner);
        items.setAdapter(adapter);

        costoEditText = (EditText)findViewById(R.id.costoEditText);
        costoEditText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Button addButton = (Button)findViewById(R.id.addButton);
                if(!costoEditText.getText().toString().equals("")){
                    addButton.setEnabled(true);
                    addButton.setOnClickListener(addButtonOnClickListener);
                }
                else{
                    addButton.setEnabled(false);
                }
            }

        });
    }
}