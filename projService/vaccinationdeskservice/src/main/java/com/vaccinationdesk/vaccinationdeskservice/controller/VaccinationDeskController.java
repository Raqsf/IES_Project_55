package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.persistence.Query;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Lote;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.LoteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    // private EntityManager em;
    // @GetMapping("/centrovacinacao/{centro}")
    // public List<?> centroVacinacao(@PathVariable Integer centro) {
    //     Query query = em.createNativeQuery("SELECT count(agendamento.id) FROM centro_vacinacao JOIN agendamento ON centro_vacinacao.id = n_utente WHERE centro_vacinacao = 1");
    //     return query.getResultList();
    //     // return centroVacinacaoRepository.findCentroVacinacaoById(centro);
    // }

    @GetMapping("/centrovacinacao")
    public List<CentroVacinacao> centroVacinacao() {
        return centroVacinacaoRepository.findAll();
    }

    @GetMapping("/utente")
    public Utente getUtenteByNome(@RequestParam(value="nome") String nome) {
        return utenteRepository.findUtenteByNome(nome);
    }

    @GetMapping("/utente/{id}")
    public Utente getUtenteByIDUtente(@PathVariable Integer id) {
        return utenteRepository.findUtenteById(id);
    }

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
        return loteRepository.findAll();
    }

    @PostMapping("/utente")
    public ResponseEntity<Agendamento> createAppointment(@Valid @RequestBody Utente utente) {

        if (utenteRepository.findUtenteById(utente.getID()) != null) {
            long millis = System.currentTimeMillis();
            utente = utenteRepository.findUtenteById(utente.getID());
            System.out.println(utente);
            CentroVacinacao cv = centroVacinacaoRepository.findCentroVacinacaoById(1);
            Agendamento a = new Agendamento(utente, new Date(millis), cv);
            try {
                agendamentoRepository.save(a);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(a);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/agendamento/{utente}")
    public List<Agendamento> getAgendamentoByUtente(@PathVariable Integer utente){
        return agendamentoRepository.findAllByUtente(utente);
    }

    @GetMapping("/centrovacinacao/{id}")
    public CentroVacinacao centroVacinacao(@PathVariable Integer id) {
        return centroVacinacaoRepository.findCentroVacinacaoById(id);
    }

    @GetMapping("/centrovacinacao/{id}/vacinas")
    public Integer /*List<Vacina>*/ centroVacinacaoVacinas(@PathVariable Integer id) {
        return centroVacinacaoRepository.findVacinas(centroVacinacaoRepository.findCentroVacinacaoById(id)).size();
    }

    @GetMapping("/centrovacinacao/{id}/agendamentos")
    public List<Agendamento> /*List<Vacina>*/ centroVacinacaoAgendamentos(@PathVariable Integer id) {
        return centroVacinacaoRepository.findAgendamentos(id);
    }
    

    // @GetMapping("/int")
    // public Integer getInt() {
    // return 123456;
    // }

}
