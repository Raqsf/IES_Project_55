package com.vaccinationdesk.vaccinationdeskservice.model;


import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="doencas_por_utente")
public class DoencaPorUtente implements Serializable{
    @EmbeddedId
    private DoencaId id;

    public DoencaPorUtente(){}

    public DoencaPorUtente(DoencaId id){
        this.id=id;
    }

    public DoencaPorUtente(Utente u, Doenca d){
        this.id = new DoencaId(u, d);
    }

    public DoencaId getID() {
        return id;
    }

}