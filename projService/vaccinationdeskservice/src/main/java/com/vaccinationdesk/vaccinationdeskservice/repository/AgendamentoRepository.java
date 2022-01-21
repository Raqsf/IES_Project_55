package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.sql.Timestamp;
import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{
    @Query("SELECT a FROM Agendamento as a WHERE n_utente = :utente")
    Agendamento findAllByUtente(@Param("utente") Integer utente);

    @Query("SELECT a FROM Agendamento a WHERE a.diaVacinacao >= :ts1 AND a.diaVacinacao <= :ts2")
    List<Agendamento> findAllTotalVaccinesByDate(@Param("ts1") Timestamp ts1, @Param("ts2") Timestamp ts2);

    @Query("SELECT a FROM Agendamento a WHERE a.centro_vacinacao = :cv AND a.diaVacinacao >= :ts1 AND a.diaVacinacao <= :ts2")
    List<Agendamento> findAllTotalVaccinesByDate(@Param("ts1") Timestamp ts1, @Param("ts2") Timestamp ts2, @Param("cv") CentroVacinacao cv);

    @Procedure(procedureName = "getAgendamentosPorDia")
    List<Agendamento> getAgendamentosPorDia(@Param("dia") String dia);

    @Procedure(procedureName = "checkQRcode")
    Agendamento checkQRcode(@Param("numero_utente") Integer n_utente, @Param("centro_id") Integer centro_id, @Param("dia") String dia);
}
