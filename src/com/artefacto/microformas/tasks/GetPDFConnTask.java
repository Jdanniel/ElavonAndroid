package com.artefacto.microformas.tasks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

public class GetPDFConnTask extends AsyncTask<String,Void, Integer>{

	private ProgressDialog progressDialog;
	private Activity activity;
	
	private File file;
	
	private char status;
	
	public GetPDFConnTask(ProgressDialog progressDialog , Activity activity) {
		super();
		this.progressDialog = progressDialog;
		this.activity = activity;
		status = 0;
	}

	@Override
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... arg0)
	{	// ("0-0")
		try {
			String idR = arg0[0];

			String[] frags = arg0[1].split(" ");
			String[] date = frags[0].split("/");
			
			if (date[0].length() == 1){
				date[0] = "0"+date[0];
			}
			
			if (date[1].length() == 1){
				date[1] = "0"+date[1];
			}
			
			String fileName = idR +"_"+ date[1] +"_"+ date[0] + "_" + date[2]+".pdf";
			
			String folder = Environment.getExternalStorageDirectory().getPath() + "/artefacto/microformas/temp/"; 
			
			// create a File object for the parent directory
			File pdfDirectory = new File(folder);
			// have the object build the directory structure, if needed.
			pdfDirectory.mkdirs();
			
            String path = folder + fileName;
            
            file = new File(folder);
            
            if (file.exists()){
	            if (file.isDirectory()) {
	                String[] children = file.list();
	                for (int i = 0; i < children.length; i++) {
	                    new File(file, children[i]).delete();
	                }
	            }
            }else{
            	file.mkdir();
            }
            
            file = new File(path);
            
            if (!file.exists()){
            	status = 1;
    			activity.runOnUiThread(new Runnable() {
    			    public void run() {
    			    	progressDialog.setMessage("Descargando PDF ...");
    			    }
    			});
    			URL url;
				HTTPConnections.VerifyServerMode(MicroformasApp.getAppContext());
    			if(Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE))
				{	// (THIS)
    				 //url = new URL("http://72.32.173.222/MIC3/UPLOADER/PDF_CIERRE_PDA/" 	+ fileName);
    				url = new URL("http://smc.microformas.com.mx/MIC3/UPLOADER/PDF_CIERRE/" 	+ fileName);
    			}
    			else{
    				//url = new URL("http://72.32.173.222/MIC_TEST/UPLOADER/PDF_CIERRE_PDA/" + fileName);
//    				url = new URL("http://smc.microformas.com.mx/MIC3TEST/UPLOADER/PDF_CIERRE/" + fileName);
//    				url = new URL("http://smc.microformas.com.mx/MIC3BIS/UPLOADER/PDF_CIERRE/" + fileName);
    				url = new URL("http://smc.microformas.com.mx/MIC3_LAB/UPLOADER/PDF_CIERRE/" + fileName);
    			}
	            
	            URLConnection connection = url.openConnection();
	            connection.connect();
	            
	            InputStream input = null;
	            try{
	            	input = new BufferedInputStream(url.openStream());
	            }catch(FileNotFoundException e){
	            	int statusGenerate = HTTPConnections.GenerarPDF(idR);
	            	if ( statusGenerate == 2){
	            		status = 3;
	            		return null;
	            	}
	            	else if (statusGenerate == 1){
	            		status = 2;
	            		return null;
	            	}
	            		
	            }
	            input = new BufferedInputStream(url.openStream());
	            OutputStream output = new FileOutputStream(path);
	
	            byte data[] = new byte[1024];
	            int count;
	            while ((count = input.read(data)) != -1) {
	                output.write(data, 0, count);
	            }
	
	            output.flush();
	            output.close();
	            input.close();
	            
            }
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        	status = 2;
        }
		return null;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		if (status == 1)
			Toast.makeText(activity, "Archivo Descargado exitosamente", Toast.LENGTH_SHORT).show();
		else if (status == 2){
			Toast.makeText(activity, "Error de conexion al servidor", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (status == 3){
			Toast.makeText(activity, "Error: El Servidor no pudo generar el archivo", Toast.LENGTH_SHORT).show();
			return;
		}
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        if (activities.size() > 0) {
            activity.startActivity(intent);
        } else {
        	Toast.makeText(activity, "Descarga un visor de PDFs", Toast.LENGTH_SHORT).show();
        }
	}
}
