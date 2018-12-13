package com.artefacto.microformas;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.SendCommentConnTask;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends AppCompatActivity
{
	private int type;
	private EditText comentarios;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.comment_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.comment_toolbar_title);
        comentarios = this.findViewById(R.id.editText1);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_comment));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		
		Button sendCommentButton = (Button)findViewById(R.id.button1);
		sendCommentButton.setOnClickListener(sendCommentButtonOnClickListener);
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

	OnClickListener sendCommentButtonOnClickListener = new OnClickListener() {
		public void onClick(View v) {
            if(!comentarios.getText().toString().trim().equals("")){
                sendComment();
            }else{
                Toast.makeText(CommentActivity.this, "Favor de Ingresar un Comentario", Toast.LENGTH_SHORT).show();
            }

		}
	};
	
//	public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	    	Intent intent = new Intent(this, RequestDetailActivity.class);
//
//	    	SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
//	    	intent.putExtra("idRequest", sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
//	    	intent.putExtra("type", type);
//
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}
	
	public void sendComment(){
		AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Confirmación");
        dialog.setMessage("¿Seguro que deseas enviar este comentario?");
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
            	ProgressDialog progressDialog = new ProgressDialog(CommentActivity.this);
            	progressDialog.setMessage("Enviando comentario");
    			progressDialog.setCancelable(false);
            	SendCommentConnTask ctask = new SendCommentConnTask(CommentActivity.this, progressDialog, type);
            	SharedPreferences sharedPreferences	= getSharedPreferences("UserConfig", Context.MODE_PRIVATE);   
            	EditText text =(EditText)findViewById(R.id.editText1);
            	ctask.execute(sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""), sharedPreferences.getString("user_id", ""), text.getText().toString());
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {

            }
        });
        dialog.show();
		
		
	}
}
