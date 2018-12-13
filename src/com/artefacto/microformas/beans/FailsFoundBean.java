package com.artefacto.microformas.beans;

public class FailsFoundBean
{
    public FailsFoundBean()
    {
        this.id = Integer.MIN_VALUE;
        this.idClient = Integer.MIN_VALUE;
        this.desc = "";
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIdClient()
    {
        return idClient;
    }

    public void setIdClient(int idClient)
    {
        this.idClient = idClient;
    }

    public int getIdFather()
    {
        return idFather;
    }

    public void setIdFather(int idFather)
    {
        this.idFather = idFather;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    @Override
    public String toString()
    {
        return "FailsFoundBean{ id=" + id + ", idClient=" + idClient + ", idFather=" + idFather +
            ", desc='" + desc + '\'' + '}';
    }

    private int id;
    private int idClient;
    private int idFather;

    private String desc;
}
