import { FormControl, FormHelperText, TextField, Button } from '@mui/material';
import React, { useState } from "react";
import { useRouter } from 'next/router';

export default function FormVaccinationInfo() {
    const router = useRouter();

    const [utente, setUtente] = useState("");
    const [nome, setNome] = useState("");

    function validateForm() {
        return utente.length > 0 && !isNaN(+utente) && nome.length > 0;
    }

    function handleSubmit(event) {
        //alert("HELLO");
        event.preventDefault();
        router.push('/vaccination_info');
    }

    return (
        <form
        component="form" 
        //action="/vaccination_info"
        onSubmit={(e) => {
            handleSubmit(e);
          }} >
          <div className="row" style={{ marginTop: "20px" }}>
              <FormControl variant="outlined">
                  <TextField
                      style={{ marginTop: "10px" }}
                      id="user-number"
                      label="Número de Utente"
                      value={utente}
                      onChange={(e) => setUtente(e.target.value)}
                      variant="outlined"
                  />
                  <FormHelperText id="user-number-helper-text">Campo Nº Utente Saúde no Cartão de Cidadão.</FormHelperText> 
                  <TextField
                      style={{ marginTop: "20px" }}
                      id="user-name"
                      label="Nome completo"
                      value={nome}
                      onChange={(e) => setNome(e.target.value)}
                      variant="outlined"
                  />
                  <Button 
                    variant="contained"
                    style={{ marginTop: "20px" }}
                    type="submit"
                    label="Submit"
                    disabled={!validateForm()}
                  >
                    CONSULTAR
                  </Button>
              </FormControl>
          </div>
        </form>
      );
} 