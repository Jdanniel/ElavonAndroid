package com.artefacto.microformas.application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;

import com.artefacto.microformas.MFActivity;
import com.artefacto.microformas.async.AsyncQueue;

public class MicroformasApp extends Application
{
	@Override
    public void onCreate()
    {
        super.onCreate();

        MicroformasApp.currentCtx = getApplicationContext();
        MicroformasApp.app = this;

    	AsyncQueue.start();
    }

    public static Context getAppContext() {
        return app.getApplicationContext();
    }

    public static  boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) currentCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isNumber(String str)
    {
        try
        {
            Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }

        return true;
    }

    public static boolean isNetworkConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                MicroformasApp.currentCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    public static int getColor(Context context, int id)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            return context.getColor(id);
        }
        else
        {
            return context.getResources().getColor(id);
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static boolean verifyStoragePermissions(Activity activity)
    {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );

            return false;
        }
        else
        {
            return true;
        }
    }

    public static float dpToPixels(Activity myActivity, int nDp)
    {
        Resources r = myActivity.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, nDp, r.getDisplayMetrics());
    }

    public static boolean firstUpdate = false;
    public static int needPhoneNumber = 1;
    public static MFActivity activity = null;
    public static Context currentCtx;

    private static Application app;

    // Storage Permissions
    public  static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}
