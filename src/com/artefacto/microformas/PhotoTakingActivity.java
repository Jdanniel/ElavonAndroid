package com.artefacto.microformas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.UploadImageConnTask;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Random;

public class PhotoTakingActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_taking);
		this.imageView = (ImageView)this.findViewById(R.id.photo_taked);
		
		sp = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
		
		numAR = sp.getString(Constants.PREF_LAST_REQUEST_VISITED, ""); 
		idTecnico = sp.getString(Constants.PREF_USER_ID, "");
		
		if(savedInstanceState == null || savedInstanceState.get("FT") == null){
			takePhoto();
		}
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == CAMERA_REQUEST)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                LoadImageTaken();
            }
        }
    }

    @Override
	public void onStart(){
		super.onStart();
	}
	
	@Override
	public void onSaveInstanceState (Bundle outState) {
		//Tells the activity has taken a photo the first time.
		super.onSaveInstanceState(outState);
		outState.putBoolean("FT", true);
		outState.putParcelable("ImageTaken", imageTaken);
	}

//    @Override
//    public void onRestoreInstanceState(Bundle b)
//    {//you need to handle NullPionterException here.
//        imageTaken = b.getParcelable("ImageTaken");
//        if(imageView != null && imageTaken != null)
//        {
//            imageView.setImageBitmap(imageTaken);
//            imageView.invalidate();
//        }
//    }
	
	public void buttonCancelarOnClick(View v){
		this.finish();
	}
	
	public void buttonAceptarOnClick(View v){
		proceed = true;
		Intent thisActivity = this.getIntent();
		
		//Retrieve extra values
		path = thisActivity.getStringExtra("path");
		photoName = thisActivity.getStringExtra("photoName");
		
		//Send the image
		//HTTPConnections.uploadImage(path, photoName, numAR, idTecnico);
		ProgressDialog pg = new ProgressDialog(this);
		pg.setMessage("Enviando imagen...");
		pg.setCancelable(false);
		UploadImageConnTask uploadImageConnTask = new UploadImageConnTask(this, pg);
		
		//En caso de superar el tamaño permitido, indicar al usuario
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		
		BitmapFactory.decodeFile(path, options);

		if(options.outWidth > 1632){
			proceed = false;
		}
		else{
			if(options.outHeight > 1224)
				proceed = false;
		}
		
		if(proceed)
			uploadImageConnTask.execute(path, photoName, numAR, idTecnico);
		else
			Toast.makeText(getApplicationContext(), "La foto es demasiado grande, por favor cambie el tamaño a 2MP", Toast.LENGTH_SHORT).show();
	}
	
	public void buttonTomarOtraOnClick(View v)
    {
		takePhoto();
	}
	
	public String newName()
    {
		String na = numAR+"_";
		
		Calendar rightNow = Calendar.getInstance();
		String date = rightNow.get(Calendar.DATE)+"";
		int month = rightNow.get(Calendar.MONTH);
		String year = rightNow.get(Calendar.YEAR)+"";
		
		na+= date;
		switch(month){
			case 0: na+="enero"; break;
			case 1: na+="febrero"; break;
			case 2: na+="marzo"; break;
			case 3: na+="abril"; break;
			case 4: na+="mayo"; break;
			case 5: na+="junio"; break;
			case 6: na+="julio"; break;
			case 7: na+="agosto"; break;
			case 8: na+="septiembre"; break;
			case 9: na+="octubre"; break;
			case 10: na+="noviembre"; break;
			case 11: na+="diciembre"; break;
		}
		
		na+= year;
		na+="_";
		
		Random r = new Random();
		boolean listo=false;
		int rn = 0;
		while(!listo){
			rn = r.nextInt();
			if(rn > 9999999){
				if(rn < 100000000){
					listo = true;
				}
			}
		}
		
		String random = rn+"";
		na+=random;
		
		na+=".jpg";
		
		return na;
	}
	
	public void takePhoto(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Intent thisActivity = this.getIntent();
		path = "";
		String externalStorageState = Environment.getExternalStorageState();
		
		if(externalStorageState.equalsIgnoreCase(Environment.MEDIA_MOUNTED)){
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
			path += "/artefacto/microformas/";

			//////////////////////////////////////////////////////
			// create a File object for the parent directory
			File file = new File(path);
            if(!file.exists())
            {//have the object build the directory structure, if needed.
                file.mkdirs();
            }

//			file = new File(path);
//			if (!file.exists())
//            {
//				file.mkdir();
//			}
			/////////////////////////////////////////////////////////
//			file = new File( path );
			photoName = newName();
			path += photoName;
			
			//Store values in extra
			thisActivity.putExtra("path", path);
			thisActivity.putExtra("photoName", photoName);
			
			SharedPreferences shared = getSharedPreferences(Constants.PREF_CONFIG_USER, MODE_PRIVATE);
			Editor editor = shared.edit();
			editor.putString("PhotoPath", path);
			editor.commit();
			
			file = new File( path );
			Uri outputFileUri = Uri.fromFile( file );
			//String absoluteOutputFileUri = file.getAbsolutePath();

			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		}

		startActivityForResult(intent, CAMERA_REQUEST);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		path =  getSharedPreferences(Constants.PREF_CONFIG_USER, MODE_PRIVATE).getString("PhotoPath", "");
	}

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case MicroformasApp.REQUEST_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    LoadImageTaken();
                }
                break;
        }

    }

    private void LoadImageTaken()
    {
        Handler myHandler = new Handler();
        myHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    File file = new File(path);
                    int count = 0;
                    while(!file.exists())
                    {
                        Thread.sleep(1000);
                        count++;

                        if(count >= 7)
                        {
                            Toast.makeText(PhotoTakingActivity.this, "No sé pudo cargar foto, por favor intenta de nuevo.",
                                Toast.LENGTH_SHORT).show();
                            PhotoTakingActivity.this.finish();
                        }
                    }

                    if (file.exists())
                    {
                        if (MicroformasApp.verifyStoragePermissions(PhotoTakingActivity.this))
                        {
                            BitmapFactory.Options decodeOpt = new BitmapFactory.Options();
                            decodeOpt.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(new FileInputStream(file), null, decodeOpt);

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                            float height = MicroformasApp.dpToPixels(PhotoTakingActivity.this, 400);
                            float width  = MicroformasApp.dpToPixels(PhotoTakingActivity.this, 250);
                            options.inSampleSize = MicroformasApp.calculateInSampleSize(decodeOpt, (int) width, (int) height);

                            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file),
                                    null, options);
                            imageTaken = bitmap;

                            imageView.setImageBitmap(bitmap);
                            imageView.invalidate();
                        }
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });


    }

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private String path = "";
    private String photoName = "";
    private String numAR = "0";
    private String idTecnico = "0";
    private SharedPreferences sp;
    boolean proceed = true;

    private Bitmap imageTaken;
}