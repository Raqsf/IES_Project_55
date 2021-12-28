package com.vaccinationdesk.vaccinationdeskservice.Service;

import com.vaccinationdesk.vaccinationdeskservice.broker.MQConsumer;

public class Distribuicao {
    

    private MQConsumer consumer;
    int capacidadeDia = consumer.getQuantityForDay();

    //! aqui dividir de forma "igual" o numero de vacinas que ha, com o numero de pessoas para determinado dia
    //! e ainda fazer as contas, com o sitio de onde sao as pessoas e para que centro de vacinacao deveriam ir
    //! levar a vacina
    public void distribuirVacinas() {
        



    }


}
