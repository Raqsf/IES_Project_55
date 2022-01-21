package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.google.zxing.WriterException;
import com.vaccinationdesk.vaccinationdeskservice.Service.Distribuicao;
import com.vaccinationdesk.vaccinationdeskservice.exception.ConflictException;
import com.vaccinationdesk.vaccinationdeskservice.exception.ResourceNotFoundException;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/api/v1/agendamento")
@CrossOrigin(origins = { "/http://localhost:3000", "http://localhost:3000" })
public class AgendamentoController {

    @Autowired
    private ListaEsperaRepository listaesperaRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private Distribuicao distribuicao;

    @GetMapping("/agendar")
    public List<Agendamento> agendar() throws Exception {
        try {
            return distribuicao.distribuirVacinasPorOrdemMarcacao();
        } catch (Exception e) {
            throw e;
        }
        
    }

    @GetMapping("/listaespera")
    public List<ListaEspera> getAllListaEspera() {
        return listaesperaRepository.findAll();
    }

    @GetMapping("/listaespera/{id}")
    public ListaEspera getListaEsperaByid(@PathVariable Integer id) {
        return listaesperaRepository.findListaEsperaByid(id);
    }

    @GetMapping("/get_por_dia")
    public List<Agendamento> getAgendarDia() {
        return agendamentoRepository.getAgendamentosPorDia("2022-01-20");
    }

    @PostMapping("/agendar_com_filtros")
    public ResponseEntity<List<Agendamento>> agendarComFiltros(@Valid @RequestBody String filtros) throws Exception {
        // {idade: int, doenca: int}
        // {doenca: int}
        // {idade: int}
        try {
            List<Agendamento> le = distribuicao.distribuirVacinasPorFiltros(filtros);
            return ResponseEntity.ok(le);
        } catch (Exception e) {
            throw e;
        }
        
    }

    @GetMapping("/{id}")
    public Agendamento getAgendamentoByUtente(@PathVariable Integer id, @Valid @RequestBody(required = false) Utente utente) throws Exception{
        if ( utente !=null)
            try{
                if (utenteRepository.findUtenteById(utente.getID()) != null){
                    Utente utenteDB = utenteRepository.findUtenteById(utente.getID());
                    if (!utente.getNome().equals(utenteDB.getNome())){
                        throw new ConflictException("Dados inválidos");
                    }
                    List<Utente> findUtenteEmLE = listaesperaRepository.findUtenteInListaEspera(utente);
                    if (findUtenteEmLE!=null && findUtenteEmLE.size()!=0){
                        throw new ConflictException("Utente encontra-se em lista de espera. Aguarde pelo agendamento");
                    }
                    return agendamentoRepository.findAllByUtente(utente.getID());
                }else{
                    throw new ResourceNotFoundException("Utente "+utente.getID()+" não encontrado!");
                }
            }catch(Exception e){
                throw e;
            }
        return agendamentoRepository.findAllByUtente(id);
    }


}
