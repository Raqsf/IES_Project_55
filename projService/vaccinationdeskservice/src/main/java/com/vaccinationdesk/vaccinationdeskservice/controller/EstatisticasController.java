package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.vaccinationdesk.vaccinationdeskservice.exception.ResourceNotFoundException;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estatisticas")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:3000" })
public class EstatisticasController {
    @Autowired
    private VacinaRepository vacinaRepository;
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/pessoasVacinadas")
    public Integer pessoasVacinadas(@RequestParam(value="data", required = false) Date data) {
        if (data != null){
            return vacinaRepository.findAllVacinnatedByDate(data).size();
        }
        return vacinaRepository.findAllVacinnated().size();
    }

    @GetMapping("/pessoasVacinadas/{id}")
    public Integer pessoasVacinadasPorCV(@PathVariable Integer id, @RequestParam(value="data", required = false) Date data) throws ResourceNotFoundException{
        try{
            CentroVacinacao cv = centroVacinacaoRepository.findCentroVacinacaoById(id);
            if ( data !=null)
                return vacinaRepository.findAllVacinnatedByCentroVacinacaoByDate(cv, data).size();
            return vacinaRepository.findAllVacinnatedByCentroVacinacao(cv).size();
        }
        catch(Exception e){
            throw new ResourceNotFoundException("Centro Vacinacao "+id+" não encontrado!");
        }
    }

    @GetMapping("/vacinasDisponiveis/{id}")
    public Integer vacinasDisponiveisPorCV(@PathVariable Integer id) throws ResourceNotFoundException{
        try{
            return centroVacinacaoRepository.getVacinasDisponiveis(id);
        }
        catch(Exception e){
            throw new ResourceNotFoundException("Centro Vacinacao "+id+" não encontrado!");
        }
    }

    @GetMapping("/vacinasPrevistas")
    public Integer agendamentosHoje(@RequestParam(value="cv", required = false) Integer cv) throws Exception{
        long millis = System.currentTimeMillis();
        Date d = new Date(millis);
        String date1 = d + " 00:00:00";
        String date2 = d +" 23:59:59";
        SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Timestamp ts1 = new Timestamp(DATE_TIME_FORMAT.parse(date1).getTime());
            Timestamp ts2 = new Timestamp(DATE_TIME_FORMAT.parse(date2).getTime());
            if (cv !=null){
                CentroVacinacao centro = centroVacinacaoRepository.findCentroVacinacaoById(cv);
                if (centro == null)
                    throw new ResourceNotFoundException("Centro Vacinacao "+cv+" não encontrado");
                return agendamentoRepository.findAllTotalVaccinesByDate(ts1,ts2, centro).size();
            }
            return agendamentoRepository.findAllTotalVaccinesByDate(ts1,ts2).size();
        } catch (Exception e) {
            throw e;
        }
    }
}
