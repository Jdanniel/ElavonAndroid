package com.artefacto.microformas.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.artefacto.microformas.AgregarUnidadNegocioActivity;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.beans.SoftwareBean;
import com.artefacto.microformas.connection.HTTPConnections;

import java.util.ArrayList;

public class GetUpdateSoftware extends AsyncTask<String, Void, Void>
{
    public GetUpdateSoftware(Activity activity, ProgressDialog progressDialog)
    {
        this.mProgressDialog = progressDialog;
        this.mActivity = activity;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        String idConn = params[0];
        String idClient = params[1];

        Object response = HTTPConnections.getUpdateSoftware(idConn, idClient);
        if(response instanceof ArrayList<?>)
        {
            listBean = (ArrayList<SoftwareBean>) response;
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
        myAddActivity.InitSpinnerSoftware(listBean);

        super.onPostExecute(aVoid);
    }

    private ArrayList<SoftwareBean> listBean;

    private ProgressDialog mProgressDialog;

    private Activity mActivity;
}
