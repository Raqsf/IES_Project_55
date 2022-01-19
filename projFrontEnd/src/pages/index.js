import Head from "next/head";
import { Box, Container, Grid } from "@mui/material";
// import { Budget } from "../components/dashboard/budget";
import { Schedule } from "../components/dashboard/schedule-vaccine";
import { Check } from "../components/dashboard/check-schedule";
import { ManageVaccines } from "../components/gerente/manage";
import { Statistics } from "../components/gerente/statistics";
// import { LatestOrders } from "../components/dashboard/latest-orders";
// import { LatestProducts } from "../components/dashboard/latest-products";
// import { Sales } from "../components/dashboard/sales";
// import { TasksProgress } from "../components/dashboard/tasks-progress";
// import { TotalCustomers } from "../components/dashboard/total-customers";
// import { TotalProfit } from "../components/dashboard/total-profit";
// import { TrafficByDevice } from "../components/dashboard/traffic-by-device";
import { DashboardLayout } from "../components/dashboard-layout";
import { DashboardLayoutGerente } from "../components/dashboard-layout-gerente";
import { useEffect } from 'react';
import { useState } from 'react';

const Dashboard = () => {
  const [username, setUsername] = useState();

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("login"));

    if (user == null) {
      // TODO: acho q devia ser false
      setUsername(true);
    }
    if (user) {
      setUsername(true);
    } else {
      setUsername(false);
    }
  }, []);

  return (
      <>
        <Head>
          <title>Dashboard | Vaccination Desk</title>
        </Head>
        <Box
          component="main"
          sx={{
            flexGrow: 1,
            py: 8,
          }}
        >
          {username 
            ? (<Container maxWidth={false}>
                <h1>Gerente</h1>
                <Grid container spacing={2}>
                <Grid item lg={6} sm={6} xl={6} xs={12}>
                  <Statistics />
                </Grid>
                <Grid item xl={6} lg={6} sm={6} xs={12}>
                  <ManageVaccines />
                </Grid>
              </Grid>
              </Container>) 
            : (<Container maxWidth={false}>
              <h1>Vacinação</h1>
              
              <Grid container spacing={2}>
                <Grid item lg={6} sm={6} xl={6} xs={12}>
                  <Schedule />
                </Grid>
                <Grid item xl={6} lg={6} sm={6} xs={12}>
                  <Check />
                </Grid>
                {/* <Grid
                  item
                  xl={3}
                  lg={3}
                  sm={6}
                  xs={12}
                >
                  <TasksProgress />
                </Grid>
                <Grid item xl={3} lg={3} sm={6} xs={12}>
                  <TotalProfit sx={{ height: "100%" }} />
                </Grid>
                <Grid item lg={8} md={12} xl={9} xs={12}>
                  <Sales />
                </Grid>
                <Grid item lg={4} md={6} xl={3} xs={12}>
                  <TrafficByDevice sx={{ height: "100%" }} />
                </Grid>
                <Grid item lg={4} md={6} xl={3} xs={12}>
                  <LatestProducts sx={{ height: "100%" }} />
                </Grid>
                <Grid item lg={8} md={12} xl={9} xs={12}>
                  <LatestOrders />
                </Grid> */}
              </Grid>
            </Container>
          )
          }
          </Box>
      </>
    );
}

if (typeof window !== 'undefined') {
  if(!JSON.parse(localStorage.getItem("login"))) {
    Dashboard.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;
  } else {
    Dashboard.getLayout = (page) => <DashboardLayoutGerente>{page}</DashboardLayoutGerente>;
  }
}

export default Dashboard;
