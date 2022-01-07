package com.vaccinationdesk.vaccinationdeskservice.model;

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

    public Lote(){}

    public Lote(String id, int quantidade, CentroVacinacao centro){
        this.id = id;
        this.quantidade = quantidade;
        this.centroVacinacao=centro;
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