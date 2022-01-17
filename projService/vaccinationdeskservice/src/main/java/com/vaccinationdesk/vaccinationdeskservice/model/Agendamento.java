package com.vaccinationdesk.vaccinationdeskservice.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
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
@Table(name = "agendamento")
public class Agendamento implements Serializable{
    @Id
    @Column(name="id")
    @GeneratedValue()
    private int id;
    @OneToOne
    @JoinColumn(name="n_utente")
    private Utente utente;
    @Column(name="dia_vacinacao")
    private Timestamp diaVacinacao;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="centro_vacinacao", referencedColumnName = "id")
    private CentroVacinacao centro_vacinacao;

    public Agendamento() {
    }

    public Agendamento(Utente utente) {
        this.utente = utente;
    }

    public Agendamento(Utente utente, Timestamp diaVacinacao, CentroVacinacao centro_vacinacao){
        this.utente = utente;
        this.diaVacinacao = diaVacinacao;
        this.centro_vacinacao = centro_vacinacao;
    }
    public int getId(){
        return this.id;
    }

    public Utente getUtente(){
        return this.utente;
    }

    public Timestamp getDiaVacinacao(){
        return this.diaVacinacao;
    }

    public CentroVacinacao getCentro() {
        return this.centro_vacinacao;
    }

    @Override
    public String toString() {
        return "Agendamento [centro_vacinacao=" + centro_vacinacao + ", diaVacinacao=" + diaVacinacao + ", id=" + id
                + ", utente=" + utente + "]";
    }
    
    
}
