package com.vaccinationdesk.vaccinationdeskservice.repository;

import com.vaccinationdesk.vaccinationdeskservice.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
    
}
