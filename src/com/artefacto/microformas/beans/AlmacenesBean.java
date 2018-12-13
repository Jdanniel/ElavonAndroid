package com.artefacto.microformas.beans;

public class AlmacenesBean {
	private String id;
	private String descAlmacen;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescAlmacen() {
		return descAlmacen;
	}

	public void setDescAlmacen(String descAlmacen) {
		this.descAlmacen = descAlmacen;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}