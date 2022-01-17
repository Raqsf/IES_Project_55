package com.vaccinationdesk.vaccinationdeskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.DoencaId;
import com.vaccinationdesk.vaccinationdeskservice.model.DoencaPorUtente;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;


public interface DoencaPorUtenteRepository extends JpaRepository<DoencaPorUtente, DoencaId> {
    // @Query(value="SELECT d FROM DoencaPorUtente d WHERE d.DoencaId.n_utente = :utente")
    List<DoencaPorUtente> findByIdUtente(Utente utente);
}
