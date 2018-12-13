package com.artefacto.microformas.beans;

import java.io.Serializable;

public class InfoActualizacionBean implements Serializable {
	private String res;
	private String desc;
	
	private String idNegocio;
	private String idCliente;
	private String idStatus;
	private String isBom;
	private String isUnidadAtendida;
	private String isUnidadAtendidaRequired;
	private String descTecnico;
	private String descNegocio;
	
	private int connStatus;

	public String getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public String getIsBom() {
		return isBom;
	}

	public void setIsBom(String isBom) {
		this.isBom = isBom;
	}

	public String getIsUnidadAtendida() {
		return isUnidadAtendida;
	}

	public void setIsUnidadAtendida(String isUnidadAtendida) {
		this.isUnidadAtendida = isUnidadAtendida;
	}

	public String getIsUnidadAtendidaRequired() {
		return isUnidadAtendidaRequired;
	}

	public void setIsUnidadAtendidaRequired(String isUnidadAtendidaRequired) {
		this.isUnidadAtendidaRequired = isUnidadAtendidaRequired;
	}

	public String getDescTecnico() {
		return descTecnico;
	}

	public void setDescTecnico(String descTecnico) {
		this.descTecnico = descTecnico;
	}

	public String getDescNegocio() {
		return descNegocio;
	}

	public void setDescNegocio(String descNegocio) {
		this.descNegocio = descNegocio;
	}

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
}