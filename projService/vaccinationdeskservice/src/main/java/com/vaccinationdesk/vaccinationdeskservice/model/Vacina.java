package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;

public class Vacina {
    private int id;
    private Lote lote;
    private String nome;
    private Date rececao;
    private Utente utente;
    private Date administracao;
    private CentroVacinacao centro;

    public Vacina(){}

    public Vacina(int id, Lote lote, String nome, Date rececao, Utente utente, Date administracao, CentroVacinacao centro){
        this.id = id;
        this.lote=lote;
        //this.nome≃^^
    }
}
