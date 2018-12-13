package com.artefacto.microformas.beans;

public class CodigosIntervencion1Bean {
	private String id;
	private String claveCodigo;
	private String idCliente;
	private String idParent0;
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

	public String getIdParent0() {
		return idParent0;
	}

	public void setIdParent0(String idParent0) {
		this.idParent0 = idParent0;
	}
}