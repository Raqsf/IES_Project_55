import { FormControl, FormHelperText, TextField, Button } from '@mui/material';
import React, { useState } from "react";
import { useRouter } from 'next/router';
import api from '../../api';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure()
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
            id: parseInt(utente,10),
            nome: nome
        };


        // axios não permite mandar dados com o GET
        api
        .post(`/agendamento`, user, headers)
        .then((response) => {
            // setResposta(response.data);
            console.log("HERE", response.data);
            if(response.data.length == 0)  {
                // TODO: passar essa info para a pagina vaccination_info
                // alert("Não existe agendamento");
                router.push({ pathname: "/vaccination_info",
                query: {
                    utente_nome: user.nome,
                    utente_num: user.id,
                    estado: "não agendado"
                    }
                // search: `?response=${response.data}`
                // state: { detail: "hello"}
                }, "/vaccination_info");
                // toast.info("Não existe agendamento", {position: toast.POSITION.TOP_CENTER});
            } else {
                console.log(response.data)
                router.push({ pathname: "/vaccination_info",
                    query: {
                        utente_nome: response.data.utente.nome,
                        utente_num: response.data.utente.id,
                        // utente: {nome:response.data[0].utente.nome, id:response.data[0].utente.nome},
                        estado: "agendado",
                        centro: response.data.centro.nome,
                        morada: response.data.centro.morada,
                        data: response.data.diaVacinacao
                        }
                    // search: `?response=${response.data}`
                    // state: { detail: "hello"}
                }, "/vaccination_info");
            }
        })
        .catch((err) => {
            console.log(err.response)
            if(err.response.status === 409) {
                toast.info(err.response.data.message, {position: toast.POSITION.TOP_CENTER});
                router.push({ pathname: "/vaccination_info",
                query: {
                    utente_nome: user.nome,
                    utente_num: user.id,
                    estado: "lista de espera"
                    }
                // search: `?response=${response.data}`
                // state: { detail: "hello"}
                }, "/vaccination_info");
            } else {
                console.error("ops! ocorreu um erro" + err);
                toast.error("Erro", {position: toast.POSITION.TOP_CENTER});
            }
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