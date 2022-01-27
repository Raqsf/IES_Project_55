import React, { useState } from "react";
import { TextField, Stack, FormControl, FormHelperText, Button, Container } from "@mui/material";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import LocalizationProvider from "@mui/lab/LocalizationProvider";
import DesktopDatePicker from "@mui/lab/DesktopDatePicker";
import { Box } from "@mui/system";
import { useRouter } from "next/router";
import api from "../../api";
//import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure()
export default function DadosUtente(props) {
  const router = useRouter();

  const [utente, setUtente] = useState("");
  const [nome, setNome] = useState("");
  const [date, setDate] = useState(new Date());
  const [resposta, setResposta] = useState();

  function validateForm() {
    return utente.length > 0 && nome.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();

    const headers = {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    };

    const user = {
      id: utente,
      nome: nome,
      dataNascimento: date.toISOString(),
    };

    api
      .post(`/utente`, user, headers)
      .then((response) => {
        // if (response.status >= 200 && response.status < 300)
        console.log(response)
        // NOTA: podemos fazer com um alert
        setResposta(response.data);
        router.push({
          pathname: "/success",
          search: `?response=${resposta}`
        });
      })
      .catch((err) => {
        if(err.response.status === 409) {
          toast.info(err.response.data.message, {position: toast.POSITION.TOP_CENTER});
        } else {
          if (err.response) {
            toast.error(err.response.data.message, {position: toast.POSITION.TOP_CENTER});
          } else if (err.request) {
            console.log(err.request);
          } else {
            console.log('err', err.message);
          }
          console.log(err.config);
        }
      });
  }

  return (
    <Container>
      <Box
        className="DadosUtente"
        sx={{
          display: "flex",
          justifyContent: "center",
          pt: 3,
        }}
      >
        <form
          component="form"
          onSubmit={handleSubmit}
          style={{
            backgroundColor: '#f3f4f6', borderRadius: "25px"
          }}
        >
          <div className="row" style={{ margin: "20px" }}>
          <FormControl variant="outlined">
            <TextField
              fullWidth
              label="Número de Utente"
              value={utente}
              onChange={(e) => setUtente(e.target.value)}
              style={{ marginTop: "20px" }}
            />
            <FormHelperText id="user-number-helper-text">
              Campo Nº Utente Saúde no Cartão de Cidadão.
            </FormHelperText>
            <TextField
              fullWidth
              label="Nome Completo"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              style={{ marginTop: "20px" }}
            />
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <Stack spacing={2} style={{ marginTop: "20px" }}>
                <DesktopDatePicker
                  label="Data de Nascimento"
                  disableFuture
                  value={date}
                  minDate={new Date("1900-01-01")}
                  onChange={(newValue) => {
                    setDate(newValue);
                  }}
                  renderInput={(params) => <TextField {...params} />}
                />
              </Stack>
            </LocalizationProvider>
            <Button
              variant="contained"
              size="lg"
              type="submit"
              disabled={!validateForm()}
              style={{ marginTop: "20px" }}
            >
              Validação
            </Button>
          </FormControl>
          </div>
        </form>
      </Box>
    </Container>
  );
}
