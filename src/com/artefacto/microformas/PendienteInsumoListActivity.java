package com.artefacto.microformas;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

public class PendienteInsumoListActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pendiente_insumo_list);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.pending_supply_list_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.pending_supply_list_toolbar_title);
		setTitle("");
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textSearch = (EditText) this.findViewById(R.id.inslistEditText);
        textSearch.addTextChangedListener(onTextTyped);

        insumoListview = (ListView) this.findViewById(R.id.inslistListView);
        insumoListview.setOnItemClickListener(onItemClickListener);

		Intent intent = getIntent();
		mType = intent.getStringExtra("type");
		mId = "";
		mIdClient = intent.getStringExtra("id_client");

		Button buttonAdd = (Button)findViewById(R.id.inslistButton);
		buttonAdd.setOnClickListener(onAddClickedListener);

        switch (mType)
        {
            case Constants.INSUMO_INSUMOS:
                textTitle.setText("Insumo [Insumo]");
                break;
            case Constants.INSUMO_ALMACENES:
                textTitle.setText("Insumo [Almacén]");
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

	OnItemClickListener onItemClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
		{
            mId = listId.get(position);
            mDesc = listDesc.get(position);
            textSearch.setText(listDesc.get(position));
		}
	};

	public void setInsumoList(CharSequence typedString)
	{
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();

		listId = new ArrayList<>();
		listDesc = new ArrayList<>();

		String itemId = "";
		String itemDesc = "";
		String itemTable = "";
		String itemIdCliente = "";

        switch (mType)
        {
            case Constants.INSUMO_ALMACENES:
                itemId = SQLiteHelper.ALMACENES_ID_ALMACEN;
                itemDesc = SQLiteHelper.ALMACENES_DESC_ALMACEN;
                itemTable = SQLiteHelper.ALMACENES_DB_NAME;
                break;

            case Constants.INSUMO_INSUMOS:
                itemId = SQLiteHelper.INSUMOS_ID_INSUMO;
                itemDesc = SQLiteHelper.INSUMOS_DESC_INSUMO;
                itemTable = SQLiteHelper.INSUMOS_DB_NAME;
                itemIdCliente = SQLiteHelper.INSUMOS_ID_CLIENTE;
                break;
        }

		String query = "SELECT " + itemId + "," + itemDesc + " FROM " + itemTable
						+ " WHERE " + itemDesc + " like '%" + typedString + "%'";

		if(mType.equals(Constants.INSUMO_INSUMOS))
		{
			query = query + " AND " + itemIdCliente + " = " + mIdClient;
		}

        Cursor cursor = null;
		try
        {
            cursor = db.rawQuery(query, null);
	       	if (cursor != null )
            {
	       		if  (cursor.moveToFirst())
                {
	       			do
                    {
	       				listId.add(cursor.getString(0));
	       				listDesc.add(cursor.getString(1));
	       			} while (cursor.moveToNext());
	       		}
	       	}
	    }
	    catch(Exception ex)
        {
            Log.d("Microformas", ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            if(cursor != null)
            {
                cursor.close();
            }

            db.close();
        }

	    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simplerow, listDesc);
	    insumoListview.setAdapter(adapter);
	}

	private OnClickListener onAddClickedListener = new OnClickListener()
    {
		public void onClick(View v)
        {
			if(mId == null || mId.equals(""))
            {
				if(mType.equals(Constants.TERMINAL_ALMACENES))
                {
                    Toast.makeText(getApplicationContext(), "Debes asignar un almacen válido", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Debes asignar un insumo válido", Toast.LENGTH_SHORT).show();
                }
			}
			else
			{
				onBackPressed();
			}
		}
	};

    private TextWatcher onTextTyped = new TextWatcher()
    {
        public void afterTextChanged(Editable arg0) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            CharSequence typedString = s;
            if(typedString.length() > 2)
            {	//Manda a llamar el ArrayAdapter con el arreglo correspondiente
                setInsumoList(typedString);
            }
            else
            {
                listId = new ArrayList<>();
                listDesc = new ArrayList<>();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.simplerow, listDesc);
                insumoListview.setAdapter(adapter);
            }
        }
    };
	
	private ArrayList<String> listId;
	private ArrayList<String> listDesc;
	
	private EditText textSearch;
	private ListView insumoListview;

    private String mId;
	private String mDesc;
	private String mType;
	private String mIdClient;
}