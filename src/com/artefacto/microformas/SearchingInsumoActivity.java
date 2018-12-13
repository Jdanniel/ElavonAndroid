package com.artefacto.microformas;

import com.artefacto.microformas.constants.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchingInsumoActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searching_insumo);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.searching_supply_list_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.searching_supply_list_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.insumo_searching_title));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		textInsumo = (TextView) this.findViewById(R.id.insumo_text_insumo);
		textInsumo.setText("(sin información)");
		
		textCantidad = (TextView) this.findViewById(R.id.insumo_text_cantidad);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
        mIdClient = sharedPreferences.getString(Constants.INSUMO_INSUMOS_ID_CLIENTE, "");

		Button buttonInsumo = (Button) this.findViewById(R.id.insumo_button_insumo);
		buttonInsumo.setOnClickListener(onButtonInsumoClickListener);
		
		Button buttonAdd = (Button) this.findViewById(R.id.insumo_button_add);
		buttonAdd.setOnClickListener(onAddClicked);
		
		isDataOk = false;
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_INSUMO_LIST)
        {
            String type = data.getStringExtra("type");

            if(resultCode == Activity.RESULT_OK)
            {
                mSupplyId = data.getStringExtra("list_id");
                mSupplyDesc = data.getStringExtra("list_desc");
                textInsumo.setText(mSupplyDesc);
            }
            else
            {
                mSupplyId = "";
                mSupplyDesc = "(sin información)";
                textInsumo.setText(mSupplyDesc);
            }
        }
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
        Intent intent = new Intent(SearchingInsumoActivity.this, PendienteInsumoActivity.class);

        if(isDataOk)
        {
            intent.putExtra("INSUMO_INSUMO_ID", mSupplyId);
            intent.putExtra("INSUMO_INSUMO", mSupplyDesc);
            intent.putExtra("INSUMO_CANTIDAD", textCantidad.getText().toString().trim());

            setResult(Activity.RESULT_OK, intent);
        }
        else
        {
            setResult(Activity.RESULT_CANCELED, intent);
        }

        super.onBackPressed();
	}
	
	OnClickListener onButtonInsumoClickListener = new OnClickListener()
	{
		public void onClick(View v) {
			goToInsumoList(Constants.INSUMO_INSUMOS);
		}
	};
	
	public void goToInsumoList(String type)
	{
		Intent intent = new Intent(this, PendienteInsumoListActivity.class);
		intent.putExtra("type", type);
        intent.putExtra("id_client", mIdClient);
		startActivityForResult(intent, REQUEST_INSUMO_LIST);
	}
	
	public OnClickListener onAddClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			if(!textInsumo.getText().toString().equals("(sin información)")
					&& !textCantidad.getText().toString().equals(""))
			{
				int cantidad = Integer.parseInt(textCantidad.getText().toString());
				if(cantidad > 0)
				{
					isDataOk = true;
					onBackPressed();
				}
				else
				{
					isDataOk = false;
					Toast.makeText(SearchingInsumoActivity.this, "Cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				isDataOk = false;
				Toast.makeText(SearchingInsumoActivity.this, "Todos los campos son necesarios.", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	public static int REQUEST_INSUMO_LIST = 777;
	
	private Boolean isDataOk;

    private String mSupplyId;
    private String mSupplyDesc;
    private String mIdClient;

	private TextView textInsumo;
	private TextView textCantidad;
}
