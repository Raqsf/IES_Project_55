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
    private int id;
    @Column(name="quantidade")
    private int quantidade;
    @Column(name="data_validade")
    private Date dataValidade;
    @ManyToOne
    @JoinColumn(name="atribuida_ao_centro")
    private CentroVacinacao centroVacinacao;

    public Lote(){}

    public Lote(int id, int quantidade, Date validade, CentroVacinacao centro){
        this.id = id;
        this.quantidade = quantidade;
        this.dataValidade = validade;
        this.centroVacinacao=centro;
    }

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