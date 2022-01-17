package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Lote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer>{
    Lote findLoteById(Integer id);
    List<Lote> findAll();
}
