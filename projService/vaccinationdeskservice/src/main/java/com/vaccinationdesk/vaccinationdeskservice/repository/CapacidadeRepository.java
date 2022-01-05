package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Capacidade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CapacidadeRepository extends JpaRepository<Capacidade, Integer> {
    List<Capacidade> findAll();
    
}
