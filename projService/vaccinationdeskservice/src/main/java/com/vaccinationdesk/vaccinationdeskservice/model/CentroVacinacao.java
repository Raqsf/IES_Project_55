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
@Table(name = "centro_vacinacao")
public class CentroVacinacao {
    @Column(name="id", nullable = false, unique = true)
    private int id;
    @Column(name="nome", nullable = false)
    private String nome;
    @Column(name="morada", nullable = false)
    private String morada;
    @Column(name="capacidade_max", nullable = false)
    private int capacidadeMax;
    @Column(name="capacidade_atual")
    private int capacidadeAtual;

    public CentroVacinacao(int id, String nome, String morada, int max, int atual){
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.capacidadeMax = max;
        this.capacidadeAtual = atual;
    }

    @Id
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
