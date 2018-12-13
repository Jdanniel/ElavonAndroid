package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.NewShipmentActivity;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class NewShipmentTask extends AsyncTask<String, Void, Void>
{
    public NewShipmentTask(Context context, ProgressDialog dialog)
    {
        mContext = context;
        mDialog = dialog;
        mDialog.setMessage("Enviando...");
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mDialog.show();
    }

    @Override
    protected Void doInBackground(String... args)
    {
        ResultBean resultBean = null;

        try
        {
            String idUser = args[0];
            String idTypeDestination = args[1];
            String idDestination = args[2];
            String idMessaging = args[3];
            String idPriority = args[4];
            String guideNum = args[5];
            String dataUnits = args[6];
            String dataSupplies = args[7];

            resultBean = HTTPConnections.insertNewShipment(idUser, idTypeDestination, idDestination, idMessaging,
                    idPriority, guideNum, dataUnits, dataSupplies);
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
        super.onPostExecute(aVoid);
        mDialog.dismiss();
        if(!mMessage.equals(""))
        {
            Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mContext, "Envío exitoso", Toast.LENGTH_SHORT).show();

            NewShipmentActivity activity = (NewShipmentActivity) mContext;
            activity.onBackPressed();
        }
    }

    private Context mContext;

    private ProgressDialog mDialog;

    private String mMessage;
}
