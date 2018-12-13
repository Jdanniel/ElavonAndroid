package com.artefacto.microformas.beans;

import android.graphics.Bitmap;

public class RequestListBean {
	private Bitmap image;
	private String idRequest;
	private String descCliente;
	private String descServicio;
	private String fechaAlta;
	private String noAr;
	private String descNegocio;
	private String fechaGarantia;
	private String fechaAtencion;
	private String prefacturacion;
	private String comentario;
	private String idcliente;
	
	public String getDescNegocio() {
		return descNegocio;
	}
	public void setDescNegocio(String descNegocio) {
		this.descNegocio = descNegocio;
	}
	public String getFechaGarantia() {
		return fechaGarantia;
	}
	public void setFechaGarantia(String fechaGarantia) {
		this.fechaGarantia = fechaGarantia;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getIdRequest() {
		return idRequest;
	}
	public void setIdRequest(String idRequest) {
		this.idRequest = idRequest;
	}
	public String getDescCliente() {
		return descCliente;
	}
	public void setDescCliente(String descCliente) {
		this.descCliente = descCliente;
	}
	public String getDescServicio() {
		return descServicio;
	}
	public void setDescServicio(String descServicio) {
		this.descServicio = descServicio;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getNoAr() {
		return noAr;
	}
	public void setNoAr(String noAr) {
		this.noAr = noAr;
	}
	public String getPrefacturacion() {
		return prefacturacion;
	}
	public void setPrefacturacion(String prefacturacion) {
		this.prefacturacion = prefacturacion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getIdcliente(){return idcliente;}
	public void setIdcliente(String idcliente){this.idcliente = idcliente;}
	public String getFechaAtencion() {
		return fechaAtencion;
	}
	public void setFechaAtencion(String fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}
}