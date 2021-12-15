import React from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Divider } from '@mui/material';
import { DashboardLayoutGerente } from '../components/dashboard-layout-gerente';
//import { customers } from '../__mocks__/customers';
import NestedList from '../components/gerente/vaccination_centers';
import TableVaccines from '../components/gerente/table_vaccines';

const Manage = () => (
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
        <NestedList />
        <Divider 
          sx={{
            py:3
          }}
        />
        <TableVaccines />
        {/* Selecionar centros de vacinção para definir capacidadde de vacinas (e pessoas)*/}
        {/* Definir ordem da vacinação */}
        {/* Tabela com centros de vacinação, vacinas a chegar no dia x e quantas vacinas têm*/}
        {/* <Success/> */}
      </Container>
    </Box>
  </>
);

Manage.getLayout = (page) => (
  <DashboardLayoutGerente>
    {page}
  </DashboardLayoutGerente>
);

export default Manage;