package com.vaccinationdesk.vaccinationdeskservice.broker;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.transaction.Transactional;

import com.vaccinationdesk.vaccinationdeskservice.model.Capacidade;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Doenca;
import com.vaccinationdesk.vaccinationdeskservice.model.DoencaPorUtente;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Lote;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CapacidadeRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.DoencaPorUtenteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.DoencaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.LoteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class MQConsumer {
    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private MQProducer producer = new MQProducer();

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

    @Autowired
    private DoencaPorUtenteRepository doencaPorUtenteRepository;
    
    @Autowired
    private DoencaRepository doencaRepository;
    
    @Autowired
    private CapacidadeRepository capacidadeRepository;
    
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    /**
     * Funcao que consume os dados do broker, sendo depois os mesmos
     * separados dependendo do "type"
     * 
     * @param input - Dados provenientes do broker
     * @throws ParseException - excecao da data
     * @throws TimeoutException
     * @throws IOException
     */
    @RabbitListener(queues = MQConfig.QUEUE)
    public void listen(String input) throws ParseException, IOException, TimeoutException {
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
            case "raspberry_reader":
                verifyQRCodes(json);
                break;
            default:
                break;
        }
    }

    private void verifyQRCodes(JSONObject json) throws IOException, TimeoutException {
        String info = json.getString("data");
        String[] dataSplit = info.split("\n");
        int  n_utente = 0;
        int centro = 0;
        String dia = "";
        for (int i = 1; i < dataSplit.length; i++) {
            String[] split = dataSplit[i].split(" - ");
            if (i == 1) {
                n_utente = Integer.parseInt(split[1]);
            } else if (i == 2) {
                centro = Integer.parseInt(split[1]);
            } else if (i == 3) {
                String[] diaSemHoras = split[1].split(" ");
                dia = diaSemHoras[0];
            }
        }

        if (agendamentoRepository.checkQRcode(n_utente, centro, dia) != null) {
            String msg = "QR Code Valido para o Utente com o numero " + n_utente;
            producer.createMessage(msg);
        } else {
            String msg = "QR Code Invalido para o Utente com o numero " + n_utente;
            producer.createMessage(msg);
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
        String doencaGeracaoDados = json.getJSONObject("utente").getString("doença");

        String data_inscricao = json.getJSONObject("utente").getString("data_inscricao");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date data_nascimento = new Date(format.parse(data_nasc).getTime());
        Timestamp data_inscricaoSQL = new Timestamp(DATE_TIME_FORMAT.parse(data_inscricao).getTime());

        Utente utente = new Utente(n_utente, nome, email, local, data_nascimento);
        utenteRepository.save(utente);

        int idDoenca = getIdFromDoenca(doencaGeracaoDados);
        if (idDoenca != 0) { // so adiciona na tabela, caso a pessoa tenha alguma doenca
            Doenca doenca = doencaRepository.findDoencaById(idDoenca);
            doencaPorUtenteRepository.save(new DoencaPorUtente(utente, doenca));
        }
        
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
        //{'type': 'vaccines_quantity', 'date': '20/01/2022', 'quantity': 40}
        String dataString = json.getString("date");
        int quantidade = json.getInt("quantity");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date(format.parse(dataString).getTime());
        
        Capacidade capacidade = new Capacidade(data, quantidade);
        capacidadeRepository.save(capacidade);   
    }

    /**
     * 
     * @param json
     * @throws ParseException
     */
    private void addVaccinesPerCenter(JSONObject json) throws ParseException {
        String idLote = json.getString("lote_id");
        int quantidade = json.getInt("quantity");
        String dataChegadaString = json.getString("arriving_date");
        String dataValidadeString = json.getString("expiration_date");
        int id_centro = json.getInt("center_id");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date dataValidade = new Date(format.parse(dataValidadeString).getTime());
        Date dataChegada = new Date(format.parse(dataChegadaString).getTime());
        CentroVacinacao centro = new CentroVacinacao(id_centro);
        Lote lote = new Lote(idLote, quantidade, centro, dataChegada);
        loteRepository.save(lote);

        // atualizar capacidade atual dos centros
        List<CentroVacinacao> centrosVacinacaoList = centroVacinacaoRepository.findAll();
        for (CentroVacinacao centroVacinacao : centrosVacinacaoList) {
            centroVacinacao.incrementCapacidadeAtual(quantidade);
            centroVacinacaoRepository.save(centroVacinacao);
        }

        // Atraves do id do lote, conseguimos saber o nome da vacina
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

    private int getIdFromDoenca(String doenca) {
        int id = 0;
        switch (doenca) {
            case "Doença Cardíaca":
                id = 1;
                break;
            case "Doença Pulmonar":
                id = 2;
                break;
            case "Diabetes":
                id = 3;
                break;
            case "Cancro":
                id = 4;
                break;
            case "Obesidade":
                id = 5;
                break;
            case "Doenca AutoImune":
                id = 6;
                break;
            default:
                id = 0;
                break;
        }
        return id;
    }
}
