package com.artefacto.microformas;

import com.artefacto.microformas.beans.InfoActualizacionBean;
import com.artefacto.microformas.beans.InfoMovimientosBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.InfoActualizacionTask;
import com.artefacto.microformas.tasks.InfoMovimientosConnTask;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InventoryMovementsActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_movements);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.inventory_movement_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.inventory_movement_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_inventory_movements));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		mIdAR = mSharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, "");

        Intent intent = getIntent();
        mNoAR = intent.getStringExtra("noar");

		TextView lblTitulo = (TextView)findViewById(R.id.lblTitulo);
		lblTitulo.setText("Movimientos AR: " + mNoAR);
	
		//Obtenemos los tipos de movimientos permitidos
		ProgressDialog pg = new ProgressDialog(this);
		pg.setMessage(getString(R.string.mov_inv_info));
		pg.setCancelable(false);
		
		InfoMovimientosConnTask task = new InfoMovimientosConnTask(this,pg, mIdAR);
		task.execute("");
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
	
	public void btnInstalacionClick(View v)
    {
		Intent intent = new Intent(this, InstalacionActivity.class);
		intent.putExtra("Get_Info", true);
		startActivity(intent);
	}
	
	public void btnRetiroClick(View v)
    {
		Intent intent = new Intent(this, RetiroActivity.class);
		startActivity(intent);
	}
	
	public void btnSustitucionClick(View v)
    {
		Intent intent = new Intent(this, SustitucionesActivity.class);
		startActivity(intent);
	}
	
	public void btnInsumoClick(View v)
    {
		Intent intent = new Intent(this, InventoryMovementSupplyActivity.class);
		startActivity(intent);
	}
	
	public void btnActualizacionClick(View v)
    {
		ProgressDialog progressDialog = new ProgressDialog(InventoryMovementsActivity.this);
		InfoActualizacionTask infoActualizacionTask = new InfoActualizacionTask(InventoryMovementsActivity.this, progressDialog, mIdAR);
		progressDialog.setMessage("Descargando la información, espere un momento.");
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		infoActualizacionTask.execute(sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
	}
	
	public void setInfo(InfoMovimientosBean bean)
    {
		TextView label;
		Button button;
		
		//Activar label y botones correspondientes
		if(bean.isActualizacion())
        {
			label = (TextView)findViewById(R.id.lblActualizacion);
			button = (Button)findViewById(R.id.btnActualizacion);
			
			label.setVisibility(View.VISIBLE);
			button.setVisibility(View.VISIBLE);
		}
		
		if(bean.isInstalacion())
        {
			label = (TextView)findViewById(R.id.lblInstalacion);
			button = (Button)findViewById(R.id.btnInstalacion);
			
			label.setVisibility(View.VISIBLE);
			button.setVisibility(View.VISIBLE);
		}
		
		if(bean.isInsumo())
        {
			label = (TextView)findViewById(R.id.lblInsumo);
			button = (Button)findViewById(R.id.btnInsumo);
			
			label.setVisibility(View.VISIBLE);
			button.setVisibility(View.VISIBLE);
		}
		
		if(bean.isRetiro())
        {
			label = (TextView)findViewById(R.id.lblRetiro);
			button = (Button)findViewById(R.id.btnRetiro);
			
			label.setVisibility(View.VISIBLE);
			button.setVisibility(View.VISIBLE);
		}
		
		if(bean.isSustitucion())
        {
			label = (TextView)findViewById(R.id.lblSustitucion);
			button = (Button)findViewById(R.id.btnSustitucion);
			
			label.setVisibility(View.VISIBLE);
			button.setVisibility(View.VISIBLE);
		}
	}
	
	public void setInfoActualizacion(InfoActualizacionBean infoActualizacionBean)
    {
		if(infoActualizacionBean.getConnStatus() == 200)
        {
			Intent intent = new Intent(this, ActualizacionActivity.class);
			intent.putExtra("bean", infoActualizacionBean);
			intent.putExtra("noar", mNoAR);
			intent.putExtra("id", mIdAR);
			startActivity(intent);
		}
		else
        {
			Toast.makeText(getApplicationContext(), "No se pudo cargar la información, intente más tarde",
							Toast.LENGTH_SHORT).show();
		}
	}

    private SharedPreferences mSharedPreferences;

    private String mIdAR;
    private String mNoAR;
}