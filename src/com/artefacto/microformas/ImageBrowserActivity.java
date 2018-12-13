package com.artefacto.microformas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.UploadImageGalleryTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by GSI-001061_ on 21/02/2018.
 */

public class ImageBrowserActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    Button sendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagebrowser);

        imageView = findViewById(R.id.imageid);
        button = findViewById(R.id.btnCargarImg);
        sendButton = findViewById(R.id.btnSendImage);

        sp = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
        numAR = sp.getString(Constants.PREF_LAST_REQUEST_VISITED, "");
        idTecnico = sp.getString(Constants.PREF_USER_ID, "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendImage();
            }
        });
    }

    private void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione Aplicacion"),71);
    }

    private void SendImage(){
        if(path != null){
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Enviando Imagen...");
            progressDialog.setCancelable(false);
            UploadImageGalleryTask uploadImageGalleryTask = new UploadImageGalleryTask(ImageBrowserActivity.this,progressDialog);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(String.valueOf(path),options);

            if(options.outWidth < 1632){
                if (options.outHeight < 1224){
                    uploadImageGalleryTask.execute(path_,name,numAR,idTecnico);

                }else{
                    Toast.makeText(getApplicationContext(), "La foto es demasiado grande, por favor cambie el tamaño a 2MP", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "La foto es demasiado grande, por favor cambie el tamaño a 2MP", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 71 ) {
            if (resultCode == RESULT_OK && requestCode == 71 && data != null && data.getData() != null) {

                path = data.getData();
                path_ = getPath(path);
                name = newName();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageURI(path);
            }
        }
    }

    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
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

    private String path_;
    private Uri path;
    private String name;
    private String numAR = "0";
    private String idTecnico = "0";
    private SharedPreferences sp;

}
