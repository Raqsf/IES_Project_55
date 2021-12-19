import Head from "next/head";
import { Box, Container } from "@mui/material";
import { DashboardLayoutGerente } from "../components/dashboard-layout-gerente";

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

          <img
            src="https://ichef.bbci.co.uk/news/1024/cpsprodpb/16674/production/_117346719_1mar_vax-nc.png"
            alt="grafico de vacinação "
          />
        </Container>
      </Box>
    </>
  );
};

Statistics.getLayout = (page) => <DashboardLayoutGerente>{page}</DashboardLayoutGerente>;

export default Statistics;
