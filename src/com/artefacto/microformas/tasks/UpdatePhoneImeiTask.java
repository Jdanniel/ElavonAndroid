package com.artefacto.microformas.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.artefacto.microformas.LoginActivity;
import com.artefacto.microformas.connection.HTTPConnections;

public class UpdatePhoneImeiTask extends AsyncTask<String,Void,Void>{

    private Activity activity;
    private String imei;
    private String idusuario;
    private String number;

    public UpdatePhoneImeiTask(Activity activity){
        super();
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try{
            imei = strings[0];
            idusuario = strings[1];
            number = strings[2];
            HTTPConnections.updatePhoneImei(imei,idusuario,number,activity);
        }catch (Exception e){
            Log.e("Prueba--", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);
        ((LoginActivity) activity).goToFirstScreenActivity(idusuario);
    }

}
