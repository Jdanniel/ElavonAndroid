package com.artefacto.microformas.async;

import java.util.ArrayList;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;

import com.artefacto.microformas.InventoryFragment;
import com.artefacto.microformas.MainActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.ChangeStatusConn;
import com.artefacto.microformas.tasks.SendCommentConn;
import com.artefacto.microformas.tasks.SparePartsConn;
import com.artefacto.microformas.tasks.UploadImageConn;
import com.artefacto.microformas.tasks.UploadPDFConn;
import com.artefacto.microformas.tasks.UploadPDFClose;
import com.artefacto.microformas.tasks.ViaticosConn;

public class AsyncQueue {
	protected static ArrayList<String> pendings;
	
	public static class PendingsThread implements Runnable{

		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(!fetchList()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			UpdateMainActivity();
			
			/*pendings.clear();
			dumpList();*/
			
			while(true){
				String pending = null;
				synchronized(pendings){
					if (pendings.size()>0)
						pending = pendings.get(0);
				}
				if (pending!=null){
					String[] fragments = pending.split(Constants.ASYNC_SEPARATOR_PARAMETER);
					if (fragments[0].equals(Constants.ASYNC_CHANGE_STATUS)){
						if (sendChangeStatus(fragments[1],fragments[2],fragments[3])){
							synchronized(pendings){
								pendings.remove(0);
							}
							UpdateMainActivity();
							dumpList();
						}
					}
					else if (fragments[0].equals(Constants.ASYNC_SEND_COMMENT)){
						if (sendSendComment(fragments[1],fragments[2],fragments[3])){
							synchronized(pendings){
								pendings.remove(0);
							}
							UpdateMainActivity();
							dumpList();
						}
					}
					else if (fragments[0].equals(Constants.ASYNC_SEND_SPARES)){
						if (sendSpareRequest(fragments[1], fragments[2], fragments[3], fragments[4], fragments[5],
											fragments[6], fragments[7], fragments[8], fragments[9]))
						{
							synchronized(pendings){
								pendings.remove(0);
							}
							UpdateMainActivity();
							dumpList();
						}
					}
					else if (fragments[0].equals(Constants.ASYNC_SEND_VIATICOS))
                    {
                        boolean isSent = sendViaticos(fragments[1], fragments[2], fragments[3], fragments[4], fragments[5],
                                fragments[6], fragments[7]);
                        if (isSent)
                        {
							synchronized(pendings)
                            {
								pendings.remove(0);
							}

							UpdateMainActivity();
							dumpList();
						}
					}
					else if (fragments[0].equals(Constants.ASYNC_UPLOAD_IMAGE)){
						if (uploadImage(fragments[1], fragments[2], fragments[3], fragments[4])){
							synchronized(pendings){
								pendings.remove(0);
							}
							UpdateMainActivity();
							dumpList();
						}
					}
					else if (fragments[0].equals(Constants.ASYNC_UPLOAD_PDF)){
						if (uploadPDF(fragments[1], fragments[2], fragments[3])){
							synchronized(pendings){
								pendings.remove(0);
							}
							UpdateMainActivity();
							dumpList();
						}
					}
					/*-------------------------------------Inicio 03/03/2017*/
					else if (fragments[0].equals(Constants.ASYNC_UPLOAD_PDF_CLOSE)){
						if (uploadPDFclose(fragments[1],fragments[2],fragments[3])){
							synchronized (pendings){
								pendings.remove(0);
							}
							UpdateMainActivity();
							dumpList();
						}
					}
					/*------------------------------------------------------*/
					else{
						synchronized(pendings){
							pendings.remove(0);
						}
						dumpList();
					}
						
				}else{
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	};
	
	public static void deleteItem(int position){
		synchronized (pendings) {
			pendings.remove(position);
			dumpList();
		}
	}
	
	public static void start(){
		PendingsThread p = new PendingsThread();
		new Thread(p).start();
	}
	
	public static void dumpList(){
		String value = "";
		synchronized(pendings){
			for (String str: pendings){
				value += str + Constants.ASYNC_SEPARATOR_REQUEST;
			}
		}
		Context context = MicroformasApp.getAppContext();
		synchronized(context){
			SharedPreferences sharedPreferences = context.getSharedPreferences("Pendings", Context.MODE_PRIVATE);  
			Editor editor = sharedPreferences.edit();
			editor.putString(Constants.PREF_CONEXIONES_PENDIENTES, value);
			editor.commit();
		}
	}
	
	public static boolean fetchList(){
		Context context = MicroformasApp.getAppContext();
		pendings = new ArrayList<String>();
		String values = null;

		if (context == null)
			return false;
		synchronized(context){
			SharedPreferences sharedPreferences = context.getSharedPreferences("Pendings", Context.MODE_PRIVATE);  
			values = sharedPreferences.getString(Constants.PREF_CONEXIONES_PENDIENTES, null);
		}
		if (values != null){
			String[] fragments = values.split(Constants.ASYNC_SEPARATOR_REQUEST);
			synchronized(pendings){
				for (String str : fragments){
					pendings.add(str);
				}
			}
		}
		return true;
	}
	
	private static boolean sendChangeStatus(String idRequest,String idUser,String status){
		ChangeStatusConn task = new ChangeStatusConn(idRequest,idUser,Integer.parseInt(status),null);
		ConnTaskResultBean result = task.changeStatus();
		if (result.getSuccess() == 1){
			task.OnSend(result);
			return true;
		}
		else{
			return false;
			//ReEncolar
		}
	}
	
	public static void AddChangeStatus(String idRequest,String idUser,String status) {
		String value = Constants.ASYNC_CHANGE_STATUS+Constants.ASYNC_SEPARATOR_PARAMETER+idRequest+Constants.ASYNC_SEPARATOR_PARAMETER+idUser+Constants.ASYNC_SEPARATOR_PARAMETER+status;
		synchronized(pendings){
			pendings.add(value);
		}
		dumpList();
	}
	
	public static boolean isChangeStatusPending(String idRequest){
		Object[] list;
		synchronized(pendings){
			if (pendings.size()<=0)
				return false;
			list = pendings.toArray();
		}
		for (Object obj : list){
			String str = (String)obj;
			if (str.startsWith(Constants.ASYNC_CHANGE_STATUS) || str.startsWith(Constants.ASYNC_SEND_SPARES) || 
					str.startsWith(Constants.ASYNC_SEND_VIATICOS)){
				String[] fragments = str.split(Constants.ASYNC_SEPARATOR_PARAMETER);
				if (fragments[1].equals(idRequest)){
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean sendSendComment(String idRequest,String idUser,String comment){
		SendCommentConn task = new SendCommentConn(idRequest,idUser,comment);
		ConnTaskResultBean result = task.sendComment();
		if (result.getSuccess() == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static void AddSendComment(String idRequest,String idUser,String comment) {
		String value = Constants.ASYNC_SEND_COMMENT+Constants.ASYNC_SEPARATOR_PARAMETER+idRequest+Constants.ASYNC_SEPARATOR_PARAMETER+idUser+Constants.ASYNC_SEPARATOR_PARAMETER+comment;
		synchronized(pendings){
			pendings.add(value);
		}
		dumpList();
	}
	
	public static boolean isSendCommentPending(String idRequest, String idUser){
		Object[] list;
		synchronized(pendings){
			if (pendings.size()<=0)
				return false;
			list = pendings.toArray();
		}
		for (Object obj : list){
			String str = (String)obj;
			if (str.startsWith(Constants.ASYNC_SEND_COMMENT)){
				String[] fragments = str.split(Constants.ASYNC_SEPARATOR_PARAMETER);
				if (fragments[1].equals(idRequest) && fragments[2].equals(idUser)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	private static boolean sendSpareRequest(String idRequest, String idUser, String idAlmacen, String strPackage,
											String idPrioridad, String notas, String tipoServicio,
											String idDireccion, String fecha)
	{
		SparePartsConn task = new SparePartsConn(idRequest, idUser, idAlmacen, strPackage,
				idPrioridad, notas, tipoServicio, idDireccion, fecha);
		
		ConnTaskResultBean result = task.sendSpareRequest();
		return (result.getSuccess() == 1);
	}
	
	public static void AddSendSpare(String idRequest, String idUser, String idAlmacen, String strPackage,
										String idPrioridad, String notas, String tipoServicio,
										String idDireccion, String fecha)
	{
		String value = Constants.ASYNC_SEND_SPARES+Constants.ASYNC_SEPARATOR_PARAMETER+idRequest
												  +Constants.ASYNC_SEPARATOR_PARAMETER+idUser
												  +Constants.ASYNC_SEPARATOR_PARAMETER+idAlmacen
												  +Constants.ASYNC_SEPARATOR_PARAMETER+strPackage
												  +Constants.ASYNC_SEPARATOR_PARAMETER+idPrioridad
												  +Constants.ASYNC_SEPARATOR_PARAMETER+notas
												  +Constants.ASYNC_SEPARATOR_PARAMETER+tipoServicio
												  +Constants.ASYNC_SEPARATOR_PARAMETER+idDireccion
												  +Constants.ASYNC_SEPARATOR_PARAMETER+fecha;
		synchronized(pendings)
		{
			pendings.add(value);
		}
		dumpList();
	}
	
	private static boolean sendViaticos(String idRequest, String idUser,
										String lugar, String observaciones,
										String idPrioridad, String viaticos, String costos)
    {
			ViaticosConn task = new ViaticosConn(idRequest, idUser, lugar, observaciones, idPrioridad, viaticos, costos);
			ConnTaskResultBean result = task.sendViaticos();
			return (result.getSuccess() == 1);
	}
	
	public static void AddSendViaticos(String idRequest, String idUser,
										String lugar, String observaciones,
										String idPrioridad, String viaticos, String costos){
		String value = Constants.ASYNC_SEND_VIATICOS+Constants.ASYNC_SEPARATOR_PARAMETER+idRequest+Constants.ASYNC_SEPARATOR_PARAMETER+idUser+Constants.ASYNC_SEPARATOR_PARAMETER+lugar+Constants.ASYNC_SEPARATOR_PARAMETER+observaciones
												  +Constants.ASYNC_SEPARATOR_PARAMETER+idPrioridad+Constants.ASYNC_SEPARATOR_PARAMETER+viaticos+Constants.ASYNC_SEPARATOR_PARAMETER+costos;
		synchronized(pendings){
			pendings.add(value);
		}
		dumpList();
	}
	
	private static boolean uploadImage(String path, String photoName, String numAR, String idTecnico){
			try{
				UploadImageConn task = new UploadImageConn(path, photoName, numAR, idTecnico);
				ConnTaskResultBean result = task.uploadImage();
				if (result.getSuccess() == 1){
					return true;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return false;
	}
	
	public static void AddUploadImage(String path, String photoName, String numAR, String idTecnico){
		String value = Constants.ASYNC_UPLOAD_IMAGE+Constants.ASYNC_SEPARATOR_PARAMETER+path+Constants.ASYNC_SEPARATOR_PARAMETER+photoName+Constants.ASYNC_SEPARATOR_PARAMETER+numAR+Constants.ASYNC_SEPARATOR_PARAMETER+idTecnico;
		synchronized(pendings){
			pendings.add(value);
		}
		dumpList();
	}

	private static boolean uploadPDF(String path, String fileName, String idAr){
		try{
			UploadPDFConn task = new UploadPDFConn(path, fileName, idAr);
			ConnTaskResultBean result = task.uploadPDF();
			if (result.getSuccess() == 1){
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/*----------------------------------------Inicia 03/08/2017*/
	private static boolean uploadPDFclose(String path, String fileName, String idAr){
		try{
			UploadPDFClose task = new UploadPDFClose(path,fileName,idAr);
			ConnTaskResultBean result = task.uploadPDF();
			if (result.getSuccess() == 1){
				return true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/*---------------------------------------------------------*/

	public static void AddUploadPDF(String path, String fileName, String idAr){
		String value = Constants.ASYNC_UPLOAD_PDF+Constants.ASYNC_SEPARATOR_PARAMETER+path+Constants.ASYNC_SEPARATOR_PARAMETER+fileName+Constants.ASYNC_SEPARATOR_PARAMETER+idAr;
		synchronized(pendings){
			pendings.add(value);
		}
		dumpList();
	}
	
	public static ArrayList<String> getList(){
		if (pendings!=null){
			synchronized (pendings) {
				return (ArrayList<String>) pendings.clone();
			}
		}
		else{
			return new ArrayList<String>();
		}
	}
	
	public static String[] getDescription(String pendingStr){
		String[] desc = new String[2];
		String[] fragments = pendingStr.split(Constants.ASYNC_SEPARATOR_PARAMETER);
		if (fragments[0].equals(Constants.ASYNC_CHANGE_STATUS)){
			String status = "";
			SQLiteHelper 	sqliteHelper 	= new SQLiteHelper(MicroformasApp.getAppContext(), null);
			SQLiteDatabase 	db 				= sqliteHelper.getWritableDB();

			Cursor c = db.rawQuery("select "+SQLiteHelper.STATUS_DESC_STATUS+" from "+SQLiteHelper.STATUS_DB_NAME+" where "+SQLiteHelper.STATUS_ID_STATUS+" = "+fragments[3],null);
			if (c.moveToFirst()) {
				//Recorremos el cursor hasta que no haya más registros
				status = c.getString(0);
			}
			c.close();
			db.close();
			
			desc[0] = "Cambio de status";
			desc[1] = "Solicitud: "+fragments[1] + " a "+ status;
		}
		else if (fragments[0].equals(Constants.ASYNC_SEND_COMMENT)){
			desc[0] = "Envío de Comentario";
			if (fragments[3].length()>15)
				desc[1] = "Solicitud: "+fragments[1] + " \""+ fragments[3].substring(0, 15) + " ...\"";
			else{
				desc[1] = "Solicitud: "+fragments[1] + " \""+ fragments[3]+"\"";
			}
		}
		else if (fragments[0].equals(Constants.ASYNC_SEND_SPARES)){
			desc[0] = "Solicitud de refacciones";
			desc[1] = "Solicitud: "+fragments[1];
		}
		else if (fragments[0].equals(Constants.ASYNC_SEND_VIATICOS)){
			desc[0] = "Solicitud de viaticos";
			desc[1] = "Solicitud: "+fragments[1];
		}
		else if (fragments[0].equals(Constants.ASYNC_UPLOAD_IMAGE)){
			desc[0] = "Envío de foto";
			desc[1] = "Solicitud: "+fragments[3];
		}
		else if (fragments[0].equals(Constants.ASYNC_UPLOAD_PDF)){
			desc[0] = "Envío de PDF";
			desc[1] = "Nombre: "+fragments[1];
		}
		return desc;
	}
	
	private static void UpdateMainActivity(){
		if (MicroformasApp.activity != null){
			if (MicroformasApp.activity.getClass() == MainActivity.class){
				MicroformasApp.activity.UpdateGUI();
			}
		}
	}
	
}
