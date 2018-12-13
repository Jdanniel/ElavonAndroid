package com.artefacto.microformas.beans;

public class InfoMovimientosBean {

	private int connStatus;
	
	private String res;
	private String desc;
	
	private boolean instalacion;
	private boolean retiro;
	private boolean sustitucion;
	private boolean insumo;
	private boolean actualizacion;
	
	public int getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}	
	
	public boolean isInstalacion() {
		return instalacion;
	}
	public void setInstalacion(boolean instalacion) {
		this.instalacion = instalacion;
	}
	public boolean isRetiro() {
		return retiro;
	}
	public void setRetiro(boolean retiro) {
		this.retiro = retiro;
	}
	public boolean isSustitucion() {
		return sustitucion;
	}
	public void setSustitucion(boolean sustitucion) {
		this.sustitucion = sustitucion;
	}
	public boolean isInsumo() {
		return insumo;
	}
	public void setInsumo(boolean insumo) {
		this.insumo = insumo;
	}
	public boolean isActualizacion() {
		return actualizacion;
	}
	public void setActualizacion(boolean actualizacion) {
		this.actualizacion = actualizacion;
	}
}
