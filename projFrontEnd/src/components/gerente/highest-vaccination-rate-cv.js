import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import NextLink from 'next/link';
import { ChartLine } from 'src/icons/chart-line';
import { useState, useEffect } from "react";
import api from 'src/api';
import { LinearProgress } from '@mui/material';

export const HigherVaccinationRateCV = () => {
    const [centroId, setCentroId] = useState(0)
    const [centro, setCentro] = useState("")
    const [rate, setRate] = useState(0)
    
    useEffect(() => {
        const loop = setInterval(function() {
            api.get(`estatisticas/pessoasVacinadasPorTodosCentros`)
            .then(res => {
                
                var keys = Object.keys(res.data)
                var values = Object.values(res.data)
                var idx_max = 0;
                for (var i; i<values.length; i++){
                  if (values[idx_max]<values[i]){
                    idx_max = i;
                  }
                }
                setCentro(keys[idx_max])
                setRate(values[idx_max])
                api.get(`centrovacinacao/pornome/${keys[idx_max]}`)
                .then(res => {
                  setCentroId(res.data.id)
                })
            }
        )    
        }, 1000);
        return () => clearInterval(loop);       
      }, []);

    return (
  <Card>
    <CardContent>
      <Grid
        container
        spacing={3}
        sx={{ justifyContent: 'space-between' }}
      >
        <Grid item
            lg={10}
            sm={10}
            xl={10}
            xs={10}
        >
          <Typography
            color="textPrimary"
            variant="h5"
          >
            Centro com maior taxa de Vacinação
          </Typography>
        </Grid>
        <Grid item
            lg={2}
            sm={2}
            xl={2}
            xs={2}
            >
          <Avatar
            sx={{
              backgroundColor: '#8ae45f',
              height: 56,
              width: 56
            }}
          >
            <ChartLine />
          </Avatar>
        </Grid>
      </Grid>
      {centroId!=0?<>
      <Grid>
      <Grid container spacing={4}>
        <Grid item lg={9} sm={9} xl={9} xs={12}>
      <Box
        sx={{
          pt: 2,
          alignItems: 'center',
          marginTop:'2%'
        }}
      >
        <Typography
            color="textSecondary"
            gutterBottom
            variant="body2"
        >
          {centro}
        </Typography>
      </Box>
      </Grid>
      <Grid item lg={3} sm={3} xl={3} xs={12}>
      <Box
      sx={{
        pt: 2,
        alignItems: 'center'
      }}
      >
        <Typography
            color="textPrimary"
            variant="h4"
        >
          {rate}%
        </Typography>
      </Box>
      </Grid>
      </Grid>
      </Grid>
      <Box sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      > 
        <NextLink href={"/vaccination_center?id="+centroId} passHref>
          <Button
            color="secondary"
            variant="contained"
            sx={{ mr: 1 }}
            // href="/agendamento"
          >
            Ver Centro
          </Button>
        </NextLink>
      </Box></> : <LinearProgress/>}
    </CardContent>
  </Card>
)
}

export default HigherVaccinationRateCV;