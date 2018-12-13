package com.artefacto.microformas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.adapters.InventorySearchAdapter;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import java.util.ArrayList;

public class InventorySearchActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_search);

		if (GetUpdatesService.isUpdating)
		{
			Toast.makeText(this, "Actualización en progreso, intente más tarde.",Toast.LENGTH_SHORT).show();
			this.finish();
			return;
		}

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.inventory_search_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.inventory_search_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_inventory_search_result));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Obtenemos los datos necesarios
		activityIntent = getIntent();
		
		type 					= activityIntent.getIntExtra("type", 0);
		criterioBusquedaNoSerie	= activityIntent.getStringExtra("Criterio_Busqueda_No_Serie"); 
		criterioBusquedaModelo	= activityIntent.getStringExtra("Criterio_Busqueda_Modelo"); 
		criterioBusquedaCliente	= activityIntent.getStringExtra("Criterio_Busqueda_Cliente"); 
		criterioBusquedaUso		= activityIntent.getIntExtra("Criterio_Busqueda_Uso", 0); 
		lvwActivity 		= (ListView)findViewById(R.id.lvwActivity);
		
		
		//Obtenemos los controles del header
		lblHeaderTipoCriterioBusqueda = (TextView) this.findViewById(R.id.lblTipoCriterioBusqueda);
		lblHeaderCriterioBusqueda = (TextView) this.findViewById(R.id.lblCriterioBusqueda);

		//Añadimos valores a controles del header
		switch(type)
        {
			case Constants.BUSQUEDA_INVENTARIO_TIPO_NO_SERIE:
				lblHeaderTipoCriterioBusqueda.setText(R.string.busqueda_inventario_resultado_no_serie_text);
				lblHeaderCriterioBusqueda.setText(criterioBusquedaNoSerie);
				break;
			case Constants.BUSQUEDA_INVENTARIO_TIPO_MODELO:
				lblHeaderTipoCriterioBusqueda.setText(R.string.busqueda_inventario_resultado_modelo_text);
				lblHeaderCriterioBusqueda.setText(criterioBusquedaModelo);
				break;
			case Constants.BUSQUEDA_INVENTARIO_TIPO_CLIENTE:
				lblHeaderTipoCriterioBusqueda.setText(R.string.busqueda_inventario_resultado_cliente_text);
				lblHeaderCriterioBusqueda.setText(criterioBusquedaCliente);
				break;
		}

		//Obtenemos resultados de busqueda
		setunidadInstalacionList();
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

    boolean isBitOn(int value, int bit)
    {
        return ((value>>bit) & (1)) == 1;
    }
	
	public void setunidadInstalacionList()
    {
		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
		SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();
		
		InventorySearchAdapter adapter;
		ArrayList<SearchResult> resultList = new ArrayList<>();
		
		if(criterioBusquedaNoSerie == null)
        {
			criterioBusquedaNoSerie = "";
		}
		
		if(criterioBusquedaModelo == null)
        {
			criterioBusquedaModelo = "";
		}
		
		if(criterioBusquedaCliente == null)
        {
			criterioBusquedaCliente = "";
		}

		String query = "SELECT " 	+ SQLiteHelper.UNIDAD_DESC_MODELO 			+ "," 
									+ SQLiteHelper.UNIDAD_NO_SERIE 				+ "," 
									+ SQLiteHelper.UNIDAD_DESC_CLIENTE 			+ "," 
									+ SQLiteHelper.UNIDAD_DESC_STATUS_UNIDAD  
						+ " from " 	+ SQLiteHelper.UNIDAD_DB_NAME
						+ " where "
							+ SQLiteHelper.UNIDAD_NO_SERIE 		+ " like '%" + criterioBusquedaNoSerie 	+ "%' AND "
							+ SQLiteHelper.UNIDAD_DESC_MODELO 	+ " like '%" + criterioBusquedaModelo 	+ "%' AND "
							+ SQLiteHelper.UNIDAD_DESC_CLIENTE 	+ " like '%" + criterioBusquedaCliente 	+ "%' AND ("
							;
		
		//Añadimos criterios para usadas, nuevas y dañadas
		if(criterioBusquedaUso != Constants.BUSQUEDA_INVENTARIO_TIPO_USO_TODAS)
        {
			if(isBitOn(criterioBusquedaUso, Constants.BUSQUEDA_INVENTARIO_TIPO_USO_NUEVAS >> 1))
            {
				query += SQLiteHelper.UNIDAD_IS_NUEVA + " = 1 OR "; 
			}

			if(isBitOn(criterioBusquedaUso, Constants.BUSQUEDA_INVENTARIO_TIPO_USO_DANIANDAS >> 1))
            {
				query += SQLiteHelper.UNIDAD_IS_DANIADA + " = 1 OR ";
			}

			if(isBitOn(criterioBusquedaUso, Constants.BUSQUEDA_INVENTARIO_TIPO_USO_USADAS >> 1))
            {
				query += "(" + SQLiteHelper.UNIDAD_IS_NUEVA + " = 0 AND " + SQLiteHelper.UNIDAD_IS_DANIADA + " = 0) OR ";
			}
			query += "(1 = 0))";
		}					
		else
        {
			query += "1 = 1)";
		}

        Cursor cursor = db.rawQuery(query, null);
        SearchResult searchResult;

		try
        {
        	if (cursor != null )
            {
        		if  (cursor.moveToFirst())
                {
        			do
                    {
                        searchResult = new SearchResult();
                        searchResult.model = cursor.getString(0);
                        searchResult.noSerie = cursor.getString(1);
                        searchResult.client = cursor.getString(2);
                        searchResult.status = cursor.getString(3);
        				resultList.add(searchResult);
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
                db.close();
                cursor.close();
            }
        }
        
        adapter = new InventorySearchAdapter(this, R.layout.layout_item_inventory_result, resultList);
		lvwActivity.setAdapter(adapter);
	}

    ListView lvwActivity;

    Intent activityIntent;

    int type;
    String criterioBusquedaNoSerie;
    String criterioBusquedaModelo;
    String criterioBusquedaCliente;
    int criterioBusquedaUso;

    TextView lblHeaderTipoCriterioBusqueda;
    TextView lblHeaderCriterioBusqueda;

    public static class SearchResult
    {
        public String idUnit;
        public String model;
        public String noSerie;
        public String client;
        public String status;
    }
}