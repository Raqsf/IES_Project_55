
package com.vaccinationdesk.vaccinationdeskservice.repository;

import com.vaccinationdesk.vaccinationdeskservice.model.Doenca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DoencaRepository extends JpaRepository<Doenca, Integer>{
    Doenca findDoencaById(Integer id);
}
