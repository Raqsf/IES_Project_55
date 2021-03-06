package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.vaccinationdesk.vaccinationdeskservice.exception.ConflictException;
import com.vaccinationdesk.vaccinationdeskservice.exception.ResourceNotFoundException;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CapacidadeRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estatisticas")
@CrossOrigin(origins = { "http://localhost:3000", "http://192.168.160.197:3000", "http://192.168.160.197:80" })
@Transactional
public class EstatisticasController {
    @Autowired
    private VacinaRepository vacinaRepository;
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private CapacidadeRepository capacidadeRepository;

    @Async
    @GetMapping("/pessoasVacinadas")
    public Integer pessoasVacinadas(@RequestParam(value = "data", required = false) Date data) {
        if (data != null)
            return vacinaRepository.findAllVacinnatedByDate(data).size();
        return vacinaRepository.findAllVacinnated().size();
    }

    @Async
    @GetMapping("/pessoasVacinadasPorPeriodo/{periodo}")
    public Map<String, Integer> pessoasVacinadasPeriodo(@PathVariable Integer periodo/*
                                                                                      * , @RequestParam(value="cv",
                                                                                      * required = false) Integer cv
                                                                                      */) throws ConflictException {

        LinkedHashMap<String, Integer> res = new LinkedHashMap<>();

        String[] days_of_the_week = new String[7];
        days_of_the_week[0] = "Dom";
        days_of_the_week[1] = "Seg";
        days_of_the_week[2] = "Ter";
        days_of_the_week[3] = "Qua";
        days_of_the_week[4] = "Qui";
        days_of_the_week[5] = "Sex";
        days_of_the_week[6] = "S??b";

        String[] months_of_year = new String[12];
        months_of_year[0] = "Jan";
        months_of_year[1] = "Fev";
        months_of_year[2] = "Mar";
        months_of_year[3] = "Abr";
        months_of_year[4] = "Mai";
        months_of_year[5] = "Jun";
        months_of_year[6] = "Jul";
        months_of_year[7] = "Ago";
        months_of_year[8] = "Set";
        months_of_year[9] = "Out";
        months_of_year[10] = "Nov";
        months_of_year[11] = "Dez";

        Date hoje = capacidadeRepository.getDiaDB().getDia();
        Calendar c = Calendar.getInstance();
        c.setTime(hoje);
        // c.add(Calendar.DATE, 3);
        // hoje = new Date(c.getTimeInMillis());
        switch (periodo) {
            // Hoje (compara????o entre hoje e ontem) ask them?! >.<
            case 0:
                res.put(c.get(c.DAY_OF_MONTH) + "/" + (c.get(c.MONTH) + 1), pessoasVacinadas(hoje));
                c.add(Calendar.DATE, -1);
                hoje = new Date(c.getTimeInMillis());
                res.put(c.get(c.DAY_OF_MONTH) + "/" + (c.get(c.MONTH) + 1), pessoasVacinadas(hoje));
                return res;
            // ??ltima Semana
            case 1:
                for (int i = 0; i < 7; i++) {
                    res.put(days_of_the_week[c.get(c.DAY_OF_WEEK) - 1], pessoasVacinadas(hoje));
                    c.add(Calendar.DATE, -1);
                    hoje = new Date(c.getTimeInMillis());
                }
                return res;
            // ??ltimo m??s
            case 2:
                for (int i = 0; i < 31; i++) {
                    res.put(c.get(c.DAY_OF_MONTH) + "/" + (c.get(c.MONTH) + 1), pessoasVacinadas(hoje));
                    c.add(Calendar.DATE, -1);
                    hoje = new Date(c.getTimeInMillis());
                }
                return res;
            // ??ltimo ano
            case 3:
                for (int i = 1; i <= 12; i++) {
                    int month = c.get(c.MONTH);
                    YearMonth yearMonthObject = YearMonth.of(c.get(c.YEAR), month + 1);
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    int totalVacs = 0;

                    for (int j = 0; j < daysInMonth; j++) {
                        totalVacs += pessoasVacinadas(hoje);
                        c.add(Calendar.DATE, -1);
                        hoje = new Date(c.getTimeInMillis());
                    }
                    res.put(months_of_year[month], totalVacs);
                }
                return res;

        }
        throw new ConflictException("Apenas pode ser apresentado per??odos do dia/semana/mes/ano");
    }

    @Async
    @GetMapping("/taxaVacinacaoPorPeriodo/{periodo}")
    public Integer taxa2semanas(@PathVariable Integer periodo) throws ConflictException {
        Date hoje = capacidadeRepository.getDiaDB().getDia();
        Calendar c = Calendar.getInstance();
        c.setTime(hoje);
        // c.add(Calendar.DATE, 3);
        // hoje = new Date(c.getTimeInMillis());
        switch (periodo) {
            case 0:
                Integer ultima = pessoasVacinadasPeriodo(0).get(c.get(c.DAY_OF_MONTH) + "/" + (c.get(c.MONTH) + 1));
                c.add(Calendar.DATE, -1);
                Integer penultima = pessoasVacinadasPeriodo(0).get(c.get(c.DAY_OF_MONTH) + "/" + (c.get(c.MONTH) + 1));

                if (penultima == 0 && ultima == 0)
                    return 0;
                else if (penultima == 0)
                    return 100;
                return (int) Math.round((((double) ultima / (double) penultima) - 1) * 100);
            case 1:
                ultima = 0;
                for (int i = 0; i < 7; i++) {
                    ultima += pessoasVacinadas(hoje);
                    c.add(Calendar.DATE, -1);
                    hoje = new Date(c.getTimeInMillis());
                }
                penultima = 0;
                for (int i = 0; i < 7; i++) {
                    penultima += pessoasVacinadas(hoje);
                    c.add(Calendar.DATE, -1);
                    hoje = new Date(c.getTimeInMillis());
                }
                if (penultima == 0 && ultima == 0)
                    return 0;
                else if (penultima == 0)
                    return 100;
                return (int) Math.round((((double) ultima / (double) penultima) - 1) * 100);
        }
        throw new ConflictException("Apenas apresentamos taxas de vacina????o por hoje e ultima semana.");
    }

    @Async
    @GetMapping("/pessoasVacinadas/{id}")
    public Integer pessoasVacinadasPorCV(@PathVariable Integer id,
            @RequestParam(value = "hoje", required = false) Boolean hoje) throws ResourceNotFoundException {
        try {
            CentroVacinacao cv = centroVacinacaoRepository.findCentroVacinacaoById(id);
            if (cv == null)
                throw new ResourceNotFoundException("Centro Vacinacao " + id + " n??o encontrado!");
            if (hoje != null) {
                Date dia = capacidadeRepository.getDiaDB().getDia();
                return vacinaRepository.findAllVacinnatedByCentroVacinacaoByDate(cv, dia).size();
            }
            return vacinaRepository.findAllVacinnatedByCentroVacinacao(cv).size();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Centro Vacinacao " + id + " n??o encontrado!");
        }
    }

    @Async
    @GetMapping("/vacinasDisponiveis/{id}")
    public Integer vacinasDisponiveisPorCV(@PathVariable Integer id) throws ResourceNotFoundException {
        try {
            if (centroVacinacaoRepository.findCentroVacinacaoById(id) == null)
                throw new ResourceNotFoundException("Centro Vacinacao " + id + " n??o encontrado!");
            return centroVacinacaoRepository.getVacinasDisponiveis(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Centro Vacinacao " + id + " n??o encontrado!");
        }
    }

    @Async
    @GetMapping("/vacinasPrevistas")
    public Integer agendamentosHoje(@RequestParam(value = "cv", required = false) Integer cv) throws Exception {
        Date hoje = capacidadeRepository.getDiaDB().getDia();
        Calendar c = Calendar.getInstance();
        c.setTime(hoje);
        // Calendar c = Calendar.getInstance();
        // c.setTime(d);
        // c.add(Calendar.DATE, 3);
        // d = new Date(c.getTimeInMillis());
        String date1 = hoje + " 00:00:00";
        String date2 = hoje + " 23:59:59";
        SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Timestamp ts1 = new Timestamp(DATE_TIME_FORMAT.parse(date1).getTime());
            Timestamp ts2 = new Timestamp(DATE_TIME_FORMAT.parse(date2).getTime());
            if (cv != null) {
                CentroVacinacao centro = centroVacinacaoRepository.findCentroVacinacaoById(cv);
                if (centro == null)
                    throw new ResourceNotFoundException("Centro Vacinacao " + cv + " n??o encontrado");
                return agendamentoRepository.findAllTotalVaccinesByDate(ts1, ts2, centro).size();
            }
            return agendamentoRepository.findAllTotalVaccinesByDate(ts1, ts2).size();
        } catch (Exception e) {
            throw e;
        }
    }

    @Async
    @GetMapping("/pessoasVacinadasPorTodosCentros")
    public Map<String, Integer> pessoasVacinadasPorTodosCentros() throws ResourceNotFoundException, ConflictException {
        Integer total = pessoasVacinadas(null);
        if (total == 0) {
            throw new ConflictException("No data to show!");
        }
        List<CentroVacinacao> centros = centroVacinacaoRepository.findAll();
        Map<String, Integer> res = new HashMap<>();
        for (CentroVacinacao c : centros) {
            double taxaVacinacao = Math.round(((double) pessoasVacinadasPorCV(c.getID(), null) / (double) total) * 100);
            res.put(c.getNome(), (int) taxaVacinacao);
        }
        return res;
    }
}
