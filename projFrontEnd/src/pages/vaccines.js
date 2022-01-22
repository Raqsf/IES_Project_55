import React from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Divider, Grid } from '@mui/material';
import { DashboardLayoutGerente } from '../components/dashboard-layout-gerente';
import NestedList from '../components/gerente/vaccination_centers';
import TableVaccines from '../components/gerente/table_vaccines';
import {VaccinationOrder} from '../components/gerente/vaccination_order';
import api from "../api";

const Manage = () => {
  const [centros, setCentros] = React.useState([]);
  const [doencas, setDoencas] = React.useState([]);

  // const handleClick = () => {
  //   alert("HELLO");
  //   setOpen(!open);
  // };
  // function getVaccinationCenters() {
    
  const headers = {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
  };

  React.useEffect(() => {
    const getData = async () => {
      const data = await api.get(
        `/centrovacinacao`, headers
      ).then((response) => {
        setCentros(response.data);
        console.log(response.data)
      })
      .catch((err) => {
        console.error("ops! ocorreu um erro" + err);
        alert("Erro");
      });
    };
    getData();
  }, []);

  React.useEffect(() => {
    const getData = async () => {
      const data = await api.get(
        `/doencas`, headers
      ).then((response) => {
        let dict = [];

        if (localStorage.getItem('ordem') === null) {
          for (const [key, value] of Object.entries(response.data)) {
            dict.push({
              id: key,
              doenca: value.doenca,
              checked: false
            })
          }
        } else {
          const ordem = JSON.parse(localStorage.getItem('ordem'));
          console.log(ordem);
          for (const [key, value] of Object.entries(response.data)) {
            dict.push({
              id: key,
              doenca: value.doenca,
              checked: ordem[key].checked
            })
          }
        }
        // console.log(dict)
        setDoencas(dict);
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
        Gerir | Vaccination Desk
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
          Gerir Vacinas
        </Typography>
        <Grid container spacing={2}>
          <Grid item lg={6} sm={6} xl={6} xs={12}>
            <NestedList centros={centros} />
          </Grid>
          <Grid item xl={6} lg={6} sm={6} xs={12}>
            <VaccinationOrder doencas={doencas}/>
          </Grid>
        </Grid>
        <Divider 
          sx={{
            py:3
          }}
        />
        <TableVaccines /*  centros={centros}  */ />
      </Container>
    </Box>
  </>
);
}

Manage.getLayout = (page) => (
  <DashboardLayoutGerente>
    {page}
  </DashboardLayoutGerente>
);

export default Manage;