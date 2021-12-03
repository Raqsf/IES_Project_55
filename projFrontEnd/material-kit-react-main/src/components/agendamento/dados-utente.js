import React, { useState } from "react";
import TextField from '@mui/material/TextField';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import Stack from '@mui/material/Stack';
import DesktopDatePicker from '@mui/lab/DesktopDatePicker';
import 'bootstrap/dist/css/bootstrap.min.css'
import { Form, Button, Col, Container } from 'react-bootstrap';

export default function DadosUtente() {
  const [utente, setUtente] = useState("");
  const [nome, setNome] = useState("");
  const [date, setDate] = useState(new Date());

  function validateForm() {
    return utente.length > 0 && nome.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();
  }

  return (
    <Container className="DadosUtente">
        <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="formBasicText">
                <Form.Label>Número de Utente</Form.Label>
                <Form.Control type="text" placeholder="123456789" 
                    value={utente}
                    onChange={(e) => setUtente(e.target.value)}/>
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicText">
                <Form.Label>Nome completo</Form.Label>
                <Form.Control type="text" placeholder="Nome completo" 
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}/>
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicDate">            
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <Stack spacing={2}>
                        <label>Data de nascimento</label>
                        <DesktopDatePicker
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
            </Form.Group>
            <Button href="/success" variant="primary" style={{ marginLeft: "50%" }} block size="lg" type="submit" disabled={!validateForm()}>
                Validação
            </Button>
        </Form>
    </Container>
  );
}
