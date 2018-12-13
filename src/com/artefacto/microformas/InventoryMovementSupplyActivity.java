package com.artefacto.microformas;

import java.util.ArrayList;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.InfoInsumoBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.InfoInsumoConnTask;
import com.artefacto.microformas.tasks.InsumoConnTask;
import com.artefacto.microformas.transactions.DataBaseTransactions;

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InventoryMovementSupplyActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_movement_supply);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.inventory_movement_supply_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.inventory_movement_supply_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_inventory_movement_supply));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UpdateInfo();
	}

    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == REQUEST_CODE_INSERT_SUPPLY)
    {
        if(Activity.RESULT_OK == resultCode)
        {
            UpdateInfo();
            updateInvSupplies();
        }
    }
}

    @Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
	  	menu.add(Menu.NONE, 1, Menu.NONE, R.string.insumo_agregarinsumo_text);
	   	menu.add(Menu.NONE, 1, Menu.NONE, R.string.insumo_enviarinsumos_text);
	    return true;
	}
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
    {
		switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case 1:
                Intent intent = new Intent(getApplicationContext(), InsertNewMovementSupplyActivity.class);
                intent.putExtra("ID_CLIENT", mInfoBean.getIdCliente());
                startActivityForResult(intent, REQUEST_CODE_INSERT_SUPPLY);
                return true;

            case 2:
                Toast.makeText(getApplicationContext(), "Enviando insumos", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
		}
	}
	
	public void setInfo(InfoInsumoBean bean)
    {
        LinearLayout layout = (LinearLayout)findViewById(R.id.layoutInsumo);
        if(layout.getChildCount() > 0)
        {
            layout.removeAllViews();
        }

        mInfoBean = bean;
        mSupplies = bean.getInsumos();

        if (mSupplies.size() == 0)
        {
            TextView InsumosZero = new TextView(this);
			InsumosZero.setText("No hay insumos registrados");
			layout.addView(InsumosZero);
		}

		for(int i = 0; i < mSupplies.size(); i++)
        {
			InfoInsumoBean.Insumo insumo = mSupplies.get(i);
            String info = getString(R.string.insumo_usuario_text)+" "+insumo.getDescUsuario() +"\n"+
                    getString(R.string.insumo_fecha_text)+" "+insumo.getFecAlta() +"\n"+
                    getString(R.string.insumo_insumo_text)+" "+insumo.getDescInsumo() + "\n"+
                    getString(R.string.insumo_cantidad_text)+" "+insumo.getCantidad() +"\n"+
                    getString(R.string.insumo_costounitario_text)+" "+insumo.getCostoUnitario() +"\n"+
                    getString(R.string.insumo_costototal_text)+" "+insumo.getCostoTotal();

			TextView text = new TextView(this);
			text.setText(info);
			layout.addView(text);

			Button button = new Button(this);
			button.setText("Quitar");
            button.setTag(i);
			button.setOnClickListener(new View.OnClickListener()
            {
				public void onClick(View v)
                {
                    final int index = (int) v.getTag();

					AlertDialog dialog = new AlertDialog.Builder(InventoryMovementSupplyActivity.this).create();
			        dialog.setTitle("Confirmación");
			        dialog.setMessage("¿Seguro que deseas quitar este insumo?");
			        dialog.setCancelable(false);
			        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener()
                    {
			            public void onClick(DialogInterface dialog, int buttonId)
                        {
			            	ProgressDialog progressDialog = new ProgressDialog(InventoryMovementSupplyActivity.this);
			            	progressDialog.setMessage("Quitando insumo");
			    			progressDialog.setCancelable(false);
			    			InsumoConnTask task = new InsumoConnTask(InventoryMovementSupplyActivity.this, progressDialog);
			            	
			        		String accion = "2";
			        		String idARInsumo = mSupplies.get(index).getIdArInsumo();
			            	
			            	task.execute("-2","-2","-2","-2",accion,idARInsumo);
			            }
			        });

			        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener()
                    {
			            public void onClick(DialogInterface dialog, int buttonId) {}
			        });

			        dialog.show();
				}
			});

			layout.addView(button);
		}
	}

    public void updateInvSupplies()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
                    mIdResponsible = sharedPreferences.getString(Constants.PREF_USER_ID, "");

                    NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(mIdResponsible);
                    if(notificationsUpdateBean.getConnStatus() == 200)
                    {
                        DataBaseTransactions dataBaseTransactions = new DataBaseTransactions();
                        SQLiteHelper sqliteHelper = new SQLiteHelper(MicroformasApp.getAppContext(), null);

                        boolean saveInvSupplies = dataBaseTransactions.setInvSupplies(mIdResponsible,
                                sqliteHelper, notificationsUpdateBean);
                        dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveInvSupplies,
                                Constants.DATABASE_INV_SUPPLIES, false);

                        sqliteHelper.close();
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void UpdateInfo()
    {
        ProgressDialog pg = new ProgressDialog(this);
        pg.setMessage(getString(R.string.sustitucion_espererecoleccion_message));
        pg.setCancelable(false);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
        mIdAR = sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
        mIdResponsible = sharedPreferences.getString(Constants.PREF_USER_ID, "");

        InfoInsumoConnTask task = new InfoInsumoConnTask(this, pg, mIdAR);
        task.execute("");
    }

    public static int REQUEST_CODE_INSERT_SUPPLY = 15023;

    private ArrayList<InfoInsumoBean.Insumo> mSupplies;

    private InfoInsumoBean mInfoBean;

    private String mIdAR;
    private String mIdResponsible;
}
