import { Avatar, Box, Card, CardContent, Grid, Typography } from '@mui/material';
import PeopleAlt from '@mui/icons-material/PeopleAlt';
import { useEffect, useState } from 'react';
import api from "../../api";

export const PeopleVaccinated = (props) => {
  const [vaccinated, setVaccinated] = useState();
  const [scheduled, setScheduled] = useState();
  const {id} = props;
  if(id) {
    localStorage.setItem("id_people_vaccinated", id);
  }
  // console.log("PeopleVaccinated", id)

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
          `/estatisticas/pessoasVacinadas/${id}`, headers
        ).then((response) => {
          console.log("Second", id)
          console.log(response.data)
          setVaccinated(response.data);
          // setCentro(response.data);
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
      console.log("Loop", id)
      id = localStorage.getItem("id_people_vaccinated");
      // console.log("Loop", param1)
      api.get(
          `/estatisticas/pessoasVacinadas/${id}`, headers
        ).then((response) => {
          console.log(response.data)
          setVaccinated(response.data);
          // setCentro(response.data);
        })
        .catch((err) => {
          console.error("ops! ocorreu um erro" + err);
          alert("Erro");
        }
      );
      }, 1000);
      return () => clearInterval(loop);
    }, []);

  // /vacinasPrevistas
    useEffect(() => {
      // setLoading(true);
      if (id) {
        api.get(
            `/estatisticas/vacinasPrevistas`, {cv: id}, headers
          ).then((response) => {
            console.log("Second", id)
            console.log(response.data)
            setScheduled(response.data);
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
        console.log("Loop", id)
        id = localStorage.getItem("id_people_vaccinated");
        // console.log("Loop", param1)
        api.get(
            `/estatisticas/vacinasPrevistas`, {cv: id}, headers
          ).then((response) => {
            console.log(response.data)
            setScheduled(response.data);
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
            PESSOAS VACINADAS
          </Typography>
          <Typography
            color="textPrimary"
            variant="h4"
          >
            {vaccinated}
          </Typography>
        </Grid>
        <Grid item>
          <Avatar
            sx={{
              backgroundColor: 'warning.main',
              height: 56,
              width: 56
            }}
          >
            <PeopleAlt />
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
          color="warning.main"
          sx={{
            mr: 1
          }}
          variant="body2"
        >
          Previstas 
        </Typography>
        <Typography
          color="textSecondary"
          variant="caption"
        >
          {scheduled}
        </Typography>
      </Box>
    </CardContent>
  </Card>
  );
}
