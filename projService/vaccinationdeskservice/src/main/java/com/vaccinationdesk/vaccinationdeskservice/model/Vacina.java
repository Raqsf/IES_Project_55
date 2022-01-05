package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "vacina")
public class Vacina {
    @Id
    @Column(name = "n_vacina")
    @GeneratedValue()
    private int id;
    @ManyToOne
    @JoinColumn(name="lote")
    private Lote lote;
    @Column(name="nome")
    private String nome;
    @Column(name="data_validade")
    private Date dataValidade;
    @OneToOne
    @JoinColumn(name="administrada_a")
    private Utente utente;
    @Column(name="data_administracao")
    private Date administracao;

    public Vacina() {
    }
    
    public Vacina(Lote lote, String nome, Date dataValidade) {
        this.lote=lote;
        this.nome = nome;
        this.dataValidade = dataValidade;
    }

    public Vacina(Lote lote, String nome, Date dataValidade, Utente utente, Date administracao){
        this.lote=lote;
        this.nome = nome;
        this.dataValidade = dataValidade;
        this.utente = utente;
        this.administracao = administracao;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public int getID(){
        return this.id;
    }

    public Lote getLote(){
        return this.lote;
    }

    public String getNome(){
        return this.nome;
    }

    public Utente getUtente(){
        return this.utente;
    }

    public Date getAdministracao(){
        return this.administracao;
    }
}