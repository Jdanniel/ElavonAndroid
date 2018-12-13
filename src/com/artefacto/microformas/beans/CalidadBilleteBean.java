package com.artefacto.microformas.beans;

/**
 * Created by GSI-001061_ on 30/01/2018.
 */

public class CalidadBilleteBean {

    private int id;
    private String descCalidadBillete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescCalidadBillete() {
        return descCalidadBillete;
    }

    public void setDescCalidadBillete(String descCalidadBillete) {
        this.descCalidadBillete = descCalidadBillete;
    }

    @Override
    public String toString(){
        return "CalidadBilleteBean{" + "Id=" + id + ", descCalidadBillete= '"+ getDescCalidadBillete() + "\'" + "}";
    }
}
