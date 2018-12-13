package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TerminalListActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminal_list);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.terminal_list_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.terminal_list_toolbar_title);
		setTitle("");
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		editText = (EditText) this.findViewById(R.id.terlistEditText);
        editText.addTextChangedListener(onTextChanged);
		
		listView = (ListView) this.findViewById(R.id.terlistListView);
		listView.setOnItemClickListener(listClickListener);
		
		Button enviarButton = (Button)findViewById(R.id.terlistButton);
		enviarButton.setOnClickListener(enviarOnClickListener);
		
		Intent intent = getIntent();
		mType = intent.getStringExtra("type");
        mId = "";
        mIdProduct = intent.getStringExtra("id_product");
        mIdClient = intent.getStringExtra("id_client");
        mIdMarca = intent.getStringExtra("id_marca");

		switch (mType)
		{
			case Constants.TERMINAL_BRANDS:
				textTitle.setText(getString(R.string.terminal_title_brand));
				break;

			case Constants.TERMINAL_MODELS:
				textTitle.setText(getString(R.string.terminal_title_model));
				break;

            case Constants.TERMINAL_CONNECTIVITY:
                textTitle.setText(getString(R.string.terminal_title_connectivity));
                break;

            case Constants.TERMINAL_SOFTWARE:
                textTitle.setText(getString(R.string.terminal_title_application));
                break;

            case Constants.TERMINAL_ALMACENES:
                textTitle.setText(getString(R.string.terminal_title_store));
                break;
		}
	}

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra("type", mType);

        if(!mId.equals(""))
        {
            intent.putExtra("list_id", mId);
            intent.putExtra("list_desc", mDesc);

            setResult(Activity.RESULT_OK, intent);
        }
        else
        {
            setResult(Activity.RESULT_CANCELED, intent);
        }

        super.onBackPressed();
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
	
	public OnItemClickListener listClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
		{
			mId = idList.get(position);
			mDesc = descList.get(position);
			editText.setText(descList.get(position));
		}
	};
	
	public void setList(CharSequence typedString)
    {
		SQLiteHelper sqliteHelper = new SQLiteHelper(this, null);
		SQLiteDatabase db = sqliteHelper.getWritableDB();

		idList = new ArrayList<>();
		descList = new ArrayList<>();
	       
		String itemId = "";
		String itemDesc = "";
		String itemTable = "";

        switch (mType)
        {
            case Constants.TERMINAL_ALMACENES:
                itemId = SQLiteHelper.ALMACENES_ID_ALMACEN;
                itemDesc = SQLiteHelper.ALMACENES_DESC_ALMACEN;
                itemTable = SQLiteHelper.ALMACENES_DB_NAME;
                break;

            case Constants.TERMINAL_BRANDS:
                itemId = SQLiteHelper.MARCAS_ID_MARCA;
                itemDesc = SQLiteHelper.MARCAS_DESC_MARCA;
                itemTable = SQLiteHelper.MARCAS_DB_NAME;
                break;

            case Constants.TERMINAL_MODELS:
                itemId = SQLiteHelper.MODELOS_ID_MODELO;
                itemDesc = SQLiteHelper.MODELOS_DESC_MODELO;
                itemTable = SQLiteHelper.MODELOS_DB_NAME;
                break;

            case Constants.TERMINAL_CONNECTIVITY:
                itemId = SQLiteHelper.CONNECTIVITY_ID;
                itemDesc = SQLiteHelper.CONNECTIVITY_DESC;
                itemTable = SQLiteHelper.CONNECTIVITY_DB_NAME;
                break;

            case Constants.TERMINAL_SOFTWARE:
                itemId = SQLiteHelper.SOFTWARE_ID;
                itemDesc = SQLiteHelper.SOFTWARE_DESC;
                itemTable = SQLiteHelper.SOFTWARE_DB_NAME;
                break;
        }
		
		String query = "SELECT " + itemId + "," + itemDesc + " FROM " + itemTable
		    + " WHERE " + itemDesc	+ " like '%" + typedString + "%'";
	
		if(mType.equals(Constants.TERMINAL_BRANDS))
        {
			query += " AND " + SQLiteHelper.PRODUCTO_ID_PRODUCTO + " = " + mIdProduct;
		}
		else if(mType.equals(Constants.TERMINAL_MODELS))
        {
			query = "SELECT MO." 	+ SQLiteHelper.MODELOS_ID_MODELO  + ", MO." + SQLiteHelper.MODELOS_DESC_MODELO
                + " FROM " 		+ SQLiteHelper.MODELOS_DB_NAME 		+ " MO "
                + " INNER JOIN " 	+ SQLiteHelper.MARCAS_DB_NAME 		+ " MA ON MO."  + SQLiteHelper.MARCAS_ID_MARCA
                                                                    + " = MA."		+ SQLiteHelper.MARCAS_ID_MARCA
                + " WHERE MA." 	+ SQLiteHelper.PRODUCTO_ID_PRODUCTO + " = "			+ mIdProduct
                + " AND MO." + SQLiteHelper.MARCAS_ID_MARCA + " = " + mIdMarca
                + " AND (MO." 		+ SQLiteHelper.MODELOS_DESC_MODELO 	+ " LIKE '%" + typedString + "%'"
                + " OR MO." 		+ SQLiteHelper.MODELOS_SKU 			+ " LIKE '%" + typedString + "%')";
		}
        else if(mType.equals(Constants.TERMINAL_CONNECTIVITY))
        {
            query += " AND " + SQLiteHelper.CONNECTIVITY_ID_CLIENT + " = " + mIdClient;
        }
        else if(mType.equals(Constants.TERMINAL_SOFTWARE))
        {
            query += " AND " + SQLiteHelper.SOFTWARE_ID_CLIENT + " = " + mIdClient;
        }
        Log.e("Query :",query);
		Cursor cursor = db.rawQuery(query, null);
		try
        {
	       	if (cursor != null )
            {
	       		if  (cursor.moveToFirst())
                {
	       			do
                    {
	       				idList.add(cursor.getString(0));
	       				descList.add(cursor.getString(1));
	       			} while (cursor.moveToNext());
	       		}
	       	}
	    }
	    catch(Exception ex)
        {
            Log.d("Microformas", ex.getMessage());
        }
        finally
        {
            if(cursor != null)
            {
                cursor.close();
            }

            db.close();
        }

	    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simplerow, descList);
		listView.setAdapter(adapter);
	}
	
	public OnClickListener enviarOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			if(mId == null || mId.equals(""))
			{
				if(mType.equals(Constants.TERMINAL_ALMACENES))
                {
                    Toast.makeText(getApplicationContext(), "Debes asignar un almacen válido", Toast.LENGTH_SHORT).show();
                }
                else if(mType.equals(Constants.TERMINAL_BRANDS))
                {
                    Toast.makeText(getApplicationContext(), "Debes asignar una marca válida", Toast.LENGTH_SHORT).show();
                }
                else if(mType.equals(Constants.TERMINAL_MODELS))
                {
                    Toast.makeText(getApplicationContext(), "Debes asignar un modelo válido", Toast.LENGTH_SHORT).show();
                }
                else if(mType.equals(Constants.TERMINAL_CONNECTIVITY))
                {
                    Toast.makeText(getApplicationContext(), "Debes asignar un conectividad válido", Toast.LENGTH_SHORT).show();
                }
                else if(mType.equals(Constants.TERMINAL_SOFTWARE))
                {
                    Toast.makeText(getApplicationContext(), "Debes asignar un aplicativo válido", Toast.LENGTH_SHORT).show();
                }
			}
			else
			{
				onBackPressed();
			}
		}
	};

    private TextWatcher onTextChanged = new TextWatcher()
    {
        public void afterTextChanged(Editable arg0) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            typedString = s;
            if(typedString.length() > 2)
            {	//Manda a llamar el ArrayAdapter con el arreglo correspondiente
                setList(typedString);
            }
            else
            {
                idList		= new ArrayList<>();
                descList	= new ArrayList<>();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerow, descList);
                listView.setAdapter(adapter);
            }
        }
    };
	
	private ArrayList<String> idList;
	private ArrayList<String> descList;
	
	private EditText editText;
	private ListView listView;
	
	private CharSequence typedString;

    private String mId;
    private String mIdProduct;
    private String mIdClient;
	private String mDesc;
	private String mType;
    private String mIdMarca;
}