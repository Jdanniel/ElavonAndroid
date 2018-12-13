package com.artefacto.microformas.beans;

public class ServiciosCausasBean {
	private String id;
	private String idServicio;
	private String idCausa;
	private int connStatus;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdServicio() {
		return idServicio;
	}
	
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	
	public String getIdCausa() {
		return idCausa;
	}
	
	public void setIdCausa(String idCausa) {
		this.idCausa = idCausa;
	}
	
	public int getConnStatus() {
		return connStatus;
	}
	
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}

}
