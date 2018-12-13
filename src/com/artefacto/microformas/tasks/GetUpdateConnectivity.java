package com.artefacto.microformas.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.artefacto.microformas.AgregarUnidadNegocioActivity;
import com.artefacto.microformas.beans.ConnectivityBean;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

import java.util.ArrayList;

public class GetUpdateConnectivity extends AsyncTask<String, Void, Void>
{
    public GetUpdateConnectivity(Activity activity, ProgressDialog progressDialog)
    {
        this.mProgressDialog = progressDialog;
        this.mActivity = activity;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        String idModel = params[0];
        String idClient = params[1];

        Object response = HTTPConnections.getUpdateConnectivity(idModel, idClient);
        if(response instanceof ArrayList<?>)
        {
            listBean = (ArrayList<ConnectivityBean>) response;
        }
        else
        {
            listBean = null;
            ResultBean resultBean = (ResultBean) response;
            Log.d("Microformas", resultBean.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        mProgressDialog.dismiss();

        AgregarUnidadNegocioActivity myAddActivity = (AgregarUnidadNegocioActivity) mActivity;
        myAddActivity.InitSpinnerConnectivity(listBean);

        super.onPostExecute(aVoid);
    }

    private ArrayList<ConnectivityBean> listBean;

    private ProgressDialog mProgressDialog;

    private Activity mActivity;
}
