package com.artefacto.microformas.beans;

public class CausasBean {
	private String id;
	private String idCliente;
	private String clave;
	private String descCausa;
	
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

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}

	public String getDescCausa() {
		return descCausa;
	}

	public void setDescCausa(String descCausa) {
		this.descCausa = descCausa;
	}
}