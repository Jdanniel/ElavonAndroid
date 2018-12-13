package com.artefacto.microformas.beans;

public class InsumosBean {
	private String id;
	private String idCliente;
	private String idTipoInsumo;
	private String descInsumo;
	
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

	public String getIdTipoInsumo() {
		return idTipoInsumo;
	}

	public void setIdTipoInsumo(String idTipoInsumo) {
		this.idTipoInsumo = idTipoInsumo;
	}

	public String getDescInsumo() {
		return descInsumo;
	}

	public void setDescInsumo(String descInsumo) {
		this.descInsumo = descInsumo;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}