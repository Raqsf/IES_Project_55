package com.vaccinationdesk.vaccinationdeskservice.repository;

// import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CentroVacinacaoRepository extends JpaRepository<CentroVacinacao, Integer>  {
    CentroVacinacao findCentroVacinacaoById(Integer id);
    // List<CentroVacinacao> findAll();
}
