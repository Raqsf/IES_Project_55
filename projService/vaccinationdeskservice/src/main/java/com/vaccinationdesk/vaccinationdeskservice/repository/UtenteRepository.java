package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Utente findUtenteById(Integer n_utente);

    Utente findUtenteByNome(String nome);
    
    @Procedure(procedureName = "getUtenteInfoDiaVacina")
    List<Utente> getUtenteInfoDiaVacina(@Param("idCentro") Integer centro, @Param("data") String dia);

    @Procedure(procedureName = "getUtentesInCenter")
    List<Utente> getUtentesInCenter(@Param("centro") Integer centro);
}
