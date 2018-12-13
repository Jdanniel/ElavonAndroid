package com.artefacto.microformas.beans;

public class ModelosBean {
	private String id;
	private String idMarca;
	private String idGPRS;
	private String descModelo;
	private String sku;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}

	public String getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(String idMarca) {
		this.idMarca = idMarca;
	}

	public String getIdGPRS() {
		return idGPRS;
	}

	public void setIdGPRS(String idGPRS) {
		this.idGPRS = idGPRS;
	}

	public String getDescModelo() {
		return descModelo;
	}

	public void setDescModelo(String descModelo) {
		this.descModelo = descModelo;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}