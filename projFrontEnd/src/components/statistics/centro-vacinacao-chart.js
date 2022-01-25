import React from 'react';
import { useState, useEffect } from "react";
import api from "../../api";
import { Box, LinearProgress, Typography } from '@mui/material';
import { Pie } from "react-chartjs-2";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure()
const CentroVacinacaoChart = () => {

    const [loading, setLoading] = useState(true)
    const [stats, setStats] = useState({})
    
    useEffect(() => {
        setLoading(true)
        api.get(`estatisticas/pessoasVacinadasPorTodosCentros`)
            .then(res => {
                
                var keys = Object.keys(res.data)
                var values = Object.values(res.data)

                setStats({
                    labels: keys,
                    datasets: [{
                        label: 'Taxa de vacinação de cada centro de vacinação',
                        data: values,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.5)',
                            'rgba(255, 159, 64, 0.5)',
                            'rgba(255, 205, 86, 0.5)',
                            'rgba(75, 192, 192, 0.5)',
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(153, 102, 255, 0.5)',
                            'rgba(106, 90, 205, 0.5)',
                            'rgba(201, 203, 207, 0.5)',
                            'rgba(238, 130, 238, 0.5)',
                            'rgba(192, 192, 192, 0.5)',
                            'rgba(255, 255, 0, 0.5)'
                        ],
                        hoverOffset: 4
                    }]
                })
            }
        )
        .catch(function (error) {
            if (error.response) {
              toast.error(error.response.data.message, {position: toast.POSITION.TOP_CENTER});
            } else if (error.request) {
              console.log(error.request);
            } else {
              console.log('Error', error.message);
            }
            console.log(error.config);
          });
        
        setLoading(false)
        const loop = setInterval(function() {
            api.get(`estatisticas/pessoasVacinadasPorTodosCentros`)
            .then(res => {
                
                var keys = Object.keys(res.data)
                var values = Object.values(res.data)
                
                setStats({
                    labels: keys,
                    datasets: [{
                        label: 'Taxa de vacinação de cada centro de vacinação',
                        data: values,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.5)',
                            'rgba(255, 159, 64, 0.5)',
                            'rgba(255, 205, 86, 0.5)',
                            'rgba(75, 192, 192, 0.5)',
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(153, 102, 255, 0.5)',
                            'rgba(106, 90, 205, 0.5)',
                            'rgba(201, 203, 207, 0.5)',
                            'rgba(238, 130, 238, 0.5)',
                            'rgba(192, 192, 192, 0.5)',
                            'rgba(255, 255, 0, 0.5)'
                        ],
                        hoverOffset: 4
                    }]
                })
            }
        )
        .catch(function (error) {
            if (error.response) {
              toast.error(error.response.data.message, {position: toast.POSITION.TOP_CENTER});
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
      <>
      <Typography
          sx={{ m: 1 }}
          variant="h5"
          textAlign={"center"}
        >
          Taxa de vacinação por Centro de Vacinação (%)
        </Typography>
    <Box style={{ margin:"0 auto"}} width="50%" >
        
        { !loading && stats.datasets!=undefined ? 
            <Pie data={stats}/>: <LinearProgress/>
        }
    </Box>
    </>
  )

};

export default CentroVacinacaoChart;