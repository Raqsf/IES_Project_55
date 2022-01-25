package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.util.List;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaccinationdesk.vaccinationdeskservice.Service.Vacinacao;
import com.vaccinationdesk.vaccinationdeskservice.exception.ConflictException;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;
import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/api/v1/vacinacao")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:3000" })
public class VacinacaoController {

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private Vacinacao vacinacao;
    
    @Async
    @GetMapping("/vacinas")
    public List<Vacina> getAllVacinas() {
        return vacinaRepository.findAll();
    }

    @Async
    @GetMapping("/vacinas_a_ser_tomadas")
    public ResponseEntity<Object> getAllVacinasTomadas() throws JsonProcessingException, ConflictException {
        return vacinacao.vacinacao();
    }

    @Async
    @GetMapping("/real_time/{id}")
    public List<Utente> getVacincaoTempoReal(@PathVariable Integer id) {
        return vacinacao.getVacinacaoEmTempoReal(id);
    }

    @Async
    @GetMapping("/vacinas_administradas_hoje/{id}")
    public List<Vacina> getVacinasInfoDiaVacina(@PathVariable Integer id) throws JsonProcessingException {
        return vacinacao.getVacinasInfoDia(id);
    }

    @Async
    @GetMapping("/utente_vacinados/{id}")
    public List<Utente> getUtentesVacinadosPorDia (@PathVariable Integer id) throws JsonProcessingException {
        return vacinacao.getUtentesVacinadosPorDia(id);
    }
    
}
