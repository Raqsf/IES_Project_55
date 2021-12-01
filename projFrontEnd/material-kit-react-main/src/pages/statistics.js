import Head from "next/head";
import { Box, Container } from "@mui/material";
import { DashboardLayout } from "../components/dashboard-layout";

const statistics = () => {
  return (
    <>
      <Head>
        <title>Statistics</title>
      </Head>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          py: 8,
        }}
      >
        <Container maxWidth={false}></Container>
      </Box>
    </>
  );
};

statistics.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default statistics;
