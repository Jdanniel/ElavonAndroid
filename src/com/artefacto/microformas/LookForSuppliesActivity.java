package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.SearchClientSupplies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LookForSuppliesActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_for_supplies);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.look_for_supplies_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.look_for_supplies_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_look_for));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		listviewInsumos = (ListView) this.findViewById(R.id.insumo_listview_result_look);

		SetAdapterWithSearch("");

		editTextSearch = (EditText) this.findViewById(R.id.look_for_edit);

		listviewInsumos.setAdapter(strAdapter);
		listviewInsumos.setOnItemClickListener(onItemClicked);
        currentPosition = Integer.MIN_VALUE;

		editTextSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable arg) {
            }

            public void beforeTextChanged(CharSequence chSequence, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence chSequence, int start, int before, int count) {
                String strTyped = chSequence.toString();
                if (strTyped.length() > 2) {
                    SetAdapterWithSearch(strTyped);
                } else {
                    SetAdapterWithSearch("");
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
        mIdResponsible = sharedPreferences.getString(Constants.PREF_USER_ID, "");

        mIdCliente = getIntent().getStringExtra("ID_CLIENT");

        // IF IS_CONTROL_INSUMOS = 0
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adquiriendo Insumos... espere un momento por favor");
        progressDialog.setCancelable(false);

        SearchClientSupplies clientSuppliesTask = new SearchClientSupplies(this, progressDialog);
        clientSuppliesTask.execute(mIdResponsible, mIdCliente);
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

    @Override
    public void onBackPressed()
    {
        if(currentPosition != Integer.MIN_VALUE)
        {
            Intent intentBackTo = new Intent(LookForSuppliesActivity.this, InsertNewMovementSupplyActivity.class);
            intentBackTo.putExtra("ID_SUPPLY", listSupplyId.get(currentPosition));
            intentBackTo.putExtra("SUPPLY_DESC", listSupplyDesc.get(currentPosition));
            intentBackTo.putExtra("SUPPLY_COUNT", listSupplyCount.get(currentPosition));
            setResult(Activity.RESULT_OK, intentBackTo);
        }
        else
        {
            setResult(Activity.RESULT_CANCELED);
        }

        super.onBackPressed();
    }

    public void setClientSuppliesBean(ArrayList<SupplyBean> mClientSuppliesBean)
    {
        this.mClientSuppliesBean = mClientSuppliesBean;
    }

    private void SetAdapterWithSearch(String strTyped)
	{
		SQLiteHelper sqliteHelper = new SQLiteHelper(this, null);
		SQLiteDatabase db = sqliteHelper.getWritableDB();

        String descClient = "";

		listSupplyId = new ArrayList<>();
		listSupplyDesc = new ArrayList<>();
        listSupplyCount = new ArrayList<>();

        String query = "SELECT " + SQLiteHelper.CLIENTS_DESC_CLIENT
            + " FROM " + SQLiteHelper.CLIENTS_DB_NAME
            + " WHERE " + SQLiteHelper.CLIENTES_ID_CLIENTE+ "=" + mIdCliente;
        Cursor cursor = db.rawQuery(query, null);
        try
        {
            if (cursor != null )
            {
                if (cursor.moveToFirst())
                {
                    descClient = cursor.getString(0);
                }
            }
        }
        catch (Exception ex)
        {
            Log.d("Microformas", ex.getMessage());
        }

        query = "SELECT " + SQLiteHelper.INV_SUPPLIES_ID_SUPPLY + ", "
            + SQLiteHelper.INV_SUPPLIES_DESC_CLIENT + ", "
            + SQLiteHelper.INV_SUPPLIES_DESC + ", "
            + SQLiteHelper.INV_SUPPLIES_TOTAL + " FROM " + SQLiteHelper.INV_SUPPLIES_DB_NAME
            + " WHERE " + SQLiteHelper.INV_SUPPLIES_DESC_CLIENT + "='" + descClient + "'";
		if(!strTyped.equals(""))
		{
			query +=  " AND " + SQLiteHelper.INV_SUPPLIES_DESC + " LIKE " + " '%" + strTyped + "%'";
            Log.e("Insumos:",query);
		}

        cursor = db.rawQuery(query, null);
        if(mClientSuppliesBean == null){
            try
            {
                if (cursor != null)
                {
                    if  (cursor.moveToFirst())
                    {
                        do
                        {
                            Log.e("ID_INSUMO",cursor.getString(0));
                            Log.e("ID_INSUMO_CLIENTE",cursor.getString(1));
                            if (cursor.getString(1).equals(descClient))
                            {
                                listSupplyId.add(cursor.getString(0));
                                listSupplyDesc.add(cursor.getString(2));
                                listSupplyCount.add(cursor.getString(3));
                            }
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
            }
        }
        //Add custom client for IS_CONTROL_INSUMOS = 0
        else if(mClientSuppliesBean != null)
        {
            for(int i = 0; i < mClientSuppliesBean.size(); i++)
            {
                if(mClientSuppliesBean.get(i).getDescSupply().toUpperCase().contains(strTyped.toUpperCase()))
                {
                    listSupplyId.add(mClientSuppliesBean.get(i).getIdSupply());
                    Log.e("IS_CONTROL_INSUMOS???:",""+mClientSuppliesBean.get(i).getIdSupply());
                    listSupplyDesc.add(mClientSuppliesBean.get(i).getDescSupply());
                    listSupplyCount.add(mClientSuppliesBean.get(i).getCount());
                }
            }
        }

        strAdapter = new ArrayAdapter<>(this, R.layout.simplerow, listSupplyDesc);
	    listviewInsumos.setAdapter(strAdapter);
	}

    private OnItemClickListener onItemClicked = new OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            try
            {
                currentPosition = position;
                onBackPressed();
            }
            catch(Exception ex)
            {
                Log.e("Microformas", ex.getMessage());
                ex.printStackTrace();
            }
        }
    };

	private EditText editTextSearch;

	private ListView listviewInsumos;

    private String mIdResponsible;
	private String mIdCliente;

    private int currentPosition;

	private ArrayAdapter<String> strAdapter;

	private ArrayList<String> listSupplyDesc;
	private ArrayList<String> listSupplyId;
	private ArrayList<String> listSupplyCount;

    private ArrayList<SupplyBean> mClientSuppliesBean;
}
