package com.vaccinationdesk.vaccinationdeskservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Centro_vacinacao")
public class CentroVacinacao {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="nome")
    private String nome;
    @Column(name="morada")
    private String morada;
    @Column(name="capacidade_max")
    private int capacidadeMax;
    @Column(name="capacidade_atual")
    private int capacidadeAtual;

    public CentroVacinacao(){}

    public CentroVacinacao(int id, String nome, String morada, int max, int atual){
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.capacidadeMax = max;
        this.capacidadeAtual = atual;
    }

    public int getID(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getMorada(){
        return this.morada;
    }

    public int getCapacidadeMax(){
        return this.capacidadeMax;
    }

    public int getCapacidadeAtual(){
        return this.capacidadeAtual;
    }
}