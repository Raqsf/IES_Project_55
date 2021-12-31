package com.vaccinationdesk.vaccinationdeskservice.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "lista_de_espera")
public class ListaEspera implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue()
    private int id;

    @OneToOne
    @JoinColumn(name = "n_utente")
    private Utente utente;

    @Column(name = "data_inscricao")
    private Date dataInscricao;

    public ListaEspera() {
    }

    public ListaEspera(Utente utente, Date dataInscricao) {
        this.utente = utente;
        this.dataInscricao = dataInscricao;
    }


    public ListaEspera(int id, Utente utente) {
        this.id = id;
        this.utente = utente;
    }
    
    public ListaEspera(Utente utente) {
        this.utente = utente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
}
