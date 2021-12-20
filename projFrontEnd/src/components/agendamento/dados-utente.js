import React, { useState } from "react";
import { TextField, Stack, FormControl,FormHelperText,Button, Container } from '@mui/material';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import DesktopDatePicker from '@mui/lab/DesktopDatePicker';
import { Box } from "@mui/system";
import { useRouter } from 'next/router';
import api from '../../api';
//import axios from 'axios';

export default function DadosUtente(props) {
  const router = useRouter();
  const { history } = props;

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
      dataNascimento: date.toISOString()
    };
    
    console.log(date)
    console.log(user.dataNascimento)
    console.log(date.toISOString())
    console.log("HERE ");
    api
        .post(`/utente`, user, headers)
        .then((response) => { setResposta(response.data); console.log(response.data);})
        .catch((err) => {
          console.error("ops! ocorreu um erro" + err);
        });
    // router.push('/success');

    // axios.get(`http://localhost:8080/api/v1/centrovacinacao`, headers)
    //   .then(res => {
    //     const persons = res.data;
    //     this.setState({ persons });
    //     console.log(persons);
    //   })
  }

  return (
    <Container>
      <Box className="DadosUtente" onSubmit={handleSubmit} sx={{
          display: 'flex',
          justifyContent: 'center',
          pt: 3
        }}>
        <form
        component="form" 
        onSubmit={(e) => {
            handleSubmit(e);
          }} >
        <FormControl variant="outlined">
              <TextField fullWidth label="Número de Utente"
                  value={utente}
                  onChange={(e) => setUtente(e.target.value)}
                  style={{ marginTop: "20px" }}/>
              <FormHelperText id="user-number-helper-text">Campo Nº Utente Saúde no Cartão de Cidadão.</FormHelperText> 
              <TextField fullWidth label="Nome Completo"
                      value={nome}
                      onChange={(e) => setNome(e.target.value)}
                      style={{ marginTop: "20px" }}/>
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                  <Stack spacing={2} style={{ marginTop: "20px" }}>
                      <DesktopDatePicker
                      label="Data de Nascimento"
                      disableFuture
                      value={date}
                      minDate={new Date('1900-01-01')}
                      onChange={(newValue) => {
                          setDate(newValue);
                      }}
                      renderInput={(params) => <TextField {...params} />}
                      />
                  </Stack>
              </LocalizationProvider>
              {/* <NextLink href="/success" passHref> */}
                <Button 
                  variant="contained" 
                  size="lg" 
                  type="submit" 
                  disabled={!validateForm()}
                  style={{ marginTop: "20px" }}
                >
                    Validação
                </Button>
              {/* </NextLink> */}
          </FormControl>
          </form>
      </Box>
    </Container>
  );
}
