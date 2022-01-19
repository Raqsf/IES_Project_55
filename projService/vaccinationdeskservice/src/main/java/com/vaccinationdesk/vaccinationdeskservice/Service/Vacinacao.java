package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.googleapis.auth.clientlogin.ClientLogin.Response;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.Capacidade;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CapacidadeRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

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

    Map<Integer, List<String>> dentroDoCentroMap = new HashMap<>();

    /**
     * Funcao que faz a vacinacao para um determinado dia.
     * É feito um pedido à Base de Dados de todos os agendamentos para o dia em questão
     * e por cada agendamento é feita a vacinacao.
     * 
     * @return 
     */
    public ResponseEntity<Object> vacinacao() {
        // ! ir buscar a string para o dia em questao (como esta escrito em cima, talvez
        // a uma tabela que faça so guardar os dias e passa-los)

        Capacidade dia = capacidadeRepository.getDiaDB();
        Date date = dia.getDia();

        List<Agendamento> agendamentoParaODiaList = agendamentoRepository.getAgendamentosPorDia(date.toString());
        capacidadeRepository.delete(dia);

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
                vacina.setUtente(utente_vacina_administrada);
                vacina.setDataAdministracao(data_toma_vacina);
                centro.decreaseCapacidadeAtual();
                
                centroVacinacaoRepository.save(centro);
                vacinaRepository.save(vacina);
                List<String> infoList = new ArrayList<String>();
                infoList.add(utente_vacina_administrada.getNome() + ", " + utente_vacina_administrada.getID() + ", "
                + vacina.getNome() + ", " + vacina.getDataAdministracao());
                dentroDoCentroMap.put(i, infoList);
                
                if (i > 4) {
                    int j = i - 5;
                    dentroDoCentroMap.remove(j);
                }
                //! fazer a parte das pessoas sairem no final do dia

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
    public List<List<String>> getVacinacaoEmTempoReal() {
        //adsada
        List<List<String>> vacinacaoTempoReal = new ArrayList<>();
        for (Integer key : dentroDoCentroMap.keySet()) {
            vacinacaoTempoReal.add(dentroDoCentroMap.get(key));
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
}
