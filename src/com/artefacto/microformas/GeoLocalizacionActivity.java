package com.artefacto.microformas;

import com.artefacto.microformas.tasks.AgregarCoordenadasConnTask;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GeoLocalizacionActivity extends AppCompatActivity implements LocationListener {

	private LocationManager locationManager;
	private String provider;
	private Activity activity;
	public String type;

	private TextView lblLatitud;
	private TextView lblLongitud;
	private TextView lblPrecision;

	private TextView lblLatitudOptimo;
	private TextView lblLongitudOptimo;
	private TextView lblPrecisionOptimo;

	private Button btnComenzar;
	private Button btnDetener;
	private Button btnEnviarDatos;

	private ProgressBar progressBar;


	private double latOptimo = 0;
	private double lonOptimo = 0;
	private double preOptimo = 0;

	boolean isBackBtnPressed;

	private static final int MY_ACCESS_FINE_LOCATION = 1 ;
	private static final int MY_ACCESS_COARSE_LOCATION = 1 ;

	private static final int REQUEST_CODE_PERMISSION = 2;

	String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geo_localizacion);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.localization_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.localization_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_geo_localizacion));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		isBackBtnPressed = false;
		activity = this;
		//Obtenemos los controles
		lblLatitud = (TextView) findViewById(R.id.lblLatitud);
		lblLongitud = (TextView) findViewById(R.id.lblLongitud);
		lblPrecision = (TextView) findViewById(R.id.lblPrecision);

		lblLatitudOptimo = (TextView) findViewById(R.id.lblLatitudOptimo);
		lblLongitudOptimo = (TextView) findViewById(R.id.lblLongitudOptimo);
		lblPrecisionOptimo = (TextView) findViewById(R.id.lblPrecisionOptimo);

		btnComenzar = (Button) findViewById(R.id.btnComenzar);
		btnDetener = (Button) findViewById(R.id.btnDetener);
		btnEnviarDatos = (Button) findViewById(R.id.btnEnviarDatos);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		btnComenzar.setOnClickListener(btnComenzarOnclick);

		progressBar.setIndeterminate(true);

		Intent intent = getIntent();
		type = intent.getStringExtra("type");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;

			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	protected View.OnClickListener btnComenzarOnclick = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

			boolean encontroCoordenadas = false;

			//Verificamos si el GPS está activado
			locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

			boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			btnDetener.setEnabled(false);
			btnEnviarDatos.setEnabled(false);

			//Borramos los datos
			lblLatitud.setText(R.string.geo_localizacion_initial_text);
			lblLongitud.setText(R.string.geo_localizacion_initial_text);
			lblPrecision.setText(R.string.geo_localizacion_initial_text);
			lblLatitudOptimo.setText(R.string.geo_localizacion_initial_text);
			lblLongitudOptimo.setText(R.string.geo_localizacion_initial_text);
			lblPrecisionOptimo.setText(R.string.geo_localizacion_initial_text);

			latOptimo = lonOptimo = preOptimo = 0;


			if (!enabled) {
				//Si el GPS está desactivado, le pedimos al usuario que lo active
				AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
				alertDialog.setTitle("Confirmación");
				alertDialog.setMessage("El GPS se encuentra desactivado, es necesario activar el GPS para obtener la localización.\n¿Deseas activar el GPS?");

				alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Activar GPS", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);

					}
				});

				alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				});

				alertDialog.show();
			}
			if (enabled) {
				// Definimos el criterio de seleccion para la localización
				Criteria criteria = new Criteria();

				//provider = locationManager.getBestProvider(criteria, true);
				provider = locationManager.NETWORK_PROVIDER;

				try{
					if (ActivityCompat.checkSelfPermission(activity,mPermission) != MockPackageManager.PERMISSION_GRANTED){
						ActivityCompat.requestPermissions(activity,new String[]{mPermission},REQUEST_CODE_PERMISSION);
					}else{
						Location location = locationManager.getLastKnownLocation(provider);

						if (location != null) {
							onLocationChanged(location);
							encontroCoordenadas = true;
						}

						locationManager.requestLocationUpdates(provider, 100, 0.1f, locationListener);

						btnComenzar.setEnabled(false);
						btnDetener.setEnabled(true);
						progressBar.setVisibility(View.VISIBLE);
						encontroCoordenadas = true;

						//Si no encuentra información, lo vuelve a intentar
						if (encontroCoordenadas == false) {
							btnComenzarOnClick();
						}else{
							btnComenzar.setEnabled(true);
							btnDetener.setEnabled(false);
							btnEnviarDatos.setEnabled(true);

							progressBar.setVisibility(View.GONE);
						}

					}
				}catch (Exception e){
					e.printStackTrace();
				}

			}
		}
	};

	public void btnComenzarOnClick() {
		boolean encontroCoordenadas = false;

		//Verificamos si el GPS está activado
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		btnDetener.setEnabled(false);
		btnEnviarDatos.setEnabled(false);

		//Borramos los datos
		lblLatitud.setText(R.string.geo_localizacion_initial_text);
		lblLongitud.setText(R.string.geo_localizacion_initial_text);
		lblPrecision.setText(R.string.geo_localizacion_initial_text);
		lblLatitudOptimo.setText(R.string.geo_localizacion_initial_text);
		lblLongitudOptimo.setText(R.string.geo_localizacion_initial_text);
		lblPrecisionOptimo.setText(R.string.geo_localizacion_initial_text);

		latOptimo = lonOptimo = preOptimo = 0;


		if (!enabled) {
			//Si el GPS está desactivado, le pedimos al usuario que lo active
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Confirmación");
			alertDialog.setMessage("El GPS se encuentra desactivado, es necesario activar el GPS para obtener la localización.\n¿Deseas activar el GPS?");

			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Activar GPS", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {

					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);

				}
			});

			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {

				}
			});

			alertDialog.show();
		}
		if (enabled) {
			// Definimos el criterio de seleccion para la localización
			Criteria criteria = new Criteria();
			Log.e("TAG", " Entro 1");
			provider = locationManager.getBestProvider(criteria, true);

			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				onLocationChanged(location);
				encontroCoordenadas = true;
			}

			locationManager.requestLocationUpdates(provider, 100, 0.1f, locationListener);

			btnComenzar.setEnabled(false);
			btnDetener.setEnabled(true);
			progressBar.setVisibility(View.VISIBLE);
			encontroCoordenadas = true;
		}

		//Si no encuentra información, lo vuelve a intentar
		if (encontroCoordenadas == false) {
			btnComenzarOnClick();
		}else{
			btnComenzar.setEnabled(true);
			btnDetener.setEnabled(false);
			btnEnviarDatos.setEnabled(true);

			progressBar.setVisibility(View.GONE);
		}
	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			lblLatitud.setText(String.valueOf(location.getLatitude()));
			lblLatitud.setText(String.valueOf(location.getLongitude()));
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onBackPressed()
	{
		isBackBtnPressed = true;
		super.onBackPressed();
	}

	public void btnDetenerOnClick(View v){
		btnComenzar.setEnabled(true);
		btnDetener.setEnabled(false);
		btnEnviarDatos.setEnabled(true);

		progressBar.setVisibility(View.GONE);

		//TODO está tronando en esta línea: Unable to pause activity
		if(isBackBtnPressed == false)
			locationManager.removeUpdates(this);
	}

	public void btnEnviarDatosOnClick(View v){
		ProgressDialog pg = new ProgressDialog(this);
		pg.setMessage(getString(R.string.geo_localizacion_info_envio));
		pg.setCancelable(false);

		//Obtenemos los datos importantes
		Intent intent = getIntent();

		String idNegocio = intent.getStringExtra("Id_Negocio");

		AgregarCoordenadasConnTask task = new AgregarCoordenadasConnTask(this,pg,idNegocio, latOptimo, lonOptimo, preOptimo);
		task.execute("");
	}

	/* Detenemos la recolección de la posición*/
	@Override
	protected void onPause() {
		super.onPause();
		btnDetenerOnClick(null);
		btnEnviarDatos.setEnabled(false);
	}


	public void onLocationChanged(Location location) {
		double lat = (location.getLatitude());
		double lng = (location.getLongitude());
		double pre = (location.getAccuracy());

		String strPre = (int)pre + " m";

		lblLatitud.setText(String.valueOf(lat));
		lblLongitud.setText(String.valueOf(lng));
		lblPrecision.setText(strPre);

		if(preOptimo == 0 || pre < preOptimo){
			preOptimo = location.getAccuracy();
			latOptimo = lat;
			lonOptimo = lng;

			String strPreOptimo = (int)preOptimo + " m";

			lblLatitudOptimo.setText(String.valueOf(lat));
			lblLongitudOptimo.setText(String.valueOf(lng));
			lblPrecisionOptimo.setText(strPreOptimo);
		}
	}

	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Deshabilitado el proveedor de locación: " + provider,
				Toast.LENGTH_SHORT).show();
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Habilitado un nuevo proveedor de locación: " + provider,
				Toast.LENGTH_SHORT).show();
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_CODE_PERMISSION){
			if (grantResults.length == 1 && grantResults[0] == MockPackageManager.PERMISSION_GRANTED){
				btnComenzarOnClick();
			}else{

			}
		}
	}

	//	public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	    	Intent intent = new Intent(this, RequestDetailActivity.class);
//
//	    	isBackBtnPressed = true;
//
//	    	SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
//	    	intent.putExtra("idRequest", sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
//	    	intent.putExtra("type", Constants.DATABASE_ABIERTAS);
//
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}
}