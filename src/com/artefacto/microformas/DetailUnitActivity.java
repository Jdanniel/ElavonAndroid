package com.artefacto.microformas;

import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailUnitActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_unit);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.inventory_search_detail_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.inventory_search_detail_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_inventory_unit_search_detail));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		String noSerie = intent.getStringExtra("Unidad_Detail_No_Serie");
		initInfo(noSerie);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }

        return true;
    }

	public void initInfo(String noSerie)
    {
        TextView lblUnidad = (TextView)findViewById(R.id.lblUnidad);
        TextView lblId = (TextView)findViewById(R.id.lblId);
        TextView lblMarca = (TextView)findViewById(R.id.lblMarca);
        TextView lblModelo = (TextView)findViewById(R.id.lblModelo);
        TextView lblCliente = (TextView)findViewById(R.id.lblCliente);
        TextView lblFechAlta = (TextView)findViewById(R.id.lblFechaAlta);
        TextView lblNoInventario = (TextView)findViewById(R.id.lblNoInventario);
        TextView lblNoSerie = (TextView)findViewById(R.id.lblNoSerie);
        TextView lblIMEI = (TextView)findViewById(R.id.lblIMEI);
        TextView lblSIM = (TextView)findViewById(R.id.lblSIM);
        TextView lblStatus = (TextView)findViewById(R.id.lblStatus);
        TextView lblUnidadNueva	= (TextView)findViewById(R.id.lblUnidadNueva);
        TextView lblUnidadDaniana = (TextView)findViewById(R.id.lblUnidadDaniada);
        TextView lblNoEquipo = (TextView)findViewById(R.id.lblNoEquipo);
        TextView lblConnectivity = (TextView)findViewById(R.id.lblConnectivityContent);
        TextView lblAplicativo = (TextView)findViewById(R.id.lblSoftware);
        TextView lblRetiro = (TextView)findViewById(R.id.lblRetiro);
        TextView lblSolicitudRecoleccion = (TextView)findViewById(R.id.lblSolicitudRecoleccion);

		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		String query = "SELECT " 	+ SQLiteHelper.UNIDAD_DESC_MODELO 			+ ","
									+ SQLiteHelper.UNIDADES_ID_UNIDAD 			+ ","	
									+ SQLiteHelper.UNIDAD_DESC_MARCA 			+ ","
									+ SQLiteHelper.UNIDAD_DESC_MODELO 			+ ","
									+ SQLiteHelper.UNIDAD_DESC_CLIENTE 			+ ","
									+ SQLiteHelper.UNIDAD_FEC_ALTA	 			+ ","
									+ SQLiteHelper.UNIDAD_NO_INVENTARIO 		+ ","
									+ SQLiteHelper.UNIDAD_NO_SERIE	 			+ ","
									+ SQLiteHelper.UNIDAD_NO_IMEI	 			+ ","
									+ SQLiteHelper.UNIDAD_NO_SIM	 			+ ","
									+ SQLiteHelper.UNIDAD_STATUS	 			+ ","
									+ SQLiteHelper.UNIDAD_IS_NUEVA	 			+ ","
									+ SQLiteHelper.UNIDAD_IS_DANIADA 			+ ","
									+ SQLiteHelper.UNIDAD_NO_EQUIPO 			+ ","
									+ SQLiteHelper.UNIDAD_DESC_LLAVE 			+ ","
									+ SQLiteHelper.UNIDAD_DESC_SOFTWARE 		+ ","
									+ SQLiteHelper.UNIDAD_IS_RETIRO 			+ ","
									+ SQLiteHelper.UNIDAD_ID_SOL_REC 			+ ","
									+ sqliteHelper.UNIDAD_CONN
						+ " from " 	+ SQLiteHelper.UNIDAD_DB_NAME
						+ " where " + SQLiteHelper.UNIDAD_NO_SERIE 	+ " like '%" + noSerie 	+ "%'";

        Cursor cursor = db.rawQuery(query, null);
		try
        {
        	if (cursor != null )
            {
        		if  (cursor.moveToFirst())
                {
        			lblUnidad.setText(cursor.getString(0));
        			lblId.setText(cursor.getString(1));
        			lblMarca.setText(cursor.getString(2));
        			lblModelo.setText(cursor.getString(3));
        			lblCliente.setText(cursor.getString(4));
        			lblFechAlta.setText(cursor.getString(5));
        			lblNoInventario.setText(cursor.getString(6));
        			lblNoSerie.setText(cursor.getString(7));
        			lblIMEI.setText(cursor.getString(8));
        			lblSIM.setText(cursor.getString(9));
        			lblStatus.setText(cursor.getString(10));
        			lblUnidadNueva.setText(booleanToYesNo(cursor.getString(11)));
        			lblUnidadDaniana.setText(booleanToSiNo(Boolean.parseBoolean(cursor.getString(12))));
        			lblNoEquipo.setText(cursor.getString(13));
                    lblAplicativo.setText(cursor.getString(15));
        			lblConnectivity.setText(cursor.getString(18));
        			lblRetiro.setText(booleanToSiNo(Boolean.parseBoolean(cursor.getString(16))));
        			lblSolicitudRecoleccion.setText(cursor.getString(17));
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
	}
	
	private String booleanToSiNo(boolean value)
    {
		if(value)
        {
            return "Si";
        }

        return "No";
	}

	private String booleanToYesNo(String value){
		if (value.equals("1")){
			return "Si";
		}
		return "No";
	}
}