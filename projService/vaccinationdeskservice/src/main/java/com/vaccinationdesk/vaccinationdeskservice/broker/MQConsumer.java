package com.vaccinationdesk.vaccinationdeskservice.broker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class MQConsumer {

    private Map<Integer, Utente> scheduleVaccineMap = new HashMap<Integer, Utente>();
    private Map<Integer, Utente> gettingVaccineMap = new HashMap<Integer, Utente>();
    private List<Integer> quantityList = new ArrayList<Integer>();

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listen(String input) throws ParseException {
        JSONObject json = new JSONObject(input);
        String messageType = json.getString("type");
        switch (messageType) {
            case "people_getting_vaccinated":
                addGettingVaccine(json);
                break;
            case "schedule_vaccine":
                addScheduleVaccines(json);
                break;
            case "vaccines_quantity":
                int quantity = json.getInt("quantity");
                quantityList.clear();
                quantityList.add(quantity);
                break;
            default:
                break;                
        }
    }

    public void addScheduleVaccines(JSONObject json) throws ParseException {
        int n_utente = json.getJSONObject("utente").getInt("n_utente");
        String nome = json.getJSONObject("utente").getString("nome");
        String email = json.getJSONObject("utente").getString("email");
        System.out.println("EMAIL\t"+email);
        String data_nasc = json.getJSONObject("utente").getString("data_nasc");
        System.out.println("DATA NASCIMENTO\t"+data_nasc);
        String local = json.getJSONObject("utente").getString("local");
        System.out.println("LOCAL\t"+local);
        String doencas = json.getJSONObject("utente").getString("doença");
        System.out.println("DOECA\t"+doencas);
        String data_agendamento = json.getJSONObject("utente").getString("data_vacina");
        System.out.println("DATA AGENDAMENTO\t"+data_agendamento);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        
        Date data_nascimento = new Date(format.parse(data_nasc).getTime());
        System.out.println("DATA NASCIMENTO\t"+data_agendamento);
        
        Date data_agenda = new Date(format.parse(data_agendamento).getTime());
        System.out.println("DATA AGENDA\t"+data_agenda);
        
        Utente utente = new Utente(n_utente, nome, email, local, data_nascimento);

        //para guardar o utente na base de dados
        utenteRepository.save(utente);
        System.out.println("I JUST SAVED UTENTE");


        List<CentroVacinacao> all_cv = centroVacinacaoRepository.findAll();
        CentroVacinacao cv = all_cv.get(new Random().nextInt(all_cv.size()));
        Agendamento agendamento = new Agendamento(utente, data_agenda, cv);
        System.out.println("I JUST CREATED AGENDAMENTO\n"+agendamento);

        // guardar agendamento na bd
        agendamentoRepository.save(agendamento);
        System.out.println("I JUST SAVED AGENDAMENTO\n"+agendamento);
        
        scheduleVaccineMap.put(n_utente, utente);
    }

    public void addGettingVaccine(JSONObject json) throws ParseException {
        int n_utente = json.getJSONObject("utente").getInt("n_utente");
        String nome = json.getJSONObject("utente").getString("nome");
        String email = json.getJSONObject("utente").getString("email");
        String data_nasc = json.getJSONObject("utente").getString("data_nasc");
        String local = json.getJSONObject("utente").getString("local");
        String doencas = json.getJSONObject("utente").getString("doença");
        //String data_vacina = json.getJSONObject("utente").getString("data_vacina");
        Date data_nascimento = (Date) new SimpleDateFormat("dd/MM/yy").parse(data_nasc);  
        Utente utente = new Utente(n_utente, nome, email, local, data_nascimento);
        
        //para guardar o utente na base de dados
        utenteRepository.save(utente);
        gettingVaccineMap.put(n_utente, utente);
    }

    public Map<Integer, Utente> getScheduleVaccineMap() {
        return scheduleVaccineMap;
    }

    public Map<Integer, Utente> getGettingVaccineMap() {
        return gettingVaccineMap;
    }

    public List<Integer> getQuantityList() {
        return quantityList;
    }
}