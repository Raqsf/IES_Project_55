package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.google.zxing.WriterException;
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

    // serve só para ver se está tudo a funcionar bem
    @GetMapping("/agendar")
    public void agendar() throws MessagingException, WriterException, IOException {
        distribuicao.distribuirVacinasPorOrdemMarcacao();
    }

    @GetMapping("/listaespera")
    public List<ListaEspera> getAllListaEspera() {
        return listaesperaRepository.findAll();
    }

    @GetMapping("/listaespera/{id}")
    public ListaEspera getListaEsperaByid(@PathVariable Integer id) {
        return listaesperaRepository.findListaEsperaByid(id);
    }


}
