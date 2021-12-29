package com.vaccinationdesk.vaccinationdeskservice.repository;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{
}
