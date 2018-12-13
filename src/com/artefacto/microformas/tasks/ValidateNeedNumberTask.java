package com.artefacto.microformas.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.connection.HTTPConnections;

public class ValidateNeedNumberTask extends AsyncTask<String, Void, Void> {

    public ValidateNeedNumberTask(int idusuario, Context context){
        this.idusuario = idusuario;
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {

         HTTPConnections.getNeedPhoneNumber(idusuario,context);

        return null;
    }

    int idusuario;
    Context context;
}
