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
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:3000" })
public class VaccinationDeskController {
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private LoteRepository loteRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private DoencaRepository doencaRepository;
    @Autowired
    private ListaEsperaRepository listaEsperaRepository;
    @Autowired
    private DoencaPorUtenteRepository dpuRepository;

    @GetMapping("/centrovacinacao")
    public List<CentroVacinacao> centroVacinacao() {
        return centroVacinacaoRepository.findAll();
    }

    /*@GetMapping("/utente")
    public Utente getUtenteByNome(@RequestParam(value="nome") String nome) {
        return utenteRepository.findUtenteByNome(nome);
    }*/

    // @GetMapping("/utente")
    // public Utente getUtenteByIDUtente(@RequestBody Utente utente) {
    //     return utenteRepository.findUtenteById(utente.getID());
    // }

    /*
     * @GetMapping("/utente/{n_utente}")
     * public Utente getUtenteByNumUtente(@PathVariable int n_utente) {
     * return utenteRepository.findUtenteByNumUtente(n_utente);
     * }
     */

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
            //System.out.println(utenteRepository.findUtenteById(utente.getID()) );
            
            Utente utenteDB = utenteRepository.findUtenteById(utente.getID());
            if (!utente.getNome().equals(utenteDB.getNome()) || 
                !utente.getDataNascimento().toString().equals(utenteDB.getDataNascimento().toString())){
                throw new ConflictException("Dados inválidos");
            }
            if (listaEsperaRepository.findUtenteInListaEspera(utente)!=null){
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

    @GetMapping("/agendamento/{id}")
    public Agendamento getAgendamentoByUtente(@PathVariable Integer id, @Valid @RequestBody(required = false) Utente utente) throws ResourceNotFoundException{
        if ( utente !=null)
            try{
                if (utenteRepository.findUtenteById(utente.getID()) != null){
                    Utente utenteDB = utenteRepository.findUtenteById(utente.getID());
                    if (!utente.getNome().equals(utenteDB.getNome())){
                        throw new ConflictException("Dados inválidos");
                    }
                    return agendamentoRepository.findAllByUtente(utente.getID());
                }
            }catch(Exception e){
                throw new ResourceNotFoundException("Utente "+utente.getID()+" não encontrado!");
            }
        return agendamentoRepository.findAllByUtente(id);
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

    @GetMapping("/centrovacinacao/{id}")
    public CentroVacinacao centroVacinacao(@PathVariable Integer id) {
        return centroVacinacaoRepository.findCentroVacinacaoById(id);
    }

    @GetMapping("/centrovacinacao/{id}/vacinas")
    public Integer /*List<Vacina>*/ centroVacinacaoVacinas(@PathVariable Integer id) {
        Integer qtd = 0;
        for (Integer i : centroVacinacaoRepository.findVacinas(id)){
            qtd+=i;
        }
        return qtd;
    }

    @GetMapping("/centrovacinacao/{id}/agendamentos")
    public List<Agendamento> /*List<Vacina>*/ centroVacinacaoAgendamentos(@PathVariable Integer id) {
        return centroVacinacaoRepository.findAgendamentos(id);
    }

    @PutMapping("/centrovacinacao/{id}/capacidade")
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

    @GetMapping("/doencas")
    public List<Doenca> doencas(){
        return doencaRepository.findAll();
    }

}
