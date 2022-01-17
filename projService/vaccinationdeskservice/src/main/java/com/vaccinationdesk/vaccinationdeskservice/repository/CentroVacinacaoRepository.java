package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;

// import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface CentroVacinacaoRepository extends JpaRepository<CentroVacinacao, Integer>  {
    
    CentroVacinacao findCentroVacinacaoById(Integer id);
    // List<CentroVacinacao> findAll();

    // @Query(value="SELECT v FROM Vacina v LEFT JOIN Lote l ON v.lote = l.id WHERE l.centroVacinacao = :centro")
    // List<Vacina> findVacinas(@Param("centro") CentroVacinacao centro);

    @Query(value="SELECT l.quantidade FROM Lote l WHERE l.centroVacinacao.id = :centro")
    List<Integer> findVacinas(@Param("centro") Integer centro);

    @Query(value="SELECT a FROM Agendamento a WHERE a.centro_vacinacao.id = :centro ORDER BY a.diaVacinacao")
    List<Agendamento> findAgendamentos(@Param("centro") Integer centro);

}
