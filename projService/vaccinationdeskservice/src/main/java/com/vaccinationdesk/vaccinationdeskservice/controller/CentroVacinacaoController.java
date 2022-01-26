package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.util.List;

import javax.validation.Valid;

import com.vaccinationdesk.vaccinationdeskservice.exception.ResourceNotFoundException;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/centrovacinacao")
@CrossOrigin(origins = { "http://localhost:3000", "http://192.168.160.197:3000", "http://192.168.160.197:80" })
public class CentroVacinacaoController {
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;

    @Async
    @GetMapping("")
    public List<CentroVacinacao> centroVacinacao() {
        return centroVacinacaoRepository.findAll();
    }

    @Async
    @GetMapping("/pornome/{nome}")
    public CentroVacinacao findCentroVacinacaoByName(@PathVariable String nome) {
        return centroVacinacaoRepository.findByNome(nome);
    }

    @Async
    @GetMapping("/{id}")
    public CentroVacinacao centroVacinacao(@PathVariable Integer id) {
        return centroVacinacaoRepository.findCentroVacinacaoById(id);
    }

    @Async
    @GetMapping("/{id}/agendamentos")
    public List<Agendamento> /* List<Vacina> */ centroVacinacaoAgendamentos(@PathVariable Integer id) {
        return centroVacinacaoRepository.findAgendamentos(id);
    }

    @Async
    @PutMapping("/{id}/capacidade")
    public ResponseEntity<CentroVacinacao> updateCapacidade(@PathVariable(value = "id") Integer id,
            @Valid @RequestBody Integer capacidade) throws ResourceNotFoundException {
        try {
            CentroVacinacao centro = centroVacinacaoRepository.findCentroVacinacaoById(id);
            if (centro == null)
                throw new ResourceNotFoundException("Centro Vacinacao " + id + " not found");
            centro.setCapacidadeMax(capacidade);
            CentroVacinacao updatedCentro = centroVacinacaoRepository.save(centro);
            return ResponseEntity.ok(updatedCentro);
        } catch (Exception e) {
            System.err.print("Centro Vacinacao " + id + " not found");
            throw new ResourceNotFoundException("Centro Vacinacao " + id + " not found");
        }

    }
}
