package com.vaccinationdesk.vaccinationdeskservice.model;

import java.sql.Date;

public class Lote {
    private int id;
    private int quantidade;
    private Date dataValidade;
    private CentroVacinacao centroVacinacao;
}
