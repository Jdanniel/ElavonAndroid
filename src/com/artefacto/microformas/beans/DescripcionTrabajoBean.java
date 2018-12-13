package com.artefacto.microformas.beans;

import java.io.Serializable;

public class DescripcionTrabajoBean implements Serializable{
	private String id;
	private String desc;
	private String status;
	private String idEspecifTipoFalla;
	private int    connStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIdEspecifTipoFalla() {
		return idEspecifTipoFalla;
	}
	public void setIdEspecifTipoFalla(String idEspecifTipoFalla) {
		this.idEspecifTipoFalla = idEspecifTipoFalla;
	}
}