package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListaEsperaRepository extends JpaRepository<ListaEspera, Integer> {
    //ListaEspera findListaEsperaById(Integer id);
    //List<ListaEspera> findAll();
    List<ListaEspera> findAll();
    ListaEspera findListaEsperaByid(Integer id);
    void deleteListaEsperaByid(Integer id);
    @Query("SELECT l.utente FROM ListaEspera as l WHERE l.utente = :utente")
    List<Utente> findUtenteInListaEspera(@Param("utente") Utente utente);
}
    
