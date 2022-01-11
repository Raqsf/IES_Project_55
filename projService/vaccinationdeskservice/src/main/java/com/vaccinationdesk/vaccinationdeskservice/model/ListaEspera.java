package com.vaccinationdesk.vaccinationdeskservice.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
    private Timestamp dataInscricao;

    public ListaEspera() {
    }

    public ListaEspera(Utente utente, Timestamp dataInscricao) {
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

    public Timestamp getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Timestamp dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
}
