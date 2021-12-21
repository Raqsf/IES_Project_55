import Head from 'next/head';
import { Typography, Container, Box } from '@mui/material';
import { DashboardLayout } from '../components/dashboard-layout';
//import { customers } from '../__mocks__/customers';
import Success from 'src/components/agendamento/success';

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
        <Typography
          sx={{ m: 1 }}
          variant="h4"
        >
          Agendamento
        </Typography>
        <Success/>
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