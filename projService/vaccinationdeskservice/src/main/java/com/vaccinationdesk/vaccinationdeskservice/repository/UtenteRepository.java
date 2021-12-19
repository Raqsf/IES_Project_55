package com.vaccinationdesk.vaccinationdeskservice.repository;

import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    List<Utente> findUtenteByNome(String nome);
    List<Utente> findUtenteByNumUtente(String nome);
}
