package com.artefacto.microformas.beans;

import java.io.Serializable;
import java.lang.String;

//trabaja en conjunto con validar Cierre.asp
@SuppressWarnings("serial")
public class ValidateClosureBean implements Serializable{

	private int	connStatus;
	
	private String idAR;
	private String idServicio;
	private String idCliente;
	private String isWincor;
	private String idCausa;
	private String idSolucion;
	private String noEquipo;
	private String descripcionTrabajo;
	private String atiende;
	private String folioTas;
	private String codigoIntervencion;
	private String noSerieFalla;
	private String noInventarioFalla;
	private String idModeloFalla;
	private String isActualizacion;
	private String isInstalacion;
	private String isSustitucion;
	private String isRetiro;
	private String fecInicio;
	private String fecCierre;
	private String fecActual;
	private String idProyecto;
	private String idModeloReq;
	private String idProducto;
	private String idTipoServicio;
	private String idTipoPrecio;
	private String idMoneda;
	private String idTipoCobro;
	private String isCobrable;
	private String descMoneda;
	private String horasAtencion;
	private String precioExito;
	private String precioRechazo;
	private String descTipoCobro;
	private String descTipoPrecio;
	private String fecLlegada;
	private String fecLlegadaTerceros;
	private String folioServicio;
	private String fecIniIngeniero;
	private String fecFinIngeniero;
	private String otorganteVoBo;
	private String otorganteVoBoTerceros;
	private String intensidadSenial;
	private String idSIMRemplazada;
	private String digitoVerificador;
	private String idTipoFallaEncontrada;
	private String fallaEncontrada;
	private String otorganteVoBoCliente;
	private String motivoCobro;
	private String isSoporteCliente;
	private String isboletin;
	private String otorganteSoporteCliente;
	private String cadenaCierreEscrita;
	private String isUptime;
	private String minsDowntime;
	private String descHorarioUptime;
	private String descHorarioAcceso;
	private String isContract;
	private String docTIR;
	private String instalacionLlaves;
	private String tipoLlaves;
	private String statusInstalacionLlaves;
	private String nombrePersonalLlavesA;
	private String nombrePersonalLlavesB;
	private String tipoPw;
	private String tisTipoTeclado;
	private String versionTecladoEPP;
	private String procesador;
	private String velocidadProcesador;
	private String memoria;
	private String capacidadDiscoDuro;
	private String monitor;
	private String lectorTarjeta;
	private String aplicacion_santander;
	private String version_santander;
	private String caja;
	private String voltajeNeutro;
	private String voltajeTierra;
	private String voltajeTierraNeutro;
	private String idEspecifiqueTipoFallo;
	private String folioValidacion;
	private String folioTIR;
	private String isCausaSolucionRequired;
	private String isTASRequired;
	private String isOtorganteTASRequired;
	private String isNoEquipoREquired;
	private String isNoSerieRequired;
	private String isNoInventarioRequired;
	private String isIdModeloRequired;
	private String isFecLlegadaRequired;
	private String isFecLlegadaTercerosRequired;
	private String isFolioServicioRequired;
	private String isFecIniIngenieroRequired;
	private String isFecFinIngenieroRequired;
	private String isOtorganteVoBoRequired;
	private String isOtorganteVoBoTercerosRequired;
	private String isIntensidadSenialRequired;
	private String isIsSIMRemplazadaRequired;
	private String isFallaEncontradaRequired;
    private String isFallaEncontradaRequiredText;
	private String isOtorganteVoBoClienteRequired;
	private String isMotivoCobroRequired;
	private String isIsSoporteClienteRequired;
	private String isIsBoletinRequired;
	private String isOtorganteSoporteClienteRequired;
	private String isCadenaCierreEscritaRequired;
	private String isCodigoIntervencionRequired;
	private String lengthCodigoIntervencion;
	private String isDigitoVerificadorRequired;
	private String isIdTipoFallaEncontradaRequired;
	private String isInstalacionLlavesRequired;
	private String isTipoLlaveRequired;
	private String isStatusInstalacionLlavesRequired;
	private String isNombrePersonaLlavesARequired;
	private String isNombrePersonaLlavesBRequired;
	private String isTipoPwRequired;
	private String isTipoTecladoRequired;
	private String isVersionTecladoEPPRequired;
	private String isProcesadorRequired;
	private String isVelocidadProcesadorRequired;
	private String isMemoriaRequired;
	private String isCapacidadDiscoDuroRequired;
	private String isMonitorRequired;
	private String isLectorTarjetaRequired;
	private String isAplicacionRequired;
	private String isVersionRequired;
	private String isCajaRequired;
	private String isMultipleTask;
	private String IsLecturaVoltajeRequired;
	private String IsEspecificaTipoFalla;
	private String IsFolioValidacionRequired;
	private String IsFolioTIRRequired;
	private String isDescTrabajoRequired;
	private String isDescTrabajoCatalogRequired;
	private String idDescripcionTrabajo;
	
	// ADD SIM AND SIM REQUIRED FIELD \( '.'   )
	private String mIdFail;
	private String sim;
	private String isSimRequired;

	//SE AGREGAN NUEVAS VARIABLES 31/01/2018
	private String isCalidadBilleteRequired;
	private String isCondicionSiteRequired;

	private String idCalidadBillete;
	private String idCondicionSite;
	
	
	//estos campo no se llena en validateClosure, ÀO si?
	private String otorganteTAS;
	private String idTecnico;
	
	//el mensaje de error (ÀEs necesario?)
	private String ERROR;
	
	private String res;
	private String val;
	private String desc;
	
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
	public String getIsWincor() {
		return isWincor;
	}
	public void setIsWincor(String isWincor) {
		this.isWincor = isWincor;
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
	public String getNoEquipo() {
		return noEquipo;
	}
	public void setNoEquipo(String noEquipo) {
		this.noEquipo = noEquipo;
	}
	public String getDescripcionTrabajo() {
		return descripcionTrabajo;
	}
	public void setDescripcionTrabajo(String descripcionTrabajo) {
		this.descripcionTrabajo = descripcionTrabajo;
	}
	public String getAtiende() {
		return atiende;
	}
	public void setAtiende(String atiende) {
		this.atiende = atiende;
	}
	public String getFolioTas() {
		return folioTas;
	}
	public void setFolioTas(String folioTas) {
		this.folioTas = folioTas;
	}
	public String getCodigoIntervencion() {
		return codigoIntervencion;
	}
	public void setCodigoIntervencion(String codigoIntervencion) {
		this.codigoIntervencion = codigoIntervencion;
	}
	public String getNoSerieFalla() {
		return noSerieFalla;
	}
	public void setNoSerieFalla(String noSerieFalla) {
		this.noSerieFalla = noSerieFalla;
	}
	public String getNoInventarioFalla() {
		return noInventarioFalla;
	}
	public void setNoInventarioFalla(String noInventarioFalla) {
		this.noInventarioFalla = noInventarioFalla;
	}
	public String getIdModeloFalla() {
		return idModeloFalla;
	}
	public void setIdModeloFalla(String idModeloFalla) {
		this.idModeloFalla = idModeloFalla;
	}
	public String getIsActualizacion() {
		return isActualizacion;
	}
	public void setIsActualizacion(String isActualizacion) {
		this.isActualizacion = isActualizacion;
	}
	public String getIsInstalacion() {
		return isInstalacion;
	}
	public void setIsInstalacion(String isInstalacion) {
		this.isInstalacion = isInstalacion;
	}
	public String getIsSustitucion() {
		return isSustitucion;
	}
	public void setIsSustitucion(String isSustitucion) {
		this.isSustitucion = isSustitucion;
	}
	public String getIsRetiro() {
		return isRetiro;
	}
	public void setIsRetiro(String isRetiro) {
		this.isRetiro = isRetiro;
	}
	public String getFecInicio() {
		return fecInicio;
	}
	public void setFecInicio(String fecInicio) {
		this.fecInicio = fecInicio;
	}
	public String getFecActual() {
		return fecActual;
	}
	public void setFecActual(String fecActual) {
		this.fecActual = fecActual;
	}
	public String getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}
	public String getIdModeloReq() {
		return idModeloReq;
	}
	public void setIdModeloReq(String idModeloReq) {
		this.idModeloReq = idModeloReq;
	}
	public String getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}
	public String getIdTipoServicio() {
		return idTipoServicio;
	}
	public void setIdTipoServicio(String idTipoServicio) {
		this.idTipoServicio = idTipoServicio;
	}
	public String getIdTipoPrecio() {
		return idTipoPrecio;
	}
	public void setIdTipoPrecio(String idTipoPrecio) {
		this.idTipoPrecio = idTipoPrecio;
	}
	public String getIdMoneda() {
		return idMoneda;
	}
	public void setIdMoneda(String idMoneda) {
		this.idMoneda = idMoneda;
	}
	public String getIdTipoCobro() {
		return idTipoCobro;
	}
	public void setIdTipoCobro(String idTipoCobro) {
		this.idTipoCobro = idTipoCobro;
	}
	public String getIsCobrable() {
		return isCobrable;
	}
	public void setIsCobrable(String isCobrable) {
		this.isCobrable = isCobrable;
	}
	public String getDescMoneda() {
		return descMoneda;
	}
	public void setDescMoneda(String descMoneda) {
		this.descMoneda = descMoneda;
	}
	public String getHorasAtencion() {
		return horasAtencion;
	}
	public void setHorasAtencion(String horasAtencion) {
		this.horasAtencion = horasAtencion;
	}
	public String getPrecioExito() {
		return precioExito;
	}
	public void setPrecioExito(String precioExito) {
		this.precioExito = precioExito;
	}
	public String getPrecioRechazo() {
		return precioRechazo;
	}
	public void setPrecioRechazo(String precioRechazo) {
		this.precioRechazo = precioRechazo;
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
	public String getFecLlegada() {
		return fecLlegada;
	}
	public void setFecLlegada(String fecLlegada) {
		this.fecLlegada = fecLlegada;
	}
	public String getFecLlegadaTerceros() {
		return fecLlegadaTerceros;
	}
	public void setFecLlegadaTerceros(String fecLlegadaTerceros) {
		this.fecLlegadaTerceros = fecLlegadaTerceros;
	}
	public String getFolioServicio() {
		return folioServicio;
	}
	public void setFolioServicio(String folioServicio) {
		this.folioServicio = folioServicio;
	}
	public String getFecIniIngeniero() {
		return fecIniIngeniero;
	}
	public void setFecIniIngeniero(String fecIniIngeniero) {
		this.fecIniIngeniero = fecIniIngeniero;
	}
	public String getFecFinIngeniero() {
		return fecFinIngeniero;
	}
	public void setFecFinIngeniero(String fecFinIngeniero) {
		this.fecFinIngeniero = fecFinIngeniero;
	}
	public String getOtorganteVoBo() {
		return otorganteVoBo;
	}
	public void setOtorganteVoBo(String otorganteVoBo) {
		this.otorganteVoBo = otorganteVoBo;
	}
	public String getOtorganteVoBoTerceros() {
		return otorganteVoBoTerceros;
	}
	public void setOtorganteVoBoTerceros(String otorganteVoBoTerceros) {
		this.otorganteVoBoTerceros = otorganteVoBoTerceros;
	}
	public String getIntensidadSenial() {
		return intensidadSenial;
	}
	public void setIntensidadSenial(String intensidadSenial) {
		this.intensidadSenial = intensidadSenial;
	}
	public String getIdSIMRemplazada() {
		return idSIMRemplazada;
	}
	public void setIdSIMRemplazada(String idSIMRemplazada) {
		this.idSIMRemplazada = idSIMRemplazada;
	}
	public String getDigitoVerificador() {
		return digitoVerificador;
	}
	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}
	public String getIdTipoFallaEncontrada() {
		return idTipoFallaEncontrada;
	}
	public void setIdTipoFallaEncontrada(String idTipoFallaEncontrada) {
		this.idTipoFallaEncontrada = idTipoFallaEncontrada;
	}
	public String getFallaEncontrada() {
		return fallaEncontrada;
	}
	public void setFallaEncontrada(String fallaEncontrada) {
		this.fallaEncontrada = fallaEncontrada;
	}
	public String getOtorganteVoBoCliente() {
		return otorganteVoBoCliente;
	}
	public void setOtorganteVoBoCliente(String otorganteVoBoCliente) {
		this.otorganteVoBoCliente = otorganteVoBoCliente;
	}
	public String getMotivoCobro() {
		return motivoCobro;
	}
	public void setMotivoCobro(String motivoCobro) {
		this.motivoCobro = motivoCobro;
	}
	public String getIsSoporteCliente() {
		return isSoporteCliente;
	}
	public void setIsSoporteCliente(String isSoporteCliente) {
		this.isSoporteCliente = isSoporteCliente;
	}
	public String getIsboletin() {
		return isboletin;
	}
	public void setIsboletin(String isboletin) {
		this.isboletin = isboletin;
	}
	public String getOtorganteSoporteCliente() {
		return otorganteSoporteCliente;
	}
	public void setOtorganteSoporteCliente(String otorganteSoporteCliente) {
		this.otorganteSoporteCliente = otorganteSoporteCliente;
	}
	public String getCadenaCierreEscrita() {
		return cadenaCierreEscrita;
	}
	public void setCadenaCierreEscrita(String cadenaCierreEscrita) {
		this.cadenaCierreEscrita = cadenaCierreEscrita;
	}
	public String getIsUptime() {
		return isUptime;
	}
	public void setIsUptime(String isUptime) {
		this.isUptime = isUptime;
	}
	public String getMinsDowntime() {
		return minsDowntime;
	}
	public void setMinsDowntime(String minsDowntime) {
		this.minsDowntime = minsDowntime;
	}
	public String getDescHorarioUptime() {
		return descHorarioUptime;
	}
	public void setDescHorarioUptime(String descHorarioUptime) {
		this.descHorarioUptime = descHorarioUptime;
	}
	public String getDescHorarioAcceso() {
		return descHorarioAcceso;
	}
	public void setDescHorarioAcceso(String descHorarioAcceso) {
		this.descHorarioAcceso = descHorarioAcceso;
	}
	public String getIsContract() {
		return isContract;
	}
	public void setIsContract(String isContract) {
		this.isContract = isContract;
	}
	public String getDocTIR() {
		return docTIR;
	}
	public void setDocTIR(String docTIR) {
		this.docTIR = docTIR;
	}
	public String getInstalacionLlaves() {
		return instalacionLlaves;
	}
	public void setInstalacionLlaves(String instalacionLlaves) {
		this.instalacionLlaves = instalacionLlaves;
	}
	public String getTipoLlaves() {
		return tipoLlaves;
	}
	public void setTipoLlaves(String tipoLlaves) {
		this.tipoLlaves = tipoLlaves;
	}
	public String getStatusInstalacionLlaves() {
		return statusInstalacionLlaves;
	}
	public void setStatusInstalacionLlaves(String statusInstalacionLlaves) {
		this.statusInstalacionLlaves = statusInstalacionLlaves;
	}
	public String getNombrePersonalLlavesA() {
		return nombrePersonalLlavesA;
	}
	public void setNombrePersonalLlavesA(String nombrePersonalLlavesA) {
		this.nombrePersonalLlavesA = nombrePersonalLlavesA;
	}
	public String getNombrePersonalLlavesB() {
		return nombrePersonalLlavesB;
	}
	public void setNombrePersonalLlavesB(String nombrePersonalLlavesB) {
		this.nombrePersonalLlavesB = nombrePersonalLlavesB;
	}
	public String getTipoPw() {
		return tipoPw;
	}
	public void setTipoPw(String tipoPw) {
		this.tipoPw = tipoPw;
	}
	public String getTisTipoTeclado() {
		return tisTipoTeclado;
	}
	public void setTisTipoTeclado(String tisTipoTeclado) {
		this.tisTipoTeclado = tisTipoTeclado;
	}
	public String getVersionTecladoEPP() {
		return versionTecladoEPP;
	}
	public void setVersionTecladoEPP(String versionTecladoEPP) {
		this.versionTecladoEPP = versionTecladoEPP;
	}
	public String getProcesador() {
		return procesador;
	}
	public void setProcesador(String procesador) {
		this.procesador = procesador;
	}
	public String getVelocidadProcesador() {
		return velocidadProcesador;
	}
	public void setVelocidadProcesador(String velocidadProcesador) {
		this.velocidadProcesador = velocidadProcesador;
	}
	public String getMemoria() {
		return memoria;
	}
	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}
	public String getCapacidadDiscoDuro() {
		return capacidadDiscoDuro;
	}
	public void setCapacidadDiscoDuro(String capacidadDiscoDuro) {
		this.capacidadDiscoDuro = capacidadDiscoDuro;
	}
	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
	public String getLectorTarjeta() {
		return lectorTarjeta;
	}
	public void setLectorTarjeta(String lectorTarjeta) {
		this.lectorTarjeta = lectorTarjeta;
	}
	public String getAplicacion_santander() {
		return aplicacion_santander;
	}
	public void setAplicacion_santander(String aplicacion_santander) {
		this.aplicacion_santander = aplicacion_santander;
	}
	public String getVersion_santander() {
		return version_santander;
	}
	public void setVersion_santander(String version_santander) {
		this.version_santander = version_santander;
	}
	public String getCaja() {
		return caja;
	}
	public void setCaja(String caja) {
		this.caja = caja;
	}
	public String getVoltajeNeutro() {
		return voltajeNeutro;
	}
	public void setVoltajeNeutro(String voltajeNeutro) {
		this.voltajeNeutro = voltajeNeutro;
	}
	public String getVoltajeTierra() {
		return voltajeTierra;
	}
	public void setVoltajeTierra(String voltajeTierra) {
		this.voltajeTierra = voltajeTierra;
	}
	public String getVoltajeTierraNeutro() {
		return voltajeTierraNeutro;
	}
	public void setVoltajeTierraNeutro(String voltajeTierraNeutro) {
		this.voltajeTierraNeutro = voltajeTierraNeutro;
	}
	public String getIdEspecifiqueTipoFallo() {
		return idEspecifiqueTipoFallo;
	}
	public void setIdEspecifiqueTipoFallo(String idEspecifiqueTipoFallo) {
		this.idEspecifiqueTipoFallo = idEspecifiqueTipoFallo;
	}
	public String getFolioValidacion() {
		return folioValidacion;
	}
	public void setFolioValidacion(String folioValidacion) {
		this.folioValidacion = folioValidacion;
	}
	public String getFolioTIR() {
		return folioTIR;
	}
	public void setFolioTIR(String folioTIR) {
		this.folioTIR = folioTIR;
	}
	public String getIsCausaSolucionRequired() {
		return isCausaSolucionRequired;
	}
	public void setIsCausaSolucionRequired(String isCausaSolucionRequired) {
		this.isCausaSolucionRequired = isCausaSolucionRequired;
	}
	public String getIsTASRequired() {
		return isTASRequired;
	}
	public void setIsTASRequired(String isTASRequired) {
		this.isTASRequired = isTASRequired;
	}
	public String getIsOtorganteTASRequired() {
		return isOtorganteTASRequired;
	}
	public void setIsOtorganteTASRequired(String isOtorganteTASRequired) {
		this.isOtorganteTASRequired = isOtorganteTASRequired;
	}
	public String getIsNoEquipoREquired() {
		return isNoEquipoREquired;
	}
	public void setIsNoEquipoREquired(String isNoEquipoREquired) {
		this.isNoEquipoREquired = isNoEquipoREquired;
	}
	public String getIsNoSerieRequired() {
		return isNoSerieRequired;
	}
	public void setIsNoSerieRequired(String isNoSerieRequired) {
		this.isNoSerieRequired = isNoSerieRequired;
	}
	public String getIsNoInventarioRequired() {
		return isNoInventarioRequired;
	}
	public void setIsNoInventarioRequired(String isNoInventarioRequired) {
		this.isNoInventarioRequired = isNoInventarioRequired;
	}
	public String getIsIdModeloRequired() {
		return isIdModeloRequired;
	}
	public void setIsIdModeloRequired(String isIdModeloRequired) {
		this.isIdModeloRequired = isIdModeloRequired;
	}
	public String getIsFecLlegadaRequired() {
		return isFecLlegadaRequired;
	}
	public void setIsFecLlegadaRequired(String isFecLlegadaRequired) {
		this.isFecLlegadaRequired = isFecLlegadaRequired;
	}
	public String getIsFecLlegadaTercerosRequired() {
		return isFecLlegadaTercerosRequired;
	}
	public void setIsFecLlegadaTercerosRequired(String isFecLlegadaTercerosRequired) {
		this.isFecLlegadaTercerosRequired = isFecLlegadaTercerosRequired;
	}
	public String getIsFolioServicioRequired() {
		return isFolioServicioRequired;
	}
	public void setIsFolioServicioRequired(String isFolioServicioRequired) {
		this.isFolioServicioRequired = isFolioServicioRequired;
	}
	public String getIsFecIniIngenieroRequired() {
		return isFecIniIngenieroRequired;
	}
	public void setIsFecIniIngenieroRequired(String isFecIniIngenieroRequired) {
		this.isFecIniIngenieroRequired = isFecIniIngenieroRequired;
	}
	public String getIsFecFinIngenieroRequired() {
		return isFecFinIngenieroRequired;
	}
	public void setIsFecFinIngenieroRequired(String isFecFinIngenieroRequired) {
		this.isFecFinIngenieroRequired = isFecFinIngenieroRequired;
	}
	public String getIsOtorganteVoBoRequired() {
		return isOtorganteVoBoRequired;
	}
	public void setIsOtorganteVoBoRequired(String isOtorganteVoBoRequired) {
		this.isOtorganteVoBoRequired = isOtorganteVoBoRequired;
	}
	public String getIsOtorganteVoBoTercerosRequired() {
		return isOtorganteVoBoTercerosRequired;
	}
	public void setIsOtorganteVoBoTercerosRequired(
			String isOtorganteVoBoTercerosRequired) {
		this.isOtorganteVoBoTercerosRequired = isOtorganteVoBoTercerosRequired;
	}
	public String getIsIntensidadSenialRequired() {
		return isIntensidadSenialRequired;
	}
	public void setIsIntensidadSenialRequired(String isIntensidadSenialRequired) {
		this.isIntensidadSenialRequired = isIntensidadSenialRequired;
	}
	public String getIsIsSIMRemplazadaRequired() {
		return isIsSIMRemplazadaRequired;
	}
	public void setIsIsSIMRemplazadaRequired(String isIsSIMRemplazadaRequired) {
		this.isIsSIMRemplazadaRequired = isIsSIMRemplazadaRequired;
	}
	public String getIsFallaEncontradaRequired() {
		return isFallaEncontradaRequired;
	}
	public void setIsFallaEncontradaRequired(String isFallaEncontradaRequired) {
		this.isFallaEncontradaRequired = isFallaEncontradaRequired;
	}
	public String getIsOtorganteVoBoClienteRequired() {
		return isOtorganteVoBoClienteRequired;
	}
	public void setIsOtorganteVoBoClienteRequired(
			String isOtorganteVoBoClienteRequired) {
		this.isOtorganteVoBoClienteRequired = isOtorganteVoBoClienteRequired;
	}
	public String getIsMotivoCobroRequired() {
		return isMotivoCobroRequired;
	}
	public void setIsMotivoCobroRequired(String isMotivoCobroRequired) {
		this.isMotivoCobroRequired = isMotivoCobroRequired;
	}
	public String getIsIsSoporteClienteRequired() {
		return isIsSoporteClienteRequired;
	}
	public void setIsIsSoporteClienteRequired(String isIsSoporteClienteRequired) {
		this.isIsSoporteClienteRequired = isIsSoporteClienteRequired;
	}
	public String getIsIsBoletinRequired() {
		return isIsBoletinRequired;
	}
	public void setIsIsBoletinRequired(String isIsBoletinRequired) {
		this.isIsBoletinRequired = isIsBoletinRequired;
	}
	public String getIsOtorganteSoporteClienteRequired() {
		return isOtorganteSoporteClienteRequired;
	}
	public void setIsOtorganteSoporteClienteRequired(
			String isOtorganteSoporteClienteRequired) {
		this.isOtorganteSoporteClienteRequired = isOtorganteSoporteClienteRequired;
	}
	public String getIsCadenaCierreEscritaRequired() {
		return isCadenaCierreEscritaRequired;
	}
	public void setIsCadenaCierreEscritaRequired(
			String isCadenaCierreEscritaRequired) {
		this.isCadenaCierreEscritaRequired = isCadenaCierreEscritaRequired;
	}
	public String getIsCodigoIntervencionRequired() {
		return isCodigoIntervencionRequired;
	}
	public void setIsCodigoIntervencionRequired(String isCodigoIntervencionRequired) {
		this.isCodigoIntervencionRequired = isCodigoIntervencionRequired;
	}
	public String getLengthCodigoIntervencion() {
		return lengthCodigoIntervencion;
	}
	public void setLengthCodigoIntervencion(String lengthCodigoIntervencion) {
		this.lengthCodigoIntervencion = lengthCodigoIntervencion;
	}
	public String getIsDigitoVerificadorRequired() {
		return isDigitoVerificadorRequired;
	}
	public void setIsDigitoVerificadorRequired(String isDigitoVerificadorRequired) {
		this.isDigitoVerificadorRequired = isDigitoVerificadorRequired;
	}
	public String getIsIdTipoFallaEncontradaRequired() {
		return isIdTipoFallaEncontradaRequired;
	}
	public void setIsIdTipoFallaEncontradaRequired(
			String isIdTipoFallaEncontradaRequired) {
		this.isIdTipoFallaEncontradaRequired = isIdTipoFallaEncontradaRequired;
	}
	public String getIsInstalacionLlavesRequired() {
		return isInstalacionLlavesRequired;
	}
	public void setIsInstalacionLlavesRequired(String isInstalacionLlavesRequired) {
		this.isInstalacionLlavesRequired = isInstalacionLlavesRequired;
	}
	public String getIsTipoLlaveRequired() {
		return isTipoLlaveRequired;
	}
	public void setIsTipoLlaveRequired(String isTipoLlaveRequired) {
		this.isTipoLlaveRequired = isTipoLlaveRequired;
	}
	public String getIsStatusInstalacionLlavesRequired() {
		return isStatusInstalacionLlavesRequired;
	}
	public void setIsStatusInstalacionLlavesRequired(
			String isStatusInstalacionLlavesRequired) {
		this.isStatusInstalacionLlavesRequired = isStatusInstalacionLlavesRequired;
	}
	public String getIsNombrePersonaLlavesARequired() {
		return isNombrePersonaLlavesARequired;
	}
	public void setIsNombrePersonaLlavesARequired(
			String isNombrePersonaLlavesARequired) {
		this.isNombrePersonaLlavesARequired = isNombrePersonaLlavesARequired;
	}
	public String getIsNombrePersonaLlavesBRequired() {
		return isNombrePersonaLlavesBRequired;
	}
	public void setIsNombrePersonaLlavesBRequired(
			String isNombrePersonaLlavesBRequired) {
		this.isNombrePersonaLlavesBRequired = isNombrePersonaLlavesBRequired;
	}
	public String getIsTipoPwRequired() {
		return isTipoPwRequired;
	}
	public void setIsTipoPwRequired(String isTipoPwRequired) {
		this.isTipoPwRequired = isTipoPwRequired;
	}
	public String getIsTipoTecladoRequired() {
		return isTipoTecladoRequired;
	}
	public void setIsTipoTecladoRequired(String isTipoTecladoRequired) {
		this.isTipoTecladoRequired = isTipoTecladoRequired;
	}
	public String getIsVersionTecladoEPPRequired() {
		return isVersionTecladoEPPRequired;
	}
	public void setIsVersionTecladoEPPRequired(String isVersionTecladoEPPRequired) {
		this.isVersionTecladoEPPRequired = isVersionTecladoEPPRequired;
	}
	public String getIsProcesadorRequired() {
		return isProcesadorRequired;
	}
	public void setIsProcesadorRequired(String isProcesadorRequired) {
		this.isProcesadorRequired = isProcesadorRequired;
	}
	public String getIsVelocidadProcesadorRequired() {
		return isVelocidadProcesadorRequired;
	}
	public void setIsVelocidadProcesadorRequired(
			String isVelocidadProcesadorRequired) {
		this.isVelocidadProcesadorRequired = isVelocidadProcesadorRequired;
	}
	public String getIsMemoriaRequired() {
		return isMemoriaRequired;
	}
	public void setIsMemoriaRequired(String isMemoriaRequired) {
		this.isMemoriaRequired = isMemoriaRequired;
	}
	public String getIsCapacidadDiscoDuroRequired() {
		return isCapacidadDiscoDuroRequired;
	}
	public void setIsCapacidadDiscoDuroRequired(String isCapacidadDiscoDuroRequired) {
		this.isCapacidadDiscoDuroRequired = isCapacidadDiscoDuroRequired;
	}
	public String getIsMonitorRequired() {
		return isMonitorRequired;
	}
	public void setIsMonitorRequired(String isMonitorRequired) {
		this.isMonitorRequired = isMonitorRequired;
	}
	public String getIsLectorTarjetaRequired() {
		return isLectorTarjetaRequired;
	}
	public void setIsLectorTarjetaRequired(String isLectorTarjetaRequired) {
		this.isLectorTarjetaRequired = isLectorTarjetaRequired;
	}
	public String getIsAplicacionRequired() {
		return isAplicacionRequired;
	}
	public void setIsAplicacionRequired(String isAplicacionRequired) {
		this.isAplicacionRequired = isAplicacionRequired;
	}
	public String getIsVersionRequired() {
		return isVersionRequired;
	}
	public void setIsVersionRequired(String isVersionRequired) {
		this.isVersionRequired = isVersionRequired;
	}
	public String getIsCajaRequired() {
		return isCajaRequired;
	}
	public void setIsCajaRequired(String isCajaRequired) {
		this.isCajaRequired = isCajaRequired;
	}
	public String getIsMultipleTask() {
		return isMultipleTask;
	}
	public void setIsMultipleTask(String isMultipleTask) {
		this.isMultipleTask = isMultipleTask;
	}
	public String getIsLecturaVoltajeRequired() {
		return IsLecturaVoltajeRequired;
	}
	public void setIsLecturaVoltajeRequired(String isLecturaVoltajeRequired) {
		IsLecturaVoltajeRequired = isLecturaVoltajeRequired;
	}
	public String getIsEspecificaTipoFalla() {
		return IsEspecificaTipoFalla;
	}
	public void setIsEspecificaTipoFalla(String isEspecificaTipoFalla) {
		IsEspecificaTipoFalla = isEspecificaTipoFalla;
	}
	public String getIsFolioValidacionRequired() {
		return IsFolioValidacionRequired;
	}
	public void setIsFolioValidacionRequired(String isFolioValidacionRequired) {
		IsFolioValidacionRequired = isFolioValidacionRequired;
	}
	public String getIsFolioTIRRequired() {
		return IsFolioTIRRequired;
	}
	public void setIsFolioTIRRequired(String isFolioTIRRequired) {
		IsFolioTIRRequired = isFolioTIRRequired;
	}
	public String getERROR() {
		return ERROR;
	}
	public void setERROR(String eRROR) {
		ERROR = eRROR;
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
	public String getOtorganteTAS() {
		return otorganteTAS;
	}
	public void setOtorganteTAS(String otorganteTAS) {
		this.otorganteTAS = otorganteTAS;
	}
	public String getIdTecnico() {
		return idTecnico;
	}
	public void setIdTecnico(String idTecnico) {
		this.idTecnico = idTecnico;
	}
	public String getFecCierre() {
		return fecCierre;
	}
	public void setFecCierre(String fecCierre) {
		this.fecCierre = fecCierre;
	}
	public String getIsDescTrabajoRequired() {
		return isDescTrabajoRequired;
	}
	public void setIsDescTrabajoRequired(String isDescTrabajoRequired) {
		this.isDescTrabajoRequired = isDescTrabajoRequired;
	}
	public String getIsDescTrabajoCatalogRequired() {
		return isDescTrabajoCatalogRequired;
	}
	public void setIsDescTrabajoCatalogRequired(String isDescTrabajoCatalogRequired) {
		this.isDescTrabajoCatalogRequired = isDescTrabajoCatalogRequired;
	}
	public String getIdDescripcionTrabajo() {
		return idDescripcionTrabajo;
	}
	public void setIdDescripcionTrabajo(String idDescripcionTrabajo) {
		this.idDescripcionTrabajo = idDescripcionTrabajo;
	}
	
	// SETTERS AND GETTERS OF SIM AND SIM REQUIRED \( '.'   )
	public String getSIM() {
		return this.sim;
	}
	
	public void setSIM(String value) {
		this.sim = value;
	}
	
	public String getIsSIMRequired() {
		return this.isSimRequired;
	}
	
	public void setIsSIMRequired(String value) {
		this.isSimRequired = value;
	}

	public String getIdFail() {
		return mIdFail;
	}

	public void setIdFail(String idFail) {
		this.mIdFail = idFail;
	}

    public String getIsFallaEncontradaRequiredText() {
        return isFallaEncontradaRequiredText;
    }

    public void setIsFallaEncontradaRequiredText(String isFallaEncontradaRequiredText) {
        this.isFallaEncontradaRequiredText = isFallaEncontradaRequiredText;
    }

	public String getIsCalidadBilleteRequired() {
		return isCalidadBilleteRequired;
	}

	public void setIsCalidadBilleteRequired(String isCalidadBilleteRequired) {
		this.isCalidadBilleteRequired = isCalidadBilleteRequired;
	}

	public String getIsCondicionSiteRequired() {
		return isCondicionSiteRequired;
	}

	public void setIsCondicionSiteRequired(String isCondicionSiteRequired) {
		this.isCondicionSiteRequired = isCondicionSiteRequired;
	}

	public String getIdCalidadBillete() {
		return idCalidadBillete;
	}

	public void setIdCalidadBillete(String idCalidadBillete) {
		this.idCalidadBillete = idCalidadBillete;
	}

	public String getIdCondicionSite() {
		return idCondicionSite;
	}

	public void setIdCondicionSite(String idCondicionSite) {
		this.idCondicionSite = idCondicionSite;
	}
}