package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

    private boolean first_time = true;

    /**
     * Funcao que faz a vacinacao para um determinado dia.
     * É feito um pedido à Base de Dados de todos os agendamentos para o dia em questão
     * e por cada agendamento é feita a vacinacao.
     * 
     * @return 
     * @throws JsonProcessingException
     * @throws ConflictException
     */
    @Async
    public ResponseEntity<Object> vacinacao() throws JsonProcessingException, ConflictException {
        // ! ir buscar a string para o dia em questao (como esta escrito em cima, talvez
        // a uma tabela que faça so guardar os dias e passa-los)
        ObjectMapper mapper = new ObjectMapper();

        if (capacidadeRepository.getDiaDB() == null) {
            throw new ConflictException("Não existe capacidade para o dia em questão");
        }
        Capacidade dia = capacidadeRepository.getDiaDB();
        Date dia_ = capacidadeRepository.getDiaDB().getDia();

        // System.out.println("data como esta no repo: " + dia_.toString());
    
        List<Agendamento> agendamentoParaODiaList = agendamentoRepository.getAgendamentosPorDia(dia_.toString());
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
//                if (centro.getCapacidadeAtual() <= 0) {
//                    throw new ConflictException(centro.getNome() + " encontra-se sem vacinas!");
//                }
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
                
                String infoJSON = mapper.writeValueAsString(map);
                
                if (dentroDoCentroMap.keySet().size() < 10) {
                    dentroDoCentroMap.put(i % 10, infoJSON);
                } else {
                    dentroDoCentroMap.remove(i % 10);
                    if (i == n_vacinas) {
                        for (Integer key : dentroDoCentroMap.keySet()) {
                            dentroDoCentroMap.remove(key);
                            wait(50);
                        }
                    }
                }
                i++;
                wait(50);
            } else {
                break;
            }
        }
        
        capacidadeRepository.delete(dia);

        return ResponseEntity.ok(null);
    }
    
    /**
     * Funcao que retona quais os utentes que estão dentro dos centros
     * sendo a mesma, chamada em intervalos de 1 segundo pelo front-end
     * @return
     */
    @Async
    public List<Utente> getVacinacaoEmTempoReal(Integer id_centro) {
        List<Utente> vacinacaoTempoReal = new ArrayList<>();
        for (Integer key : dentroDoCentroMap.keySet()) {
            JSONObject json = new JSONObject(dentroDoCentroMap.get(key));
            int centro = json.getInt("centro");
            if (centro == id_centro) {
                Utente utente = utenteRepository.findUtenteById((int) json.get("n_utente"));
                vacinacaoTempoReal.add(utente);
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

    public List<Vacina> getVacinasInfoDia(Integer id) throws JsonProcessingException {
        //Capacidade dia = capacidadeRepository.getDiaDB();
        //Date date = dia.getDia();
        
        Date dia2 = capacidadeRepository.getDiaDB().getDia();
        Calendar calendario2 = Calendar.getInstance();
        calendario2.setTime(dia2);
        calendario2.add(Calendar.DATE, -1);
        dia2.setTime(calendario2.getTime().getTime());

        List<Vacina> resultList = vacinaRepository.getVacinasInfoDiaVacina(id, dia2.toString());
        
        Date dia3 = capacidadeRepository.getDiaDB().getDia();
        Calendar calendario3 = Calendar.getInstance();
        calendario3.setTime(dia3);
        calendario3.add(Calendar.DATE, 1);
        dia2.setTime(calendario3.getTime().getTime());
        return resultList; 
    }

    public List<Utente> getUtentesVacinadosPorDia(Integer id) throws JsonProcessingException {
        //Capacidade dia = capacidadeRepository.getDiaDB();
        //Date date = dia.getDia();

        Date dia2 = capacidadeRepository.getDiaDB().getDia();
        Calendar calendario2 = Calendar.getInstance();
        calendario2.setTime(dia2);
        calendario2.add(Calendar.DATE, -1);
        dia2.setTime(calendario2.getTime().getTime());

        List<Utente> resultList = utenteRepository.getUtenteInfoDiaVacina(id, dia2.toString());
    
        Date dia3 = capacidadeRepository.getDiaDB().getDia();
        Calendar calendario3 = Calendar.getInstance();
        calendario3.setTime(dia3);
        calendario3.add(Calendar.DATE, 1);
        dia2.setTime(calendario3.getTime().getTime());
        return resultList;

    }
}
