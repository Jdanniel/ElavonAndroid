package com.artefacto.microformas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.RetiroConnTask;

import java.util.ArrayList;

/**
 * Created by GSI-1045 on 22/03/2017.
 */

public class CausaRetiroActivity extends AppCompatActivity {

    String idunidad,
            idcliente,
            noequipo,
            idar,
            idtecnico,
            idnegocio,
            edit,
            isDaniada,
            accion,
            idcausaRetiro;
    ArrayList<String> causaRetiroList;
    TextView titleSpinner;
    Spinner CausaRetiroSpinner;
    Cursor cursor;
    Button btnCausaRetiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_causa_retiro);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.causa_retiro_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.causa_retiro_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_causa_retiro));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        idunidad = intent.getStringExtra("ID_UNIDAD");
        idcliente = intent.getStringExtra("ID_CLIENTE");
        noequipo = intent.getStringExtra("NO_EQUIPO");
        idar = intent.getStringExtra("ID_AR");
        idtecnico = intent.getStringExtra("ID_TECNICO");
        idnegocio = intent.getStringExtra("ID_NEGOCIO");
        edit = intent.getStringExtra("EDIT");
        isDaniada = intent.getStringExtra("IS_DANIADA");
        accion = intent.getStringExtra("ACCION");

        causaRetiroList = new ArrayList<>();

        CausaRetiroSpinner = (Spinner) findViewById(R.id.CausasRetiroSpinner);
        titleSpinner = (TextView) findViewById(R.id.titleSpinner);
        titleSpinner.setText("Seleccionar Causa :");
        btnCausaRetiro = (Button) findViewById(R.id.btn_causa_retiro);

        SQLiteHelper sqliteHelper 	= new SQLiteHelper(this, null);
        SQLiteDatabase dbRequest 		= sqliteHelper.getWritableDB();

        ArrayList<String> causaretiroSpinnerArray = new ArrayList<>();

        cursor = dbRequest.rawQuery("SELECT " + sqliteHelper.CAUSA_RETIRO_ID + ","
                                    + sqliteHelper.CAUSA_RETIRO_DESC + " FROM " + sqliteHelper.CAUSA_RETIRO_DB_NAME
                                    + " WHERE " + sqliteHelper.CAUSA_RETIRO_STATUS + "= 'ACTIVO' "
                                    + "AND " + SQLiteHelper.CAUSA_RETIRO_ID_CLIENTE + " = " + idcliente,null);

        if(cursor.moveToFirst()){

            do {
                causaRetiroList.add(cursor.getString(0));
                causaretiroSpinnerArray.add(cursor.getString(1));
            }while (cursor.moveToNext());

        }
        cursor.close();

        ArrayAdapter<String> causaRetiroAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,causaretiroSpinnerArray);
        causaRetiroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CausaRetiroSpinner.setAdapter(causaRetiroAdapter);

        btnCausaRetiro.setOnClickListener(agregarOnclickListener);
    }

    View.OnClickListener agregarOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            idcausaRetiro = causaRetiroList.get(CausaRetiroSpinner.getSelectedItemPosition());

            AlertDialog dialog = new AlertDialog.Builder(CausaRetiroActivity.this).create();
            dialog.setTitle("Confrimación");
            dialog.setMessage("¿La unidad se encuentra dañada?");
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                        fnIsDaniada("1",idcausaRetiro);
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fnIsDaniada("0",idcausaRetiro);
                }
            });
            dialog.show();
        }
    };

    public void fnIsDaniada(final String isd, final String causaR){
        final String dnd = isd;
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(CausaRetiroActivity.this).create();
        dialog.setTitle("Confirmación");
        dialog.setMessage("¿Seguro que deseas enviar este retiro?");
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
                ProgressDialog progressDialog = new ProgressDialog(CausaRetiroActivity.this);
                progressDialog.setMessage("Enviando Retiro");
                progressDialog.setCancelable(false);
                RetiroConnTask ctask = new RetiroConnTask(CausaRetiroActivity.this, progressDialog);

                ctask.execute(idar, idtecnico,idunidad,idnegocio,edit,dnd,accion,noequipo,causaR);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {

            }
        });
        dialog.show();
    }
}
