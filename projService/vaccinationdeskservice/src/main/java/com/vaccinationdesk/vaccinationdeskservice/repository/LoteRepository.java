package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.sql.Date;
import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Lote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer>{
    List<Lote> findAll();
    @Query("SELECT l FROM Lote l WHERE l.data_chegada >= :date")
    List<Lote> findAllAfterDate(@Param("date") Date date);
}
