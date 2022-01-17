package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.sql.Date;
import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Lote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoteRepository extends JpaRepository<Lote, Integer>{
    Lote findLoteById(Integer id);
    List<Lote> findAll();
    // @Query("SELECT l FROM Lote l WHERE l.data_administracao >= :date AND v.utente IS NOT NULL")
    // List<Lote> findAllLotesAfterDate(@Param("date") Date date);
}
