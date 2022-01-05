package com.vaccinationdesk.vaccinationdeskservice.Service;

import com.vaccinationdesk.vaccinationdeskservice.repository.VacinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Vacinacao {

    @Autowired
    private VacinaRepository vacinaRepository;

    public void vacinacao() {
        

    }
    
}
