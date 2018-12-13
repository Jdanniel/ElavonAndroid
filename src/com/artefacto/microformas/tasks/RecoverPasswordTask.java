package com.artefacto.microformas.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class RecoverPasswordTask extends AsyncTask<String, Void, Void>
{
    public RecoverPasswordTask(Activity activity, ProgressDialog progressDialog)
    {
        mActivity = activity;
        mDialog = progressDialog;
        mDialog.setMessage("Recuperando...");
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
            String username = args[0];
            resultBean = HTTPConnections.verifyUser(username);

            int isCorrect = Integer.parseInt(resultBean.getMessage());
            if(isCorrect == 1)
            {
                resultBean = HTTPConnections.recoverPassword(username);
                mMessage = resultBean.getMessage().equals(username) ?
                        "Se ha enviado un mensaje a su correo electronico." : resultBean.getMessage();
            }
            else
            {
                mMessage = "Usuario invalido.";
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
        Toast.makeText(mActivity, mMessage, Toast.LENGTH_SHORT).show();
    }

    private Activity mActivity;

    private ProgressDialog mDialog;

    private String mMessage;
}
