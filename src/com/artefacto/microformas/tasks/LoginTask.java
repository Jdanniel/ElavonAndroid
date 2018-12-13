package com.artefacto.microformas.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.artefacto.microformas.LoginActivity;
import com.artefacto.microformas.TermsActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class LoginTask extends AsyncTask<String, Void, Void>
{
    public LoginTask(Activity activity, ProgressDialog dialog)
    {
		this.mActivity = activity;
		this.mDialog = dialog;
	}
	
	@Override
	protected void onPreExecute()
    {
		mDialog.show();
	}

	@Override
	protected Void doInBackground(String... args)
    {
        try
        {
            String user = args[0];
            String password = args[1];
            boolean isIdUser = Boolean.parseBoolean(args[2]);

            mResultBean = HTTPConnections.loginUser(user, password, isIdUser);
            mMessage = "";
            if(MicroformasApp.isNumber(mResultBean.getMessage()))
            {
                mIdUser = mResultBean.getMessage();
            }
            else
            {
                mMessage = mResultBean.getMessage();
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
            Toast.makeText(mActivity, mMessage, Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(mActivity instanceof LoginActivity)
            {
                //((LoginActivity) mActivity).goToFirstScreenActivity(mIdUser);
                ((LoginActivity) mActivity).updatePhoneInfo(mIdUser);
            }
            else if(mActivity instanceof TermsActivity)
            {
                ((TermsActivity) mActivity).userChecked();
            }
        }
    }

    static NotificationsUpdateBean notificationsUpdateBean;

    private ProgressDialog mDialog;
    private Activity mActivity;

    private ResultBean mResultBean;

    private String mMessage;
    private String mIdUser;
}