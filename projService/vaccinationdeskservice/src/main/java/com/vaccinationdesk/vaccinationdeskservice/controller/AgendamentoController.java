package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.google.zxing.WriterException;
import com.vaccinationdesk.vaccinationdeskservice.Service.Distribuicao;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private AgendamentoRepository agendamentoRepository;

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

    @GetMapping("/get_por_dia")
    public List<Agendamento> getAgendarDia() {
        return agendamentoRepository.getAgendamentosPorDia("2022-01-20");
    }

    @PostMapping("/agendar_com_filtros")
    public ResponseEntity<ListaEspera> agendarComFiltros(@Valid @RequestBody String filtros) {
        // {idade: int, doenca: int}
        // {doenca: int}
        // {idade: int}
        distribuicao.distribuirVacinasPorFiltros(filtros);
        return null;
    }


}
