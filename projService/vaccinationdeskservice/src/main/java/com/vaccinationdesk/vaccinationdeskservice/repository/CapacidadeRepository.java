package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaccinationdesk.vaccinationdeskservice.model.Capacidade;

public interface CapacidadeRepository extends JpaRepository<Capacidade, Integer> {
    List<Capacidade> findAll();
    
}
