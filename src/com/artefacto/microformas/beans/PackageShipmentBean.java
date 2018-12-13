package com.artefacto.microformas.beans;

public class PackageShipmentBean
{
    public String getIdShipment()
    {
        return mIdShipment;
    }

    public void setIdShipment(String idShipment)
    {
        this.mIdShipment = idShipment;
    }

    public String getType()
    {
        return mType;
    }

    public void setType(String type)
    {
        this.mType = type;
    }

    public int getCount()
    {
        return mCount;
    }

    public void setCount(int count)
    {
        this.mCount = count;
    }

    public static String TYPE_UNIT = "UNIDAD";
    public static String TYPE_SUPPLY = "INSUMO";
    public static String TYPE_SIM = "SIM";

    private String mIdShipment;

    private String mType;

    private int mCount;
}
