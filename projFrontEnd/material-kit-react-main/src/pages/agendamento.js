import Head from 'next/head';
import { Box, Container, TextField } from '@mui/material';
import { DashboardLayout } from '../components/dashboard-layout';
import { customers } from '../__mocks__/customers';
import DadosUtente from 'src/components/agendamento/dados-utente';

const Agendamento = () => (
  <>
    <Head>
      <title>
        Agendamento
      </title>
    </Head>
    <h1 style={{ fontSize:"500%", textAlign: "center"}}>Agendamento</h1>
    <DadosUtente/>
  </>
);
Agendamento.getLayout = (page) => (
  <DashboardLayout>
    {page}
  </DashboardLayout>
);

export default Agendamento;
