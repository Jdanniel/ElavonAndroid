package com.artefacto.microformas.beans;

public class SolucionesBean
{
	private String id;
	private String idCliente;
	private String clave;
	private String isExito;
	private String descSolucion;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getDescSolucion() {
		return descSolucion;
	}

	public void setDescSolucion(String descSolucion) {
		this.descSolucion = descSolucion;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getIsExito() {
		return isExito;
	}

	public void setIsExito(String isExito) {
		this.isExito = isExito;
	}
}