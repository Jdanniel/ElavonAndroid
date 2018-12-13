package com.artefacto.microformas.beans;

public class SKUBean {
	private String sku;
	private String idModelo;
	private String idMarca;
	private String descMarca;
	private String descripcion;
	private String descModelo;
	
	private int    connStatus;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(String idModelo) {
		this.idModelo = idModelo;
	}
	public String getDescMarca() {
		return descMarca;
	}
	public void setDescMarca(String descMarca) {
		this.descMarca = descMarca;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescModelo() {
		return descModelo;
	}
	public void setDescModelo(String descModelo) {
		this.descModelo = descModelo;
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
}