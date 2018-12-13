package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.NewShipmentSearchSuppliesActivity;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class ValidateSupplyCountTask extends AsyncTask<String, Void, Void>
{
    public ValidateSupplyCountTask(Context context, ProgressDialog progressDialog)
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
            String idSupply = args[0];
            String idUser = args[1];
            String count = args[2];

            resultBean = HTTPConnections.validateSupply(idSupply, idUser, count);
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
            NewShipmentSearchSuppliesActivity.isInserted = true;
            NewShipmentSearchSuppliesActivity activity = (NewShipmentSearchSuppliesActivity) mContext;
            activity.onBackPressed();
        }
    }

    private Context mContext;

    private ProgressDialog mDialog;

    private String mMessage;
}
