package com.artefacto.microformas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.tasks.InsumoConnTask;

public class InsertNewMovementSupplyActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_new_movement_supply);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.insert_new_movement_supply_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.insert_new_movement_supply_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_insert_new_movement_supply));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

        Spinner spinner = (Spinner)findViewById(R.id.InsumoSpinner);
        spinner.setVisibility(View.GONE);

        idCliente = getIntent().getStringExtra("ID_CLIENT");

        buttonLookForInsumos = (Button) this.findViewById(R.id.buttonLookForInsumo);
        buttonLookForInsumos.setOnClickListener(lookForInsumosListener);
        buttonLookForInsumos.setVisibility(View.VISIBLE);

        btnAddInsumo = (Button) this.findViewById(R.id.insertarInsumo);
        btnAddInsumo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InsertarButtonOnClick();
                }
            }
        );

        mSupplyTotal = Integer.MIN_VALUE;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_MOVEMENT_NEW_SUPPLY)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                mSupplyId = data.getStringExtra("ID_SUPPLY");

                mSupplyDesc = data.getStringExtra("SUPPLY_DESC");
                buttonLookForInsumos.setText(mSupplyDesc);

                String strTotal = data.getStringExtra("SUPPLY_COUNT");
                if(MicroformasApp.isNumber(strTotal))
                {
                    mSupplyTotal = Integer.parseInt(strTotal);
                }
            }
        }
    }

    public void InsertarButtonOnClick()
    {
		EditText cantidadEdit = findViewById(R.id.InsumoCantidadEdit);
		String userCount = cantidadEdit.getText().toString();

		if (!userCount.trim().equals(""))
        {
            if(MicroformasApp.isNumber(userCount))
            {
                if(mSupplyTotal != Integer.MIN_VALUE)
                {
                    final int count = Integer.parseInt(userCount);
                    if(mSupplyTotal != -9999)
                    {
                        if(count > mSupplyTotal)
                        {
                            Toast.makeText(InsertNewMovementSupplyActivity.this,
                                    "La cantidad ingresada es mayor a la existente.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    AlertDialog dialog = new AlertDialog.Builder(InsertNewMovementSupplyActivity.this).create();
                    dialog.setTitle("Confirmación");
                    dialog.setMessage("¿Seguro que deseas agregar este insumo?");
                    dialog.setCancelable(false);
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int buttonId)
                        {
                            ProgressDialog progressDialog = new ProgressDialog(InsertNewMovementSupplyActivity.this);
                            progressDialog.setMessage("Enviando insumo");
                            progressDialog.setCancelable(false);
                            InsumoConnTask task = new InsumoConnTask(InsertNewMovementSupplyActivity.this, progressDialog);

                            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);

                            String idAR = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
                            String idTecnico = sharedPreferences.getString(Constants.PREF_USER_ID,"");
                            String accion = "1";
                            String idARInsumo = "-2";

                            task.execute(idAR, idTecnico, mSupplyId, String.valueOf(count), accion, idARInsumo);
                        }
                    });

                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int buttonId) {

                        }
                    });

                    dialog.show();
                }
                else
                {
                    Toast.makeText(InsertNewMovementSupplyActivity.this,
                            "Favor de seleccionar insumo", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(InsertNewMovementSupplyActivity.this,
                        "Formato de cantidad incorrecta", Toast.LENGTH_SHORT).show();
            }
		}
        else
        {
            Toast.makeText(InsertNewMovementSupplyActivity.this, "Introduce una cantidad",
                    Toast.LENGTH_SHORT).show();
        }
	}
	
	
	android.view.View.OnClickListener lookForInsumosListener = new View.OnClickListener()
    {
		public void onClick(View v)
        {
			Intent intentLookForActivity = new Intent(InsertNewMovementSupplyActivity.this,
                    LookForSuppliesActivity.class);
			intentLookForActivity.putExtra("ID_CLIENT", idCliente);
			startActivityForResult(intentLookForActivity, REQUEST_CODE_MOVEMENT_NEW_SUPPLY);
		}
	};

    public static int REQUEST_CODE_MOVEMENT_NEW_SUPPLY = 15022;

    private String idCliente;
    private String mSupplyId;
    private String mSupplyDesc;

    private int mSupplyTotal;

    Button buttonLookForInsumos;
    Button btnAddInsumo;
}
