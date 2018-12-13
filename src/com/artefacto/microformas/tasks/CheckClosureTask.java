package com.artefacto.microformas.tasks;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.artefacto.microformas.RequestDetailActivity;
import com.artefacto.microformas.beans.DescripcionTrabajoBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.SolicitudesBean;
import com.artefacto.microformas.beans.ValidateClosureBean;
import com.artefacto.microformas.beans.ValidateRejectClosureBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;

public class CheckClosureTask extends AsyncTask<String, Void, Integer> {
	private ProgressDialog 				progressDialog;
	private RequestDetailActivity 		activity;
	ValidateRejectClosureBean 			validateRejectClosureBean;
	ValidateClosureBean 				validateClosureBean;
	ArrayList<DescripcionTrabajoBean> 	descripcionTrabajoBeanArray;
	
	private String 	connIdRequest;
	private String 	connType;
	
	static ArrayList<SolicitudesBean>	solicitudesNuevasBean;
	static ArrayList<SolicitudesBean>	solicitudesAbiertasBean;
	NotificationsUpdateBean				notificationsUpdateBean;
	
	int 	success;
	
	boolean busy = false;
	
	public CheckClosureTask(String idRequest, String type) {
		BasicNameValuePair basicId 		= new BasicNameValuePair("idRequest", idRequest);
		BasicNameValuePair basicType 	= new BasicNameValuePair("type", type);
		
		connIdRequest 	= basicId.getValue().toString();
		connType		= basicType.getValue().toString();
		
		progressDialog = null;
		activity = null;
		
	}
	
	public CheckClosureTask(RequestDetailActivity activity, ProgressDialog progressDialog) {
		this.activity 		= activity;
		this.progressDialog = progressDialog;
	}
	
	protected void onPreExecute(){
		progressDialog.show();
	}
	
	protected Integer doInBackground(String... params) {
		BasicNameValuePair basicId 		= new BasicNameValuePair("idRequest", params[0]);
		BasicNameValuePair basicType 	= new BasicNameValuePair("type", params[1]);
		
		connIdRequest 	= basicId.getValue().toString();
		connType 		= basicType.getValue().toString();
		int success = 0;
		descripcionTrabajoBeanArray = new ArrayList<DescripcionTrabajoBean>();

		if(connType.equals(Constants.SUCCESS_CLOSURE)){ // CIERRE EXITOSO
			validateClosureBean = HTTPConnections.validateClosure(connIdRequest, 0);
			setValidateClosureBean(validateClosureBean);
				
			if(validateClosureBean.getConnStatus() == 200){
				
				if(validateClosureBean.getVal().equals(Constants.VALIDATION_TRUE)){
					success = Constants.VALIDATE_NUM_TRUE;
					
					descripcionTrabajoBeanArray = HTTPConnections.getDescripcionTrabajoCatalogo();
				}
					
				else if(validateClosureBean.getVal().equals(Constants.VALIDATION_INSTALACION))
					success = Constants.VALIDATE_NUM_INSTALACION;
				else if(validateClosureBean.getVal().equals(Constants.VALIDATION_INSUMO))
					success = Constants.VALIDATE_NUM_INSUMO;
				else if(validateClosureBean.getVal().equals(Constants.VALIDATION_RETIRO))
					success = Constants.VALIDATE_NUM_RETIRO;
				else if(validateClosureBean.getVal().equals(Constants.VALIDATION_SUSTITUCION))
					success = Constants.VALIDATE_NUM_SUSTITUCION;
				//  NEW CASE OF ERROR (17-10-2014)
				else if(validateClosureBean.getVal().trim().equals(Constants.VALIDATION_FILE))
					success = Constants.VALIDATE_NUM_FILE;
				else
					success = Constants.VALIDATE_NUM_INVALIDO;
			}
			else{
				success = Constants.VALIDATE_NUM_INVALIDO;
				validateClosureBean.setDesc("Error al contactar con el servidor");
			}
		}
		else{ // CIERRE RECHAZO
			validateRejectClosureBean = HTTPConnections.validateRejectClosure(connIdRequest);
			setValidateRejectClosureBean(validateRejectClosureBean);
			setValidateRejectClosureBean(validateRejectClosureBean);
			
			if(validateRejectClosureBean.getConnStatus() == 200){
				if(validateRejectClosureBean.getVal().trim().equals(Constants.VALIDATION_TRUE))
					success = Constants.VALIDATE_NUM_TRUE;
				else if(validateRejectClosureBean.getVal().trim().equals(Constants.VALIDATION_INSTALACION))
					success = Constants.VALIDATE_NUM_INSTALACION;
				else if(validateRejectClosureBean.getVal().trim().equals(Constants.VALIDATION_INSUMO))
					success = Constants.VALIDATE_NUM_INSUMO;
				else if(validateRejectClosureBean.getVal().trim().equals(Constants.VALIDATION_RETIRO))
					success = Constants.VALIDATE_NUM_RETIRO;
				else if(validateRejectClosureBean.getVal().trim().equals(Constants.VALIDATION_SUSTITUCION))
					success = Constants.VALIDATE_NUM_SUSTITUCION;
				//  NEW CASE OF ERROR (17-10-2014)
				else if(validateRejectClosureBean.getVal().trim().equals(Constants.VALIDATION_FILE))
					success = Constants.VALIDATE_NUM_FILE;
				else
					success = Constants.VALIDATE_NUM_INVALIDO;
			}
			else{
				success = Constants.VALIDATE_NUM_INVALIDO;
				validateClosureBean.setDesc("Error al contactar con el servidor");
			}
				
		}
		setSuccess(success);
		return success;
	}
	
	protected void onPostExecute(Integer headerCode){
		progressDialog.dismiss();
		
		success = getSuccess();
		
		if(connType.equals(Constants.SUCCESS_CLOSURE)){
			activity.sendValidationAnswer(success, validateClosureBean, descripcionTrabajoBeanArray);
		}
		else{
			activity.sendValidationAnswer(success, validateRejectClosureBean);
		}
	}
	
	public ValidateRejectClosureBean getValidateRejectClosureBean(){
		return validateRejectClosureBean;
	}
	
	public ValidateClosureBean getValidateClosureBean(){
		return validateClosureBean;
	}
	
	public void setValidateRejectClosureBean(ValidateRejectClosureBean validateRejectClosureBean){
		this.validateRejectClosureBean = validateRejectClosureBean;
	}
	
	public void setValidateClosureBean(ValidateClosureBean validateClosureBean){
		this.validateClosureBean = validateClosureBean;
	}
	
	public void setSuccess(int success){
		this.success = success;
	}
	
	public int getSuccess(){
		return success;
	}
}
