import { Box, Card, CardContent, Typography, Button, FormGroup, FormControlLabel, Checkbox, TextField, LinearProgress } from '@mui/material';
import { useState, useEffect } from 'react';
import api from "../../api";


export const VaccinationOrder = (props) => { 
    const {doencas} = props;
    const [age, setAge] = useState(65);
    const [ageCheck, setAgeCheck] = useState(true);
    const [checkboxes, setCheckboxes] = useState(doencas);
    useEffect(() => {
      // manually deep compare here before updating state
      setCheckboxes(doencas);
    },[doencas])

    const handleChange = (event) => {
        if (event.target.type == "checkbox") {
          // console.log(event.target.value + " " + event.target.checked)
          // console.log(doencas)
          let doe = "";
          for (const d in doencas) {
            if(parseInt(d,10) == event.target.value) {
              // console.log(doencas[d].doenca)
              doe = doencas[d].doenca;

            }
          }
          let newDoencas = [...checkboxes];
          newDoencas[event.target.value] = {id: event.target.value, doenca: doe, checked: event.target.checked}
          setCheckboxes(newDoencas,);
        }

        if (event.target.type == "number") {
          setAge(event.target.value);
        }
        // console.log(checkboxes);
        // alert("Funciona");
    };

    const handleChangeAge = (event) => {
      // console.log(ageCheck)
      setAgeCheck(!ageCheck);
    }

    const handleSubmit = (event) => {
      // alert("Submit");
      // console.log(event.target)
      const headers = {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      };

      let res = []
      for (const c of checkboxes) {
        if(c.checked) {
          res.push(c.id)
        }
      }

      let order = {};
      if(ageCheck){
        order = {
          doencas: res,
          idade: age
        }
      } else {
        order = {
          doencas: res
        }
      }
      
      // api
      // .post(`TODO`, order, headers)
      // .then((response) => {
      //   // if (response.status >= 200 && response.status < 300)
      //   alert("Nova ordem definida")
      // })
      // .catch((err) => {
      //   console.error("ops! ocorreu um erro" + err);
      //   alert("Erro");
      // });
    };
    return(
  <Card {...props}>
    <CardContent>
      <Typography
        color="textPrimary"
        variant="h5"
      >
        Ordem Vacinação
      </Typography>
      <Box
        sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
        <Typography
            color="textSecondary"
            gutterBottom
            variant="body2"
        >
            Doenças
        </Typography>
      </Box>
      <Box
        component="form" 
        onSubmit={handleChange}
        validateForm
        autoComplete="off"
      >
      <Box
        sx={{
            pt: 2,
            display: 'flex',
            alignItems: 'center'
          }}
      >
        <FormGroup 
          sx={{ width: '100%', mb: 2 }}
        >
            {doencas.length > 0 ? doencas.map((doenca) => (
              <FormControlLabel control={<Checkbox onChange={handleChange} value={doenca.id} />} key={doenca.id} label={doenca.doenca} />
            )) :  
            <Box sx={{ width: '100%' }}>
              <LinearProgress />
            </Box>}
            {/* <FormControlLabel control={<Checkbox defaultChecked onChange={handleChange} />} label="Label" />
            <FormControlLabel control={<Checkbox onChange={handleChange} />} label="Label" /> */}
            {/* checked={checked}
            onChange={handleChange}
            inputProps={{ 'aria-label': 'controlled' }} */}
        </FormGroup>
      </Box>
      <Box
        // component="form"
        // sx={{
        //     '& .MuiTextField-root': { m: 1, width: '25ch' },
        // }}
        // noValidate
        // autoComplete="off"
        >
        <Typography
            color="textSecondary"
            gutterBottom
            variant="body2"
        >
            Idade
        </Typography>
        <FormControlLabel value="age_checked" control={<Checkbox onChange={handleChangeAge}/>} label="Ordenar pela idade" />
        <div>
            <TextField
            id="outlined-number"
            label="Idade"
            type="number"
            defaultValue="65"
            InputLabelProps={{
                shrink: true,
            }}
            onChange={handleChange}
            />
        </div>
      </Box>
      <Box sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
          <Button
            color="secondary"
            variant="contained"
            sx={{ mr: 1 }}
            onClick={handleSubmit}
          >
            Guardar
          </Button>
      </Box>
      </Box>
    </CardContent>
  </Card>
);
    }
