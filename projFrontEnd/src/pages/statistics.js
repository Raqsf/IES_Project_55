import Head from "next/head";
import { Box, Container } from "@mui/material";
import { DashboardLayoutGerente } from "../components/dashboard-layout-gerente";
import { Sales } from "../components/dashboard/sales";
import { StatisticsToolbar } from "../components/statistics/statistics-toolbar";
import CentroVacinacaoChart from "src/components/statistics/centro-vacinacao-chart";
import { TitleRounded } from "@mui/icons-material";
// import CentroVacinacaoChart from "src/components/statistics/CVs";

// TODO: Última semana, último mês, último ano
const Statistics = () => {
  return (
    <>
      <Head>
        <title>Estatísticas | Vaccination Desk</title>
      </Head>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          py: 8,
        }}
      >
        <Container maxWidth={false}>
          <StatisticsToolbar />
          <CentroVacinacaoChart />
        </Container>
      </Box>
    </>
  );
};

Statistics.getLayout = (page) => <DashboardLayoutGerente>{page}</DashboardLayoutGerente>;

export default Statistics;
