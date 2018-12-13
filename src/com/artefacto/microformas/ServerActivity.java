package com.artefacto.microformas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;

public class ServerActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.server_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.server_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_server));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mConfigServer = getSharedPreferences(Constants.PREF_CONFIG_SERVER, Context.MODE_PRIVATE);

        mRadioLive = (RadioButton) this.findViewById(R.id.server_radio_live);
        mRadioLive.setOnClickListener(onRadioLiveClicked);
        mRadioLive.setChecked(Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE));

        mRadioTest = (RadioButton) this.findViewById(R.id.server_radio_test);
        mRadioTest.setOnClickListener(onRadioTestClicked);
        mRadioTest.setChecked(Constants.APP_VERSION.equals(Constants.BASE_MODE_TEST));

        mEditLive = (EditText) this.findViewById(R.id.server_edit_live);
        mEditLive.setText(mConfigServer.getString(Constants.PREF_APP_URL_LIVE, Constants.BASE_URL_LIVE));

        mEditTest = (EditText) this.findViewById(R.id.server_edit_test);
        mEditTest.setText(mConfigServer.getString(Constants.PREF_APP_URL_TEST, Constants.BASE_URL_TEST));

        Button buttonUpdate = (Button) this.findViewById(R.id.server_button_update);
        buttonUpdate.setOnClickListener(onButtonUpdateClicked);

        isLiveServer = Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE);
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

    public RadioButton.OnClickListener onRadioLiveClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            isLiveServer = true;
            mRadioLive.setChecked(true);
            mRadioTest.setChecked(false);
        }
    };

    public RadioButton.OnClickListener onRadioTestClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            isLiveServer = false;
            mRadioLive.setChecked(false);
            mRadioTest.setChecked(true);
        }
    };

    public Button.OnClickListener onButtonUpdateClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(!mEditLive.getText().toString().equals("") && !mEditTest.getText().toString().equals(""))
            {
                SharedPreferences.Editor editor = mConfigServer.edit();

                Constants.APP_VERSION = isLiveServer ? Constants.BASE_MODE_LIVE
                        : Constants.BASE_MODE_TEST;

                editor.putString(Constants.PREF_APP_MODE, Constants.APP_VERSION);
                editor.putString(Constants.PREF_APP_URL_LIVE, mEditLive.getText().toString());
                editor.putString(Constants.PREF_APP_URL_TEST, mEditTest.getText().toString());
                editor.commit();

                Toast.makeText(ServerActivity.this, "Servidor: " + (isLiveServer ? "Producción" : "Pruebas"),
                        Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
            else
            {
                Toast.makeText(ServerActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private SharedPreferences mConfigServer;

    private RadioButton mRadioLive;
    private RadioButton mRadioTest;

    private EditText mEditLive;
    private EditText mEditTest;

    private boolean isLiveServer;
}
