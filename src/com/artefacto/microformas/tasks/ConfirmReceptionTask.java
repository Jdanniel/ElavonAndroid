package com.artefacto.microformas.tasks;

import com.artefacto.microformas.ReceptionActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.transactions.DataBaseTransactions;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

public class ConfirmReceptionTask extends AsyncTask<String,Void, Void>
{
	public ConfirmReceptionTask(ReceptionActivity activity, ProgressDialog dialog, String idSent,
                                String idResponsible, ArrayList<UnitBean> listUnits, ArrayList<SupplyBean> listSupplies)
    {
		this.mActivity = activity;
		this.mDialog = dialog;
		this.idSent = idSent;
		this.idResponsible = idResponsible;
		this.mUnits = listUnits;
        this.mSupplies = listSupplies;
	}
	
	@Override
	protected void onPreExecute()
    {
        mDialog.show();
	}

	@Override
	protected Void doInBackground(String... params)
    {
        String units = "";
        for(int i = 0; i < mUnits.size(); i++)
        {
            units += mUnits.get(i).getId() + ((i == mUnits.size() - 1) ? "" : ",");
        }

        String supplies = "";
        for(int i = 0; i < mSupplies.size(); i++)
        {
            supplies += mSupplies.get(i).getIdSupply() + ((i == mSupplies.size() - 1) ? "" : ",");
        }

		ResultBean result = HTTPConnections.confirmReception(idSent, idResponsible, units, supplies);
        mMessage = result.getMessage();
        return null;
	} 
	
	@Override
	protected void onPostExecute(Void aVoid)
	{
        super.onPostExecute(aVoid);
        mDialog.dismiss();

        if(mMessage.equals(HTTPConnections.REQUEST_OK))
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        NotificationsUpdateBean notificationsUpdateBean = HTTPConnections.getUpdates(idResponsible);
                        if(notificationsUpdateBean.getConnStatus() == 200)
                        {
                            DataBaseTransactions dataBaseTransactions = new DataBaseTransactions();
                            SQLiteHelper sqliteHelper = new SQLiteHelper(MicroformasApp.getAppContext(), null);

                            boolean saveReceptions = dataBaseTransactions.setRecepcionesCatalog(idResponsible,
                                    sqliteHelper, notificationsUpdateBean);
                            dataBaseTransactions.setMD5(MicroformasApp.getAppContext(), saveReceptions,
                                    Constants.DATABASE_RECEPCIONES, false);

                            sqliteHelper.close();
                        }
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
        else
        {
            Toast.makeText(mActivity, mMessage, Toast.LENGTH_SHORT).show();
        }

        mActivity.updateUnidades(mMessage);
	}

    private ReceptionActivity mActivity;
    private ProgressDialog mDialog;

    private String idSent;
    private String idResponsible;
    private ArrayList<UnitBean> mUnits;
    private ArrayList<SupplyBean> mSupplies;

    private String mMessage;
}