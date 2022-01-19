package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.util.List;

import javax.validation.Valid;

import com.vaccinationdesk.vaccinationdeskservice.exception.ResourceNotFoundException;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/centrovacinacao")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:3000" })
public class CentroVacinacaoController {
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;

    @GetMapping("")
    public List<CentroVacinacao> centroVacinacao() {
        return centroVacinacaoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public CentroVacinacao centroVacinacao(@PathVariable Integer id) {
        return centroVacinacaoRepository.findCentroVacinacaoById(id);
    }

    // @GetMapping("/{id}/vacinas")
    // public Integer /*List<Vacina>*/ centroVacinacaoVacinas(@PathVariable Integer id) {
    //     Integer qtd = 0;
    //     for (Integer i : centroVacinacaoRepository.findVacinas(id)){
    //         System.out.println(i);
    //         qtd+=i;
    //     }
    //     return qtd;
    // }

    @GetMapping("/{id}/agendamentos")
    public List<Agendamento> /*List<Vacina>*/ centroVacinacaoAgendamentos(@PathVariable Integer id) {
        return centroVacinacaoRepository.findAgendamentos(id);
    }

    @PutMapping("/{id}/capacidade")
    public ResponseEntity<CentroVacinacao> updateCapacidade(@PathVariable(value = "id") Integer id, 
        @Valid @RequestBody Integer capacidade) throws ResourceNotFoundException {
        try {
            CentroVacinacao centro = centroVacinacaoRepository.findCentroVacinacaoById(id);
            centro.setCapacidadeMax(capacidade);
            CentroVacinacao updatedCentro = centroVacinacaoRepository.save(centro);
            return ResponseEntity.ok(updatedCentro);
        } catch (Exception e) {
            System.err.print("Centro Vacinacao "+id+" not found");
            throw new ResourceNotFoundException("Centro Vacinacao "+id+" not found");
        }
        
    }
}
