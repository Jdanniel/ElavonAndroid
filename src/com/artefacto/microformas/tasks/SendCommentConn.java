package com.artefacto.microformas.tasks;

import com.artefacto.microformas.beans.ConnTaskResultBean;
import com.artefacto.microformas.connection.HTTPConnections;

public class SendCommentConn {
	private String idRequest;
	private String idUser;
	private String comment;
	
	boolean busy;

	public SendCommentConn(String idRequest, String idUser, String comment) {
		this.idRequest = idRequest;
		this.idUser = idUser;
		this.comment = comment;
	}
	
	public ConnTaskResultBean sendComment(){
		ConnTaskResultBean ret = new ConnTaskResultBean();
		
		String res = HTTPConnections.sendComment(idRequest, idUser, comment);
		ret.setSuccess(0);
		
		if(res.equals("OK")){
			ret.setSuccess(1);
			ret.setStatus("Enviado con éxito");
		} else {
			ret.setStatus(res);
		}
		return ret;
	}
	
}
