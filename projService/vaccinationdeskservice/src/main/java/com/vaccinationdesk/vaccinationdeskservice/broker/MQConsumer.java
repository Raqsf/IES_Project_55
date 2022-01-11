package com.vaccinationdesk.vaccinationdeskservice.broker;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Lote;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.LoteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class MQConsumer {
    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ListaEsperaRepository listaEsperaRepository;

    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private VacinaRepository vacinaRepository;



    /**
     * Funcao que consume os dados do broker, sendo depois os mesmos
     * separados dependendo do "type"
     * 
     * @param input - Dados provenientes do broker
     * @throws ParseException - excecao da data
     */
    @RabbitListener(queues = MQConfig.QUEUE)
    public void listen(String input) throws ParseException {
        JSONObject json = new JSONObject(input);
        String messageType = json.getString("type");
        switch (messageType) {
            case "schedule_vaccine":
                addScheduleVaccines(json);
                break;
            case "vaccines_quantity":
                addCapacityForDay(json);
                break;
            case "vaccines_per_centers":
                addVaccinesPerCenter(json);
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
    private void addScheduleVaccines(JSONObject json) throws ParseException {
        int n_utente = json.getJSONObject("utente").getInt("n_utente");
        String nome = json.getJSONObject("utente").getString("nome");
        String email = json.getJSONObject("utente").getString("email");
        String data_nasc = json.getJSONObject("utente").getString("data_nasc");
        String local = json.getJSONObject("utente").getString("local");
        //! CRIAR UMA TABELA DOENCAS? SERIA MELHOR TALVEZ... NS DEPOIS PENSAR SOBRE ISSO!!!
        //! String doencas = json.getJSONObject("utente").getString("doença");


        //! nao está a guardar na BD as horas, nem os minutos, nem os segundos
        String data_inscricao = json.getJSONObject("utente").getString("data_inscricao");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatHours = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date data_nascimento = new Date(format.parse(data_nasc).getTime());
        Timestamp data_inscricaoSQL = new Timestamp(DATE_TIME_FORMAT.parse(data_inscricao).getTime());

        Utente utente = new Utente(n_utente, nome, email, local, data_nascimento);

        utenteRepository.save(utente);
        
        ListaEspera lista_de_espera = new ListaEspera(utente, data_inscricaoSQL);
        listaEsperaRepository.save(lista_de_espera);
    }

    /**
     * Funcao que recebe a informacao da geracao de dados, sobre a capacidade
     * de todo o sistema para um dadod dia.
     * 
     * @param json - informacao que chega da geração de dados
     * @throws ParseException - excecao da data
     */
    private void addCapacityForDay(JSONObject json) throws ParseException {
        /*String dataString = json.getString("date");
        int quantidade = json.getInt("quantity");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date(format.parse(dataString).getTime());

        Capacidade capacidade = new Capacidade(data, quantidade);
        capacidadeRepository.save(capacidade);*/
    }

    /**
     * 
     * @param json
     * @throws ParseException
     */
    private void addVaccinesPerCenter(JSONObject json) throws ParseException {
        String idLote = json.getString("lote_id");
        int quantidade = json.getInt("quantity");
        String dataValidadeString = json.getString("expiration_date");
        int id_centro = json.getInt("center_id");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date dataValidade = new Date(format.parse(dataValidadeString).getTime());
        CentroVacinacao centro = new CentroVacinacao(id_centro);
        Lote lote = new Lote(idLote, quantidade, centro);
        loteRepository.save(lote);

        //atualizar capacidade atual dos centros
        List<CentroVacinacao> centrosVacinacaoList = centroVacinacaoRepository.findAll();
        for (CentroVacinacao centroVacinacao : centrosVacinacaoList) {
            centroVacinacao.incrementCapacidadeAtual(quantidade);
            centroVacinacaoRepository.save(centroVacinacao);
        }

        //Atraves do id do lote, conseguimos saber o nome da vacina
        for (int i = 0; i < quantidade; i++) {
            String nomeVacina = "";
            if (idLote.contains("PF")) {
                nomeVacina = "Pfizer";
            } else if (idLote.contains("MO")) {
                nomeVacina = "Moderna";
            } else if (idLote.contains("AZ")) {
                nomeVacina = "Astrazeneca";
            } else {
                nomeVacina = "J&J";
            }

            Vacina vacina = new Vacina(lote, nomeVacina, dataValidade);
            vacinaRepository.save(vacina);
        }
    }
}
