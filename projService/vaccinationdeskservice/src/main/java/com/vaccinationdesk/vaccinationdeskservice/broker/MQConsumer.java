package com.vaccinationdesk.vaccinationdeskservice.broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class MQConsumer {

    private Map<Integer, String> scheduleVaccineMap = new HashMap<Integer, String>();
    private Map<Integer, String> gettingVaccineMap = new HashMap<Integer, String>();
    private List<Integer> quantityList = new ArrayList<Integer>();

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listen(String input) {
        // TODO: Na parte de ter JSONObjects dentro de outros tentar fazer com um iterator (map, i guess)
        //TODO e fazer isto com as classes, já que estao criadas, pode ser um mapa de Utente for instance
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

    public void addScheduleVaccines(JSONObject json) {
        int n_utente = json.getJSONObject("utente").getInt("n_utente");
        String nome = json.getJSONObject("utente").getString("nome");
        String email = json.getJSONObject("utente").getString("email");
        String data_nasc = json.getJSONObject("utente").getString("data_nasc");
        String local = json.getJSONObject("utente").getString("local");
        String doencas = json.getJSONObject("utente").getString("doença");
        String data_vacina = json.getJSONObject("utente").getString("data_vacina");
        List<String> utente_list = new ArrayList<String>();
        utente_list.add(nome);
        utente_list.add(email);
        utente_list.add(data_nasc);
        utente_list.add(local);
        utente_list.add(doencas);
        utente_list.add(data_vacina);

        scheduleVaccineMap.put(n_utente, utente_list.toString());
    }

    public void addGettingVaccine(JSONObject json) {
        int n_utente = json.getJSONObject("utente").getInt("n_utente");
        String nome = json.getJSONObject("utente").getString("nome");
        String email = json.getJSONObject("utente").getString("email");
        String data_nasc = json.getJSONObject("utente").getString("data_nasc");
        String local = json.getJSONObject("utente").getString("local");
        String doencas = json.getJSONObject("utente").getString("doença");
        String data_vacina = json.getJSONObject("utente").getString("data_vacina");
        List<String> utente_list = new ArrayList<String>();
        utente_list.add(nome);
        utente_list.add(email);
        utente_list.add(data_nasc);
        utente_list.add(local);
        utente_list.add(doencas);
        utente_list.add(data_vacina);

        gettingVaccineMap.put(n_utente, utente_list.toString());
    }

    public Map<Integer, String> getScheduleVaccineMap() {
        return scheduleVaccineMap;
    }

    public Map<Integer, String> getGettingVaccineMap() {
        return gettingVaccineMap;
    }

    public List<Integer> getQuantityList() {
        return quantityList;
    }

}