package com.artefacto.microformas.beans;

/**
 * Created by GSI-1045 on 22/03/2017.
 * Agregado para catalogo de Causas de
 */

public class CausaRetiroBean {
    private int id;
    private int idcliente;
    private String descCausaRetiro;
    private String Status;
    private String connStatus;


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

    public String getDescCausaRetiro() {
        return descCausaRetiro;
    }

    public void setDescCausaRetiro(String descCausaRetiro) {
        this.descCausaRetiro = descCausaRetiro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){

        return "CausaRetiroBean{" + "Id=" + id + ", idcliente= "+ getIdcliente() + ", descCausaRetiro='" + descCausaRetiro + "\', status='" + Status + "\'" + "}";

    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }
}
