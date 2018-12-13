package com.artefacto.microformas.beans;

import java.io.Serializable;

public class RefaccionesUnidadBean implements Serializable{
	private String idUnidad;
	private String idNegocio;
	private String descNegocio;
	private String idCliente;
	private String descCliente;
	private String idMarca;
	private String descMarca;
	private String idModelo;
	private String descModelo;
	private String noSerie;
	private String noInventario;
	private String idStatusUnidad;
	private String idTipoProducto;
	private String descStatusUnidad;
	
	private int connStatus;

	public String getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}

	public String getDescCliente() {
		return descCliente;
	}

	public void setDescCliente(String descCliente) {
		this.descCliente = descCliente;
	}

	public String getDescMarca() {
		return descMarca;
	}

	public void setDescMarca(String descMarca) {
		this.descMarca = descMarca;
	}

	public String getDescModelo() {
		return descModelo;
	}

	public void setDescModelo(String descModelo) {
		this.descModelo = descModelo;
	}

	public String getNoSerie() {
		return noSerie;
	}

	public void setNoSerie(String noSerie) {
		this.noSerie = noSerie;
	}

	public String getNoInventario() {
		return noInventario;
	}

	public void setNoInventario(String noInventario) {
		this.noInventario = noInventario;
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

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getDescNegocio() {
		return descNegocio;
	}

	public void setDescNegocio(String descNegocio) {
		this.descNegocio = descNegocio;
	}

	public String getDescStatusUnidad() {
		return descStatusUnidad;
	}

	public void setDescStatusUnidad(String descStatusUnidad) {
		this.descStatusUnidad = descStatusUnidad;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(String idMarca) {
		this.idMarca = idMarca;
	}

	public String getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(String idModelo) {
		this.idModelo = idModelo;
	}

	public String getIdTipoProducto() {
		return idTipoProducto;
	}

	public void setIdTipoProducto(String idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
}