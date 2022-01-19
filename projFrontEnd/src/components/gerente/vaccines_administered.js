import { Avatar, Box, Card, CardContent, Grid, Typography } from '@mui/material';
import VaccinesIcon from '@mui/icons-material/Vaccines';
import { useEffect, useState } from 'react';
import api from "../../api";

export const VaccinesAdministered = (props) => {
  const {id} = props;
  const [totalVaccines, setTotalVaccines] = useState();

  if(id) {
    localStorage.setItem("id_vaccines_administrated", id);
  }
  // console.log("VaccinesAdministrated", id)

  const headers = {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
  };    
  // while(id == null) {
  // }
      
  useEffect(() => {
    // setLoading(true);

    if (id) {

      api.get(
          `/centrovacinacao/${id}/vacinas`, headers
        ).then((response) => {
          // console.log("Second", id)
          console.log(response.data)
          setTotalVaccines(response.data);
          // setLoading(false);
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
          `/centrovacinacao/${id}/vacinas`, headers
        ).then((response) => {
          console.log(response.data)
          setTotalVaccines(response.data);
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
    sx={{ height: '100%' }}
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
            10
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
          Faltam/Total (TODO: escolher o q preferirem)
        </Typography>
        <Typography
          color="textSecondary"
          variant="caption"
        >
          {/* 50 (Total para o dia de hoje) */}
          {totalVaccines}
        </Typography>
      </Box>
    </CardContent>
  </Card>
);
}
