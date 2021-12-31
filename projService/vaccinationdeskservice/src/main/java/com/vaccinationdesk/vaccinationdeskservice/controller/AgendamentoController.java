package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.vaccinationdesk.vaccinationdeskservice.Service.Distribuicao;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/api/v1/agendamento")
@CrossOrigin(origins = { "/http://localhost:3000", "http://localhost:3000" })
public class AgendamentoController {

    @Autowired
    private ListaEsperaRepository listaesperaRepository;

    @Autowired
    private Distribuicao distribuicao;

    @GetMapping("/listaespera")
    public List<ListaEspera> getAllListaEspera() throws MessagingException {
        distribuicao.distribuirVacinasPorOrdemMarcacao();
        return listaesperaRepository.findAll();
    }

    @GetMapping("/listaespera/{id}")
    public ListaEspera getListaEsperaByid(@PathVariable Integer id) {
        return listaesperaRepository.findListaEsperaByid(id);
    }


}
