package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.sql.Date;
import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Integer> {
    List<Vacina> findAll();

    @Query("SELECT v FROM Vacina v WHERE v.utente IS NOT NULL AND v.data_administracao IS NOT NULL")
    List<Vacina> findAllVacinnated();

    @Query("SELECT v FROM Vacina v WHERE v.data_administracao = :date AND v.utente IS NOT NULL")
    List<Vacina> findAllVacinnatedByDate(@Param("date") Date date);

    @Query("SELECT v FROM Vacina v JOIN Lote l ON v.lote=l.id WHERE l.centroVacinacao=:id AND v.utente IS NOT NULL AND v.data_administracao IS NOT NULL")
    List<Vacina> findAllVacinnatedByCentroVacinacao(@Param("id") CentroVacinacao cv);

    @Query("SELECT v FROM Vacina v JOIN Lote l ON v.lote=l.id WHERE l.centroVacinacao=:id AND v.data_administracao=:date AND v.utente IS NOT NULL")
    List<Vacina> findAllVacinnatedByCentroVacinacaoByDate(@Param("id") CentroVacinacao cv, @Param("date") Date date);
    
    //@Modifying
    //@Query("UPDATE vacina SET administrada_a = :n_utente, data_administracao = :data WHERE n_vacina = :id")
    //void updateVacina(@Param("nome") Integer n_utente, @Param("data") Date data, @Param("id") Integer id);
}
