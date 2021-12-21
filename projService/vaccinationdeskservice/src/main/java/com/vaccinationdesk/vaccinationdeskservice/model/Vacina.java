package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "vacina")
public class Vacina {
    @Id
    @Column(name="n_vacina")
    private int id;
    @ManyToOne
    @JoinColumn(name="lote")
    private Lote lote;
    @Column(name="nome")
    private String nome;
    @Column(name="data_rececao")
    private Date rececao;
    @OneToOne
    @JoinColumn(name="administrada_a")
    private Utente utente;
    @Column(name="data_administracao")
    private Date administracao;

    public Vacina(){}

    public Vacina(int id, Lote lote, String nome, Date rececao, Utente utente, Date administracao){
        this.id = id;
        this.lote=lote;
        this.nome=nome;
        this.rececao = rececao;
        this.utente = utente;
        this.administracao=administracao;
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

    public Date getRececao(){
        return this.rececao;
    }

    public Utente getUtente(){
        return this.utente;
    }

    public Date getAdministracao(){
        return this.administracao;
    }
}