import { BarChart } from "./bar-chart.js";
import React from 'react';
import {Bar} from 'react-chartjs-2';
import { useState, useEffect } from "react";
import api from "../../api";
import { LinearProgress } from '@mui/material';

const VaccinesChart = ({periodo}) => {

    // useEffect(() => {
    //     const fetchPrices = async () => {
    //       const res = await fetch("https://api.coincap.io/v2/assets/?limit=5")
    //       const data = await res.json()
    //     //   console.log(data)
    //     }
    //     fetchPrices()
    //   }, []);
      console.log(periodo);
      const [chartData, setChartData] = useState({})
      const [loading, setLoading] = useState(true)

    useEffect(() => {
        setLoading(true)
        api.get(`/estatisticas/pessoasVacinadasPorPeriodo/${periodo}`)
          .then(res => {
            console.log("Chegada da api\n"+res.data)
            var keys = Object.keys(res.data)
            var values = Object.values(res.data)
            setChartData({
                labels: ["Hoje"],
                datasets: [{
                  label: 'Pessoas vacinadas',
                  borderColor: '#0000B9',
                  borderWidth: 1,
                  data: values,
                  backgroundColor: [
                    "#9F9FFF"
                  ] 
                }]
              });
              setLoading(false)
          })
          const loop = setInterval(function() {
            api.get(`/estatisticas/pessoasVacinadasPorPeriodo/${periodo}`)
          .then(res => {
            // periodo no url não atualiza
            // console.log("periodo"+periodo)
            // console.log(res)
            var keys = Object.keys(res.data)
            var values = Object.values(res.data)
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
            });
          });
        }, 1000);
        return () => clearInterval(loop);       
      }, []);

  return ( 
    <div>
         { !loading && chartData!=null ? 
        <BarChart chartData={chartData}/> : <LinearProgress/>
        }
      
    </div>
  )

};

export default VaccinesChart;
