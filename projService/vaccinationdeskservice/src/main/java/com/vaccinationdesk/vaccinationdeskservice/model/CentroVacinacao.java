package com.vaccinationdesk.vaccinationdeskservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vaccinationdesk.vaccinationdeskservice.exception.GlobalExceptionHandler;

import lombok.Data;

@Data
@Entity
@Table(name = "centro_vacinacao")
public class CentroVacinacao {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "morada")
    private String morada;
    @Column(name = "capacidade_max")
    private int capacidadeMax;
    @Column(name = "capacidade_atual")
    private int capacidadeAtual;

    public CentroVacinacao() {
    }

    public CentroVacinacao(int id) {
        this.id = id;
    }

    public CentroVacinacao(int id, String nome, String morada, int max, int atual) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.capacidadeMax = max;
        this.capacidadeAtual = atual;
    }

    public int getID() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getMorada() {
        return this.morada;
    }

    public int getCapacidadeMax() {
        return this.capacidadeMax;
    }

    public int getCapacidadeAtual() {
        return this.capacidadeAtual;
    }

    public void setCapacidadeMax(int capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
    }

    public void setCapacidadeAtual(int capacidadeAtual) {
        this.capacidadeAtual = capacidadeAtual;
    }

    public void incrementCapacidadeAtual(int capacidadeAtual) {
        this.capacidadeAtual += capacidadeAtual;
    }

    public void decreaseCapacidadeAtual() {
        if (this.capacidadeAtual > 0) {
            this.capacidadeAtual -= 1;
        } else {
            System.err.println(this.nome + " está sem vacinas");
        }
    }

}