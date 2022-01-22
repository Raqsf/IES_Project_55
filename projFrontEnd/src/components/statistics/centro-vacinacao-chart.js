import { BarChart } from "./bar-chart.js";
import React from 'react';
import { useState, useEffect } from "react";
import api from "../../api";
import { set } from "nprogress";
import { Box, LinearProgress } from '@mui/material';
import { Pie } from "react-chartjs-2";



const CentroVacinacaoChart = ({args}) => {
    const cvs = args[0]
    const names = args[1]
    const total = args[2]
    // console.log(props)
    // props.map(elem => console.log(elem))


    //const {cvs,names,total} =args
    //alert(cvs+"\t"+names+"\t"+total)

    const [loading, setLoading] = useState(true)
    const [vacs, setVacs] = useState([])
    const [stats, setStats] = useState({})
    // console.log(cvs)
    useEffect(() => {
        setLoading(true)
        
        console.log("*-* "+cvs)
        const l = []

        cvs.map((cv, idx) =>
        api.get(`/estatisticas/pessoasVacinadas/${cv}`)
            .then(res => {
                // periodo no url não atualiza
                if (total!=0){
                    l[idx] = (res.data/total)
                }
                l[idx] = res.data
                if (idx===(cvs.length-1)){
                    setVacs(l)
                }
            }
        ))
        setStats({
            labels: names,
            datasets: [{
                label: 'Taxa de vacinação de cada centro de vacinação',
                data: vacs,
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
        setLoading(false)
        const loop = setInterval(function() {
            const l = []

            cvs.map((cv, idx) =>
            api.get(`/estatisticas/pessoasVacinadas/${cv}`)
                .then(res => {
                    // periodo no url não atualiza
                    if (total!=0){
                        l[idx] = (res.data/total)
                    }
                    l[idx] = res.data
                    if (idx===(cvs.length-1)){
                        setVacs(l)
                        setLoading(false)
                        console.log("asdsff"+l)
                        console.log("asdsff"+vacs)
                    }
                }
            ))
            setStats({
                labels: names,
                datasets: [{
                    label: 'Taxa de vacinação de cada centro de vacinação',
                    data: vacs,
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
        }, 1000);
        return () => clearInterval(loop);       
      }, [cvs, names, total]);
      
  return (
    <>
        <Box>
          {console.log(stats)}
         {!loading && stats.length!=0 && stats.datasets[0].data.length!=0 
         && stats.datasets[0].data.reduce((total, currentValue) => total = total + currentValue,0)!=0? <>
          {console.log(stats +"\n"+stats.datasets[0].data)}
            <Pie datasets={stats}/></>:
            <LinearProgress/> }
        </Box>
    </>
  )

};

export default CentroVacinacaoChart;