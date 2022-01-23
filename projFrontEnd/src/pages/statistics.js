import Head from "next/head";
import { useRouter } from "next/router";
import { useState, useEffect } from "react";
import { Box, Container } from "@mui/material";
import { DashboardLayoutGerente } from "../components/dashboard-layout-gerente";
import { Sales } from "../components/dashboard/sales";
import { StatisticsToolbar } from "../components/statistics/statistics-toolbar";
import CentroVacinacaoChart from "src/components/statistics/centro-vacinacao-chart";
import { TitleRounded } from "@mui/icons-material";
// import CentroVacinacaoChart from "src/components/statistics/CVs";

// TODO: Última semana, último mês, último ano
const Statistics = () => {
  const router = useRouter();
  const [loading, setLoading] = useState(true);

  useEffect(() => { 
    setLoading(true);
    if(!JSON.parse(localStorage.getItem("login"))) {
      router.push("/");
    } else {
      setLoading(false);
    }
  })

  return (
    <>
      <Head>
        <title>Estatísticas | Vaccination Desk</title>
      </Head>
      {!loading ? 
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
      : null}
    </>
  );
};

if (typeof window !== 'undefined') {
  if(JSON.parse(localStorage.getItem("login"))) {
    Statistics.getLayout = (page) => <DashboardLayoutGerente>{page}</DashboardLayoutGerente>;
  } else {
    window.location.href = "/";
  }
}

export default Statistics;
