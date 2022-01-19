package com.vaccinationdesk.vaccinationdeskservice.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "capacidade_por_dia")
public class Capacidade implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue()
    private int id;
    
    @Column(name = "dia")
    private Date dia;

    @Column(name="quantidade")
    private int quantidade;

    public Capacidade() {
    }

    public Capacidade(Date dia) {
        this.dia = dia;
    }

    public Capacidade(Date dia, int quantidade) {

        this.dia = dia;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }    
}
