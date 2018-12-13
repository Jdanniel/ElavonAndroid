package com.artefacto.microformas.beans;

public class ClienteModelosBean {
	private String id;
	private String idCliente;
	private String idModelo;
	
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
	
	public String getIdModelo() {
		return idModelo;
	}
	
	public void setIdModelo(String idModelo) {
		this.idModelo = idModelo;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}