package com.artefacto.microformas.beans;

public class ConnectivityBean
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

    public boolean isGPRS() {
        return mIsGPRS;
    }

    public void setIsGPRS(boolean isGPRS)
    {
        this.mIsGPRS = isGPRS;
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
    public String toString() {
        return "ConnectivityBean{" + "mId=" + mId + ", mIdClient=" + mIdClient +
                ", mIsGPRS=" + mIsGPRS + ", mDescription='" + mDescription + '\'' + '}';
    }

    private int mId;
    private int mIdClient;

    private boolean mIsGPRS;

    private String mDescription;
}
