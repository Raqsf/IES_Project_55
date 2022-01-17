package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{
    @Query("SELECT a FROM Agendamento as a WHERE n_utente = :utente")
    Agendamento findAllByUtente(@Param("utente") Integer utente);
}
