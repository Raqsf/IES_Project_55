package com.vaccinationdesk.vaccinationdeskservice.model;

import javax.persistence.ManyToOne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="doenca")
// TODO
public class Doenca {
    @Column(name="")
    private int id;
    @Column(name="")
    private String name;
    @ManyToOne
    // TODO
    @JoinColumn(name="utente")
    private Utente utente;

    public Doenca(){}
    public Doenca(int id, String name, Utente utente){
        this.id=id;
        this.name=name;
        this.utente=utente;
    }
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Utente getUtente(){
        return utente;
    }
}
