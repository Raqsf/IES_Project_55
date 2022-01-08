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

import lombok.Data;


@Data
@Entity
@Table(name="doencas_por_utente")
public class DoencaPorUtente implements Serializable{
    @EmbeddedId
    private DoencaId id;

    public Utente getUser() {
        return id.utente;
    }

    public Doenca getDoenca() {
        return id.doenca;
    }
}