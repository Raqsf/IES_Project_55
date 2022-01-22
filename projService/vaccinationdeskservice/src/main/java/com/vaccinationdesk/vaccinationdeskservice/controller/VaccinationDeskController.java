package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import com.vaccinationdesk.vaccinationdeskservice.exception.ConflictException;
import com.vaccinationdesk.vaccinationdeskservice.exception.ResourceNotFoundException;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Doenca;
import com.vaccinationdesk.vaccinationdeskservice.model.DoencaPorUtente;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Lote;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.DoencaPorUtenteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.DoencaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.LoteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = { "http://localhost:3001", "http://localhost:3001" })
public class VaccinationDeskController {
    
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private LoteRepository loteRepository;
    @Autowired
    private DoencaRepository doencaRepository;
    @Autowired
    private ListaEsperaRepository listaEsperaRepository;
    @Autowired
    private DoencaPorUtenteRepository dpuRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    /*@GetMapping("/utente")
    public Utente getUtenteByNome(@RequestParam(value="nome") String nome) {
        return utenteRepository.findUtenteByNome(nome);
    }*/

    @GetMapping("/utente/{id}")
    public Utente getUtenteByIDUtente(@RequestBody Integer id) {
        return utenteRepository.findUtenteById(id);
    }

    @GetMapping("/lote/{id}")
    public Lote getUtenteByNome(@PathVariable Integer lote) {
        return loteRepository.findLoteById(lote);
    }

    @GetMapping("/lote")
    public List<Lote> getUtenteByNome() {
        Date d = new Date(System.currentTimeMillis());
        return loteRepository.findAllAfterDate(d);
    }

    @PostMapping("/utente")
    public ResponseEntity<ListaEspera> createAppointment(@Valid @RequestBody Utente utente) throws ConflictException {

        if (utenteRepository.findUtenteById(utente.getID()) != null) {
            
            Utente utenteDB = utenteRepository.findUtenteById(utente.getID());
            if (!utente.getNome().equals(utenteDB.getNome()) || 
                !utente.getDataNascimento().toString().equals(utenteDB.getDataNascimento().toString())){
                throw new ConflictException("Dados inválidos");
            }
            List<Utente> findUtenteEmLE = listaEsperaRepository.findUtenteInListaEspera(utente);
            if (findUtenteEmLE!=null && findUtenteEmLE.size()!=0){
                throw new ConflictException("Utente com id "+utente.getID()+" já fez o pedido de agendamento");
            }
            long millis = System.currentTimeMillis();
            ListaEspera le = new ListaEspera(utenteDB, new Timestamp(millis));
            try {
                listaEsperaRepository.save(le);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(le);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/agendamento")
    public Agendamento getAgendamentoByUtente(@Valid @RequestBody(required = false) Utente utente) throws Exception{
        if ( utente !=null)
            try{
                if (utenteRepository.findUtenteById(utente.getID()) != null){
                    Utente utenteDB = utenteRepository.findUtenteById(utente.getID());
                    if (!utente.getNome().equals(utenteDB.getNome())){
                        throw new ConflictException("Dados inválidos");
                    }
                    if (!listaEsperaRepository.findUtenteInListaEspera(utente).isEmpty() && agendamentoRepository.findAllByUtente(utente.getID()) == null){
                        throw new ConflictException("Utente encontra-se em lista de espera. Aguarde pelo agendamento");
                    }
                }else{
                    throw new ResourceNotFoundException("Utente "+utente.getID()+" não encontrado!");
                }
            }catch(Exception e){
                throw e;
            }
        return agendamentoRepository.findAllByUtente(utente.getID());
    }

    @GetMapping("/doencaPorUtente/{id}")
    public List<DoencaPorUtente> getDoencasPorUtente(@PathVariable Integer id) throws ResourceNotFoundException{
        try{
            Utente u = utenteRepository.findUtenteById(id);
            return dpuRepository.findByIdUtente(u);
        }catch(Exception e){
            throw new ResourceNotFoundException("Utente "+id+" não encontrado!");
        }
        
    }

    @GetMapping("/doencas")
    public List<Doenca> doencas(){
        return doencaRepository.findAll();
    }

}
