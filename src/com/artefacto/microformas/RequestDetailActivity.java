 package com.artefacto.microformas;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.ChangeStatusConnTask;
import com.artefacto.microformas.tasks.CheckClosureTask;
import com.artefacto.microformas.tasks.GetInformacionCierreTask;
import com.artefacto.microformas.tasks.GetPDFConnTask;
import com.artefacto.microformas.tasks.InformacionCierreBean;
import com.artefacto.microformas.tasks.LogComentariosTask;
import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.DescripcionTrabajoBean;
import com.artefacto.microformas.beans.LogComentariosBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.ValidateClosureBean;
import com.artefacto.microformas.beans.ValidateRejectClosureBean;
import com.artefacto.microformas.constants.Constants;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RequestDetailActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cerradas_detail);

		Intent intent = getIntent();
		idRequest = intent.getStringExtra("idRequest");
		type = intent.getIntExtra("type", 0);
		idaCliente = intent.getStringExtra("idcliente");

		SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		if (type == Constants.DATABASE_NUEVAS) {
			editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_NUEVAS_DETAIL_ACTIVITY);
		} else if (type == Constants.DATABASE_ABIERTAS) {
			editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_ABIERTAS_DETAIL_ACTIVITY);
		} else if (type == Constants.DATABASE_CERRADAS) {
			editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_CERRADAS_DETAIL_ACTIVITY);
		} else if (type == Constants.DATABASE_PENDIENTES) {
			editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_PENDIENTES_DETAIL_ACTIVITY);
		} else if (type == Constants.DATABASE_DOCUMENTS) {
			editor.putString(Constants.PREF_ACTIVITY_CHECKER, Constants.PREF_DOCUMENTS_DETAIL_ACTIVITY);
		}

		editor.putString(Constants.PREF_LAST_REQUEST_VISITED, idRequest);
		editor.commit();

		sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		Editor ed;
		if (sharedPreferences.contains(Constants.RESPONSE_PDF_CLOSE+"_"+idRequest)){
			String valorPdf = sharedPreferences.getString(Constants.RESPONSE_PDF_CLOSE+"_"+idRequest,"");
			if (valorPdf.equals("1")){
				validateSheet = true;
			}
		}

		mToolbar = (Toolbar) this.findViewById(R.id.request_list_detail_toolbar);
		mToolbarTitle = (TextView) this.findViewById(R.id.request_list_detail_toolbar_title);
		setTitle("");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title;
		switch (type)
		{
			case Constants.DATABASE_NUEVAS:
				title = "Nuevas";
				break;

			case Constants.DATABASE_ABIERTAS:
				title = "Abiertas";
				break;

			case Constants.DATABASE_CERRADAS:
				title = "Cerradas";
				break;

			case Constants.DATABASE_DOCUMENTS:
				title = "Documentos";
				break;

			default:
				title = "Pendientes";
				break;
		}
		mToolbarTitle.setText(title);

        statusDescription		= "";
        TextView noArTextView 			= findViewById(R.id.noArTextViewContent);
        statusTextView 					= findViewById(R.id.statusTextViewContent);
        TextView descClienteTextView 	= findViewById(R.id.descClienteTextViewContent);
        TextView noAfiliacionTextView	= findViewById(R.id.noAfiliacionTextViewContent);
        TextView fecAltaTextView		= findViewById(R.id.fecAltaTextViewContent);
        TextView fecGarantiaTextView	= findViewById(R.id.fecGarantiaTextViewContent);
        TextView descServicioTextView 	= findViewById(R.id.descServicioTextViewContent);
        TextView sintomaTextView 		= findViewById(R.id.sintomaTextViewContent);
        TextView bitacoraTextView		= findViewById(R.id.bitacoraTextViewContent);
        TextView descEquipoTextView		= findViewById(R.id.descEquipoTextViewContent);
        TextView direccionTextView		= findViewById(R.id.direccionTextViewContent);
        TextView descNegocioTextView 	= findViewById(R.id.descNegocioTextViewContent);
        final TextView telefonoTextView		= findViewById(R.id.telefonoTextViewContent);
        TextView fecAtencionTextView	= findViewById(R.id.fecAtencionTextViewContent);
        TextView noSerieTextView		= findViewById(R.id.noSerieTextViewContent);
        TextView fecCierreTextView		= findViewById(R.id.fecCierreTextViewContent);
		TextView textSoftware			= findViewById(R.id.descSoftwareTextViewContent);
		TextView textConnectivity		= findViewById(R.id.descConnTextViewContent);
        ImageView lightImage			= findViewById(R.id.lightImage);

        // FIELD SIM AND CLAVE_RECHAZO \( '.'   )
        TextView noSim = (TextView) this.findViewById(R.id.textViewDetailSim);
        TextView claveRechazo = (TextView) this.findViewById(R.id.textViewDetailClaveRechazo);

        SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
        SQLiteDatabase 	db 				= sqliteHelper.getReadableDB();

        String[] campos = new String[] {SQLiteHelper.REQUESTS_NO_AR,
        								SQLiteHelper.STATUS_ID_STATUS,
        								SQLiteHelper.REQUESTS_DESC_CLIENTE,
        								SQLiteHelper.REQUESTS_NO_AFILIACION,
        								SQLiteHelper.REQUESTS_FEC_ALTA,
        								SQLiteHelper.REQUESTS_FEC_GARANTIA,
        								SQLiteHelper.REQUESTS_DESC_SERVICIO,
        								SQLiteHelper.REQUESTS_SINTOMA,
        								SQLiteHelper.REQUESTS_BITACORA,
        								SQLiteHelper.REQUESTS_NOTAS_REMEDY,
        								SQLiteHelper.REQUESTS_DESC_EQUIPO,
        								SQLiteHelper.REQUESTS_EQUIPO,
        								SQLiteHelper.REQUESTS_DIRECCION,
        								SQLiteHelper.REQUESTS_DESC_NEGOCIO,
        								SQLiteHelper.REQUESTS_TELEFONO,
        								SQLiteHelper.REQUESTS_FEC_ATENCION,
        								SQLiteHelper.REQUESTS_ID_PRODUCTO,
        								SQLiteHelper.CLIENTES_ID_CLIENTE,
        								SQLiteHelper.REQUESTS_HORAS_GARANTIA,
        								SQLiteHelper.REQUESTS_HORAS_ATENCION,
        								SQLiteHelper.REQUESTS_ID_NEGOCIO,
        								SQLiteHelper.REQUESTS_NO_SERIE,
        								SQLiteHelper.REQUESTS_FEC_CIERRE,
        								SQLiteHelper.REQUESTS_PREFACTURACION,
        								SQLiteHelper.REQUESTS_COLONIA,
        								SQLiteHelper.REQUESTS_POBLACION,
        								SQLiteHelper.REQUESTS_ESTADO,
        								SQLiteHelper.REQUESTS_CP,
        								SQLiteHelper.REQUESTS_COMENTARIO,
        								SQLiteHelper.REQUESTS_DESC_CORTA,
        								SQLiteHelper.REQUESTS_NO_SIM, // 30
        								SQLiteHelper.REQUESTS_CLAVE_RECHAZO,//31
                                        SQLiteHelper.REQUESTS_DESC_SOFTWARE,//32
                                        SQLiteHelper.REQUESTS_DESC_CONN,//33
        								SQLiteHelper.REQUESTS_ID_SERVICIO//34
        };
        String[] args 	= new String[] {idRequest};

        //Adquiere la cantidad de registros que tiene un módulo
        Cursor c = db.query(SQLiteHelper.REQUESTS_DB_NAME, campos, SQLiteHelper.REQUESTS_ID_REQUEST + "=?", args, null, null, null);

        try{
        	if (c != null && c.getCount() > 0) {
        		if  (c.moveToFirst()) {
        			do {
        				noAr = c.getString(0);
        				noArTextView.setText(c.getString(0));
        				statusDescription = c.getString(1);
        		        descClienteTextView.setText(c.getString(2));
        		        noAfiliacionTextView.setText(c.getString(3));
        		        fecAltaTextView.setText(c.getString(4));
        		        fecGarantiaTextView.setText(c.getString(5));
        		        descServicioTextView.setText(c.getString(6));
        		        sintomaTextView.setText(c.getString(7));
        		        bitacoraTextView.setText(c.getString(8) + c.getString(9) + "/" + c.getString(29));
        		        descEquipoTextView.setText(c.getString(10) + " " + c.getString(11));
        		        direccionTextView.setText(c.getString(12) + ", " + c.getString(24) + ", " + c.getString(25) + ", " + c.getString(26) + ", " + c.getString(27));
        		        descNegocioTextView.setText(c.getString(13));
        		        telefonoTextView.setText(c.getString(14));
        		        fecAtencionTextView.setText(c.getString(15));
        		        noSim.setText(c.getString(30));
        		        claveRechazo.setText(c.getString(31));
                        textSoftware.setText(c.getString(32));
                        textConnectivity.setText(c.getString(33));
						idServicio = c.getString(34);
						/*------------------------Cambio 03/08/2017---------------------------*/
						telefonoTextView.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {

								String getUri = telefonoTextView.getText().toString();
								goToPhone(getUri);
							}
						});

        				statusInicial 	= statusDescription;
        				idProducto		= c.getString(16);
        				idCliente		= c.getString(17);

        		        int horasGarantia = Integer.valueOf(c.getString(18));
        				int horasAtencion = Integer.valueOf(c.getString(19));

        				idNegocio = c.getString(20);

        				int fechaDifference = horasGarantia - horasAtencion;
        				if(fechaDifference > 2)
        					lightImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.green_button));
        				else if(fechaDifference > 2)
        					lightImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yellow_button));
        				else
        					lightImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.red_button));

        				noSerieTextView.setText(c.getString(21));
        		        fechaCierre=c.getString(22);
        		        fecCierreTextView.setText(fechaCierre);
        		        vComment = c.getString(28);
        		        prefacturacion = c.getString(23);
        			} while(c.moveToNext());
        		}
        	}
        }
        catch(Exception e){
        }
        c.close();

        //Adquiere la descripcion del status (Ciclo FOR utilizando el A rrayList
        campos = new String[] {SQLiteHelper.STATUS_DESC_STATUS, SQLiteHelper.STATUS_MOBILE};
        args 	= new String[] {statusDescription};
        c = db.query(SQLiteHelper.STATUS_DB_NAME, campos, SQLiteHelper.STATUS_ID_STATUS + "=?", args, null, null, null);

        try{
        	if (c != null ) {
        		if  (c.moveToFirst()) {
        			do {
        				statusTextView.setText(c.getString(0));
						statusMobile = c.getString(1);
        			}while (c.moveToNext());
        		}
        	}
        }
        catch(Exception e){
        }
        c.close();

		campos = new String[] {SQLiteHelper.CLIENTS_MOBILE};
		args 	= new String[] {idCliente};
		c = db.query(SQLiteHelper.CLIENTS_DB_NAME, campos, SQLiteHelper.CLIENTS_ID_CLIENT + "=?", args, null, null, null);

		try{
			if (c != null ) {
				if  (c.moveToFirst()) {
					do {
						clienteMobile = c.getString(0);
					}while (c.moveToNext());
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		c.close();

		/*/*------------------------------Inicia cambio 03/08/2017------------------------------------------*/
		/**
		 * Lectura de c_servicios
		 */
		campos = new String[]{
				sqliteHelper.SERVICES_NEED_SHEET
		};
		args 	= new String[] {idServicio};

		c = db.query(sqliteHelper.SERVICES_DB_NAME, campos, sqliteHelper.SERVICES_ID_SERVICIO + "=?", args, null, null, null);

		try{
			if (c != null){
				if  (c.moveToFirst()) {
					do {
						needSheet = c.getString(0);
					} while (c.moveToNext());
				}
			}
		}catch (Exception e){
		}
		c.close();
		db.close();
		myAnnounce = new Dialog(this);

		if(clienteMobile.equals("1") && statusMobile.equals("1")){
			ShowPopup();
		}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CLOSE_SUCCESS || requestCode == REQUEST_CLOSE_DENIED)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	if(type == 0)
        {
    		//menu.add(Menu.NONE, 1, Menu.NONE, R.string.request_detail_approve).setIcon(R.drawable.approved);
    		menu.add(Menu.NONE, 1, Menu.NONE, R.string.request_detail_approve);
    		menu.add(Menu.NONE, 2, Menu.NONE, R.string.request_detail_refuse);
    	}

    	if(type == 1)
        {
    		SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(this, null);
    		SQLiteDatabase 	db 		= sqliteHelper.getReadableDB();
            Cursor c = sqliteHelper.getDisplayStatus(idCliente, statusInicial, idProducto, db);
            statusList = new ArrayList<String>();

            try{
            	if (c != null ) {
            		if  (c.moveToFirst()) {
            			do {
            				statusList.add(c.getString(0));
            			}while (c.moveToNext());
            		}
            	}
            }
            catch(Exception e){
            }
            c.close();

            String[] args 	= new String[statusList.size()];
            //Llena el arrayList
            for(int i = 0; i < statusList.size(); i++){
            	args[i] = statusList.get(i).toString();
            }

            String[] campos = new String[] {SQLiteHelper.STATUS_DESC_STATUS};
            statusDetailList = new ArrayList<CharSequence>();
            //Adquiere la descripción de cada status a cambiar
            for(int i = 0; i < statusList.size(); i++){
            	String args2[] = new String[]{args[i]};
            	 c = db.query(SQLiteHelper.STATUS_DB_NAME, campos, SQLiteHelper.STATUS_ID_STATUS + "=?", args2, null, null, null);

                 try{
                 	if (c != null ) {
                 		if  (c.moveToFirst()) {
                 			do {
                 				statusDetailList.add(c.getString(0));
                 			}while (c.moveToNext());
                 		}
                 	}
                 }
                 catch(Exception e){
                 }
                 c.close();
            }

            db.close();
			//Log.e("Number", String.valueOf(statusDetailList.size()));
    		//Crea el submenú con los status disponibles a cambiar
            if(statusDetailList.size() > 0){
            	SubMenu sub = menu.addSubMenu(0,10,0, R.string.request_detail_status);
        		for(int i=0; i < statusDetailList.size(); i++){
        			sub.add(0, (11 + i), 0, statusDetailList.get(i));
        		}
            }

            if(statusDescription.equals(Constants.STATUS_PENDIENTE_VIATICOS)){
    			menu.add(Menu.NONE, 21, Menu.NONE, R.string.pendiente_viatico);
    		}

            if(statusDescription.equals(Constants.STATUS_PENDIENTE_REFACCION)){
    			menu.add(Menu.NONE, 22, Menu.NONE, R.string.pendiente_refaccion);
    		}

            if(statusDescription.equals(Constants.STATUS_EN_SITIO)){
				if (!needSheet.equals("1") || validateSheet){
					menu.add(Menu.NONE, 6, Menu.NONE, R.string.request_detail_success);
					menu.add(Menu.NONE, 7, Menu.NONE, R.string.request_detail_reject);
				}else{
					menu.add(Menu.NONE, 16, Menu.NONE, "Enviar PDF");
				}
                menu.add(Menu.NONE, 8, Menu.NONE, R.string.request_detail_inventario);
            }

            if(statusDescription.equals(Constants.STATUS_PENDIENTE_TERMINAL)){
    			menu.add(Menu.NONE, 31, Menu.NONE, R.string.pendiente_terminal);
    		}

            if(statusDescription.equals(Constants.STATUS_PENDIENTE_SPAREPARTS)){
    			menu.add(Menu.NONE, 32, Menu.NONE, R.string.pendiente_spareparts);
    		}

            if(statusDescription.equals(Constants.STATUS_PENDIENTE_INSUMO)){
    			menu.add(Menu.NONE, 33, Menu.NONE, R.string.pendiente_insumos);
    		}

            menu.add(Menu.NONE, 2, Menu.NONE, R.string.request_detail_comment);
            menu.add(Menu.NONE, 4, Menu.NONE, R.string.request_detail_location);
            menu.add(Menu.NONE, 5, Menu.NONE, R.string.request_detail_vercomentario);
    	}

    	if(type == 2){
    		menu.add(Menu.NONE, 1, Menu.NONE, R.string.request_detail_close);
    	}

    	if(type == Constants.DATABASE_DOCUMENTS){
    		menu.add(Menu.NONE, 15, Menu.NONE, "Ver PDF");

    		if (!prefacturacion.equals("2")) {
    			menu.add(Menu.NONE, 16, Menu.NONE, "Enviar PDF");
    		}

    		if(prefacturacion.equals("3")) {
    			menu.add(Menu.NONE, 34, Menu.NONE, "Comentario");
    		}
    	}

    	//menu.add(Menu.NONE, 3, Menu.NONE, R.string.request_detail_camera).setIcon(R.drawable.camera);
    	// OPCION TOMAR FOTO
    	menu.add(Menu.NONE, 3, Menu.NONE, R.string.request_detail_camera);
    	menu.add(Menu.NONE, 35, Menu.NONE, "Tomar Foto de Galería");
    	if(type == Constants.DATABASE_PENDIENTES || type == Constants.DATABASE_NUEVAS) {
    		menu.add(Menu.NONE, 5, Menu.NONE, R.string.request_detail_vercomentario);
    	}
    	/*if(type != Constants.DATABASE_PENDIENTES) {
    		menu.add(Menu.NONE, 3, Menu.NONE, R.string.request_detail_camera);
    	} else {
    		menu.add(Menu.NONE, 5, Menu.NONE, R.string.request_detail_vercomentario);
    	}*/

    	if(type == 3){
    		menu.add(Menu.NONE, 2, Menu.NONE, R.string.request_detail_comment);
    	}
        return true;
    }

    public void changeStatus(String confirmationMessage, final String progressMessage, final String status){
    	AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Confirmación");

		/***********************************************Inicia cambio 02/08/2017 comentario para rechazo**************************************************/
		final EditText input = new EditText(this);

		if(status == Constants.REQUEST_REFUSED){
			input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
			dialog.setView(input);
		}

		/************************************************************************************************************************************************/
        dialog.setMessage(confirmationMessage);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
				String m_text = "";

				if (status == Constants.REQUEST_REFUSED){
					m_text = input.getText().toString();
				}
            	progressDialog = new ProgressDialog(RequestDetailActivity.this);
            	progressDialog.setMessage(progressMessage);
    			progressDialog.setCancelable(false);
            	ChangeStatusConnTask ctask = new ChangeStatusConnTask(RequestDetailActivity.this, progressDialog);
            	Intent intent 	 = getIntent();
            	sharedPreferences	= getSharedPreferences("UserConfig", Context.MODE_PRIVATE);
            	ctask.execute(intent.getStringExtra("idRequest"), sharedPreferences.getString("user_id", ""), status, m_text);
			}
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {

            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(type == Constants.DATABASE_NUEVAS){
    		switch (item.getItemId()) {
            case 1:
            	/*Intent timeDateIntent = new Intent(this, TimeAndDate.class);
    			boolean accepted = false;
            	timeDateIntent.putExtra("status", accepted);
            	this.startActivityForResult(timeDateIntent, DATETIME_REQUEST);*/
    			changeStatus("¿Seguro que quieres aprobar la solicitud?",
            				 "Aprobando solicitud",
            				 Constants.REQUEST_APPROVED);
                return true;
            case 2:
            	changeStatus("¿Seguro que quieres rechazar la solicitud?",
            				 "Rechazando solicitud.",
            				 Constants.REQUEST_REFUSED);
                return true;
            case 3:
            	Intent intent = new Intent(this, PhotoTakingActivity.class);
    			intent.putExtra("type", type);
    			startActivity(intent);
    			return true;
			case 5:
				ProgressDialog progressDialog = new ProgressDialog(this);
				progressDialog.setMessage("Cargando comentarios...");
				progressDialog.setCancelable(false);

				SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
				LogComentariosTask task = new LogComentariosTask(this, progressDialog, sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
				task.execute();
				return true;
            default:
                return super.onOptionsItemSelected(item);
    		}
        }
    	if(type == Constants.DATABASE_ABIERTAS)
		{
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateobj = new Date();
            if(android.R.id.home  != item.getItemId()) {
            	if (item.getItemId() == 35){
					DateFormat df_ = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
					Date dateobj_ = new Date();
					Intent intentimg = new Intent(this,ImageBrowserActivity.class);
					intentimg.putExtra("idrequest", idRequest);
					intentimg.putExtra("fec_cierre", df_.format(dateobj_));
					intentimg.putExtra("location","1");
					startActivity(intentimg);
				}
				else if(item.getItemId() == 16){
					Intent intent = new Intent(this, FileBrowserActivity.class);
					intent.putExtra("idRequest", idRequest);
					intent.putExtra("fec_cierre", df.format(dateobj));
					intent.putExtra("location","1");
					startActivity(intent);
				}
				else if (item.getItemId() > 10) {
                    if (statusList.get(item.getItemId() - 11).equals(Constants.STATUS_PENDIENTE_VIATICOS)) {
                        Intent inte = getIntent();
                        if (!AsyncQueue.isChangeStatusPending(inte.getStringExtra("idRequest"))) {
                            Intent intent = new Intent(getApplicationContext(), ViaticosActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Esta solicitud ya tiene un cambio en proceso", Toast.LENGTH_SHORT).show();
                        }
                    } else if (statusList.get(item.getItemId() - 11).equals(Constants.STATUS_PENDIENTE_REFACCION)) {
                        Intent inte = getIntent();
                        if (!AsyncQueue.isChangeStatusPending(inte.getStringExtra("idRequest"))) {
                            Intent intent = new Intent(getApplicationContext(), RefaccionesActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Esta solicitud ya tiene un cambio en proceso", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //TODO ------------------------------Inicia bloque nuevo----------------------------------------------------------
                    else if (statusList.get(item.getItemId() - 11).equals(Constants.STATUS_PENDIENTE_TERMINAL)) {
                        Intent inte = getIntent();
                        if (!AsyncQueue.isChangeStatusPending(inte.getStringExtra("idRequest"))) {
                            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
                            Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.INSUMO_INSUMOS_ID_CLIENTE, idCliente);
							editor.putString(Constants.INSUMO_INSUMOS_ID_PRODUCT, idProducto);
                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(), TerminalActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Esta solicitud ya tiene un cambio en proceso", Toast.LENGTH_SHORT).show();
                        }
                    } else if (statusList.get(item.getItemId() - 11).equals(Constants.STATUS_PENDIENTE_SPAREPARTS)) {
                        Intent inte = getIntent();
                        if (!AsyncQueue.isChangeStatusPending(inte.getStringExtra("idRequest"))) {

                            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
                            Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.TERMINAL_ALMACENES_ID, "");
                            editor.putString(Constants.TERMINAL_ALMACENES_DESC, "(sin información)");
                            editor.putString(Constants.SPAREPART_SPAREPART_ID, "");
                            editor.putString(Constants.SPAREPART_SPAREPART_DESC, "(sin información)");
                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(), SparePartActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Esta solicitud ya tiene un cambio en proceso", Toast.LENGTH_SHORT).show();
                        }
                    } else if (statusList.get(item.getItemId() - 11).equals(Constants.STATUS_PENDIENTE_INSUMO)) {
                        Intent inte = getIntent();
                        if (!AsyncQueue.isChangeStatusPending(inte.getStringExtra("idRequest"))) {
                            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
                            Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.TERMINAL_ALMACENES_ID, "");
                            editor.putString(Constants.TERMINAL_ALMACENES_DESC, "(sin información)");
                            editor.putString(Constants.INSUMO_INSUMOS_ID, "");
                            editor.putString(Constants.INSUMO_INSUMOS_DESC, "(sin información)");
                            editor.putString(Constants.INSUMO_INSUMOS_ID_CLIENTE, idCliente);
                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(), PendienteInsumoActivity.class);
//    					intent.putExtra(Constants.EXTRA_ID_CLIENTE, idCliente);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Esta solicitud ya tiene un cambio en proceso", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //TODO ------------------------------Termina bloque nuevo----------------------------------------------------------

                    else {
                        Intent intent = getIntent();
                        if (!AsyncQueue.isChangeStatusPending(intent.getStringExtra("idRequest"))) {
                            changeStatus("¿Seguro que quieres cambiar el status de la solicitud a " + statusDetailList.get(item.getItemId() - 11) + "?",
                                    "Cambiando status de la solicitud.",
                                    statusList.get(item.getItemId() - 11));
                        } else {
                            Toast.makeText(this, "Esta solicitud ya tiene un cambio en proceso", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                } else if (item.getItemId() == 3) {
                    Intent intent = new Intent(this, PhotoTakingActivity.class);
                    intent.putExtra("type", type);
                    startActivity(intent);
                } else if (item.getItemId() == 6 || item.getItemId() == 7) { // 6 = CIERRE EXITOSO || 7 = CIERRE RECHAZO
                    progressDialog = new ProgressDialog(RequestDetailActivity.this);
                    progressDialog.setMessage(getString(R.string.rechazo_enviandopeticion_message));
                    progressDialog.setCancelable(false);
                    CheckClosureTask ctask = new CheckClosureTask(RequestDetailActivity.this, progressDialog);
                    Intent intent = getIntent();

                    if (item.getItemId() == 6) {
                        ctask.execute(intent.getStringExtra("idRequest"), Constants.SUCCESS_CLOSURE);
                    } else {
                        ctask.execute(intent.getStringExtra("idRequest"), Constants.REJECT_CLOSURE);
                    }
                } else if (item.getItemId() == 8) { //Movimientos de Inventario
                    Intent intent = new Intent(this, InventoryMovementsActivity.class);
                    intent.putExtra("noar", noAr);
                    startActivity(intent);
                }
            }
    	}
    	else if(type == Constants.DATABASE_CERRADAS){
    		if(item.getItemId() == 1){
    			progressDialog = new ProgressDialog(RequestDetailActivity.this);
            	progressDialog.setMessage("Cargando información");
    			progressDialog.setCancelable(false);
    			SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
    			GetInformacionCierreTask informationCierreTask = new GetInformacionCierreTask(RequestDetailActivity.this, progressDialog, sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
            	Intent intent 	 = getIntent();
            	informationCierreTask.execute(intent.getStringExtra(noAr));
    		}
    	}
    	else if (type == Constants.DATABASE_DOCUMENTS){
    		// VER PDF
    		if (item.getItemId() == 15){
    			progressDialog = new ProgressDialog(RequestDetailActivity.this);
            	progressDialog.setMessage("Abriendo PDF...");
    			progressDialog.setCancelable(false);
    			GetPDFConnTask getPdf = new GetPDFConnTask(progressDialog,this);
    			getPdf.execute(idRequest,fechaCierre);
    		}// ENVIAR PDF
    		else if (item.getItemId() == 16){
    			Intent intent = new Intent(this, FileBrowserActivity.class);
    			intent.putExtra("idRequest", idRequest);
    			intent.putExtra("fec_cierre", fechaCierre);
    			startActivity(intent);
    		} else if(item.getItemId() == 34) {
    			// TOMAR FOTO
    			AlertDialog.Builder builder = new AlertDialog.Builder(this);
    			builder.setMessage(vComment).setTitle("Comentario");
    			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
    			AlertDialog dialog = builder.create();
    			dialog.show();
    		}
    	}

    	else if(type == Constants.DATABASE_PENDIENTES){
    		if(item.getItemId() == 3){
    			Intent intent = new Intent(this, PhotoTakingActivity.class);
    			intent.putExtra("type", type);
    			startActivity(intent);
    		}
    	}

    	if(item.getItemId() == 2){
    		Intent intent = new Intent(this, CommentActivity.class);
    		intent.putExtra("type", type);
        	startActivity(intent);
    	}

    	if(item.getItemId() == 5){

    		ProgressDialog progressDialog = new ProgressDialog(this);
        	progressDialog.setMessage("Cargando comentarios...");
        	progressDialog.setCancelable(false);

        	SharedPreferences sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
    		LogComentariosTask task = new LogComentariosTask(this, progressDialog, sharedPreferences.getString(Constants.PREF_LAST_REQUEST_VISITED, ""));
    		task.execute();
    	}

    	//Localización
    	if(item.getItemId() == 4){
    		Intent intent = new Intent(this, GeoLocalizacionActivity.class);
    		intent.putExtra("Id_Negocio", idNegocio);
    		intent.putExtra("type", type);
        	startActivity(intent);
    	}

        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }

        return true;
    }

    public void verComentarios(ArrayList<LogComentariosBean> logComentariosBeanArray){
    	Intent intent = new Intent(this, LogComentarioActivity.class);
    	intent.putExtra("bean", logComentariosBeanArray);
    	startActivity(intent);
    }

    public void sendStatusAnswer(int success, int type, NotificationsUpdateBean notificationsUpdateBean){
    	String text = "Sin red, se intentará mas tarde."; //Valor inicial, considerando que haya error
    	if(success == 1){
    		if(type == 0){
    			text = "¡Solicitud aprobada!";
    		}
    		else if(type == 1){
    			text = "Solicitud rechazada";
    		}
    		else{
    			text = "¡Status modificado!";
    		}

    	}else{
    		if (type == 3){
    			text = "Esta solicitud ya tiene un cambio en proceso";
    		}
    	}

		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    	goToNextScreen(type);
    }

    //Envía la respuesta al intentar cerrar por exito
    public void sendValidationAnswer(int success, ValidateClosureBean validateClosureBean,
        ArrayList<DescripcionTrabajoBean> descripcionTrabajoBean)
    {
    	switch(success)
        {
            case Constants.VALIDATE_NUM_TRUE:
                Intent intent = new Intent(this, CierreActivity.class);
                intent.putExtra("bean", validateClosureBean);
                intent.putExtra("descbean", descripcionTrabajoBean);
                startActivityForResult(intent, REQUEST_CLOSE_SUCCESS);
            break;

            case Constants.VALIDATE_NUM_INSTALACION: 	intent = new Intent(this, InstalacionActivity.class);
                                                        intent.putExtra("Get_Info", true);
                                                        startActivity(intent);
                                                        break;
            case Constants.VALIDATE_NUM_INSUMO: intent = new Intent(this, InventoryMovementSupplyActivity.class);
                                                startActivity(intent);
                                                break;
            case Constants.VALIDATE_NUM_RETIRO: intent = new Intent(this, RetiroActivity.class);
                                                startActivity(intent);
                                                break;
            case Constants.VALIDATE_NUM_SUSTITUCION: 	intent = new Intent(this, SustitucionesActivity.class);
                                                        startActivity(intent);
                                                        break;
            //  NEW CASE OF ERROR (17-10-2014)
            case Constants.VALIDATE_NUM_FILE:
                Toast.makeText(getApplicationContext(), "El servicio debe tener al menos un archivo o foto.", Toast.LENGTH_LONG).show();
            break;

            default:
                Toast.makeText( getApplicationContext(), "No se ha validado el cierre", Toast.LENGTH_SHORT).show();
                    break;
    	}
    }

    //Envía la respuesta al intentar cerrar por rechazo
    public void sendValidationAnswer(int success, ValidateRejectClosureBean validateRejectClosureBean){
    	switch(success){
    	case Constants.VALIDATE_NUM_TRUE: 	Intent intent = new Intent(this, CierreRechazoActivity.class);
    										intent.putExtra("bean", validateRejectClosureBean);
    										startActivityForResult(intent, REQUEST_CLOSE_DENIED);
    										break;
    	case Constants.VALIDATE_NUM_INSTALACION: 	intent = new Intent(this, InstalacionActivity.class);
    												intent.putExtra("Get_Info", true);
    												startActivity(intent);
													break;
    	case Constants.VALIDATE_NUM_INSUMO: intent = new Intent(this, InventoryMovementSupplyActivity.class);
											startActivity(intent);
											break;
    	case Constants.VALIDATE_NUM_RETIRO: intent = new Intent(this, RetiroActivity.class);
											startActivity(intent);
											break;
    	case Constants.VALIDATE_NUM_SUSTITUCION: 	intent = new Intent(this, SustitucionesActivity.class);
													startActivity(intent);
													break;
		//  NEW CASE OF ERROR (17-10-2014)
    	case Constants.VALIDATE_NUM_FILE:
    		Toast.makeText(getApplicationContext(), "El servicio debe tener al menos un archivo o foto.", Toast.LENGTH_LONG).show();
    	break;

    	default: Toast.makeText(getApplicationContext(), "No se ha validado el cierre " + success, Toast.LENGTH_SHORT).show();
    			break;
    	}
    }

    public void setInformacionCierre(InformacionCierreBean informacionCierreBean){
    	if(informacionCierreBean.getRes().equals("OK")){
    		Intent intent = new Intent(this, InformacionCierreActivity.class);
    		intent.putExtra("bean", informacionCierreBean);
    		intent.putExtra("noar", noAr);
    		startActivity(intent);
    	}
    	else
    		Toast.makeText(getApplicationContext(), "Error: " + informacionCierreBean.getDesc(), Toast.LENGTH_SHORT).show();
    }

    //Si se aprueba la solicitud, se
    public void goToNextScreen(int type)
    {
    	Intent 	intent	= new Intent(this, RequestListActivity.class);
    	if(type == 1 )
    		intent.putExtra("type", Constants.DATABASE_NUEVAS);

    	else
    		intent.putExtra("type", Constants.DATABASE_ABIERTAS);
    	startActivity(intent);
    }

    //redireccionar telefono
	public void goToPhone(String uri){
		if(uri.toString().equals("")){
			Toast.makeText(getApplicationContext(), "No hay número.", Toast.LENGTH_SHORT).show();
		}else{
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:"+uri));
			startActivity(intent);
		}
	}

	public void ShowPopup(){
		TextView txtclose;
		Button btOk;
		myAnnounce.setContentView(R.layout.anunciolayout2);
		txtclose = (TextView) myAnnounce.findViewById(R.id.idClose);
		btOk = (Button) myAnnounce.findViewById(R.id.idok);
		txtclose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myAnnounce.dismiss();
			}
		});

		myAnnounce.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		myAnnounce.show();
	}

	private static final int DATETIME_REQUEST = 1567;
    public static int REQUEST_CLOSE_SUCCESS = 654;
    public static int REQUEST_CLOSE_DENIED = 655;

	private Toolbar mToolbar;
	private TextView mToolbarTitle;
	private Dialog myAnnounce;

	ProgressDialog 		progressDialog;
	SharedPreferences sharedPreferences;

	boolean validateSheet = false;
	int 	type 		= 0;
	String fechaCierre;
	String statusMobile;
	String clienteMobile;
	String idCliente;
	String idaCliente;
	String idNegocio;
	String idProducto;
	String idRequest 	= "";
	String idServicio;
	String statusDescription;
	String statusInicial;
	String needSheet;
	String noAr;
	String prefacturacion;
	String vComment;

	TextView statusTextView;

	ArrayList<String> statusSubMenu;
	ArrayList<String> statusList;
	ArrayList<CharSequence> statusDetailList;
}