import { BarChart } from "./bar-chart.js";
import React from 'react';
import { useState, useEffect } from "react";
import api from "../../api";
import VaccinesIcon from '@mui/icons-material/Vaccines';
import { Avatar, Grid, LinearProgress, Box, CardActionArea, CardContent, Typography } from '@mui/material';
import { VaccinationRates } from "./vaccination-rates.js";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure()
const VaccinesChart = ({periodo}) => {

    const [chartData, setChartData] = useState({})
    const [loading, setLoading] = useState(true)
    const [total, setTotal]=useState(0)

    useEffect(() => {
        setLoading(true)
          const loop = setInterval(function() {
            api.get(`/estatisticas/pessoasVacinadasPorPeriodo/${periodo}`)
          .then(res => {
            // periodo no url não atualiza
            var keys = Object.keys(res.data)
            var values = Object.values(res.data)
            setTotal(values.reduce((total, elem) => total = total+elem))
            setChartData({
              labels: keys,
              datasets: [{
                  label: 'Pessoas vacinadas',
                  borderColor: '#0000B9',
                  borderWidth: 1,
                  data: values,
                  backgroundColor: [
                    "#9F9FFF"
                  ]
                }]
            })
            setLoading(false);
          })
          .catch(function (error) {
            if (error.response) {
              toast.error(error.response.data.message, {position: toast.POSITION.TOP_CENTER})
            } else if (error.request) {
              console.log(error.request);
            } else {
              console.log('Error', error.message);
            }
            console.log(error.config);
          });
        }, 1000);
        return () => clearInterval(loop);       
      }, [periodo,total]);
  return (
    <Box>{ !loading && chartData!=null ? <>
      <Box style={{ margin:"0 auto"}} width="50%">
        <BarChart chartData={chartData} />
      </Box>
      { periodo<2 ?<>
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
                TOTAL VACINADAS
              </Typography>
              <Typography
                color="textPrimary"
                variant="h4"
              >
                {total}
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
      <VaccinationRates periodo={periodo}/>
       </> : 
      <Box style={{ margin:"0 auto"}} width="75%">
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
              TOTAL
            </Typography>
            <Typography
              color="textPrimary"
              variant="h4"
            >
              {total}
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
    </Box> }
      </>
    : <Box style={{ margin:"0 auto"}} width="50%"><LinearProgress/></Box>}
    </Box>
    
  )

};

export default VaccinesChart;
