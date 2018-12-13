package com.artefacto.microformas;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.LoginTask;
import com.artefacto.microformas.tasks.RecoverPasswordTask;
import com.artefacto.microformas.tasks.UpdatePhoneImeiTask;

public class LoginActivity extends AppCompatActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)
		{
			finish();
			return;
		}

		setContentView(R.layout.activity_login);

		mEditUser = (EditText) this.findViewById(R.id.login_edit_user);
		mEditPassword = (EditText) this.findViewById(R.id.login_edit_password);
		mCheckBoxReminder = (CheckBox) findViewById(R.id.login_checkbox_reminder);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.login_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.login_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_login));
		setSupportActionBar(toolbarInventory);

		Button buttonSign = (Button) this.findViewById(R.id.login_button_in);
		buttonSign.setOnClickListener(onButtonLoginClicked);

		Button buttonRecovery = (Button) this.findViewById(R.id.login_recovery_password);
        buttonRecovery.setOnClickListener(onButtonRecoveryClicked);

		//Lee el nombre de usuario en caso de haber ingresado uno ya
		mSharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		String user = mSharedPreferences.getString("user", "");
		if (!(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_LOGIN_ACTIVITY))) {   //En caso de que crashee, regresará a MainActivity
			try {
				checkScreenMarker();
			} catch (Exception e) {
				Editor editor = mSharedPreferences.edit();
				editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_MAIN_ACTIVITY);
				editor.commit();

				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra(Constants.PREF_USER_ID, mSharedPreferences.getString(Constants.PREF_USER_ID, ""));
				startActivity(intent);
			}
		}

		if (mSharedPreferences.getBoolean("check_user", false)) {
			mCheckBoxReminder.setChecked(true);
			mEditUser.setText(user);
		}

		//Check internet connection
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			Toast.makeText(getApplicationContext(), "No tiene conexión a internet, inténtelo más tarde.", Toast.LENGTH_LONG).show();

		}
		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		MicroformasApp.currentCtx = this;

		SharedPreferences configServer = getSharedPreferences(Constants.PREF_CONFIG_SERVER,
				Context.MODE_PRIVATE);

		if (!configServer.contains(Constants.PREF_APP_URL_TEST)) {
			Editor editor = configServer.edit();
			editor.putString(Constants.PREF_APP_URL_TEST, Constants.BASE_URL_TEST);
			editor.commit();
		}

		if (!configServer.contains(Constants.PREF_APP_URL_LIVE)) {
			Editor editor = configServer.edit();
			editor.putString(Constants.PREF_APP_URL_LIVE, Constants.BASE_URL_LIVE);
			editor.commit();
		}

		// Default URL Server
		if (!configServer.contains(Constants.PREF_APP_MODE))
		{
			Constants.APP_VERSION = Constants.BASE_MODE_LIVE;

			Editor editor = configServer.edit();
			editor.putString(Constants.PREF_APP_MODE, Constants.APP_VERSION);
			editor.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, Menu.NONE, R.string.menu_server);
//		menu.add(Menu.NONE, 2, Menu.NONE, R.string.menu_password);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 1:
				final EditText input = new EditText(this);
				input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

				LinearLayout layout = new LinearLayout(this);
				layout.setOrientation(LinearLayout.VERTICAL);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				params.setMargins(30, 0, 30, 0);
				layout.addView(input, params);

				new AlertDialog.Builder(this)
						.setTitle("")
						.setMessage(R.string.login_password)
						.setView(layout)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								String value = input.getText().toString();
								if (value.equals("sis-mfU51")) {
									startActivity(new Intent(LoginActivity.this, ServerActivity.class));
								} else {
									getWindow().setSoftInputMode(
											WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
									);

									Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
								}
							}
						}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).show();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	protected OnClickListener onButtonLoginClicked = new OnClickListener() {
		public void onClick(View v) {
			Editor editor = mSharedPreferences.edit();

			ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.setMessage("Autentificando usuario");
			progressDialog.setCancelable(false);

			String userName = mEditUser.getText().toString().trim().replaceAll(" ", "");
			String userPassword = mEditPassword.getText().toString().trim().replaceAll(" ", "");

			//Almacenamos el nombre de usuario para la pantalla de login
			editor.putBoolean("check_user", mCheckBoxReminder.isChecked());
			editor.putString("user", mCheckBoxReminder.isChecked() ? userName : null);
			editor.commit();

			if (validatePassword(userPassword)) {
				LoginTask rTask = new LoginTask(LoginActivity.this, progressDialog);
				rTask.execute(userName, userPassword, "false");
			}
		}
	};

	protected OnClickListener onButtonRecoveryClicked = new OnClickListener()
	{
		public void onClick(View v)
		{
            final EditText inputPassword = new EditText(LoginActivity.this);
            inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);

            LinearLayout layoutPassword = new LinearLayout(LoginActivity.this);
            layoutPassword.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams paramsPassword = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsPassword.setMargins(30, 0, 30, 0);
            layoutPassword.addView(inputPassword, paramsPassword);

            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("")
                    .setMessage(R.string.login_user)
                    .setView(layoutPassword)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String username = inputPassword.getText().toString().trim().replaceAll(" ", "");
                            if (!username.equals("")) {
                                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                                progressDialog.setTitle("");
                                progressDialog.setIndeterminate(true);

                                RecoverPasswordTask recoverPassTask =
                                        new RecoverPasswordTask(LoginActivity.this, progressDialog);
                                recoverPassTask.execute(username);
                            } else {
                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                );

                                Toast.makeText(LoginActivity.this, "Nombre de usuario incorrecto",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            }).show();
		}
	};

	public void goToFirstScreenActivity(String idUser)
	{
		Editor	editor	= mSharedPreferences.edit();
		editor.putString(Constants.PREF_USER_ID, idUser);
		editor.commit();

        Intent 	intent	= new Intent(this, MainActivity.class);
		intent.putExtra(Constants.PREF_USER_ID, idUser);
		startActivity(intent);
	}

	public void checkScreenMarker()
	{
		if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_MAIN_ACTIVITY))
		{
			Intent 	intent	= new Intent(this, MainActivity.class);
			intent.putExtra(Constants.PREF_USER_ID, mSharedPreferences.getString(Constants.PREF_USER_ID, ""));
			startActivity(intent);
		}
		else if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_NUEVAS_LIST_ACTIVITY))
		{
			Intent 	intent	= new Intent(this, RequestListActivity.class);
			intent.putExtra("type", Constants.DATABASE_NUEVAS);
			startActivity(intent);
		}
		else if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_ABIERTAS_LIST_ACTIVITY))
		{
			Intent 	intent	= new Intent(this, RequestListActivity.class);
			intent.putExtra("type", Constants.DATABASE_ABIERTAS);
			startActivity(intent);
		}
		else if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_CERRADAS_LIST_ACTIVITY))
		{
			Intent 	intent	= new Intent(this, RequestListActivity.class);
			intent.putExtra("type", Constants.DATABASE_CERRADAS);
			startActivity(intent);
		}
		else if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_PENDIENTES_LIST_ACTIVITY)){
			Intent 	intent	= new Intent(this, RequestListActivity.class);
			intent.putExtra("type", Constants.DATABASE_PENDIENTES);
			startActivity(intent);
		}
		else if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_NUEVAS_DETAIL_ACTIVITY))
		{
			Intent 	intent	= new Intent(this, RequestDetailActivity.class);
			intent.putExtra("type", Constants.DATABASE_NUEVAS);
			intent.putExtra("idRequest", mSharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
			startActivity(intent);
		}
		else if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_ABIERTAS_DETAIL_ACTIVITY))
		{
			Intent 	intent	= new Intent(this, RequestDetailActivity.class);
			intent.putExtra("type", Constants.DATABASE_ABIERTAS);
			intent.putExtra("idRequest", mSharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
			startActivity(intent);
		}
		else if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_CERRADAS_DETAIL_ACTIVITY))
		{
			Intent 	intent	= new Intent(this, RequestDetailActivity.class);
			intent.putExtra("type", Constants.DATABASE_CERRADAS);
			intent.putExtra("idRequest", mSharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
			startActivity(intent);
		}
		else if(mSharedPreferences.getString(Constants.PREF_ACTIVITY_CHECKER, "").equals(Constants.PREF_PENDIENTES_DETAIL_ACTIVITY))
		{
			Intent 	intent	= new Intent(this, RequestDetailActivity.class);
			intent.putExtra("type", Constants.DATABASE_PENDIENTES);
			intent.putExtra("idRequest", mSharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
			startActivity(intent);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	private boolean validatePassword(String password)
	{
		if(password.length() < 8)
		{
			mEditPassword.setText("");
			Toast.makeText(getApplicationContext(), "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show();
		}
		else if (password.contains(" "))
		{
			mEditPassword.setText("");
			Toast.makeText(getApplicationContext(), "La contraseña no puede incluir espacios", Toast.LENGTH_SHORT).show();
		}
		else if (password.contains("'"))
		{
			mEditPassword.setText("");
			Toast.makeText(getApplicationContext(), "La contraseña no puede incluir comilla simple", Toast.LENGTH_SHORT).show();
		}
		else
		{
			return true;
		}

		return false;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
			grantResults) {

		switch (requestCode) {
			case PERMISSION_READ_STATE: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					myImei = getImeiNumber();
					myNumber = getPhoneNumber();
				} else {
					finish();
				}
				return;
			}
			case PERMISSIONS_REQUEST: {
				if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//startTrackerService();
				} else {
					finish();
				}
				return;
			}
		}
	}

	public void updatePhoneInfo(String idUser){
		UpdatePhoneImeiTask task = new UpdatePhoneImeiTask(this);
		task.execute(myImei,idUser,myNumber);
	}

	private String getImeiNumber() {
		final TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			finish();
		}
		return telephonyManager.getDeviceId();
	}

	private String getPhoneNumber() {
		final TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			finish();
		}
		return telephonyManager.getLine1Number();
	}

	private SharedPreferences	mSharedPreferences;
	private EditText	mEditUser;
	private EditText 	mEditPassword;
	private String myImei = "";
	private String myNumber = "";
	private CheckBox mCheckBoxReminder;
	private static final int PERMISSION_READ_STATE = 255;
	private static final int PERMISSIONS_REQUEST = 100;
}