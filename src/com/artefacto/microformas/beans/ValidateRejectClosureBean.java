package com.artefacto.microformas.beans;

import java.io.Serializable;

//trabaja en conjunto con ValidarCierreRechazo.asp
public class ValidateRejectClosureBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private int connStatus;
	
	private String res;
	private String val;
	private String desc;
	
	private String idAR;
	private String idServicio;
	private String idCliente;
	private String descMoneda;
	private String descTipoCobro;
	private String descTipoPrecio;
	private String descripcionTrabajo;
	private String horasAtencion;
	private String idTipoCobro;
	private String idTipoPrecio;
	private String isCobrable;
	private String precioRechazo;
	private String isCausaSolucionRequired;
	private String isCausaRequired;
	private String isSolucionRequired;
	private String isFolioServicioRechazoRequired;
	private String isOtorganteVoBoRechazoRequired; 
	private String isDescripcionTrabajoRechazoRequired;
	private String isIdTipoFallaEncontradaRequired;
	private String isEspecificaTipoFalla;
	private String isIdCausaRechazoRequired;
	private String otorganteVoBo;
	private String fecInicio;
	
	// ADD CLAVE RECHAZO REQUIRED FIELD \( '.'   )
	private String claveRechazo;
	private String isClaveRechazoRequired;
	
	public int getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
	public String getIdAR() {
		return idAR;
	}
	public void setIdAR(String idAR) {
		this.idAR = idAR;
	}
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getDescMoneda() {
		return descMoneda;
	}
	public void setDescMoneda(String descMoneda) {
		this.descMoneda = descMoneda;
	}
	public String getDescTipoCobro() {
		return descTipoCobro;
	}
	public void setDescTipoCobro(String descTipoCobro) {
		this.descTipoCobro = descTipoCobro;
	}
	public String getDescTipoPrecio() {
		return descTipoPrecio;
	}
	public void setDescTipoPrecio(String descTipoPrecio) {
		this.descTipoPrecio = descTipoPrecio;
	}
	public String getDescripcionTrabajo() {
		return descripcionTrabajo;
	}
	public void setDescripcionTrabajo(String descripcionTrabajo) {
		this.descripcionTrabajo = descripcionTrabajo;
	}
	public String getHorasAtencion() {
		return horasAtencion;
	}
	public void setHorasAtencion(String horasAtencion) {
		this.horasAtencion = horasAtencion;
	}
	public String getIdTipoCobro() {
		return idTipoCobro;
	}
	public void setIdTipoCobro(String idTipoCobro) {
		this.idTipoCobro = idTipoCobro;
	}
	public String getIdTipoPrecio() {
		return idTipoPrecio;
	}
	public void setIdTipoPrecio(String idTipoPrecio) {
		this.idTipoPrecio = idTipoPrecio;
	}
	public String getIsCobrable() {
		return isCobrable;
	}
	public void setIsCobrable(String isCobrable) {
		this.isCobrable = isCobrable;
	}
	public String getPrecioRechazo() {
		return precioRechazo;
	}
	public void setPrecioRechazo(String precioRechazo) {
		this.precioRechazo = precioRechazo;
	}
	public String getIsCausaSolucionRequired() {
		return isCausaSolucionRequired;
	}
	public void setIsCausaSolucionRequired(String isCausaSolucionRequired) {
		this.isCausaSolucionRequired = isCausaSolucionRequired;
	}
	public String getIsCausaRequired() {
		return isCausaRequired;
	}
	public void setIsCausaRequired(String isCausaRequired) {
		this.isCausaRequired = isCausaRequired;
	}
	public String getIsSolucionRequired() {
		return isSolucionRequired;
	}
	public void setIsSolucionRequired(String isSolucionRequired) {
		this.isSolucionRequired = isSolucionRequired;
	}
	public String getIsFolioServicioRechazoRequired() {
		return isFolioServicioRechazoRequired;
	}
	public void setIsFolioServicioRechazoRequired(
			String isFolioServicioRechazoRequired) {
		this.isFolioServicioRechazoRequired = isFolioServicioRechazoRequired;
	}
	public String getIsOtorganteVoBoRechazoRequired() {
		return isOtorganteVoBoRechazoRequired;
	}
	public void setIsOtorganteVoBoRechazoRequired(
			String isOtorganteVoBoRechazoRequired) {
		this.isOtorganteVoBoRechazoRequired = isOtorganteVoBoRechazoRequired;
	}
	public String getIsDescripcionTrabajoRechazoRequired() {
		return isDescripcionTrabajoRechazoRequired;
	}
	public void setIsDescripcionTrabajoRechazoRequired(
			String isDescripcionTrabajoRechazoRequired) {
		this.isDescripcionTrabajoRechazoRequired = isDescripcionTrabajoRechazoRequired;
	}
	public String getIsIdTipoFallaEncontradaRequired() {
		return isIdTipoFallaEncontradaRequired;
	}
	public void setIsIdTipoFallaEncontradaRequired(
			String isIdTipoFallaEncontradaRequired) {
		this.isIdTipoFallaEncontradaRequired = isIdTipoFallaEncontradaRequired;
	}
	public String getIsEspecificaTipoFalla() {
		return isEspecificaTipoFalla;
	}
	public void setIsEspecificaTipoFalla(String isEspecificaTipoFalla) {
		this.isEspecificaTipoFalla = isEspecificaTipoFalla;
	}
	public String getIsIdCausaRechazoRequired() {
		return isIdCausaRechazoRequired;
	}
	public void setIsIdCausaRechazoRequired(String isIdCausaRechazoRequired) {
		this.isIdCausaRechazoRequired = isIdCausaRechazoRequired;
	}
	public String getOtorganteVoBo() {
		return otorganteVoBo;
	}
	public void setOtorganteVoBo(String otorganteVoBo) {
		this.otorganteVoBo = otorganteVoBo;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFecInicio() {
		return fecInicio;
	}
	public void setFecInicio(String fecInicio) {
		this.fecInicio = fecInicio;
	}
	
	// SETTERS AND GETTERS CLAVE_RECHAZO AND IS REQUIRED \( '.'   )
	
	public String getClaveRechazo() {
		return this.claveRechazo;
	}
	
	public void setClaveRechazo(String value) {
		this.claveRechazo = value;
	}
	
	public String getIsClaveRechazoRequired() {
		return this.isClaveRechazoRequired;
	}
	
	public void setIsClaveRechazoRequired(String value) {
		this.isClaveRechazoRequired = value;
	}
}