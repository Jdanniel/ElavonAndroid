package com.artefacto.microformas.beans;

public class CausasRechazoBean {
	private String id;
	private String idCliente;
	private String descCausaRechazo;
	
	private int connStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getDescCausaRechazo() {
		return descCausaRechazo;
	}

	public void setDescCausaRechazo(String descCausaRechazo) {
		this.descCausaRechazo = descCausaRechazo;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}