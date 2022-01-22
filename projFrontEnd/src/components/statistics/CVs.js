import { BarChart } from "./bar-chart.js";
import React from 'react';
import { useState, useEffect } from "react";
import api from "../../api";
import { set } from "nprogress";
import { Box, LinearProgress } from '@mui/material';
import { Pie } from "react-chartjs-2";
import CentroVacinacaoChart from "./centro-vacinacao-chart.js";


const CVs = () => {

    const [loading, setLoading] = useState(true)
    const [centersID, setCentersID] = useState([]);
    const [cv, setCV] = useState([])
    const [total, setTotal] = useState(1)

    useEffect(() => {
        setLoading(true)
        api.get(`/centrovacinacao`)
          .then(res => {
            const l = []
            setCentersID(res.data.map((obj, idx) => l[idx] = obj.id))
            setCV(res.data.map((obj, idx) => l[idx] = obj.nome))
          })
        // api.get(`/estatisticas/pessoasVacinadas`)
        //     .then(res => {
        //     setTotal(res.data)
        //     })
        setLoading(false)
        // const loop = setInterval(function() {
        //     api.get(`/centrovacinacao`)
        //     .then(res => {
        //         const l = []
        //         console.log(res)
        //         setCentersID(res.data.map((obj, idx) => l[idx] = obj.id))
        //         setCV(res.data.map((obj, idx) => l[idx] = obj.nome))
        //     })
        //     api.get(`/estatisticas/pessoasVacinadas`)
        //         .then(res => {
        //         setTotal(res.data)
        //         })
        // }, 1000);
        // return () => clearInterval(loop);       
      }, []);
    console.log(total)
    const a = [centersID, cv, total]
  return (
    <>
        {!loading && cv.length!=0 && centersID.length!=0 ?
        
        <>
        <CentroVacinacaoChart args={a}/>
        </>
        :<LinearProgress/>}
    </>
  )

};

export default CVs;