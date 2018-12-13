package com.artefacto.microformas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.artefacto.microformas.adapters.CustomAdapter;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.RequestListBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import java.util.ArrayList;


public class RequestListActivity extends AppCompatActivity implements MFActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        mListView = (ListView) this.findViewById(R.id.request_list_view);
        mListView.setOnItemClickListener(mOnItemClicked);

        mToolbar = (Toolbar) this.findViewById(R.id.request_list_toolbar);
        mToolbarTitle = (TextView) this.findViewById(R.id.request_list_toolbar_title);
        setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        UpdateGUI();
        MicroformasApp.activity = this;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MicroformasApp.activity = null;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    public void UpdateGUI()
    {
    	runOnUiThread(new Runnable() {
            public void run() {
                type = getIntent().getIntExtra("type", 0);

                SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
                Editor editor = sharedPreferences.edit();
                if (type == Constants.DATABASE_NUEVAS)
                    editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_NUEVAS_LIST_ACTIVITY);
                else if (type == Constants.DATABASE_ABIERTAS)
                    editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_ABIERTAS_LIST_ACTIVITY);
                else if (type == Constants.DATABASE_CERRADAS)
                    editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_CERRADAS_LIST_ACTIVITY);
                else if (type == Constants.DATABASE_PENDIENTES)
                    editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_PENDIENTES_LIST_ACTIVITY);
                else if (type == Constants.DATABASE_DOCUMENTS)
                    editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_DOCUMENTS_LIST_ACTIVITY);
                editor.commit();

                String title;
                String conditional = "=?";
                String argument = String.valueOf(type);

                if (type == Constants.DATABASE_NUEVAS)
                    title = "Nuevas";
                else if (type == Constants.DATABASE_ABIERTAS)
                    title = "Abiertas";
                else if (type == Constants.DATABASE_CERRADAS) {
                    title = "Cerradas";
                    conditional += " AND (v_prefacturacion<0 OR v_prefacturacion >4)";
                } else if (type == Constants.DATABASE_DOCUMENTS) {
                    title = "Documentos";
                    conditional += " AND v_prefacturacion>0 AND v_prefacturacion <5";
                    argument = String.valueOf(Constants.DATABASE_CERRADAS);
                } else
                    title = "Pendientes";
                mToolbarTitle.setText(title);

                SQLiteHelper sqliteHelper = new SQLiteHelper(RequestListActivity.this, null);
                SQLiteDatabase dbRequest = sqliteHelper.getReadableDB();

                int requestsNumber = 0;

                String[] campos = new String[]{SQLiteHelper.REQUESTS_ID_REQUEST,
                        SQLiteHelper.REQUESTS_DESC_CLIENTE,
                        SQLiteHelper.REQUESTS_DESC_SERVICIO,
                        SQLiteHelper.REQUESTS_FEC_ALTA,
                        SQLiteHelper.REQUESTS_HORAS_GARANTIA,
                        SQLiteHelper.REQUESTS_HORAS_ATENCION,
                        SQLiteHelper.REQUESTS_NO_AR,
                        SQLiteHelper.REQUESTS_DESC_NEGOCIO,
                        SQLiteHelper.REQUESTS_FEC_GARANTIA,
                        SQLiteHelper.REQUESTS_PREFACTURACION,
                        SQLiteHelper.REQUESTS_COMENTARIO,
                        SQLiteHelper.REQUESTS_ID_CLIENTE,
                        SQLiteHelper.REQUESTS_FEC_ATENCION};
                String[] args = new String[]{argument};
                Cursor c = dbRequest.query(SQLiteHelper.REQUESTS_DB_NAME, campos,
                        SQLiteHelper.CATALOGS_ID_CATALOG + conditional,
                        args,
                        null,
                        null,
                        null);
                requestsNumber = c.getCount();
                requests = new ArrayList<RequestListBean>();

                try {
                    c.moveToFirst();
                    for (int i = 0; i < requestsNumber; i++) {
                        String idRequest = c.getString(0);
                        String descNegocio = c.getString(1);
                        String descServicio = c.getString(2);
                        String fechaAlta = c.getString(3);
                        String noAr = c.getString(6);
                        String negocio = c.getString(7);
                        String fechaGarantia = c.getString(8);
                        String prefacturacion = c.getString(9);
                        String comentario = c.getString(10);
                        String idcliente = c.getString(11);
                        String fecAtencion = c.getString(12);

                        int horasGarantia;
                        int horasAtencion;

                        try {
                            horasGarantia = Integer.valueOf(c.getString(4));
                        } catch (Exception e) {
                            horasGarantia = 0;
                        }

                        try {
                            horasAtencion = Integer.valueOf(c.getString(5));
                        } catch (Exception e) {
                            horasAtencion = 0;
                        }

                        requests.add(new RequestListBean());

                        requests.get(i).setNoAr(noAr);
                        requests.get(i).setDescCliente(descNegocio);
                        requests.get(i).setDescServicio(descServicio);
                        requests.get(i).setFechaAlta(fechaAlta);
                        requests.get(i).setIdRequest(idRequest);
                        requests.get(i).setDescNegocio(negocio);
                        requests.get(i).setFechaGarantia(fechaGarantia);
                        requests.get(i).setPrefacturacion(prefacturacion);
                        requests.get(i).setComentario(comentario);
                        requests.get(i).setIdcliente(idcliente);
                        requests.get(i).setFechaAtencion(fecAtencion);

                        if (type == Constants.DATABASE_DOCUMENTS) {
                            requests.get(i).setImage(null);
                        } else {
                            int fechaDifference = horasGarantia - horasAtencion;
                            if (fechaDifference > 2)
                                requests.get(i).setImage(BitmapFactory.decodeResource(getResources(), R.drawable.green_button));
                            else if (fechaDifference > 2)
                                requests.get(i).setImage(BitmapFactory.decodeResource(getResources(), R.drawable.yellow_button));
                            else
                                requests.get(i).setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_button));
                        }
                        c.moveToNext();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (adapter == null) {
                    adapter = new CustomAdapter(RequestListActivity.this, R.layout.activity_cerradas, requests);
                    mListView.setAdapter(adapter);
                } else {
                    adapter.reload(requests);
                }

                mListView.setTextFilterEnabled(true);

                c.close();
                dbRequest.close();

            }
        });
    }

    private AdapterView.OnItemClickListener mOnItemClicked = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            String idRequest = requests.get(position).getIdRequest();
            String idCliente = requests.get(position).getIdcliente();

            Intent intent = new Intent(RequestListActivity.this, RequestDetailActivity.class);
            intent.putExtra("idRequest", idRequest);
            intent.putExtra("idcliente",idCliente);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    };

    private int type = 0;

    private ArrayList <RequestListBean> requests;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    private ListView mListView;

    private CustomAdapter adapter = null;

}