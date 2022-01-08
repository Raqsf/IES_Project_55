package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinaRepository extends JpaRepository<Vacina, Integer> {
    List<Vacina> findAll();
    
    //@Modifying
    //@Query("UPDATE vacina SET administrada_a = :n_utente, data_administracao = :data WHERE n_vacina = :id")
    //void updateVacina(@Param("nome") Integer n_utente, @Param("data") Date data, @Param("id") Integer id);
}
