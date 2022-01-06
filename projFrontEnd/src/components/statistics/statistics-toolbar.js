import React from 'react';
import {
    Box,
    Typography, 
    InputLabel, 
    Select, 
    MenuItem, FormControl
  } from '@mui/material';
  
  export const StatisticsToolbar = (props) => {
    const [age, setAge] = React.useState('');

    const handleChange = (event) => {
      setAge(event.target.value);
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
        <Box sx={{ m: 1 }}>
            <FormControl sx={{ m: 1, minWidth: 120 }}>
            <InputLabel id="demo-simple-select-helper-label">Período</InputLabel>
            <Select
                labelId="demo-simple-select-helper-label"
                id="demo-simple-select-helper"
                value={age}
                label="Período"
                onChange={handleChange}
            >
                <MenuItem value={"Hoje"}>
                    <em>Hoje</em>
                </MenuItem>
                <MenuItem value={10}>Última semana</MenuItem>
                <MenuItem value={20}>Último mês</MenuItem>
                <MenuItem value={30}>Último ano</MenuItem>
            </Select>
            </FormControl>
        </Box>
      </Box>
    </Box>
  );
}