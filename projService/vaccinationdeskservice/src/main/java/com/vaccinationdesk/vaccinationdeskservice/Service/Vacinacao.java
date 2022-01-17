package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
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

    public void vacinacao() {
        // ! ir buscar a string para o dia em questao (como esta escrito em cima, talvez
        // a uma tabela que faça so guardar os dias e passa-los)
        List<Agendamento> agendamentoParaODiaList = agendamentoRepository.getAgendamentosPorDia("2022-01-25");
        List<Vacina> vacinaList = vacinaRepository.findAll();
        int i = 0;
        System.out.println(agendamentoParaODiaList.size());
        for (Vacina vacina : vacinaList) {
            if (i < agendamentoParaODiaList.size()) {

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
                System.out.println("entrou no centro: " + dentroDoCentroMap.get(i));
                
                if (i > 2) {
                    int j = i - 3;
                    dentroDoCentroMap.remove(j);
                }
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
