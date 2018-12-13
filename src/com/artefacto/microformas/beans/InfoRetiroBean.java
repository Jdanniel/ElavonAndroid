package com.artefacto.microformas.beans;

import java.util.ArrayList;

//import com.artefacto.microformas.beans.InfoRetiroBean.Unidad;

public class InfoRetiroBean {
	private int connStatus;
	private int statusRet;
	
	private String res;
	private String val;
	private String desc;
	
	private String idNegocio;
	private String idCliente;
	private String isBom;
	private String buttonDisplay;
	private String cancelButtonDisplay;
	private String edit;
	private String idTipoProducto;
	
	private ArrayList<Unidad> unidadesNegocio;
	
	private ArrayList<UnidadRetiro> unidadesRetiro;
	
	
	public int getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
	public int getStatusRet() {
		return statusRet;
	}
	public void setStatusRet(int statusRet) {
		this.statusRet = statusRet;
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
	public String getIsBom() {
		return isBom;
	}
	public void setIsBom(String isBom) {
		this.isBom = isBom;
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
	}
	
	
	public static class UnidadRetiro{
		private String IdUnidad;
		private String IdModelo;
		private String DescModelo;
		private String DescMarca;
		private String NoSerie;
		private String NoInventario;
		private String NoIMEI;
		private String NoEquipo;
		private String PosicionInventario;
		private String DescStatusUnidad;
		private String IdStatusUnidad;
		private String IdTipoUnidad;
		public String getIdUnidad() {
			return IdUnidad;
		}
		public void setIdUnidad(String idUnidad) {
			IdUnidad = idUnidad;
		}
		public String getIdModelo() {
			return IdModelo;
		}
		public void setIdModelo(String idModelo) {
			IdModelo = idModelo;
		}
		public String getDescModelo() {
			return DescModelo;
		}
		public void setDescModelo(String descModelo) {
			DescModelo = descModelo;
		}
		public String getDescMarca() {
			return DescMarca;
		}
		public void setDescMarca(String descMarca) {
			DescMarca = descMarca;
		}
		public String getNoSerie() {
			return NoSerie;
		}
		public void setNoSerie(String noSerie) {
			NoSerie = noSerie;
		}
		public String getNoInventario() {
			return NoInventario;
		}
		public void setNoInventario(String noInventario) {
			NoInventario = noInventario;
		}
		public String getNoIMEI() {
			return NoIMEI;
		}
		public void setNoIMEI(String noIMEI) {
			NoIMEI = noIMEI;
		}
		public String getNoEquipo() {
			return NoEquipo;
		}
		public void setNoEquipo(String noEquipo) {
			NoEquipo = noEquipo;
		}
		public String getPosicionInventario() {
			return PosicionInventario;
		}
		public void setPosicionInventario(String posicionInventario) {
			PosicionInventario = posicionInventario;
		}
		public String getDescStatusUnidad() {
			return DescStatusUnidad;
		}
		public void setDescStatusUnidad(String descStatusUnidad) {
			DescStatusUnidad = descStatusUnidad;
		}
		public String getIdStatusUnidad() {
			return IdStatusUnidad;
		}
		public void setIdStatusUnidad(String idStatusUnidad) {
			IdStatusUnidad = idStatusUnidad;
		}
		public String getIdTipoUnidad() {
			return IdTipoUnidad;
		}
		public void setIdTipoUnidad(String idTipoUnidad) {
			IdTipoUnidad = idTipoUnidad;
		}
	}

	public ArrayList<Unidad> getUnidadesNegocio() {
		return unidadesNegocio;
	}
	public void setUnidadesNegocio(ArrayList<Unidad> unidadesNegocio) {
		this.unidadesNegocio = unidadesNegocio;
	}
	public ArrayList<UnidadRetiro> getUnidadesRetiro() {
		return unidadesRetiro;
	}
	public void setUnidadesRetiro(ArrayList<UnidadRetiro> unidadesRetiro) {
		this.unidadesRetiro = unidadesRetiro;
	}
}