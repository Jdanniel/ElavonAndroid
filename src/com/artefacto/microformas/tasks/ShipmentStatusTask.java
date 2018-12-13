package com.artefacto.microformas.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.artefacto.microformas.ProcessShipmentListActivity;
import com.artefacto.microformas.ReceptionsListActivity;
import com.artefacto.microformas.beans.ShipmentBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import java.util.ArrayList;

public class ShipmentStatusTask extends AsyncTask<String,Void, Integer>
{
	public ShipmentStatusTask(Activity activity, ProgressDialog progressDialog, int type)
    {
		super();
		this.activity = activity;
		this.progressDialog = progressDialog;
		this.type = type;
	}

	@Override
	protected void onPreExecute()
	{
		progressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params)
    {
        mShipmentBeanList = new ArrayList<>();
		ArrayList<String> shipmentList	= new ArrayList<>();

		SQLiteHelper sqliteHelper = new SQLiteHelper(activity, null);
        SQLiteDatabase db = sqliteHelper.getReadableDB();

        Cursor cursor;
        if(type == 0)
        {
        	cursor = db.query(SQLiteHelper.PROCESS_SHIPMENT_DB_NAME, new String[] {SQLiteHelper.PROCESS_SHIPMENT_ID},
                null, null, null, null, null);
        }
        else
        {
        	cursor = db.query(SQLiteHelper.RECEPTIONS_DB_NAME, new String[] {SQLiteHelper.RECEPTIONS_ID_SHIPMENT},
                null, null, null, null, null);
        }

        try
        {
	       	if (cursor != null )
            {
	       		if(cursor.moveToFirst())
                {
	       			do
                    {
                        String idShipment = cursor.getString(0);
						if(!IsAlreadyInList(shipmentList, idShipment))
						{
                            shipmentList.add(idShipment);
						}
	       			} while (cursor.moveToNext());
	       		}
	       	}
	    }
	    catch(Exception ex)
        {
            Log.d("Microformas", ex.getMessage());
        }
        finally
        {
            if(cursor != null)
            {
                cursor.close();
            }

            db.close();
        }

	    if(type == 0)
        {
	    	for(int i = 0; i< shipmentList.size(); i++)
            {
				Object result = HTTPConnections.getProcessShipmentDetail(shipmentList.get(i));
				if(result instanceof ShipmentBean)
				{
					mShipmentBeanList.add((ShipmentBean) result);
				}
		    }
	    }
	    else
        {
            for(int i = 0; i < shipmentList.size(); i++)
            {
                Object result = HTTPConnections.getReceptionDetail(shipmentList.get(i));
				if(result instanceof ShipmentBean)
				{
					mShipmentBeanList.add((ShipmentBean) result);
				}
		    }
	    }

		return 0;
	}

	@Override
	protected void onPostExecute(Integer headerCode)
    {
		progressDialog.dismiss();
		goToEnvios(mShipmentBeanList, type);
	}

	public void goToEnvios(ArrayList<ShipmentBean> shipmentBeanArray, int type)
    {
        if(type == 0)
        {
			Intent intent = new Intent(activity, ProcessShipmentListActivity.class);
			intent.putExtra("bean", shipmentBeanArray);
			activity.startActivity(intent);
		}
		else
        {
			Intent intent = new Intent(activity, ReceptionsListActivity.class);
			intent.putExtra("bean", shipmentBeanArray);
			activity.startActivity(intent);
		}
	}

	private boolean IsAlreadyInList(ArrayList<String> list, String id)
	{
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).equals(id))
            {
                return true;
            }
        }

        return false;
	}

    private Activity activity;
    private ProgressDialog progressDialog;
    private int type;

    ArrayList<ShipmentBean> mShipmentBeanList;
}
