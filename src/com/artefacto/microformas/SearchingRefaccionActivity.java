package com.artefacto.microformas;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.constants.Constants;

public class SearchingRefaccionActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searching_refaccion);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.searching_repair_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.searching_repair_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.refaccion_searching_title));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		textMarca = (TextView) this.findViewById(R.id.refaccion_marca_content);
		textMarca.setText(sharedPreferences.getString(Constants.REFACCIONES_MARCA_TEXT, "(sin información)"));
		
		textModelo = (TextView) this.findViewById(R.id.refaccion_modelo_content);
		textModelo.setText(sharedPreferences.getString(Constants.REFACCIONES_MODELO_TEXT, "(sin información)"));
		
		textAmount = (EditText) this.findViewById(R.id.refaccion_cantidad_text);
		textAmount.setText(sharedPreferences.getString(Constants.REFACCIONES_CANTIDAD_TEXT, ""));
		
		Button buttonMarca = (Button) this.findViewById(R.id.refaccion_button_marca);
		buttonMarca.setOnClickListener(marcaOnClickListener);
		
		Button buttonModelo = (Button) this.findViewById(R.id.refaccion_button_modelo);
		buttonModelo.setOnClickListener(modeloOnClickListener);
		
		Button buttonAdd = (Button) this.findViewById(R.id.refaccion_button_add);
		buttonAdd.setOnClickListener(onAddClicked);
	}
	
	@Override
	protected void onResume()
	{	// TODO Auto-generated method stub
		super.onResume();
		
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		textMarca.setText(sharedPreferences.getString(Constants.REFACCIONES_MARCA_TEXT, "(sin información)"));
		textModelo.setText(sharedPreferences.getString(Constants.REFACCIONES_MODELO_TEXT, "(sin información)"));
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
	{	// TODO Auto-generated method stub
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		Intent intent = new Intent(SearchingRefaccionActivity.this, RefaccionesActivity.class);
	
		intent.putExtra("REFACCION_MARCA_ID", sharedPreferences.getString(Constants.REFACCIONES_MARCA_ID, ""));
		intent.putExtra("REFACCION_MODELO_ID", sharedPreferences.getString(Constants.REFACCIONES_MODELO_ID, ""));
		
		if(isDataOk)
		{	
			intent.putExtra("REFACCION_MARCA", textMarca.getText().toString());
			intent.putExtra("REFACCION_MODELO", textModelo.getText().toString());
			intent.putExtra("REFACCION_CANTIDAD", textAmount.getText().toString());
		}
		else
		{
			intent.putExtra("REFACCION_MARCA", "");
			intent.putExtra("REFACCION_MODELO", "");
			intent.putExtra("REFACCION_CANTIDAD", "");
		}
		
		setResult(Activity.RESULT_OK, intent);
		super.onBackPressed();
	}
	
	public OnClickListener marcaOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			goToRefaccionList(Constants.REFACCIONES_TIPO_BRAND);
	    }
	};
	
	public OnClickListener modeloOnClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			goToRefaccionList(Constants.REFACCIONES_TIPO_MODEL);
	    }
	};
	
	public OnClickListener onAddClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			if(!textMarca.getText().toString().equals("(sin información)")
					&& !textModelo.getText().toString().equals("(sin información)")
					&& !textAmount.getText().toString().equals(""))
			{
				int cantidad = Integer.parseInt(textAmount.getText().toString());
				if(cantidad > 0)
				{
					isDataOk = true;
					onBackPressed();
				}
				else
				{
					isDataOk = false;
					Toast.makeText(SearchingRefaccionActivity.this, "Cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				isDataOk = false;
				Toast.makeText(SearchingRefaccionActivity.this, "Todos los campos son necesarios.", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	public void goToRefaccionList(int type)
	{
		Intent intent = new Intent(getApplicationContext(), RefaccionesCatalogsActivity.class);
    	intent.putExtra("type", type);
//    	intent.putExtra("activity", "");
    	startActivity(intent);
	}
	
	private Boolean isDataOk;
	
	private TextView textModelo;
	private TextView textMarca;
	
	private EditText textAmount;

//	private CheckBox checkboxNuevo;
//	private CheckBox checkboxDanado;
	
//	private TextView labelAmount;
//	private TextView labelCosto;
	
//	private EditText textCosto;
}
