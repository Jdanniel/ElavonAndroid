package com.artefacto.microformas.beans;

/**
 * Created by GSI-001061_ on 16/08/2017.
 */

public class EtiquetasBean {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getEtiqueta() {
        return descEtiqueta;
    }

    public void setEtiqueta(String descEtiqueta) {
        this.descEtiqueta = descEtiqueta;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getConnStatus() {
        return connStatus;
    }

    public void setConnStatus(String connStatus) {
        this.connStatus = connStatus;
    }

    @Override
    public String toString(){

        return "EtiquetasBean{" + "Id=" + id + ", idcliente= "+ getIdcliente() + ", descEtiqueta='" + descEtiqueta + "\'" + "}";

    }

    private int idcliente;
    private String descEtiqueta;
    private String Status;
    private String connStatus;

}
