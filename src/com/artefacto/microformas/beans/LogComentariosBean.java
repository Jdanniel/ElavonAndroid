package com.artefacto.microformas.beans;

import java.io.Serializable;

public class LogComentariosBean implements Serializable{
	private String descTipoUsuario;
	private String nombre;
	private String tipoComentario;
	private String fecAlta;
	private String descComentario;
	
	private int connStatus;

	public String getDescTipoUsuario() {
		return descTipoUsuario;
	}

	public void setDescTipoUsuario(String descTipoUsuario) {
		this.descTipoUsuario = descTipoUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoComentario() {
		return tipoComentario;
	}

	public void setTipoComentario(String tipoComentario) {
		this.tipoComentario = tipoComentario;
	}

	public String getFecAlta() {
		return fecAlta;
	}

	public void setFecAlta(String fecAlta) {
		this.fecAlta = fecAlta;
	}

	public String getDescComentario() {
		return descComentario;
	}

	public void setDescComentario(String descComentario) {
		this.descComentario = descComentario;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}	
}