package com.artefacto.microformas.beans;

/**
 * Created by GSI-001061_ on 30/01/2018.
 */

public class CondicionSiteBean {

    private int id;
    private String descCondicionSite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescCondicionSite() {
        return descCondicionSite;
    }

    public void setDescCondicionSite(String descCondicionSite) {
        this.descCondicionSite = descCondicionSite;
    }

    @Override
    public String toString(){
        return "CondicionSiteBean{" + "Id=" + id + ", descCondicionSite= '"+ getDescCondicionSite() + "\'" + "}";
    }

}
