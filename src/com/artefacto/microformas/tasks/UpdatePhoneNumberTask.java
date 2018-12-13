package com.artefacto.microformas.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.artefacto.microformas.connection.HTTPConnections;

public class UpdatePhoneNumberTask extends AsyncTask<String, Void, Void> {

    private Activity activity;
    private String number;
    private String idusuario;

    public UpdatePhoneNumberTask(Activity activity){
        super();
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... strings) {
        number = strings[0];
        idusuario = strings[1];
        HTTPConnections.updatePhoneNumber(number,idusuario,activity);
        return null;
    }
}
