package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Capacidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacidadeRepository extends JpaRepository<Capacidade, Integer> {
    List<Capacidade> findAll();
    
}
