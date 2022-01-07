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
    private Date data_administracao;

    public Vacina() {
    }
    
    public Vacina(Lote lote, String nome, Date dataValidade) {
        this.lote=lote;
        this.nome = nome;
        this.dataValidade = dataValidade;
    }

    public Vacina(Lote lote, String nome, Date dataValidade, Utente utente, Date data_administracao){
        this.lote=lote;
        this.nome = nome;
        this.dataValidade = dataValidade;
        this.utente = utente;
        this.data_administracao = data_administracao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Date getDataAdministracao() {
        return data_administracao;
    }

    public void setDataAdministracao(Date data_administracao) {
        this.data_administracao = data_administracao;
    }

}