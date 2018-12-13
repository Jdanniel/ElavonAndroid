package com.artefacto.microformas.beans;

public class SendRechazoBean {
	private String idAR;
	private int    isIdCausaRechazoRequired;
	private String idCausaRechazo;
	private int    especificaCausaRechazoRequired;
	private String idEspecificaCausaRechazo;
	private int    isCausaSolucionRequired;
	private String idCausa;
	private String idSolucion;
	private int    isDescripcionTrabajoRechazoRequired; 
	private String descriptionTrabajo;
	private int	   isFolioServicioRechazoRequired; 
	private String folioServicioRechazo;
	private int    isOtorganteVoBoRechazoRequired;
	private String otorganteVoBoRechazo;
	private String autorizador;
	private String fecCierre;
	private String idTecnico;
	private String precioRechazo;
	
	// ADD CLAVE RECHAZO REQUIRED FIELD \( '.'   )
	private String claveRechazo;
	private int isClaveRechazoRequired;

	private int connStatus;

	public String getIdAR() {
		return idAR;
	}

	public void setIdAR(String idAR) {
		this.idAR = idAR;
	}

	public int getIsIdCausaRechazoRequired() {
		return isIdCausaRechazoRequired;
	}

	public void setIsIdCausaRechazoRequired(int isIdCausaRechazoRequired) {
		this.isIdCausaRechazoRequired = isIdCausaRechazoRequired;
	}

	public String getIdCausaRechazo() {
		return idCausaRechazo;
	}

	public void setIdCausaRechazo(String idCausaRechazo) {
		this.idCausaRechazo = idCausaRechazo;
	}

	public int getEspecificaCausaRechazoRequired() {
		return especificaCausaRechazoRequired;
	}

	public void setEspecificaCausaRechazoRequired(int especificaCausaRechazoRequired) {
		this.especificaCausaRechazoRequired = especificaCausaRechazoRequired;
	}

	public String getIdEspecificaCausaRechazo() {
		return idEspecificaCausaRechazo;
	}

	public void setIdEspecificaCausaRechazo(String idEspecificaCausaRechazo) {
		this.idEspecificaCausaRechazo = idEspecificaCausaRechazo;
	}

	public int getIsCausaSolucionRequired() {
		return isCausaSolucionRequired;
	}

	public void setIsCausaSolucionRequired(int isCausaSolucionRequired) {
		this.isCausaSolucionRequired = isCausaSolucionRequired;
	}

	public String getIdCausa() {
		return idCausa;
	}

	public void setIdCausa(String idCausa) {
		this.idCausa = idCausa;
	}

	public String getIdSolucion() {
		return idSolucion;
	}

	public void setIdSolucion(String idSolucion) {
		this.idSolucion = idSolucion;
	}

	public int getIsDescripcionTrabajoRechazoRequired() {
		return isDescripcionTrabajoRechazoRequired;
	}

	public void setIsDescripcionTrabajoRechazoRequired(
			int isDescripcionTrabajoRechazoRequired) {
		this.isDescripcionTrabajoRechazoRequired = isDescripcionTrabajoRechazoRequired;
	}

	public String getDescriptionTrabajo() {
		return descriptionTrabajo;
	}

	public void setDescriptionTrabajo(String descriptionTrabajo) {
		this.descriptionTrabajo = descriptionTrabajo;
	}

	public int getIsFolioServicioRechazoRequired() {
		return isFolioServicioRechazoRequired;
	}

	public void setIsFolioServicioRechazoRequired(int isFolioServicioRechazoRequired) {
		this.isFolioServicioRechazoRequired = isFolioServicioRechazoRequired;
	}

	public String getFolioServicioRechazo() {
		return folioServicioRechazo;
	}

	public void setFolioServicioRechazo(String folioServicioRechazo) {
		this.folioServicioRechazo = folioServicioRechazo;
	}

	public int getIsOtorganteVoBoRechazoRequired() {
		return isOtorganteVoBoRechazoRequired;
	}

	public void setIsOtorganteVoBoRechazoRequired(int isOtorganteVoBoRechazoRequired) {
		this.isOtorganteVoBoRechazoRequired = isOtorganteVoBoRechazoRequired;
	}

	public String getOtorganteVoBoRechazo() {
		return otorganteVoBoRechazo;
	}

	public void setOtorganteVoBoRechazo(String otorganteVoBoRechazo) {
		this.otorganteVoBoRechazo = otorganteVoBoRechazo;
	}

	public String getAutorizador() {
		return autorizador;
	}

	public void setAutorizador(String autorizador) {
		this.autorizador = autorizador;
	}

	public String getFecCierre() {
		return fecCierre;
	}

	public void setFecCierre(String fecCierre) {
		this.fecCierre = fecCierre;
	}

	public String getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(String idTecnico) {
		this.idTecnico = idTecnico;
	}

	public String getPrecioRechazo() {
		return precioRechazo;
	}

	public void setPrecioRechazo(String precioRechazo) {
		this.precioRechazo = precioRechazo;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
	
	// SETTERS AND GETTERS CLAVE_RECHAZO AND IS REQUIRED \( '.'   )

	public String getClaveRechazo() {
		return claveRechazo;
	}

	public void setClaveRechazo(String claveRechazo) {
		this.claveRechazo = claveRechazo;
	}

	public int getIsClaveRechazoRequired() {
		return isClaveRechazoRequired;
	}

	public void setIsClaveRechazoRequired(int isClaveRechazoRequired) {
		this.isClaveRechazoRequired = isClaveRechazoRequired;
	}

}