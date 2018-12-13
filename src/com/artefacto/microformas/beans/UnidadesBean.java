package com.artefacto.microformas.beans;

public class UnidadesBean
{
	private String id;
	private String idStatusUnidad;

	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdStatusUnidad() {
		return idStatusUnidad;
	}

	public void setIdStatusUnidad(String idStatusUnidad) {
		this.idStatusUnidad = idStatusUnidad;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}
