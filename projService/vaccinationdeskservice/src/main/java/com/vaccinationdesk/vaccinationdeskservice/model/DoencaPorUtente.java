package com.vaccinationdesk.vaccinationdeskservice.model;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.vaccinationdesk.vaccinationdeskservice.repository.DoencaPorUtenteRepository;

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