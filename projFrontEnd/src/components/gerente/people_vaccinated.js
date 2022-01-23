import { Avatar, Box, Card, CardContent, Grid, Typography, CircularProgress } from '@mui/material';
import PeopleAlt from '@mui/icons-material/PeopleAlt';
import { useEffect, useState } from 'react';
import api from "../../api";
import ErrorAlert from '../erro/erro';

export const PeopleVaccinated = (props) => {
  const [loadingPeople, setLoadingPeople] = useState(true);
  const [loadingScheduled, setLoadingScheduled] = useState(true);
  const [vaccinated, setVaccinated] = useState();
  const [scheduled, setScheduled] = useState();
  const d = new Date().toISOString().split('T')[0];
  let {id} = props;

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
    setLoadingPeople(true);

    if (id) {
      let payload = {data:d}
      api.get(
          `/estatisticas/pessoasVacinadas/${id}`, {params: payload}, headers
        ).then((response) => {
          // console.log("Second", id)
          // console.log(response.data)
          setVaccinated(response.data);
          setLoadingPeople(false);
        })
        .catch(function (error) {
          if (error.response) {
            <ErrorAlert message={error.response}/>
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
          console.log(error.config);
        });
      }
    const loop = setInterval(function() {
      // console.log("Loop", id)
      id = localStorage.getItem("id_people_vaccinated");
      // console.log("Loop", param1)
      let payload = {data:d}
      api.get(
          `/estatisticas/pessoasVacinadas/${id}`, {params: payload}, headers
        ).then((response) => {
          // console.log(response.data)
          setVaccinated(response.data);
          setLoadingPeople(false);
        })
        .catch(function (error) {
          if (error.response) {
            <ErrorAlert message={error.response}/>
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
          console.log(error.config);
        });
      }, 1000);
      return () => clearInterval(loop);
    }, []);

    useEffect(() => {
      setLoadingScheduled(true);
      if (id) {
        api.get(
            `/estatisticas/vacinasPrevistas`, {cv: id}, headers
          ).then((response) => {
            // console.log("Second", id)
            // console.log(response.data)
            setScheduled(response.data);
            setLoadingScheduled(false);
          })
          .catch(function (error) {
            if (error.response) {
              <ErrorAlert message={error.response}/>
            } else if (error.request) {
              console.log(error.request);
            } else {
              console.log('Error', error.message);
            }
            console.log(error.config);
          });
        }
      const loop = setInterval(function() {
        // console.log("Loop", id)
        id = localStorage.getItem("id_people_vaccinated");
        // console.log("Loop", param1)
        api.get(
            `/estatisticas/vacinasPrevistas`, {cv: id}, headers
          ).then((response) => {
            // console.log(response.data)
            setScheduled(response.data);
            setLoadingScheduled(false);
          })
          .catch(function (error) {
            if (error.response) {
              <ErrorAlert message={error.response}/>
            } else if (error.request) {
              console.log(error.request);
            } else {
              console.log('Error', error.message);
            }
            console.log(error.config);
          });
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
            PESSOAS VACINADAS
          </Typography>
          <Typography
            color="textPrimary"
            variant="h4"
          >
            {loadingPeople ? 
              <Box sx={{ display: 'flex', mt: 1 }}>
                <CircularProgress size="30px"/> 
              </Box>
            : 
            vaccinated}
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
          {loadingScheduled ? 
            <CircularProgress size="10px" /> 
          : scheduled}
        </Typography>
      </Box>
    </CardContent>
  </Card>
  );
}
