package com.artefacto.microformas.beans;

public class EspecificacionTipoFallaBean {
	
	private String id;
	private String idFallaParent;
	private String descEspecificacionFalla;
	private int connStatus;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdFallaParent() {
		return idFallaParent;
	}
	public void setIdFallaParent(String idFallaParent) {
		this.idFallaParent = idFallaParent;
	}
	public String getDescEspecificacionFalla() {
		return descEspecificacionFalla;
	}
	public void setDescEspecificacionFalla(String descEspecificacionFalla) {
		this.descEspecificacionFalla = descEspecificacionFalla;
	}
	public int getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
	
	

}
