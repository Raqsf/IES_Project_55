package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.util.List;

import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Centro {
    
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;

    public void atualizarCapacidade() {
        List<CentroVacinacao> centrosVacinacaoList = centroVacinacaoRepository.findAll();
        for (CentroVacinacao centro : centrosVacinacaoList) {
            System.out.println(centro);
        }
    }
    
    
}
