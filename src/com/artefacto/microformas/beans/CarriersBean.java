package com.artefacto.microformas.beans;

import java.io.Serializable;

public class CarriersBean implements Serializable{
	private String id;
	private String descCarrier;
	private int    connStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescCarrier() {
		return descCarrier;
	}
	public void setDescCarrier(String descCarrier) {
		this.descCarrier = descCarrier;
	}
	public int getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}