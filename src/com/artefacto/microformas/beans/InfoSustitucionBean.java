package com.artefacto.microformas.beans;

import java.util.ArrayList;

public class InfoSustitucionBean {
	
	private int connStatus;
	private int statusSust;
	
	private String res;
	private String val;
	private String desc;
	
	//data
	private String data_idNegocio;
	private String data_idCliente;
	private String data_isIdRequired;
	private String data_isBom;
	private String data_buttonDisplay;
	private String data_cancelButtonDisplay;
	private String data_edit;
	private String data_idTipoProducto;
	
	public int getStatusSust() {
		return statusSust;
	}

	public void setStatusSust(int statusSust) {
		this.statusSust = statusSust;
	}

	public static class Unidad{
		private String idUnidad;
		private String idModelo;
		private String idProducto;
		private String noUnidades;
		private String descCliente;
		private String isDisabled;
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
		public String getIdProducto() {
			return idProducto;
		}
		public void setIdProducto(String idProducto) {
			this.idProducto = idProducto;
		}
		public String getNoUnidades() {
			return noUnidades;
		}
		public void setNoUnidades(String noUnidades) {
			this.noUnidades = noUnidades;
		}
		public String getDescCliente() {
			return descCliente;
		}
		public void setDescCliente(String descCliente) {
			this.descCliente = descCliente;
		}
		public String getIsDisabled() {
			return isDisabled;
		}
		public void setIsDisabled(String isDisabled) {
			this.isDisabled = isDisabled;
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
	
	//sus (Sustitucion)
	
	public static class Sustitucion {
		private String s_idAR;
		private String s_idNegocio;
		private String s_idTecnico;
		private String s_idUnidadEntrada;
		private String s_idUnidadSalida;
		private String s_descCliente;
		private String s_descNegocio;
		private String s_descTecnico;
		private String s_noSerieEntrada;
		private String s_noSerieSalida;
		private String s_idTipoProducto;
		
		public String getS_idAR() {
			return s_idAR;
		}

		public void setS_idAR(String s_idAR) {
			this.s_idAR = s_idAR;
		}

		public String getS_idNegocio() {
			return s_idNegocio;
		}

		public void setS_idNegocio(String s_idNegocio) {
			this.s_idNegocio = s_idNegocio;
		}

		public String getS_idTecnico() {
			return s_idTecnico;
		}

		public void setS_idTecnico(String s_idTecnico) {
			this.s_idTecnico = s_idTecnico;
		}

		public String getS_idUnidadEntrada() {
			return s_idUnidadEntrada;
		}

		public void setS_idUnidadEntrada(String s_idUnidadEntrada) {
			this.s_idUnidadEntrada = s_idUnidadEntrada;
		}

		public String getS_idUnidadSalida() {
			return s_idUnidadSalida;
		}

		public void setS_idUnidadSalida(String s_idUnidadSalida) {
			this.s_idUnidadSalida = s_idUnidadSalida;
		}

		public String getS_descCliente() {
			return s_descCliente;
		}

		public void setS_descCliente(String s_descCliente) {
			this.s_descCliente = s_descCliente;
		}

		public String getS_descNegocio() {
			return s_descNegocio;
		}

		public void setS_descNegocio(String s_descNegocio) {
			this.s_descNegocio = s_descNegocio;
		}

		public String getS_descTecnico() {
			return s_descTecnico;
		}

		public void setS_descTecnico(String s_descTecnico) {
			this.s_descTecnico = s_descTecnico;
		}

		public String getS_noSerieEntrada() {
			return s_noSerieEntrada;
		}

		public void setS_noSerieEntrada(String s_noSerieEntrada) {
			this.s_noSerieEntrada = s_noSerieEntrada;
		}

		public String getS_noSerieSalida() {
			return s_noSerieSalida;
		}

		public void setS_noSerieSalida(String s_noSerieSalida) {
			this.s_noSerieSalida = s_noSerieSalida;
		}

		public String getS_idTipoProducto() {
			return s_idTipoProducto;
		}

		public void setS_idTipoProducto(String s_idTipoProducto) {
			this.s_idTipoProducto = s_idTipoProducto;
		}
	}

	private ArrayList<Unidad> unidadesNegocio;
	private ArrayList<Unidad> unidadesTecnico;
	private ArrayList<Sustitucion> sustituciones;
	
	
	private String error;

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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getData_idNegocio() {
		return data_idNegocio;
	}

	public void setData_idNegocio(String data_idNegocio) {
		this.data_idNegocio = data_idNegocio;
	}

	public String getData_idCliente() {
		return data_idCliente;
	}

	public void setData_idCliente(String data_idCliente) {
		this.data_idCliente = data_idCliente;
	}

	public String getData_isIdRequired() {
		return data_isIdRequired;
	}

	public void setData_isIdRequired(String data_isIdRequired) {
		this.data_isIdRequired = data_isIdRequired;
	}

	public String getData_isBom() {
		return data_isBom;
	}

	public void setData_isBom(String data_isBom) {
		this.data_isBom = data_isBom;
	}

	public String getData_buttonDisplay() {
		return data_buttonDisplay;
	}

	public void setData_buttonDisplay(String data_buttonDisplay) {
		this.data_buttonDisplay = data_buttonDisplay;
	}

	public String getData_cancelButtonDisplay() {
		return data_cancelButtonDisplay;
	}

	public void setData_cancelButtonDisplay(String data_cancelButtonDisplay) {
		this.data_cancelButtonDisplay = data_cancelButtonDisplay;
	}

	public String getData_edit() {
		return data_edit;
	}

	public void setData_edit(String data_edit) {
		this.data_edit = data_edit;
	}

	public String getData_idTipoProducto() {
		return data_idTipoProducto;
	}

	public void setData_idTipoProducto(String data_idTipoProducto) {
		this.data_idTipoProducto = data_idTipoProducto;
	}

	public ArrayList<Unidad> getUnidadesNegocio() {
		return unidadesNegocio;
	}

	public void setUnidadesNegocio(ArrayList<Unidad> unidadesNegocio) {
		this.unidadesNegocio = unidadesNegocio;
	}

	public ArrayList<Unidad> getUnidadesTecnico() {
		return unidadesTecnico;
	}

	public void setUnidadesTecnico(ArrayList<Unidad> unidadesTecnico) {
		this.unidadesTecnico = unidadesTecnico;
	}

	public ArrayList<Sustitucion> getSustituciones() {
		return sustituciones;
	}

	public void setSustituciones(ArrayList<Sustitucion> sustituciones) {
		this.sustituciones = sustituciones;
	}

	
}
