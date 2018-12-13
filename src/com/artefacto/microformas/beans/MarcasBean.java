package com.artefacto.microformas.beans;

public class MarcasBean {
	private String id;
	private String idProducto;
	private String descMarca;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public String getDescMarca() {
		return descMarca;
	}

	public void setDescMarca(String descMarca) {
		this.descMarca = descMarca;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}