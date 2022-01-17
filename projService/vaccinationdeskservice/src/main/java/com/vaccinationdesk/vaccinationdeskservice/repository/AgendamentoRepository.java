package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.sql.Date;
import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{
    @Query("SELECT a FROM Agendamento as a WHERE n_utente = :utente")
    List<Agendamento> findAllByUtente(@Param("utente") Integer utente);

    @Procedure(procedureName = "getAgendamentosPorDia")
    List<Agendamento> getAgendamentosPorDia(@Param("dia") String dia);
}
