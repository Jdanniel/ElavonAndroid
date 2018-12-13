package com.artefacto.microformas.beans;

public class ClientBean
{
    public ClientBean()
    {
        id = Integer.MIN_VALUE;
        description = "";
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getActive_mobile_notification() {
        return active_mobile_notification;
    }

    public void setActive_mobile_notification(int active_mobile_notification) {
        this.active_mobile_notification = active_mobile_notification;
    }

    @Override
    public String toString()
    {
        return "ClientBean{id=" + id + ", description='" + description + '\'' + '}';
    }

    private int id;

    private String description;

    private int active_mobile_notification;
}
