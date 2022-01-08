package com.vaccinationdesk.vaccinationdeskservice.model;

import javax.persistence.ManyToOne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name="doenca")
// TODO
public class Doenca {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="nome")
    private String nome;
    // @ManyToOne
    // // TODO
    // @JoinColumn(name="utente")
    // private Utente utente;

    public Doenca(int id, String nome/* , Utente utente */){
        this.id=id;
        this.nome=nome;
        // this.utente=utente;
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    // public Utente getUtente(){
    //     return utente;
    // }
}