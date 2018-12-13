package com.artefacto.microformas;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.adapters.SingleAdapter;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.ValidateSupplyCountTask;

import java.util.ArrayList;

public class NewShipmentSearchSuppliesActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment_search_supplies);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.new_shipment_search_supplies_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.new_shipment_search_supplies_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_new_shipment_search_insumo));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListSuppliesAdded = getIntent().getStringArrayListExtra("list");

        mRowSupply = (TableRow) this.findViewById(R.id.new_shipment_supplies_layout_input);
        mRowCount = (TableRow) this.findViewById(R.id.new_shipment_supplies_layout_count);

        mListResult = (ListView) this.findViewById(R.id.new_shipment_supplies_listview);

        mEditSearchClient = (EditText) this.findViewById(R.id.new_shipment_supplies_edit_client);
        mEditSearchClient.addTextChangedListener(mOnTextWatcher);

        mEditSearchSupply = (EditText) this.findViewById(R.id.new_shipment_supplies_edit_supply);
        mEditSearchSupply.addTextChangedListener(mOnTextWatcher);

        mEditCount = (EditText) this.findViewById(R.id.new_shipment_supplies_edit_count);

        mButtonClient = (Button) this.findViewById(R.id.new_shipment_supplies_button_client);
        mButtonClient.setOnClickListener(onButtonClientClicked);

        mButtonSupply = (Button) this.findViewById(R.id.new_shipment_supplies_button_supply);
        mButtonSupply.setOnClickListener(onButtonSupplyClicked);

        Button buttonAccept = (Button) this.findViewById(R.id.new_shipment_supplies_button_accept);
        buttonAccept.setOnClickListener(onButtonAcceptClicked);

        mSingleAdapter = new SingleAdapter(NewShipmentSearchSuppliesActivity.this, new ArrayList<String>());
        mListResult.setAdapter(mSingleAdapter);

        mType = Constants.AGREGAR_CLIENTE;

        NewShipmentSearchSuppliesActivity.isInserted = false;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(NewShipmentSearchSuppliesActivity.this, NewShipmentActivity.class);
        if(NewShipmentSearchSuppliesActivity.isInserted)
        {
            intent.putExtra(Constants.SEARCH_SHIPMENT_ID, mIdSupply);
            intent.putExtra(Constants.SEARCH_SHIPMENT_ID_CLIENT, mIdClient);
            intent.putExtra(Constants.SEARCH_SHIPMENT_CLIENT, mClient);
            intent.putExtra(Constants.SEARCH_SHIPMENT_DESC, mSupply);
            intent.putExtra(Constants.SEARCH_SHIPMENT_COUNT, mEditCount.getText().toString());
            setResult(Activity.RESULT_OK, intent);
        }
        else
        {
            setResult(Activity.RESULT_CANCELED, intent);
        }


        super.onBackPressed();
    }

    private TextWatcher mOnTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            mSingleAdapter = new SingleAdapter(NewShipmentSearchSuppliesActivity.this,
                    new ArrayList<String>());
            mListResult.setAdapter(mSingleAdapter);

            mListId = new ArrayList<>();
            mListDesc = new ArrayList<>();

            if(s.length() > 0)
            {
                generateList(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void generateList(String value)
    {
        switch (mType)
        {
            case Constants.AGREGAR_CLIENTE:
                SearchClients(value);
                break;

            case Constants.AGREGAR_INSUMO:
                SearchSupplies(value, mIdClient);
                break;
        }

        mSingleAdapter = new SingleAdapter(NewShipmentSearchSuppliesActivity.this, mListDesc);
        mListResult.setAdapter(mSingleAdapter);
        mListResult.setOnItemClickListener(onItemClickListener);
    }

    private void SearchClients(String value)
    {
        SQLiteHelper sqliteHelper = new SQLiteHelper(this, null);
        SQLiteDatabase db = sqliteHelper.getWritableDB();

        Cursor cursor = db.query(SQLiteHelper.CLIENTS_DB_NAME, new String[]{SQLiteHelper.CLIENTS_ID_CLIENT,
                SQLiteHelper.CLIENTS_DESC_CLIENT}, SQLiteHelper.CLIENTS_DESC_CLIENT
                + " like '%" + value + "%'", null, null, null, null);
        try
        {
            if (cursor != null )
            {
                if  (cursor.moveToFirst())
                {
                    do
                    {
                        mListId.add(cursor.getString(0));
                        mListDesc.add(cursor.getString(1));
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
        }
    }

    private void SearchSupplies(String value, String idClient)
    {
        SQLiteHelper sqliteHelper = new SQLiteHelper(this, null);
        SQLiteDatabase db = sqliteHelper.getWritableDB();
        Cursor cursor = null;
        try
        {
            cursor = db.query(SQLiteHelper.INSUMOS_DB_NAME, new String[]{SQLiteHelper.INSUMOS_ID_INSUMO,
                        SQLiteHelper.INSUMOS_DESC_INSUMO}, SQLiteHelper.INSUMOS_ID_CLIENTE
                        + "=? AND " + SQLiteHelper.INSUMOS_DESC_INSUMO + " LIKE '%" + value + "%' ",
                        new String[] { idClient }, null, null, null);

            if (cursor != null )
            {
                if  (cursor.moveToFirst())
                {
                    do
                    {
                        mListId.add(cursor.getString(0));
                        mListDesc.add(cursor.getString(1));
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
        }
    }

    private Button.OnClickListener onButtonClientClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            mType = Constants.AGREGAR_CLIENTE;
            mEditSearchClient.setVisibility(View.VISIBLE);
            mButtonClient.setVisibility(View.GONE);

            mIdClient = "";
            mClient = "";

            mIdSupply = "";
            mSupply = "";

            mRowSupply.setVisibility(View.GONE);
            mRowCount.setVisibility(View.GONE);

            mEditSearchSupply.setVisibility(View.VISIBLE);
            mButtonSupply.setVisibility(View.GONE);
        }
    };

    private Button.OnClickListener onButtonSupplyClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            mType = Constants.AGREGAR_INSUMO;
            mRowCount.setVisibility(View.GONE);

            mIdSupply = "";
            mSupply = "";

            mEditSearchSupply.setVisibility(View.VISIBLE);
            mButtonSupply.setVisibility(View.GONE);
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            View oldSelected = mSingleAdapter.getSelected();
            if(oldSelected != null)
            {
                oldSelected.setBackgroundColor(MicroformasApp.getColor(NewShipmentSearchSuppliesActivity.this,
                        R.color.white));
                oldSelected.invalidate();
            }

            for(int i = 0; i < mSingleAdapter.getStatus().size(); i++)
            {
                mSingleAdapter.getStatus().set(i, (i == position));
                if(i == position)
                {
                    mSingleAdapter.setSelected(view);
                    view.setBackgroundColor(MicroformasApp.getColor(NewShipmentSearchSuppliesActivity.this,
                            R.color.orange_micro));
                    view.invalidate();
                }
            }

            if(mType == Constants.AGREGAR_CLIENTE)
            {   // Is Client
                mIdClient = mListId.get(position);
                mClient = mListDesc.get(position);

                mEditSearchClient.setText("");
                mEditSearchClient.setVisibility(View.GONE);

                mButtonClient.setText(mClient);
                mButtonClient.setVisibility(View.VISIBLE);

                mRowSupply.setVisibility(View.VISIBLE);

                mType = Constants.AGREGAR_INSUMO;
            }
            else
            {   // Is Supply
                mIdSupply = mListId.get(position);
                mSupply = mListDesc.get(position);

                mEditSearchSupply.setText("");
                mEditCount.setText("");
                mEditSearchSupply.setVisibility(View.GONE);

                mButtonSupply.setText(mSupply);
                mButtonSupply.setVisibility(View.VISIBLE);

                mRowCount.setVisibility(View.VISIBLE);
            }
        }
    };

    private Button.OnClickListener onButtonAcceptClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String count = mEditCount.getText().toString().trim().replaceAll(" ", "");
            if(!count.equals(""))
            {
                try
                {
                    Integer inCount = Integer.parseInt(count);
                    if(!isAlreadyInSent(mIdSupply))
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
                        String idUser = sharedPreferences.getString(Constants.PREF_USER_ID, "");

                        ProgressDialog mDialog = new ProgressDialog(NewShipmentSearchSuppliesActivity.this);
                        mDialog.setIndeterminate(true);

                        ValidateSupplyCountTask validateTask = new ValidateSupplyCountTask(NewShipmentSearchSuppliesActivity.this, mDialog);
                        validateTask.execute(mIdSupply, idUser, inCount.toString());
                    }
                    else
                    {
                        Toast.makeText(NewShipmentSearchSuppliesActivity.this, "El Insumo ya ha sido ingresado a este envío", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(NumberFormatException exNumber)
                {
                    Toast.makeText(NewShipmentSearchSuppliesActivity.this, "Numero incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(NewShipmentSearchSuppliesActivity.this, "Cantidad no puede ser vacio.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean isAlreadyInSent(String newId)
    {
        if(mListSuppliesAdded != null)
        {
            for(int i = 0; i < mListSuppliesAdded.size(); i++)
            {
                if(mListSuppliesAdded.get(i).equals(newId))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isInserted;

    private SingleAdapter mSingleAdapter;

    private ArrayList<String> mListId;
    private ArrayList<String> mListDesc;
    private ArrayList<String> mListSuppliesAdded;

    private ListView mListResult;

    private String mIdClient;
    private String mIdSupply;
    private String mClient;
    private String mSupply;

    private String mType;

    private EditText mEditSearchClient;
    private EditText mEditSearchSupply;
    private EditText mEditCount;

    private TableRow mRowSupply;
    private TableRow mRowCount;

    private Button mButtonClient;
    private Button mButtonSupply;
}
