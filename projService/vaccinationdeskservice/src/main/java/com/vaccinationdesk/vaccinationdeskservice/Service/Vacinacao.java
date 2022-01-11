package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.sql.Date;
import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.model.Vacina;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Vacinacao {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;

    public void vacinacao() {
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        List<Vacina> vacinaList = vacinaRepository.findAll();

        int i = 0;
        for (Vacina vacina : vacinaList) {
            Agendamento agendamento = agendamentoList.get(i);
            Date data_toma_vacina = agendamento.getDiaVacinacao();
            Utente utente_vacina_administrada = agendamento.getUtente();
            CentroVacinacao centro = agendamento.getCentro();
            vacina.setUtente(utente_vacina_administrada);
            vacina.setDataAdministracao(data_toma_vacina);
            centro.updateCapacidadeAtual();

            centroVacinacaoRepository.save(centro);
            vacinaRepository.save(vacina);
            i++;
        }

        //! na geracao de dados fazer o os "timers" para  simular a chegada das pessoas, e talvez
        //! arranjar forma de ter uma lista ou um dicionario de malta que está a ser vacinada no momento
        //! e ao fim de algum tempo deverá ser removido da lista ou dicionario.
        //! A IDEIA SERA NO SITE, A CADA X SEGUNDOS FAZER UM GET DA LISTA OU DO DICIONARIO E MOSTRAR LÁ
        //! AS PESSOAS A SEREM VACINADAS EM TEMPO REAL, DEPOIS FAZER ISTO POR CENTRO TAMBEM.
    }    
}
