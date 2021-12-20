package com.vaccinationdesk.vaccinationdeskservice.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name="n_utente", insertable = false, updatable = false)
    private int num_utente;
    @OneToOne
    @JoinColumn(name="n_utente")
    private Utente nUtente;
    @Column(name="dia_vacinacao")
    private Date diaVacinacao;
    @ManyToOne
    @JoinColumn(name="centro_vacinacao")
    private CentroVacinacao centro_vacinacao;

    public Agendamento(){}

    public Agendamento(Utente nUtente, Date diaVacinacao, CentroVacinacao centro_vacinacao){
        this.num_utente = nUtente.getID();
        this.nUtente = nUtente;
        this.diaVacinacao = diaVacinacao;
        this.centro_vacinacao = centro_vacinacao;
    }

    public Utente getNUtente(){
        return this.nUtente;
    }

    public Date getDiaVacinacao(){
        return this.diaVacinacao;
    }

    public CentroVacinacao getCentro(){
        return this.centro_vacinacao;
    }
}
