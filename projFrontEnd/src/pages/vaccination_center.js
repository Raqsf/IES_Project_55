import React from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Divider, Grid } from '@mui/material';
import { DashboardLayoutGerente } from '../components/dashboard-layout-gerente';
//import { customers } from '../__mocks__/customers';
import { useRouter } from "next/router"

const VaccinationCenter = () => {
    const router = useRouter();
    const {
        query: { center },
    } = router
    
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
          {center}
        </Typography>
        {/* <Grid container spacing={2}>
          <Grid item lg={6} sm={6} xl={6} xs={12}>
            <NestedList />
          </Grid>
          <Grid item xl={6} lg={6} sm={6} xs={12}>
            <VaccinationOrder />
            {/* <Check /> 
          </Grid>
        </Grid>
        <Divider 
          sx={{
            py:3
          }}
        />
        <TableVaccines /> */}
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