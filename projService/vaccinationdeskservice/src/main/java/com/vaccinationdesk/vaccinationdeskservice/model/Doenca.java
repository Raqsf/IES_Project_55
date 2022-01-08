package com.vaccinationdesk.vaccinationdeskservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="doencas")
public class Doenca {
    @Id
    @Column(name="id")
    @GeneratedValue()
    private int id;

    @Column(name="doenca")
    private String doenca;

    public Doenca(){}

    public Doenca(int id, String doenca){
        this.id=id;
        this.doenca=doenca;
    }

    public int getID(){
        return this.id;
    }

    public String getDoenca(){
        return this.doenca;
    }
}
