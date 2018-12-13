package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.NewShipmentSearchActivity;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class ValidateUnitTask extends AsyncTask<String, Void, Void>
{
    public ValidateUnitTask(Context context, ProgressDialog progressDialog)
    {
        mContext = context;
        mDialog = progressDialog;
        mDialog.setMessage("Validando...");
    }

    @Override
    protected void onPreExecute()
    {
        mDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... args)
    {
        ResultBean resultBean = null;

        try
        {
            String idUnit = args[0];
            String idUser = args[1];

            resultBean = HTTPConnections.validateUnit(idUnit, idUser);
            mMessage = "";
            if(!resultBean.getMessage().equals(HTTPConnections.REQUEST_OK))
            {
                mMessage = resultBean.getMessage();
            }
        }
        catch(NumberFormatException exNumber)
        {
            if(resultBean != null)
            {
                mMessage = resultBean.getMessage();
            }
        }
        catch (Exception ex)
        {
            mMessage = ex.getMessage();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        mDialog.dismiss();
        if(!mMessage.equals(""))
        {
            Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
        }
        else
        {
            NewShipmentSearchActivity activity = (NewShipmentSearchActivity) mContext;
            activity.onBackPressed();
        }
    }

    private Context mContext;

    private ProgressDialog mDialog;

    private String mMessage;
}
