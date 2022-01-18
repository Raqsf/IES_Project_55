import React from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Divider, Grid, TextField, Button } from '@mui/material';
import { DashboardLayoutGerente } from '../components/dashboard-layout-gerente';
//import { customers } from '../__mocks__/customers';
import { useRouter } from "next/router";
import api from "../api";
import { PeopleVaccinated } from "../components/gerente/people_vaccinated";
import { VaccinesAdministered } from "../components/gerente/vaccines_administered";
import { People } from "../components/gerente/people";
// import { useParams } from "react-router-dom";
import { useState } from 'react';

const VaccinationCenter = () => {
    const router = useRouter();
    // const [param1, setParam] = useState();
    const [loading, setLoading] = useState(true);
    const [capacity, setCapacity] = useState();
    const {
        query: { id },
    } = router
    // let param1 = id;
    if(id) {
      localStorage.setItem("id", id);
    }
    // if(id && !localStorage.getItem("id")) {
    //   localStorage.setItem("id", id);
    // } 
    
    // console.log(typeof window !== undefined )
    // if (typeof window !== undefined) {
    //   const c = new URLSearchParams(window.location.search);
    //   alert("It's loaded!")
    //   console.log(c.get('id'))
    // }
    // console.log(router)
    // React.useEffect(() => { 
    //   if(router && router.query.length !== 0) {
    //     console.log(router.query)
    //   }
    // }, [router]);

    // console.log(param1)
    
    // console.log(id);
    
    // const history = createHistory();
    // console.log(window.location.hrefs)
    // history.push()
    // NOTA: acho q dá com o router
    // window.addEventListener('onbeforeunload', alert("HI"));
    
    // React.useEffect(() => {
    //   window.addEventListener("beforeunload", router.reload({pathname: "/vaccination_center", query: id}));
    //   return () => {
    //     window.removeEventListener("beforeunload", null);
    //   };
    // }, []);
    const [centro, setCentro] = React.useState('');

    const headers = {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    };    
    
    React.useEffect(() => {
      setLoading(true);
      // setParam(id);
      // console.log(id, !id)
      // if(id) {
        // id = localStorage.getItem("id");
      // }
      // console.log("First", id)
      if (id) {
        // id = localStorage.getItem("id");
        api.get(
            `/centrovacinacao/${id}`, headers
          ).then((response) => {
            // console.log("Second", id)
            setCentro(response.data);
            setCapacity(response.data.capacidadeMax);
            setLoading(false);
          })
          .catch((err) => {
            console.error("ops! ocorreu um erro" + err);
            alert("Erro");
            // if(response.status === 500 && typeof id == undefined) {
            //   alert("Erro")
            // }
          })
        }
      const loop = setInterval(function() {
        console.log("Loop", id)
        id = localStorage.getItem("id");
        // console.log("Loop", param1)
        api.get(
            `/centrovacinacao/${id}`, headers
          ).then((response) => {
            setCentro(response.data);
            setCapacity(response.data.capacidadeMax);
          })
          .catch((err) => {
            console.error("ops! ocorreu um erro" + err);
            alert("Erro");
          }
        );
        }, 1000);
        return () => clearInterval(loop);
      }, []);
    // console.log("ID",id)

    function handleSubmit(e) {
      e.preventDefault();
      console.log(capacity)
    
      api
      .put(`/centrovacinacao/${id}/capacidade`, capacity, headers)
      .then((response) => {
        // if (response.status >= 200 && response.status < 300)
        alert("Nova ordem definida")
      })
      .catch((err) => {
        console.error("ops! ocorreu um erro" + err);
      })
    }
    
    const handleChange = (event) => {
      console.log(event.target.value)
      setCapacity(event.target.value);
    }

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
          {centro.nome}
        </Typography>
        <Typography
          sx={{ m: 1 }}
          variant="string"
        >
          {centro.morada}
        </Typography>
        {/* TODO: numero de pessoas vacinadas e numero vacinas em tempo real */}
        <Grid container spacing={2}>
          <Grid item lg={6} sm={6} xl={6} xs={12}>
            <PeopleVaccinated id={id}/>
          </Grid>
          <Grid item xl={6} lg={6} sm={6} xs={12}>
            <VaccinesAdministered id={id}/>
          </Grid>
        </Grid>
        {/*<Divider 
          sx={{
            py:3
          }}
        />
        <TableVaccines />      */}
      </Container>
      <Container maxWidth={false} sx={{ mt: 4 }}>
        <Box>
          <People />
        </Box>
      </Container>
      <Container maxWidth={false} sx={{ mt: 4 }}>
        <Box 
          component="form"
          sx={{
              '& .MuiTextField-root': { m: 1, width: '25ch' },
          }}
          noValidate
          autoComplete="off"
        > 
          <Typography
            sx={{ m: 1 }}
            variant="h6"
          >
            Capacidade Máxima
          </Typography>
          {centro ? <TextField
            id="max-vaccines"
            label="Vacinas"
            type="number"
            defaultValue={centro.capacidadeMax}
            InputLabelProps={{
                shrink: true,
            }}
            onChange={handleChange}
          />: null}
          {/* {centro ? <TextField
            id="max-people"
            label="Pessoas"
            type="number"
            defaultValue="1"
            InputLabelProps={{
                shrink: true,
            }}
          />: null} */}
          <Box sx={{
              pt: 2,
              display: 'flex',
              alignItems: 'center'
            }}
          >
            <Button
              color="primary"
              variant="contained"
              sx={{ mr: 1 }}
              onClick={handleSubmit}
            >
              Guardar
            </Button>
          </Box>
        </Box>
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