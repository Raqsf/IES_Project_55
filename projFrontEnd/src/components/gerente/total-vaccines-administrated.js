import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import NextLink from 'next/link';
import { ChartLine } from 'src/icons/chart-line';
import { useState, useEffect } from "react";
import api from 'src/api';
import { LinearProgress } from '@mui/material';
import VaccinesIcon from '@mui/icons-material/Vaccines';

export const TotalVaccinesAdministrated = () => {
    const [total, setTotal] = useState(0)
    
    useEffect(() => {
        const loop = setInterval(function() {
            api.get(`estatisticas/pessoasVacinadas`)
            .then(res => {
                setTotal(res.data)
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
            Vacinas
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
              backgroundColor: 'warning.main',
              height: 56,
              width: 56
            }}
          >
            <VaccinesIcon />
          </Avatar>
        </Grid>
      </Grid>
      <Grid>
      <Grid container spacing={4} sx={{marginBottom: '55px' }}>
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
          Total de vacinas administradas
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
          {total}
        </Typography>
      </Box>
      </Grid>
      </Grid>
      </Grid>
    </CardContent>
  </Card>
)
}

export default TotalVaccinesAdministrated;