package com.artefacto.microformas.beans;

public class NetworkBean
{
    public NetworkBean()
    {
        mStatus = "";
        mMessage = "";
    }

    public String getStatus()
    {
        return mStatus;
    }

    public void setStatus(String mStatus)
    {
        this.mStatus = mStatus;
    }

    public String getMessage()
    {
        return mMessage;
    }

    public void setMessage(String message)
    {
        this.mMessage = message;
    }

    public static String STATUS_TIMEOUT = "network_timeout";
    public static String STATUS_EXCEPTION = "network_exception";
    public static String STATUS_OFFLINE = "network_offline";

    private String mStatus;
    private String mMessage;
}
