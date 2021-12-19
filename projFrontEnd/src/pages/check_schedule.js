import Head from 'next/head';
import { Box, Container, FormControl, FormHelperText, TextField, Button } from '@mui/material';
import { CheckToolbar } from '../components/utente/check-toolbar';
import { DashboardLayout } from '../components/dashboard-layout';
import FormVaccinationInfo from '../components/check_schedule/form'

const CheckSchedule = () => (
  <>
    <Head>
      <title>
      Consultar Agendamento | Vaccination Desk
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
        <CheckToolbar />
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'center',
            pt: 3
          }}
        >
          <FormVaccinationInfo />
        </Box>
      </Container>
    </Box>
  </>
);

CheckSchedule.getLayout = (page) => (
  <DashboardLayout>
    {page}
  </DashboardLayout>
);

export default CheckSchedule;