package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaccinationdesk.vaccinationdeskservice.exception.ConflictException;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.Capacidade;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CapacidadeRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
public class Vacinacao {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;

    @Autowired
    private CapacidadeRepository capacidadeRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    Map<Integer, String> dentroDoCentroMap = new HashMap<>();

    /**
     * Funcao que faz a vacinacao para um determinado dia.
     * É feito um pedido à Base de Dados de todos os agendamentos para o dia em questão
     * e por cada agendamento é feita a vacinacao.
     * 
     * @return 
     * @throws JsonProcessingException
     * @throws ConflictException
     */
    public ResponseEntity<Object> vacinacao() throws JsonProcessingException, ConflictException {
        // ! ir buscar a string para o dia em questao (como esta escrito em cima, talvez
        // a uma tabela que faça so guardar os dias e passa-los)
        ObjectMapper mapper = new ObjectMapper();

        if (capacidadeRepository.getDiaDB() == null) {
            throw new ConflictException("Não existe capacidade para o dia em questão");
        }
        //Capacidade dia = capacidadeRepository.getDiaDB();
        //Date date = dia.getDia();

        List<Agendamento> agendamentoParaODiaList = agendamentoRepository.getAgendamentosPorDia("2022-01-23");
        //capacidadeRepository.delete(dia);

        //List<Agendamento> agendamentoParaODiaList = agendamentoRepository.findAll();
        List<Vacina> vacinaList = vacinaRepository.findAll();
        int i = 0;
        int n_vacinas = agendamentoParaODiaList.size();
        for (Vacina vacina : vacinaList) {
            if (i < n_vacinas) {

                Agendamento agendamento = agendamentoParaODiaList.get(i);
                Timestamp data_toma_vacina = agendamento.getDiaVacinacao();
                Utente utente_vacina_administrada = agendamento.getUtente();
                CentroVacinacao centro = agendamento.getCentro();
                if (centro.getCapacidadeAtual() <= 0) {
                    throw new ConflictException(centro.getNome() + " encontra-se sem vacinas!");
                }
                vacina.setUtente(utente_vacina_administrada);
                vacina.setDataAdministracao(data_toma_vacina);
                centro.decreaseCapacidadeAtual();

                centroVacinacaoRepository.save(centro);
                vacinaRepository.save(vacina);
                List<String> infoList = new ArrayList<String>();
                infoList.add(utente_vacina_administrada.getNome() + ", " + utente_vacina_administrada.getID() + ", "
                        + agendamento.getCentro() + ", " + vacina.getNome() + ", " + vacina.getDataAdministracao());
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nome", utente_vacina_administrada.getNome());
                map.put("n_utente", utente_vacina_administrada.getID());
                map.put("centro", agendamento.getCentro().getID());
                map.put("vacina", vacina.getNome());
                map.put("data_administracao", vacina.getDataAdministracao().toString());
                
                String infoJSON = mapper.writeValueAsString(map);
                
                if (dentroDoCentroMap.keySet().size() < 5) {
                    dentroDoCentroMap.put(i % 5, infoJSON);
                } else {
                    dentroDoCentroMap.remove(i % 5);
                    if (i == n_vacinas) {
                        for (Integer key : dentroDoCentroMap.keySet()) {
                            dentroDoCentroMap.remove(key);
                            wait(4000);
                        }
                    }
                    System.out.println(dentroDoCentroMap);
                }

                i++;
                wait(4000);
            } else {
                break;
            }
        }
        return ResponseEntity.ok(null);
    }
    
    /**
     * Funcao que retona quais os utentes que estão dentro dos centros
     * sendo a mesma, chamada em intervalos de 1 segundo pelo front-end
     * @return
     */
    @Async
    public List<String> getVacinacaoEmTempoReal(Integer id_centro) {
        List<String> vacinacaoTempoReal = new ArrayList<>();
        for (Integer key : dentroDoCentroMap.keySet()) {
            JSONObject json = new JSONObject(dentroDoCentroMap.get(key));
            int centro = json.getInt("centro");
            if (centro == id_centro) {
                vacinacaoTempoReal.add(dentroDoCentroMap.get(key));
            }
        }
        return vacinacaoTempoReal;
    } 

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public String getVacinasInfoDia(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        Capacidade dia = capacidadeRepository.getDiaDB();
        Date date = dia.getDia();
        List<Vacina> resultList = vacinaRepository.getVacinasInfoDiaVacina(id, date.toString());
        for (Vacina vacina : resultList) {
            Map<String, Object> map = new HashMap<>();
            map.put("nome_vacina", vacina.getNome());
            map.put("lote", vacina.getLote().getID());
            map.put("data_validade", vacina.getDataValidade());
            map.put("n_utente", vacina.getUtente().getNome());
            result += mapper.writeValueAsString(map);
        }
        return result ;
    }

    public String getUtentesVacinadosPorDia(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        //Capacidade dia = capacidadeRepository.getDiaDB();
        //Date date = dia.getDia();
        List<Utente> resultList = utenteRepository.getUtenteInfoDiaVacina(id, "2022-01-23");
        for (Utente utente : resultList) {
            Map<String, Object> map = new HashMap<>();
            map.put("nome", utente.getNome());
            map.put("data_nascimento", utente.getDataNascimento());
            map.put("email", utente.getEmail());
            map.put("n_utente", utente.getID());
            result += mapper.writeValueAsString(map);
        }
        return result ;
    }
}
