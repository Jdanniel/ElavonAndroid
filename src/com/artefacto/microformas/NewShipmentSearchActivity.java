package com.artefacto.microformas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.adapters.SingleAdapter;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.ValidateUnitTask;

import java.util.ArrayList;

public class NewShipmentSearchActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment_search);

        if (GetUpdatesService.isUpdating)
        {
            Toast.makeText(this, "Actualización en progreso, intente más tarde", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.new_shipment_search_toolbar);
        mTextTitle = (TextView) this.findViewById(R.id.new_shipment_search_toolbar_title);
        setTitle("");
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mType =  getIntent().getStringExtra(Constants.AGREGAR_TYPE);
        mTextSearch = (TextView) this.findViewById(R.id.new_shipment_text_search);
        InitTitle();

        EditText editSearch = (EditText) this.findViewById(R.id.new_shipment_edit_search);
        editSearch.addTextChangedListener(mOnTextWatching);


        mListResult = (ListView) this.findViewById(R.id.new_shipment_listview_result);

        Button buttonSearch = (Button) this.findViewById(R.id.new_shipment_button_accept);
        buttonSearch.setOnClickListener(mOnButtonAccept);

        mListId = new ArrayList<>();
        mListDesc = new ArrayList<>();

        mSingleAdapter = new SingleAdapter(NewShipmentSearchActivity.this, new ArrayList<String>());
        mListResult.setAdapter(mSingleAdapter);

        mClicked = Integer.MIN_VALUE;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(NewShipmentSearchActivity.this, NewShipmentActivity.class);
        intent.putExtra(Constants.AGREGAR_TYPE, mType);
        if(mClicked != Integer.MIN_VALUE && mListDesc != null)
        {
            if(mListDesc.size() > 0)
            {
                intent.putExtra(Constants.SEARCH_SHIPMENT_ID, mListId.get(mClicked));
                intent.putExtra(Constants.SEARCH_SHIPMENT_DESC, mListDesc.get(mClicked));
            }
        }

        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    public Button.OnClickListener mOnButtonAccept = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(mType.equals(Constants.AGREGAR_UNIDAD))
            {
                if(mClicked != Integer.MIN_VALUE && mListDesc != null)
                {
                    if(mListDesc.size() > 0)
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
                        String idUser = sharedPreferences.getString(Constants.PREF_USER_ID, "");

                        ProgressDialog mDialog = new ProgressDialog(NewShipmentSearchActivity.this);
                        mDialog.setIndeterminate(true);

                        String idUnit = mListId.get(mClicked);

                        ValidateUnitTask validateTask = new ValidateUnitTask(NewShipmentSearchActivity.this, mDialog);
                        validateTask.execute(idUnit, idUser);
                    }
                }
            }
            else
            {
                onBackPressed();
            }
        }
    };

    private TextWatcher mOnTextWatching = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            mSingleAdapter = new SingleAdapter(NewShipmentSearchActivity.this,
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

    private void InitTitle()
    {
        switch (mType)
        {
            case Constants.AGREGAR_ALMACEN:
                mTextSearch.setText(getString(R.string.base_warehouse));
                mTextTitle.setText("Buscar Almacén");
                break;

            case Constants.AGREGAR_INGENIERO:
                mTextSearch.setText(getString(R.string.base_engineers));
                mTextTitle.setText("Buscar Ingeniero");
                break;

            case Constants.AGREGAR_UNIDAD:
                mTextSearch.setText(getString(R.string.base_no_serie));
                mTextTitle.setText("Buscar Unidad");
                break;
        }
    }

    private void generateList(String value)
    {
        switch (mType)
        {
            case Constants.AGREGAR_UNIDAD:
                SearchUnits(value);
                break;

            case Constants.AGREGAR_ALMACEN:
                SearchWarehouse(value);
                break;

            case Constants.AGREGAR_INGENIERO:
                SearchEngineers(value);
                break;
        }

        mSingleAdapter = new SingleAdapter(NewShipmentSearchActivity.this, mListDesc);
        mListResult.setAdapter(mSingleAdapter);
        mListResult.setOnItemClickListener(onItemClickListener);
    }

    private void SearchUnits(String value)
    {
        SQLiteHelper sqliteHelper = new SQLiteHelper(this, null);
        SQLiteDatabase db = sqliteHelper.getWritableDB();

        Cursor cursor = db.rawQuery("SELECT " + SQLiteHelper.UNIDADES_ID_UNIDAD + ","
                + SQLiteHelper.UNIDAD_NO_SERIE + ","
                + SQLiteHelper.UNIDAD_DESC_MODELO + ","
                + SQLiteHelper.UNIDAD_DESC_CLIENTE
                + " FROM " + SQLiteHelper.UNIDAD_DB_NAME
                + " WHERE " + SQLiteHelper.UNIDAD_NO_SERIE + " like '%" + value + "%'", null);

        try
        {
            if (cursor != null )
            {
                if  (cursor.moveToFirst())
                {
                    do
                    {
                        mListId.add(cursor.getString(0));
                        mListDesc.add(cursor.getString(1) + ": " + cursor.getString(2)
                                + " - " + cursor.getString(3));
                    }while (cursor.moveToNext());
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

    private void SearchWarehouse(String value)
    {
        SQLiteHelper sqliteHelper = new SQLiteHelper(this, null);
        SQLiteDatabase db = sqliteHelper.getWritableDB();

        Cursor cursor = db.query(SQLiteHelper.ALMACENES_DB_NAME, new String[]{SQLiteHelper.ALMACENES_ID_ALMACEN,
                SQLiteHelper.ALMACENES_DESC_ALMACEN}, SQLiteHelper.ALMACENES_DESC_ALMACEN
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

    private void SearchEngineers(String value)
    {
        SQLiteHelper sqliteHelper = new SQLiteHelper(this, null);
        SQLiteDatabase db = sqliteHelper.getWritableDB();

        Cursor cursor = db.query(SQLiteHelper.INGENIEROS_DB_NAME, new String[]{SQLiteHelper.INGENIEROS_ID_INGENIERO,
                SQLiteHelper.INGENIEROS_NOMBRECOMPLETO}, SQLiteHelper.INGENIEROS_NOMBRECOMPLETO
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

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            View oldSelected = mSingleAdapter.getSelected();
            if(oldSelected != null)
            {
                oldSelected.setBackgroundColor(MicroformasApp.getColor(NewShipmentSearchActivity.this,
                        R.color.white));
                oldSelected.invalidate();
            }

            mClicked = position;

            for(int i = 0; i < mSingleAdapter.getStatus().size(); i++)
            {
                mSingleAdapter.getStatus().set(i, (i == position));
                if(i == position)
                {
                    mSingleAdapter.setSelected(view);
                    view.setBackgroundColor(MicroformasApp.getColor(NewShipmentSearchActivity.this,
                            R.color.orange_micro));
                    view.invalidate();
                }
            }
        }
    };

    private ArrayList<String> mListId;
    private ArrayList<String> mListDesc;

    private TextView mTextSearch;
    private TextView mTextTitle;

    private SingleAdapter mSingleAdapter;

    private String mType;

    private int mClicked;

    private ListView mListResult;
}
