package com.artefacto.microformas.beans;

public class SparePartsBean {
	private String id;
	private String descSparePart;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescSparePart() {
		return descSparePart;
	}

	public void setDescSparePart(String descSparePart) {
		this.descSparePart = descSparePart;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}
