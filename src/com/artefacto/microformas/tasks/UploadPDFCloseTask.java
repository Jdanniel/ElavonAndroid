package com.artefacto.microformas.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.ConnTaskResultBean;

import java.io.File;

/**
 * Created by GSI-001061_ on 03/08/2017.
 */

public class UploadPDFCloseTask extends AsyncTask<String,Void,Integer>{

    private ProgressDialog progressDialog;
    private Activity activity;

    String path;
    String fileName;
    String idAr;

    private ConnTaskResultBean ctrb;

    public UploadPDFCloseTask(	String path,
                                 String fileName, String idAr)
    {
        super();
        this.path = path;
        this.fileName = fileName;
        this.idAr = idAr;
        this.progressDialog = null;
        this.activity = null;
    }

    public UploadPDFCloseTask(Activity activity,ProgressDialog progressDialog) {
        super();
        this.progressDialog = progressDialog;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute(){
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {

        this.path = params[0];
        this.fileName = params[1];
        this.idAr = params[2];

        UploadPDFClose upc = new UploadPDFClose(path, fileName,idAr);

        ctrb = upc.uploadPDF();
        int exito = ctrb.getSuccess();

        Log.e("Success",""+exito);

        if (ctrb.getSuccess() == 0){
            Log.e("Success",""+exito);
            AsyncQueue.AddUploadPDF(path, fileName,idAr);
        }else{
            File file = new File(path);
            file.delete();
        }
        Log.e("Success",""+exito);
        return ctrb.getSuccess();
    }

    @Override
    protected void onPostExecute(Integer headerCode){

        try{
            progressDialog.dismiss();
            Toast.makeText(activity, ctrb.getStatus(), Toast.LENGTH_SHORT).show();
            activity.finish();
        }catch(Exception e){
            Log.e("PD",e.toString());
        }

    }
}
