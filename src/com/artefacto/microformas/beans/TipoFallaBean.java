package com.artefacto.microformas.beans;

public class TipoFallaBean {
	
	private String id;
	private String idServicio;
	private String idCliente;
	private String descTipoFalla;
	
	private int connStatus;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
	public String getIdServicio() {
		return idServicio;
	}
	
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	
	public int getConnStatus() {
		return connStatus;
	}
	
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
	
	public String getDescTipoFalla() {
		return descTipoFalla;
	}
	
	public void setDescTipoFalla(String descTipoFalla) {
		this.descTipoFalla = descTipoFalla;
	}

}
