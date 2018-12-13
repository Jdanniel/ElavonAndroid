package com.artefacto.microformas.tasks;

import com.artefacto.microformas.InventoryMovementSupplyActivity;
import com.artefacto.microformas.beans.InfoInsumoBean;
import com.artefacto.microformas.connection.HTTPConnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class InfoInsumoConnTask extends AsyncTask<String,Void, Integer>{
	private InventoryMovementSupplyActivity activity;
	private ProgressDialog progressDialog;
	
	String idAR;
	String message;
	int status;
	InfoInsumoBean infoInsumoBean;
	
	public InfoInsumoConnTask(InventoryMovementSupplyActivity activity, ProgressDialog progressDialog,
			String idAR)
	{
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.idAR = idAR;
	}
	
	@Override
	protected void onPreExecute()
    {
		progressDialog.show();
	}
	

	@Override
	protected Integer doInBackground(String... params)
    {
		infoInsumoBean = HTTPConnections.obtainInfoInsumo(idAR);

        status = infoInsumoBean.getStatusRet();
		switch(status)
        {
			case 1:
                message = "Puede Continuar";
                break;

			default:
                message = "No se pudo cargar la información, intente más tarde";
				break;
		}

		return 0;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode)
    {
		progressDialog.dismiss();
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

        if (status == 1)
        {
			activity.setInfo(infoInsumoBean);
		}
		else
        {
			activity.finish();
		}
	}
}
