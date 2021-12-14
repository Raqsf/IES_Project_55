import Head from 'next/head';
import { Box, Container, Typography } from '@mui/material';
import { DashboardLayout } from '../components/dashboard-layout';
// import { customers } from '../__mocks__/customers';
import DadosUtente from 'src/components/agendamento/dados-utente';

const Agendamento = () => (
  <>
    <Head>
      <title>
      Agendamento | Vaccination Desk
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
      <Box
        sx={{
          alignItems: 'center',
          display: 'flex',
          justifyContent: 'space-between',
          flexWrap: 'wrap',
          m: -1
        }}
      >
        <Typography
          sx={{ m: 1 }}
          variant="h4"
        >
          Agendamento
        </Typography>
      </Box>
      <DadosUtente/>
      </Container>
    </Box>
  </>
);
Agendamento.getLayout = (page) => (
  <DashboardLayout>
    {page}
  </DashboardLayout>
);

export default Agendamento;
