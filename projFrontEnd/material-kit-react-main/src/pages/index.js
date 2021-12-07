import Head from "next/head";
import { Box, Container, Grid } from "@mui/material";
import { Budget } from "../components/dashboard/budget";
import { Schedule } from "../components/dashboard/schedule-vaccine";
import { Check } from "../components/dashboard/check-schedule";
import { LatestOrders } from "../components/dashboard/latest-orders";
import { LatestProducts } from "../components/dashboard/latest-products";
import { Sales } from "../components/dashboard/sales";
import { TasksProgress } from "../components/dashboard/tasks-progress";
import { TotalCustomers } from "../components/dashboard/total-customers";
import { TotalProfit } from "../components/dashboard/total-profit";
import { TrafficByDevice } from "../components/dashboard/traffic-by-device";
import { DashboardLayout } from "../components/dashboard-layout";
<<<<<<< HEAD
=======
import { DashboardLayoutGerente } from "../components/dashboard-layout-gerente";
>>>>>>> 674b97e11b2f97c946aa5312b80fa767d0b57bcf

const Dashboard = () => (
  <>
    <Head>
      <title>Dashboard | Vacinação</title>
    </Head>
    <Box
      component="main"
      sx={{
        flexGrow: 1,
        py: 8,
      }}
    >
      <Container maxWidth={false}>
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
    </Box>
  </>
);
<<<<<<< HEAD

Dashboard.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;
=======
if (typeof window !== 'undefined') {
  if(localStorage.getItem("login") == "false") {
    Dashboard.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;
  } else {
    Dashboard.getLayout = (page) => <DashboardLayoutGerente>{page}</DashboardLayoutGerente>;
  }
}
>>>>>>> 674b97e11b2f97c946aa5312b80fa767d0b57bcf

export default Dashboard;
