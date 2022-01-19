import Head from 'next/head';
import { Box, Container, Grid } from '@mui/material';
import { CheckToolbar } from '../components/utente/check-toolbar';
import { DashboardLayout } from '../components/dashboard-layout';
import { Info } from '../components/utente/info';

const VaccinationInfo = () => (
  <>
    <Head>
      <title>
      Informação Agendamento | Vaccination Desk
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
        <Grid>
          <Info />
        </Grid>
      </Container>
    </Box>
  </>
);

VaccinationInfo.getLayout = (page) => (
  <DashboardLayout>
    {page}
  </DashboardLayout>
);

export default VaccinationInfo;