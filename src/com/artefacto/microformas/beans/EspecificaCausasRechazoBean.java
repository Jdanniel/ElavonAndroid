package com.artefacto.microformas.beans;

public class EspecificaCausasRechazoBean {
	private String id;
	private String idCausaRechazoParent;
	private String descEspeficicacionCausaRechazo;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCausaRechazoParent() {
		return idCausaRechazoParent;
	}

	public void setIdCausaRechazoParent(String idCausaRechazoParent) {
		this.idCausaRechazoParent = idCausaRechazoParent;
	}

	public String getDescEspeficicacionCausaRechazo() {
		return descEspeficicacionCausaRechazo;
	}

	public void setDescEspeficicacionCausaRechazo(
			String descEspeficicacionCausaRechazo) {
		this.descEspeficicacionCausaRechazo = descEspeficicacionCausaRechazo;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}