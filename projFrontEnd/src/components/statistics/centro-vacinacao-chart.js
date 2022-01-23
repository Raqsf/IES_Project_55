import { BarChart } from "./bar-chart.js";
import React from 'react';
import { useState, useEffect } from "react";
import api from "../../api";
import { set } from "nprogress";
import { Box, LinearProgress, Typography } from '@mui/material';
import { Pie } from "react-chartjs-2";



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
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(255, 205, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(201, 203, 207, 0.2)'
                        ],
                        hoverOffset: 4
                    }]
                })
            }
        )
        .catch((err) => {
          console.error("ops! ocorreu um erro" + err);
          alert("Erro");
        })
        
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
        .catch((err) => {
          console.error("ops! ocorreu um erro" + err);
          alert("Erro");
        })    
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