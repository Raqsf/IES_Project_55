package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // para fazer passar os dias, poderá ser:
    // ir buscar o dia em questão, ha uma nova tabela da bd, e a partir dai ir
    // buscar todas os agendamentos para esse dia
    // criar uma estrutura de dados (poderá ser uma nova tabela, estudar esta
    // possibilidade tambem)que permita que cada utente
    // fique dentro do centro x segundos, que corresponderiam a 30/40 min na
    // realidade
    // ao fim de esses segundos a pessoa sai dessa estrutura de dados, e entrará uma
    // nova, e isto repete-se ate as lista de pessoas para aquele dia acabar, e aí
    // faz uma pauda (qql coisa, agr fui tudo dormir crlh, esperem para amanha), e
    // quando esse x segundos passarem, volta a repetir todo o processo


    // qual a ideia
    /*
    qual a arquitetura
    estatistica de como o trabalho foi dividido
    apresentar os resultdos finais
    fazer uma demo em real time, mas podemos ter um backup em video
    cenario interessante, ter um url para toda a gente da turma estar a mexer de
    
    */
    public void vacinacao() {

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
    }
    
    @Async
    public List<List<String>> getVacinacaoEmTempoReal() {
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
