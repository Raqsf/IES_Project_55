import React from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Divider, Grid, TextField, Button } from '@mui/material';
import { DashboardLayoutGerente } from '../components/dashboard-layout-gerente';
//import { customers } from '../__mocks__/customers';
import { useRouter } from "next/router";
import api from "../api";
// import { useParams } from "react-router-dom";
import { useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure()
const PeopleVaccinated = () => {
    const router = useRouter();
    // const [loading, setLoading] = useState(true);
    // const [capacity, setCapacity] = useState();
    const {
        query: { id },
    } = router
    console.log(id)
    // 
    // if(id) {
    //   localStorage.setItem("id", id);
    // }
    //  
    // const [centro, setCentro] = React.useState('');

    // const headers = {
    //   "Access-Control-Allow-Origin": "*",
    //   "Content-Type": "application/json",
    // };    
    // 
    // React.useEffect(() => {
    //   setLoading(true);
    //   if (id) {
        // api.get(
            // `/centrovacinacao/${id}`, headers
        //   ).then((response) => {
            // setCentro(response.data);
            // setCapacity(response.data.capacidadeMax);
            // setLoading(false);
        //   })
        //   .catch((err) => {
            // console.error("ops! ocorreu um erro" + err);
            // alert("Erro");
            // if(response.status === 500 && typeof id == undefined) {
            //   alert("Erro")
            // }
        //   })
        // }
    //   const loop = setInterval(function() {

        // id = localStorage.getItem("id");

        // api.get(
            // `/centrovacinacao/${id}`, headers
        //   ).then((response) => {
            // setCentro(response.data);
            // setCapacity(response.data.capacidadeMax);
        //   })
        //   .catch((err) => {
            // console.error("ops! ocorreu um erro" + err);
            // alert("Erro");
        //   }
        // );
        // }, 1000);
        // return () => clearInterval(loop);
    //   }, []);

    return (
  <>
    <Head>
      <title>
        Pessoas Vacinadas | Vaccination Desk
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
          Pessoas Vacinadas
        </Typography>
        <Typography
          sx={{ m: 1 }}
          variant="string"
        >
            Centro de XXXXX
          {/* {centro.nome} */}
        </Typography>
      </Container>
    </Box>
  </>
);
    }

PeopleVaccinated.getLayout = (page) => (
  <DashboardLayoutGerente>
    {page}
  </DashboardLayoutGerente>
);

export default PeopleVaccinated;