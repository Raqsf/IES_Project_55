package com.vaccinationdesk.vaccinationdeskservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class DoencaId implements Serializable{
    @ManyToOne
    @JoinColumn(name="n_utente")
    public Utente utente;
    // TODO next annotation might be wrong, check afterwards with data
    @ManyToOne
    @JoinColumn(name="doenca")
    public Doenca doenca;

    public DoencaId(){}

    public DoencaId(Utente utente, Doenca doenca){
        this.utente = utente;
        this.doenca=doenca;
    }
}