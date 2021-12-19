package com.vaccinationdesk.vaccinationdeskservice.controller;

import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtenteController {
    
    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping("/utente/{nome}")
    public Utente getUtenteByNome(@PathVariable String nome) {
        return utenteRepository.findUtenteByNome(nome);
    }

    /*@GetMapping("/utente/{n_utente}")
    public Utente getUtenteByNumUtente(@PathVariable int n_utente) {
        return utenteRepository.findUtenteByNumUtente(n_utente);
    }*/

}
