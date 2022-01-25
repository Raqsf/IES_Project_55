package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "pessoa")
public class Utente {
    @Id
    @Column(name="n_utente")
    private int id;
    @Column(name="nome")
    private String nome;
    @Column(name="email")
    private String email;
    @Column(name="morada")
    private String morada;
    @Column(name="data_nascimento")
    private Date dataNascimento;

    public Utente(){}

    public Utente( int numUtente, String nome, Date dataNascimento){
        this.id = numUtente;
        this.nome = nome;
        this.dataNascimento=dataNascimento;
    }

    public Utente(int numUtente, String nome){
        this.id = numUtente;
        this.nome = nome;
    }

    public Utente( int numUtente, String nome, String email, String morada, Date dataNascimento){
        this.nome = nome;
        this.id = numUtente;
        this.email = email;
        this.morada=morada;
        this.dataNascimento = dataNascimento;
    }

    public String getNome(){
        return nome;
    }
    
    public int getID(){
        return id;
    }
    public Date getDataNascimento(){
        return dataNascimento;
    }
    public String getEmail(){
        return email;
    }
    public String getMorada(){
        return morada;
    }

    public void setMorada(String string) {
        morada = string;
    }
}