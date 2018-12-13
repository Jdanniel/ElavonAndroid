package com.artefacto.microformas.tasks;

/**
 * Created by GSI-1045 on 13/02/2017.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ValidateConnTask {

    public boolean Conexion(Context context){

        boolean resultado = false;

        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            resultado = true;
        }
        return resultado;
    }

}
