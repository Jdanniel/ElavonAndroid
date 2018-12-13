package com.artefacto.microformas.tasks;

import com.artefacto.microformas.InsertNewMovementSupplyActivity;
import com.artefacto.microformas.InventoryMovementSupplyActivity;
import com.artefacto.microformas.InventoryMovementsActivity;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class InsumoConnTask extends AsyncTask<String, Void, Integer>
{
	private Activity activity;
	private ProgressDialog progressDialog;

	private String idAR, idTecnico, idInsumo, cantidad, accion, idARInsumo;

	private int success;
	private String status;
	
	public InsumoConnTask(String idAR, String idTecnico, String idInsumo,
			String cantidad, String accion, String idARInsumo)
    {
		super();
		this.idAR = idAR;
		this.idTecnico = idTecnico;
		this.idInsumo = idInsumo;
		this.cantidad = cantidad;
		this.accion = accion;
		this.idARInsumo = idARInsumo;
	}
	
	

	public InsumoConnTask(Activity activity, ProgressDialog progressDialog)
    {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute()
    {
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params)
    {

		this.idAR = params[0];
		this.idTecnico = params[1];
		this.idInsumo = params[2];
		this.cantidad = params[3];
		this.accion = params[4];
		this.idARInsumo = params[5];
		
		insertarInsumo();
		
		return success;
	}
	
	public Integer insertarInsumo()
	{
		String res = HTTPConnections.InsertarInsumo(idAR,idTecnico,idInsumo,cantidad,accion,idARInsumo);
		success = 0;
		if(res.equals("OK")){
			success=1;
			status = "Enviado con éxito";
		}else{
			status = res;
		}
		return success;
	}

	@Override
	protected void onPostExecute(Integer headerCode)
	{
		progressDialog.dismiss();
		Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();

        if(activity instanceof InsertNewMovementSupplyActivity)
        {
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
        else if(activity instanceof InventoryMovementSupplyActivity)
        {
            Intent intent = activity.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.finish();
            activity.overridePendingTransition(0, 0);

            activity.startActivity(intent);
            activity.overridePendingTransition(0, 0);

			((InventoryMovementSupplyActivity) activity).updateInvSupplies();
        }

//		activity.finish();
//
//		Intent intent = new Intent(activity, InventoryMovementSupplyActivity.class);
//		activity.startActivity(intent);
	}
}
