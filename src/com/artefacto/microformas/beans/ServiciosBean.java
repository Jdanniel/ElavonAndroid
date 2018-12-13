package com.artefacto.microformas.beans;

public class ServiciosBean {
	private String id;
	private String idCliente;
	private String idTipoServicio;
	private String idMoneda;
	private String idTipoPrecio;
	private String isInsumosRequired;
	private String isCausaSolucionRequired;
	private String isCausaRequired;
	private String isSolucionRequired;
	private String isTASRequired;
	private String isOtorganteTASRequired;
	private String isNoEquipoRequired;
	private String isNoSerieRequired;
	private String isNoInventarioRequired;
	private String isIDModeloRequired;
	private String isFecLlegadaRequired;
	private String isFecLlegadaTercerosRequired;
	private String isFolioServicioRequired;
	private String isFecIniIngenieroRequired;
	private String isFecFinIngenieroRequired;
	private String isOtorganteVoBoRequired;
	private String isOtorganteVoBoTercerosRequired;
	private String isIntensidadSenialRequired;
	private String isSimReemplazadaRequired;
	private String isFolioServicioRechazoRequired;
	private String isOtorganteVoBoRechazoRequired;
	private String isFallaEncontradaRequired;
	private String isOtorganteVoBoClienteRequired;
	private String isMotivoCobroRequired;
	private String isSoporteClienteRequired;
	private String isOtorganteSoporteClienteRequired;
	private String isBoletinRequired;
	private String isCadenaCierreEscritaRequired;
	private String isDowntime;
	private String isCierrePDA;
	private String isAplicacionRequired;
	private String isVersionRequired;
	private String isCajaRequired;
	private String descServicio;
	// ADD NEW COLUMNS FIELDS (17-10-2014)
	private String idARReopen;
	private String idARNeedFile;
	private String idARNeedNoCheckup;
	// ADD NEW COLUMNS FIELDS (21-01-2015)
	private String mIsKitRequired;
	private String mKitSupply;
	//ADD NEW COLUMNS FIELDS (03/08/2017)
	private String mNeedSevicesSheet;
	//ADD NEW COLUMNS FIELDS (30/01/2018)
	private String mIsCalidadBillete;
	private String mIsCondicionSite;

	private int		connStatus;

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

	public String getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(String idMoneda) {
		this.idMoneda = idMoneda;
	}
	
	public String getIdTipoPrecio() {
		return idTipoPrecio;
	}

	public void setIdTipoPrecio(String idTipoPrecio) {
		this.idTipoPrecio = idTipoPrecio;
	}

	public String getIdTipoServicio() {
		return idTipoServicio;
	}

	public void setIdTipoServicio(String idTipoServicio) {
		this.idTipoServicio = idTipoServicio;
	}

	public String getIsInsumosRequired() {
		return isInsumosRequired;
	}

	public void setIsInsumosRequired(String isInsumosRequired) {
		this.isInsumosRequired = isInsumosRequired;
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

	public String getIsOtorganteTASRequired() {
		return isOtorganteTASRequired;
	}

	public void setIsOtorganteTASRequired(String isOtorganteTASRequired) {
		this.isOtorganteTASRequired = isOtorganteTASRequired;
	}

	public String getIsNoEquipoRequired() {
		return isNoEquipoRequired;
	}

	public void setIsNoEquipoRequired(String isNoEquipoRequired) {
		this.isNoEquipoRequired = isNoEquipoRequired;
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

	public String getIsIDModeloRequired() {
		return isIDModeloRequired;
	}

	public void setIsIDModeloRequired(String isIDModeloRequired) {
		this.isIDModeloRequired = isIDModeloRequired;
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

	public String getIsSimReemplazadaRequired() {
		return isSimReemplazadaRequired;
	}

	public void setIsSimReemplazadaRequired(String isSimReemplazadaRequired) {
		this.isSimReemplazadaRequired = isSimReemplazadaRequired;
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

	public String getIsSoporteClienteRequired() {
		return isSoporteClienteRequired;
	}

	public void setIsSoporteClienteRequired(String isSoporteClienteRequired) {
		this.isSoporteClienteRequired = isSoporteClienteRequired;
	}

	public String getIsOtorganteSoporteClienteRequired() {
		return isOtorganteSoporteClienteRequired;
	}

	public void setIsOtorganteSoporteClienteRequired(
			String isOtorganteSoporteClienteRequired) {
		this.isOtorganteSoporteClienteRequired = isOtorganteSoporteClienteRequired;
	}

	public String getIsBoletinRequired() {
		return isBoletinRequired;
	}

	public void setIsBoletinRequired(String isBoletinRequired) {
		this.isBoletinRequired = isBoletinRequired;
	}

	public String getIsCadenaCierreEscritaRequired() {
		return isCadenaCierreEscritaRequired;
	}

	public void setIsCadenaCierreEscritaRequired(
			String isCadenaCierreEscritaRequired) {
		this.isCadenaCierreEscritaRequired = isCadenaCierreEscritaRequired;
	}

	public String getIsDowntime() {
		return isDowntime;
	}

	public void setIsDowntime(String isDowntime) {
		this.isDowntime = isDowntime;
	}

	public String getIsCierrePDA() {
		return isCierrePDA;
	}

	public void setIsCierrePDA(String isCierrePDA) {
		this.isCierrePDA = isCierrePDA;
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

	public String getDescServicio() {
		return descServicio;
	}

	public void setDescServicio(String descServicio) {
		this.descServicio = descServicio;
	}

	public int getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}

	public String getIsTASRequired() {
		return isTASRequired;
	}

	public void setIsTASRequired(String isTASRequired) {
		this.isTASRequired = isTASRequired;
	}
	
	// ADD NEW COLUMNS FIELDS (17-10-2014)
	public String getIdARReopen()
	{
		return idARReopen;
	}
	
	public void setIdArReopen(String idArReopen)
	{
		this.idARReopen = idArReopen;
	}
	
	public String getIdARNeedFile()
	{
		return idARNeedFile;
	}
	
	public void setIdARNeedFile(String idArNeedFile)
	{
		this.idARNeedFile = idArNeedFile;
	}
	
	public String getIdARNeedNoCheckUp()
	{
		return idARNeedNoCheckup;
	}
	
	public void setIdARNeedNoCheckUp(String idARNeedNoCheckup)
	{
		this.idARNeedNoCheckup = idARNeedNoCheckup;
	}

    // ADD NEW COLUMNS FIELDS (21-01-2015)
    public String getKitSupply()
    {
        return mKitSupply;
    }

    public void setKitSupply(String mKitSupply)
    {
        this.mKitSupply = mKitSupply;
    }

    public String getIsKitRequired()
    {
        return mIsKitRequired;
    }

    public void setIsKitRequired(String mIsKitRequired)
    {
        this.mIsKitRequired = mIsKitRequired;
    }

    //ADD NEW COLUMNS FIELDS (03/08/2017)
	public String getmNeedSevicesSheet() {
		return mNeedSevicesSheet;
	}

	public void setmNeedSevicesSheet(String mNeedSevicesSheet) {
		this.mNeedSevicesSheet = mNeedSevicesSheet;
	}

	public String getmIsCalidadBillete() {
		return mIsCalidadBillete;
	}

	public void setmIsCalidadBillete(String mIsCalidadBillete) {
		this.mIsCalidadBillete = mIsCalidadBillete;
	}

	public String getmIsCondicionSite() {
		return mIsCondicionSite;
	}

	public void setmIsCondicionSite(String mIsCondicionSite) {
		this.mIsCondicionSite = mIsCondicionSite;
	}
}