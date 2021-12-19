package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
// @NoArgsConstructor
@Entity
@Table(name = "Pessoa")
public class Utente {
    @Id
    @Column(name="n_utente")
    private int nUtente;
    @Column(name="nome")
    private String nome;
    @Column(name="email")
    private String email;
    @Column(name="morada")
    private String morada;
    @Column(name="data_nascimento")
    private Date dataNascimento;
    @Column(name="doencas")
    private Doenca doencas;

    // public Utente(){}

    public Utente( int numUtente, String nome, String email, String morada, Date dataNascimento, Doenca doencas){
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
    public Doenca getDoencas(){
        return doencas;
    }

}
