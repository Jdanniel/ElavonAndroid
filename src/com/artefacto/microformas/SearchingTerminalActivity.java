package com.artefacto.microformas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.constants.Constants;

public class SearchingTerminalActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.searching_terminal_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.searching_terminal_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_terminal));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		cantidadEditText = (EditText)findViewById(R.id.terminalCantidadEditText);

        Button buttonAdd 	= (Button) this.findViewById(R.id.button_add_terminal);
        buttonAdd.setOnClickListener(onAddClicked);

        Button buttonConnectivity = (Button) this.findViewById(R.id.terminal_button_connectivity);
        buttonConnectivity.setOnClickListener(onButtonConnectivityClicked);

        Button buttonApplication = (Button) this.findViewById(R.id.terminal_button_application);
        buttonApplication.setOnClickListener(onButtonApplicationClicked);

        Button buttonBrand 	= (Button) this.findViewById(R.id.terminalMarcaButton);
        buttonBrand.setOnClickListener(onButtonBrandClicked);

        Button buttonModel 	= (Button) this.findViewById(R.id.terminalModeloButton);

        if(brandConnectivityId != null){
            buttonModel.setOnClickListener(onButtonModelClicked);
        }else{
            buttonModel.setEnabled(false);
        }

		idProduct = sharedPreferences.getString(Constants.TERMINAL_ID_PRODUCT, "");
		idClient = sharedPreferences.getString(Constants.TERMINAL_ID_CLIENT, "");
        mRequestType = sharedPreferences.getString(Constants.TERMINAL_REQUEST_TYPE, "");

        // Tipo de unidad y conectividad
        TextView textTitleBrandConnectivity = (TextView) this.findViewById(R.id.terminal_text_brand_connectivity);
        TextView textTitleModelApplication = (TextView) this.findViewById(R.id.terminal_text_model_appplication);

        textBrandConnectivity = (TextView) this.findViewById(R.id.terminal_text_brand_connectivity_content);
        textBrandConnectivity.setText("(sin información)");

        textModelApplication = (TextView) this.findViewById(R.id.terminal_text_model_appplication_content);
        textModelApplication.setText("(sin información)");

		if(idProduct.trim().equals("1"))
		{// It's a TPV
            buttonBrand.setVisibility(View.GONE);
            buttonModel.setVisibility(View.GONE);

            buttonConnectivity.setVisibility(View.VISIBLE);
            buttonApplication.setVisibility(View.VISIBLE);

            textTitleBrandConnectivity.setText(getString(R.string.terminal_text_connectivity));
            textTitleModelApplication.setText(getString(R.string.terminal_text_software));
		}

        isDataOk = false;
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TERMINAL_LIST)
        {
            String type = data.getStringExtra("type");

            if(resultCode == Activity.RESULT_OK)
            {
                String id = data.getStringExtra("list_id");
                String desc = data.getStringExtra("list_desc");
                idMarca = id;
                Button buttonModel 	= (Button) this.findViewById(R.id.terminalModeloButton);
                buttonModel.setOnClickListener(onButtonModelClicked);
                buttonModel.setEnabled(true);

                switch (type)
                {
                    case Constants.TERMINAL_BRANDS:
                    case Constants.TERMINAL_CONNECTIVITY:
                        brandConnectivityId = id;
                        brandConnectivityDesc = desc;
                        textBrandConnectivity.setText(brandConnectivityDesc);
                        break;

                    case Constants.TERMINAL_MODELS:
                    case Constants.TERMINAL_SOFTWARE:
                        modelSoftwareId = id;
                        modelSoftwareDesc = desc;
                        textModelApplication.setText(modelSoftwareDesc);
                        break;
                }
            }
            else
            {
                switch (type)
                {
                    case Constants.TERMINAL_BRANDS:
                    case Constants.TERMINAL_CONNECTIVITY:
                        brandConnectivityId = "";
                        brandConnectivityDesc = "";
                        textBrandConnectivity.setText("(sin información)");
                        modelSoftwareId = "";
                        modelSoftwareDesc = "";
                        textModelApplication.setText("(sin información)");
                        break;

                    case Constants.TERMINAL_MODELS:
                    case Constants.TERMINAL_SOFTWARE:
                        brandConnectivityId = "";
                        brandConnectivityDesc = "";
                        textBrandConnectivity.setText("(sin información)");
                        modelSoftwareId = "";
                        modelSoftwareDesc = "";
                        textModelApplication.setText("(sin información)");
                        break;
                }
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
		Intent intent = new Intent(SearchingTerminalActivity.this, TerminalActivity.class);
		
		if(isDataOk)
		{
            intent.putExtra("BRAND_CONNECTIVITY_ID", brandConnectivityId);
            intent.putExtra("BRAND_CONNECTIVITY_DESC", brandConnectivityDesc);
            intent.putExtra("MODEL_SOFTWARE_ID", modelSoftwareId);
            intent.putExtra("MODEL_SOFTWARE_DESC", modelSoftwareDesc);
            intent.putExtra("TERMINAL_CANTIDAD", cantidadEditText.getText().toString().trim());
            intent.putExtra(Constants.TERMINAL_REQUEST_TYPE, mRequestType);

            setResult(Activity.RESULT_OK, intent);
		}
		else
		{
            setResult(Activity.RESULT_CANCELED, intent);
		}

		super.onBackPressed();
	}
	
	public OnClickListener onButtonBrandClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			goToTerminalList(Constants.TERMINAL_BRANDS);
		}
	};
	
	public OnClickListener onButtonModelClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			goToTerminalList(Constants.TERMINAL_MODELS);
		}
	};

    public OnClickListener onButtonConnectivityClicked = new OnClickListener()
    {
        public void onClick(View v)
        {
            goToTerminalList(Constants.TERMINAL_CONNECTIVITY);
        }
    };

    public OnClickListener onButtonApplicationClicked = new OnClickListener()
    {
        public void onClick(View v)
        {
            goToTerminalList(Constants.TERMINAL_SOFTWARE);
        }
    };

	public OnClickListener onAddClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			if(!textBrandConnectivity.getText().toString().equals("(sin información)")
					&& !textModelApplication.getText().toString().equals("(sin información)")
					&& !cantidadEditText.getText().toString().equals(""))
			{
				int cantidad = Integer.parseInt(cantidadEditText.getText().toString().trim());
				if(cantidad > 0)
				{
					isDataOk = true;
					onBackPressed();
				}
				else
				{
					isDataOk = false;
					Toast.makeText(SearchingTerminalActivity.this, "Cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				isDataOk = false;
				Toast.makeText(SearchingTerminalActivity.this, "Todos los campos son necesarios.", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	public void goToTerminalList(String type)
	{
		Intent intent = new Intent(this, TerminalListActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("id_product", idProduct);
        intent.putExtra("id_client", idClient);
        intent.putExtra("id_marca",idMarca);
		startActivityForResult(intent, REQUEST_TERMINAL_LIST);
	}

    public static int REQUEST_TERMINAL_LIST = 660;

	private EditText cantidadEditText;
	
	private Boolean isDataOk;

	private String idProduct;
    private String idClient;
    private String idMarca = null;
    private String brandConnectivityId = null;
    private String brandConnectivityDesc;
    private String modelSoftwareId;
    private String modelSoftwareDesc;
    private String mRequestType;

	private TextView textBrandConnectivity;
	private TextView textModelApplication;
}
