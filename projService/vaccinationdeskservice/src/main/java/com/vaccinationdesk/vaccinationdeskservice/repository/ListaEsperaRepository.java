package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaEsperaRepository extends JpaRepository<ListaEspera, Integer> {
    //ListaEspera findListaEsperaById(Integer id);
    //List<ListaEspera> findAll();
    List<ListaEspera> findAll();
    ListaEspera findListaEsperaByid(Integer id);
    void deleteListaEsperaByid(Integer id);
}
    
