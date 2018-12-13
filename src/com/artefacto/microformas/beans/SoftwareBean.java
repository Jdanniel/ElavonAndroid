package com.artefacto.microformas.beans;

public class SoftwareBean
{
    public int getId()
    {
        return mId;
    }

    public void setId(int id)
    {
        this.mId = id;
    }

    public int getIdClient()
    {
        return mIdClient;
    }

    public void setIdClient(int idClient)
    {
        this.mIdClient = idClient;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description)
    {
        this.mDescription = description;
    }

    @Override
    public String toString()
    {
        return "SoftwareBean{" + "mId=" + mId + ", mIdClient=" + mIdClient
                + ", mDescription='" + mDescription + '\'' + '}';
    }

    private int mId;
    private int mIdClient;

    private String mDescription;
}
