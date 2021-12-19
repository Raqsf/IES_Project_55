import React, { useState } from "react";
import TextField from '@mui/material/TextField';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import Stack from '@mui/material/Stack';
import DesktopDatePicker from '@mui/lab/DesktopDatePicker';
import { FormControl,FormHelperText,Button, Container } from '@mui/material';
import { Box } from "@mui/system";
import NextLink from 'next/link';

export default function DadosUtente(props) {
  const { history } = props;

  const [utente, setUtente] = useState("");
  const [nome, setNome] = useState("");
  const [date, setDate] = useState(new Date());

  function validateForm() {
    return utente.length > 0 && nome.length > 0;
  }

  function handleSubmit(event) {
    //event.preventDefault();
  }

  return (
    <Container>
      <Box className="DadosUtente" onSubmit={handleSubmit} sx={{
          display: 'flex',
          justifyContent: 'center',
          pt: 3
        }}>
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
              <NextLink href="/success" passHref>
                <Button 
                  variant="contained" 
                  size="lg" 
                  type="submit" 
                  disabled={!validateForm()}
                  style={{ marginTop: "20px" }}
                >
                    Validação
                </Button>
              </NextLink>
          </FormControl>
      </Box>
    </Container>
  );
}
