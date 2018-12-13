package com.artefacto.microformas.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class ShipmentBean implements Serializable
{
	public String getIdShipment()
    {
		return mIdShipment;
	}

    public void setIdShipment(String idShipment)
    {
		this.mIdShipment = idShipment;
	}

    public String getResponsibleTypeDesc()
    {
		return mResponsibleTypeDesc;
	}

    public void setResponsibleTypeDesc(String responsibleTypeDesc)
    {
		this.mResponsibleTypeDesc = responsibleTypeDesc;
	}

    public String getResponsible()
    {
		return mResponsible;
	}

    public void setResponsible(String responsible)
    {
		this.mResponsible = responsible;
	}

    public String getMessagingServiceDesc()
    {
		return mMessagingServiceDesc;
	}

    public void setMessagingServiceDesc(String messagingServiceDesc)
    {
		this.mMessagingServiceDesc = messagingServiceDesc;
	}

    public String getNoGuide()
    {
		return mNoGuide;
	}

    public void setNoGuide(String noGuide)
    {
		this.mNoGuide = noGuide;
	}

    public String getDate()
    {
		return mDate;
	}

    public void setDate(String date)
    {
		this.mDate = date;
	}

    public ArrayList<UnitBean> getUnitBeanArray()
    {
		return mUnitBeanArray;
	}

    public void setUnitBeanArray(ArrayList<UnitBean> unitBeanArray)
    {
		this.mUnitBeanArray = unitBeanArray;
	}

    public ArrayList<SupplyBean> getSupplyBeanArray()
    {
        return mSupplyBeanArray;
    }

    public void setSupplyBeanArray(ArrayList<SupplyBean> supplyBeanArray)
    {
        this.mSupplyBeanArray = supplyBeanArray;
    }

    public ArrayList<SimBean> getSimBeanArray() {
        return mSimBeanArray;
    }

    public void setSimBeanArray(ArrayList<SimBean> simBeanArray) {
        this.mSimBeanArray = simBeanArray;
    }

    public String getDescClient()
    {
        if(mUnitBeanArray != null)
        {
            if(mUnitBeanArray.size() > 0)
            {
                return mUnitBeanArray.get(0).getDescClient();
            }
        }

        if(mSupplyBeanArray != null)
        {
            if(mSupplyBeanArray.size() > 0)
            {
                return mSupplyBeanArray.get(0).getDescClient();
            }
        }

        if(mSimBeanArray != null)
        {
            if(mSimBeanArray.size() > 0)
            {
                return mSimBeanArray.get(0).getDescClient();
            }
        }

        return "";
    }

    public int getConnStatus()
    {
		return connStatus;
	}

    public void setConnStatus(int connStatus)
    {
		this.connStatus = connStatus;
	}

    private static final long serialVersionUID = 1L;

    private String mIdShipment;
    private String mResponsible;
    private String mResponsibleTypeDesc;
    private String mMessagingServiceDesc;
    private String mNoGuide;
    private String mDate;

    private ArrayList<UnitBean> mUnitBeanArray;

    private ArrayList<SupplyBean> mSupplyBeanArray;

    private ArrayList<SimBean> mSimBeanArray;

    private int connStatus;
}