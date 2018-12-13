package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.artefacto.microformas.LookForSuppliesActivity;
import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.connection.HTTPConnections;

import java.util.ArrayList;

public class SearchClientSupplies extends AsyncTask<String, Void, Void>
{
    public SearchClientSupplies(LookForSuppliesActivity activity, ProgressDialog pgDialog)
    {
        mActivity = activity;
        mProgressDialog = pgDialog;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(String... params)
    {
        mSuppliesBean = new ArrayList<>();

        String idResponsible = params[0];
        String idCliente = params[1];

        Object result = HTTPConnections.getClientSupplies(idResponsible, idCliente);
        if(result instanceof ArrayList<?>)
        {
            mSuppliesBean = (ArrayList<SupplyBean>) result;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        mProgressDialog.dismiss();
        mActivity.setClientSuppliesBean(mSuppliesBean);
    }

    private LookForSuppliesActivity mActivity;

    private ProgressDialog mProgressDialog;

    private ArrayList<SupplyBean> mSuppliesBean;
}
