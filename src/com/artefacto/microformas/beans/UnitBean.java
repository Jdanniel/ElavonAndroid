package com.artefacto.microformas.beans;

import java.io.Serializable;

public class UnitBean implements Serializable
{
	public String getId()
    {
		return mId;
	}

	public void setId(String id)
    {
		this.mId = id;
	}

	public String getIdRequestCollection()
    {
		return mIdRequestCollection;
	}

	public void setIdRequestCollection(String idRequestCollection)
    {
		this.mIdRequestCollection = idRequestCollection;
	}

	public String getIdProduct()
    {
		return mIdProduct;
	}

	public void setIdProduct(String idProduct)
    {
		this.mIdProduct = idProduct;
	}

	public String getIsNew()
    {
		return mIsNew;
	}

	public void setIsNew(String isNew)
    {
		this.mIsNew = isNew;
	}

	public String getIsBroken()
    {
		return mIsBroken;
	}

	public void setIsBroken(String isBroken)
    {
		this.mIsBroken = isBroken;
	}

	public String getIdClient()
    {
		return mIdClient;
	}

	public void setIdClient(String idClient)
    {
		this.mIdClient = idClient;
	}

	public String getIdProductType()
    {
		return mIdProductType;
	}

	public void setIdProductType(String idProductType)
    {
		this.mIdProductType = idProductType;
	}

	public String getIdUnitStatus()
    {
		return mIdUnitStatus;
	}

	public void setIdUnitStatus(String idUnitStatus)
    {
		this.mIdUnitStatus = idUnitStatus;
	}

	public String getIsWithdrawal()
    {
		return mIsWithdrawal;
	}

	public void setIsWithdrawal(String isWithdrawal)
    {
		this.mIsWithdrawal = isWithdrawal;
	}

	public String getIdNewUser()
    {
		return mIdNewUser;
	}

	public void setIdNewUser(String idNewUser)
    {
		this.mIdNewUser = idNewUser;
	}

	public String getDescClient()
    {
		return mDescClient;
	}

	public void setDescClient(String descClient)
    {
		this.mDescClient = descClient;
	}

	public String getDescBrand()
    {
		return mDescBrand;
	}

	public void setDescBrand(String descBrand)
    {
		this.mDescBrand = descBrand;
	}

	public String getDescModel()
    {
		return mDescModel;
	}

	public void setDescModel(String descModel)
    {
		this.mDescModel = descModel;
	}

	public String getNoSerie()
    {
		return mNoSerie;
	}

	public void setNoSerie(String noSerie)
    {
		this.mNoSerie = noSerie;
	}

	public String getNoInventory()
    {
		return mNoInventory;
	}

	public void setNoInventory(String noInventory)
    {
		this.mNoInventory = noInventory;
	}

	public String getNoIMEI()
    {
		return mNoIMEI;
	}

	public void setNoIMEI(String noIMEI)
    {
		this.mNoIMEI = noIMEI;
	}

	public String getNoSim()
    {
		return mNoSim;
	}

	public void setNoSim(String noSim)
    {
		this.mNoSim = noSim;
	}

	public String getNoEquipment()
    {
		return mNoEquipment;
	}

	public void setNoEquipment(String noEquipment)
    {
		this.mNoEquipment = noEquipment;
	}

	public String getDescKey()
    {
		return mDescKey;
	}

	public void setDescKey(String descKey)
    {
		this.mDescKey = descKey;
	}

	public String getDescSoftware()
    {
		return mDescSoftware;
	}

	public void setDescSoftware(String descSoftware)
    {
		this.mDescSoftware = descSoftware;
	}

	public String getInventoryPos()
    {
		return mInventoryPos;
	}

	public void setInventoryPos(String inventoryPos)
    {
		this.mInventoryPos = inventoryPos;
	}

	public String getDescUnitStatus()
    {
		return mDescUnitStatus;
	}

	public void setDescUnitStatus(String descUnitStatus)
    {
		this.mDescUnitStatus = descUnitStatus;
	}

	public String getStatus()
    {
		return mStatus;
	}

	public void setStatus(String status)
    {
		this.mStatus = status;
	}

	public String getNewDate()
    {
		return mNewDate;
	}

	public void setNewDate(String newDate)
    {
		this.mNewDate = newDate;
	}

	public int getConnStatus()
    {
		return mConnStatus;
	}

	public void setConnStatus(int connStatus)
    {
		this.mConnStatus = connStatus;
	}

	public String getDescAccesory()
    {
		return mDescAccesory;
	}

	public void setDescAccesory(String descAccesory)
    {
		this.mDescAccesory = descAccesory;
	}

	public String getDescriptionAccesory()
    {
		return mDescriptionAccesory;
	}

	public void setDescriptionAccesory(String descriptionAccesory)
    {
		this.mDescriptionAccesory = descriptionAccesory;
	}

	public String getStatusAccesory()
    {
		return mStatusAccesory;
	}

	public void setStatusAccesory(String statusAccesory)
    {
		this.mStatusAccesory = statusAccesory;
	}

	public String getDescBusiness()
    {
		return mDescBusiness;
	}

	public void setDescBusiness(String descBusiness)
    {
		this.mDescBusiness = descBusiness;
	}

	public String getIdBusiness()
    {
		return mIdBusiness;
	}

	public void setIdBusiness(String idBusiness) {
		this.mIdBusiness = idBusiness;
	}

	public String getIdModel()
    {
		return mIdModel;
	}

	public void setIdModel(String idModel)
    {
		this.mIdModel = idModel;
	}

	public boolean isChecked()
    {
		return mIsChecked;
	}

	public void setChecked(boolean isChecked)
    {
		this.mIsChecked = isChecked;
	}

	public String getDescConnectivity() {
		return mDescConnectivity;
	}

	public void setDescConnectivity(String mDescConnectivity) {
		this.mDescConnectivity = mDescConnectivity;
	}

	public void toggleChecked()
    {
        mIsChecked = !mIsChecked ;
	}

    private String mId;
    private String mIdRequestCollection;
    private String mIdProduct;
    private String mIdClient;
    private String mIdProductType;
    private String mIdUnitStatus;
    private String mIdNewUser;
    private String mIdBusiness;
    private String mIdModel;
    private String mIsNew;
    private String mIsBroken;
    private String mIsWithdrawal;
    private String mDescClient;
    private String mDescBrand;
    private String mDescModel;
    private String mDescKey;
    private String mDescSoftware;
    private String mDescConnectivity;
    private String mDescUnitStatus;
    private String mDescAccesory;
    private String mDescBusiness;
    private String mDescriptionAccesory;
    private String mNoSerie;
    private String mNoInventory;
    private String mNoIMEI;
    private String mNoSim;
    private String mNoEquipment;
    private String mInventoryPos;
    private String mStatus;
    private String mStatusAccesory;
    private String mNewDate;
	private String mConnectivity;

    private boolean mIsChecked;

    private int mConnStatus;
}