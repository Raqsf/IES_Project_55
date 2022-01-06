import React from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Divider, Grid, TextField, Button } from '@mui/material';
import { DashboardLayoutGerente } from '../components/dashboard-layout-gerente';
//import { customers } from '../__mocks__/customers';
import { useRouter } from "next/router";
import api from "../api";
// import { createBrowserHistory  as createHistory} from 'history';
import { PeopleVaccinated } from "../components/gerente/people_vaccinated";
import { VaccinesAdministered } from "../components/gerente/vaccines_administered";

const VaccinationCenter = () => {
    // const history = createHistory();
    // console.log(window.location.hrefs)
    // history.push()
    // window.addEventListener('load', history.go(0), false);
    const router = useRouter();
    const [centro, setCentro] = React.useState('');
    const {
        query: { id },
    } = router

    const headers = {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    };

    React.useEffect(() => {
      const getData = async () => {
        const data = await api.get(
          `/centrovacinacao/${id}`, headers
        ).then((response) => {
          setCentro(response.data);
        })
        .catch((err) => {
          console.error("ops! ocorreu um erro" + err);
          alert("Erro");
        });
      };
      getData();
    }, []);
    
    return (
  <>
    <Head>
      <title>
        Centro de Vacinação | Vaccination Desk
      </title>
    </Head>
    <Box
      component="main"
      sx={{
        flexGrow: 1,
        py: 8
      }}
    >
      <Container maxWidth={false}>
        <Typography
          sx={{ m: 1 }}
          variant="h4"
        >
          {centro.nome}
        </Typography>
        <Typography
          sx={{ m: 1 }}
          variant="string"
        >
          {centro.morada}
        </Typography>
        {/* TODO: numero de pessoas vacinadas e numero vacinas em tempo real */}
        <Grid container spacing={2}>
          <Grid item lg={6} sm={6} xl={6} xs={12}>
            <PeopleVaccinated />
          </Grid>
          <Grid item xl={6} lg={6} sm={6} xs={12}>
            <VaccinesAdministered />
          </Grid>
        </Grid>
        {/*<Divider 
          sx={{
            py:3
          }}
        />
        <TableVaccines />      */}
      </Container>
      <Container maxWidth={false} sx={{ mt: 4 }}>
        <Box 
          component="form"
          sx={{
              '& .MuiTextField-root': { m: 1, width: '25ch' },
          }}
          noValidate
          autoComplete="off"
        > 
          <Typography
            sx={{ m: 1 }}
            variant="h6"
          >
            Capacidade Máxima
          </Typography>
          {centro ? <TextField
            id="max-vaccines"
            label="Vacinas"
            type="number"
            defaultValue={centro.capacidadeMax}
            InputLabelProps={{
                shrink: true,
            }}
          />: null}
          {centro ? <TextField
            id="max-people"
            label="Pessoas"
            type="number"
            defaultValue="1"
            InputLabelProps={{
                shrink: true,
            }}
          />: null}
          <Box sx={{
              pt: 2,
              display: 'flex',
              alignItems: 'center'
            }}
          >
            <Button
              color="primary"
              variant="contained"
              sx={{ mr: 1 }}
            >
              Guardar
            </Button>
          </Box>
        </Box>
      </Container>
    </Box>
  </>
);
    }

VaccinationCenter.getLayout = (page) => (
  <DashboardLayoutGerente>
    {page}
  </DashboardLayoutGerente>
);

export default VaccinationCenter;