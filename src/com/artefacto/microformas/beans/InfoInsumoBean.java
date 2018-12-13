package com.artefacto.microformas.beans;

import java.util.ArrayList;

public class InfoInsumoBean {
	private int connStatus;
	private int statusRet;
	
	private String res;
	private String val;
	private String desc;
	
	private String noInsumosRollo;
	private String idCliente;
	
	private ArrayList<Insumo> insumos;
	
	public static class Insumo{
		private String idArInsumo;
		private String descInsumo;
		private String descUsuario;
		private String fecAlta;
		private String cantidad;
		private String costoUnitario;
		private String costoTotal;
		
		public String getIdArInsumo() {
			return idArInsumo;
		}
		public void setIdArInsumo(String idArInsumo) {
			this.idArInsumo = idArInsumo;
		}
		public String getDescInsumo() {
			return descInsumo;
		}
		public void setDescInsumo(String descInsumo) {
			this.descInsumo = descInsumo;
		}
		public String getDescUsuario() {
			return descUsuario;
		}
		public void setDescUsuario(String descUsuario) {
			this.descUsuario = descUsuario;
		}
		public String getFecAlta() {
			return fecAlta;
		}
		public void setFecAlta(String fecAlta) {
			this.fecAlta = fecAlta;
		}
		public String getCantidad() {
			return cantidad;
		}
		public void setCantidad(String cantidad) {
			this.cantidad = cantidad;
		}
		public String getCostoUnitario() {
			return costoUnitario;
		}
		public void setCostoUnitario(String costoUnitario) {
			this.costoUnitario = costoUnitario;
		}
		public String getCostoTotal() {
			return costoTotal;
		}
		public void setCostoTotal(String costoTotal) {
			this.costoTotal = costoTotal;
		}
		
		
	}

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

	public String getNoInsumosRollo() {
		return noInsumosRollo;
	}

	public void setNoInsumosRollo(String noInsumosRollo) {
		this.noInsumosRollo = noInsumosRollo;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public ArrayList<Insumo> getInsumos() {
		return insumos;
	}

	public void setInsumos(ArrayList<Insumo> insumos) {
		this.insumos = insumos;
	}
	
	
	
}
