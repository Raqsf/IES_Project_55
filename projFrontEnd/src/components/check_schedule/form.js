import { FormControl, FormHelperText, TextField, Button } from '@mui/material';
import React, { useState } from "react";
import { useRouter } from 'next/router';
import api from '../../api';

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
        const headers = {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
        };

        const user = {
            id: utente,
            nome: nome,
        };
        
        api
        .get(`/agendamento/${user.id}`, headers)
        .then((response) => {
            // setResposta(response.data);
            console.log(response.data);
            if(response.data.length == 0)  {
                // TODO: passar essa info para a pagina vaccination_info
                alert("Não existe agendamento");
            } else {
                router.push({ pathname: "/vaccination_info",
                    // search: `?response=${response.data}`
                    state: { detail: "hello"}
                });
            }
        })
        .catch((err) => {
            console.error("ops! ocorreu um erro" + err);
            alert("Erro");
        });
        // router.push('/vaccination_info');
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