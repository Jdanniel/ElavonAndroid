package com.artefacto.microformas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.artefacto.microformas.ImageBrowserActivity;
import com.artefacto.microformas.PhotoTakingActivity;
import com.artefacto.microformas.async.AsyncQueue;
import com.artefacto.microformas.beans.ConnTaskResultBean;

/**
 * Created by GSI-001061_ on 22/02/2018.
 */

public class UploadImageGalleryTask extends AsyncTask<String,Void,Integer> {

    private ProgressDialog progressDialog;
    private ImageBrowserActivity activity;

    String path;
    String photoName;
    String numAr;
    String idTecnico;

    private ConnTaskResultBean ctrb;

    public UploadImageGalleryTask(	String path,
                                   String photoName,
                                   String numAr,
                                   String idTecnico)
    {
        super();
        this.path = path;
        this.photoName = photoName;
        this.numAr = numAr;
        this.idTecnico = idTecnico;
    }

    public UploadImageGalleryTask(ImageBrowserActivity activity,ProgressDialog progressDialog) {
        super();
        this.progressDialog = progressDialog;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute(){
        try {
            progressDialog.show();
        }catch (Exception ex){
            Log.e("Dialog",ex.getMessage());
        }
    }

    @Override
    protected Integer doInBackground(String... params) {

        this.path = params[0];
        this.photoName = params[1];
        this.numAr = params[2];
        this.idTecnico = params[3];

        UploadImageConn uic = new UploadImageConn(path, photoName, numAr, idTecnico);
        ctrb = uic.uploadImage();

        if (ctrb.getSuccess() == 0){
            AsyncQueue.AddUploadImage(path, photoName, numAr, idTecnico);
        }

        return ctrb.getSuccess();
    }


    @Override
    protected void onPostExecute(Integer headerCode){
        progressDialog.dismiss();
        Toast.makeText(activity, ctrb.getStatus(), Toast.LENGTH_SHORT).show();

        activity.finish();
    }
}
