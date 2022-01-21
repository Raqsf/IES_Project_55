package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.vaccinationdesk.vaccinationdeskservice.exception.ConflictException;
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

    @GetMapping("/pessoasVacinadasPorPeriodo/{periodo}")
    public Map<String, Integer> pessoasVacinadasSemana(@PathVariable Integer periodo/*, @RequestParam(value="cv", required = false) Integer cv*/) throws ConflictException{
        
        LinkedHashMap<String, Integer> res = new LinkedHashMap<>();

        String[] days_of_the_week = new String[7];
        days_of_the_week[0] = "Dom";
        days_of_the_week[1] = "Seg";
        days_of_the_week[2] = "Ter";
        days_of_the_week[3] = "Qua";
        days_of_the_week[4] = "Qui";
        days_of_the_week[5] = "Sex";
        days_of_the_week[6] = "Sáb";

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
        
        long millis = System.currentTimeMillis();
        Date hoje = new Date(millis);
        Calendar c = Calendar.getInstance();
        c.setTime(hoje);
        switch(periodo){
            //Hoje      (comparação entre hoje e ontem) ask them?! >.<
            case 0:
                res.put(c.get(c.DAY_OF_MONTH)+"/"+(c.get(c.MONTH)+1), pessoasVacinadas(hoje));
                c.add(Calendar.DATE, -1);
                hoje = new Date(c.getTimeInMillis());
                res.put(c.get(c.DAY_OF_MONTH)+"/"+(c.get(c.MONTH)+1), pessoasVacinadas(hoje));
                return res;
            //Última Semana
            case 1:
                for (int i=0; i<7; i++){
                    res.put(days_of_the_week[c.get(c.DAY_OF_WEEK)-1], pessoasVacinadas(hoje));
                    c.add(Calendar.DATE, -1);
                    hoje = new Date(c.getTimeInMillis());
                }
                return res;
            //Último mês
            case 2:
                for (int i=0; i<31; i++){
                    res.put(c.get(c.DAY_OF_MONTH)+"/"+(c.get(c.MONTH)+1), pessoasVacinadas(hoje));
                    c.add(Calendar.DATE, -1);
                    hoje = new Date(c.getTimeInMillis());
                }
                return res;
            //Último ano
            case 3:
                for (int i=1; i<=12; i++){
                    int month = c.get(c.MONTH);
                    YearMonth yearMonthObject = YearMonth.of(c.get(c.YEAR), month+1);
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    int totalVacs = 0;
                    
                    for (int j=0; j<daysInMonth; j++){
                        totalVacs+= pessoasVacinadas(hoje);
                        c.add(Calendar.DATE, -1);
                        hoje = new Date(c.getTimeInMillis());
                    }
                    res.put(months_of_year[month], totalVacs);
                }
                return res;                    
        }
        throw new ConflictException("Apenas pode ser apresentado períodos do dia/semana/mes/ano");
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
