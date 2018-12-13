package com.artefacto.microformas.beans;

public class ServiciosSolucionesBean {
	private String id;
	private String idServicio;
	private String idSolucion;
	private int connStatus;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdServicio() {
		return idServicio;
	}
	
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	
	public String getIdSolucion() {
		return idSolucion;
	}
	
	public void setIdSolucion(String idSolucion) {
		this.idSolucion = idSolucion;
	}
	
	public int getConnStatus() {
		return connStatus;
	}
	
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}

}
