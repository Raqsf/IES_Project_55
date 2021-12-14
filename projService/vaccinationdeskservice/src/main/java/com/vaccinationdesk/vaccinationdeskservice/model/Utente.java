package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Utente {
    private int nUtente;
    private String nome;
    private String email;
    private String morada;
    private Date dataNascimento;
    private String doencas;

    public Utente(){};
    
    public Utente( int numUtente, String nome, String email, String morada, Date dataNascimento, String doencas){
        this.nome = nome;
        this.nUtente = numUtente;
        this.email = email;
        this.morada=morada;
        this.dataNascimento = dataNascimento;
        this.doencas = doencas;
    }

    public String getNome(){
        return nome;
    }
    public int nUtente(){
        return nUtente;
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
    public String getDoencas(){
        return doencas;
    }

}
