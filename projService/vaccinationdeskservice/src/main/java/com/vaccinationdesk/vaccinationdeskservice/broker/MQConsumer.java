package com.vaccinationdesk.vaccinationdeskservice.broker;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class MQConsumer {

    private Map<Integer, Utente> scheduleVaccineMap = new HashMap<Integer, Utente>();
    private Map<Integer, Utente> gettingVaccineMap = new HashMap<Integer, Utente>();
    private int quantityForDay;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    /*@Autowired
    private AgendamentoRepository agendamentoRepository;
    
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;*/
    @Autowired
    private ListaEsperaRepository listaEsperaRepository;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listen(String input) throws ParseException {
        JSONObject json = new JSONObject(input);
        String messageType = json.getString("type");
        switch (messageType) {
            case "people_getting_vaccinated":
                //addGettingVaccine(json);
                break;
            case "schedule_vaccine":
                addScheduleVaccines(json);
                break;
            case "vaccines_quantity":
                int quantity = json.getInt("quantity");
                quantityForDay = quantity;
                break;
            default:
                break;                
        }
    }

    public void addScheduleVaccines(JSONObject json) throws ParseException {
        int n_utente = json.getJSONObject("utente").getInt("n_utente");
        String nome = json.getJSONObject("utente").getString("nome");
        String email = json.getJSONObject("utente").getString("email");
        String data_nasc = json.getJSONObject("utente").getString("data_nasc");
        String local = json.getJSONObject("utente").getString("local");
        String doencas = json.getJSONObject("utente").getString("doença");
        //!adicionar campo na tabela lista_de_espera sobre a data de agendamento
        String data_agendamento = json.getJSONObject("utente").getString("data_vacina");

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date data_nascimento = new Date(format.parse(data_nasc).getTime());
        Date data_agenda = new Date(format.parse(data_agendamento).getTime());
        Utente utente = new Utente(n_utente, nome, email, local, data_nascimento);
        //para guardar o utente na base de dados
        utenteRepository.save(utente);

        //List<CentroVacinacao> all_cv = centroVacinacaoRepository.findAll();
        //CentroVacinacao cv = all_cv.get(1 + (int) (Math.random() * ((all_cv.size() - 1))));
        //Agendamento agendamento = new Agendamento(utente);
        //agendamentoRepository.save(agendamento);
        
        ListaEspera lista_de_espera = new ListaEspera(utente, data_agenda);
        listaEsperaRepository.save(lista_de_espera);
        

        //scheduleVaccineMap.put(n_utente, utente);
    }
    

    public void addGettingVaccine(JSONObject json) throws ParseException {
        /*int n_utente = json.getJSONObject("utente").getInt("n_utente");
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
        gettingVaccineMap.put(n_utente, utente);*/
    }

    public Map<Integer, Utente> getScheduleVaccineMap() {
        return scheduleVaccineMap;
    }

    public Map<Integer, Utente> getGettingVaccineMap() {
        return gettingVaccineMap;
    }

    public int getQuantityForDay() {
        return quantityForDay;
    }
}