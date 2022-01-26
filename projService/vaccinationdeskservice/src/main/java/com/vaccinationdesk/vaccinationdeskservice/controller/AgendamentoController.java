package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.vaccinationdesk.vaccinationdeskservice.Service.Distribuicao;
import com.vaccinationdesk.vaccinationdeskservice.exception.ConflictException;
import com.vaccinationdesk.vaccinationdeskservice.exception.ResourceNotFoundException;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:3000" })
public class AgendamentoController {

    @Autowired
    private ListaEsperaRepository listaesperaRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private Distribuicao distribuicao;

    @Async
    @GetMapping("/agendar")
    public List<Agendamento> agendar() throws Exception {
        try {
            return distribuicao.distribuirVacinasPorOrdemMarcacao();
        } catch (Exception e) {
            throw e;
        }
    }

    @Async
    @GetMapping("/listaespera")
    public List<ListaEspera> getAllListaEspera() {
        return listaesperaRepository.findAll();
    }

    @Async
    @GetMapping("/listaespera/{id}")
    public ListaEspera getListaEsperaByid(@PathVariable Integer id) {
        return listaesperaRepository.findListaEsperaByid(id);
    }

    @Async
    @PostMapping("/agendar_com_filtros")
    public ResponseEntity<List<Agendamento>> agendarComFiltros(@Valid @RequestBody String filtros) throws Exception {
        // {idade: int, doenca: int}
        // {doenca: int}
        // {idade: int}
        try {
            List<Agendamento> le = distribuicao.distribuirVacinasPorFiltros(filtros);
            return ResponseEntity.ok(le);
        } catch (Exception e) {
            throw e;
        }
        
    }

}
