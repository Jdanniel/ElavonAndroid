package com.artefacto.microformas.beans;

public class StatusBean {
	private String id;
	private String	isNuevas;
	private String isCerradas;
	private String isPendientes;
	private String isAbiertas;
	private String isSolicitudAlmacen;
	private String isSolicitudViaticos;
	private String activo;
	private String orden;
	private String active_mobile_notification;
	
	private String 	descStatus;
	private int		connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsNuevas() {
		return isNuevas;
	}

	public void setIsNuevas(String isNuevas) {
		this.isNuevas = isNuevas;
	}

	public String getIsCerradas() {
		return isCerradas;
	}

	public void setIsCerradas(String isCerradas) {
		this.isCerradas = isCerradas;
	}

	public String getIsPendientes() {
		return isPendientes;
	}

	public void setIsPendientes(String isPendientes) {
		this.isPendientes = isPendientes;
	}

	public String getIsAbiertas() {
		return isAbiertas;
	}

	public void setIsAbiertas(String isAbiertas) {
		this.isAbiertas = isAbiertas;
	}

	public String getIsSolicitudAlmacen() {
		return isSolicitudAlmacen;
	}

	public void setIsSolicitudAlmacen(String isSolicitudAlmacen) {
		this.isSolicitudAlmacen = isSolicitudAlmacen;
	}

	public String getIsSolicitudViaticos() {
		return isSolicitudViaticos;
	}

	public void setIsSolicitudViaticos(String isSolicitudViaticos) {
		this.isSolicitudViaticos = isSolicitudViaticos;
	}

	public String getDescStatus() {
		return descStatus;
	}

	public void setDescStatus(String descStatus) {
		this.descStatus = descStatus;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getActive_mobile_notification() {
		return active_mobile_notification;
	}

	public void setActive_mobile_notification(String active_mobile_notification) {
		this.active_mobile_notification = active_mobile_notification;
	}
}