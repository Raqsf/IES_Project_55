package com.vaccinationdesk.vaccinationdeskservice.broker;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class MQConsumer {

    private int quantityForDay;
    
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ListaEsperaRepository listaEsperaRepository;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listen(String input) throws ParseException {
        JSONObject json = new JSONObject(input);
        String messageType = json.getString("type");
        switch (messageType) {
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

    /**
     * Funcao para tratar da informacao que chega da geração de dados.
     * A informcao chega num formato JSON, e é guardada na base de dados
     * nomeadamente nas tabelas, pessoa, e lista_de_espera
     * 
     * @param json - informacao que chega da geração de dados
     * @throws ParseException - excecao da data
     */
    public void addScheduleVaccines(JSONObject json) throws ParseException {
        int n_utente = json.getJSONObject("utente").getInt("n_utente");
        String nome = json.getJSONObject("utente").getString("nome");
        String email = json.getJSONObject("utente").getString("email");
        String data_nasc = json.getJSONObject("utente").getString("data_nasc");
        String local = json.getJSONObject("utente").getString("local");
        //! CRIAR UMA TABELA DOENCAS? SERIA MELHOR TALVEZ... NS DEPOIS PENSAR SOBRE ISSO!!!
        //! String doencas = json.getJSONObject("utente").getString("doença");
        String data_agendamento = json.getJSONObject("utente").getString("data_vacina");

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date data_nascimento = new Date(format.parse(data_nasc).getTime());
        Date data_agenda = new Date(format.parse(data_agendamento).getTime());
        Utente utente = new Utente(n_utente, nome, email, local, data_nascimento);

        utenteRepository.save(utente);
        
        ListaEspera lista_de_espera = new ListaEspera(utente, data_agenda);
        listaEsperaRepository.save(lista_de_espera);
    }

    public int getQuantityForDay() {
        return quantityForDay;
    }
}