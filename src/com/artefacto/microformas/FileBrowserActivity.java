package com.artefacto.microformas;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.artefacto.microformas.adapters.FileBrowserAdapter;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.UploadPDFCloseTask;
import com.artefacto.microformas.tasks.UploadPDFConnTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FileBrowserActivity extends ListActivity {

	public class FileBean {
		private String fileName;
		private boolean isDirectory;

		public FileBean(String fileName, boolean isDirectory) {
			super();
			this.fileName = fileName;
			this.isDirectory = isDirectory;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public boolean isDirectory() {
			return isDirectory;
		}
		public void setDirectory(boolean isDirectory) {
			this.isDirectory = isDirectory;
		}
	};

	ArrayList<FileBean> files;
	FileBrowserAdapter adapter = null;


	private String path;
	private File mPath;
	private String dir;
	private String idRequest;
	private String fec_cierre;
	private String location;
	private Integer rClose;

	private static final String FTYPE = ".pdf";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		path =  intent.getStringExtra("path");
		idRequest = intent.getStringExtra("idRequest");
		fec_cierre = intent.getStringExtra("fec_cierre");
		location = intent.getStringExtra("location");

		if (location == null){
			location = "0";
		}
		if (path == null || path.equals("")){
			path = Environment.getExternalStorageDirectory().getPath();
		}

		mPath = new File(path);
		dir = mPath.getPath();
		loadFileList();
		//showDialog(DIALOG_LOAD_FILE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_file_browser, menu);
		return true;
	}

	private void loadFileList() {
		files = new ArrayList<FileBean>();
		try {
			mPath.mkdirs();
		}
		catch(SecurityException e) {
			Log.e("BrowserActivity", "unable to write on the sd card " + e.toString());
		}
		if(mPath.exists()) {
			int fileCount = 0;
			String temporalPath = Environment.getExternalStorageDirectory().getAbsolutePath();

			if (!dir.equals(temporalPath)){
				files.add(new FileBean("..",true));
				fileCount++;
			}
			String[] mFileList = mPath.list();
			Arrays.sort(mFileList,new SortIgnoreCase());
			for(String fileName : mFileList){
				File sel = new File(dir, fileName);
				if (sel.isDirectory()){
					files.add(fileCount++,new FileBean(fileName,true));
				}
				else if (fileName.contains(FTYPE)){
					files.add(new FileBean(fileName,false));
				}
			}
		}
		adapter = new FileBrowserAdapter(this, R.layout.activity_file_browser, files);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView listView, View v, final int position, long id) {

		String goToPath;
		FileBean file = files.get(position);

		if (file.fileName.equals("..")){
			goToPath = mPath.getParent();
		}
		else if (file.isDirectory()){
			goToPath = dir + "/" + file.fileName +"/";
		}
		else{
			showFileDialog(file.fileName);
			return;
		}

		Intent intent = new Intent(this, FileBrowserActivity.class);
		intent.putExtra("path", goToPath);
		intent.putExtra("idRequest",idRequest);
		intent.putExtra("fec_cierre",fec_cierre);
		intent.putExtra("location",location);
		startActivity(intent);

	}

	public void showFileDialog(final String fileName){
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle("Confirmación");
		dialog.setMessage("¿Que deseas hacer con este archivo?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ver", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int buttonId) {
				openPDF(fileName);
			}
		});
		dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Enviar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int buttonId) {
				sendPDF(fileName);
			}
		});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int buttonId) {

			}
		});
		dialog.show();
	}


	public void openPDF(String fileName){
		File F = new File(dir + "/" + fileName);
		Uri uri = Uri.fromFile(F);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(uri, "application/pdf");
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
		if (activities.size() > 0) {
			startActivity(intent);
		} else {
			Toast.makeText(FileBrowserActivity.this, "Descarga un visor de PDFs", Toast.LENGTH_SHORT).show();
		}
	}

	public void sendPDF(String fileName){
		String filePath = dir + "/" + fileName;

		int i;

		for (i=0;i<fec_cierre.length();i++){
			if (fec_cierre.charAt(i) == ' ')
				break;
		}

		String frag = fec_cierre.substring(0, i);
		String[] date = frag.split("/");

		if (date[0].length() == 1){
			date[0] = "0"+date[0];
		}

		if (date[1].length() == 1){
			date[1] = "0"+date[1];
		}

		String sendFileName = idRequest +"_"+ date[1] +"_"+ date[0] + "_" + date[2]+".pdf";

		ProgressDialog pg = new ProgressDialog(this);
		pg.setMessage("Enviando archivo...");
		pg.setCancelable(false);

		if (location.equals("0")){
			UploadPDFConnTask uploadPDFConnTask = new UploadPDFConnTask(this, pg);
			uploadPDFConnTask.execute(filePath, sendFileName,idRequest);
		}else{
			UploadPDFCloseTask uploadPDFCloseTask = new UploadPDFCloseTask(this, pg);
			try {
				rClose = uploadPDFCloseTask.execute(filePath,sendFileName,idRequest).get();
				if (rClose == 1){
					SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString(Constants.RESPONSE_PDF_CLOSE+"_"+idRequest, "1");
					editor.commit();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

	}


	public class SortIgnoreCase implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			return s1.toLowerCase().compareTo(s2.toLowerCase());
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
