package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Lote;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.LoteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CentroVacinacaoController {
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private LoteRepository loteRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/centrovacinacao/{centro}")
    public CentroVacinacao centroVacinacao(@PathVariable Integer centro) {
        return centroVacinacaoRepository.findCentroVacinacaoById(centro);
    }

    @GetMapping("/centrovacinacao")
    public List<CentroVacinacao> centroVacinacao() {
        return centroVacinacaoRepository.findAll();
    }

    @GetMapping("/utente/{nome}")
    public Utente getUtenteByNome(@PathVariable String nome) {
        return utenteRepository.findUtenteByNome(nome);
    }

    /*@GetMapping("/utente/{n_utente}")
    public Utente getUtenteByNumUtente(@PathVariable int n_utente) {
        return utenteRepository.findUtenteByNumUtente(n_utente);
    }*/

    @GetMapping("/lote/{id}")
    public Lote getUtenteByNome(@PathVariable Integer lote) {
        return loteRepository.findLoteById(lote);
    }

    @GetMapping("/lote")
    public List<Lote> getUtenteByNome() {
        return loteRepository.findAll();
    }

    @PostMapping("/utente")
    public ResponseEntity<Agendamento> createAppointment(@Valid @RequestBody Utente utente ){
        
        if (utenteRepository.findUtenteById(utente.getID()) != null){
            long millis=System.currentTimeMillis();  
            Agendamento a = new Agendamento(utente, new Date(millis), new CentroVacinacao());
            return ResponseEntity.ok(agendamentoRepository.save(a));
        }

        return ResponseEntity.notFound().build();
    }

    // @GetMapping("/int")
    // public Integer getInt() {
    //     return 123456;
    // }
    
}
