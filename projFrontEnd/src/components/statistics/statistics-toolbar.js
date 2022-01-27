import React from 'react';
import VaccinesChart from "./chart.js";
import {
    Box,
    Typography, 
    InputLabel, 
    Select, 
    MenuItem, FormControl
  } from '@mui/material';
  
  export const StatisticsToolbar = (props) => {
    const [periodo, setPeriodo] = React.useState(0);

    const handleChange = (event) => {
      setPeriodo(event.target.value);
    };
  
    return (
    <Box {...props}>
      <Box
        sx={{
          alignItems: 'center',
          display: 'flex',
          justifyContent: 'space-between',
          flexWrap: 'wrap',
          m: -1
        }}
      >
        <Typography
          sx={{ m: 1 }}
          variant="h4"
        >
          Estatísticas
        </Typography>
        <Box sx={{ m: -1 }}>
            <FormControl sx={{ m: 1, minWidth: 120 }}>
            <InputLabel id="demo-simple-select-helper-label">Período</InputLabel>
            <Select
                labelId="demo-simple-select-helper-label"
                id="demo-simple-select-helper"
                value={periodo}
                label="Período"
                onChange={handleChange}
            >
                <MenuItem value={0}><em>Hoje</em></MenuItem>
                <MenuItem value={1}>Última semana</MenuItem>
                <MenuItem value={2}>Último mês</MenuItem>
                <MenuItem value={3}>Último ano</MenuItem>
            </Select>
            </FormControl>
        </Box>
      </Box>
      <VaccinesChart periodo={periodo}/>
    </Box>
  );
}