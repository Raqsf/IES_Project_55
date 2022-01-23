import { Avatar, Grid, Box, CardActionArea, CardContent, Typography } from '@mui/material';
import VaccinesIcon from '@mui/icons-material/Vaccines';
import React, { useEffect, useState } from 'react';
import api from "../../api";

export const VaccinationRates = ({ periodo }) => {

    const [rate, setRate]=useState(0)

    useEffect(() => {
        const loop = setInterval(function() {
            api.get(`/estatisticas/taxaVacinacaoPorPeriodo/${periodo}`)
                .then(res => {
                setRate(res.data)
            })
            .catch((err) => {
              console.error("ops! ocorreu um erro" + err);
              alert("Erro");
            })
            }, 1000);
        return () => clearInterval(loop);       
      }, [periodo]);

  return (
    <>
    <Box style={{ display:"inline-block", margin:"0 auto"}} width="50%">
        <CardActionArea >
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
                TAXA DE VACINAÇÃO
              </Typography>
              <Typography
                color="textPrimary"
                variant="h4"
              >
                {rate} %
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
                <VaccinesIcon />
              </Avatar>
            </Grid>
          </Grid>
          </CardContent>
        </CardActionArea>
      </Box>
    </>
  );
};