package com.vaccinationdesk.vaccinationdeskservice.repository;

import com.vaccinationdesk.vaccinationdeskservice.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Utente findUtenteById(Integer n_utente);
    Utente findUtenteByNome(String nome);
}
