package com.artefacto.microformas.beans;

public class CodigosIntervencion2Bean {
	private String id;
	private String claveCodigo;
	private String idCliente;
	private String idParent1;
	private String descCodigo;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClaveCodigo() {
		return claveCodigo;
	}

	public void setClaveCodigo(String claveCodigo) {
		this.claveCodigo = claveCodigo;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getDescCodigo() {
		return descCodigo;
	}

	public void setDescCodigo(String descCodigo) {
		this.descCodigo = descCodigo;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}

	public String getIdParent1() {
		return idParent1;
	}

	public void setIdParent1(String idParent1) {
		this.idParent1 = idParent1;
	}
}