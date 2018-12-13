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
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.LoginTask;

public class TermsActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.terms_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.terms_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_terms));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		EditText textTerms = (EditText) this.findViewById(R.id.text_terms);
		textTerms.setKeyListener(null);
		
		buttonAccept = (Button) this.findViewById(R.id.terms_accept_yes);
		buttonAccept.setOnClickListener(onButtonAcceptClicked);
		
		buttonCancel = (Button) this.findViewById(R.id.terms_accept_no);
		buttonCancel.setOnClickListener(onButtonCancelClicked);
		
		isAccepted = false;
	}
	
	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent();
		intent.putExtra("USER_ACCEPTED", isAccepted);
		
		setResult(Activity.RESULT_OK, intent);
		
		super.onBackPressed();
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

    public OnClickListener onButtonAcceptClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			final EditText input = new EditText(TermsActivity.this);
			input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			
	       	LinearLayout layout = new LinearLayout(TermsActivity.this);
	       	layout.setOrientation(LinearLayout.VERTICAL);
	       	 
	       	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
       			LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	       	params.setMargins(30, 0, 30, 0);
	       	layout.addView(input, params);
       	 
			new AlertDialog.Builder(TermsActivity.this)
            .setTitle("")
            .setMessage("Contraseña de Usuario")
            .setView(layout)
            .setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                	ProgressDialog progressDialog = new ProgressDialog(TermsActivity.this);
        			progressDialog.setMessage("Autentificando usuario");
        			progressDialog.setCancelable(false);
        			
        			SharedPreferences preferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
                    String idUser = preferences.getString(Constants.PREF_USER_ID, "");
        			
        			String value = input.getText().toString();
                    String userPassword = value.trim().replaceAll(" ", "");
                    
                    if(userPassword.length() < 8)
                    {
                    	input.setText("");
        	    		Toast.makeText(getApplicationContext(), "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show();
        	    	}
        	    	else if (userPassword.contains(" "))
        	    	{
        	    		input.setText("");
        	    		Toast.makeText(getApplicationContext(), "La contraseña no puede incluir espacios", Toast.LENGTH_SHORT).show();
        	    	}
        	    	else if (userPassword.contains("'"))
        	    	{ 
        	    		input.setText("");
        	    		Toast.makeText(getApplicationContext(), "La contraseña no puede incluir comilla simple", Toast.LENGTH_SHORT).show();
        	    	}
        	    	else
        	    	{
						LoginTask loginTask = new LoginTask(TermsActivity.this, progressDialog);
						loginTask.execute(idUser, userPassword, "true");
        	    	}
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                	isAccepted = false;
                }
            }).show();
		}
	};
	
	public void userChecked()
	{
		isAccepted = true;
		onBackPressed();
    }
	
	public OnClickListener onButtonCancelClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
			isAccepted = false;
			onBackPressed();
		}
	};
	
	private Boolean isAccepted;
	
	private Button buttonAccept;
	private Button buttonCancel;
}
