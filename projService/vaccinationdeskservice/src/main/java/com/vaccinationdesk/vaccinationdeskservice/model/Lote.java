package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;

import javax.persistence.Id;

public class Lote {
    private int id;
    private int quantidade;
    private Date dataValidade;
    private CentroVacinacao centroVacinacao;

    public Lote(int id, int quantidade, Date validade, CentroVacinacao centro){
        this.id = id;
        this.quantidade = quantidade;
        this.dataValidade = validade;
        this.centroVacinacao=centro;
    }

    @Id
    public int getID(){
        return this.id;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

    public Date getDataValidade(){
        return this.dataValidade;
    }

    public CentroVacinacao getCentroVacinacao(){
        return this.centroVacinacao;
    }
}
