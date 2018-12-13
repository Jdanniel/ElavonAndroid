package com.artefacto.microformas.beans;

public class MSparePartsBean {
	private String id;
	private String idModelo;
	private String idSparePart;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(String idModelo) {
		this.idModelo = idModelo;
	}

	public String getIdSparePart() {
		return idSparePart;
	}

	public void setIdSparePart(String idSparePart) {
		this.idSparePart = idSparePart;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}