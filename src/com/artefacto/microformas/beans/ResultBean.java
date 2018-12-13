package com.artefacto.microformas.beans;

public class ResultBean
{
    public ResultBean()
    {
        message = "";
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    private String message;
}
