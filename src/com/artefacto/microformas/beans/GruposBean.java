package com.artefacto.microformas.beans;

public class GruposBean {
	private String id;
	private String descGrupo;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescGrupo() {
		return descGrupo;
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}