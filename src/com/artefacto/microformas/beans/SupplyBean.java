package com.artefacto.microformas.beans;

import java.io.Serializable;

public class SupplyBean implements Serializable
{
    public String getIdClient()
    {
        return mIdClient;
    }

    public void setIdClient(String idClient)
    {
        this.mIdClient = idClient;
    }

    public String getIdSupply()
    {
        return mIdSupply;
    }

    public void setIdSupply(String idSupply)
    {
        this.mIdSupply = idSupply;
    }

    public String getDescClient()
    {
        return mDescClient;
    }

    public void setDescClient(String descClient)
    {
        this.mDescClient = descClient;
    }

    public String getDescSupply()
    {
        return mDescSupply;
    }

    public void setDescSupply(String descSupply)
    {
        this.mDescSupply = descSupply;
    }

    public String getCount()
    {
        return mCount;
    }

    public void setCount(String count)
    {
        this.mCount = count;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean isChecked) {
        this.mChecked = isChecked;
    }

    private String mIdClient;
    private String mIdSupply;
    private String mDescClient;
    private String mDescSupply;
    private String mCount;

    private boolean mChecked;
}
