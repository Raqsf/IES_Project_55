package com.vaccinationdesk.vaccinationdeskservice.repository;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListaEsperaRepository extends JpaRepository<ListaEspera, Integer> {
    //ListaEspera findListaEsperaById(Integer id);
    //List<ListaEspera> findAll();
    List<ListaEspera> findAll();
    ListaEspera findListaEsperaByid(Integer id);

    void deleteListaEsperaByid(Integer id);

    //TODO: FAZER QUERY DE ACORDO COM AS DOENCAS QUE VEM DA API PARA O AGENDAMENTO
    //@Query(value="SELECT a FROM ListaEspera)
    //List<ListaEspera> findAgendamentos(@Param("doenca") String doenca);
}
    
