package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "lote")
public class Lote {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="quantidade")
    private int quantidade;
    @ManyToOne
    @JoinColumn(name="atribuida_ao_centro")
    private CentroVacinacao centroVacinacao;

    @Column(name = "data_chegada")
    private Date data_chegada;
    

    public Lote(){}

    public Lote(String id, int quantidade, CentroVacinacao centro, Date data_chegada){
        this.id = id;
        this.quantidade = quantidade;
        this.centroVacinacao = centro;
        this.data_chegada = data_chegada;
    }

    public Date getData_chegada() {
        return data_chegada;
    }

    public void setData_chegada(Date data_chegada) {
        this.data_chegada = data_chegada;
    }

    public String getID(){
        return this.id;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

    public CentroVacinacao getCentroVacinacao(){
        return this.centroVacinacao;
    }
}