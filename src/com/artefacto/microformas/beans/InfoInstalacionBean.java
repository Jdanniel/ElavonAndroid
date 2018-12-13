package com.artefacto.microformas.beans;

import java.util.ArrayList;

public class InfoInstalacionBean {

	private int connStatus;
	
	private String res;
	private String desc;
	
	private String idNegocio;
	private String idCliente;
	private String idIsReq;
	private String idIsBom;
	private String buttonDisplay;
	private String cancelButtonDisplay;
	private String edit;
	private String idTipoProducto;
	
	private ArrayList<Unidad> unidadesNegocio;
	
	public ArrayList<Unidad> getUnidadesNegocio() {
		return unidadesNegocio;
	}
	public void setUnidadesNegocio(ArrayList<Unidad> unidadesNegocio) {
		this.unidadesNegocio = unidadesNegocio;
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
	public String getIdIsReq() {
		return idIsReq;
	}
	public void setIdIsReq(String idIsReq) {
		this.idIsReq = idIsReq;
	}
	public String getIdIsBom() {
		return idIsBom;
	}
	public void setIdIsBom(String idIsBom) {
		this.idIsBom = idIsBom;
	}
	public String getButtonDisplay() {
		return buttonDisplay;
	}
	public void setButtonDisplay(String buttonDisplay) {
		this.buttonDisplay = buttonDisplay;
	}
	public String getCancelButtonDisplay() {
		return cancelButtonDisplay;
	}
	public void setCancelButtonDisplay(String cancelButtonDisplay) {
		this.cancelButtonDisplay = cancelButtonDisplay;
	}
	public String getEdit() {
		return edit;
	}
	public void setEdit(String edit) {
		this.edit = edit;
	}
	public String getIdTipoProducto() {
		return idTipoProducto;
	}
	public void setIdTipoProducto(String idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
	
	public static class Unidad{
		
		private String idUnidad;
		private String idModelo;
		private String descModelo;
		private String descMarca;
		private String noSerie;
		private String noInventario;
		private String noIMEI;
		private String noEquipo;
		private String posicionInventario;
		private String descStatusUnidad;
		private String idStatusUnidad;
		private String descNegocio;
		
		private String idProducto;

		public String getIdUnidad() {
			return idUnidad;
		}
		public void setIdUnidad(String idUnidad) {
			this.idUnidad = idUnidad;
		}
		public String getIdModelo() {
			return idModelo;
		}
		public void setIdModelo(String idModelo) {
			this.idModelo = idModelo;
		}
		public String getDescModelo() {
			return descModelo;
		}
		public void setDescModelo(String descModelo) {
			this.descModelo = descModelo;
		}
		public String getDescMarca() {
			return descMarca;
		}
		public void setDescMarca(String descMarca) {
			this.descMarca = descMarca;
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
		public String getNoIMEI() {
			return noIMEI;
		}
		public void setNoIMEI(String noIMEI) {
			this.noIMEI = noIMEI;
		}
		public String getNoEquipo() {
			return noEquipo;
		}
		public void setNoEquipo(String noEquipo) {
			this.noEquipo = noEquipo;
		}
		public String getPosicionInventario() {
			return posicionInventario;
		}
		public void setPosicionInventario(String posicionInventario) {
			this.posicionInventario = posicionInventario;
		}
		public String getDescStatusUnidad() {
			return descStatusUnidad;
		}
		public void setDescStatusUnidad(String descStatusUnidad) {
			this.descStatusUnidad = descStatusUnidad;
		}
		public String getIdStatusUnidad() {
			return idStatusUnidad;
		}
		public void setIdStatusUnidad(String idStatusUnidad) {
			this.idStatusUnidad = idStatusUnidad;
		}
		public String getDescNegocio() {
			return descNegocio;
		}
		public void setDescNegocio(String descNegocio) {
			this.descNegocio = descNegocio;
		}
		public String getIdProducto() {
			return idProducto;
		}
		public void setIdProducto(String idProducto) {
			this.idProducto = idProducto;
		}
	}
}