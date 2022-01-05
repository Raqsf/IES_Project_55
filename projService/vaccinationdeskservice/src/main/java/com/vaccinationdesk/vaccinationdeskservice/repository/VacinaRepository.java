package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.sql.Date;
import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VacinaRepository extends JpaRepository<Vacina, Integer> {
    List<Vacina> findAll();
    
    //@Modifying
    //@Query("UPDATE vacina SET administrada_a = :n_utente, data_administracao = :data WHERE n_vacina = :id")
    //void updateVacina(@Param("nome") Integer n_utente, @Param("data") Date data, @Param("id") Integer id);
}
