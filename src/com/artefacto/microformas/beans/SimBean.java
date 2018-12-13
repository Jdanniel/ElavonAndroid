package com.artefacto.microformas.beans;

import java.io.Serializable;

public class SimBean implements Serializable
{
    public SimBean()
    {
        this.mIdSim = "";
        this.mIdClient = "";
        this.mIdUnit = "";
        this.mIdCarrier = "";
        this.mIdItems = "";
        this.mNoSim = "";
        this.mDescClient = "";
        this.mDescCarrier = "";
        this.mIsAvailable = "";
        this.mIsNew = "";
        this.mIsBroken = "";
        this.mIsRetirement = "";
    }

    public String getIdSim()
    {
        return mIdSim;
    }

    public void setIdSim(String idSim)
    {
        this.mIdSim = idSim;
    }

    public String getIdClient()
    {
        return mIdClient;
    }

    public void setIdClient(String idClient)
    {
        this.mIdClient = idClient;
    }

    public String getIdUnit()
    {
        return mIdUnit;
    }

    public void setIdUnit(String idUnit)
    {
        this.mIdUnit = idUnit;
    }

    public String getIdCarrier()
    {
        return mIdCarrier;
    }

    public void setIdCarrier(String idCarrier)
    {
        this.mIdCarrier = idCarrier;
    }

    public String getIdItems()
    {
        return mIdItems;
    }

    public void setIdItems(String idItems)
    {
        this.mIdItems = idItems;
    }

    public String getNoSim()
    {
        return mNoSim;
    }

    public void setNoSim(String noSim)
    {
        this.mNoSim = noSim;
    }

    public String getDescClient()
    {
        return mDescClient;
    }

    public void setDescClient(String descClient)
    {
        this.mDescClient = descClient;
    }

    public String getDescCarrier()
    {
        return mDescCarrier;
    }

    public void setDescCarrier(String descCarrier)
    {
        this.mDescCarrier = descCarrier;
    }

    public String getIsAvailable()
    {
        return mIsAvailable;
    }

    public void setIsAvailable(String isAvailable)
    {
        this.mIsAvailable = isAvailable;
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

    public String getIsRetirement()
    {
        return mIsRetirement;
    }

    public void setIsRetirement(String isRetrieve)
    {
        this.mIsRetirement = isRetrieve;
    }

    public boolean isChecked()
    {
        return mChecked;
    }

    public void setChecked(boolean mChecked)
    {
        this.mChecked = mChecked;
    }

    @Override
    public String toString()
    {
        return "SimBean{" +
                "idSim='" + mIdSim + '\'' +
                ", idClient='" + mIdClient + '\'' +
                ", idUnit='" + mIdUnit + '\'' +
                ", idCarrier='" + mIdCarrier + '\'' +
                ", idItems='" + mIdItems + '\'' +
                ", noSim='" + mNoSim + '\'' +
                ", descClient='" + mDescClient + '\'' +
                ", descCarrier='" + mDescCarrier + '\'' +
                ", isAvailable='" + mIsAvailable + '\'' +
                ", isNew='" + mIsNew + '\'' +
                ", isBroken='" + mIsBroken + '\'' +
                ", isRetirement='" + mIsRetirement + '\'' +
                ", checked=" + mChecked +
                '}';
    }

    private String mIdSim;
    private String mIdClient;
    private String mIdUnit;
    private String mIdCarrier;
    private String mIdItems;
    private String mNoSim;
    private String mDescClient;
    private String mDescCarrier;
    private String mIsAvailable;
    private String mIsNew;
    private String mIsBroken;
    private String mIsRetirement;

    private boolean mChecked;
}
