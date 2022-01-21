package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ListaEsperaRepository extends JpaRepository<ListaEspera, Integer> {
    List<ListaEspera> findAll();
    ListaEspera findListaEsperaByid(Integer id);

    void deleteListaEsperaByid(Integer id);

    @Procedure(procedureName = "getListaEsperaByAge")
    List<ListaEspera> getListaEsperaByAge(Integer age);
    
    @Procedure(procedureName = "getListaEsperaByAgeAndDoenca")
    List<ListaEspera> getListaEsperaByAgeAndDoenca(Integer age, Integer doenca);

    @Procedure(procedureName = "getListaEsperaByDoenca")
    List<ListaEspera> getListaEsperaByDoenca(Integer doenca);

    @Procedure(procedureName = "getListaEsperaPeloDia")
    List<ListaEspera> getListaEsperaPeloDia(@Param("dia") String dia);

    @Query("SELECT l.utente FROM ListaEspera as l WHERE l.utente = :utente")
    List<Utente> findUtenteInListaEspera(@Param("utente") Utente utente);
}
    
