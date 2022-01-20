import { Avatar, Box, Card, CardContent, Grid, Typography, CircularProgress } from '@mui/material';
import VaccinesIcon from '@mui/icons-material/Vaccines';
import { useEffect, useState } from 'react';
import api from "../../api";

export const VaccinesAdministered = (props) => {
  const {id} = props;
  const [totalVaccines, setTotalVaccines] = useState();
  const [loadingVaccines, setLoadingVaccines] = useState(true);
  const [availableVaccines, setAvailableVaccines] = useState();
  const [loadingAvailable, setLoadingAvailable] = useState(true);

  if(id) {
    localStorage.setItem("id_vaccines_administrated", id);
  }
  // console.log("VaccinesAdministrated", id)

  const headers = {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
  };    
      
  useEffect(() => {
    setLoadingVaccines(true);

    if (id) {
      // TOTAL de pessoas vacinadas
      api.get(
          `/estatisticas/pessoasVacinadas/${id}`, headers 
        ).then((response) => {
          // console.log("Second", id)
          // console.log(response.data)
          setTotalVaccines(response.data);
          setLoadingVaccines(false);
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
      // console.log("Loop", id)
      id = localStorage.getItem("id_vaccines_administrated");
      // console.log("Loop", param1)
      api.get(
          `/estatisticas/pessoasVacinadas/${id}`, headers
        ).then((response) => {
          console.log(response.data)
          setTotalVaccines(response.data);
          setLoadingVaccines(false);
        })
        .catch((err) => {
          console.error("ops! ocorreu um erro" + err);
          alert("Erro");
        }
      );
      }, 1000);
      return () => clearInterval(loop);
    }, []);

    useEffect(() => {
      setLoadingAvailable(true);
      if (id) {
        // TOTAL de pessoas vacinadas
        api.get(
            `/estatisticas/vacinasDisponiveis/${id}`, headers 
          ).then((response) => {
            // console.log("Second", id)
            // console.log(response.data)
            setAvailableVaccines(response.data);
            setLoadingAvailable(false);
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
        // console.log("Loop", id)
        id = localStorage.getItem("id_vaccines_administrated");
        // console.log("Loop", param1)
        api.get(
            `/estatisticas/vacinasDisponiveis/${id}`, headers
          ).then((response) => {
            // console.log(response.data)
            setAvailableVaccines(response.data);
            setLoadingAvailable(false);
          })
          .catch((err) => {
            console.error("ops! ocorreu um erro" + err);
            alert("Erro");
          }
        );
        }, 1000);
        return () => clearInterval(loop);
    }, []);

  return (
  <Card
    sx={{ 
      height: '100%',
      ':hover': {
        boxShadow: 20, 
        cursor: "pointer"
    } }}
    {...props}
  >
    <CardContent>
      <Grid
        container
        spacing={3}
        sx={{ justifyContent: 'space-between' }}
      >
        <Grid item>
          <Typography
            color="textSecondary"
            gutterBottom
            variant="overline"
          >
            VACINAS ADMINISTRADAS
          </Typography>
          <Typography
            color="textPrimary"
            variant="h4"
          >
            {loadingVaccines ? 
              <Box sx={{ display: 'flex', mt: 1 }}>
                <CircularProgress size="30px"/> 
              </Box>
            : 
            totalVaccines}
          </Typography>
        </Grid>
        <Grid item>
          <Avatar
            sx={{
              backgroundColor: 'success.main',
              height: 56,
              width: 56
            }}
          >
            <VaccinesIcon />
          </Avatar>
        </Grid>
      </Grid>
      <Box
        sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
        <Typography
          color="success.main"
          sx={{
            mr: 1
          }}
          variant="body2"
        >
          Disponíveis
        </Typography>
        <Typography
          color="textSecondary"
          variant="caption"
        >
          {loadingAvailable ? 
            <CircularProgress size="10px"/> 
          : 
          availableVaccines}
        </Typography>
      </Box>
    </CardContent>
  </Card>
);
}
