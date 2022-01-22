import Head from "next/head";
import { useRouter } from "next/router";
import { useState, useEffect } from "react";
import { Box, Container } from "@mui/material";
import { DashboardLayoutGerente } from "../components/dashboard-layout-gerente";
import { Sales } from "../components/dashboard/sales";
import { StatisticsToolbar } from "../components/statistics/statistics-toolbar";

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
          <table className="styled-table">
            <thead>
              <tr>
                <th>doses aplicadas</th>
                <th>população imunizada</th>
                <th>doses restantes</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1000</td>
                <td>1000</td>
                <td>6000</td>
              </tr>
            </tbody>
          </table>
          <Sales />
          <img
            src="https://ichef.bbci.co.uk/news/1024/cpsprodpb/16674/production/_117346719_1mar_vax-nc.png"
            alt="grafico de vacinação "
          />
        </Container>
      </Box>
      : null}
    </>
  );
};

if (typeof window !== 'undefined') {
  if(JSON.parse(localStorage.getItem("login"))) {
    Statistics.getLayout = (page) => <DashboardLayoutGerente>{page}</DashboardLayoutGerente>;
  }
}

export default Statistics;
