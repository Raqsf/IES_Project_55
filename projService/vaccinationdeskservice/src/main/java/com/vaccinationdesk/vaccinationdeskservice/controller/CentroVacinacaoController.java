package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Lote;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.LoteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // @GetMapping("/int")
    // public Integer getInt() {
    //     return 123456;
    // }
    
}
